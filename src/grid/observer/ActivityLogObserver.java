package grid.observer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete Observer #2: Stores a chronological log of all library and garage events
 * during the session so the user can review them in the Notification Center.
 */
public class ActivityLogObserver implements LibraryObserver {
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final List<String> entries = new ArrayList<>();

    @Override
    public void update(String eventType, String message) {
        String timestamp = LocalTime.now().format(TIME_FMT);
        entries.add(String.format("[%s] (%s) %s", timestamp, eventType, message));
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(entries);
    }
}
