package testScripts.failurescenarios;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC054_Failure1 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName, "Verifying Failure on property of object is changed");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void NeoOps_AAF_TC054_Test1() throws Exception {
		// Collecting data from Excel and FlightImages Path
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		
		// Launching the driver, application and Login
		try {
//			driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);
			comm.performAction(driver, IFlightNeo_HomePage.btn_findFlight_Failure(driver), "CLICK", "", "Find Flight button");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
