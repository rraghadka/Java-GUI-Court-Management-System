package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Meeting;

public class MeetingRemovePanel extends JPanel {
    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public MeetingRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Meeting ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        // Fill with meeting IDs
        // Adjust getAllMeetings() if your method is named differently
        Court.getInstance().getAllMeetings().values().forEach(m -> idComboBox.addItem(m.getMeetingID()));

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
                removeMeeting();
            }
        });
    }
    public void resetFields() {
        idComboBox.removeAllItems(); // Clear existing items

        Court.getInstance().getAllMeetings().values().forEach(m -> idComboBox.addItem(m.getMeetingID()));
        idComboBox.setSelectedIndex(-1);

        if (idComboBox.getItemCount() == 0) {
            messageLabel.setText("No meetings available.");
            removeButton.setEnabled(false);
        } else {
            messageLabel.setText("");
            removeButton.setEnabled(true);
        }
    }

    private void removeMeeting() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No meeting selected.");
            return;
        }

        // Get the actual Meeting by ID
        Meeting meet = Court.getInstance().getRealMeeting(selectedId);
        if (meet == null) {
            messageLabel.setText("Meeting not found.");
            return;
        }

        // Show toString in scrollable popup
        JTextArea textArea = new JTextArea(meet.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeMeeting(meet);
            if (removed) {
                messageLabel.setText("Meeting removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove meeting.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}
