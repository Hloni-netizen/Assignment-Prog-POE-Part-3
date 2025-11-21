package Part3;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */


import Part2.Message;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class MessageManagerTest {

    private MessageManager messageManager;

    @Before
    public void setUp() {
        messageManager = new MessageManager();
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        List<Message> sentMessages = messageManager.getSentMessages();

        boolean foundMessage1 = false;
        boolean foundMessage4 = false;

        // Check if expected messages are present
        for (Message msg : sentMessages) {
            if ("Did you get the cake?".equals(msg.getContent())) {
                foundMessage1 = true;
            }
            if ("It is dinner time!".equals(msg.getContent())) {
                foundMessage4 = true;
            }
        }

        assertTrue("Message 1 should be in sent messages", foundMessage1);
        assertTrue("Message 4 should be in sent messages", foundMessage4);
        assertEquals("Should have correct number of sent messages", 2, sentMessages.size());
    }

    @Test
    public void testDisplayLongestMessage() {
        String longestMessage = messageManager.getLongestMessage();
        String expected = "Where are you? You are late! I have asked you to be on time.";

        assertEquals("Longest message should match test data", expected, longestMessage);
    }

    @Test
    public void testSearchAllMessagesForRecipient() {
        List<String> messages = messageManager.searchByRecipient("+27838884567");

        // Should find 2 messages for this recipient (one sent, one stored)
        assertEquals("Should find 2 messages for recipient", 2, messages.size());
        assertTrue("Should contain the stored message", 
                messages.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue("Should contain the stored message",
                messages.contains("OK I am leaving without you?"));
    }

    @Test
    public void testDisplayReport() {
        List<Message> reportMessages = messageManager.getAllMessagesForReport();

        assertFalse("Report should not be empty", reportMessages.isEmpty());

        for (Message msg : reportMessages) {
            assertNotNull("Message hash should not be null", msg.getMessageHash());
            assertNotNull("Recipient should not be null", msg.getRecipient());
            assertNotNull("Message content should not be null", msg.getContent());
            assertNotNull("Flag should not be null", msg.getStatus());
        }
    }

    @Test
    public void testMessageHashGeneration() {
        Message testMessage = new Message(6, "+27834557896", "Test message", "Sent");
        String hash = testMessage.getMessageHash();

        assertNotNull("Message hash should be generated", hash);
        assertFalse("Message hash should not be empty", hash.isEmpty());
    }
}
