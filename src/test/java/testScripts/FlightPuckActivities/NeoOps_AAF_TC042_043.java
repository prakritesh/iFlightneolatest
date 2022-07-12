package testScripts.FlightPuckActivities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC042_043 {
	public utilities.ReportLibrary report = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	// Global variable for test data
	static String flightNo, fltDate, departureAirport, arrivalAirport, imagePath;
		
	@BeforeTest
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Verify user can Assign/Unassign flight leg to an a/c registration");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
	
	@Test
	public void mainMethod() throws Exception {
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		fltDate = CollectTestData.flightDate;
		flightNo = CollectTestData.flightNumber;
		departureAirport = CollectTestData.origin;
		arrivalAirport = CollectTestData.destination;
		//imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_2.PNG";
		
		// Login
		if(IFlightNeo_LoginPage.login(driver, username, password)) {
			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);
			// Navigate to Unassigned Page
			IFlightNeo_Gantt.gotoUnassignedPane(driver);
			Thread.sleep(2000);
			// Find Flight
			IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo, fltDate, departureAirport, arrivalAirport);
			// Open Flight Leg Detail 
			String selectedFlightImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_2.png";
			if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
				selectedFlightImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_1.png";
			}
			if(IFlightNeo_Gantt.selectFlightInGantt(driver, selectedFlightImg, "RightClick")) {
				IFlightNeo_Gantt.unassignFlight(driver);
				Thread.sleep(4000);
				// Find Flight
				IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo, fltDate, departureAirport, arrivalAirport);
				Screen scn=new Screen();
				scn.hover();
				try {
					bizCom.getImageWithSikuli(selectedFlightImg).hover();
					//report.logReport("Verify Flight is Unassigned Successfully", "Flight is Unassigned", "PASS", driver, true);
					//Now there is no Flight leg unassigned message visible( check with IBS)
					if(IFlightNeo_Gantt.msg_flightLegUnassigned(driver).isDisplayed())
						report.logReport("Verify Flight is Unassigned Message Displayed", "Message Displayed", "PASS", driver, true);
					else 
						report.logReport("Verify Flight is Unassigned Message Displayed", "Message NOT Displayed", "WARNING", driver, true);
				}catch(Exception e) {
					report.logReport("Verify Flight is Unassigned Successfully", "Unable to Unassign Flight", "FAIL", driver, true);
				}
			}
			// Logout
			//IFlightNeo_HomePage.signOut(driver);
		}
	}
	
	
	@AfterTest
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}

