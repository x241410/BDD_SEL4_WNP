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
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: Port In_Cancel_WLN to Telus Port in-Pending State > AUTHOR:
 * x241410 > Test Case: TC03_WNP_REG_WLN Port In_Cancel_WLN to Telus Port in _
 * in pending state then cancel the request from PAC
 ****************************************************************************
 */

public class WLSC_169214_WNP_REG_CancelWLNPortinPending extends BaseTest {

	String foreignSub = null;
	String foreignBan = null;
	String testDataFilePath = null;
	String testCaseName = null;
	String scriptName = null;
	String expectedStatus = null;
	String actualStatus = null;
	String formattedSub = null;
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
	JSONObject apiTestData = null;

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
		lsmsTestData = testDataJson.getJSONObject("LSMS_CONSTANTS");
		apiTestData = testDataJson.getJSONObject("API_CONSTANTS");

		foreignSub = tnTestData.getString("WLN_SUB");
		foreignBan = tnTestData.getString("WLN_BAN");
	}

	@Test(groups = { "WLSC_169214_WNP_REG_CancelWLNPortinPending", "Port-In", "CompleteRegressionSuite", "Batch1" })
	public void testMethod_CancelWLNToTelusPortin(ITestContext iTestContext) throws Exception {

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

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, foreignSub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, foreignSub, "inactive");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING THE SUBSCRIBER SPID AS TU07 ***/
		Reporting.setNewGroupName("LSMS API - SPID POSTVALIDATION");
		Reporting.logReporter(Status.INFO, "STEP === SPID Validation --> SPID validation check via API call===");

		System.setProperty("karate.TN", foreignSub);

		Map<String, Object> apiOperation = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/Test_LSMS_SPID.feature");

		Reporting.logReporter(Status.INFO, "API Operation status: " + apiOperation.get("spidAPIfeatureCallStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation.size());
		Reporting.logReporter(Status.INFO, "API Operation Request: " + apiOperation.get("spidAPIfeatureCallRequest"));

		Reporting.logReporter(Status.INFO, "API Operation response: " + apiOperation.get("spidAPIfeatureCallResponse"));
		Reporting.logReporter(Status.INFO, "API Operation response Soap conversion: "
				+ XmlUtils.toXml(apiOperation.get("spidAPIfeatureCallResponse")));

		String expectedSPID = lsmsTestData.getString("EXPECTED_SPID");
		String resp = XmlUtils.toXml(apiOperation.get("spidAPIfeatureCallResponse"));
		String actualSPID = GenericUtils.returnKeyValueFromXMLNode(resp, "lspId");
		Validate.assertEquals(actualSPID, expectedSPID, "SPID is not as expected", true);
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR CREATING WLN PORTIN PENDING REQUEST **/
		Reporting.setNewGroupName("CREATE PORTIN PENDING REQUEST");
		Reporting.logReporter(Status.INFO, "STEP===Creating portin pending request===");

		System.setProperty("karate.tn", GenericUtils.getHyphenSeparatedTN(foreignSub));

		Map<String, Object> apiOperation1 = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/WLN_Portin_Pending.feature");

		Reporting.logReporter(Status.INFO, "API Operation status: " + apiOperation1.get("createPortinPendingStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation1.size());
		Reporting.logReporter(Status.INFO, "API Operation Request: " + apiOperation1.get("createPortinPendingRequest"));
		Reporting.logReporter(Status.INFO,
				"API Operation response: " + apiOperation1.get("createPortinPendingResponse"));
		Reporting.logReporter(Status.INFO, "API Operation response Soap conversion: "
				+ XmlUtils.toXml(apiOperation1.get("createPortinPendingResponse")));

		String resp1 = XmlUtils.toXml(apiOperation1.get("createPortinPendingResponse"));
		String requestId = GenericUtils.returnKeyValueFromXMLNode(resp1, "cl:createPortInRequestResult");
		Reporting.logReporter(Status.INFO, "Request Id for Portin Pending Request is: " + requestId);

		System.out.println("requestId: " + requestId);

		Validate.assertTrue(!requestId.isEmpty(), true, "Request Id is not empty");

		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - REQUEST VALIDATION AND CANCELLATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, foreignSub, "preValidation");
		PACDashboardPageSteps.cancelRequestFromPAC(pacTestData);

		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		BaseSteps.Clicks.clickElement(PACDashboardPage.searchPageLinkBtn);

		PACSearchPageSteps.searchPhoneNo(foreignSub);
		PACDashboardPageSteps.handleMultipleRecordsDisplayed();

		String expectedPortStatus = pacTestData.getString("PORT_STATUS_AFTER");
		String actualPortStatus = PACDashboardPage.getPortStatus();
		Validate.assertEquals(actualPortStatus, expectedPortStatus, "Port status is not as expected", false);

		PACDashboardPageSteps.verifyPortSummary(pacTestData, "postValidation");
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