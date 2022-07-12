package testScripts.Interface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import pageObjects.IFlightNeo_AddFlight;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * the flight in the test data should not yet exist on the day of testing.
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC013 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "check for multi-leg flight, FLCM is sending the standard time instead of origin");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=22)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
			String registrationNumber = CollectTestData.Regno; 

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the "Add Flights" screen
			IFlightNeo_HomePage.selectAddFlights(driver);
			Thread.sleep(2000);			
			// the timeout is required, if not the menu doesn't disappear
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

			// add the flight details in the "Add Flights" screen
			IFlightNeo_AddFlight.addFlightDetails(driver, flightNumber, registrationNumber);
			Thread.sleep(2000); 
			
			// click on the "Add Flight" Button
			IFlightNeo_AddFlight.clickAddFlight(driver);
			Thread.sleep(2000); 
			
			// add the flight details in the "Add Flights" screen for the second leg
			IFlightNeo_AddFlight.addMultiLegFlightDetails(driver);
			Thread.sleep(2000); 

			// click on the "Add Flight" Button
			IFlightNeo_AddFlight.clickAddFlight(driver);
			Thread.sleep(2000); 

			// click on the "Save" Button
			IFlightNeo_AddFlight.clickSave(driver);
			Thread.sleep(2000); 

			// close the "Add Flights" screen
			BusinessFunctions.closeTab(driver, 0, false);
			Thread.sleep(2000); 

			// open the "Message List" window
			IFlightNeo_HomePage.selectMessageList(driver);
			Thread.sleep(2000); 

			// set the filter to search for ASM NEW messages today.
			String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());

			ArrayList<String> messageTypes = new ArrayList<String>();
			messageTypes.add("FLCM");
			ArrayList<String> messageDirections = new ArrayList<String>();
			messageDirections.add("OUT");

			IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, date, date, messageDirections, messageTypes, null);
			Thread.sleep(2000); 
            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);

			// open the details of the message
			IFlightNeo_MessageList.scrollAndView(driver, 1);
			Thread.sleep(2000); 

			// retrieve the message
			String message = IFlightNeo_MessageList.txtbx_message(driver).getAttribute("value");
			date = new SimpleDateFormat("yyyyMMdd").format(new Date());		

			// verify the message DOES NOT CONTAIN TODAYS TIMESTAMP
			if(message.contains(date) == false) {
				htmlLib.logReport("message contains the correct date", "message contains the correct date", "Pass", driver, true);				
			}
			else {
				htmlLib.logReport("message wrongly contains todays date", "message wrongly contains todays date", "Fail", driver, true);
				System.out.println("message: " + message);
				System.out.println("message should contain tomorrows date");
			}
		} catch (Exception e) {
			htmlLib.logReport("The script failed - check the Exceptions", "The script failed - check the Exceptions", "Fail", driver, true);
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
