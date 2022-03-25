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

/***
 * in my opinion this test case is duplicate in the description to TC125
 * 
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_AAF_TC073_LOOKANDFEEL {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName,
		 * "Check up cancellation notification & Outbound messages after cancellation");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Verify user can find all flights operating to particular station on GANTT (using a filter)");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void login() throws Exception {
		try {
			String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC073_LOOKANDFEEL\\blue.jpg";
			String username = CollectTestData.userName;
			String password = CollectTestData.password;

			// Login
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			// open the "Manage Filter" screen through the main menu
			// Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);

			// adding filter for SYD station in manage filter screen
			IFlightNeo_ManageFilter.addFilterForStationInManageFilter(driver);
			htmlLib.logReport("station filter added and applied", "station filter added and applied", "Pass", driver,
					true);

			// closing the "Manage Filter" screen
			IFlightNeo_ManageFilter.closeFilter(driver);

			// in case there is a pop up, when closing the "Manage Filter" screen, then we
			// are closing that popup
			IFlightNeo_ManageFilter.btn_YesToOverrideChanges(driver);

			Thread.sleep(5000);

			// open Gantt screen
			IFlightNeo_HomePage.selectGantt(driver);

			// verify that the filter has been applied and the test case was successful
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "singleClick") == true) {
				htmlLib.logReport("GANTT displayed WITH applied filter", "GANTT displayed WITH applied filter", "Pass",
						driver, true);
			} else {
				htmlLib.logReport("GANTT displayed WITHOUT applied filter", "GANTT displayed WITHOUT applied filter",
						"Fail", driver, true);
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
