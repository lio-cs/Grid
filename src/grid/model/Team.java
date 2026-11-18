package grid.model;

/**
 * Represents a racing team operating a manufacturer's race cars in sanctioned series
 * (e.g., Scuderia Ferrari, AF Corse, Porsche Penske Motorsport).
 */
public class Team extends CatalogItem {
    private String principal;
    private String manufacturerId;
    private String primarySeries;

    public Team(String id, String name, String country, String principal, String manufacturerId, String primarySeries) {
        super(id, name, country);
        setPrincipal(principal);
        setManufacturerId(manufacturerId);
        setPrimarySeries(primarySeries);
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        if (principal == null || principal.trim().isEmpty()) {
            throw new IllegalArgumentException("Team principal cannot be empty.");
        }
        this.principal = principal.trim();
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

    public String getPrimarySeries() {
        return primarySeries;
    }

    public void setPrimarySeries(String primarySeries) {
        if (primarySeries == null || primarySeries.trim().isEmpty()) {
            throw new IllegalArgumentException("Primary series cannot be empty.");
        }
        this.primarySeries = primarySeries.trim();
    }

    @Override
    public String getCategoryName() {
        return "Team";
    }

    @Override
    public String getDetails() {
        return String.format("Principal: %s | Series: %s", principal, primarySeries);
    }

    @Override
    public String toCsv() {
        return String.join(",",
                getId(),
                getName(),
                getCountry(),
                principal,
                manufacturerId,
                primarySeries);
    }
}
