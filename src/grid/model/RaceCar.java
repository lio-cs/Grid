package grid.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing an officially sanctioned racing car built by a Manufacturer.
 */
public abstract class RaceCar extends CatalogItem implements CarComponent {
    private String manufacturerId;
    private String sanctionedSeries;
    private String engineSpec;
    private int horsepower;
    private int topSpeedKph;
    private int weightKg;

    private final List<String> accolades = new ArrayList<>();
    private final List<String> teamIds = new ArrayList<>();
    private final List<String> driverIds = new ArrayList<>();
    private final List<String> trackIds = new ArrayList<>();

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
        this.manufacturerId = manufacturerId;
        this.sanctionedSeries = sanctionedSeries;
        this.engineSpec = engineSpec;
        this.horsepower = horsepower;
        this.topSpeedKph = topSpeedKph;
        this.weightKg = weightKg;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public String getSanctionedSeries() {
        return sanctionedSeries;
    }

    public void setSanctionedSeries(String sanctionedSeries) {
        this.sanctionedSeries = sanctionedSeries;
    }

    @Override
    public String getEngineSpec() {
        return engineSpec;
    }

    public void setEngineSpec(String engineSpec) {
        this.engineSpec = engineSpec;
    }

    @Override
    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        if (horsepower > 0) {
            this.horsepower = horsepower;
        }
    }

    @Override
    public int getTopSpeedKph() {
        return topSpeedKph;
    }

    public void setTopSpeedKph(int topSpeedKph) {
        this.topSpeedKph = topSpeedKph;
    }

    @Override
    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        this.weightKg = weightKg;
    }

    public void addAccolade(String accolade) {
        if (accolade != null && !accolade.trim().isEmpty()) {
            accolades.add(accolade.trim());
        }
    }

    public List<String> getAccolades() {
        return Collections.unmodifiableList(accolades);
    }

    public void addTeamId(String teamId) {
        if (teamId != null && !teamId.trim().isEmpty()) {
            teamIds.add(teamId.trim());
        }
    }

    public List<String> getTeamIds() {
        return Collections.unmodifiableList(teamIds);
    }

    public void addDriverId(String driverId) {
        if (driverId != null && !driverId.trim().isEmpty()) {
            driverIds.add(driverId.trim());
        }
    }

    public List<String> getDriverIds() {
        return Collections.unmodifiableList(driverIds);
    }

    public void addTrackId(String trackId) {
        if (trackId != null && !trackId.trim().isEmpty()) {
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
