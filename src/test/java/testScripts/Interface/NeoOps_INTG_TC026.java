package testScripts.Interface;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_AddFlight;
import pageObjects.IFlightNeo_FlightLegDetails;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * execute in this order:
 * 1) NeoOps_INTG_TC025 (this is required pre-requisite)
 * 2) NeoOps_INTG_TC026 (this script)
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC026 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "update SRM message, check new is updated with new times");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=23)
	public void login() throws Exception {
		try {
			String inputfile = System.getProperty("user.dir") + "\\TestData\\NeoOps_INTG_TC026\\"+System.currentTimeMillis()+".txt";
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// i don't need to create a flight, as TC025 is pre-requisite
			
			// calculate CTOT and EOBT
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
			timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Calendar calEOBT = Calendar.getInstance();
			calEOBT.setTime(new Date());
			calEOBT.add(Calendar.MINUTE, 11);
			String eobt =timeFormat.format(calEOBT.getTime());
			
			Calendar calCTOT = Calendar.getInstance();
			calCTOT.setTime(new Date());
			calCTOT.add(Calendar.MINUTE, 16);
			String ctot =timeFormat.format(calCTOT.getTime());

			// create file
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			PrintWriter writer = new PrintWriter(inputfile, "UTF-8");
			writer.println("-TITLE SRM");
			writer.println("-ARCID ETD" + flightNumber);
			writer.println("-IFPLID AA19801539");
			writer.println("-ADEP OMAA");
			writer.println("-ADES WMKK");    // the flight must be to Kuala Lumpur (code WMKK)
			writer.println("-EOBD "+dateFormat.format(new Date()));  // todays date
			writer.println("-EOBT " + eobt);    
			writer.println("-NEWCTOT " + ctot);    // this is hardcoded further down as 08:11
			writer.println("-REGUL LCS12X06");
			writer.println("-TTO -PTID VELOX -TO 1248 -FL F254");
			writer.println("-TAXITIME 0012");
			writer.println("-REGCAUSE SE 82");
			writer.close();
			
			// open the "Message List" window
			IFlightNeo_HomePage.selectMessageList(driver);
			Thread.sleep(2000);
			
			// upload a file
			IFlightNeo_MessageList.uploadATCfile(driver,inputfile);
			Thread.sleep(10000);
			
			// close the message list screen
			BusinessFunctions.closeTab(driver, 0, true);
			Thread.sleep(2000);
			
			// open "Flight Leg Details" screen
			IFlightNeo_HomePage.selectFlightLegDetails(driver);
			Thread.sleep(2000);

			// search for the flight leg details
			IFlightNeo_FlightLegDetails.searchFlightLeg(driver, flightNumber);
			Thread.sleep(2000); // if i don't have a sleep here, the value is not yet filled when i'm reading the attribute in the next step
			
			// verify CTOT is the same as in the uploaded file
			String ctotHtml = IFlightNeo_FlightLegDetails.txtBx_CTOT(driver).getAttribute("value");
			
			if(ctotHtml.contains(ctot.substring(0,2)+":"+ctot.substring(2,4)) == true) {
				htmlLib.logReport("CTOT contains the correct timestamp for CTOT", "CTOT contains the correct timestamp for CTOT", "Pass", driver, true);				
			}
			else {
				htmlLib.logReport("CTOT DOES NOT contain the correct timestamp", "CTOT DOES NOT contain the correct timestamp", "Fail", driver, true);
				System.out.println("CTOT: " + ctotHtml);
				System.out.println("expected CTOT: " + ctot.substring(0,2)+":"+ctot.substring(2,4));
			}
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
