package pageObjects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
 * class to handle the Edit Flight screen
 * 
 * 
 * @author EYHGoiss
 *
 */
public class IFlightNeo_FlightLegDetails {

	public static CommonLibrary com = new CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	static int rows;
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;

	
	/**
	 * fill the search criteria and search for the flight leg
	 * 
	 * @param driver
	 */
	public static void searchFlightLeg(WebDriver driver, String flightNumber) {
		// fill Airline Code
		com.performAction(driver,list_CarrierCode(driver), "CLICK", "click on Carrier Code Dropdown", "click on Carrier Code Dropdown");
	
		com.performAction(driver,list_EYElement(driver), "CLICK", "select EY from Carrier Code Dropdown", "select EY from Carrier Code Dropdown");
		
		// fill Flight Number
		com.performAction(driver,txtBx_FlightNumber(driver), "SET", flightNumber, "set the Flight Number in the Search Criteria");
		
		// fill Flight Date
		String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
		com.performAction(driver,txtBx_FlightDate(driver), "SET", date, "set the Flight Date in the Search Criteria");
		
		// click the Search button
		com.performAction(driver,btn_Search(driver), "CLICK", "click on the SEARCH button", "click on the SEARCH button");		
	}	
	
	/**
	 * return the Dropdown field for Carrier Code
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_CarrierCode(WebDriver driver) {
		String xpath = "(//div[contains(@id,'flightIdComponent')])[last()]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Dropdown Element EY out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_EYElement(WebDriver driver) {
		String xpath = "//div[contains(text(),'EY')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the text box to fill the Flight Number
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_FlightNumber(WebDriver driver) {
		String xpath = "//input[@ng-model='fltNo']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the text box to fill the Flight Date
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_FlightDate(WebDriver driver) {
		String xpath = "//input[@name='flightLegDetailsWidgetModel_flightDate']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the text box to fill the Act Out
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_ActOut(WebDriver driver) {
		String xpath = "//input[@name='flightLegDetailsWidgetModel_outActTime']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the text box to fill the CTOT
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_CTOT(WebDriver driver) {
		String xpath = "//input[@name='flightLegDetailsWidgetModel_ctot']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the text box to fill the Act Off
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_ActOff(WebDriver driver) {
		String xpath = "//input[@name='flightLegDetailsWidgetModel_offActTime']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the text box to fill the Act On
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_ActOn(WebDriver driver) {
		String xpath = "//input[@name='flightLegDetailsWidgetModel_onActTime']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the text box to fill the Act In
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_ActIn(WebDriver driver) {
		String xpath = "//input[@name='flightLegDetailsWidgetModel_inActTime']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Search Button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Search(WebDriver driver) {
		String xpath = "//button[contains(text(),'Search')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * fill the time for Act Out
	 * 
	 * @param driver
	 */
	public static void fillOutActTime(WebDriver driver, String timestamp) {
		// fill Act Out
		com.performAction(driver,txtBx_ActOut(driver), "SET", timestamp + Keys.ESCAPE, "set the Act Out Time");
		
		// the DL has to be filled after writing the Act Time
		// click on DL dropdown
		com.performAction(driver,list_DL(driver), "CLICK", "click on DL dropdown", "click on DL dropdown");
		
		// select any item from DL dropdown
		com.performAction(driver,list_DLElement(driver), "CLICK", "click on DL dropdown element", "click on DL dropdown element");	
	}	
	
	/**
	 * fill the time for Act Off
	 * 
	 * @param driver
	 */
	public static void fillOffActTime(WebDriver driver, String timestamp) {
		// fill Act Off
		com.performAction(driver,txtBx_ActOff(driver), "SET", timestamp + Keys.ESCAPE, "set the Act Off Time");
	}	

	/**
	 * fill the time for Act On
	 * 
	 * @param driver
	 */
	public static void fillOnActTime(WebDriver driver, String timestamp) {
		// fill Act On
		com.performAction(driver,txtBx_ActOn(driver), "SET", timestamp + Keys.ESCAPE, "set the Act On Time");
	}	

	/**
	 * fill the time for Act In
	 * 
	 * @param driver
	 */
	public static void fillInActTime(WebDriver driver, String timestamp) {
		// fill Act In
		com.performAction(driver,txtBx_ActIn(driver), "SET", timestamp + Keys.ESCAPE, "set the Act In Time");
	}	

	/**
	 * return the Dropdown field for DL
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_DL(WebDriver driver) {
		String xpath = "//div[@id='s2id_dl1']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return an element of the Dropdown list for DL
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_DLElement(WebDriver driver) {
		String xpath = "(//div[@class='select2-result-label'])[1]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * click on the "Save" button
	 * 
	 * @param driver
	 */
	public static void clickUpdate(WebDriver driver) {
		// click on "Update" button
		com.performAction(driver, btn_Update(driver), "CLICK", "click on Update button", "click on Update button");
	}	

	/**
	 * return the Update Button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Update(WebDriver driver) {
		String xpath = "//button[contains(text(),'Update')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
}