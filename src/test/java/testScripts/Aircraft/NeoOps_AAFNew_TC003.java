package testScripts.Aircraft;

import java.util.concurrent.TimeUnit;

import javax.swing.plaf.basic.BasicInternalFrameUI.InternalFrameLayout;

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

public class NeoOps_AAFNew_TC003 {


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
			String RegNo = CollectTestData.Regno;
			String equipment = "CONNECTIVITY";

			String aircraftImageLocation = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAFNew_TC003\\AircraftRegistration.PNG";
			String afterAddingAircraftEquipment = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAFNew_TC003\\AfterAddingAircraftEquipment.PNG";
			// Login as Admin role
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			// Navigate to the Gantt Chart
			IFlightNeo_HomePage.selectGantt(driver);
			
			//Right click on a/c reg and select "Add Manage Aircraft Equipment"
			IFlightNeo_Gantt.addManageAircraftEquipment(driver ,RegNo,aircraftImageLocation,equipment, afterAddingAircraftEquipment);
			IFlightNeo_Gantt.deleteAllAircraftEquipment(driver,afterAddingAircraftEquipment); 
			
		}

		catch (Exception e) {
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();

		}

	}

	
	/*
	 * @AfterMethod public void closeTest() { Driver.tearDownTestExecution(driver);
	 * }
	 */
	 



}
