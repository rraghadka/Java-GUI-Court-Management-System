package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import control.Court;
import model.Verdict;

public class VerdictGetRealPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> verdictIdComboBox;
    private JTextArea detailsArea;

    public VerdictGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Verdict ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        verdictIdComboBox = new JComboBox<>();
        verdictIdComboBox.setBounds(200, 30, 150, 25);
        add(verdictIdComboBox);

        detailsArea = new JTextArea();
        detailsArea.setBounds(50, 80, 600, 300);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);  // Enables line wrapping
        detailsArea.setWrapStyleWord(true); // Wraps text at word boundaries
        add(detailsArea);

        Court.getInstance().getAllVerdicts().values().forEach(v -> {
            verdictIdComboBox.addItem(v.getVerdictID());
        });
        verdictIdComboBox.setSelectedIndex(-1);


        verdictIdComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer selectedId = (Integer) verdictIdComboBox.getSelectedItem();
                    if (selectedId != null) {
                        Verdict verdict = Court.getInstance().getRealVerdict(selectedId);
                        if (verdict != null) {
                            detailsArea.setText(verdict.toString());
                        } else {
                            detailsArea.setText("Verdict not found.");
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
        verdictIdComboBox.setSelectedIndex(-1);
    }

    private void fillComboBoxes() {
        verdictIdComboBox.removeAllItems();
        Court.getInstance().getAllVerdicts().values().forEach(v -> verdictIdComboBox.addItem(v.getVerdictID()));
    }

}
