package com.example.medibook;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.example.medibook.classes.User;

public class UserTest {

    private User user;
    private final String firstName = "John";
    private final String lastName = "Doe";
    private final String emailAddress = "john.doe@example.com";
    private final String accountPassword = "password123";
    private final String phoneNumber = "1234567890";
    private final String address = "123 Main Street";
    private final String status = "Confirmed";

    private final String userID = "user123";

    @Before
    public void setUp() {
        user = new User(firstName, lastName, emailAddress, accountPassword, phoneNumber, address, status, userID);
    }

    @Test
    public void testUserConstructorAndGetters() {
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(emailAddress, user.getEmail());
        assertEquals(accountPassword, user.getPassword());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(address, user.getAddress());
        assertEquals(status, user.getStatus());
        assertEquals(userID, user.getUserId());
    }

    @Test
    public void testSetters() {
        String newFirstName = "Jane";
        String newLastName = "Smith";
        String newEmailAddress = "jane.smith@example.com";
        String newAccountPassword = "newpassword123";
        String newPhoneNumber = "0987654321";
        String newAddress = "321 Side Street";
        String newStatus = "Inactive";
        String newUserID = "newuser123";

        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setEmail(newEmailAddress);
        user.setPassword(newAccountPassword);
        user.setPhoneNumber(newPhoneNumber);
        user.setAddress(newAddress);
        user.setStatus(newStatus);
        user.setUserId(newUserID);

        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
        assertEquals(newEmailAddress, user.getEmail());
        assertEquals(newAccountPassword, user.getPassword());
        assertEquals(newPhoneNumber, user.getPhoneNumber());
        assertEquals(newAddress, user.getAddress());
        assertEquals(newStatus, user.getStatus());
        assertEquals(newUserID, user.getUserId());
    }
}
