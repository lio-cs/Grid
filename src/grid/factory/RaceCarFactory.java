package grid.factory;

import grid.model.RaceCar;

/**
 * Abstract Creator class for the Factory Method Design Pattern (Guide/tasks.pdf Exercise 4).
 *
 * Why use Factory Method here?
 * - Allows the library loader and user menu to create RaceCar objects through a common
 *   factory interface without tightly coupling to concrete classes (FormulaCar, PrototypeCar, GTCar).
 * - Adding a new race car category later (e.g., RallyCar + RallyCarFactory) requires zero changes
 *   to existing factory classes.
 */
public abstract class RaceCarFactory {

    /**
     * Factory Method to be implemented by concrete factory subclasses.
     */
    public abstract RaceCar createCar(
            String id,
            String name,
            String country,
            String manufacturerId,
            String sanctionedSeries,
            String engineSpec,
            int horsepower,
            int topSpeedKph,
            int weightKg);

    /**
     * Helper to select the appropriate concrete factory at runtime based on car class name.
     */
    public static RaceCarFactory getFactoryForClass(String carClass) {
        if (carClass == null) {
            throw new IllegalArgumentException("Car class cannot be null.");
        }
        switch (carClass.trim().toUpperCase()) {
            case "FORMULA":
                return new FormulaCarFactory();
            case "PROTOTYPE":
                return new PrototypeCarFactory();
            case "GT":
                return new GTCarFactory();
            default:
                throw new IllegalArgumentException("Unknown car class: " + carClass);
        }
    }
}
