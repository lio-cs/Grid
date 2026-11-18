package grid.factory;

import grid.model.PrototypeCar;
import grid.model.RaceCar;

/**
 * Concrete Factory that instantiates PrototypeCar objects.
 */
public class PrototypeCarFactory extends RaceCarFactory {

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
        return new PrototypeCar(
                id, name, country, manufacturerId, sanctionedSeries, engineSpec, horsepower, topSpeedKph, weightKg);
    }
}
