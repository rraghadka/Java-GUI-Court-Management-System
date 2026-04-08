package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

import control.Court;
import enums.Gender;
import enums.Specialization;
import enums.Status;
import model.Accused;
import model.CriminalCase;
import model.FamilyCase;
import model.FinancialCase;
import model.Judge;
import model.Lawyer;
import model.Person;
import utils.UtilsMethods;

public class CaseAddPanel extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Dropdown for case type
    private JComboBox<String> caseTypeComboBox;

    // Dropdowns for Accused / Lawyer
    private JComboBox<Integer> accusedComboBox;
    private JComboBox<Integer> lawyerComboBox;

    // Common fields
    private JTextField openedDateField;
    private JComboBox<Status> statusComboBox;
    private JComboBox<Specialization> specializationComboBox;

    // Criminal & Family fields (both have a victim)
    private JTextField victimNameField;

    // CriminalCase only
    private JTextField crimeSceneField;
    private JTextField crimeToolField;

    // FamilyCase only
    private JTextField relationTypeField;

    // FinancialCase only
    private JTextField lossesAmountField;
    private JTextField damagedItemField;

    private JButton addButton;
    private JLabel messageLabel;
    private JLabel victimNameLabel;
    private JLabel crimeSceneLabel;
    private JLabel crimeToolLabel;
    private JLabel relationTypeLabel;
    private JLabel lossesAmountLabel;
    private JLabel damagedItemLabel;


    public CaseAddPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // ---------------------
        // Case Type
        // ---------------------
        JLabel caseTypeLabel = new JLabel("Case Type:");
        caseTypeLabel.setBounds(50, 30, 150, 25);
        add(caseTypeLabel);

        caseTypeComboBox = new JComboBox<>(new String[] {"Criminal", "Family", "Financial"});
        caseTypeComboBox.setBounds(200, 30, 200, 25);
        add(caseTypeComboBox);

        // ---------------------
        // Accused (Dropdown)
        // ---------------------
        JLabel accusedLabel = new JLabel("Select Accused:");
        accusedLabel.setBounds(50, 70, 150, 25);
        add(accusedLabel);

        accusedComboBox = new JComboBox<>();
        accusedComboBox.setBounds(200, 70, 250, 25);
        add(accusedComboBox);

        // Fill accused combo from Court
        for (Accused a : Court.getInstance().getAllAccuseds().values()) {
            accusedComboBox.addItem(a.getId());
        }

        // ---------------------
        // Lawyer (Dropdown)
        // ---------------------
        JLabel lawyerLabel = new JLabel("Select Lawyer:");
        lawyerLabel.setBounds(50, 110, 150, 25);
        add(lawyerLabel);

        lawyerComboBox = new JComboBox<>();
        lawyerComboBox.setBounds(200, 110, 250, 25);
        add(lawyerComboBox);

        // Fill lawyer combo from Court
        for (Lawyer l : Court.getInstance().getAllLawyers().values()) {
            lawyerComboBox.addItem(l.getId());
        }

        // ---------------------
        // Common fields
        // ---------------------
        JLabel openedDateLabel = new JLabel("Opened Date (yyyy-MM-dd):");
        openedDateLabel.setBounds(50, 150, 200, 25);
        add(openedDateLabel);

        openedDateField = new JTextField();
        openedDateField.setBounds(250, 150, 200, 25);
        add(openedDateField);

        JLabel statusLabel = new JLabel("Case Status:");
        statusLabel.setBounds(50, 190, 150, 25);
        add(statusLabel);

        statusComboBox = new JComboBox<>(Status.values());
        statusComboBox.setBounds(200, 190, 200, 25);
        add(statusComboBox);

        JLabel specializationLabel = new JLabel("Specialization:");
        specializationLabel.setBounds(50, 230, 150, 25);
        add(specializationLabel);

        specializationComboBox = new JComboBox<>(Specialization.values());
        specializationComboBox.setBounds(200, 230, 200, 25);
        add(specializationComboBox);

        // ---------------------
        // Criminal & Family: victim name
        // ---------------------
        victimNameLabel = new JLabel("Victim Name:");
        victimNameLabel.setBounds(50, 310, 150, 25);
        add(victimNameLabel);

        victimNameField = new JTextField();
        victimNameField.setBounds(200, 310, 200, 25);
        add(victimNameField);

        // ---------------------
        // CriminalCase fields
        // ---------------------
        crimeSceneLabel = new JLabel("Crime Scene:");
        crimeSceneLabel.setBounds(50, 350, 150, 25);
        add(crimeSceneLabel);

        crimeSceneField = new JTextField();
        crimeSceneField.setBounds(200, 350, 200, 25);
        add(crimeSceneField);

        crimeToolLabel = new JLabel("Crime Tool:");
        crimeToolLabel.setBounds(50, 390, 150, 25);
        add(crimeToolLabel);

        crimeToolField = new JTextField();
        crimeToolField.setBounds(200, 390, 200, 25);
        add(crimeToolField);

        // ---------------------
        // FamilyCase fields
        // ---------------------
        relationTypeLabel = new JLabel("Relation Type:");
        relationTypeLabel.setBounds(50, 430, 150, 25);
        add(relationTypeLabel);

        relationTypeField = new JTextField();
        relationTypeField.setBounds(200, 430, 200, 25);
        add(relationTypeField);

        // ---------------------
        // FinancialCase fields
        // ---------------------
        lossesAmountLabel = new JLabel("Losses Amount:");
        lossesAmountLabel.setBounds(50, 470, 150, 25);
        add(lossesAmountLabel);

        lossesAmountField = new JTextField();
        lossesAmountField.setBounds(200, 470, 200, 25);
        add(lossesAmountField);

        damagedItemLabel = new JLabel("Damaged Item:");
        damagedItemLabel.setBounds(50, 510, 150, 25);
        add(damagedItemLabel);

        damagedItemField = new JTextField();
        damagedItemField.setBounds(200, 510, 200, 25);
        add(damagedItemField);

        // ---------------------
        // Button & Message
        // ---------------------
        addButton = new JButton("Add Case");
        addButton.setBounds(200, 560, 150, 30);
        add(addButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setBounds(50, 610, 600, 25);
        add(messageLabel);
        

        // Update fields on type changes
        caseTypeComboBox.addActionListener(this::updateFieldsVisibility);

        // Attempt to add the case on button click
        addButton.addActionListener(e -> addCase());

        // Set initial visibility for specialized fields
        updateFieldsVisibility(null);
    }

    private void updateFieldsVisibility(ActionEvent e) {
        String caseType = (String) caseTypeComboBox.getSelectedItem();

        boolean isCriminal   = "Criminal".equals(caseType);
        boolean isFamily     = "Family".equals(caseType);
        boolean isFinancial  = "Financial".equals(caseType);

        // For Criminal & Family
        victimNameField.setVisible(isCriminal || isFamily);

        // Criminal only
        crimeSceneField.setVisible(isCriminal);
        crimeToolField.setVisible(isCriminal);

        // Family only
        relationTypeField.setVisible(isFamily);

        // Financial only
        lossesAmountField.setVisible(isFinancial);
        damagedItemField.setVisible(isFinancial);
        
     // For Criminal & Family
        victimNameLabel.setVisible(isCriminal || isFamily);

        // Criminal only
        crimeSceneLabel.setVisible(isCriminal);
        crimeToolLabel.setVisible(isCriminal);

        // Family only
        relationTypeLabel.setVisible(isFamily);

        // Financial only
        lossesAmountLabel.setVisible(isFinancial);
        damagedItemLabel.setVisible(isFinancial);
    }

    private boolean validateFields() {
        if (accusedComboBox.getSelectedItem() == null) {
            messageLabel.setText("Accused must be selected.");
            return false;
        }
        if (lawyerComboBox.getSelectedItem() == null) {
            messageLabel.setText("Lawyer must be selected.");
            return false;
        }
        if (openedDateField.getText().isEmpty()) {
            messageLabel.setText("Opened Date cannot be empty.");
            return false;
        }
        if (statusComboBox.getSelectedItem() == null) {
            messageLabel.setText("Case Status must be selected.");
            return false;
        }
        if (specializationComboBox.getSelectedItem() == null) {
            messageLabel.setText("Specialization must be selected.");
            return false;
        }
        String caseType = (String) caseTypeComboBox.getSelectedItem();
        if ("Criminal".equals(caseType)) {
            if (victimNameField.getText().isEmpty()) {
                messageLabel.setText("Victim Name cannot be empty.");
                return false;
            }
            if (crimeSceneField.getText().isEmpty()) {
                messageLabel.setText("Crime Scene cannot be empty.");
                return false;
            }
            if (crimeToolField.getText().isEmpty()) {
                messageLabel.setText("Crime Tool cannot be empty.");
                return false;
            }
        } else if ("Family".equals(caseType)) {
            if (victimNameField.getText().isEmpty()) {
                messageLabel.setText("Victim Name cannot be empty.");
                return false;
            }
            if (relationTypeField.getText().isEmpty()) {
                messageLabel.setText("Relation Type cannot be empty.");
                return false;
            }
        } else if ("Financial".equals(caseType)) {
            if (lossesAmountField.getText().isEmpty()) {
                messageLabel.setText("Losses Amount cannot be empty.");
                return false;
            }
            if (damagedItemField.getText().isEmpty()) {
                messageLabel.setText("Damaged Item cannot be empty.");
                return false;
            }
        }
        return true;
    }

    public void fillComboBoxes() {
        accusedComboBox.removeAllItems();
        Court.getInstance().getAllAccuseds().values().stream().forEach(a -> accusedComboBox.addItem(a.getId()));

        lawyerComboBox.removeAllItems();
        Court.getInstance().getAllLawyers().values().stream()
        .filter(l -> !(l instanceof Judge)) // Exclude judges
        .forEach(l -> lawyerComboBox.addItem(l.getId())); 
    }

    public void resetFields() {
    	fillComboBoxes();
        caseTypeComboBox.setSelectedIndex(0);
        accusedComboBox.setSelectedIndex(-1);
        lawyerComboBox.setSelectedIndex(-1);
        openedDateField.setText("");
        statusComboBox.setSelectedIndex(0);
        specializationComboBox.setSelectedIndex(0);
        victimNameField.setText("");
        crimeSceneField.setText("");
        crimeToolField.setText("");
        relationTypeField.setText("");
        lossesAmountField.setText("");
        damagedItemField.setText("");
    }

    private void addCase() {
        if (!validateFields()) {
            return;
        }        try {
            // Retrieve selected Accused & Lawyer from combos
            Accused accused = (Accused) Court.getInstance().getAllAccuseds().get(accusedComboBox.getSelectedItem());
            Lawyer lawyer   = (Lawyer) Court.getInstance().getAllLawyers().get(lawyerComboBox.getSelectedItem());

            if (accused == null || lawyer == null) {
                messageLabel.setText("Accused or Lawyer not selected.");
                return;
            }

            // Common fields
            Date openedDate = UtilsMethods.parseDate(openedDateField.getText());
            Status caseStatus = (Status) statusComboBox.getSelectedItem();
            Specialization caseSpec = (Specialization) specializationComboBox.getSelectedItem();
            // Decide which case to build
            String caseType = (String) caseTypeComboBox.getSelectedItem();

            if ("Criminal".equals(caseType)) {
                // Additional fields: victimName, crimeScene, crimeTool
                String victimName = victimNameField.getText();
                String crimeScene = crimeSceneField.getText();
                String crimeTool  = crimeToolField.getText();

                // Minimal new Person for the victim. Adjust if you want to pick an existing Person.
                Person victim = new Person(
                    0,
                    victimName,
                    "N/A",
                    new Date(),
                    "N/A",
                    "N/A",
                    "N/A",
                    Gender.M
                );

                CriminalCase criminalCase = new CriminalCase(
                    accused,
                    openedDate,
                    caseStatus,
                    caseSpec,
                    lawyer,
                    victim,
                    crimeScene,
                    crimeTool
                );
                Court.getInstance().addCase(criminalCase); // Must match your Court method

            } else if ("Family".equals(caseType)) {
                // Additional fields: victimName, relationType
                String victimName    = victimNameField.getText();
                String relationType  = relationTypeField.getText();

                Person victim = new Person(
                    0,
                    victimName,
                    "N/A",
                    new Date(),
                    "N/A",
                    "N/A",
                    "N/A",
                    Gender.M
                );

                FamilyCase familyCase = new FamilyCase(
                    accused,
                    openedDate,
                    caseStatus,
                    caseSpec,
                    lawyer,
                    victim,
                    relationType
                );
                Court.getInstance().addCase(familyCase);

            } else if ("Financial".equals(caseType)) {
                // Additional fields: lossesAmount, damagedItem
                double losses = Double.parseDouble(lossesAmountField.getText());
                String damagedItem = damagedItemField.getText();

                FinancialCase financialCase = new FinancialCase(
                    accused,
                    openedDate,
                    caseStatus,
                    caseSpec,
                    lawyer,
                    losses,
                    damagedItem
                );
                Court.getInstance().addCase(financialCase);
            }

            messageLabel.setText("Case added successfully!");
            resetFields();
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setText("Invalid input. Check fields.");
        }
    }
}
