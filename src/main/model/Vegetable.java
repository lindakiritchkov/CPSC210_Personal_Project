package model;

// Represents a vegetable with a type and the days between watering
public class Vegetable {

    private final String type;
    private final int daysBetweenWatering;
    private int daysSinceLastWatered;

    // Constructs a vegetable
    // REQUIRES: daysBetweenWatering >= 1
    // EFFECTS: creates a vegetable with a type and days between watering. Assumes the vegetable needs to be watered.
    public Vegetable(String type, int daysBetweenWatering) {
        this.type = type;
        this.daysBetweenWatering = daysBetweenWatering;
        this.daysSinceLastWatered = daysBetweenWatering;
    }

    // MODIFIES: this
    // EFFECTS: sets daysSinceLastWatered = 0
    public void water() {
        this.daysSinceLastWatered = 0;
    }

    // EFFECTS: returns the number of days until the vegetable needs to be watered
    public int whenToWaterNext() {
        return this.daysBetweenWatering - this.daysSinceLastWatered;
    }

    // EFFECTS: returns the days between watering for the vegetable
    public int getDaysBetweenWatering() {
        return this.daysBetweenWatering;
    }

    // EFFECTS: returns the vegetables type
    public String getType() {
        return this.type;
    }

    // EFFECTS: returns the days since the vegetable was watered last
    public int getDaysSinceLastWatered() {
        return this.daysSinceLastWatered;
    }

    // EFFECTS: set days since the vegetable was watered last
    public void setDaysSinceLastWatered(int days) {
        this.daysSinceLastWatered = days;
    }

    // REQUIRES: obj must be a vegetable
    // EFFECTS: returns true if the vegetable is the same as the object passed as a parameter
    @Override
    public boolean equals(Object obj) {
        Vegetable comparedVeg = (Vegetable) obj; // casts obj as a vegetable
        return this.getType().equals(comparedVeg.getType())
                && this.getDaysBetweenWatering() == comparedVeg.getDaysBetweenWatering();
    }
}
