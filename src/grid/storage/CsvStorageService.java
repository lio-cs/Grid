package grid.storage;

import grid.factory.RaceCarFactory;
import grid.model.Driver;
import grid.model.Manufacturer;
import grid.model.RaceCar;
import grid.model.Team;
import grid.model.Track;
import grid.service.MotorsportLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handles loading and saving all MotorsportLibrary records to plain CSV files in the `data/` folder.
 * Uses standard Java I/O (BufferedReader and PrintWriter) with zero external library dependencies.
 */
public class CsvStorageService {
    private final String dataDir;

    public CsvStorageService(String dataDir) {
        this.dataDir = dataDir;
    }

    public void loadAll(MotorsportLibrary library) throws IOException {
        library.clearAllCatalogData();
        loadManufacturers(library, new File(dataDir, "manufacturers.csv"));
        loadTeams(library, new File(dataDir, "teams.csv"));
        loadDrivers(library, new File(dataDir, "drivers.csv"));
        loadTracks(library, new File(dataDir, "tracks.csv"));
        loadCars(library, new File(dataDir, "cars.csv"));
    }

    public void saveAll(MotorsportLibrary library) throws IOException {
        File dir = new File(dataDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        saveManufacturers(library, new File(dir, "manufacturers.csv"));
        saveTeams(library, new File(dir, "teams.csv"));
        saveDrivers(library, new File(dir, "drivers.csv"));
        saveTracks(library, new File(dir, "tracks.csv"));
        saveCars(library, new File(dir, "cars.csv"));
    }

    private void loadManufacturers(MotorsportLibrary library, File file) throws IOException {
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 5) {
                    Manufacturer m = new Manufacturer(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim()),
                            parts[4].trim());
                    library.addManufacturerSilently(m);
                }
            }
        }
    }

    private void loadTeams(MotorsportLibrary library, File file) throws IOException {
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    Team t = new Team(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[4].trim(),
                            parts[5].trim());
                    library.addTeamSilently(t);
                }
            }
        }
    }

    private void loadDrivers(MotorsportLibrary library, File file) throws IOException {
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    Driver d = new Driver(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Integer.parseInt(parts[3].trim()),
                            parts[4].trim(),
                            Integer.parseInt(parts[5].trim()));
                    library.addDriverSilently(d);
                }
            }
        }
    }

    private void loadTracks(MotorsportLibrary library, File file) throws IOException {
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 6) {
                    Track tr = new Track(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim(),
                            Double.parseDouble(parts[3].trim()),
                            Integer.parseInt(parts[4].trim()),
                            parts[5].trim());
                    library.addTrackSilently(tr);
                }
            }
        }
    }

    private void loadCars(MotorsportLibrary library, File file) throws IOException {
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 14) {
                    String id = parts[0].trim();
                    String carClass = parts[1].trim();
                    String name = parts[2].trim();
                    String country = parts[3].trim();
                    String manufacturerId = parts[4].trim();
                    String sanctionedSeries = parts[5].trim();
                    String engineSpec = parts[6].trim();
                    int hp = Integer.parseInt(parts[7].trim());
                    int topSpeed = Integer.parseInt(parts[8].trim());
                    int weightKg = Integer.parseInt(parts[9].trim());

                    // Uses the Factory Method pattern to instantiate the right RaceCar subclass!
                    RaceCarFactory factory = RaceCarFactory.getFactoryForClass(carClass);
                    RaceCar car = factory.createCar(
                            id, name, country, manufacturerId, sanctionedSeries, engineSpec, hp, topSpeed, weightKg);

                    for (String acc : parts[10].split(";")) {
                        car.addAccolade(acc);
                    }
                    for (String tId : parts[11].split(";")) {
                        car.addTeamId(tId);
                    }
                    for (String dId : parts[12].split(";")) {
                        car.addDriverId(dId);
                    }
                    for (String trId : parts[13].split(";")) {
                        car.addTrackId(trId);
                    }
                    library.addRaceCarSilently(car);
                }
            }
        }
    }

    private void saveManufacturers(MotorsportLibrary library, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("# id,name,country,foundedYear,headquarters");
            for (Manufacturer m : library.getManufacturers()) {
                out.println(m.toCsv());
            }
        }
    }

    private void saveTeams(MotorsportLibrary library, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("# id,name,country,principal,manufacturerId,primarySeries");
            for (Team t : library.getTeams()) {
                out.println(t.toCsv());
            }
        }
    }

    private void saveDrivers(MotorsportLibrary library, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("# id,name,country,carNumber,teamId,championships");
            for (Driver d : library.getDrivers()) {
                out.println(d.toCsv());
            }
        }
    }

    private void saveTracks(MotorsportLibrary library, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("# id,name,country,lengthKm,turns,lapRecord");
            for (Track tr : library.getTracks()) {
                out.println(tr.toCsv());
            }
        }
    }

    private void saveCars(MotorsportLibrary library, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("# id,carClass,name,country,manufacturerId,sanctionedSeries,engineSpec,hp,topSpeed,weightKg,accolades,teamIds,driverIds,trackIds");
            for (RaceCar c : library.getRaceCars()) {
                out.println(c.toCsv());
            }
        }
    }
}
