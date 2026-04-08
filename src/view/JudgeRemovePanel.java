package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Judge;

public class JudgeRemovePanel extends JPanel {

    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public JudgeRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Judge ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        Court.getInstance().getAllLawyers().values().forEach(l -> {
            if (l instanceof Judge) {
                idComboBox.addItem(l.getId());
            }
        });

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
                removeJudge();
            }
        });
    }
    
    public void resetFields() {
        fillComboBox(); // Refresh judge IDs in the combo box
        messageLabel.setText("");
        idComboBox.setSelectedIndex(-1);

    }

    public void fillComboBox() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllLawyers().values().forEach(l -> {
            if (l instanceof Judge) {
                idComboBox.addItem(l.getId());
            }
        });

        if (idComboBox.getItemCount() == 0) {
            messageLabel.setText("No judges available.");
        } else {
            idComboBox.setSelectedIndex(0);
        }
    }

    private void removeJudge() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No judge selected.");
            return;
        }

        // Retrieves the actual Judge by ID (through getRealLawyer())
        Judge judge = (Judge) Court.getInstance().getRealLawyer(selectedId);
        if (judge == null) {
            messageLabel.setText("Judge not found.");
            return;
        }

        // Displays judge.toString() in a scrollable dialog for confirmation
        JTextArea textArea = new JTextArea(judge.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeJudge(judge);
            if (removed) {
                messageLabel.setText("Judge removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove judge.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}