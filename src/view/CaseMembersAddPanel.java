package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Court;
import enums.Gender;
import model.Accused;
import model.Witness;
import utils.UtilsMethods;
import java.util.Date;

public class CaseMembersAddPanel extends JPanel {
    private JRadioButton witnessRadio, accusedRadio;
    private JTextField idField, firstNameField, lastNameField, phoneField, emailField, birthDateField, addressField;
    private JComboBox<Gender> genderComboBox;
    private JTextField jobField;
    private JButton addButton;
    private JLabel messageLabel;
private JLabel jobLabel;
    
    public CaseMembersAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));
        
        JLabel roleLabel = new JLabel("Select Type:");
        roleLabel.setBounds(50, 30, 150, 25);
        add(roleLabel);
        
        witnessRadio = new JRadioButton("Witness");
        witnessRadio.setBounds(200, 30, 100, 25);
        accusedRadio = new JRadioButton("Accused");
        accusedRadio.setBounds(300, 30, 100, 25);
        
        ButtonGroup group = new ButtonGroup();
        group.add(witnessRadio);
        group.add(accusedRadio);
        add(witnessRadio);
        add(accusedRadio);
        
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 70, 150, 25);
        add(idLabel);
        idField = new JTextField();
        idField.setBounds(200, 70, 200, 25);
        add(idField);
        
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 110, 150, 25);
        add(firstNameLabel);
        firstNameField = new JTextField();
        firstNameField.setBounds(200, 110, 200, 25);
        add(firstNameField);
        
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 150, 150, 25);
        add(lastNameLabel);
        lastNameField = new JTextField();
        
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 170, 150, 25);
        add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(200, 170, 200, 25);
        add(addressField);
        lastNameField.setBounds(200, 150, 200, 25);
        add(lastNameField);
        
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 190, 150, 25);
        add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(200, 190, 200, 25);
        add(phoneField);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 230, 150, 25);
        add(emailLabel);
        emailField = new JTextField();
        emailField.setBounds(200, 230, 200, 25);
        add(emailField);
        
        JLabel birthDateLabel = new JLabel("Birth Date (yyyy-MM-dd):");
        birthDateLabel.setBounds(50, 270, 200, 25);
        add(birthDateLabel);
        birthDateField = new JTextField();
        birthDateField.setBounds(250, 270, 200, 25);
        add(birthDateField);
        
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 310, 150, 25);
        add(genderLabel);
        genderComboBox = new JComboBox<>(Gender.values());
        genderComboBox.setBounds(200, 310, 200, 25);
        add(genderComboBox);
        
        jobLabel = new JLabel("Job:");
        jobLabel.setBounds(50, 350, 150, 25);
        add(jobLabel);
        jobField = new JTextField();
        jobField.setBounds(200, 350, 200, 25);
        add(jobField);
        jobField.setVisible(false);
        
        addButton = new JButton("Add Case Member");
        addButton.setBounds(200, 500, 200, 30);
        add(addButton);
        
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 550, 400, 25);
        add(messageLabel);
        
        witnessRadio.addActionListener(e -> updateFieldsVisibility());
        accusedRadio.addActionListener(e -> updateFieldsVisibility());
        addButton.addActionListener(e -> addCaseMember());
        
        updateFieldsVisibility();
    }
    
    private void updateFieldsVisibility() {
        jobField.setVisible(accusedRadio.isSelected());
        jobLabel.setVisible(accusedRadio.isSelected());
    }
    
    private boolean validateFields() {
        if (idField.getText().isEmpty()) {
            messageLabel.setText("ID cannot be empty.");
            return false;
        }
        if (firstNameField.getText().isEmpty()) {
            messageLabel.setText("First Name cannot be empty.");
            return false;
        }
        if (lastNameField.getText().isEmpty()) {
            messageLabel.setText("Last Name cannot be empty.");
            return false;
        }
        if (phoneField.getText().isEmpty()) {
            messageLabel.setText("Phone cannot be empty.");
            return false;
        }
        if (emailField.getText().isEmpty()) {
            messageLabel.setText("Email cannot be empty.");
            return false;
        }
        if (birthDateField.getText().isEmpty()) {
            messageLabel.setText("Birth Date cannot be empty.");
            return false;
        }
        if (accusedRadio.isSelected() && jobField.getText().isEmpty()) {
            messageLabel.setText("Job cannot be empty for Accused.");
            return false;
        }
        return true;
    }

    public void fillComboBoxes() {
        genderComboBox.removeAllItems();
        for (Gender gender : Gender.values()) {
            genderComboBox.addItem(gender);
        }
    }

    public void resetFields() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        birthDateField.setText("");
        addressField.setText("");
        jobField.setText("");
        genderComboBox.setSelectedIndex(0);
        witnessRadio.setSelected(false);
        accusedRadio.setSelected(false);
        updateFieldsVisibility();
    }

    private void addCaseMember() {
        if (!validateFields()) {
            return;
        }        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            Gender gender = (Gender) genderComboBox.getSelectedItem();
            Date birthDate = UtilsMethods.parseDate(birthDateField.getText());
            String address = addressField.getText();
            
            if (accusedRadio.isSelected()) {
                String job = jobField.getText();
                Accused accused = new Accused(id, firstName, lastName, birthDate, address, phone, email, gender, job);
                Court.getInstance().addAccused(accused);
            } else if (witnessRadio.isSelected()) {
                Witness witness = new Witness(id, firstName, lastName, birthDate, address, phone, email, gender);
                Court.getInstance().addWitness(witness);
            }
            messageLabel.setText("Case member added successfully!");
            resetFields();
        } catch (Exception ex) {
            messageLabel.setText("Invalid input. Check fields.");
        }
    }
}
