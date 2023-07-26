package com.amazon.automation.parallel;

import java.util.HashMap;
/*
 * read data from excel,json
 * cross check logger text
 * replace thread.sleep with sleeptime
 * 
 */
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.amazon.automation.factory.DriverFactory;
import com.amazon.automation.pages.ProductPage;
import com.amazon.automation.util.ConfigReader;
import com.amazon.automation.util.Loggers;
import com.amazon.automation.util.ScreenshotUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import com.amazon.automation.util.JSONUtils;

public class ProductStepDefinition extends DriverFactory {
	private Loggers logger;
	private ScreenshotUtility screenshotutility;
	private ConfigReader config;
	private ProductPage productobj;
	private JSONUtils jsonutil;
	private String[] fullClassName = this.getClass().getName().split("[.]");
	private String className = this.getClass().getName().split("[.]")[fullClassName.length - 1];
	Properties prop;
	static HashMap<String, String> changePinTestData;
//	static HashMap<String, String> ProfiletestData;
	
	
	@Given("user is on application")
	public void user_is_on_application() throws Exception {
		logger = new Loggers();
		logger.configureLoggerSystem(new Throwable().getStackTrace()[0].getClassName());
		config = new ConfigReader(logger.loggingInstance());
		config.initprop();
		screenshotutility = new ScreenshotUtility(logger.loggingInstance());
		productobj=new ProductPage(getDriver(),logger.loggingInstance());
		jsonutil =new JSONUtils();
		logger.setLoggerInfo(" Test Execution is about to begin for "+className+" ");
		changePinTestData=jsonutil.getNestedTestDataInMap(config.readAddressJsonFilePath(),"ChangeAddress");
	//	ProfiletestData=jsonutil.getNestedTestDataInMap(config.writeJsonFilePath(),"Profile");
		getDriver().get(config.readLoginURL());
		
		try {
		productobj.changepincode();
		screenshotutility.captureScreenshot(className, "ChangePincode");
		logger.setLoggerInfo("Change Pincode");
		Thread.sleep(2000);
		productobj.EnterPincode(changePinTestData.get("USPinCode"));
		
		//productobj.EnterPincode("94016");
		screenshotutility.captureScreenshot(className, "EnterPincode");
		logger.setLoggerInfo("Enter Pincode:"+"94016");
		Thread.sleep(2000);
		 //updating into profile object
	/*	 ProfiletestData.put("user","madhuri.sai");
		 ProfiletestData.put("FirstName","SSS");
		 ProfiletestData.put("LastName","MMMM");
		 jsonutil.writing(config.writeJsonFilePath(),"Profile",ProfiletestData );
		*/
		productobj.applypincode();
		screenshotutility.captureScreenshot(className,"Applypincode");
		logger.setLoggerInfo("Apply pincode");	
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@Then("user search the product {string}")
	public void user_search_the_product(String string) throws Exception {
		try {
		productobj.searchproduct(string);
		screenshotutility.captureScreenshot(className,"Searchproducts");
		logger.setLoggerInfo("Search products: "+string);
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@Then("product add to the cart")
	public void product_add_to_the_cart() throws Exception {
		try {
		Thread.sleep(2000);
		productobj.NavigatetoCart();	
		screenshotutility.captureScreenshot(className, "Navigatetocart");
		logger.setLoggerInfo("Navigate to product");
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
}

	@Then("user validates the product with quantity {string} in the cart")
	public void user_validates_the_product_with_quantity_in_the_cart(String string) throws Exception {
		try {
		productobj.ValidateCart(string);
		Thread.sleep(3000);
		screenshotutility.captureScreenshot(className, "Validatecart");
		logger.setLoggerInfo("validate cart:"+string);
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
		}

	@Then("increase the quantity of the product by {int} in cart")
	public void increase_the_quantity_of_the_product_by_in_cart(Integer int1) throws Exception {
		try {
		productobj.inputQuantity(productobj.ddquantity , "2");
	screenshotutility.captureScreenshot(className, "Inputquantity");
	logger.setLoggerInfo("Input quantity");
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
}
	@Then("user deletes the product {string}")
	public void user_deletes_the_product(String string) throws Exception {
		try {
		productobj.delete(); 
		screenshotutility.captureScreenshot(className, "Deleteproduct");
		logger.setLoggerInfo("delete product");
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@Then("product add to the cart to proceed to checkout")
	public void product_add_to_the_cart_to_proceed_to_checkout() throws Exception {
		try {
		Thread.sleep(2000);
	productobj.NavigatetoCheckout();  
	screenshotutility.captureScreenshot(className, "Navigatetocheckout");
	logger.setLoggerInfo("Navigate to checkout");
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@Then("navigate and validate the screen Select a shipping address")
	public void navigate_and_validate_the_screen_select_a_shipping_address() throws Exception {
		try {
		productobj.validateshippingscreen();
		screenshotutility.captureScreenshot(className, "validatetoshippingscreen");
		logger.setLoggerInfo("validate to shipping screen");
	   productobj.navigateback();
	   screenshotutility.captureScreenshot(className, "Navigateback");
	   logger.setLoggerInfo("navigate back");
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@Then("navigate to cart and validate the save later functionality")
	public void navigate_to_cart_and_validate_the_save_later_functionality() throws Exception {
		try {
	   productobj.saveforlaterandgotocart();
	   screenshotutility.captureScreenshot(className,"Saveforlater");
	   logger.setLoggerInfo("save for later");
	}
	catch(Exception e) {
		logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
		logger.closeTheHandler();
		throw new Exception(e);
	}
		//Maintain the finally block for the last method of test case
		finally {
			logger.closeTheHandler();
		}
	}
}
