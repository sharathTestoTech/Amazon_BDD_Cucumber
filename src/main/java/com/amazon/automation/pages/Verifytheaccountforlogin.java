package com.amazon.automation.pages;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.automation.util.ElementUtil;

public class Verifytheaccountforlogin extends ElementUtil{
	
	
	private WebDriver driver;
	private Logger logg;

	@FindBy(id="nav-signin-tooltip") WebElement login;
	@FindBy(id="createAccountSubmit") WebElement Createaccount;
	@FindBy(id="continue") WebElement Clickcontinue;

  @FindBy(id="auth-customerName-missing-alert") WebElement Verifythename;
	@FindBy(id="auth-email-missing-alert") WebElement Verifytheemail;
	@FindBy(id="auth-password-missing-alert") WebElement Verifythepassword;
	
	
	public Verifytheaccountforlogin(WebDriver driver,Logger logg) {
		super(driver, logg);
		this.logg=logg;
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickonsigin() {
		highlightElement(driver, login);
		clickElementUsingActions(login);
		}
	
	public void clickcreateaccount() {
		highlightElement(driver, Createaccount);
		clickElementUsingActions(Createaccount);
	}
	
	public void clickcontinue() {
		highlightElement(driver, Clickcontinue);
	clickElementUsingActions(Clickcontinue);
	}
	
	public String verifyname() {
		return	FetchTextBoxValuewithText(Verifythename);
	//return Verifythename.getText();
	}

	public String verifyemailandnumber() {
		return	FetchTextBoxValuewithText(Verifytheemail);
		//return Verifytheemail.getText();
	}

	public String verifythepassword() {
		return	FetchTextBoxValuewithText(Verifythepassword);
		//return Verifythepassword.getText();
	}
}
