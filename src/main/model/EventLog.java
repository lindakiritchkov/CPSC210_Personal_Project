package model;

// The following code is adapted from: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/commit/
//                                     047c12f321ec713fae1f1a5dfdb01688ea1df596

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of gardening events
public class EventLog implements Iterable<Event> {
    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: constructs the event log, using a private constructor to prevent external construction
    //          (Singleton Design Pattern)
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: get this instance of the EventLog, or create it if it doesn't exist yet.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: iterates over the events in the EventLog
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
