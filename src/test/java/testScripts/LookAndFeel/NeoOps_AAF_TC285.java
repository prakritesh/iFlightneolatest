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

public class NeoOps_AAF_TC285 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Validate user can create a filter to view specific flights on GANTT");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
		/*Driver.setUpTestExecution(tcName, "Check up cancellation notification & Outbound messages after cancellation");
		CollectTestData.main(tcName);*/
	}

	
	@Test(priority=10)
	public void login() throws Exception {
		try {
//		String imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC024_1\\32M.png";
		String username = CollectTestData.userName;
		String password = CollectTestData.password;

		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

		// open the "Manage Filter" screen through the main menu
		//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
		IFlightNeo_HomePage.menuItem_ManageFilter(driver);
		htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

		// add multiple stations to the filter and apply it
		IFlightNeo_ManageFilter.addMultipleFiltersForStationInManageFilter(driver);
		htmlLib.logReport("added stations and applied", "added stations and applied", "Pass", driver, true);	

		// close the "Manage Filter" screen
		IFlightNeo_ManageFilter.closeFilter(driver);
		
		// in case there is a pop-up while closeing the "Manage Filter" screen, close it
		IFlightNeo_ManageFilter.btn_YesToOverrideChanges(driver);

		// open the Gantt chart
		IFlightNeo_HomePage.selectGantt(driver);
		
		// this is to open the tabular pane, to be able to search for the stations
		IFlightNeo_Gantt.click_tabularpaneWithoutW1(driver);
		
		// this is to link the tabular pane with the GANTT and only display those flights
		IFlightNeo_Gantt.click_LinkTabularpaneToGantt(driver);
		
		// this is to search for the stations in the filter
		if(IFlightNeo_Gantt.findStation(driver, new String[] {"MSQ","ISB","KHI","LHE","DEL","ICN","PEK","NRT","PVG", "KTM"})==true) {
			htmlLib.logReport("GANTT displayed with applied filter", "filter applied correctly", "Pass", driver, true);	
		}
		else {
			htmlLib.logReport("GANTT NOT displayed with applied filter", "filter applied INcorrectly", "Fail", driver, true);	
		}
		
		//this is to clear the filter after execution
		IFlightNeo_Gantt.clearFilter(driver);
		} catch (Exception e) {
			htmlLib.logReport("The script failed - check the Exceptions", "The script failed - check the Exceptions", "Fail", driver, true);
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
		}

	}
	
	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
