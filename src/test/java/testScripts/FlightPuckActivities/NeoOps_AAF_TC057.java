package testScripts.FlightPuckActivities;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Button;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_MessageList;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC057 {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static Screen scn;

	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Registration Change Using Drag and Drop & Outbound Message generation");
		CollectTestData.main(tcName);
	}

	@SuppressWarnings("unused")
	@Test(priority=31)
	//script is refactored on 21st jan,22 to achieve better performance
	public void NeoOps_AAF_TC057_Test() throws Exception {
		try {
		// Collecting data from Excel and FlightImages Path
		String sit_URL = CollectTestData.url;
		String sit_Username = CollectTestData.userName;
		String sit_Password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String AflighNo = CollectTestData.flightNumber;
		String messageDate= comm.dateCalendarEntry(0,0,0);
		String[] flightNo=AflighNo.split(",",2);
		String flighNo = flightNo[0];
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String Image_AircraftReg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\A6AEB.PNG";
		//String img_firstFlight_color = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_2.PNG";
		
		//String img_secondFlight = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC057\\FlightEY017.png";
		String browser = CollectTestData.browser;

		// Launching the driver, application and Login
		driver = IFlightNeo_LoginPage.launchApplication(browser, sit_URL);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		IFlightNeo_LoginPage.login(driver, sit_Username, sit_Password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		
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
	
		Thread.sleep(2000);
					
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		Thread.sleep(1000);
		String img_firstFlight_color = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_2.PNG";
		if(bizCom.getImageWithSikuli(img_firstFlight_color)==null) {
			img_firstFlight_color = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_1.PNG";
		}

		// Implementing the Screen and Pattern using SikuliScript
		scn = new Screen();
		Pattern Flight1 = new Pattern(img_firstFlight_color);
		// Identifying the flight using images and performing double mouse click
		scn.wait(Flight1, 900);
		scn.doubleClick(Flight1);
		// gets aircraft registration of first flight before swap
		String aircraftRegistrationBefore = IFlightNeo_HomePage.AfterreadAircraftReg(driver);
		htmlLib.logReport("View aircraft Reg prior to change", "Aircraft Reg checked", "INFO", driver, true);
		IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
		//scn.click(Flight1);
		 
		/*int scroll_cnt=45;
		Pattern Flight2 = new Pattern(img_secondFlight);

		 for(int i=0; i<scroll_cnt;i++) 
		 {
		  scn.wheel(Button.WHEEL_DOWN, 3);		  
		  		  Match m = scn.exists(Flight2.exact());
		  if(m!=null) 
			  
		  { scn.keyDown(Key.CTRL);
			  scn.click(Flight2.exact());
		   scn.keyUp(Key.CTRL);
		    break;
		  }
		 }*/
		  Screen scn=new Screen();
		  scn.hover();
		  Pattern aircraft_Registration = new Pattern(Image_AircraftReg);
		  scn.dragDrop(Flight1.similar(0.80), aircraft_Registration.similar(0.97));
		  IFlightNeo_HomePage.geographicaldiscontinuity(driver);
		Actions action = new Actions(driver);
		try {
			action.moveToElement(IFlightNeo_HomePage.dialog_ChangeReason(driver)).build().perform();
			List<WebElement> list_reason = driver
					.findElements(By.xpath("//td[@ng-repeat='i in mainScheduleReasonCodes track by $index']//div//a"));
			for (int tableRow = 0; tableRow < list_reason.size(); tableRow++) {
				WebElement reason_value = list_reason.get(0);
				reason_value.click();
				comm.performAction(driver, IFlightNeo_HomePage.dropDown_reason(driver), "SET", "ATC",
						"setting reason as ATC");
				comm.performAction(driver, IFlightNeo_HomePage.dropDown_resultOfReason(driver), "Click", "",
						"Selecting ATC as reason code from list");
				comm.performAction(driver, IFlightNeo_HomePage.btn_Publish(driver), "click", "", "Swap flight confirmed");
			
				break;
			}
		} catch (Exception e) {
			Thread.sleep(1000);
		
		}
		try
		{
	
		action.moveToElement(IFlightNeo_Gantt.dialog_RuleWarning(driver)).build().perform();
		}
		catch(Exception e)
		{
			System.out.println("No force-publish window appeared");
		}
		IFlightNeo_HomePage.forcepublish(driver);
		Thread.sleep(5000);
		try {
			action.moveToElement(IFlightNeo_HomePage.dialog_ChangeReason(driver)).build().perform();
			List<WebElement> list_reason = driver
					.findElements(By.xpath("//td[@ng-repeat='i in mainScheduleReasonCodes track by $index']//div//a"));
			for (int tableRow = 0; tableRow < list_reason.size(); tableRow++) {
				WebElement reason_value = list_reason.get(0);
				reason_value.click();
				comm.performAction(driver, IFlightNeo_HomePage.dropDown_reason(driver), "SET", "ATC",
						"setting reason as ATC");
				comm.performAction(driver, IFlightNeo_HomePage.dropDown_resultOfReason(driver), "Click", "",
						"Selecting ATC as reason code from list");
				comm.performAction(driver, IFlightNeo_HomePage.btn_Publish(driver), "click", "", "Swap flight confirmed");
	
				break;
			}
		} catch (Exception e) {
			Thread.sleep(1000);
			
		}
		Thread.sleep(4000);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		
		Thread.sleep(3000);
		Flight1 = new Pattern(img_firstFlight_color);
		// Identifying the flight using images and performing double mouse click
		//scn.wait(Flight1, 9000);
		scn.doubleClick(Flight1);
		// gets aircraft registration of first flight after swap
		String aircraftRegistrationafter = IFlightNeo_HomePage.AfterreadAircraftReg(driver);
		htmlLib.logReport("View aircraft Reg after change", "Aircraft Reg checked", "INFO", driver, true);
		// adding the code to close the flight leg details so that flight date element does not appear twice in the application ( Moumita on 25th mar,22)
		comm.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow2ndsearch(driver), "click", "", "Closed the flight details");
		// condition to check the flight swap and end the test case
		boolean Swap_Sucess = aircraftRegistrationBefore.contentEquals(aircraftRegistrationafter);
		if (!Swap_Sucess == true) {
			htmlLib.logReport("Swap Flights Operation", "Swap flights success", "Pass", driver, true);
		}
		
		
		
		 // added below method (line number 218 to 219)to check outbound messages after swap ( Moumita , 24/03/22)
		 IFlightNeo_MessageList.click_Messagelist(driver);
		 IFlightNeo_MessageList.set_Messagelistfilters_alloutbound(driver,flighNo,Date,messageDate);
		 
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
	}

	@AfterMethod
	public void closeTest() {

		Driver.tearDownTestExecution(driver);
	}

}
