package pageObjects;

import org.openqa.selenium.By;
//import org.sikuli.hotkey.Keys;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonLibrary;


public class IFlightNeo_SAW {

	public static CommonLibrary comm = new CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	static int rows;
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;
	
	/**
	 * this function adds an Alert Monitor
	 * 
	 * @param driver
	 */
	public static void addAlertMonitor(WebDriver driver) {
		// click on the Add Widget Button
		comm.performAction(driver,btn_AddWidget(driver), "CLICK", "click on the Add Widget", "click on the Add Widget");
		
		// click on Add for the Alert Monitor
		comm.performAction(driver,btn_AddAlertMonitor(driver), "CLICK", "click on Add for the Alert Monitor", "click on Add for the Alert Monitor");
	
		// close the Add Widget PopUp
		comm.performAction(driver,btn_CloseAddWidgetPopUp(driver), "CLICK", "click on X to close the Add Widget Popup", "click on X to close the Add Widget Popup");
	}

	/**
	 * TODO this function configures an open Alert Monitor - IT DOES NOT YET WORK!!!
	 * 
	 * @param driver
	 */
	public static void configureAlertMonitor(WebDriver driver) {
		// click on the Add Widget Button
		comm.performAction(driver,btn_EditAlertMonitor(driver), "CLICK", "click on Edit for the Alert Monitor", "click on Edit for the Alert Monitor");

		// click on the dropdown for the Alert Type
		comm.performAction(driver,list_AlertType(driver), "CLICK", "click on dropdown for the Alert Type", "click on dropdown for the Alert Type");
	
		// click on the element for Critical
	//	comm.performAction(driver,list_CriticalElement(driver), "CLICK", "click on Critical Element", "click on Critical Element");
	
		comm.performAction(driver,txtbx_AlertTypeDIV(driver), "CLICK" , "Low", "leave focus from textbox for Alert Type");
		
		try {
			Thread.sleep(2000);
		}
		catch(Exception e) {
			
		}
		
		// leave focus from textbox for Alert Type
	//	comm.performAction(driver,txtbx_AlertType(driver), "SET" , "Low", "leave focus from textbox for Alert Type");
		txtbx_AlertTypeDIV(driver).sendKeys("Low");
//Keys.ESCAPE
		
		try {
			Thread.sleep(20000);
		}
		catch(Exception e) {
			
		}
		
		
		
		// click on the Save button
		comm.performAction(driver,btn_SaveAlertMonitor(driver), "CLICK", "click on the Save button", "click on the Save button");
	}

	/**
	 * link the current Alert Monitor to the Gantt
	 * 
	 * @param driver
	 */
	public static void linkToGantt(WebDriver driver) {
		// click on the Add Widget Button
		comm.performAction(driver,btn_linkToGantt(driver), "CLICK", "link the current Alert Monitor to the Gantt", "link the current Alert Monitor to the Gantt");
	}

	/**
	 * return the textbox for the Alert Type of the Alert Monitor
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtbx_AlertTypeINPUT(WebDriver driver) {
		String xpath = "(//input[@class='select2-input'])[last()]";
//		String xpath = "(//div[@class='select2-search'])[last()]";
		
//		wait = new WebDriverWait(driver,30);
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static WebElement txtbx_AlertTypeDIV(WebDriver driver) {
//		String xpath = "(//input[@class='select2-input'])[last()]";
		String xpath = "(//div[@class='select2-search'])[last()]";
		
//		wait = new WebDriverWait(driver,30);
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the button to link the Alert Monitor to the Gantt
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_linkToGantt(WebDriver driver) {
		String xpath = "//div[@off-label='LINK']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the Save button for the Alert Monitor
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_SaveAlertMonitor(WebDriver driver) {
		String xpath = "//button[@value='Save']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the textbox for the Alert Type
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_CriticalElement(WebDriver driver) {
		String xpath = "//div[contains(text(), 'Critical')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the textbox for the Alert Type
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AlertType(WebDriver driver) {
		String xpath = "//input[@name='config_severities']/../div";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Edit button for the Alert Monitor
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_EditAlertMonitor(WebDriver driver) {
		String xpath = "//span[@key='#dashboard.edit.message']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the Add Widget button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_CloseAddWidgetPopUp(WebDriver driver) {
		String xpath = "(//button[@title='Close'])[1]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the Add Widget button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_AddWidget(WebDriver driver) {
		String xpath = "//button[contains(text(), 'Add Widget')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the Add for the Alert Monitor button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_AddAlertMonitor(WebDriver driver) {
		String xpath = "//h1[contains(text(), 'Alert Monitor')]/../button";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
}