package pageObjects;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;
import utilities.CommonLibrary;

public class IFlightNeo_HomePage {

	public static CommonLibrary com = new CommonLibrary();
	private static WebElement element = null;
	private static By locator;
	private static WebDriverWait wait;
	static int rows,popupappear;

	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;

	
	/** Please wait spinner image on Home Page */
	public static WebElement spinner_PleaseWait(WebDriver driver) {
		wait = new WebDriverWait(driver, 70);
		locator = By.xpath("//span[contains(text(),'Please Wait')]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element = driver.findElement(locator);
		return element;
	}
	
	/** Date place holder on Home Page */
	public static WebElement label_Date(WebDriver driver) throws InterruptedException {

		wait = new WebDriverWait(driver,60);

	 	element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currentDateTime")));
		Thread.sleep(2000);

		return element;
	}
	
	/** User box section in Home Page */
	public static WebElement div_UserBox(WebDriver driver) {
		locator = By.xpath("//div[@class='user_box']");
		element = driver.findElement(locator);
		return element;
	}
	
	public static WebElement link_SwitchRole(WebDriver driver) {
		wait = new WebDriverWait(driver,60);
	 	element = wait.until(ExpectedConditions.elementToBeClickable(div_UserBox(driver).findElement(By.xpath("//a[contains(text(),'Switch Role')]"))));
		return element;
	}
	
	//Switch Role Link in top right corner
	public static WebElement dropdownExpandSelectRole(WebDriver driver) {
		wait = new WebDriverWait(driver,60);
	 	element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("select2-arrow"))));
		return element;
	}
	
	//Expand Arrow icon to get list of role in switch role popup
	public static WebElement expandArrowRole(WebDriver driver) {
		wait = new WebDriverWait(driver,60);
	 	element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("select2-arrow"))));
		return element;
	}
	
	//Select role from Switch user role popup dropdown
	public static WebElement selectRole(WebDriver driver, String role ) {
		wait = new WebDriverWait(driver,60);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'"+role+"')]")));
		return element;
	}
	//List of Roles displayed in switch role dropdown
	public static List<WebElement> roleList(WebDriver driver) {
		List<WebElement> elements = driver.findElements(By.xpath("//div[@id='select2-drop']/ul/li"));
		return elements;
	}
	
		
	
	//Select role from Switch user role popup dropdown
	public static WebElement button_ApplyInSwithRole(WebDriver driver) {
		wait = new WebDriverWait(driver,60);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'APPLY')]")));
		return element;
	}
	//OK button in the next popup comes after switch role
	public static WebElement button_OK(WebDriver driver) {
		wait = new WebDriverWait(driver,60);
		element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/span[contains(text(),'OK')]")));
		return element; 
	}
	
	public static WebElement userRole(WebDriver driver) {
		wait = new WebDriverWait(driver, 120);
		element = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(text(),'Welcome')]"))));
		return element;
	}
	/*
	 * This method is to get the User role of the logged in User
	 * @author Prakritesh Saha
	 * @since 23-mar-2022
	 * @param driver
	 * @return userRole
	 */
	public static String getUserRole(WebDriver driver){
		String userRole= userRole(driver).getText();
		return userRole;
		
	}
	
	/** Sign Out Option in Home Page */
	public static WebElement link_SignOut(WebDriver driver) {
		locator = By.xpath("//div[@class='user_box']//a[contains(text(),'Sign')]");
		element = driver.findElement(locator);
		return element;
	}

	private static WebElement breaktrip(WebDriver driver) {
		return driver.findElement(By.xpath("//li//span[text()='Break Trip']"));
	}
	
	/** Airport Option as Main menu in Home Page */
	public static WebElement mainMenu_Airport(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		locator = By.xpath("//a[@id='menu_aircraft']");
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		element = driver.findElement(locator);
		return element;
	}
	
	/** SAW Option as Main menu in Home Page */
	public static WebElement mainMenu_SAW(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'S.A.W')]")));
		element = driver.findElement(By.xpath("//a[contains(text(),'S.A.W')]"));
		return element;
	}

	/** Sub menu Seasonal Awareness under SAW in Home Page */
	public static WebElement subMenu_SeasonalAwareness(WebDriver driver) {
		element = driver.findElement(By.xpath("//a[contains(text(),'Situational Awareness')]"));
		return element;
	}

	/** Aircraft Option as Main menu in Home Page */
	public static WebElement mainMenu_Aircraft(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		locator = By.xpath("//a[@id='menu_aircraft']");
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			element = driver.findElement(locator);
		}
		catch(TimeoutException toe) {}
		return element;
	}
	
	/** Sub menu Aircraft Details under Aircraft in Home Page */
	public static WebElement subMenu_AircraftDetails(WebDriver driver) {
		element = driver.findElement(By.id("menu_aircraftDetails"));
		return element;
	}
	
	/** Sub menu Gantt under Aircraft in Home Page */
	public static WebElement subMenu_AircraftGantt(WebDriver driver) {
		element = driver.findElement(By.xpath("//ul[@id='nav']//li//ul//li//a[@id='menu_aircraftGantt']"));
		return element;
	}

	/** Messages Option as Main menu in Home Page */
	public static WebElement mainMenu_Messages(WebDriver driver) {
		element = driver.findElement(By.xpath("//a[contains(text(),'Messages')]"));
		return element;
	}
	
	/** Message List Sub menu in Home Page under Messages Option */
	public static WebElement subMenu_MessageList(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu_messageList")));
		element = driver.findElement(By.id("menu_messageList"));
		return element;
	}
	
	/** Flights Option as Main menu in Home Page */
	public static WebElement mainMenu_Flights(WebDriver driver) {
		element = driver.findElement(By.xpath("//a[contains(text(),'Flights')]"));
		return element;
	}
	
	/** EditFlight Sub menu in Home Page under Flights Option */
	public static WebElement subMenu_EditFlight(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu_editFlight")));
		element = driver.findElement(By.id("menu_editFlight"));
		return element;

	}
  
	/** AddFlights Sub menu in Home Page under Flights Option */
	public static WebElement subMenu_AddFlights(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='menu_flights']//../ul/li[1]/a")));
		element = driver.findElement(By.xpath("//a[@id='menu_flights']//../ul/li[1]/a"));
		return element;

	}

	/** CancelledActivities Sub menu in Home Page under Flights Option */
	public static WebElement subMenu_CancelledActivities(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='menu_cancelledactivity']")));
		element = driver.findElement(By.xpath("//a[@id='menu_cancelledactivity']"));
		return element;

	}

	/** "Flight Leg Details" Sub menu in Home Page under Flights Option */
	public static WebElement subMenu_FlightLegDetails(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='menu_flightLegDetails']")));
		element = driver.findElement(By.xpath("//a[@id='menu_flightLegDetails']"));
		return element;
	}

	/** Flight Date in Flight Leg Detail*/
	public static WebElement txtbox_Flightdate(WebDriver driver)	{
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("flightLegDetailsWidgetModel_flightDate")));
		element = driver.findElement(By.name("flightLegDetailsWidgetModel_flightDate"));
		return element;
	}


	/** Find flight Image link at the bottom in Gantt Screen */
	public static WebElement imgLink_FindFlight(WebDriver driver) {
		wait = new WebDriverWait(driver, 120);
		locator = By.xpath("//li[@title='Find Flight']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		// Actions a5 = new Actions(driver);
		element = driver.findElement(locator);
		return element;
	}

	public static WebElement realworld_flight_No(WebDriver driver) {
		wait = new WebDriverWait(driver, 80);
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='IATAflightIdLabel_W2_fltNo']")));
		element = driver.findElement(By.xpath("//input[@id='IATAflightIdLabel_W2_fltNo']"));
		return element;
	}
	
	/** Flight No. textbox inside Find Flight Dialog */
	public static WebElement txtBx_FlightNoOnFindFlight(WebDriver driver) {
		wait = new WebDriverWait(driver, 80);
		locator = By.xpath("//div[contains(@id,'IATAflightIdLabel')]//input[contains(@id,'fltNo')]");
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element = driver.findElement(locator);
		return element;
	}  
	
	/** Aircraft Details form */
	public static WebElement form_AircraftDetails(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("aircraftDetailsForm")));
		element = driver.findElement(By.name("aircraftDetailsForm"));
		return element;
	}
	
	public static WebElement txtbx_Regno(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_aircraftRegistration"));
		return element;
	}

	public static WebElement txtbx_ACR(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_tailNumber"));
		return element;
	}

	public static WebElement txtbx_Manufac(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_manufacturerName"));
		return element;
	}

	public static WebElement txtbx_Name(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_aircraftName"));
		return element;
	}

	public static WebElement txtbx_Eng(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_engineInformation"));
		return element;
	}

	public static WebElement txtbx_Engtyp(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_engineType"));
		return element;
	}

	public static WebElement date_Delv(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_deliveredDate"));
		return element;
	}
    /* Element attribute changed hence modifying the webelement on 14th jun,22*/
	public static WebElement txtbx_F(WebDriver driver) {
		element = driver.findElements(By.xpath("//input[contains(@ng-model,'seatCount')]")).get(0);
		return element;
	}
	
	 /* Element attribute changed hence modifying the webelement on 14th jun,22*/
	public static WebElement txtbx_J(WebDriver driver) {
		element = driver.findElements(By.xpath("//input[contains(@ng-model,'seatCount')]")).get(1);
		return element;
	}
   
	 /* Element attribute changed hence modifying the webelement on 14th jun,22*/
	public static WebElement txtbx_Y(WebDriver driver) {
		element = driver.findElements(By.xpath("//input[contains(@ng-model,'seatCount')]")).get(2);
		return element;
	}

	public static WebElement txtbx_Cabin(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_cabinCrewJumpSeatCount"));
		return element;
	}

	public static WebElement txtbx_Cockpit(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_cockpitCrewJumpSeatCount"));
		return element;
	}
    //Webelement attribute changed hence changed the code on 14th jan,22
	public static WebElement txtbx_Remarks(WebDriver driver) {
		element = driver.findElement(By.xpath("//textarea[@title='aircraftDTO.remark']"));
		return element;
	}
	
	public static WebElement RealWorld_FF_DateField(WebDriver driver) {
		element = driver.findElement(By.xpath("//input[@id='W2_flightDateLabel_findFlightWidget']"));
		return element;
	}

	/** Departure field on Find Flight Dialog in Gantt Screen*/
	public static WebElement txtBx_DepartureOnFindFlight(WebDriver driver) {
		element = driver.findElement(By.xpath("//body/div[@id='select2-drop']/div//input"));
		return element;
	}

	public static WebElement list_DepArrSearchResultOnFindFlight(WebDriver driver) {
		element = driver.findElement(By.xpath("//div[@class='select2-result-label']"));
		return element;
	}

	public static WebElement txtBx_ArrivalOnFindFlight(WebDriver driver) {
		element = driver.findElement(By.xpath("//body/div[@id='select2-drop']/div//input"));
		return element;
	}

	/** Search button on Find Flight in Gantt Screen */
	public static WebElement btn_SearchOnFindFlight(WebDriver driver) {
		element = driver.findElement(By.xpath("//button[@ng-click='findFlight()']"));
		return element;
	}

	public static WebElement rightClick_Menu(WebDriver driver) {
		element = driver.findElement(By.xpath("//ul[@class='context-menu-list context-menu-root']"));
		return element;
	}

	public static WebElement Delete_Flight(WebDriver driver) {
		element = driver
				.findElement(By.xpath("//ul[@class='context-menu-list']//li//span[contains(text(),'Delete Flight')]"));
		return element;
	}

	public static WebElement confirm_DeleteFlightPopup(WebDriver driver) {
		
		wait = new WebDriverWait(driver, 30);
		// slight change in visible text for delete flight pop-up after neo upgrade ( 3rd sep)
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(text(),'Do you want to delete the selected Flight?')]")));
		element = driver.findElement(By.xpath("//div[contains(text(),'Do you want to delete the selected Flight?')]"));
		return element;
	}

	public static WebElement Yes_DeleteFlight(WebDriver driver) {
		element = driver.findElement(By.xpath("//span[contains(text(),'YES')]"));
		return element;
	}

	public static WebElement btn_add(WebDriver driver) {
		element = driver.findElement(By.xpath("//button[@ng-click='addButton()']"));
		return element;
	}

	/*******************************************************
	 * Select Aircraft Details from Home Page->Aircraft
	 * @author Moumita Sengupta
	 *******************************************************/
	public static void selectAircraftDetails(WebDriver driver) {
		mainMenu_Airport(driver);
		// Aircraft Menu
		com.performAction(driver, mainMenu_Aircraft(driver), "click", "", "aircraftmenu");
		List<WebElement> nav_dropdown = driver.findElements(By.xpath("//ul[@id='nav']//li//ul//li//a"));
		for (int navcounter = 0; navcounter < nav_dropdown.size(); navcounter++) {
			WebElement element = nav_dropdown.get(navcounter);
			String subHeader = element.getAttribute("innerHTML").replaceAll("\\s", "");
			if (subHeader.contains("AircraftDetails")) {
				com.performAction(driver, subMenu_AircraftDetails(driver), "click", "", "aircraftmenudetails");
			}
		}
		form_AircraftDetails(driver);
		Actions a5 = new Actions(driver);
		a5.moveToElement(driver.findElement(By.name("aircraftDetailsForm"))).click();
	}

	/*******************************************************
	 * Fill Aircraft detail form
	 * @author Moumita Sengupta
	 *******************************************************/
	public static void fillform_Aircraftdetails(WebDriver driver, String Regno, String ACR, String name, String Manufac,
			String Eng, String engtyp, String Delv, String f, String J, String y, String cabin, String cockpit,
			String remarks, String validform, String validTo, String Etops, String flightrange, String acsubtype,
			String Rotcode) throws InterruptedException	{
		com.performAction(driver, btn_add(driver), "click", "", "Add");
		com.performAction(driver, txtbx_Regno(driver), "SET", Regno, "Set Regno");
		com.performAction(driver, txtbx_ACR(driver), "SET", ACR, "Set ACR");
		com.performAction(driver, dropdown_Acsubtype(driver), "click", "", "click on acsubtype");
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[text()='" + acsubtype + "']")).click();
		dropdown_Rotcode(driver, Rotcode);
		htmlLib.logReport("Capture screenshot of Rotcode", "Set '" + Rotcode + "' is Successful", "PASS", driver, true);
		com.performAction(driver, date_Validform(driver), "SET", validform, "Set validform date");
		com.performAction(driver, date_ValidTo(driver), "SET", validTo + Keys.TAB, "Set validTo date");
		dropdown_Etops(driver,Etops);
		htmlLib.logReport("Capture screenshot of Etops", "Set '" + Etops + "' is Successful", "PASS", driver, true);
		//com.performAction(driver,dropdown_Etops(driver), "SET", Etops+Keys.ENTER, "Set ETOPS");
		com.performAction(driver, txtbx_Flightrange(driver), "SET", flightrange, "Set flightrange");
		com.performAction(driver, txtbx_Name(driver), "SET", name, "Set name");
		com.performAction(driver, txtbx_Manufac(driver), "SET", Manufac, "Set Manufac");
		com.performAction(driver, txtbx_Eng(driver), "SET", Eng, "Set Eng");
		com.performAction(driver, txtbx_Engtyp(driver), "SET", engtyp, "Set engtyp");
		//date_Delv(driver).clear();
		com.performAction(driver, date_Delv(driver), "SET", Delv + Keys.TAB, "Set delivered date");
		//driver.findElement(By.xpath("//td[@data-handler='selectDay']")).click();
		com.performAction(driver, txtbx_F(driver), "SET", f, "Set f class count");
		com.performAction(driver, txtbx_J(driver), "SET", J, "Set J class count");
		com.performAction(driver, txtbx_Y(driver), "SET", y, "Set Y class count");
		com.performAction(driver, txtbx_Cabin(driver), "SET", cabin, "Set cabin count");
		com.performAction(driver, txtbx_Cockpit(driver), "SET", cockpit, "Set cockpit count");
		com.performAction(driver, txtbx_Remarks(driver), "SET", remarks, "Set remarks");
	}
	/*******************************************************
	 * Sign out of Application
	 * @author Rohit
	 *******************************************************/
	public static void signOut(WebDriver driver) {
		wait = new WebDriverWait(driver, 1000);
		wait.until(ExpectedConditions.elementToBeClickable(div_UserBox(driver)));
		// User box section
		div_UserBox(driver).click();
		// Sign out
		wait.until(ExpectedConditions.elementToBeClickable(link_SignOut(driver)));
		link_SignOut(driver).click();
		// Wait
		IFlightNeo_LoginPage.txtbx_UserName(driver);
	}
    // to be placed inside aircraft creation
	private static void dropdown_Etops(WebDriver driver, String etops) {
		
		Select Etops2=new Select(driver.findElement(By.name("aircraftDTO_etopsRating")));
		Etops2.selectByVisibleText(etops);
	}
	
	
	
		 // to be placed inside aircraft creation
	private static WebElement txtbx_Flightrange(WebDriver driver) {

		element = driver.findElement(By.name("aircraftDTO_maxFlightRangeMins"));
		return element;
	}
	
	public static WebElement goto_CurrentDate(WebDriver driver){		
        element = driver.findElement(By.xpath("//li[@title='Goto current date']"));
        return element;
	}
	
	private static WebElement date_ValidTo(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_toDate"));
		return element;
	}

	private static WebElement date_Validform(WebDriver driver) {
		element = driver.findElement(By.name("aircraftDTO_fromDate"));
		return element;
	}

	private static void dropdown_Rotcode(WebDriver driver, String Rotcode) {
		driver.findElement(By.xpath("//li[@oh-compid='FAC001_005']//span[contains(text(),'Select IATA')]")).click();
		driver.findElement(By.xpath("//div[text()='" + Rotcode + "']")).click();

	}

	private static WebElement dropdown_Acsubtype(WebDriver driver) {
		element = driver.findElement(By.xpath("//li[@oh-compid='FAC001_004']//span[contains(text(),'Select IATA')]"));
		return element;
	}
	
	
	
	/*******************************************************
	 * Navigates to Gantt Screen
	 * @author Rohit Prajapati
	 * @throws InterruptedException 
	 *******************************************************/
	public static void selectGantt(WebDriver driver) throws InterruptedException {
		mainMenu_Airport(driver);
        com.performAction(driver, mainMenu_Aircraft(driver), "CLICK", "", "Aircraft Option from Main Menu");
        List<WebElement> nav_dropdown=driver.findElements(By.xpath("//ul[@id='nav']//li//ul//li//a"));
        for(int iterator=0; iterator<nav_dropdown.size(); iterator++)
        {
            WebElement element = nav_dropdown.get(iterator);
            String subHeader=element.getAttribute("innerHTML"); 
            if(subHeader.contains("Gantt")) {
                com.performAction(driver, subMenu_AircraftGantt(driver), "CLICK", "", "Gantt Option");
            }
        }

		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a[contains(text(),'OPS GANTT')]")));
		Thread.sleep(5000);
      //a[contains(text(),'OPS GANTT')]
       //com.performAction(driver, IFlightNeo_Gantt.link_DayZoom(driver), "CLICK", "", "DayZoom button");
        

	}

	public static void scrollandsave(WebDriver driver)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath("//button[@ng-click='save()']"));
		js.executeScript("arguments[0].scrollIntoView();", element);
		element.click();

	}

	public static WebElement btn_CloseFlightDetailsWindow(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		List<WebElement> Allclose_button = driver.findElements(By.xpath("//div[contains(@class,'ui-dialog-titlebar')]//button[@title='Close']"));
		// The webelement list size changed in any of the recent release to 83 from 82 , hence changed that on 1st feb,22
		WebElement close_button=Allclose_button.get(Allclose_button.size()-83);
		wait.until(ExpectedConditions.visibilityOf(close_button));
		return close_button;
	}
	
	public static WebElement alert_Flightnotfound(WebDriver driver) {
		
		errorpopup_flightdoesnotexist(driver);
        return element;
	}
	
	private static WebElement errorpopup_flightdoesnotexist(WebDriver driver) {
		// TODO Auto-generated method stub
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[text()='Flight does not exist within the filter criteria.']")));
		element = driver.findElement(By.xpath("//span[text()='Flight does not exist within the filter criteria.']"));
		return element;
		
	}

	public static WebElement alert_flightnotfoundonsameday(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[text()='Flight does not exist on the selected date.']")));
		element = driver.findElement(By.xpath("//span[text()='Flight does not exist on the selected date.']"));
		return element;
	}

	/*********************************************************************************************
	 * View value of cdmtimestamp 
	 * @author : Moumita Sengupta 
	 * @since : 23/11/2020
	 ********************************************************************************************/

	public static String value_cdmtimestamp(WebDriver driver) throws InterruptedException

	{

		driver.findElement(By.xpath("//*[text()='CDM']")).click();

		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		String toenable = "document.getElementsByName('flightLegDetailsWidgetModel_startupRequestActual')[0].removeAttribute('disabled');";
		javascript.executeScript(toenable);
		Thread.sleep(3000);

		String value = driver.findElement(By.name("flightLegDetailsWidgetModel_startupRequestActual"))
				.getAttribute("value");
		return value;

	}

	/**
	 * open the "Edit Flight" screen from the main menu.
	 * 
	 * @param driver
	 */
	public static void selectEditFlight(WebDriver driver) {
		// click on the Flights option in the "main menu"
		com.performAction(driver, mainMenu_Flights(driver), "CLICK", "", "Flights Option from Main Menu");
		// select "Edit Flight" in the menu.
        com.performAction(driver, subMenu_EditFlight(driver), "CLICK", "", "Edit Flight Option from Flights Sub Menu");
        
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.hover();
        scn.mouseMove(-300, 0);

	}
	
	/**
	 * open the "Add Flights" screen from the main menu.
	 * 
	 * @param driver
	 */
	public static void selectSituationalAwarenessWindow(WebDriver driver) {
		WebElement additionalMenuOption = menu_AdditionalMenuOption(driver);
		if(additionalMenuOption != null)
			additionalMenuOption.click();
		
		// click on the S.A.W option
		com.performAction(driver, subMenu_SAW(driver), "CLICK", "", "SAW menu option");
		// select "Situational Awareness Window" in the menu.
        com.performAction(driver, subMenu_SituationalAwarenessWindow(driver), "CLICK", "", "Situational Awareness Window menu option");
        
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.mouseMove(500, 500);
	}

	/** S.A.W. Sub menu in Home Page under ... */
	public static WebElement subMenu_SAW(WebDriver driver) {
		String xpath = "//a[text()='S.A.W']";
		
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/** Situational Awareness Window Sub menu in Home Page under S.A.W. Option */
	public static WebElement subMenu_SituationalAwarenessWindow(WebDriver driver) {
		String xpath = "//a[contains(text(), 'Situational Awareness')]";
		
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	
	/**
	 * open the "Add Flights" screen from the main menu.
	 * 
	 * @param driver
	 */
	public static void selectAddFlights(WebDriver driver) {
		// click on the Flights option in the "main menu"
		com.performAction(driver, mainMenu_Flights(driver), "CLICK", "", "Flights Option from Main Menu");
		// select "Edit Flight" in the menu.
        com.performAction(driver, subMenu_AddFlights(driver), "CLICK", "", "Add Flights Option from Flights Sub Menu");
        
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.mouseMove(500, 500);
	}

	/**
	 * open the "Cancelled Activities" screen from the main menu.
	 * 
	 * @param driver
	 */
	public static void selectCancelledActivities(WebDriver driver) {
		// click on the Flights option in the "main menu"
		com.performAction(driver, mainMenu_Flights(driver), "CLICK", "", "Flights Option from Main Menu");
		// select "Edit Flight" in the menu.
        com.performAction(driver, subMenu_CancelledActivities(driver), "CLICK", "", "Cancelled Activities Option from Flights Sub Menu");
        
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.mouseMove(1500, 1500);
	}

	/**
	 * open the "Add Flights" screen from the main menu.
	 * 
	 * @param driver
	 */
	public static void selectFlightLegDetails(WebDriver driver) {
		// click on the Flights option in the "main menu"
		com.performAction(driver, mainMenu_Flights(driver), "CLICK", "", "Flights Option from Main Menu");
		// select "Edit Flight" in the menu.
        com.performAction(driver, subMenu_FlightLegDetails(driver), "CLICK", "", "Flight Leg Details Option from Flights Sub Menu");
        
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.mouseMove(500, 500);
	}

	/**
	 * open the "MessagesList" screen from the main menu.
	 * 
	 * @param driver
	 */
	public static void selectMessageList(WebDriver driver) {
		// click on the Messages option in the "main menu"
        com.performAction(driver, mainMenu_Messages(driver), "CLICK", "", "Flights Option from Main Menu");
		// select "Message List" in the menu.
        com.performAction(driver, subMenu_MessageList(driver), "CLICK", "", "Edit Flight Option from Flights Sub Menu");
        
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.mouseMove(400, 400);
	}
  
	public static void viewAircraft(WebDriver driver, String Regno) throws InterruptedException
	{
		driver.findElement(By.xpath("//li[@oh-compid='FAC001_002']//span[contains(text(),'Select IATA')]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys(Regno + Keys.ENTER);
		Thread.sleep(700);

	}
   // method partially modified so that flights deleted successfully message gets captured on 21st jan,22
	public static void DeleteFlight(WebDriver driver, String select) throws Exception {
		Actions action1 = new Actions(driver);
		action1.moveToElement(rightClick_Menu(driver));
		List<WebElement> rightClick_drpdown = driver
				.findElements(By.xpath("//ul[@class='context-menu-list context-menu-root']//li//span"));
		for (int optionscounter = 0; optionscounter < rightClick_drpdown.size(); optionscounter++) {
			WebElement data = rightClick_drpdown.get(optionscounter);

			if (data.getAttribute("innerHTML").contains(select)) {
				data.click();
				break;
			}

		}

		Delete_Flight(driver).click();
		geographicaldiscontinuity(driver);
		confirm_DeleteFlightPopup(driver);
		Yes_DeleteFlight(driver).click();
		Thread.sleep(1000);
		//modified below piece of code on 24th Jan,22 in order to avoid extra wait after deleting flight
		if(driver.findElement(By.xpath("//span[text()='Rule Warning']")).isDisplayed())
		{
		Thread.sleep(3000);
		forcepublish(driver);
		}
		
	    else
		{  
			System.out.println("Deleted flight");
			return;
		}
		

	}

	public static void forcepublish(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		//Add code to force publish flight on 3rd sep,21 by Moumita
				try
		        {
		        	//Click Force-publish if Rule warning appears
		         boolean ifrulewarning=driver.findElement(By.xpath("//span[text()='Rule Warning']")).isDisplayed();
		         if (ifrulewarning==true)
		         {
		         driver.findElement(By.xpath("//button[@ng-click='forcePublish()']")).click();
		         }
		         else
		         {
		         Thread.sleep(2000);
		         System.out.println("There is no force pulish option");
		         }
		         
		        }
		         catch(Exception e)
		         {
		        	 Thread.sleep(2000);
		        	 System.out.println("There is no force pulish option");
		        	 
		         }
		
	}

	public static void geographicaldiscontinuity(WebDriver driver) throws InterruptedException {
		// TODO Auto-generated method stub
		// Adding code to handle geographical discontinuity pop-up on 3rd sep,21 by Moumita
				try
		        {
		        	//Click Yes if geographical discontinuity appears
		         boolean ifgeographicalDiscontinuity=driver.findElement(By.xpath("//div[contains(text(),'Do you want to continue?')]")).isDisplayed();
		         if (ifgeographicalDiscontinuity==true)
		         {
		         driver.findElement(By.xpath("//span[text()='YES']")).click();
		         }
		         else
		         {
		         Thread.sleep(4000);
		         }
		         
		        }
		         catch(Exception e)
		         {
		        	 Thread.sleep(4000);
		         }
		
	}

	public static void set_filterdaterange(WebDriver driver) {
		Calendar today = Calendar.getInstance();
		int fromDayOfTheMonth;
		int toDayOfTheMonth;
	    fromDayOfTheMonth = today.get(Calendar.DAY_OF_MONTH); 
	    today.add(Calendar.DATE, 2);
	    toDayOfTheMonth = today.get(Calendar.DAY_OF_MONTH);
	    if(toDayOfTheMonth < fromDayOfTheMonth)
	    	toDayOfTheMonth = fromDayOfTheMonth;
		
		com.performAction(driver, date_Filterdaterange(driver), "click", "", "click on date filter");
		date_From(driver, fromDayOfTheMonth);
		date_To(driver, toDayOfTheMonth);
		com.performAction(driver, button_Search(driver), "click", "", "click on search button");

	}

	private static WebElement button_Search(WebDriver driver) {

		element = driver.findElement(By.xpath("//button[@ng-click='filterTabularPane()']"));
		return element;

	}

	public static void date_To(WebDriver driver, int day) {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//li//input[@id='W1_toDate_filterTabularPaneWidget']")));

		driver.findElement(By.xpath("//li//input[@id='W1_toDate_filterTabularPaneWidget']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[text()='"+day+"']")));

		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[text()='"+day+"']")).click();

		driver.findElement(By.xpath("//li//input[@id='W1_toDate_filterTabularPaneWidget']")).sendKeys(Keys.TAB);

	}

	public static void date_From(WebDriver driver, int day) {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//li//input[@id='W1_fromDate_filterTabularPaneWidget']")));

		driver.findElement(By.xpath("//li//input[@id='W1_fromDate_filterTabularPaneWidget']")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[text()='"+day+"']")));

		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[text()='"+day+"']")).click();

		driver.findElement(By.xpath("//li//input[@id='W1_fromDate_filterTabularPaneWidget']")).sendKeys(Keys.TAB);

	}

	public static WebElement date_Filterdaterange(WebDriver driver) {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[contains(text(),'FIltered Date Range')]//a")));
		element = driver.findElement(By.xpath("//span[contains(text(),'FIltered Date Range')]//a"));
		return element;
	}

	public static void Retrive_filteredFlightsandmatchwithGantt(WebDriver driver) {

		HashMap<Integer, String> map = new HashMap<Integer, String>();// Creating HashMap
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//table[@id='TabularPaneGrid_W1']/tbody/tr/td/a")));

		List<WebElement> tablerows = driver.findElements(By.xpath("//table[@id='TabularPaneGrid_W1']/tbody/tr/td/a"));

		for (rows = 0; rows < tablerows.size(); rows++) {

			map.put(rows, tablerows.get(rows).getText().trim()); // Put elements in Map

		}

		driver.findElement(By.xpath("//button[@ng-click='reverseLinkDataAction()']")).click();

		close_TabPane(driver);
		/*Screen scn = new Screen();

		for (int rowCounter = 0; rowCounter < tablerows.size(); rowCounter++) {

			int scroll_cnt = 45;
			for (int leftCounter = 0; leftCounter < scroll_cnt; leftCounter++) {

				scn.aSwipeLeft();
				Pattern Flight = new Pattern(Image_Path + "//" + map.get(leftCounter) + ".PNG");
				Match flight1 = scn.exists(Flight.similar(0.92));

				if (flight1 == null)

				{
					htmlLib.logReport("does flight exist in both Gantt & tabular pane(past dates)",
							"Flight not Found in past dates", "INFO", true);

				}

				if (flight1 != null)

				{
					htmlLib.logReport("does flight exist in both Gantt & tabular pane(past dates)",
							"Flight  Found in past dates", "PASS", true);
				}
			}

			for (int rightCounter = 0; rightCounter < scroll_cnt; rightCounter++) {
				scn.type(Key.RIGHT, 10);

				Pattern Flight1 = new Pattern(Image_Path + "//" + map.get(rightCounter) + ".PNG");
				Match flight2 = scn.exists(Flight1.similar(0.92));
				if (flight2 != null)

				{
					htmlLib.logReport("does flight exists in both Gantt & tabular pane in future dates",
							"Flight Found in future date", "Pass", true);

				}

				if (flight2 == null)

				{
					htmlLib.logReport("does flight exist in both Gantt & tabular pane in future dates",
							"Flight Not Found in future dates", "INFO", true);

				}

			}

		}*/

	}

	public static void close_TabPane(WebDriver driver) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='gantt_pane_icons_wrap']")));

		List<WebElement> hiddenmenus = driver.findElements(By.xpath("//a[@class='gantt_pane_icons_wrap']"));

		Actions a = new Actions(driver);
		a.moveToElement(hiddenmenus.get(4)).perform();

		driver.findElement(By.xpath("//li[@id='TabularPaneGrid_W1close']")).click();

	}
	
	/*
	 * wait till page refresh
	 */
	
	public static void waitTillPageRefreshCompletes(WebDriver driver) {
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.attributeContains(By.className("body_custom_div"), "style", "zoom: 1;"));
	}

	/*******************************************
	 * Select Seasonal Awareness Window from main menu ( modified on 14th june)
	 * @throws InterruptedException 
	 *******************************************/
	public static void selectSeasonalAwarenessWindow(WebDriver driver) throws InterruptedException {
		//Wait till page refresh
		waitTillPageRefreshCompletes(driver);
		//Thread.sleep(2000);
		// SAW
		com.performAction(driver, mainMenu_SAW(driver), "HOVER", "", "SAW option");
		//Thread.sleep(2000);
		com.performAction(driver, subMenu_SeasonalAwareness(driver), "click", "", "SAW Menu option");	
	}

	public static void Add_Widget(WebDriver driver) {
		com.performAction(driver, addWidget(driver), "click", "", "adding a widget");
		
	}


	public static WebElement addWidget(WebDriver driver) {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Widget']")));
		element= driver.findElement(By.xpath("//button[text()='Add Widget']"));
		return element;
	}

 
	public static void Add_ReqWidget(WebDriver driver) {
	    
		com.performAction(driver, addreqWidget(driver), "click", "", "adding specific widget");
	}


	public static WebElement addreqWidget(WebDriver driver) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Alert Monitor']")));
		List<WebElement> addbutton= driver.findElements(By.xpath("//button[text()='Add ']"));
		return addbutton.get(1);
	}
		
    public static void rename_header(WebDriver driver) {
    	doubleclick_header(driver);
    	com.performAction(driver, input_header(driver), "SET", "Alert Monitor Testing", "Rename header");
		
	}
	
	public static WebElement input_header(WebDriver driver) {
		
		return driver.findElement(By.xpath("//input[@ng-show='editTitle']"));
		
	}

    
	
	
	public static void doubleclick_header(WebDriver driver) {
		
		WebElement AlertMonitor=driver.findElement(By.xpath("//span[text()='Alert Monitor']"));
		Actions a = new Actions(driver);
		a.moveToElement(AlertMonitor).doubleClick(AlertMonitor).build().perform();
	
		}
	
	
   public static void actionson_widget(WebDriver driver) throws InterruptedException {
		
	   com.performAction(driver, refresh_widget(driver), "Click", "", "Refresh widget");
	   com.performAction(driver, resize_widget(driver), "Click", "", "Resize widget");
	   Thread.sleep(1000);
	   com.performAction(driver, remove_widget(driver), "Click", "", "Remove widget");
	   htmlLib.logReport("Screen capture", "Screen capture of remove widget successfull", "INFO",
				driver, true);
	   Thread.sleep(1000);
	   
		
	}

    public static WebElement refresh_widget(WebDriver driver) {
		
		return driver.findElement(By.xpath("//span[@ng-show='!edit']"));
		
	}
    
    public static WebElement resize_widget(WebDriver driver) {
		
		return driver.findElement(By.xpath("//span[@ng-click='show=!show']"));
		
	}
    
 public static WebElement remove_widget(WebDriver driver) {
		
		return driver.findElement(By.xpath("//span[@ng-click='removeDashlet()']"));
		
	}
  


	public static void close_widget(WebDriver driver) {
		driver.findElements(By.xpath("//button[@title='Close']")).get(0).click();
		
	}


	public static void select_Newscenariomode(WebDriver driver) throws InterruptedException {
		
		driver.findElement(By.xpath("//div//button[contains(@id,'GanttWorldMenu_ActListBtn')]")).click();
		driver.findElement(By.xpath("//a[text()='New Scenario Mode']")).click();
		List<WebElement> Auto_Off = driver.findElements(By.xpath("//span[text()='AUTO OFF']"));
		wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.elementToBeClickable(Auto_Off.get(0)));
		Thread.sleep(1000);
		/*element=driver.findElement(By.xpath("//li[@aria-controls='W1']//span[text()='Remove Tab']"));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Actions a = new Actions(driver);
		a.moveToElement(element).click(element).build().perform();*/
		
		//Thread.sleep(100);
		//wait = new WebDriverWait(driver, 60);
        //wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//span[text()=' LW4 ']"))));
				
		//comm.performAction(ActList_button(driver), "click", "", "click on Dropdown");
		//comm.performAction(NewScenarioModel(driver), "click", "", "click on New scenario mode");
		//close_realtimescenariotab(driver);

		
		
	}


	public static void close_realtimescenariotab(WebDriver driver) {
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Remove Tab']")));
		WebElement element=driver.findElements(By.xpath("//span[text()='Remove Tab']")).get(0);
		
		Actions a = new Actions(driver);
		a.moveToElement(element).click(element).build().perform();		
	}


	public static WebElement NewScenarioModel(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//a[text()='New Scenario Mode']"));
	}


	public static WebElement ActList_button(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//div//button[@id='W1_GanttWorldMenu_ActListBtn']"));
		
	}
	
	/** Yes button in Swap Confirmation Message */
	 public static WebElement btn_YesInSwapConfirmationMsg(WebDriver driver) {
		 WebDriverWait wait = new WebDriverWait(driver, 10);
		 try {
			 element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Confirmation Message']/../following-sibling::div/following-sibling::div//button[1]")));
		 }
		 catch(TimeoutException toe) {}
		 return element;
	 }
	 
	 /** Reason text box in swap->reason for change dialog */
	 public static WebElement txtBx_Reason(WebDriver driver){
		 wait=new WebDriverWait(driver,30);
		 element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='select2-drop']/div//input")));
	     return element;
	 }
	 
	/** Result after Swap Reason in Change Reason Dialog Filtering */
	public static WebElement list_ReasonResult(WebDriver driver){
		 wait=new WebDriverWait(driver,30);
		 element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='select2-result-label']")));
		 return element;
	}
	
	/*************************************************************************************************************************
	 * Description	: Get the Aircraft Registration of a flight by opening Flight details window and returns the string value
	 * @param 		: driver
	 * @return		: readAircraftReg
	 * @throws 		: Exception
	 *************************************************************************************************************************/
	public static String readAircraftReg(WebDriver driver) throws Exception
	{
		//Waiting for flight details to open
		List<WebElement> FlightDetails = driver.findElements(By.xpath("//span[contains(text(),'Flight Leg Details')]"));
		WebElement flightDetail=FlightDetails.get(FlightDetails.size()-1);
		 wait=new WebDriverWait(driver, 60);
		 wait.until(ExpectedConditions.visibilityOf(flightDetail));
		 WebElement element2 = driver.findElement(By.xpath("//form[contains(@name,'flightLegStatusForm')]"));
		 //htmlLib.logReport("flight Details Screen", "Flight Details Screen Open success", "Pass", true);
		 Actions action = new Actions(driver);
		 action.moveToElement(element2).build().perform();

		 // To enable AircraftRegistrationfield text box
		 JavascriptExecutor javascript = (JavascriptExecutor) driver;
		 String toenable = "document.getElementsByName('flightLegDetailsWidgetModel_aircraftReg')[0].removeAttribute('disabled');";
		 javascript.executeScript(toenable);
		 Thread.sleep(3000);
		 //Storing the Aircraft registration value of first flight in a string variable
		 String AircraftRegistration_before = driver.findElement(By.name("flightLegDetailsWidgetModel_aircraftReg")).getAttribute("value");
		 System.out.println("Aircraft Registration :" + AircraftRegistration_before);
		 //driver.findElement(By.xpath("//div[@aria-labelledby='ui-id-49']")).sendKeys(Keys.ESCAPE);
		 return AircraftRegistration_before;
		 
	}

	
	

	 /*public static void swapFlight(final WebDriver driver) throws Exception {
	        final List<WebElement> List = (List<WebElement>)driver.findElements(By.xpath("//ul[@class='context-menu-list context-menu-root']//li//span"));
	        System.out.println(List);
	        for (int drp_rightClick = 0; drp_rightClick < List.size(); ++drp_rightClick) {
	            final WebElement data = List.get(drp_rightClick);
	            if (data.getAttribute("innerHTML").contentEquals("Swap Selected")) {
	                data.click();
	                break;
	            }
	            if (data.getAttribute("innerHTML").contentEquals("Rotate Selected")) {
	                data.click();
	                break;
	            }
	        }
	        final Actions action = new Actions(driver);
	       	        
	        action.moveToElement(container_swapList(driver)).build().perform();
	        com.performAction(driver, btn_ForcePublish(driver), "Click", "", "Click on force Publish button");
	        Thread.sleep(2000L);
	        
	        try {
	            action.moveToElement(poup_changeReason(driver)).build().perform();
	            final List<WebElement> list_reason = (List<WebElement>)driver.findElements(By.xpath("//td[@ng-init='lowerIndex = $index']//div//a"));
	            final int i = 0;
	            if (i < list_reason.size()) {
	                WebElement reason_value = list_reason.get(0);
	                reason_value.click();
	                com.performAction(driver, dropDown_reason(driver), "SET", "ATC", "setting reason as ATC");
	                com.performAction(driver, dropDown_resultOfReason(driver), "Click", "", "Selecting ATC as reason code from list");
	                reason_value = list_reason.get(4);
	                reason_value.click();
	                com.performAction(driver, dropDown_reason(driver), "SET", "ATC", "setting reason as ATC");
	                com.performAction(driver, dropDown_resultOfReason(driver), "Click", "", "Selecting ATC as reason code from list");
	                reason_value = list_reason.get(8);
	                reason_value.click();
	                com.performAction(driver, dropDown_reason(driver), "SET", "ATC", "setting reason as ATC");
	                com.performAction(driver, dropDown_resultOfReason(driver), "Click", "", "Selecting ATC as reason code from list");
	                reason_value = list_reason.get(12);
	                reason_value.click();
	                com.performAction(driver, dropDown_reason(driver), "SET", "ATC", "setting reason as ATC");
	                com.performAction(driver, dropDown_resultOfReason(driver), "Click", "", "Selecting ATC as reason code from list");
	            }
	            com.performAction(driver, btn_ForcePublish1(driver), "click", "", "Swap flight confirmed");
	            Thread.sleep(4000L);
	        }
	        catch (Exception e) {
	            System.out.println("No Change reason Pop-up appeared, so proceed with only publish option");
	        }
	    }*/

	
	/**************************************************************************
	 * Description: Method to swap flights in 2 different aircraft registration
	 **************************************************************************/
	public static void swapFlight(final WebDriver driver) throws Exception {
		final List<WebElement> List = driver.findElements(By.xpath("//ul[@class='context-menu-list context-menu-root']//li//span"));
	    System.out.println(List);
        for (int drp_rightClick = 0; drp_rightClick < List.size(); ++drp_rightClick) {
            final WebElement data = List.get(drp_rightClick);
            if (data.getAttribute("innerHTML").contentEquals("Swap Selected")) {
                data.click();
                break;
            }
            if (data.getAttribute("innerHTML").contentEquals("Rotate Selected")) {
                data.click();
                break;
            }
        }
       Thread.sleep(5000);
       
       
        
        try
        {
        	driver.findElement(By.xpath("//span[text()='Confirmation Message']/../following-sibling::div/following-sibling::div//button[1]")).isDisplayed();
        	com.performAction(driver, btn_YesInSwapConfirmationMsg(driver), "Click", "", "Yes Button in Confirmation Message");
        	Thread.sleep(2000);
        	try {
        		//added code for force publish handling for flight swap on 1st feb,22
        		Actions a=new Actions(driver);   
        		a.moveToElement(IFlightNeo_Gantt.dialog_RuleWarning(driver)).build().perform();
        		IFlightNeo_HomePage.forcepublish(driver);
        		//Actions a5 = new Actions(driver);
    			//a5.moveToElement(poup_changeReason(driver)).build().perform(); 
        		  try
        		  {
        			  final List<WebElement> list_Reason = driver.findElements(By.xpath("//table[@class='table table-bordered table-responsive']/tbody//td[2]"));
        	   	         // Select Global Reason
        	   	         list_Reason.get(0).click();
        	   	         Thread.sleep(1000);
        	   	         // Filter Reason
        	   	         com.performAction(driver, txtBx_Reason(driver), "SET", "ATC", "Reason as ATC");
        	   	         list_ReasonResult(driver).click();
        	   	         // Publish
        	   	         com.performAction(driver, btn_Publish(driver), "Click", "", "Publish Button");
        	   	         Thread.sleep(4000L);
        		  }
        		  
        		  catch (Exception e6)
        		  
        		  {
        			  System.out.println("No change pop-up appeared");
        		  }
            	
            }
            catch (Exception e) {
            	System.out.println("No force-publish window appeared");
            	final List<WebElement> list_Reason = driver.findElements(By.xpath("//table[@class='table table-bordered table-responsive']/tbody//td[2]"));
   	         // Select Global Reason
   	         list_Reason.get(0).click();
   	         Thread.sleep(1000);
   	         // Filter Reason
   	         com.performAction(driver, txtBx_Reason(driver), "SET", "ATC", "Reason as ATC");
   	         list_ReasonResult(driver).click();
   	         // Publish
   	         com.performAction(driver, btn_Publish(driver), "Click", "", "Publish Button");
   	         Thread.sleep(4000L);
            
                
            }
        }
        
        catch(Exception e2)
        {
        	System.out.println("Swap confirmation msg does not appear");
        	try
        	{
        	driver.findElement(By.xpath("//span[text()='Change Reason']")).isDisplayed();
        	
        	try {
        		//Actions a5 = new Actions(driver);
    			//a5.moveToElement(poup_changeReason(driver)).build().perform(); 
            	final List<WebElement> list_Reason = driver.findElements(By.xpath("//table[@class='table table-bordered table-responsive']/tbody//td[2]"));
    	         // Select Global Reason
    	         list_Reason.get(0).click();
    	         Thread.sleep(1000);
    	         // Filter Reason
    	         com.performAction(driver, txtBx_Reason(driver), "SET", "ATC", "Reason as ATC");
    	         list_ReasonResult(driver).click();
    	         // Publish
    	         com.performAction(driver, btn_Publish(driver), "Click", "", "Publish Button");
    	         Thread.sleep(4000L);
                  }
            catch (Exception e12) {
                System.out.println("No Change reason Pop-up appeared, so proceed with only publish option");
              }
        	
        	}
        	
        	catch (Exception e3)
        	
        	{
        		System.out.println("Change reason pop-up does not appear");
        		try {
          			
            	driver.findElement(By.xpath("//button[@ng-click='forcePublish()']")).isDisplayed();
            	
            	 try {
          			
         	         Thread.sleep(1000);
         	         
         	         // Publish
         	         com.performAction(driver, btn_Publish(driver), "Click", "", "Publish Button");
         	         Thread.sleep(4000L);
                 }
                 catch (Exception e4) {
                     System.out.println("No Change reason Pop-up appeared, so proceed with only publish option");
                 }
        	}
        	
        	catch(Exception e1)
        	{
                 System.out.println("Rule warning pop-up does not appear");
            	
            	
        	}
        	}		
        	
        }
        	
        
	

        
        
        
        if (popupappear==1)
         //Confirmation Yes
         {
        	com.performAction(driver, btn_YesInSwapConfirmationMsg(driver), "Click", "", "Yes Button in Confirmation Message");
        	Thread.sleep(2000L);
          // com.performAction(driver, btn_ForcePublish(driver), "Click", "", "Click on force Publish button");
	   try {
			//action.moveToElement(poup_changeReason(driver)).build().perform(); 

        // Confirmation Yes
        com.performAction(driver, btn_YesInSwapConfirmationMsg(driver), "Click", "", "Yes Button in Confirmation Message");
	    Thread.sleep(2000L);
        try {
			// action.moveToElement(poup_changeReason(driver)).build().perform(); 

        	final List<WebElement> list_Reason = driver.findElements(By.xpath("//table[@class='table table-bordered table-responsive']/tbody//td[2]"));
	         // Select Global Reason
	         list_Reason.get(0).click();
	         Thread.sleep(1000);
	         // Filter Reason
	         com.performAction(driver, txtBx_Reason(driver), "SET", "ATC", "Reason as ATC");
	         list_ReasonResult(driver).click();
	         // Publish
	         com.performAction(driver, btn_Publish(driver), "Click", "", "Publish Button");
	         Thread.sleep(4000L);
        }
        catch (Exception e4) {
            System.out.println("No Change reason Pop-up appeared, so proceed with only publish option");
        }
	   }
	            catch(Exception e9)
        {System.out.println("No confirmation message apperas");}
	     }
        
	
         
        if (popupappear==2)
        {
        	 try {
     			//action.moveToElement(poup_changeReason(driver)).build().perform(); 
             	final List<WebElement> list_Reason = (List<WebElement>)driver.findElements(By.xpath("//table[@class='table table-bordered table-responsive']/tbody//td[2]"));
     	         // Select Global Reason
     	         list_Reason.get(0).click();
     	         Thread.sleep(1000);
     	         // Filter Reason
     	         com.performAction(driver, txtBx_Reason(driver), "SET", "ATC", "Reason as ATC");
     	         list_ReasonResult(driver).click();
     	         // Publish
     	         com.performAction(driver, btn_Publish(driver), "Click", "", "Publish Button");
     	         Thread.sleep(4000L);
             }
             catch (Exception e3) {
                 System.out.println("No Change reason Pop-up appeared, so proceed with only publish option");
             }

        } 
        if (popupappear==3)
        {
        	 try {
     			
     	         Thread.sleep(1000);
     	         
     	         // Publish
     	         com.performAction(driver, btn_Publish(driver), "Click", "", "Publish Button");
     	         Thread.sleep(4000L);
             }
             catch (Exception e5) {
                 System.out.println("No Change reason Pop-up appeared, so proceed with only publish option");
             }

        }  
        if (popupappear==4)
        
        	System.out.println("No Pop-up appeared"); 
        
	            
	            
         
        
	
	}

	            
         
	


	 
	 private static WebElement popup_Rulerwarning(WebDriver driver) 
	{
		 WebDriverWait wait = new WebDriverWait(driver, 30);
		 try {
			 element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='forcePublish()']")));
		 }
		 catch(Exception toe) {}
		 return element;

		// TODO Auto-generated method stub
		
	}

	private static WebElement popup_ChangeReason(WebDriver driver) {
		
		return driver.findElement(By.xpath("//span[text()='Change Reason']"));
	}

	public static WebElement dropDown_resultOfReason(WebDriver driver){
			wait=new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='select2-result-label']")));
	        element = driver.findElement(By.xpath("//div[@class='select2-result-label']"));
	        return element;
		}	


	public static WebElement dropDown_reason(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='select2-drop']/div//input")));
        element = driver.findElement(By.xpath("//body/div[@id='select2-drop']/div//input"));
        return element;
	}

	/** Change reason dialog */ 
	// Wenbelement changed , hence xpath modified on 20th jan,22
	public static WebElement dialog_ChangeReason(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@name='ChangePublishReasonForm']")));
	    element = driver.findElement(By.xpath("//form[@name='ChangePublishReasonForm']"));
	    return element;
	}	
	
	/******************************************************************************************************************
	 * Description	: When swap option is selected sometimes it ask for change reasons and then publish button displays
	 * @param 		: driver
	 * @return		: element
	 ******************************************************************************************************************/
	public static WebElement btn_Publish(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Publish')]")));
        element = driver.findElement(By.xpath("//button[contains(text(),'Publish')]"));
        return element;
	}


	 public static WebElement container_swapList(WebDriver driver){
			wait=new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='ui-id-81']")));
	        element = driver.findElement(By.xpath("//span[@id='ui-id-81']"));
	        return element;
		}
	 
	 /*************************************************
	 * Description	: Delay Flight
	 * @param 		: driver
	 * @return		: element
	 **************************************************/
	 public static void delayflight(WebDriver driver) throws InterruptedException
	 {
		 Actions a5 = new Actions(Instance);
		 a5.moveToElement(IFlightNeo_Gantt.link_cancelOption(Instance)).perform();
			
	     com.performAction(driver, IFlightNeo_Gantt.link_cancelFlightOption(Instance), "click", "", "Flight");
	     Instance.findElement(By.xpath("//span[text()='YES']")).click();
	     Thread.sleep(40);
	     Instance.findElement(By.xpath("//span[text()='YES']")).click();
//HANNU next line was not commented!	     
//	     com.performAction(driver, reasonode(Instance), "click", "", "select reason code");
	     Instance.findElement(By.xpath("//div[text()='ATC']")).click();
	     Instance.findElement(By.xpath("//li//button[text()='Publish']")).click();
	 }

	public static void auto_Off_RightClickoptions(WebDriver driver,String tcname) {
		final List<WebElement> List = (List<WebElement>)driver.findElements(By.xpath("//ul[@class='context-menu-list context-menu-root']//li//span"));
        System.out.println(List);
        for (int drp_rightClick = 0; drp_rightClick < List.size(); ++drp_rightClick) {
            final WebElement data = List.get(drp_rightClick);
            if (data.getAttribute("innerHTML").contentEquals("Swap Selected")) {
                data.click();
                break;
            }
            if (data.getAttribute("innerHTML").contentEquals("Rotate Selected")) {
                data.click();
                break;
            }
        }
	}	

	public static void confirmChange(WebDriver driver) throws InterruptedException {
		//wait=new WebDriverWait(driver,30);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@title='Change List']")));
		//driver.findElement(By.xpath("//li[@title='Change List']")).click();
		//wait=new WebDriverWait(driver,100);
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[contains(@id,'changeListGrid')]//td//input[@role='checkbox']")));
		//driver.findElement(By.xpath("//table[contains(@id,'changeListGrid')]//td//input[@role='checkbox']")).click();
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@ng-show='showPublishButton']")));
		//driver.findElement(By.xpath("//button[@ng-show='showPublishButton']")).click();
		Thread.sleep(1000);
		//WebElement popup_publishsim=);
		//WebElement popup_forcepublish=);
		if(driver.findElement(By.xpath("//span[text()='Publish SIM ']")).isDisplayed())
		{
		
			com.performAction(driver, IFlightNeo_HomePage.btn_Yespublishsim(driver), "click", "",
					"Confirm publish changes");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Change Reason']")));
			WebElement reasoncode=driver.findElement(By.xpath("//tbody//td[@ng-repeat='i in mainScheduleReasonCodes track by $index']"));
			reasoncode.click();
			com.performAction(driver, IFlightNeo_HomePage.dropDown_reason(driver), "SET", "ATC",
					"setting reason as ATC");
			com.performAction(driver, IFlightNeo_HomePage.dropDown_resultOfReason(driver), "Click", "",
					"Selecting ATC as reason code from list");
			Thread.sleep(4000);
		    driver.findElement(By.xpath("//button[text()='Publish']")).click();
		}
		//else if(driver.findElement(By.xpath("//button[@ng-click='forcePublish()']")).getSize()!=null)
		//{
			//driver.findElement(By.xpath("//button[@ng-click='forcePublish()']")).click();
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Change Reason']")));
			//WebElement reasoncode=driver.findElement(By.xpath("//tbody//td[@ng-repeat='i in mainScheduleReasonCodes track by $index']"));
			//reasoncode.click();
			//com.performAction(driver, IFlightNeo_HomePage.dropDown_reason(driver), "SET", "ATC",
					//"setting reason as ATC");
			//com.performAction(driver, IFlightNeo_HomePage.dropDown_resultOfReason(driver), "Click", "",
					//"Selecting ATC as reason code from list");
			//Thread.sleep(4000);
		    //driver.findElement(By.xpath("//button[text()='Publish']")).click();
			//driver.findElement(By.xpath("//span[text()='YES']")).click();
			
		//}
		/*else
		{
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='YES']")));
		//driver.findElement(By.xpath("//span[text()='YES']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Change Reason']")));
			List <WebElement> reasoncode=driver.findElements(By.xpath("//tbody//td[@ng-repeat='i in mainScheduleReasonCodes track by $index']"));
			reasoncode.get(reasoncode.size()-15).click();
			com.performAction(driver, IFlightNeo_HomePage.dropDown_reason(driver), "SET", "ATC",
					"setting reason as ATC");
			com.performAction(driver, IFlightNeo_HomePage.dropDown_resultOfReason(driver), "Click", "",
					"Selecting ATC as reason code from list");
			Thread.sleep(4000);
		    driver.findElement(By.xpath("//button[text()='Publish']")).click();
		}*/
		
		//div[@class='ui-dialog-titlebar-buttonpane']//button[@title='Close']
		//Actions a5 = new Actions(driver);
	    //a5.moveToElement(driver.findElements(By.xpath("//div[@class='ui-dialog-titlebar-buttonpane']//button[@title='Close']")).get(3)).click().build().perform();
		//driver.findElements(By.xpath("//div[@class='ui-dialog-titlebar-buttonpane']//button[@title='Close']\r\n" + 
			//	"")).get(1).click();
		Thread.sleep(2000);
		
		
	}

	private static WebElement btn_Yespublishsim(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//span[text()='YES']"));
		
	}

	public static WebElement msg_toolTipOfFlight(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='gantt_tooltip']")));
        element = driver.findElement(By.xpath("//div[@class='gantt_tooltip']"));
        return element;
	}

	public static boolean regularExpression(String toolTip) {
		
		
		if (toolTip.contains("BrokenTrip")||toolTip.contains("Discontinuity"))
		{
			toolTip=toolTip.replaceAll("BrokenTrip,", "").replaceAll("Discontinuity", "");
			
			System.out.println(toolTip);
		}
		
		
		
		String regex= "[0-9]{3}[A-Z]{3}[0-9]{2}[:]{1}[0-9]{2}[-]{1}[0-9]{2}[:]{1}[0-9]{2}[A-Z]{4}[:]{1}[0-9]{1}[/]{1}[0-9]+{1}[/]{1}[0-9]+{1}[A-Z]{1}[:]{1}[0-9]{1}[/]{1}[0-9]{1}[/]{1}[0-9]+{1}";
		Pattern pattern = Pattern.compile(regex);
		Matcher mtch = pattern.matcher(toolTip);
		boolean b=mtch.matches();
		System.out.println(b);
		return b;
	}
      //Restructured the method on 25th Jan,22 to correctly identify if break-trip option available or not
	public static boolean breakTrip(WebDriver driver,String imagePath) throws Exception {
		boolean breaktripavailable;
		IFlightNeo_Gantt.selectFlightInGantt(driver, imagePath, "RIGHTCLICK");
		try {
		Actions a5 = new Actions(driver);
	    a5.moveToElement(breaktrip(driver)).click().build().perform();
         com.performAction(driver, confirm_breaktrip(driver), "Click", "", "Confirm break trip");
         
         htmlLib.logReport("Break the trip", "Trip broken", "INFO", driver, true);
	    breaktripavailable=true;
		}
		catch(Exception e)
		{
			
			breaktripavailable=false;
		}
		// TODO Auto-generated method stub
		return breaktripavailable;
	}

	private static WebElement confirm_breaktrip(WebDriver driver) {
		// TODO Auto-generated method stub
		wait=new WebDriverWait(driver,120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'YES')]")));
		WebElement confirm_breaktrip=driver.findElement(By.xpath("//span[contains(text(),'YES')]"));
		return confirm_breaktrip;
	}

	public static void readAircraftRegforTrip(WebDriver driver) throws InterruptedException {
		registrationNumberTrip(driver);
		
	}

	public static  String registrationNumberTrip(WebDriver driver) throws InterruptedException {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@sp-dialog-show='toShowFlightTripDetailsDialog']")));
		WebElement tripdetailswindow= driver.findElement(By.xpath("//div[@sp-dialog-show='toShowFlightTripDetailsDialog']"));
		 Actions action = new Actions(driver);
		 action.moveToElement(tripdetailswindow).build().perform();
		 
		 JavascriptExecutor javascript = (JavascriptExecutor) driver;
		 String toenable = "document.getElementsByName('flightTripDetailWidgetModel_aircraftRegistration')[0].removeAttribute('disabled');";
		 javascript.executeScript(toenable);
		 Thread.sleep(3000);
		 //Storing the Aircraft registration value of first flight in a string variable
		 String AircraftRegistration = driver.findElement(By.name("flightTripDetailWidgetModel_aircraftRegistration")).getAttribute("value");
		 System.out.println("Aircraft Registration :" + AircraftRegistration);
		 //driver.findElement(By.xpath("//div[@aria-labelledby='ui-id-49']")).sendKeys(Keys.ESCAPE);
		 return AircraftRegistration;
	}

	
	public static WebElement btn_CloseTripDetailsWindow(WebDriver driver) {
		List<WebElement> close_button_TripDetails = driver.findElements(By.xpath("//div[contains(@class,'ui-dialog-titlebar')]//div[contains(@class,'titlebar-buttonpane')]//button[@title='Close']"));
		WebElement close=close_button_TripDetails.get(1);
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(close));
		return close;
		
	}

	public static WebElement btn_findFlight_Failure(WebDriver driver){
		element = null;
		wait =new WebDriverWait(driver, 10);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@title='Find Flight_1']")));
			element = driver.findElement(By.xpath("//li[@title='Find Flight_1']"));
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
		}
        return element;
	}

	public static boolean alertmessage_comparisonforflightntexits(WebDriver driver) {
		// TODO Auto-generated method stub
		 boolean error_Exists=alert_Flightnotfound(driver).equals(error1msgto_Compare(driver));
		return error_Exists;
		 
		
	}
	
	public static void selectFIDSscreen(WebDriver driver) {
        com.performAction(driver, mainMenu_Hub(driver), "CLICK", "", "Hub Option from Main Menu");
        List<WebElement> nav_dropdown=driver.findElements(By.xpath("//ul[@id='nav']//li//ul//li//a"));
        for(int iterator=0; iterator<nav_dropdown.size(); iterator++)
        {
            WebElement element = nav_dropdown.get(iterator);
            String subHeader=element.getAttribute("innerHTML"); 
            if(subHeader.contains("Guest")) {
                com.performAction(driver, subMenu_GuestConnection(driver), "CLICK", "", "Guest Connection option");
                com.performAction(driver, subMenu_GuestConnectionFIDS(driver), "CLICK", "", "Guest Connection FIDS option");
                
            }
        }
        wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.elementToBeClickable(Fids_filter(driver)));
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
        com.performAction(driver, Fids_filter(driver), "CLICK", "", "Guest Connection option");
       
	}



	public static WebElement Fids_filter(WebDriver driver) {
		 WebElement filter = driver.findElements(By.xpath("//div[@class='row']//i[@ng-click='FilterShowAndHide()']")).get(1);
		return filter;
	}


public static WebElement subMenu_GuestConnection(WebDriver driver) {
		element = driver.findElement(By.xpath("//ul[@id='nav']//li//ul//li//a[@id='menu_guestConnection']"));
		return element;
	}
	
	public static WebElement subMenu_GuestConnectionFIDS(WebDriver driver) {
		element = driver.findElement(By.xpath("//ul[@id='nav']//li//ul//li//a[@id='menu_fids']"));
		return element;
	}







public static WebElement mainMenu_Hub(WebDriver driver) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='menu_hub']")));
		element = driver.findElement(By.xpath("//a[@id='menu_hub']"));
		return element;
	}


	public static boolean alertmessage_comparisonforflightntexitsontheday(WebDriver driver) {
		// TODO Auto-generated method stub
		 boolean error_Exists=alert_flightnotfoundonsameday(driver).equals(error2msgto_Compare(driver));
		return error_Exists;
		 
		
	}
	 private static WebElement error2msgto_Compare(WebDriver driver) {
		 element= driver.findElement(By.xpath("//span[text()='Flight does not exist on the selected date.']"));
		return element;
	}

	public static WebElement error1msgto_Compare(WebDriver driver)
	 
	 {
		element= driver.findElement(By.xpath("//span[text()='Flight does not exist within the filter criteria.']"));
		return element;
		 
	 }

	public static String AfterreadAircraftReg(WebDriver driver) throws InterruptedException {
		//Waiting for flight details to open
		        Thread.sleep(1000);
				WebElement FlightDetails = driver.findElement(By.xpath("//span[contains(text(),'-Flight Leg Details')]"));
				//WebElement flightDetail=FlightDetails.get(FlightDetails.size()-1);
				 wait=new WebDriverWait(driver, 60);
				 wait.until(ExpectedConditions.visibilityOf(FlightDetails));
				 WebElement element2 = driver.findElement(By.xpath("//form[contains(@name,'flightLegStatusForm')]"));
				 //htmlLib.logReport("flight Details Screen", "Flight Details Screen Open success", "Pass", true);
				 Actions action = new Actions(driver);
				 action.moveToElement(element2).build().perform();

					 // To enable AircraftRegistrationfield text box
					 JavascriptExecutor javascript = (JavascriptExecutor) driver;
					 String toenable = "document.getElementsByName('flightLegDetailsWidgetModel_aircraftReg')[0].removeAttribute('disabled');";
					 javascript.executeScript(toenable);
					 Thread.sleep(3000);
					 //Storing the Aircraft registration value of first flight in a string variable
					 String AircraftRegistration_After = driver.findElement(By.name("flightLegDetailsWidgetModel_aircraftReg")).getAttribute("value");
					 System.out.println("Aircraft Registration :" + AircraftRegistration_After);
					 //driver.findElement(By.xpath("//div[@aria-labelledby='ui-id-49']")).sendKeys(Keys.ESCAPE);
					 return AircraftRegistration_After;
	}

	public static WebElement btn_CloseFlightDetailsWindow2ndsearch(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		List<WebElement> Allclose_button = driver.findElements(By.xpath("//div[contains(@class,'ui-dialog-titlebar')]//button[@title='Close']"));
		//The position of the close button changed to 4th index hence the below count changed to 80 on 1st feb,22
		WebElement close_button=Allclose_button.get(Allclose_button.size()-80);
		wait.until(ExpectedConditions.visibilityOf(close_button));
		return close_button;
	}

	/**
	 * this is to open the Manage Filter main menu item
	 * 
	 * @param driver this is the parameter to control the web browser, which executes the test case
	 */
	public static void menuItem_ManageFilter(WebDriver driver) {
		WebElement additionalMenuOption = menu_AdditionalMenuOption(driver);
		if(additionalMenuOption != null)
			additionalMenuOption.click();
		
		Actions mousemovement = new Actions(driver);
		mousemovement.moveToElement(mainMenu_Filter(driver)).perform();;
		mousemovement.moveToElement(subMenu_ManageFilter(driver)).click().perform();;
	}
	
	/** Additional Menu Option (3 horizontal bars) in Global Menu Bar */
	// 3 horizontal bars removed from front end in Aug,21 build instead Filter option has to be clicked hence chaging the code
	public static WebElement menu_AdditionalMenuOption(WebDriver driver) {
		try {
			wait = new WebDriverWait(driver, 5);
			//Locator changed before upgrade
			locator = By.xpath("//div[@class='menu_option_button dropdown-toggle']");
			//Locator changed after upgrade
			locator = By.xpath("//div[@class='menu_option_button ']");
			//new locator after upgrade - THIS IS INCORRECT. we already search for this filter in mainMenu_Filter
			//locator = By.xpath("//a[@id='menu_filter']");
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			element = driver.findElement(locator);
		} catch (Exception e) {
			return null;
		}
		return element;
	}
	
	/** Menu Option Filter in Global Menu bar */
	public static WebElement mainMenu_Filter(WebDriver driver) {
		try {
			Thread.sleep(1000);
		}
		catch(Exception e) {}
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='menu_filter']")));
		element = driver.findElement(By.xpath("//a[@id='menu_filter']"));
		return element;
	}

	/** Sub-menu Option Manage Filter inside Filter Menu */
	public static WebElement subMenu_ManageFilter(WebDriver driver) {		
		wait = new WebDriverWait(driver,30);
		locator = By.xpath("//a[@id='menu_manageFilter']");
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		element = driver.findElement(locator);
		return element;
	}
	
	/** Menu Option Filter in Global Menu bar */
	public static WebElement mainMenu_Admin(WebDriver driver) {
		try {
			Thread.sleep(1000);
		}
		catch(Exception e) {}
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("menu_admin")));
		element = driver.findElement(By.id("menu_admin"));
		return element;
	}

	/** Sub-menu Option Manage Filter inside Filter Menu */
	public static WebElement subMenu_ManageUsers(WebDriver driver) {		
		wait = new WebDriverWait(driver,30);
		locator = By.id("menu_manageUsers");
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		element = driver.findElement(locator);
		return element;
	}
	
	// The webelement position in the list changed in 2nd Feb release , hence changed that on 4th Feb,22 & created this new method
	public static WebElement btn_CloseFlightmessagelist(WebDriver driver) {
		// TODO Auto-generated method stub
		wait = new WebDriverWait(driver,120);
		List<WebElement> Allclose_button = driver.findElements(By.xpath("//div[contains(@class,'ui-dialog-titlebar')]//button[@title='Close']"));
	    WebElement close_button=Allclose_button.get(Allclose_button.size()-82);
		wait.until(ExpectedConditions.visibilityOf(close_button));
		return close_button;
	}

	/*
	 * This method is to switch user role
	 * @author Prakritesh Saha
	 * @param driver, roleToSwitch
	 */
	public static void switchUserRole(WebDriver driver, String roleToSwitch) throws InterruptedException {
		if(!getUserRole(driver).contains(roleToSwitch)) {
			// User box section
			div_UserBox(driver).click();
			//click switch role link
			com.performAction(driver, link_SwitchRole(driver), "CLICK", "", "Switch Role Link");
			//select role from switch role popup
			com.performAction(driver, dropdownExpandSelectRole(driver), "click", "", "Expand dropdown arrow");
			com.performAction(driver, selectRole(driver, roleToSwitch), "click", "", "User Role "+roleToSwitch);
			Thread.sleep(1000);
			//click apply button
			com.performAction(driver, button_ApplyInSwithRole(driver), "click", "", "Apply Button");
			//click ok
			com.performAction(driver, button_OK(driver), "click", "", "OK button");
			//Verify if the user role is changed
			if(getUserRole(driver).contains(roleToSwitch)) {
				htmlLib.logReport("Switch Role to OPS_Controller", "User role is switched to "+roleToSwitch, "PASS", driver, true);
			}
			else {
				htmlLib.logReport("Switch Role to OPS_Controller", "User role is not switched to "+roleToSwitch, "FAIL", driver, true);

			}
		}
		else {
			htmlLib.logReport("User Role is OPS_Controller", null, "INFO", driver, true);
		}
	}

	public static void openAdminManageUsers(WebDriver driver) {

		WebElement additionalMenuOption = menu_AdditionalMenuOption(driver);
		if(additionalMenuOption != null)
			additionalMenuOption.click();
		
		Actions mousemovement = new Actions(driver);
		mousemovement.moveToElement(mainMenu_Admin(driver)).perform();;
		mousemovement.moveToElement(subMenu_ManageUsers(driver)).click().perform();;
	
		
	}

	public static void verifyRolesNotPresent(WebDriver driver, List<String> userRole) {

		// User box section
		wait= new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.elementToBeClickable(div_UserBox(driver)));
		div_UserBox(driver).click();
		//click switch role link
		com.performAction(driver, link_SwitchRole(driver), "CLICK", "", "Switch Role Link");
		//select role from switch role popup
		com.performAction(driver, dropdownExpandSelectRole(driver), "click", "", "Expand dropdown arrow");
		//Validate if roles are available in the list or not
		Boolean roleExists = roleList(driver).stream().map(role->role.findElement(By.xpath("//div")).getText()).anyMatch(role->userRole.contains(role));
		if(roleExists) {
			htmlLib.logReport("Verify that as the roles are removed now those are not available in the Assign Role dropdown", userRole+" Roles are available", "FAIL", driver, true);
		}	
		else {
			htmlLib.logReport("Verify that as the roles are removed now those are not available in the Assign Role dropdown", userRole+" Roles are not available", "PASS", driver, true);
		}
	}

	
}








