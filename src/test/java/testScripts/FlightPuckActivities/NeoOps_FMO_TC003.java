package testScripts.FlightPuckActivities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_Messages;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_FMO_TC003 {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {

		
		// Set Up Initial Script Requirement
				Driver.setUpTestExecution(tcName, "Audit trail (flight operation including delay details from FLD) for above operations");
				// launch application
				String browser = CollectTestData.browser;
				String url = CollectTestData.url;	
				driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test(priority=33)
	public void NeoOps_FMO_TC003_Test() throws Exception {
		try {
		// Collecting data from Excel and FlightImages Path
		String sit_URL = CollectTestData.url;
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		String[] flightNo=flighNo.split(",");
		String AflighNo = flightNo[0];
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String Image_Path = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_1stflighttrip.PNG";
		String browser = CollectTestData.browser;
		int inboundconnections;
		int outboundconnections;
		List<String> Actionlist=new ArrayList<String>();  
		// Launching the driver, application and Login
		// Login
		  
				if(IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password)) {
					// Navigate to Gantt Screen
					Thread.sleep(1000);
					// filter the flight
					// open the "Manage Filter" screen through the main menu
							//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
							IFlightNeo_HomePage.menuItem_ManageFilter(driver);
							htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

							// add filter for aircraft subtype in the "Manage Filter" screen
							IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver,flightNo,Date);
							htmlLib.logReport("Filter Saved and Applied", "Filter Saved and Applied", "Pass", driver, true);	
							driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
							Thread.sleep(2000);
										
							// close the "Manage Filter" Screen
							IFlightNeo_ManageFilter.closeFilter(driver);
							driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
							//If unsaved data exists pop-up comes handle that 23rd Feb,22
			                try	
							{
				            IFlightNeo_ManageFilter.btn_YesToOverrideChanges(driver);
								
								//Thread.sleep(2000);
				            }
							
							catch (Exception e)
							{
								
							}
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		//comm.performAction(driver, IFlightNeo_Gantt.btn_CrewConnDetails(driver), "CLICK", "", "Click on Crew Connection Details");
		//Thread.sleep(2000);
		//htmlLib.logReport("Crew Connection Line Displayed", "Crew Connection Line Displayed successfully", "Pass", driver, true);
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
			if(option.contains("Audit Trail for Flight")) {
				comm.performAction(driver, element, "CLICK", "", "clicking on Audit Trail for Flight Option");
				break;
			}
		}
		comm.performAction(driver, IFlightNeo_Messages.window_AuditTrail(driver), "HOVER", "", "display Audit Trail list");
		//comm.performAction(driver, IFlightNeo_Messages.btn_clear(driver), "CLICK", "", "Click Clear button");

		//comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightmessagelist(driver), "click", "", "Closed the message list window");
		//IFlightNeo_Gantt.label_FlightIDInFLD(driver);
		
		//comm.performAction(driver, IFlightNeo_Gantt.btn_ViewDropdownInFlightLegDetails(driver), "Click", "", "Click on view drop down of Flight details");
		/*List<WebElement> viewdrp_List1 = driver.findElements(By.xpath("//ul[@role='menu']//li//a"));
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
*/
		
	//	IFlightNeo_Gantt.label_FlightIDInFLD(driver);

		if(IFlightNeo_Gantt.auditTrailTable(driver).size()>1) {
			int audittablerowcount = IFlightNeo_Gantt.auditTrailTable(driver).size();
			for(int audittablerow=2;audittablerow<=audittablerowcount;audittablerow++)
			{
			
			String Action = driver.findElement(By.xpath("//table[contains(@id,'flightAuditTrailDetailsGrid')]//tr["+audittablerow+"]//td[7]")).getAttribute("title");
			
			Actionlist.add(Action);
			
			
			}
			htmlLib.logReport("Verify all actions in Audit Table on 1st Page", "Following actions are performed on this flight: "+Actionlist, "Pass", driver, true);
		}
		
		else {
			htmlLib.logReport("Verify actions in Audit Table", "No actions are done on this Flight", "INFO", driver, true);
			Thread.sleep(2000);
		}
		
		
		try
		{  
			
					String pagesize=IFlightNeo_Gantt.nextPageofAuditTable(driver).getText();
					int Pagesize=Integer.parseInt(pagesize);
					System.out.println(Pagesize);
					if (Pagesize>1)
					{
             IFlightNeo_Gantt.nextPageofAuditTable(driver).click();
                  
			
			if(IFlightNeo_Gantt.auditTrailTable(driver).size()>1) {
				int audittablerowcount = IFlightNeo_Gantt.auditTrailTable(driver).size();
				for(int audittablerow=2;audittablerow<=audittablerowcount;audittablerow++)
				{
				
				
				String Action = driver.findElement(By.xpath("//table[@id='flightAuditTrailDetailsGrid_W1']//tr["+audittablerow+"]//td[7]")).getAttribute("title");
				
				Actionlist.add(Action);
				
				
				}
				htmlLib.logReport("Verify all actions in Audit Table on 2nd Page", "Following actions are performed on this flight: "+Actionlist, "Pass", driver, true);
			}
		}
					
		}				
		
		catch (Exception e1)
			
		
		{
			
			htmlLib.logReport("Verify all actions in Audit Table on 2nd Page", "There is no 2nd page", "Pass", driver, true);
			
		
			
		}
		Thread.sleep(1000);

		System.out.println("out of inner try catch");
		comm.performAction(driver, IFlightNeo_Gantt.closeFlightAuditTrailwindow(driver), "Click", "", "Audit Details Close button");
		

		}
				
				// open the "Manage Filter" screen through the main menu
				 //Moved the menuItem_ManageFilter method in ManageFilter Page object on 23rd Aug
				 IFlightNeo_HomePage.menuItem_ManageFilter(driver);
					htmlLib.logReport("Manage Filter Screen Opened", "Manage Filter Screen Open success", "Pass", driver, true);	

					// deleting the previously created filter.

				 if(IFlightNeo_ManageFilter.deleteFilterFromInManageFilter(driver))
				 {
					 System.out.println("Filter created for this TC is deleted");
				 }
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);
			
		}
		//IFlightNeo_HomePage.btn_CloseFlightDetailsWindow(driver);
		}
	


	/*@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}*/

}
