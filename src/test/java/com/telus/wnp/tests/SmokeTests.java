package com.telus.wnp.tests;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.utils.Status;
import com.telus.wnp.pages.CODASearchPage;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.pages.PACSearchPage;
import com.telus.wnp.pages.RCMPage;
import com.telus.wnp.pages.SMGICPPortDetailsPage;
import com.telus.wnp.pages.SmartDesktopDashboardPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.SMGICPPortDetailsPageSteps;
import com.telus.wnp.steps.SmartDesktopSearchPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.SystemProperties;

public class SmokeTests extends BaseTest {

	String sub = null;
	String ban = null;
	String testCaseName = null;
	String scriptName = null;
	String testCaseDescription = null;
	String environment = null;
	String testDataFilePath = null;
	JSONObject testDataJson = null;
	JSONObject tnTestData = null;
	JSONObject sdTestData = null;
	JSONObject pacTestData = null;
	JSONObject rcmTestData = null;
	JSONObject codaTestData = null;
	JSONObject smgTestData = null;

	/**
	 * @param iTestContext
	 */
	@BeforeTest(alwaysRun = true)
	public void BeforeMethod(ITestContext iTestContext) {

		testCaseName = this.getClass().getName();
		scriptName = GenericUtils.getTestCaseName(testCaseName);
		testCaseDescription = "The purpose of this test case is to verify \"" + scriptName + "\" workflow";

		testDataFilePath = "\\TestData\\" + scriptName + ".json";
		JSONObject jsonFileObject = GenericUtils.getJSONObjectFromJSONFile(testDataFilePath);
		environment = SystemProperties.EXECUTION_ENVIRONMENT;
		testDataJson = jsonFileObject.getJSONObject(environment);

		tnTestData = testDataJson.getJSONObject("SUB_AND_BAN_DETAILS");
		sdTestData = testDataJson.getJSONObject("SMART_DESKTOP_CONSTANTS");
		rcmTestData = testDataJson.getJSONObject("RCM_CONSTANTS");
		codaTestData = testDataJson.getJSONObject("CODA_CONSTANTS");

		sub = tnTestData.getString("TELUS_SUB");
		ban = tnTestData.getString("TELUS_BAN");

	}

	@Test(groups = { "SmokeTests", "SanityTests", "CompleteRegressionSuite" })
	public void testMethod_SmokeTests(ITestContext iTestContext) throws Exception {

		Reporting.setNewGroupName("Automation Configurations / Environment Details & Data Setup");
		Reporting.logReporter(Status.INFO,
				"Automation Configuration - Environment Configured for Automation Execution [" + environment + "]");
		Reporting.logReporter(Status.INFO, "Test Data File for " + scriptName + " placed at : " + testDataFilePath);
		Reporting.printAndClearLogGroupStatements();

		/*** Test Case Details ***/
		Reporting.setNewGroupName("Test Case Details");
		Reporting.logReporter(Status.INFO, "Test Case Name : [" + scriptName + "]");
		Reporting.logReporter(Status.INFO, "Test Case Description : [" + testCaseDescription + "]");
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SMOKE TEST");
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		LoginPageSteps.appLogin_SD();
		SmartDesktopSearchPageSteps.verifySDHomePageIsDisplayed();

		SmartDesktopSearchPageSteps.searchPhoneNo(sub);
		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.tnStatus);
		String stat = SmartDesktopDashboardPage.getTNStatus();
		boolean actualStatus = false;
		if (!stat.isEmpty()) {
			actualStatus = true;
		}
		Validate.assertEquals(actualStatus, true, "SD - Subscriber Status not displayed", false);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SMOKE TEST");
		RCMPage RCMPage = new RCMPage();
		LoginPageSteps.appLogin_RCM();
		RCMPageSteps.verifyRCMHomePageIsDisplayed();

		BaseSteps.Clicks.clickElement(RCMPage.inventory);

		RCMPageSteps.enterRequiredDetailsForTNSearchInRCM(sub, "UMSISDN");

		String provisioningStat = null;

		try {
			provisioningStat = RCMPage.provisioningStatus();
		} catch (Exception e) {
			provisioningStat = RCMPage.getNoSearchResultFoundMsg();
		}
		boolean actualProvisioningStat = false;

		if (!provisioningStat.isEmpty()) {
			actualProvisioningStat = true;
		}
		Validate.assertEquals(actualProvisioningStat, true, "RCM - Subscriber Provisioning Status not displayed",
				false);

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR NAVIGATING TO PAC DASHBOARD PAGE **/
		Reporting.setNewGroupName("PAC - SMOKE TEST");
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		PACSearchPage PACSearchPage = new PACSearchPage();

		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.verifyPACHomePageIsDisplayed();
		PACSearchPageSteps.searchPhoneNo(sub);
		String pacStatus = null;

		try {
			PACDashboardPageSteps.handleMultipleRecordsDisplayed();
			pacStatus = PACDashboardPage.getPortStatus();
		} catch (Exception e) {
			pacStatus = PACSearchPage.portOSPCancelRequestStatus();
		}
		boolean pacStatusDisplayed = false;
		if (!pacStatus.isEmpty())
			pacStatusDisplayed = true;
		Validate.assertEquals(pacStatusDisplayed, true, "PAC Dashboard Page not displayed", false);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR NAVIGATE TO SMG ICP PORT DETAILS PAGE ***/
		Reporting.setNewGroupName("SMG - SMOKE TEST");
		SMGICPPortDetailsPageSteps.navigateToSMGICPPortDetailsPage();
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Waits.waitUntilPageLoadComplete();
		Validate.assertEquals(SMGICPPortDetailsPage.portTypeDisplayedStatus(), true,
				"SMG - PORT Details Page not displayed", false);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SMOKE TEST");
		LoginPageSteps.appLogin_CODA();

		CODASearchPageSteps.verifyCODAHomePageIsDisplayed();
		CODASearchPageSteps.selectEnvironment();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		CODASearchPage CodaSearchPage = new CODASearchPage();

		CODASearchPageSteps.searchPhoneNo(codaTestData, sub);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();

		String codaStatus = null;

		try {
			codaStatus = CodaSearchPage.getSubscriberStatus();
		} catch (Exception e) {
			codaStatus = CodaSearchPage.getNoSearchResultsMsg();
		}
		boolean codaStatusDisplayed = false;
		if (!codaStatus.isEmpty())
			codaStatusDisplayed = true;
		Validate.assertEquals(codaStatusDisplayed, false, "CODA - Dashboard Page not displayed", false);

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();
	}

	/**
	 * Close any opened browser instances
	 */
	@AfterMethod(alwaysRun = true)
	public void afterTest() {
		Reporting.setNewGroupName("Close All Browser");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();
	}
}
