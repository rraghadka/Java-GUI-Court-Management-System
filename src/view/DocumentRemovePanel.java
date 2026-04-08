package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.Court;
import model.Document;

public class DocumentRemovePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public DocumentRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Document ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        removeButton = new JButton("Remove");
        removeButton.setBounds(200, 80, 150, 30);
        add(removeButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 130, 500, 25);
        add(messageLabel);

        removeButton.addActionListener(e -> removeDocument());

        fillComboBox(); // Load initial data
    }

    public void resetFields() {
        fillComboBox();
        messageLabel.setText("");
        idComboBox.setSelectedIndex(-1);

    }

    public void fillComboBox() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllDocuments().values().forEach(d -> idComboBox.addItem(d.getCode()));
        idComboBox.setSelectedIndex(-1);
    }

    private void removeDocument() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No document selected.");
            return;
        }

        Document doc = Court.getInstance().getRealDocument(selectedId);
        if (doc == null) {
            messageLabel.setText("Document not found.");
            return;
        }

        JTextArea textArea = new JTextArea(doc.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeDocument(doc);
            if (removed) {
                messageLabel.setText("Document removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove document.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}