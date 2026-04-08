package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.Court;
import model.Courtroom;
import model.Department;

public class CourtroomAddPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField courtroomNumberField;
    private JComboBox<Department> departmentComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public CourtroomAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // Courtroom Number
        JLabel numberLabel = new JLabel("Courtroom Number:");
        numberLabel.setBounds(50, 30, 150, 25);
        add(numberLabel);

        courtroomNumberField = new JTextField();
        courtroomNumberField.setBounds(200, 30, 200, 25);
        add(courtroomNumberField);

        // Department
        JLabel departmentLabel = new JLabel("Select Department:");
        departmentLabel.setBounds(50, 70, 150, 25);
        add(departmentLabel);

        departmentComboBox = new JComboBox<>();
        departmentComboBox.setBounds(200, 70, 300, 25);
        add(departmentComboBox);

        // Fill department combo
        // Adjust getAllDepartments() if your method name differs
        Court.getInstance().getAllDepartments().values().forEach(dep -> departmentComboBox.addItem(dep));

        // Button
        addButton = new JButton("Add Courtroom");
        addButton.setBounds(200, 110, 150, 30);
        add(addButton);

        // Message Label
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 160, 400, 25);
        add(messageLabel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourtroom();
            }
        });
    }

    private boolean validateFields() {
        if (courtroomNumberField.getText().isEmpty()) {
            messageLabel.setText("Courtroom Number cannot be empty.");
            return false;
        }
        if (departmentComboBox.getSelectedItem() == null) {
            messageLabel.setText("Please select a Department.");
            return false;
        }
        return true;
    }

    public void fillComboBoxes() {
        departmentComboBox.removeAllItems();
        Court.getInstance().getAllDepartments().values().forEach(departmentComboBox::addItem);
    }

    public void resetFields() {
        courtroomNumberField.setText("");
        departmentComboBox.setSelectedIndex(-1);
        fillComboBoxes();
    }

    private void addCourtroom() {
        if (!validateFields()) {
            return;
        }        try {
            int number = Integer.parseInt(courtroomNumberField.getText());
            Department selectedDepartment = (Department) departmentComboBox.getSelectedItem();

            if (selectedDepartment == null) {
                messageLabel.setText("Department not selected.");
                return;
            }

            Courtroom courtroom = new Courtroom(number, selectedDepartment);
            // Adjust if you have a different method for adding a courtroom
            Court.getInstance().addCourtroom(courtroom);

            messageLabel.setText("Courtroom added successfully!");
            resetFields();
        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid input. Check the number.");
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Error adding courtroom.");
        }
    }
}
