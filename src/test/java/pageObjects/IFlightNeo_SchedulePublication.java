package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IFlightNeo_SchedulePublication {
	protected static String emailSubfrommail;
	protected static String emailSubfrommailpri;
	protected static String emailSubfrommailsec;
	public utilities.ReportLibrary report = new utilities.ReportLibrary();
	public static utilities.CommonLibrary comm = new utilities.CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	
	public static WebElement Opt_schedulePublication(WebDriver driver){
        element = driver.findElement(By.xpath("//a[@id='menu_schedulepublication']"));
        return element;
	}
	
	public static WebElement txtbox_EmailStartDate(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='schedulepublicationWidgetModel_flightSchedulePublicationDTO_fromDate']")));
        element = driver.findElement(By.xpath("//input[@name='schedulepublicationWidgetModel_flightSchedulePublicationDTO_fromDate']"));
        return element;
	}
	
	public static WebElement txtbox_EmailEndDate(WebDriver driver){
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='schedulepublicationWidgetModel_flightSchedulePublicationDTO_toDate']")));
        element = driver.findElement(By.xpath("//input[@name='schedulepublicationWidgetModel_flightSchedulePublicationDTO_toDate']"));
        return element;
	}
	
	public static WebElement radiobtn_DailyAllocationType(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='dailyAllocationReport']")));
        element = driver.findElement(By.xpath("//input[@id='dailyAllocationReport']"));
        return element;
	}
	
	public static WebElement radiobtn_ScheduleChangesType(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='scheduleChangesReport']")));
        element = driver.findElement(By.xpath("//input[@id='scheduleChangesReport']"));
        return element;
	}
	
	public static WebElement btn_SearchButton(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Search')]")));
        element = driver.findElement(By.xpath("//button[contains(text(),'Search')]"));
        return element;
	}
	
	public static WebElement resultLines(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='schedulePublicationGrid_W1']//tr")));
        element = driver.findElement(By.xpath("//table[@id='schedulePublicationGrid_W1']//tr"));
        return element;
	}
	
	public static WebElement perviewSendbutton(WebDriver driver){
        element = driver.findElement(By.xpath("//button[@ng-click='previewAndSend()']"));
        return element;
	}
	
	public static WebElement emailPreviewWindow(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-describedby='EmailPreview']")));
        element = driver.findElement(By.xpath("//div[@aria-describedby='EmailPreview']"));
        return element;
	}
	
	public static WebElement emailSubjectline(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='schedulepublicationWidgetModel_subject']")));
        element = driver.findElement(By.xpath("//input[@name='schedulepublicationWidgetModel_subject']"));
        return element;
	}
	
	public static WebElement headerEmailPreview(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Dear All')]")));
        element = driver.findElement(By.xpath("//span[contains(text(),'Dear All')]"));
        return element;
	}
	
	public static WebElement footerEmailPreview(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'ALL TIMES IN UTC')]")));
        element = driver.findElement(By.xpath("//span[contains(text(),'ALL TIMES IN UTC')]"));
        return element;
	}
	
	public static WebElement To_textBox(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@ng-model='newTag.text']")));
        element = driver.findElement(By.xpath("//input[@ng-model='newTag.text']"));
        return element;
	}
	
	public static WebElement sendNowbutton(WebDriver driver){
		wait=new WebDriverWait(driver,90);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Send Now')]")));
        element = driver.findElement(By.xpath("//button[contains(text(),'Send Now')]"));
        return element;
	}
     
	public static WebElement MainMenu_Messages(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='menu_messages']")));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='menu_aircraft']")));
        element = driver.findElement(By.xpath("//a[@id='menu_messages']"));
        return element;
	}
	
	private static WebElement link_DeleteEmail(WebDriver driver) {
		// TODO Auto-generated method stub
		WebElement deleteEmailoption= driver.findElement(By.xpath("//div[@id='divToolbarButtondelete']//span[text()='Delete']"));
		return deleteEmailoption;
	}
	
	public static String verifyEmail (WebDriver driver, String emailSub) throws Exception
	{  
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(text(),'"+emailSub+"')]")));
		List<WebElement> scheduleEmailexists=driver.findElements(By.xpath("//div[contains(text(),'"+emailSub+"')]"));
		try {
			if(scheduleEmailexists.size()!=0)
			{  
				emailSubfrommailpri=scheduleEmailexists.get(1).getText();
				if(emailSubfrommailpri.equals(emailSub)) {
					emailSubfrommail=scheduleEmailexists.get(1).getText();
				}			
			}
		    else {
		    	emailSubfrommail="No schedule publication email received";
			}	
		}
		catch(IndexOutOfBoundsException error) {  
			emailSubfrommailsec=scheduleEmailexists.get(0).getText();
			if(scheduleEmailexists.size()!=0) {
				System.out.println(emailSubfrommailsec);
				if(emailSubfrommailsec.equals(emailSub)) {
					emailSubfrommail=scheduleEmailexists.get(0).getText();
				}
			}
		}
		return emailSubfrommail;
	}
	
	/**
	 * 
	 * @param driver
	 * @param startDate
	 * @param endDate
	 * @param type
	 */
	public static void emailNotificaitonForm(WebDriver driver, String startDate, String endDate, String type)
	{
		comm.performAction(driver, MainMenu_Messages(driver), "Click", "", "Click on Messages option in main menu");
		List<WebElement> nav_dropdown=driver.findElements(By.xpath("//ul[@id='nav']//li//ul//li//a"));
		for(int i=0;i<nav_dropdown.size();i++)
		{
			WebElement element =nav_dropdown.get(i);
			
			String subHeader=element.getAttribute("innerHTML");
				
			if(subHeader.contains("Schedule Publication")) 
			{
				comm.performAction(driver, Opt_schedulePublication(driver), "Click", "", "Select the Schedule publication option in messages menu");
				break;
			}
		}
		comm.performAction(driver, txtbox_EmailStartDate(driver), "SET", startDate, "entering start date");
		comm.performAction(driver, txtbox_EmailEndDate(driver), "SET", endDate, "entering End date");
		if(type.contains("DailyAllocation"))
		{
			comm.performAction(driver, radiobtn_DailyAllocationType(driver), "Click", "", "select Daily Allocation radio button");
		}
		if(type.contains("ScheduleChanges"))
		{
			comm.performAction(driver, radiobtn_ScheduleChangesType(driver), "Click", "", "select Schedule Changes radio button");
		}
		comm.performAction(driver, btn_SearchButton(driver), "Click", "", "Search button");
		resultLines(driver).isDisplayed();
		wait=new WebDriverWait(driver,90);
		wait.until(ExpectedConditions.elementToBeClickable(perviewSendbutton(driver))).click();
		emailPreviewWindow(driver).isDisplayed();
	}
	
	public static String emailPreviewVerify(WebDriver driver) 
	{
		String subject_line=emailSubjectline(driver).getAttribute("value");
		System.out.println(subject_line);
		String header_Line=headerEmailPreview(driver).getAttribute("innerHTML");
		System.out.println(header_Line);
		String footer_Line=footerEmailPreview(driver).getAttribute("innerHTML");
		System.out.println(footer_Line);
		List<String> columnNames =new ArrayList<String>();
		List<WebElement> columns = driver.findElements(By.xpath("//tbody//tr[5]//td//p//span"));
		for(int k=0; k<columns.size();k++)
		{
			WebElement eachColumn =columns.get(k);
			String columnHeaders =eachColumn.getAttribute("innerHTML");
			columnNames.add(columnHeaders);
		}
		System.out.println(columnNames);
		return subject_line;
		
		
	}
	
	public static String sendSchedulePublicationReport(String startDate, String endDate, String type, WebDriver driver, String emailID) throws InterruptedException
	{   
		//Filling data and getting results for Email Notification Form
		 emailNotificaitonForm(driver, startDate, endDate, type);
		// htmlLib.logReport("Email Preview to be sent", "Email Preview verify success", "Pass", true);
		 //Reading content from the Email
		 emailPreviewVerify(driver);
		 String mAil_subject=emailPreviewVerify(driver);
		 //Sending Email to the concerned mail ID's
		 To_textBox(driver).click();
		 To_textBox(driver).sendKeys(emailID+Keys.TAB);
		 
		 Thread.sleep(2000);
		 //JavascriptExecutor js = (JavascriptExecutor)driver;
		 //driver.manage().window().maximize();
		 //js.executeScript("arguments[0].scrollIntoView(true);",EY_iFlightNeo_HomePage_Messages.sendNowbutton(driver));
		 comm.scrollToWebElement(driver, sendNowbutton(driver));
		 //Thread.sleep(7000);
		 Actions a5 = new Actions(driver);
		 a5.moveToElement(sendNowbutton(driver)).click().build().perform();
		 System.out.println("Email sent");
		 return mAil_subject;
	}

	public static void deleteExistingMail(WebDriver driver, String emailSub) {
		// TODO Auto-generated method stub
		List<WebElement> scheduleEmailexists=driver.findElements(By.xpath("//div[contains(text(),'"+emailSub+"')]"));
		try {
			if(scheduleEmailexists.size()>1) {  
				comm.performAction(driver, link_DeleteEmail(driver),"click", "", "Delete schedulepublication email");
			}	
			else if(scheduleEmailexists.size()==1) {   
				Actions a5 = new Actions(driver);
				a5.moveToElement(scheduleEmailexists.get(0)).click().build().perform();
				comm.performAction(driver, link_DeleteEmail(driver),"click", "", "Delete schedulepublication email");
			}
		}
		catch(IndexOutOfBoundsException error) {
		  System.out.println("No such mail");
		}
	}
}
