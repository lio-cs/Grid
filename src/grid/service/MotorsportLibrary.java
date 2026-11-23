package grid.service;

import grid.model.CarComponent;
import grid.model.Driver;
import grid.model.Manufacturer;
import grid.model.RaceCar;
import grid.model.Team;
import grid.model.Track;
import grid.observer.LibraryObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Central Motorsport Database implementing the Thread-Safe Singleton Pattern
 * (Guide/tasks.pdf Exercise 1) and acting as the Subject in the Observer Pattern (Exercise 2).
 */
public class MotorsportLibrary {
    // Thread-safe volatile Singleton instance (Exercise 1 Additional Challenge)
    private static volatile MotorsportLibrary instance;

    private final List<Manufacturer> manufacturers;
    private final List<Team> teams;
    private final List<Driver> drivers;
    private final List<Track> tracks;
    private final List<RaceCar> raceCars;
    private final List<CarComponent> userGarage;
    private final List<LibraryObserver> observers;

    /**
     * Private constructor prevents outside classes from calling `new MotorsportLibrary()`.
     */
    private MotorsportLibrary() {
        this.manufacturers = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.drivers = new ArrayList<>();
        this.tracks = new ArrayList<>();
        this.raceCars = new ArrayList<>();
        this.userGarage = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Thread-safe double-checked locking getInstance() method.
     */
    public static MotorsportLibrary getInstance() {
        if (instance == null) {
            synchronized (MotorsportLibrary.class) {
                if (instance == null) {
                    instance = new MotorsportLibrary();
                }
            }
        }
        return instance;
    }

    // =========================================================================
    // Observer Pattern Methods (Subscribe, Unsubscribe, Notify)
    // =========================================================================

    public void subscribe(LibraryObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(LibraryObserver observer) {
        observers.remove(observer);
    }

    public boolean isSubscribed(LibraryObserver observer) {
        return observers.contains(observer);
    }

    public void notifyObservers(String eventType, String message) {
        for (LibraryObserver observer : observers) {
            observer.update(eventType, message);
        }
    }

    // =========================================================================
    // Data Seeding & Registration Methods
    // =========================================================================

    public void clearAllCatalogData() {
        manufacturers.clear();
        teams.clear();
        drivers.clear();
        tracks.clear();
        raceCars.clear();
    }

    public void addManufacturerSilently(Manufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }

    public void addTeamSilently(Team team) {
        teams.add(team);
    }

    public void addDriverSilently(Driver driver) {
        drivers.add(driver);
    }

    public void addTrackSilently(Track track) {
        tracks.add(track);
    }

    public void addRaceCarSilently(RaceCar car) {
        raceCars.add(car);
    }

    public void registerNewRaceCar(RaceCar car) {
        raceCars.add(car);
        Manufacturer m = getManufacturerById(car.getManufacturerId());
        String makerName = (m != null) ? m.getName() : car.getManufacturerId();
        notifyObservers(
                "NEW_CAR",
                String.format("Registered %s (%s) under %s in %s",
                        car.getName(), car.getCarClass(), makerName, car.getSanctionedSeries()));
    }

    public void addAccoladeToCar(RaceCar car, String accolade) {
        car.addAccolade(accolade);
        notifyObservers(
                "NEW_ACCOLADE",
                String.format("Added accolade '%s' to %s", accolade, car.getName()));
    }

    // =========================================================================
    // Garage Management (Supports Decorator Wrapping)
    // =========================================================================

    public void bookmarkCarToGarage(CarComponent car) {
        userGarage.add(car);
        notifyObservers(
                "GARAGE_BOOKMARK",
                String.format("Bookmarked '%s' into Personal Garage.", car.getName()));
    }

    public void updateGarageCarSetup(int garageIndex, CarComponent decoratedCar, String upgradeName) {
        if (garageIndex >= 0 && garageIndex < userGarage.size()) {
            userGarage.set(garageIndex, decoratedCar);
            notifyObservers(
                    "GARAGE_TUNING",
                    String.format("Applied %s to Garage Car -> %s (%d HP, %d km/h, %d kg)",
                            upgradeName,
                            decoratedCar.getName(),
                            decoratedCar.getHorsepower(),
                            decoratedCar.getTopSpeedKph(),
                            decoratedCar.getWeightKg()));
        }
    }

    public List<CarComponent> getUserGarage() {
        return Collections.unmodifiableList(userGarage);
    }

    // =========================================================================
    // Manufacturer -> Car -> Teams / Drivers / Tracks Drill-Down Lookups
    // =========================================================================

    public List<Manufacturer> getManufacturers() {
        return Collections.unmodifiableList(manufacturers);
    }

    public List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }

    public List<Driver> getDrivers() {
        return Collections.unmodifiableList(drivers);
    }

    public List<Track> getTracks() {
        return Collections.unmodifiableList(tracks);
    }

    public List<RaceCar> getRaceCars() {
        return Collections.unmodifiableList(raceCars);
    }

    public Manufacturer getManufacturerById(String id) {
        for (Manufacturer m : manufacturers) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    public Team getTeamById(String id) {
        for (Team t : teams) {
            if (t.getId().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    public Driver getDriverById(String id) {
        for (Driver d : drivers) {
            if (d.getId().equalsIgnoreCase(id)) {
                return d;
            }
        }
        return null;
    }

    public Track getTrackById(String id) {
        for (Track tr : tracks) {
            if (tr.getId().equalsIgnoreCase(id)) {
                return tr;
            }
        }
        return null;
    }

    /**
     * Returns all sanctioned RaceCars built by a specific Manufacturer.
     */
    public List<RaceCar> getCarsByManufacturer(String manufacturerId) {
        List<RaceCar> result = new ArrayList<>();
        for (RaceCar car : raceCars) {
            if (car.getManufacturerId().equalsIgnoreCase(manufacturerId)) {
                result.add(car);
            }
        }
        return result;
    }

    /**
     * Resolves all Team objects that operate the given RaceCar.
     */
    public List<Team> getTeamsForCar(RaceCar car) {
        List<Team> result = new ArrayList<>();
        for (String teamId : car.getTeamIds()) {
            Team team = getTeamById(teamId);
            if (team != null) {
                result.add(team);
            }
        }
        return result;
    }

    /**
     * Resolves all Driver objects who have piloted the given RaceCar.
     */
    public List<Driver> getDriversForCar(RaceCar car) {
        List<Driver> result = new ArrayList<>();
        for (String driverId : car.getDriverIds()) {
            Driver driver = getDriverById(driverId);
            if (driver != null) {
                result.add(driver);
            }
        }
        return result;
    }

    /**
     * Resolves all Track objects that the given RaceCar has competed on.
     */
    public List<Track> getTracksForCar(RaceCar car) {
        List<Track> result = new ArrayList<>();
        for (String trackId : car.getTrackIds()) {
            Track track = getTrackById(trackId);
            if (track != null) {
                result.add(track);
            }
        }
        return result;
    }
}
