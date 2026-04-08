package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import control.Court;
import model.FamilyCase;

public class FamilyCasesWithWitnessesPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;

    public FamilyCasesWithWitnessesPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 900));

        // Table columns
        String[] columns = {"Case Code", "Status", "Specialization", "Accused"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void resetFields() {
        fillTable();
    }

    public void fillTable() {
        tableModel.setRowCount(0); // Clear existing rows
        List<FamilyCase> cases = Court.getInstance().findFamilyCasesWithWitnessesFromBothSides();

        if (cases.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No family cases found with witnesses from both sides.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (FamilyCase familyCase : cases) {
            tableModel.addRow(new Object[]{
                familyCase.getCode(),
                familyCase.getCaseStatus(),
                familyCase.getCaseType(),
                familyCase.getAccused().getFirstName() + " " + familyCase.getAccused().getLastName()
            });
        }
    }
}