package view;
import java.io.*;
import java.sql.Time;
import java.util.*;
import javax.swing.JOptionPane;

import Exceptions.InvalidBirthDateException;
import utils.UtilsMethods;
import control.Court;
import enums.Gender;
import enums.Specialization;
import enums.Status;
import enums.Position;
import model.*;

public class Main {
    public static Court court = Court.getInstance();
    private static Map<String, Command> commands = new HashMap<>();

    public static void main(String[] args) throws IOException {
        loadDataFromCSV("INPUT.csv");  // Read CSV input
        save();  // Save court data
        // Open GUI after loading data
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        // Save data when the program ends
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            save();
        }));
    }

   

    public static void loadDataFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue;
                
                String commandName = parts[0];
                String[] args = Arrays.copyOfRange(parts, 1, parts.length);

                if (commands.containsKey(commandName)) {
                    commands.get(commandName).execute(args);
                } else {
                    System.out.println("Unknown command: " + commandName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (FileOutputStream fos = new FileOutputStream("Court.ser");
             ObjectOutputStream out = new ObjectOutputStream(fos)) {
            out.writeObject(court);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to save data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private interface Command {
        void execute(String... args);
    }
}
