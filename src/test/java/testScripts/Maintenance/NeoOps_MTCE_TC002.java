package testScripts.Maintenance;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_SAW;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * configure widgets based on maintenance events
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_MTCE_TC002 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void login() throws Exception {
		try {
			String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_MTCE_TC002\\SELECTED_FLIGHT.PNG";
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
//		String registrationNumber = "A6-EIT";  //CollectTestData.Regno;  - the CollectTestData.Regno always returns NULL

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the "Situational Awareness Window" screen
			IFlightNeo_HomePage.selectSituationalAwarenessWindow(driver);
			// the timeout is required, if not the menu doesn't disappear
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
			
			// add an "Alert Monitor"
			IFlightNeo_SAW.addAlertMonitor(driver);
			
			// configure the "Alert Monitor"
			IFlightNeo_SAW.configureAlertMonitor(driver);
			
			// link the Alert Monitor to the Gantt
			IFlightNeo_SAW.linkToGantt(driver);

			if (IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "singleClick") == true) {
				htmlLib.logReport("Filtered flights successfully found!", "Filtered flights successfully found!", "Pass", driver, true);
			} else {
				htmlLib.logReport("Filtered flights NOT found!", "Filtered flights NOT found!", "Fail", driver, true);
			}
			
			Thread.sleep(10000);
		} catch (Exception e) {
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
