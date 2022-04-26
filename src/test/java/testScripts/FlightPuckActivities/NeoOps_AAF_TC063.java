package testScripts.FlightPuckActivities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class NeoOps_AAF_TC063 {
	
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
		Driver.setUpTestExecution(tcName, "Diversion  (Departing from AUH) & outbound data generation");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

    @Test
   	public void mainMethod() throws Exception {
   		// Collect Test Data
   		String username = CollectTestData.userName;
   		String password = CollectTestData.password;
   		String flightNo = CollectTestData.flightNumber;
   		String[] flightNoforfilter = CollectTestData.flightNumber.split(",", 1);
   		String flightDate = com.dateCalendarEntry(-1,0,0);
   		String messageDate= com.dateCalendarEntry(0,0,0);
   		String depCode = CollectTestData.origin;
   		String arrCode = CollectTestData.destination;
   		String delaycode=CollectTestData.delaycode;
   		String reroutestation=CollectTestData.reroutestation;
   		String selectedFlightImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_NoOOOITime.PNG";
   		String actualOutTimeImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_ActualOutTime.PNG";
   		String actualOffTimeImg = System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"_ActualOffTime.PNG";
   		String returnlegImg= System.getProperty("user.dir") + "\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flightNo+"A_1.PNG";
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
   				
   				
   				
   				// Flight Detail
   				if(IFlightNeo_Gantt.selectFlightInGantt(driver, actualOutTimeImg, "DoubleClick") && proceed) {
   					// Update Off time
   					String estOffTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_offEstTime");
   					String actOffTime = bizCom.updateDate(estOffTime, 0, 1);
   					if(updateOOOITime(driver, "Off Time", actOffTime, "")) {
   						Thread.sleep(2000);
   						System.out.println(actOffTime);
   					}
   				}
   				else {
   					proceed = false;
   				}
   				
   				Screen scn=new Screen();
   			     scn.hover();
   				//Right Click on the flightpuck & select divert & continue option
   			     
   			  if(IFlightNeo_Gantt.selectFlightInGantt(driver, actualOffTimeImg, "RIGHTCLICK")) {
   				  
   				com.performAction(driver, IFlightNeo_HomePage.menuOption_Divert(driver), "Click", "",
						"Clicking on Divert option");
   				com.performAction(driver, IFlightNeo_HomePage.menuOption_Diversionandcontinue(driver), "Click", "",
						"Clicking on Divert & Continue option");
   				Thread.sleep(2000);
   				com.performAction(driver, IFlightNeo_HomePage.menuOption_Reroute(driver), "Click", "",
						"Clicking on Diverted station dropdown");
   				
   				Thread.sleep(2000);
   				
   				IFlightNeo_Gantt.Validatediversionoption(driver);
   				String HistoryContent = IFlightNeo_Gantt.checkHistorycontent(driver);
   				htmlLib.logReport("Validate the History Content", "The history content is:" +HistoryContent, "INFO", driver, true);
   				Actions action1 = new Actions(driver);
   				action1.moveToElement(IFlightNeo_HomePage.menuOption_Reroutestationset(driver)).build().perform();
   				com.performAction(driver, IFlightNeo_HomePage.menuOption_Reroutestationset(driver),"SET", reroutestation, "Setting diverted station");
   				com.performAction(driver, IFlightNeo_HomePage.menuOption_Rerouteselect(driver), "Click", "",
						"Clicking on selected Diverted station");
   				String divertedflightlegscharrTime = bizCom.updateDate(actOutTime, 0, 50);
   				String createdFlightLegSchdepTime = bizCom.updateDate(actOutTime, 1, 00);
   				String createdFlightLegScharrTime = bizCom.updateDate(actOutTime, 3, 00);
   				com.performAction(driver, IFlightNeo_HomePage.divertedFlightLegScharr_time(driver),"SET", divertedflightlegscharrTime, "Setting divertedflightlegscharr time");
   				Thread.sleep(2000);
   				com.performAction(driver, IFlightNeo_HomePage.createdFlightLegSchdep_time(driver),"SET",createdFlightLegSchdepTime , "Setting createdFlightLegSchdep time");
   				Thread.sleep(2000);
   				com.performAction(driver, IFlightNeo_HomePage.createdFlightLegScharr_time(driver),"SET", createdFlightLegScharrTime, "Setting createdFlightLegScharr time");
   				Thread.sleep(2000);
   				//com.performAction(driver, IFlightNeo_HomePage.divertedFlight_Suffix(driver),"SET", "X", "Setting Flight Suffix for the diverted flight");
   				com.performAction(driver, IFlightNeo_HomePage.saveoption_DivertedandContinue(driver), "Click", "",
						"Clicking on Save button of diveretedflight");
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
   						Thread.sleep(3000);
   						Screen scn1 = new Screen();
   						scn1.hover();
   						
   						break;
   					}
   				} catch (Exception e) {
   					Thread.sleep(1000);
   					
   				}
   			// waiting for the external system ( OPS-ESB)to send message to Neo System
				Thread.sleep(70000); 
   				if(IFlightNeo_Gantt.selectFlightInGantt(driver, returnlegImg, "DoubleClick"))
						
					{
						System.out.println("image found");	
						String Fltstatus = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_flightStatus");
						String Opsstatus= bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_deptStatus");
						String Divertedarrapt= bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_arrIata");
						
						if (Fltstatus.equalsIgnoreCase("ENRT")&& Opsstatus.equalsIgnoreCase("DIV"))
						{
						htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the diverted Flight Status is:"+Fltstatus+"ops status is:" +Opsstatus, "Pass", driver, true);	
						}
						
						else
							
						{
							htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the diverted Flight Status is not: "+Fltstatus+" ops status is not: " +Opsstatus, "Fail", driver, true);
						}
						
						if (Divertedarrapt.equalsIgnoreCase(reroutestation))
							
						{
							htmlLib.logReport("Verify whether Arr airport of the diverted leg displays the airport to which the flight is diverted", "Flight leg details Open success & the diverted airport is correct which is :"+Divertedarrapt, "Pass", driver, true);	
						}
						
						else 
						{
							htmlLib.logReport("Verify whether Arr airport of the diverted leg displays the airport to which the flight is diverted", "Flight leg details Open success & the diverted airport is not correct which it shows :"+Divertedarrapt, "Fail", driver, true);
							IFlightNeo_MessageList.click_Messagelist(driver);
					         htmlLib.logReport("Click on MessageList", "Successfully Clicked on Message list", "Pass", driver, true);
					         //String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
								
								ArrayList<String> messageTypes = new ArrayList<String>();
								messageTypes.add("AIDXFlightLegNotification");
								ArrayList<String> messageSubTypes = new ArrayList<String>();
								messageSubTypes.add(null);
								ArrayList<String> messageDirections = new ArrayList<String>();
								messageDirections.add("IN");
								
								IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNo, flightDate, messageDate, messageDirections, messageTypes, messageSubTypes);
								Thread.sleep(2000);			
					            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);
					         // open the details of the message
					         // verify the message contains the correct timestamp
					            try {
								if (IFlightNeo_MessageList.aidx_Details(driver,Divertedarrapt)==true)
				
								 {
									htmlLib.logReport("Verify message contains the original arrival airport", "message contains the original airport", "INFO", driver, true);				
								}
								else {
									
									htmlLib.logReport("Verify message contains the original arrival airport", "message does not contain the original airport", "INFO", driver, true);	
									
								}
					                 }
					            
					            catch(Exception e2)
					            
					            {
					            	e2.printStackTrace();
					            }
					            
					         
						}
					}
					
   				bizCom.switchtotab(driver, Tabname);
				// Close Window
			    	IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
			    	
			    	if(IFlightNeo_Gantt.selectFlightInGantt(driver, newlegImg, "DoubleClick"))
			    	{
			    		System.out.println("image found");
			    		String Fltstatus = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_flightStatus");
			    		String Diverteddepapt= driver.findElement(By.xpath("//span[text()='" + reroutestation + "']")).getAttribute("innerText");
						
						
						if (Fltstatus.equalsIgnoreCase("PDEP"))
						{
						htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the Diverted Continuation Leg Flight Status is: "+Fltstatus+" & ops status is blank " , "Pass", driver, true);	
						}
						
						else
							
						{
							htmlLib.logReport("Open flight leg details Screen", "Flight leg details Open success & the diverted continuation Flight Status is not:"+Fltstatus+"ops status is not blank:", "Fail", driver, true);
						}
						
                       if (Diverteddepapt.equalsIgnoreCase(reroutestation))
							
						{
							htmlLib.logReport("Verify whether Dep airport of the New Continuation leg displays the airport to which the flight is diverted", "Flight leg details Open success & the New Continuation leg dep airport is correct which is :"+Diverteddepapt, "Pass", driver, true);	
						}
						
						else 
						{
							htmlLib.logReport("Verify whether Dep airport of the New Continuation leg displays the airport to which the flight is diverted", "Flight leg details Open success & the New Continuation leg dep airport is not correct which it shows :"+Diverteddepapt, "Fail", driver, true);
							
						
			    	    }
			    	
			    	// Close Window
			    	IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
			    	
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
   			  
   			}
   			
   		}
   		
    }
   				


       
   	/*@AfterMethod
   	public void closeTest() {
   		Driver.tearDownTestExecution(driver);
   	}*/
   	
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
    		htmlLib.logReport("Verify Update Complete", "Unable to Update due to "+e.getMessage(), "FATAL", driver, true);
			e.printStackTrace();
			return false;
		}
    }
	
}
