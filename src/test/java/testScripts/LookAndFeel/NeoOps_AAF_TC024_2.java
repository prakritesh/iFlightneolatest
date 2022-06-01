package testScripts.LookAndFeel;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
public class NeoOps_AAF_TC024_2 {

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
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String filter_name;

			// Login
			IFlightNeo_LoginPage.login(driver, username, password);
			// Wait
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the "Manage Filter" screen through the main menu
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);

		    filter_name="Aircraft subtype_32";
			// open an existing filter created in test case TF024_1 and edit it
			//changed method "editFilterInManageFilter" on 23rd Aug to generalize the method
			if(IFlightNeo_ManageFilter.editFilterInManageFilter(driver,filter_name) == true) {
				htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);
			} else {
				htmlLib.logReport("editing the filter failed", "editing the filter failed", "Fail", driver, true);
			}

			// apply changes
			if (IFlightNeo_ManageFilter.applyFilterInManageFilter(driver) == true) {
				htmlLib.logReport("apply filter in Manage Filter", "apply filter in Manage Filter success", "Pass",
						driver, true);
			} else {
				htmlLib.logReport("apply filter in Manage Filter failed", "apply filter in Manage Filter failed",
						"Fail", driver, true);
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
