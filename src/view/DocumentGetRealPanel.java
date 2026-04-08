package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import control.Court;
import model.Document;

public class DocumentGetRealPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<Integer> docIdComboBox;
    private JTextArea detailsArea;

    public DocumentGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Document ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        docIdComboBox = new JComboBox<>();
        docIdComboBox.setBounds(200, 30, 150, 25);
        add(docIdComboBox);

        detailsArea = new JTextArea();
        detailsArea.setBounds(50, 80, 600, 300);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);  // Enables line wrapping
        detailsArea.setWrapStyleWord(true); // Wraps text at word boundaries
        add(detailsArea);

        fillComboBox(); // Load initial data

        docIdComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Integer selectedCode = (Integer) docIdComboBox.getSelectedItem();
                if (selectedCode != null) {
                    Document doc = Court.getInstance().getRealDocument(selectedCode);
                    detailsArea.setText((doc != null) ? doc.toString() : "Document not found.");
                } else {
                    detailsArea.setText("");
                }
            }
        });
    }

    public void resetFields() {
        fillComboBox();
        detailsArea.setText("");
        docIdComboBox.setSelectedIndex(-1);

    }

    public void fillComboBox() {
        docIdComboBox.removeAllItems();
        Court.getInstance().getAllDocuments().values().forEach(doc -> docIdComboBox.addItem(doc.getCode()));
        docIdComboBox.setSelectedIndex(-1);
    }
}