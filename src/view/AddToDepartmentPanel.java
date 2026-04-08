package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.Court;
import model.Department;
import model.Employee;
import model.Judge;
import model.Lawyer;

public class AddToDepartmentPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> typeComboBox;
    private JComboBox<Integer> personComboBox;
    private JComboBox<Integer> departmentComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public AddToDepartmentPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // Type Selection
        JLabel typeLabel = new JLabel("Select Type:");
        typeLabel.setBounds(50, 30, 150, 25);
        add(typeLabel);

        typeComboBox = new JComboBox<>(new String[]{"Employee", "Lawyer", "Judge"});
        typeComboBox.setBounds(200, 30, 200, 25);
        add(typeComboBox);

        // Person Selection
        JLabel personLabel = new JLabel("Select Person:");
        personLabel.setBounds(50, 70, 150, 25);
        add(personLabel);

        personComboBox = new JComboBox<>();
        personComboBox.setBounds(200, 70, 200, 25);
        add(personComboBox);

        // Department Selection
        JLabel departmentLabel = new JLabel("Select Department:");
        departmentLabel.setBounds(50, 110, 150, 25);
        add(departmentLabel);

        departmentComboBox = new JComboBox<>();
        departmentComboBox.setBounds(200, 110, 200, 25);
        add(departmentComboBox);

        // Add Button
        addButton = new JButton("Add to Department");
        addButton.setBounds(200, 150, 200, 30);
        add(addButton);

        // Message Label
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 200, 500, 25);
        add(messageLabel);

        // Listeners
        typeComboBox.addActionListener(e -> fillPersonComboBox());
        addButton.addActionListener(e -> addPersonToDepartment());

        fillDepartmentComboBox();
        fillPersonComboBox();
    }

    private void fillPersonComboBox() {
        personComboBox.removeAllItems();
        String selectedType = (String) typeComboBox.getSelectedItem();

        if ("Employee".equals(selectedType)) {
            Court.getInstance().getAllEmployees().values().forEach(e -> personComboBox.addItem(e.getId()));
        } else if ("Lawyer".equals(selectedType)) {
            Court.getInstance().getAllLawyers().values().stream()
                    .filter(l -> !(l instanceof Judge)) // Exclude Judges
                    .forEach(l -> personComboBox.addItem(l.getId()));
        } else if ("Judge".equals(selectedType)) {
            Court.getInstance().getAllLawyers().values().stream()
                    .filter(l -> l instanceof Judge) // Only Judges
                    .forEach(j -> personComboBox.addItem(j.getId()));
        }
    }

    private void fillDepartmentComboBox() {
        departmentComboBox.removeAllItems();
        Court.getInstance().getAllDepartments().values().forEach(d -> departmentComboBox.addItem(d.getNumber()));
    }

    private void addPersonToDepartment() {
        if (departmentComboBox.getSelectedItem() == null || personComboBox.getSelectedItem() == null) {
            messageLabel.setText("Please select both a person and a department.");
            return;
        }

        Integer departmentId = (Integer) departmentComboBox.getSelectedItem();
        Integer personId = (Integer) personComboBox.getSelectedItem();
        String selectedType = (String) typeComboBox.getSelectedItem();
        
        Department department = Court.getInstance().getRealDepartment(departmentId);
        boolean success = false;

        if ("Employee".equals(selectedType)) {
            Employee employee = Court.getInstance().getRealEmployee(personId);
            success = Court.getInstance().addEmployeeToDepartment(department, employee);
        } else if ("Lawyer".equals(selectedType)) {
            Lawyer lawyer = Court.getInstance().getRealLawyer(personId);
            success = Court.getInstance().addLawyerToDepartment(department, lawyer);
        } else if ("Judge".equals(selectedType)) {
            Judge judge = (Judge) Court.getInstance().getRealLawyer(personId);
            success = Court.getInstance().addJudgeToDepartment(department, judge);
        }

        if (success) {
            messageLabel.setForeground(Color.GREEN);
            messageLabel.setText(selectedType + " added to department successfully!");
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Failed to add " + selectedType + " to department.");
        }
    }

    public void resetFields() {
        fillPersonComboBox();
        fillDepartmentComboBox();
        typeComboBox.setSelectedIndex(0);
        personComboBox.setSelectedIndex(-1);
        departmentComboBox.setSelectedIndex(-1);
        messageLabel.setText("");
    }
}