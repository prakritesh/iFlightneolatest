package iFlightNeo.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_VerifyFlightPucks {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	
	@BeforeMethod (groups = {"demo"})
	public void setup() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Demo Script to Identify Said Flight Pucks");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
	
	@Test (groups = {"demo"})
	public void validatePucks() throws InterruptedException {
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String[] flightNo = CollectTestData.flightNumber.split(",");
		String[] date = CollectTestData.flightDate.split(",");
		
		// Login
		if(IFlightNeo_LoginPage.login(driver, username, password)) {
			// Navigate to Gantt Screen
			IFlightNeo_HomePage.selectGantt(driver);
			// Switch to Real World Gantt
			if(IFlightNeo_Gantt.changeGanttMode(driver, "Real World")) {
				// Close Default LW
				IFlightNeo_Gantt.closeGanttTab(driver, 0); 
				Thread.sleep(3000);
				for(int index=0; index<flightNo.length; index++) {
					// Find Flight
					IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo[index], date[index], "", "");
					Thread.sleep(2000);
					// Open Flight Leg Detail
					String selectedFlightImg = System.getProperty("user.dir") + "\\TestData\\"+tcName+"\\EY"+flightNo[index]+"_2.png";
					if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
						selectedFlightImg = System.getProperty("user.dir") + "\\TestData\\"+tcName+"\\EY"+flightNo[index]+"_1.png";
					}
					try {
						IFlightNeo_Gantt.selectFlightInGantt(driver, selectedFlightImg, "DoubleClick");
						// Close Window
						IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
						System.out.println("Flight '"+flightNo[index]+"' Found");
					}
					catch(Exception e) {
						System.out.println("Flight '"+flightNo[index]+"' Not Found");
					}
					Screen scn=new Screen();
					scn.hover();
				}
			}
		}
	}
}
