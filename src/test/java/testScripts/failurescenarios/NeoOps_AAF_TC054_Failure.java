package testScripts.failurescenarios;

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
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC054_Failure {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Verifying Failure on test data");
		CollectTestData.main(tcName);
	}

	@Test
	public void NeoOps_AAF_TC054_Test() throws Exception {
		
		// Collecting data from Excel and FlightImages Path
		String sit_URL = CollectTestData.url;
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC054\\FlightEY222.png";
		String expectedMessage = "Flight(s) deleted successfully.";
		String browser = CollectTestData.browser;
		
		// Launching the driver, application and Login
		driver = IFlightNeo_LoginPage.launchApplication(browser, sit_URL);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		try {
			IFlightNeo_Gantt.msg_FlightNotAvailable(driver);
			Thread.sleep(2000);
			htmlLib.logReport("Flight Availability", "Flight details are not available in the iNeo", "Fail", driver, true);
		} catch (Exception e) {
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
		}		
		
		
	}	
	
	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
