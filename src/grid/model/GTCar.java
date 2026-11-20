package grid.model;

/**
 * Concrete RaceCar subclass representing a homologated Grand Touring (GT3 / GTE) race car
 * (e.g., Ferrari 296 GT3, Porsche 911 GT3 R, Mercedes-AMG GT3 Evo).
 */
public class GTCar extends RaceCar {

    public GTCar(
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
        return "GT";
    }

    @Override
    public String getRegulationNotes() {
        return "Production-derived Grand Touring chassis governed by FIA Balance of Performance (BoP).";
    }

    @Override
    public String getAsciiSilhouette() {
        return String.join("\n",
                "       _                  ________________                  ",
                "      | \\___         ____/   ___    ___   \\____             ",
                "   ___|_____\\_______/       |   |  |   |       \\_______     ",
                "  |   _     _     FIA GT3   |___|  |___|    _     _    `\\   ",
                "  '--/ \\---(_)-----------------------------/ \\---(_)----'   ",
                "     \\_/                                   \\_/              ");
    }
}
