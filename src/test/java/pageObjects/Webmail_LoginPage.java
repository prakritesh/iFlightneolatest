package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Webmail_LoginPage {
	private static WebElement element = null;

	/** User Name field in Outlook Login Page */
	private static WebElement txtbx_UserName(WebDriver driver) {
		element = driver.findElement(By.xpath("//input[@id='username']"));
		return element;
	}
	
	/** Password field in Outlook Login Page */
	private static WebElement txtbx_Password(WebDriver driver) {
		element = driver.findElement(By.xpath("//input[@id='password']"));
		return element;
	}
	
	/** Login button in Outlook Login Page */
	private static WebElement btn_LogIn(WebDriver driver) {
		element = driver.findElement(By.xpath("//input[@type='submit']"));
		return element;
	}
	
	
	/** 
	 * Method to Login into OutLook Email
	 */
	public static void loginOutlookMail(WebDriver driver, String userName, String pwd) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOf(txtbx_UserName(driver)));
		txtbx_UserName(driver).sendKeys(userName);
		txtbx_Password(driver).sendKeys(pwd);
		btn_LogIn(driver).click();
	}
}
