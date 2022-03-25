package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IFlightNeo_Messages {
	public utilities.ReportLibrary report = new utilities.ReportLibrary();
	public static utilities.CommonLibrary comm = new utilities.CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	
	/** Messages Main Menu */
	public static WebElement mainMenu_Messages(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='menu_messages']")));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='menu_aircraft']")));
        element = driver.findElement(By.xpath("//a[@id='menu_messages']"));
        return element;
	}

	/** Sub menu SchedulePublication under Messages */
	public static WebElement subMenu_SchedulePublication(WebDriver driver){
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
	
	public static WebElement radiobtn_Type_DailyAllocation(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='dailyAllocationReport']")));
        element = driver.findElement(By.xpath("//input[@id='dailyAllocationReport']"));
        return element;
	}
	
	public static WebElement radiobtn_Type_ScheduleChanges(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='scheduleChangesReport']")));
        element = driver.findElement(By.xpath("//input[@id='scheduleChangesReport']"));
        return element;
	}
	
	public static WebElement btn_searchButton(WebDriver driver){
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
	
	public static WebElement window_msgList(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'LW1-Message List')]")));
        element = driver.findElement(By.xpath("//span[contains(text(),'LW1-Message List')]"));
        return element;
	}
	
	public static WebElement btn_clear(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Clear')]")));
        element = driver.findElement(By.xpath("//button[contains(text(),'Clear')]"));
        return element;
	}
	
	public static WebElement txt_direction(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(),'Direction')]/following-sibling::div")));
        element = driver.findElement(By.xpath("//label[contains(text(),'Direction')]/following-sibling::div"));
        return element;
	}
	
	public static WebElement txt_fromDate(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='messageListWidgetModel_messageListDTO_fromDate']")));
        element = driver.findElement(By.xpath("//input[@name='messageListWidgetModel_messageListDTO_fromDate']"));
        return element;
	}
	
	public static WebElement txt_toDate(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='messageListWidgetModel_messageListDTO_toDate']")));
        element = driver.findElement(By.xpath("//input[@name='messageListWidgetModel_messageListDTO_toDate']"));
        return element;
	}
	
	public static WebElement btn_search(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@ng-click='searchFilteredMessages()']")));
        element = driver.findElement(By.xpath("//button[@ng-click='searchFilteredMessages()']"));
        return element;
	}
	
	public static WebElement row_msgType(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[contains(text(),'CrewPairing')]")));
        element = driver.findElement(By.xpath("//td[contains(text(),'CrewPairing')]"));
        return element;
	}
	
	public static WebElement drpDwn_msgActList(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='MessageGrid_W1_InlineAction_0_ActListBtn']")));
        element = driver.findElement(By.xpath("//button[@id='MessageGrid_W1_InlineAction_0_ActListBtn']"));
        return element;
	}
	
	public static WebElement list_downLoad(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(),'Download')]")));
        element = driver.findElement(By.xpath("//li[contains(text(),'Download')]"));
        return element;
	}
	public static WebElement txt_messageType(WebDriver driver){
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[contains(text(),'Message Type')]/following-sibling::div")));
        element = driver.findElement(By.xpath("//label[contains(text(),'Message Type')]/following-sibling::div"));
        return element;
	}
	
	/**
	 * Clear button in Message List Page
	 */
	public static WebElement btn_ClearInMessageList(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='clear()']")));
		element = driver.findElement(By.xpath("//button[@ng-click='clear()']"));
		return element;
	}
	
	/**
	 * Load Message button in Message List page
	 */
	public static WebElement btn_LoadMessage(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Load Message')]")));
		element = driver.findElement(By.xpath("//button[contains(text(),'Load Message')]"));
		return element;
	}
	
	/**
	 * Message type list in Message Load Dialog in Message Page
	 */
	public static WebElement list_MessageType(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@name='messageLoadForm']//span[contains(text(),'Select an Item')]")));
		element = driver.findElement(By.xpath("//form[@name='messageLoadForm']//span[contains(text(),'Select an Item')]"));
		return element;
	}
	
	/**
	 * Message type search box in Message Load Dialog in Message Page
	 */
	public static WebElement txtBx_MessageType(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/div[@id='select2-drop']//input")));
		element = driver.findElement(By.xpath("//body/div[@id='select2-drop']//input"));
		return element;
	}
	
	
	/**
	 * Message Load dialog in Message Page
	 */
	public static WebElement dialog_MessageLoad(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']//span[contains(text(),'Message Load   [FMSG005]')]")));
		element = driver.findElement(By.xpath("//div[@role='dialog']//span[contains(text(),'Message Load   [FMSG005]')]"));
		return element;
	}
	
	/**
	 * Add files button on Message Load under Message Page
	 */
	public static WebElement btn_AddFiles(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Add files')]")));
		element = driver.findElement(By.xpath("//button[contains(text(),'Add files')]"));
		return element;
	}
	
	/**
	 * Close Message Load Dialog
	 */
	public static WebElement btn_CloseMessgeUploadPopUp(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		String xpath = "//span[contains(text(),'Message Load')]/following-sibling::div//button";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	/**
	 * Upload button in Message Load under Message Page
	 */
	public static WebElement btn_Upload(WebDriver driver) {
		comm.scrollToWebElement(driver, driver.findElement(By.xpath("//button[contains(text(),'Upload')]")));
		element = driver.findElement(By.xpath("//button[contains(text(),'Upload')]"));
		return element;
	}
	
	/**
	 * 
	 */
	public static WebElement msg_ProcessedMessage(WebDriver driver) {
		wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Message processing initiated')]")));
		element = driver.findElement(By.xpath("//b[contains(text(),'Message processing initiated')]"));
		return element;
	}
	
	/**
	 * 
	 * @param driver
	 * @param startDate
	 * @param endDate
	 * @param type
	 */
	public static void SchedulePublication_emailNotificaitonForm(WebDriver driver, String startDate, String endDate, String type)
	{
			comm.performAction(driver, mainMenu_Messages(driver), "Click", "", "Click on Messages option in main menu");
			List<WebElement> nav_dropdown=driver.findElements(By.xpath("//ul[@id='nav']//li//ul//li//a"));
			for(int i=0;i<nav_dropdown.size();i++)
			{
				WebElement element =nav_dropdown.get(i);
				
				String subHeader=element.getAttribute("innerHTML");
					
				if(subHeader.contains("Schedule Publication")) 
				{
					comm.performAction(driver, subMenu_SchedulePublication(driver), "Click", "", "Select the Schedule publication option in messages menu");
					break;
				}
			}
			comm.performAction(driver, txtbox_EmailStartDate(driver), "SET", startDate, "entering start date");
			comm.performAction(driver, txtbox_EmailEndDate(driver), "SET", endDate, "entering End date");
			if(type.contains("DailyAllocation"))
			{
				comm.performAction(driver, radiobtn_Type_DailyAllocation(driver), "Click", "", "select Daily Allocation radio button");
			}
			if(type.contains("ScheduleChanges"))
			{
				comm.performAction(driver, radiobtn_Type_ScheduleChanges(driver), "Click", "", "select Schedule Changes radio button");
			}
			comm.performAction(driver, btn_searchButton(driver), "Click", "", "Search button clicked");
			resultLines(driver).isDisplayed();
			wait=new WebDriverWait(driver,90);
			wait.until(ExpectedConditions.elementToBeClickable(perviewSendbutton(driver))).click();
			emailPreviewWindow(driver).isDisplayed();
	}
	
	/**
	 * Verify Email Preview Before Sending Notification
	 * @param driver
	 */
	public static void verifyEmailPreview(WebDriver driver) 
	{
		String subject_line=emailSubjectline(driver).getText();
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
		
		
	}
	
}
