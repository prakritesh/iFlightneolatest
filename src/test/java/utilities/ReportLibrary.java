package utilities;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ReportLibrary {
	protected String resultColor = "";
	protected static int StNo = 1/* Driver_Script.stepCount*/;
	protected static ExtentHtmlReporter htmlReporter;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static String reportDirectory = System.getProperty("user.dir") + "\\Reports\\";
	protected static String finalReportPath = "";
	protected static String tcName;
	
	/*********************************************************************************************
	 * Description: Method to collect supporting files for HTML report
	 * @author 		Rohit Prajapati
	 * @since		13/12/2016
	 * ********************************************************************************************/
	public void collectReportDependentFiles(String destinationFolder, String fileNames[])
	{
		// Create Destination Folder
		new File(destinationFolder).mkdir();
		
		String sourceFilePath = null;
		
		for(String fileName: fileNames) {
			sourceFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + fileName;
			copyFile(sourceFilePath, destinationFolder);
		}
	}
	
	/*********************************************************************************************
	 * Description: Method to create header of a html report
	 * @author 		Rohit Prajapati
	 * @since		13/12/2016
	 * ********************************************************************************************/
	/*public void createLogHeader(String testName, String testDescription)
	{
		Date dt = new Date();
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String ReportHead = "<html>\n" +
			"<head>\n" +
				"<title>" + testName + "</title>\n" +
				"<link rel='stylesheet' type='text/css' href='../" + new File(Driver_Script.htmlTestResultFolder).getName() + "/Supporting Files/design.css'>\n" +
				"<div class='inner-container' style='width: 1570px; margin: 0 auto;'>" + 
				"<div class='centered-content3'>" +
				"<img src='../" + new File(Driver_Script.htmlTestResultFolder).getName() + "/Supporting Files/Etihad Logo.jpg' align='left'/>\n" +
				"<img src='../" + new File(Driver_Script.htmlTestResultFolder).getName() + "/Supporting Files/IBM Logo.jpg' align='right'/>\n" +
				"</div>\n" + "</div>" +			
				"</head>\n" +  
			"<body oncontextmenu='return false'>\n" + 
				"<table class='one' align='center'>\n" +
					"<col width='78%'>\n" + 
					"<col width='22%'>\n" +
					
					"<tr>\n" +
						"<th class='one1'>RESULT FOR : " + testName + "</th>\n" +
						"<th class='one1'>EXECUTION STATUS: PASS</th>\n" +
					"</tr>\n" +
						
					"<tr>\n" +
						"<td class='two'>" + testDescription + "</td>\n" + 
						"<td class='two'>EXEC. DATE : " +  sDate.format(dt) + "</td>\n" +
					"</tr>\n" +
				"</table>\n";
		executionResult = ReportHead;
	}*/
	
	/*********************************************************************************************
	 * Description: Method to create header of a Html report details Header
	 * @author 		Rohit Prajapati
	 * @since		13-Dec-2016
	 * ********************************************************************************************/
	/*public void createResultDetailHeader()
	{
		String DetailHead = "<h2 font-family='Trebuchet MS' align='center'>Detailed Execution Result:</h2>\n" +
				"<table class='one' align='center'>\n" +
					"<col width='7%'>\n"+
					"<col width='37%'>\n"+
					"<col width='40%'>\n"+
					"<col width='8.5%'>\n" +
					"<col width='7.5%'>\n" +
					
					"<tr>\n"+
						"<th class='two'>STEP NO.</th>\n"+
						"<th class='two'>STEP / VERIFICATION</th>\n"+
						"<th class='two'>ACTUAL OUTCOME</th>\n"+
						"<th class='two'>RESULT</th>\n" +
						"<th class='two'>EXEC. TIME</th>\n"+
					"</tr>\n";
		executionResult = executionResult + DetailHead;
	}*/
	
	/*********************************************************************************************
	 * Description: Method to set prerequisites for HTML Report
	 * @author 		Rohit Prajapati
	 * @since		13-Aug-2020
	 * ********************************************************************************************/
	public void configureReport()
	{
		if(!new File(reportDirectory).isDirectory()){
			new File(reportDirectory).mkdir();
		}
		Date dt = new Date();
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		finalReportPath = reportDirectory + "Batch_Exec_"+ sDate.format(dt).replace(" ", "_").replace(":", "");
		/*if(new File(reportPath).isDirectory()) {
			 Common_Library.deleteFolder(new File(reportPath));
		}*/
		new File(finalReportPath).mkdir();
		//location of the extent report
		htmlReporter = new ExtentHtmlReporter(finalReportPath +"\\Report.html");
		extent = new ExtentReports();  //create object of ExtentReports
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setDocumentTitle("Automation Report"); // Title of Report
		htmlReporter.config().setReportName("Batch Execution Report"); // Name of the report
		//test = extent.createTest(testName, "Description");
	}
	
	/****************************************************************
	 * Description: Method to set prerequisites for HTML Report
	 * @author      Rohit Prajapati
	 * @since		13-Aug-2020
	 * **************************************************************/
	public void createReport(String testName, String testDescription)
	{
		tcName = testName;
		test = extent.createTest(testName, testDescription);
	}
	
	/**************************************************************
	 * Description: Method to finalize and open HTML Report
	 * @author 		Rohit Prajapati
	 * @since		13-Aug-2020 
	 * ************************************************************/
	@Test
	public void closeReport()
	{
		extent.flush();
		try {
			Desktop.getDesktop().open(new File(finalReportPath +"\\Report.html"));
		} catch (IOException e) {}
	}
	
	/*****************************************************************
	 * Description: Method to report verdict in the log file.
	 * @author 		Rohit Prajapati
	 * @since		29-Apr-2015. Last modified on 13-Aug-2020
	 * ***************************************************************/
	public void logReport(String sVerification, String sActualResult, String verdict, WebDriver driver, boolean isScreenReq)
	{		
		try {
			String screenLocation = "";
			switch(verdict.trim().toUpperCase()){
			case "PASS":
				System.out.println(verdict.toUpperCase() + ": " + sVerification + ", Actual- " + sActualResult);
				if(isScreenReq) {
					screenLocation = new File(captureScreenShot(driver)).getName();
					test.log(Status.PASS, "ACTION: "+ sVerification +", ACTUAL RESULT: "+ sActualResult, MediaEntityBuilder.createScreenCaptureFromPath(screenLocation).build());
				}
				else {
					test.log(Status.PASS, "ACTION: "+ sVerification +", ACTUAL RESULT: "+ sActualResult);
				}
				break;
			case "FAIL":
				System.out.println(verdict.toUpperCase() +": "+ sVerification +", Actual- "+ sActualResult);
				if(isScreenReq) {
					screenLocation = new File(captureScreenShot(driver)).getName();
					test.log(Status.FAIL, "ACTION: "+ sVerification +", ACTUAL RESULT: "+ sActualResult, MediaEntityBuilder.createScreenCaptureFromPath(screenLocation).build());
				}
				else {
					test.log(Status.FAIL, "ACTION: "+ sVerification +", ACTUAL RESULT: "+ sActualResult);
				}
				//Driver_Script.htmlTestExecStatus = false;
				break;
			case "WARNING":
				System.out.println(verdict.toUpperCase() +": " + sVerification + ", Actual- "+ sActualResult);
				if(isScreenReq) {
					screenLocation = new File(captureScreenShot(driver)).getName();
					test.log(Status.WARNING, sVerification +", ActualResult: "+ sActualResult, MediaEntityBuilder.createScreenCaptureFromPath(screenLocation).build());
				}
				else {
					test.log(Status.WARNING, "ACTION: "+ sVerification +", ACTUAL RESULT: "+ sActualResult);
				}
				break;
			case "INFO":
				System.out.println(verdict.toUpperCase() +": "+ sVerification +", Actual- "+ sActualResult);
				if(isScreenReq) {
					screenLocation = new File(captureScreenShot(driver)).getName();
					test.log(Status.INFO, sVerification +", ActualResult: "+ sActualResult, MediaEntityBuilder.createScreenCaptureFromPath(screenLocation).build());
				}
				else {
					test.log(Status.INFO, "ACTION: "+ sVerification +", ACTUAL RESULT: "+ sActualResult);
				}
				break;
			case "FATAL":
				test.log(Status.FATAL, "ACTION: "+ sVerification +", ACTUAL RESULT: "+ sActualResult);
			}
			//insertExecutionStep(sVerification, sActualResult, verdict, true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**********************************************************************
	 * Description: Method to insert test step into into Html report
	 * @author 		Rohit Prajapati
	 * @since		13-Dec-2016
	 * ********************************************************************/
	/*public void insertExecutionStep(String expectedResult, String actualResult, String status, boolean isScreenRequired)
	{		
		StNo = ++Driver_Script.stepCount;
		String colorCode = "";
		String mark = "";
		if(status.equalsIgnoreCase("PASS")){
			colorCode = "#319B1C";
			mark = "&#10004";
		}
		else if(status.equalsIgnoreCase("FAIL")){
			colorCode = "#FF0000";
			mark = "&#10008";
		}
		else if(status.equalsIgnoreCase("INFO")){
			colorCode = "#0000FF";
			mark = "&#10071";
		}
		else if(status.equalsIgnoreCase("WARNING")){
			status = "ALERT";
			colorCode = "#B8860B";
			mark = "&#10071";
		}
		
		String href = "";
		if(isScreenRequired){
			File file = new File(captureScreenShot());
			href = "<a href='../" + new File(Driver_Script.htmlTestResultFolder).getName() + "/" + file.getName() + "'>";
		}
		Date dt = new Date();
		SimpleDateFormat sDate = new SimpleDateFormat("HH:mm:ss");
		
		String execSteps = "<tr>\n"+
					"<td class = 'one'>" + StNo + ".</td>\n"+
					"<td class = 'two'>" + expectedResult + "</td>\n"+
					"<td class = 'two'>" + actualResult + "</td>\n"+
					"<td class = 'one'>" + href + "<button class='fixedButton'><font color='" + colorCode + "'>" + mark + " " + status.toUpperCase() + "</font></button></td>\n" +
					"<td class = 'one'>" + sDate.format(dt) + "</td>\n" +
				"</tr>\n";
		executionResult = executionResult + execSteps;
	}*/
	
	/*******************************************************************************
	 * Description:	Method to take screen shot and return screen image location
	 * @author	 	Rohit Prajapati
	 * @since 	 	13-Dec-2016
	 * @return   	Screenshot File Location
	 * *****************************************************************************/
	public String captureScreenShot(WebDriver driver)
	{
		String screenLocation = finalReportPath + "\\ScreenImg" + Integer.toString(StNo++) + ".jpg";
		try {
			/*Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "jpg", new File(screenLocation));*/

			//File src = ((TakesScreenshot) driver.getDelegate()).getScreenshotAs(OutputType.FILE);
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(screenLocation));
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		} catch (WebDriverException wde) {
			wde.printStackTrace();
		} catch (UnsupportedOperationException uoe) {
			uoe.printStackTrace();
		}
		return screenLocation;
	}
	
	/*********************************************************************************
	 * Description: Method to close report file at the end to each test execution
	 * @author	 	Rohit Prajapati
	 * @since	 	13-Dec-2016 
	 * *******************************************************************************/
	/*public void closeReportFile()
	{
		String reportLocation = Driver_Script.htmlTestResultFolder + "\\" + Driver_Script.testcase + ".txt";
		File file = new File(reportLocation);
		try {
			if(!Driver_Script.htmlTestExecStatus){
				if(executionResult.contains("EXECUTION STATUS: PASS")){
					executionResult = executionResult.replace("EXECUTION STATUS: PASS", "EXECUTION STATUS: FAIL");
				}
			}
		} catch(Exception e){}
		executionResult = executionResult + "</table></body></html>";
		writeTextFile(reportLocation, executionResult, false, false);
		// Change Report file extension to .html
		file.renameTo(new File(reportLocation.replace(".txt", ".html")));
	}*/
	
	/********************************************************
	 * Description: Read Entire Text File Content
	 * @author 		Rohit Prajapati
	 * @since		05-Jun-2015
	 * @return		Text content of the entire file
	 * ******************************************************/
	public String readEntireTextFile(String fileLocation)
	{
		String currentLine = "";
		try(BufferedReader br = new BufferedReader(new FileReader(new File(fileLocation)))) {
			StringBuilder sb = new StringBuilder();
			currentLine = br.readLine();
			while(currentLine!= null) {
				sb.append(currentLine);
				currentLine = br.readLine();
			}
			currentLine = sb.toString();
			br.close();
		} catch(FileNotFoundException e){
			logReport("Verify File Exist", "File NOT Found at : " + fileLocation, "FAIL", null, false);
		} catch(IOException e){
			e.printStackTrace();
		}
		return currentLine;
	}
	
	/********************************************************
	 * Description:	Method to write data into Text file
	 * @author 		Rohit Prajapati
	 * @since		05-Jun-2015
	 * ******************************************************/
	public void writeTextFile(String filePath, String content, boolean appendValue, boolean addNewLine)
	{
		try{	
			File file = new File(filePath);
	
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), appendValue);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			if(addNewLine){
				bw.newLine();
			}
			bw.flush();
			bw.close();
			System.out.println("Done");
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/******************************************************************
	 * Description:	Method to copy File from source to destination
	 * @author 		Rohit Prajapati
	 * @since		23-Dec-2016
	 * ****************************************************************/@SuppressWarnings("resource")
	public void copyFile(String sourceFilePath, String destinationFolder)
	{
		try{
			File source = new File(sourceFilePath);
			String fileName = new File(sourceFilePath).getName();
			File destination = new File(destinationFolder + "//" + fileName);
			
			FileChannel sourceChannel = new FileInputStream(source).getChannel();
			FileChannel destChannel = new FileOutputStream(destination).getChannel();
	    	
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
			sourceChannel.close();
			destChannel.close();
			System.out.println("File Copy Successful");
		} catch (FileNotFoundException e) {
			logReport("Copy File from Source to Destination", "File NOT Present at - " + sourceFilePath, "FAIL", null, false);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**********************************************
	 * Description: To Create a Batch Report
	 * @author 		Rohit Prajapati
	 * @since       25-Apr-2017
	 * ********************************************/
	/*public void createBatchReport()
	{
		int passCount = 0;
		int failCount = 0;
		for(int iterator=0; iterator<Driver_Script.TestName.length; iterator++){
			if(Driver_Script.testExecStatus.get(Driver_Script.TestName[iterator]).toString().equalsIgnoreCase("PASS")) {
				passCount = passCount+1;
			}
			else if(Driver_Script.testExecStatus.get(Driver_Script.TestName[iterator]).toString().equalsIgnoreCase("FAIL")) {
				failCount = failCount+1;
			}
		}
		String appType = "Web";
		String execSummary = "<html>\n" +
				"<head>\n" +
					"<title>Batch Execution Report</title>\n" +
					"<link rel='stylesheet' type='text/css' href='../" + new File(Driver_Script.htmlBatchFolder).getName() + "/Supporting Files/design.css'>\n" +
					"<script src='../" + new File(Driver_Script.htmlBatchFolder).getName() + "/Supporting Files/Chart.bundle.js'></script>\n" +
					"<script src='../" + new File(Driver_Script.htmlBatchFolder).getName() + "/Supporting Files/jquery.min.js'></script>\n" +
				"</head>\n" +
		 
				"<body>\n" +
					"<h2 font-family='Trebuchet MS' align='center'>Batch Execution Summary - " + Driver_Script.ApplicationName + "</h2>\n" +
					"<div class='inner-container' style='width: 1510px; margin: 0 auto;'>\n" +
						"<div class='centered-content1' style='width: 380px;'>\n" +
							"<div style='width:100%'>\n" +
							"<canvas id='chart-area'/>\n" +
							"</div>\n" +
						"</div>\n" +
				
						"<div class='centered-content2'>\n" +
							"<table class='two' align='left'>\n" +
								"<col width='200'>\n" +
								"<col width='200'>\n" +
								"<col width='200'>\n" +
								"<tr>\n" +
									"<th class='one2'>SYSTEM ENVIRONMENT</th>\n" +
									"<th class='one2'>OPERATING SYSTEM</th>\n" +
									"<th class='one2'>APPLICATION TYPE</th>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td class='one'>" + Driver_Script.SystemEnv + "</td>\n" +
									"<td class='one'> " + System.getProperty("os.name") + "</td>\n" +
									 "<td class='one'>" + appType + "</td>\n" +
								"</tr>\n" +
							"</table>\n" +
						"</div>\n" +
					"</div>\n" +
					"<script>\n" + 
						"var config = {\n" +
							"type: 'doughnut',\n" + 
							"data: {\n" +
							"datasets: [{\n" + 
							"data: [" + passCount + "," + failCount + "],\n" + 
							"backgroundColor: ['#319B1C', '#FF0000'],\n" + 
							"label: 'Dataset 1'}],\n" + 
							"labels: ['PASS', 'FAIL']\n" + 
						"},\n" +
						"options: {\n" +
							"responsive: true,\n" +
							"legend: {\n" +
								"position: 'bottom',\n" +
							"},\n" +
							"title: {\n" +
							"display: true,\n" +
							"text: 'Test Execution Summary'\n" +
						"},\n" +
						"animation: {\n" +
							"animateScale: true,\n" +
							"animateRotate: true\n" + 
							"}\n" +
						"}\n" +
					"};\n" +
			
					"window.onload = function() {\n" +
						"var ctx = document.getElementById('chart-area').getContext('2d');\n" +
						"window.myDoughnut = new Chart(ctx, config);\n" + 
					"};\n" +
				"</script>\n" +

		 		"<h2 font-family='Trebuchet MS' align='center'>Test Execution Detail</h2>\n" +
		 		"<table class='two' align='center'>\n" +
		 			"<col width='90'>\n" +
		 			"<col width='220'>\n" +
		 			"<col width='550'>\n" +
		 			"<col width='110'>\n" +
		 			"<col width='120'>\n" +

		 			"<tr>\n" +
		 				"<th class='two'>Sl NO.</th>\n" +
		 				"<th class='two'>TESTCASE NAME</th>\n" +
		 				"<th class='two'>TEST DESCRIPTION</th>\n" +
		 				"<th class='two'>TEST STATUS</th>\n" +
		 				"<th class='two'>DETAIL REPORT</th>\n" +
		 			"</tr>\n";
		writeTextFile(Driver_Script.htmlBatchFolder + "\\Batch_Execution_Report.txt", execSummary, true, true);
		addTestEntry();
		
		File file = new File(Driver_Script.htmlBatchFolder + "\\Batch_Execution_Report.txt");
		file.renameTo(new File(Driver_Script.htmlBatchFolder + "\\Batch_Execution_Report.html"));
	}*/	
	
	/****************************************************
	 * Description: Add Test Entry in Batch Result
	 * @author 		Rohit Prajapati
	 * @since		25-Apr-2017 
	 * **************************************************/
	/*public void addTestEntry()
	{
		String mark = "";
		for(int iterator=0; iterator<Driver_Script.TestName.length; iterator++){
			// Collect Test Level Status
			try {
				if(Driver_Script.testExecStatus.get(Driver_Script.TestName[iterator]).toString().equalsIgnoreCase("PASS")) {
					resultColor = "#319B1C";
					mark = "&#10004";
				}
				else if(Driver_Script.testExecStatus.get(Driver_Script.TestName[iterator]).toString().equalsIgnoreCase("FAIL")) {
					resultColor = "#FF0000";
					mark = "&#10008";
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
			}
			
			String href = "../" + new File(Driver_Script.htmlBatchFolder).getName() + "/" + Driver_Script.htmlTestFolderNameList.get(iterator) + "/" +  Driver_Script.TestName[iterator] + ".html";
			String testEntry = "<tr>\n" +
				"<td class='one'>" + Integer.toString(iterator+1) + ".</td>\n" +
				"<td class='two'>" + Driver_Script.TestName[iterator] + "</td>\n" +
				"<td class='two'>" + Driver_Script.TestCaseDescription[iterator] + "</td>\n" +
				"<td class='one'><font color='" + resultColor + "'>" + mark + " " + Driver_Script.testExecStatus.get(Driver_Script.TestName[iterator]) + "</font></td>\n" +
				"<td class='one'><a href='" + href + "'<font color='#1116A5'>View Report</font></a></td>\n" +
			"</tr>\n";
			
			writeTextFile(Driver_Script.htmlBatchFolder + "\\Batch_Execution_Report.txt", testEntry, true, true);
		}
		
		String htmlEnd = "</table>\n" +
				"</body>\n" +
			"</html>";
		writeTextFile(Driver_Script.htmlBatchFolder + "\\Batch_Execution_Report.txt", htmlEnd, true, false);
	}*/
}
