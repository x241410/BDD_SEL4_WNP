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
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LSRPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.PublicMobilePageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.SMGASMSPageSteps;
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
 * > DESCRIPTION: RK to WLN_Verify 2FA sent_Confirm response (Public Mobile)
 * from LSR > AUTHOR: x241410 > Test Case: TC06_WNP_REG_WLN_Submit Port Out
 * Request from RK to WLN_Verify 2FA sent_Confirm response (Public Mobile)
 ****************************************************************************
 */

public class WLSWNP_1191_2FA_PMPortOutToWLNFromLSR extends BaseTest {

	String sub = null;
	String ban = null;
	String publicMobileNum = null;
	String publicMobileBan = null;
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
	JSONObject publicMobileTestData = null;
	JSONObject smgASMSTestData = null;

	String expectedStatus = null;
	String response = null;
	String laguageCode = null;
	String localDate = null;
	String expectedResponseType = null;

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
		publicMobileTestData = testDataJson.getJSONObject("PUBLIC_MOBILE_CONSTANTS");
		smgASMSTestData = testDataJson.getJSONObject("SMG_ASMS_CONSTANTS");
		lsrPortalTestData = testDataJson.getJSONObject("LSR_PORTAL_CONSTANTS");

		sub = tnTestData.getString("TELUS_SUB");
		ban = tnTestData.getString("TELUS_BAN");

	}

	@Test(groups = { "WLSWNP_1191_2FA_PMPortOutToWLNFromLSR", "Port-Out", "WireLine", "CompleteRegressionSuite" })
	public void testMethod_PortOutToWLNFromLSR(ITestContext iTestContext) throws Exception {

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

		/*** STEPS FOR ACTIVATE PUBMIC MOBILE ***/
		Reporting.setNewGroupName("PUBLIC MOBILE NUMBER ACTIVATION");
		PublicMobilePageSteps.activatePublicMobileNumber(publicMobileTestData, "", "");
		publicMobileNum = PublicMobilePageSteps.activatedPublicMobileNumber;
		publicMobileBan = PublicMobilePageSteps.activatedPublicMobileAccountNumber;
		publicMobileNum = publicMobileNum.replaceAll(" ", "").replaceAll("-", "").replaceAll("\\)", "")
				.replaceAll("\\(", "");
		Reporting.logReporter(Status.INFO, "Activated publicMobileNum is: " + publicMobileNum);
		Reporting.logReporter(Status.INFO, "Activated publicMobileBan is: " + publicMobileBan);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, publicMobileNum, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, publicMobileNum, "available");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * Test Case Flow Steps
		 */

		/*** STEPS TO RAISE REQUEST OUTSIDE OF LSR WINDOW FROM PAC ***/
		Reporting.setNewGroupName("PAC - ENABLE PREPORT AUTH");
		PACDashboardPageSteps.enablePreportAuthorization(pacTestData, publicMobileNum, 240, "wireline");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST");
		String spid = lsrPortalTestData.getString("SPID");
		String serviceProvider = lsrPortalTestData.getString("SERVICE_PROVIDER");
		LSRPageSteps.createWLNPortOutRequestFromLSR(lsrPortalTestData, publicMobileNum, publicMobileBan, spid,
				serviceProvider);
		String ponNumber = LSRPageSteps.ponNumber;
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*
		 * //publicMobileNum = "(416) 176 - 6827"; //publicMobileBan = "10000001921581";
		 * //String ponNumber = "AB87182734"; publicMobileNum = "4161766882";
		 * publicMobileBan = "10000001920866"; String ponNumber = "AB25892733"; String
		 * spid = lsrPortalTestData.getString("SPID"); String serviceProvider =
		 * lsrPortalTestData.getString("SERVICE_PROVIDER");
		 */

		/*** STEPS FOR WAITING FOR SPECIFIC STATUS FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("sent", publicMobileNum, publicMobileBan, spid, serviceProvider,
				ponNumber, 1800);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.searchPhoneNo(publicMobileNum);
		PACDashboardPageSteps.handleMultipleRecordsDisplayed();
		PACDashboardPageSteps.performSearchForPACRecordsDisplayed(publicMobileNum, 60);
		PACDashboardPageSteps.verifyPortStatusFromPAC(pacTestData, "preValidation");
		PACDashboardPageSteps.verifyPortSummary(pacTestData, "preValidation");
		internalReqNumFromPAC = PACDashboardPageSteps.getPACRequestNumber();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		SmartDesktopDashboardPageSteps.navigateToCommunicationHistoryTabToVerifySMS_MVNE(sdTestData, publicMobileNum);
		SmartDesktopDashboardPageSteps.openPreviewAndVerifySMSDetails(sdTestData.getString("EXPECTED_BODY_TEXT"),
				publicMobileNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR RESPONDING oUI TO 2FA SMS AUTHENTICATION **/
		Reporting.setNewGroupName("2FA SMS API CALL - RESPONDED oUI");
		Reporting.logReporter(Status.INFO, "STEP===Responding oUI to 2FA SMS===");

		expectedStatus = smsResponseTestData.getString("EXPECTED_STATUS");
		response = smsResponseTestData.getString("2FA_SMS_RESPONSE");
		laguageCode = smsResponseTestData.getString("LANGUAGE_CODE");
		localDate = GenericUtils.getFormattedSystemDate("yyyy-mm-ddThh:mm:ss:ttt");

		System.setProperty("karate.pacInternalReqNum", internalReqNumFromPAC);
		System.setProperty("karate.TN", publicMobileNum);
		System.setProperty("karate.responseYN", response);
		System.setProperty("karate.dateTime", localDate);
		System.setProperty("karate.languageCode", laguageCode);
		System.setProperty("karate.BAN", publicMobileBan);

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
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, publicMobileNum, "intermediate1");
		PACDashboardPageSteps.changedBrandVerification(pacTestData);
		PACDashboardPageSteps
				.verifySMSDetailsCapturedUnderNotesInPAC_NEW(smsResponseTestData.getString("2FA_SMS_RESPONSE"));

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING REQUEST COMPLETION FROM SMG ***/
		Reporting.setNewGroupName("SMG - REQUEST COMPLETION VALIDATION");
		String portType = smgTestData.getString("QUERY_PORT_RECORD_TYPE");
		String oldNetwork = smgTestData.getString("OWNER_SPID");
		String expectedText = smgTestData.getString("SMG_STATUS_CONFIRMED");
		SMGICPPortSelectionPageSteps.verifyRequestStatusFromICPPortSelectionPage(publicMobileNum, portType, oldNetwork,
				expectedText);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("confirmed", publicMobileNum, publicMobileBan, spid,
				serviceProvider, ponNumber, 1800);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS TO PERFORM NPAC ACTIVATE STEPS FROM SMG ASMS ***/
		Reporting.setNewGroupName("SMGASMS - PERFORM NPAC ACTIVATE");
		SMGASMSPageSteps.createNewSPRequestFromSMGASMS(smgASMSTestData, publicMobileNum);
		String expectedState = smgASMSTestData.getString("EXPECTED_NPAC_PENDING_STATUS");
		SMGASMSPageSteps.queryForNPACPendingStatus(publicMobileNum, expectedState);
		SMGASMSPageSteps.performNPACActivateSteps(smgASMSTestData);
		SMGASMSPageSteps.logOffFromTruNumberGateway();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * Perform Post Validations
		 */

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB POSTVALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, publicMobileNum, "postValidation");
		PACDashboardPageSteps.changedBrandVerification(pacTestData);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING THE SUBSCRIBER SPID AS 8084 ***/
		Reporting.setNewGroupName("LSMS API - SPID POSTVALIDATION");
		Reporting.logReporter(Status.INFO, "STEP === SPID Validation --> SPID validation check via API call===");

		System.setProperty("karate.TN", publicMobileNum);

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
