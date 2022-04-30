package testScripts.Aircraft;

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
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TCNew_003 {

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
		Driver.setUpTestExecution(tcName, "Able to add an Unserviceable");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void login() throws Exception {
		try {
			// Collect Test Data
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String Date = CollectTestData.flightDate;
			String AflighNo = CollectTestData.flightNumber;
			String RegNo = CollectTestData.Regno;
			String acsubtype = CollectTestData.acsubtype;
			String[] flightNo = AflighNo.split(",", 1);
			String flighNo = flightNo[0];
			String departureAirport = CollectTestData.origin;
			String arrivalAirport = CollectTestData.destination;
			String flightImagelocation = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TCNew_003\\FlightEY"
					+ flighNo + ".PNG";
			String aircraftImageLocation = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TCNew_003\\AircraftRegistration.PNG";
			// Login as Admin role
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);
			// Verify Aircraft in Aircraft popup
			IFlightNeo_Gantt.verifyAircraftInFindAircraftpopup(driver, RegNo ,aircraftImageLocation);
			driver.navigate().refresh();
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			// open the "Manage Filter" screen through the main menu
			// Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter
			// Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);

			// add filter for aircraft subtype in the "Manage Filter" screen
			IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver, flightNo, Date);
			htmlLib.logReport("Filter Saved and Applied", "Filter Saved and Applied", "Pass", driver, true);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			Thread.sleep(2000);

			// close the "Manage Filter" Screen
			IFlightNeo_ManageFilter.closeFilter(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			// If unsaved data exists pop-up comes handle that 23rd Feb,22
			try {
				IFlightNeo_ManageFilter.btn_YesToOverrideChanges(driver);
			}

			catch (Exception e) {

			}
			Thread.sleep(2000);

			// Navigate to Gantt
			// Opening the Gantt Screen and Finding the Flight
			IFlightNeo_HomePage.selectGantt(driver);

			IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);

			// Right click on an existing Flight puck
			IFlightNeo_Gantt.selectFlightInGantt(driver, flightImagelocation, "RIGHTCLICK");
			// -> Edit Flight -> Check the Aircraft added in the previous test case showin
			// in the Edit Flight Window
			IFlightNeo_Gantt.verifyAircraftInEditFlightpopup(driver, RegNo, acsubtype);

			
			  // open the "Manage Filter" screen through the main menu //Moved the
			  //menuItem_ManageFilter method in ManageFilter Page object on 23rd Aug
			  IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			  htmlLib.logReport("Manage Filter Screen Opened",
			  "Manage Filter Screen Open success", "Pass", driver, true);
			  
			  // deleting the previously created filter.
			  
			  if(IFlightNeo_ManageFilter.deleteFilterFromInManageFilter(driver)) {
			  System.out.println("Filter created for this TC is deleted"); }
			 

		}

		catch (Exception e) {
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();

		}

	}

	
	  @AfterMethod public void closeTest() { Driver.tearDownTestExecution(driver);
	  }
	 

}
