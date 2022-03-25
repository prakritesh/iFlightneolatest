package pageObjects;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.BusinessFunctions;
import utilities.CommonLibrary;

/**
 * class to handle the Edit Flight screen
 * 
 * 
 * @author EYHGoiss
 *
 */
public class IFlightNeo_AddFlight {

	public static CommonLibrary comm = new CommonLibrary();
	private static WebElement element = null;
	static int rows;
	public static utilities.ReportLibrary report = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;

	/**
	 * click on the "Add Flight" button
	 * 
	 * @param driver
	 */
	public static void clickAddFlight(WebDriver driver) {
		// click on "Add Flight" button
		comm.performAction(driver, btn_AddFlight(driver), "CLICK", "click on Add Flight button", "click on Add Flight button");
	}	
	
	/**
	 * click on the "Save" button
	 * 
	 * @param driver
	 */
	public static void clickSave(WebDriver driver) {
		// click on "Save" button
		comm.performAction(driver, btn_Save(driver), "CLICK", "click on Save button", "click on Save button");
		
		// click on dropdown to select a reason
		comm.performAction(driver, dropdown_reason(driver), "CLICK", "click on reason dropdown", "click on reason dropdown");

		// select ATC from dropdown
		comm.performAction(driver, dropdown_ATC(driver), "CLICK", "select ATC", "select ATC");

		// click on PUBLISH button
		comm.performAction(driver, btn_publish(driver), "CLICK", "click on publish button", "click on publish button");
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
	 * adding a flight number to the search filter in the edit flight screen
	 * 
	 * @param driver
	 * @param flightNumber
	 * @throws InterruptedException 
	 */
	public static void addFlightDetails(WebDriver driver, String flightNumber, String ACRegistration) throws InterruptedException {
		
		Thread.sleep(2000);
		// click on dropdown to select a Carrier Code
		comm.performAction(driver, dropdown_CarrierCode(driver), "CLICK", "click on carrier code dropdown", "click on carrier code dropdown");
		
		// write Carrier Code
		comm.performAction(driver, txtBx_CarrierCode(driver), "Set", "EY", "write carrier code EY");
		
		// select Carrier Code
		comm.performAction(driver, dropdown_EYElement(driver), "CLICK", "click on the dropdown for EY", "click on the dropdown for EY");

		// Flight Number
		comm.performAction(driver,txtBx_FlightNumber(driver), "SET", flightNumber, "Flight Number");		

		// if the AC Registration was provided as a parameter, than use it. If not, than a random A/C Subtype & A/C Version will be selected.
		if(ACRegistration != null) {
			// click on dropdown to select a A/C Registration
			comm.performAction(driver, dropdown_ACRegistration(driver), "CLICK", "click on A/C Registration dropdown", "click on A/C Registration dropdown");
		
			// write A/C Registration
			comm.performAction(driver, txtBx_ACRegistration(driver), "Set", ACRegistration, "write A/C Registration");
		
			// select A/C Registration
			comm.performAction(driver, dropdown_ACRegistrationElement(driver), "CLICK", "click on the dropdown for the A/C Registration", "click on the dropdown for the A/C Registration");
		}
		else {
			// fill the A/C Subtype in case the Registration has not been provided
			
			// click on dropdown to select a A/C Subtype
			comm.performAction(driver, dropdown_ACSubtype(driver), "CLICK", "click on A/C Subtype dropdown", "click on A/C Subtype dropdown");
			
			// select A/C Subtype
			comm.performAction(driver, dropdown_ACSubtypeElement(driver), "CLICK", "select an element from dropdown of A/C Subtype", "select an element from dropdown of A/C Subtype");

			// fill the A/C Version in case the Registration has not been provided
			
			// click on dropdown to select a A/C Version
			comm.performAction(driver, dropdown_ACVersion(driver), "CLICK", "click on A/C Version dropdown", "click on A/C Version dropdown");
			
			// select A/C Version
			comm.performAction(driver, dropdown_ACVersionElement(driver), "CLICK", "select an element from dropdown of A/C Version", "select an element from dropdown of A/C Version");
		}
		
		// click on dropdown to select a Service Type
		comm.performAction(driver, dropdown_ServiceType(driver), "CLICK", "click on Service Type dropdown", "click on Service Type dropdown");
		
		// write Service Type
		comm.performAction(driver, txtBx_ServiceType(driver), "Set", "J", "write Service Type");
		
		// select Service Type
		comm.performAction(driver, dropdown_ServiceTypeElement(driver), "CLICK", "click on the dropdown for the Service Type", "click on the dropdown for the Service Type");
		
		// click on dropdown to select a Departure Station
		comm.performAction(driver, dropdown_DepartureStation(driver), "CLICK", "click on Departure Station dropdown", "click on Departure Station dropdown");
		
		// write Departure Station
		comm.performAction(driver, txtBx_DepartureStation(driver), "Set", "AUH", "write Departure Station");
		
		// select Departure Station
		comm.performAction(driver, dropdown_DepartureStationElement(driver), "CLICK", "click on the dropdown for the Departure Station", "click on the dropdown for the Departure Station");
		
		// click on dropdown to select a Arrival Station
		comm.performAction(driver, dropdown_ArrivalStation(driver), "CLICK", "click on Arrival Station dropdown", "click on Arrival Station dropdown");
		
		// write Arrival Station - logic is the same as to retrieve AC Registration
		comm.performAction(driver, txtBx_ACRegistration(driver), "Set", "KUL", "write Arrival Station");
		
		// select Arrival Station
		comm.performAction(driver, dropdown_ArrivalStationElement(driver), "CLICK", "click on the dropdown for the Arrival Station", "click on the dropdown for the Arrival Station");
		
		// write STA
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(new Date());
		tomorrow.add(Calendar.HOUR, 8);
//		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
		String date =sdf.format(tomorrow.getTime());
		comm.performAction(driver, txtBx_STA(driver), "Set", date, "write STA");
	}

	/**
	 * adding a flight number to the search filter in the edit flight screen
	 * 
	 * @param driver
	 * @param flightNumber
	 */
	public static void addMultiLegFlightDetails(WebDriver driver) {
		// select the radiobutton "Transit Flight"
		comm.performAction(driver, radioBtn_TransitFlight(driver), "CLICK", "click on the Radio Button Transit Flight", "click on the Radio Button Transit Flight");
		
		// click on dropdown to select a Arrival Station
		comm.performAction(driver, dropdown_ArrivalStation(driver), "CLICK", "click on Arrival Station dropdown", "click on Arrival Station dropdown");
		
		// select Arrival Station
		comm.performAction(driver, dropdown_ArrivalStationFirstElement(driver), "CLICK", "click on the dropdown for the Arrival Station", "click on the dropdown for the Arrival Station");

		// write STD
		Calendar tomorrowSTD = Calendar.getInstance();
		tomorrowSTD.setTime(new Date());
		tomorrowSTD.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdfSTD = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
		String dateSTD = sdfSTD.format(tomorrowSTD.getTime());
		comm.performAction(driver, txtBx_STD(driver), "Set", dateSTD, "write STD");
		
		// write STA
		Calendar tomorrowSTA = Calendar.getInstance();
		tomorrowSTA.setTime(new Date());
		tomorrowSTA.add(Calendar.DAY_OF_MONTH, 1);
		tomorrowSTA.add(Calendar.HOUR, 4);
		SimpleDateFormat sdfSTA = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
		String dateSTA = sdfSTA.format(tomorrowSTA.getTime());
		comm.performAction(driver, txtBx_STA(driver), "Set", dateSTA, "write STA");
	}
	
	/**
	 * return the Radio Button for Transit Flight
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement radioBtn_TransitFlight(WebDriver driver) {
		String xpath = "//input[@name='istransitFlight'][@value='transit']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the dropdown for A/C Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ACRegistrationElement(WebDriver driver) {
		String xpath = "//li[@class='select2-results-dept-0 select2-result select2-result-selectable select2-highlighted']/div";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * select the an element of the dropdown for A/C Subtype
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ACSubtypeElement(WebDriver driver) {
		String xpath = "(//div[@class='select2-result-label'])[1]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the an element of the dropdown for A/C Version
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ACVersionElement(WebDriver driver) {
		String xpath = "(//div[@class='select2-result-label'])[1]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the dropdown for Service Type - same logic as for A/C Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ServiceTypeElement(WebDriver driver) {
		return IFlightNeo_AddFlight.dropdown_ACRegistrationElement(driver);
	}

	/**
	 * select the dropdown for Departure Station - same logic as for A/C Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_DepartureStationElement(WebDriver driver) {
		return IFlightNeo_AddFlight.dropdown_ACRegistrationElement(driver);
	}
	
	/**
	 * select the dropdown for Arrival Station - same logic as for A/C Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ArrivalStationElement(WebDriver driver) {
		return IFlightNeo_AddFlight.dropdown_ACRegistrationElement(driver);
	}
	
	
	public static WebElement dropdown_ArrivalStationFirstElement(WebDriver driver) {
		String xpath = "//li[@class='select2-results-dept-0 select2-result select2-result-selectable']/div";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	

	/**
	 * select the dropdown for CarrierCode
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_EYElement(WebDriver driver) {
		String xpath = "//span[@class='select2-match']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the dropdown for A/C Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ACRegistration(WebDriver driver) {
		String xpath = "//a[contains(text(),'A/C Registration')]/../../div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * select the dropdown for A/C Subtype
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ACSubtype(WebDriver driver) {
		String xpath = "//label[contains(text(),'A/C Subtype')]/../div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the dropdown for A/C Version
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ACVersion(WebDriver driver) {
		String xpath = "//a[contains(text(),'A/C Version')]/../../div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the dropdown for Service Type
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ServiceType(WebDriver driver) {
		String xpath = "//label[contains(text(),'Service Type')]/../div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the dropdown for Departure Station
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_DepartureStation(WebDriver driver) {
		String xpath = "//a[contains(text(),'Departure')]/../../div/div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * select the dropdown for Arrival Station
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_ArrivalStation(WebDriver driver) {
		String xpath = "//a[contains(text(),'Arrival')]/../../div/div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select the dropdown for CarrierCode
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement dropdown_CarrierCode(WebDriver driver) {
		String xpath = "//div[@id='s2id_createfId_W1_ccode']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the text box to write the A/C Registration 
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_ACRegistration(WebDriver driver) {
		String xpath = "(//div[@class='select2-drop select2-display-none select2-with-searchbox select2-drop-active']/div/input)[last()]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
			
	/**
	 * get the text box to write the Service Type - the same logic works as for AC Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_ServiceType(WebDriver driver) {
		return IFlightNeo_AddFlight.txtBx_ACRegistration(driver);
	}

	/**
	 * get the text box to write the Departure Station - the same logic works as for AC Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_DepartureStation(WebDriver driver) {
		return IFlightNeo_AddFlight.txtBx_ACRegistration(driver);
	}	
	
	/**
	 * get the text box to write the Arrival Station - the same logic works as for AC Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_ArrivalStation(WebDriver driver) {
		String xpath = "(//input[@class='select2-input'])[last()]";
		
	//	WebDriverWait wait = new WebDriverWait(driver, 30);
	//	wait.until(ExpectedConditions.elementToBeClickable(By.id(xpath)));
/*	try {	
		Thread.sleep(300000);
	}
	catch(Exception e) {
		
	}
	*/	
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	
	
	/**
	 * get the text box to write the STA - the same logic works as for AC Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_STA(WebDriver driver) {
		String xpath = "//input[@name='scheduledArrivalDateTime']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * get the text box to write the SDA - the same logic works as for AC Registration
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_STD(WebDriver driver) {
		String xpath = "//input[@name='scheduledDepartureDateTime']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * get the text box to write the carrier code 
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_CarrierCode(WebDriver driver) {
		String xpath = "//div[@class='select2-drop select2-display-none select2-with-searchbox select2-drop-active']/div/input";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	
	/**
	 * get the text box for the Flight Number
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement txtBx_FlightNumber(WebDriver driver) {
		String xpath = "//input[@id='createfId_W1_fltNo']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the Add Flight button
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement btn_AddFlight(WebDriver driver) {
		String xpath = "//button[contains(text(),'Add Flight')]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the Save button
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement btn_Save(WebDriver driver) {
		String xpath = "//button[@ng-click='askConfirmationBeforeSave()']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	public static void addFlight(WebDriver driver, String flightNumber, String registrationNumber) throws Exception {
		// open the "Add Flights" screen
		IFlightNeo_HomePage.selectAddFlights(driver);
		// the timeout is required, if not the menu doesn't disappear
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

		Thread.sleep(2000); // this is required. if no sleep, then sometimes the script is faster than the application and fails
		
		// add the flight details in the "Add Flights" screen
		IFlightNeo_AddFlight.addFlightDetails(driver, flightNumber, registrationNumber);

		
		// click on the "Add Flight" Button
		IFlightNeo_AddFlight.clickAddFlight(driver);

		// click on the "Save" Button
		IFlightNeo_AddFlight.clickSave(driver);
		Thread.sleep(2000); 
		report.logReport("Flight saved", "Flight saved", "Pass", driver, true);

		// close the "Add Flights" screen
		BusinessFunctions.closeTab(driver, 0, false);
	}
}