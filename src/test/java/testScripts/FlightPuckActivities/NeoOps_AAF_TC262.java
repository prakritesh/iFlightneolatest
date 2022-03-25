package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC262 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizComm = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	
	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Select multiple flights and perform swap");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
	
	@Test
	public void login() throws Exception {
		
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC262\\FlightEY097.png";
		
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
		// Select Gantt screen
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		
		for(int i=0; i<10 ;i++) {
		driver.findElement(By.id("chronos_overlayW1_gantt_placeholder_aircraftPaneWidgetui-gantt_holder")).sendKeys(Keys.ARROW_RIGHT);
		Screen scn = new Screen();
		Pattern Flight = new Pattern(Image_Path);
		Match m = scn.exists(Flight.similar(0.92));
		if (m != null) {
			System.out.println("PASS");
		}else {
			System.out.println("Not Scrolled");
		}
		
		}
	
	}
	
	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
