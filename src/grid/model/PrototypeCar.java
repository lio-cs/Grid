package grid.model;

/**
 * Concrete RaceCar subclass representing a Le Mans Hypercar (LMH) or LMDh Sports Prototype
 * (e.g., Ferrari 499P, Porsche 963).
 */
public class PrototypeCar extends RaceCar {

    public PrototypeCar(
            String id,
            String name,
            String country,
            String manufacturerId,
            String sanctionedSeries,
            String engineSpec,
            int horsepower,
            int topSpeedKph,
            int weightKg) {
        super(id, name, country, manufacturerId, sanctionedSeries, engineSpec, horsepower, topSpeedKph, weightKg);
    }

    @Override
    public String getCarClass() {
        return "PROTOTYPE";
    }

    @Override
    public String getRegulationNotes() {
        return "Closed-cockpit endurance prototype built for multi-class 6h/12h/24h sports car racing.";
    }

    @Override
    public String getAsciiSilhouette() {
        return String.join("\n",
                "        /|_______________     _.-=\"\"\"\"=-._                  ",
                "       / |   LMH / LMDh  \\___/   _.._     '.                ",
                "  ____/__|______________________/____\\______\\____..--._     ",
                " |  _   _   ___   HYPERCAR   ___   _   _   ____       `\\   ",
                " '-/ \\ (_) /   \\____________/   \\ (_) / \\-'    \\___/\\___\\  ",
                "   \\_/     \\___/            \\___/     \\_/          \\_/      ");
    }
}
