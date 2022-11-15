package ui;

import model.Garden;
import model.Vegetable;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

// Represents the Garden Plot as a single-row table
public class GardenPlot extends AbstractTableModel {
    private GardenBuddyGui gui;
    private Garden garden;
    private int size;
    private Vector columns;
    private Vector types;

    // MODIFIES: this
    // EFFECTS: creates the GardenPlot panel to represent the garden
    public GardenPlot(GardenBuddyGui gui) {
        this.gui = gui;
        garden = gui.getGarden();
        this.size = garden.getSizeOfGarden();
        columns = new Vector(1);
        for (int i = 0; i < size; i++) {
            Vegetable vegetable = garden.getGardenList().get(i);
            String type = vegetable.getType();
            columns.add(type);
        }

        types = new Vector(0);
        for (int i = 0; i < size; i++) {
            Vegetable vegetable = garden.getGardenList().get(i);
            String type = vegetable.getType();
            types.add(type);
        }
    }

    // EFFECTS: returns the number of rows (always 1)
    @Override
    public int getRowCount() {
        return 1;
    }

    // EFFECTS: returns the number of columns (size of the garden)
    @Override
    public int getColumnCount() {
        return garden.getSizeOfGarden();
    }

    // EFFECTS: returns the value at the given column index
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return types.get(columnIndex);
    }
}
