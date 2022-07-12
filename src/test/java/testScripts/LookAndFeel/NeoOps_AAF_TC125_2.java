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
 * The LookAndFeel test cases should be run in the following order: TC125
 * TC125_2
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_AAF_TC125_2 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "delete a custom filter");
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

	@Test(priority=9)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the "Manage Filter" screen through the main menu
			// Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

			// deleting the previously created filter. this step will not work, if script
			// TC0024_1 has not been executed.
			if (IFlightNeo_ManageFilter.deleteFilterFromTC125InManageFilter(driver) == true) {
				htmlLib.logReport("Delete Filter performed", "Delete Filter success", "Pass", driver, true);
			} else {
				htmlLib.logReport("Delete Filter could not be performed", "Delete Filter could not be success", "Fail",
						driver, true);
			}
		} catch (Exception e) {
			htmlLib.logReport("The script failed - check the Exceptions", "The script failed - check the Exceptions"+e.getMessage(), "Fail", driver, true);
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
