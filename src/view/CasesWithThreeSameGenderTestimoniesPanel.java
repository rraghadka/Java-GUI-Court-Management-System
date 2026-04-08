package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import control.Court;
import model.Case;
import enums.Gender;

public class CasesWithThreeSameGenderTestimoniesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<Gender> genderComboBox;
    private JButton searchButton;

    public CasesWithThreeSameGenderTestimoniesPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 900));

        // Table columns
        String[] columns = {"Case Code", "Status", "Specialization", "Accused"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Gender selection panel
        JPanel topPanel = new JPanel();
        genderComboBox = new JComboBox<>();
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> loadCasesData());

        topPanel.add(new JLabel("Select Gender: "));
        topPanel.add(genderComboBox);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        fillComboBoxes();
    }

    public void fillComboBoxes() {
        genderComboBox.removeAllItems();
        for (Gender g : Gender.values()) {
            genderComboBox.addItem(g);
        }
    }

    public void resetFields() {
        genderComboBox.setSelectedIndex(-1);
        tableModel.setRowCount(0);
        fillComboBoxes();
    }

    private void loadCasesData() {
        tableModel.setRowCount(0); // Clear existing rows
        Gender selectedGender = (Gender) genderComboBox.getSelectedItem();
        if (selectedGender == null) {
            JOptionPane.showMessageDialog(this, "Please select a gender.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Case> cases = Court.getInstance().findCasesWithMoreThanThreeTestimoniesFromSameGender(selectedGender);
        if (cases.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No cases found for the selected gender.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Case c : cases) {
            tableModel.addRow(new Object[]{
                c.getCode(),
                c.getCaseStatus(),
                c.getCaseType(),
                c.getAccused().getFirstName() + ' ' + c.getAccused().getLastName()
            });
        }
    }
}