package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC007 {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizComm = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Verify user can Search/highlight aircraft by registration/flight number");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
	
	@Test(priority=26)
	public void login() throws Exception {
		try
		{
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_1.PNG";
		String aircraft_imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\A6EIS.PNG";
		
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		//filter by using aircraft registration
		com.performAction(driver, IFlightNeo_Gantt.btn_FindAircraft(driver), "CLICK", "", "Click on Aircraft Registration button");
		Actions action = new Actions(driver);
		action.moveToElement(IFlightNeo_Gantt.innerDIV_FindAircraft(driver));
		com.performAction(driver, IFlightNeo_Gantt.list_AircraftRegistration(driver), "CLICK", "", "Click on dropdownlist");
		Thread.sleep(2000);//1st sep,21 , Moumita Sengupta
		com.performAction(driver, IFlightNeo_Gantt.txtBx_DepSearchOnFindFlight(driver), "SET", "A6-EIS", "Entered Aircraft registration number");
		//com.performAction(driver, IFlightNeo_Gantt.txtBx_DepSearchOnFindFlight(driver), "SET", "-EIS", "Entered Aircraft registration number");
		Thread.sleep(2000);//22nd Feb,22
		com.performAction(driver, IFlightNeo_Gantt.link_FilterResult(driver), "CLICK", "", "Selecting the registration number displayed");
		com.performAction(driver, IFlightNeo_Gantt.btn_SearchButton(driver), "CLICK", "", "Click on search button");
		//Hover on the aircraft displayed
		IFlightNeo_Gantt.selectFlightInGantt(driver, aircraft_imagePath, "HOVER");
		Screen scn1 = new Screen();//1st sep,21 , Moumita Sengupta
		scn1.click();//1st sep,21 , Moumita Sengupta
		//find aircraft using key a
		driver.findElement(By.xpath("//canvas[@id='chronos_overlayW1_gantt_placeholder_aircraftPaneWidgetui-gantt_holder']")).sendKeys("a");
		action.moveToElement(IFlightNeo_Gantt.innerDIV_FindAircraft(driver));
		com.performAction(driver, IFlightNeo_Gantt.list_AircraftRegistration(driver), "CLICK", "", "Click on dropdownlist");
		Thread.sleep(2000);//22nd Feb,22
		com.performAction(driver, IFlightNeo_Gantt.txtBx_DepSearchOnFindFlight(driver), "SET", "A6-EIS", "Entered Aircraft registration number");
		//com.performAction(driver, IFlightNeo_Gantt.txtBx_DepSearchOnFindFlight(driver), "SET", "-EIS", "Entered Aircraft registration number");
		com.performAction(driver, IFlightNeo_Gantt.link_FilterResult(driver), "CLICK", "", "Selecting the registration number displayed");
		com.performAction(driver, IFlightNeo_Gantt.btn_SearchButton(driver), "CLICK", "", "Click on search button");
		//Hover on the aircraft displayed
		IFlightNeo_Gantt.selectFlightInGantt(driver, aircraft_imagePath, "HOVER");
		Screen scn2 = new Screen();//1st sep,21 , Moumita Sengupta
		scn2.click();//1st sep,21 , Moumita Sengupta
		//Step 3: identify by using find flight option
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		Thread.sleep(4000);//added on 23rd Feb,22
		//Hover on the flight displayed
		IFlightNeo_Gantt.selectFlightInGantt(driver, imagePath, "HOVER");
		Screen scn3 = new Screen();//1st sep,21 , Moumita Sengupta
		scn3.click();//1st sep,21 , Moumita Sengupta
		//Step 4: Identify flight by using key word Q
		driver.findElement(By.xpath("//canvas[@id='chronos_overlayW1_gantt_placeholder_aircraftPaneWidgetui-gantt_holder']")).sendKeys("q");
		//find_Flight(driver).click();
		action.moveToElement(IFlightNeo_Gantt.innerDIV_FindFlight(driver));
		// Flight No.
		com.performAction(driver, IFlightNeo_Gantt.txtBx_FlightNoOnFindFlight(driver), "SET", flighNo, "Enter flight number");
		// Flight Date
		IFlightNeo_Gantt.txtbx_DateFieldOnFindFlight(driver).clear();
		com.performAction(driver, IFlightNeo_Gantt.txtbx_DateFieldOnFindFlight(driver), "SET", Date, "Enter flight date");
		// Departure
		com.performAction(driver, IFlightNeo_Gantt.txtBx_DepFieldOnFindFlight(driver), "CLICK", "", "CLick on depCode field");
		com.performAction(driver, IFlightNeo_Gantt.txtBx_DepSearchOnFindFlight(driver), "SET", departureAirport, "Enter flight depCode");
		com.performAction(driver, IFlightNeo_Gantt.link_FilterResult(driver), "CLICK", "", "CLick on depCode field");
		// Arrival
		com.performAction(driver, IFlightNeo_Gantt.txtBx_ArrFieldOnFindFlight(driver), "CLICK", "", "CLick on depCode field");
		com.performAction(driver, IFlightNeo_Gantt.txtBx_ArrSearchOnFindFlight(driver), "SET", arrivalAirport, "Enter flight depCode");
		com.performAction(driver, IFlightNeo_Gantt.link_FilterResult(driver), "CLICK", "", "CLick on depCode field");
		// Search
		com.performAction(driver, IFlightNeo_Gantt.btn_SearchOnFindFlight(driver), "CLICK", "", "CLick on search button");
	
		// Close Dialog
		IFlightNeo_Gantt.dialog_FindFlight(driver).sendKeys(Keys.ESCAPE);
		
		Thread.sleep(6000);
		/*
		 * Screen scn = new Screen();
		 * scn.type(org.sikuli.hotkey.Keys.getModifierName("a")); Thread.sleep(4000);
		 */
		}
		
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);	
			
		}
		
	}
	
	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
