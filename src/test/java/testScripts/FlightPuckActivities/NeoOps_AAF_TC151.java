package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC151 {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
	
	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName, "Verify the ToolTip of the Flight");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Verify the ToolTip of the Flight");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=32)
	public void tooltipTest() throws Exception {
		try
		{
		// Collecting data from Excel and FlightImages Path
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String AflighNo = CollectTestData.flightNumber;
		String[] flightNo=AflighNo.split(",");
		String flighNo = flightNo[0];
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_2.PNG";

		// Launching the driver, application and Login
//		driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Login
		IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		// filter the flight
		// open the "Manage Filter" screen through the main menu
				//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
				IFlightNeo_HomePage.menuItem_ManageFilter(driver);
				htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

				// add filter for aircraft subtype in the "Manage Filter" screen
				IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver,flightNo,Date);
				htmlLib.logReport("Filter Saved and Applied", "Filter Saved and Applied", "Pass", driver, true);	
				driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				Thread.sleep(2000);
							
				// close the "Manage Filter" Screen
				IFlightNeo_ManageFilter.closeFilter(driver);
				driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				//If unsaved data exists pop-up comes handle that 23rd Feb,22
                try	
				{
	            IFlightNeo_ManageFilter.btn_YesToOverrideChanges(driver);
					
					//Thread.sleep(2000);
	            }
				
				catch (Exception e)
				{
					
				}
				Thread.sleep(2000);
		
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		Thread.sleep(2000);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		// Implementing the Screen and Pattern using SikuliScript to select flight and
		// Single click on Flight in gantt screen
		IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "HOVER");
		String toolTip = IFlightNeo_HomePage.msg_toolTipOfFlight(driver).getAttribute("innerHTML");
		htmlLib.logReport("toolTip of the flight", "toolTip displayed successfully", "Pass", driver, true);
		String[] lines = toolTip.split("\\r?\\n");
		System.out.println(lines[0]);
		System.out.println(lines[0].replaceAll("\\s", "").replaceAll("<br>", ""));
		boolean result = IFlightNeo_HomePage.regularExpression(lines[0].replaceAll("\\s", "").replaceAll("<br>", ""));
		System.out.println(result);
		if (!result == false) {
			htmlLib.logReport("Format of toolTip of flight", "Format of toolTip matched", "Pass", driver, true);
		} else {
			htmlLib.logReport("Format of toolTip of flight", "Format of toolTip didn't match", "WARNING", driver, true);
		}
		
		boolean isBroken = bizCom.verifyToolTipMessage(lines[0].replaceAll("\\s", ""), "BrokenTrip");
		
		if(isBroken) {
			htmlLib.logReport("Whether toolTip of the flight displayes broken trip", "toolTip displayed BrokenTrip", "Pass", driver, true);
		}
		else {
			htmlLib.logReport("Whether toolTip of the flight displayes broken trip", "Flight trip is not yet broken", "INFO", driver, true);
			
		// Adding mouse hover so that flight puck can be right clicked ( on 21st Jan,22)
		Screen scn = new Screen();
	    scn.hover();
		//adding a piece of code to handle break trip available option there or not
	    boolean breaktripavlb;
	    breaktripavlb=IFlightNeo_HomePage.breakTrip(driver,Image_Path);
		Thread.sleep(3000);
		Screen Scn1=new Screen();
		Scn1.hover();
        //EY_iFlightNeo_HomePage.Find_flight(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		//IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "singleClick");
		if(breaktripavlb==true)
		{
		IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "HOVER");
		String toolTipAfter = IFlightNeo_HomePage.msg_toolTipOfFlight(driver).getAttribute("innerHTML");
		String[] linesAfter = toolTipAfter.split("\\r?\\n");
		System.out.println(linesAfter[0]);
		System.out.println(linesAfter[0].replaceAll("\\s", ""));

		boolean isBrokenafter = bizCom.verifyToolTipMessage(linesAfter[0].replaceAll("\\s", ""), "BrokenTrip");
		
		if(isBrokenafter) {
			htmlLib.logReport("Whether toolTip of the flight displayes broken trip", "toolTip displayed BrokenTrip successfully", "Pass", driver, true);
		}
		else {
			htmlLib.logReport("Whether toolTip of the flight displayes broken trip", "toolTip not displayed BrokenTrip", "Fail", driver, true);
		}
		}
		else
		{
			htmlLib.logReport("Break the trip", "Break Trip option not available", "INFO", driver, false);
			Screen scn1 = new Screen();
	        scn1.click();
			//IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "SINGLECLICK");

		}
		
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
			
		}
		
		
	}


	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}

}
