package testScripts.FlightPuckActivities;

//import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_MessageList;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC058 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	public WebDriver driver;
	public String date_zoom;
	private String newdate_zoom;
    static WebDriverWait wait = null;
	static Screen scn;
	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Swap Multiple Flights Using Pan Rectangle Option & Outbound message generation");
		CollectTestData.main(tcName);
	}
	
	@SuppressWarnings("unused")
	@Test(priority=34)
	public void NeoOps_AAF_TC058_Test2() throws Exception 
	{      try
	          {
		 //Collecting data from Excel and FlightImages Path
		 String sit_URL=CollectTestData.url;
		 String sit_Username =CollectTestData.userName;
		 String sit_Password =CollectTestData.password;
		 String Date = CollectTestData.flightDate;
		 String date_zoom = CollectTestData.flightDate;
		 String[] flightNo = CollectTestData.flightNumber.split(",", 4);
		 String messageDate= comm.dateCalendarEntry(0,0,0);
		 String departureAirport = CollectTestData.origin;
		 String arrivalAirport = CollectTestData.destination;
		 String img_flight1_flight = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[0]+"_1_Color.PNG";
		 String img_flight1_actualflight = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[0]+"_1_No Color.PNG";
		 String img_flight2 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[1]+"_1_No Color.PNG";
		 String img_flight2_blank = System.getProperty("user.dir")+ "\\TestData\\NeoOps_AAF_TC058\\FlightEY122.png";
		 String img_flight3 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[2]+"_1_No Color.PNG";
		 String img_flight4 = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[3]+"_1_No Color.PNG";
		 String img_flight4_color = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo[3]+"_1_Color.PNG";
		 String img_blank= System.getProperty("user.dir")+ "\\TestData\\NeoOps_AAF_TC058\\FlightEY20.png";
		 String img_flight1_enlarge=System.getProperty("user.dir")+ "\\TestData\\NeoOps_AAF_TC058\\FlightEY077_Enlarge.png";
		 String Image_Path2=System.getProperty("user.dir") +"\\TestData\\NeoOps_AAF_TC058\\Multiscreen.PNG";
		 String browser = CollectTestData.browser;
		 
		 //Launching the driver, application and Login
		 driver = IFlightNeo_LoginPage.launchApplication(browser, sit_URL);
		 driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		 IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
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

			// open the Gantt screen
			IFlightNeo_HomePage.selectGantt(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		 //Opening the Gantt Screen and Finding the Flight
		 htmlLib.logReport("Gantt Screen Opened", "Gantt Screen Open success", "Pass", driver, true);
		 //calculate date range for customize zoom
		 newdate_zoom=IFlightNeo_Gantt.calculatecustomized_Zoomdate(driver,date_zoom);
		 //Go to specified date range
		 IFlightNeo_Gantt.customized_Zoom(driver,newdate_zoom);
		 //Apply customize date zoom
		 IFlightNeo_Gantt.btn_customized_Zoom(driver);
         //Implementing the Screen and Pattern using SikuliScript
		 scn = new Screen();
		 Pattern Flight1 = new Pattern(img_flight1_actualflight);
		 Pattern Flight2 = new Pattern(img_flight2);
		 Pattern Flight1_color=new Pattern(img_flight1_flight);
		//Identifying the flight using images and performing double mouse click
		 scn.wait(Flight1, 9000);
		 scn.doubleClick(Flight1);
		 //gets aircraft registration of first flight before swap
		 String aircraftRegistrationBefore=IFlightNeo_HomePage.readAircraftReg(driver);
		 htmlLib.logReport("Flight details open", "Aircraft registration screen shot taken", "Pass", driver, true);
		 comm.performAction(driver, IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver), "click", "", "Closed the flight details");
		// Pan Rectangle
	    comm.performAction(driver, IFlightNeo_Gantt.link_PanRectangleOptionInGantt(driver), "click", "", "Clicking on PanRectangle Option");
	    
		 //Implements the WebDriverWait and Action interface for further purpose
		 WebDriverWait wait = new WebDriverWait(driver, 30);
		 Actions action = new Actions(driver);
		
		 //scn.click(Flight1);
		 Thread.sleep(2000);
		 scn.dragDrop(Flight1_color.targetOffset(-100, 0), Flight2.targetOffset(0, 10));
		 // changed the code to hover in different area of gantt screen on 1st feb,22
		//IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path2, "hover");
		  Screen scn=new Screen();
		  scn.hover();
		 Pattern Flight3 = new Pattern(img_flight3);
		 scn.wait(Flight3, 9000);
		 // Match m = scn.exists(Flight3.similar(0.97));
		  scn.keyDown(Key.CTRL);  
		  Pattern Flight4 = new Pattern(img_flight4);
			scn.dragDrop(Flight3.similar(0.75).targetOffset(-100,0), Flight4.targetOffset(0, 10));
			scn.keyUp(Key.CTRL);
			Pattern Flight4_color = new Pattern(img_flight4_color);
			scn.rightClick(Flight4_color.similar(0.77));
		 
			 htmlLib.logReport("Selected multiple flights and right clicked", "Selected multiple flights and right clicked success", "Pass", driver, true);
			  scn.keyUp(Key.CTRL);
			  
		 IFlightNeo_HomePage.swapFlight(driver);
		 Thread.sleep(3000);
		 //IFlightNeo_Gantt.findFlightInGantt(driver, flighNo, Date, departureAirport, arrivalAirport);
		 Thread.sleep(2000);
		 Flight1 = new Pattern(img_flight1_flight);		 
		 //Identifying the flight using images and performing double mouse click
		 scn.wait(Flight1, 9000);
		 scn.doubleClick(Flight1);
		//wait till screenshot is taken perfectly 
		 Thread.sleep(5000);
		 htmlLib.logReport("Flight details open", "Aircraft registration screen shot taken", "Pass", driver, true);
		 //wait till screenshot is taken perfectly 
		 Thread.sleep(3000);
		 //gets aircraft registration of first flight after swap
		 String aircraftRegistrationafter=IFlightNeo_HomePage.AfterreadAircraftReg(driver);
		 comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow2ndsearch(driver), "click", "", "Closed the flight details");
		 //condition to check the flight swap and end the test case
		 boolean Swap_Sucess=aircraftRegistrationBefore.contentEquals(aircraftRegistrationafter);
		 if(!Swap_Sucess==true) {
			 htmlLib.logReport("Swap Flights Operation", "Swap flights success", "Pass", driver, true);
		 }
		 
		 else {
				htmlLib.logReport("Swap Flights Operation", "Swap flights NOT success", "FAIL", driver, true);
			}   
		 // added below method (line number 199 to 203)to check outbound messages after swap ( Moumita , 23/03/22)
		 IFlightNeo_MessageList.click_Messagelist(driver);
		 for(int flight_counter=0;flight_counter<flightNo.length;flight_counter++)
		 {
		 IFlightNeo_MessageList.set_Messagelistfilters_alloutbound(driver,flightNo[flight_counter],Date,messageDate);
		 }
		// open the "Manage Filter" screen through the main menu
		 //Moved the menuItem_ManageFilter method in ManageFilter Page object on 23rd Aug
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

