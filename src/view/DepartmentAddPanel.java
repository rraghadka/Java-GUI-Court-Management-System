package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import enums.Specialization;
import model.Department;
import model.Employee;

public class DepartmentAddPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField numberField;
    private JTextField nameField;
    private JComboBox<Employee> managerComboBox;
    private JTextField buildingField;
    private JComboBox<Specialization> specializationComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public DepartmentAddPanel() {
        setLayout(null); // Absolute layout
        
        JLabel numberLabel = new JLabel("Department Number:");
        numberLabel.setBounds(50, 30, 150, 25);
        add(numberLabel);
        
        numberField = new JTextField();
        numberField.setBounds(200, 30, 200, 25);
        add(numberField);
        
        JLabel nameLabel = new JLabel("Department Name:");
        nameLabel.setBounds(50, 70, 150, 25);
        add(nameLabel);
        
        nameField = new JTextField();
        nameField.setBounds(200, 70, 200, 25);
        add(nameField);
        
        JLabel managerLabel = new JLabel("Manager:");
        managerLabel.setBounds(50, 110, 150, 25);
        add(managerLabel);
        
        managerComboBox = new JComboBox<>();
        managerComboBox.setBounds(200, 110, 200, 25);
        add(managerComboBox);
        
        JLabel buildingLabel = new JLabel("Building:");
        buildingLabel.setBounds(50, 150, 150, 25);
        add(buildingLabel);
        
        buildingField = new JTextField();
        buildingField.setBounds(200, 150, 200, 25);
        add(buildingField);
        
        JLabel specializationLabel = new JLabel("Specialization:");
        specializationLabel.setBounds(50, 190, 150, 25);
        add(specializationLabel);
        
        specializationComboBox = new JComboBox<>(Specialization.values());
        specializationComboBox.setBounds(200, 190, 200, 25);
        add(specializationComboBox);
        
        addButton = new JButton("Add Department");
        addButton.setBounds(200, 230, 150, 30);
        add(addButton);
        
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 270, 350, 25);
        add(messageLabel);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDepartment();
            }
        });
        
    }

    

    private boolean validateFields() {
        if (numberField.getText().isEmpty()) {
            messageLabel.setText("Department Number cannot be empty.");
            return false;
        }
        if (nameField.getText().isEmpty()) {
            messageLabel.setText("Department Name cannot be empty.");
            return false;
        }
        if (managerComboBox.getSelectedItem() == null) {
            messageLabel.setText("Please select a Manager.");
            return false;
        }
        if (buildingField.getText().isEmpty()) {
            messageLabel.setText("Building field cannot be empty.");
            return false;
        }
        if (specializationComboBox.getSelectedItem() == null) {
            messageLabel.setText("Please select a Specialization.");
            return false;
        }
        return true;
    }

    public void fillComboBoxes() {
        managerComboBox.removeAllItems();
        Court.getInstance().getAllEmployees().values().forEach(managerComboBox::addItem);
    }

    public void resetFields() {
        numberField.setText("");
        nameField.setText("");
        managerComboBox.setSelectedIndex(-1);
        buildingField.setText("");
        specializationComboBox.setSelectedIndex(0);
        fillComboBoxes();
    }

    private void addDepartment() {
        if (!validateFields()) {
            return;
        }        try {
            int number = Integer.parseInt(numberField.getText());
            String name = nameField.getText();
            Employee manager = (Employee) managerComboBox.getSelectedItem();
            String building = buildingField.getText();
            Specialization specialization = (Specialization) specializationComboBox.getSelectedItem();
            
            if (manager == null) {
                messageLabel.setText("Please select a manager.");
                return;
            }
            
            Department department = new Department(number, name, manager, building, specialization);
            if (Court.getInstance().addDepartment(department)) {
                messageLabel.setText("Department added successfully!");
                resetFields();
            } else {
                messageLabel.setText("Failed to add department.");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid number format.");
        }
    }
}