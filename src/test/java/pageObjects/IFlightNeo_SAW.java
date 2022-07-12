package pageObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
//import org.sikuli.hotkey.Keys;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonLibrary;

public class IFlightNeo_SAW {

	public static CommonLibrary comm = new CommonLibrary();
	private static WebElement element = null;
	private static WebDriverWait wait;
	static int rows;
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	String[] lists = this.getClass().getName().split("\\.");
	static WebDriver Instance;

	// Alert Monitor title
	public static WebElement heading_AlertMonitor(WebDriver driver) {
		element = driver.findElement(By.xpath("//span[contains(text(),'Alert Monitor')]"));
		return element;

	}

	// button for changing dashboard in SSW
	public static WebElement buttonUpdateDashBoard(WebDriver driver) {
		wait = new WebDriverWait(driver, 120);
		element = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class='btn btn-default dropdown-toggle ng-binding']")));
		// button[@class='btn btn-default dropdown-toggle ng-bind
		return element;

	}

	// list of widgets
	public static List<WebElement> listOfWidgets(WebDriver driver) {
		wait = new WebDriverWait(driver, 300);
		List<WebElement> elements = wait.until(
				ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("span.widget_text.ng-binding"), 5));
		// List<WebElement> elements = driver.findElements(By.className("widget_text
		// ng-binding"));
		return elements;
	}

	// link for Network Control 3 switch
	public static WebElement link_dashBoardName(WebDriver driver, String dashBoardToChangeName) {
		wait = new WebDriverWait(driver, 30);
		element = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//a[contains(text(),'" + dashBoardToChangeName + "')]")));
		return element;
	}

	/**
	 * this function adds an Alert Monitor
	 * 
	 * @param driver
	 */
	public static void addAlertMonitor(WebDriver driver) {
		// click on the Add Widget Button
		comm.performAction(driver, btn_AddWidget(driver), "CLICK", "click on the Add Widget",
				"click on the Add Widget");

		// click on Add for the Alert Monitor
		comm.performAction(driver, btn_AddAlertMonitor(driver), "CLICK", "click on Add for the Alert Monitor",
				"click on Add for the Alert Monitor");

		// close the Add Widget PopUp
		comm.performAction(driver, btn_CloseAddWidgetPopUp(driver), "CLICK", "click on X to close the Add Widget Popup",
				"click on X to close the Add Widget Popup");
	}

	// Add Local World Dashlet if not opened
	public static void addLocalWorldWidget(WebDriver driver) {
		try {
			//widgetOpenedTitle(driver, "LocalWorld Dashlet").isDisplayed();
			tableHeader_LocalWorldDashlet(driver).isDisplayed();
		} catch (Exception e) {
			// click on the Add Widget Button
			comm.performAction(driver, btn_AddWidget(driver), "CLICK", "click on the Add Widget",
					"click on the Add Widget");
			// click on Add for the Alert Monitor
			comm.performAction(driver, btn_AddLocalWorldDashlet(driver), "CLICK", "",
					"Add for the Local World Dashlet");
			// close the Add Widget PopUp
			comm.performAction(driver, btn_CloseAddWidgetPopUp(driver), "CLICK",
					"click on X to close the Add Widget Popup", "close the Add Widget Popup");
		}
	}
	
	//Delete local worlds from LocalWorld Dashlet widgets
	public static void deleteAllLocalWorlds(WebDriver driver) {
		addLocalWorldWidget(driver);		
		tableHeader_LocalWorldDashlet(driver).isDisplayed();
		System.out.println(tableHeader_LocalWorldDashlet(driver).findElement(By.xpath("//ancestor::table")).getSize());
		List<WebElement> listOfLW = tableHeader_LocalWorldDashlet(driver).findElement(By.xpath("//ancestor::table")).findElements(By.xpath("//tbody/tr/td[contains(text(),'LW')]"));
		if(listOfLW.size()>0) {
			for(int i =0;i<listOfLW.size();i++) {
				WebElement firstLW = tableHeader_LocalWorldDashlet(driver).findElement(By.xpath("//ancestor::table")).findElement(By.xpath("//tbody/tr/td[contains(text(),'LW')]"));
				firstLW.findElement(By.xpath("//following-sibling::td/button[contains(text(),'Delete')]")).click();
				btn_Yes(driver).click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			htmlLib.logReport("Verify any Local world is open in Gantt", "Deleted all open Local world in LocalWorld Dashlet", "INFO", driver, true);
		}
		else {
			htmlLib.logReport("Verify any Local world is open in Gantt", "No Local world is open in LocalWorld Dashlet", "INFO", driver, true);
		}
	}

	/**
	 * TODO this function configures an open Alert Monitor - IT DOES NOT YET WORK!!!
	 * 
	 * @param driver
	 */
	public static void configureAlertMonitor(WebDriver driver, String[] alertType, String[] alertGroup,
			String periodstart, String periodEnd) {

		Actions action = new Actions(driver);
		// click on the Add Widget Button
		comm.performAction(driver, btn_EditAlertMonitor(driver), "CLICK", "click on Edit for the Alert Monitor",
				"click on Edit for the Alert Monitor");

		// click on the dropdown for the Alert Type
		comm.performAction(driver, list_AlertType(driver), "CLICK", "click on dropdown for the Alert Type",
				"click on dropdown for the Alert Type");

		for (int i = 0; i < alertType.length; i++) {
			comm.performAction(driver, list_AlertType(driver, alertType[i]), "CLICK", "", alertType[i]);
		}
		action.sendKeys(Keys.ESCAPE).perform();
		;

		// click on the dropdown for the Alert Group
		comm.performAction(driver, list_AlertGroup(driver), "CLICK", "", "Alert Group");

		for (int i = 0; i < alertGroup.length; i++) {
			comm.performAction(driver, list_AlertGroup(driver, alertGroup[i]), "CLICK", "", alertGroup[i]);
		}
		action.sendKeys(Keys.ESCAPE).perform();
		;

		comm.performAction(driver, text_PeriodStartDay(driver), "SET", periodstart, "Period Start Day");
		comm.performAction(driver, text_PeriodEndDay(driver), "SET", periodEnd, "Period End Day");
		// click on the Save button
		comm.performAction(driver, btn_SaveAlertMonitor(driver), "CLICK", "click on the Save button",
				"click on the Save button");
	}

	/**
	 * link the current Alert Monitor to the Gantt
	 * 
	 * @param driver
	 */
	public static void linkToGantt(WebDriver driver) {
		// click on the Add Widget Button
		comm.performAction(driver, btn_linkToGantt(driver), "CLICK", "link the current Alert Monitor to the Gantt",
				"link the current Alert Monitor to the Gantt");
	}

	/**
	 * return the textbox for the Alert Type of the Alert Monitor
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement txtbx_AlertTypeINPUT(WebDriver driver) {
		String xpath = "(//input[@class='select2-input'])[last()]";
//		String xpath = "(//div[@class='select2-search'])[last()]";

//		wait = new WebDriverWait(driver,30);
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static WebElement txtbx_AlertTypeDIV(WebDriver driver) {
//		String xpath = "(//input[@class='select2-input'])[last()]";
		String xpath = "(//div[@class='select2-search'])[last()]";

//		wait = new WebDriverWait(driver,30);
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the button to link the Alert Monitor to the Gantt
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_linkToGantt(WebDriver driver) {
		String xpath = "//div[@off-label='LINK']";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Save button for the Alert Monitor
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_SaveAlertMonitor(WebDriver driver) {
		String xpath = "//button[@value='Save']";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the textbox for the Alert Type
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_CriticalElement(WebDriver driver) {
		String xpath = "//div[contains(text(), 'Critical')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static WebElement list_AlertType(WebDriver driver, String alertType) {
		String xpath = "//div[contains(text(), '" + alertType + "')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static WebElement list_AlertGroup(WebDriver driver, String alertGroup) {
		String xpath = "//div[contains(text(), '" + alertGroup + "')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the textbox for the Alert Type
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement list_AlertType(WebDriver driver) {
		String xpath = "//input[@name='config_severities']/../div";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	// dropdown of AlertGroup
	public static WebElement list_AlertGroup(WebDriver driver) {
		String xpath = "//label[text()='Alert Group']/ancestor::div[@class='row']//ul[@class='select2-choices']";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	// Period start day of AlertGroup
	public static WebElement text_PeriodStartDay(WebDriver driver) {
		String xpath = "//label[text()='Period']/ancestor::div[@class='row']//div[@class='h_size_40 pull-left'][1]/input";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	// Period end day of AlertGroup
	public static WebElement text_PeriodEndDay(WebDriver driver) {
		String xpath = "//label[text()='Period']/ancestor::div[@class='row']//div[@class='h_size_40 pull-left'][2]/input";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Edit button for the Alert Monitor
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_EditAlertMonitor(WebDriver driver) {
		String xpath = "//span[@key='#dashboard.edit.message']";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Add Widget button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_CloseAddWidgetPopUp(WebDriver driver) {
		String xpath = "(//button[@title='Close'])[1]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	// Title of widget table opened
	public static WebElement widgetOpenedTitle(WebDriver driver, String widgetTitle) {
		String xpath = "//div[@class='portlet-header ui-widget-header ui-corner-all']/span[contains(text(),'"
				+ widgetTitle + "')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;

	}
	
	// Title of widget table opened
		public static WebElement tableHeader_LocalWorldDashlet(WebDriver driver) {
			String xpath = "//table[@class='dashboard_grid arrival']/thead/tr/th[contains(text(),'LOCALWORLD')]";

			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			element = driver.findElement(By.xpath(xpath));
			return element;

		}

	/**
	 * return the Add Widget button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_AddWidget(WebDriver driver) {
		String xpath = "//button[contains(text(), 'Add Widget')]";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	/**
	 * return the Add for the Alert Monitor button
	 * 
	 * @param driver
	 * @return
	 */
	public static WebElement btn_AddAlertMonitor(WebDriver driver) {
		String xpath = "//h1[contains(text(), 'Alert Monitor')]/../button";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}
	
	// Yes button
	public static WebElement btn_Yes(WebDriver driver) {	
		String xpath = "//button/span[text()='YES']";
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static WebElement btn_AddLocalWorldDashlet(WebDriver driver) {
		String xpath = "//h1[contains(text(), 'LocalWorld Dashlet')]/../button";

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		element = driver.findElement(By.xpath(xpath));
		return element;
	}

	public static void changeDashBoard(WebDriver driver, String dashBoardToChangeName) {
		// expand change dashboard button/list
		comm.performAction(driver, buttonUpdateDashBoard(driver), "click", "", "Change dashboard expand button");
		// click dashBoard from dropdown
		comm.performAction(driver, link_dashBoardName(driver, dashBoardToChangeName), "click", "",
				dashBoardToChangeName + " link");
		// verify dashboard changed
		if (buttonUpdateDashBoard(driver).getText().contains(dashBoardToChangeName)) {
			htmlLib.logReport("Verify " + dashBoardToChangeName + " dashboard should open",
					dashBoardToChangeName + " opened", "PASS", driver, true);
		} else {

			htmlLib.logReport("Verify " + dashBoardToChangeName + " dashboard should open",
					dashBoardToChangeName + " didnot opened", "FAIL", driver, true);
		}

		List<String> widgetNames = listOfWidgets(driver).stream().map(ele -> ele.getText())
				.collect(Collectors.toList());
		List<String> widgetToVerify = Arrays.asList("Equipment Restrictions", "Missing OOOI timings",
				"LocalWorld Dashlet");
		Boolean haveWidget = false;
		String widget = null;
		for (String wg : widgetToVerify) {
			widget = wg;
			if (widgetNames.contains(wg)) {
				haveWidget = true;
			}
			if (!haveWidget) {
				break;
			}
		}
		if (haveWidget) {
			htmlLib.logReport("Verify that Widgets are present in dashboard", "All widgets are available in dashboard",
					"PASS", driver, true);
		} else {
			htmlLib.logReport("Verify that Widgets are present in dashboard", widget + "is not available", "FAIL",
					driver, true);
		}

	}

	public static void verifyFlight(WebDriver driver, String flightNo) {
		try {

			WebElement flight = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//td[contains(text(),'" + flightNo + "')]")));
			comm.performAction(driver, flight, "HOVER", "", flightNo);
			htmlLib.logReport("Verify flight present in SAW", "Flight is present in SAW : EY" + flightNo, "PASS",
					driver, true);
		} catch (Exception e) {
			htmlLib.logReport("Verify flight present in SAW", "Flight is not present in SAW : EY" + flightNo, "FAIL",
					driver, true);
		}

	}

}