/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Part1.newpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login {

    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Login & Registration");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);

            showLoginPanel();

            frame.setVisible(true);
        });
    }

    private static void showLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        frame.setContentPane(loginPanel);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton toRegisterBtn = new JButton("Register");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginBtn);
        loginPanel.add(toRegisterBtn);

        loginBtn.addActionListener(e -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = new String(passwordField.getPassword());

            if (loginUser(enteredUsername, enteredPassword)) {
                showHomePage();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid login. Please try again.");
            }
        });

        toRegisterBtn.addActionListener(e -> showRegisterPanel());

        frame.revalidate();
        frame.repaint();
    }

    // Stub for login logic
    private static boolean loginUser(String username, String password) {
        // Replace this with actual login logic
        return username.equals("admin") && password.equals("admin123");
    }

    private static void showRegisterPanel() {
        // Placeholder panel
        JPanel registerPanel = new JPanel();
        registerPanel.add(new JLabel("Registration Form Coming Soon..."));
        frame.setContentPane(registerPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void showHomePage() {
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("Welcome to the Home Page!"));
        frame.setContentPane(homePanel);
        frame.revalidate();
        frame.repaint();
    }
}
