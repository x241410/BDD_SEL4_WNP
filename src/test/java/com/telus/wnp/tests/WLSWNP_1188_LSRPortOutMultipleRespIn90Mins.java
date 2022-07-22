package com.telus.wnp.tests;

import java.util.Map;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.utils.Status;
import com.intuit.karate.XmlUtils;
import com.telus.api.test.utils.APIJava;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LSRPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.SMGASMSPageSteps;
import com.telus.wnp.steps.SMGICPPortDetailsPageSteps;
import com.telus.wnp.steps.SMGICPPortSelectionPageSteps;
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
 * > DESCRIPTION: Response_All requests within 90 mins window from LSR > AUTHOR:
 * x241410 > Test Case: "TC03_WNP_REG_Submit Port Out Request from Telus to WLN
 * 2782_2FA sent_Send Negative Response _Submit Port Out Request 8084_2FA
 * sent_Send Negative Response _Submit Port Out Request 8083_2FA sent_Send
 * Positive Response_All requests within 90 mins window"
 ****************************************************************************
 */

public class WLSWNP_1188_LSRPortOutMultipleRespIn90Mins extends BaseTest {

	String sub = null;
	String ban = null;
	String internalReqNumFromPAC = null;
	String testCaseName = null;
	String scriptName = null;
	String testCaseDescription = null;
	String environment = null;
	String smgRequestNum = null;
	String testDataFilePath = null;
	JSONObject testDataJson = null;
	JSONObject tnTestData = null;
	JSONObject sdTestData = null;
	JSONObject pacTestData = null;
	JSONObject rcmTestData = null;
	JSONObject codaTestData = null;
	JSONObject smgTestData = null;
	JSONObject lsmsTestData = null;
	JSONObject smsResponseTestData = null;
	JSONObject lsrPortalTestData = null;
	JSONObject smgASMSTestData = null;
	String portType = null;
	String oldNetwork = null;
	String expectedText = null;
	String actualStatus = null;
	String actualStatusMsg = null;
	String actualOrderId = null;
	String expectedStatus = null;
	String response = null;
	String laguageCode = null;
	String localDate = null;
	String expectedResponseType = null;
	String serviceProvider = null;
	int maxTimeInSeconds = 0;

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
		pacTestData = testDataJson.getJSONObject("PAC_CONSTANTS");
		rcmTestData = testDataJson.getJSONObject("RCM_CONSTANTS");
		codaTestData = testDataJson.getJSONObject("CODA_CONSTANTS");
		smgTestData = testDataJson.getJSONObject("SMG_CONSTANTS");
		lsmsTestData = testDataJson.getJSONObject("LSMS_CONSTANTS");
		smsResponseTestData = testDataJson.getJSONObject("SMS_RESPONSE_CONSTANTS");
		smgASMSTestData = testDataJson.getJSONObject("SMG_ASMS_CONSTANTS");
		lsrPortalTestData = testDataJson.getJSONObject("LSR_PORTAL_CONSTANTS");

		sub = tnTestData.getString("TELUS_SUB");
		ban = tnTestData.getString("TELUS_BAN");
		serviceProvider = lsrPortalTestData.getString("SERVICE_PROVIDER");
		maxTimeInSeconds = lsrPortalTestData.getInt("MAX_WAITTIME_IN_SECONDS");

	}

	@Test(groups = { "WLSWNP_1188_LSRPortOutMultipleRespIn90Mins", "Port-Out", "WireLine", "CompleteRegressionSuite" })
	public void testMethod_LSRPortOutMultipleResp(ITestContext iTestContext) throws Exception {

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

		/**
		 * PRE Validation Steps
		 */

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, sub, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// Test Case Flow Steps

		// Part-1 worked
		// String ponNumber= "TL80192774";
		// String spid = lsrPortalTestData.getString("SPID_1");
		// String serviceProvider = lsrPortalTestData.getString("SERVICE_PROVIDER");

		// Second Time: Not picked up
		// String spid1 = lsrPortalTestData.getString("SPID_1");
		// String ponNumber1 = "TL42559802";

		// Set 1- String ponNumber1 = "TL20293924";

		// String spid1 = lsrPortalTestData.getString("SPID_1");
		// String ponNumber1 = "TL29493604";

		/**
		 * 
		 * PART - 1
		 * 
		 */

		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST");
		String spid1 = lsrPortalTestData.getString("SPID_1");
		LSRPageSteps.createWLNPortOutRequestFromLSR(lsrPortalTestData, sub, ban, spid1, serviceProvider);
		String ponNumber1 = LSRPageSteps.ponNumber;
		Reporting.logReporter(Status.INFO, "ponNumber1 is: " + ponNumber1);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR WAITING FOR REQUIRED STATUS TO BE DISPLAYED IN LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - WAIT FOR REQUIRED STATUS");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("sent", sub, ban, spid1, serviceProvider, ponNumber1,
				maxTimeInSeconds);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.searchPhoneNo(sub);
		PACDashboardPageSteps.handleMultipleRecordsDisplayed();
		PACDashboardPageSteps.performSearchForPACRecordsDisplayed(sub, 30);
		PACDashboardPageSteps.verifyPortStatusFromPAC(pacTestData, "preValidation");
		PACDashboardPageSteps.verifyPortSummary(pacTestData, "preValidation");

		internalReqNumFromPAC = PACDashboardPageSteps.getPACRequestNumber();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		SmartDesktopDashboardPageSteps.navigateToCommunicationHistoryTabToVerifySMS(sdTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR RESPONDING NON TO 2FA SMS AUTHENTICATION **/
		Reporting.setNewGroupName("2FA SMS API CALL - RESPONDED NON");
		Reporting.logReporter(Status.INFO, "STEP===Responding non to 2FA SMS===");

		expectedStatus = smsResponseTestData.getString("EXPECTED_STATUS");
		response = smsResponseTestData.getString("2FA_SMS_RESPONSE_NON");
		laguageCode = smsResponseTestData.getString("LANGUAGE_CODE");
		localDate = GenericUtils.getFormattedSystemDate("yyyy-mm-ddThh:mm:ss:ttt");

		System.setProperty("karate.pacInternalReqNum", internalReqNumFromPAC);
		System.setProperty("karate.TN", sub);
		System.setProperty("karate.responseYN", response);
		System.setProperty("karate.dateTime", localDate);
		System.setProperty("karate.languageCode", laguageCode);
		System.setProperty("karate.BAN", ban);

		Map<String, Object> apiOperation1 = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/Test_2FA_SMS_Response.feature");

		Reporting.logReporter(Status.INFO,
				"API Operation status: " + apiOperation1.get("sendResponseSMSAPIfeatureCallStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation1.size());
		Reporting.logReporter(Status.INFO,
				"API Operation Request: " + apiOperation1.get("sendResponseSMSAPIfeatureCallRequest"));
		Reporting.logReporter(Status.INFO,
				"API Operation response: " + apiOperation1.get("sendResponseSMSAPIfeatureCallResponse"));

		actualStatus = apiOperation1.get("status").toString();
		actualStatusMsg = apiOperation1.get("statusMessage").toString();
		actualOrderId = apiOperation1.get("orderId").toString();

		Validate.assertEquals(actualStatus, expectedStatus, "2fa sms response mismtach", true);
		Validate.assertEquals(actualOrderId, internalReqNumFromPAC, "2fa sms order Id mismtach", true);
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS AND SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - REQUEST VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, sub, "intermediate1");
		PACDashboardPageSteps
				.verifySMSDetailsCapturedUnderNotesInPAC_NEW(smsResponseTestData.getString("2FA_SMS_RESPONSE_NON"));
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING REQUEST COMPLETION FROM SMG ***/
		Reporting.setNewGroupName("SMG - REQUEST COMPLETION VALIDATION");
		portType = smgTestData.getString("QUERY_PORT_RECORD_TYPE");
		oldNetwork = smgTestData.getString("OWNER_SPID");
		expectedText = smgTestData.getString("SMG_STATUS_RESOLUTION_REQUIRED");
		SMGICPPortSelectionPageSteps.verifyRequestStatusFromICPPortSelectionPage(sub, portType, oldNetwork,
				expectedText);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR CANCELLING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CANCEL REQUEST WITH 2782 SPID ");
		LSRPageSteps.cancelRequestFromLSR(sub, ponNumber1, spid1, serviceProvider);
		LSRPageSteps.verifyRecordsStatusAfterCancellation("Completed", "Superseded");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR WAITING FOR REQUIRED STATUS TO BE DISPLAYED IN LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - WAIT FOR REQUIRED STATUS");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("Sent", sub, ban, spid1, serviceProvider, ponNumber1,
				maxTimeInSeconds);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS AND SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - REQUEST VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, sub, "intermediate2");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * 
		 * PART - 2
		 * 
		 */

		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST");
		String spid2 = lsrPortalTestData.getString("SPID_2");
		LSRPageSteps.createWLNPortOutRequestFromLSR(lsrPortalTestData, sub, ban, spid2, serviceProvider);
		String ponNumber2 = LSRPageSteps.ponNumber;
		Reporting.logReporter(Status.INFO, "ponNumber2 is: " + ponNumber2);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR WAITING FOR REQUIRED STATUS TO BE DISPLAYED IN LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - WAIT FOR REQUIRED STATUS");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("sent", sub, ban, spid2, serviceProvider, ponNumber2,
				maxTimeInSeconds);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.searchPhoneNo(sub);
		PACDashboardPageSteps.handleMultipleRecordsDisplayed();
		PACDashboardPageSteps.performSearchForPACRecordsDisplayed(sub, 30);
		PACDashboardPageSteps.verifyPortStatusFromPAC(pacTestData, "preValidation");
		PACDashboardPageSteps.verifyPortSummary(pacTestData, "preValidation");

		internalReqNumFromPAC = PACDashboardPageSteps.getPACRequestNumber();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		SmartDesktopDashboardPageSteps.navigateToCommunicationHistoryTabToVerifySMS(sdTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR RESPONDING NON TO 2FA SMS AUTHENTICATION **/
		Reporting.setNewGroupName("2FA SMS API CALL - RESPONDED NON");
		Reporting.logReporter(Status.INFO, "STEP===Responding non to 2FA SMS===");

		expectedStatus = smsResponseTestData.getString("EXPECTED_STATUS");
		response = smsResponseTestData.getString("2FA_SMS_RESPONSE_N");
		laguageCode = smsResponseTestData.getString("LANGUAGE_CODE");
		localDate = GenericUtils.getFormattedSystemDate("yyyy-mm-ddThh:mm:ss:ttt");

		System.setProperty("karate.pacInternalReqNum", internalReqNumFromPAC);
		System.setProperty("karate.TN", sub);
		System.setProperty("karate.responseYN", response);
		System.setProperty("karate.dateTime", localDate);
		System.setProperty("karate.languageCode", laguageCode);
		System.setProperty("karate.BAN", ban);

		Map<String, Object> apiOperation2 = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/Test_2FA_SMS_Response.feature");

		Reporting.logReporter(Status.INFO,
				"API Operation status: " + apiOperation2.get("sendResponseSMSAPIfeatureCallStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation2.size());
		Reporting.logReporter(Status.INFO,
				"API Operation Request: " + apiOperation2.get("sendResponseSMSAPIfeatureCallRequest"));
		Reporting.logReporter(Status.INFO,
				"API Operation response: " + apiOperation2.get("sendResponseSMSAPIfeatureCallResponse"));

		actualStatus = apiOperation2.get("status").toString();
		actualStatusMsg = apiOperation2.get("statusMessage").toString();
		actualOrderId = apiOperation2.get("orderId").toString();

		Validate.assertEquals(actualStatus, expectedStatus, "2fa sms response mismtach", true);
		Validate.assertEquals(actualOrderId, internalReqNumFromPAC, "2fa sms order Id mismtach", true);
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS AND SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - REQUEST VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, sub, "intermediate1");
		PACDashboardPageSteps
				.verifySMSDetailsCapturedUnderNotesInPAC_NEW(smsResponseTestData.getString("2FA_SMS_RESPONSE_N"));
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING REQUEST COMPLETION FROM SMG ***/
		Reporting.setNewGroupName("SMG - REQUEST COMPLETION VALIDATION");
		portType = smgTestData.getString("QUERY_PORT_RECORD_TYPE");
		oldNetwork = smgTestData.getString("OWNER_SPID");
		expectedText = smgTestData.getString("SMG_STATUS_RESOLUTION_REQUIRED");
		SMGICPPortSelectionPageSteps.verifyRequestStatusFromICPPortSelectionPage(sub, portType, oldNetwork,
				expectedText);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// String spid2 = lsrPortalTestData.getString("SPID_2");
		// String ponNumber2 = "AB04929993";

		/*** STEPS FOR CANCELLING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CANCEL REQUEST WITH 2782 SPID ");
		LSRPageSteps.cancelRequestFromLSR(sub, ponNumber2, spid2, serviceProvider);
		LSRPageSteps.verifyRecordsStatusAfterCancellation("Completed", "Superseded");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR WAITING FOR REQUIRED STATUS TO BE DISPLAYED IN LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - WAIT FOR REQUIRED STATUS");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("Sent", sub, ban, spid2, serviceProvider, ponNumber2,
				maxTimeInSeconds);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS AND SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - REQUEST VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, sub, "intermediate2");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * 
		 * PART - 3
		 * 
		 */

		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST WITH 8083 SPID ");
		String spid3 = lsrPortalTestData.getString("SPID_3");
		LSRPageSteps.createWLNPortOutRequestFromLSR(lsrPortalTestData, sub, ban, spid3, serviceProvider);
		String ponNumber3 = LSRPageSteps.ponNumber;
		Reporting.logReporter(Status.INFO, "ponNumber3 is: " + ponNumber3);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// iteration 2
		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST WITH 8083 SPID ");
		LSRPageSteps.createWLNPortOutRequestFromLSR(lsrPortalTestData, sub, ban, spid3, serviceProvider);
		Reporting.logReporter(Status.INFO, "ponNumber3 is: " + ponNumber3);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// iteration 3
		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST WITH 8083 SPID ");
		LSRPageSteps.createWLNPortOutRequestFromLSR(lsrPortalTestData, sub, ban, spid3, serviceProvider);
		Reporting.logReporter(Status.INFO, "ponNumber3 is: " + ponNumber3);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// String spid3 = lsrPortalTestData.getString("SPID_3");
		// String ponNumber3 = "QC13642099";
		// String ponNumber3 = "QC77042568";

		/*** STEPS FOR WAITING FOR REQUIRED STATUS TO BE DISPLAYED IN LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - WAIT FOR REQUIRED STATUS");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("sent", sub, ban, spid3, serviceProvider, ponNumber3,
				maxTimeInSeconds);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.searchPhoneNo(sub);
		PACDashboardPageSteps.handleMultipleRecordsDisplayed();
		PACDashboardPageSteps.performSearchForPACRecordsDisplayed(sub, 30);
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		String expectedPortStatus = pacTestData.getString("PORT_STATUS_BEFORE");
		PACDashboardPageSteps.waitForSpecificPortStatus(expectedPortStatus);
		String actualPortStatus = PACDashboardPage.getPortStatus();
		Validate.assertEquals(actualPortStatus.toUpperCase(), expectedPortStatus.toUpperCase(), false);
		PACDashboardPageSteps.verifyPortSummary(pacTestData, "preValidation");
		internalReqNumFromPAC = PACDashboardPageSteps.getPACRequestNumber();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		SmartDesktopDashboardPageSteps.navigateToCommunicationHistoryTabToVerifySMS(sdTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR RESPONDING NON TO 2FA SMS AUTHENTICATION **/
		Reporting.setNewGroupName("2FA SMS API CALL - RESPONDED O");
		Reporting.logReporter(Status.INFO, "STEP===Responding O to 2FA SMS===");

		expectedStatus = smsResponseTestData.getString("EXPECTED_STATUS");
		response = smsResponseTestData.getString("2FA_SMS_RESPONSE_O");
		laguageCode = smsResponseTestData.getString("LANGUAGE_CODE");
		localDate = GenericUtils.getFormattedSystemDate("yyyy-mm-ddThh:mm:ss:ttt");

		System.setProperty("karate.pacInternalReqNum", internalReqNumFromPAC);
		System.setProperty("karate.TN", sub);
		System.setProperty("karate.responseYN", response);
		System.setProperty("karate.dateTime", localDate);
		System.setProperty("karate.languageCode", laguageCode);
		System.setProperty("karate.BAN", ban);

		Map<String, Object> apiOperation3 = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/Test_2FA_SMS_Response.feature");

		Reporting.logReporter(Status.INFO,
				"API Operation status: " + apiOperation3.get("sendResponseSMSAPIfeatureCallStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation3.size());
		Reporting.logReporter(Status.INFO,
				"API Operation Request: " + apiOperation3.get("sendResponseSMSAPIfeatureCallRequest"));
		Reporting.logReporter(Status.INFO,
				"API Operation response: " + apiOperation3.get("sendResponseSMSAPIfeatureCallResponse"));

		actualStatus = apiOperation3.get("status").toString();
		actualStatusMsg = apiOperation3.get("statusMessage").toString();
		actualOrderId = apiOperation3.get("orderId").toString();

		Validate.assertEquals(actualStatus, expectedStatus, "2fa sms response mismtach", true);
		Validate.assertEquals(actualOrderId, internalReqNumFromPAC, "2fa sms order Id mismtach", true);
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS AND SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - REQUEST VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, sub, "intermediate3");
		PACDashboardPageSteps.changedBrandVerification(pacTestData);
		PACDashboardPageSteps
				.verifySMSDetailsCapturedUnderNotesInPAC_NEW(smsResponseTestData.getString("2FA_SMS_RESPONSE_O"));
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// String spid3 = lsrPortalTestData.getString("SPID_3");
		// String ponNumber3 = "QC74923988";

		/*** STEPS FOR WAITING FOR REQUIRED STATUS TO BE DISPLAYED IN LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - WAIT FOR REQUIRED STATUS");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("Confirmed", sub, ban, spid3, serviceProvider, ponNumber3,
				maxTimeInSeconds);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING REQUEST COMPLETION FROM SMG ***/
		Reporting.setNewGroupName("SMG - REQUEST COMPLETION VALIDATION");
		expectedResponseType = smgTestData.getString("SMG_STATUS_CONFIRMED");
		portType = smgTestData.getString("QUERY_PORT_RECORD_TYPE");
		oldNetwork = smgTestData.getString("OWNER_SPID");
		expectedText = smgTestData.getString("SMG_STATUS_CONFIRMED");
		SMGICPPortSelectionPageSteps.verifyRequestStatusFromICPPortSelectionPage(sub, portType, oldNetwork,
				expectedText);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * Perform NPAC Activate
		 */

		/*** STEPS TO PERFORM NPAC ACTIVATE STEPS FROM SMG ASMS ***/
		Reporting.setNewGroupName("SMGASMS - PERFORM NPAC ACTIVATE");
		SMGASMSPageSteps.createNewSPRequestFromSMGASMS(smgASMSTestData, sub);
		String expectedState = smgASMSTestData.getString("EXPECTED_NPAC_PENDING_STATUS");
		SMGASMSPageSteps.queryForNPACPendingStatus(sub, expectedState);
		SMGASMSPageSteps.performNPACActivateSteps(smgASMSTestData);
		SMGASMSPageSteps.logOffFromTruNumberGateway();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * 
		 * POST VALIDATION STEPS
		 * 
		 */

		/*** STEPS FOR VERIFYING THE SUBSCRIBER SPID AS 8083 ***/
		Reporting.setNewGroupName("LSMS API - SPID POSTVALIDATION");
		Reporting.logReporter(Status.INFO, "STEP === SPID Validation --> SPID validation check via API call===");

		System.setProperty("karate.TN", sub);

		Map<String, Object> apiOperation = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/Test_LSMS_SPID.feature");

		Reporting.logReporter(Status.INFO, "API Operation status: " + apiOperation.get("spidAPIfeatureCallStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation.size());
		Reporting.logReporter(Status.INFO, "API Operation Request: " + (apiOperation.get("spidAPIfeatureCallRequest")));

		Reporting.logReporter(Status.INFO, "API Operation response: " + apiOperation.get("spidAPIfeatureCallResponse"));
		Reporting.logReporter(Status.INFO, "API Operation response Soap conversion: "
				+ XmlUtils.toXml(apiOperation.get("spidAPIfeatureCallResponse")));

		String expectedSPID = lsmsTestData.getString("EXPECTED_SPID");
		String resp = XmlUtils.toXml(apiOperation.get("spidAPIfeatureCallResponse"));
		String actualSPID = GenericUtils.returnKeyValueFromXMLNode(resp, "lspId");
		Validate.assertEquals(actualSPID, expectedSPID, "SPID is not as expected", true);
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, sub, "postValidation");
		PACDashboardPageSteps.changedBrandVerification(pacTestData);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB POSTVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "cancelled");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB POSTVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub, "cancelled");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB POSTVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, sub, "cancelled");
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
