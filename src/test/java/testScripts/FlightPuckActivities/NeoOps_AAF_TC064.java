package testScripts.FlightPuckActivities;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.hotkey.Keys;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageFilter;
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC064 {
	
	WebDriver driver;
	public static utilities.CommonLibrary com = new utilities.CommonLibrary();
    public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
    public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
    public static pageObjects.IFlightNeo_MessageList msglist=new pageObjects.IFlightNeo_MessageList();
    String[] lists = this.getClass().getName().split("\\.");
    String tcName = lists[lists.length-1];
    String actOutTime;
    
    @BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Ramp return & outbound data generation");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

    @Test(priority=39)
   	public void mainMethod() throws Exception {
   		// Collect Test Data
   		String username = CollectTestData.userName;
   		String password = CollectTestData.password;
   		String flightNo = CollectTestData.flightNumber;
   		String[] flightNoforfilter = CollectTestData.flightNumber.split(",", 1);
   		String flightDate = com.dateCalendarEntry(-2,0,0);
   		String messageDate= com.dateCalendarEntry(0,0,0);
   		String depCode = CollectTestData.origin;
   		String arrCode = CollectTestData.destination;
   		String delaycode=CollectTestData.delaycode;
   		String selectedFlightImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_NoOOOITime.PNG";
   		String actualOutTimeImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_ActualOutTime.PNG";
   		String actualOffTimeImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_ActualOffTime.PNG";
   		String returnlegImg= System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"A_2.PNG";
   		String newlegImg= System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_Newleg.PNG";

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
   				System.out.println("In Real World");
   				// Close Default LW
   				BusinessFunctions.closeTab(driver, 0, false);
   				Thread.sleep(3000);
   				// Find Flight
   				IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo, flightDate, depCode, arrCode);	
   				// Flight Detail
   				Thread.sleep(2000);
   				if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
   					selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_NoOOOITime_2.png";
   					if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
   						selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_NoOOOITime_1.png";
   						actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOutTime_1.png";
   						actualOffTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOffTime_1.png";
   					
   					}
   				}
   				boolean proceed = false;
   				if(IFlightNeo_Gantt.selectFlightInGantt(driver, selectedFlightImg, "DoubleClick")) {
   					// Update Out time
   					String estOutTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_outEstTime");
   					actOutTime = bizCom.updateDate(estOutTime, 0, 1);
   		
   					if(updateOOOITime(driver, "Out Time", actOutTime, delaycode)) {
   						try{
   							bizCom.getImageWithSikuli(actualOutTimeImg);
   						}
   						catch(NullPointerException npe) {
   							actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOutTime_2.png";
   						}
   						// Verify color change
   						
   						proceed = true;
   						
   						
   						
   						
   					}
   				}
   				
   				
   				
   				
   				
   				Screen scn=new Screen();
   			     scn.hover();
   				//Right Click on the flightpuck & 
   			     
   			  if(IFlightNeo_Gantt.selectFlightInGantt(driver, actualOutTimeImg, "RIGHTCLICK")) {
   				  
   				com.performAction(driver, IFlightNeo_HomePage.menuOption_GroundReturnContinue(driver), "Click", "",
						"Clicking on ground return & continue");
   				
   				//String actOutTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "rerouteWidgetModel_disruptedFlightLeg_actualOffBlockDateTime");
   				
  
   				String actgroundreturnTime = bizCom.updateDate(actOutTime, 0, 50);
   				String revisedouttime = bizCom.updateDate(actOutTime, 2, 00);
   				String revisedintime = bizCom.updateDate(actOutTime, 6, 00);
   				com.performAction(driver, IFlightNeo_HomePage.groundReturn_timeforRamp(driver),"SET", actgroundreturnTime, "Setting groundreturn time");
   				Thread.sleep(2000);
   				com.performAction(driver, IFlightNeo_HomePage.Revisedout_timeforRamp(driver),"SET", revisedouttime, "Setting Revised Out time");
   				Thread.sleep(2000);
   				com.performAction(driver, IFlightNeo_HomePage.Revisedin_timeforRamp(driver),"SET", revisedintime, "Setting Revised In time");
   				Thread.sleep(2000);
   				com.performAction(driver, IFlightNeo_HomePage.Flight_Suffix(driver),"SET", "A", "Setting Flight Suffix for the returned flight");
   				Thread.sleep(2000);
   				com.performAction(driver, IFlightNeo_HomePage.Flight_remarks(driver),"SET", "Testing Ramp Return", "Setting Remarks for the flight");
   				Thread.sleep(2000);
   				String HistoryContent = IFlightNeo_Gantt.checkHistorycontent(driver);
   				htmlLib.logReport("Validate the History Content", "The history content is:" +HistoryContent, "INFO", driver, true);
   			
   				com.performAction(driver, IFlightNeo_HomePage.saveoption_FlightReturnContinue(driver), "Click", "",
						"Clicking on Save button of flight return & continue");
   				Actions action = new Actions(driver);
   				try {
   					action.moveToElement(IFlightNeo_HomePage.dialog_ChangeReason(driver)).build().perform();
   					List<WebElement> list_reason = driver
   							.findElements(By.xpath("//td[@ng-repeat='i in mainScheduleReasonCodes track by $index']//div//a"));
   					for (int tableRow = 0; tableRow < list_reason.size(); tableRow++) {
   						WebElement reason_value = list_reason.get(0);
   						reason_value.click();
   						com.performAction(driver, IFlightNeo_HomePage.dropDown_reason(driver), "SET", "ATC",
   								"setting reason as ATC");
   						com.performAction(driver, IFlightNeo_HomePage.dropDown_resultOfReason(driver), "Click", "",
   								"Selecting ATC as reason code from list");
   						com.performAction(driver, IFlightNeo_HomePage.btn_Publish(driver), "click", "", "Flight Return confirmed");
   						break;
   					}
   				} catch (Exception e) {
   					Thread.sleep(1000);
   					
   				}
   				// wait statement to view the visibiliy of GANTT
   				//IFlightNeo_Gantt.waituntillflightpuckdisplayed(driver);
   				Thread.sleep(7000);
   				htmlLib.logReport("Now Gantt have 2 flight pucks", "Gantt has 2 flight pucks", "INFO", driver, true);	
   				Screen scn1=new Screen();
   				scn1.hover();
   				if(IFlightNeo_Gantt.selectFlightInGantt(driver, returnlegImg, "DoubleClick"))
						
					{
						System.out.println("image found");	
						String Fltstatus = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_flightStatus");
						String Opsstatus= bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_deptStatus");
						String flightSuffix= driver.findElements(By.xpath("//input[contains(@id,'flightIdComponent')]")).get(2).getAttribute("value");
						
						if (Fltstatus.equalsIgnoreCase("OFBL")&& Opsstatus.equalsIgnoreCase("RR")&&flightSuffix.equalsIgnoreCase("A"))
						{
						htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the Ground Return Flight Status is: "+Fltstatus+"ops status is: " +Opsstatus+"Flight Suffix is "+flightSuffix, "Pass", driver, true);	
						}
						
						else
							
						{
							htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the Ground Return Flight Status is not: "+Fltstatus+" ops status is not: " +Opsstatus, "Fail", driver, true);
						}
					}
					
				// Close Window
			    	IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
			    	
			    	if(IFlightNeo_Gantt.selectFlightInGantt(driver, newlegImg, "DoubleClick"))
			    	{
			    		System.out.println("image found");
			    		String Fltstatus = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_flightStatus");
						String Opsstatus= bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_deptStatus");
						
						if (Fltstatus.equalsIgnoreCase("PDEP")&& Opsstatus.equalsIgnoreCase("EARLY"))
						{
						htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the Ground Return Continuation Leg Flight Status is: "+Fltstatus+" ops status is: " +Opsstatus, "Pass", driver, true);	
						}
						
						else
							
						{
							htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the Ground Return Flight Status is not:"+Fltstatus+"ops status is not:" +Opsstatus, "Fail", driver, true);
						}
			    	}
			    	
			    	// Close Window
			    	IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
			    	
   			  }
   				
   				
   			}
   		}
   		
   		// Check Outbound messages 
   		
   		IFlightNeo_MessageList.click_Messagelist(driver);
         htmlLib.logReport("Click on MessageList", "Successfully Clicked on Message list", "Pass", driver, true);
		
		IFlightNeo_MessageList.set_Messagelistfilters_alloutbound(driver,flightNo,flightDate,messageDate);
   		
   		
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
       
   	@AfterMethod
   	public void closeTest() {
   		Driver.tearDownTestExecution(driver);
   	}
   	
   	/**
	 * Method to update Out, Off, On, In time in flight leg details dialog
	 * @return boolean true if update is successful else returns false
	 */
	public static boolean updateOOOITime(WebDriver driver, String fieldToUpdate, String date, String reasonCode) {
    	WebElement oooiTxtbx = null;
    	try {
	    	switch (fieldToUpdate.toUpperCase()) {
			case "OUT TIME":
				oooiTxtbx = IFlightNeo_Gantt.txtBx_ActOutTime(driver);
				// Update time
				com.performAction(driver, oooiTxtbx, "SET", date+Keys.ESC, fieldToUpdate);
				// Delay Reason
				IFlightNeo_Gantt.list_DelayReasonList(driver).click();
				Thread.sleep(2000);
				com.performAction(driver, IFlightNeo_Gantt.link_DelayReasonItem(driver, reasonCode), "Click", "", "Delay Reason Item");
				break;
			case "OFF TIME":
				oooiTxtbx = IFlightNeo_Gantt.txtBx_ActOffTime(driver);
				// Update time
				com.performAction(driver, oooiTxtbx, "SET", date+Keys.ESC, fieldToUpdate);
				break;
			case "ON TIME":
				oooiTxtbx = IFlightNeo_Gantt.txtBx_ActOnTime(driver);
				// Update time
				com.performAction(driver, oooiTxtbx, "SET", date+Keys.ESC, fieldToUpdate);
				break;
			case "IN TIME":
				oooiTxtbx = IFlightNeo_Gantt.txtBx_ActInTime(driver);
				// Update time
				com.performAction(driver, oooiTxtbx, "SET", date+Keys.ESC, fieldToUpdate);
				break;
			}
	    	Thread.sleep(1000);
	    	// Update
	    	com.performAction(driver, IFlightNeo_Gantt.btn_Update(driver), "Click", "", "Update Flight Leg");
	    	// Wait
	    	// Wait
	    	WebDriverWait wait = new WebDriverWait(driver, 30);
	    	wait.until(ExpectedConditions.visibilityOf(IFlightNeo_Gantt.msg_DataSavedSuccess(driver)));
	    	// Close Window
	    	IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
	    	return true;
    	}
    	catch (Exception e) {
    		htmlLib.logReport("Verify Update Complete", "Unable to Update due to "+e, "Fail", driver, true);
			e.printStackTrace();
			
			return false;
		}
    }
	
}
