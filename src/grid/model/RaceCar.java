package grid.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing an officially sanctioned racing car built by a Manufacturer.
 *
 * Links together:
 * - The Manufacturer that built it (manufacturerId)
 * - The Officially Sanctioned Series it competes in (sanctionedSeries)
 * - Full technical specs (engineSpec, horsepower, topSpeedKph, weightKg)
 * - Accolades won by the car (accolades)
 * - Teams operating the car (teamIds)
 * - Drivers who have piloted the car (driverIds)
 * - Tracks the car has run on (trackIds)
 */
public abstract class RaceCar extends CatalogItem implements CarComponent {
    private String manufacturerId;
    private String sanctionedSeries;
    private String engineSpec;
    private int horsepower;
    private int topSpeedKph;
    private int weightKg;

    private final List<String> accolades;
    private final List<String> teamIds;
    private final List<String> driverIds;
    private final List<String> trackIds;

    public RaceCar(
            String id,
            String name,
            String country,
            String manufacturerId,
            String sanctionedSeries,
            String engineSpec,
            int horsepower,
            int topSpeedKph,
            int weightKg) {
        super(id, name, country);
        setManufacturerId(manufacturerId);
        setSanctionedSeries(sanctionedSeries);
        setEngineSpec(engineSpec);
        setHorsepower(horsepower);
        setTopSpeedKph(topSpeedKph);
        setWeightKg(weightKg);

        this.accolades = new ArrayList<>();
        this.teamIds = new ArrayList<>();
        this.driverIds = new ArrayList<>();
        this.trackIds = new ArrayList<>();
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        if (manufacturerId == null || manufacturerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer ID cannot be empty.");
        }
        this.manufacturerId = manufacturerId.trim();
    }

    @Override
    public String getSanctionedSeries() {
        return sanctionedSeries;
    }

    public void setSanctionedSeries(String sanctionedSeries) {
        if (sanctionedSeries == null || sanctionedSeries.trim().isEmpty()) {
            throw new IllegalArgumentException("Sanctioned series cannot be empty.");
        }
        this.sanctionedSeries = sanctionedSeries.trim();
    }

    @Override
    public String getEngineSpec() {
        return engineSpec;
    }

    public void setEngineSpec(String engineSpec) {
        if (engineSpec == null || engineSpec.trim().isEmpty()) {
            throw new IllegalArgumentException("Engine specification cannot be empty.");
        }
        this.engineSpec = engineSpec.trim();
    }

    @Override
    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        if (horsepower <= 0) {
            throw new IllegalArgumentException("Horsepower must be positive.");
        }
        this.horsepower = horsepower;
    }

    @Override
    public int getTopSpeedKph() {
        return topSpeedKph;
    }

    public void setTopSpeedKph(int topSpeedKph) {
        if (topSpeedKph <= 0) {
            throw new IllegalArgumentException("Top speed must be positive.");
        }
        this.topSpeedKph = topSpeedKph;
    }

    @Override
    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
        this.weightKg = weightKg;
    }

    public void addAccolade(String accolade) {
        if (accolade != null && !accolade.trim().isEmpty() && !accolades.contains(accolade.trim())) {
            accolades.add(accolade.trim());
        }
    }

    public List<String> getAccolades() {
        return Collections.unmodifiableList(accolades);
    }

    public void addTeamId(String teamId) {
        if (teamId != null && !teamId.trim().isEmpty() && !teamIds.contains(teamId.trim())) {
            teamIds.add(teamId.trim());
        }
    }

    public List<String> getTeamIds() {
        return Collections.unmodifiableList(teamIds);
    }

    public void addDriverId(String driverId) {
        if (driverId != null && !driverId.trim().isEmpty() && !driverIds.contains(driverId.trim())) {
            driverIds.add(driverId.trim());
        }
    }

    public List<String> getDriverIds() {
        return Collections.unmodifiableList(driverIds);
    }

    public void addTrackId(String trackId) {
        if (trackId != null && !trackId.trim().isEmpty() && !trackIds.contains(trackId.trim())) {
            trackIds.add(trackId.trim());
        }
    }

    public List<String> getTrackIds() {
        return Collections.unmodifiableList(trackIds);
    }

    @Override
    public String getCategoryName() {
        return "RaceCar (" + getCarClass() + ")";
    }

    @Override
    public String getSpecSummary() {
        return String.format(
                "Class: %s | Series: %s | Engine: %s | Power: %d HP | Top Speed: %d km/h | Weight: %d kg",
                getCarClass(), sanctionedSeries, engineSpec, horsepower, topSpeedKph, weightKg);
    }

    @Override
    public String getDetails() {
        return getSpecSummary();
    }

    /**
     * Serializes the RaceCar and its semicolon-separated lists (accolades, teams, drivers, tracks) to CSV.
     */
    @Override
    public String toCsv() {
        return String.join(",",
                getId(),
                getCarClass(),
                getName(),
                getCountry(),
                manufacturerId,
                sanctionedSeries,
                engineSpec,
                String.valueOf(horsepower),
                String.valueOf(topSpeedKph),
                String.valueOf(weightKg),
                String.join(";", accolades),
                String.join(";", teamIds),
                String.join(";", driverIds),
                String.join(";", trackIds));
    }
}
