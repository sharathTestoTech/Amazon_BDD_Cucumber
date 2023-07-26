package com.amazon.automation.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.amazon.automation.factory.DriverFactory;
import com.amazon.automation.util.ElementUtil;

public class LoginPage extends ElementUtil {

	private WebDriver driver;
	private Logger logg;

	// 1. By Locators: OR
			@FindBy(id="nav-signin-tooltip") WebElement login;
			@FindBy(id="ap_email") WebElement Userid;
			@FindBy(id="continue") WebElement cont;
			@FindBy(id="ap_password") WebElement Password;
			@FindBy(xpath="//a[@id='auth-fpp-link-bottom']") WebElement Forgotpwdlink;
			@FindBy(id="signInSubmit") WebElement singinbutton;
			@FindBy(xpath="//a[contains(text(),'United') and contains(text(),'States')]") WebElement changecountry;
			@FindBy(id="searchDropdownBox") WebElement alldepartment;
			@FindBy(xpath="//div[@class='nav-search-scope nav-sprite']") WebElement alldepartments;
			@FindBy(id="nav-hamburger-menu") WebElement Menutab;
			@FindBy(id="searchDropdownBox") WebElement	searchdd;
			@FindBy(id="nav-hamburger-menu") WebElement menutab;
			@FindBy(linkText="Amazon Music") WebElement Amazonmusic;
			@FindBy(linkText="Kindle E-readers & Books") WebElement kindle;
			@FindBy(linkText="Appstore for Android") WebElement appstore;
			@FindBy(linkText="Electronics") WebElement Electronics;
			@FindBy(linkText="Computers") WebElement computer;
			@FindBy(linkText="Smart Home") WebElement smarthome;
			@FindBy(linkText="Arts & Crafts") WebElement ArtsCraft;
			@FindBy(linkText="Gift Cards") WebElement gift;
			@FindBy(linkText="#FoundItOnAmazon") WebElement foundinamazon;
			@FindBy(linkText="Amazon Live") WebElement Amazonlive;
			@FindBy(linkText="International Shopping") WebElement internationalshopping;
			@FindBy(linkText="Your Account") WebElement youraccount;
			@FindBy(linkText="English") WebElement lang;
			@FindBy(linkText="Customer Service") WebElement custserv;
			@FindBy(linkText="Sign In") WebElement Sigin;

	// 2. Constructor of the page class:

	public LoginPage(WebDriver driver,Logger logg) {
		super(driver, logg);
		this.logg=logg;
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// 3. page actions: features(behavior) of the page the form of methods:

	public String getLoginPageTitle() {
		return driver.getTitle();
	}
	public void LaunchUrl(String url) {
		driver.get(url);
	}
	public void clickonsigin() {
		highlightElement(driver, login);
		clickElement(login);
	}

	public void enterUserName(String username) throws IOException {
		highlightElement(driver, Userid);
		webEditTxtChange(Userid, username);
	}
	
	public void clickoncontinue() {
		highlightElement(driver, cont);
		clickElement(cont);
	}
	
	public void enterPassword(String pswrd) throws InterruptedException {
		Thread.sleep(2000);
		highlightElement(driver, Password);
         webEditTxtChange(Password,pswrd);
	}
	
	public boolean isForgotPwdLinkExist() {
		highlightElement(driver, Forgotpwdlink);
		return Forgotpwdlink.isDisplayed();
	}
	
	public void clickOnLogin() {
		highlightElement(driver, singinbutton);
		clickElement(singinbutton);	
	}

	public void clickoncountrytab() {
		//highlightElement(driver, changecountry);
clickElementUsingJavscriptExecutor(changecountry);
	}
	
	public void validateonalldepartment()  {
		highlightElement(driver, alldepartments);
clickElement(alldepartments);	
		
		 WebElement alldep = driver.findElement(By.id("searchDropdownBox"));
	      
		 Select s = new Select(alldep);
		 s.selectByValue("search-alias=aps");
		 s.selectByValue("search-alias=arts-crafts-intl-ship");
		 s.selectByValue("search-alias=automotive-intl-ship");
		 s.selectByValue("search-alias=baby-products-intl-ship");
		 s.selectByValue("search-alias=beauty-intl-ship");
		 s.selectByValue("search-alias=stripbooks-intl-ship");
		 s.selectByValue("search-alias=fashion-boys-intl-ship");
		 s.selectByValue("search-alias=computers-intl-ship");
		 s.selectByValue("search-alias=deals-intl-ship");
		 s.selectByValue("search-alias=digital-music");
		 s.selectByValue("search-alias=electronics-intl-ship");
		 s.selectByValue("search-alias=fashion-girls-intl-ship");
		 s.selectByValue("search-alias=hpc-intl-ship");
		 s.selectByValue("search-alias=kitchen-intl-ship");
		 s.selectByValue("search-alias=industrial-intl-ship");
		 s.selectByValue("search-alias=digital-text");
		 s.selectByValue("search-alias=luggage-intl-ship");
		 s.selectByValue("search-alias=fashion-mens-intl-ship");
		 s.selectByValue("search-alias=movies-tv-intl-ship");
		 s.selectByValue("search-alias=music-intl-ship");
		 s.selectByValue("search-alias=pets-intl-ship");
		 s.selectByValue("search-alias=instant-video");
		 s.selectByValue("search-alias=software-intl-ship");
		 s.selectByValue("search-alias=sporting-intl-ship");
		 s.selectByValue("search-alias=tools-intl-ship");
		 s.selectByValue("search-alias=toys-and-games-intl-ship");
		 s.selectByValue("search-alias=videogames-intl-ship");
		 s.selectByValue("search-alias=fashion-womens-intl-ship");
		 
		 
	}
	public void validatemenutab() {
		highlightElement(driver, Menutab);
clickElement(Menutab);
highlightElement(driver, Amazonmusic);
highlightElement(driver, Amazonlive);
highlightElement(driver, kindle);
highlightElement(driver, appstore);
highlightElement(driver, Electronics);
highlightElement(driver, computer);
highlightElement(driver, smarthome);
highlightElement(driver, ArtsCraft);
highlightElement(driver, gift);
highlightElement(driver, foundinamazon);
highlightElement(driver, Amazonlive);
highlightElement(driver, internationalshopping);
highlightElement(driver, youraccount);
highlightElement(driver, lang);
highlightElement(driver, custserv);
highlightElement(driver, Sigin);
	
	}
}

