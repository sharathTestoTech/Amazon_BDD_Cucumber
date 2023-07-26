package com.amazon.automation.parallel;

import java.util.HashMap;
import java.util.Properties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.amazon.automation.factory.DriverFactory;
import com.amazon.automation.pages.Verifytheaccountforlogin;
import com.amazon.automation.util.ConfigReader;
import com.amazon.automation.util.JSONUtils;
import com.amazon.automation.util.Loggers;
import com.amazon.automation.util.ScreenshotUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import junit.framework.Assert;

public class VerifyLoginaccount extends DriverFactory{
	
	
	private Loggers logger;
	private ScreenshotUtility screenshotutility;
	private ConfigReader config;
	private Verifytheaccountforlogin verify;
	private JSONUtils jsonutil;
	static HashMap<String, String> mandatoryErrorMessagesTestData;
	static HashMap<String, String> ProfiletestData;
	private String[] fullClassName = this.getClass().getName().split("[.]");
	private String className = this.getClass().getName().split("[.]")[fullClassName.length - 1];
	Properties prop;

	
	@Given("as a user login for amazon website")
	public void as_a_user_login_for_amazon_website() throws Exception {
		logger = new Loggers();
		logger.configureLoggerSystem(new Throwable().getStackTrace()[0].getClassName());
	
		config = new ConfigReader(logger.loggingInstance());
		config.initprop();
		
		screenshotutility = new ScreenshotUtility(logger.loggingInstance());
		verify=new Verifytheaccountforlogin(getDriver(),logger.loggingInstance());
		jsonutil =new JSONUtils();
		mandatoryErrorMessagesTestData=jsonutil.getNestedTestDataInMap(config.readMandatoryJsonFilePath(),"MandatoryMessage_CreateAccount");
		System.out.println("mandatoryErrorMessagesTestData is: "+mandatoryErrorMessagesTestData);
		ProfiletestData=jsonutil.getNestedTestDataInMap(config.writeJsonFilePath(),"Profile");
		try {	
			CommonStep.scenario.log("Launching Application");
		getDriver().get(config.readLoginURL());
	logger.setLoggerInfo("Launching the URL");
	screenshotutility.captureScreenshot(className, "LaunchURL");
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@Then("as a  user click signin")
	public void as_a_user_click_signin() throws Exception {
		try {
			CommonStep.scenario.log("Landing on Home screen & clicking Signin");
			 //updating into profile object
			 ProfiletestData.put("user","Upstream.gs");
			 ProfiletestData.put("FirstName","Upstream");
			 ProfiletestData.put("LastName","Gs");
			 jsonutil.writing(config.writeJsonFilePath(),"Profile",ProfiletestData );
			
	verify.clickonsigin();
	logger.setLoggerInfo("ClickonSigin");
	screenshotutility.captureScreenshot(className, "Clickonsigin");	
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@Then("as a user click create a account")
	public void as_a_user_click_create_a_account() throws Exception {
	try {
		CommonStep.scenario.log("clicking on create account");
		verify.clickcreateaccount();
	logger.setLoggerInfo("Clickcreateaccount");
	screenshotutility.captureScreenshot(className, "Createaccount");
	verify.clickcontinue();
	logger.setLoggerInfo("Clickconitnue");
	screenshotutility.captureScreenshot(className, "ClickContinue");
	}
	catch(Exception e) {
		logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
		logger.closeTheHandler();
		throw new Exception(e);
	}
	}

	@Then("as a user Verify the username Validate the Message")
	public void as_a_user_verify_the_username_validate_the_message() throws Exception {
	try {
		CommonStep.scenario.log("Validating the Mandatory Message for user Name");
		String actual = verify.verifyname();
		Assert.assertEquals(mandatoryErrorMessagesTestData.get("userName"), actual);
		
		//	Assert.assertEquals(expSucces, actual);
		logger.setLoggerInfo(actual);
		screenshotutility.captureScreenshot(className, "Message1");
	}
	catch(Exception e) {
		logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
		logger.closeTheHandler();
		throw new Exception(e);
	}
	}

	@Then("as a user Verify the Mobilenumber Validate the Message")
	public void as_a_user_verify_the_mobilenumber_validate_the_message() throws Exception {
		try {
			CommonStep.scenario.log("Validating the Mandatory Message for Mobile Number");
		String actual = verify.verifyemailandnumber();
		Assert.assertEquals(mandatoryErrorMessagesTestData.get("mobileNumber"),actual);
	//	Assert.assertEquals(actual2, expSuccess1);
		logger.setLoggerInfo(actual);
		screenshotutility.captureScreenshot(className, "Message2");
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
}

	@Then("as a user Verify the Password Validate the Message")
	public void as_a_user_verify_the_password_validate_the_message() throws Exception {
		try {
		CommonStep.scenario.log("Validating the Mandatory Message for Password");
		String actual = verify.verifythepassword();
		Assert.assertEquals(mandatoryErrorMessagesTestData.get("password"),actual);
		//Assert.assertEquals(actual3, expSuccess2);
		logger.setLoggerInfo(actual);
		screenshotutility.captureScreenshot(className, "Message3");
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
