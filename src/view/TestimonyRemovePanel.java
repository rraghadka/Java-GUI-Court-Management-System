package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import model.Testimony;

public class TestimonyRemovePanel extends JPanel {

    private JComboBox<Integer> idComboBox;
    private JButton removeButton;
    private JLabel messageLabel;

    public TestimonyRemovePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel selectLabel = new JLabel("Select Testimony ID:");
        selectLabel.setBounds(50, 30, 150, 25);
        add(selectLabel);

        idComboBox = new JComboBox<>();
        idComboBox.setBounds(200, 30, 150, 25);
        add(idComboBox);

        // Assuming getAllTestimonies() returns a map of <Integer, Testimony>
        Court.getInstance().getAllTestimonies().values().forEach(t -> idComboBox.addItem(t.getTestimonyID()));

        removeButton = new JButton("Remove");
        removeButton.setBounds(200, 80, 150, 30);
        add(removeButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 130, 500, 25);
        add(messageLabel);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTestimony();
            }
        });
    }
    public void resetFields() {
        fillComboBoxes();
        idComboBox.setSelectedIndex(-1);

    }

    private void fillComboBoxes() {
        idComboBox.removeAllItems();
        Court.getInstance().getAllTestimonies().values().forEach(t -> idComboBox.addItem(t.getTestimonyID()));
    }

    private void removeTestimony() {
        Integer selectedId = (Integer) idComboBox.getSelectedItem();
        if (selectedId == null) {
            messageLabel.setText("No testimony selected.");
            return;
        }

        // Assuming getRealTestimony() retrieves the full object by ID
        Testimony testimony = Court.getInstance().getRealTestimony(selectedId);
        if (testimony == null) {
            messageLabel.setText("Testimony not found.");
            return;
        }

        JTextArea textArea = new JTextArea(testimony.toString(), 10, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int choice = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Are you sure?",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            boolean removed = Court.getInstance().removeTestimony(testimony);
            if (removed) {
                messageLabel.setText("Testimony removed successfully!");
                idComboBox.removeItem(selectedId);
            } else {
                messageLabel.setText("Failed to remove testimony.");
            }
        } else {
            messageLabel.setText("Removal canceled.");
        }
    }
}
