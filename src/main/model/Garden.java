package model;

// Represents a single-row garden of given size in which vegetables can be planted.
// The first (left-most) spot is designated as index 1.
// The garden is considered "empty" when it has "placeholders" in every position.

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Garden {

    ArrayList<Vegetable> gardenList;
    int sizeOfGarden;
    Vegetable placeHolder;

    // Constructs a garden
    // EFFECTS: creates a garden with given size, and places "placeholder" in each spot
    public Garden(int size) {
        gardenList = new ArrayList<>();
        sizeOfGarden = size;
        placeHolder = new Vegetable(" ", 1);

        // this fills the garden with "placeholders" in each spot
        for (int i = size; i > 0; i--) {
            gardenList.add(placeHolder);
        }
    }

    // constructs a garden
    // EFFECTS: creates a garden from a JSONArray
    public Garden(JSONArray list) {
        gardenList = new ArrayList<>(list.length());
        sizeOfGarden = list.length();
        placeHolder = new Vegetable(" ", 1);

        for (Object obj : list) {
            JSONObject nextObj = (JSONObject) obj;
            addVegetableIntoGarden(gardenList, nextObj);
        }
    }

    // MODIFIES: gardenList
    // EFFECTS: parses vegetables from JSON object and adds them to the garden
    public void addVegetableIntoGarden(ArrayList<Vegetable> gardenList, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        int daysBetweenWatering = jsonObject.getInt("daysBetweenWatering");
        int daysSinceLastWatered = jsonObject.getInt("daysSinceLastWatered");
        Vegetable vegetable = new Vegetable(type, daysBetweenWatering);
        vegetable.setDaysSinceLastWatered(daysSinceLastWatered);
        gardenList.add(vegetable);
    }

    // MODIFIES: this
    // REQUIRES: given Vegetable must have already been created
    // EFFECTS: adds the given Vegetable into the garden at given position.
    //          If another vegetable is in the given position, replace that vegetable with the new vegetable.
    //          If given position is larger than garden size, do not add vegetable.
    public void addVegetable(Vegetable veg, int pos) {
        if (pos <= sizeOfGarden) {
            gardenList.set(pos - 1, veg);
            EventLog.getInstance().logEvent(new Event(veg.getType() + " added to garden in position " + pos + "."));
        }
    }

    // MODIFIES: this
    // REQUIRES: given Vegetable must have already been created, and planted in the garden
    // EFFECTS: removes the first instance of the given Vegetable from the garden,
    //          or else does nothing if there are no instances of the given Vegetable in the garden
    public void removeVegetable(Vegetable veg) {
        if (gardenList.contains(veg)) {
            int position = gardenList.indexOf(veg);
            gardenList.set(position,placeHolder);
        }
    }

    // MODIFIES: this
    // EFFECTS: if there is a vegetable at given position, remove it and replace is with a "placeholder"
    public void removeVegetableAtPosition(int pos) {
        gardenList.set(pos, placeHolder);
        EventLog.getInstance().logEvent(new Event("Vegetable at position " + pos + " removed from garden."));
    }

    // EFFECTS: returns a list of vegetables in the garden. If garden is empty, return empty list
    public ArrayList<Vegetable> getVegetables() {
        ArrayList<Vegetable> listOfVegetables = new ArrayList<>();
        for (Vegetable veg: gardenList) {
            if (!(veg.equals(placeHolder))) {
                listOfVegetables.add(veg);
            }
        }
        return listOfVegetables;
    }

    // EFFECTS: returns a list of vegetable types in the garden.
    //          If garden is empty (filled with placeholders), return empty list.
    public ArrayList<String> listVegetableTypes() {
        ArrayList<String> listOfVegTypes = new ArrayList<>();
        for (Vegetable veg: gardenList) {
            if (!(veg.equals(placeHolder))) {
                listOfVegTypes.add(veg.getType());
            }
        }
        return listOfVegTypes;
    }

    // EFFECTS:  returns true if the garden is empty (filled with placeholders), otherwise return false
    public boolean isEmpty() {
       // boolean stillEmpty = true;
        for (Vegetable item : gardenList) {
            if (!(item.equals(placeHolder))) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: return true if the garden contains the given vegetable
    public  boolean contains(Vegetable veg) {
        return gardenList.contains(veg);
    }

    // EFFECTS: returns the size of the garden
    public int getSizeOfGarden() {
        return gardenList.size();
    }

    // EFFECTS: return the garden list (all vegetables, including placeholders)
    public ArrayList<Vegetable> getGardenList() {
        return gardenList;
    }

    // EFFECTS: returns garden as a JSON array
    public JSONArray gardenToJson() {
        return new JSONArray(gardenList);
    }
}
