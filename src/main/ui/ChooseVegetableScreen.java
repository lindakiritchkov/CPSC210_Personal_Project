package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// Represents a screen that allows the user to choose from created vegetables
public class ChooseVegetableScreen extends JPanel implements ActionListener {

    private final GardenBuddyGui gui;
    private final ArrayList<String> createdVeg;

    ArrayList<JButton> vegButtons;
    private static JFrame frame;

    // MODIFIES: this
    // EFFECTS: constructs the ChooseVegetableScreen
    public ChooseVegetableScreen(GardenBuddyGui gui) {
        this.gui = gui;
        this.createdVeg = gui.getListOfCreatedVegetableTypes();
        vegButtons = new ArrayList<>();

        createVegButtons();

        //Add Components to this container, using the default FlowLayout.
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        for (JButton button : vegButtons) {
            add(button);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates buttons for each created vegetable
    public void createVegButtons() {
        for (String veg: createdVeg) {
            JButton vegButton = new JButton(veg);
            vegButton.setVerticalTextPosition(AbstractButton.CENTER);
            vegButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
            vegButton.setMnemonic(KeyEvent.VK_D);
            vegButton.setActionCommand(veg);
            vegButton.addActionListener(this);
            vegButtons.add(vegButton);
        }
    }


    //MODIFIES: this, GardenBuddyGUI
    //EFFECTS: listens for which vegetable button is chosen, and updates the gui to store this data,
    //         then closes this window
    public void actionPerformed(ActionEvent e) {
        int numCreatedVeg = createdVeg.size();
        for (int i = 0; i < numCreatedVeg; i++) {
            String type = createdVeg.get(i);
            if (type.equals(e.getActionCommand())) {
                gui.setIndexOfVegToPlant(i);
                frame.dispose();
            }
        }
    }

    // EFFECTS: runs this window
    public static void run(GardenBuddyGui gui) {

        //Create and set up the window.
        frame = new JFrame("Choose a Vegetable");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        ChooseVegetableScreen newContentPane = new ChooseVegetableScreen(gui);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}