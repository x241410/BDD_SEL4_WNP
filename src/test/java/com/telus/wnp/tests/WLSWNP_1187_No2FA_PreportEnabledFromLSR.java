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
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.SMGASMSPageSteps;
import com.telus.wnp.steps.SMGICPPortSelectionPageSteps;
import com.telus.wnp.steps.SmartDesktopDashboardPageSteps;
import com.telus.wnp.steps.SmartDesktopSearchPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: Preport Bypass_Submit Port Out Request 8086 within bypass
 * window_no 2FA sent > AUTHOR: x241410 > Test Case:
 * TC02_WNP_REG_WWUP-429_Submit Preport Bypass_Submit Port Out Request 8086
 * within bypass window_no 2FA sent
 ****************************************************************************
 */

public class WLSWNP_1187_No2FA_PreportEnabledFromLSR extends BaseTest {

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
	JSONObject smgASMSTestData = null;
	JSONObject lsmsTestData = null;
	JSONObject smsResponseTestData = null;
	JSONObject lsrPortalTestData = null;

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

	}

	@Test(groups = { "WLSWNP_1187_No2FA_PreportEnabledFromLSR", "Port-Out", "WireLine", "CompleteRegressionSuite" })
	public void testMethod_PreportEnabledFromLSR(ITestContext iTestContext) throws Exception {

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

		/**
		 * Test Case Flow Steps
		 */

		/*** STEPS FOR ENABLING PREPORT AUTH FROM PAC ***/

		Reporting.setNewGroupName("PAC - ENABLE PREPORT AUTH");
		int prePortDelayTimeInMinutes = pacTestData.getInt("PREPORT_DELAY_TIME_IN_MINUTES");
		PACDashboardPageSteps.enablePreportAuthorization(pacTestData, sub, prePortDelayTimeInMinutes, "wireline");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEP FOR DDDT WAIT ***/

		Reporting.setNewGroupName("WAIT FOR DEFINED DDDT MINUTES");
		Reporting.logReporter(Status.INFO, "Step === Wait for defined DDDT ===");
		int DelayTime = prePortDelayTimeInMinutes;
		int delayTimeInSeconds = DelayTime * 60 * 1000;
		BaseSteps.Waits.waitGeneric(delayTimeInSeconds);
		Reporting.logReporter(Status.INFO, "Wait for defined DDDT of " + DelayTime + " minutes.");
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR CREATING PORT OUT REQUEST FROM LSR ***/

		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST");
		String spid = lsrPortalTestData.getString("SPID");
		String serviceProvider = lsrPortalTestData.getString("SERVICE_PROVIDER");
		LSRPageSteps.createWLNPortOutRequestFromLSR(lsrPortalTestData, sub, ban, spid, serviceProvider);
		String ponNumber = LSRPageSteps.ponNumber;
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR WAITING FOR SPECIFIC STATUS FROM LSR ***/
		Reporting.setNewGroupName("LSR PORTAL - CREATE PORT OUT REQUEST");
		LSRPageSteps.waitUntilSpecificStatusFromLSR("sent", sub, ban, spid, serviceProvider, ponNumber, 1800);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/**
		 * String spid = lsrPortalTestData.getString("SPID"); String serviceProvider =
		 * lsrPortalTestData.getString("SERVICE_PROVIDER"); String ponNumber =
		 * "AB04923988"; /
		 * 
		 * /*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC
		 ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.searchPhoneNo(sub);
		PACDashboardPageSteps.handleMultipleRecordsDisplayed();
		PACDashboardPageSteps.performSearchForPACRecordsDisplayed(sub, 60);
		PACDashboardPageSteps.verifyPortStatusFromPAC(pacTestData, "preValidation");
		PACDashboardPageSteps.verifyPortSummary(pacTestData, "preValidation");

		internalReqNumFromPAC = PACDashboardPageSteps.getPACRequestNumber();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		SmartDesktopDashboardPageSteps.verifyNoSMSEntryForCurrentDay(sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING REQUEST COMPLETION FROM SMG **/
		Reporting.setNewGroupName("SMG - REQUEST COMPLETION VALIDATION");
		String expectedResponseType = smgTestData.getString("SMG_STATUS_CONFIRMED");
		SMGICPPortSelectionPageSteps.verifyRequestStatusFromICPPortSelectionPage(sub,
				smgTestData.getString("PORT_TYPE_SELECT_PORT_OUT"), smgTestData.getString("TELUS_TU04_MOBILITY"),
				expectedResponseType);
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

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB POSTVALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, sub, "postValidation");
		PACDashboardPageSteps.changedBrandVerification(pacTestData);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING THE SUBSCRIBER SPID AS 8084 ***/
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
