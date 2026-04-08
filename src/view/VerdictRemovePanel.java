package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Verdict;

public class VerdictRemovePanel extends JPanel {

    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public VerdictRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Verdict ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        Court.getInstance().getAllVerdicts().values().forEach(v -> idComboBox.addItem(v.getVerdictID()));

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
                removeVerdict();
            }
        });
    }
    public void resetFields() {
        fillComboBoxes();
        
    }

    private void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllVerdicts().values().forEach(v -> idComboBox.addItem(v.getVerdictID()));
    }

    private void removeVerdict() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No verdict selected.");
            return;
        }

        Verdict verdict = Court.getInstance().getRealVerdict(selectedId);
        if (verdict == null) {
            messageLabel.setText("Verdict not found.");
            return;
        }

        JTextArea textArea = new JTextArea(verdict.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeVerdict(verdict);
            if (removed) {
                messageLabel.setText("Verdict removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove verdict.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}
