/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Part3;

import Part2.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private List<Message> messages;

    public MessageManager() {
        this.messages = new ArrayList<>();
        // Sample messages for testing
        messages.add(new Message(1, "+27834557896", "Did you get the cake?", "sent"));
        messages.add(new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "stored"));
        messages.add(new Message(3, "+27838884567", "Yohoo, I am at your gate", "discarded"));
        messages.add(new Message(4, "+27834557896", "It is dinner time!", "discarded"));
        messages.add(new Message(5, "+27834557896", "OK I am leaving without you?", "stored"));
    }

    // Adds a message to the list
    public void addMessage(Message message) {
        messages.add(message);
    }

    // Returns all sent messages
    public List<Message> getSentMessages() {
        List<Message> sentMessages = new ArrayList<>();
        for (Message message : messages) {
            if ("sent".equalsIgnoreCase(message.getStatus())) {
                sentMessages.add(message);
            }
        }
        return sentMessages;
    }

    // Returns the longest message by content length
    public String getLongestMessage() {
        String longestMessage = "";
        for (Message message : messages) {
            if (message.getContent().length() > longestMessage.length()) {
                longestMessage = message.getContent();
            }
        }
        return longestMessage;
    }

    // Search messages by message ID
    public String searchByMessageID(String messageId) {
        for (Message message : messages) {
            if (message.getMessageId().equals(messageId)) {
                return message.printMessages();
            }
        }
        return "Message not found.";
    }

    // Search messages by recipient
    public List<String> searchByRecipient(String recipient) {
        List<String> results = new ArrayList<>();
        for (Message message : messages) {
            if (message.getRecipient().equals(recipient)) {
                results.add(message.printMessages());
            }
        }
        return results;
    }

    // Find a message by its hash
    public Message findMessageByHash(String hash) {
        for (Message message : messages) {
            if (message.getMessageHash().equals(hash)) {
                return message;
            }
        }
        return null;
    }

    // Delete message by hash
    public boolean deleteMessageByHash(String hash) {
        Message messageToDelete = findMessageByHash(hash);
        if (messageToDelete != null) {
            messages.remove(messageToDelete);
            return true;
        }
        return false;
    }

    // Get all messages for the report (sent only)
    public List<Message> getAllMessagesForReport() {
        List<Message> sentMessages = new ArrayList<>();
        for (Message message : messages) {
            if ("sent".equalsIgnoreCase(message.getStatus())) {
                sentMessages.add(message);
            }
        }
        return sentMessages;
    }
}
