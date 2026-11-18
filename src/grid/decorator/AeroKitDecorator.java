package grid.decorator;

import grid.model.CarComponent;

/**
 * Concrete Decorator #1: Applies a Low-Drag Aerodynamic Package to a CarComponent.
 * Boosts top speed by +15 km/h and reduces weight by 5 kg.
 */
public class AeroKitDecorator extends CarSetupDecorator {

    public AeroKitDecorator(CarComponent wrappedCar) {
        super(wrappedCar);
    }

    @Override
    public String getName() {
        return wrappedCar.getName() + " [+Low-Drag Aero]";
    }

    @Override
    public int getTopSpeedKph() {
        return wrappedCar.getTopSpeedKph() + 15;
    }

    @Override
    public int getWeightKg() {
        return Math.max(500, wrappedCar.getWeightKg() - 5);
    }
}
