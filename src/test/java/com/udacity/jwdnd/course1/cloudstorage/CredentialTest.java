package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTest extends CloudStorageApplicationTests {

    private final String urlHttps1 = "www.wafaa.com";
    private final String username1 = "wafa";
    private final String password1 = "wafaPW";
    private final String urlHttps2 = "www.walaa.com";
    private final String username2 = "wala";
    private final String password2 = "walaPW";


    private void createCredential(String url, String username, String password, HomePage homePage) {
        homePage.navigateCredentialsTab();
        homePage.addNewCredential();

        setCredentialFields(url, username, password, homePage);
        homePage.saveCredential();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navigateCredentialsTab();
    }

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }


    @Test
    public void testCreateAndShow() {
        HomePage homePage = signUpAndLogin();
        createCredential(urlHttps1, username1, password1, homePage);
        Credential credential = homePage.getTopCredential();

        Assertions.assertEquals(urlHttps1, credential.getUrl());
        Assertions.assertEquals(username1, credential.getUsername());
        Assertions.assertNotEquals(password1, credential.getPassword());

        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        homePage.logout();
    }


    @Test
    public void testModify() {
        HomePage homePage = signUpAndLogin();
        createCredential(urlHttps1, username1, password1, homePage);

        homePage.editCredential();
        setCredentialFields(urlHttps2, username2, password2, homePage);
        homePage.saveCredential();

        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        homePage.navigateCredentialsTab();

        Credential modifiedCredential = homePage.getTopCredential();

        Assertions.assertEquals(urlHttps2, modifiedCredential.getUrl());
        Assertions.assertEquals(username2, modifiedCredential.getUsername());
        Assertions.assertNotEquals(password2, modifiedCredential.getPassword());

        homePage.deleteCredential();
        resultPage.clickOk();

        homePage.logout();
    }


    /**
     * Write a Selenium test that logs in an existing user with existing credentials,
     * clicks the delete credential button on an existing credential, and verifies
     * that the credential no longer appears in the credential list.
     */

    @Test
    public void testDelete(){
        HomePage homePage = signUpAndLogin();
        createCredential(urlHttps1, username1, password1, homePage);
        createCredential(urlHttps2, username2, password2, homePage);

        Assertions.assertFalse(!homePage.noCredentials(driver));

        homePage.deleteCredential();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();

        homePage.navigateCredentialsTab();
        Assertions.assertFalse(!homePage.noCredentials(driver));

        homePage.deleteCredential();
        resultPage.clickOk();

        homePage.navigateCredentialsTab();
        Assertions.assertTrue(homePage.noCredentials(driver));

        homePage.logout();
    }
}