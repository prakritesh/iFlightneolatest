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
 * 6) NeoOps_INTG_TC006
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC006 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "reinstate a cancelled flight to check if ASM NEW is generated");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test (priority=6)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
//		String registrationNumber = "A6-EIT";  //CollectTestData.Regno;  - the CollectTestData.Regno always returns NULL

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

			// set the filter to search for ASM NEW messages today.
			String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
			
			ArrayList<String> messageTypes = new ArrayList<String>();
			messageTypes.add("ASM");
			ArrayList<String> messageSubTypes = new ArrayList<String>();
			messageSubTypes.add("NEW");
			ArrayList<String> messageDirections = new ArrayList<String>();
			messageDirections.add("OUT");

			IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, date, date, messageDirections, messageTypes, messageSubTypes);
			Thread.sleep(2000);			
            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);

			// verify if ASM NEW message is present
            String messageType = IFlightNeo_MessageList.grid_FirstMessageType(driver).getAttribute("textContent");
            String messageSubType = IFlightNeo_MessageList.grid_FirstMessageSubType(driver).getAttribute("textContent");
            
			// verify if ASM NEW message is present
			if (messageType.compareTo("ASM") == 0 & messageSubType.compareTo("NEW") == 0) {
				htmlLib.logReport("Verify ASM NEW Message Status", "ASM NEW Message Found", "Pass", driver, true);
			} else {
				htmlLib.logReport("Verify ASM NEW Message Status", "ASM NEW Message NOT Found", "Fail", driver, true);
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
