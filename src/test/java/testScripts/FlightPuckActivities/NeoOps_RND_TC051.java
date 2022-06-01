package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_Notification;
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_RND_TC051 {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName,
		 * "Check up cancellation notification & Outbound messages after cancellation");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Check up cancellation notification & Outbound messages after cancellation");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=47)
	public void login() throws Exception {
		try
		{
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String date = CollectTestData.flightDate;
		String flightNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String[] flightNoforfilter = CollectTestData.flightNumber.split(",", 1);
		String imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_2.PNG";
		String messageDate= comm.dateCalendarEntry(0,0,0);
		String filter_name;
//		driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		// open the "Manage Filter" screen through the main menu
		//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
		IFlightNeo_HomePage.menuItem_ManageFilter(driver);
	    htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

		// add filter for aircraft subtype in the "Manage Filter" screen
		IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver,flightNoforfilter,date);
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
		// select Gantt Option
		IFlightNeo_HomePage.selectGantt(driver);
		// Change To Real World Gantt Mode 
		IFlightNeo_Gantt.changeGanttMode(driver, "Real World");
		// Close Default Mode
		BusinessFunctions.closeTab(driver, 0, false);
		Thread.sleep(4000);
		//Re-apply filter
		//IFlightNeo_ManageFilter.filter_Apply(driver);
		//htmlLib.logReport("Verify Filter Applied", "Filter Applied", "Pass", driver, true);	
		Thread.sleep(4000);
//		IFlightNeo_HomePage.RealWorld_Find_flight(driver, arrivalAirport, date, departureAirport, arrivalAirport);
		IFlightNeo_Gantt.findFlightInGantt(driver, flightNo, date, departureAirport, arrivalAirport);
		Thread.sleep(4000);
		// Cancel Flight
		IFlightNeo_Gantt.cancelFlight(driver, imagePath);
		
		
		
		if (IFlightNeo_Notification.browse_notification(driver, flightNo,"cancellation"))
		{		
			
	    		
			
		htmlLib.logReport("Check if flight cancelled", "Flight is cancelled", "Pass", driver, true);
		
		htmlLib.logReport("Notification Generated", "Notification generated for Flight cancellation", "Pass", driver, true);
	    }
		
		else 
			
		{
			htmlLib.logReport("Check if flight cancelled", "Flight is not cancelled", "Fail", driver, true);
			
			htmlLib.logReport("Check if Notification Generated", "Notification not generated for Flight cancellation", "Fail", driver, true);
		}
			
		
		IFlightNeo_MessageList.click_Messagelist(driver);
		
		htmlLib.logReport("Click on MessageList", "Successfully Clicked on Message list", "Pass", driver, true);
		
		IFlightNeo_MessageList.set_Messagelistfilters_alloutbound(driver,flightNo,date,messageDate);
		
		filter_name="Filter for swaping";
		// Added the below methods to delete the filter name on 23rd Aug,21
		
		// open the "Manage Filter" screen through the main menu
				//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
		IFlightNeo_HomePage.menuItem_ManageFilter(driver);
				htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	
		
		        IFlightNeo_ManageFilter.deleteFilterFromTC001InManageFilter(driver,filter_name);
		        
		        
		
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);
			
		}
	}
	
	@AfterMethod
	public void closeTest()
	{		Driver.tearDownTestExecution(driver);
	}

}
