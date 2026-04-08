package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import control.Court;
import model.Testimony;

public class TestimonyGetRealPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> testimonyIdComboBox;
    private JTextArea detailsArea;

    public TestimonyGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Testimony ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        testimonyIdComboBox = new JComboBox<>();
        testimonyIdComboBox.setBounds(200, 30, 150, 25);
        add(testimonyIdComboBox);

        detailsArea = new JTextArea();
        detailsArea.setBounds(50, 80, 600, 300);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);  // Enables line wrapping
        detailsArea.setWrapStyleWord(true); // Wraps text at word boundaries
        add(detailsArea);

        Court.getInstance().getAllTestimonies().values().forEach(t -> {
            testimonyIdComboBox.addItem(t.getTestimonyID());
        });
        testimonyIdComboBox.setSelectedIndex(-1);


        testimonyIdComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer selectedId = (Integer) testimonyIdComboBox.getSelectedItem();
                    if (selectedId != null) {
                        Testimony testimony = Court.getInstance().getRealTestimony(selectedId);
                        if (testimony != null) {
                            detailsArea.setText(testimony.toString());
                        } else {
                            detailsArea.setText("Testimony not found.");
                        }
                    } else {
                        detailsArea.setText("");
                    }
                }
            }
        });
    }
    
    public void resetFields() {
        fillComboBoxes();
        detailsArea.setText("");
        testimonyIdComboBox.setSelectedIndex(-1);

    }

    private void fillComboBoxes() {
        testimonyIdComboBox.removeAllItems();
        Court.getInstance().getAllTestimonies().values().forEach(t -> testimonyIdComboBox.addItem(t.getTestimonyID()));
    }
}
