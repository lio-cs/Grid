package grid;

import grid.decorator.AeroKitDecorator;
import grid.decorator.SlickTiresDecorator;
import grid.decorator.TurboDecorator;
import grid.factory.RaceCarFactory;
import grid.model.CarComponent;
import grid.model.Driver;
import grid.model.Manufacturer;
import grid.model.RaceCar;
import grid.model.Team;
import grid.model.Track;
import grid.observer.ActivityLogObserver;
import grid.observer.ConsoleAlertObserver;
import grid.service.MotorsportLibrary;
import grid.storage.CsvStorageService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point and interactive console menu for the Grid Motorsport Library Management System.
 *
 * Core Flow:
 * 1. User selects a Manufacturer (Ferrari, Porsche, Mercedes-AMG, McLaren)
 * 2. App displays all sanctioned Race Cars built by that Manufacturer
 * 3. User selects a Race Car to inspect its Official Dossier:
 *    - Sanctioned Series & Class Regulations
 *    - Technical Specifications (Engine, HP, Top Speed, Weight)
 *    - Major Accolades
 *    - Operating Teams
 *    - Drivers
 *    - Tracks Run On
 */
public class Main {
    private static final String DATA_DIR = "data";

    public static void main(String[] args) {
        // 1. Obtain the single shared MotorsportLibrary instance (Singleton Pattern)
        MotorsportLibrary library = MotorsportLibrary.getInstance();

        // 2. Load catalog data from CSV files (uses Factory Method internally for RaceCars)
        CsvStorageService storage = new CsvStorageService(DATA_DIR);
        try {
            storage.loadAll(library);
        } catch (IOException e) {
            System.out.println("Warning: Could not load CSV files: " + e.getMessage());
        }

        // 3. Register Observers (Observer Pattern)
        ConsoleAlertObserver liveAlertObserver = new ConsoleAlertObserver("PaddockPassUser");
        ActivityLogObserver activityLogObserver = new ActivityLogObserver();
        library.subscribe(liveAlertObserver);
        library.subscribe(activityLogObserver);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("+======================================================================+");
        System.out.println("| WELCOME TO GRID: MOTORSPORT ARCHIVE & GARAGE                         |");
        System.out.println("| Explore Sanctioned Race Cars, Specs, Accolades, Teams & Tracks       |");
        System.out.println("+======================================================================+");
        System.out.println("  [IP Notice] Non-commercial academic project. All manufacturer names, car models,");
        System.out.println("  teams, tracks & series (Ferrari, Porsche, Mercedes-AMG, McLaren, FIA, F1, WEC)");
        System.out.println("  are trademarks & intellectual property of their respective owners.");

        while (running) {
            System.out.println("\n+======================================================================+");
            System.out.println("|                 GRID - MOTORSPORT LIBRARY MAIN MENU                  |");
            System.out.println("+======================================================================+");
            System.out.println("  1. Select a Manufacturer (Browse Cars, Specs, Teams, Drivers & Tracks)");
            System.out.println("  2. Register a New Sanctioned Race Car (Factory Method Pattern)");
            System.out.println("  3. My Garage & Setup Tuner (Decorator Pattern)");
            System.out.println("  4. Notification Center & Activity Log (Observer Pattern)");
            System.out.println("  0. Save & Exit");
            System.out.println("------------------------------------------------------------------------");
            System.out.print("Select an option (0-4): ");

            String choice = readLine(scanner);
            if (choice == null) break;

            switch (choice) {
                case "1":
                    handleManufacturerFlow(scanner, library);
                    break;
                case "2":
                    handleRegisterNewCarFlow(scanner, library);
                    break;
                case "3":
                    handleGarageFlow(scanner, library);
                    break;
                case "4":
                    handleObserverCenterFlow(scanner, library, liveAlertObserver, activityLogObserver);
                    break;
                case "0":
                    saveAndExit(storage, library);
                    running = false;
                    break;
                default:
                    System.out.println("  [!] Invalid option. Please enter a number from 0 to 4.");
            }
        }
    }

    // =========================================================================
    // 1. Manufacturer -> Sanctioned Race Cars -> Full Car Dossier Flow
    // =========================================================================

    private static void handleManufacturerFlow(Scanner scanner, MotorsportLibrary library) {
        List<Manufacturer> manufacturers = library.getManufacturers();
        if (manufacturers.isEmpty()) {
            System.out.println("  No manufacturers found in the library.");
            return;
        }

        while (true) {
            System.out.println("\n+----------------------------------------------------------------------+");
            System.out.println("|                       SELECT A MANUFACTURER                          |");
            System.out.println("+----------------------------------------------------------------------+");
            for (int i = 0; i < manufacturers.size(); i++) {
                Manufacturer m = manufacturers.get(i);
                int carCount = library.getCarsByManufacturer(m.getId()).size();
                System.out.printf("  %d. %-16s (%s) | %d Sanctioned Car(s)%n",
                        i + 1, m.getName(), m.getDetails(), carCount);
            }
            System.out.println("  0. Back to Main Menu");
            System.out.print("Pick a Manufacturer (number): ");

            String input = readLine(scanner);
            if (input == null || input.equals("0")) return;

            int index = parseIndex(input, manufacturers.size());
            if (index == -1) {
                System.out.println("  [!] Invalid selection.");
                continue;
            }

            Manufacturer selectedMaker = manufacturers.get(index);
            handleManufacturerCarsMenu(scanner, library, selectedMaker);
        }
    }

    private static void handleManufacturerCarsMenu(
            Scanner scanner, MotorsportLibrary library, Manufacturer maker) {
        while (true) {
            List<RaceCar> cars = library.getCarsByManufacturer(maker.getId());
            System.out.println("\n+======================================================================+");
            System.out.printf("| %-68s |%n", maker.getName().toUpperCase() + " - SANCTIONED RACING CARS");
            System.out.printf("| %-68s |%n", maker.getDetails());
            System.out.println("+======================================================================+");

            if (cars.isEmpty()) {
                System.out.println("  No sanctioned cars registered for this manufacturer yet.");
                return;
            }

            for (int i = 0; i < cars.size(); i++) {
                RaceCar c = cars.get(i);
                System.out.printf("  %d. %-34s [%-9s] | %d HP | Series: %s%n",
                        i + 1, c.getName(), c.getCarClass(), c.getHorsepower(), c.getSanctionedSeries());
            }
            System.out.println("  0. Back to Manufacturers");
            System.out.print("Select a Race Car to inspect its complete dossier: ");

            String input = readLine(scanner);
            if (input == null || input.equals("0")) return;

            int index = parseIndex(input, cars.size());
            if (index == -1) {
                System.out.println("  [!] Invalid selection.");
                continue;
            }

            RaceCar selectedCar = cars.get(index);
            displayCarDossier(scanner, library, maker, selectedCar);
        }
    }

    private static void displayCarDossier(
            Scanner scanner, MotorsportLibrary library, Manufacturer maker, RaceCar car) {
        while (true) {
            System.out.println("\n+======================================================================+");
            System.out.printf("| %-68s |%n", "OFFICIAL DOSSIER: " + car.getName() + " (" + maker.getName() + ")");
            System.out.println("+======================================================================+");
            // Polymorphic call: each RaceCar subclass prints its own car silhouette
            System.out.println(car.getAsciiSilhouette());

            // 1. Sanctioned Series & Regulations
            System.out.println("  [SANCTIONED SERIES & REGULATIONS]");
            System.out.println("    * Series       : " + car.getSanctionedSeries());
            System.out.println("    * Category     : " + car.getCarClass());
            System.out.println("    * Regulations  : " + car.getRegulationNotes());

            // 2. Technical Specifications
            System.out.println("\n  [TECHNICAL SPECIFICATIONS]");
            System.out.println("    * Engine       : " + car.getEngineSpec());
            System.out.println("    * Power Output : " + car.getHorsepower() + " HP");
            System.out.println("    * Top Speed    : " + car.getTopSpeedKph() + " km/h");
            System.out.println("    * Minimum Wt.  : " + car.getWeightKg() + " kg");

            // 3. Accolades
            System.out.println("\n  [MAJOR ACCOLADES & VICTORIES]");
            if (car.getAccolades().isEmpty()) {
                System.out.println("    * None recorded yet.");
            } else {
                for (String acc : car.getAccolades()) {
                    System.out.println("    * " + acc);
                }
            }

            // 4. Operating Teams
            List<Team> carTeams = library.getTeamsForCar(car);
            System.out.println("\n  [TEAMS OPERATING THIS CAR]");
            if (carTeams.isEmpty()) {
                System.out.println("    * No registered teams linked.");
            } else {
                for (Team t : carTeams) {
                    System.out.printf("    * %s (%s) - %s%n", t.getName(), t.getCountry(), t.getDetails());
                }
            }

            // 5. Notable Drivers
            List<Driver> carDrivers = library.getDriversForCar(car);
            System.out.println("\n  [NOTABLE DRIVERS]");
            if (carDrivers.isEmpty()) {
                System.out.println("    * No registered drivers linked.");
            } else {
                for (Driver d : carDrivers) {
                    Team driverTeam = library.getTeamById(d.getTeamId());
                    String teamLabel = (driverTeam != null) ? driverTeam.getName() : d.getTeamId();
                    System.out.printf("    * %-22s | #%2d | %-14s | Team: %-28s | Titles: %d%n",
                            d.getName(), d.getCarNumber(), d.getCountry(), teamLabel, d.getChampionships());
                }
            }

            // 6. Tracks Competed On
            List<Track> carTracks = library.getTracksForCar(car);
            System.out.println("\n  [SANCTIONED TRACKS RUN ON]");
            if (carTracks.isEmpty()) {
                System.out.println("    * No tracks linked.");
            } else {
                for (Track tr : carTracks) {
                    System.out.printf("    * %-32s (%s) | %s%n",
                            tr.getName(), tr.getCountry(), tr.getDetails());
                }
            }

            System.out.println("------------------------------------------------------------------------");
            System.out.println("  Actions for " + car.getName() + ":");
            System.out.println("    1. Bookmark this Car to My Garage (for Decorator Setup Tuning)");
            System.out.println("    2. Add a New Accolade / Race Win to this Car");
            System.out.println("    0. Back to " + maker.getName() + " Car List");
            System.out.print("Choose an action (0-2): ");

            String action = readLine(scanner);
            if (action == null || action.equals("0")) return;

            if ("1".equals(action)) {
                library.bookmarkCarToGarage(car);
            } else if ("2".equals(action)) {
                System.out.print("  Enter new accolade (e.g., '2025 24 Hours of Daytona Winner'): ");
                String newAcc = readLine(scanner);
                if (newAcc != null && !newAcc.trim().isEmpty()) {
                    library.addAccoladeToCar(car, newAcc.trim());
                }
            } else {
                System.out.println("  [!] Invalid option.");
            }
        }
    }

    // =========================================================================
    // 2. Register a New Sanctioned Race Car (Factory Method Pattern)
    // =========================================================================

    private static void handleRegisterNewCarFlow(Scanner scanner, MotorsportLibrary library) {
        System.out.println("\n--- REGISTER NEW SANCTIONED RACE CAR (FACTORY METHOD) ---");
        List<Manufacturer> makers = library.getManufacturers();
        for (int i = 0; i < makers.size(); i++) {
            System.out.printf("  %d. %s (%s)%n", i + 1, makers.get(i).getName(), makers.get(i).getId());
        }
        System.out.print("Select Manufacturer (number): ");
        String mInput = readLine(scanner);
        int mIdx = parseIndex(mInput, makers.size());
        if (mIdx == -1) {
            System.out.println("  [!] Cancelled or invalid manufacturer.");
            return;
        }
        Manufacturer maker = makers.get(mIdx);

        System.out.println("Select Car Regulation Class:");
        System.out.println("  1. FORMULA   (Single-seater open-wheel)");
        System.out.println("  2. PROTOTYPE (Le Mans Hypercar / LMDh)");
        System.out.println("  3. GT        (Homologated GT3 / GTE)");
        System.out.print("Choice (1-3): ");
        String classChoice = readLine(scanner);
        String carClass;
        if ("1".equals(classChoice)) carClass = "FORMULA";
        else if ("2".equals(classChoice)) carClass = "PROTOTYPE";
        else if ("3".equals(classChoice)) carClass = "GT";
        else {
            System.out.println("  [!] Invalid car class.");
            return;
        }

        System.out.print("Enter Car Name (e.g., Ferrari F2004): ");
        String name = readLine(scanner);
        System.out.print("Enter Sanctioned Series (e.g., FIA Formula 1 World Championship): ");
        String series = readLine(scanner);
        System.out.print("Enter Engine Spec (e.g., 3.0L V10 Naturally Aspirated): ");
        String engine = readLine(scanner);
        System.out.print("Enter Horsepower (e.g., 920): ");
        int hp = parsePositiveInt(readLine(scanner), 650);
        System.out.print("Enter Top Speed in km/h (e.g., 360): ");
        int topSpeed = parsePositiveInt(readLine(scanner), 320);
        System.out.print("Enter Weight in kg (e.g., 605): ");
        int weight = parsePositiveInt(readLine(scanner), 800);
        System.out.print("Enter a headline Accolade (optional): ");
        String accolade = readLine(scanner);

        String newId = "C-" + (library.getRaceCars().size() + 100);

        RaceCarFactory factory = RaceCarFactory.getFactoryForClass(carClass);
        RaceCar newCar = factory.createCar(
                newId, name, maker.getCountry(), maker.getId(), series, engine, hp, topSpeed, weight);

        if (accolade != null && !accolade.trim().isEmpty()) {
            newCar.addAccolade(accolade.trim());
        }

        for (Team t : library.getTeams()) {
            if (t.getManufacturerId().equalsIgnoreCase(maker.getId())) {
                newCar.addTeamId(t.getId());
            }
        }
        if (!library.getTracks().isEmpty()) {
            newCar.addTrackId(library.getTracks().get(0).getId());
        }

        library.registerNewRaceCar(newCar);
    }

    // =========================================================================
    // 3. My Garage & Setup Tuner (Decorator Pattern)
    // =========================================================================

    private static void handleGarageFlow(Scanner scanner, MotorsportLibrary library) {
        while (true) {
            List<CarComponent> garage = library.getUserGarage();
            System.out.println("\n+======================================================================+");
            System.out.println("|        MY PERSONAL GARAGE & SETUP TUNER (DECORATOR PATTERN)          |");
            System.out.println("+======================================================================+");

            if (garage.isEmpty()) {
                System.out.println("  Your garage is currently empty.");
                System.out.println("  Tip: Browse a Manufacturer -> Pick a Car -> Select '1. Bookmark to Garage'!");
                return;
            }

            for (int i = 0; i < garage.size(); i++) {
                CarComponent c = garage.get(i);
                System.out.printf("  %d. %s%n", i + 1, c.getSpecSummary());
            }
            System.out.println("------------------------------------------------------------------------");
            System.out.print("Select a Garage Car number [1-" + garage.size() + "] to tune (or 0 to go back): ");
            String input = readLine(scanner);
            if (input == null || input.equals("0")) return;

            int idx = parseIndex(input, garage.size());
            if (idx == -1) {
                System.out.println("  [!] Invalid car selection.");
                continue;
            }

            CarComponent currentCar = garage.get(idx);
            System.out.println("\nSelected Garage Car: " + currentCar.getName());
            System.out.println(currentCar.getAsciiSilhouette());

            System.out.println("Choose a Setup Decorator to wrap around this car:");
            System.out.println("  1. Apply Low-Drag Aero Package Decorator (+15 km/h Top Speed, -5 kg Weight)");
            System.out.println("  2. Apply Qualifying Engine Boost Decorator (+65 HP, +8 km/h Top Speed)");
            System.out.println("  3. Apply Soft-Compound Racing Slicks Decorator (+10 km/h Speed, -8 kg Weight)");
            System.out.println("  0. Cancel");
            System.out.print("Select option (0-3): ");

            String upChoice = readLine(scanner);
            if ("1".equals(upChoice)) {
                library.updateGarageCarSetup(idx, new AeroKitDecorator(currentCar), "Low-Drag Aero Package");
            } else if ("2".equals(upChoice)) {
                library.updateGarageCarSetup(idx, new TurboDecorator(currentCar), "Qualifying Engine Boost");
            } else if ("3".equals(upChoice)) {
                library.updateGarageCarSetup(idx, new SlickTiresDecorator(currentCar), "Soft Racing Slicks");
            }
        }
    }

    // =========================================================================
    // 4. Notification Center & Activity Log (Observer Pattern)
    // =========================================================================

    private static void handleObserverCenterFlow(
            Scanner scanner,
            MotorsportLibrary library,
            ConsoleAlertObserver liveObserver,
            ActivityLogObserver logObserver) {
        while (true) {
            boolean isLiveSubscribed = library.isSubscribed(liveObserver);
            System.out.println("\n+======================================================================+");
            System.out.println("|                 NOTIFICATION CENTER (OBSERVER PATTERN)               |");
            System.out.println("+======================================================================+");
            System.out.println("  Live Console Alerts: " + (isLiveSubscribed ? "SUBSCRIBED (ON)" : "UNSUBSCRIBED (OFF)"));
            System.out.println("  Recorded Activity Log Events (" + logObserver.getEntries().size() + "):");
            if (logObserver.getEntries().isEmpty()) {
                System.out.println("    * No events logged yet in this session.");
            } else {
                for (String entry : logObserver.getEntries()) {
                    System.out.println("    * " + entry);
                }
            }
            System.out.println("------------------------------------------------------------------------");
            System.out.println("  1. Toggle Live Console Alerts Subscribe / Unsubscribe");
            System.out.println("  0. Back to Main Menu");
            System.out.print("Select option: ");

            String input = readLine(scanner);
            if (input == null || input.equals("0")) return;
            if ("1".equals(input)) {
                if (isLiveSubscribed) {
                    library.unsubscribe(liveObserver);
                    System.out.println("  [i] Unsubscribed ConsoleAlertObserver. You will no longer see live popups.");
                } else {
                    library.subscribe(liveObserver);
                    System.out.println("  [i] Subscribed ConsoleAlertObserver. Live popups are now active!");
                }
            }
        }
    }

    private static void saveAndExit(CsvStorageService storage, MotorsportLibrary library) {
        try {
            storage.saveAll(library);
            System.out.println("\n  [OK] All catalog changes saved to CSV files in '" + DATA_DIR + "/'.");
        } catch (IOException e) {
            System.out.println("\n  [!] Failed to save CSV files: " + e.getMessage());
        }
        System.out.println("  Thank you for using Grid Motorsport Library. Goodbye!");
    }

    // =========================================================================
    // Input Helpers
    // =========================================================================

    private static String readLine(Scanner scanner) {
        if (!scanner.hasNextLine()) return null;
        return scanner.nextLine().replace("\uFEFF", "").trim();
    }

    private static int parseIndex(String input, int maxSize) {
        if (input == null) return -1;
        try {
            int val = Integer.parseInt(input.trim());
            if (val >= 1 && val <= maxSize) {
                return val - 1;
            }
        } catch (NumberFormatException ignored) {
        }
        return -1;
    }

    private static int parsePositiveInt(String input, int fallback) {
        if (input == null) return fallback;
        try {
            int val = Integer.parseInt(input.trim());
            return val > 0 ? val : fallback;
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
