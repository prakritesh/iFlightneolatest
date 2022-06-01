package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_New_TC001 {

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
		Driver.setUpTestExecution(tcName, "User Able to open 4 LWs (1 AUTO ON and 3 AUTO OFF)");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=44)
	public void login() throws Exception {
		try {
			// Collect Test Data
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String mode = "New Scenario";
			int tabOpenCount = 4;

			//Login
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);
			// Open LWS Gantt 4 times
			for (int index = 0; index < tabOpenCount; index++) {
				IFlightNeo_Gantt.changeGanttMode(driver, mode);
			}

		}

		catch (Exception e) {
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();

		}

	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}

}
