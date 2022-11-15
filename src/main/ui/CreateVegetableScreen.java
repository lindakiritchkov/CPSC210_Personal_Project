package ui;

import model.Vegetable;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.Format;
import java.text.NumberFormat;

// Represents the Create Vegetable Screen
public class CreateVegetableScreen extends JPanel implements ActionListener {

    private GardenBuddyGui gui;

    protected JButton okayButton;
    private static JFrame frame;

    //Labels to identify fields
    private JLabel typeLabel;
    private JLabel daysLabel;

    // Strings for the labels
    private static String typeString = "Vegetable type: ";
    private static String daysString = "How often does it need to be watered? ";

    // Fields for data entry
    private JFormattedTextField typeField;
    private JFormattedTextField daysField;

    // formats for data
    private Format typeFormat;
    private NumberFormat daysFormat;

    // EFFECTS: creates the CreateVegetableScreen and initializes fields
    public CreateVegetableScreen(GardenBuddyGui gui) {
        this.gui = gui;

        // Create the labels
        typeLabel = new JLabel(typeString);
        daysLabel = new JLabel(daysString);

        // create the text fields + set them up
        typeField = new JFormattedTextField(new DefaultFormatterFactory(new NumberFormatter(daysFormat)));
        typeField.setColumns(10);
        daysField = new JFormattedTextField(new DefaultFormatterFactory());
        daysField.setColumns(10);

        // Tell accessibility tools about label/textfield pairs
        typeLabel.setLabelFor(typeField);
        daysLabel.setLabelFor(daysField);

        //Layout the labels in a panel
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(typeLabel);
        labelPane.add(daysLabel);

        // Layout the test fields in a panel
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(typeField);
        fieldPane.add(daysField);

        createOkayButton();

        //Add Components to this container, using the default FlowLayout.
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(labelPane,BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        add(okayButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the "Okay" Button
    public void createOkayButton() {
        okayButton = new JButton("Okay");
        okayButton.setVerticalTextPosition(AbstractButton.CENTER);
        okayButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        okayButton.setMnemonic(KeyEvent.VK_D);
        okayButton.setActionCommand("Okay");

        //Listen for actions on okayButton
        okayButton.addActionListener(this);
    }


    //MODIFIES: GardenBuddyGUI, Vegetable
    //EFFECTS: Creates the vegetable and adds it into created vegetable list in GUI, then closes the window
    public void actionPerformed(ActionEvent e) {
        if ("Okay".equals(e.getActionCommand())) {
            String type = typeField.getText();
            int daysBtwnWatering = Integer.valueOf(daysField.getText());
            Vegetable newVegetable = new Vegetable(type, daysBtwnWatering);
            gui.addVegetable(newVegetable);
            frame.dispose();
        }
    }

    //EFFECTS: runs this window
    public static void run(GardenBuddyGui gui) {

        //Create and set up the window.
        frame = new JFrame("Garden Buddy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CreateVegetableScreen newContentPane = new CreateVegetableScreen(gui);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: Creates an Image Icon
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = GardenBuddyGui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}