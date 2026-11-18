package grid.model;

/**
 * Represents a racing driver who pilots sanctioned race cars
 * (e.g., Charles Leclerc, Alessandro Pier Guidi, Kevin Estre).
 */
public class Driver extends CatalogItem {
    private int carNumber;
    private String teamId;
    private int championships;

    public Driver(String id, String name, String country, int carNumber, String teamId, int championships) {
        super(id, name, country);
        setCarNumber(carNumber);
        setTeamId(teamId);
        setChampionships(championships);
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        if (carNumber < 0 || carNumber > 999) {
            throw new IllegalArgumentException("Car number must be between 0 and 999.");
        }
        this.carNumber = carNumber;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        if (teamId == null || teamId.trim().isEmpty()) {
            throw new IllegalArgumentException("Team ID cannot be empty.");
        }
        this.teamId = teamId.trim();
    }

    public int getChampionships() {
        return championships;
    }

    public void setChampionships(int championships) {
        if (championships < 0) {
            throw new IllegalArgumentException("Championships cannot be negative.");
        }
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
