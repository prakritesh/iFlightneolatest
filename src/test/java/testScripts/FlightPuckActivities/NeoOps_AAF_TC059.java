package testScripts.FlightPuckActivities;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Button;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC059 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static Screen scn;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Select Multiple Flights and Perform Swap Operation");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=36)
	public void mainMethod() throws Exception {
		// Collecting data from Excel and FlightImages Path
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		//String flightNo = CollectTestData.flightNumber;
		String[] flightNo = CollectTestData.flightNumber.split(",", 2);
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String img_Flight1 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[0]+"_1_Color.PNG";
//		String img_Flight2 = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\Flight2.png";
		String img_Flight3 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[1]+"_1_No Color.PNG";
//		String img_Flight4 = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\Flight4.png";
		// Login
		if(IFlightNeo_LoginPage.login(driver, username, password)) {
			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);
			// Switch to Real World Gantt
			if(IFlightNeo_Gantt.changeGanttMode(driver, "Real World")) {
				Thread.sleep(3000);
				System.out.println("In Real World");
				// Close Default LW
				BusinessFunctions.closeTab(driver, 0, false);
				Thread.sleep(3000);
				// Find Flight
				IFlightNeo_Gantt.findFlightInGantt(driver, flightNo[0], Date, departureAirport, arrivalAirport);
				// Implementing the Screen and Pattern using SikuliScript
				scn = new Screen();
				Pattern Flight1 = new Pattern(img_Flight1);
			//	Pattern Flight2 = new Pattern(img_Flight2);
				// Identifying the flight using images and performing double mouse click
				scn.wait(Flight1, 9000);
				scn.doubleClick(Flight1);			
				// Gets Aircraft registration number before flight swap
				String aircraftRegistrationBeforeSwap = IFlightNeo_HomePage.readAircraftReg(driver);
				// Close
				comm.performAction(driver, IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver), "click", "", "Closed the flight details");
				// Scrolling down or up for next flight trip
				int scroll_cnt = 45;
				Pattern Flight3 = new Pattern(img_Flight3);
				for (int i = 0; i < scroll_cnt; i++) {
					Match m = scn.exists(Flight3.similar(0.95));
					if (m != null) {
						scn.keyDown(Key.CTRL);
						scn.rightClick(Flight3.similar(0.95));
						htmlLib.logReport("Selected multiple flights and right clicked",
								"Selected multiple flights and right clicked success", "Pass", driver, true);
						break;
					}else {
						scn.wheel(Button.WHEEL_UP, 3);
					}
				}
				// confirm swap operation
				IFlightNeo_HomePage.swapFlight(driver);
				// Again finding the first trip flight to verify the aircraft registration
				IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo[0], Date, departureAirport, arrivalAirport);
				scn.wait(Flight1, 9000);
				scn.doubleClick(Flight1);
				// Gets Aircraft registration number after flight swap
				String aircraftRegistrationAfterSwap = IFlightNeo_HomePage.readAircraftReg(driver);//code change
				// condition to check the flight swap and end the test case
				boolean swapSucess = aircraftRegistrationBeforeSwap.contentEquals(aircraftRegistrationAfterSwap);
				if (!swapSucess) {
					htmlLib.logReport("Verify Swap Flights Operation", "Swap Flights Successful", "Pass", driver, true);
				}
				else {
					htmlLib.logReport("Verify Swap Flights Operation", "Swap Flights Not Successful", "Fail", driver, true);
				}
				// Close
				comm.performAction(driver, IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver), "click", "", "Closed the flight details");
				htmlLib.logReport("Collect Gantt Screen After Swap Operation", "Gantt Screen After Swap Operation", "INFO", driver, true);
			}
		}
	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}