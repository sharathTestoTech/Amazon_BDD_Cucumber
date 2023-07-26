package com.amazon.automation.util;
/**
 * This class contains the generic methods which can be used across all selenium projects
 *
 * @author 
 * @DateOfChange
 * @ChangedFunction/Event
 * @ChangeDescription
 *
 */

import java.io.FileReader;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;


public class ElementUtil extends ConfigReader{

	private  WebDriver driver;
	private Logger logg;
	private static WebDriverWait wait;
	public static String mainWindow;
	private static String reportname="Amazon-Spark";
	
	public ElementUtil(WebDriver driver, Logger logg) {
		super(logg);
		this.driver=driver;
		this.logg=logg;
	}

public  int getPageCount(PDDocument doc) {
	logg.info("Page Count is:"+doc.getNumberOfPages());
	return doc.getNumberOfPages();
}
public   void sleepTime(long milliseconds) {
	try {
		Thread.sleep(milliseconds);
	} catch (InterruptedException e) {
		logg.severe("Error in ElementUtil"+e);
	}
}
public String getTitle() {
	logg.info("Title is :"+driver.getTitle());
	return driver.getTitle();
}
public  Boolean isElementSelected(WebElement ele) {
	logg.info(ele+" is selected : "+ele.isSelected());
	return ele.isSelected();
	
}
public  void clearElement(WebElement ele) {
	ele.clear();
}
	/*
	 * @description : Read the data from JSON File & return JSONObject
	 * @param  : NA
	 * @return : JSON Object
	 * @author : 
	 */
	public  JSONObject getJSONObjectfromJSONFile(String jsonFile) throws IOException, ParseException {
		 JSONParser jsonparser = new JSONParser();
		  JSONObject jsonObject = null;
		try {
			 FileReader reader = new FileReader(jsonFile);
			Object obj = jsonparser.parse(reader);
			jsonObject = (JSONObject)obj;
		} catch (FileNotFoundException e) {
			logg.severe("Error in ElementUtil"+e);
		}
		return jsonObject;
	}
	
	/*
	 * @description : from JSONObject returns the value for the passed key
	 * @param  : key
	 * @return : value of respective key
	 * @author : 
	 */
	public  String getvalueFromJSONObject(String jsonFile,String key) throws IOException, ParseException {
		JSONObject jsonObject=getJSONObjectfromJSONFile(jsonFile);
		String keyvalue=(String) jsonObject.get(key);
		return keyvalue;

	}
	/*
	 * @description : takes the screenshot and save as a png file
	 * @param  : driver
	 * @param  : screenshot name
	 * @return : path of the saved screenshot
	 * @author : 
	 */
	public  String getScreenshot(WebDriver driver, String screenshotName) throws Exception {

		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.focus();");

		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String destination = null;

		// Current page screenshot
		{
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);

			//--to store in "TestResults" folder in same project Directory
			destination = System.getProperty("user.dir") + "/TestResults/FailedTestsScreenshots/"+screenshotName+dateName+".png";

			//--to store in "AutomationTestResult" folder in LAN path
			//destination = ConfigReader.readLogsDirectory()+"\\"+ConfigReader.getApplicationName()+"/FailedTestsScreenshots/"+screenshotName+dateName+".png";

			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
		}
	

		return destination;
	}
	/*
	 * @description : adds the input value to current date
	 * @param  : no of days to be added
	 * @return : added date in dd-MMM-yyyy
	 * @author : 
	 */
	public  String addDate(String days) {
		try {
			if(days.equals("\"\"")||days.equals("")) {
				return days;
			}
			else {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, Integer.parseInt(days));

				SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
				String newDate = format.format(cal.getTime());
				return newDate;
			}
		}
		catch(Exception e)
		{
			throw e;
		}

	}
	/*
	 * @description : adds the input value to current date
	 * @param  : no of days to be added
	 * @return : added date in YYYY-MM-DD
	 * @author : 
	 */
	public  String addDateInSQLDateFormat(String days) {
		try {
			if(days.equals("\"\"")||days.equals("-")||days.equals("")) {
				return days;
			}
			else {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, Integer.parseInt(days));

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String newDate = format.format(cal.getTime());
				return newDate;
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	/*
	 * @description : giving focus on the Slider Object element and clicks the element using X and Y coordinates
	 * @param  : Slider Element
	 * @return : NA
	 * @author : 
	 */
	public  void clickOnSliderElement(WebElement element, Duration timeOutInSeconds) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.focus();");

			wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.elementToBeClickable(element));

			WebElement sliderElement = element;
			new Actions(driver).moveToElement(sliderElement).click().perform();  // to give focus on the element
			// Instantiate Point class to get location of X and Y co-ordinates.
			Point point = sliderElement.getLocation();
			// Store value of elements as pixels in integers x and y
			int NumberX=point.getX();
			int NumberY=point.getY();
			Actions act= new Actions(driver);
			act.moveByOffset( NumberX + 1 , NumberY).click().build().perform();

		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : enters value of type Keys to a text box or text area
	 * @param  : keys
	 * @param  : input value
	 * @param  : timeout for element to be present in seconds
	 * @return : NA
	 * @author : 
	 */
	public   void webEditTxtkeys(WebElement inputObject,Keys value) {
		try
		{
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.elementToBeClickable(inputObject));
				inputObject.sendKeys(value);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	/*
	 * @description : enters value to a text box or text area
	 * @param  : text element
	 * @param  : input value
	 * @return : NA
	 * @author : 
	 */
	public   void webEditTxtChange(WebElement inputObject,String value) {
		try
		{
			logg.info("Log Info From ElementUtil "+value +" is entering into ");
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(inputObject));
			
			if(!value.equals("")) {
				wait.until(ExpectedConditions.elementToBeClickable(inputObject));
				inputObject.clear();

				if(!value.equals("\"\"")) {
					inputObject.sendKeys(value);
				
							 }

						 }
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	/*
	 * @description : check a checkbox
	 * @param  : chckbox element
	 * @return : NA
	 * @author : 
	 */
	public  void webCheckON(WebElement inputObject) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(inputObject));
			if(!inputObject.isSelected()) {
				inputObject.click();
			}
			else {
			}
		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : uncheck a checkbox
	 * @param  : chckbox element
	 * @return : NA
	 * @author : 
	 */
	public  void webCheckOFF(WebElement inputObject) {
		try {
			if(inputObject.isSelected()) {
				inputObject.click();

			}
			else {

			}
		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : waits till the elemnt is clickable and clicks the element
	 * @param  : chckbox element
	 * @return : NA
	 * @author : 
	 */
	public  void clickElement(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : click an element using javascript executor
	 * @param  : chckbox element
	 * @return : NA
	 * @author : 
	 */
	public  void clickElementUsingJavscriptExecutor(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", element);
		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : click an element using Actions
	 * @param  : click element
	 * @return : NA
	 * @author : 
	 */
	public  void clickElementUsingActions(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().perform();
		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : click an element ignoring StaleElementReferenceException
	 * @param  : chckbox element
	 * @return : NA
	 * @author : 
	 */
	public   void clickElementIgnoreStaleElementReferenceException(WebElement webElement) throws Exception {
		int attempts = 0;
		while (attempts < 2) {
			try {
				wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.elementToBeClickable(webElement));
				webElement.click();
				break;
			}
			catch (StaleElementReferenceException e) {
				//setLoggerError("Error in ElementUtil"+e);
				logg.info("Error in ElementUtil"+e);
			}
			attempts++;
		}
	}
				/*
	 * @description : refresh current web page
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public  void refreshWebpage() {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			//setLoggerError("Error in ElementUtil"+e);
		//	logg.error("Error in ElementUtil"+e);
			throw e;
		}
	}
	/*
	 * @description : wait until an element is visible
	 * @param  : element to be displayed
	 * @param  : timeout in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void waitUntilElementVisible(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * @description : wait until an element is visible
	 * @param  : element to be displayed
	 * @param  : timeout in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void waitUntilElementsVisible(List<WebElement> element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * @description : wait until an element is visible
	 * @param  : element to be displayed
	 * @param  : timeout in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void waitUntilElementClickable(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(element));

		} catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : wait until an element is visible
	 * @param  : element to be displayed
	 * @param  : timeout in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void waitUntilElementIsPresent(String xpath, Duration timeOutInSeconds) {
		try {
			wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : wait for an element to appear even if it is not present in the DOM, by ignoring
	 * NoSuchElementException and StaleElementReferenceException
	 * @param  : xpath of element to be displayed
	 * @param  : timeout in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void waitUntilElementExists(String xpath, long timeOutInSeconds) {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver)
					.withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(5))
					.ignoring(NoSuchElementException.class,StaleElementReferenceException.class);

			//Define new function as follows when we need more than what is present in ExpectedConditions

			@SuppressWarnings("unused")
			WebElement element = wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.xpath(xpath));
				}
			});
		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * @description : wait until an element is visible
	 * @param  : element to be displayed
	 * @param  : timeout in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void waitUntilElementInvisible(WebElement element, Duration timeOutInSeconds) {
		try {
			wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.invisibilityOf(element));
		} catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : scroll to an element
	 * @param  : element to be displayed
	 * @return : NA
	 * @author : 
	 */
	public  void scrollIntoView(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * @description : scroll until the element is at the center of the web page
	 * @param  : element to be displayed
	 * @return : NA
	 * @author : 
	 */
	public  void scrollElementToCentreOfWebPage(WebElement element) {
		try {
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

		} catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : scroll to the top of a web page
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public void scrollToTop() {
		try {
			((JavascriptExecutor) driver)
			.executeScript("window.scrollTo(0, 0)");

		} catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : scroll to the bottom of a web page
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public void scrollToBottom() {
		try {
			((JavascriptExecutor) driver)
			.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		} catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : returns true if the element is present and displayed.
	 * returns false if the element is not visible or not present in the DOM
	 * @param  : element
	 * @return : presence/visibility of the element
	 * @author : 
	 */
	public  boolean isPresentAndDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	/*
	 * @description : returns true if the element contains value.
	 * returns false if the element doest not contain value
	 * @param  : element
	 * @return : presence of the element's value
	 * @author : 
	 */
	public  boolean isElementcontainsValue(WebElement element, String value) {
		try {
			return element.getAttribute(value).isEmpty();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	/*
	 * @description : download file to the default downloads folder in IE
	 * @param  : download button/link
	 * @return : NA
	 * @author : 
	 */
	public  void downloadFileIE(WebElement donwloadButton) throws Exception {
		try
		{
			donwloadButton.click();
			sleepTime(1000); // wait for save pop up

			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			sleepTime(500);
			robot.keyPress(KeyEvent.VK_S);
			sleepTime(500);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);
			sleepTime(500);
		}
		catch (Exception e) {

			throw e;
		}
	}
	public  void saveAsFile() throws Exception {
		try {
			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_CONTROL);
			sleepTime(500);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_S);
			sleepTime(500);
		}
		catch (Exception e) {
			throw e;
		}

	}
	
	/*
	 * @description : upload file using Robot class
	 * @param  : full file path
	 * @return : NA
	 * @author : 
	 */
	public  void uploadFile(String filePath) throws Exception {
		try {
			//Wait for upload pop up to open
			sleepTime(3000);

			//Copy the location of the file and store in clip board
			StringSelection stringSelection = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

			//Press ctrl+v to paste the file path in upload window and press Enter
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_CONTROL);
			sleepTime(500);
			robot.keyPress(KeyEvent.VK_V);
			sleepTime(500);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			sleepTime(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			sleepTime(500);
		} catch (Exception e) {
			//setLoggerError("Error in ElementUtil"+e);
			logg.info("Error in ElementUtil"+e);
			throw e;
		}
	}

	public String checkFileExistence(String filePath, String filename, String extension)
	{
		File pathAsFile = new File(filePath);
		if (!Files.exists(Paths.get(filePath))) {
			pathAsFile.mkdirs();
		}
		String DesiredPath = filePath + filename + "." + extension;
		File dstFile = new File(filePath + filename + "." + extension);
		int i = 0;
		if (dstFile.exists()) {
			while (dstFile.exists()) {
				i += 1;
				DesiredPath = filePath + filename + " (" + i + ")" + "." + extension;
				dstFile = new File(filePath + filename + " (" + i + ")" + "." + extension);
			}
		}
		else {
			DesiredPath = filePath + filename + "." + extension;
		}
		return DesiredPath;

	}
		/*
	 * @description : delete file from directory if exists
	 * @param  : filePath
	 * @return : NA
	 * @author : 
	 */
	public  void deleteFileIfExists(String filePath) throws IOException {
		try {
			Files.deleteIfExists(Paths.get(filePath)); //eg-- "D:\\WorkSpace\\775870.jpg"

		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * @description : verify file from directory if exists
	 * @param  : filePath
	 * @return : NA
	 * @author : 
	 */
	public  void veifyFileIfExists(String filePath) throws IOException {
		try {
			Files.exists(Paths.get(filePath)); //eg-- "D:\\WorkSpace\\775870.jpg"

		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * wait for the element to load
	 * @author 
	 * @param driver
	 */
	public  void waitForLoad(WebDriver driver) {
		try {

			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					// Get JS is ready
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
			};
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
			wait.until(pageLoadCondition);

		} catch (Exception e) {

			throw e;
		}
	}
	/**
	 * Scroll the page to view element and click
	 * @author 
	 * @param element
	 */
	public  void scrollToViewAndClickElement(WebElement element) throws Exception{

		JavascriptExecutor executor = ((JavascriptExecutor) driver);
		int scrollHeight = ((Number) executor.executeScript("return document.body.scrollHeight")).intValue();
		int i = 0;
		for (i = 0; i <= scrollHeight; ) {
			try {

				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + i + ");");
				if (element.isDisplayed()) {
					clickElementUsingJavscriptExecutor(element);
					break;
				}
			} catch (Exception e) {

			}
			i += 50;
		}
	}
	/**
	 * To open downloaded files in Internet explorer
	 * @author 
	 * @param element
	 * @throws InterruptedException
	 */
	public  void opendownloadedDocument(WebElement element) throws Exception {

		element.click();
		try
		{
			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			sleepTime(1000);
			robot.keyPress(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_O);

		}
		catch (AWTException e)
		{
			//setLoggerError("Error in ElementUtil"+e);
		//	logg.error("Error in ElementUtil"+e);
		}

	}
	/**
	 * To close downloaded files in Internet explorer
	 * @author 
	 * @param element
	 * @throws InterruptedException
	 */
	public  void closeDownloadedDocument(WebElement element) throws Exception {

		element.click();
		try
		{
			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			sleepTime(1000);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_C);

		}
		catch (AWTException e)
		{
			//setLoggerError("Error in ElementUtil"+e);
			logg.info("Error in ElementUtil"+e);
		}

	}
	/**
	 * To scroll and view error messages and validate with actual error messages
	 * @author 
	 * @param element
	 * @param expectedMessage
	 */
	public  void scrollToViewAndValidateError(WebElement element, String expectedMessage) throws Exception{

		JavascriptExecutor executor = ((JavascriptExecutor) driver);
		int scrollHeight = ((Number) executor.executeScript("return document.body.scrollHeight")).intValue();
		int i = 0;
		for (i = 0; i <= scrollHeight; ) {
			try {

				((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + i + ");");
				if (element.isDisplayed()) {
					assert true;
					break;
				}
			} catch (Exception e) {

			}
			i += 50;
		}
	}
	/**
	 * To handle date picker
	 * @author 
	 * @param dateElement
	 * @param dateString
	 * @throws Exception
	 */
	public  void selectDateFromDatePickerCV(WebElement dateElement, String dateString) throws Exception {
		Date requestedDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);

		String month = new SimpleDateFormat("MMMM").format(requestedDate);
		String mon=month.substring(0, 3);

		String day = new SimpleDateFormat("d").format(requestedDate);

		String year = new SimpleDateFormat("yyyy").format(requestedDate);

		scrollToViewAndClickElement(dateElement);


		Select selectMonth = new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-month']")));
		selectMonth.selectByVisibleText(mon);
		Select selectYear = new Select(driver.findElement(By.xpath("//select[@class='ui-datepicker-year']")));
		selectYear.selectByVisibleText(year);
		for (WebElement element : driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//td[@data-handler='selectDay'] "))) {
			if (element.getText().trim().equals(day)) {
				element.click();
				break;
			}
		}
	}
	/*
	 * @description : handles unexpected alerts
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public  boolean isAlertPresent() {
		try
		{
			driver.switchTo().alert();
			return true;
		}   // try
		catch (NoAlertPresentException Ex)
		{
			return false;
		}   // catch
	}
	/*
	 * @description : handles alerts if alert is present while refresing the web Page
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public void handleAlert() {
	//	driver.navigate().refresh();
		if(isAlertPresent()){
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
	}
	/*
	 * @description : to handle unexpected alerts Exception
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public  void handleAlertExceptions() throws Exception {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver)
					.withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(5))
					.ignoring(NoSuchElementException.class)
					.ignoring(UnhandledAlertException.class);
			Alert alert = wait.until(new Function<WebDriver, Alert>() {

				@Override
				public Alert apply(WebDriver driver) {
					try {
						return driver.switchTo().alert();
					}
					catch(NoAlertPresentException e) {
						return null;
					}
				}
			});

			alert.accept();

		} catch (UnhandledAlertException e) {
			((JavascriptExecutor) driver).executeScript("window.focus();");
			//sleepTime(500);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			//sleepTime(500);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
	}
		/*
	 * @description : get latest file from the specified directory
	 * @param  : directory path
	 * @return : NA
	 * @author : 
	 */
	public String getLatestFilefromDir(String dirPath){
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile.getName();
	}
	/*
	 * @description : return the required date format
	 * @param  : number of days
	 * @return : string date
	 * @author : 
	 */
	public  String selectGreaterDate(Date inputDate, Date dateToBeModified) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		Date date1 = dateToBeModified;
		Date date2 = inputDate;
		String dateToBeEntered = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		if (date1.compareTo(date2) >= 0) {
			cal.add(Calendar.DATE, 1);
			dateToBeEntered = sdf.format(cal.getTime());
		} else if (date1.compareTo(date2) < 0) {
			cal.add(Calendar.DATE, 0);
			dateToBeEntered = sdf.format(cal.getTime());
		}

		return dateToBeEntered;
	}
	/*
	 * @description : selects first child window
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public void CloseFirstChildWindow() throws Exception{
		mainWindow=driver.getWindowHandle();
		Set<String> set =driver.getWindowHandles();
		// Using Iterator to iterate with in windows
		Iterator<String> itr= set.iterator();
		while(itr.hasNext()){
			String childWindow=itr.next();
			// Compare whether the main windows is not equal to child window. If not equal, we will close.
			if(!mainWindow.equals(childWindow)){
				driver.switchTo().window(childWindow);
				driver.close();
				driver.switchTo().window(mainWindow);
				break;
			}
			
			
		}
		//make sure that you will use the following line of code after doing operations on child window --> driver.switchTo().window(Common.mainWindow);
	}
	public void waitForTwoWindow(int noofwindows) {
		WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.numberOfWindowsToBe(noofwindows));
			}
	/*
	 * @description : selects first child window
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public String SwitchtoFirstChildWindow() throws Exception{
		mainWindow=driver.getWindowHandle();
		String childWindow = null;
		Set<String> set =driver.getWindowHandles();
		// Using Iterator to iterate with in windows
		Iterator<String> itr= set.iterator();
		while(itr.hasNext()){
			 childWindow=itr.next();
			// Compare whether the main windows is not equal to child window. If not equal, we will close.
			if(!mainWindow.equals(childWindow)){
				driver.switchTo().window(childWindow);
				driver.manage().window().maximize();
				break;
			}
		}
		return childWindow;
	}
	/*
	 * @description : selects first child window
	 * @param  : NA
	 * @return : NA
	 * @author : 
	 */
	public String GetParentWindow() throws Exception{
		mainWindow=driver.getWindowHandle();
		return mainWindow;
	}
	public void closeSpecificWindow(String SpecificWindow) {
		driver.switchTo().window(SpecificWindow);
		driver.close();
	}
	public void SwitchSpecificWindow(String SpecificWindow) {
		driver.switchTo().window(SpecificWindow);
	}
	/*
	 * @description : returns number of days between two dates
	 * @param  : two dates along with their pattern
	 * @return : number of days between two dates
	 * @author : 
	 */
	public  int numberOfDaysBetweenTwoDates(String date1, String date2, String datePattern) {

		//SimpleDateFormat myFormat = new SimpleDateFormat(datePattern);
		//datePattern can be any valid patterns like "dd MMM yyyy" or "dd/mm/yyyy" etc
		String dateBeforeString = date1;
		String dateAfterString = date2;

		//Parsing the date
		LocalDate dateBefore = LocalDate.parse(dateBeforeString);
		LocalDate dateAfter = LocalDate.parse(dateAfterString);

		//calculating number of days in between
		long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		return (int) noOfDaysBetween;

	}
	/*
	 * @description : returns current date in specified date format
	 * @param  : required date pattern
	 * @return : current date in string format
	 * @author : 
	 */
	public  String getCurrentDateInSpecifiedFormat(String requiredDateFormat) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat(requiredDateFormat);
		Date date = new Date();
		return dateFormat.format(date);
	}
	/*
	 * @description : returns required date in specified date format
	 * @param  : required date pattern and number of days from current date(for past date @days should be -ve and for future date it should be +ve and for current date it should be zero)
	 * @return : string date
	 * @author : 
	 */
	public  String getAddedDateInSpecifiedFormat(String requiredDateFormat, int days) throws Exception{
		DateFormat dateFormat = new SimpleDateFormat(requiredDateFormat);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return dateFormat.format(cal.getTime());
	}
	/*
	 * @description : returns required past/future date in specified date format with reference to given input date
	 * @param  : reference date and how many days previous/next to the reference date
	 * @return : string date
	 * @author : 
	 */
	public  String selectPastOrFutureDate(String requiredDateFormat,String inputDate, int days) throws ParseException, java.text.ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(requiredDateFormat);
		Date date = sdf.parse(inputDate);
		String dateToBeEntered = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		dateToBeEntered = sdf.format(cal.getTime());
		return dateToBeEntered;
	}
	public  String daysDifference(String requiredDateFormat,String firstDate,String secondDate) throws Exception   {
		  Date date2=new SimpleDateFormat(requiredDateFormat).parse(secondDate);  
		  Date date1=new SimpleDateFormat(requiredDateFormat).parse(firstDate);  
		  String dateComparison=null;
		  if(date1.after(date2)){
			  dateComparison="After";
          }
          // before() will return true if and only if date1 is before date2
          if(date1.before(date2)){
        	  dateComparison="Before";
                   }

          //equals() returns true if both the dates are equal
          if(date1.equals(date2)){
        	  dateComparison="Equal";
              
          }
		return dateComparison;
	}
	/*
	 * @Author : 
	 * @description : Getting current system date
	 */
	public  List<Object> currentDate() {
		try {
			//Getting the current date value
			LocalDate currentdate = LocalDate.now();
			//Getting the current day
			int currentDay = currentdate.getDayOfMonth();
			//Getting the current month
			Month monthName = currentdate.getMonth();
			//getting the current year
			int yearName = currentdate.getYear();
			return Arrays.asList(currentDay, monthName, yearName);
		} catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : Highlight the element
	 * @author :
	 * @param : driver,Timeunits
	 */
	public  void highlightElement(WebDriver driver,WebElement element) {
		try {
			//Create object of a JavascriptExecutor interface
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//use executeScript() method and pass the arguments
			//Here i pass values based on css style. Yellow background color with solid red color border.
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		}
		catch (Exception e) {

			throw e;
		}
	}
	/*
	 * @description : enters value to a text box or text area
	 * @param  : text element
	 * @param  : input value
	 * @param  : timeout for element to be present in seconds
	 * @return : NA
	 * @author : 
	 */
	public   void webEditTxtChange(WebElement inputObject,String value, Duration timeOutInSeconds) {
		try
		{
			if(!value.equals("")) {
				wait = new WebDriverWait(driver, timeOutInSeconds);
				wait.until(ExpectedConditions.elementToBeClickable(inputObject));

				inputObject.clear();

				if(!value.equals("\"\"")) {
					inputObject.sendKeys(value);

				}
				else {
					inputObject.sendKeys(Keys.SPACE);
					inputObject.sendKeys(Keys.BACK_SPACE);
				}

			} else {

			}

		}
		catch(Exception e)
		{
			throw e;
		}
	}
	/*
	 * @description : enters value to a text box or text area
	 * @param  : text element
	 * @param  : input value
	 * @param  : timeout for element to be present in seconds
	 * @return : NA
	 * @author : 
	 */
	public   void webEditTxt(WebElement inputObject,String value) {
		try
		{
				wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.elementToBeClickable(inputObject));
				inputObject.sendKeys(value);
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	/*
	 * @description : check a checkbox
	 * @param  : chckbox element
	 * @param  : timeout for element to be present in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void webCheckON(WebElement inputObject, Duration timeOutInSeconds) {
		try {
			wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.elementToBeClickable(inputObject));

			if(!inputObject.isSelected()) {
				inputObject.click();

			}
			else {

			}
		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : uncheck a checkbox
	 * @param  : chckbox element
	 * @param  : timeout for element to be present in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void webCheckOFF(WebElement inputObject, Duration timeOutInSeconds) {
		try {
			wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions.elementToBeClickable(inputObject));

			if(inputObject.isSelected()) {
				inputObject.click();

			}
			else {

			}
		}
		catch(Exception e){
			throw e;
		}
	}
	/*
	 * @description : select an option from drop down using visible text
	 * @param  : element
	 * @param  : timeout for element to be present in seconds
	 * @return : NA
	 * @author : 
	 */
	public  void selectFromDropdownByVisibleText(WebElement element, String option) {
		try {
			if(!option.equals("")) {
				wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				Select select=new Select(element);
				select.selectByVisibleText(option);
				
			}
		} catch (Exception e) {

			throw e;
		}
	}
		public  void selectFromDropdownByValue(WebElement element, String option) {
		try {
			if(!option.equals("")) {
				wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.elementToBeClickable(element));
				Select select=new Select(element);
							select.selectByValue(option);
			}
		} catch (Exception e) {

			throw e;
		}
	}
	
	/*
	 * @description : waits for javascript and jquery to finish loading
	 * @param  : chckbox element
	 * @return : NA
	 * @author : 
	 */
	public  boolean waitForJStoLoad() throws InterruptedException {
		//sleepTime(1000);
		//Calendar c = Calendar.getInstance();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long)((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				}
				catch (Exception e) {
					return true;
				}
			}
		};
		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		};
		boolean a = wait.until(jQueryLoad)&&wait.until(jsLoad);
	//	Calendar c1 = Calendar.getInstance();
		return a;
	}
	public  void mouseoveronElement(WebDriver driver,WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}

	public  boolean validateDropdownSelected(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(element));
			Select select=new Select(element);
			return select.getFirstSelectedOption().getText() == null;
		}
		
		catch(Exception e) {
			return false;
		}
	
	}
	public  String FetchDropdownSelectedValue(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(element));
			Select select=new Select(element);
			return select.getFirstSelectedOption().getText();
		}
		
		catch(Exception e) {
			return null;
		}
	
	}

	public  boolean validateTextbox(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(element));
			return element.getAttribute("value").isEmpty();
		}
		
		catch(Exception e) {
			return false;
		}
	}
	public  String FetchTextBoxValuewithText(WebElement element) {
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(element));
			if(!(element.getText().isEmpty())) {
				return element.getText();
		}
		}
		catch(Exception e) {
			return null;
		}
		return element.getText();
	}
		public  String FetchTextBoxValuewithattribute(WebElement element,String attributetype) {
			String attributevalue = null;
			try {
				wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(ExpectedConditions.visibilityOf(element));
				if(element.isEnabled()) {
					attributevalue=element.getAttribute(attributetype);
				}
			}
			catch(Exception e) {
				return null;
			}
			return attributevalue;
	}
		public  String FetchTextBoxValuewithattributedisabled(WebElement element,String attributetype) {
			String attributevalue = null;
			try {
				attributevalue=element.getAttribute(attributetype);	
			}
			catch(Exception e) {
				return null;
			}
			return attributevalue;
	}
		//DB Utils


		/*
		 * @description : executes select query & Insertion Query
		 * @param  : SQL query,Application Instance,Username & password
		 * @return : result of the query in the form of resultset
		 * @author : 
		 */
		public  ResultSet executeSelectQuery(String query, String applicationInstance, String username, String password, String jdbcdriverdetails) throws Exception{

			ResultSet rs = null;
			Connection con = null;
			Statement st=null;
			try {
				// String jdbcUrl = "jdbc:mysql://localhost:3306/BORAJI";
				Class.forName(jdbcdriverdetails);
				//	Class.forName(config.readJdbcDriver());
				 con = DriverManager.getConnection(applicationInstance,username,password);
				 st = con.createStatement();
				//"INSERT INTO EMPLOYEE (ID,FIRST_NAME,LAST_NAME,STAT_CD) "+ "VALUES (1,'Lokesh','Gupta',5)" --For Insertion
				//"SELECT ID,FIRST_NAME,LAST_NAME,STAT_CD FROM EMPLOYEE WHERE ID <= 10" --For Select Query
				rs = st.executeQuery(query);
			
			} catch (SQLException e) {
				throw e;
			}
			finally {
				 if (rs != null) {
				        try {
				            rs.close();
				        } catch (SQLException e) { /* Ignored */}
				    }
				    if (st != null) {
				        try {
				        	st.close();
				        } catch (SQLException e) { /* Ignored */}
				    }
				    if (con != null) {
				        try {
				        	con.close();
				        } catch (SQLException e) { /* Ignored */}
				    }
			}
			return rs;
			}
		/*
		 * @description : executes update query and for Deletion also
		 * @param  : SQL query,Application Instance,Username & password
		 * @param  : applicationInstance
		 * @return : NA
		 * @author : 
		 */
		public  void executeUpdateQuery(String query, String applicationInstance, String username, String password, String jdbcdriverdetails) throws Exception {
			Connection con = null;
			Statement st=null;
			try {
				//Class.forName(config.readJdbcDriver());
				Class.forName(jdbcdriverdetails);
				 con=DriverManager.getConnection(applicationInstance,username,password);
				 st = con.createStatement();
				//DELETE FROM EMPLOYEE WHERE ID >= 1  --For Deletion
				st.executeUpdate(query);
				} 
			catch (SQLException e) {
				throw e;
			}
			finally {
			    if (st != null) {
			        try {
			        	st.close();
			        } catch (SQLException e) { /* Ignored */}
			    }
			    if (con != null) {
			        try {
			        	con.close();
			        } catch (SQLException e) { /* Ignored */}
			    }
			}
		}

		public  File getLastModified(String directoryFilePath)
		{
		    File directory = new File(directoryFilePath);
		    File[] files = directory.listFiles(File::isFile);
		    long lastModifiedTime = Long.MIN_VALUE;
		    File chosenFile = null;

		    if (files != null)
		    {
		        for (File file : files)
		        {
		        	if(file.getName().contains(reportname)) {
		            if (file.lastModified() > lastModifiedTime)
		            {
		                chosenFile = file;
		                lastModifiedTime = file.lastModified();
		            }
		        	}
		        }
		    }

		    return chosenFile;
		}
		public static String getfolder(String filepath) {
			File mostRecent = null;
			String mostRecentFilePath = null;
			Path parentFolder = Paths.get(filepath);
			 
			Optional<File> mostRecentFileOrFolder =
			    Arrays
			        .stream(parentFolder.toFile().listFiles())
			        .max(
			            (f1, f2) -> Long.compare(f1.lastModified(),
			                f2.lastModified()));
			 
			if (mostRecentFileOrFolder.isPresent()) {
			     mostRecent = mostRecentFileOrFolder.get();
			     if(mostRecent.getName().contains(reportname)) {
			    	 mostRecentFilePath= mostRecent.getPath();
			    
			     }
		}
			return mostRecentFilePath; 
}

}