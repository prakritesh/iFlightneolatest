package testScripts.FlightPuckActivities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_Messages;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.BusinessFunctions;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC261 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizComm = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;
	
	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Load AIDX Message and Verify the Data in iFlight Neo Application");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}
	
	@Test
	public void login() throws Exception {
		try {
		// Collect Test Data
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flighNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String MessageType = CollectTestData.messageType;
		String Image_Path1 = System.getProperty("user.dir") +"\\TestData\\NeoOps_AAF_TC261\\FlightEY101.png";
		String Image_Path2 = System.getProperty("user.dir") +"\\TestData\\NeoOps_AAF_TC261\\Multiscreen.PNG";
		String MessagePath = "";
		if (MessageType.contains("OOOI")) {
			MessagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC261\\OOOI.txt";
		}
		if (MessageType.contains("AIDX")) {
			MessagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC261\\Actual OOOI update.xml";
			MessagePath = System.getProperty("user.dir") +"\\TestData\\NeoOps_AAF_TC261\\OOOI.txt";
		}
		if (MessageType.contains("AIDX")) {
			MessagePath = System.getProperty("user.dir") +"\\TestData\\NeoOps_AAF_TC261\\Actual OOOI update.xml";
		}
		String ESToutTime = "";
		String ESToffTime = "";
		String ESTonTime = "";
		String ESTinTime = "";

		if (MessageType.contains("AIDX")) {
			String ESToutTime_raw = com.extractXMLTagContent(MessagePath, "OperationTime")[2].replaceAll("T", " ")
					.replaceAll(":00Z", "");
			String ESToffTime_raw = com.extractXMLTagContent(MessagePath, "OperationTime")[3].replaceAll("T", " ")
					.replaceAll(":00Z", "");
			String ESTonTime_raw = com.extractXMLTagContent(MessagePath, "OperationTime")[4].replaceAll("T", " ")
					.replaceAll(":00Z", "");
			String ESTinTime_raw = com.extractXMLTagContent(MessagePath, "OperationTime")[7].replaceAll("T", " ")
					.replaceAll(":00Z", "");

			// Changing format of the date present in input message to the format displayed
			// in application
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = sdf.parse(ESToutTime_raw);
			ESToutTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(date);
			date = sdf.parse(ESToffTime_raw);
			ESToffTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(date);
			date = sdf.parse(ESTonTime_raw);
			ESTonTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(date);
			date = sdf.parse(ESTinTime_raw);
			ESTinTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(date);
		}

		String timeStampInMessage = "";
		if (MessageType.contains("OOOI")) {
			timeStampInMessage = Files.readAllLines(Paths.get(MessagePath)).get(1);
		}

		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
		// Select Gantt screen
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		 //Implementing the Screen and Pattern using SikuliScript to select flight and
		// Double click in gantt screen
		IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path1, "doubleClick");

		// verify FlightLegdetails window is opened
//		IFlightNeo_HomePage.view_flightlegdetails(driver);
		/*
		 * String ESTOutTimeBefore = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_outEstTime");
		 * String ESTOffTimeBefore = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_offEstTime");
		 * String ESTOnTimeBefore = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_onEstTime");
		 * String ESTINTimeBefore = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_inEstTime");
		*/
		htmlLib.logReport("Verify the Flight Leg details before the update", "Flight Leg Details Before the Update",
				"INFO", driver, true);

		// Opens Message List screen
		com.performAction(driver, IFlightNeo_HomePage.btn_CloseFlightDetailsWindow(driver), "CLICK", "",
				"Close Flight details window");
		IFlightNeo_MessageList.click_Messagelist(driver);

		// wait for message list screen to load
		IFlightNeo_Messages.btn_ClearInMessageList(driver).isDisplayed();
		// click on Load Message button
		com.performAction(driver, IFlightNeo_Messages.btn_LoadMessage(driver), "CLICK", "",
				"Click on Load Messages");
		IFlightNeo_Messages.dialog_MessageLoad(driver);
		// Upload message file to iNeo Application
		com.performAction(driver, IFlightNeo_Messages.list_MessageType(driver), "Click", "",
				"Click on the messageType dropdown");
		if (MessageType.contains("AIDX")) {
			com.performAction(driver, IFlightNeo_Messages.txtBx_MessageType(driver), "SET",
					"AIDXFlightLegNotification", "Enter message type");
		}
		if (MessageType.contains("OOOI")) {
			com.performAction(driver, IFlightNeo_Messages.txtBx_MessageType(driver), "SET", "DEP",
					"Enter message type");
		}
		com.performAction(driver, driver.findElement(By.xpath("//div[@class='select2-result-label']")), "Click", "",
				"Select value from dropdown");
		com.performAction(driver, driver.findElement(By.xpath("//div[@text='uploaderDesc']")), "CLICK", "",
				"click on the upload file button");
		com.performAction(driver, IFlightNeo_Messages.btn_AddFiles(driver), "CLICK", "",
				"Click on Add files Butoon");
		
		bizComm.uploadFileRobotClass(MessagePath);

		Thread.sleep(3000);
		com.performAction(driver, IFlightNeo_Messages.btn_Upload(driver), "Click", "",
				"Click On Upload message button");
		IFlightNeo_Messages.msg_ProcessedMessage(driver).isDisplayed();

		com.performAction(driver, IFlightNeo_Messages.btn_CloseMessgeUploadPopUp(driver), "CLICK", "",
				"Close the Message upload popUp");
		//com.performAction(driver, IFlightNeo_Gantt.tab_LocalGantt(driver), "CLICK", "", "Go back to Local world screen");
		// Close Default LW
				BusinessFunctions.closeTab(driver, 0, false);
				Thread.sleep(5000);
				IFlightNeo_HomePage.selectGantt(driver);
		// Implementing the Screen and Pattern using SikuliScript to select flight and
		// Double click in gantt screen
		Thread.sleep(5000);
		IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path2, "hover");
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		//IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path1, "SINGLECLICK");
		//IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path1, "doubleClick");
		//IFlightNeo_Gantt.findFlightInGantt(driver, "" + flighNo, Date, departureAirport, arrivalAirport);
		IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path1, "doubleClick");
		//IFlightNeo_Gantt.selectFlightInGantt(driver, Image_Path, "doubleClick");
		// verify FlightLegdetails window is opened
//		IFlightNeo_HomePage.view_flightlegdetails(driver);
		// If message type is AIDX
		if (MessageType.contains("AIDX")) {
			String ESTOutTimeAfter = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_outEstTime");
			String ESTOffTimeAfter = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_offEstTime");
			String ESTOnTimeAfter = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_onEstTime");
			String ESTINTimeAfter = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_inEstTime");
			htmlLib.logReport("Verify the Flight Leg details after the update", "Flight Leg Details after the Update",
					"INFO", driver, true);

			if (ESTOutTimeAfter.contains(ESToutTime)) {
				htmlLib.logReport("Verify the Estimated Out time update",
						"Estimated Out time stamp is correctly updated in application and value is:" + ESTOutTimeAfter
								+ "Estimated out time stamp sent from message" + ESToutTime + "",
						"PASS", driver, true);
			} else {
				htmlLib.logReport("Verify the Estimated Out time update",
						"Estimated Out time stamp is not correctly updated in application and value is:"
								+ ESTOutTimeAfter + "Estimated out time stamp sent from message" + ESToutTime + "",
						"FAIL", driver, true);
			}
			if (ESTOffTimeAfter.contains(ESToffTime)) {
				htmlLib.logReport("Verify the Estimated OFF time update",
						"Estimated OFF time stamp is correctly updated in application and value is:" + ESTOffTimeAfter
								+ "Estimated OFF time stamp sent from message" + ESToffTime + "",
						"PASS", driver, true);
			} else {
				htmlLib.logReport("Verify the Estimated OFF time update",
						"Estimated OFF time stamp is not correctly updated in application and value is:"
								+ ESTOffTimeAfter + "Estimated OFF time stamp sent from message" + ESToffTime + "",
						"FAIL", driver, true);
			}
			if (ESTOnTimeAfter.contains(ESTonTime)) {
				htmlLib.logReport("Verify the Estimated ON time update",
						"Estimated ON time stamp is correctly updated in application and value is:" + ESTOnTimeAfter
								+ "Estimated ON time stamp sent from message" + ESTonTime + "",
						"PASS", driver, true);
			} else {
				htmlLib.logReport("Verify the Estimated ON time update",
						"Estimated ON time stamp is not correctly updated in application and value is:" + ESTOnTimeAfter
								+ "Estimated ON time stamp sent from message" + ESTonTime + "",
						"FAIL", driver, true);
			}
			if (ESTINTimeAfter.contains(ESTinTime)) {
				htmlLib.logReport("Verify the Estimated IN time update",
						"Estimated IN time stamp is correctly updated in application and value is:" + ESTINTimeAfter
								+ "Estimated IN time stamp sent from message" + ESTinTime + "",
						"PASS", driver, true);
			} else {
				htmlLib.logReport("Verify the Estimated IN time update",
						"Estimated IN time stamp is not correctly updated in application and value is:" + ESTINTimeAfter
								+ "Estimated IN time stamp sent from message" + ESTinTime + "",
						"FAIL", driver, true);
			}
		}
		// if Message type is OOOI
		if (MessageType.contains("OOOI")) {
			String ACTOutTime_raw = bizComm.readDisabledValuesInFlightLegDetails(driver, "flightLegDetailsWidgetModel_outActTime");
			String ACTOutTime_After = ACTOutTime_raw.replaceAll("-", "").replaceAll(":", "");
			String[] ACTOutTime_After1 = ACTOutTime_After.split("\\s", 2);
			System.out.println(ACTOutTime_After1[1]);
			if (timeStampInMessage.contains(ACTOutTime_After1[1])) {
				htmlLib.logReport("Verify the DEP time is updated as per the input message",
						"Dep time stamp is updated in iNeo Application as per the Input Message", "PASS", driver, true);
			} else {
				htmlLib.logReport("Verify the DEP time is updated as per the input message",
						"Dep time stamp is not updated in iNeo Application as per the Input Message", "FAIL", driver, true);
			}
		}
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			
		}

	}

	@AfterMethod
	public void closeTest() {

		Driver.tearDownTestExecution(driver);
	}

}
