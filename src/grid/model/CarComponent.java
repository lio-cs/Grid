package grid.model;

/**
 * Interface representing the technical and performance specification of a race car.
 *
 * Design Pattern Role:
 * - Component interface for the Decorator pattern (Block 3), allowing us to wrap any
 *   RaceCar in tuning/setup decorators and dynamically adjust its specs.
 */
public interface CarComponent {
    String getId();
    String getName();
    String getSanctionedSeries();
    String getCarClass();
    String getEngineSpec();
    int getHorsepower();
    int getTopSpeedKph();
    int getWeightKg();
    String getRegulationNotes();
    String getSpecSummary();
}
