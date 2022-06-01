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
import pageObjects.IFlightNeo_EditFlight;

import utilities.CollectTestData;
import utilities.Driver;

/**
 * this test case checks for FLCM message after a change to the scheduled times.
 * 
 * there are no requirements to run other test cases before this one.
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC012 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "change standard time of flight to see if FLCM message generated");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		// Login
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=15)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
			String registrationNumber = CollectTestData.Regno; 

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// this will create the flight as test data for this script
			IFlightNeo_AddFlight.addFlight(driver,flightNumber,registrationNumber);
			Thread.sleep(2000);			
						
			// open the "Edit Flight" screen
			IFlightNeo_HomePage.selectEditFlight(driver);
			Thread.sleep(2000);			
			// the timeout is required, if not the menu doesn't disappear
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

			// fill Flight Number
			IFlightNeo_EditFlight.addFlightNumber(driver, flightNumber, 2);
			Thread.sleep(2000);			

			// search for the Flight Number in the Edit Flights screen
			IFlightNeo_EditFlight.searchFlight(driver);
			Thread.sleep(2000);			

			IFlightNeo_EditFlight.scrollAndEdit(driver, 2);
			Thread.sleep(2000);			

			// we have to change the STA/STD
			IFlightNeo_EditFlight.changeSTD(driver);
			Thread.sleep(2000);			

			// click the SAVE button
			IFlightNeo_EditFlight.saveChangesSTD(driver);
			Thread.sleep(2000);			

			// go to the message list and check for the ASQ message
			IFlightNeo_HomePage.selectMessageList(driver);
			Thread.sleep(2000);			
			String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
			
			ArrayList<String> messageTypes = new ArrayList<String>();
			messageTypes.add("FLCM");
			ArrayList<String> messageDirections = new ArrayList<String>();
			messageDirections.add("OUT");

			IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, date, date, messageDirections, messageTypes, null);
			Thread.sleep(2000);			
            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);

			// verify if FLCM message is present
            String messageType = IFlightNeo_MessageList.grid_FirstMessageType(driver).getAttribute("textContent");
            
			if (messageType.compareTo("FLCM") == 0) {
				htmlLib.logReport("Verify FLCM Message Status", "FLCM Message Found", "Pass", driver, true);
			} else {
				htmlLib.logReport("Verify FLCM Message Status", "FLCM Message NOT Found", "Fail", driver, true);
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
