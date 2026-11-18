package grid.observer;

import grid.ui.ConsoleTheme;

/**
 * Concrete Observer #1: Prints an immediate in-console alert banner when an event occurs.
 * Users can subscribe/unsubscribe this observer at runtime to see the Observer pattern in action.
 */
public class ConsoleAlertObserver implements LibraryObserver {
    private final String subscriberName;

    public ConsoleAlertObserver(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    @Override
    public void update(String eventType, String message) {
        System.out.printf("  %s>>> [LIVE ALERT for %s | %s] %s%s%n",
                ConsoleTheme.GREEN + ConsoleTheme.BOLD,
                subscriberName,
                eventType,
                message,
                ConsoleTheme.RESET);
    }
}
