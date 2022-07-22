package com.telus.wnp.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;

import com.test.utils.Status;
import com.intuit.karate.XmlUtils;
import com.telus.api.test.utils.APIJava;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.pages.PACSearchPage;
import com.telus.wnp.pages.SMGICPPortDetailsPage;
import com.telus.wnp.pages.SmartDesktopSearchPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.RKPortalPageSteps;
import com.telus.wnp.steps.SMGICPPortDetailsPageSteps;
import com.telus.wnp.steps.SMGICPPortSelectionPageSteps;
import com.telus.wnp.steps.SMGPageSteps;
import com.telus.wnp.steps.SmartDesktopDashboardPageSteps;
import com.telus.wnp.steps.SmartDesktopSearchPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.files.interaction.ReadJSON;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: Send Cancellation to External Service Provider Fall out >
 * AUTHOR: x241410 > Test Case: TC03_WNP_REG_Send Cancellation to Service
 * Provider _fallout
 ****************************************************************************
 */

public class WLSC_169267_WNP_REG_SendCancelESPFallOut extends BaseTest {

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
		foreignSub = tnTestData.getString("FOREIGN_SUB");
		foreignBan = tnTestData.getString("FOREIGN_BAN");

	}

	@Test(groups = { "WLSC_169267_WNP_REG_SendCancelESPFallOut", "SendCancelation", "CompleteRegressionSuite" })
	public void testMethod_SendCancellationtoESPFallOut(ITestContext iTestContext) throws Exception {

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
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, sub, "active");
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

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING THE SUBSCRIBER SPID AS TU02 ***/
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

		/*** STEPS FOR NAVIGATE TO SMARTDESKTOP AND PERFORM OSP CANCEL REQUEST ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUBMIT OSP CANCEL REQUEST");
		SmartDesktopDashboardPageSteps.submitOSPCancelReqFromSubscriberTab(sdTestData, sub, ban, foreignSub,
				foreignBan);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, foreignSub, "preValidation");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT CREATE CANCEL SVC RESPONSE FROM SMG ***/
		Reporting.setNewGroupName("SMG - CREATE CANCEL SVC RESPONSE");
		SMGICPPortSelectionPageSteps.createCancelSVCResponseFromSMG(smgTestData, foreignSub, sub, "fallout");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, foreignSub, "intermediate1");
		String ddt = GenericUtils.getDDDTWithDelayInPACFormat_Old(30);
		PACDashboardPageSteps.modifyRequestFromPAC(pacTestData, foreignSub, foreignBan, ddt);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT CREATE CANCEL SVC RESPONSE FROM SMG ***/
		Reporting.setNewGroupName("SMG - CREATE CANCEL SVC RESPONSE");
		SMGICPPortSelectionPageSteps.createCancelSVCResponseFromSMG(smgTestData, foreignSub, sub, "");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEP FOR DDDT WAIT ***/
		Reporting.setNewGroupName("WAIT FOR DEFINED DDDT MINUTES");
		Reporting.logReporter(Status.INFO, "Step === Wait for defined DDDT ===");
		int DelayTime = smgTestData.getInt("DDDT_DELAY_IN_MINUTES") + 1;
		int delayTimeInSeconds = DelayTime * 60 * 1000;
		BaseSteps.Waits.waitGeneric(delayTimeInSeconds);
		Reporting.logReporter(Status.INFO, "Wait for defined DDDT of " + DelayTime + " minutes.");
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, foreignSub, "postValidation");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// *** STEPS FOR VALIDATE REQUEST STATUS FROM SMG **
		Reporting.setNewGroupName("SMG - VERIFY REQUEST STATUS");
		SMGICPPortSelectionPageSteps.verifyRequestStatusFromICPPortSelectionPage(foreignSub, "Port Out",
				smgTestData.getString("EXTERNAL_TU02_MOBILITY"), smgTestData.getString("SMG_STATUS_CONFIRMED"));
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB POST VALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "cancelled");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB POST VALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub, "cancelled");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
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
