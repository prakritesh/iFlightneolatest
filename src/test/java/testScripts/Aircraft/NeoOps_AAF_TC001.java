package testScripts.Aircraft;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC001 {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Fill up aircraftdetails form");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
		/*
		 * Driver.setUpScript(tcName, "Fill up aircraftdetails form");
		 * CollectTestData.main(tcName);
		 */
	}

	@Test(priority=24)
	public void login() throws Exception {
		try
		{
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String RegNo = CollectTestData.Regno;
		String Acr = CollectTestData.ACR;
		String name = CollectTestData.name;
		String Manufac = CollectTestData.manufacture;
		String Eng = CollectTestData.engine;
		String engtyp = CollectTestData.engineType;
		String Delv = CollectTestData.Delv;
		String f = Integer.toString(com.getRandomNumberBetwRange(5, 20));
		String J = Integer.toString(com.getRandomNumberBetwRange(10, 50));
		String y = Integer.toString(com.getRandomNumberBetwRange(100, 250));
		String cabin = Integer.toString(com.getRandomNumberBetwRange(4, 7));
		String cockpit = Integer.toString(com.getRandomNumberBetwRange(2, 3));
		String remarks = "Testing_"+com.getRandomNumberFixedLen(5);
		String acsubtype = CollectTestData.acsubtype;
		String Rotcode = CollectTestData.Rotcode;
		String validform = com.dateCalendarEntry(0, 0, 0)+" 12:55";
		String validTo = com.dateCalendarEntry(3, 0, 0)+" 12:55";
		String Etops = CollectTestData.Etops;
		String flightrange = CollectTestData.flightRange;

//		driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Wait
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		// Wait
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		htmlLib.logReport("Verify Login Function", "Login is Successful", "Pass", driver, true);

		IFlightNeo_HomePage.selectAircraftDetails(driver);

		htmlLib.logReport("Aircraft Details pop-up Opened", "Aircraft Details pop-up Open success", "Pass", driver, true);

		IFlightNeo_HomePage.fillform_Aircraftdetails(driver, RegNo, Acr, name, Manufac, Eng, engtyp, Delv, f, J, y,
				cabin, cockpit, remarks, validform, validTo, Etops, flightrange, acsubtype, Rotcode);

		htmlLib.logReport("Aircraft Details form fill up", "Form filled up successfully", "Pass", driver, true);

		IFlightNeo_HomePage.scrollandsave(driver);

		htmlLib.logReport("Aircraft Details save performed", "Form saved successfully", "Pass", driver, true);

		driver.findElement(By.name("aircraftDetailsForm")).sendKeys(Keys.ESCAPE);

		IFlightNeo_HomePage.selectAircraftDetails(driver);

		IFlightNeo_HomePage.viewAircraft(driver, RegNo);

		htmlLib.logReport("View Saved Aircraft Details", "Aircraft details viewed successfully", "Pass", driver, true);
		}
		
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			
		}
	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
