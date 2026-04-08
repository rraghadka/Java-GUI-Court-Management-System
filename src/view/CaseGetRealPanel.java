package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import control.Court;
import model.Case;
import model.CriminalCase;
import model.FamilyCase;
import model.FinancialCase;

public class CaseGetRealPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> typeComboBox;   // "All", "Criminal", "Family", "Financial"
    private JComboBox<String> caseCodeComboBox; // Will hold the case codes (Strings)
    private JTextArea detailsArea;

    public CaseGetRealPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(1200, 900));

        // First dropdown: choose case type
        JLabel typeLabel = new JLabel("Case Type:");
        typeLabel.setBounds(50, 30, 100, 25);
        add(typeLabel);

        typeComboBox = new JComboBox<>(new String[] { "All", "Criminal", "Family", "Financial" });
        typeComboBox.setBounds(160, 30, 150, 25);
        add(typeComboBox);

        // Second dropdown: choose case code
        JLabel codeLabel = new JLabel("Select Case Code:");
        codeLabel.setBounds(50, 70, 150, 25);
        add(codeLabel);

        caseCodeComboBox = new JComboBox<>();
        caseCodeComboBox.setBounds(160, 70, 150, 25);
        add(caseCodeComboBox);

        // Text area to show details
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);  // Enables line wrapping
        detailsArea.setWrapStyleWord(true); // Wraps text at word boundaries
        detailsArea.setBounds(50, 110, 600, 300);
        add(detailsArea);

        // Populate the second dropdown initially
        refreshCaseCodes();

        // If the user changes the type, refresh the codes
        typeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    refreshCaseCodes();
                }
            }
        });
        caseCodeComboBox.setSelectedIndex(-1);


        // If the user picks a case code, display its details automatically
        caseCodeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedCode = (String) caseCodeComboBox.getSelectedItem();
                    if (selectedCode != null) {
                        Case theCase = Court.getInstance().getRealCase(selectedCode);
                        if (theCase != null) {
                            detailsArea.setText(theCase.toString());
                        } else {
                            detailsArea.setText("Case not found.");
                        }
                    } else {
                        detailsArea.setText("");
                    }
                }
            }
        });
    }
    
    
    public void resetFields() {
    	refreshCaseCodes();
        typeComboBox.setSelectedIndex(0);
        caseCodeComboBox.setSelectedIndex(-1);
        detailsArea.setText("");
    }

    // Refreshes the caseCodeComboBox based on the type selected in typeComboBox
    private void refreshCaseCodes() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        caseCodeComboBox.removeAllItems();

        Collection<Case> allCases = Court.getInstance().getAllCases().values();

        for (Case c : allCases) {
            if ("All".equals(selectedType)) {
                caseCodeComboBox.addItem(c.getCode());
            } else if ("Criminal".equals(selectedType) && c instanceof CriminalCase) {
                caseCodeComboBox.addItem(c.getCode());
            } else if ("Family".equals(selectedType) && c instanceof FamilyCase) {
                caseCodeComboBox.addItem(c.getCode());
            } else if ("Financial".equals(selectedType) && c instanceof FinancialCase) {
                caseCodeComboBox.addItem(c.getCode());
            }
        }

        // Clear the details area whenever we refresh the codes
        detailsArea.setText("");
    }
}