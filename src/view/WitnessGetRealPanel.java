package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import control.Court;
import model.Witness;

public class WitnessGetRealPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;
    private JScrollPane scrollPane;

    public WitnessGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Witness ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);  // Enables line wrapping
        detailsArea.setWrapStyleWord(true); // Wraps text at word boundaries

        scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(50, 80, 600, 300);
        add(scrollPane);

        fillComboBoxes();
        idComboBox.setSelectedIndex(-1);

        idComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer selectedId = (Integer) idComboBox.getSelectedItem();
                    if (selectedId != null) {
                        Witness witness = Court.getInstance().getRealWitness(selectedId);
                        if (witness != null) {
                            detailsArea.setText(witness.toString());
                        } else {
                            detailsArea.setText("Witness not found.");
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
        idComboBox.setSelectedIndex(-1);
    }

    private void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllWitnesses().values().forEach(w -> idComboBox.addItem(w.getId()));
    }
}