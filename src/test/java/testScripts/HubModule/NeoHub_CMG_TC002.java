package testScripts.HubModule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_Hub;
import pageObjects.IFlightNeo_Messages;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoHub_CMG_TC002 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Ability for Neo Hub to amend the RTT based on different stand/gate combinations");
		CollectTestData.main(tcName);
		// Launching the driver, application and Login
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}

	@Test
	public void flightconnection() throws Exception {
		// Collecting data from Excel and FlightImages Path
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		//String Date = CollectTestData.flightDate;
		String[] flighNo = CollectTestData.flightNumber.split(",", 2);
		String Fromhr=CollectTestData.fromhr;
		String Tohr=CollectTestData.tohr;
		String Arrterminal=CollectTestData.Arrterminal;
		String Arrgate=CollectTestData.Arrgate;
		String Arrstand=CollectTestData.Arrstand;
		String Depterminal=CollectTestData.Depterminal;
		String Depgate=CollectTestData.Depgate;
		String Depstand=CollectTestData.Depstand;
		String Rttinitial;
		String RTTNew;
		//String departureAirport = CollectTestData.origin;
		//String arrivalAirport = CollectTestData.destination;
		//String imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC050\\FlightEY431.png";		
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		// Opening the Gantt Screen and Finding the Flight
		//IFlightNeo_HomePage.selectFIDSscreen(driver);
		//IFlightNeo_Hub.filterFlightconnection(driver);
        //IFlightNeo_Hub.FilterConnection(driver,flighNo[0],flighNo[1],Fromhr,Tohr);
        
        //IFlightNeo_Hub RTT= new IFlightNeo_Hub();
         //Rttinitial=RTT.GetRTTvalue(driver);
         //htmlLib.logReport("Check RTT value ", "RTT value Prior to change: "+Rttinitial, "INFO",driver, true);

		//IFlightNeo_Hub.updateStandGateflight(driver,flighNo[0],flighNo[1],Arrterminal,Arrgate,Arrstand,Depterminal,Depgate,Depstand);
		//Thread.sleep(1000);
		
		//RTTNew=RTT.GetRTTvalue(driver);
		//if(Rttinitial.equalsIgnoreCase(RTTNew))
		//{
			htmlLib.logReport("Check RTT value ", "RTT value not changed successfully", "Fail",
					driver, true);
			
		//}
		
		//else

			
		//{	
		//	htmlLib.logReport("Check RTT value ", "RTT value changed successfully from old value: '" + Rttinitial + "' to new value: "+ RTTNew , "Pass",driver, true);
			

	
			
		//}

		IFlightNeo_Hub.calculateRTTfromreference(driver);
			

	}

	//@AfterMethod
	//public void closeTest() {
	//Driver.sumUpTestScriptExec(driver);
	//}

}
    