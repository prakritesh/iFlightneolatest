package testScripts.FlightPuckActivities;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC081_82 {
	WebDriver driver;
	public static utilities.CommonLibrary com = new utilities.CommonLibrary();
    public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
    public static utilities.BusinessFunctions bizCom = new utilities.BusinessFunctions();
    String[] lists = this.getClass().getName().split("\\.");
    String tcName = lists[lists.length-1];
    // Global Test Data
    static String flightNo, flightDate, depCode, arrCode;
	static String flightPostResize, originalEstOnTime;
	
    @BeforeTest
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Verify user's Ability to assign a slot for a flight leg & add flying time (EST)");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;	
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
    
    @Test
   	public void mainMethod() throws InterruptedException {
    	// Local Test Data
		String username1 = CollectTestData.userName.split(",")[0];
		String password1 = CollectTestData.password.split(",")[0];
		flightNo = CollectTestData.flightNumber;
		flightDate = com.dateCalendarEntry(1,0,0);
		depCode = CollectTestData.origin;
		arrCode = CollectTestData.destination;
		String selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_Selected.png";
//		String nonSelectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_NonSelected.png";
		String flightWithCTOTImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_CTOT.png";
		flightPostResize = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_PostResize.png";
		// Login
		if(IFlightNeo_LoginPage.login(driver, username1, password1)) {
			// Navigate to Gantt Screen
			Thread.sleep(1000);
			IFlightNeo_HomePage.selectGantt(driver);
			// Switch to Real World Gantt
			if(IFlightNeo_Gantt.changeGanttMode(driver, "Real World")) {
				// Close Default LW
				BusinessFunctions.closeTab(driver, 0, false);
				Thread.sleep(3000);
				// Find Flight
				IFlightNeo_Gantt.findFlightInGantt(driver, flightNo, flightDate, depCode, arrCode);
				Thread.sleep(5000);
				try {
					new Screen().wait(selectedFlightImg, 15);
				} catch (FindFailed e) {}
				// Flight Detail for CTOT Update
				if(bizCom.getImageWithSikuli(selectedFlightImg)==null) {
					selectedFlightImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_Selected_1.png";
					flightWithCTOTImg = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_CTOT_1.png";
					flightPostResize = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_PostResize_1.png";
				}
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, selectedFlightImg, "DoubleClick")) {
					// Verify Flight After CTOT Change
					updateCTOT();
					Thread.sleep(1000);
					verifyCTOTUpdate(flightWithCTOTImg);
				}
				// Wait
		    	IFlightNeo_Gantt.msg_DataSavedSuccess(driver);
				try {	
					while(IFlightNeo_Gantt.msg_DataSavedSuccess(driver).isDisplayed()) {
						Thread.sleep(1000);
					}
				} catch(NullPointerException | StaleElementReferenceException ex) {}
				new Screen().hover();
				// Flight Detail for Puck Resize
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, flightWithCTOTImg, "DoubleClick")) {
					// Verify Flight Puck Size
					updateFlightPuckSize();
					Thread.sleep(1000);
					verifyFlightPuckSize(flightPostResize);
				}
			}
			// Logout
			IFlightNeo_HomePage.signOut(driver);
		}
    }
    
    @Test (priority=2)
    void Login2() throws InterruptedException {
    	String username2 = CollectTestData.userName.split(",")[1];
		String password2 = CollectTestData.password.split(",")[1];
		// Second Login
		if(IFlightNeo_LoginPage.login(driver, username2, password2)) {
			// Navigate to Gantt Screen
			Thread.sleep(1000);
			IFlightNeo_HomePage.selectGantt(driver);
			// Switch to Real World Gantt
			if(IFlightNeo_Gantt.changeGanttMode(driver, "Real World")) {
				// Close Default LW
				BusinessFunctions.closeTab(driver, 0, false);
				Thread.sleep(3000);
				// Find Flight
				IFlightNeo_Gantt.findFlightInGantt(driver, flightNo, flightDate, depCode, arrCode);
				Thread.sleep(5000);
				// Verify Flight Puck Size
				String flightPostResizeSelected = System.getProperty("user.dir")+"\\TestData\\"+tcName+"\\FlightEY"+flightNo+"_PostResizeSelected.png";
				verifyFlightPuckSize(flightPostResizeSelected);
				Thread.sleep(1000);
				new Screen().hover();
				// Reset Data
				if(IFlightNeo_Gantt.selectFlightInGantt(driver, flightPostResize, "DOUBLECLICK")) {
					// Set CTOT Blank
					IFlightNeo_Gantt.txtBx_CTOTime(driver).clear();
					// Reset Est On time
					com.performAction(driver, IFlightNeo_Gantt.txtBx_EstOnTime(driver), "SET", originalEstOnTime+Key.ESC, "EST On Time");
					// Update
					IFlightNeo_Gantt.btn_Update(driver).click();
					// Close
					IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
				}
			}
		}
    }
    
    @AfterTest
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
    
    /**
     * Update Flight Puck Updated After CTOT Change
     * @param flightWithCTOTImg
     * @throws InterruptedException
     */
    protected void updateCTOT() throws InterruptedException {
    	// Update Out time
		String estOutTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_outEstTime");
		String ctoTimeValue = bizCom.updateDate(estOutTime, 0, 30);
		// Update CTOT
		com.performAction(driver, IFlightNeo_Gantt.txtBx_CTOTime(driver), "SET", ctoTimeValue+Key.ESC, "Flight CTOT Field");
		// Update
		Thread.sleep(1000);
		com.performAction(driver, IFlightNeo_Gantt.btn_Update(driver), "CLICK", "", "Update Button");
		// Wait
		IFlightNeo_Gantt.msg_DataSavedSuccess(driver);
		Thread.sleep(1000);
		// Close Window
		IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
    }
    
    /**
     * Verify Flight Puck Updated After CTOT Change
     * @param flightWithCTOTImg
     * @throws InterruptedException
     */
    protected void verifyCTOTUpdate(String flightPostResize) throws InterruptedException {
		try {
			bizCom.getImageWithSikuli(flightPostResize).click();
			htmlLib.logReport("Verify Flight Puck Updated with CTOT", "Flight Puck Updated with CTOT with White Vertical Line", "PASS", driver, true);
		}
		catch (NullPointerException npe) {
			htmlLib.logReport("Verify Flight Puck Updated with CTOT", "Flight Puck is Either Not Updated or Updated with Incorrect Value of CTOT", "FAIL", driver, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Update and Verify Flight Puck Size According to New EST On Time
     * @param flightWithCTOTImg
     * @throws InterruptedException
     */
    protected void updateFlightPuckSize() throws InterruptedException {
    	// Update Out time
    	originalEstOnTime = bizCom.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_onEstTime");
		String newEstOnTimeValue = bizCom.updateDate(originalEstOnTime, 2, 0);
		// Update EST On Time
		com.performAction(driver, IFlightNeo_Gantt.txtBx_EstOnTime(driver), "SET", newEstOnTimeValue+Key.ESC, "Flight EST On Time");
		// Update
		Thread.sleep(1000);
		com.performAction(driver, IFlightNeo_Gantt.btn_Update(driver), "CLICK", "", "Update Button");
		// Wait
		IFlightNeo_Gantt.msg_DataSavedSuccess(driver);
		Thread.sleep(1000);
		// Close Window
		IFlightNeo_Gantt.btn_CloseFlightLegDetail(driver).click();
    }
    
    /**
     * Verify Flight Puck Size According to New EST On Time
     * @param flightWithCTOTImg
     * @throws InterruptedException
     */
    protected void verifyFlightPuckSize(String flightWithCTOTImg) throws InterruptedException {
		try {
			bizCom.getImageWithSikuli(flightWithCTOTImg).click();
			htmlLib.logReport("Verify Neo Accepts Change and Update Flight Puck Size", "Flight Puck Updated According to New EST On Time", "PASS", driver, true);
		}
		catch (NullPointerException npe) {
			htmlLib.logReport("Verify Neo Accepts Change and Update Flight Puck Size", "Flight Puck Size is Either Not Updated or Updated with Incorrect Value of EST On Time", "FAIL", driver, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
}
