package testScripts.FlightPuckActivities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.hotkey.Keys;
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
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC048 {
	WebDriver driver;
	public static utilities.CommonLibrary com = new utilities.CommonLibrary();
    public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
    public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
    public static pageObjects.IFlightNeo_MessageList msglist=new pageObjects.IFlightNeo_MessageList();
    String[] lists = this.getClass().getName().split("\\.");
    String tcName = lists[lists.length-1];
    
    @BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Validate Flight leg colour changes based on aircraft activity");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
    @Test(priority=30)
    
	public void mainMethod() throws Exception {
    	
    	try {
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String AflighNo = CollectTestData.flightNumber;
		String[] flightNo=AflighNo.split(",");
		String flighNo = flightNo[0];
		String flightDate = com.dateCalendarEntry(-1,0,0);
		String messageDate= com.dateCalendarEntry(0,0,0);
		String depCode = CollectTestData.origin;
		String arrCode = CollectTestData.destination;
		String delaycode=CollectTestData.delaycode;
		String selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_NoOOOITime.png";
		String actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_ActualOutTime.png";
		String actualOffTimeImg = System.getProperty("user.dir")+"\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_ActualOffTime.png";
		String actualOnTimeImg = System.getProperty("user.dir")+"\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_ActualOnTime.png";
		String actualInTimeImg = System.getProperty("user.dir")+"\\TestData\\NeoOps_VerifyFlightPucks\\EY"+flighNo+"_ActualInTime.png";
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
					IFlightNeo_ManageFilter.addFilterForFlightnumberInManageFilter(driver,flightNo,flightDate);
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
				IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, flightDate, depCode, arrCode);	
				// Flight Detail
				Thread.sleep(2000);
				if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
					selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flighNo+"_NoOOOITime_2.png";
					if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
						selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flighNo+"_NoOOOITime_1.png";
						actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flighNo+"_ActualOutTime_1.png";
						actualOffTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flighNo+"_ActualOffTime_1.png";
						actualOnTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flighNo+"_ActualOnTime_1.png";
						actualInTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flighNo+"_ActualInTime_1.png";
					}
				}
				boolean proceed = false;
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, selectedFlightImg, "DoubleClick")) {
					// Update Out time
					String estOutTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_outEstTime");
					String actOutTime = bizCom.updateDate(estOutTime, 0, 1);
					if(updateOOOITime(driver, "Out Time", actOutTime, delaycode)) {
						try{
							bizCom.getImageWithSikuli(actualOutTimeImg);
						}
						catch(NullPointerException npe) {
							actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flighNo+"_ActualOutTime_2.png";
						}
						// Verify color change
						if(verifyFlightColour(actualOutTimeImg)) {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Light Green", 
									"Flight Puck Colour Changes to Light Green for Pushed back Flight", "PASS", driver, true);
						}
						else {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Light Green", 
									"Flight Puck Colour Does NOT Change to Light Green for Pushed back Flight", "FAIL", driver, true);
						}
						proceed = true;
						
						//Added line of codes ( from 144 to 157 for validating delay reason code in AIDX outbound message ( Moumita on 18th March,22)
						IFlightNeo_MessageList.click_Messagelist(driver);
						// verify if the message contains the correct delay reason code
						if(msglist.checkDetails_AIDXoutbound(driver,delaycode,""+ flighNo, flightDate,messageDate))
						   {
							htmlLib.logReport("message contains the correct delay reason code", "message contains the correct delay reason code", "Pass", driver, true);
						   }
							
						else {
							htmlLib.logReport("message DOES NOT contain the correct delay reason code", "message DOES NOT contain the correct delay reason code", "Fail", driver, true);
							System.out.println("expected delay codes would have been:"+delaycode);
							 }
						
						bizCom.switchtotab(driver, Tabname);
					}
				}
				
				
				
				// Flight Detail
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, actualOutTimeImg, "DoubleClick") && proceed) {
					// Update Off time
					String estOffTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_offEstTime");
					String actOffTime = bizCom.updateDate(estOffTime, 0, 1);
					if(updateOOOITime(driver, "Off Time", actOffTime, "")) {
						// Verify color change
						if(verifyFlightColour(actualOffTimeImg)) {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Dark Green", 
									"Flight Puck Colour Changes to Dark Green for Airborne Flight", "PASS", driver, true);
						}
						else {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Dark Green", 
									"Flight Puck Colour Does NOT Change to Dark Green for Airborne Flight", "FAIL", driver, true);
						}
					}
				}
				else {
					proceed = false;
				}
				// Flight Detail
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, actualOffTimeImg, "DoubleClick") && proceed) {
					// Update On time
					String estOnTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_onEstTime");
					String actOnTime = bizCom.updateDate(estOnTime, 0, 1);
					if(updateOOOITime(driver, "On Time", actOnTime, "")) {
						// Verify color change
						if(verifyFlightColour(actualOnTimeImg)) {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Light Green", 
									"Flight Puck Colour Changes to Light Green for Touched Down Flight", "PASS", driver, true);
						}
						else {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Light Green", 
									"Flight Puck Colour Does NOT Change to Light Green for Touched Down Flight", "FAIL", driver, true);
						}
					}
				}
				else {
					proceed = false;
				}
				// Flight Detail
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, actualOnTimeImg, "DoubleClick") && proceed) {
					// Update In time
					String estInTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_inEstTime");
					String actInTime = bizCom.updateDate(estInTime, 0, 1);
					if(updateOOOITime(driver, "In Time", actInTime, "")) {
						// Verify color change
						if(verifyFlightColour(actualInTimeImg)) {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Grey", 
									"Flight Puck Colour Changes to Grey for On-Chox Flight", "PASS", driver, true);
						}
						else {
							htmlLib.logReport("Verify Flight Puck Colour Changes to Grey", 
									"Flight Puck Colour Does NOT Change to Grey for On-Chox Flight", "FAIL", driver, true);
						}
					}
				}
			}
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
    		htmlLib.logReport("Verify Update Complete", "Unable to Update due to "+e, "FATAL", driver, true);
			e.printStackTrace();
			return false;
		}
    }
	
	/**
	 * Method to validate flight colour change depending on OOOI time update
	 * @param flightImgLoc
	 * @return
	 */
	protected boolean verifyFlightColour(String flightImgLoc) {
		// Pushed back flight color
		Screen scn = new Screen();
		scn.click();
		Pattern actOutTime = new Pattern(flightImgLoc);
		Match match = scn.exists(actOutTime);
		if(match!=null) {
			return true;
		}
		else {
			return false;
		}
	}
}
