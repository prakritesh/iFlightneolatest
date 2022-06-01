package testScripts.SAW;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_RND_TC059 {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		/*
		 * Driver.setUpTestExecution(tcName,
		 * "Adding a widget on the Situational Awareness WIndow");
		 * CollectTestData.main(tcName);
		 */
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "Adding a widget on the Situational Awareness Window");
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
//		driver = EY_iFlightNeo_LoginPage.launchApplication(driver, sit_URL);
		// Login
		IFlightNeo_LoginPage.login(driver, username, password);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		htmlLib.logReport("Login Functionality is success", "Login sucess", "Pass", driver, true);
		IFlightNeo_HomePage.selectSeasonalAwarenessWindow(driver);
		IFlightNeo_HomePage.Add_Widget(driver);
		//h1[text()='Alert Monitor']
		IFlightNeo_HomePage.Add_ReqWidget(driver);
		IFlightNeo_HomePage.close_widget(driver);
		IFlightNeo_HomePage.rename_header(driver);
		IFlightNeo_HomePage.actionson_widget(driver);
		}
		catch(Exception e)
		{
			System.out.println("The exception occured for this TC is"+e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e.getMessage(), "Fail", driver, true);
			
		}
	}
	
	/*@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}*/
}
