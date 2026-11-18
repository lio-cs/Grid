package grid.factory;

import grid.model.FormulaCar;
import grid.model.RaceCar;

/**
 * Concrete Factory that instantiates FormulaCar objects.
 */
public class FormulaCarFactory extends RaceCarFactory {

    @Override
    public RaceCar createCar(
            String id,
            String name,
            String country,
            String manufacturerId,
            String sanctionedSeries,
            String engineSpec,
            int horsepower,
            int topSpeedKph,
            int weightKg) {
        return new FormulaCar(
                id, name, country, manufacturerId, sanctionedSeries, engineSpec, horsepower, topSpeedKph, weightKg);
    }
}
