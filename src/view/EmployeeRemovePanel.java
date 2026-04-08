package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Employee;

public class EmployeeRemovePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public EmployeeRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Employee ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        Court.getInstance().getAllEmployees().values().forEach(e -> idComboBox.addItem(e.getId()));

        removeButton = new JButton("Remove");
        removeButton.setBounds(200, 80, 150, 30);
        add(removeButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 130, 500, 25);
        add(messageLabel);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeEmployee();
            }
        });
    }

    public void resetFields() {
        fillComboBox();
        idComboBox.setSelectedIndex(-1);

    }

    public void fillComboBox() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllEmployees().values().forEach(e -> idComboBox.addItem(e.getId()));
        idComboBox.setSelectedIndex(-1);
    }

    private void removeEmployee() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No employee selected.");
            return;
        }

        Employee emp = Court.getInstance().getRealEmployee(selectedId);
        if (emp == null) {
            messageLabel.setText("Employee not found.");
            return;
        }

        JTextArea textArea = new JTextArea(emp.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeEmployee(emp);
            if (removed) {
                messageLabel.setText("Employee removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove employee.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}