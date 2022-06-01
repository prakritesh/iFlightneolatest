package pageObjects;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;

import utilities.CommonLibrary;

/**
 * class to handle the Edit Flight screen
 * 
 * 
 * @author EYHGoiss
 *
 */
public class IFlightNeo_EditFlight {

	public static CommonLibrary comm = new CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	static int rows;
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;
	
	/**
	 * adding a flight number to the search filter in the edit flight screen
	 * 
	 * @param driver
	 * @param flightNumber
	 * @throws InterruptedException 
	 */
	public static void addFlightNumber(WebDriver driver, String flightNumber, int tabnumber) throws InterruptedException {
		
	
		// click on Flight Range

		Actions a= new Actions(driver);
		a.moveToElement(txtBx_FlightRange(driver)).click().perform();

		//comm.performAction(driver,txtBx_FlightRange(driver), "CLICK", "flight range", "flight range");


		// select from in Flight Range and fill it with the flightNumber
		comm.performAction(driver,txtBx_FlightNumberFrom(driver, tabnumber), "SET", flightNumber, "setting filter for from-value of flightNumber");
		
		// select to in Flight Range and fill it with the flightNumber
		comm.performAction(driver,txtBx_FlightNumberTo(driver, tabnumber), "SET", flightNumber, "setting filter for to-value of flightNumber");
		
		// click on Add
		comm.performAction(driver,btn_add(driver, tabnumber), "CLICK", "click on Add button", "click on Add button");
	}
	
	/**
	 * clicking on the search button to search for a flight
	 * 
	 * @param driver
	 */
	public static void searchFlight(WebDriver driver) {
		// click on Search
		comm.performAction(driver,btn_search(driver), "CLICK", "click on Search button", "click on Search button");
	}

	/**
	 * scroll and edit a flight in the search result grid
	 * 
	 * @param driver
	 */
	public static void scrollAndEdit(WebDriver driver, int tabnumber) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement Element = driver.findElement(By.xpath("//td[@aria-describedby='EditFlightGrid_W"+tabnumber+"_GridActions']"));
		js.executeScript("arguments[0].scrollIntoView();", Element);
		Element.click();
	
		// click on the button
		comm.performAction(driver,btn_dropdown(driver, tabnumber), "CLICK", "click on DROPDOWN button", "click on DROPDOWN button");
		
		try {
			Thread.sleep(2000);
		}
		catch (Exception e) {
			
		}
		
		// click on the EDIT button
		comm.performAction(driver,btn_edit(driver, tabnumber), "CLICK", "click on EDIT button", "click on EDIT button");
		
	}

	/**
	 * change the STD in the result grid
	 * 
	 * @param driver
	 */
	public static void changeSTD(WebDriver driver) {
		// click on STD
		comm.performAction(driver,grid_STD(driver), "CLICK", "click on STD", "STD");
		
		String date = new SimpleDateFormat("dd-MMMM-yyyy HH:mm").format(new Date());
		
		grid_STD(driver).clear();
		grid_STD(driver).sendKeys(date + Keys.ENTER);
		
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.mouseMove(500, 500);
        scn.click();
		try {
			Thread.sleep(1000);
		}
		catch (Exception e) {
			
		}
	}
	
	/**
	 * change the STD in the result grid
	 * 
	 * @param driver
	 * @throws InterruptedException 
	 */
	public static void changeSTA(WebDriver driver,String STA) throws InterruptedException {
		
		
		//String date = new SimpleDateFormat("dd-MMMM-yyyy HH:mm").format(new Date());
		
		grid_STA(driver).clear();
		comm.performAction(driver, grid_STA(driver), "SET", STA+Keys.ENTER, "Set STA");
		//introducing waitime so that force publish pop-up appears
		Thread.sleep(8000);
		grid_STA(driver).sendKeys(Keys.TAB);
		//grid_ETA(driver).clear();
		//comm.performAction(driver, grid_ETA(driver), "SET", STA+Keys.ENTER, "Set ETA");
		grid_ETA(driver).sendKeys(Keys.TAB);
		
		//grid_STA(driver).sendKeys(STA + Keys.ENTER);
		
        // we have to move the mouse, if not the menu doesn't disappear!
		
		driver.findElement(By.xpath("//span[contains(text(),'Flight Management - Edit Flight ')]")).click();
		//wait for sometime so that unexpected error not thrown
		Thread.sleep(3000);
        /*Screen scn = new Screen();
       
        scn.click();*/
		try {
			Thread.sleep(1000);
		}
		catch (Exception e) {
			
		}
	}
	
	private static WebElement grid_ETA(WebDriver driver) {
		// TODO Auto-generated method stub
		return driver.findElement(By.xpath("//input[@name='estimatedOnBlocksDateTime']"));
	}

	/**
	 * change the ETD in the result grid
	 * 
	 * @param driver
	 */
	public static void changeETD(WebDriver driver) {
		// click on ETD
		comm.performAction(driver,grid_ETD(driver), "CLICK", "click on ETD", "ETD");
		
		String date = new SimpleDateFormat("dd-MMMM-yyyy HH:mm").format(new Date());
		
		grid_ETD(driver).clear();
		grid_ETD(driver).sendKeys(date + Keys.ESCAPE);
		
        // we have to move the mouse, if not the menu doesn't disappear!
        Screen scn = new Screen();
        scn.mouseMove(500, 500);
        scn.click();
		try {
			Thread.sleep(1000);
		}
		catch (Exception e) {
			
		}
        htmlLib.logReport("after updating the ETD", "after updating the ETD", "INFO", driver, true);
	}

	/**
	 * change the aircraft registration in the search result grid
	 * 
	 * @param driver
	 * @param registrationNumber
	 * @throws InterruptedException 
	 */
	public static void changeACReg(WebDriver driver, String registrationNumber, int tabnumber) throws InterruptedException {

		Thread.sleep(5000);

		// click on Aircraft Reg
		comm.performAction(driver,grid_ACReg(driver, tabnumber), "CLICK", "click on aircraft reg", "click on aircraft reg");
		
		Thread.sleep(5000);
		
		// now select the new Aircraft Registration
		comm.performAction(driver,dropdown_flightNumber(driver, registrationNumber), "CLICK", "select the aircraft reg", "select the aircraft reg");				
	}
	
	/**
	 * remove the aircraft registration in the search result grid
	 * 
	 * @param driver
	 * @param registrationNumber
	 * @throws InterruptedException 
	 */
	public static void removeACReg(WebDriver driver, int tabnumber) throws InterruptedException {
		// click on Aircraft Reg
		comm.performAction(driver,grid_ACReg(driver, tabnumber), "CLICK", "click on aircraft reg", "click on aircraft reg");
		
		Thread.sleep(5000);
		
		// now select the new Aircraft Registration
		comm.performAction(driver,dropdown_emptyField(driver), "CLICK", "remove the aircraft reg", "remove the aircraft reg");				
	}

	/**
	 * change the ACS in the search result grid
	 * 
	 * @param driver
	 */
	public static void changeACS(WebDriver driver, int tabnumber) {
		// click on Aircraft Reg
		comm.performAction(driver,grid_ACS(driver, tabnumber), "CLICK", "click on ACS", "click on ACS");
		
		// now select the new Aircraft Registration
		comm.performAction(driver,dropdown_ACS(driver), "CLICK", "select the ACS", "select the ACS");				
	}

	/**
	 * change the ACV in the search result grid
	 * 
	 * @param driver
	 */
	public static void changeACV(WebDriver driver, int tabnumber) {
		// click on Aircraft Reg
		comm.performAction(driver,grid_ACV(driver, tabnumber), "CLICK", "click on ACS", "click on ACS");
		
		// now select the new Aircraft Registration
		comm.performAction(driver,dropdown_ACV(driver), "CLICK", "select the ACS", "select the ACS");				
	}

	/**
	 * click on save changes to save the changes made in the search result grid, if not publish is required
	 * 
	 * @param driver
	 */
	public static void saveChanges(WebDriver driver) {
		// click the SAVE button
		comm.performAction(driver,btn_save(driver), "CLICK", "click on SAVE button", "click on SAVE button");
		
		comm.performAction(driver,btn_confirmSave(driver), "CLICK", "click on OK button", "click on OK button");
		
	}

	/**
	 * click on save changes to save the changes made in the search result grid, if a publish is required
	 * 
	 * @param driver
	 */
	public static void saveChangesSTD(WebDriver driver) {
		// click the SAVE button
		comm.performAction(driver,btn_save(driver), "CLICK", "click on SAVE button", "click on SAVE button");
	
		// click on dropdown to select a reason
		comm.performAction(driver, dropdown_reasonSTD(driver), "CLICK", "click on reason dropdown", "click on reason dropdown");
		
		// select ATC from dropdown
		comm.performAction(driver, dropdown_ATC(driver), "CLICK", "select ATC", "select ATC");
		
		// click on PUBLISH button
		comm.performAction(driver, btn_publish(driver), "CLICK", "click on publish button", "click on publish button");
		

		// click on OK button to confirm
		comm.performAction(driver,btn_confirmSave(driver), "CLICK", "click on OK button", "click on OK button");
	}
	
	/**
	 * click on save changes to save the changes made in the search result grid, if a publish & forcepublish is required
	 * 
	 * @param driver
	 */
	public static void saveChangesSTAforgrndtmviolation(WebDriver driver) {
		// click the SAVE button
		comm.performAction(driver,btn_save(driver), "CLICK", "click on SAVE button", "click on SAVE button");
		
		// Click on Force Publish
		try {
		comm.performAction(driver,IFlightNeo_Gantt.btn_ForcePublish(driver) ,"Click", "", "Clicked on force publish");
		}
		
		catch(Exception e)
		{}
	
		// click on dropdown to select a reason
		comm.performAction(driver, dropdown_reasonSTD(driver), "CLICK", "click on reason dropdown", "click on reason dropdown");
		
		// select ATC from dropdown
		comm.performAction(driver, dropdown_ATC(driver), "CLICK", "select ATC", "select ATC");
		
		// click on PUBLISH button
		comm.performAction(driver, btn_publish(driver), "CLICK", "click on publish button", "click on publish button");
		

		// click on OK button to confirm
		comm.performAction(driver,btn_confirmSave(driver), "CLICK", "click on OK button", "click on OK button");
	}


	/**
	 * click on save changes to save the changes made in the search result grid, if a publish is required
	 * 
	 * @param driver
	 */
	public static void saveChangesETD(WebDriver driver) {
		// click the SAVE button
		comm.performAction(driver,btn_save(driver), "CLICK", "click on SAVE button", "click on SAVE button");
	
		// click on dropdown to select a reason
		comm.performAction(driver, dropdown_reasonETD(driver), "CLICK", "click on reason dropdown", "click on reason dropdown");

		// select the textbox to type the reason
		comm.performAction(driver, txtBx_reasonETD(driver), "SET", "XXX", "type the reason for the time change");
		
		// select ATC from dropdown
		comm.performAction(driver, dropdown_XXX(driver), "CLICK", "select ATC", "select ATC");
		
		// click on PUBLISH button
		comm.performAction(driver, btn_publish(driver), "CLICK", "click on publish button", "click on publish button");

		// click on OK button to confirm
		comm.performAction(driver,btn_confirmSave(driver), "CLICK", "click on OK button", "click on OK button");
	}

	/**
	 * click on save changes to save the changes made in the search result grid, if a publish is required
	 * 
	 * @param driver
	 */
	public static void saveChangesACRemoval(WebDriver driver) {
		// click the SAVE button
		comm.performAction(driver,btn_save(driver), "CLICK", "click on SAVE button", "click on SAVE button");
/*	
		// click on dropdown to select a reason
		comm.performAction(driver, dropdown_reasonETD(driver), "CLICK", "click on reason dropdown", "click on reason dropdown");

		// select the textbox to type the reason
		comm.performAction(driver, txtBx_reasonETD(driver), "SET", "XXX", "type the reason for the time change");
		
		// select ATC from dropdown
		comm.performAction(driver, dropdown_XXX(driver), "CLICK", "select ATC", "select ATC");
		
		// click on PUBLISH button
		comm.performAction(driver, btn_publish(driver), "CLICK", "click on publish button", "click on publish button");

		// click on OK button to confirm
		comm.performAction(driver,btn_confirmSave(driver), "CLICK", "click on OK button", "click on OK button");
*/
	}

	
	
	/**
	 * click on the dropdown for the publish reason for changing STD
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement dropdown_reasonSTD(WebDriver driver) {
		String xpath = "(//th[contains(text(),'RC1')])[1]/../../../tbody/tr[1]/td[2]/div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the dropdown for the publish reason for changing ETD
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement dropdown_reasonETD(WebDriver driver) {
		String xpath = "(//th[contains(text(),'RC1')])[2]/../../../tbody/tr[1]/td[2]/div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the textbox for the publish reason for changing ETD
	 *  
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_reasonETD(WebDriver driver) {
		String xpath = "(//input[@class='select2-input select2-focused'])[last()]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select ATC from the dropdown for the publish reason
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement dropdown_ATC(WebDriver driver) {
		String xpath = "//div[text()='ATC']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * select XXX from the dropdown for the publish reason
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement dropdown_XXX(WebDriver driver) {
		String xpath = "//div[text()='XXX']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the publish button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_publish(WebDriver driver) {
		String xpath = "//button[@ng-click='publish()']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get AC Registration from the grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement grid_ACReg(WebDriver driver, int tabnumber) {
		String xpath = "//td[@aria-describedby='EditFlightGrid_W"+tabnumber+"_aircraftRegistration']/div/a";
		
		System.err.println("------------------");
		System.err.println("xpath: "+ xpath);
		System.err.println("------------------");
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get AC Subtype from the grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement grid_ACS(WebDriver driver, int tabnumber) {
		String xpath = "//td[@aria-describedby='EditFlightGrid_W"+tabnumber+"_aircraftSubtype']/div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * get AC Version from the grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement grid_ACV(WebDriver driver, int tabnumber) {
		String xpath = "//td[@aria-describedby='EditFlightGrid_W"+tabnumber+"_aircraftVersion']/div/a";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * get STD from the grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement grid_STD(WebDriver driver) {
		String xpath = "//input[@name='scheduledDepartureDateTime']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * get STA from the grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement grid_STA(WebDriver driver) {
		String xpath = "//input[@name='scheduledArrivalDateTime']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}


	/**
	 * get ETD from the grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement grid_ETD(WebDriver driver) {
		String xpath = "//input[@name='estimatedOffBlocksDateTime']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the dropdown element for the registration number
	 * 
	 * @param driver
	 * @param registrationNumber
	 * @return
	 */
	public static WebElement dropdown_flightNumber(WebDriver driver, String registrationNumber) {
		String xpath = "//div[contains(text(), '" + registrationNumber + "')]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * get the dropdown element for the empty field in the ac registration dropdown
	 * 
	 * @param driver
	 * @param registrationNumber
	 * @return
	 */
	public static WebElement dropdown_emptyField(WebDriver driver) {
		String xpath = "(//div[@class='select2-result-label'])[1]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the dropdown element for the ACS
	 * 
	 * @param driver
	 * @param registrationNumber
	 * @return
	 */
	public static WebElement dropdown_ACS(WebDriver driver) {
		String xpath = "//div[contains(text(), '33C')]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the dropdown element for the ACV
	 * 
	 * @param driver
	 * @param registrationNumber
	 * @return
	 */
	public static WebElement dropdown_ACV(WebDriver driver) {
		String xpath = "(//div[contains(text(), 'P')])/../../li[2]/div";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the text box for the flight range in the search filter
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_FlightRange(WebDriver driver) {
		String xpath = "//label[text()='Flight Range']/../div/iflight-tags-input/div";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * get the text box for the "flight number from" in the search filter
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_FlightNumberFrom(WebDriver driver, int tabnumber) {
		String xpath = "//input[@id='flightNumber1flightRangeItem_flightRangeW"+tabnumber+"']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the text box for the "flight number to" in the search filter
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtBx_FlightNumberTo(WebDriver driver, int tabnumber) {
		String xpath = "//input[@id='flightNumber2flightRangeItem_flightRangeW"+tabnumber+"']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the ADD button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_add(WebDriver driver, int tabnumber) {
		String xpath = "//button[@id='addButton_flightRangeW"+tabnumber+"']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the SAVE button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_save(WebDriver driver) {
		String xpath = "//button[@ng-click='saveEditFlight()']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the OK button to confirm saving
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_confirmSave(WebDriver driver) {
		String xpath = "//span[contains(text(), 'OK')]/..";
		wait = new WebDriverWait(driver, 70);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the SEARCH button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_search(WebDriver driver) {
		String xpath = "//button[@ng-click='getEditFlightsList()']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * get the "button" to open the dropdown for an element in the search result grid
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_dropdown(WebDriver driver, int tabnumber) {
		String xpath = "//button[@id='EditFlightGrid_W"+tabnumber+"_InlineAction_0_ActListBtn']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * click on the EDIT button to edit a flight.
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_edit(WebDriver driver, int tabnumber) {
		String xpath = "//ul[@id='EditFlightGrid_W"+tabnumber+"_InlineAction_0']/li[1]";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/*
	 * close the Edit Flight popup, which appears after confirming to save an empty registration number
	 */
	public static void click_EditFlightPopupOK(WebDriver driver) {

		comm.performAction(driver,btn_EditFlightOK(driver), "CLICK", "field tab", "field tab");		

	}

	/**
	 * return the button OK from the Edit Flight pop up.
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_EditFlightOK(WebDriver driver) {
		String xpath = "//span[text()='OK']";
		
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
}