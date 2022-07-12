package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

@Test
public class NeoOps_AAF_TC040 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.ReportLibrary report = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	// Global variable for test data
	static String flightNo, date, departureAirport, arrivalAirport, imagePath;
	
	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Verify user can add Remarks on a flight leg");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
	@Test(priority=28)
	public void mainMethod() throws Exception {
		try {
		// Collect Test Data
		String username1 = CollectTestData.userName.split(",")[0];
		String password1 = CollectTestData.password.split(",")[0];
		date = CollectTestData.flightDate;
		flightNo = CollectTestData.flightNumber;
		String[] flightNoforfilter = CollectTestData.flightNumber.split(",", 1);
		departureAirport = CollectTestData.origin;
		arrivalAirport = CollectTestData.destination;
		imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_1.PNG";
		String filter_name;
		
		// Login
		IFlightNeo_LoginPage.login(driver, username1, password1);
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
		   // Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		
		// Find Flight
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo, date, departureAirport, arrivalAirport);
		
		// Open Flight Leg Detail 
		IFlightNeo_Gantt.selectFlightInGantt(driver, imagePath, "DoubleClick");
		
		// Click on Remarks Tab of flightLegDetails window
		com.performAction(driver, IFlightNeo_Gantt.link_RemarksTab(driver), "CLICK", "", "Click on remarks tab");
		
		// Select Type of Remarks
		selectRemarksType(driver, "Delay");
		
		// Enter Remarks description
		com.performAction(driver, IFlightNeo_Gantt.txtBx_RemarksDescriptionInFlightLegDetailsWindow(driver), "SET", "Flight Delayed_SIT", "Remarks Description");
		
		//Add button to add remarks
		com.performAction(driver, IFlightNeo_Gantt.btn_AddButtonInRemarksTab(driver), "CLICK", "", "Add Remarks Button");
		
		// click on Update
		com.performAction(driver, IFlightNeo_Gantt.btn_Update(driver), "CLICK", "", "Update FLD Button");
		
		// wait for data saved successfully message
    	WebDriverWait wait = new WebDriverWait(driver, 60);
    	wait.until(ExpectedConditions.visibilityOf(IFlightNeo_Gantt.msg_DataSavedSuccess(driver)));
    	Thread.sleep(2000);// added sleep so that script can go to remarks tab 13th jan,22
		// Verify Remarks
		verifyRemarks("DELAY");
		Thread.sleep(1000);
		filter_name="Filter for swaping";
		// Added the below methods to delete the filter name on 23rd Aug,21
		
				// open the "Manage Filter" screen through the main menu
						//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
				IFlightNeo_HomePage.menuItem_ManageFilter(driver);
						htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	
				
				        IFlightNeo_ManageFilter.deleteFilterFromTC001InManageFilter(driver,filter_name);
		// Logout
		IFlightNeo_HomePage.signOut(driver);
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
		}
	}
	/*@Test(priority=29)
	void login2() throws InterruptedException {
		try {
		String username2 = CollectTestData.userName.split(",")[1];
		String password2 = CollectTestData.password.split(",")[1];
		String[] flightNoforfilter = CollectTestData.flightNumber.split(",", 1);
		String filter_name;
		// Second Login
		
			
			if(IFlightNeo_LoginPage.login(driver, username2, password2)) {
				
				Thread.sleep(1000);
				// open the "Manage Filter" screen through the main menu
				//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
				IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			    htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

				// add filter for aircraft subtype in the "Manage Filter" screen
				IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver,flightNoforfilter,date);
				htmlLib.logReport("Filter Saved and Applied", "Filter Saved and Applied", "Pass", driver, true);	
				driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				
				// close the "Manage Filter" Screen
				IFlightNeo_ManageFilter.closeFilter(driver);
				driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				// Navigate to Gantt Screen
			IFlightNeo_HomePage.selectGantt(driver);
			Thread.sleep(3000);
			// Find Flight
			IFlightNeo_Gantt.findFlightInGantt(driver, flightNo, date, departureAirport, arrivalAirport);
			Thread.sleep(4000);
			new Screen().hover();
			// Open Flight Leg Detail 
			if(IFlightNeo_Gantt.selectFlightInGantt(driver, imagePath, "DoubleClick")) {			
				// Click on Remarks Tab of flightLegDetails window
				com.performAction(driver, IFlightNeo_Gantt.link_RemarksTab(driver), "CLICK", "", "Remarks Tab");
				// Verify Remarks
				verifyRemarks("DELAY");
			
			}
			
			filter_name="Filter for swaping";
			// Added the below methods to delete the filter name on 23rd Aug,21
			
			// open the "Manage Filter" screen through the main menu
					//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
					htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	
			
			        IFlightNeo_ManageFilter.deleteFilterFromTC001InManageFilter(driver,filter_name);
			
			}
			
			
			
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);
		}
	}*/
	
	@AfterMethod
	public void closeTest() throws InterruptedException {
		driver.manage().deleteAllCookies();
		  Thread.sleep(7000);

		Driver.tearDownTestExecution(driver);
	}

	/*****************************************************************************************
	 * Method Filter Element in the List and thus Selects it
	 * ***************************************************************************************/
	public void selectRemarksType(WebDriver driver, String element) {
		com.performAction(driver, IFlightNeo_Gantt.list_RemarksTypeInFlightLegDetailsWindow(driver), "CLICK", "", "Remarks List");
		// Filter
		com.performAction(driver, IFlightNeo_Gantt.txtBx_DepSearchOnFindFlight(driver), "SET", element, "List Filter Box");
		// Select
		com.performAction(driver, IFlightNeo_Gantt.link_FilterResult(driver), "CLICK", "", "Filter Result");
	}
	
	/*****************************************************************************************
	 * Verify Remarks Added to the Flight
	 * @throws InterruptedException 
	 * ***************************************************************************************/
	public void verifyRemarks(String remarks) throws InterruptedException {
		// Click on Remarks tab again
		com.performAction(driver, IFlightNeo_Gantt.link_RemarksTab(driver), "CLICK", "", "Remarks Tab");
		//Thread.sleep(1000);
		// Remarks added is listed
		if(driver.findElement(By.xpath("//td[contains(text(),'"+remarks+"')]")).isDisplayed())
			report.logReport("Verify all the Remarks Added are Listed", "Remarks Saved Successfully and Added to the Flight", "PASS", driver, true);
		else 
			report.logReport("Verify all the Remarks Added are Listed", "Remarks NOT Saved to the Flight", "FAIL", driver, true);
	}
}
