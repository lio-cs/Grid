package grid.factory;

import grid.model.GTCar;
import grid.model.RaceCar;

/**
 * Concrete Factory that instantiates GTCar objects.
 */
public class GTCarFactory extends RaceCarFactory {

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
        return new GTCar(
                id, name, country, manufacturerId, sanctionedSeries, engineSpec, horsepower, topSpeedKph, weightKg);
    }
}
