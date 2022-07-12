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
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_MessageList;
import pageObjects.IFlightNeo_SAW;
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
	private String newdate_zoom;
	private String date_zoom;
	
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

	@Test(priority=48)
	@SuppressWarnings("unused")
	public void login() throws Exception {	
		try
		{
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String AflighNo = CollectTestData.flightNumber;
		//String flightNo = CollectTestData.flightNumber;
		String[] flightNo=AflighNo.split(",",4);
		String flighNo = flightNo[0];
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String tcname=CollectTestData.tcname;
		//String Image_Path = System.getProperty("user.dir") + "\\TestData\\FlightEY956.png";
		String img_Flight1 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[0]+"_1stflighttrip.PNG";
		String img_Flight2 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[1]+"_1stflighttrip.PNG";
		String img_Flight3 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[2]+"_2ndflighttrip.PNG";
		String img_Flight4 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[3]+"_2ndflighttrip.PNG";
		String date_zoom = CollectTestData.flightDate;
		String messageDate= comm.dateCalendarEntry(0,0,0);
            
//			driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Login
		IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
		
		// open the "Manage Filter" screen through the main menu
				//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
				 IFlightNeo_HomePage.menuItem_ManageFilter(driver);
					htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

					// add filter for aircraft subtype in the "Manage Filter" screen
					IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver,flightNo,Date);
					htmlLib.logReport("Filter Saved and Applied", "Filter Saved and Applied", "Pass", driver, true);	
					driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
					
					// close the "Manage Filter" Screen
					IFlightNeo_ManageFilter.closeFilter(driver);
					driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
					try	
					{
			        IFlightNeo_ManageFilter.btn_YesToOverrideChanges(driver);
						
						//Thread.sleep(2000);
			        }
					
					catch (Exception e)
					{
						
					}
				
					Thread.sleep(2000);
					
		
					// Verify and delete Local world Dashlet
					IFlightNeo_HomePage.selectSeasonalAwarenessWindow(driver);
					IFlightNeo_SAW.deleteAllLocalWorlds(driver);

		IFlightNeo_HomePage.selectGantt(driver);
		htmlLib.logReport("Gantt Screen Opened", "Gantt Screen Open success", "Pass", driver, true);
		IFlightNeo_HomePage.select_Newscenariomode(driver);
		Thread.sleep(8000);
		// Close Default LW
		BusinessFunctions.closeTab(driver, 1, false);
		Thread.sleep(3000);
		Screen scn = new Screen();
        scn.mouseMove(500, 500);
		//calculate date range for customize zoom
		 newdate_zoom=IFlightNeo_Gantt.calculatecustomized_Zoomdate(driver,date_zoom);
		 //Go to specified date range
		 IFlightNeo_Gantt.customized_Zoom(driver,newdate_zoom);
		 //Apply customize date zoom
		 IFlightNeo_Gantt.btn_customized_Zoom(driver);
		scn = new Screen();
		Pattern Flight1 = new Pattern(img_Flight1);
		Pattern Flight2 = new Pattern(img_Flight2);
		// Identifying the flight using images and performing double mouse click
		scn.wait(Flight1, 9000);
		scn.doubleClick(Flight1);
		// Gets Aircraft registration number before flight swap
		String AircraftRegistration_before = IFlightNeo_HomePage.readAircraftReg(driver);
		htmlLib.logReport("Flight details open", "Aircraft registration screen shot taken", "Pass", driver, true);
		comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow(driver), "click", "", "Closed the flight details");
		// Selecting the second segment of first flight trip
		scn.keyDown(Key.CTRL);
		scn.click(Flight2.similar(0.85));
		scn.keyUp(Key.CTRL);
		// Scrolling down or up for next flight trip
		/*int scroll_cnt = 40;
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
		}*/
		Pattern Flight3 = new Pattern(img_Flight3);
		Pattern Flight4 = new Pattern(img_Flight4);
		scn.keyDown(Key.CTRL);
		scn.click(Flight3.similar(0.85));
		scn.keyDown(Key.CTRL);
		scn.rightClick(Flight4.similar(0.85));
		htmlLib.logReport("Selected multiple flights and right clicked", "Selected multiple flights and right clicked success", "Pass", driver, true);
		// confirm swap operation
		//IFlightNeo_HomePage.auto_Off_RightClickoptions(driver,tcname);
		 scn.keyUp(Key.CTRL);
		 IFlightNeo_HomePage.swapFlight(driver);
		 Thread.sleep(3000);
		 
		 //Check Change List
		 comm.performAction(driver, IFlightNeo_Gantt.changeList(driver), "click", "", "Clicked on change list");
		 //Expand Change List Record
		 comm.performAction(driver, IFlightNeo_Gantt.changeListExpand(driver), "click", "", "Clicked on change list details");
		 //Wait for the visibility of change list
		 IFlightNeo_Gantt.changelistdetails(driver);
		
		//Close change list 
			comm.performAction(driver, IFlightNeo_HomePage.closeChangeList(driver), "click", "", "Clicked on Close button of change list details");
		

        //publish local world
		
		IFlightNeo_Gantt.publish_Localworld(driver);
		//confirm change 
		IFlightNeo_HomePage.confirmChange(driver);
		Thread.sleep(500);
		// Again finding the first trip flight to verify the aircraft registration
		// change
		//IFlightNeo_Gantt.findFlightInGantt(driver, flighNo, Date, departureAirport, arrivalAirport);
		Screen s = new Screen();
		 s.hover();
		scn.wait(Flight1, 9000);
		scn.doubleClick(Flight1);
		htmlLib.logReport("Flight details open", "Aircraft registration screen shot taken", "Pass", driver, true);
		// Gets Aircraft registration number after flight swap
		String AircraftRegistration_after = IFlightNeo_HomePage.AfterreadAircraftReg(driver);
		// condition to check the flight swap and end the test case
		comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow2ndsearch(driver), "click", "", "Closed the flight details");
		boolean Swap_Sucess = AircraftRegistration_before.contentEquals(AircraftRegistration_after);
		if (!Swap_Sucess == true) {
			htmlLib.logReport("Swap Flights Operation", "Swap flights success", "Pass", driver, true);
		}
		else {
			htmlLib.logReport("Swap Flights Operation", "Swap flights NOT success", "FAIL", driver, true);
		}   
		
		IFlightNeo_HomePage.realworldmode(driver);
		// Close Default LW
				BusinessFunctions.closeTab(driver, 0, false);
				
				Thread.sleep(5000);
		//Go to specified date range
		 IFlightNeo_Gantt.customized_Zoom(driver,newdate_zoom);
		 //Apply customize date zoom
		 IFlightNeo_Gantt.btn_customized_Zoom(driver);
		 Screen s1 = new Screen();
		 s1.hover();
		scn.wait(Flight1, 9000);
		scn.doubleClick(Flight1);
		htmlLib.logReport("Flight details open", "Aircraft registration screen shot taken", "Pass", driver, true);
		// Gets Aircraft registration number after flight swap
		String AircraftRegistration_afterinreal = IFlightNeo_HomePage.readAircraftReg(driver);
		// condition to check the flight swap and end the test case
		//comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow2ndsearch(driver), "click", "", "Closed the flight details");
		boolean Swap_Sucessinreal = AircraftRegistration_before.contentEquals(AircraftRegistration_afterinreal);
		if (!Swap_Sucess == true) {
			htmlLib.logReport("Swap Flights Operation", "Swap flights success shows in real world", "Pass", driver, true);
		}
		else {
			htmlLib.logReport("Swap Flights Operation", "Swap flights NOT success hence not shown in real world", "FAIL", driver, true);
		}   
		
		
		// Close Default LW
		BusinessFunctions.closeTab(driver, 0, false);
		
		IFlightNeo_MessageList.click_Messagelist(driver);
		 for(int flight_counter=0;flight_counter<flightNo.length;flight_counter++)
		 {
		 IFlightNeo_MessageList.set_Messagelistfilters_alloutbound(driver,flightNo[flight_counter],Date,messageDate);
		 }
		 
		 IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Manage Filter Screen Opened", "Manage Filter Screen Open success", "Pass", driver, true);	

			// deleting the previously created filter.

		 if(IFlightNeo_ManageFilter.deleteFilterFromInManageFilter(driver))
		 {
			 System.out.println("Filter created for this TC is deleted");
		 }
	          
		 
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);
			
		}
	}
	
	/*@AfterMethod
	public void closeTest() {


		Driver.tearDownTestExecution(driver);
	}
*/
}