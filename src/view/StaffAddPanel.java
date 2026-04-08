package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import control.Court;
import enums.Gender;
import enums.Position;
import enums.Specialization;
import model.Employee;
import model.Judge;
import model.Lawyer;
import utils.UtilsMethods;
import java.util.Date;

public class StaffAddPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> roleComboBox;
    private JTextField idField, firstNameField, lastNameField, phoneField, emailField, birthDateField;
    private JComboBox<Gender> genderComboBox;
    private JTextField salaryField, licenseNumberField, experienceYearField;
    private JComboBox<Position> positionComboBox;
    private JComboBox<Specialization> specializationComboBox;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton addButton;
    private JLabel messageLabel;
    private JLabel salaryLabel, licenseNumberLabel, experienceYearLabel, positionLabel,addressLabel, specializationLabel;
	JTextField addressField;

    public StaffAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setBounds(50, 30, 150, 25);
        add(roleLabel);

        roleComboBox = new JComboBox<>(new String[]{"Employee", "Lawyer", "Judge"});
        roleComboBox.setBounds(200, 30, 200, 25);
        add(roleComboBox);

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
        birthDateField.setBounds(200, 270, 200, 25);
        add(birthDateField);
        
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 310, 150, 25);
        add(genderLabel);

        genderComboBox = new JComboBox<>(Gender.values());
        genderComboBox.setBounds(200, 310, 200, 25);
        add(genderComboBox);
        
        positionLabel = new JLabel("Positiion:");
        positionLabel.setBounds(50, 390, 150, 25);
        add(positionLabel);

        positionComboBox = new JComboBox<>(Position.values());
        positionComboBox.setBounds(200, 390, 200, 25);
        add(positionComboBox);
        
        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 350, 150, 25);
        add(addressLabel);
        addressField = new JTextField();
        addressField.setBounds(200, 350, 200, 25);
        add(addressField);

        salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(410, 70, 150, 25);
        add(salaryLabel);
        salaryField = new JTextField();
        salaryField.setBounds(560, 70, 200, 25);
        add(salaryField);

        licenseNumberLabel = new JLabel("License Number:");
        licenseNumberLabel.setBounds(410, 110, 150, 25);
        add(licenseNumberLabel);
        licenseNumberField = new JTextField();
        licenseNumberField.setBounds(560, 110, 200, 25);
        add(licenseNumberField);
        
        specializationLabel = new JLabel("Specialization:");
        specializationLabel.setBounds(410, 150, 150, 25);
        add(specializationLabel);

        specializationComboBox = new JComboBox<>(Specialization.values());
        specializationComboBox.setBounds(560, 150, 200, 25);
        add(specializationComboBox);

        experienceYearLabel = new JLabel("Experience Years:");
        experienceYearLabel.setBounds(410, 190, 150, 25);
        add(experienceYearLabel);
        experienceYearField = new JTextField();
        experienceYearField.setBounds(560, 190, 200, 25);
        add(experienceYearField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 470, 150, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 470, 200, 25);
        add(passwordField);

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds(410, 470, 150, 25);
        add(showPasswordCheckBox);

        showPasswordCheckBox.addActionListener((ActionEvent e) -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        licenseNumberField.setVisible(false);
        experienceYearField.setVisible(false);

        addButton = new JButton("Add Staff");
        addButton.setBounds(200, 510, 150, 30);
        add(addButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 550, 350, 25);
        add(messageLabel);
        
        

        roleComboBox.addActionListener(e -> updateFieldsVisibility());
        addButton.addActionListener(e -> addStaff());

        updateFieldsVisibility();
    }

    private void resetFields() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        birthDateField.setText("");
        addressField.setText("");
        salaryField.setText("");
        licenseNumberField.setText("");
        experienceYearField.setText("");
        passwordField.setText("");
        genderComboBox.setSelectedIndex(0);
        positionComboBox.setSelectedIndex(0);
        specializationComboBox.setSelectedIndex(0);
        roleComboBox.setSelectedIndex(0);
    }
    
    private boolean validateFields() {
        String role = (String) roleComboBox.getSelectedItem();
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
        if (addressField.getText().isEmpty()) {
            messageLabel.setText("Address cannot be empty.");
            return false;
        }
        if (passwordField.getPassword().length == 0) {
            messageLabel.setText("Password cannot be empty.");
            return false;
        }
        if (salaryField.getText().isEmpty()) {
            messageLabel.setText("Salary cannot be empty.");
            return false;
        }
        if (("Lawyer".equals(role) || "Judge".equals(role)) && licenseNumberField.getText().isEmpty()) {
            messageLabel.setText("License Number cannot be empty.");
            return false;
        }
        if ("Judge".equals(role) && experienceYearField.getText().isEmpty()) {
            messageLabel.setText("Experience Years cannot be empty for Judge.");
            return false;
        }
        return true;
    }
    
    private void updateFieldsVisibility() {
        if (salaryField == null || licenseNumberField == null || experienceYearField == null) {
            return;
        }

        String role = (String) roleComboBox.getSelectedItem();
        boolean isEmployee = "Employee".equals(role);
        boolean isLawyer = "Lawyer".equals(role);
        boolean isJudge = "Judge".equals(role);

        licenseNumberField.setVisible(isLawyer || isJudge);
        experienceYearField.setVisible(isJudge);
        positionComboBox.setVisible(isEmployee);
        specializationComboBox.setVisible(!isEmployee);

        licenseNumberLabel.setVisible(isLawyer || isJudge);
        experienceYearLabel.setVisible(isJudge);
        positionLabel.setVisible(isEmployee);
        specializationLabel.setVisible(!isEmployee);



    }

    private void addStaff() {
    	if (!validateFields()) {
            return;
        }
        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            Gender gender = (Gender) genderComboBox.getSelectedItem();
            Position position = (Position) positionComboBox.getSelectedItem();
            Date birthDate = UtilsMethods.parseDate(birthDateField.getText());
            String password = new String(passwordField.getPassword());
            String address = new String(addressField.getText());
            Specialization specialization = (Specialization) specializationComboBox.getSelectedItem();
            String role = (String) roleComboBox.getSelectedItem();
            
            
            
            if ("Employee".equals(role)) {
            	
            	System.out.println("2");
                double salary = Double.parseDouble(salaryField.getText());
                Employee employee = new Employee(id, firstName, lastName, birthDate, address, phone, email, gender, new Date(), salary, position, password);
                Court.getInstance().addEmployee(employee);
            } else if ("Lawyer".equals(role)) {

                int licenseNumber = Integer.parseInt(licenseNumberField.getText());
                double salary = Double.parseDouble(salaryField.getText());
                Lawyer lawyer = new Lawyer(id, firstName, lastName, birthDate, address, phone, email, gender, specialization, licenseNumber, salary, password);
                Court.getInstance().addLawyer(lawyer);
                
            } else if ("Judge".equals(role)) {
            	
                int licenseNumber = Integer.parseInt(licenseNumberField.getText());
                int experienceYear = Integer.parseInt(experienceYearField.getText());
                double salary = Double.parseDouble(salaryField.getText());
                Judge judge = new Judge(id, firstName, lastName, birthDate, address, phone, email, gender, specialization, licenseNumber, salary, password, experienceYear);
                Court.getInstance().addJudge(judge);
                
            }
            resetFields();
            messageLabel.setText("Staff added successfully!");
        } catch (Exception ex) {
            messageLabel.setText("Invalid input!");
        }
    }
    
    
    
}