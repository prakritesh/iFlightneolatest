package testScripts.Schedulepublication;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_SchedulePublication;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC166 {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	String emailID= CollectTestData.mailUserName+"@etihadppe.ae";
	public static String mailSubject;
	
	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName, "Preview Email and Send to Recipients");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Preview Email and Send to Recipients");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void NeoOps_AAF_TC166_sendReport() throws Exception
	{
		//Collecting data from Excel and FlightImages Path
		 String username = CollectTestData.userName;
		 String password = CollectTestData.password;
		 String startDate = CollectTestData.startDate;
		 String endDate = CollectTestData.endtDate;
		 String type = CollectTestData.type;
		 
		 // Login
		 IFlightNeo_LoginPage.login(driver, username, password);
		 driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		 // Send Publication Report
		 mailSubject = IFlightNeo_SchedulePublication.sendSchedulePublicationReport(startDate,endDate,type,driver,emailID);
	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
