package com.amazon.automation.pages;

import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.automation.factory.DriverFactory;
import com.amazon.automation.util.ConfigReader;
import com.amazon.automation.util.ElementUtil;



public class ProductPage extends ElementUtil {
	
	private WebDriver driver;
	private Logger logg;
	
	public ProductPage(WebDriver driver,Logger logg) {
		super(driver, logg);
		this.logg=logg;
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//input[@id='GLUXZipUpdateInput']") WebElement pin;
	  @FindBy(xpath="//div[@id='a-popover-content-1']") WebElement PincodeBox;
		@FindBy(xpath="//span[@id='GLUXZipUpdate']")WebElement Apply;
		@FindBy(id="nav-signin-tooltip") WebElement siginbutton;
		@FindBy(xpath="/html/body/div[5]/div/div/div[2]/span/span/input") WebElement Continuepincode;
		@FindBy(id="ap_email") WebElement username;
		@FindBy(id="continue") WebElement cont;
		@FindBy(id="ap_password") WebElement password;
		@FindBy(id="signInSubmit") WebElement submitbutton;
		@FindBy(xpath="//a[@id='nav-global-location-popover-link']") WebElement PincodeChange;
		@FindBy(id="twotabsearchtextbox") WebElement Searchtextbox;
		@FindBy(id="nav-search-submit-button") WebElement submitsearch;
//		@FindBy(xpath="(//span[contains(@id,'amazons-choice-label')])[1]") WebElement item1;
		@FindBy(xpath="//input[@id='add-to-cart-button']") WebElement addtocart;
		@FindBy(xpath="//div[@id='attach-desktop-sideSheet']") WebElement CartinteruptPopup;
		@FindBy(xpath="//a[@id='attach-close_sideSheet-link']") WebElement closePopup;
		@FindBy(xpath="//div[@id='attach-warranty-pane']") WebElement Cartinteruptpopup2;
		@FindBy(xpath="//span[@id='attachSiNoCoverage-announce']") WebElement nothankspopup;
		@FindBy(id="nav-cart-text-container") WebElement validateaddtocart;
		@FindBy(id="nav-link-accountList-nav-line-1") WebElement page;
		@FindBy(xpath="//*[text()='Edit addresses for orders and gifts']") WebElement youraddress;
		@FindBy(id="address-ui-widgets-enterAddressPostalCode") WebElement postalcode;
		@FindBy(id="address-ui-widgets-enterAddressLine1") WebElement addressline1;
		@FindBy(id="address-ui-widgets-enterAddressLine2") WebElement addressline2;
		@FindBy(id="address-ui-widgets-landmark") WebElement landmark;
	@FindBy(xpath="//input[@id='GLUXZipUpdateInput']") WebElement Enterpincode;
		@FindBy(id="address-ui-widgets-enterAddressCity") WebElement cityname;
		@FindBy(id="address-ui-widgets-enterAddressStateOrRegion") WebElement statename;
		@FindBy(id="ya-myab-plus-address-icon") WebElement addaddress;
		@FindBy(xpath="//*[@id='address-ui-widgets-form-submit-button']/span/input") WebElement addaddress2;
		@FindBy(id="address-ui-widgets-enterAddressFullName") WebElement fullname;
		@FindBy(id="address-ui-widgets-enterAddressPhoneNumber") WebElement phonenumber;
		@FindBy(xpath="//select[@id='quantity']")
		public WebElement ddquantity;
		@FindBy(xpath=".//*[@id='nav-link-accountList']") WebElement ActionEle;
		@FindBy(xpath=".//*[@id='nav-al-your-account']") WebElement ActionEle1;
		@FindBy(xpath="//span[contains(text(),'Sign Out')]") WebElement signout;
		
		@FindBy(xpath="//input[@value='Delete']") WebElement delete;
		@FindBy(name="proceedToRetailCheckout") WebElement proceedcheckout;
		@FindBy(xpath="//h1[@class='a-spacing-base']	") WebElement selectAddress;
		@FindBy(xpath="//span[@class='a-dropdown-prompt']") WebElement quantityselected;
		@FindBy(xpath="//a[contains(text(),'Go to')]") WebElement gotocart;
		@FindBy(xpath="//input[@value='Save for later']") WebElement saveforlater;


		@FindBy(xpath="//a[contains(@aria-label,'Choose a language for shopping.')]") WebElement ddcountry;
		@FindBy(xpath="//div[contains(text(),'Change country/region.')]") WebElement changecountrylink;
		@FindBy(xpath="//select[@id='icp-dropdown']") WebElement ddcountryselect;
	@FindBy(xpath="//body/div[4]/div[1]/div[1]/div[2]/span[1]/span[1]") WebElement Pincodedone;
	@FindBy(xpath="//div[@data-cel-widget='MAIN-SEARCH_RESULTS-2']//div[contains(@class,'product-image-container')]") WebElement item2;
	@FindBy(xpath="//div[@data-cel-widget='MAIN-SEARCH_RESULTS-3']//div[contains(@class,'product-image-container')]") WebElement item3;
	@FindBy(xpath="//div[@data-cel-widget='MAIN-SEARCH_RESULTS-1']//div[contains(@class,'product-image-container')]") WebElement item1;
	@FindBy(xpath="//span[contains(@id,'amazons-choice')]") WebElement amazonschoice;
	@FindBy(xpath="//div[@id='nav-flyout-ewc']") WebElement flyout;
	@FindBy(xpath="//a[@id='a-autoid-0-announce']") WebElement gotocartflyout;
	
	public void searchproduct(String  searchele) {
		try {
		webEditTxtChange(Searchtextbox, searchele);
		Thread.sleep(2000);
		highlightElement(driver, Searchtextbox);
		Thread.sleep(3000);
		highlightElement(driver, submitsearch);
		clickElementUsingActions(submitsearch);
		Thread.sleep(4000);
		if(item3.isDisplayed()) {
			highlightElement(driver, item3);
			clickElementUsingActions(item3);
		}
		}
		catch(Exception e){
			System.out.println("Error in "+e.getMessage());
		}
	}
	
	
	public void inputQuantity(WebElement dd,String quantityvalue) throws  Exception{
		WebDriverWait ww =new WebDriverWait(driver,Duration.ofSeconds(3000));
		ww.until(ExpectedConditions.elementToBeClickable(dd));
		Select quantity = new Select(dd);
		quantity.selectByValue(quantityvalue);
		Thread.sleep(2000);
	}
	public void NavigatetoCart() throws InterruptedException {

			Thread.sleep(3000);
		highlightElement(driver, addtocart);
		Thread.sleep(2000);
		clickElementUsingActions(addtocart);
		Thread.sleep(2000);
		
		try {
		if(Cartinteruptpopup2.isDisplayed()) {
			if(nothankspopup.isDisplayed()) {
			clickElementUsingActions(nothankspopup);
			Thread.sleep(2000);
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(CartinteruptPopup.isDisplayed()) {
				clickElementUsingActions(closePopup);
				Thread.sleep(2000);
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		try {
			if(flyout.isDisplayed()) {
				clickElementUsingActions(gotocartflyout);
				Thread.sleep(2000);
			}}
			catch (Exception e) {
				 e.printStackTrace();
			}
		highlightElement(driver, validateaddtocart);
		Thread.sleep(2000);
		clickElementUsingActions(validateaddtocart);
		Thread.sleep(2000);
		
	}
		public void NavigatetoCheckout() throws  Exception{
		Thread.sleep(3000);
		highlightElement(driver, addtocart);
		Thread.sleep(2000);
		clickElementUsingActions(addtocart);
		Thread.sleep(2000);
		highlightElement(driver, proceedcheckout);
		Thread.sleep(2000);
		clickElementUsingActions(proceedcheckout);
		Thread.sleep(2000);		
			}
		public void changepincode() {
			highlightElement(driver, PincodeChange);
			clickElementUsingActions(PincodeChange);
			clickElementUsingActions(PincodeBox);

		}
		
public void EnterPincode(String enterpinvalue) {
	driver.findElement(By.xpath("//input[@id='GLUXZipUpdateInput']")).sendKeys(enterpinvalue);
}
public void applypincode() throws Exception {
	clickElementUsingActions(Apply);
	Thread.sleep(2000);
//clickElementUsingActions(Pincodedone);
clickElementUsingActions(Continuepincode);


}
	public void ValidateCart(String Expectedquantityvalue) throws  Exception{
		highlightElement(driver, quantityselected);
		String Actualquantityvalue=quantityselected.getText();
		if(Actualquantityvalue.equalsIgnoreCase(Expectedquantityvalue)) {
			System.out.println("Pass for validating the cart");
		}
		else {
			System.out.println("Fail for validating the cart"+Expectedquantityvalue);
		}
	}
	public void delete() throws Exception{
		highlightElement(driver, delete);
		clickElementUsingActions(delete);
		Thread.sleep(3000);
	}
public void Login(String usernameValue, String passwordValue) throws Exception { 
//	DriverFactory.getDriver().get(ConfigReader.readLoginURL());
	//driver.get(ConfigReader.readLoginURL());
	Thread.sleep(1000);
	highlightElement(driver, siginbutton);
	clickElementUsingActions(siginbutton);
	Thread.sleep(1000);		
	highlightElement(driver,username);
	webEditTxtChange(username,usernameValue);
	Thread.sleep(1000);
	highlightElement(driver, cont);
	clickElementUsingActions(cont);
	Thread.sleep(1000);
	highlightElement(driver, password);
	webEditTxtChange(password,passwordValue);
	Thread.sleep(1000);
	highlightElement(driver, submitbutton);
	clickElementUsingActions(submitbutton);
	Thread.sleep(1000);
}
public  void item1() throws Exception{
	//Item1
	searchproduct("Amazon Essentials Women's Digital Chronograph Resin Strap Watch");
			NavigatetoCart();
			ValidateCart("1");
			Thread.sleep(3000);
}
public  void item2() throws Exception{
	//Item2
			searchproduct("Mobile");
			inputQuantity(ddquantity, "2");
			NavigatetoCart();
			ValidateCart("2");
			Thread.sleep(3000);
}
public  void item3() throws Exception{
			//Item3
			searchproduct("mouse");
			NavigatetoCart();
			Thread.sleep(3000);
			delete();
}
public  void item4() throws Exception{
			//Item4
			searchproduct("Watch");
			NavigatetoCheckout();
			validateshippingscreen();
			navigateback();
			saveforlaterandgotocart();
			
}
public void validateshippingscreen() throws InterruptedException {
	highlightElement(driver,selectAddress);
	if(selectAddress.getText().equalsIgnoreCase("Select a shipping address")) {
		System.out.println("Pass");
	}
	else {
		System.out.println("Fail");
	}
	Thread.sleep(3000);

}
public void navigateback() throws InterruptedException {
	driver.navigate().back();
	Thread.sleep(2000);
}	
public void saveforlaterandgotocart() throws InterruptedException {
	Thread.sleep(2000);
	/*highlightElement(driver, gotocart);
	Thread.sleep(2000);
	clickElementUsingActions(gotocart);
	Thread.sleep(3000);*/
	highlightElement(driver, saveforlater);
	clickElementUsingActions(saveforlater);
	Thread.sleep(3000);
}
}

