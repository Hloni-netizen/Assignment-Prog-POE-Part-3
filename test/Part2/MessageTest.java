package Part2;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testConstructorAndGetters() {
        Message message = new Message(123, "+27123456789", "Hello, this is a test message.", "sent");

        assertEquals("1230000000", message.getMessageId());  // Checking the ID format
        assertEquals("+27123456789", message.getRecipient());
        assertEquals("Hello, this is a test message.", message.getContent());
        assertEquals("sent", message.getStatus());
        assertNotNull(message.getMessageHash());
    }

    @Test
    public void testMessageIdGeneration() {
        Message message = new Message(1, "+27123456789", "Test", "sent");
        String messageId = message.getMessageId();

        // Check if the messageId is 10 characters long and zero-padded
        assertEquals(10, messageId.length());
        assertTrue(messageId.startsWith("0000000001"));
    }

    @Test
    public void testCheckMessageID() {
        Message message = new Message(123, "+27123456789", "Test", "sent");
        assertTrue(message.checkMessageID());

        // Now you can pass a long number
        Message messageLarge = new Message(12345678901L, "+27123456789", "Test", "sent");
        assertFalse(messageLarge.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell() {
        Message validMessage = new Message(123, "+27123456789", "Test", "sent");
        assertEquals(1, validMessage.checkRecipientCell());

        Message invalidMessage = new Message(123, "+278123456", "Test", "sent");
        assertEquals(0, invalidMessage.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash() {
        Message message = new Message(123, "+27123456789", "Hello World", "sent");
        String hash = message.getMessageHash();

        // Hash should be formatted as IDStart:MessageNumber:FirstWordLastWord (uppercase)
        assertEquals("00:123:HELLOWORLD", hash);
    }

    @Test
    public void testSentMessage() {
        Message sentMessage = new Message(123, "+27123456789", "Hello", "sent");
        assertEquals("Message sent", sentMessage.SentMessage());

        Message storedMessage = new Message(124, "+27123456789", "Hello", "stored");
        assertEquals("Message stored for later", storedMessage.SentMessage());

        Message discardedMessage = new Message(125, "+27123456789", "Hello", "discarded");
        assertEquals("Message discarded", discardedMessage.SentMessage());

        Message unknownMessage = new Message(126, "+27123456789", "Hello", "unknown");
        assertEquals("Unknown message status", unknownMessage.SentMessage());
    }

    @Test
    public void testPrintMessages() {
        Message message = new Message(123, "+27123456789", "Test content", "sent");

        String expectedOutput = "ID: 0000000123\nRecipient: +27123456789\nContent: Test content\nHash: 00:123:TESTCONTENT\nStatus: sent\n";
        assertEquals(expectedOutput, message.printMessages());
    }

    @Test
    public void testReturnTotalMessages() {
        Message message = new Message(500, "+27123456789", "Test", "sent");
        assertEquals(500, message.returnTotalMessages());
    }

    @Test
    public void testSetStatus() {
        Message message = new Message(123, "+27123456789", "Test", "sent");

        message.setStatus("stored");
        assertEquals("stored", message.getStatus());

        message.setStatus("discarded");
        assertEquals("discarded", message.getStatus());
    }
}
