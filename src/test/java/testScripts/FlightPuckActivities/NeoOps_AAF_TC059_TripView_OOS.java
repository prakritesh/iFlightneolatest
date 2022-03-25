package testScripts.FlightPuckActivities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
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
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC059_TripView_OOS {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static Screen scn;
	Match m;
    boolean n;
    static String AircraftRegistration_before, AircraftRegistration_after;
    
	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName,
		 * "Select Multiple Flights and Perform Swap Operation");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Select Multiple Flights and Perform Swap Operation");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@SuppressWarnings("unused")
	@Test
	//This TC is not feasible for automation
	/*public void NeoOps_AAF_TC059_Test2() throws Exception {
		// Collecting data from Excel and FlightImages Path
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		//String departureAirport = CollectTestData.dep_Code;
		//String arrivalAirport = CollectTestData.arr_Code;
		String img_Trip1 = System.getProperty("user.dir")+"\\TestData\\FlightEY077.png";
		String img_Trip2 = System.getProperty("user.dir")+"\\TestData\\FlightEY097.png";
		//String img_Trip3 = System.getProperty("user.dir")+"\\TestData\\FlightEY212_P.png";
	
		// Launching the driver, application and Login
//		driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.goToTripViewWindow(driver);
		IFlightNeo_Gantt.findFlightInTripView(driver, flighNo, date);
		Thread.sleep(200);
		//EY_iFlightNeo_HomePage.Find_flight_Trip(driver, flighNo, Date);
		htmlLib.logReport("Gantt Screen Opened", "Gantt Screen Open success", "Pass", driver, true);
		// Implements the WebDriverWait and Action interface for further purpose
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			Actions action = new Actions(driver);
			// Implementing the Screen and Pattern using SikuliScript
			scn = new Screen();
			Pattern Trip1 = new Pattern(img_Trip1);
			// Identifying the flight using images and performing double mouse click
			scn.wait(Trip1, 90);
			scn.doubleClick(Trip1);
			// Gets Aircraft registration number before flight swap
			String AircraftRegistration_before = IFlightNeo_HomePage.registrationNumberTrip(driver);
			comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow(driver), "click", "", "Closed the flight details");
			// Selecting the second segment of first flight trip
			// Scrolling down or up for next flight trip
			int scroll_cnt = 100;
			for (int i = 0; i < scroll_cnt; i++) {
				scn.wheel(Button.WHEEL_DOWN, 3);
				Pattern Trip2 = new Pattern(img_Trip2);
				m = scn.exists(Trip2.exact());
				if (m != null) {
					scn.keyDown(Key.CTRL);
					scn.rightClick(Trip2.similar(0.95));
					htmlLib.logReport("Selected multiple flights and right clicked", "Selected multiple flights and right clicked success", "Pass", driver, true);
					break;
				}
					else {   
						for (int l = 0; l < scroll_cnt; l++) 
							scn.wheel(Button.WHEEL_UP, 3);
						    m = scn.exists(Trip2.similar(0.20));
						    if (m != null) {
			                scn.keyDown(Key.CTRL);
							scn.rightClick(Trip2.similar(0.95));
							htmlLib.logReport("Selected multiple flights and right clicked",
									"Selected multiple flights and right clicked success", "Pass", true);
							break;
						}
						
					}
			}
			if(m==null) {
				for (int l = 0; l < scroll_cnt; l++) {
					Pattern Trip2 = new Pattern(img_Trip2);
					scn.wheel(Button.WHEEL_UP, 3);
				    m = scn.exists(Trip2.similar(0.20));
				    if (m != null) {
		                scn.keyDown(Key.CTRL);
						scn.rightClick(Trip2.similar(0.95));
						htmlLib.logReport("Selected multiple flights and right clicked", "Selected multiple flights and right clicked success", "Pass", driver, true);
						break;
				    }
				}
			}
			// confirm swap operation
			IFlightNeo_HomePage.swapFlight(driver);
			// Again finding the first trip flight to verify the aircraft registration
			// change

			//Pattern Trip3 = new Pattern(img_Trip3);
			IFlightNeo_HomePage.Find_flight_Trip(driver, "" + flighNo, date);
			scn.wait(Trip1, 9000);
			scn.doubleClick(Trip1);

			Pattern Trip3 = new Pattern(img_Trip3);
			IFlightNeo_Gantt.findFlightInTripView(driver, "" + flighNo, date);
			scn.wait(Trip3, 9000);
			scn.doubleClick(Trip3);

			// Gets Aircraft registration number after flight swap
			String AircraftRegistration_after = IFlightNeo_HomePage.registrationNumberTrip(driver);
			Thread.sleep(3000);
			// condition to check the flight swap and end the test case
			boolean Swap_Sucess = AircraftRegistration_before.contentEquals(AircraftRegistration_after);
			if (!Swap_Sucess == true) {
				htmlLib.logReport("Verify Swap Flights Operation", "Swap Flights Successful", "PASS", driver, true);
			}
			else {
				htmlLib.logReport("Verify Swap Flights Operation", "Swap Flights NOT Successful", "FAIL", driver, true);
			}
		}
		catch(FindFailed e) {
			htmlLib.logReport("Swap Flights Operation", "Flight Not highlighted Successfully", "Fatal", driver, true);
		}
	}*/
	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}	
}