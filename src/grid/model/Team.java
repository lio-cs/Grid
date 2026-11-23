package grid.model;

/**
 * Represents a racing team operating a manufacturer's race cars in sanctioned series.
 */
public class Team extends CatalogItem {
    private String principal;
    private String manufacturerId;
    private String primarySeries;

    public Team(String id, String name, String country, String principal, String manufacturerId, String primarySeries) {
        super(id, name, country);
        this.principal = principal;
        this.manufacturerId = manufacturerId;
        this.primarySeries = primarySeries;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getPrimarySeries() {
        return primarySeries;
    }

    public void setPrimarySeries(String primarySeries) {
        this.primarySeries = primarySeries;
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
        return String.join(",", getId(), getName(), getCountry(), principal, manufacturerId, primarySeries);
    }
}
