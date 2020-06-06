package ua.external.util;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PasswordEncryptorTest {

    @Test
    public void shouldReturnTrueForPasswordAndHash() throws NoSuchAlgorithmException {
        String actual = PasswordEncryptor.encrypt("password");
        String expected = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnFalseForPasswordAndHash() throws NoSuchAlgorithmException {
        String actual = PasswordEncryptor.encrypt("password");
        String expected = "password";

        assertNotEquals(expected, actual);
    }

}