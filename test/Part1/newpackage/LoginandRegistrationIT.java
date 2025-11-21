/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Part1.newpackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginandRegistrationIT {
    
    private LoginandRegistration loginSystem;
    private AuthProfile validProfile;
    private AuthProfile invalidProfile;
    
    public LoginandRegistrationIT() {
        // Default constructor
    }
    
    @BeforeAll
    public static void setUpClass() {
        System.out.println("=== Starting LoginandRegistration Integration Tests ===");
        System.out.println("Testing GUI-based authentication system with validation");
    }
    
    @AfterAll
    public static void tearDownClass() {
        System.out.println("=== LoginandRegistration Integration Tests Completed ===");
    }
    
    @BeforeEach
    public void setUp() {
        System.out.println("Setting up test environment...");
        
        // Initialize the main application
        loginSystem = new LoginandRegistration();
        
        // Create test profiles for consistent testing
        validProfile = new AuthProfile(
            "John", 
            "Doe", 
            "jd_01", 
            "MyPass123!", 
            "+27123456789"
        );
        
        invalidProfile = new AuthProfile(
            "Jane", 
            "Smith", 
            "janesmithverylongusername", // Invalid - too long, no underscore
            "weak", // Invalid - doesn't meet complexity
            "123456" // Invalid - wrong format
        );
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("Cleaning up test environment...");
        loginSystem = null;
        validProfile = null;
        invalidProfile = null;
    }

    // =============== MAIN METHOD TEST ===============
    
    /**
     * Test of main method, of class LoginandRegistration.
     * This test ensures the application can start without throwing exceptions.
     */
    @Test
    public void testMain() {
        System.out.println("Testing main method execution...");
        String[] args = null;
        
        // Test that main method executes without throwing exceptions
        assertDoesNotThrow(() -> {
            LoginandRegistration.main(args);
        }, "Main method should execute without throwing exceptions");
        
        System.out.println("Main method test passed - application starts successfully");
    }

    // =============== USERNAME VALIDATION TESTS ===============
    
    @Test
    public void testValidUsernameFormat() {
        System.out.println("Testing valid username format...");
        
        // Test various valid username formats
        AuthProfile profile1 = new AuthProfile("Test", "User", "a_1", "TempPass123!", "+27123456789");
        AuthProfile profile2 = new AuthProfile("Test", "User", "ab_12", "TempPass123!", "+27123456789");
        AuthProfile profile3 = new AuthProfile("Test", "User", "x_y", "TempPass123!", "+27123456789");
        
        assertTrue(profile1.validateUsernameFormat(), "Single char + underscore + single char should be valid");
        assertTrue(profile2.validateUsernameFormat(), "4 characters with underscore should be valid");
        assertTrue(profile3.validateUsernameFormat(), "3 characters with underscore should be valid");
        
        System.out.println("Valid username format tests passed");
    }
    
    @Test
    public void testInvalidUsernameFormat() {
        System.out.println("Testing invalid username formats...");
        
        // Test various invalid username formats
        AuthProfile noUnderscore = new AuthProfile("Test", "User", "abcde", "TempPass123!", "+27123456789");
        AuthProfile tooLong = new AuthProfile("Test", "User", "abc_def", "TempPass123!", "+27123456789");
        AuthProfile empty = new AuthProfile("Test", "User", "", "TempPass123!", "+27123456789");
        AuthProfile nullUsername = new AuthProfile("Test", "User", null, "TempPass123!", "+27123456789");
        
        assertFalse(noUnderscore.validateUsernameFormat(), "Username without underscore should be invalid");
        assertFalse(tooLong.validateUsernameFormat(), "Username longer than 5 chars should be invalid");
        assertFalse(empty.validateUsernameFormat(), "Empty username should be invalid");
        assertFalse(nullUsername.validateUsernameFormat(), "Null username should be invalid");
        
        System.out.println("Invalid username format tests passed");
    }

    // =============== PASSWORD VALIDATION TESTS ===============
    
    @Test
    public void testValidPasswordComplexity() {
        System.out.println("Testing valid password complexity...");
        
        // Test various valid passwords
        AuthProfile profile1 = new AuthProfile("Test", "User", "usr_1", "Password1!", "+27123456789");
        AuthProfile profile2 = new AuthProfile("Test", "User", "usr_1", "MyP@ssw0rd123", "+27123456789");
        AuthProfile profile3 = new AuthProfile("Test", "User", "usr_1", "Str0ng#Pass", "+27123456789");
        
        assertTrue(profile1.validatePasswordComplexity(), "Password with all requirements should be valid");
        assertTrue(profile2.validatePasswordComplexity(), "Complex password should be valid");
        assertTrue(profile3.validatePasswordComplexity(), "Password with special chars should be valid");
        
        System.out.println("Valid password complexity tests passed");
    }
    
    @Test
    public void testInvalidPasswordComplexity() {
        System.out.println("Testing invalid password complexity...");
        
        // Test various invalid passwords
        AuthProfile tooShort = new AuthProfile("Test", "User", "usr_1", "Pass1!", "+27123456789");
        AuthProfile noUpper = new AuthProfile("Test", "User", "usr_1", "password123!", "+27123456789");
        AuthProfile noNumber = new AuthProfile("Test", "User", "usr_1", "Password!", "+27123456789");
        AuthProfile noSpecial = new AuthProfile("Test", "User", "usr_1", "Password123", "+27123456789");
        AuthProfile nullPassword = new AuthProfile("Test", "User", "usr_1", null, "+27123456789");
        
        assertFalse(tooShort.validatePasswordComplexity(), "Password less than 8 chars should be invalid");
        assertFalse(noUpper.validatePasswordComplexity(), "Password without uppercase should be invalid");
        assertFalse(noNumber.validatePasswordComplexity(), "Password without number should be invalid");
        assertFalse(noSpecial.validatePasswordComplexity(), "Password without special char should be invalid");
        assertFalse(nullPassword.validatePasswordComplexity(), "Null password should be invalid");
        
        System.out.println("Invalid password complexity tests passed");
    }

    // =============== MOBILE NUMBER VALIDATION TESTS ===============
    
    @Test
    public void testValidMobileFormat() {
        System.out.println("Testing valid mobile number formats...");
        
        // Test various valid mobile numbers
        AuthProfile profile1 = new AuthProfile("Test", "User", "usr_1", "Password123!", "+27123456789");
        AuthProfile profile2 = new AuthProfile("Test", "User", "usr_1", "Password123!", "+27987654321");
        AuthProfile profile3 = new AuthProfile("Test", "User", "usr_1", "Password123!", "+27000000000");
        
        assertTrue(profile1.validateMobileFormat(), "Valid SA mobile number should pass");
        assertTrue(profile2.validateMobileFormat(), "Another valid SA mobile number should pass");
        assertTrue(profile3.validateMobileFormat(), "SA mobile with all zeros should pass");
        
        System.out.println("Valid mobile format tests passed");
    }
    
    @Test
    public void testInvalidMobileFormat() {
        System.out.println("Testing invalid mobile number formats...");
        
        // Test various invalid mobile numbers
        AuthProfile noCountryCode = new AuthProfile("Test", "User", "usr_1", "Password123!", "0123456789");
        AuthProfile wrongCountry = new AuthProfile("Test", "User", "usr_1", "Password123!", "+1234567890");
        AuthProfile tooShort = new AuthProfile("Test", "User", "usr_1", "Password123!", "+2712345");
        AuthProfile tooLong = new AuthProfile("Test", "User", "usr_1", "Password123!", "+271234567890");
        AuthProfile nullMobile = new AuthProfile("Test", "User", "usr_1", "Password123!", null);
        
        assertFalse(noCountryCode.validateMobileFormat(), "Number without country code should be invalid");
        assertFalse(wrongCountry.validateMobileFormat(), "Number with wrong country code should be invalid");
        assertFalse(tooShort.validateMobileFormat(), "Number too short should be invalid");
        assertFalse(tooLong.validateMobileFormat(), "Number too long should be invalid");
        assertFalse(nullMobile.validateMobileFormat(), "Null mobile number should be invalid");
        
        System.out.println("Invalid mobile format tests passed");
    }

    // =============== REGISTRATION PROCESS TESTS ===============
    
    @Test
    public void testCompleteRegistrationSuccess() {
        System.out.println("Testing successful registration process...");
        
        String result = validProfile.completeRegistration();
        assertEquals("Regstration successfully!", result, "Valid profile should register successfully");
        
        System.out.println("Registration success test passed");
    }
    
    @Test
    public void testCompleteRegistrationFailureUsername() {
        System.out.println("Testing registration failure due to invalid username...");
        
        AuthProfile invalidUsernameProfile = new AuthProfile(
            "Test", "User", "toolongusername", "Password123!", "+27123456789"
        );
        
        String result = invalidUsernameProfile.completeRegistration();
        assertEquals("Invalid user ID format. Must contain underscore and be ≤5 characters.", result);
        
        System.out.println("Registration username failure test passed");
    }
    
    @Test
    public void testCompleteRegistrationFailurePassword() {
        System.out.println("Testing registration failure due to invalid password...");
        
        AuthProfile invalidPasswordProfile = new AuthProfile(
            "Test", "User", "usr_1", "weak", "+27123456789"
        );
        
        String result = invalidPasswordProfile.completeRegistration();
        assertEquals("Security code must be 8+ characters with uppercase, number, and special character.", result);
        
        System.out.println("Registration password failure test passed");
    }
    
    @Test
    public void testCompleteRegistrationFailureMobile() {
        System.out.println("Testing registration failure due to invalid mobile...");
        
        AuthProfile invalidMobileProfile = new AuthProfile(
            "Test", "User", "usr_1", "Password123!", "123456"
        );
        
        String result = invalidMobileProfile.completeRegistration();
        assertEquals("Invalid mobile format. Use +27 followed by 9 digits.", result);
        
        System.out.println("Registration mobile failure test passed");
    }

    // =============== AUTHENTICATION TESTS ===============
    
    @Test
    public void testVerifyCredentialsSuccess() {
        System.out.println("Testing successful credential verification...");
        
        String result = validProfile.verifyCredentials("jd_01", "MyPass123!");
        assertEquals("Welcome John, Doe! Great to see you again.", result);
        
        System.out.println("Credential verification success test passed");
    }
    
    @Test
    public void testVerifyCredentialsFailureUsername() {
        System.out.println("Testing credential verification failure - wrong username...");
        
        String result = validProfile.verifyCredentials("wrong_user", "MyPass123!");
        assertEquals("Authentication failed. Please check your credentials.", result);
        
        System.out.println("Credential verification username failure test passed");
    }
    
    @Test
    public void testVerifyCredentialsFailurePassword() {
        System.out.println("Testing credential verification failure - wrong password...");
        
        String result = validProfile.verifyCredentials("jd_01", "WrongPassword");
        assertEquals("Authentication failed. Please check your credentials.", result);
        
        System.out.println("Credential verification password failure test passed");
    }
    
    @Test
    public void testVerifyCredentialsFailureBoth() {
        System.out.println("Testing credential verification failure - both wrong...");
        
        String result = validProfile.verifyCredentials("wrong_user", "WrongPassword");
        assertEquals("Authentication failed. Please check your credentials.", result);
        
        System.out.println("Credential verification both wrong test passed");
    }

    // =============== EDGE CASE TESTS ===============
    
    @Test
    public void testUsernameBoundaryConditions() {
        System.out.println("Testing username boundary conditions...");
        
        // Test exactly 5 characters with underscore
        AuthProfile exactly5 = new AuthProfile("Test", "User", "ab_12", "Password123!", "+27123456789");
        assertTrue(exactly5.validateUsernameFormat(), "Exactly 5 characters with underscore should be valid");
        
        // Test 6 characters with underscore (should fail)
        AuthProfile exactly6 = new AuthProfile("Test", "User", "abc_12", "Password123!", "+27123456789");
        assertFalse(exactly6.validateUsernameFormat(), "6 characters should be invalid");
        
        System.out.println("Username boundary tests passed");
    }
    
    @Test
    public void testPasswordBoundaryConditions() {
        System.out.println("Testing password boundary conditions...");
        
        // Test exactly 8 characters with all requirements
        AuthProfile exactly8 = new AuthProfile("Test", "User", "usr_1", "Abc123!@", "+27123456789");
        assertTrue(exactly8.validatePasswordComplexity(), "Exactly 8 chars with requirements should be valid");
        
        // Test 7 characters with all requirements (should fail)
        AuthProfile exactly7 = new AuthProfile("Test", "User", "usr_1", "Abc12!@", "+27123456789");
        assertFalse(exactly7.validatePasswordComplexity(), "7 characters should be invalid");
        
        System.out.println("Password boundary tests passed");
    }

    // =============== INTEGRATION TESTS ===============
    
    @Test
    public void testFullRegistrationAndAuthenticationFlow() {
        System.out.println("Testing full registration and authentication flow...");
        
        // Step 1: Register a user
        AuthProfile newUser = new AuthProfile("Alice", "Johnson", "aj_99", "SecurePass456!", "+27555123456");
        String registrationResult = newUser.completeRegistration();
        assertEquals("Regstration successfully!", registrationResult, "Registration should succeed");
        
        // Step 2: Authenticate the registered user
        String authResult = newUser.verifyCredentials("aj_99", "SecurePass456!");
        assertEquals("Welcome Alice, Johnson! Great to see you again.", authResult, "Authentication should succeed");
        
        // Step 3: Try with wrong credentials
        String wrongAuthResult = newUser.verifyCredentials("aj_99", "WrongPassword");
        assertEquals("Authentication failed. Please check your credentials.", wrongAuthResult, "Wrong password should fail");
        
        System.out.println("Full flow integration test passed");
    }
    
    @Test
    public void testMultipleValidationErrors() {
        System.out.println("Testing profile with multiple validation errors...");
        
        // This profile has multiple issues but should fail on the first validation (username)
        AuthProfile multipleErrors = new AuthProfile(
            "Test", "User", 
            "verylongusernamewithnounderscore", // Invalid username
            "short", // Invalid password
            "invalidphone" // Invalid mobile
        );
        
        String result = multipleErrors.completeRegistration();
        assertEquals("Invalid user ID format. Must contain underscore and be ≤5 characters.", result,
                    "Should fail on first validation error (username)");
        
        System.out.println("Multiple validation errors test passed");
    }

    // =============== CONSTRUCTOR AND INITIALIZATION TESTS ===============
    
    @Test
    public void testLoginandRegistrationConstructor() {
        System.out.println("Testing LoginandRegistration constructor...");
        
        assertDoesNotThrow(() -> {
            LoginandRegistration app = new LoginandRegistration();
            assertNotNull(app, "LoginandRegistration object should be created successfully");
        }, "Constructor should not throw exceptions");
        
        System.out.println("Constructor test passed");
    }
    
    @Test
    public void testAuthProfileConstructor() {
        System.out.println("Testing AuthProfile constructor...");
        
        assertDoesNotThrow(() -> {
            AuthProfile profile = new AuthProfile("First", "Last", "usr_1", "Pass123!", "+27123456789");
            assertNotNull(profile, "AuthProfile object should be created successfully");
        }, "AuthProfile constructor should not throw exceptions");
        
        System.out.println("AuthProfile constructor test passed");
    }
}