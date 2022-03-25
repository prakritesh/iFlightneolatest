    package testScripts.FlightPuckActivities;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
public class NeoOps_WIA_TC001 {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	static Screen scn;
	int flightfound=0;
	Match m,n;
	
	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName, "What if scenario analysis");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "What if scenario analysis");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	@SuppressWarnings("unused")
	public void login() throws Exception {	
		try
		{
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String tcname=CollectTestData.tcname;
		//String Image_Path = System.getProperty("user.dir") + "\\TestData\\FlightEY956.png";
		String img_Flight1 = System.getProperty("user.dir")+"\\TestData\\FlightEY555.png";
		String img_Flight2 = System.getProperty("user.dir")+"\\TestData\\FlightEY556.png";
		String img_Flight3 = System.getProperty("user.dir")+"\\TestData\\FlightEY111.png";
		String img_Flight4 = System.getProperty("user.dir")+"\\TestData\\FlightEY122.png";
            
//			driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Login
		IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
		IFlightNeo_HomePage.selectGantt(driver);
		htmlLib.logReport("Gantt Screen Opened", "Gantt Screen Open success", "Pass", driver, true);
		IFlightNeo_HomePage.select_Newscenariomode(driver);
		Thread.sleep(5000);
		// Close Default LW
		BusinessFunctions.closeTab(driver, 0, false);
		Thread.sleep(3000);
		//Instance.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		//comm.performAction(EY_iFlightNeo_HomePage.close_realtimescenariotab(Instance), "click", "", "close realtime scenario tab");
		IFlightNeo_Gantt.findFlightInGantt(driver, flighNo, Date, departureAirport, arrivalAirport);
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		// Implements the WebDriverWait and Action interface for further purpose
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Actions action = new Actions(driver);
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
		scn.keyDown(Key.CTRL);
		scn.click(Flight2.similar(0.95));
		scn.keyUp(Key.CTRL);
		// Scrolling down or up for next flight trip
		int scroll_cnt = 40;
		for (int i = 0; i < scroll_cnt; i++) {
			scn.wheel(Button.WHEEL_UP, 3);
			Pattern Flight3 = new Pattern(img_Flight3);
			Pattern Flight4 = new Pattern(img_Flight4);
			m = scn.exists(Flight3.similar(0.95));
			if (m!=null)
				break;
		}
		if (m != null) {
			Pattern Flight3 = new Pattern(img_Flight3);
			Pattern Flight4 = new Pattern(img_Flight4);

			scn.keyDown(Key.CTRL);
			scn.click(Flight3.similar(0.95));
			scn.keyDown(Key.CTRL);
			scn.rightClick(Flight4.similar(0.95));
			
			//flightfound=1;
			htmlLib.logReport("Selected multiple flights and right clicked",
					"Selected multiple flights and right clicked success", "Pass", driver, true);
		}
		//break;
		if(m == null)
		{
			int scroll_cnt2 = 85;
			for (int j = 0; j < scroll_cnt2; j++) {

			scn.wheel(Button.WHEEL_DOWN, 3);
			Pattern Flight3 = new Pattern(img_Flight3);
			Pattern Flight4 = new Pattern(img_Flight4);

		 n = scn.exists(Flight3.similar(0.99));
		 if (n!=null)
				break;
		 
			}
			
		}
		if (n != null) {
			Pattern Flight3 = new Pattern(img_Flight3);
			Pattern Flight4 = new Pattern(img_Flight4);

			scn.keyDown(Key.CTRL);
			scn.click(Flight3.similar(0.99));
			scn.keyDown(Key.CTRL);
			//scn.wheel(Button.WHEEL_DOWN, 3);
			//scn.wheel(Button.WHEEL_DOWN, 3);
			scn.rightClick(Flight4.similar(0.99));
			
			//flightfound=1;
			htmlLib.logReport("Selected multiple flights and right clicked",
					"Selected multiple flights and right clicked success", "Pass", driver, true);
		}
		// confirm swap operation
		IFlightNeo_HomePage.auto_Off_RightClickoptions(driver,tcname);
        //publish local world
		IFlightNeo_Gantt.publish_Localworld(driver);
		//confirm change from change list
		IFlightNeo_HomePage.confirmChange(driver);
		Thread.sleep(500);
		// Again finding the first trip flight to verify the aircraft registration
		// change
		IFlightNeo_Gantt.findFlightInGantt(driver, flighNo, Date, departureAirport, arrivalAirport);
		scn.wait(Flight1, 9000);
		scn.doubleClick(Flight1);
		// Gets Aircraft registration number after flight swap
		String AircraftRegistration_after = IFlightNeo_HomePage.AfterreadAircraftReg(driver);
		// condition to check the flight swap and end the test case
		boolean Swap_Sucess = AircraftRegistration_before.contentEquals(AircraftRegistration_after);
		if (!Swap_Sucess == true) {
			htmlLib.logReport("Swap Flights Operation", "Swap flights success", "Pass", driver, true);
		}
		else {
			htmlLib.logReport("Swap Flights Operation", "Swap flights NOT success", "FAIL", driver, true);
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