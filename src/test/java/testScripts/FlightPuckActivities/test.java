package testScripts.FlightPuckActivities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class test {
	WebDriver driver;
	public static utilities.CommonLibrary com = new utilities.CommonLibrary();
    public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
    public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
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
    
    @Test
	public void mainMethod() throws Exception {
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String flightNo = CollectTestData.flightNumber;
		String flightDate = com.dateCalendarEntry(-1,0,0);
		String messageDate= com.dateCalendarEntry(0,0,0);
		String depCode = CollectTestData.origin;
		String arrCode = CollectTestData.destination;
		String selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_NoOOOITime.png";
		String actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOutTime.png";
		String actualOffTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOffTime.png";
		String actualOnTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOnTime.png";
		String actualInTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualInTime.png";
		
		// Login
		if(IFlightNeo_LoginPage.login(driver, username, password)) {
			// Navigate to Gantt Screen
			Thread.sleep(1000);
			IFlightNeo_HomePage.selectGantt(driver);
			// Switch to Real World Gantt
			if(IFlightNeo_Gantt.changeGanttMode(driver, "Real World")) {
				Thread.sleep(5000);
				System.out.println("In Real World");
				// Close Default LW
				BusinessFunctions.closeTab(driver, 0, false);
				Thread.sleep(3000);
				// Find Flight
				IFlightNeo_Gantt.findFlightInGantt(driver, flightNo, flightDate, depCode, arrCode);	
				// Flight Detail
				Thread.sleep(2000);
				if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
					selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_NoOOOITime_2.png";
					if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
						selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_NoOOOITime_1.png";
						actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOutTime_1.png";
						actualOffTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOffTime_1.png";
						actualOnTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOnTime_1.png";
						actualInTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualInTime_1.png";
					}
				}
				boolean proceed = false;
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, selectedFlightImg, "DoubleClick")) {
					// Update Out time
					String estOutTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_outEstTime");
					String actOutTime = bizCom.updateDate(estOutTime, 0, 1);
					if(updateOOOITime(driver, "Out Time", actOutTime, "16G")) {
						try{
							bizCom.getImageWithSikuli(actualOutTimeImg);
						}
						catch(NullPointerException npe) {
							actualOutTimeImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\EY"+flightNo+"_ActualOutTime_2.png";
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
    		htmlLib.logReport("Verify Update Complete", "Unable to Update due to "+e.getMessage(), "FATAL", driver, true);
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
