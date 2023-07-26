package com.amazon.automation.factory;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.amazon.automation.util.Loggers;
import io.cucumber.java.Scenario;


public class DriverFactory{

	static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	static Scenario scenario;
	protected Loggers logger=new Loggers();
	
	
	
	
	
	public WebDriver initdriver(String browser) throws Exception {

		if (browser.equals("chrome-incognito")) {
			ChromeOptions option = new ChromeOptions();
			option.addArguments("incognito");
		//	WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(option));
		}
		else if (browser.equals("chrome")) {
		//	WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver());
		}
		else if (browser.equals("firefox")) {
			//WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
		}
		else if (browser.equals("edge")) {
		//	WebDriverManager.edgedriver().setup();
			tlDriver.set(new EdgeDriver());
	    } 
		else if (browser.equals("safari")) {
		//	WebDriverManager.safaridriver().setup();
			tlDriver.set(new SafariDriver());
			
		} 

		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
		getDriver().manage().window().maximize();
		return getDriver();
	}

	public synchronized static  WebDriver getDriver() throws Exception {
		if (tlDriver == null) {
			throw new Exception ("Webdriver is not initialized properly");
		} else {
			return tlDriver.get();
		}
	}

	public static void quit() throws Exception {
		if (tlDriver != null) {
			try {
				getDriver().quit();
			} 
			catch (Exception e) {
				
			}
		}
	}
	
}
