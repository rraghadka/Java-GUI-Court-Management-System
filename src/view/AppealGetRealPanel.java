package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import control.Court;
import model.Appeal;

public class AppealGetRealPanel extends JPanel {

    private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;

    public AppealGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Appeal ID:");
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

        idComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Integer selectedId = (Integer) idComboBox.getSelectedItem();
                if (selectedId != null) {
                    Appeal appeal = Court.getInstance().getRealAppeal(selectedId);
                    if (appeal != null) {
                        detailsArea.setText(appeal.toString());
                    } else {
                        detailsArea.setText("Appeal not found.");
                    }
                } else {
                    detailsArea.setText("");
                }
            }
        });
    }

    public void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllAppeals().values().forEach(a -> idComboBox.addItem(a.getAppealID()));
        idComboBox.setSelectedIndex(-1);
    }

    public void resetFields() {
        idComboBox.setSelectedIndex(-1);
        detailsArea.setText("");
    }
}