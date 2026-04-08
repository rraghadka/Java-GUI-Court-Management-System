package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Judge;
import model.Lawyer;

public class LawyerRemovePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public LawyerRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Lawyer ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        Court.getInstance().getAllLawyers().values().forEach(l -> idComboBox.addItem(l.getId()));

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
                removeLawyer();
            }
        });
    }
    
    public void resetFields() {
        fillComboBox(); // Refresh lawyer IDs in the combo box
        messageLabel.setText("");
        idComboBox.setSelectedIndex(-1);

    }

    public void fillComboBox() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllLawyers().values().stream()
        .filter(l -> !(l instanceof Judge)) // Exclude judges
        .forEach(l -> idComboBox.addItem(l.getId()));
        if (idComboBox.getItemCount() == 0) {
            messageLabel.setText("No available lawyers to remove.");
        } else {
            idComboBox.setSelectedIndex(0);
        }
    }

    private void removeLawyer() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No lawyer selected.");
            return;
        }

        Lawyer lawyer = Court.getInstance().getRealLawyer(selectedId);
        if (lawyer == null) {
            messageLabel.setText("Lawyer not found.");
            return;
        }

        JTextArea textArea = new JTextArea(lawyer.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeLawyer(lawyer);
            if (removed) {
                messageLabel.setText("Lawyer removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove lawyer.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}
