package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signup")
    private WebElement submit;


    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signup(String fn,String ln,String un,String ps){
        firstname.sendKeys(fn);
        lastname.sendKeys(ln);
        username.sendKeys(un);
        password.sendKeys(ps);
        submit.click();

    }
}
