package testScripts.FlightPuckActivities;

import java.util.ArrayList;
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
import pageObjects.IFlightNeo_Messages;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC050 {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Verify Crew Connection Details");
		CollectTestData.main(tcName);
	}

	@Test(priority=29)
	public void NeoOps_AAF_TC050_Test() throws Exception {
		try {
		// Collecting data from Excel and FlightImages Path
		String sit_URL = CollectTestData.url;
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_2.PNG";
		String browser = CollectTestData.browser;
		int inboundconnections;
		int outboundconnections;
		// Launching the driver, application and Login
		driver = IFlightNeo_LoginPage.launchApplication(browser, sit_URL);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		comm.performAction(driver, IFlightNeo_Gantt.btn_CrewConnDetails(driver), "CLICK", "", "Click on Crew Connection Details");
		Thread.sleep(2000);
		htmlLib.logReport("Crew Connection Line Displayed", "Crew Connection Line Displayed successfully", "Pass", driver, true);
		IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "doubleClick");
		// Wait
		IFlightNeo_Gantt.label_FlightIDInFLD(driver);
		comm.performAction(driver, IFlightNeo_Gantt.btn_ViewDropdownInFlightLegDetails(driver), "Click", "", "Click on view drop down of Flight details");
		List<WebElement> viewdrp_List = driver.findElements(By.xpath("//ul[@role='menu']//li//a"));
		for(int drp=0; drp<viewdrp_List.size();drp++)
		{
			WebElement element =viewdrp_List.get(drp);
			String option=element.getAttribute("innerHTML");
			System.out.println(option);
			if(option.contains("Message List")) {
				comm.performAction(driver, element, "CLICK", "", "clicking on Message list Option");
				break;
			}
		}
		comm.performAction(driver, IFlightNeo_Messages.window_msgList(driver), "HOVER", "", "display message list");
		comm.performAction(driver, IFlightNeo_Messages.btn_clear(driver), "CLICK", "", "Click Clear button");

		comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightmessagelist(driver), "click", "", "Closed the message list window");
		IFlightNeo_Gantt.label_FlightIDInFLD(driver);
		
		comm.performAction(driver, IFlightNeo_Gantt.btn_ViewDropdownInFlightLegDetails(driver), "Click", "", "Click on view drop down of Flight details");
		List<WebElement> viewdrp_List1 = driver.findElements(By.xpath("//ul[@role='menu']//li//a"));
		for(int drp=0; drp<viewdrp_List1.size();drp++)
		{
			WebElement element =viewdrp_List1.get(drp);
			String option=element.getAttribute("innerHTML");
			System.out.println(option);
			if(option.contains("Crew Connection Details")) {
				comm.performAction(driver, element, "CLICK", "", "clicking on Crew Connection Details Option");
				break;
			}
			
		}

		
		IFlightNeo_Gantt.label_FlightIDInFLD(driver);

		if(IFlightNeo_Gantt.label_CockPitCrewUnderFlightConnDetails(driver).isDisplayed()) {
			htmlLib.logReport("Verify Crew tab is highlighted", "Crew tab is highlighted by default", "Pass", driver, true);
		}
		else {
			htmlLib.logReport("Verify Crew tab is highlighted", "Crew tab is not highlighted by default", "Fail", driver, true);
			Thread.sleep(2000);
			comm.performAction(driver, IFlightNeo_Gantt.btn_CrewTabUnderFlightConnDetails(driver), "CLICK", "", "Click on crew Tab");
			Thread.sleep(2000);
			htmlLib.logReport("Verify Crew tab is highlighted manually", "Crew tab is highlighted manually", "Pass", driver, true);
		}
		Thread.sleep(1000);
		IFlightNeo_Gantt.dragFlightconnectiondetailswindow(driver);
		//WebElement InBoundConnectionGrid=driver.findElement(By.xpath("//table[contains(@id,'InBoundConnectionGrid')]//tr[2]"));
		//WebElement OutBoundConnectionGrid=driver.findElement(By.xpath("//table[contains(@id,'OutBoundConnectionGrid')]//tr[2]"));
		//modified code on 18th Jan,2022 to make the code working correctly
		int connectionlistexistence=IFlightNeo_Gantt.InboundorOutboundlistexists(driver);
		
		if(connectionlistexistence==1)
		{
			inboundconnections=bizCom.getRowCount(driver, "//table[contains(@id,'InBoundConnectionGrid')]")-1;
			outboundconnections=bizCom.getRowCount(driver, "//table[contains(@id,'OutBoundConnectionGrid')]")-1;
		ArrayList<String> Actualconnectiontime=bizCom.getColumnData(driver, "//table[contains(@id,'InBoundConnectionGrid')]", 0, 4);
		ArrayList<String> Minimumconnectiontime=bizCom.getColumnData(driver, "//table[contains(@id,'InBoundConnectionGrid')]", 0, 13);
		htmlLib.logReport("Verify inbound connection list", "There are total " + inboundconnections +" connections for this flight", "Pass", driver, true);
		htmlLib.logReport("Verify inbound connection list", "There are total " + outboundconnections +" connections for this flight", "Pass", driver, false);
		
		for(int rowcount=1;rowcount<inboundconnections+1;rowcount++)
		{
		htmlLib.logReport("Verify inbound connection status", "For the "+rowcount+"th connection Actual connection time is " + Actualconnectiontime.get(rowcount) + " & minimum connection time is " + Minimumconnectiontime.get(rowcount), "Pass", driver, false);
		}
		
		}
		
		if(connectionlistexistence==2)
		{   inboundconnections=bizCom.getRowCount(driver, "//table[contains(@id,'InBoundConnectionGrid')]")-1;
			outboundconnections=bizCom.getRowCount(driver, "//table[contains(@id,'OutBoundConnectionGrid')]")-1;
			ArrayList<String> Actualconnectiontime=bizCom.getColumnData(driver, "//table[contains(@id,'OutBoundConnectionGrid')]", 0, 4);
			ArrayList<String> Minimumconnectiontime=bizCom.getColumnData(driver, "//table[contains(@id,'OutBoundConnectionGrid')]", 0, 13);
			htmlLib.logReport("Verify inbound connection list", "There are total " + inboundconnections +" connections for this flight", "Pass", driver, false);
			htmlLib.logReport("Verify outbound connection list", "There are total " + outboundconnections +" connections for this flight", "Pass", driver, true);
			for(int rowcount=1;rowcount<outboundconnections+1;rowcount++)
			{
			htmlLib.logReport("Verify outbound connection status", "For the "+rowcount+"th connection Actual connection time is " + Actualconnectiontime.get(rowcount) + " & minimum connection time is " + Minimumconnectiontime.get(rowcount), "Pass", driver, false);
			}
		}
		if (connectionlistexistence==3)
		{
		
			htmlLib.logReport("Verify connection list", "There is no inbound or outbound connection in this flight", "Pass", driver, true);
			
		}
		IFlightNeo_Gantt.closeFlightconnectiondetailwindow(driver);
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			
		}
		//IFlightNeo_HomePage.btn_CloseFlightDetailsWindow(driver);
		}
	


	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}

}
