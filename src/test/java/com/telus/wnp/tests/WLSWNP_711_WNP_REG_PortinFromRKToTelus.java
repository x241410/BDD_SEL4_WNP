package com.telus.wnp.tests;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.utils.Status;
import com.telus.wnp.pages.CODASearchPage;
import com.telus.wnp.pages.SmartDesktopDashboardPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.PublicMobilePageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.RKPortalPageSteps;
import com.telus.wnp.steps.SmartDesktopDashboardPageSteps;
import com.telus.wnp.steps.SmartDesktopSearchPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.EncryptionUtils;
import com.test.utils.SystemProperties;

public class WLSWNP_711_WNP_REG_PortinFromRKToTelus extends BaseTest {

	String sub = null;
	String ban = null;
	String koodoPrepaidNum = null;
	String koodoPrepaidBan = null;
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
	JSONObject rkPortalTestData = null;
	JSONObject publicMobileTestData = null;
	JSONObject smsResponseTestData = null;

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
		rkPortalTestData = testDataJson.getJSONObject("RK_PORTAL_CONSTANTS");

		sub = tnTestData.getString("TELUS_SUB");
		ban = tnTestData.getString("TELUS_BAN");

	}

	@Test(groups = { "WLSWNP_711_WNP_REG_PortinFromRKToTelus", "TELUS-Interbrand", "CompleteRegressionSuite" })
	public void testMethod_InterbrandPortinFromRKToTelus(ITestContext iTestContext) throws Exception {

		Reporting.setNewGroupName("Automation Configurations / Environment Details & Data Setup");
		Reporting.logReporter(Status.INFO,
				"Automation Configuration - Environment Configured for Automation Execution [" + environment + "]");
		Reporting.logReporter(Status.INFO, "Test Data File for " + scriptName + " placed at : " + testDataFilePath);
		Reporting.printAndClearLogGroupStatements();

/*
 * 
 * 
 * 
 * 		*//*** Test Case Details ***//*
		Reporting.setNewGroupName("Test Case Details");
		Reporting.logReporter(Status.INFO, "Test Case Name : [" + scriptName + "]");
		Reporting.logReporter(Status.INFO, "Test Case Description : [" + testCaseDescription + "]");
		Reporting.printAndClearLogGroupStatements();

		*//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***//*
		Reporting.setNewGroupName("SMART DESKTOP - SUB VALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, ban, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		*//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***//*
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, sub, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		*//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***//*
		Reporting.setNewGroupName("CODA - SUB VALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub, "active");
		//CODASearchPageSteps.verifySubOrderStatus(codaTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		*//*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***//*
		Reporting.setNewGroupName("PAC - SUB VALIDATION");
		PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		*//*** STEPS FOR ACTIVATE KOODO PREPAID NUMBER FROM RK PORTAL **//*
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

		*//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***//*
		Reporting.setNewGroupName("CODA - SUB VALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, koodoPrepaidNum, "active");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		*//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***//*
		Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, koodoPrepaidNum, "available");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();
		
		*
		*
		*
		*/

		
		 koodoPrepaidNum = "7780316642"; koodoPrepaidBan = "10000001922049";
		 

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC - SUB PREVALIDATION");
		PACSearchPageSteps.verifyNoPortReqForSubFromPAC(pacTestData, koodoPrepaidNum);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR NAVIGATE TO SMARTDESKTOP AND PERFORM CHANGE PHONE NUMBER ***/
		Reporting.setNewGroupName("SMART DESKTOP - CHANGE PHONE NUMBER");
		SmartDesktopDashboardPageSteps.submitTNForChangeFromSubscriberTab_SD(sdTestData, sub, ban, koodoPrepaidNum,
				koodoPrepaidBan);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR PORT STATUS VALIDATION FROM PAC ***/
		Reporting.setNewGroupName("PAC - PORT STATUS VALIDATION");
		PACDashboardPageSteps.VerifyPortSummaryAndPortStatus(pacTestData, koodoPrepaidNum, "postValidation");
		PACDashboardPageSteps.changedBrandVerification(pacTestData);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR KOODO SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB VALIDATION");
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, koodoPrepaidNum, ban, "active");
		SmartDesktopSearchPageSteps.verifyAnotherSubscriberStatus(sdTestData, sub, ban, "cancelled");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM - SUB VALIDATION");
		RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, koodoPrepaidNum, "available");
		RCMPageSteps.verifyAnotherSubscriberProvisioningStatus(rcmTestData, sub, "cancelled");
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB VALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, koodoPrepaidNum, "active");
		CODASearchPageSteps.verifyAnotherSubStatusFromCODA(codaTestData, sub, "cancelled");
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
