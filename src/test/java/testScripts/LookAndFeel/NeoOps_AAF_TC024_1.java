package testScripts.LookAndFeel;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import utilities.CollectTestData;
import utilities.Driver;

/****
 * The LookAndFeel test cases should be run in the following order: TC024_1
 * TC124 TC024_2 TC024_3 TC025_1
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_AAF_TC024_1 {

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
		/*
		 * Driver.setUpTestExecution(tcName,
		 * "Check up cancellation notification & Outbound messages after cancellation");
		 * CollectTestData.main(tcName);
		 */
	}

	@Test
	public void login() throws Exception {
		try {
			// Collect Test Data
			String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC024_1\\32M_2.PNG";
			String username = CollectTestData.userName;
			String password = CollectTestData.password;

			// Login
			IFlightNeo_LoginPage.login(driver, username, password);
			// Wait
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			// open the "Manage Filter" screen through the main menu
			//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);

			// add filter for aircraft subtype in the "Manage Filter" screen
			IFlightNeo_ManageFilter.addFilterForAircraftSubtypeInManageFilter(driver);
			htmlLib.logReport("Filter Saved and Applied", "Filter Saved and Applied", "Pass", driver, true);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// close the "Manage Filter" Screen
			IFlightNeo_ManageFilter.closeFilter(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// open the Gantt screen
			IFlightNeo_HomePage.selectGantt(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// verify if the filter has been applied correctly by searching for an aircraft
			// this will also fail, if there is no test data in the system
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "singleClick") == true) {
				htmlLib.logReport("aircraft subtype found", "aircraft subtype found", "Pass", driver, true);
			} else {
				htmlLib.logReport("aircraft subtype NOT found", "aircraft subtype NOT found", "Fail", driver, true);
			}
		} catch (Exception e) {
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e.getMessage(), "Fail", driver, true);
		}

	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}