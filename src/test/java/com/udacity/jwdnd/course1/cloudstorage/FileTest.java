package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileTest extends CloudStorageApplicationTests {
    private void uploadFile(HomePage homePage){
        homePage.navigateFilesTab();
        homePage.uploadNewFile();
        homePage.submitUploadFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navigateFilesTab();
    }

    @Test
    public void testFileUpload(){
        HomePage homePage = signUpAndLogin();
        uploadFile(homePage);
        homePage = new HomePage(driver);
        homePage.navigateFilesTab();

        deleteFile(homePage);
        homePage.logout();
    }

    @Test
    public void testDelete(){
        HomePage homePage = signUpAndLogin();
        uploadFile(homePage);
        homePage.navigateFilesTab();
        homePage = new HomePage(driver);
        Assertions.assertFalse(homePage.noFiles(driver));
        deleteFile(homePage);
        Assertions.assertTrue(homePage.noFiles(driver));
    }

    private void deleteFile(HomePage homePage) {
        homePage.deleteFile();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
    }


}
