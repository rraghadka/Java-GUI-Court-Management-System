package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import control.Court;
import model.Verdict;
import model.Judge;
import model.Case;
import utils.UtilsMethods;

public class VerdictAddPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField verdictSummaryField;
    private JTextField issuedDateField;
    private JComboBox<Judge> judgeComboBox;
    private JComboBox<Case> caseComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public VerdictAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // Verdict Summary
        JLabel verdictSummaryLabel = new JLabel("Verdict Summary:");
        verdictSummaryLabel.setBounds(50, 30, 150, 25);
        add(verdictSummaryLabel);

        verdictSummaryField = new JTextField();
        verdictSummaryField.setBounds(200, 30, 300, 25);
        add(verdictSummaryField);

        // Issued Date
        JLabel issuedDateLabel = new JLabel("Issued Date (yyyy-MM-dd):");
        issuedDateLabel.setBounds(50, 70, 200, 25);
        add(issuedDateLabel);

        issuedDateField = new JTextField();
        issuedDateField.setBounds(250, 70, 250, 25);
        add(issuedDateField);

        // Judge Combo
        JLabel judgeLabel = new JLabel("Select Judge:");
        judgeLabel.setBounds(50, 110, 150, 25);
        add(judgeLabel);

        judgeComboBox = new JComboBox<>();
        judgeComboBox.setBounds(200, 110, 300, 25);
        add(judgeComboBox);

        // Fill judge combo from Court
        // Adjust getAllJudges() if your method name differs
        Court.getInstance().getAllLawyers().values().forEach(law -> {
            if (law instanceof Judge) {
                judgeComboBox.addItem((Judge)law);
            }
        });

        // Case Combo
        JLabel caseLabel = new JLabel("Select Case:");
        caseLabel.setBounds(50, 150, 150, 25);
        add(caseLabel);

        caseComboBox = new JComboBox<>();
        caseComboBox.setBounds(200, 150, 300, 25);
        add(caseComboBox);

        // Fill case combo
        // Adjust getAllCases() if your method name differs
        Court.getInstance().getAllCases().values().forEach(c -> caseComboBox.addItem(c));

        

        // Button
        addButton = new JButton("Add Verdict");
        addButton.setBounds(200, 230, 150, 30);
        add(addButton);

        // Message Label
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 280, 400, 25);
        add(messageLabel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVerdict();
            }
        });
    }

    private boolean validateFields() {
        if (verdictSummaryField.getText().isEmpty()) {
            messageLabel.setText("Verdict Summary cannot be empty.");
            return false;
        }
        if (issuedDateField.getText().isEmpty()) {
            messageLabel.setText("Issued Date cannot be empty.");
            return false;
        }
        if (judgeComboBox.getSelectedItem() == null) {
            messageLabel.setText("A Judge must be selected.");
            return false;
        }
        if (caseComboBox.getSelectedItem() == null) {
            messageLabel.setText("A Case must be selected.");
            return false;
        }
        return true;
    }

    public void resetFields() {
        fillComboBoxes();
        verdictSummaryField.setText("");
        issuedDateField.setText("");
        judgeComboBox.setSelectedIndex(-1);
        caseComboBox.setSelectedIndex(-1);
    }

    private void fillComboBoxes() {
        judgeComboBox.removeAllItems();
        caseComboBox.removeAllItems();

        Court.getInstance().getAllLawyers().values().forEach(law -> {
            if (law instanceof Judge) {
                judgeComboBox.addItem((Judge) law);
            }
        });
        Court.getInstance().getAllCases().values().forEach(c -> caseComboBox.addItem(c));
    }

    private void addVerdict() {
        if (!validateFields()) {
            return;
        }
        try {
            String summary = verdictSummaryField.getText();
            Date issuedDate = UtilsMethods.parseDate(issuedDateField.getText());
            Judge selectedJudge = (Judge) judgeComboBox.getSelectedItem();
            Case selectedCase = (Case) caseComboBox.getSelectedItem();

            if (selectedJudge == null || selectedCase == null) {
                messageLabel.setText("Judge or Case not selected.");
                return;
            }

            // Create new Verdict
            Verdict verdict = new Verdict(summary, issuedDate, selectedJudge, selectedCase);

            // Add or store the verdict. Adjust if you have a different method.
            Court.getInstance().addVerdict(verdict);

            messageLabel.setText("Verdict added successfully!");
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Invalid input. Check fields.");
        }
    }
}
