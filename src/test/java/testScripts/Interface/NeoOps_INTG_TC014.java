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
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_AddFlight;
import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * this test case checks for FLCM messages after a flight has been cancelled.
 * 
 * there are no requirements to run other test cases before this one.
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC014 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "cancel a flight to check if FLCM generated with D action code");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=16)
	public void login() throws Exception {
		try {
			String Image_Path_Filtered_Flight = System.getProperty("user.dir")
					+ "\\TestData\\NeoOps_INTG_TC014\\SELECTED_FLIGHT.PNG";

			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
			String message;
			String registrationNumber = CollectTestData.Regno; 

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// this will create the flight as test data for this script
			IFlightNeo_AddFlight.addFlight(driver,flightNumber,registrationNumber);
			Thread.sleep(2000); 
						
			// open Manage Filter screen
			//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			Thread.sleep(2000); 
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);

			// apply filter for Flight
			IFlightNeo_ManageFilter.addFilterForFlightNo(driver, flightNumber);
			Thread.sleep(2000); 

			// close Manage Filter screen
			IFlightNeo_ManageFilter.closeFilter(driver);
			Thread.sleep(2000); 
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// in case there is a pop up, when closing the "Manage Filter" screen, then we
			// are closing that popup
			IFlightNeo_ManageFilter.btn_YesToOverrideChanges(driver);
			Thread.sleep(2000); 

			// open Gantt
			IFlightNeo_HomePage.selectGantt(driver);
			Thread.sleep(2000); 
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// right click on Flight
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path_Filtered_Flight, "RIGHTCLICK") == false) {
				// Filtered flight could not be found. Test case failed.
				htmlLib.logReport("Filtered flight could not be found. Test case failed",
						"Filtered flight could not be found. Test case failed", "Fail", driver, true);

				return;
			}

			// cancel flight
			IFlightNeo_Gantt.cancelFlightFromGantt(driver);
			Thread.sleep(2000); 
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// clear the applied filters
			IFlightNeo_Gantt.clearFilter(driver);
			Thread.sleep(2000); 
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// close Gantt
			BusinessFunctions.closeTab(driver, 0, false);
			Thread.sleep(2000); 

			// open Messages
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

			// open the details of the FLCM message
			IFlightNeo_MessageList.scrollAndView(driver, 1);
			Thread.sleep(5000); 

			// retrieve the last message
			message = IFlightNeo_MessageList.txtbx_message(driver).getAttribute("value");
			
			// verify the message contains the correct type
			if(message.contains("D") == true) {
				htmlLib.logReport("FLCM message contains correct type", "FLCM message contains correct type", "Pass", driver, true);				
			}
			else {
				htmlLib.logReport("FLCM message DOES NOT contains correct type", "FLCM message DOES NOT contains correct type", "Fail", driver, true);
				System.out.println("message: " + message);
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
