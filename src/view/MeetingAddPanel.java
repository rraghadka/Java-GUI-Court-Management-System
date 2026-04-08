package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import control.Court;
import model.Meeting;
import model.Case;
import model.Courtroom;
import utils.UtilsMethods;

public class MeetingAddPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField meetingDateField; // e.g. yyyy-MM-dd
    private JTextField timeField;        // e.g. HH:mm
    private JComboBox<Courtroom> courtroomComboBox;
    private JComboBox<Case> caseComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public MeetingAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // Meeting Date
        JLabel dateLabel = new JLabel("Meeting Date (yyyy-MM-dd):");
        dateLabel.setBounds(50, 30, 200, 25);
        add(dateLabel);

        meetingDateField = new JTextField();
        meetingDateField.setBounds(250, 30, 200, 25);
        add(meetingDateField);

        // Time
        JLabel timeLabel = new JLabel("Time (HH:mm):");
        timeLabel.setBounds(50, 70, 150, 25);
        add(timeLabel);

        timeField = new JTextField();
        timeField.setBounds(250, 70, 200, 25);
        add(timeField);

        // Courtroom
        JLabel courtroomLabel = new JLabel("Select Courtroom:");
        courtroomLabel.setBounds(50, 110, 150, 25);
        add(courtroomLabel);

        courtroomComboBox = new JComboBox<>();
        courtroomComboBox.setBounds(250, 110, 250, 25);
        add(courtroomComboBox);

        // Fill courtroom combo
        // Adjust getAllCourtrooms() if your method name differs
        Court.getInstance().getAllCourtrooms().values().forEach(cr -> courtroomComboBox.addItem(cr));

        // Case
        JLabel caseLabel = new JLabel("Select Case:");
        caseLabel.setBounds(50, 150, 150, 25);
        add(caseLabel);

        caseComboBox = new JComboBox<>();
        caseComboBox.setBounds(250, 150, 250, 25);
        add(caseComboBox);

        // Fill case combo
        Court.getInstance().getAllCases().values().forEach(c -> caseComboBox.addItem(c));

        // Button
        addButton = new JButton("Add Meeting");
        addButton.setBounds(250, 190, 150, 30);
        add(addButton);

        // Message Label
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 240, 600, 25);
        add(messageLabel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMeeting();
            }
        });
        
    }

    private boolean validateFields() {
        if (meetingDateField.getText().isEmpty()) {
            messageLabel.setText("Meeting Date cannot be empty.");
            return false;
        }
        if (timeField.getText().isEmpty()) {
            messageLabel.setText("Time cannot be empty.");
            return false;
        }
        if (courtroomComboBox.getSelectedItem() == null) {
            messageLabel.setText("A Courtroom must be selected.");
            return false;
        }
        if (caseComboBox.getSelectedItem() == null) {
            messageLabel.setText("A Case must be selected.");
            return false;
        }
        return true;
    }

    public void resetFields() {
        meetingDateField.setText("");
        timeField.setText("");
        fillComboBoxes(); // Refreshes available courtroom and case options
    }

    public void fillComboBoxes() {
        courtroomComboBox.removeAllItems();
        caseComboBox.removeAllItems();

        Court.getInstance().getAllCourtrooms().values().forEach(courtroomComboBox::addItem);
        Court.getInstance().getAllCases().values().forEach(caseComboBox::addItem);

        if (courtroomComboBox.getItemCount() == 0 || caseComboBox.getItemCount() == 0) {
            messageLabel.setText("No available courtrooms or cases.");
        } else {
            courtroomComboBox.setSelectedIndex(0);
            caseComboBox.setSelectedIndex(0);
        }
    }

    private void addMeeting() {
        if (!validateFields()) {
            return;
        }        try {
            // Parse date
            Date meetingDate = UtilsMethods.parseDate(meetingDateField.getText());
            // Parse time
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            long ms = sdf.parse(timeField.getText()).getTime();
            Time hour = new Time(ms);

            Courtroom selectedCourtroom = (Courtroom) courtroomComboBox.getSelectedItem();
            Case selectedCase = (Case) caseComboBox.getSelectedItem();

            if (selectedCourtroom == null || selectedCase == null) {
                messageLabel.setText("Courtroom or Case not selected.");
                return;
            }

            // Create Meeting
            Meeting meeting = new Meeting(meetingDate, hour, selectedCourtroom, selectedCase);

            // Add or store the meeting. Adjust if you have a different method.
            Court.getInstance().addMeeting(meeting);

            messageLabel.setText("Meeting added successfully!");
            resetFields();
        } catch (ParseException ex) {
            ex.printStackTrace();
            messageLabel.setText("Invalid time format. Use HH:mm.");
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Invalid input. Check fields.");
        }
    }
}
