package pageObjects;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonLibrary;

public class IFlightNeo_ManageUsers {
	private static WebDriverWait wait;
	private static WebElement element;
	private static By locator;
	public static CommonLibrary comm = new CommonLibrary();
	private static By editIconLocator = By.xpath("//td//div/span[@class='ui-icon ui-icon-edit']");

	public static WebElement manageUserTableHeader_UserName(WebDriver driver) {
		element = driver.findElement(By.id("jqgh_manageUsersGrid_W1_userName"));
		return element;
	}

	public static List<WebElement> userNames(WebDriver driver) {
		List<WebElement> element = driver.findElements(By.xpath(
				"//table[@id='manageUsersGrid_W1']/tbody[1]/tr/td[@aria-describedby='manageUsersGrid_W1_userName']"));
		return element;
	}

	public static WebElement getTotalPageCount(WebDriver driver) {
		element = driver.findElement(By.xpath(
				"//td[@id='manageUsersGrid_Pager_W1_right']/table/tbody/tr//span[@id='sp_1_manageUsersGrid_Pager_W1']"));
		return element;
	}

	public static WebElement nextpageButton(WebDriver driver) {
		wait = new WebDriverWait(driver, 180);
		locator = By.xpath(
				"//td[@id='manageUsersGrid_Pager_W1_right']/table/tbody/tr//span[@class='ui-icon ui-icon-seek-next']");
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}

	public static WebElement selectRoleDropdown(WebDriver driver) {
		wait = new WebDriverWait(driver, 180);
		// locator
		// =By.xpath("//form[@name='newUserDetails']//div[@id='s2id_autogen12']//li[contains(text(),'Select
		// a Role')]");
		locator = By.xpath(
				"//form[@name='newUserDetails']//label[contains(text(),'Assign Roles')]/following-sibling::div/ul[@class='select2-choices']");
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}

	public static WebElement selectRoleDropDownSearchBox(WebDriver driver) {
		wait = new WebDriverWait(driver, 180);
		locator = By.xpath("//div[@id='select2-drop']//input[@id='s2id_autogen13']");
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}

	public static List<WebElement> existingRoles(WebDriver driver) {
		List<WebElement> existingRole = driver.findElements(By.xpath(
				"//form[@name='newUserDetails']//label[contains(text(),'Assign Roles')]/following-sibling::div/ul[@class='select2-choices']/li"));
		System.out.println(existingRole.size());
		return existingRole;
	}
	
	public static WebElement firstExistingRole(WebDriver driver) {
		element = driver.findElement(By.xpath(
				"//form[@name='newUserDetails']//label[contains(text(),'Assign Roles')]/following-sibling::div/ul[@class='select2-choices']/li[1]//a"));
		return element;
	}

	public static WebElement saveButtonUserEdit(WebDriver driver) {
		wait = new WebDriverWait(driver, 180);
		locator = By.xpath(
				"//form[@name='newUserDetails']//div[@class='row button_panel  margin_t_sm']//button[contains(text(),'Save')]");
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}

	public static WebElement OKButtonUserUpdated(WebDriver driver) {
		wait = new WebDriverWait(driver, 180);
		locator = By.xpath(
				"//div[contains(text(),'User Updated Successfully')]/following-sibling::div//span[contains(text(),'OK')]");
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}

	public static void editUser(WebDriver driver, String userName, List<String> roles) throws InterruptedException {
		// Wait for the user table to display
		wait = new WebDriverWait(driver, 320);
		WebElement pager = wait.until(ExpectedConditions.visibilityOf(getTotalPageCount(driver)));
		// Get total page count in pagination of users list
		int pageCount = Integer.parseInt(pager.getText());
		// Loop through all pages and find the userName
		for (int index = 0; index < pageCount; index++) {
			// If condition works if the name is present in the page else moves to the next
			// page
			if (!userNames(driver).stream().filter(name -> name.getText().equalsIgnoreCase(userName))
					.collect(Collectors.toList()).isEmpty()) {
				// Loop through the userNames present in the page in which name found
				for (WebElement user : userNames(driver)) {
					if (user.getText().equalsIgnoreCase(userName)) {
						// get row of the username found
						WebElement row = user.findElement(By.xpath("//parent::tr"));
						row.findElement(By.xpath("//td/input[@type='checkbox']")).click();
						// Scroll to div's most right:
						WebElement scrollArea = driver.findElement(By.className("ui-jqgrid-bdiv"));
						((JavascriptExecutor) driver)
								.executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollArea);
						wait.until(ExpectedConditions.elementToBeClickable(
								row.findElement(By.xpath("//td//div/span[@class='ui-icon ui-icon-edit']"))));
						// click pencil icon for edit
						comm.performAction(driver, row.findElement(editIconLocator), "click", "", "Pencil icon");
						// if roles exists else not exists
						try {
							wait = new WebDriverWait(driver, 60);
							wait.until(ExpectedConditions.visibilityOf(selectRoleDropdown(driver)
									.findElement(By.xpath("//li[contains(text(),'Select a Role')]"))));
						} catch (Exception e) {
							int size = existingRoles(driver).size();
							for (int i = 0; i < size; i++) {
								comm.performAction(driver, firstExistingRole(driver), "click", "", "Existing Role "+i);
								Thread.sleep(1000);
							}
							/*
							 * for(WebElement existingRole : countOfExistingRole) {
							 * existingRole.findElement(By.xpath("//a")).click(); }
							 */
							// countOfExistingRole.forEach(existingRole->existingRole.findElement(By.xpath("//a")).click());
						}

						// Set roles
						roles.forEach(role -> {
							comm.performAction(driver, selectRoleDropdown(driver), "click", "",
									"Assign Roles dropdown");
							// Edit Assign Roles
							comm.performAction(driver, selectRoleDropDownSearchBox(driver), "SET", role, role);
							comm.performAction(driver, selectRoleDropDownSearchBox(driver).findElement(By.xpath(
									"//parent::div/following-sibling::ul//li/div[contains(text(),'" + role + "')]")),
									"click", role, role);
						});
						// scroll to the end bottom of popup
						WebElement scrollAreaNewUserDetails = driver.findElement(By.id("W2"));
						((JavascriptExecutor) driver).executeScript(
								"arguments[0].scrollTop = arguments[0].scrollHeight", scrollAreaNewUserDetails);
						// Click Save
						comm.performAction(driver, saveButtonUserEdit(driver), "click", "", "Save button");
						// Click OK
						comm.performAction(driver, OKButtonUserUpdated(driver), "click", "", "OK button");

						break;
					}

				}

				break;
			}
			nextpageButton(driver).click();
			;

		}

	}

	public static void deselectRoles(WebDriver driver, String userToEdit, List<String> userRole) throws InterruptedException {
		// Wait for the user table to display
		wait = new WebDriverWait(driver, 320);
		WebElement pager = wait.until(ExpectedConditions.visibilityOf(getTotalPageCount(driver)));
		// Get total page count in pagination of users list
		int pageCount = Integer.parseInt(pager.getText());
		// Loop through all pages and find the userName
		for (int index = 0; index < pageCount; index++) {
			// If condition works if the name is present in the page else moves to the next
			// page
			if (!userNames(driver).stream().filter(name -> name.getText().equalsIgnoreCase(userToEdit))
					.collect(Collectors.toList()).isEmpty()) {
				// Loop through the userNames present in the page in which name found
				for (WebElement user : userNames(driver)) {
					if (user.getText().equalsIgnoreCase(userToEdit)) {
						// get row of the username found
						WebElement row = user.findElement(By.xpath("//parent::tr"));
						row.findElement(By.xpath("//td/input[@type='checkbox']")).click();
						// Scroll to div's most right:
						WebElement scrollArea = driver.findElement(By.className("ui-jqgrid-bdiv"));
						((JavascriptExecutor) driver)
								.executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", scrollArea);
						wait.until(ExpectedConditions.elementToBeClickable(
								row.findElement(By.xpath("//td//div/span[@class='ui-icon ui-icon-edit']"))));
						// click pencil icon for edit
						comm.performAction(driver, row.findElement(editIconLocator), "click", "", "Pencil icon");
						// Delete Roles
						int size = existingRoles(driver).size();
						for (int i = 1; i <= size; i++) {
							comm.performAction(driver, firstExistingRole(driver), "click", "", "Existing Role "+i);
							Thread.sleep(1000);
						}
						// scroll to the end bottom of popup
						WebElement scrollAreaNewUserDetails = driver.findElement(By.id("W2"));
						((JavascriptExecutor) driver).executeScript(
								"arguments[0].scrollTop = arguments[0].scrollHeight", scrollAreaNewUserDetails);
						// Click Save
						comm.performAction(driver, saveButtonUserEdit(driver), "click", "", "Save button");
						// Click OK
						comm.performAction(driver, OKButtonUserUpdated(driver), "click", "", "OK button");

						break;
					}

				}

				break;
			}
			nextpageButton(driver).click();
			;

		}

	

	}

}
