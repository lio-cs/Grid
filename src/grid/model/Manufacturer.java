package grid.model;

/**
 * Represents an automotive/motorsport manufacturer (e.g., Ferrari, Porsche, Mercedes-AMG, McLaren).
 * Inherits id, name, and country from CatalogItem.
 */
public class Manufacturer extends CatalogItem {
    private int foundedYear;
    private String headquarters;

    public Manufacturer(String id, String name, String country, int foundedYear, String headquarters) {
        super(id, name, country);
        setFoundedYear(foundedYear);
        setHeadquarters(headquarters);
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(int foundedYear) {
        if (foundedYear < 1800 || foundedYear > 2100) {
            throw new IllegalArgumentException("Founded year must be between 1800 and 2100.");
        }
        this.foundedYear = foundedYear;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        if (headquarters == null || headquarters.trim().isEmpty()) {
            throw new IllegalArgumentException("Headquarters cannot be empty.");
        }
        this.headquarters = headquarters.trim();
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
        return String.join(",",
                getId(),
                getName(),
                getCountry(),
                String.valueOf(foundedYear),
                headquarters);
    }
}
