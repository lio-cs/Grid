package grid.decorator;

import grid.model.CarComponent;

/**
 * Abstract Decorator class for the Decorator Design Pattern (Guide/tasks.pdf Exercise 3).
 *
 * Implements CarComponent and wraps another CarComponent object so concrete decorators
 * can dynamically modify horsepower, top speed, weight, and spec summary in any combination.
 */
public abstract class CarSetupDecorator implements CarComponent {
    protected final CarComponent wrappedCar;

    public CarSetupDecorator(CarComponent wrappedCar) {
        if (wrappedCar == null) {
            throw new IllegalArgumentException("Wrapped CarComponent cannot be null.");
        }
        this.wrappedCar = wrappedCar;
    }

    @Override
    public String getId() {
        return wrappedCar.getId();
    }

    @Override
    public String getName() {
        return wrappedCar.getName();
    }

    @Override
    public String getSanctionedSeries() {
        return wrappedCar.getSanctionedSeries();
    }

    @Override
    public String getCarClass() {
        return wrappedCar.getCarClass();
    }

    @Override
    public String getEngineSpec() {
        return wrappedCar.getEngineSpec();
    }

    @Override
    public int getHorsepower() {
        return wrappedCar.getHorsepower();
    }

    @Override
    public int getTopSpeedKph() {
        return wrappedCar.getTopSpeedKph();
    }

    @Override
    public int getWeightKg() {
        return wrappedCar.getWeightKg();
    }

    @Override
    public String getRegulationNotes() {
        return wrappedCar.getRegulationNotes();
    }

    @Override
    public String getSpecSummary() {
        return String.format(
                "%s | Series: %s | Engine: %s | Power: %d HP | Top Speed: %d km/h | Weight: %d kg",
                getName(),
                getSanctionedSeries(),
                getEngineSpec(),
                getHorsepower(),
                getTopSpeedKph(),
                getWeightKg());
    }
}
