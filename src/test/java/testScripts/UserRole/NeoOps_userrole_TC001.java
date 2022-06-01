package testScripts.UserRole;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.IFlightNeo_HomePage;
import pageObjects.IFlightNeo_LoginPage;
import pageObjects.IFlightNeo_ManageUsers;
import utilities.CollectTestData;
import utilities.Driver;

public class NeoOps_userrole_TC001 {

	public utilities.ReportLibrary htmlLib = new utilities.ReportLibrary();
	public utilities.CommonLibrary com = new utilities.CommonLibrary();
	public utilities.BusinessFunctions bizComm = new utilities.BusinessFunctions();
	String[] lists = this.getClass().getName().split("\\.");
	String tcName = lists[lists.length - 1];
	static WebDriver driver;
	static WebDriverWait wait;

	@BeforeMethod
	void setUp() {
		// Set Up Initial Script Requirement
		Driver.setUpTestExecution(tcName, "User role (permission add/delete) and switch roles ");
		// launch application
		String browser = CollectTestData.browser;
		String url = CollectTestData.url;
		driver = IFlightNeo_LoginPage.launchApplication(browser, url);
	}

	@Test
	public void login() throws Exception {
		try {
			// Collect Test Data
			String username = CollectTestData.userName;
			String password = CollectTestData.password;
			String userToEdit = CollectTestData.userToEdit;
			String userToEditPwd = CollectTestData.userToEditPassword;
			List<String> userRole = Arrays.asList("OPS_CONTROLLER", "HCC_TEAM");

			//// Login as Admin role
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			// Click on Admin->Manage Users
			IFlightNeo_HomePage.openAdminManageUsers(driver);
			/*
			 * Search a Username available for Testing Scroll to the right of the username
			 * record & click on Pencil icon. Click on Assign Role dropdown , Select below
			 * roles i) OPS_Controller ii)HCC Team Click on Save button
			 */
			IFlightNeo_ManageUsers.editUser(driver, userToEdit, userRole);
			// Log out & Log in as the updated user
			IFlightNeo_HomePage.waitTillPageRefreshCompletes(driver);
			IFlightNeo_HomePage.signOut(driver);
			IFlightNeo_LoginPage.login(driver, userToEdit, userToEditPwd);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			// Click on the Username form the top Left corner of the system.
			// Click on Switch Role
			IFlightNeo_HomePage.switchUserRole(driver, "OPS_CONTROLLER");
			// Log out & Log in as the admin user
			IFlightNeo_HomePage.waitTillPageRefreshCompletes(driver);
			IFlightNeo_HomePage.signOut(driver);
			IFlightNeo_LoginPage.login(driver, username, password);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			// Click on Admin->Manage Users
			IFlightNeo_HomePage.openAdminManageUsers(driver);
			// Search the same Username available for Testing
			IFlightNeo_ManageUsers.deselectRoles(driver, userToEdit, userRole);
			// Log out & Log in as the updated user
			IFlightNeo_HomePage.waitTillPageRefreshCompletes(driver);
			IFlightNeo_HomePage.signOut(driver);
			IFlightNeo_LoginPage.login(driver, userToEdit, userToEditPwd);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			//Verify that as the roles are removed now those are not available in the Assign Role dropdown
			IFlightNeo_HomePage.verifyRolesNotPresent(driver,userRole);
		}

		catch (Exception e) {
			System.out.println("The exception occured for this TC is" + e);
			e.printStackTrace();
			htmlLib.logReport("Status of Test Case", "Test Case Failed"+e.getMessage(), "Fail", driver, true);

		}

	}

	@AfterMethod
	public void closeTest() {
		Driver.tearDownTestExecution(driver);
	}

}
