package pageObjects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;

import utilities.CommonLibrary;

/**
 * class to handle the Cancelled Activities List screen
 * 
 * @author EYHGoiss
 */
public class IFlightNeo_CancelledActivitiesList {

	public static CommonLibrary com = new CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	static int rows;
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;

	
	/**
	 * reinstate a flight from the Activity Grid
	 * 
	 * @param driver
	 */
	public static void reinstateFlight(WebDriver driver) {
		// select Item from Activity Grid
		com.performAction(driver, chkbx_ActivityGridItem(driver), "CLICK", "click on Cancelled Activities Item", "click on Cancelled Activities Item");
		
		// click on button "Reinstate leg/Activity"
		com.performAction(driver, btn_reinstateLeg(driver), "CLICK", "click on Reinstate Leg", "click on button Reinstate Leg");
	
		// click on the SAVE button in the Change Aircraft Registration pop-up
		com.performAction(driver, btn_Save(driver), "CLICK", "SAVE button", "SAVE button");
		
		// schedule change select ATC
		// click on dropdown to select a reason
		com.performAction(driver, dropdown_reason(driver), "CLICK", "click on reason dropdown", "click on reason dropdown");
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		
		// select ATC from dropdown
		com.performAction(driver, dropdown_ATC(driver), "CLICK", "select ATC", "select ATC");
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		// click on PUBLISH button
		com.performAction(driver, btn_publish(driver), "CLICK", "click on publish button", "click on publish button");
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		
	}	
	
	/**
	 * click on the dropdown for the publish reason
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement dropdown_reason(WebDriver driver) {
		String xpath = "(//th[contains(text(),'RC1')])[1]/../../../tbody/tr[1]/td[2]/div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * select ATC from the dropdown for the publish reason
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement dropdown_ATC(WebDriver driver) {
		String xpath = "//div[text()='ATC']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the publish button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_publish(WebDriver driver) {
		String xpath = "//button[@ng-click='publish()']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Dropdown field for Carrier Code
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement chkbx_ActivityGridItem(WebDriver driver) {
		String xpath = "(//input[contains(@id,'jqg_CancelledActivityGrid_')])[1]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the button for Reinstate Leg/Activity
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_reinstateLeg(WebDriver driver) {
		String xpath = "//button[contains(text(),'Reinstate leg')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the SAVE button in the Change Aircraft Registration pop-up
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Save(WebDriver driver) {
		String xpath = "//button[@ng-click='reinstateActivities()']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
}