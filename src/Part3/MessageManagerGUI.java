/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Part3;

import Part2.Message;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MessageManagerGUI extends JFrame {
    private final MessageManager messageManager;
    private JTextArea outputArea;
    private JTable reportTable;
    private DefaultTableModel tableModel;

    public MessageManagerGUI() {
        messageManager = new MessageManager();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Message Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(900, 700);

        createMenuBar();
        createMainPanel();
        createOutputPanel();
        createReportPanel();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operationsMenu = new JMenu("Operations");

        String[] menuItems = {
            "Show Sent Messages", "Find Longest Message", "Search by Message ID",
            "Search by Recipient", "Delete Message by Hash", "Show Report"
        };

        for (String item : menuItems) {
            JMenuItem menuItem = new JMenuItem(item);
            menuItem.addActionListener(e -> handleMenuAction(item));
            operationsMenu.add(menuItem);
        }

        menuBar.add(operationsMenu);
        setJMenuBar(menuBar);
    }

    private void handleMenuAction(String action) {
        switch (action) {
            case "Show Sent Messages" -> displaySentMessages();
            case "Find Longest Message" -> displayLongestMessage();
            case "Search by Message ID" -> searchByMessageID();
            case "Search by Recipient" -> searchByRecipient();
            case "Delete Message by Hash" -> deleteMessageByHash();
            case "Show Report" -> displayReport();
        }
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Message Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.NORTH);
    }

    private void createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));

        outputArea = new JTextArea(15, 60);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.CENTER);
    }

    private void createReportPanel() {
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder("Sent Messages Report"));
        reportPanel.setPreferredSize(new Dimension(800, 200));

        String[] columnNames = {"Message Hash", "Recipient", "Message", "Flag"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);
        reportTable.setFont(new Font("Monospaced", Font.PLAIN, 11));

        JScrollPane tableScrollPane = new JScrollPane(reportTable);
        reportPanel.add(tableScrollPane, BorderLayout.CENTER);
        add(reportPanel, BorderLayout.SOUTH);
    }

    private void displaySentMessages() {
        List<Message> sentMessages = messageManager.getSentMessages();
        outputArea.setText("SENT MESSAGES:\n");
        outputArea.append("=".repeat(80) + "\n");

        if (sentMessages.isEmpty()) {
            outputArea.append("No sent messages found.\n");
        } else {
            for (Message msg : sentMessages) {
                outputArea.append("Recipient: " + msg.getRecipient() + "\n");
                outputArea.append("Message: " + msg.getContent() + "\n");
                
                outputArea.append("-".repeat(80) + "\n");
            }
        }
    }

    private void displayLongestMessage() {
        String longestMessage = messageManager.getLongestMessage();
        outputArea.setText("LONGEST MESSAGE:\n");
        outputArea.append("=".repeat(80) + "\n");
        outputArea.append("Length: " + longestMessage.length() + " characters\n");
        outputArea.append("Content: " + longestMessage + "\n");
    }

    private void searchByMessageID() {
        String messageID = JOptionPane.showInputDialog(this, "Enter Message ID:");
        if (messageID != null && !messageID.trim().isEmpty()) {
            String message = messageManager.searchByMessageID(messageID.trim());
            outputArea.setText("SEARCH BY MESSAGE ID:\n");
            outputArea.append("=".repeat(80) + "\n");
            outputArea.append("Message ID: " + messageID + "\n");
            outputArea.append("Result: " + message + "\n");
        }
    }

    private void searchByRecipient() {
        String recipient = JOptionPane.showInputDialog(this, "Enter Recipient:");
        if (recipient != null && !recipient.trim().isEmpty()) {
            List<String> messages = messageManager.searchByRecipient(recipient.trim());

            outputArea.setText("MESSAGES FOR RECIPIENT: " + recipient + "\n");
            outputArea.append("=".repeat(80) + "\n");

            if (messages.isEmpty()) {
                outputArea.append("No messages found for this recipient.\n");
            } else {
                outputArea.append("Found " + messages.size() + " message(s):\n\n");
                for (String msg : messages) {
                    outputArea.append("• " + msg + "\n\n");
                }
            }
        }
    }

    private void deleteMessageByHash() {
        String messageHash = JOptionPane.showInputDialog(this, "Enter Message Hash:");
        if (messageHash != null && !messageHash.trim().isEmpty()) {
            Message messageToDelete = messageManager.findMessageByHash(messageHash.trim());
            if (messageToDelete != null) {
                boolean deleted = messageManager.deleteMessageByHash(messageHash.trim());
                if (deleted) {
                    outputArea.setText("DELETE MESSAGE:\n");
                    outputArea.append("=".repeat(80) + "\n");
                    outputArea.append("SUCCESS: Message \"" +
                            messageToDelete.getContent().substring(0, Math.min(50, messageToDelete.getContent().length())) +
                            "...\" successfully deleted.\n");
                } else {
                    outputArea.setText("ERROR: Failed to delete message with hash " + messageHash + "\n");
                }
            } else {
                outputArea.setText("ERROR: Message with hash " + messageHash + " not found.\n");
            }
        }
    }

    private void displayReport() {
        List<Message> messages = messageManager.getAllMessagesForReport();
        tableModel.setRowCount(0);

        for (Message msg : messages) {
            tableModel.addRow(new Object[]{
                    
                    msg.getRecipient(),
                    msg.getContent(),
                    msg.getStatus()  // Assuming "Flag" refers to status
            });
        }

        outputArea.setText("REPORT GENERATED:\n");
        outputArea.append("Displaying " + messages.size() + " sent messages in the table below.\n");
    }

    public static void main(String[] args) {
        // Set system look and feel with proper method
        try {
            // Correct way to set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            try {
                // Fallback if system look and feel fails
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
                System.err.println("Error setting look and feel: " + ex.getMessage());
            }
        }

        SwingUtilities.invokeLater(() -> new MessageManagerGUI());
    }
}
