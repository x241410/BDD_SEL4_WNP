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
import org.xml.sax.SAXException;

import com.test.utils.Status;
import com.intuit.karate.XmlUtils;
import com.telus.api.test.utils.APIJava;
import com.telus.wnp.pages.CODASearchPage;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.pages.PACSearchPage;
import com.telus.wnp.pages.RCMPage;
import com.telus.wnp.pages.SMGICPPortSelectionPage;
import com.telus.wnp.pages.SmartDesktopDashboardPage;
import com.telus.wnp.pages.SmartDesktopSearchPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.MSSPartnersPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.RCMPageSteps;
import com.telus.wnp.steps.SMGICPPortDetailsPageSteps;
import com.telus.wnp.steps.SMGICPPortSelectionPageSteps;
import com.telus.wnp.steps.SMGPageSteps;
import com.telus.wnp.steps.SmartDesktopDashboardPageSteps;
import com.telus.wnp.steps.SmartDesktopHRTPageSteps;
import com.telus.wnp.steps.SmartDesktopSearchPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.files.interaction.ReadJSON;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: TC17_WNP_REG_Prepaid to postpaid migration> AUTHOR: x241410 >
 * Test Case: TC17_WNP_REG_Prepaid to postpaid migration
 ****************************************************************************
 */

public class WLSC_169283_PRE2POST_Migration extends BaseTest {

	String sub = null;
	String ban = null;
	String actualFirstName = "";
	String actualLastName = "";
	String actualPostalCode = "";
	String activatedSub = null;
	String activatedBan = null;
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
	JSONObject mssTestData = null;

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

		mssTestData = testDataJson.getJSONObject("MSS_CONSTANTS");

		sub = tnTestData.getString("TELUS_PREPAID_SUB");
		ban = tnTestData.getString("TELUS_PREPAID_BAN");

	}

	@Test(groups = { "PRE2POST", "MSS", "CompleteRegressionSuite" })
	public void testMethod_ForeignToTelusPortin(ITestContext iTestContext) throws Exception {

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
		String expectedConsumerTypeBefore = sdTestData.getString("EXPECTED_CONSUMER_TYPE_BEFORE");
		SmartDesktopDashboardPageSteps.verifyConsumerTypeFromSD(expectedConsumerTypeBefore);
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		String prepaidSubDetails = SmartDesktopDashboardPage.getPrepaidSubscriberDetails();
		int sizeOfDetails = prepaidSubDetails.trim().split("\\r?\\n").length;
		if (prepaidSubDetails.trim().split("\\r?\\n")[0].split(" ").length > 2) {
			actualFirstName = prepaidSubDetails.trim().split("\\r?\\n")[0].split(" ")[1];
			actualLastName = prepaidSubDetails.trim().split("\\r?\\n")[0].split(" ")[2];
		} else {
			actualFirstName = prepaidSubDetails.trim().split("\\r?\\n")[0].split(" ")[0];
			actualLastName = prepaidSubDetails.trim().split("\\r?\\n")[0].split(" ")[1];
		}
		actualPostalCode = prepaidSubDetails.trim().split("\\r?\\n")[sizeOfDetails - 1].split(" ")[0];
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		/*
		 * Reporting.setNewGroupName("RCM - SUB PREVALIDATION");
		 * RCMPageSteps.verifySubscriberStatusFromRCM(rcmTestData, sub, "active");
		 * WebDriverSteps.closeTheBrowser();
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 *//*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		/*
		 * Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		 * CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub,
		 * "active"); String expectedBillingTypeBefore =
		 * codaTestData.getString("EXPECTED_CONSUMER_TYPE_BEFORE");
		 * CODASearchPageSteps.verifyConsumerTypeFromCODA(expectedBillingTypeBefore);
		 * CODASearchPageSteps.verifySubOrderStatus(codaTestData, sub);
		 * WebDriverSteps.closeTheBrowser();
		 * Reporting.printAndClearLogGroupStatements();
		 * 
		 *//*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***//*
																		 * Reporting.
																		 * setNewGroupName("PAC - SUB PREVALIDATION");
																		 * PACSearchPageSteps.
																		 * verifyNoPortReqForSubFromPAC(pacTestData,
																		 * sub); WebDriverSteps.closeTheBrowser();
																		 * Reporting.printAndClearLogGroupStatements();
																		 */

		/*** STEPS TO PERFORM ACTIVATION FROM MSS PORTAL ***/
		Reporting.setNewGroupName("MSS - SIM CARD ACTIVATION");
		MSSPartnersPageSteps.performPreToPostFromMSS(mssTestData, "telus", sub, actualFirstName, actualLastName,
				actualPostalCode);
		activatedSub = MSSPartnersPageSteps.activatedTN;
		// activatedSub = "416-176-5827";
		activatedSub = activatedSub.replaceAll("-", "");
		activatedBan = MSSPartnersPageSteps.activatedBAN;
		Reporting.logReporter(Status.INFO, "Activated Sub: " + activatedSub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA - SUB PREVALIDATION");
		CODASearchPageSteps.verifySubscriberStatusFromCODA(codaTestData, sub, "active");
		String expectedBillingTypeAfter = codaTestData.getString("EXPECTED_CONSUMER_TYPE_AFTER");
		CODASearchPageSteps.verifyConsumerTypeFromCODA(expectedBillingTypeAfter);
		CODASearchPageSteps.verifySubOrderStatus(codaTestData, sub);
		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM SMARTDESKTOP ***/
		Reporting.setNewGroupName("SMART DESKTOP - SUB PREVALIDATION");
		activatedBan = "70987596";
		SmartDesktopSearchPageSteps.verifySubscriberStatusFromSD(sdTestData, sub, activatedBan, "active");
		String expectedConsumerTypeAfter = sdTestData.getString("EXPECTED_CONSUMER_TYPE_AFTER");
		SmartDesktopDashboardPageSteps.verifyConsumerTypeFromSD(expectedConsumerTypeAfter);
		SmartDesktopSearchPageSteps.scrollToMobilePortingStatusImage();
		String expectedSystemText = sdTestData.getString("EXPECTED_MIGRATION_SYSTEM_TEXT");
		SmartDesktopSearchPageSteps.verifySubscriberMigrationNotes(expectedSystemText);
		SmartDesktopSearchPageSteps.verifySubscriberStatusForOSP(sdTestData, sub, ban, "cancelled");
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