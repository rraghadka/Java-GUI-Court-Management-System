package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import control.Court;
import model.Case;
import model.CriminalCase;
import model.FamilyCase;
import model.FinancialCase;

public class CaseRemovePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> typeComboBox;   
    private JComboBox<String> caseIdComboBox; 
    private JButton removeButton;
    private JLabel messageLabel;

    public CaseRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel typeLabel = new JLabel("Case Type:");
        typeLabel.setBounds(50, 30, 100, 25);
        add(typeLabel);

        typeComboBox = new JComboBox<>();
        typeComboBox.setBounds(150, 30, 150, 25);
        add(typeComboBox);

        JLabel caseLabel = new JLabel("Select Case Code:");
        caseLabel.setBounds(50, 70, 150, 25);
        add(caseLabel);

        caseIdComboBox = new JComboBox<>();
        caseIdComboBox.setBounds(150, 70, 150, 25);
        add(caseIdComboBox);

        removeButton = new JButton("Remove");
        removeButton.setBounds(150, 110, 150, 30);
        add(removeButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 160, 400, 25);
        add(messageLabel);

        typeComboBox.addActionListener(e -> refreshCaseCodes());
        removeButton.addActionListener(e -> removeSelectedCase());

        fillComboBoxes();
    }

    public void fillComboBoxes() {
        typeComboBox.removeAllItems();
        typeComboBox.addItem("All");
        typeComboBox.addItem("Criminal");
        typeComboBox.addItem("Family");
        typeComboBox.addItem("Financial");

        refreshCaseCodes();
    }

    public void resetFields() {
        typeComboBox.setSelectedIndex(0);
        messageLabel.setText("");
        fillComboBoxes();
        caseIdComboBox.setSelectedIndex(-1);

    }

    private void refreshCaseCodes() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        caseIdComboBox.removeAllItems();

        Collection<Case> allCases = Court.getInstance().getAllCases().values();

        for (Case c : allCases) {
            if ("All".equals(selectedType)) {
                caseIdComboBox.addItem(c.getCode());
            } else if ("Criminal".equals(selectedType) && c instanceof CriminalCase) {
                caseIdComboBox.addItem(c.getCode());
            } else if ("Family".equals(selectedType) && c instanceof FamilyCase) {
                caseIdComboBox.addItem(c.getCode());
            } else if ("Financial".equals(selectedType) && c instanceof FinancialCase) {
                caseIdComboBox.addItem(c.getCode());
            }
        }
    }

    private void removeSelectedCase() {
        String selectedCode = (String) caseIdComboBox.getSelectedItem();
        if (selectedCode == null) {
            messageLabel.setText("No case selected.");
            return;
        }

        Case selectedCase = Court.getInstance().getRealCase(selectedCode);
        if (selectedCase == null) {
            messageLabel.setText("Case not found.");
            return;
        }

        JTextArea textArea = new JTextArea(selectedCase.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeCase(selectedCase);
            if (removed) {
                messageLabel.setText("Case removed successfully!");
                caseIdComboBox.removeItem(selectedCode);
            } else {
                messageLabel.setText("Failed to remove case.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}