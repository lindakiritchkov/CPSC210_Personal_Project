package ui;

import model.Event;
import model.EventLog;
import model.Garden;
import model.Vegetable;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents a GUI application for Garden Buddy
public class GardenBuddyGui implements ActionListener {

    // persistence related fields
    private static final String JSON_STORE = "./data/garden.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // gui related fields
    JFrame frame;
    JInternalFrame internalFrame;
    JPanel contentPane;
    JMenuBar menuBar;
    JButton createVegButton;
    JButton plantVegButton;
    JButton removeVegButton;
    JButton saveGardenButton;
    JButton loadGardenButton;

    //selected garden spot
    int colIndex;

    //stores whether the current selection is planting or removing vegetables
    String selection;

    //stores the index of the vegetable to be planted (in list of created vegetables)
    int indexOfVegToPlant;

    // Garden related fields:
    private GardenPlot gardenPlot;
    private Garden garden;
    private ArrayList<Vegetable> listOfVegetablesInGarden;

    // INVARIANT: listOfCreatedVegetables and listOfCreatedVegetableTypes in correspondence. Lists are the same size.
    //            The 1st item in listOfCreatedVegetableTypes is the vegetable type of the first vegetable in
    //            listOfCreatedVegetables,
    //            The 2nd item in listOfCreatedVegetableTypes is the vegetable type of the second vegetable in
    //            listOfCreatedVegetables, and so on.
    private ArrayList<Vegetable> listOfCreatedVegetables;
    private ArrayList<String> listOfCreatedVegetableTypes;

    // MODIFIES: this
    // EFFECTS: sets up the fields that are related to the garden
    public GardenBuddyGui() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        garden = new Garden(10);
        listOfCreatedVegetables = new ArrayList<>();
        listOfCreatedVegetableTypes = new ArrayList<>();
        createAndShowGUI();
    }


    // MODIFIES: this
    // EFFECTS: Sets up and shows the GUI
    private void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Garden Buddy");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog eventLog = EventLog.getInstance();
                for (Event next : eventLog) {
                    System.out.println(next);
                }
                System.exit(0);
            }
        });

        //Create and set up the content pane.
        frame.setJMenuBar(this.createMenuBar());
        frame.setContentPane(createContentPane());

        //Display the window.
        frame.setSize(845, 163);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the menu bar
    public JMenuBar createMenuBar() {

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Create buttons in menu bar
        buildCreateVegButton();
        buildPlaceVegButton();
        buildRemoveVegButton();
        buildSaveGardenButton();
        buildLoadGardenButton();

        return menuBar;
    }

    // MODIFIES: this
    // EFFECTS: Builds the "Create Vegetable" button
    public void buildCreateVegButton() {
        ImageIcon createVegButtonIcon = createImageIcon("images/new_veg_image.png");
        createVegButton = new JButton("Create Vegetable", createVegButtonIcon);
        createVegButton.setMnemonic(KeyEvent.VK_A);
        createVegButton.setToolTipText("Click this button to create a vegetable");
        createVegButton.setActionCommand("Create Vegetable");
        createVegButton.addActionListener(this);
        menuBar.add(createVegButton);
    }

    // MODIFIES: this
    // EFFECTS: Builds the "Plant Vegetable" Button
    public void buildPlaceVegButton() {
        ImageIcon plantVegButtonIcon = createImageIcon("images/plant_veg_image.png");
        plantVegButton = new JButton("Plant Vegetable", plantVegButtonIcon);
        plantVegButton.setMnemonic(KeyEvent.VK_N);
        plantVegButton.setToolTipText("Click this button to plant a vegetable in the garden");
        plantVegButton.setActionCommand("Plant Vegetable");
        plantVegButton.addActionListener(this);
        menuBar.add(plantVegButton);
    }

    // MODIFIES: this
    // EFFECTS: Builds the "Remove Vegetable" Button
    public void buildRemoveVegButton() {
        ImageIcon removeVegButtonIcon = createImageIcon("images/remove_veg_image.png");
        removeVegButton = new JButton("Remove Vegetable", removeVegButtonIcon);
        removeVegButton.setMnemonic(KeyEvent.VK_N);
        removeVegButton.setToolTipText("Click this button to remove a vegetable from the garden");
        removeVegButton.setActionCommand("Remove Vegetable");
        removeVegButton.addActionListener(this);
        menuBar.add(removeVegButton);
    }

    // MODIFIES: this
    // EFFECTS: Builds the "Load Garden" Button
    public void buildLoadGardenButton() {
        ImageIcon loadButtonIcon = createImageIcon("images/load_image.png");
        loadGardenButton = new JButton("Load Garden",loadButtonIcon);
        loadGardenButton.setMnemonic(KeyEvent.VK_N);
        loadGardenButton.setToolTipText("Click this button to load a garden");
        loadGardenButton.setActionCommand("Load Garden");
        loadGardenButton.addActionListener(this);
        menuBar.add(loadGardenButton);
    }

    // MODIFIES: this
    // EFFECTS: Builds the "Save Garden" Button
    public void buildSaveGardenButton() {
        ImageIcon saveGardenIcon = createImageIcon("images/save_image.png");
        saveGardenButton = new JButton("Save Garden", saveGardenIcon);
        saveGardenButton.setMnemonic(KeyEvent.VK_N);
        saveGardenButton.setToolTipText("Click this button to save your garden");
        saveGardenButton.setActionCommand("Save Garden");
        saveGardenButton.addActionListener(this);
        menuBar.add(saveGardenButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates the main content pane in the GUI
    public Container createContentPane() {
        //Create the content-pane-to-be.
        contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        // Create actual content area
        internalFrame = new JInternalFrame("Garden Plot");

        //Add the text area to the content pane.
        contentPane.add(internalFrame, BorderLayout.CENTER);
        //Create the scroll pane and add the table to it.
        contentPane.add(gardenPlotPanel());

        return contentPane;
    }

    // MODIFIES: this, GardenPlot
    // EFFECTS: Initializes the GardenPlot and creates a JTable that represents the GardenPlot
    public JTable gardenPlotPanel() {
        gardenPlot = new GardenPlot(this);
        JTable table = new JTable(gardenPlot);
        table.setPreferredSize(new Dimension(350,70));
        table.setFillsViewportHeight(true);
        table.getTableHeader().setUI(null);
        table.setRowHeight(70);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            // EFFECTS: listens for selections in the table
            @Override
            public void valueChanged(ListSelectionEvent e) {
                colIndex = table.getSelectedColumn();
                if (selection.equals("Plant")) {
                    plantVegetable(indexOfVegToPlant);
                } else if (selection.equals("Remove")) {
                    removeVegetable();
                }
            }
        });
        return table;
    }

    // MODIFIES: this
    // EFFECTS: processes user actions
    public void actionPerformed(ActionEvent e) {
        if ("Create Vegetable".equals(e.getActionCommand())) {
            createVegetable();
        } else if ("Plant Vegetable".equals(e.getActionCommand())) {
            chooseVegetable();
        } else if ("Remove Vegetable".equals(e.getActionCommand())) {
            updateSelectionToRemoveVegetable();
        } else if ("Save Garden".equals(e.getActionCommand())) {
            saveGarden();
        } else {
            loadGarden();
        }
    }

    // MODIFIES: this, CreateVegetableScreen
    // EFFECTS: runs the CreateVegetableScreen
    public void createVegetable() {
        CreateVegetableScreen.run(this);
    }

    // MODIFIES: this
    // EFFECTS: adds a vegetable into the garden
    public void addVegetable(Vegetable vegetable) {
        listOfCreatedVegetables.add(vegetable);
        listOfCreatedVegetableTypes.add(vegetable.getType());
    }

    // MODIFIES: this, ChooseVegetableScreen
    // EFFECTS: runs the ChooseVegetableScreen, updates selection to allow planting
    public void chooseVegetable() {
        this.selection = "Plant";
        ChooseVegetableScreen.run(this);
    }

    // MODIFIES: this
    // EFFECTS: plants vegetable in garden at the correct position
    public void plantVegetable(int indexOfVeg) {
        Vegetable vegToPlant = listOfCreatedVegetables.get(indexOfVeg);
        int index = colIndex + 1;
        garden.addVegetable(vegToPlant, index);
        update();
    }

    // MODIFIES: this, GardenPlotPanel
    // EFFECTS: updates the GardenPlotPanel and GUI to reflect changes
    public void update() {
        contentPane.removeAll();
        contentPane.add(gardenPlotPanel());
        contentPane.revalidate();
        contentPane.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Updates the selection field to "Remove"
    public void updateSelectionToRemoveVegetable() {
        selection = "Remove";
    }

    // MODIFIES: this
    // EFFECTS: removes the chosen vegetable from the Garden
    public void removeVegetable() {
        garden.removeVegetableAtPosition(colIndex);
        update();
    }

    // EFFECTS: saves garden to file
    public void saveGarden() {
        try {
            jsonWriter.open();
            jsonWriter.write(toJson());
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECTS: returns a JSONObject with the listOfCreatedVegetables and the garden
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("created vegetables", listOfCreatedVegetablesToJson());
        json.put("garden", garden.gardenToJson());
        return json;
    }

    // EFFECTS: returns listOfCreatedVegetables as a JSON array
    public JSONArray listOfCreatedVegetablesToJson() {
        return new JSONArray(listOfCreatedVegetables);
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

            update();

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

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = GardenBuddyGui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // EFFECTS: returns the garden
    public Garden getGarden() {
        return garden;
    }

    // EFFECTS: returns the listOfCreatedVegetableTypes
    public ArrayList<String> getListOfCreatedVegetableTypes() {
        return listOfCreatedVegetableTypes;
    }

    // MODIFIES: this
    // EFFECTS: sets indexOfVegToPlant to the given index
    public void setIndexOfVegToPlant(int i) {
        this.indexOfVegToPlant = i;
    }
}
