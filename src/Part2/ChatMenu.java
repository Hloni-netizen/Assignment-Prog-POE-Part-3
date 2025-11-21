/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Part2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

public class ChatMenu extends JFrame {
    private JButton btnSendMessages;
    private JButton btnShowReport;
    private JButton btnExit;
    private JLabel lblWelcome;
    private final MessageStorage messageStorage;

    public ChatMenu() {
        initComponents();
        messageStorage = new MessageStorage(); // Use MessageStorage to store messages
    }

    private void initComponents() {
        setTitle("QuickChat Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                JOptionPane.showMessageDialog(ChatMenu.this,
                        "Please use the Exit button to close the application.",
                        "Use Exit Button",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        lblWelcome = new JLabel("Welcome to QuickChat");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);

        btnSendMessages = new JButton("Send Messages");
        btnShowReport = new JButton("Show Message Report");
        btnExit = new JButton("Exit");

        setLayout(new BorderLayout());
        add(lblWelcome, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonPanel.add(btnSendMessages);
        buttonPanel.add(btnShowReport);
        buttonPanel.add(btnExit);

        add(buttonPanel, BorderLayout.CENTER);

        btnSendMessages.addActionListener(e -> handleSendMessages());
        btnShowReport.addActionListener(e -> showMessageReportMenu());
        btnExit.addActionListener(e -> dispose());
    }

    // Handles sending multiple messages
    private void handleSendMessages() {
        String input = JOptionPane.showInputDialog(this,
                "How many messages would you like to send?",
                "Number of Messages",
                JOptionPane.QUESTION_MESSAGE);

        if (input == null) return;

        int numMessages;
        try {
            numMessages = Integer.parseInt(input);
            if (numMessages <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a positive number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int messagesSent = 0;
        long currentMessageNumber = messageStorage.getNextMessageNumber();

        for (int i = 0; i < numMessages; i++) {
            String recipient = null;
            while (recipient == null) {
                String recipientInput = JOptionPane.showInputDialog(this,
                        "Message " + (i + 1) + " of " + numMessages + "\nEnter recipient phone number (with +27 country code):",
                        "Message Recipient",
                        JOptionPane.QUESTION_MESSAGE);

                if (recipientInput == null) {
                    int choice = JOptionPane.showConfirmDialog(this,
                            "Skip this message?",
                            "Skip Message",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        break;
                    } else {
                        continue;
                    }
                }

                if (isValidPhoneNumber(recipientInput.trim())) {
                    recipient = recipientInput.trim();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid phone number format. Please use +27 followed by 9 digits (e.g., +27823456789)",
                            "Invalid Phone Number",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            if (recipient == null) continue;

            String content = JOptionPane.showInputDialog(this,
                    "Message " + (i + 1) + " of " + numMessages + "\nEnter message content (max 250 chars):",
                    "Message Content",
                    JOptionPane.QUESTION_MESSAGE);

            if (content == null) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "Skip this message?",
                        "Skip Message",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    continue;
                } else {
                    i--;
                    continue;
                }
            }

            Message message = new Message(currentMessageNumber++, recipient, content, "Sent");
            String[] options = {"Send", "Store", "Discard"};
            int choice = JOptionPane.showOptionDialog(this,
                    message.toString() + "\n\nWhat would you like to do with this message?",
                    "Message Created",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0: // Send
                    message.setStatus("Sent");
                    messageStorage.addMessage(message);
                    break;
                case 1: // Store
                    message.setStatus("Stored");
                    messageStorage.addMessage(message);
                    break;
                case 2: // Discard
                default:
                    message.setStatus("Discarded");
                    messageStorage.addMessage(message);
                    break;
            }

            messageStorage.saveMessages();
            JOptionPane.showMessageDialog(this,
                    "Message " + message.getStatus().toLowerCase() + " successfully!",
                    "Message " + message.getStatus(),
                    JOptionPane.INFORMATION_MESSAGE);
            messagesSent++;
        }

        if (messagesSent > 0) {
            JOptionPane.showMessageDialog(this,
                    "You have sent/stored " + messagesSent + " message(s).",
                    "Message Summary",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+27[0-9]{9}$";
        return Pattern.matches(regex, phoneNumber);
    }

    // Show message report menu
    private void showMessageReportMenu() {
        JFrame reportFrame = new JFrame("Message Report Menu");
        reportFrame.setSize(450, 400);
        reportFrame.setLocationRelativeTo(null);
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel reportPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton showAllMessagesButton = new JButton("Show All Messages");
        showAllMessagesButton.addActionListener(e -> showAllMessages());
        reportPanel.add(showAllMessagesButton, gbc);

        JButton searchByRecipientButton = new JButton("Search Message by Recipient");
        searchByRecipientButton.addActionListener(e -> {
            String recipient = JOptionPane.showInputDialog(null, "Enter the recipient phone number to search:");
            if (recipient != null && !recipient.trim().isEmpty()) {
                searchMessagesByRecipient(recipient.trim());
            } else {
                JOptionPane.showMessageDialog(null, "Recipient phone number cannot be empty.");
            }
        });
        reportPanel.add(searchByRecipientButton, gbc);

        reportFrame.add(reportPanel);
        reportFrame.setVisible(true);
    }

    private void showAllMessages() {
        List<Message> messages = messageStorage.getMessages();

        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found.", "Message Report", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder report = new StringBuilder("All Messages Report:\n\n");
        for (Message message : messages) {
            report.append("ID: ").append(message.getMessageId()).append("\n");
            report.append("Recipient: ").append(message.getRecipient()).append("\n");
            report.append("Content: ").append(message.getContent()).append("\n");
            report.append("Status: ").append(message.getStatus()).append("\n");
            report.append("─────────────────────────────\n");
        }

        JTextArea textArea = new JTextArea(report.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "All Messages Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatMenu().setVisible(true));
    }

    private void searchMessagesByRecipient(String recipient) {
        List<Message> result = messageStorage.searchByRecipient(recipient);

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found for recipient " + recipient, "Message Search", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder report = new StringBuilder("Messages for recipient " + recipient + ":\n\n");
        for (Message message : result) {
            report.append(message.printMessages());
            report.append("─────────────────────────────\n");
        }

        JTextArea textArea = new JTextArea(report.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(null, scrollPane, "Message Search", JOptionPane.INFORMATION_MESSAGE);
    }
}
