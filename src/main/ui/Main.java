package ui;

import java.io.FileNotFoundException;

// This is the main UI class from which the application is run
public class Main {
    public static void main(String[] args) {
        try {
            new GardenBuddyGui();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
