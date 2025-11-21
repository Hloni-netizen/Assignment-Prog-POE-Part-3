/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Part2;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage {

    private List<Message> messageList;
    private long nextMessageId;

    // Constructor to initialize the message list and next message ID
    public MessageStorage() {
        this.messageList = new ArrayList<>();
        this.nextMessageId = 1;  // Starting message ID from 1
    }

    // Get the next message ID and increment it
    public long getNextMessageNumber() {
        return nextMessageId++;
    }

    // Add a message to the storage
    public void addMessage(Message message) {
        messageList.add(message);
    }

    // Save all messages (currently just prints out for simplicity)
    public void saveMessages() {
        for (Message msg : messageList) {
            System.out.println("Saving message: " + msg.printMessages());
        }
    }

    // Get all messages
    public List<Message> getMessages() {
        return new ArrayList<>(messageList);
    }

    // Search messages by recipient
    public List<Message> searchByRecipient(String recipient) {
        List<Message> result = new ArrayList<>();
        for (Message message : messageList) {
            if (message.getRecipient().equals(recipient)) {
                result.add(message);
            }
        }
        return result;
    }

    // Search messages by hash
    public Message searchByHash(String hash) {
        for (Message message : messageList) {
            if (message.getMessageHash().equals(hash)) {
                return message;
            }
        }
        return null;
    }

    // Delete message by hash
    public boolean deleteByHash(String hash) {
        for (Message message : messageList) {
            if (message.getMessageHash().equals(hash)) {
                messageList.remove(message);
                return true;
            }
        }
        return false;
    }

    // Get messages by status
    public List<Message> getMessagesByStatus(String status) {
        List<Message> result = new ArrayList<>();
        for (Message message : messageList) {
            if (message.getStatus().equalsIgnoreCase(status)) {
                result.add(message);
            }
        }
        return result;
    }
}
