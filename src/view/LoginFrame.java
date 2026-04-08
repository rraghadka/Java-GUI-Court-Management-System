package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import control.Court;
import model.Employee;
import model.Judge;
import model.Lawyer;
import utils.SerializationUtils;

public class LoginFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;

    public LoginFrame() {
        setTitle("Court Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(179, 88, 126, 14);
        getContentPane().add(usernameLabel);
        
        usernameField = new JTextField(15);
        usernameField.setBounds(179, 113, 126, 20);
        getContentPane().add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(179, 145, 126, 14);
        getContentPane().add(passwordLabel);
        
        passwordField = new JPasswordField(15);
        passwordField.setBounds(179, 170, 126, 20);
        getContentPane().add(passwordField);
        
        loginButton = new JButton("Login");
        loginButton.setBounds(179, 201, 126, 23);
        getContentPane().add(loginButton);
        
        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setBounds(460, 16, 0, 0);
        errorLabel.setForeground(Color.RED);
        getContentPane().add(errorLabel);
        
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Serialize the hospital data before exiting
            	SerializationUtils.saveCourt();
                System.exit(0);
            }
        });
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
    }
    
    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("ADMIN") && password.equals("ADMIN")) {
            JOptionPane.showMessageDialog(this, "Admin login successful!");
            MainFrame mainFrame = new MainFrame("admin");
            mainFrame.setVisible(true);
            this.dispose();
        }else {

        // Check if the user is an Employee
        Employee employee = Court.getInstance().getAllEmployees().get(Integer.valueOf(username));
        if (employee != null && employee.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Employee login successful!");
            MainFrame mainFrame = new MainFrame(""+employee.getId());
            mainFrame.setVisible(true);
            this.dispose();
        }

        // Check if the user is a Lawyer or a Judge
        Lawyer lawyer = Court.getInstance().getAllLawyers().get(Integer.valueOf(username));
        	
        if(lawyer !=null) {
        String lusername =""+ lawyer.getId();
        	
            if (lusername.equals(username) && lawyer.getPassword().equals(password)) {
                if (lawyer instanceof Judge) {
                    JOptionPane.showMessageDialog(this, "Judge login successful!");
                    MainFrame mainFrame = new MainFrame(lusername);
                    mainFrame.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Lawyer login successful!");
                    MainFrame mainFrame = new MainFrame(lusername);
                    mainFrame.setVisible(true);
                    this.dispose();
                }
                return;
            }
            
        }
        }
        

        // If no match found, display an error message
        errorLabel.setText("Invalid username or password.");
    }
    
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		SerializationUtils.loadCourt();
	}
}
