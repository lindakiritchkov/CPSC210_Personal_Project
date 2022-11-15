package ui;

import model.Garden;
import model.Vegetable;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// Represents a console based application for Garden Buddy
public class GardenBuddyConsole {
    private static final String JSON_STORE = "./data/garden.json";
    private Garden garden;
    private Scanner input;
    private ArrayList<Vegetable> listOfVegetablesInGarden;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // INVARIANT: listOfCreatedVegetables and listOfCreatedVegetableTypes in correspondence. Lists are the same size.
    //            The 1st item in listOfCreatedVegetableTypes is the vegetable type of the first vegetable in
    //            listOfCreatedVegetables,
    //            The 2nd item in listOfCreatedVegetableTypes is the vegetable type of the second vegetable in
    //            listOfCreatedVegetables, and so on.
    private ArrayList<Vegetable> listOfCreatedVegetables;
    private ArrayList<String> listOfCreatedVegetableTypes;

    // EFFECTS: runs the garden application
    public GardenBuddyConsole() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runGardenApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGardenApp() {
        boolean continueGoing = true;
        String command;

        initializeGarden();

        while (continueGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("8")) {       // to exit the program
                continueGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThanks for Gardening!");
    }

    // EFFECTS: determines what size of garden the user wants
    private int initializeSize() {
        int size;
        System.out.println("\nWhat size would you like the garden to be?");
        size = input.nextInt();

        if (size > 0) {
            return size;
        } else {
            System.out.println("\nInvalid number. Try again");
            initializeSize();
        }
        return size;
    }

    // MODIFIES: this
    // EFFECTS: initialize the garden
    private void initializeGarden() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        String selection = ""; // forces entry into loop

        while (!(selection.equals("l") || selection.equals("n"))) {
            System.out.println("\nWould you like to load a garden from a saved file or start a new garden?");
            System.out.println("l -> load from saved file");
            System.out.println("n -> create new garden");
            selection = input.next();
        }
        if (selection.equals("l")) {
            loadGarden();
            listOfVegetablesInGarden = garden.getGardenList();
        } else {
            int size = initializeSize();
            garden = new Garden(size);
            listOfCreatedVegetables = new ArrayList<>();
            listOfCreatedVegetableTypes = new ArrayList<>();
            listOfVegetablesInGarden = new ArrayList<>();
            listOfVegetablesInGarden = garden.getGardenList();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            createNewVegetable();
        } else if (command.equals("2")) {
            plantVegetable();
        } else if (command.equals("3")) {
            removeVegetable();
        } else if (command.equals("4")) {
            printVegetables();
        } else if (command.equals("5")) {
            waterVegetable();
        } else if (command.equals("6")) {
            whenToWaterVegetable();
        } else if (command.equals("7")) {
            saveGarden();
        } else {
            System.out.println("Selection not valid. Please try again");
        }
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelect from the following:");
        System.out.println("1 -> create new vegetable");
        System.out.println("2 -> plant a vegetable in your garden");
        System.out.println("3 -> remove a vegetable from your garden");
        System.out.println("4 -> list all vegetables in the garden");
        System.out.println("5 -> select a vegetable and water it");
        System.out.println("6 -> select a vegetable and find out when it needs to be watered next");
        System.out.println("7 -> save garden to file");
        System.out.println("8 -> quit");
    }

    // MODIFIES: this
    // EFFECT: creates a new vegetable
    private void createNewVegetable() {
        System.out.println("\nEnter vegetable type:");
        String type = input.next().toLowerCase();
        System.out.println("\nHow often in days does it need to be watered?");
        int days = input.nextInt();
        Vegetable newVegetable = new Vegetable(type, days);
        listOfCreatedVegetables.add(newVegetable);
        String vegetableType = newVegetable.getType().toLowerCase();
        listOfCreatedVegetableTypes.add(vegetableType);
        System.out.println("\nVegetable Created!");
    }

    // MODIFIES: this
    // EFFECT: plants vegetable in garden at given position
    private void plantVegetable() {
        Vegetable selected = selectFromCreatedVegetables();
        System.out.println("\nWhat spot will you plant it in?");
        int pos = input.nextInt();
        int size = garden.getSizeOfGarden();

        if (pos <= size) {
            garden.addVegetable(selected, pos);
            System.out.println("\nPlanted!");
        } else {
            System.out.println("\nYour garden is too small!");
        }
    }

    // MODIFIES: this
    // REQUIRES: there is at least one vegetable planted in the garden
    // EFFECT: removes a vegetable from the garden
    private void removeVegetable() {
        String selection = ""; // forces entry into loop

        while (!(selection.equals("1") || selection.equals("2"))) {
            System.out.println("\nWhich of the following would you like to do?");
            System.out.println("1 -> remove first instance of vegetable in garden?");
            System.out.println("2 -> remove a vegetable at a specific position?");
            selection = input.next();
        }
        if (selection.equals("1")) {
            Vegetable selectedVeg = selectVegetableInGarden();
            garden.removeVegetable(selectedVeg);
        } else {
            System.out.println("\nFrom which spot would you like to remove a vegetable?");
            int pos = input.nextInt();
            garden.removeVegetableAtPosition(pos);
        }
        System.out.println("\nVegetable removed");
    }

    // EFFECT: Print a list of all vegetables in the garden
    private void printVegetables() {
        System.out.println("\nThese are the vegetables in your garden:");
        for (String vegetable : garden.listVegetableTypes()) {
            System.out.print(vegetable);
            System.out.print(", ");
        }
        System.out.println(" ");
    }

    // MODIFIES: this
    // REQUIRES: there is at least one vegetable planted in the garden to chose from
    // EFFECTS: waters the selected vegetable
    private void waterVegetable() {
        Vegetable selected = selectVegetableInGarden();
        System.out.println("\nWould you like to water this vegetable? y or n");
        String answer = input.next();
        if (answer.equals("y")) {
            selected.water();
        } else {
            displayMainMenu();
        }
    }

    // REQUIRES: there is at least one vegetable planted in the garden to chose from
    // EFFECTS: shows how many days until vegetable should be watered next
    private void whenToWaterVegetable() {
        Vegetable selected = selectVegetableInGarden();
        int days = selected.whenToWaterNext();
        if (days == 0) {
            System.out.println("You should water " + selected.getType() + " today");
        } else {
            System.out.println("You should water " + selected.getType() + " in " + days + " days");
        }
    }

    // EFFECTS: allows the user to select a vegetable from the list of already created vegetables
    private Vegetable selectFromCreatedVegetables() {
        String selection = ""; // force entry into loop

        while (!(listOfCreatedVegetableTypes.contains(selection))) {
            System.out.println("\nSelect from the following vegetables:");
            for (Vegetable vegetable : listOfCreatedVegetables) {
                System.out.print(vegetable.getType());
                System.out.print(", ");
            }
            System.out.println(" ");
            selection = input.next().toLowerCase();
        }
        int index = listOfCreatedVegetableTypes.indexOf(selection);
        return listOfCreatedVegetables.get(index);
    }

    // REQUIRES: selected vegetable must match a vegetable from the printed list
    // EFFECTS: allows the user to select a vegetable from the list of vegetables planted in the garden
    private Vegetable selectVegetableInGarden() {
        String selection = ""; // force entry into loop
        Vegetable selectedVegetable = null;

        while (!(garden.listVegetableTypes().contains(selection))) {
            System.out.println("\nSelect from the following vegetables:");
            for (Vegetable vegetable : garden.getVegetables()) {
                System.out.print(vegetable.getType());
                System.out.print(", ");
            }
            System.out.println(" ");
            selection = input.next().toLowerCase();
            int vegIndex = listOfCreatedVegetableTypes.indexOf(selection);
            selectedVegetable = listOfCreatedVegetables.get(vegIndex);
        }
        int index = listOfVegetablesInGarden.indexOf(selectedVegetable);
        return garden.getGardenList().get(index);
    }

    // EFFECTS: returns listOfCreatedVegetables as a JSON array
    public JSONArray listOfCreatedVegetablesToJson() {
        return new JSONArray(listOfCreatedVegetables);
    }

    // EFFECTS: returns a JSONObject with the listOfCreatedVegetables and the garden
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("created vegetables", listOfCreatedVegetablesToJson());
        json.put("garden", garden.gardenToJson());
        return json;
    }

    // EFFECTS: saves garden to file
    public void saveGarden() {
        try {
            jsonWriter.open();
            jsonWriter.write(toJson());
            jsonWriter.close();
            System.out.println("Saved garden to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads garden and created vegetables from file
    public void loadGarden() {
        JSONObject jsonObject;
        try {
            jsonObject = jsonReader.read();

            // This creates the list of created vegetables from the file
            JSONArray list = jsonObject.getJSONArray("created vegetables");
            listOfCreatedVegetables = new ArrayList<>(list.length());
            listOfCreatedVegetableTypes = new ArrayList<>(list.length());
            for (Object obj : list) {
                JSONObject nextObj = (JSONObject) obj;
                addCreatedVegetableToList(listOfCreatedVegetables, nextObj);
            }

            // This creates the garden from the file
            JSONArray gardenList = jsonObject.getJSONArray("garden");
            garden = new Garden(gardenList);

            // This adds each vegetable to the listOfVegetablesInGarden
            listOfVegetablesInGarden = new ArrayList<>(garden.getGardenList().size());
            Vegetable placeHolder = new Vegetable(" ", 1);
            for (Vegetable veg : garden.getGardenList()) {
                if (!(veg.equals(placeHolder))) {
                    listOfVegetablesInGarden.add(veg);
                }
            }

            System.out.println("Loaded garden from " + JSON_STORE);

        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses created vegetables from JSON object and adds them to the createdVegetableList
    private void addCreatedVegetableToList(ArrayList<Vegetable> listOfCreatedVegetables, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        int daysBetweenWatering = jsonObject.getInt("daysBetweenWatering");
        int daysSinceLastWatered = jsonObject.getInt("daysSinceLastWatered");
        Vegetable newVeg = new Vegetable(type, daysBetweenWatering);
        newVeg.setDaysSinceLastWatered(daysSinceLastWatered);
        listOfCreatedVegetables.add(newVeg);
        listOfCreatedVegetableTypes.add(type);
    }
}
