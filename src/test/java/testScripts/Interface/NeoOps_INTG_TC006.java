package testScripts.Interface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_CancelledActivitiesList;
import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * this test case checks for ASM NEW message after a cancelled flight (by test case NeoOps_INTG_TC005) has been reinstated.
 * 
 * the test cases should be run in the following order: 
 * 1) NeoOps_INTG_TC004 (REQUIRED, to create the test data)
 * 2) NeoOps_INTG_TC001 (optional)
 * 3) NeoOps_INTG_TC002 (optional)
 * 4) NeoOps_INTG_TC003 (optional)
 * 5) NeoOps_INTG_TC005 (REQUIRED to cancel the flight)
 * 6) NeoOps_INTG_TC006
 * 
 * @author EYHGoiss
 */
public class NeoOps_INTG_TC006 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "reinstate a cancelled flight to check if ASM NEW is generated");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test (priority=6)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
//		String registrationNumber = "A6-EIT";  //CollectTestData.Regno;  - the CollectTestData.Regno always returns NULL

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// open the "Cancelled Activities List" screen
			IFlightNeo_HomePage.selectCancelledActivities(driver);
			Thread.sleep(2000);			
			// the timeout is required, if not the menu doesn't disappear
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

			// click on the first checkbox in the table
			IFlightNeo_CancelledActivitiesList.reinstateFlight(driver);
			Thread.sleep(2000);			
			
			// open the "Message List" window
			IFlightNeo_HomePage.selectMessageList(driver);
			Thread.sleep(2000);			

			// set the filter to search for messages today.
			String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
			
//			ArrayList<String> messageTypes = new ArrayList<String>();
//			messageTypes.add("ASM");
//			ArrayList<String> messageSubTypes = new ArrayList<String>();
//			messageSubTypes.add("NEW");
			ArrayList<String> messageDirections = new ArrayList<String>();
			messageDirections.add("OUT");

			IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, date, date, messageDirections, null, null);
//			IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, date, date, messageDirections, messageTypes, messageSubTypes);
			Thread.sleep(2000);			
            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);

            List<WebElement> messageTypeWebElements = IFlightNeo_MessageList.grid_AllMessageTypes(driver);
            List<WebElement> subMessageTypesWebElements = IFlightNeo_MessageList.grid_AllMessageSubTypes(driver);
            
			ArrayList<String> requiredMessageTypes = new ArrayList<String>();
			ArrayList<String> requiredMessageSubTypes = new ArrayList<String>();
			requiredMessageTypes.add("ASM");
			requiredMessageSubTypes.add("NEW");
			requiredMessageTypes.add("AIDXFlightLegNotification");
			requiredMessageSubTypes.add(null);
			requiredMessageTypes.add("FLCM");
			requiredMessageSubTypes.add(null);

			boolean[] requiredMessageTypesFound = new boolean[requiredMessageTypes.size()];
			for(int i=0;i<requiredMessageTypesFound.length;i++)
				requiredMessageTypesFound[i] = false;
			
			ListIterator<String> requiredMessageTypeIterator = requiredMessageTypes.listIterator();
			ListIterator<String> requiredMessageSubTypeIterator = requiredMessageSubTypes.listIterator();
			

			for(int i=0;i<requiredMessageTypesFound.length;i++) {
				String requiredMessageType = requiredMessageTypeIterator.next();
				String requiredMessageSubType = requiredMessageSubTypeIterator.next();
				
				ListIterator<WebElement> messageTypeIterator = messageTypeWebElements.listIterator();
				ListIterator<WebElement> messageSubTypeIterator = subMessageTypesWebElements.listIterator();
				
				while(messageTypeIterator.hasNext() == true) {
					WebElement messageTypeWebElement = messageTypeIterator.next();
					WebElement messageSubTypeWebElement = messageSubTypeIterator.next();
					
					String messageType = messageTypeWebElement.getAttribute("textContent");
					if(messageType.compareTo(requiredMessageType) == 0) {
						
						if(requiredMessageSubType == null)
							requiredMessageTypesFound[i] = true;
						else {
							String messageSubType = messageSubTypeWebElement.getAttribute("textContent");
							if(messageSubType.compareTo(requiredMessageSubType) == 0) 
								requiredMessageTypesFound[i] = true;
						}
					}
				}
			}
            
			boolean allRequiredTypesFound = true;
			for(int i=0;i<requiredMessageTypesFound.length;i++) {
				if(requiredMessageTypesFound[i] == false)
					allRequiredTypesFound = false;
			}

			if (allRequiredTypesFound == true) {
				htmlLib.logReport("Verify Message Status", "all required messages have been found", "Pass", driver, true);
			} else {
				htmlLib.logReport("Verify Message Status", "all required messages have NOT been found", "Fail", driver, true);
			}
		} catch (Exception e) {
			htmlLib.logReport("The script failed - check the Exceptions", "The script failed - check the Exceptions", "Fail", driver, true);
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}
