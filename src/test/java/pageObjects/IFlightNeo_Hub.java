package pageObjects;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import utilities.CommonLibrary;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class IFlightNeo_Hub {
	public static CommonLibrary comm = new CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	static String depGatevalue;

		
		public static WebElement filterButton(WebDriver driver) {
			
			WebElement Filter=driver.findElement(By.xpath("//li//button[contains(text(),'Filter')]"));
			
			return Filter;
	       
			
			
		}
        
		public static void filterFlightconnection(WebDriver driver) {
        
		comm.performAction(driver, filterButton(driver), "click", "", "click on filter");

		}

		private static WebElement loadFidsscreen(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElements(By.xpath("//span[contains(text(),'EY')]")).get(0);
		}

		public static void updateStandGateflight(WebDriver driver, String arrivalFlight,String departureFlight,String Arrterminal,String Arrgate,String Arrstand,String Depterminal,String Depgate,String Depstand) throws InterruptedException {
			comm.performAction(driver, ClickonFlightlink(driver,arrivalFlight), "click", "", "click on arrival flight");
			flightLegdetails(driver);
			UpdateArrStandGateTerminal(driver,Arrterminal,Arrgate,Arrstand);
            comm.performAction(driver, ClickonFlightlink(driver,departureFlight), "click", "", "click on departure flight");
			flightLegdetails(driver);
			UpdateDepStandGateTerminal(driver,Depterminal,Depgate,Depstand);
            WaittillDepvalueload(driver);

			
		}

		public static void WaittillDepvalueload(WebDriver driver) throws InterruptedException {
			
			//depGatevalue=driver.findElement(By.xpath("//table[@id='guestconnectionDetailsGrid_W1']//tr[2]/td[26]")).getAttribute("oldtitle");
			//System.out.println("depGatevalue:"+ depGatevalue);
			/*while (depGatevalue.isEmpty())
			{   
				
				
				if(depGatevalue.equalsIgnoreCase("10"))
				{
					System.out.println("depGatevalue");

					break;
				}
		    }*/
				/*wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='guestconnectionDetailsGrid_W1']//tr[2]/td[@oldtitle='10']")));
			*/
			Thread.sleep(10000);
			
				
			
		}

		public static void flightLegdetails(WebDriver driver) {
			
			wait = new WebDriverWait(driver,60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@sp-dialog='flightLegDetailsDialogOptions']")));
		}

		public static WebElement ClickonFlightlink(WebDriver driver, String Flight) {
			return driver.findElement(By.xpath("//tr//a[contains(text(),'" + Flight + "')]"));
			
		}

		

		public static void UpdateArrStandGateTerminal(WebDriver driver,String Arrterminal,String Arrgate,String Arrstand) {
			comm.performAction(driver, loadAirportInfoTab(driver), "click", "", "click on airport info sub tab");
			comm.performAction(driver,updateArrAirTerminal(driver),"SET",Arrterminal+Keys.TAB,"Set Arrival Terminal ");
			comm.performAction(driver,updateArrPassTerminal(driver),"SET",Arrterminal+Keys.TAB,"Set Arrival Terminal ");
			comm.performAction(driver,updateArrGate(driver),"SET",Arrgate+Keys.TAB,"Set Arrival Gate ");
			comm.performAction(driver,updateArrStand(driver),"SET",Arrstand+Keys.TAB,"Set Arrival Stand ");
			comm.performAction(driver, updatebutton(driver), "click","", "Click on Update button");
			comm.performAction(driver, btn_CloseFlightDetailsWindow(driver), "click","", "Click on Flight leg details close button");
		}

		private static WebElement updatebutton(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//button[contains(text(),'Update')]"));
		}

		public static void UpdateDepStandGateTerminal(WebDriver driver,String Depterminal,String Depgate,String Depstand) {
			comm.performAction(driver, loadAirportInfoTab(driver), "click", "", "click on airport info sub tab");
			comm.performAction(driver,updateDepAirTerminal(driver),"SET",Depterminal+Keys.TAB,"Set Departure Terminal ");
			comm.performAction(driver,updateDepPassTerminal(driver),"SET",Depterminal+Keys.TAB,"Set Departure Terminal ");
			comm.performAction(driver,updateDepGate(driver),"SET",Depgate+Keys.TAB,"Set Departure Gate ");
			comm.performAction(driver,updateDepStand(driver),"SET",Depstand+Keys.TAB,"Set Departure Stand ");
			comm.performAction(driver, updatebutton(driver), "click","", "Click on Update button");
			comm.performAction(driver, btn_CloseFlightDetailsWindow(driver), "click","", "Click on Flight leg details close button");
		}

	
		private static WebElement updateDepStand(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_deptStand']"));
		}

		private static WebElement updateDepGate(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_deptGate']"));
		}

		private static WebElement updateDepPassTerminal(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_passengerDeptTerminal']"));
		}

		private static WebElement updateDepAirTerminal(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_aircraftDeptTerminal']"));
		}

		private static WebElement updateArrStand(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_arrStand']"));
		}

		private static WebElement updateArrGate(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_arrGate']"));
		}

		private static WebElement updateArrPassTerminal(WebDriver driver) {
			// TODO Auto-generated method stub
			 return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_passengerArrTerminal']"));
		}

		private static WebElement updateArrAirTerminal(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='flightLegDetailsWidgetModel_aircraftArrTerminal']"));
		}

		public static WebElement loadAirportInfoTab(WebDriver driver) {
			return driver.findElement(By.xpath("//li[@select='loadAirportInfoTab()']"));
			

			
		}
		
		

		public static void FilterConnection(WebDriver driver, String arrivalFlight, String departureFlight,String Fromhours,String Tohours) throws InterruptedException {
			
			comm.performAction(driver, Duration_From(driver), "SET", Fromhours + Keys.TAB, "Set From hours");
			comm.performAction(driver, Duration_To(driver), "SET", Tohours + Keys.TAB, "Set To hours");
			Thread.sleep(500);
			/*comm.performAction(driver, Arrccode(driver), "click", "", "Click on Arr code");
			comm.performAction(driver, Txtbx_Arrccode(driver), "", "", "SET Arr code");
			comm.performAction(driver, Depccode(driver), "click", "", "Click on Arr code");
			comm.performAction(driver, SetDepccode(driver), "SET", "EY"+Keys.ENTER, "SET Dep code");*/
			comm.performAction(driver, ArrivalFlight(driver), "SET", arrivalFlight + Keys.TAB, "Set Arrival Flight");
			comm.performAction(driver, DepartureFlight(driver), "SET",departureFlight + Keys.TAB, "Set Departure Flight");
            comm.performAction(driver, filterButton(driver), "click", "", "click on filter");
    		Thread.sleep(10000);

           
		}
		
		
		private static WebElement SetDepccode(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElements(By.xpath("//div[@class='select2-search']")).get(7);
		}

		private static WebElement Depccode(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElements(By.xpath("//div[contains(@id,'ccode')]")).get(1);

		}

		private static WebElement SetArrccode(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElements(By.xpath("//div[@class='select2-search']")).get(7);
		}

		private static WebElement Arrccode(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElements(By.xpath("//div[contains(@id,'ccode')]")).get(0);
		}

		public static WebElement Duration_To(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='guestconnectionfidsWidgetModel_toHours ']"));
		}

		public static WebElement Duration_From(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//input[@name='guestconnectionfidsWidgetModel_fromHours ']"));
		}

		
		public static WebElement DepartureFlight(WebDriver driver) {
			return driver.findElement(By.xpath("//input[@id='createdepfId_W1_fltNo']"));
			
		}

		public static WebElement ArrivalFlight(WebDriver driver) 
		{
			return driver.findElement(By.xpath("//input[@id='createarrfId_W1_fltNo']"));
			 
		}
		
		public static WebElement btn_CloseFlightDetailsWindow(WebDriver driver) {
			
			List<WebElement> close_button = driver.findElements(By.xpath("//div[contains(@class,'ui-dialog-titlebar')]//div[contains(@class,'ui-dialog-titlebar')]//button[@title='Close']"));
			WebElement close=close_button.get(0);
			wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(close));
			return close;
		}

		public String GetRTTvalue(WebDriver driver) {
			String Rttvalue=driver.findElement(By.xpath("//td[@iflight-tooltip='RTTTooltipOptions']")).getAttribute("title");
			return Rttvalue;
		}

		public static void calculateRTTfromreference(WebDriver driver) {
			referenceTransittimedetails(driver);
			busandterminalTransittimevalue(driver).click();
			wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='btn_wrap']//button[contains(@class,'ui-state-hover')]")));

			driver.findElement(By.xpath("//div[@class='btn_wrap']//button[contains(@class,'ui-state-hover')]")).click();
			
			
		}
	

		private static WebElement busandterminalTransittimevalue(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//div[contains(@id,'inboundGateName')]"));
		}

		private static void referenceTransittimedetails(WebDriver driver) {
			comm.performAction(driver, mainMenu_Ref(driver), "CLICK", "", "Reference Option from Main Menu");
	        List<WebElement> nav_dropdown=driver.findElements(By.xpath("//ul[@id='nav']//li//ul//li//a"));
	        for(int iterator=0; iterator<nav_dropdown.size(); iterator++)
	        {
	            WebElement element = nav_dropdown.get(iterator);
	            String subHeader=element.getAttribute("innerHTML"); 
	            if(subHeader.contains("Hub")) {
	                comm.performAction(driver, subMenu_Hub(driver), "CLICK", "", "Hub Reference menu");
	                comm.performAction(driver, subMenu_transitTimedetails(driver), "CLICK", "", "Transit time details");
	                
	            }
	        
			
		}

		
}

		private static WebElement subMenu_transitTimedetails(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//ul[@id='nav']//li//ul//li//a[text()='Transit Time Details']"));
		}

		private static WebElement subMenu_Hub(WebDriver driver) {
			// TODO Auto-generated method stub
			return driver.findElement(By.xpath("//ul[@id='nav']//li//ul//li//a[text()='Hub']"));
		}

		private static WebElement mainMenu_Ref(WebDriver driver) {
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Ref')]")));
			element = driver.findElement(By.xpath("//a[contains(text(),'Ref')]"));
			return element;
			
		}

		}