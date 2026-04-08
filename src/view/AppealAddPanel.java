package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import control.Court;
import model.Appeal;
import model.Verdict;
import utils.UtilsMethods;

public class AppealAddPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField appealSummaryField;
    private JTextField appealDateField;
    private JComboBox<Verdict> currentVerdictComboBox;
    private JComboBox<Verdict> newVerdictComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public AppealAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // Appeal Summary
        JLabel appealSummaryLabel = new JLabel("Appeal Summary:");
        appealSummaryLabel.setBounds(50, 30, 150, 25);
        add(appealSummaryLabel);

        appealSummaryField = new JTextField();
        appealSummaryField.setBounds(200, 30, 300, 25);
        add(appealSummaryField);

        // Appeal Date
        JLabel appealDateLabel = new JLabel("Appeal Date (yyyy-MM-dd):");
        appealDateLabel.setBounds(50, 70, 200, 25);
        add(appealDateLabel);

        appealDateField = new JTextField();
        appealDateField.setBounds(250, 70, 250, 25);
        add(appealDateField);

        // Current Verdict
        JLabel currentVerdictLabel = new JLabel("Current Verdict:");
        currentVerdictLabel.setBounds(50, 110, 150, 25);
        add(currentVerdictLabel);

        currentVerdictComboBox = new JComboBox<>();
        currentVerdictComboBox.setBounds(200, 110, 300, 25);
        add(currentVerdictComboBox);

        // Fill current verdict combo
        // Adjust getAllVerdicts() if your method name differs
        Court.getInstance().getAllVerdicts().values().forEach(v -> currentVerdictComboBox.addItem(v));

        // New Verdict
        JLabel newVerdictLabel = new JLabel("New Verdict:");
        newVerdictLabel.setBounds(50, 150, 150, 25);
        add(newVerdictLabel);

        newVerdictComboBox = new JComboBox<>();
        newVerdictComboBox.setBounds(200, 150, 300, 25);
        add(newVerdictComboBox);

        // Fill new verdict combo
        Court.getInstance().getAllVerdicts().values().forEach(v -> newVerdictComboBox.addItem(v));

        // Button
        addButton = new JButton("Add Appeal");
        addButton.setBounds(200, 190, 150, 30);
        add(addButton);

        // Message Label
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 240, 400, 25);
        add(messageLabel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppeal();
            }
        });
    }
    
    private boolean validateFields() {
        if (appealSummaryField.getText().isEmpty()) {
            messageLabel.setText("Appeal Summary cannot be empty.");
            return false;
        }
        if (appealDateField.getText().isEmpty()) {
            messageLabel.setText("Appeal Date cannot be empty.");
            return false;
        }
        if (currentVerdictComboBox.getSelectedItem() == null) {
            messageLabel.setText("Current Verdict must be selected.");
            return false;
        }
        if (newVerdictComboBox.getSelectedItem() == null) {
            messageLabel.setText("New Verdict must be selected.");
            return false;
        }
        return true;
    }

    public void resetFields() {
        appealSummaryField.setText("");
        appealDateField.setText("");
        fillComboBoxes();
    }
    
    public void fillComboBoxes() {
        currentVerdictComboBox.removeAllItems();
        newVerdictComboBox.removeAllItems();
        Court.getInstance().getAllVerdicts().values().forEach(v -> {
            currentVerdictComboBox.addItem(v);
            newVerdictComboBox.addItem(v);
        });
        currentVerdictComboBox.setSelectedIndex(-1);
        newVerdictComboBox.setSelectedIndex(-1);
    }

    private void addAppeal() {
    	if (!validateFields()) {
            return;
        }
        try {
            String summary = appealSummaryField.getText();
            Date appealDate = UtilsMethods.parseDate(appealDateField.getText());
            Verdict currentVerdict = (Verdict) currentVerdictComboBox.getSelectedItem();
            Verdict newVerdict = (Verdict) newVerdictComboBox.getSelectedItem();

            // Create new Appeal
            Appeal appeal = new Appeal(summary, appealDate, currentVerdict, newVerdict);

            // Add or store the appeal. Adjust if you have a different method.
            Court.getInstance().addAppeal(appeal);

            messageLabel.setText("Appeal added successfully!");
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Invalid input. Check fields.");
        }
    }
}
