package com.telus.wnp.tests;

import java.util.Map;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.utils.Status;
import com.telus.api.test.utils.APIJava;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.RKPortalPageSteps;
import com.telus.wnp.steps.SMGICPPortDetailsPageSteps;
import com.telus.wnp.steps.SmartDesktopDashboardPageSteps;
import com.telus.wnp.steps.SmartDesktopSearchPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: Koodo Prepaid to Foreign Port out - SUP 1 (Cancel)> AUTHOR:
 * x241410 > Test Case: TC08_WNP_REG_Koodo Prepaid to Foreign Port out _ Cancel
 * from SMG _SUP1
 ****************************************************************************
 */

public class WLSC_169298_WNP_REG_KPREToForeignSUP1 extends BaseTest {

	String koodoPrepaidNum = null;
	String koodoPrepaidBan = null;
	String sub = null;
	String ban = null;
	String testDataFilePath = null;
	String testCaseName = null;
	String scriptName = null;
	String internalReqNumFromPAC = null;
	String expectedStatus = null;
	String formattedSub = null;
	String smgRequestNum = null;
	String testCaseDescription = null;
	String environment = null;
	JSONObject testDataJson = null;
	JSONObject tnTestData = null;
	JSONObject sdTestData = null;
	JSONObject pacTestData = null;
	JSONObject rcmTestData = null;
	JSONObject codaTestData = null;
	JSONObject smgTestData = null;
	JSONObject lsmsTestData = null;
	JSONObject rkPortalTestData = null;
	JSONObject smsResponseTestData = null;

	/**
	 * @param iTestContext
	 */
	@BeforeTest(alwaysRun = true)
	public void BeforeMethod(ITestContext iTestContext) {
		testCaseName = this.getClass().getName();
		scriptName = GenericUtils.getTestCaseName(testCaseName);
		testCaseDescription = "The purpose of this test case is to verify \"" + scriptName + "\" workflow.";

		testDataFilePath = "\\TestData\\" + scriptName + ".json";
		JSONObject jsonFileObject = GenericUtils.getJSONObjectFromJSONFile(testDataFilePath);
		environment = SystemProperties.EXECUTION_ENVIRONMENT;
		testDataJson = jsonFileObject.getJSONObject(environment);

		tnTestData = testDataJson.getJSONObject("SUB_AND_BAN_DETAILS");
		sdTestData = testDataJson.getJSONObject("SMART_DESKTOP_CONSTANTS");
		pacTestData = testDataJson.getJSONObject("PAC_CONSTANTS");
		rcmTestData = testDataJson.getJSONObject("RCM_CONSTANTS");
		codaTestData = testDataJson.getJSONObject("CODA_CONSTANTS");
		smgTestData = testDataJson.getJSONObject("SMG_CONSTANTS");
		lsmsTestData = testDataJson.getJSONObject("LSMS_CONSTANTS");
		rkPortalTestData = testDataJson.getJSONObject("RK_PORTAL_CONSTANTS");
		smsResponseTestData = testDataJson.getJSONObject("SMS_RESPONSE_CONSTANTS");

		sub = tnTestData.getString("TELUS_SUB");
		ban = tnTestData.getString("TELUS_BAN");

	}

	@Test(groups = { "WLSC_169298_KoodoPrepaidToForeignSUP1Cancel", "Port-Out", "CompleteRegressionSuite" })
	public void testMethod_KoodoPrepaidToForeignSUP1Cancel(ITestContext iTestContext) throws Exception {

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
		Reporting.setNewGroupName("SMART DESKTOP - SUB PRE VALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * PRE Validation Steps
		 */

		// *** STEPS FOR ACTIVATE KOODO PREPAID NUMBER FROM RK PORTAL *
		Reporting.setNewGroupName("RK PORTAL - KOODO PREPAID ACTIVATION");
		boolean newNumberFlag = rkPortalTestData.getBoolean("NEW_NUMBER_FLAG");
		RKPortalPageSteps.activateKoodoPrepaidNumber_NEW(rkPortalTestData, "", "", newNumberFlag);
		koodoPrepaidNum = RKPortalPageSteps.activatedKoodoPrepaidMobileNumber;
		koodoPrepaidBan = RKPortalPageSteps.activatedKoodoPrepaidAccountNumber;
		koodoPrepaidNum = koodoPrepaidNum.replaceAll(" ", "").replaceAll("-", "").replaceAll("\\)", "")
				.replaceAll("\\(", "");
		Reporting.logReporter(Status.INFO, "ACtivated KPRE Subscriber TN is: " + koodoPrepaidNum);
		Reporting.logReporter(Status.INFO, "ACtivated KPRE Subscriber BAN is: " + koodoPrepaidBan);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, koodoPrepaidNum, "active");
		CODASearchPageSteps.verifySubOrderStatus(codaTestData, koodoPrepaidNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		

		/**
		 * 
		 * 
		 * pt 148 test data koodoPrepaidNum = "4161285125"; koodoPrepaidBan =
		 * "10000001922007";
		 * 
		 */

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, koodoPrepaidNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * 
		 * Test Case Flow Steps
		 * 
		 */

		/*** STEPS FOR NAVIGATE TO SMG AND INITIATE PORT OUT REQUEST ***/
		Reporting.setNewGroupName("SMG - SAVE PORT OUT REQUEST");
		SMGICPPortDetailsPageSteps.saveCreatePortRequestDetailsForSingleSub(smgTestData, koodoPrepaidNum,
				koodoPrepaidBan, "portout");
		smgRequestNum = SMGICPPortDetailsPageSteps.SMGRequestNumber;
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, koodoPrepaidNum, "preValidation");
		internalReqNumFromPAC = PACDashboardPageSteps.getPACRequestNumber();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - COMM HISTORY VALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		SmartDesktopDashboardPageSteps.navigateToCommunicationHistoryTabToVerifySMSDetails(sdTestData, koodoPrepaidNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING 8k DELAY CODE FROM SMG ***/
		Reporting.setNewGroupName("SMG - DELAY CODE 8k VALIDATION");
		SMGICPPortDetailsPageSteps.navigateToSMGAndVerifyDelyCode8k(smgTestData, koodoPrepaidNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR RESPONDING YES TO 2FA SMS AUTHENTICATION **/
		Reporting.setNewGroupName("2FA SMS API CALL - RESPONDED YES");
		Reporting.logReporter(Status.INFO, "STEP===Responding Yes to 2FA SMS===");

		String expectedStatus = smsResponseTestData.getString("EXPECTED_STATUS");
		String response = smsResponseTestData.getString("2FA_SMS_RESPONSE");
		String laguageCode = smsResponseTestData.getString("LANGUAGE_CODE");
		String localDate = GenericUtils.getFormattedSystemDate("yyyy-mm-ddThh:mm:ss:ttt");

		System.setProperty("karate.pacInternalReqNum", internalReqNumFromPAC);
		System.setProperty("karate.TN", koodoPrepaidNum);
		System.setProperty("karate.responseYN", response);
		System.setProperty("karate.dateTime", localDate);
		System.setProperty("karate.languageCode", laguageCode);
		System.setProperty("karate.BAN", koodoPrepaidBan);

		Map<String, Object> apiOperation1 = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/Test_2FA_SMS_Response.feature");

		Reporting.logReporter(Status.INFO,
				"API Operation status: " + apiOperation1.get("sendResponseSMSAPIfeatureCallStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation1.size());
		Reporting.logReporter(Status.INFO,
				"API Operation Request: " + apiOperation1.get("sendResponseSMSAPIfeatureCallRequest"));
		Reporting.logReporter(Status.INFO,
				"API Operation response: " + apiOperation1.get("sendResponseSMSAPIfeatureCallResponse"));

		String actualStatus = apiOperation1.get("status").toString();
		String actualStatusMsg = apiOperation1.get("statusMessage").toString();
		String actualOrderId = apiOperation1.get("orderId").toString();

		Validate.assertEquals(actualStatus, expectedStatus, "2fa sms response mismtach", true);
		Validate.assertEquals(actualOrderId, internalReqNumFromPAC, "2fa sms order Id mismtach", true);
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, koodoPrepaidNum, "intermediate1");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR NAVIGATE TO SMG AND CREATE CANCEL REQUEST - SUP1 ***/
		Reporting.setNewGroupName("SMG - CREATE CANCEL REQUEST - SUP1");
		SMGICPPortDetailsPageSteps.performSUP1FromSMGICPPortDetailsPage(smgTestData, koodoPrepaidNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * POST Validation Steps
		 */

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB POSTVALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, koodoPrepaidNum, "postValidation");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB POSTVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, koodoPrepaidNum, "active");
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
