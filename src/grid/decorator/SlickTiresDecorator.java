package grid.decorator;

import grid.model.CarComponent;

/**
 * Concrete Decorator #3: Applies Soft-Compound Racing Slicks & Lightweight Forged Wheels.
 * Boosts effective speed by +10 km/h and reduces unsprung weight by 8 kg.
 */
public class SlickTiresDecorator extends CarSetupDecorator {

    public SlickTiresDecorator(CarComponent wrappedCar) {
        super(wrappedCar);
    }

    @Override
    public String getName() {
        return wrappedCar.getName() + " [+Soft Slicks]";
    }

    @Override
    public int getTopSpeedKph() {
        return wrappedCar.getTopSpeedKph() + 10;
    }

    @Override
    public int getWeightKg() {
        return Math.max(500, wrappedCar.getWeightKg() - 8);
    }
}
