package testScripts.Interface;

import java.awt.Robot;
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
import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * this test case checks for ASM CNL messages after a flight has been cancelled.
 * 
 * the test cases should be run in the following order:
 * 
 * 1) NeoOps_INTG_TC004 (REQUIRED, to create the test data)
 * 2) NeoOps_INTG_TC001 (optional)
 * 3) NeoOps_INTG_TC002 (optional)
 * 4) NeoOps_INTG_TC003 (optional)
 * 5) NeoOps_INTG_TC005
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_INTG_TC005 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "cancel a flight to check if ASM CNL is generated");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test (priority=5)
	public void login() throws Exception {
		try {
			String Image_Path_Filtered_Flight = System.getProperty("user.dir")
					+ "\\TestData\\NeoOps_INTG_TC005\\SELECTED_FLIGHT.PNG";

			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
//		String registrationNumber = "A6-EIT";  //CollectTestData.Regno;  - the CollectTestData.Regno always returns NULL

			// move mouse to the side, as I will have issues later if I move the mouse over the menu bar
			Robot robot = new Robot();
			robot.mouseMove(0, 1000);
			
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

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
				Thread.sleep(2000);
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
			messageTypes.add("ASM");
			ArrayList<String> messageSubTypes = new ArrayList<String>();
			messageSubTypes.add("CNL");
			ArrayList<String> messageDirections = new ArrayList<String>();
			messageDirections.add("OUT");

			IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, date, date, messageDirections, messageTypes, messageSubTypes);
			Thread.sleep(2000);
            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);

			// verify if ASM CNL message is present
            String messageType = IFlightNeo_MessageList.grid_FirstMessageType(driver).getAttribute("textContent");
            String messageSubType = IFlightNeo_MessageList.grid_FirstMessageSubType(driver).getAttribute("textContent");
            
			if (messageType.compareTo("ASM") == 0 & messageSubType.compareTo("CNL") == 0) {
				htmlLib.logReport("Verify ASM CNL Message Status", "ASM CNL Message Found", "Pass", driver, true);
			} else {
				htmlLib.logReport("Verify ASM CNL Message Status", "ASM CNL Message NOT Found", "Fail", driver, true);
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
