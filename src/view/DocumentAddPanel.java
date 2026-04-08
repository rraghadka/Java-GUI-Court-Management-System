package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import control.Court;
import model.Case;
import model.Document;
import utils.UtilsMethods;

public class DocumentAddPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField titleField;
    private JTextArea contentArea;
    private JTextField issuedDateField;
    private JComboBox<Case> caseComboBox;
    private JButton addButton;
    private JLabel messageLabel;

    public DocumentAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // Title
        JLabel titleLabel = new JLabel("Document Title:");
        titleLabel.setBounds(50, 30, 150, 25);
        add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(200, 30, 300, 25);
        add(titleField);

        // Content
        JLabel contentLabel = new JLabel("Content:");
        contentLabel.setBounds(50, 70, 150, 25);
        add(contentLabel);

        contentArea = new JTextArea();
        contentArea.setBounds(200, 70, 500, 200);
        add(contentArea);

        // Issued Date
        JLabel issuedDateLabel = new JLabel("Issued Date (yyyy-MM-dd):");
        issuedDateLabel.setBounds(50, 290, 200, 25);
        add(issuedDateLabel);

        issuedDateField = new JTextField();
        issuedDateField.setBounds(250, 290, 250, 25);
        add(issuedDateField);

        // Case Combo
        JLabel caseLabel = new JLabel("Select Case:");
        caseLabel.setBounds(50, 330, 150, 25);
        add(caseLabel);

        caseComboBox = new JComboBox<>();
        caseComboBox.setBounds(200, 330, 300, 25);
        add(caseComboBox);

        // Fill case combo
        Court.getInstance().getAllCases().values().forEach(c -> caseComboBox.addItem(c));

        // Button
        addButton = new JButton("Add Document");
        addButton.setBounds(200, 380, 150, 30);
        add(addButton);

        // Message Label
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 420, 400, 25);
        add(messageLabel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDocument();
            }
        });
    }

    private boolean validateFields() {
        if (titleField.getText().isEmpty()) {
            messageLabel.setText("Document Title cannot be empty.");
            return false;
        }
        if (contentArea.getText().isEmpty()) {
            messageLabel.setText("Content cannot be empty.");
            return false;
        }
        if (issuedDateField.getText().isEmpty()) {
            messageLabel.setText("Issued Date cannot be empty.");
            return false;
        }
        if (caseComboBox.getSelectedItem() == null) {
            messageLabel.setText("A Case must be selected.");
            return false;
        }
        return true;
    }

    public void resetFields() {
        titleField.setText("");
        contentArea.setText("");
        issuedDateField.setText("");
        caseComboBox.setSelectedIndex(-1);
        fillComboBoxes();
    }

    public void fillComboBoxes() {
        caseComboBox.removeAllItems();
        Court.getInstance().getAllCases().values().forEach(caseComboBox::addItem);
    }

    private void addDocument() {
        if (!validateFields()) {
            return;
        }        try {
            String title = titleField.getText();
            String content = contentArea.getText();
            Date issuedDate = UtilsMethods.parseDate(issuedDateField.getText());
            Case selectedCase = (Case) caseComboBox.getSelectedItem();

            if (selectedCase == null) {
                messageLabel.setText("Case not selected.");
                return;
            }

            Document document = new Document(title, content, issuedDate, selectedCase);
            Court.getInstance().addDocument(document);

            messageLabel.setText("Document added successfully!");
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Invalid input. Check fields.");
        }
    }
}
