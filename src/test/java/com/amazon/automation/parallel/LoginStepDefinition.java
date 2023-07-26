package com.amazon.automation.parallel;


import java.util.Properties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Assert;
import com.amazon.automation.pages.LoginPage;
import com.amazon.automation.util.ConfigReader;
import com.amazon.automation.util.Loggers;
import com.amazon.automation.util.ScreenshotUtility;
import com.amazon.automation.util.ExcelUtil;
import com.amazon.automation.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition extends DriverFactory {
	private Loggers logger;
	private ScreenshotUtility screenshotutility;
	private LoginPage loginPage;
	private ConfigReader config;
	private int readRowNo = 1;
	private int writeRowNo;
	private ExcelUtil excelutil = null;
	private ExcelUtil excelutilWrite = null; 
	private String[] fullClassName = this.getClass().getName().split("[.]");
	private String className = this.getClass().getName().split("[.]")[fullClassName.length - 1];
	Properties prop;

	@Given("user is on login page")
	public void user_is_on_login_page() throws Exception {
		logger = new Loggers();
		logger.configureLoggerSystem(new Throwable().getStackTrace()[0].getClassName());
		
		config = new ConfigReader(logger.loggingInstance());
		loginPage =new LoginPage(getDriver(),logger.loggingInstance());
		screenshotutility = new ScreenshotUtility(logger.loggingInstance());
		logger.setLoggerInfo(" Test Execution is about to begin for "+className+" ");
		config.initprop();
		CommonStep.scenario.log("Launch the application using URL");
		try {
			CommonStep.scenario.log("Launching Application");
			excelutil = new ExcelUtil(config.readExcel(),logger.loggingInstance());
			excelutilWrite = new ExcelUtil(config.writeExcel(),logger.loggingInstance());
			writeRowNo = excelutilWrite.getEmptyRowNumber(config.writeExcel(),"Login");
		getDriver().get(config.readLoginURLIndia());
		screenshotutility.captureScreenshot(className, "Clicksignin");
		loginPage.clickonsigin();
		}
		catch(Exception e) {
			logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
			logger.closeTheHandler();
			throw new Exception(e);
		}
	}

	@When("user enter Username")
	public void user_enter_username() throws Exception {
		try {
			CommonStep.scenario.log("Enter userName");
			loginPage.enterUserName(excelutil.getCellData("Login", "userName", readRowNo));
		screenshotutility.captureScreenshot(className, "EnterUsername");
		logger.setLoggerInfo("Enter Username");
	excelutilWrite.setCellData(config.writeExcel(),"Login","userName",writeRowNo,excelutil.getCellData("Login", "userName", readRowNo));
		
		loginPage.clickoncontinue();
		screenshotutility.captureScreenshot(className, "Clickcontinue");
		logger.setLoggerInfo("Click continue");
	}
	catch(Exception e) {
		logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
		logger.closeTheHandler();
		throw new Exception(e);
	}
	}

	@When("user enter Password")
	public void user_enter_password() throws Exception {
		try {
			CommonStep.scenario.log("Enter password");
			loginPage.enterPassword(excelutil.getCellData("Login", "Password", readRowNo));
		screenshotutility.captureScreenshot(className, "EnterPassword");
		logger.setLoggerInfo("Enter Password");
		excelutilWrite.setCellData(config.writeExcel(),"Login","Password",writeRowNo,excelutil.getCellData("Login", "Password", readRowNo));
	}
	catch(Exception e) {
		logger.setLoggerInfo(className+ExceptionUtils.getStackTrace(e));
		logger.closeTheHandler();
		throw new Exception(e);
	}
	}
	@Then("forgot your password link should be displayed")
	public void forgot_your_password_link_should_be_displayed() throws Exception {
		try {
			CommonStep.scenario.log("Validate Forgot Your Password");
		Assert.assertFalse(loginPage.isForgotPwdLinkExist());
		screenshotutility.captureScreenshot(className, "CheckMessage");
		logger.setLoggerInfo("Check Message");
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

