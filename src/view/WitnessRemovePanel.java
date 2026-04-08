package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Witness;

public class WitnessRemovePanel extends JPanel {

    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public WitnessRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Witness ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        // Fills with ID from all Witness objects
        Court.getInstance().getAllWitnesses().values().forEach(w -> idComboBox.addItem(w.getId()));

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
                removeWitness();
            }
        });
    }
    
    public void resetFields() {
        fillComboBoxes();
        idComboBox.setSelectedIndex(-1);
    }

    private void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllWitnesses().values().forEach(w -> idComboBox.addItem(w.getId()));
    }

    private void removeWitness() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No witness selected.");
            return;
        }

        Witness witness = Court.getInstance().getRealWitness(selectedId);
        if (witness == null) {
            messageLabel.setText("Witness not found.");
            return;
        }

        JTextArea textArea = new JTextArea(witness.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeWitness(witness);
            if (removed) {
                messageLabel.setText("Witness removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove witness.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}
