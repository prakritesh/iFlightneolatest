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
import pageObjects.IFlightNeo_ManageUsers;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_MTCE_TC035 {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizComm = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Add miscellaneous pucks");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=52)
	public void login() throws Exception {
		try {
			// Collect Test Data
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String activityIDMISC = "102";
			String station = "AUH";
			String imageOfItemCreated = System.getProperty("user.dir")
					+ "\\TestData\\NeoOps_MTCE_TC035\\MiscActivity.PNG";

			// Login as Admin role
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			// Navigate to Gantt
			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);
			// Right click on the empty area on the line of flying of an aircraft, select
			// "Add Miscellaneous"
			IFlightNeo_Gantt.addMiscellaneous(driver, activityIDMISC, station);

			// Verify Image Created
			IFlightNeo_Gantt.verifyItemCreatedInGantt(driver, imageOfItemCreated);
		}

		catch (Exception e) {
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
