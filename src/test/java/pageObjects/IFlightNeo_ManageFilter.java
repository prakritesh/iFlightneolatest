package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
//import org.sikuli.hotkey.Keys;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonLibrary;


public class IFlightNeo_ManageFilter {

	public static CommonLibrary comm = new CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	static int rows;
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;
	private static By locator;
	
	/**
	 * return the save button inside a pop up, which appears once you save on the manager filter screen and that pop-up gets displayed
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_SaveIfApplicable(WebDriver driver) {
		String xpath = "//span[contains(text(),'YES')]/..";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * this clicks on the save button, which appears in a pop up, once this pop up gets displayed.
	 * this pop up gets displayed when clicking on save in the manage filter screen.
	 * as this doesn't happen every time, there is a try catch. nothing has to be done, in case the element cannot be found.
	 * 
	 * @param driver
	 */
	public static void btn_YesToOverrideChanges(WebDriver driver) {
		try { 
			comm.performAction(driver, btn_SaveIfApplicable(driver), "CLICK", "yes to overwrite with saving", "yes to overwrite with saving");
		} catch(Exception e) {
			//this element might not exist, as it only pops up if we are overwriting instead of saving. hence catching this exception
		}
	}
	
	/**
	 * close the Manage Filter screen
	 * 
	 * @param driver
	 */
	public static void closeFilter(WebDriver driver) {
		element = driver.findElement(By.xpath("(//span[contains(text(), 'Remove Tab')])[1]"));
		comm.performAction(driver, element, "CLICK", "", "Close filter screen");
	}
	
	/**
	 * this function adds the data, saves and applies for a new filter in the ManagerFilter screen
	 * 
	 * @param driver
	 * @throws InterruptedException 
	 */
	public static void addFilterForAircraftSubtypeInManageFilter(WebDriver driver) throws InterruptedException {
		// type into box for filter name and clal it "Aircraft subtype_32". this filtername will be referred to in other test cases
		comm.performAction(driver,txtBx_FilterName(driver), "SET", "Aircraft subtype_32", "setting filter name as Aircraft subtype_32M");
		Thread.sleep(1000);
		// click on the checkbox to make this a favorite filter
		comm.performAction(driver,chkBx_FavoriteFilter(driver), "CLICK", "favourite", "favourite");
		Thread.sleep(1000);
		// click on the dropdown for Entity Type
		comm.performAction(driver,list_EntityType(driver), "CLICK", "entity type", "entity type");
		Thread.sleep(1000);
		// in the dropdown for entity type, select aircraft
		comm.performAction(driver,list_AircraftElement(driver), "CLICK", "select aircraft", "select aircraft");
		Thread.sleep(1000);
		// click on the add button to add aircraft to the filters
		comm.performAction(driver,btn_Add(driver),"CLICK","add button","add button");
		Thread.sleep(1000);
		// click on the dropdown for aircraft subtype
		comm.performAction(driver,list_Aircraft(driver), "CLICK", "aircraft subtype", "aircraft subtype");
		Thread.sleep(1000);
		// select the element for aircraft subtype from the dropdown aircraft
		comm.performAction(driver,list_AircraftSubtypeElement(driver), "CLICK", "select aircraft subtype", "select aircraft subtype");
		Thread.sleep(1000);
		// click on the dropdown for aircraft subtype
		comm.performAction(driver,list_AircraftSubtype(driver), "CLICK", "select subtype", "select subtype");
		Thread.sleep(1000);
		//click on the button to select an aircraft subtype from the dropdown
		comm.performAction(driver,list_78EElement(driver), "CLICK", "select aircraft subtype", "select aircraft subtype");
		Thread.sleep(1000);
		// click on the save button
		comm.performAction(driver,btn_Save(driver),"CLICK","save button","save button");
		Thread.sleep(1000);
		// in case a filter with that name already exists, there is a pop up. just clicking save inside of there
		clickSaveIfApplicable(driver);
		Thread.sleep(1000);
		// click on the apply button
		comm.performAction(driver,btn_Apply(driver),"CLICK","apply button","apply button");
		Thread.sleep(1000);
	}
   
	/**
	 * this function adds the data, saves and applies for a new filter in the ManagerFilter screen for flight number & date
	 * 
	 * @param driver
	 * @param date 
	 * @param flighNo 
	 * @throws InterruptedException 
	 */
	public static void addFilterForFlightnumberInManageFilter(WebDriver driver, String[] flightNo, String date) throws InterruptedException {
		// type into textbox for filter name and clal it "Filter for swaping". this filtername will be referred to in other test cases
		comm.performAction(driver,txtBx_FilterName(driver), "SET", "Filter for swaping", "setting filter name as Filter for swaping");
		// click on the checkbox to make this a favorite filter
		//comm.performAction(driver,chkBx_FavoriteFilter(driver), "CLICK", "favourite", "favourite");
		//change display
		comm.performAction(driver,dropdown_display(driver),"CLICK","","display dropdown");
		//select only filtered items from display dropdown
		comm.performAction(driver,list_display(driver),"CLICK","","select dropdown option");
      	// click on the dropdown for Entity Type
		comm.performAction(driver,list_EntityType(driver), "CLICK", "entity type", "entity type"); 	
		// in the dropdown for entity type, select flight number
		comm.performAction(driver,list_FlightnoElement(driver), "CLICK", "", "select flight no");
		// click on the add button to add flight no to the filters
		comm.performAction(driver,btn_Add(driver),"CLICK","add button","add button");
	   		// click on the dropdown for flight no
		comm.performAction(driver,list_Flight(driver), "CLICK", "", "flight");
		// click on the dropdown for flight number
		comm.performAction(driver,list_FlightNofilter(driver), "CLICK", "", "Flight No");
		//Click on operational element
		dropdown_OperationalElement(driver);
		Thread.sleep(1000);
		// click on the flight number textbox
		comm.performAction(driver,dropdownbox_FlightNo(driver), "CLICK", "", "Flight number textbox");
		Thread.sleep(1000);
		comm.performAction(driver,filter_FlightNo(driver), "CLICK", "", "Flight number filter");
		//Enter flight number range in filter 
		apply_Flightnumberfilter(driver,flightNo);
		//Final Backslash to close the Flight Number Dropdown box(23rd Mar,22)
		try {
		close_Flightnumberfilter(driver);
		}
		
		catch(Exception e)
		{
			
		}
		//Enter flight date in filter
		comm.performAction(driver,add_Flightdatecondition(driver),"CLICK","","Add flight date condition ");
		// click on the dropdown for flight date
		comm.performAction(driver,list_Flightrepeat(driver), "CLICK", "", "flight list for date");
		// click on the dropdown for flight date
		comm.performAction(driver,list_Flightdate(driver), "CLICK", "", "Flight Date filter");
		//set date textbox
		comm.performAction(driver,txtbox_Flightdate(driver), "SET", date + Keys.TAB , "Flight Date textbox");
		//uncheck highlight items checkbox
	    comm.performAction(driver,checkbox_highlightitem(driver),"CLICK","","Highlight items checkbox");
		// click on the save button
		comm.performAction(driver,btn_Save(driver),"CLICK","save button","save button");
		// in case a filter with that name already exists, there is a pop up. just clicking save inside of there
		clickSaveIfApplicable(driver);
		// click on the apply button
		comm.performAction(driver,btn_Apply(driver),"CLICK","apply button","apply button");
		Thread.sleep(1000);
		
		
		
	}



	private static void close_Flightnumberfilter(WebDriver driver) {
		// TODO Auto-generated method stub
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']"))).sendKeys(Keys.BACK_SPACE).build().perform();

		
	}

	public static void filter_Apply(WebDriver driver) throws InterruptedException {
		IFlightNeo_HomePage.menuItem_ManageFilter(driver);
		driver.findElement(By.xpath("//div[@ng-hide='filterInvocationFromDashBoard']//following-sibling::label[1]")).click();
		driver.findElement(By.xpath("//div[contains(@oh-compid,'FFTR001_003')]//following-sibling::div//a")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']")).sendKeys("Filter for swaping"+Keys.ENTER);
		driver.findElement(By.xpath("//li[contains(@oh-compid,'FFTR001')]//button[text()='Apply']")).click();
		
	}

	private static WebElement list_display(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//div[text()='Only Filtered Items']"));
	}

	private static WebElement dropdown_display(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//label[contains(text(), 'Display')]/../div"));
	}

	private static WebElement txtbox_Flightdate(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//input[@ui-date='dateFromOptions']"));
	}

	private static WebElement list_Flightdate(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//div[contains(text(),'Flight Date')]"));
	}

	private static WebElement list_Flightrepeat(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//div[@ng-if='isExpression(elem)']//a//span[text()='Select an Item']"));
	}

	private static WebElement checkbox_highlightitem(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//input[@name='userFilterWidgetModel_userFilterDTO_highlightFiltered']"));
	}

	private static WebElement add_Flightdatecondition(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//button[@ng-show='elem.showAddButton']"));
	}

	private static void apply_Flightnumberfilter(WebDriver driver, String[] flightNo) throws InterruptedException {
		// TODO Auto-generated method stub
		for(int flightnoDigit=0;flightnoDigit<flightNo.length;flightnoDigit++)
			
		
		{
	
			

		Actions action = new Actions(driver);
		//Xpath for Flightno text box changed after Aug,21 upgrade
        //action.moveToElement(driver.findElement(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']"))).sendKeys(flightNo[flightnoDigit]).build().perform();
		action.moveToElement(driver.findElement(By.xpath("//div[@ng-if='elem.needList']"))).sendKeys(flightNo[flightnoDigit]).build().perform();
		//action.moveToElement(driver.findElement(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']"))).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//div[text()='" + flightNo[flightnoDigit] + "']")).click();
		// this waittime added to wait the script before it checks for visibility of the dropdownlist
		//Thread.sleep(10000);
		
		try
		{
		boolean dropdownopen=driver.findElement(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']")).isDisplayed();
        
			if (dropdownopen==true)
			{
			for(int flightnoDigit1=0;flightnoDigit1<flightNo[flightnoDigit].length();flightnoDigit1++)
    
			action.moveToElement(driver.findElement(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']"))).sendKeys(Keys.BACK_SPACE).build().perform();
			}
			
      
		}
     
		catch(Exception e)
		{
			// click on the flight number textbox
			Thread.sleep(4000);
			if(flightnoDigit!=(flightNo.length-1))
			{
			//comm.performAction(driver,dropdownbox_FlightNo(driver), "CLICK", "", "Flight number textbox");
			/*Changed the below piece of code as due to multiple 4 digit flight number the flight number range field getting the click on remove button of already entered flight number
				changed on 1st feb,22*/
			action.moveToElement(driver.findElement(By.xpath("//ul[@class='select2-choices']"))).moveByOffset(30, 0).click().perform();
				
				/*WebElement element = driver.findElement(By.xpath("//ul[@class='select2-choices']"));
		        JavascriptExecutor js =(JavascriptExecutor)driver;
		        js.executeScript("window.scrollTo(0,"+element.getLocation().moveBy(200, 0)+")");
		        element.click();*/
			comm.performAction(driver,filter_FlightNo(driver), "CLICK", "", "Flight number filter");
			
			}

		}
		
		}
			}

	private static WebElement filter_FlightNo(WebDriver driver) {
		wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']")));
		return driver.findElement(By.xpath("//div[@id='select2-drop']//div[@class='select2-search']"));
	}

	private static WebElement dropdownbox_FlightNo(WebDriver driver) {
		// TODO Auto-generated method stub
		wait = new WebDriverWait(driver,120);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@ng-if='elem.needList']")));
		//return driver.findElement(By.xpath("//div[@ng-if='elem.needList']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='select2-choices']")));
		return driver.findElement(By.xpath("//ul[@class='select2-choices']"));
	}

	private static WebElement list_FlightNofilter(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//div[contains(text(),'Flight No')]")); 
		
		//New attribute value after neo upgrade
		//return driver.findElements(By.xpath("//span[contains(text(),'Select an Item')]")).get(3);
		
	}

	/*private static WebElement list_FlightNoRange(WebDriver driver) {
		String xpath="//div[@id='select2-drop']//div[@class='select2-search']";
		WebElement element = driver.findElement(By.xpath(xpath));
		return element;
	}*/

	//div[@ng-hide='filterInvocationFromDashBoard']/div[contains(@class,'select2-container form')]
	private static void dropdown_OperationalElement(WebDriver driver) {
		// TODO Auto-generated method stub

			
			Select operationalelement=new Select(driver.findElement(By.name("elem_operation")));
			operationalelement.selectByVisibleText("IN");
		
	}

	/**
	 * this function adds the data, saves and applies for a new filter in the ManagerFilter screen
	 * 
	 * @param driver
	 */
	public static void addFilterForAirportWithoutStationInManageFilter(WebDriver driver) {
		// type into textbox for filter name and clal it "generic airport name". this filtername will be referred to in other test cases
		comm.performAction(driver,txtBx_FilterName(driver), "SET", "generic airport name", "setting filter name as Aircraft subtype_32M");
		// click on the checkbox to make this a favorite filter
		comm.performAction(driver,chkBx_FavoriteFilter(driver), "CLICK", "favourite", "favourite");
		
		// click on the dropdown for Entity Type
		comm.performAction(driver,list_EntityType(driver), "CLICK", "entity type", "entity type");
		// in the dropdown for entity type, select generic
		comm.performAction(driver,list_GenericElement(driver), "CLICK", "select generic", "select generic");
		// click on the add button to add aircraft to the filters
		comm.performAction(driver,btn_Add(driver),"CLICK","add button","add button");
		// click on the dropdown for generic
		comm.performAction(driver,list_Generic(driver), "CLICK", "generic dropdown", "generic dropdown");
		// select the element for airport from the airport
		comm.performAction(driver,list_AirportElement(driver), "CLICK", "select airport", "select airport");
		
		// click on the save button
		comm.performAction(driver,btn_Save(driver),"CLICK","save button","save button");
		// in case a filter with that name already exists, there is a pop up. just clicking save inside of there
		clickSaveIfApplicable(driver);
	}

	/**
	 * this function adds the data, saves and applies for a new filter in the ManagerFilter screen
	 * 
	 * @param driver
	 */
	public static void addFilterForStationInManageFilter(WebDriver driver) {
		// type into textbox for filter name 
		comm.performAction(driver,txtBx_FilterName(driver), "SET", "station filter for test", "setting filter name");
		// click on the dropdown for Entity Type
		comm.performAction(driver,list_EntityType(driver), "CLICK", "entity type", "entity type");
		// in the dropdown for entity type, select generic
		comm.performAction(driver,list_GenericElement(driver), "CLICK", "select generic", "select generic");
		// click on the add button to add aircraft to the filters
		comm.performAction(driver,btn_Add(driver),"CLICK","add button","add button");
		// click on the dropdown for generic
		comm.performAction(driver,list_Generic(driver), "CLICK", "generic dropdown", "generic dropdown");
		// select the element for airport from the airport
		comm.performAction(driver,list_AirportElement(driver), "CLICK", "select airport", "select airport");
		// click on the dropdown for airport
		comm.performAction(driver,list_Airport(driver), "CLICK", "select subtype", "select subtype");
		//type SYD into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "SYD", "search for SYD in filter name");
		//click on the button to select an SYD from the dropdown
		comm.performAction(driver,list_SYDElement(driver), "CLICK", "select SYD", "select SYD");
		// click on the apply button
		comm.performAction(driver,btn_Apply(driver),"CLICK","apply button","apply button");
	}

	/**
	 * this function adds the data, applies for a new filter in the ManagerFilter screen
	 * 
	 * @param driver
	 */
	public static void addFilterForFlightNo(WebDriver driver, String flightNo) {
		// type into textbox for filter name 
		comm.performAction(driver,txtBx_FilterName(driver), "SET", "station filter for flight number", "setting filter name");
		// click on the dropdown for Entity Type
		comm.performAction(driver,list_EntityType(driver), "CLICK", "entity type", "entity type");
		// in dropdown for entity flight, select flight
		comm.performAction(driver,list_FlightElement(driver), "CLICK", "select flight", "select flight");
		// click on the add button to add aircraft to the filters
		comm.performAction(driver,btn_Add(driver),"CLICK","add button","add button");
		// click on the flight dropdown
		comm.performAction(driver,list_Flight(driver), "CLICK", "flight dropdown", "flight dropdown");
		// in the flight dropdown, select FlightNo
		comm.performAction(driver,list_FlightNoElement(driver), "CLICK", "select flight date", "select flight date");
		// click on the flight dropdown
		comm.performAction(driver,list_FlightNo(driver), "CLICK", "flight no dropdown", "flight no dropdown");
		// fill in the flight number
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", flightNo, "flight no textbox");
		// click on the actual flight number
		comm.performAction(driver, list_ActualFlightNoElement(driver, flightNo), "CLICK", "select the flight number element", "select the flight number element");
		
		// click on the apply button
		comm.performAction(driver,btn_Apply(driver),"CLICK","apply button","apply button");
	}

	/**
	 * this function adds the data (multiple stations), saves and applies for a new filter in the ManagerFilter screen
	 * 
	 * @param driver
	 */
	public static void addMultipleFiltersForStationInManageFilter(WebDriver driver) {
		// type into textbox for filter name 
		comm.performAction(driver,txtBx_FilterName(driver), "SET", "station filter for test", "setting filter name");
		// click on the dropdown for Entity Type
		comm.performAction(driver,list_EntityType(driver), "CLICK", "entity type", "entity type");
		// in the dropdown for entity type, select generic
		comm.performAction(driver,list_GenericElement(driver), "CLICK", "select aircraft", "select generic");
		// click on the add button to add aircraft to the filters
		comm.performAction(driver,btn_Add(driver),"CLICK","add button","add button");
		// click on the dropdown for generic
		comm.performAction(driver,list_Generic(driver), "CLICK", "generic dropdown", "generic dropdown");
		// select the element for airport from the airport
		comm.performAction(driver,list_AirportElement(driver), "CLICK", "select airport", "select airport");
		// click on select airport relation for a list
		comm.performAction(driver,list_RelationForGenericAirport(driver), "CLICK", "select relation", "select relation");
		// click on IN
		comm.performAction(driver,list_InElement(driver), "CLICK", "select IN", "select IN");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type MSQ into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "MSQ", "search for MSQ in filter name");
		//click on the button to select an MSQ from the dropdown
		comm.performAction(driver,list_MSQElement(driver), "CLICK", "select MSQ", "select MSQ");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type ISB into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "ISB", "search for ISB in filter name");
		//click on the button to select an ISB from the dropdown
		comm.performAction(driver,list_ISBElement(driver), "CLICK", "select ISB", "select ISB");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type KHI into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "KHI", "search for KHI in filter name");
		//click on the button to select an KHI from the dropdown
		comm.performAction(driver,list_KHIElement(driver), "CLICK", "select KHI", "select KHI");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type LHE into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "LHE", "search for LHE in filter name");
		//click on the button to select an LHE from the dropdown
		comm.performAction(driver,list_LHEElement(driver), "CLICK", "select LHE", "select LHE");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type DEL into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "DEL", "search for DEL in filter name");
		//click on the button to select an DEL from the dropdown
		comm.performAction(driver,list_DELElement(driver), "CLICK", "select DEL", "select DEL");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type ICN into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "ICN", "search for ICN in filter name");
		//click on the button to select an ICN from the dropdown
		comm.performAction(driver,list_ICNElement(driver), "CLICK", "select ICN", "select ICN");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type PEK into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "PEK", "search for PEK in filter name");
		//click on the button to select an PEK from the dropdown
		comm.performAction(driver,list_PEKElement(driver), "CLICK", "select PEK", "select PEK");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type NRT into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "NRT", "search for NRT in filter name");
		//click on the button to select an NRT from the dropdown
		comm.performAction(driver,list_NRTElement(driver), "CLICK", "select NRT", "select NRT");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type PVG into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "PVG", "search for PVG in filter name");
		//click on the button to select an PVG from the dropdown
		comm.performAction(driver,list_PVGElement(driver), "CLICK", "select PVG", "select PVG");
		// click on the dropdown for airport
		comm.performAction(driver,list_AirportForAirportList(driver), "CLICK", "select airport", "select airport");
		//type KTM into the textfield to narrow down the search in the drop down
		comm.performAction(driver,txtBx_ForFilter(driver), "SET", "KTM", "search for KTM in filter name");
		//click on the button to select an KTM from the dropdown
		comm.performAction(driver,list_KTMElement(driver), "CLICK", "select KTM", "select KTM");
		// click on the apply button
		comm.performAction(driver,btn_Apply(driver),"CLICK","apply button","apply button");
	}

	/**
	 * this function loads an existing filter, edits it and applies the changed filter.
	 * 
	 * @param driver
	 */
	public static boolean editFilterInManageFilter(WebDriver driver,String filter_name) {
		boolean success = true;
		
		try {
			// click on the edit radiobutton to load an existing filter
			comm.performAction(driver,radioBtn_Edit(driver), "CLICK", "edit radiobutton", "edit radiobutton");
			// click on the dropdown to select an existing filter
			comm.performAction(driver,list_FilterName(driver), "CLICK", "existing filter dropdown", "existing filter dropdown");
			// click on the textfield in the dropdown to type the name of hte existing filter
			comm.performAction(driver,txtBx_ForFilter(driver), "SET", "Aircraft subtype_32", "enter the name in the textbox to search for the filter name");
			// click on the existing filter in the dropdown
			comm.performAction(driver,list_AircraftSubtype32Element(driver, filter_name), "CLICK", "select existing filter from previous TC", "select existing filter from previous TC");
			// select dropdown for entity type
			comm.performAction(driver,list_EntityType(driver), "CLICK", "entity type", "entity type");
			// in dropdown for entity flight, select flight
			comm.performAction(driver,list_FlightElement(driver), "CLICK", "select flight", "select flight");
			// click on the add button to add the flight entity to the selection
			comm.performAction(driver,btn_Add(driver),"CLICK","add button","add button");
			// click on the flight dropdown
			comm.performAction(driver,list_Flight(driver), "CLICK", "flight dropdown", "flight dropdown");
			// in the flight dropdown, select FlightDate
			comm.performAction(driver,list_FlightDateElement(driver), "CLICK", "select flight date", "select flight date");
			// click on the relation dropdown
			comm.performAction(driver,list_Relation(driver), "CLICK", "select relation", "select relation");
			// select relative
			comm.performAction(driver,list_RelativeElement(driver), "CLICK", "select RELATIVE", "select RELATIVE");		
			// select first textbox to enter relative days in the past
			comm.performAction(driver,txtBx_RelativeDaysPast(driver), "SET", "1", "set relative days to -1");
			// select second textbox to enter relative days in the future
			comm.performAction(driver,txtBx_RelativeDaysFuture(driver), "SET", "1", "set relative days to +1");
		}
		catch (Exception e) {
			// there was an issue while performing any of the action above. above steps were 
			// all trying to enter values to apply in the current filter
			return false;
		}
		
		return success;
	}

	public static boolean applyFilterInManageFilterPopup(WebDriver driver) {
		boolean success = true;
		
		try {
			// click on the apply button
			comm.performAction(driver,btn_ApplyPopup(driver),"CLICK","apply button","apply button");
		}
		catch (Exception e) {
			// there was an issue while performing any of the action above. above steps were 
			// all trying to enter values to apply in the current filter
			return false;
		}
		
		return success;
	}

	public static boolean applyFilterInManageFilter(WebDriver driver) {
		boolean success = true;
		
		try {
			// click on the apply button
			comm.performAction(driver,btn_Apply(driver),"CLICK","apply button","apply button");
		}
		catch (Exception e) {
			// there was an issue while performing any of the action above. above steps were 
			// all trying to enter values to apply in the current filter
			return false;
		}
		
		return success;
	}

	/**
	 * load an existing filter and delete it.
	 * 
	 * @param driver
	 * @param filter_name 
	 */
	//changing the method to generalize on 23rd Aug,21
	public static boolean deleteFilterFromTC001InManageFilter(WebDriver driver, String filter_name) {
		boolean success = true;
		
		try {
			// click on the radio button EDIT, so that filter can be loaded and deleted in a later stage
			comm.performAction(driver,radioBtn_Edit(driver), "CLICK", "edit radiobutton", "edit radiobutton");
			// click on dropdown to select an existing filter
			comm.performAction(driver,list_FilterName(driver), "CLICK", "existing filter dropdown", "existing filter dropdown");
			// click on text field in the dropdown, so that we can type the name of the filter
			comm.performAction(driver,txtBx_ForFilter(driver), "SET", filter_name, "search for the filter name");
			// select the filter to load it
			//Changing the method to generalize list_AircraftSubtype32Element(driver) on 23rd Aug,21
			comm.performAction(driver,list_AircraftSubtype32Element(driver,filter_name), "CLICK", "select existing filter from previous TC", "select existing filter from previous TC");
			Thread.sleep(2000);
			// click on the delete button to delete the filter
			comm.performAction(driver,btn_Delete(driver),"CLICK","delete button","delete button");
			// confirm that we want to delete the filter
			comm.performAction(driver,btn_Yes(driver),"CLICK","confirm delete","confirm delete");
		}
		catch (Exception e) {
			return false;
		}
		
		return success;
	}

	/**
	 * load an existing filter and delete it.
	 * 
	 * @param driver
	 */
	public static boolean deleteFilterFromTC125InManageFilter(WebDriver driver) {
		boolean success = true;
		
		try {
			// click on the radio button EDIT, so that filter can be loaded and deleted in a later stage
			comm.performAction(driver,radioBtn_Edit(driver), "CLICK", "edit radiobutton", "edit radiobutton");
			// click on dropdown to select an existing filter
			comm.performAction(driver,list_FilterName(driver), "CLICK", "existing filter dropdown", "existing filter dropdown");
			// click on text field in the dropdown, so that we can type the name of the filter
			comm.performAction(driver,txtBx_ForFilter(driver), "SET", "generic airport name", "search for the filter name");
			// select the filter to load it
			comm.performAction(driver,list_AircraftGenericAirportNameElement(driver), "CLICK", "select existing filter from previous TC", "select existing filter from previous TC");
			//add wait time to allow the filter to load
			Thread.sleep(4000);
			// click on the delete button to delete the filter
			comm.performAction(driver,btn_Delete(driver),"CLICK","delete button","delete button");
			// confirm that we want to delete the filter
			comm.performAction(driver,btn_Yes(driver),"CLICK","confirm delete","confirm delete");
		}
		catch (Exception e) {
			return false;
		}
		
		return success;
	}

	/**
	 * find the radio button for the favorite
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement chkBx_FavoriteFilter(WebDriver driver) {
		String xpath = "//input[@name='userFilterWidgetModel_userFilterDTO_favoriteFilter']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * find the text box for the filter name
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_FilterName(WebDriver driver) {
		String xpath = "//input[@name='userFilterWidgetModel_userFilterDTO_filterName']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * return the textbox for the relative days in future for a day search
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_RelativeDaysFuture(WebDriver driver) {
		String xpath = "(//input[@placeholder='Days'])[2]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the textbox for the relative days in past for a day search
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_RelativeDaysPast(WebDriver driver) {
		String xpath = "(//input[@placeholder='Days'])[1]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the textbox for the filtername in the dropdown searching for an existing filter
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_ForFilter(WebDriver driver) {
		String xpath = "//div[@id='select2-drop']/div/input";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the radiobutton for editing an existing filter
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement radioBtn_Edit(WebDriver driver) {
		String xpath = "//label[contains(text(), 'Edit')]/input";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * find the dropdown for the filter name
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_FilterName(WebDriver driver) {
		String xpath = "//b[contains(text(), 'Filter Name')]/../../div/div/a";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * find the dropdown for the Entity Type
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_EntityType(WebDriver driver) {
		String xpath = "//b[contains(text(), 'Entity Type')]/../../following-sibling::li/div/a";

		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * find the dropdown for aircrafts
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_Aircraft(WebDriver driver) {
		//changed path because of application upgrade in third week of August
//		String xpath = "//th[text()='Aircraft']/../../following-sibling::tbody/tr/td/div/div/div/following-sibling::div/div/following-sibling::div/div/div/a";
		String xpath = "(//span[text()='Select an Item'])[last()]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the dropdown for generic elements
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_Generic(WebDriver driver) {
		//changed path because of application upgrade in third week of August
//		String xpath = "//th[text()='Generic']/../../following-sibling::tbody/tr/td/div/div/div/following-sibling::div/div/following-sibling::div/div/div/a";
		String xpath = "(//span[text()='Select an Item'])[last()]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the dropdown for aircraft subtypes
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AircraftSubtype(WebDriver driver) {
		//changed path because of application upgrade in third week of August
//		String xpath = "//span[contains(text(),'Aircraft Subtype')]/../../../../../../div[2]/div[2]/div[3]/div[2]/div/div/div/div/a";
		String xpath = "(//a[@class='select2-choice'])[last()]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * find the dropdown for Flight no
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_FlightNodropdown(WebDriver driver) {
		String xpath = "//span[contains(text(),'Flight No')]/../../../../../../div[2]/div[2]/div[3]/div[2]/div/div/div/div/a";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the dropdown for airports
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_Airport(WebDriver driver) {
		String xpath = "//span[contains(text(),'Airport')]/../../../../../../div[2]/div[2]/div[3]/div[2]/div/div/div/div/a";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the dropdown for airports
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AirportForAirportList(WebDriver driver) {
		//changed path because of application upgrade in third week of August
//		String xpath = "//span[contains(text(),'Airport')]/../../../../../../div[2]/div[2]/div[3]/div[2]/div/div/div/div/ul";
		String xpath = "//ul[@class='select2-choices']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the dropdown for flights
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_Flight(WebDriver driver) {
		//Below Xpath changed after upgrade
		//String xpath = "//th[text()='Flight']/../../following-sibling::tbody/tr/td/div/div/div/following-sibling::div/div/following-sibling::div/div/div/a";
		// changed after upgrade - version from hannu
		WebElement Selectdropdown = driver.findElement(By.xpath("(//span[text()='Select an Item'])[last()]"));
		//Changed after upgrade - version from moumita - this doesn't work with Hannu's script. I hope my solution works for Moumita's?
//		WebElement Selectdropdown = driver.findElements(By.xpath("//span[contains(text(),'Select an Item')]")).get(3);

		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(Selectdropdown));
		
		return Selectdropdown;
	}
	
	/**
	 * find the dropdown for flight numbers
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_FlightNo(WebDriver driver) {
		String xpath = "(//a[@href='javascript:void(0)'])[9]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the text box for flight numbers
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_FlightNoFocused(WebDriver driver) {
		String xpath = "(//input[@class='select2-input select2-focused'])[last()]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the dropdown for the relation (which appears once flight date is selected)
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_Relation(WebDriver driver) {
		//changed path because of application upgrade in third week of August
//		String xpath = "//th[text()='Flight']/../../following-sibling::tbody/tr/td/div/div/div/following-sibling::div/div/div[2]/select";
		String xpath = "//select[@name='elem_operation']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the dropdown for the relation (which appears once airport from generic is selected)
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_RelationForGenericAirport(WebDriver driver) {
		String xpath = "//span[text()='Airport']/../../../../div[2]//select";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the save button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Save(WebDriver driver) {
		String xpath = "//button[contains(text(), 'Apply')]/../../li[3]/button";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the apply button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Apply(WebDriver driver) {
		String xpath = "//button[contains(text(), 'Apply')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static WebElement btn_ApplyPopup(WebDriver driver) {
		String xpath = "(//button[contains(text(), 'Apply')])[2]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * find the YES button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Yes(WebDriver driver) {
		String xpath = "//span[contains(text(), 'YES')]/..";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * find the add button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Add(WebDriver driver) {
		String xpath = "//button[contains(text(), 'Add')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * find the DELETE button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_Delete(WebDriver driver) {
		String xpath = "//button[contains(text(), 'Delete')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
		
	/**
	 * return the Dropdown Element Flight out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_FlightElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Flight')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	

	/**
	 * return the dropdown element flight date out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_FlightDateElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Flight Date')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element flight no out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_FlightNoElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Flight No')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element from the actual flight no out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_ActualFlightNoElement(WebDriver driver, String flightNo) {
		String xpathDropdownItem = "//div[contains(text(),'" + flightNo + "')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element aircraft out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AircraftElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Aircraft')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element aircraft out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_FlightnoElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Flight')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element Generic out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_GenericElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Generic')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element 32M out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_32MElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'32M')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element 78E out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_78EElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'78E')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element flight numbers out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_flightnumbers(WebDriver driver,String flightno) {
		String xpathDropdownItem = "//div[contains(text(),'32M')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element SYD out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_SYDElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'SYD')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element MSQ out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_MSQElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'MSQ')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element ISB out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_ISBElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'ISB')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element KHI out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_KHIElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'KHI')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element LHE out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_LHEElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'LHE')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element DEL out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_DELElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'DEL')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element ICN out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_ICNElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'ICN')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element PEK out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_PEKElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'PEK')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element PVG out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_PVGElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'PVG')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element NRT out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_NRTElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'NRT')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element KTM out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_KTMElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'KTM')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}
	
	/**
	 * return the dropdown element "Aircraft subtype" out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AircraftSubtypeElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Aircraft Subtype')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element "Airport" out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AirportElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'Airport')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element "Aircraft subtype_32" out of a dropdown list
	 * 
	 * @param driver
	 * @param filter_name 
	 * @return
	 */
	//hanged the below method to generalize on 23rd Aug,21
	public static WebElement list_AircraftSubtype32Element(WebDriver driver, String filter_name) {
		String xpathDropdownItem = "//div[contains(text(),'"+filter_name+"')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element "generic airport name" out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AircraftGenericAirportNameElement(WebDriver driver) {
		String xpathDropdownItem = "//div[contains(text(),'generic airport name')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
	}

	/**
	 * return the dropdown element IN out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_InElement(WebDriver driver) {
		String xpathDropdownElementRelative = "//option[@label='IN']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownElementRelative)));
		
		element = driver.findElement(By.xpath(xpathDropdownElementRelative));
		return element;
	}	
	
	/**
	 * return the dropdown element Relative out of a dropdown list
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_RelativeElement(WebDriver driver) {
		String xpathDropdownElementRelative = "//option[@label='RELATIVE']";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownElementRelative)));
		
		element = driver.findElement(By.xpath(xpathDropdownElementRelative));
		return element;
	}

	/**
	 * this clicks on the save button, which appears in a pop up, once this pop up gets displayed.
	 * this pop up gets displayed when clicking on save in the manage filter screen.
	 * as this doesn't happen every time, there is a try catch. nothing has to be done, in case the element cannot be found.
	 * 
	 * @param driver
	 */
	public static void clickSaveIfApplicable(WebDriver driver) {
		try { 
			comm.performAction(driver,btn_SaveIfApplicable(driver), "CLICK", "yes to overwrite with saving", "yes to overwrite with saving");
		} catch(Exception e) {
			//this element might not exist, as it only pops up if we are overwriting instead of saving. hence catching this exception
		}
	}
	
	/**
	 * close the Manage Filter screen
	 * 
	 * @param driver
	 */
	public static void closeManageFilterScreen(WebDriver driver) {
		comm.performAction(driver,driver.findElement(By.xpath("(//span[contains(text(), 'Remove Tab')])[1]")), "CLICK", "favourite", "favourite");
	}

	public static boolean deleteFilterFromInManageFilter(WebDriver driver) {
		// TODO Auto-generated method stub
        boolean success = true;
		
		try {
			// click on the radio button EDIT, so that filter can be loaded and deleted in a later stage
			comm.performAction(driver,radioBtn_Edit(driver), "CLICK", "edit radiobutton", "edit radiobutton");
			// click on dropdown to select an existing filter
			comm.performAction(driver,list_FilterName(driver), "CLICK", "existing filter dropdown", "existing filter dropdown");
			// click on text field in the dropdown, so that we can type the name of the filter
			comm.performAction(driver,txtBx_ForFilter(driver), "SET", "Filter for swaping", "search for the filter name");
			// select the filter to load it
			comm.performAction(driver,list_filterforswaping(driver), "CLICK", "", "select existing filter TC");
			//Allow the filter to load (21st jan,22)
			Thread.sleep(3000);
			// click on the delete button to delete the filter
			comm.performAction(driver,btn_Delete(driver),"CLICK","","delete button");
			// confirm that we want to delete the filter
			comm.performAction(driver,btn_Yes(driver),"CLICK","","confirm delete");
		}
		catch (Exception e) {
			return false;
		}
		
		return success;
	}

	private static WebElement list_filterforswaping(WebDriver driver) {
		// TODO Auto-generated method stub
        String xpathDropdownItem = "//div[contains(text(),'Filter for swaping')]";
		
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathDropdownItem)));
		
		element = driver.findElement(By.xpath(xpathDropdownItem));
		return element;
		
	}
	
	public static void closeManageFilterPopup(WebDriver driver) {
		comm.performAction(driver,driver.findElement(By.xpath("(//button[@title='Close'])[1]")), "CLICK", "favourite", "favourite");
	}
}