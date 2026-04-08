package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;
import control.Court;
import model.CriminalCase;

public class UniqueCrimeToolsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> crimeSceneDropdown;
    private JButton searchButton;

    public UniqueCrimeToolsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 900));

        // Table columns
        String[] columns = {"Unique Crime Tools"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Crime Scene selection panel
        JPanel topPanel = new JPanel();
        crimeSceneDropdown = new JComboBox<>(getAllCrimeScenes());
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> loadCrimeToolsData());
        topPanel.add(new JLabel("Select Crime Scene: "));
        topPanel.add(crimeSceneDropdown);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);
        resetFields();
    }

    private String[] getAllCrimeScenes() {
        Set<String> crimeScenes = Court.getInstance().getAllCases().values().stream()
                .filter(c -> c instanceof CriminalCase)
                .map(c -> ((CriminalCase) c).getCrimeScene())
                .collect(Collectors.toSet());
        return crimeScenes.toArray(new String[0]);
    }

    
    public void resetFields() {
        fillComboBoxes();
        tableModel.setRowCount(0);
    }

    private void fillComboBoxes() {
        crimeSceneDropdown.removeAllItems();
        Set<String> crimeScenes = Court.getInstance().getAllCases().values().stream()
                .filter(c -> c instanceof CriminalCase)
                .map(c -> ((CriminalCase) c).getCrimeScene())
                .collect(Collectors.toSet());
        crimeScenes.forEach(crimeSceneDropdown::addItem);
    }
    
    private void loadCrimeToolsData() {
        tableModel.setRowCount(0); // Clear existing rows
        String crimeScene = (String) crimeSceneDropdown.getSelectedItem();
        if (crimeScene == null || crimeScene.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a crime scene.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Set<String> crimeTools = Court.getInstance().findUniqueCrimeToolsByCrimeScene(crimeScene);
        
        if (crimeTools.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No unique crime tools found for this crime scene.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (String tool : crimeTools) {
            tableModel.addRow(new Object[]{tool});
        }
    }
}