package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import control.Court;
import model.Department;

public class DepartmentGetRealPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;

    public DepartmentGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Department ID:");
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

        fillComboBoxes();

        idComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Integer selectedId = (Integer) idComboBox.getSelectedItem();
                if (selectedId != null) {
                    Department department = Court.getInstance().getRealDepartment(selectedId);
                    if (department != null) {
                        detailsArea.setText(department.toString());
                    } else {
                        detailsArea.setText("Department not found.");
                    }
                } else {
                    detailsArea.setText("");
                }
            }
        });
    }

    public void resetFields() {
    	fillComboBoxes();
        idComboBox.setSelectedIndex(-1);
        detailsArea.setText("");
        
    }

    public void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllDepartments().values().forEach(dep -> idComboBox.addItem(dep.getNumber()));
    }
}