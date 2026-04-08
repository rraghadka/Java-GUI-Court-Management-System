package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.Court;
import model.Appeal;

public class AppealRemovePanel extends JPanel {

    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public AppealRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Appeal ID:");
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

        removeButton.addActionListener(e -> removeAppeal());

        fillComboBoxes();
    }

    public void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllAppeals().values().forEach(a -> idComboBox.addItem(a.getAppealID()));
    }

    public void resetFields() {
    	fillComboBoxes();
        idComboBox.setSelectedIndex(-1);
        messageLabel.setText("");
    }

    private void removeAppeal() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No appeal selected.");
            return;
        }

        Appeal appeal = Court.getInstance().getRealAppeal(selectedId);
        if (appeal == null) {
            messageLabel.setText("Appeal not found.");
            return;
        }

        JTextArea textArea = new JTextArea(appeal.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeAppeal(appeal);
            if (removed) {
                messageLabel.setText("Appeal removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove appeal.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}