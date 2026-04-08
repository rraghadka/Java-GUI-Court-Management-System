package view;

import javax.swing.*;

import control.Court;
import model.Case;
import model.Department;
import model.Employee;
import model.Lawyer;
import utils.SerializationUtils;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel panelContainer;
    private JPanel mainPanel;
    
    private DepartmentAddPanel departmentAddPanel;
    private StaffAddPanel staffAddPanel;
    private CaseMembersAddPanel caseMembersAddPanel;
    private CaseAddPanel caseAddPanel;
    private VerdictAddPanel verdictAddPanel;
    private TestimonyAddPanel testimonyAddPanel;
    private MeetingAddPanel meetingAddPanel;
    private DocumentAddPanel documentAddPanel;
    private CourtroomAddPanel courtroomAddPanel;
    private AppealAddPanel appealAddPanel;
    private DepartmentRemovePanel departmentRemovePanel;
    private CourtroomRemovePanel courtroomRemovePanel;
    private LawyerRemovePanel lawyerRemovePanel;
    private CaseRemovePanel caseRemovePanel;
    private JudgeRemovePanel judgeRemovePanel;
    private EmployeeRemovePanel employeeRemovePanel;
    private DocumentRemovePanel documentRemovePanel;
    private VerdictRemovePanel verdictRemovePanel;
    private TestimonyRemovePanel testimonyRemovePanel;
    private AppealRemovePanel appealRemovePanel;
    private WitnessRemovePanel witnessRemovePanel;
    private AccusedRemovePanel accusedRemovePanel;
    private MeetingRemovePanel meetingRemovePanel;
    private DepartmentGetRealPanel departmentGetRealPanel;
    private CourtroomGetRealPanel courtroomGetRealPanel;
    private LawyerGetRealPanel lawyerGetRealPanel;
    private JudgeGetRealPanel judgeGetRealPanel;
    private AccusedGetRealPanel accusedGetRealPanel;
    private EmployeeGetRealPanel employeeGetRealPanel;
    private DocumentGetRealPanel documentGetRealPanel;
    private VerdictGetRealPanel verdictGetRealPanel;
    private TestimonyGetRealPanel testimonyGetRealPanel;
    private WitnessGetRealPanel witnessGetRealPanel;
    private AppealGetRealPanel appealGetRealPanel;
    private MeetingGetRealPanel meetingGetRealPanel;
    private CaseGetRealPanel caseGetRealPanel;
    private InactiveCasesByDepartmentPanel inactiveCasesByDepartmentPanel;
    private FamilyCasesWithWitnessesPanel familyCasesWithWitnessesPanel;
    private CasesWithThreeSameGenderTestimoniesPanel casesWithThreeSameGenderTestimoniesPanel;
    private UniqueCrimeToolsPanel uniqueCrimeToolsPanel;
    private AddToDepartmentPanel addToDepartmentPanel;
    
private JButton logoutButton;

    public MainFrame(String user) {
        setTitle("Court Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        // Creating the menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu addMenu = new JMenu("Add");
        JMenu removeMenu = new JMenu("Remove");
        JMenu getRealMenu = new JMenu("Get Real");
        JMenu queryMenu = new JMenu("Queries");
        JMenu main = new JMenu("Main");

        
        
        menuBar.add(main);
        menuBar.add(addMenu);
        menuBar.add(removeMenu);
        menuBar.add(getRealMenu);
        menuBar.add(queryMenu);
        
        // Adding panels to the menu
        main.add(createMenuItem("Home", "main"));
        
        
        
        addMenu.add(createMenuItem("Add Department", "DepartmentAdd"));
        addMenu.add(createMenuItem("Add Staff", "StaffAdd"));
        addMenu.add(createMenuItem("Add Case Member", "CaseMemberAdd"));
        addMenu.add(createMenuItem("Add Case", "CaseAdd"));
        addMenu.add(createMenuItem("Add Verdict", "VerdictAdd"));
        addMenu.add(createMenuItem("Add Testimony", "TestimonyAdd"));
        addMenu.add(createMenuItem("Add Meeting", "MeetingAdd"));
        addMenu.add(createMenuItem("Add Document", "DocumentAdd"));
        addMenu.add(createMenuItem("Add Courtroom", "CourtroomAdd"));
        addMenu.add(createMenuItem("Add Appeal", "AppealAdd"));
        addMenu.add(createMenuItem("Add To Department", "PersonAdd"));

        
        removeMenu.add(createMenuItem("Remove Department", "DepartmentRemove"));
        removeMenu.add(createMenuItem("Remove Courtroom", "CourtroomRemove"));
        removeMenu.add(createMenuItem("Remove Lawyer", "LawyerRemove"));
        removeMenu.add(createMenuItem("Remove Case", "CaseRemove"));
        removeMenu.add(createMenuItem("Remove Judge", "JudgeRemove"));
        removeMenu.add(createMenuItem("Remove Employee", "EmployeeRemove"));
        removeMenu.add(createMenuItem("Remove Document", "DocumentRemove"));
        removeMenu.add(createMenuItem("Remove Verdict", "VerdictRemove"));
        removeMenu.add(createMenuItem("Remove Testimony", "TestimonyRemove"));
        removeMenu.add(createMenuItem("Remove Appeal", "AppealRemove"));
        removeMenu.add(createMenuItem("Remove Witness", "WitnessRemove"));
        removeMenu.add(createMenuItem("Remove Accused", "AccusedRemove"));
        removeMenu.add(createMenuItem("Remove Meeting", "MeetingRemove"));
        
        getRealMenu.add(createMenuItem("Get Real Department", "DepartmentGetReal"));
        getRealMenu.add(createMenuItem("Get Real Courtroom", "CourtroomGetReal"));
        getRealMenu.add(createMenuItem("Get Real Lawyer", "LawyerGetReal"));
        getRealMenu.add(createMenuItem("Get Real Judge", "JudgeGetReal"));
        getRealMenu.add(createMenuItem("Get Real Accused", "AccusedGetReal"));
        getRealMenu.add(createMenuItem("Get Real Employee", "EmployeeGetReal"));
        getRealMenu.add(createMenuItem("Get Real Document", "DocumentGetReal"));
        getRealMenu.add(createMenuItem("Get Real Verdict", "VerdictGetReal"));
        getRealMenu.add(createMenuItem("Get Real Testimony", "TestimonyGetReal"));
        getRealMenu.add(createMenuItem("Get Real Witness", "WitnessGetReal"));
        getRealMenu.add(createMenuItem("Get Real Appeal", "AppealGetReal"));
        getRealMenu.add(createMenuItem("Get Real Meeting", "MeetingGetReal"));
        getRealMenu.add(createMenuItem("Get Real Case", "CaseGetReal"));
        
        
        JMenuItem casesBeforeDateItem = new JMenuItem("Cases Before Date");
        casesBeforeDateItem.addActionListener(e -> showCasesBeforeDatePopup());
        queryMenu.add(casesBeforeDateItem);
        
        JMenuItem inactiveCasesItem = new JMenuItem("Inactive Cases by Department");
        inactiveCasesItem.addActionListener(e -> showPanel("InactiveCasesByDepartment"));
        queryMenu.add(inactiveCasesItem);
        
        JMenuItem suitableLawyerItem = new JMenuItem("Find Suitable Lawyer");
        suitableLawyerItem.addActionListener(e -> showSuitableLawyerPopup());
        queryMenu.add(suitableLawyerItem);
        
        JMenuItem appointManagerItem = new JMenuItem("Appoint New Manager");
        appointManagerItem.addActionListener(e -> showAppointManagerPopup());
        queryMenu.add(appointManagerItem);
        
        JMenuItem caseDifferenceItem = new JMenuItem("Case Length Difference");
        caseDifferenceItem.addActionListener(e -> showCaseDifferencePopup());
        queryMenu.add(caseDifferenceItem);
        
        JMenuItem familyCasesItem = new JMenuItem("Family Cases With Witnesses");
        familyCasesItem.addActionListener(e -> showPanel("FamilyCasesWithWitnesses"));
        queryMenu.add(familyCasesItem);
        
        JMenuItem casesItem = new JMenuItem("Cases With 3+ Same-Gender Testimonies");
        casesItem.addActionListener(e -> showPanel("CasesWithThreeSameGenderTestimonies"));
        queryMenu.add(casesItem);
        
        JMenuItem crimeToolsItem = new JMenuItem("Find Unique Crime Tools");
        crimeToolsItem.addActionListener(e -> showPanel("UniqueCrimeTools"));
        queryMenu.add(crimeToolsItem);
        
        
        logoutButton = new JButton("LOGOUT");
        logoutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoginFrame loginFrame = new LoginFrame();
        		loginFrame.setVisible(true);
        		dispose();
        	}
        });
        main.add(logoutButton);
        
       
        
        
     // Initialize all panels
        mainPanel = new JPanel();
        departmentAddPanel = new DepartmentAddPanel();
        staffAddPanel = new StaffAddPanel();
        caseMembersAddPanel = new CaseMembersAddPanel();
        caseAddPanel = new CaseAddPanel();
        verdictAddPanel = new VerdictAddPanel();
        testimonyAddPanel = new TestimonyAddPanel();
        meetingAddPanel = new MeetingAddPanel();
        documentAddPanel = new DocumentAddPanel();
        courtroomAddPanel = new CourtroomAddPanel();
        appealAddPanel = new AppealAddPanel();
        departmentRemovePanel = new DepartmentRemovePanel();
        courtroomRemovePanel = new CourtroomRemovePanel();
        lawyerRemovePanel = new LawyerRemovePanel();
        caseRemovePanel = new CaseRemovePanel();
        judgeRemovePanel = new JudgeRemovePanel();
        employeeRemovePanel = new EmployeeRemovePanel();
        documentRemovePanel = new DocumentRemovePanel();
        verdictRemovePanel = new VerdictRemovePanel();
        testimonyRemovePanel = new TestimonyRemovePanel();
        appealRemovePanel = new AppealRemovePanel();
        witnessRemovePanel = new WitnessRemovePanel();
        accusedRemovePanel = new AccusedRemovePanel();
        meetingRemovePanel = new MeetingRemovePanel();
        departmentGetRealPanel = new DepartmentGetRealPanel();
        courtroomGetRealPanel = new CourtroomGetRealPanel();
        lawyerGetRealPanel = new LawyerGetRealPanel();
        judgeGetRealPanel = new JudgeGetRealPanel();
        accusedGetRealPanel = new AccusedGetRealPanel();
        employeeGetRealPanel = new EmployeeGetRealPanel();
        documentGetRealPanel = new DocumentGetRealPanel();
        verdictGetRealPanel = new VerdictGetRealPanel();
        testimonyGetRealPanel = new TestimonyGetRealPanel();
        witnessGetRealPanel = new WitnessGetRealPanel();
        appealGetRealPanel = new AppealGetRealPanel();
        meetingGetRealPanel = new MeetingGetRealPanel();
        caseGetRealPanel = new CaseGetRealPanel();
        inactiveCasesByDepartmentPanel = new InactiveCasesByDepartmentPanel();
        familyCasesWithWitnessesPanel = new FamilyCasesWithWitnessesPanel();
        casesWithThreeSameGenderTestimoniesPanel = new CasesWithThreeSameGenderTestimoniesPanel();
        uniqueCrimeToolsPanel = new UniqueCrimeToolsPanel();
        addToDepartmentPanel = new AddToDepartmentPanel();
        
     // Add all initialized panels to card layout
        panelContainer.add(mainPanel, "main");
        panelContainer.add(departmentAddPanel, "DepartmentAdd");
        panelContainer.add(staffAddPanel, "StaffAdd");
        panelContainer.add(caseMembersAddPanel, "CaseMemberAdd");
        panelContainer.add(caseAddPanel, "CaseAdd");
        panelContainer.add(verdictAddPanel, "VerdictAdd");
        panelContainer.add(testimonyAddPanel, "TestimonyAdd");
        panelContainer.add(meetingAddPanel, "MeetingAdd");
        panelContainer.add(documentAddPanel, "DocumentAdd");
        panelContainer.add(courtroomAddPanel, "CourtroomAdd");
        panelContainer.add(appealAddPanel, "AppealAdd");
        panelContainer.add(departmentRemovePanel, "DepartmentRemove");
        panelContainer.add(courtroomRemovePanel, "CourtroomRemove");
        panelContainer.add(lawyerRemovePanel, "LawyerRemove");
        panelContainer.add(caseRemovePanel, "CaseRemove");
        panelContainer.add(judgeRemovePanel, "JudgeRemove");
        panelContainer.add(employeeRemovePanel, "EmployeeRemove");
        panelContainer.add(documentRemovePanel, "DocumentRemove");
        panelContainer.add(verdictRemovePanel, "VerdictRemove");
        panelContainer.add(testimonyRemovePanel, "TestimonyRemove");
        panelContainer.add(appealRemovePanel, "AppealRemove");
        panelContainer.add(witnessRemovePanel, "WitnessRemove");
        panelContainer.add(accusedRemovePanel, "AccusedRemove");
        panelContainer.add(meetingRemovePanel, "MeetingRemove");
        panelContainer.add(departmentGetRealPanel, "DepartmentGetReal");
        panelContainer.add(courtroomGetRealPanel, "CourtroomGetReal");
        panelContainer.add(lawyerGetRealPanel, "LawyerGetReal");
        panelContainer.add(judgeGetRealPanel, "JudgeGetReal");
        panelContainer.add(accusedGetRealPanel, "AccusedGetReal");
        panelContainer.add(employeeGetRealPanel, "EmployeeGetReal");
        panelContainer.add(documentGetRealPanel, "DocumentGetReal");
        panelContainer.add(verdictGetRealPanel, "VerdictGetReal");
        panelContainer.add(testimonyGetRealPanel, "TestimonyGetReal");
        panelContainer.add(witnessGetRealPanel, "WitnessGetReal");
        panelContainer.add(appealGetRealPanel, "AppealGetReal");
        panelContainer.add(meetingGetRealPanel, "MeetingGetReal");
        panelContainer.add(caseGetRealPanel, "CaseGetReal");
        panelContainer.add(inactiveCasesByDepartmentPanel, "InactiveCasesByDepartment");
        panelContainer.add(familyCasesWithWitnessesPanel, "FamilyCasesWithWitnesses");
        panelContainer.add(casesWithThreeSameGenderTestimoniesPanel, "CasesWithThreeSameGenderTestimonies");
        panelContainer.add(uniqueCrimeToolsPanel, "UniqueCrimeTools");
        panelContainer.add(addToDepartmentPanel, "PersonAdd");	


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Serialize the hospital data before exiting
            	SerializationUtils.saveCourt();
                System.exit(0);
            }
        });

        
        getContentPane().add(panelContainer);
    }
    
    private JMenuItem createMenuItem(String name, String panelName) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> showPanel(panelName));
        return item;
    }
    
    private void showCasesBeforeDatePopup() {
        String dateString = JOptionPane.showInputDialog(this, "Enter date (yyyy-MM-dd):", "Cases Before Date", JOptionPane.PLAIN_MESSAGE);
        if (dateString != null && !dateString.isEmpty()) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                int count = Court.getInstance().howManyCasesBefore(date);
                JOptionPane.showMessageDialog(this, "Total cases before " + dateString + ": " + count, "Cases Before Date", JOptionPane.INFORMATION_MESSAGE);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please enter yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showSuitableLawyerPopup() {
        Map<String, Case> cases = Court.getInstance().getAllCases();
        if (cases.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No cases available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<String> caseDropdown = new JComboBox<>(cases.keySet().toArray(new String[0]));
        int result = JOptionPane.showConfirmDialog(this, caseDropdown, "Select a Case", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String selectedCaseCode = (String) caseDropdown.getSelectedItem();
            Case selectedCase = cases.get(selectedCaseCode);
            
            if (selectedCase != null) {
                Lawyer lawyer = Court.getInstance().findTheSuitableLawyer(selectedCase);
                if (lawyer != null) {
                    JOptionPane.showMessageDialog(this, "Suitable Lawyer: \n" + lawyer.toString(), "Suitable Lawyer Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No suitable lawyer found for this case.", "No Match", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    
    private void showCaseDifferencePopup() {
        Map<Integer, Lawyer> lawyers = Court.getInstance().getAllLawyers();
        if (lawyers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No lawyers available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Integer> lawyerDropdown = new JComboBox<>(lawyers.keySet().toArray(new Integer[0]));
        int result = JOptionPane.showConfirmDialog(this, lawyerDropdown, "Select a Lawyer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            Integer selectedLawyerId = (Integer) lawyerDropdown.getSelectedItem();
            Lawyer selectedLawyer = lawyers.get(selectedLawyerId);
            
            if (selectedLawyer != null) {
                int difference = Court.getInstance().differenceBetweenTheLongestAndShortestCase(selectedLawyer);
                JOptionPane.showMessageDialog(this, "Difference between longest and shortest case: " + difference + " days", "Case Length Difference", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void showAppointManagerPopup() {
        Map<Integer, Department> departments = Court.getInstance().getAllDepartments();
        if (departments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No departments available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Integer> departmentDropdown = new JComboBox<>(departments.keySet().toArray(new Integer[0]));
        int result = JOptionPane.showConfirmDialog(this, departmentDropdown, "Select a Department", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            Integer selectedDepartmentId = (Integer) departmentDropdown.getSelectedItem();
            Department selectedDepartment = departments.get(selectedDepartmentId);
            
            if (selectedDepartment != null) {
                Employee newManager = Court.getInstance().AppointANewManager(selectedDepartment);
                if (newManager != null) {
                    JOptionPane.showMessageDialog(this, "New Manager Appointed: \n" + newManager.toString(), "Manager Appointed", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No suitable employee found to appoint as manager.", "No Match", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    
    public void showPanel(String panelName) {
        JPanel panel = getPanelByName(panelName);
        if (panel != null) {
            invokeResetFields(panel);
        }
        cardLayout.show(panelContainer, panelName);
    }

    // Retrieve the pre-initialized panel directly
    private JPanel getPanelByName(String panelName) {
        switch (panelName) {
            case "DepartmentAdd": return departmentAddPanel;
            case "StaffAdd": return staffAddPanel;
            case "CaseMemberAdd": return caseMembersAddPanel;
            case "CaseAdd": return caseAddPanel;
            case "VerdictAdd": return verdictAddPanel;
            case "TestimonyAdd": return testimonyAddPanel;
            case "MeetingAdd": return meetingAddPanel;
            case "DocumentAdd": return documentAddPanel;
            case "CourtroomAdd": return courtroomAddPanel;
            case "AppealAdd": return appealAddPanel;
            case "DepartmentRemove": return departmentRemovePanel;
            case "CourtroomRemove": return courtroomRemovePanel;
            case "LawyerRemove": return lawyerRemovePanel;
            case "CaseRemove": return caseRemovePanel;
            case "JudgeRemove": return judgeRemovePanel;
            case "EmployeeRemove": return employeeRemovePanel;
            case "DocumentRemove": return documentRemovePanel;
            case "VerdictRemove": return verdictRemovePanel;
            case "TestimonyRemove": return testimonyRemovePanel;
            case "AppealRemove": return appealRemovePanel;
            case "WitnessRemove": return witnessRemovePanel;
            case "AccusedRemove": return accusedRemovePanel;
            case "MeetingRemove": return meetingRemovePanel;
            case "DepartmentGetReal": return departmentGetRealPanel;
            case "CourtroomGetReal": return courtroomGetRealPanel;
            case "LawyerGetReal": return lawyerGetRealPanel;
            case "JudgeGetReal": return judgeGetRealPanel;
            case "AccusedGetReal": return accusedGetRealPanel;
            case "EmployeeGetReal": return employeeGetRealPanel;
            case "DocumentGetReal": return documentGetRealPanel;
            case "VerdictGetReal": return verdictGetRealPanel;
            case "TestimonyGetReal": return testimonyGetRealPanel;
            case "WitnessGetReal": return witnessGetRealPanel;
            case "AppealGetReal": return appealGetRealPanel;
            case "MeetingGetReal": return meetingGetRealPanel;
            case "CaseGetReal": return caseGetRealPanel;
            case "InactiveCasesByDepartment": return inactiveCasesByDepartmentPanel;
            case "FamilyCasesWithWitnesses": return familyCasesWithWitnessesPanel;
            case "CasesWithThreeSameGenderTestimonies": return casesWithThreeSameGenderTestimoniesPanel;
            case "UniqueCrimeTools": return uniqueCrimeToolsPanel;
            case "PersonAdd": return addToDepartmentPanel;
            case "main": return mainPanel;

            default: return null;
        }
    }

    // Call resetFields() if the panel has it
    private void invokeResetFields(JPanel panel) {
        try {
            panel.getClass().getMethod("resetFields").invoke(panel);
        } catch (NoSuchMethodException ignored) {
            // Do nothing if resetFields() is missing
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
   
}
