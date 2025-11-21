package Part2;

import java.util.UUID;

public class Message {
    private String messageId;
    private String recipient;
    private String content;
    private String status;
    private String messageHash;

    // Constructor
    public Message(long id, String recipient, String content, String status) {
        this.messageId = String.format("%010d", id);  // Zero-padded to 10 digits
        this.recipient = recipient;
        this.content = content;
        this.status = status;
        this.messageHash = generateMessageHash();  // Generate hash when the message is created
    }

    // Generate a unique hash for the message
    private String generateMessageHash() {
        return "00:" + messageId + ":" + content.toUpperCase().replaceAll("\\s+", "");
    }

    // Getters
    public String getMessageId() {
        return messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public String getMessageHash() {
        return messageHash;
    }

    // Check the validity of the message ID (only accepts IDs with 10 digits)
    public boolean checkMessageID() {
        return messageId.matches("\\d{10}");
    }

    // Check if recipient cell number is valid (starts with +27)
    public int checkRecipientCell() {
        if (recipient.startsWith("+27") && recipient.length() == 13) {
            return 1;  // valid number
        }
        return 0;  // invalid number
    }

    // Sent message logic based on status
    public String SentMessage() {
        switch (status.toLowerCase()) {
            case "sent":
                return "Message sent";
            case "stored":
                return "Message stored for later";
            case "discarded":
                return "Message discarded";
            default:
                return "Unknown message status";
        }
    }

    // Return a formatted string for the message
    public String printMessages() {
        return String.format("ID: %s\nRecipient: %s\nContent: %s\nHash: %s\nStatus: %s\n",
                messageId, recipient, content, messageHash, status);
    }

    // Return total number of messages (for the sake of example)
    public long returnTotalMessages() {
        return Long.parseLong(messageId);  // Just returns the message ID for testing purposes
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public int getMessageNumber() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
