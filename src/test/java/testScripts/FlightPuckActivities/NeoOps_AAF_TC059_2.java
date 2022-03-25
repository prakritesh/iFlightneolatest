package testScripts.FlightPuckActivities;

import org.openqa.selenium.Keys;
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
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC059_2 {
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

	@Test
	public void mainMethod() throws Exception {
		try {
		// Collecting data from Excel and FlightImages Path
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String date = comm.dateCalendarEntry(1,0,0);//CollectTestData.flightDate;
//		String date_zoom = date;
		String[] flightNo = CollectTestData.flightNumber.split(",", 4);
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String img_Flight1 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[0]+"_1_Color.PNG";
		String img_Flight2 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[1]+"_1.PNG";
		String img_Flight3 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[2]+"_1.PNG";
		String img_Flight4 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[3]+"_1.PNG";

		// Login
		if(IFlightNeo_LoginPage.login(driver, username, password)) {
			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);
			// Find Flight
//			IFlightNeo_HomePage.findFlightInGantt(driver, flighNo, date, departureAirport, arrivalAirport);
			// Implements the WebDriverWait and Action interface for further purpose
//			Actions action = new Actions(driver);
			IFlightNeo_Gantt.gantt_GanttCanvas(driver).click();
			IFlightNeo_Gantt.gantt_GanttCanvas(driver).sendKeys(Keys.RIGHT);
			//Go to specified date range
			// IFlightNeo_Gantt.customized_Zoom(driver,date_zoom);
			 //Apply customize date zoom
			 //IFlightNeo_Gantt.btn_customized_Zoom(driver);
			// Implementing the Screen and Pattern using SikuliScript
			scn = new Screen();
			Pattern Flight1 = new Pattern(img_Flight1);
			Pattern Flight2 = new Pattern(img_Flight2);
			// Identifying the flight using images and performing double mouse click
			scn.wait(Flight1, 9000);
			scn.doubleClick(Flight1);
			// Gets Aircraft registration number before flight swap
			String AircraftRegistration_before = IFlightNeo_HomePage.readAircraftReg(driver);
			comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow(driver), "click", "", "Closed the flight details");
			// Selecting the second segment of first flight trip
			scn.keyDown(Key.CTRL);//nt
			scn.click(Flight2.similar(0.80));//nt
			scn.keyUp(Key.CTRL);//nt
			// Scrolling down or up for next flight trip
			int scroll_cnt = 45;
			for (int i = 0; i < scroll_cnt; i++) {
				scn.wheel(Button.WHEEL_DOWN, 3);
				Pattern Flight3 = new Pattern(img_Flight3);
				Pattern Flight4 = new Pattern(img_Flight4);//nt
				Match m = scn.exists(Flight3.similar(0.92));
				if (m != null) {
					scn.keyDown(Key.CTRL);
					scn.click(Flight3.similar(0.95));//nt
					scn.keyDown(Key.CTRL);//nt
					scn.rightClick(Flight4.similar(0.95));
					htmlLib.logReport("Selected multiple flights and right clicked",
							"Selected multiple flights and right clicked success", "Pass", driver, true);
					break;
				}
			}
			// confirm swap operation
			IFlightNeo_HomePage.swapFlight(driver);
			
			
			// Again finding the first trip flight to verify the aircraft registration change
			// add wait to handle the pop-ups appearing while changing registration on 1st Feb,22
			Thread.sleep(11000);
			IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo[0], date, departureAirport, arrivalAirport);
			Screen scn=new Screen();
			 scn.hover();
			scn.wait(Flight1, 9000);
			scn.doubleClick(Flight1);
			// Gets Aircraft registration number after flight swap
			String AircraftRegistration_after = IFlightNeo_HomePage.AfterreadAircraftReg(driver);//code change
			// condition to check the flight swap and end the test case
			boolean Swap_Sucess = AircraftRegistration_before.contentEquals(AircraftRegistration_after);
			if (!Swap_Sucess == true) {
				htmlLib.logReport("Verify Swap Flights Operation", "Swap Flights Successful", "Pass", driver, true);
			}
			else {
				htmlLib.logReport("Verify Swap Flights Operation", "Swap Flights Not Successful", "Fail", driver, true);
			}
		}
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