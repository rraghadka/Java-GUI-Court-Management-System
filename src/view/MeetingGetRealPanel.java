package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import control.Court;
import model.Meeting;

public class MeetingGetRealPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;

    public MeetingGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Meeting ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        detailsArea = new JTextArea();
        detailsArea.setBounds(50, 80, 600, 300);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);  // Enables line wrapping
        detailsArea.setWrapStyleWord(true); // Wraps text at word boundaries
        add(detailsArea);

        Court.getInstance().getAllMeetings().values().forEach(m -> {
            idComboBox.addItem(m.getMeetingID());
        });
        idComboBox.setSelectedIndex(-1);


        idComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer selectedId = (Integer) idComboBox.getSelectedItem();
                    if (selectedId != null) {
                        Meeting meet = Court.getInstance().getRealMeeting(selectedId);
                        if (meet != null) {
                            detailsArea.setText(meet.toString());
                        } else {
                            detailsArea.setText("Meeting not found.");
                        }
                    } else {
                        detailsArea.setText("");
                    }
                }
            }
        });
    }
    public void resetFields() {
        idComboBox.removeAllItems(); // Clear existing items

        Court.getInstance().getAllMeetings().values().forEach(m -> idComboBox.addItem(m.getMeetingID()));

        if (idComboBox.getItemCount() == 0) {
            detailsArea.setText("No meetings available.");
        } else {
            idComboBox.setSelectedIndex(-1);
        }
    }
}
