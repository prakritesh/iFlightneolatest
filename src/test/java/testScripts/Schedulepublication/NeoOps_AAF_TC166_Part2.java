package testScripts.Schedulepublication;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_SchedulePublication;
import pageObjects.Webmail_LoginPage;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_AAF_TC166_Part2 {
	
	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary comm = new utilities.CommonLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	String emailID = CollectTestData.mailUserName+"@etihadppe.ae";
	
	@BeforeMethod
	void setUp() {
		Driver.setUpTestExecution(tcName, "Preview Email and Send to Recipients");
		CollectTestData.main(tcName);//
		// Launch Application
		String url = "https://webmail.etihadppe.ae/owa/";
		driver = IFlightNeo_LoginPage.launchApplication(CollectTestData.browser, url);
	}
    
	@Test
	public void NeoOps_AAF_TC166_emailAttachmentDownload() throws Exception
	{	
		String emailUser = CollectTestData.mailUserName;
		String emailPassword =CollectTestData.mailPassword;
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		Webmail_LoginPage.loginOutlookMail(driver, emailUser, emailPassword);
		// Verify Email received
		String emailSub = NeoOps_AAF_TC166.mailSubject;
		if(IFlightNeo_SchedulePublication.verifyEmail(driver,emailSub).equals(emailSub)) {
			htmlLib.logReport("Whether schedulepublication email received", "SchedulePublication email received", "Pass", driver, true);
		} 
		else {
			htmlLib.logReport("Whether schedulepublication email received", "SchedulePublication email not received", "Fail", driver, true);	
		}
		IFlightNeo_SchedulePublication.deleteExistingMail(driver,emailSub);
	}
	
	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}
}