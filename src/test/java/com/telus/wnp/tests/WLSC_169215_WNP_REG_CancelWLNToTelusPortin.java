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
import com.telus.wnp.pages.CODASearchPage;
import com.telus.wnp.pages.PACSearchPage;
import com.telus.wnp.pages.RCMPage;
import com.telus.wnp.pages.SMGICPPortSelectionPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.RCMPageSteps;
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
 * > DESCRIPTION: Port In_Cancel_WLN to Telus Port in > AUTHOR: x241410 > Test
 * Case: TC04_WNP_REG_WLN Port In_Cancel_WLN to Telus Port in _ In progress
 * state then cancel the request from PAC before confirmation
 ****************************************************************************
 */

public class WLSC_169215_WNP_REG_CancelWLNToTelusPortin extends BaseTest {

	String sub = null;
	String ban = null;
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

		sub = tnTestData.getString("TELUS_SUB");
		ban = tnTestData.getString("TELUS_BAN");
		foreignSub = tnTestData.getString("WLN_SUB");
		foreignBan = tnTestData.getString("WLN_BAN");
	}

	@Test(groups = { "WLSC_169215_WNP_REG_CancelWLNToTelusPortin", "Port-In", "CompleteRegressionSuite", "Batch1" })
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

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PRE VALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, sub);
		PACSearchPage PACSearchPage = new PACSearchPage();
		BaseSteps.Waits.waitGeneric(1000);
		String expectedErrMsg = pacTestData.getString("NO_PORT_OSP_CANCEL_REQ_PRESENCE_ERR_MSG");
		Reporting.logReporter(Status.INFO,
				"STEP === PAC Search Page --> Enter Phone Number and click submit : " + foreignSub);
		BaseSteps.SendKeys.clearFieldAndSendKeysUsingJS(PACSearchPage.ctn, foreignSub);
		BaseSteps.Clicks.clickElement(PACSearchPage.submit);
		String actualErrMsg = PACSearchPageSteps.getPortOSPCancelStatus_PAC();

		Validate.assertEquals(actualErrMsg, expectedErrMsg, "Port/OSP Cancel request Msg validation fails", false);
		Validate.takeStepFullScreenShot("Port/OSP Cancel request Msg validation is PASS", Status.PASS);

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, sub, "active");
		RCMPage RCMPage = new RCMPage();

		BaseSteps.Clicks.clickElement(RCMPage.reset);
		Reporting.logReporter(Status.INFO,
				"STEP === PAC Search Page --> Enter Phone Number and click Search : " + foreignSub);
		expectedStatus = rcmTestData.getString("NO_SEARCH_RESULTS_FOUND_ERR_MSG");
		String resourceType = rcmTestData.getString("RESOURCE_TYPE_UMSISDN");
		RCMPageSteps.enterRequiredDetailsForTNSearchInRCM(foreignSub, resourceType);
		actualStatus = RCMPage.getNoSearchResultFoundMsg();
		Validate.assertEquals(actualStatus, expectedStatus, "Expected error message is not displayed", false);
		Validate.takeStepFullScreenShot("Error message displayed: " + actualStatus, Status.PASS);

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub, "active");

		CODASearchPage CodaSearchPage = new CODASearchPage();
		BaseSteps.Clicks.clickElement(CodaSearchPage.searchPageLink);
		int MAX_WAIT_TIME = codaTestData.getInt("MAX_WAIT_TIME");
		CODASearchPageSteps.searchPhoneNo(codaTestData, foreignSub);
		BaseSteps.Waits.waitForElementVisibility(CodaSearchPage.noResultsFound, MAX_WAIT_TIME);
		expectedStatus = codaTestData.getString("INACTIVE_SUB_MSG");
		actualStatus = CodaSearchPage.getNoSearchResultsMsg();

		Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
		Validate.takeStepFullScreenShot("Subscriber status is " + actualStatus, Status.PASS);

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

		/*** STEPS FOR NAVIGATE TO SMARTDESKTOP AND PERFORM CHANGE PHONE NUMBER ***/
		Reporting.setNewGroupName("SMART DESKTOP - CHANGE PHONE NUMBER");
		SmartDesktopDashboardPageSteps.submitTNForChangeFromSubscriberTabForWLN_SD(sdTestData, sub, ban, foreignSub,
				foreignBan);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*
		 * CANCEL Request before sending confirm from SMG
		 */

		/*** STEPS FOR PORT STATUS VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, foreignSub, "preValidation");
		PACDashboardPageSteps.cancelRequestFromPAC(pacTestData);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFY CANCELLED FROM SMG ***/
		Reporting.setNewGroupName("SMG - VERIFY CANCELED STATUS");
		SMGICPPortSelectionPageSteps.navigateToSMGICPPortSelectionPage();
		String portType = smgTestData.getString("PORT_TYPE_SELECT_PORT_IN");
		String newNetwork = smgTestData.getString("TELUS_TU04_MOBILITY");
		SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(foreignSub, portType, newNetwork);
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		String expectedICPPortStatus = smgTestData.getString("CANCELED_STATUS");
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortSelectionPage.smgICPPortStatusFromICPPortSelection);
		BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.smgICPPortStatusFromICPPortSelection);
		String actualICPPortStatus = SMGICPPortSelectionPage.getICPPortStatusFromICPPortSelection();
		Validate.assertEquals(actualICPPortStatus, expectedICPPortStatus,
				"SMG STATUS is not as expected for " + foreignSub + ".", false);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR POST VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - POST VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, foreignSub, "postValidation");
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