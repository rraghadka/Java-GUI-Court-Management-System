package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import control.Court;
import model.Employee;

public class EmployeeGetRealPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;

    public EmployeeGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Employee ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        detailsArea = new JTextArea();
        detailsArea.setBounds(50, 80, 600, 300);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);  // Enables line wrapping
        detailsArea.setWrapStyleWord(true); // Wraps text at word boundaries
        add(detailsArea);

        fillComboBox(); // Load initial data

        idComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Integer selectedId = (Integer) idComboBox.getSelectedItem();
                if (selectedId != null) {
                    Employee employee = Court.getInstance().getRealEmployee(selectedId);
                    detailsArea.setText((employee != null) ? employee.toString() : "Employee not found.");
                } else {
                    detailsArea.setText("");
                }
            }
        });
    }

    public void resetFields() {
        fillComboBox();
        detailsArea.setText("");
        idComboBox.setSelectedIndex(-1);

    }

    public void fillComboBox() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllEmployees().values().forEach(emp -> idComboBox.addItem(emp.getId()));
        idComboBox.setSelectedIndex(-1);
    }
}