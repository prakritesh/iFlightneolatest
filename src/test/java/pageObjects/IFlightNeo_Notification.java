package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonLibrary;

public class IFlightNeo_Notification {

	public static CommonLibrary comm = new CommonLibrary();
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	public static String groupName = "";
	WebDriver driver;

	public static WebElement filterIcon(WebDriver driver) {
		WebElement Filter = driver
				.findElement(By.xpath("//div[@class='icon_wrap']/span[@class='icon_filter fa fa-filter']"));
		return Filter;
	}

	public static WebElement searchkey(WebDriver driver) {
		WebElement search = driver.findElement(By.xpath("//input[@id='jsTree_q']"));
		return search;
	}

	public static List<WebElement> select_Keyword(WebDriver driver, String keyword) {

		/*
		 * List<WebElement> AlertGroup = driver.findElements(By.xpath(
		 * "//a[contains(@id ,'"+keyword+"')]/i[@class='jstree-icon jstree-checkbox']"))
		 * ;
		 */
		List<WebElement> AlertGroup = driver.findElements(
				By.xpath("//a[contains(text() ,'" + keyword + "')]/i[@class='jstree-icon jstree-checkbox']"));

		return AlertGroup;
	}

	public static WebElement select_OperationAlert(WebDriver driver) {
		WebElement AlertGroup = driver.findElement(By.xpath(
				"//a[@id='OPERATIONAL_ALERTS@OPERATIONAL_ALERTS_anchor']/i[@class='jstree-icon jstree-checkbox']"));
		return AlertGroup;
	}

	public static WebElement select_RuleViolation(WebDriver driver) {
		WebElement AlertGroup = driver.findElement(
				By.xpath("//a[@id='RULE_VIOLATION@RULE_VIOLATION_anchor']/i[@class='jstree-icon jstree-checkbox']"));
		return AlertGroup;
	}

	public static WebElement select_InformationUpdate(WebDriver driver) {
		WebElement AlertGroup = driver.findElement(By.xpath(
				"//a[@id='INFORMATION_UPDATES@INFORMATION_UPDATES_anchor']/i[@class='jstree-icon jstree-checkbox']"));
		return AlertGroup;
	}

	public static WebElement select_High(WebDriver driver) {
		WebElement high = driver
				.findElement(By.xpath("//a[@id='HIGH@HIGH_anchor']/i[@class='jstree-icon jstree-checkbox']"));
		return high;
	}

	public static WebElement select_Critical(WebDriver driver) {
		WebElement critical = driver
				.findElement(By.xpath("//a[@id='CRITICAL@CRITICAL_anchor']/i[@class='jstree-icon jstree-checkbox']"));
		return critical;
	}

	public static WebElement select_Medium(WebDriver driver) {
		WebElement medium = driver
				.findElement(By.xpath("//a[@id='MEDIUM@MEDIUM_anchor']/i[@class='jstree-icon jstree-checkbox']"));
		return medium;
	}

	public static WebElement select_Low(WebDriver driver) {
		WebElement low = driver
				.findElement(By.xpath("//a[@id='LOW@LOW_anchor']/i[@class='jstree-icon jstree-checkbox']"));
		return low;
	}

	public static WebElement filterButton(WebDriver driver) {
		WebElement Filter = driver
				.findElement(By.xpath("//div/button[@ng-click='filterNotification(selectedTreeList)']"));
		return Filter;
	}

	public static WebElement filterSaveButton(WebDriver driver) {
		WebElement Filter = driver
				.findElement(By.xpath("//div/button[@ng-click='saveFilterNotification(selectedTreeList)']"));
		return Filter;
	}

	public static WebElement alertGroup(WebDriver driver) {
		WebElement group = driver.findElement(By.xpath("//div[@id='jqgh_notificationBrowserGrid_W1_group']"));
		return group;
	}

	public static WebElement alertCategory(WebDriver driver) {
		WebElement category = driver.findElement(By.xpath("//div[@id='jqgh_notificationBrowserGrid_W1_category']"));
		return category;
	}

	public static WebElement alertGroupAfterRefresh(WebDriver driver) {
		WebElement group = driver
				.findElement(By.xpath("//div[@id='jqgh_configureNotificationGrid_W3_notificationGroup']"));
		return group;
	}

	public static WebElement alertSeverity(WebDriver driver) {
		WebElement severity = driver.findElement(By.xpath("//div[@id='jqgh_notificationBrowserGrid_W1_severity']"));
		return severity;
	}

	public static WebElement filterNameTextBox(WebDriver driver) {
		WebElement group = driver.findElement(By.xpath("//form[@id='ifnLogoutForm']//input"));
		return group;
	}

	public static WebElement filterNameOKButton(WebDriver driver) {
		WebElement group = driver.findElement(By.xpath("//button[@ng-click='saveFilter()']"));
		return group;
	}

	public static WebElement moreFilterButton(WebDriver driver) {
		WebElement more = driver.findElement(By.xpath("//button[contains(@class,'right sm_icon_only')]/i[1]"));
		return more;
	}

	public static WebElement filterName(WebDriver driver) {
		WebElement more = driver.findElement(By.xpath("//li[contains(text(),'" + groupName + "')]"));
		return more;
	}

	public static WebElement selectCriteria(WebDriver driver) {
		WebElement criteria = driver.findElement(By.xpath("//select[@ng-model='criteria']"));
		return criteria;
	}
	
	public static WebElement firstUnreadRecord(WebDriver driver) {
		WebElement record = driver.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr']n"));
		return record;
	}
	
	public static List<WebElement> records(WebDriver driver) {
		List<WebElement> record = driver.findElements(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr']"));
		return record;
	}
	
	public static WebElement recordsBody(WebDriver driver) {
		WebElement recordBody = driver.findElement(By.xpath("//table[@id='notificationBrowserGrid_W1']/tbody"));
		return recordBody;
	}
	
	
	public static WebElement markAsRead(WebDriver driver) {
		WebElement recordBody = driver.findElement(By.xpath("//ul[@class='toggle_menu rootmenu inline_drop']/li[4]"));
		return recordBody;
	}
	
	public static WebElement markAsUnRead(WebDriver driver) {
		WebElement recordBody = driver.findElement(By.xpath("//ul[@class='toggle_menu rootmenu inline_drop']/li[4]"));
		return recordBody;
	}


	// added by prakritesh

	public static void navigate_AlertBrowser(WebDriver driver) throws InterruptedException {
		wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='menu_notification']")));
		Thread.sleep(2000);
		element = driver.findElement(By.xpath("//a[@id='menu_notification']"));
		element.click();
		driver.findElement(By.xpath("//a[@id='menu_notificationBrowser']")).click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='searchNotifications()']")));

	}

	public static void navigateAgain_AlertBrowser(WebDriver driver) throws InterruptedException {
		wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@id='menu_notification']")));
		Thread.sleep(2000);
		element = driver.findElement(By.xpath("//a[@id='menu_notification']"));
		element.click();
		driver.findElement(By.xpath("//a[@id='menu_notificationBrowser']")).click();
		// driver.findElement(By.xpath("//a[@id='menu_configureNotification']")).click();

		wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@id='menu_notification']")));
		element = driver.findElement(By.xpath("//a[@id='menu_notification']"));
		element.click();
		// driver.findElement(By.xpath("//a[@id='menu_notificationBrowser']")).click();
		driver.findElement(By.xpath("//a[@id='menu_configureNotification']")).click();

		wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@id='menu_notification']")));
		element = driver.findElement(By.xpath("//a[@id='menu_notification']"));
		element.click();
		driver.findElement(By.xpath("//a[@id='menu_notificationBrowser']")).click();
		// driver.findElement(By.xpath("//a[@id='menu_configureNotification']")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@id='ui-id-1']")).click();
		Thread.sleep(3000);

	}

	public static void filterByFirstDropDown(WebDriver driver, String GroupOrSeverity, String alertGroup)
			throws InterruptedException {
		if (driver.findElement(By.xpath("//div[@class='barwest']")).getAttribute("style").contains("left: 0px")) {
			comm.performAction(driver, filterIcon(driver), "click", "", "Click Filter icon on bottom left corner ");
		}
		if (GroupOrSeverity.contains("Group")) {
			comm.performAction(driver, selectCriteria(driver), "SELECT", "Group", "Group");
			filterByAlertGroup(driver, alertGroup);
		} else if (GroupOrSeverity.contains("Severity")) {
			comm.performAction(driver, selectCriteria(driver), "SELECT", "Severity", "Severity");
			filterBySeverity(driver, alertGroup);
		}
	}

	public static void filterBySeverity(WebDriver driver, String alertGroup) throws InterruptedException {

		if (driver.findElement(By.xpath("//div[@class='barwest']")).getAttribute("style").contains("left: 0px")) {
			comm.performAction(driver, filterIcon(driver), "click", "", "Click Filter icon on bottom left corner ");
		}

		if (alertGroup.contains("High")) {
			comm.performAction(driver, select_Critical(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Medium(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Low(driver), "click", "", "");
			Thread.sleep(1000);
		} else if (alertGroup.contains("Critical")) {
			comm.performAction(driver, select_High(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Medium(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Low(driver), "click", "", "");
			Thread.sleep(1000);
		} else if (alertGroup.contains("Medium")) {
			comm.performAction(driver, select_High(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Critical(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Low(driver), "click", "", "");
			Thread.sleep(1000);
		} else if (alertGroup.contains("Low")) {
			comm.performAction(driver, select_High(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Critical(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_Medium(driver), "click", "", "");
			Thread.sleep(1000);
		}
		comm.performAction(driver, filterButton(driver), "", "", "Click on filter button");

		String expectedVal = "";
		if (alertGroup.contains("High")) {
			expectedVal = "HIGH";
		} else if (alertGroup.contains("Critical")) {
			expectedVal = "CRITICAL";
		} else if (alertGroup.contains("Medium")) {
			expectedVal = "MEDIUM";
		} else if (alertGroup.contains("Low")) {
			expectedVal = "LOW";
		}

		Thread.sleep(3000);
		comm.performAction(driver, alertSeverity(driver), "", "", "");
		WebElement row = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[9]"));
		String rowval = row.getAttribute("title");
		if (expectedVal.equalsIgnoreCase(rowval)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

		Thread.sleep(3000);
		comm.performAction(driver, alertGroup(driver), "", "", "");
		WebElement row2 = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[9]"));
		rowval = row2.getAttribute("title");
		if (expectedVal.equalsIgnoreCase(rowval)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

	}
	
	public static int getRowOfUnreadRecord(WebDriver driver) {
		int unreadRowFirst =0;
		for(int i=1;i<records(driver).size();i++) {
			WebElement row = recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td[4]"));
			if(row.getAttribute("style").contains("bold")) {
				
				comm.performAction(driver, recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td/input[@class='cbox']")), "click", "", "Select Row");
				
				
				WebElement scrollArea = driver.findElement(By.className("ui-jqgrid-bdiv"));
				
				// Scroll to div's most right:
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollArea);

				wait.until(ExpectedConditions.elementToBeClickable(recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td/div[@class='btn_container']"))));
				
				comm.performAction(driver, recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td//button[@class='btn btn-primary toggle_collapse']")), "click", "", "Record Action");
				
				comm.performAction(driver, markAsRead(driver), "click", "", "Mark as Read");
				unreadRowFirst = i;
				break;
			}
		}
		return unreadRowFirst;

	}
	
	
	public static int getRowOfReadRecord(WebDriver driver) {
		int readRowFirst =0;
		for(int i=2;i<records(driver).size();i++) {
			WebElement row = recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td[4]"));
			if(!row.getAttribute("style").contains("bold")) {
				
				comm.performAction(driver, recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td/input[@class='cbox']")), "click", "", "Select Row");
				
				
				WebElement scrollArea = driver.findElement(By.className("ui-jqgrid-bdiv"));
				
				// Scroll to div's most right:
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollArea);

				wait.until(ExpectedConditions.elementToBeClickable(recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td/div[@class='btn_container']"))));
				
				comm.performAction(driver, recordsBody(driver).findElement(By.xpath("//tr["+i+"]/td//button[@class='btn btn-primary toggle_collapse']")), "click", "", "Record Action");
				
				comm.performAction(driver, markAsUnRead(driver), "click", "", "Mark as UnRead");
				readRowFirst = i;
				break;
			}
		}
		return readRowFirst;

	
	}
	
	public static void verifyReadRow(WebDriver driver, int rowNum) {
		WebElement row = recordsBody(driver).findElement(By.xpath("//tr["+rowNum+"]/td[4]"));
		if(!row.getAttribute("style").contains("bold")) {
			htmlLib.logReport("Verify that Notification will be only marked as read for the user ", "Notification is read", "PASS", driver, true);
		}
		else{
			htmlLib.logReport("Verify that Notification will be only marked as read for the user ", "Notification is unread", "FAIL", driver, true);
		}
		
	}
	
	public static void verifyUnReadRow(WebDriver driver, int rowNum) {
		WebElement row = recordsBody(driver).findElement(By.xpath("//tr["+rowNum+"]/td[4]"));
		if(row.getAttribute("style").contains("bold")) {
			htmlLib.logReport("Verify that Notification will be only marked as unread for the user ", "Notification is unread", "PASS", driver, true);
		}
		else{
			htmlLib.logReport("Verify that Notification will be only marked as unread for the user ", "Notification is read", "FAIL", driver, true);
		}
		
	}
	public static void filterByKeyword(WebDriver driver, String keyword) throws InterruptedException {
		String expectedVal = keyword.toUpperCase();
		if (driver.findElement(By.xpath("//div[@class='barwest']")).getAttribute("style").contains("left: 0px")) {
			comm.performAction(driver, filterIcon(driver), "click", "", "Click Filter icon on bottom left corner ");
		}

		comm.performAction(driver, select_RuleViolation(driver), "click", "", "");
		Thread.sleep(1000);
		comm.performAction(driver, select_InformationUpdate(driver), "click", "", "");
		Thread.sleep(1000);
		comm.performAction(driver, select_OperationAlert(driver), "click", "", "");

		comm.performAction(driver, searchkey(driver), "SET", keyword, expectedVal);
		Thread.sleep(2000);
		System.out.println(select_Keyword(driver, keyword).size());
		for (WebElement key : select_Keyword(driver, keyword)) {
			key.click();
		}

		comm.performAction(driver, filterButton(driver), "", "", "Click on filter button");

		Thread.sleep(3000);
		comm.performAction(driver, alertCategory(driver), "", "", "");
		WebElement row = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[5]"));
		String rowval = row.getAttribute("title");
		if (rowval.toUpperCase().contains(expectedVal)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

		Thread.sleep(3000);
		comm.performAction(driver, alertCategory(driver), "", "", "");
		WebElement row2 = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[5]"));
		rowval = row2.getAttribute("title");
		if (rowval.toUpperCase().contains(expectedVal)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

	}

	public static void filterByAlertGroup(WebDriver driver, String alertGroup) throws InterruptedException {
		if (driver.findElement(By.xpath("//div[@class='barwest']")).getAttribute("style").contains("left: 0px")) {
			comm.performAction(driver, filterIcon(driver), "click", "", "Click Filter icon on bottom left corner ");
		}

		if (alertGroup.contains("Operation Alert")) {
			comm.performAction(driver, select_RuleViolation(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_InformationUpdate(driver), "click", "", "");
			Thread.sleep(1000);
		} else if (alertGroup.contains("Rule Violation")) {
			comm.performAction(driver, select_OperationAlert(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_InformationUpdate(driver), "click", "", "");
			Thread.sleep(1000);
		} else if (alertGroup.contains("Information Update")) {
			comm.performAction(driver, select_OperationAlert(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_RuleViolation(driver), "click", "", "");
		}

		comm.performAction(driver, filterButton(driver), "", "", "Click on filter button");

		String expectedVal = "";
		if (alertGroup.contains("Operation Alert")) {
			expectedVal = "OPERATIONAL ALERTS";
		} else if (alertGroup.contains("Rule Violation")) {
			expectedVal = "RULE_VIOLATION";
		} else if (alertGroup.contains("Information Update")) {
			expectedVal = "INFORMATION_UPDATES";
		}

		Thread.sleep(3000);
		comm.performAction(driver, alertGroup(driver), "", "", "");
		WebElement row = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[4]"));
		String rowval = row.getAttribute("title");
		if (expectedVal.equalsIgnoreCase(rowval)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

		Thread.sleep(3000);
		comm.performAction(driver, alertGroup(driver), "", "", "");
		WebElement row2 = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[4]"));
		rowval = row2.getAttribute("title");
		if (expectedVal.equalsIgnoreCase(rowval)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

	}

	public static void saveFilter(WebDriver driver, String alertGroup) throws InterruptedException {

		comm.performAction(driver, filterIcon(driver), "click", "", "Click Filter icon on bottom left corner ");
		if (alertGroup.contains("Operation Alert")) {
			comm.performAction(driver, select_RuleViolation(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_InformationUpdate(driver), "click", "", "");
			Thread.sleep(1000);
		} else if (alertGroup.contains("Rule Violation")) {
			comm.performAction(driver, select_OperationAlert(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_InformationUpdate(driver), "click", "", "");
			Thread.sleep(1000);
		} else if (alertGroup.contains("Information Update")) {
			comm.performAction(driver, select_OperationAlert(driver), "click", "", "");
			Thread.sleep(1000);
			comm.performAction(driver, select_RuleViolation(driver), "click", "", "");
		}
		comm.performAction(driver, filterSaveButton(driver), "", "", "Click on Save button");
		Thread.sleep(3000);
		Actions action = new Actions(driver);
		groupName = comm.getRandomString(5);
		action.sendKeys(groupName).build().perform();

		/*
		 * Actions okButton = new Actions(driver);
		 * okButton.click(filterNameOKButton(driver)).build().perform();
		 */
		comm.performAction(driver, filterNameOKButton(driver), "", "", "Enter filter name");

		// comm.performAction(driver, filterNameTextBox(driver), "SET", "testprak",
		// "Enter filter name");

	}

	public static void displayByFilterName(WebDriver driver, String alertGroup) throws InterruptedException {
		comm.performAction(driver, moreFilterButton(driver), "HOVER", "", "Hover filter name");
		Thread.sleep(1000);
		comm.performAction(driver, filterName(driver), "", "", "click filter name");
		/*
		 * List<WebElement> filterName =
		 * driver.findElements(By.xpath("//li[@class='ng-binding']")); for(int i
		 * =0;i<filterName.size();i++) { String fn = filterName.get(i).getText();
		 * System.out.println(fn); }
		 */

		String expectedVal = "";
		if (alertGroup.contains("Operation Alert")) {
			expectedVal = "OPERATIONAL ALERTS";
		} else if (alertGroup.contains("Rule Violation")) {
			expectedVal = "RULE_VIOLATION";
		} else if (alertGroup.contains("Information Update")) {
			expectedVal = "INFORMATION_UPDATES";
		}

		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfAllElements(alertGroup(driver)));
		comm.performAction(driver, alertGroup(driver), "", "", "");
		WebElement row = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[4]"));
		String rowval = row.getAttribute("title");
		System.out.println(rowval);
		if (expectedVal.equalsIgnoreCase(rowval)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(alertGroupAfterRefresh(driver)));
		comm.performAction(driver, alertGroupAfterRefresh(driver), "", "", "");
		WebElement row2 = driver
				.findElement(By.xpath("//tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr'][1]/td[4]"));
		rowval = row2.getAttribute("title");
		if (expectedVal.equalsIgnoreCase(rowval)) {
			htmlLib.logReport("Only " + expectedVal + " is displayed", rowval, "PASS", driver, true);
		} else {
			htmlLib.logReport("Other than filter group is also displayed", rowval, "FAIL", driver, true);
		}

	}

	public static boolean browse_notification(WebDriver driver, String flighNo) throws InterruptedException

	{
		boolean ifflightCancelled = false;
		wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@id='menu_notification']")));
		element = driver.findElement(By.xpath("//a[@id='menu_notification']"));
		element.click();

		driver.findElement(By.xpath("//a[@id='menu_notificationBrowser']")).click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@ng-click='searchNotifications()']")));

		driver.findElement(By.xpath("//button[@ng-click='searchNotifications()']")).click();

		Thread.sleep(500);

		List<WebElement> cancellation_notification = driver
				.findElements(By.xpath("//table[contains(@id,'notificationBrowserGrid')]//tr//td"));
 // In order to avoid the long wait for accessing the whole table of notification browser limited the tdcounter to 69 ( 1st 2 rows)
		for (int tdcounter = 0; tdcounter < 69; tdcounter++) {

			WebElement element = driver
					.findElements(By.xpath("//table[contains(@id,'notificationBrowserGrid')]//tr//td")).get(tdcounter);

			String notification = element.getAttribute("innerHTML").replaceAll("\\s", "");

			if (notification.contains("EY" + flighNo + "Cancelled!")) {

				// String
				// flightentity=driver.findElements(By.xpath("//table[contains(@id,'notificationBrowserGrid')]//tr//td")).get(tdcounter+5).getAttribute("innerHTML").replaceAll("\\s",
				// "");

				System.out.println("Cancellation notification triggered for flight: " + flighNo
						+ " which is cancelled from GANTT");

				tdcounter = cancellation_notification.size();

				ifflightCancelled = true;

			}

		}
		return ifflightCancelled;

	}

	public static void navigate_configureAlert(WebDriver driver) {
		wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@id='menu_notification']")));
		element = driver.findElement(By.xpath("//a[@id='menu_notification']"));
		element.click();
		driver.findElement(By.xpath("//a[@id='menu_configureNotification']")).click();

		wait = new WebDriverWait(driver, 70);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//h4[@class='padding_l_sm hidden_header']")));

		WebElement title = driver.findElement(By.xpath("//h4[@class='padding_l_sm hidden_header']"));

		String titleTextActual = title.getText();
		if (titleTextActual.contains("Configure Alerts List")) {
			htmlLib.logReport("Configure Alerts displyed", titleTextActual, "PASS", driver, true);
		} else {
			htmlLib.logReport("Configure Alerts not displyed", titleTextActual, "FAIL", driver, true);
		}

	}

}
