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

public class NeoOps_AAF_TC142 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Validate filter on tabular pane and link to gantt");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=9)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the Gantt screen
			IFlightNeo_HomePage.selectGantt(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			Thread.sleep(2000); 

			// open the tabular panel
			IFlightNeo_Gantt.gotoTabularPane(driver);
			Thread.sleep(2000); 

			// apply a date filter
			IFlightNeo_HomePage.set_filterdaterange(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			Thread.sleep(2000); 
			
			// set filter for arrival airport
			IFlightNeo_Gantt.set_filterarrival(driver);
			Thread.sleep(2000); 
			htmlLib.logReport("setting the filter for the arrival airport", "setting the filter for the arrival airport", "Pass", driver, true);
			
			// link the tabular pane

			com.performAction(driver, IFlightNeo_Gantt.link_ReverseLinkTabularpaneToGantt(driver), "click", "", "REVERSE link the taublar pane");
			Thread.sleep(6000); 
			htmlLib.logReport("REVERSE linking the tabular pane", "REVERSE linking the tabular pane", "Pass", driver, true);


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
