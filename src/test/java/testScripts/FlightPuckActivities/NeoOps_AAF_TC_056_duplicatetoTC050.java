package testScripts.FlightPuckActivities;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_Messages;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC_056_duplicatetoTC050 {
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Verify Passenger Connection Details");
		CollectTestData.main(tcName);
		// Launching the driver, application and Login
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}

	@Test
	public void NeoOps_AAF_TC050_Test() throws Exception {
		// Collecting data from Excel and FlightImages Path
		try
		{
		String username = CollectTestData.userName;
		String password = CollectTestData.password;
		String Date = CollectTestData.flightDate;
		String flightNo = CollectTestData.flightNumber;
		String departureAirport = CollectTestData.origin;
		String arrivalAirport = CollectTestData.destination;
		String imagePath = System.getProperty("user.dir") + "\\TestData\\NeoOps_AAF_TC050\\EY513.PNG";		
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		// Opening the Gantt Screen and Finding the Flight
		IFlightNeo_HomePage.selectGantt(driver);
		IFlightNeo_Gantt.findFlightInGantt(driver, "" + flightNo, Date, departureAirport, arrivalAirport);
		
		htmlLib.logReport("Find flight Success", "flight Listed on Top", "Pass", driver, true);
		// Crew Connection Detail
		comm.performAction(driver, IFlightNeo_Gantt.btn_CrewConnDetails(driver), "CLICK", "", "Click on Crew Connection Details");
		Thread.sleep(2000);
		htmlLib.logReport("Crew Connection Line Displayed", "Crew Connection Line Displayed successfully", "Pass", driver, true);
		IFlightNeo_Gantt.selectFlightInGantt(driver, imagePath, "doubleClick");
		// Wait
		IFlightNeo_Gantt.label_FlightIDInFLD(driver);
		comm.performAction(driver, IFlightNeo_Gantt.btn_ViewDropdownInFlightLegDetails(driver), "Click", "", "Click on view drop down of Flight details");
		List<WebElement> viewdrp_List = driver.findElements(By.xpath("//ul[@role='menu']//li//a"));
		for(int drp=0; drp<viewdrp_List.size();drp++)
		{
			WebElement element =viewdrp_List.get(drp);
			String option=element.getAttribute("innerHTML");
			System.out.println(option);
			if(option.contains("Message List")) {
				comm.performAction(driver, element, "CLICK", "", "clicking on Message list Option");
				break;
			}
		}
		IFlightNeo_Messages.window_msgList(driver);
		comm.performAction(driver, IFlightNeo_Messages.btn_clear(driver), "CLICK", "", "Click Clear button");
		
		IFlightNeo_Gantt.window_FlightConnDetails(driver);
		if(IFlightNeo_Gantt.label_CockPitCrewUnderFlightConnDetails(driver).isDisplayed()) {
			htmlLib.logReport("Verify Crew tab is highlighted", "Crew tab is highlighted by default", "Pass", driver, true);
		}
		else {
			htmlLib.logReport("Verify Crew tab is highlighted", "Crew tab is not highlighted by default", "Fail", driver, true);
			Thread.sleep(2000);
			comm.performAction(driver, IFlightNeo_Gantt.btn_CrewTabUnderFlightConnDetails(driver), "CLICK", "", "Click on crew Tab");
			Thread.sleep(2000);
			htmlLib.logReport("Verify Crew tab is highlighted manually", "Crew tab is highlighted manually", "Pass", driver, true);
		}
		Actions action =new Actions(driver);
		action.moveToElement(IFlightNeo_Gantt.window_FlightConnDetails(driver)).sendKeys(Keys.ESCAPE).build().perform();
		IFlightNeo_HomePage.btn_CloseFlightDetailsWindow(driver);
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
