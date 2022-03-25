package utilities;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import pageObjects.IFlightNeo_Gantt;
import pageObjects.IFlightNeo_MessageList;

public class BusinessFunctions {
	public static CommonLibrary com = new CommonLibrary();
	
	/*************************************************************************
	 * Description: Method to close the current open Tab    
	 * ***********************************************************************/
//	public void closeTab(WebDriver driver) {
		// close the Tab
//		com.performAction(driver, IFlightNeo_MessageList.tab_close(driver), "CLICK", "click on Tab Close", "click on Tab Close");
//	}
	/*******************************************************
	 * Close Gantt Tab of Your Choice
	 *******************************************************/
	public static void closeTab(WebDriver driver, int tabIndex, boolean unpin) {
		if(unpin) {
			Actions actions = new Actions(driver);
			
			// right click to get the context menu
			actions.contextClick(IFlightNeo_MessageList.tab_pinned(driver)).perform();
			
			// click on "unpin" in the context
			com.performAction(driver, IFlightNeo_MessageList.list_unpin(driver), "CLICK", "click on Unpin Tab", "click on Unpin Tab");			
		}
		
		IFlightNeo_Gantt.link_RemoveGanttTab(driver).get(tabIndex).click();
	}

	
	/*************************************************************************
	 * Description: Method to switch to current working window    
	 * ***********************************************************************/
	public void switchToCurrentWindow(WebDriver driver) {
		// Switch Window
		Set<String> allWindows = driver.getWindowHandles();
		for(String window: allWindows) {
			driver.switchTo().window(window);
		}
	}
	
	/****************************************************************************
	 * Method returns column index of provided column name
	 * @return Column index number of the said column in the mentioned web table
	 * **************************************************************************/
	public int getColumnIndex(WebDriver driver, WebElement tableObject, String columnName) {
        // No.of columns    	
    	List<WebElement> columns = tableObject.findElements(By.xpath("//thead/tr/td"));
        System.out.println("No of cols are : " + columns.size());
        int colIndex = 0;
        for(int cl=1; cl<=columns.size(); cl++) {
        	if(tableObject.findElement(By.xpath("/thead/tr/td[" + cl + "]")).getText().contains(columnName)) {
        		colIndex = cl;
        		break;
        	}
        }
        System.out.println(columnName+" found at column index - "+colIndex);
        return colIndex;
    }
	
	/*****************************************************************************************
	 * Description: Method returns row index of provided data in said column
	 * @return Row index number of the data to find in said column in the mentioned web table
	 * ***************************************************************************************/
	public int getRowIndex(WebDriver driver, String tableXPath, int columnIndex, String data2Find)
    {  
        // No.of rows 
		List<WebElement> rows = driver.findElements(By.xpath(tableXPath + "/tbody/tr/td[1]"));
        System.out.println("No of rows are : " + rows.size());
        int rowIndex = 0;
		for(int rw=1; rw<=rows.size(); rw++) {
    		if(driver.findElement(By.xpath(tableXPath + "/tbody/tr[" + rw + "]/td[" + columnIndex + "]")).getText().contentEquals(data2Find)) {
        		rowIndex = rw;
        		break;
        	}
		}
        System.out.println(data2Find+" found at row index - "+rowIndex);
        return rowIndex;
    }
	
	/*****************************************************************************************
	 * Description: Method returns OOOI time from Flight Leg Details Dialog in Gantt screen
	 * ***************************************************************************************/
	public String readDisabledValuesInFlightLegDetails(WebDriver driver, String namePropertyVal) throws InterruptedException{
		String value="";
		try {
			// To enable AircraftRegistrationfield text box
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			String toEnable = "document.getElementsByName('"+namePropertyVal+"')[0].removeAttribute('disabled');";
			javascript.executeScript(toEnable);
			Thread.sleep(3000);
			//Storing the Aircraft registration value of first flight in a string variable
			value = driver.findElement(By.name(namePropertyVal)).getAttribute("value");
			System.out.println("Aircraft Registration :" + value);
		}
		catch(NoSuchElementException nsee) {}
		return value;
	}
	
	/*****************************************************************************************
	 * Description: Method to handle windows popup and upload the message file into the application
	 * ***************************************************************************************/
	public void uploadFileRobotClass(String filePath) throws Exception {
		// Specify the file location with extension
		StringSelection sel = new StringSelection(filePath);
		// Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel,null);
		System.out.println("selection" +sel);		
		// Create object of Robot class
		Robot robot = new Robot();
		Thread.sleep(1000);		     
		// Press Enter
		robot.keyPress(KeyEvent.VK_ENTER);		
		// Release Enter
		robot.keyRelease(KeyEvent.VK_ENTER);		
		// Press CTRL+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		// Release CTRL+V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		Thread.sleep(1000);		       
		//Press Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/*****************************************************************************************
	 * Description: Find Image in iNeo Application and returns the Screen Element
	 * @return imageScreen
	 * @throws InterruptedException 
	 * @throws FindFailed 
	 * ***************************************************************************************/
	public Screen getImageWithSikuli(String imagePath) throws InterruptedException {
		Thread.sleep(1000);
		Screen scn = new Screen();
		Pattern imgPattern = new Pattern(imagePath);
		Match match = scn.exists(imgPattern.similar(0.65));
		if(match!=null) {
			System.out.println("***IMAGE FOUND***");
			return scn;
		}
		else {
			System.out.println("***IMAGE NOT FOUND***");
			return null;
		}
	}
	
	/*****************************************************************************************
	 * Description: Method to update time in reference with base data-time
	 * ***************************************************************************************/
	public String updateDate(String baseTime, int hrsToUpdate, int minsToUpdate) {
		String time1 = baseTime.split(" ")[1];
		String time2 = com.addTime(time1, hrsToUpdate, minsToUpdate);
		return baseTime.split(" ")[0]+" "+time2;
	}
	
	/*****************************************************************************************
	 * Method returns entire column data for a specific row
	 * @return If rowIndex=0, method will return entire column data else if rowIndex>0, method will return column data of specific row
	 * ***************************************************************************************/
	public ArrayList<String> getColumnData(WebDriver driver, String tableXpath, int rowIndex, int columnIndex) {
		ArrayList<String> columnData = new ArrayList<String>();
		if(rowIndex==0) {
			// No.of rows
			int rows = getRowCount(driver, tableXpath);
	        System.out.println("No of rows are : " + rows);
	        try {
				for(int rw=1; rw<=rows; rw++) {
					columnData.add(driver.findElement(By.xpath(tableXpath+"/tbody/tr[" + rw + "]/td[" + columnIndex + "]")).getText());
	        	}
	        }catch(Exception e) {}
		} else {
			columnData.add(driver.findElement(By.xpath(tableXpath+"/tbody/tr[" + rowIndex + "]/td[" + columnIndex + "]")).getText());
		}
		return columnData;
    }
	
	/****************************************************************************
	 * Get total row count of provided table
	 * @return total row count in the mentioned web table
	 * **************************************************************************/
	public int getRowCount(WebDriver driver, String tableXpath) {
        // No.of columns    	
    	List<WebElement> rows = driver.findElements(By.xpath(tableXpath+"/tbody/tr"));
        return rows.size();
    }

	/*****************************************************************************************
	 * Verify text in tooltip message
	 * @return Boolean true if tooltip message contains expected text else returns false
	 * ***************************************************************************************/
	public boolean verifyToolTipMessage(String tooltipMsg, String textToVerify) {
		if (tooltipMsg.contains(textToVerify)){
			return true;
		} else {
			return false;
		}
	}
	
	/*****************************************************************************************
	 * Switch to another tab in Gantt
	  @author Moumita Sengupta
	 * ***************************************************************************************/
	//22nd March,2022
	public  void switchtotab(WebDriver driver,String Tabname)
	
	
	{   
		driver.findElements(By.xpath(Tabname)).get(0).click();
	}
	
	
	
	
}
