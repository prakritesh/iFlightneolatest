package testScripts.FlightPuckActivities;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Button;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC027 {
	WebDriver driver;
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
    public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
    String[] lists = this.getClass().getName().split("\\.");
    String tcName = lists[lists.length-1];
	
	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Different background colours for local/real world");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
	
	@Test(priority=27)
	public void mainMethod() throws Exception {
		try {
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String imgRW = System.getProperty("user.dir")+"\\TestData\\NeoOps_AAF_TC027\\RealWorldBG.png";
		String imgLW = System.getProperty("user.dir")+"\\TestData\\NeoOps_AAF_TC027\\LocalWorldBG.png";
		// Login
		if(IFlightNeo_LoginPage.login(driver, username, password)) {
			// Navigate to Gantt Screen
			IFlightNeo_HomePage.selectGantt(driver);
			// Switch to Real World Gantt
			if(IFlightNeo_Gantt.changeGanttMode(driver, "Real World")) {
				Thread.sleep(7000);
				System.out.println("In Real World");
				Screen scn = new Screen();
				int entries=1;
				for(; entries<=5; entries++) {
					scn.wheel(Button.WHEEL_DOWN, 7);
					Pattern rwBG = new Pattern(imgRW);
					Match match = scn.exists(rwBG);
					if(match != null) {
						htmlLib.logReport("Verify Background Colour of Real World", "Background Colour is Grey", "PASS", driver, true);
						break;
					}
				}
				if(entries>5) {
					htmlLib.logReport("Verify Background Colour of Real World", "Background Colour is NOT Grey", "FAIL", driver, true);
				}
			}
			// Switch to New Local World Gantt
			if(IFlightNeo_Gantt.changeGanttMode(driver, "New Scenario Mode")) {
				Thread.sleep(7000);
				System.out.println("In Local World");
				Screen scn = new Screen();
				int entries=1;
				for(; entries<=5; entries++) {
					scn.wheel(Button.WHEEL_DOWN, 7);
					Pattern lwBG = new Pattern(imgLW);
					Match match = scn.exists(lwBG);
					if(match != null) {
						htmlLib.logReport("Verify Background Colour of Local World", "Background Colour is Pink", "PASS", driver, true);
						break;
					}
				}
				if(entries>5) {
					htmlLib.logReport("Verify Background Colour of Local World", "Background Colour is NOT Pink", "FAIL", driver, true);
				}
			}
	
		}
		
		}
		
		catch(Exception e)
		
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);	
		}
	}
		
	
	@AfterMethod
	public void closeTest() throws InterruptedException {
		driver.manage().deleteAllCookies();
		  Thread.sleep(7000);

		Driver.tearDownTestExecution(driver);
	}
}
