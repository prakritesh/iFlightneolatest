package testScripts.Interface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_AddFlight;
import pageObjects.IFlightNeo_FlightLegDetails;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * this test case checks for FLCM messages if actual times have been updated.
 * 
 * there are no requirements to run other test cases before this one. the flight number in the test data should be changed,
 * if re-executing this test case on the same day.
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC017 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "update actual times to see if FLCM is generated for each update with U action code");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=17)
	public void login() throws Exception {
		try {
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
						
			for(int iteration=4;iteration>0;iteration--) {
				// open "Flight Leg Details" screen
				IFlightNeo_HomePage.selectFlightLegDetails(driver);
				Thread.sleep(2000);			
				
				// enter the airline code, flight number & date 
				IFlightNeo_FlightLegDetails.searchFlightLeg(driver, flightNumber);
				Thread.sleep(2000);			
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MINUTE, (0-iteration));
				SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
				timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				String timestamp = timeFormat.format(calendar.getTime());

				switch(iteration) {
					case 4: 
						// enter Out act time (current time - 4 minutes) - save the time in a variable
						IFlightNeo_FlightLegDetails.fillOutActTime(driver, timestamp);
						break;
					case 3:
						// enter Off act time (current time - 3 minutes) - save the time in a variable
						IFlightNeo_FlightLegDetails.fillOffActTime(driver, timestamp);
						break;
					case 2:
						// enter On act time (current time - 2 minutes) - save the time in a variable
						IFlightNeo_FlightLegDetails.fillOnActTime(driver, timestamp);
						break;
					case 1:
						// enter In act time (current time - 1 minutes) - save the time in a variable
						IFlightNeo_FlightLegDetails.fillInActTime(driver, timestamp);
						break;
				}
				Thread.sleep(2000);			
							
				timeFormat = new SimpleDateFormat("HHmm");
				timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				timestamp = timeFormat.format(calendar.getTime());

				// click on update
				IFlightNeo_FlightLegDetails.clickUpdate(driver);
				Thread.sleep(2000);			
				htmlLib.logReport("flight update performed", "flight update performed", "Pass", driver, true); 
							
				// close the "Add Flights" screen
				BusinessFunctions.closeTab(driver, 0, false);
				Thread.sleep(2000);			
	
				// open the "Message List" window
				IFlightNeo_HomePage.selectMessageList(driver);
	
				// set the filter to search for ASM NEW messages today.
				String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
	
				ArrayList<String> messageTypes = new ArrayList<String>();
				messageTypes.add("FLCM");
				ArrayList<String> messageDirections = new ArrayList<String>();
				messageDirections.add("OUT");

				IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, date, date, messageDirections, messageTypes, null);
	            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);
				Thread.sleep(2000);			
				
				// open the details of the FLCM message
				IFlightNeo_MessageList.scrollAndView(driver, 1);
				Thread.sleep(2000);			
	
				// retrieve the last message
				message = IFlightNeo_MessageList.txtbx_message(driver).getAttribute("value");
				Thread.sleep(2000);			
				
				// verify the message contains the correct timestamp
				if(message.contains(timestamp) == true) {
					htmlLib.logReport("message contains the correct timestamp", "message contains the correct timestamp", "Pass", driver, true);				
				}
				else {
					htmlLib.logReport("message DOES NOT contain the correct timestamp", "message DOES NOT contain the correct timestamp", "Fail", driver, true);
					System.out.println("message: " + message);
					System.out.println("expected timestamp: " + timestamp);
				}
				
				// close the message list screen
				BusinessFunctions.closeTab(driver, 0, true);
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
