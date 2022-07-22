package com.telus.wnp.tests;

import java.util.Map;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.utils.Status;
import com.intuit.karate.XmlUtils;
import com.telus.api.test.utils.APIJava;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.pages.SMGICPPortDetailsPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.PublicMobilePageSteps;
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
import com.test.utils.EncryptionUtils;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: Public Mobile to Foreign Portout 9B Then Perform SUP3 From SMG
 * > AUTHOR: x241410 > Test Case: TC03_WNP_REG_2FA_Verify that Reject response
 * sent back to Syniverse with the new reason codes 9B while SUP3 from NSP,if
 * there is no response from the customer earlier _Public Mobile
 ****************************************************************************
 */
public class WLSC_169390_WNP_REG_PublicMobileToForeign9B extends BaseTest {

	String publicMobileNum = null;
	String publicMobileBan = null;
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
	JSONObject publicMobileTestData = null;

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
		publicMobileTestData = testDataJson.getJSONObject("PUBLIC_MOBILE_CONSTANTS");
		lsmsTestData = testDataJson.getJSONObject("LSMS_CONSTANTS");
		smsResponseTestData = testDataJson.getJSONObject("SMS_RESPONSE_CONSTANTS");

		sub = tnTestData.getString("TELUS_SUB");
		ban = tnTestData.getString("TELUS_BAN");
	}

	@Test(groups = { "WLSC_169390_WNP_REG_PublicMobileToForeign9B", "Port-Out", "CompleteRegressionSuite" })
	public void testMethod_PublicMobileToForeign9B(ITestContext iTestContext) throws Exception {

		/*
		 * Reporting.
		 * setNewGroupName("Automation Configurations / Environment Details & Data Setup"
		 * ); Reporting.logReporter(Status.INFO,
		 * "Automation Configuration - Environment Configured for Automation Execution ["
		 * + environment + "]"); Reporting.logReporter(Status.INFO,
		 * "Test Data File for " + scriptName + " placed at : " + testDataFilePath);
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 *//*** Test Case Details ***/
		/*
		 * Reporting.setNewGroupName("Test Case Details");
		 * Reporting.logReporter(Status.INFO, "Test Case Name : [" + scriptName + "]");
		 * Reporting.logReporter(Status.INFO, "Test Case Description : [" +
		 * testCaseDescription + "]"); Reporting.printAndClearLogGroupStatements();
		 * 
		 *//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		/*
		 * Reporting.setNewGroupName("SMART DESKTOP - SUB PRE VALIDATION");
		 * SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub,
		 * ban, "active"); WebDriverSteps.closeTheBrowser();
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 *//*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		/*
		 * Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		 * PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, sub);
		 * WebDriverSteps.closeTheBrowser();
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 * 
		 *//*** STEPS FOR ACTIVATE PUBMIC MOBILE ***/
		/*
		 * Reporting.setNewGroupName("PUBLIC MOBILE NUMBER ACTIVATION");
		 * 
		 * PublicMobilePageSteps.activatePublicMobileNumber(publicMobileTestData, "",
		 * ""); publicMobileNum = PublicMobilePageSteps.activatedPublicMobileNumber;
		 * publicMobileBan = PublicMobilePageSteps.activatedPublicMobileAccountNumber;
		 * 
		 * publicMobileNum = publicMobileNum.replaceAll(" ", "").replaceAll("-",
		 * "").replaceAll("\\)", "") .replaceAll("\\(", "");
		 * Reporting.logReporter(Status.INFO, "Activated publicMobileNum is: " +
		 * publicMobileNum); Reporting.logReporter(Status.INFO,
		 * "Activated publicMobileBan is: " + publicMobileBan);
		 * 
		 * System.out.println("Activated publicMobileNum is: " + publicMobileNum);
		 * System.out.println("Activated publicMobileBan is: " + publicMobileBan);
		 * 
		 * WebDriverSteps.closeTheBrowser();
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 *//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		/*
		 * Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		 * CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData,
		 * publicMobileNum, "active");
		 * CODASearchPageSteps.verifySubOrderStatus(codaTestData, publicMobileNum);
		 * WebDriverSteps.closeTheBrowser();
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 *//**
			 * 
			 * pt 148 test data publicMobileNum = "5871678263"; publicMobileBan =
			 * "10000001922010";
			 * 
			 */
		/*
		
		*//*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		/*
		 * Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		 * PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData,
		 * publicMobileNum); WebDriverSteps.closeTheBrowser();
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 *//*** STEPS FOR NAVIGATE TO SMG AND INITIATE PORT OUT REQUEST ***//*
																			 * Reporting.
																			 * setNewGroupName("SMG - PORT OUT REQUEST"
																			 * ); SMGICPPortDetailsPageSteps.
																			 * saveCreatePortRequestDetailsForSingleSub(
																			 * smgTestData, publicMobileNum,
																			 * publicMobileBan, "portout");
																			 * smgRequestNum =
																			 * SMGICPPortDetailsPageSteps.
																			 * SMGRequestNumber;
																			 * WebDriverSteps.closeTheBrowser();
																			 * Reporting.printAndClearLogGroupStatements
																			 * ();
						
																			 */

		publicMobileNum = "4031917510"; 
		publicMobileBan ="10000002370615";
				 		
		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, publicMobileNum, "preValidation");
		internalReqNumFromPAC = PACDashboardPageSteps.getPACRequestNumber();
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - COMM HISTORY VALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		BaseSteps.Waits.waitGeneric(30000);
		SmartDesktopDashboardPageSteps.navigateToCommunicationHistoryTabToVerifySMSDetails(sdTestData, publicMobileNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING 8k DELAY CODE FROM SMG ***/
		Reporting.setNewGroupName("SMG - DELAY CODE 8k VALIDATION");
		SMGICPPortDetailsPageSteps.navigateToSMGAndVerifyDelyCode8k(smgTestData, publicMobileNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		// *
		// * DO NOT RESPOND TO 2FA SMS
		// *

		/*** STEP FOR DDDT WAIT ***/
		Reporting.setNewGroupName("WAIT FOR DEFINED DDDT MINUTES");
		Reporting.logReporter(Status.INFO, "Step === Wait for defined DDDT ===");
		int DelayTime = smgTestData.getInt("DDDT_DELAY_IN_MINUTES") + 1;
		int delayTimeInSeconds = DelayTime * 60 * 1000;
		BaseSteps.Waits.waitGeneric(delayTimeInSeconds);
		Reporting.logReporter(Status.INFO, "Wait for defined DDDT of " + DelayTime + " minutes.");
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC **/
		Reporting.setNewGroupName("PAC - PORT STATUS AND SUMMARY VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, publicMobileNum, "postValidation");
		PACDashboardPageSteps.verifyResponseCodeFromPAC(smgTestData.getString("EXPECTED_RESPONSE_CODE"));
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR VERIFYING REQUEST REJECTION FROM SMG ***/
		Reporting.setNewGroupName("SMG - REQUEST REJECTION VALIDATION");
		SMGICPPortDetailsPageSteps.verifyResponseAndCodeFromSMGICPPortDetails(smgTestData, publicMobileNum);
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		String actualResponseCodeDetail = SMGICPPortDetailsPage.getResponseCodeDetailsVerifyText();
		String expectedResponseCodeDetail = smgTestData.getString("EXPECTED_RESPONSE_CODE_DETAIL_TEXT");
		Validate.assertEquals(actualResponseCodeDetail, expectedResponseCodeDetail, "Response Code mismatch", false);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR MODIFY REQUEST FROM SMG ***/
		Reporting.setNewGroupName("SMG - TRY MODIFYING REQUEST");
		SMGICPPortSelectionPageSteps.verifyUnableToPerformSUP3FromSMGFor9BRequest(smgTestData, publicMobileNum,
				smgTestData.getString("MODIFY_REQUEST_REMARKS"));
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT SUMMARY VALIDATION FROM PAC **/
		Reporting.setNewGroupName("PAC - NO CHANGE IN PAC");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, publicMobileNum, "postValidation");
		PACDashboardPageSteps.verifyResponseCodeFromPAC(smgTestData.getString("EXPECTED_RESPONSE_CODE"));
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
