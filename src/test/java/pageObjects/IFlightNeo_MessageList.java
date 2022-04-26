package pageObjects;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;

import utilities.CollectTestData;
import utilities.CommonLibrary;

public class IFlightNeo_MessageList {

	public static CommonLibrary com = new CommonLibrary();
	public static WebElement element = null;
	public static WebDriverWait wait;
	public static String xmlvalue;
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String flightNumber = CollectTestData.flightNumber;
	static boolean isvaluetocompreavailable,messagecontailsdelaycode;

	/*********************************************************************************************
	 * Method Name : click_messagelist Parameter Used : Webdriver Author : Moumita
	 * Sengupta Creation Date : 23/11/2020 Description : click on Message List
	 * Screen
	 ********************************************************************************************/

	public static void click_Messagelist(WebDriver driver) {

		menu_Message(driver).click();
		List<WebElement> nav_dropdown = driver.findElements(By.xpath("//ul[@id='nav']//li//ul//li//a"));
		for (int i = 0; i < nav_dropdown.size(); i++) {
			WebElement element = nav_dropdown.get(i);

			String subHeader = element.getAttribute("innerHTML");

			if (subHeader.contains("Message List")) {
				list_MessageList(driver).click();
			}
		}

	}

	
	
	
	/*********************************************************************************************
	 * Method Name : check the deatil of AIDX Outbound message 
	 * Author : Moumita Sengupta Creation Date : 22/03/2022 Description : Check details of AIDX Outbound
	 * @param flightDate 
	 * @param messageDate 
	 * @param flightnumber 
	 * @param  delaycode
	 ********************************************************************************************/
	
	public boolean checkDetails_AIDXoutbound(WebDriver driver,String delaycode, String flightNumber, String flightDate, String messageDate) {
		
		    try {
		    	
		    	 

		// go to the message list and check for the AIDXOutbound message
					//IFlightNeo_HomePage.selectMessageList(driver);
					Thread.sleep(2000);			
					//String date = new SimpleDateFormat("dd-MMMM-yyyy").format(new Date());
					
					ArrayList<String> messageTypes = new ArrayList<String>();
					messageTypes.add("AIDXFlightLegNotification");
					ArrayList<String> messageDirections = new ArrayList<String>();
					messageDirections.add("OUT");
					ArrayList<String> messageSubTypes = new ArrayList<String>();
					messageSubTypes.add(null);
					IFlightNeo_MessageList.set_Messagelistfilters(driver, flightNumber, flightDate,messageDate, messageDirections, messageTypes, messageSubTypes);
					Thread.sleep(2000);			
		            htmlLib.logReport("Filter applied for message list", "Filter applied for message list", "INFO", driver, true);

		            Thread.sleep(7000);	
		        	
					// open the details of the AIDX Out message & check if correct delay code available
				    if(IFlightNeo_MessageList.aidx_Details(driver,delaycode))
				    {
				    	 messagecontailsdelaycode = true;
				    	
				    }
				    
				    else 
				    {
				    	messagecontailsdelaycode = false;
				    }
					
					Thread.sleep(2000);		
					
		            
		            
				} 
	       catch (Exception e) {
		// TODO Auto-generated method stub
		
					htmlLib.logReport("The script failed - check the Exceptions", "The script failed - check the Exceptions", "Fail", driver, true);
					System.out.println("The exception occured for this TC is" + e);
					e.printStackTrace();
				}
			return messagecontailsdelaycode;
			

	}

	
	/*********************************************************************************************
	 * Method Name : check whether the AIDX Outbound message has correct delay code 
	 * Author : Moumita Sengupta Creation Date : 18/03/2022 
	 * Description : Check details of AIDX Outbound
	 * @param flightDate 
	 * @param messageDate 
	 * @param flightnumber 
	 * @param  delaycode
	 ********************************************************************************************/
	
	public static boolean aidx_Details(WebDriver driver, String valuetocompre) {
		// TODO Auto-generated method stub
		
		Actions a2 = new Actions(driver) ;
		a2.moveToElement(driver.findElements(By.xpath("//td[contains(@aria-describedby,'GridActions')]")).get(20));
		
		driver.findElements(By.xpath("//td[contains(@aria-describedby,'GridActions')]")).get(20).click();
		
		// click on the button
		com.performAction(driver,IFlightNeo_MessageList.btn_dropdown(driver, 2), "CLICK", "click on DROPDOWN button", "click on DROPDOWN button");
		
		try {
			Thread.sleep(2000);
		}
		catch (Exception e) {
			
		}
		
		
		// click on the view details button
		com.performAction(driver,IFlightNeo_MessageList.btn_ViewDetails(driver,2), "CLICK", "click on View details button", "click on View Details button");
		try {
			Thread.sleep(10000);
		}
		catch (Exception e) {
			
		}
		String message = IFlightNeo_MessageList.txtbx_message(driver).getAttribute("value");
		System.out.println(message);
		
		// verify the message contains the correct delay reason code
		if(message.contains(valuetocompre)==true) {
			//htmlLib.logReport("message contains the correct delay reason code", "message contains the correct delay reason code", "Pass", driver, true);
			isvaluetocompreavailable=true;
		}
		else {
			/*htmlLib.logReport("message DOES NOT contain the correct delay reason code", "message DOES NOT contain the correct delay reason code", "Fail", driver, true);
			System.out.println("message: " + message);
			System.out.println("expected delay codes would have been:"+delaycode);*/
			isvaluetocompreavailable=false;
		}
		return isvaluetocompreavailable;
		
	}




	/*********************************************************************************************
	 * Method Name : setmessagelistfilters Parameter Used : Webdriver Author :
	 * Moumita Sengupta Creation Date : 23/11/2020 Description : set up message list
	 * filters
	 * @return 
	 ********************************************************************************************/

	/*public static String set_Messagelistfilters(WebDriver driver, String flightNo, String flighDate) throws InterruptedException, AWTException, FindFailed

	{

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='clear()']")));
		driver.findElement(By.xpath("//button[@ng-click='clear()']")).click();
		filter_MessageList(driver).sendKeys("AIDXFlightLegNotification" + Keys.ENTER);
				Thread.sleep(500);
		WebElement close = driver.findElements(By.xpath("//a[@class='select2-search-choice-close']")).get(2);

		close.click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='PROCESSING_FAILED']")));
		driver.findElements(By.xpath("//a[@class='select2-search-choice-close']")).get(2).click();
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).clear();
	
		
		
		
		 * for (int j = 0; j < 17; j++) {
		 * driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).
		 * sendKeys(Keys.BACK_SPACE);
		 * 
		 * }
		 
		

		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).sendKeys(flighDate +" 00:00" + Keys.TAB);

		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).clear();
		/*
		 * for (int j = 0; j < 17; j++) {
		 * driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).
		 * sendKeys(Keys.BACK_SPACE);
		 * 
		 * }
		 
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).sendKeys(flighDate +" 23:59" + Keys.TAB);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_022']//button")).click();
		
		//System.out.println("xmlvalue:" +xmlvalue);
		//System.out.println(driver.findElement(By.xpath("//textarea[@ng-model='messageDetailsWidgetModel.messageContent']")).getAttribute("value"));

		if(!flightNo.equals(""))
		{
		driver.findElement(By.xpath("//img[@class='margin_l_sm']")).click();
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_010']//a")).click();
		driver.findElement(By.xpath("//div[text()='FLIGHT']")).click();
		WebElement Carrierfield = driver
				.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//a[@class='select2-choice']"));
		Carrierfield.click();
		driver.findElement(By.xpath("//div[text()='EY']")).click();
		driver.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//input[@ng-model='fltNo']")).click();
		driver.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//input[@ng-model='fltNo']")).sendKeys(flightNo);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_022']//button")).click();
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[contains(@id,'MessageGrid')]//td//input[@role='checkbox']")));
		
		driver.findElement(By.xpath("//table[contains(@id,'MessageGrid')]//td//input[@role='checkbox']")).click();
		driver.findElement(By.xpath("//table[contains(@id,'MessageGrid')]//button[contains(@id,'InlineAction_0_ActListBtn')]")).click();
	    driver.findElement(By.xpath("//button[contains(@id,'ActListBtn')][contains(@data-target,'#MessageGrid')]")).click();
		driver.findElement(By.xpath("//li[text()='View Details']")).click();
		Thread.sleep(3000);
		xmlvalue=driver.findElement(By.xpath("//textarea[@ng-model='messageDetailsWidgetModel.messageContent']")).getAttribute("value").toString();
		
		}
		driver.findElement(By.xpath("//li[text()='Download']")).click();
		Thread.sleep(200);
		Screen scn = new Screen();
		Pattern alert = new Pattern(
				System.getProperty("user.dir") + "\\TestData\\keep.PNG");
		scn.setActive("Gannt");
		scn.click(alert);
		return xmlvalue;	
	}*/

	
	public static void set_Messagelistfilters_alloutbound(WebDriver driver, String flightNo, String flightDate,String messagedate) throws InterruptedException, AWTException, FindFailed
	{

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='clear()']")));
		driver.findElement(By.xpath("//button[@ng-click='clear()']")).click();
		Thread.sleep(200);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_001']//ul[@class='select2-choices']//li//a[@class='select2-search-choice-close']")).click();
		Thread.sleep(200);
		
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_001']//ul[@class='select2-choices']")).sendKeys("OUT"+Keys.ENTER);
		filter_MessageList(driver).sendKeys("AIDXFlightLegNotification" + Keys.ENTER);
		filter_MessageList(driver).sendKeys("ASM" + Keys.ENTER);
		filter_MessageList(driver).sendKeys("MVT" + Keys.ENTER);
		filter_MessageList(driver).sendKeys("FLCM" + Keys.ENTER);
		
		Thread.sleep(500);
		WebElement close = driver.findElement(By.xpath("//li[contains(@oh-compid,'_004')]//a[@class='select2-search-choice-close']"));

		close.click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='PROCESSING_FAILED']")));
		//driver.findElements(By.xpath("//a[@class='select2-search-choice-close']")).get(2).click();
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_004']//a[@class='select2-search-choice-close']")).click();
		//driver.findElement(By.xpath("//li[contains(@oh-compid,'_004')]//div//ul[@class='select2-choices']")).sendKeys(Keys.BACK_SPACE);
		//driver.findElement(By.xpath("//li[contains(@oh-compid,'_004')]//div//ul[@class='select2-choices']")).sendKeys(Keys.BACK_SPACE);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).clear();
		/*
		 * for (int j = 0; j < 17; j++) {
		 * driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).
		 * sendKeys(Keys.BACK_SPACE);
		 * 
		 * }
		 */

		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input")).sendKeys(messagedate +" 00:00" + Keys.TAB);

		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).clear();
		/*
		 * for (int j = 0; j < 17; j++) {
		 * driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).
		 * sendKeys(Keys.BACK_SPACE);
		 * 
		 * }
		 */
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input")).sendKeys(messagedate +" 23:59" + Keys.TAB);
		//Expand advance Search
		driver.findElement(By.xpath("//img[@ng-click='advancedSearchShowAndHide()'][@class='margin_l_sm']")).click();
		driver.findElement(By.xpath("//li[@oh-compid='FMSG003_010']//a")).click();
		driver.findElement(By.xpath("//div[text()='FLIGHT']")).click();
		WebElement Carrierfield = driver
				.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//a[@class='select2-choice']"));
		Carrierfield.click();
		driver.findElement(By.xpath("//div[text()='EY']")).click();
		driver.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//input[@ng-model='fltNo']")).click();
		driver.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//input[@ng-model='fltNo']")).sendKeys(flightNo);
		
		driver.findElement(By.xpath("//input[contains(@ng-model,'flightDate')]")).sendKeys(flightDate);

	    driver.findElement(By.xpath("//li[@oh-compid='FMSG003_022']//button")).click();
		Thread.sleep(200);
//		List<WebElement> table_rows = driver.findElements(By.xpath("//table[contains(@id,'MessageGrid')]//tr[@role='row']"));
		List<WebElement> message_type = driver.findElements(By.xpath("//table[contains(@id,'MessageGrid')]//tr[@role='row']//td[contains(@aria-describedby,'messageType')]"));
		List<WebElement> message_status = driver.findElements(By.xpath("//table[contains(@id,'MessageGrid')]//tr[@role='row']//td[contains(@aria-describedby,'messageStatus')]"));
		List<WebElement> source_destination=driver.findElements(By.xpath("//table[contains(@id,'MessageGrid')]//tr[@role='row']//td[contains(@aria-describedby,'source')]"));
		Thread.sleep(500);
		for (int i = 0; i < 5; i++) {
			//message_type.get(i);
			String messageType = message_type.get(i).getAttribute("innerHTML");
			//System.out.println(messageType);
			String messagestatus = message_status.get(i).getAttribute("innerHTML");
			String sourcedestination=source_destination.get(i).getAttribute("innerHTML");
			//System.out.println(messagestatus);
			//changed the below if condition on 7th feb,22 so that the sending failed outbound messages to 'Test' destination not considered as failed step instead considered as INFO
            htmlLib.logReport("Message type & status check", "Message type is "+ messageType+ " & status is "+messagestatus+" for number "+i+ " outbound message", "INFO", driver, true);
			if(messagestatus.equals("SENDING_FAILED")&! sourcedestination.equals("TEST")) {
				htmlLib.logReport("Message status failure check", "Message status is "+ messagestatus+ " for number "+i+ " outbound message", "Fail", driver, false);
			}
		}
		
		//Hide advance Search  (added on 23rd March,22 by Moumita so that expanded advance search does not create issue in next execution
				driver.findElement(By.xpath("//img[@ng-click='advancedSearchShowAndHide()'][@class='margin_l_sm']")).click();
		
	}

	
	
	
	
	
	
	/*********************************************************************************************
	 * Method Name : setmessagelistfilters 
	 * Parameter Used : 
	 *    - messageType: Message Types used in the filter. they must be named as in the UI. if not used, send null 
	 *    - messageSubType: Message Sub Types used in the filter. they must be named as in the UI. if not used, send null
	 * Author : Hannu-Daniel Goiss 
	 * Creation Date : 06/09/2021 
	 * Description : dynamic function to set up message filters based on Moumita's function set_Messagelistfilters
	 * @return 
	 ********************************************************************************************/
	public static void set_Messagelistfilters(WebDriver driver, String flightNo, String flightDate, String messagedate, ArrayList<String> messageDirection, ArrayList<String> messageType, ArrayList<String> messageSubType) throws InterruptedException, AWTException, FindFailed
	{
		// clicking on clear button
		IFlightNeo_MessageList.btn_Clear(driver).click();
		
		// remove the "In" from the Direction
		IFlightNeo_MessageList.btn_DirectionIn(driver).click();
		// the last click doesn't work without this Thread.sleep. keep it
		Thread.sleep(200);
		
		// add directions from the ArrayList
		if (messageDirection != null) {
			messageDirection.forEach((n) -> IFlightNeo_MessageList.txtBx_Direction(driver).sendKeys(n + Keys.ENTER));
		}
		
		// add message types from the ArrayList
		if (messageType != null) {
			messageType.forEach((n) -> IFlightNeo_MessageList.filter_MessageList(driver).sendKeys(n + Keys.ENTER + Keys.ESCAPE));
		}

		// add message sub types from the ArrayList
		if (messageSubType != null) {
			messageSubType.forEach((n) -> IFlightNeo_MessageList.filter_MessageSubType(driver).sendKeys(n + Keys.ENTER + Keys.ESCAPE));
		}
		
		// remove Decoding Fail from Message Status
		IFlightNeo_MessageList.btn_MessageStatusDecodingFail(driver).click();
		
		// remove Processing Failed from Message Status
		IFlightNeo_MessageList.btn_MessageStatusProcessingFailed(driver).click();
		
		// clear the From Date textbox
		IFlightNeo_MessageList.txtBx_FromDate(driver).sendKeys(Keys.ENTER);
		IFlightNeo_MessageList.txtBx_FromDate(driver).clear();

		// fill the From Date textbox
		IFlightNeo_MessageList.txtBx_FromDate(driver).sendKeys(messagedate +" 00:00" + Keys.TAB);

		// clear the To Date textbox
		IFlightNeo_MessageList.txtBx_ToDate(driver).sendKeys(Keys.ENTER);
		IFlightNeo_MessageList.txtBx_ToDate(driver).clear();

		// fill the To Date textbox
		IFlightNeo_MessageList.txtBx_ToDate(driver).sendKeys(messagedate +" 23:59" + Keys.TAB);
		
		// expand the "Advanced Search"
	
		IFlightNeo_MessageList.dropdown_AdvancedSearch(driver).click();
		
		// click on the "Entity Type" dropdown
		IFlightNeo_MessageList.dropdown_EntityType(driver).click();

		// select FLIGHT from the dropdown "Entity Field"
		IFlightNeo_MessageList.dropdown_EntityTypeFlightElement(driver).click();
		
		// select the "Flight Id" dropdown
		IFlightNeo_MessageList.dropdown_FlightId(driver).click();

		// select the EY element from the "Flight Id" dropdown
		IFlightNeo_MessageList.dropdown_FlightIdEYElement(driver).click();
		
		// fill the flight number
		IFlightNeo_MessageList.txtBx_FlightNo(driver).click();
		IFlightNeo_MessageList.txtBx_FlightNo(driver).sendKeys(flightNo);
		
		// fill the flight date
		IFlightNeo_MessageList.txtBx_FlightDate(driver).sendKeys(flightDate);
		
		// click the search button
	    IFlightNeo_MessageList.btn_Search(driver).click();
	    
		Thread.sleep(5000);

		// verify if a message has status "SENDING_FAILED". if yes, then FAIL the test script
		// Modified the fail validation point to Info
		List<WebElement> message_status = driver.findElements(By.xpath("//table[contains(@id,'MessageGrid')]//tr[@role='row']//td[contains(@aria-describedby,'messageStatus')]"));
		Thread.sleep(500);
		for (WebElement gridRow : message_status) {
			String messagestatus = gridRow.getAttribute("innerHTML");
			if(messagestatus.equals("SENDING_FAILED")) {
				htmlLib.logReport("Message status failure check", "Message status is "+ messagestatus, "INFO", driver, true);
			}
		}
	}

	/*
	 * returns the search button 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement btn_Search(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@oh-compid='FMSG003_022']//button")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_022']//button"));
		return element;
	}		

	/*
	 * returns textbox to enter the Flight Date in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement txtBx_FlightDate(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[contains(@ng-model,'flightDate')]")));
		element = driver.findElement(By.xpath("//input[contains(@ng-model,'flightDate')]"));
		return element;
	}		

	/*
	 * returns textbox to enter the Flight Number in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement txtBx_FlightNo(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'flightIdFields')]//input[@ng-model='fltNo']")));
		element = driver.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//input[@ng-model='fltNo']"));
		return element;
	}		

	/*
	 * returns the EY Element from the Dropdown of the Carrier Codes in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement dropdown_FlightIdEYElement(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[text()='EY'])[last()]")));
		element = driver.findElement(By.xpath("(//div[text()='EY'])[last()]"));
		return element;
	}		
			
	/*
	 * returns the Dropdown of the Carrier Codes in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement dropdown_FlightId(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'flightIdFields')]//a[@class='select2-choice']")));
		element = driver.findElement(By.xpath("//div[contains(@id,'flightIdFields')]//a[@class='select2-choice']"));
		return element;
	}		

	/*
	 * returns the FLIGHT Element from the Dropdown of the Entity Types in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement dropdown_EntityTypeFlightElement(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='FLIGHT']")));
		element = driver.findElement(By.xpath("//div[text()='FLIGHT']"));
		return element;
	}		
		
	/*
	 * returns the Dropdown of the Entity Types in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement dropdown_EntityType(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@oh-compid='FMSG003_010']//a")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_010']//a"));
		return element;
	}		
	
	/*
	 * returns the Dropdown to open the Advanced Search options in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement dropdown_AdvancedSearch(WebDriver driver) {
		wait = new WebDriverWait(driver, 1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@ng-click='advancedSearchShowAndHide()'][@class='margin_l_sm']")));
		element = driver.findElement(By.xpath("//img[@ng-click='advancedSearchShowAndHide()'][@class='margin_l_sm']"));
		return element;
	}	
	
	/*
	 * returns the textbox of the "To Date" in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement txtBx_ToDate(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@oh-compid='FMSG003_006']//input")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_006']//input"));
		return element;
	}	

	/*
	 * returns the textbox of the "From Date" in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement txtBx_FromDate(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@oh-compid='FMSG003_005']//input")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_005']//input"));
		return element;
	}	
		
	/*
	 * returns the item "Processing Failed" in the Message Status 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement btn_MessageStatusProcessingFailed(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='PROCESSING_FAILED']")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_004']//a[@class='select2-search-choice-close']"));
		return element;
	}	

	/*
	 * returns the item "Decoding Failed" in the Message Status 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement btn_MessageStatusDecodingFail(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@oh-compid,'_004')]//a[@class='select2-search-choice-close']")));
		element = driver.findElement(By.xpath("//li[contains(@oh-compid,'_004')]//a[@class='select2-search-choice-close']"));
		return element;
	}	
	
	
	/*
	 * returns the textbox of the "Direction" in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement txtBx_Direction(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@oh-compid='FMSG003_001']//ul[@class='select2-choices']")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_001']//ul[@class='select2-choices']"));
		return element;
	}	
	
	/*
	 * returns the "Clear" button in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement btn_Clear(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='clear()']")));
		element = driver.findElement(By.xpath("//button[@ng-click='clear()']"));
		return element;
	}	
	
	/*
	 * returns the "In" element of the Direction in the Search Criteria 
	 * 
	 * @author Hannu-Daniel Goiss
	 */
	private static WebElement btn_DirectionIn(WebDriver driver) {
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@oh-compid='FMSG003_001']//ul[@class='select2-choices']//li//a[@class='select2-search-choice-close']")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_001']//ul[@class='select2-choices']//li//a[@class='select2-search-choice-close']"));
		return element;
	}	

	private static WebElement menu_Message(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@id='menu_messages']")));
		element = driver.findElement(By.xpath("//a[@id='menu_messages']"));
		return element;
	}

	private static WebElement list_MessageList(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_messageList")));
		element = driver.findElement(By.id("menu_messageList"));
		return element;
	}

	private static WebElement filter_MessageList(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//li[@oh-compid='FMSG003_001']//ul[@class='select2-choices']")));
		element = driver.findElement(By.xpath("//li[@oh-compid='FMSG003_002']//ul[@class='select2-choices']"));
		return element;
	}

	/**
	 * added, similar as above function filter_MessageList from Moumita
	 * 
	 * @param driver
	 * @return
	 */
	private static WebElement filter_MessageSubType(WebDriver driver)

	{

		wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='s2id_messageSubTypeField']//ul[@class='select2-choices']")));

		element = driver.findElement(By.xpath("//div[@id='s2id_messageSubTypeField']//ul[@class='select2-choices']"));

		return element;

	}
	
	
	/**
	 * scroll and view details in the search result grid
	 * 
	 * @param driver
	 */
	public static void scrollAndView(WebDriver driver, int tabnumber) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element = driver.findElement(By.xpath("(//td[contains(@aria-describedby,'GridActions')])["+tabnumber+"]"));
		js.executeScript("arguments[0].scrollIntoView();", Element);
		Element.click();
	
		// click on the button
		com.performAction(driver,btn_dropdown(driver, tabnumber), "CLICK", "click on DROPDOWN button", "click on DROPDOWN button");
		
		try {
			Thread.sleep(2000);
		}
		catch (Exception e) {
			
		}
		
		// click on the EDIT button
		com.performAction(driver,btn_ViewDetails(driver, tabnumber), "CLICK", "click on EDIT button", "click on EDIT button");
		
	}
	
	/**
	 * get the "button" to open the dropdown for an element in the search result grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_dropdown(WebDriver driver, int tabnumber) {
//		String xpath = "//button[@id='MessageGrid_W2_InlineAction_0_ActListBtn']";
		String xpath = "(//button[contains(@id,'InlineAction_0_ActListBtn')])["+tabnumber+"]";

		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the View Details button to view a flight.
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_ViewDetails(WebDriver driver, int tabnumber) {
//		String xpath = "//ul[@id='MessageGrid_W2_InlineAction_0']/li[1]";
		String xpath = "(//ul[contains(@id,'InlineAction_0')])["+tabnumber+"]/li[1]"; 
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	
	/**
	 * click on the "field" tab in the message details.
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement tab_field(WebDriver driver) {
		String xpath = "//a[text()='Field']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the text box for the message .
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtbx_message(WebDriver driver) {
		String xpath = "//textarea[@name='messageDetailsWidgetModel_messageContent']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the text box for the Aircraft Registration .
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtbx_aircraftRegistration(WebDriver driver) {
		String xpath = "//label[text()='AircraftRegistration']/../input";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
		
	/**
	 * open the field tab in the message details pop up
	 */
	public static void click_field(WebDriver driver) {
		com.performAction(driver,tab_field(driver), "CLICK", "field tab", "field tab");		
	}

	/**
	 * upload an ATC file
	 */
	public static void uploadATCfile(WebDriver driver, String filename) throws Exception {
		// click on Load Message button
		com.performAction(driver,btn_LoadMessage(driver), "CLICK", "Load Message Button", "Load Message Button");		

		// click on Upload | Drop Files button in the Message Load popup
		com.performAction(driver,btn_DropFiles(driver), "CLICK", "Upload Button", "Upload Button");		
		
		// click on "Add files" button
		com.performAction(driver,btn_AddFiles(driver), "CLICK", "Add Files Button", "Add Files Button");		
		
		// select the file to upload
		com.uploadFilePopup(filename);
		
        // select ATCSlot from Message Type dropdown - 1. click on the drop down
		com.performAction(driver,list_MessageType(driver), "CLICK", "Message Type dropdown", "Message Type dropdown");		
        
        // select ATCSlot from Message Type dropdown - 2. select ATCSlot
		com.performAction(driver,list_MessageTypeATCSlotElement(driver), "CLICK", "select ATCSlot", "select ATCSlot");		
		
		// click the Upload Button
		com.performAction(driver,btn_Upload(driver), "CLICK", "click Upload button", "click Upload button");		
	}

	/**
	 * return the Upload button inside of the Message Load popup
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement btn_Upload(WebDriver driver) {
		String xpath = "//button[contains(text(),'Upload')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * return the ATCSlot element inside of the "Message Type" dropdown inside of the Message Load popup
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement list_MessageTypeATCSlotElement(WebDriver driver) {
		String xpath = "//div[contains(text(),'AtcSlot')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * return the "Message Type" dropdown inside of the Message Load popup
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement list_MessageType(WebDriver driver) {
		String xpath = "//div[@sp-dialog='messageLoadDialogOptions']//label[contains(text(),'Message Type')]/../div";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	
	
	/**
	 * return the "Add Files" button inside of the Message Load popup
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement btn_AddFiles(WebDriver driver) {
		String xpath = "//button[contains(text(),'Add files')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * return the "Upload | Drop Files" button inside of the Message Load popup
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement btn_DropFiles(WebDriver driver) {
		String xpath = "//span[contains(text(),'Drop Files')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	
	
	/**
	 * return the "Message Type" from the first row of the grid
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement grid_FirstMessageType(WebDriver driver) {
		String xpath = "(//table[contains(@id,'MessageGrid')]//tr[@role='row']//td[contains(@aria-describedby,'messageType')])[1]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * return the "Message Sub Type" from the first row of the grid
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement grid_FirstMessageSubType(WebDriver driver) {
		String xpath = "(//table[contains(@id,'MessageGrid')]//tr[@role='row']//td[contains(@aria-describedby,'messageSubType')])[1]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * return the "Load Message" button
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement btn_LoadMessage(WebDriver driver) {
		String xpath = "//button[contains(text(),'Load Message')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * return the webelement from the conext menu, which contains "Unpin Tab"
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement list_unpin(WebDriver driver) {
		String xpath = "(//span[contains(text(),'Unpin Tab')])[1]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	/**
	 * return the webelement to click on to close the tab
	 * 
	 * @param driver
	 * @return
	 */  
	public static WebElement tab_pinned(WebDriver driver) {
		String xpath = "(//span[contains(text(),'Pinned Tab')])[1]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}	

	
}
