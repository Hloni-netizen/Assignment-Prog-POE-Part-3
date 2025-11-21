/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Part1.newpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

        

public class LoginandRegistration extends JFrame {
    private JPanel authPanel, signupPanel;
    JTextField authUserField;
    JTextField signFirstName;
    JTextField signLastName;
    JTextField signUserField;
    JTextField signMobileField;
    JPasswordField authPassField;
    JPasswordField signPassField;
    private JButton authButton, gotoSignupButton, signupButton, backToAuthButton;
    AuthProfile storedProfile;

    public LoginandRegistration() {
        configureWindow();
        initializeComponents();
        setupEventHandlers();
        setupLayout();
    }

    private void configureWindow() {
        setTitle("User Authentication Portal");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());
    }

    private void initializeComponents() {
        // Authentication components
        authUserField = new JTextField(12);
        authPassField = new JPasswordField(12);
        authButton = new JButton("Authenticate");
        gotoSignupButton = new JButton("Create Account");

        // Registration components
        signFirstName = new JTextField(12);
        signLastName = new JTextField(12);
        signUserField = new JTextField(12);
        signPassField = new JPasswordField(12);
        signMobileField = new JTextField(12);
        signupButton = new JButton("Complete Registration");
        backToAuthButton = new JButton("Return to Login");
    }

    private void setupEventHandlers() {
        authButton.addActionListener(e -> processAuthentication());
        gotoSignupButton.addActionListener(e -> showPanel(signupPanel));
        backToAuthButton.addActionListener(e -> showPanel(authPanel));
        signupButton.addActionListener(e -> handleRegistration());
    }

    private void setupLayout() {
        createAuthPanel();
        createSignupPanel();
        
        add(authPanel, "Authentication");
        add(signupPanel, "Registration");
        
        showPanel(authPanel);
    }

    private void createAuthPanel() {
        authPanel = new JPanel();
        authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
        
        authPanel.add(new JLabel("UserName:"));
        authPanel.add(authUserField);
        authPanel.add(new JLabel("Access Code:"));
        authPanel.add(authPassField);
        authPanel.add(authButton);
        authPanel.add(gotoSignupButton);
    }

    private void createSignupPanel() {
        signupPanel = new JPanel();
        signupPanel.setLayout(new BoxLayout(signupPanel, BoxLayout.Y_AXIS));
        
        signupPanel.add(new JLabel("Fist Name:"));
        signupPanel.add(signFirstName);
        signupPanel.add(new JLabel("Last Name:"));
        signupPanel.add(signLastName);
        signupPanel.add(new JLabel("UserName:"));
        signupPanel.add(signUserField);
        signupPanel.add(new JLabel("Security Code:"));
        signupPanel.add(signPassField);
        signupPanel.add(new JLabel("Mobile Number (+27XXXXXXXXX):"));
        signupPanel.add(signMobileField);
        signupPanel.add(signupButton);
        signupPanel.add(backToAuthButton);
    }

    void processAuthentication() {
        if (storedProfile != null) {
            String inputUser = authUserField.getText();
            String inputPass = new String(authPassField.getPassword());
            
            String authResult = storedProfile.verifyCredentials(inputUser, inputPass);
            JOptionPane.showMessageDialog(this, authResult);
        } else {
            JOptionPane.showMessageDialog(this, "No registered profiles found!");
        }
    }

    void handleRegistration() {
        AuthProfile newProfile = new AuthProfile(
            signFirstName.getText(),
            signLastName.getText(),
            signUserField.getText(),
            new String(signPassField.getPassword()),
            signMobileField.getText()
        );
        
        String registrationOutcome = newProfile.completeRegistration();
        JOptionPane.showMessageDialog(this, registrationOutcome);

        if (registrationOutcome.equals("Profile created successfully!")) {
            storedProfile = newProfile;
            showPanel(authPanel);
        }
    }

    private void showPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginandRegistration().setVisible(true));
    }
}

class AuthProfile {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String mobileNumber;

    public AuthProfile(String firstName, String lastName, String username, String password, String mobileNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.mobileNumber = mobileNumber;
    }

    public boolean validateUsernameFormat() {
        return username != null && username.length() <= 5 && username.contains("_");
    }

    public boolean validatePasswordComplexity() {
    // Check if password is null or its length is less than 8 characters
    if (password == null || password.length() < 8) return false;
    
    // Check for at least one uppercase letter
    boolean hasUpper = !password.equals(password.toLowerCase());
    
    // Check for at least one digit
    boolean hasDigit = password.matches(".*\\d.*");
    
    // Check for at least one special character
    boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    
    // Return true if all conditions are met
    return hasUpper && hasDigit && hasSpecial;
}


    public boolean validateMobileFormat() {
        return mobileNumber != null && mobileNumber.matches("^\\+27\\d{9}$");
    }

    public String completeRegistration() {
        if (!validateUsernameFormat()) {
            return "Invalid user ID format. Must contain underscore and be ≤5 characters.";
        }
        
        if (!validatePasswordComplexity()) {
            return "Security code must be 8+ characters with uppercase, number, and special character.";
        }
        
        if (!validateMobileFormat()) {
            return "Invalid mobile format. Use +27 followed by 9 digits.";
        }
        
        return "Regstration successfully!";
    }

    public String verifyCredentials(String inputUser, String inputPass) {
        if (username.equals(inputUser) && password.equals(inputPass)) {
            return "Welcome " + firstName + ", " + lastName + "! Great to see you again.";
        } else {
            return "Authentication failed. Please check your credentials.";
        }
    }
}