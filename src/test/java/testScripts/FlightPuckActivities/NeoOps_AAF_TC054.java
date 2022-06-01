package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC054 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Deletion of Flight");
		CollectTestData.main(tcName);
	}

	@Test(priority=32)
	public void NeoOps_AAF_TC054_Test() throws Exception {
		try
		{
		
		// Collecting data from Excel and FlightImages Path
		String sit_URL = CollectTestData.url;
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String AflighNo = CollectTestData.flightNumber;
		String[] flightNo=AflighNo.split(",",2);
		String flighNo = flightNo[0];
        String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_2.PNG";
		String expectedMessage = "Flight(s) deleted successfully.";
		String browser = CollectTestData.browser;
		
		// Launching the driver, application and Login
		driver = IFlightNeo_LoginPage.launchApplication(browser, sit_URL);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
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
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		// Implementing the Screen and Pattern using SikuliScript to select flight and
		// right click in gantt screen
		IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "rightClick");
		htmlLib.logReport("Right Clicked on flight", "Right click flight success", "Pass", driver, true);
		// Deleting the flight
		IFlightNeo_HomePage.DeleteFlight(driver, "Delete");
		System.out.println("everything success");
		// Verify the successfully deleted message is displayed on gantt screen post
		// deletion of flight
		String actualDeletemsg = IFlightNeo_Gantt.msg_DeleteFlightSuccess(driver).getAttribute("innerHTML");
		htmlLib.logReport("Flight Deleted successfully message", "Flight(s) deleted successfully is displayed", "Pass", driver, true);
		Assert.assertEquals(actualDeletemsg, expectedMessage);
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
			System.out.println("The exception occured for this TC is: "+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);
			
		}
	}

	@AfterMethod
	public void closeTest() {

		Driver.tearDownTestExecution(driver);
	}

}