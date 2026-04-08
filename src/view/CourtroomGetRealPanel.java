package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import control.Court;
import model.Courtroom;

public class CourtroomGetRealPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;

    public CourtroomGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Courtroom ID:");
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

        Court.getInstance().getAllCourtrooms().values().forEach(cr -> {
            idComboBox.addItem(cr.getCourtroomNumber());
        });
        idComboBox.setSelectedIndex(-1);


        idComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer selectedId = (Integer) idComboBox.getSelectedItem();
                    if (selectedId != null) {
                        Courtroom cr = Court.getInstance().getRealCourtroom(selectedId);
                        if (cr != null) {
                            detailsArea.setText(cr.toString());
                        } else {
                            detailsArea.setText("Courtroom not found.");
                        }
                    } else {
                        detailsArea.setText("");
                    }
                }
            }
        });
    }
    
    public void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllCourtrooms().values().forEach(cr -> idComboBox.addItem(cr.getCourtroomNumber()));
        idComboBox.setSelectedIndex(-1);
    }

    public void resetFields() {
        idComboBox.setSelectedIndex(-1);
        detailsArea.setText("");
        fillComboBoxes();
    }
}
