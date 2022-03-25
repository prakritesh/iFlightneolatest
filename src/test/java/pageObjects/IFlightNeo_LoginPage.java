package pageObjects;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IFlightNeo_LoginPage {
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public static utilities.CommonLibrary comm = new utilities.CommonLibrary();
	private static WebElement element = null;
	private static By locator;

	/** User Name field on Login Page */
	public static WebElement txtbx_UserName(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		locator = By.xpath("//input[@id='user_id']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element = driver.findElement(locator);
		return element;
	}
	
	/** Password field on Login Page */
	public static WebElement txtbx_Password(WebDriver driver) {
		element = driver.findElement(By.xpath("//input[@id='user_password']"));
		return element;
	}
	
	/** Login Button on Login Page */
	public static WebElement btn_LogIn(WebDriver driver) {
		element = driver.findElement(By.xpath("//input[@name='submit']"));
		return element;
	}
	
	/** Login Error Message on Login Page */
	public static boolean msg_LoginFailure(WebDriver driver) {
		try {
			element = driver.findElement(By.xpath("//span[contains(text(),'Login failed')]"));
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	/*public static WebDriver launchApplication(WebDriver driver, String url) {
		//System.setProperty("webdriver.chrome.driver",
			//	"C:\\Users\\MoumitaSengupta\\Downloads\\iFlightNeo_Moumita\\iFlightNeo_Moumita\\iFlight_Neo_Mou\\TestData\\chromedriver.exe");
	//	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\SupportingFiles\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);
		WebDriverManager.chromedriver().setup(); //added by hannu
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);
		return driver;

	}*/
	
	/**
     * Launch Browser and Set URL
     * @param browserName
     * @param url
     * @return
     */
	public static WebDriver launchApplication(String browserName, String url) {
        WebDriver driver = null;
        try {
	        switch (browserName.toUpperCase()) {
	        case "CHROME":
	            // Options
	            ChromeOptions chromeOptions = new ChromeOptions();
	            chromeOptions.addArguments("--headless");
	            DesiredCapabilities capabilities = new DesiredCapabilities();
	            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
	            chromeOptions.merge(capabilities);
	            WebDriverManager.chromedriver().setup();
	            driver = new ChromeDriver();
	            break;
	        case "FIREFOX":
	            FirefoxOptions ffOptions = new FirefoxOptions();
	            ffOptions.addArguments("--headless");
	            capabilities = new DesiredCapabilities();
	            capabilities.setCapability(ChromeOptions.CAPABILITY, ffOptions);
	            ffOptions.merge(capabilities);
	            WebDriverManager.firefoxdriver().setup();
	            driver = new FirefoxDriver();
	            break;
	        case "EDGE":
	            WebDriverManager.edgedriver().setup();
	            driver = new EdgeDriver();
	            break;
	        case "IE":
	            WebDriverManager.iedriver().setup();
	            driver = new InternetExplorerDriver();
	            break;
	        }
	        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	        driver.manage().window().maximize();
	        driver.get(url);
	        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        }
        catch(Exception e) {
        	htmlLib.logReport("Launch Application", "Error Encountered - " + e.getMessage(), "FATAL", driver, false);
        }
        return driver;
    }

	/*** Login to iFlight Neo ***/
	public static boolean login(WebDriver driver, String userName, String pwd) {
		boolean loginSuccess = true;
		try {
			// Username
			comm.performAction(driver, txtbx_UserName(driver), "SET", userName, "User Name");
			// Password
			comm.performAction(driver, txtbx_Password(driver), "SET", pwd, "Password");
			// Login button
			comm.performAction(driver, btn_LogIn(driver), "Click", pwd, "Login");
			Thread.sleep(1000);
			if(msg_LoginFailure(driver)) {
				htmlLib.logReport("Verify Login Functionality", "Login Unsuccessful", "FAIL", driver, true);
			} else {
				// Wait
				IFlightNeo_HomePage.label_Date(driver);
				driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
				htmlLib.logReport("Verify Login Functionality", "Login Successful", "PASS", driver, true);
			}
		}
		catch(NoSuchElementException nsefe) {
			loginSuccess = false;
			htmlLib.logReport("Verify Login Functionality", "Login NOT Successful", "FAIL", driver, true);
		}
		catch(Exception e) {
			e.printStackTrace();
			loginSuccess = false;
			htmlLib.logReport("Verify Login Functionality", "Login NOT Successful", "FAIL", driver, true);
		}
		return loginSuccess;
	}
}
