/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Part1.newpackage;

/**
 *
 * @author Hloniphile
 */
import javax.swing.*;

public class welcome extends JFrame {

    private JLabel jLabel1;

    public welcome() {
        initComponents();
    }

    private void initComponents() {
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome");

        jLabel1.setText("Welcome to EasyKanban");
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(100)
                    .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(50)
                    .addComponent(jLabel1)
                    .addContainerGap(200, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null); // Center the window
    }

    public static void main(String[] args) {
        // Set Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Failed to set look and feel.");
        }

        // Run GUI
        SwingUtilities.invokeLater(() -> {
            new welcome().setVisible(true);
        });
    }
}