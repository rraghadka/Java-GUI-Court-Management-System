package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import control.Court;
import model.Judge;

public class JudgeGetRealPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Integer> idComboBox;
    private JTextArea detailsArea;

    public JudgeGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Judge ID:");
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

        Court.getInstance().getAllLawyers().values().forEach(l -> {
            if (l instanceof Judge) {
                idComboBox.addItem(l.getId());
            }
        });
        idComboBox.setSelectedIndex(-1);


        idComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Integer selectedId = (Integer) idComboBox.getSelectedItem();
                    if (selectedId != null) {
                        // getRealJudge uses the same map as lawyers but returns a cast judge
                        Judge judge = Court.getInstance().getRealJudge(selectedId);
                        if (judge != null) {
                            detailsArea.setText(judge.toString());
                        } else {
                            detailsArea.setText("Judge not found.");
                        }
                    } else {
                        detailsArea.setText("");
                    }
                }
            }
        });
    }
    
    public void resetFields() {
        fillComboBox();
        detailsArea.setText("");
        idComboBox.setSelectedIndex(-1);

    }

    public void fillComboBox() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllLawyers().values().forEach(l -> {
            if (l instanceof Judge) {
                idComboBox.addItem(l.getId());
            }
        });

        if (idComboBox.getItemCount() == 0) {
            detailsArea.setText("No judges available.");
        } else {
            idComboBox.setSelectedIndex(0);
        }
    }
}
