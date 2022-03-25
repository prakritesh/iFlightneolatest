package utilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class Driver {
	public static utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	@Test
	public static void baseMethod() {
		// TODO Auto-generated method stub
		htmlLib.configureReport();
	}
	
	/**
	 * Set Up Initial Script Requirement viz. Create Report, Collect TestData
	 * @param tesCasecName, testDescription
	 */
	public static void setUpTestExecution(String tesCasecName, String testDescription) {
		htmlLib.createReport(tesCasecName, testDescription);
		CollectTestData.main(tesCasecName);
	}
	
	/**
	 * Sum Up test script execution and erase memory allocated to collection object used and close browser
	 * @param WebDriver driver
	 */
	public static void tearDownTestExecution(WebDriver driver) {
		CollectTestData.testData.clear();

		
		 // driver.close(); 
		 //driver.quit();
		 

		driver.close(); 
		driver.quit();

	}
}


