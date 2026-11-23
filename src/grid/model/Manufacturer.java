package grid.model;

/**
 * Represents a motorsport manufacturer (e.g., Ferrari, Porsche, Mercedes-AMG, McLaren).
 */
public class Manufacturer extends CatalogItem {
    private int foundedYear;
    private String headquarters;

    public Manufacturer(String id, String name, String country, int foundedYear, String headquarters) {
        super(id, name, country);
        this.foundedYear = foundedYear;
        this.headquarters = headquarters;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(int foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    @Override
    public String getCategoryName() {
        return "Manufacturer";
    }

    @Override
    public String getDetails() {
        return String.format("HQ: %s, %s | Founded: %d", headquarters, getCountry(), foundedYear);
    }

    @Override
    public String toCsv() {
        return String.join(",", getId(), getName(), getCountry(), String.valueOf(foundedYear), headquarters);
    }
}
