package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.Court;
import model.Accused;

public class AccusedRemovePanel extends JPanel {

    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public AccusedRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Accused ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        removeButton = new JButton("Remove");
        removeButton.setBounds(200, 80, 150, 30);
        add(removeButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 130, 500, 25);
        add(messageLabel);

        removeButton.addActionListener(e -> removeAccused());

        fillComboBoxes();
    }

    public void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllAccuseds().values().forEach(a -> idComboBox.addItem(a.getId()));
        idComboBox.setSelectedIndex(-1);
    }

    public void resetFields() {
        fillComboBoxes();
        messageLabel.setText("");
        idComboBox.setSelectedIndex(-1);

    }

    public void removeAccused() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No accused selected.");
            return;
        }

        Accused accused = Court.getInstance().getRealAccused(selectedId);
        if (accused == null) {
            messageLabel.setText("Accused not found.");
            return;
        }

        JTextArea textArea = new JTextArea(accused.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeAccused(accused);
            if (removed) {
                messageLabel.setText("Accused removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove accused.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}