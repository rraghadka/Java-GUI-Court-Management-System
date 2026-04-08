package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Courtroom;

public class CourtroomRemovePanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public CourtroomRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Courtroom ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        Court.getInstance().getAllCourtrooms().values().forEach(c -> idComboBox.addItem(c.getCourtroomNumber()));

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
                removeCourtroom();
            }
        });
    }
    
    public void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllCourtrooms().values().forEach(c -> idComboBox.addItem(c.getCourtroomNumber()));
    }

    public void resetFields() {
        fillComboBoxes();
        idComboBox.setSelectedIndex(-1);

    }

    private void removeCourtroom() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No courtroom selected.");
            return;
        }

        Courtroom cr = Court.getInstance().getRealCourtroom(selectedId);
        if (cr == null) {
            messageLabel.setText("Courtroom not found.");
            return;
        }

        JTextArea textArea = new JTextArea(cr.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeCourtroom(cr);
            if (removed) {
                messageLabel.setText("Courtroom removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove courtroom.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}
