package grid.model;

/**
 * Represents a motorsport racing circuit/track (e.g., Spa-Francorchamps, Monza, Le Mans).
 */
public class Track extends CatalogItem {
    private double lengthKm;
    private int turns;
    private String lapRecord;

    public Track(String id, String name, String country, double lengthKm, int turns, String lapRecord) {
        super(id, name, country);
        this.lengthKm = lengthKm;
        this.turns = turns;
        this.lapRecord = lapRecord;
    }

    public double getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(double lengthKm) {
        this.lengthKm = lengthKm;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public String getLapRecord() {
        return lapRecord;
    }

    public void setLapRecord(String lapRecord) {
        this.lapRecord = lapRecord;
    }

    @Override
    public String getCategoryName() {
        return "Track";
    }

    @Override
    public String getDetails() {
        return String.format("Length: %.3f km | Turns: %d | Lap Record: %s", lengthKm, turns, lapRecord);
    }

    @Override
    public String toCsv() {
        return String.join(",",
                getId(),
                getName(),
                getCountry(),
                String.valueOf(lengthKm),
                String.valueOf(turns),
                lapRecord);
    }
}
