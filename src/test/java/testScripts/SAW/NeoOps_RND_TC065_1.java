package testScripts.SAW;

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

public class NeoOps_RND_TC065_1 {

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
		Driver.setUpTestExecution(tcName, "Verify link to Gantt from SAW");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void login() throws Exception {
		try {
			// Collect Test Data
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNo = CollectTestData.flightNumber;
			String flightDate = com.dateCalendarEntry(0, 0, 0);
			String depCode = CollectTestData.origin;
			String arrCode = CollectTestData.destination;
			String RegNo = CollectTestData.Regno;
			String acsubtype = CollectTestData.acsubtype;
			String[] alertType = { "High", "Medium", "Low" };
			String[] alertGroup = { "RULE_VIOLATION", "INFORMATION_UPDATES", "REMINDERS" };
			String periodstart = "0";
			String periodEnd = "3";
			String defaultRows = "15";

			String beforeImage = System.getProperty("user.dir")
					+ "\\TestData\\NeoOps_AAFNew_TC003\\3018Before.PNG";
			String afterModify = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAFNew_TC003\\EY3000RedBefore.PNG";
			// Login as Admin role
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			IFlightNeo_HomePage.selectGantt(driver);
			
			  // Navigate to the Gantt Chart IFlightNeo_HomePage.selectGantt(driver);
			  IFlightNeo_Gantt.findFlightInGantt(driver, flightNo, flightDate, depCode,
			  arrCode); // Right click on an existing Flight puck
			  IFlightNeo_Gantt.selectFlightInGantt(driver, beforeImage, "RIGHTCLICK");
			  // -> Edit Flight -> Check the Aircraft added in the previous test case showin
			  // in the Edit Flight Window
			  IFlightNeo_Gantt.verifyAircraftInEditFlightpopup(driver, RegNo, acsubtype);
			 
			

			// Navigate to SAW
			IFlightNeo_HomePage.selectSeasonalAwarenessWindow(driver);
			IFlightNeo_HomePage.Add_Widget(driver);
			// h1[text()='Alert Monitor']
			IFlightNeo_HomePage.Add_ReqWidget(driver);
			IFlightNeo_HomePage.close_widget(driver);
			// configure the "Alert Monitor"
			IFlightNeo_SAW.configureAlertMonitor(driver, alertType, alertGroup, periodstart, periodEnd);
			
			IFlightNeo_SAW.verifyFlight(driver,flightNo);
			
			// link the Alert Monitor to the Gantt
			IFlightNeo_SAW.linkToGantt(driver);

			if (IFlightNeo_Gantt.selectFlightInGantt(driver, afterModify, "singleClick") == true) {
				htmlLib.logReport("Filtered flights successfully found!", "Filtered flights successfully found!",
						"Pass", driver, true);
			} else {
				htmlLib.logReport("Filtered flights NOT found!", "Filtered flights NOT found!", "Fail", driver, true);
			}

		}

		catch (Exception e) {
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();

		}

	}

	/*
	 * @AfterMethod public void closeTest() { Driver.tearDownTestExecution(driver);
	 * }
	 */

}
