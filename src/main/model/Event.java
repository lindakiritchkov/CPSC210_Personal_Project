package model;

// The following code is adapted from: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/commit/
//                                     047c12f321ec713fae1f1a5dfdb01688ea1df596

import java.util.Calendar;
import java.util.Date;

 // Represents a gardening event.
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    // EFFECTS: Creates an event with the given description and the current date/time stamp
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: returns the date of this event, including time
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: returns the description of this event
    public String getDescription() {
        return description;
    }

    // EFFECTS: determines if the given object is the same as this event
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: returns the hashcode for this event
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: returns the date/time of with event with it's description as a string
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
