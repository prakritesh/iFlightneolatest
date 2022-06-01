package testScripts.LookAndFeel;

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

/****
 * This is a combination of the following test cases: TC024_1 TC124 TC024_2
 * TC024_3 TC025_1
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_AAF_TC024_124_025 {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Create, Manage and Delete Filters");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
		
		/*
		 * Driver.setUpTestExecution(tcName,
		 * "Check up cancellation notification & Outbound messages after cancellation");
		 * CollectTestData.main(tcName);
		 */
	}

	@Test(priority=7)
	public void login() throws Exception {
		try {
			// Collect Test Data
			String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC024_1\\78E.PNG";
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String filter_name;

			// Login
			IFlightNeo_LoginPage.login(driver, username, password);
			// Wait
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			/////////////////////////////////////////////////////////////////////////////////////////
			// TC024_1
			/////////////////////////////////////////////////////////////////////////////////////////
			// open the "Manage Filter" screen through the main menu
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);

			// add filter for aircraft subtype in the "Manage Filter" screen
			IFlightNeo_ManageFilter.addFilterForAircraftSubtypeInManageFilter(driver);
			htmlLib.logReport("Filter Saved and Applied", "Filter Saved and Applied", "Pass", driver, true);
			Thread.sleep(2000); 

			// close the "Manage Filter" Screen
			IFlightNeo_ManageFilter.closeFilter(driver);
			Thread.sleep(2000); 

			// open the Gantt screen
			IFlightNeo_HomePage.selectGantt(driver);
			Thread.sleep(2000); 

			// verify if the filter has been applied correctly by searching for an aircraft
			// this will also fail, if there is no test data in the system
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "singleClick") == true) {
				htmlLib.logReport("aircraft subtype found", "aircraft subtype found", "Pass", driver, true);
			} else {
				htmlLib.logReport("aircraft subtype NOT found", "aircraft subtype NOT found", "Fail", driver, true);
			}

			/////////////////////////////////////////////////////////////////////////////////////////
			// TC124
			/////////////////////////////////////////////////////////////////////////////////////////
			// verify that the filter has been applied correctly in the Gantt screen
			if (IFlightNeo_Gantt.verifyFilter_Gantt(driver) == true) {
				htmlLib.logReport("Filter successfully identified", "Filter successfully identified", "PASS", driver,
						true);
			} else {
				htmlLib.logReport("Filter could not be found", "Filter could not be found", "FAIL", driver, true);
			}

			/////////////////////////////////////////////////////////////////////////////////////////
			// TC024_2
			/////////////////////////////////////////////////////////////////////////////////////////
			// open the "Manage Filter" screen through the main menu
			//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			Thread.sleep(2000); 
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);
			filter_name="Aircraft subtype_32";

			// open an existing filter created in test case TF024_1 and edit it
			//changed method "editFilterInManageFilter" on 23rd Aug to generalize the method
			if(IFlightNeo_ManageFilter.editFilterInManageFilter(driver,filter_name) == true) {
				htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);
			} else {
				htmlLib.logReport("editing the filter failed", "editing the filter failed", "Fail", driver, true);
			}
			Thread.sleep(2000); 

			// apply changes
			if (IFlightNeo_ManageFilter.applyFilterInManageFilterPopup(driver) == true) {
				htmlLib.logReport("apply filter in Manage Filter", "apply filter in Manage Filter success", "Pass",
						driver, true);
			} else {
				htmlLib.logReport("apply filter in Manage Filter failed", "apply filter in Manage Filter failed",
						"Fail", driver, true);
			}
			Thread.sleep(2000); 

			IFlightNeo_ManageFilter.closeManageFilterPopup(driver);
			Thread.sleep(2000); 

			/////////////////////////////////////////////////////////////////////////////////////////
			// TC024_3
			/////////////////////////////////////////////////////////////////////////////////////////

			Thread.sleep(2000);

			// first clear filter
			IFlightNeo_Gantt.clearFilter(driver);
			Thread.sleep(2000); 

			// now steps from TC024_3

			// apply filter in gantt screen
			IFlightNeo_Gantt.applyFilter_Gantt(driver);
			Thread.sleep(2000); 
			htmlLib.logReport("Filter applied", "Filter applied", "Pass", driver, true);

			// verify aircraft subtype is available and filter was correctly applied.
			// the function throws an exception, if the element cannot be found
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "singleClick") == true) {
				htmlLib.logReport("aircraft subtype found", "aircraft subtype found", "Pass", driver, true);
			} else {
				htmlLib.logReport("the filter value was not found in the gantt",
						"the filter value was not found in the gantt", "Fail", driver, true);
			}

			/////////////////////////////////////////////////////////////////////////////////////////
			// TC025_1
			/////////////////////////////////////////////////////////////////////////////////////////
			// first clear filter
			IFlightNeo_Gantt.clearFilter(driver);
			Thread.sleep(2000); 
						
			// open the "Manage Filter" screen through the main menu
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			Thread.sleep(2000); 
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);

			// deleting the previously created filter. this step will not work, if script
			// TC0024_1 has not been executed.
			if(IFlightNeo_ManageFilter.deleteFilterFromTC001InManageFilter(driver,"Aircraft subtype_32") == true) {
				htmlLib.logReport("Delete Filter performed", "Delete Filter success", "Pass", driver, true);
			} else {
				htmlLib.logReport("Delete Filter could not be performed", "Delete Filter could not be success", "Fail",
						driver, true);
			}
			
		} catch (Exception e) {
			htmlLib.logReport("The script failed - check the Exceptions", "The script failed - check the Exceptions"+e.getMessage(), "Fail", driver, true);
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}