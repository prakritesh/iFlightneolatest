package testScripts.LookAndFeel;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_MessageList;
import utilities.CollectTestData;
import utilities.CommonLibrary;
import utilities.Driver;

/****
 * The LookAndFeel test cases should be run in the following order: TC125
 * TC125_2
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_AAF_TC125 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	public static CommonLibrary com = new CommonLibrary();

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "create & edit custom dynamic filter for the gantt");
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

	@Test(priority=8)
	public void login() throws Exception {
		try {
			String imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC125\\blue.JPG";
			String username = CollectTestData.userName;
			String password = CollectTestData.password;

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the "Manage Filter" screen through the main menu
			// Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
			IFlightNeo_HomePage.menuItem_ManageFilter(driver);
			htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

			// add filter for airport without station in the "Manage Filter" screen
			IFlightNeo_ManageFilter.addFilterForAirportWithoutStationInManageFilter(driver);
			htmlLib.logReport("Filter Saved", "Filter Saved", "Pass", driver, true);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// close the "Manage Filter" Screen
			IFlightNeo_ManageFilter.closeFilter(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// open the Gantt screen
			IFlightNeo_HomePage.selectGantt(driver);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

			// apply generic filter in gantt screen
			IFlightNeo_Gantt.applyGenericAirportFilter_Gantt(driver);
			htmlLib.logReport("Filter applied", "Filter applied", "Pass", driver, true);
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			
			// addning wait time so that gantt can load properly ( on 22nd feb,22)
			Thread.sleep(3000);

			// verify if the filter has been applied correctly by searching for an aircraft
			// with destination SYD
			// this should be flight EY454 and will fail, if no test data is in the system
			if (IFlightNeo_Gantt.selectFlightInGantt(driver, imagePath, "singleClick") == true) {
				htmlLib.logReport("SYD station found", "SYD station found", "Pass", driver, true);
			} else {
				htmlLib.logReport("SYD station NOT found", "SYD station NOT found", "Fail", driver, true);
			}
			
			//Remove the filter applied for this TC (added on 10th Feb,22)
			 Actions a3 = new Actions(driver);
		     // a3.moveToElement(driver.findElement(By.xpath("//li[@title='generic airport name']")));
		      a3.contextClick(driver.findElement(By.xpath("//li[@title='generic airport name']"))).perform();
		      Thread.sleep(2000);
		      com.performAction(driver, driver.findElement(By.xpath("//a[text()='Remove Item']")), "CLICK", "Remove button", "click on Remove Tab");
		     // a3.moveToElement(driver.findElement(By.xpath("//li[@title='generic airport name']"))).build().perform();
		      //contextClick();
			
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
