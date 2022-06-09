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

import pageObjects.IFlightNeo_AddFlight;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_MessageList;
import utilities.CollectTestData;
import utilities.Driver;

/**
 * the flight in the test data should not yet exist on the day of testing.
 * 
 * this test case can create the test data for the execution of the other Interface 
 * test scripts. It is not required to run any other test case before the execution
 * of this test script.
 * 
 * @author EYHGoiss
 *
 */
public class NeoOps_INTG_TC004 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "create a new flight, ASM NEW should be generated");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test (priority=1)
	public void login() throws Exception {
		try {
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String flightNumber = CollectTestData.flightNumber;
			String registrationNumber = CollectTestData.Regno;

			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);

			// call this function to open the "add flights" screen, create a flight and close the screen again
			IFlightNeo_AddFlight.addFlight(driver,flightNumber,registrationNumber);
			Thread.sleep(3000);
			
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
