package grid.decorator;

import grid.model.CarComponent;

/**
 * Concrete Decorator #2: Applies a Qualifying Engine / Hybrid Deployment Map to a CarComponent.
 * Boosts power by +65 HP and top speed by +8 km/h.
 */
public class TurboDecorator extends CarSetupDecorator {

    public TurboDecorator(CarComponent wrappedCar) {
        super(wrappedCar);
    }

    @Override
    public String getName() {
        return wrappedCar.getName() + " [+Qualifying Boost]";
    }

    @Override
    public int getHorsepower() {
        return wrappedCar.getHorsepower() + 65;
    }

    @Override
    public int getTopSpeedKph() {
        return wrappedCar.getTopSpeedKph() + 8;
    }
}
