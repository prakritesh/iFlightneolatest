package testScripts.SAW;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_SAW;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_SAW_NewTC001 {

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
		Driver.setUpTestExecution(tcName, "Able to open SAW and load widgets.");
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

			// Login
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
			// Switch Role to OPS_Controller
			IFlightNeo_HomePage.switchUserRole(driver, "OPS_CONTROLLER");
			// Navigate to SAW window
			IFlightNeo_HomePage.selectSeasonalAwarenessWindow(driver);
			// Click on defaultdashboard dropdown & Select Network Control 3
			IFlightNeo_SAW.changeDashBoard(driver, "Network Control 3");

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
