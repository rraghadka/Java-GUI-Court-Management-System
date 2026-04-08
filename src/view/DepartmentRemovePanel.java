package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.Court;
import model.Department;

public class DepartmentRemovePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<Integer> departmentIdComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public DepartmentRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Department ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        departmentIdComboBox = new JComboBox<>();
        departmentIdComboBox.setBounds(200, 30, 150, 25);
        add(departmentIdComboBox);

        removeButton = new JButton("Remove");
        removeButton.setBounds(200, 80, 150, 30);
        add(removeButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 130, 500, 25);
        add(messageLabel);

        removeButton.addActionListener(e -> removeSelectedDepartment());

        fillComboBoxes();
    }

    public void resetFields() {
        fillComboBoxes();
        departmentIdComboBox.setSelectedIndex(-1);
    }

    public void fillComboBoxes() {
        departmentIdComboBox.removeAllItems();
        Court.getInstance().getAllDepartments().values()
                .forEach(dep -> departmentIdComboBox.addItem(dep.getNumber()));
    }

    private void removeSelectedDepartment() {
        Integer selectedId = (Integer) departmentIdComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No department ID selected.");
            return;
        }

        Department department = Court.getInstance().getRealDepartment(selectedId);
        if (department == null) {
            messageLabel.setText("Department not found in Court.");
            return;
        }

        JTextArea textArea = new JTextArea(department.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeDepartment(department);
            if (removed) {
                messageLabel.setText("Department removed successfully!");
                departmentIdComboBox.removeItem(selectedId);
                resetFields();
            } else {
                messageLabel.setText("Failed to remove department.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}