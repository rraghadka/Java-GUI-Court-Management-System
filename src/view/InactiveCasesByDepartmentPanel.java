package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import control.Court;
import model.Department;

public class InactiveCasesByDepartmentPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel tableModel;

    public InactiveCasesByDepartmentPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1200, 900));

        // Table columns
        String[] columns = { "Department", "Inactive Cases" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

    }

    public void resetFields() {
        fillTable();
    }

    public void fillTable() {
        tableModel.setRowCount(0); // Clear existing rows
        HashMap<Department, Integer> data = Court.getInstance().findInActiveCasesCountByDepartment();

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No inactive cases found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Department dept : data.keySet()) {
            tableModel.addRow(new Object[]{ dept.getName(), data.get(dept) });
        }
    }
}