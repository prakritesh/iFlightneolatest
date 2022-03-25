package testScripts.FlightPuckActivities;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC073 {

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
		 * "Reverse Link Between Tabular Pane & the GANTT");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Reverse Link Between Tabular Pane & the GANTT");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void mainMethod() throws Exception {
		String username = CollectTestData.userName;
		String password = CollectTestData.password;

//		driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		htmlLib.logReport("Login Functionality is success", "Login success", "Pass", driver, true);

		IFlightNeo_HomePage.selectGantt(driver);

		htmlLib.logReport("Gantt Screen Opened", "Gantt Screen Open success", "Pass", driver, true);

		IFlightNeo_Gantt.gotoTabularPane(driver);
		
		htmlLib.logReport("Tabular Pane Opened", "Tabular Pane Open success", "Pass", driver, true);

		IFlightNeo_HomePage.set_filterdaterange(driver);
		
		htmlLib.logReport("Set Filter date range", "Date range is filtered", "Pass", driver, true);

       IFlightNeo_HomePage.Retrive_filteredFlightsandmatchwithGantt(driver);

	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
