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
import utilities.CollectTestData;
import utilities.Driver;

/****
 * The LookAndFeel test cases should be run in the following order: TC024_1
 * TC124 TC024_2 TC024_3 TC025_1
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_AAF_TC024_3 {
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
			String imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC024_1\\32M_2.png";
			String username = CollectTestData.userName;
			String password = CollectTestData.password;

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open gantt screen
			IFlightNeo_HomePage.selectGantt(driver);
			htmlLib.logReport("Gantt chart loaded", "Gantt chart loaded", "Pass", driver, true);

			// apply filter in gantt screen
			IFlightNeo_Gantt.applyFilter_Gantt(driver);
			htmlLib.logReport("Filter applied", "Filter applied", "Pass", driver, true);

			// verify aircraft subtype is available and filter was correctly applied.
			// the function throws an exception, if the element cannot be found
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, imagePath, "singleClick") == true) {
				htmlLib.logReport("aircraft subtype found", "aircraft subtype found", "Pass", driver, true);
			} else {
				htmlLib.logReport("the filter value was not found in the gantt",
						"the filter value was not found in the gantt", "Fail", driver, true);
			}
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
