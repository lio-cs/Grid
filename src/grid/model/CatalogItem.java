package grid.model;

/**
 * Abstract base class representing any item in our Motorsport Library.
 * Demonstrates Abstraction, Encapsulation, Inheritance, and Polymorphism.
 */
public abstract class CatalogItem {
    private final String id;
    private String name;
    private String country;

    public CatalogItem(String id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public abstract String getCategoryName();

    public abstract String getDetails();

    public abstract String toCsv();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s", id, name, country, getDetails());
    }
}
