package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class CollectTestData {

	static String testDataDir = System.getProperty("user.dir") +"\\TestData\\TestData_iNeo.xlsx";
	public static ReportLibrary htmlLib = new ReportLibrary();
	public static HashMap<String, String> testData = new HashMap<String, String>();
	public static String browser ="";
	public static String url ="";
	public static String userName = "";
	public static String password = "";
	public static String flightDate = "";
	public static String origin = "";
	public static String destination = "";
	public static String flightNumber="";
	public static String Regno= "";
	public static String ACR="";
	public static String name="";
	public static String manufacture="";
	public static String engine="";
	public static String engineType="";
	public static String Delv="";
	public static String remarks="";
	public static String acsubtype="";
	public static String Rotcode="";
	public static String Etops="";
	public static String flightRange="";
	public static String tcname="";
	public static String message_date="";
	public static String startDate = "";
	public static String endtDate = "";
	public static String type = "";
	public static String mailUserName="";
	public static String mailPassword="";
	public static String messageType="";
	public static String fromhr;
	public static String tohr;
	public static String Arrterminal;
	public static String Arrgate;
	public static String Arrstand;
	public static String Depterminal;
	public static String Depgate;
	public static String Depstand;
	public static String keyword;
	public static String delaycode;
	public static String userToEdit;
	public static String userToEditPassword;
	public static String reroutestation;
	
	public static void main(String scriptName) {
		HashMap<String, String> data = new HashMap<String, String>();
		data = getTestData(scriptName);
		browser = data.get("Browser");
		url = data.get("iNeo_URL");
		userName = data.get("User_Id");
		password = data.get("User_Pwd");
		flightNumber = data.get("Flight_Number");
		flightDate = data.get("Flight_Date");
		origin = data.get("Origin");
		destination = data.get("Destination");
		Regno = data.get("Registration_No");
		ACR=data.get("ACR");
		name=data.get("Name");
		manufacture=data.get("Manufacturer");
		engine=data.get("Engine");
		engineType=data.get("Engine_Type");
		Delv=data.get("Delv");
//		remarks=data.get("Remarks");
		acsubtype=data.get("AC_Subtype");
		Rotcode=data.get("Rotcode");
		Etops=data.get("Etops");
		flightRange=data.get("Flight_Range");
		tcname=data.get("TestCaseName");
		message_date=data.get("Message_Date");
		startDate =data.get("StartDate");
		endtDate =data.get("EndDate");
		type =data.get("Type");
		mailUserName=data.get("Email_Username");
		mailPassword=data.get("Email_Password");
		messageType=data.get("Message_Type");
		fromhr=data.get("FromHr");
		tohr=data.get("Tohr");
		Arrterminal=data.get("Arrterminal");
		Arrgate=data.get("Arrgate");
		Arrstand=data.get("Arrstand");
		Depterminal=data.get("Depterminal");
		Depgate=data.get("Depgate");
		Depstand=data.get("Depstand");
		keyword=data.get("Keyword");
		delaycode=data.get("Delaycode");
		userToEdit=data.get("UserToEdit");
		userToEditPassword=data.get("UserToEditPassword");
		reroutestation=data.get("Divertedstation");
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap<String, String> getTestData(String scriptName){
		if(testData.get("ScriptName") == null) {
			try {
				FileInputStream inFile = new FileInputStream(new File(testDataDir));
				XSSFWorkbook workbook = new XSSFWorkbook(inFile);
				XSSFSheet worksheet = workbook.getSheet("TestData");			
				// Get Column No
				XSSFRow headerRow =  worksheet.getRow(0);
		        Iterator headerIterator = headerRow.cellIterator();	        
		        // Get Row
		        Iterator rows = worksheet.rowIterator();
		        short columnNo = 1;
		        while (rows.hasNext()) {
		            XSSFRow row = (XSSFRow) rows.next();
		            XSSFCell col = row.getCell(columnNo);
		            int colCount = row.getLastCellNum();
		            if(col != null && col.getStringCellValue().equalsIgnoreCase(scriptName)){
		            	inFile = new FileInputStream(new File(testDataDir)); 
			            workbook = new XSSFWorkbook(inFile); 
			            XSSFCell cellValue = null; 
			            while (columnNo<=colCount) {
			            	XSSFCell headerColumn = (XSSFCell)headerIterator.next();
				            if(headerColumn != null && !headerColumn.getStringCellValue().contentEquals("") && columnNo>=2) {
				            	cellValue = worksheet.getRow(row.getRowNum()).getCell(columnNo-1);
				            	testData.put(headerColumn.getStringCellValue(), cellValue.getStringCellValue());
				            }
				            columnNo++;
				        }
			            inFile.close();
			            break;
		            }
		        }
		        workbook.close();
			} catch (FileNotFoundException fnfe) {
			    System.out.println("File Does Not Exists At " + testDataDir);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (NullPointerException npe) {
				System.out.println("Cell Value is Empty in File at " + testDataDir);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return testData;
	}
	
	
	public HashMap<String, String> getData(){
		HashMap<String, String> testData = new HashMap<String, String>();
		String currentLine = "";
		try(BufferedReader br = new BufferedReader(new FileReader(new File(testDataDir)))) {
			currentLine = br.readLine();
			while(currentLine!= null) {
				testData.put(currentLine.split(":=")[0], currentLine.split(":=")[1]);
				currentLine = br.readLine();
			}
			br.close();
		} catch(FileNotFoundException e){
			htmlLib.logReport("Verify File Exist", "File NOT Found at : " + testDataDir, "FAIL", null, false);
		} catch(IOException e){
			System.out.println(e.getMessage());
		}
		return testData;
	}

	
	
}
