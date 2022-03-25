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

import pageObjects.IFlightNeo_AddFlight;
import pageObjects.IFlightNeo_EditFlight;
import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * the flight in the test data has to exist on the day of testing. this class
 * contains a registrationNumber. this needs to have a valid registration
 * number, and the flight should not have this registration number before
 * execution of the test case.
 * 
 * the validation is based on image. Hence the underlying image has to be the same as 
 * in the test data. do not change the tail in the script to another tail without also
 * changing the image in the image validation of the last step.
 * 
 * there are no requirements to run other test cases before this one.
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC011 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "change tail of a flight to check if FLCM is generated with new tail");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=13)
	public void login() throws Exception {
		try {
			String Image_Path_ACReg = System.getProperty("user.dir") + "\\TestData\\NeoOps_INTG_TC011\\RegEIT.PNG";
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

			// change the AC Reg
			IFlightNeo_EditFlight.changeACReg(driver, "A6-EIT", 2);
			Thread.sleep(2000);			

			// click the SAVE button
			IFlightNeo_EditFlight.saveChanges(driver);
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

			// open the details of the FLCM message
			IFlightNeo_MessageList.scrollAndView(driver, 2);
			Thread.sleep(2000);			

			// click on Field tab
			IFlightNeo_MessageList.click_field(driver);
			Thread.sleep(2000);			
			
			// check that message found with correct tail
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path_ACReg, "singleClick") == true) {
				htmlLib.logReport("Verify Aircraft Reg in Message", "Correct AC Reg Message Found", "Pass", driver, true);
			} else {
				htmlLib.logReport("Verify Aircraft Reg in Message", "Correct AC Reg NOT Found", "Fail", driver, true);
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
