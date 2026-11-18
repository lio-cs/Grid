package grid.model;

/**
 * Abstract base class representing any item in our Motorsport Library.
 *
 * OOP Concepts demonstrated:
 * 1. Abstraction: Cannot be instantiated directly (`new CatalogItem(...)` is not allowed).
 *    Defines a contract (`getDetails()`, `getCategoryName()`, `toCsv()`) that all subclasses must implement.
 * 2. Encapsulation: Fields (`id`, `name`, `country`) are private and accessed only via getters/setters
 *    with validation.
 * 3. Inheritance: Shared fields and logic are written once here and inherited by
 *    Manufacturer, Team, Driver, Track, and RaceCar.
 */
public abstract class CatalogItem {
    private final String id;
    private String name;
    private String country;

    public CatalogItem(String id, String name, String country) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty.");
        }
        this.id = id.trim();
        setName(name);
        setCountry(country);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty.");
        }
        this.country = country.trim();
    }

    /**
     * Returns the type of catalog item (e.g., "Manufacturer", "Team", "Driver", "Track", "RaceCar").
     */
    public abstract String getCategoryName();

    /**
     * Polymorphic method: each subclass formats its own specific details for display.
     */
    public abstract String getDetails();

    /**
     * Serializes the object into a single CSV line for saving to disk.
     */
    public abstract String toCsv();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s", id, name, country, getDetails());
    }
}
