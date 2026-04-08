package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Case;
import model.Testimony;
import model.Witness;

public class TestimonyAddPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Case> caseComboBox;
    private JTextArea testimonyContentArea;
    private JComboBox<Witness> witnessComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public TestimonyAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));
        
        JLabel caseLabel = new JLabel("Select Case:");
        caseLabel.setBounds(50, 30, 150, 25);
        add(caseLabel);

        caseComboBox = new JComboBox<>();
        caseComboBox.setBounds(200, 30, 300, 25);
        add(caseComboBox);

        // Fill case combo from Court
        // Adjust getAllCases() if your method name differs
        Court.getInstance().getAllCases().values().forEach(c -> caseComboBox.addItem(c));

        JLabel witnessLabel = new JLabel("Select Witness:");
        witnessLabel.setBounds(50, 70, 150, 25);
        add(witnessLabel);

        witnessComboBox = new JComboBox<>();
        witnessComboBox.setBounds(200, 70, 300, 25);
        add(witnessComboBox);

        // Fill witness combo
        // Adjust getAllWitnesses() if your method name differs
        Court.getInstance().getAllWitnesses().values().forEach(w -> witnessComboBox.addItem(w));

        JLabel testimonyContentLabel = new JLabel("Testimony Content:");
        testimonyContentLabel.setBounds(50, 110, 150, 25);
        add(testimonyContentLabel);

        testimonyContentArea = new JTextArea();
        testimonyContentArea.setBounds(200, 110, 500, 200);
        add(testimonyContentArea);

        addButton = new JButton("Add Testimony");
        addButton.setBounds(200, 330, 150, 30);
        add(addButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 380, 500, 25);
        add(messageLabel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTestimony();
            }
        });
    }
    

    private boolean validateFields() {
        if (caseComboBox.getSelectedItem() == null) {
            messageLabel.setText("A Case must be selected.");
            return false;
        }
        if (witnessComboBox.getSelectedItem() == null) {
            messageLabel.setText("A Witness must be selected.");
            return false;
        }
        if (testimonyContentArea.getText().isEmpty()) {
            messageLabel.setText("Testimony content cannot be empty.");
            return false;
        }
        return true;
    }

    public void resetFields() {
        fillComboBoxes();
        testimonyContentArea.setText("");
    }

    private void fillComboBoxes() {
        caseComboBox.removeAllItems();
        witnessComboBox.removeAllItems();
        Court.getInstance().getAllCases().values().forEach(caseComboBox::addItem);
        Court.getInstance().getAllWitnesses().values().forEach(witnessComboBox::addItem);
    }

    private void addTestimony() {
        if (!validateFields()) {
            return;
        }        try {
            Case selectedCase = (Case) caseComboBox.getSelectedItem();
            Witness selectedWitness = (Witness) witnessComboBox.getSelectedItem();
            String testimonyContent = testimonyContentArea.getText();

            if (selectedCase == null || selectedWitness == null || testimonyContent.isEmpty()) {
                messageLabel.setText("Please select a case, a witness, and enter testimony content.");
                return;
            }

            Testimony testimony = new Testimony(selectedCase, testimonyContent, selectedWitness);
            // Adjust your Court method for adding a testimony if needed
            Court.getInstance().addTestimony(testimony);

            messageLabel.setText("Testimony added successfully!");
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Invalid input. Check fields.");
        }
    }
}
