package grid.model;

/**
 * Concrete RaceCar subclass representing an open-wheel Formula car (e.g., Ferrari SF-24, McLaren MCL38).
 */
public class FormulaCar extends RaceCar {

    public FormulaCar(
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
        return "FORMULA";
    }

    @Override
    public String getRegulationNotes() {
        return "Open-wheel single-seater with ground-effect venturi tunnels & DRS active aerodynamics.";
    }

    @Override
    public String getAsciiSilhouette() {
        return String.join("\n",
                "            _.-=\"\"=-._                                      ",
                "          .'  _    _  '.     /|____                         ",
                "   _..---/   (_)  (_)   \\---' | DRS|___                     ",
                " /'_____(================)____|____|___\\___/\\__             ",
                " \\_______/  (O)      (O)  \\____________/  (O)  `--.___      ",
                "            `-'      `-'                  `-'               ");
    }
}
