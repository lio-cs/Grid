package grid.model;

/**
 * Represents a racing driver who pilots sanctioned race cars.
 */
public class Driver extends CatalogItem {
    private int carNumber;
    private String teamId;
    private int championships;

    public Driver(String id, String name, String country, int carNumber, String teamId, int championships) {
        super(id, name, country);
        this.carNumber = carNumber;
        this.teamId = teamId;
        this.championships = championships;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getChampionships() {
        return championships;
    }

    public void setChampionships(int championships) {
        this.championships = championships;
    }

    @Override
    public String getCategoryName() {
        return "Driver";
    }

    @Override
    public String getDetails() {
        return String.format("#%d | Nationality: %s | World/Major Titles: %d", carNumber, getCountry(), championships);
    }

    @Override
    public String toCsv() {
        return String.join(",",
                getId(),
                getName(),
                getCountry(),
                String.valueOf(carNumber),
                teamId,
                String.valueOf(championships));
    }
}
