package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_EditFlight;
import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_Notification;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC060_1 {

	WebDriver driver;
	public static utilities.CommonLibrary com = new utilities.CommonLibrary();
    public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
    public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
    public static pageObjects.IFlightNeo_MessageList msglist=new pageObjects.IFlightNeo_MessageList();
    String[] lists = this.getClass().getName().split("\\.");
    String tcName = lists[lists.length-1];
    String prposedNewReg;
    
    
    @BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Verify user can use smart Move feature in Neo Ops (Check one case of Smart Move/Swap each such that system provides a result)");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

    @Test(priority=50)
   	public void mainMethod() throws Exception {
    	
    	try {
    	
    	
   		// Collect Test Data
   		String username = CollectTestData.userName;
   		String password = CollectTestData.password;
   		String flightNo = CollectTestData.flightNumber;
   		String[] flightNoforfilter = CollectTestData.flightNumber.split(",", 2);
   		String flighno=flightNoforfilter[0];
   		String flightDate = com.dateCalendarEntry(0,0,0);
   		String messageDate= com.dateCalendarEntry(0,0,0);
   		String depCode = CollectTestData.origin;
   		String arrCode = CollectTestData.destination;
   		String selectedFlightImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighno+"_2.PNG";
   		//String returnlegImg= System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNoforfilter[1]+"_return flight.PNG";
   		
   		String operation;
   		

   		String Tabname = "//a[contains(text(),'OPS')]";
   		
   		// Login
   		if(IFlightNeo_LoginPage.login(driver, username, password)) {
   			// Navigate to Gantt Screen
   			Thread.sleep(1000);
   			// filter the flight
   			// open the "Manage Filter" screen through the main menu
   					//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
   					IFlightNeo_HomePage.menuItem_ManageFilter(driver);
   					htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	

   					// add filter for aircraft subtype in the "Manage Filter" screen
   					IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver,flightNoforfilter,flightDate);
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
   			IFlightNeo_HomePage.selectGantt(driver);
   			// Switch to Real World Gantt
   			if(IFlightNeo_Gantt.changeGanttMode(driver, "Real World")) {
   				
   				Thread.sleep(5000);
   				//System.out.println("In Real World");
   				// Close Default LW
   				BusinessFunctions.closeTab(driver, 0, false);
   				Thread.sleep(3000);
   				// Find Flight
   				IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighno, flightDate, depCode, arrCode);	
   				// Flight Detail
   				Thread.sleep(2000);
   					
   	   				
   	   					// smart swap operation
   				prposedNewReg=IFlightNeo_Gantt.smartSwap(driver,selectedFlightImg);
   	   				
   	   				
   	   		// click on STD
					//com.performAction(driver,IFlightNeo_EditFlight.grid_STA(driver), "CLICK", "", "Click on STA");
   	   				
   	   			//String STAcurrentTime = driver.findElement(By.xpath("//input[@name='scheduledArrivalDateTime']")).getAttribute("value");
				//	STAnewTime = bizCom.updateDate(STAcurrentTime, 0, 15);
		
   	   		  // we have to change the STA/STD	
					
   	     			//IFlightNeo_EditFlight.changeSTA(driver,STAnewTime);
   	     			//Thread.sleep(2000);			

   	     			// click the SAVE button
   	     			//IFlightNeo_EditFlight.saveChangesSTAforgrndtmviolation(driver);
   	     			
   	     			//Thread.sleep(2000);	
   	     			
   	     			//close edit flight window
   	     			//com.performAction(driver, IFlightNeo_Gantt.close_EditFlight(driver), "Click", "", "Closed the edit flight pop-up");
   	     			
   	     			// go to alert browser & check the Ground Time violation 
   	
   	   				
   	   				
   	   				
   	   				
   			}
   		String filter_name="Filter for swaping";
		// Added the below methods to delete the filter name on 23rd Aug,21
		
		// open the "Manage Filter" screen through the main menu
				//Moved the menuItem_ManageFilter(driver) method in IFlightNeo_ManageFilter Page object on 23rd Aug,21
		IFlightNeo_HomePage.menuItem_ManageFilter(driver);
				htmlLib.logReport("Main Filter Screen Opened", "Main Filter Screen Open success", "Pass", driver, true);	
		
		        IFlightNeo_ManageFilter.deleteFilterFromTC001InManageFilter(driver,filter_name);
		        
		        if (!prposedNewReg.equalsIgnoreCase(null))
		        	
		        {
		        
		        BusinessFunctions.closeTab(driver, 0, false);
		        
		        IFlightNeo_HomePage.selectGantt(driver);
		        
		     // Find Flight
   				IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighno, flightDate, depCode, arrCode);	
   				
   				if(IFlightNeo_Gantt.selectFlightInGantt(driver, selectedFlightImg, "DOUBLECLICK")) {
   				
   			 htmlLib.logReport("Flight details open", "Aircraft registration screen shot taken", "Pass", driver, true);
   			 //wait till screenshot is taken perfectly 
   			 Thread.sleep(3000);
   			 //gets aircraft registration of first flight after swap
   			 String aircraftRegistrationafter=IFlightNeo_HomePage.AfterreadAircraftReg(driver);
   			 
   			 if(aircraftRegistrationafter.equalsIgnoreCase(prposedNewReg))
   				 
   			 {
   				 htmlLib.logReport("Check if Smart Swap happened to proposed Reg number: "+ prposedNewReg, "Smart swap happened successfully", "Pass", driver, true);
   			 }
   			 
   			 
   			com.performAction(driver, IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver), "click", "", "Closed the flight details");
   			}
		        
   				
		       
   		
		        
   		}
   		

}
   		
    }
    	
    	catch(Exception e)
		{
			System.out.println("The exception occured for this TC is: "+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e, "Fail", driver, true);
			
		}
    }
    
    
   /* @AfterMethod
	public void closeTest()
	{		Driver.tearDownTestExecution(driver);
	}
*/
   			
   			
   		
}


