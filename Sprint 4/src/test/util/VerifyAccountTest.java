package util;

import com.kidsbank.util.VerifyAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerifyAccountTest {

    @Test
    public void testAccountNotExist(){
        String userName = "abc";
        assertTrue(VerifyAccount.notExist(userName));
    }

    @Test
    public void testAccountExist(){
        String userName = "maggie2@co.com";
        assertFalse(VerifyAccount.notExist(userName));
    }

    @Test
    public void testCorrectAccountPassword(){
        String username = "maggie2@co.com";
        String password = "pass";
        assertTrue(VerifyAccount.isValidAccount(username, password));
    }

    @Test
    public void testWrongAccountPassword(){
        String username = "maggie2@co.com";
        String password = "password";
        assertFalse(VerifyAccount.isValidAccount(username, password));
    }

    @Test
    public void testValidLinkAccountForParent(){
        String username = "maggie2@co.com";
        String linkcode = "123";
        assertFalse(VerifyAccount.isValidLinkAccount(username,linkcode));
    }

    @Test
    public void testValidLinkAccountForWrongChild(){
        String username = "maggie@co.com";
        String linkcode = "123";
        assertFalse(VerifyAccount.isValidLinkAccount(username,linkcode));
    }

    @Test
    public void testValidLinkAccountForCorrectChild(){
        String username = "maggie@co.com";
        String linkcode = "862618";
        assertTrue(VerifyAccount.isValidLinkAccount(username,linkcode));
    }






}
