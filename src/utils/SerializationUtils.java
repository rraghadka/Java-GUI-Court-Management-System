package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import control.Court;

public class SerializationUtils {

    // Method to serialize the Court object and save it to a file
    public static void saveCourt() {
        try (FileOutputStream fileOut = new FileOutputStream("Court.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(Court.getInstance());
            System.out.println("Court data is saved in Court.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to deserialize the Court object from a file
    public static void loadCourt() {
        try (FileInputStream fileIn = new FileInputStream("Court.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
        	Court loadedCourt = (Court) in.readObject();
            Court.setInstance(loadedCourt);
            System.out.println("Court data is loaded from Court.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
