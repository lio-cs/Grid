package grid.observer;

/**
 * Observer interface for the Observer Design Pattern (Guide/tasks.pdf Exercise 2).
 *
 * Any class implementing LibraryObserver can subscribe to MotorsportLibrary
 * to receive automatic notifications when cars, accolades, or garage setups change.
 */
public interface LibraryObserver {
    void update(String eventType, String message);
}
