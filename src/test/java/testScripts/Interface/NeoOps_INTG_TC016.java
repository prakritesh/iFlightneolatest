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

import pageObjects.IFlightNeo_CancelledActivitiesList;
import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * this test case checks for ASM NEW message after a cancelled flight (by test case NeoOps_INTG_TC005) has been reinstated.
 * 
 * the test cases should be run in the following order: 
 * 1) NeoOps_INTG_TC004 (REQUIRED, to create the test data)
 * 2) NeoOps_INTG_TC001 (optional)
 * 3) NeoOps_INTG_TC002 (optional)
 * 4) NeoOps_INTG_TC003 (optional)
 * 5) NeoOps_INTG_TC005 (REQUIRED to cancel the flight)
 * 6) NeoOps_INTG_TC016
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC016 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "reinstate a cancel flight to check if FLCM generated with N action code");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=23)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the "Cancelled Activities List" screen
			IFlightNeo_HomePage.selectCancelledActivities(driver);
			Thread.sleep(2000);			
			// the timeout is required, if not the menu doesn't disappear
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

			// click on the first checkbox in the table
			IFlightNeo_CancelledActivitiesList.reinstateFlight(driver);
			Thread.sleep(2000);			
			
			// open the "Message List" window
			IFlightNeo_HomePage.selectMessageList(driver);
			Thread.sleep(2000);			

			// set the filter to search for ASM NEW messages today
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
			Thread.sleep(2000);			

			// retrieve the last message
			String message = IFlightNeo_MessageList.txtbx_message(driver).getAttribute("value");
			
			// verify the message contains the correct type
			if(message.contains("N") == true) {
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
