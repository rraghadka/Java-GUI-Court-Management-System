package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import control.Court;
import model.Accused;

public class AccusedGetRealPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;

    public AccusedGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Accused ID:");
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

        fillComboBoxes();

        idComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer selectedId = (Integer) idComboBox.getSelectedItem();
                    if (selectedId != null) {
                        Accused accused = Court.getInstance().getRealAccused(selectedId);
                        if (accused != null) {
                            detailsArea.setText(accused.toString());
                        } else {
                            detailsArea.setText("Accused not found.");
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
        Court.getInstance().getAllAccuseds().values().forEach(acc -> idComboBox.addItem(acc.getId()));
        idComboBox.setSelectedIndex(-1);
    }

    public void resetFields() {
        fillComboBoxes();
        detailsArea.setText("");
    }
}