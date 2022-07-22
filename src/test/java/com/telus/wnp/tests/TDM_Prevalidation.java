package com.telus.wnp.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
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
import com.telus.wnp.pages.SmartDesktopSearchPage;
import com.telus.wnp.steps.CODASearchPageSteps;
import com.telus.wnp.steps.LoginPageSteps;
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
import com.test.utils.EncryptionUtils;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: PreValidation
 ****************************************************************************
 */

public class TDM_Prevalidation extends BaseTest {

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
	static String environment = null;
	static JSONObject testDataJson = null;
	JSONObject tnTestData = null;
	static JSONObject sdTestData = null;
	static JSONObject pacTestData = null;
	static JSONObject rcmTestData = null;
	static JSONObject codaTestData = null;
	JSONObject smgTestData = null;
	JSONObject lsmsTestData = null;

	static String statusFromSD;
	static String statusFromPAC;
	static String statusFromRCM;
	static String statusFromCODA;
	static String overallStatusFromCODA;
	static String orderStateFromCODA;
	static int pendingOrdersCountFromCODA = -1;
	static int taskErrorsCountFromCODA = -1;
	static int validationerrorsCountFromCODA = -1;

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

		sdTestData = testDataJson.getJSONObject("SMART_DESKTOP_CONSTANTS");
		pacTestData = testDataJson.getJSONObject("PAC_CONSTANTS");
		rcmTestData = testDataJson.getJSONObject("RCM_CONSTANTS");
		codaTestData = testDataJson.getJSONObject("CODA_CONSTANTS");
		lsmsTestData = testDataJson.getJSONObject("LSMS_CONSTANTS");

	}

	@Test(groups = { "PrevalidationCHECK" })
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

		/*** Verifying ALL TNs in SD PAC RCM and CODA ***/
		Reporting.setNewGroupName("Verifying ALL TNs");
		ArrayList<String> subDetails = new ArrayList<>();
		ArrayList<String> tnList = new ArrayList<>();
		ArrayList<String> banList = new ArrayList<>();

		String filepath = "C:\\Users\\mukesh.pandit\\Desktop\\TNAndBANs.csv";

		subDetails = getListOfSubscribers(filepath);
		for (int i = 0; i < subDetails.size(); i++) {
			tnList.add(subDetails.get(i).split("-")[0].trim());
			banList.add(subDetails.get(i).split("-")[1].trim());

		}

		Reporting.logReporter(Status.INFO, "Available TN And BAN Details: " + subDetails);
		Reporting.logReporter(Status.INFO, "Available TN List: " + tnList);
		Reporting.logReporter(Status.INFO, "Available BAN List: " + banList);

		//spidPreValidationCheck(tnList, "TU04");
		//sdPreValidationCheck(tnList, banList);
		//pacPreValidationCheck(tnList, banList);
		//rcmPreValidationCheck(tnList, banList);
		codaPreValidationCheck(tnList, banList);

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

	public static ArrayList<String> getListOfSubscribers(String filePath) {
		ArrayList<String> aList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String tn = line.toString();
				aList.add(tn);
			}
		} catch (Exception e) {
			return aList;
		}

		return aList;
	}

	public static void sdPreValidationCheck(ArrayList<String> TN, ArrayList<String> BAN) {

		/*** STEPS FOR SUBSCRIBER PREVALIDATION ***/
		Reporting.setNewGroupName("SD PV CHECKS");
		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		String tn = "";
		String ban = "";
		try {
			LoginPageSteps.appLogin_SD();
			SmartDesktopSearchPage.isHomePageDisplayed();
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopSearchPage.searchByMethod,
					sdTestData.getString("SEARCHBY_PHONE_NUMBER"));

			for (int i = 0; i < TN.size(); i++) {
				tn = TN.get(i);
				ban = BAN.get(i);
				try {
					BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.searchPageLink);
					BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopSearchPage.searchByMethod);
					statusFromSD = SmartDesktopSearchPageSteps.getSubStatus_SD(tn, ban);

					String typeOfSub = WebDriverSteps.getWebDriverSession()
							.findElement(By.xpath("//tbody//td[3]/span[1]")).getText();
					Reporting.logReporter(Status.INFO, "SUBSCRIBER TYPE FOR " + tn + " IS : " + typeOfSub);

				} catch (Exception e) {
					statusFromSD = "EXCEPTION OCCURED";
					Validate.takeStepScreenShot("EXCEPTION in SD", Status.INFO);
				}
				Reporting.logReporter(Status.INFO,
						"Status of TN in SD : " + tn + " and ban : " + ban + " is: " + statusFromSD);
			}

			Validate.takeStepScreenShot("Subscriber Status From SD", Status.INFO);

		} catch (Exception e) {
			statusFromSD = "EXCEPTION OCCURED";
			Validate.takeStepScreenShot("EXCEPTION in SD", Status.INFO);
			Reporting.logReporter(Status.INFO,
					"Status of TN in SD : " + tn + " and ban : " + ban + " is: " + statusFromSD);
		}

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();
	}

	public static void pacPreValidationCheck(ArrayList<String> TN, ArrayList<String> BAN) {

		/*** STEPS FOR SUBSCRIBER PORTING STATUS CHECK FROM PAC ***/
		Reporting.setNewGroupName("PAC PV CHECKS");
		PACSearchPage PACSearchPage = new PACSearchPage();
		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		String tn = "";
		String ban = "";

		try {
			LoginPageSteps.appLogin_PAC();
			PACSearchPage.isHomePageDisplayed();

			for (int i = 0; i < TN.size(); i++) {
				tn = TN.get(i);
				ban = BAN.get(i);
				try {
					PACSearchPageSteps.searchPhoneNo(tn);
					statusFromPAC = PACSearchPageSteps.getPortOSPCancelStatus_PAC();
					Validate.takeStepScreenShot("Subscriber Status From PAC", Status.INFO);
				} catch (Exception e) {
					statusFromPAC = "EXCEPTION OCCURED";
					Validate.takeStepScreenShot("EXCEPTION in PAC", Status.INFO);
					BaseSteps.Clicks.clickElement(PACDashboardPage.searchPageLinkBtn);
				}
				Reporting.logReporter(Status.INFO,
						"Status of TN in PAC : " + tn + " and ban : " + ban + " is: " + statusFromPAC);
			}

		} catch (Exception e) {
			statusFromPAC = "EXCEPTION OCCURED";
			Validate.takeStepScreenShot("EXCEPTION in PAC", Status.INFO);
			Reporting.logReporter(Status.INFO,
					"Status of TN in PAC : " + tn + " and ban : " + ban + " is: " + statusFromPAC);
		}

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();
	}

	public static void rcmPreValidationCheck(ArrayList<String> TN, ArrayList<String> BAN) {

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM RCM ***/
		Reporting.setNewGroupName("RCM PV CHECKS");
		RCMPage RCMPage = new RCMPage();

		String tn = "";
		String ban = "";

		try {
			LoginPageSteps.appLogin_RCM();
			RCMPage.isHomePageDisplayed();
			String resourceType = rcmTestData.getString("RESOURCE_TYPE_UMSISDN");
			BaseSteps.Clicks.clickElement(RCMPage.inventory);

			for (int i = 0; i < TN.size(); i++) {
				tn = TN.get(i);
				ban = BAN.get(i);
				try {
					RCMPageSteps.enterRequiredDetailsForTNSearchInRCM(tn, resourceType);
					statusFromRCM = RCMPage.provisioningStatus();
					Validate.takeStepScreenShot("Subscriber Status From RCM", Status.INFO);
					BaseSteps.Clicks.clickElement(RCMPage.reset);

				} catch (Exception e) {
					statusFromRCM = "EXCEPTION OCCURED";
					Validate.takeStepScreenShot("EXCEPTION in RCM", Status.INFO);
				}

				Reporting.logReporter(Status.INFO,
						"Status of TN in RCM : " + tn + " and ban : " + ban + " is: " + statusFromRCM);
			}
		} catch (Exception e) {
			statusFromRCM = "EXCEPTION OCCURED";
			Validate.takeStepScreenShot("EXCEPTION in RCM", Status.INFO);
			Reporting.logReporter(Status.INFO,
					"Status of TN in RCM : " + tn + " and ban : " + ban + " is: " + statusFromRCM);
		}

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

	}

	public static void codaPreValidationCheck(ArrayList<String> TN, ArrayList<String> BAN) {

		/*** STEPS FOR SUBSCRIBER ACTIVE STATUS FROM CODA ***/
		Reporting.setNewGroupName("CODA PV CHECKS");
		CODASearchPage CODASearchPage = new CODASearchPage();

		String tn = "";
		String ban = "";

		try {
			LoginPageSteps.appLogin_CODA();
			CODASearchPage.isHomePageDisplayed();
			BaseSteps.Waits.waitGeneric(1000);
			CODASearchPageSteps.selectEnvironment();
			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			CODASearchPage CodaSearchPage = new CODASearchPage();

			for (int i = 0; i < TN.size(); i++) {
				tn = TN.get(i);
				ban = BAN.get(i);
				try {

					BaseSteps.Clicks.clickElement(CodaSearchPage.searchPageLink);
					CODASearchPageSteps.searchPhoneNo(codaTestData, tn);

					if (CodaSearchPage.checkMultipleRecordsPresence()) {
						BaseSteps.Clicks.clickElement(CodaSearchPage.firstRowFromSearchRecords);
					}

					BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();

					pendingOrdersCountFromCODA = Integer.parseInt(CodaSearchPage.pendingOrderCount.getText());
					taskErrorsCountFromCODA = Integer.parseInt(CodaSearchPage.taskErrorCount.getText());
					validationerrorsCountFromCODA = Integer.parseInt(CodaSearchPage.validationErrorCount.getText());
					statusFromCODA = WebDriverSteps.getWebDriverForCurrentThreat()
							.findElement(By.xpath("/html/body/div[6]/div[1]/div/div/div/h1/span[2]")).getText();

					Validate.takeStepScreenShot("Subscriber Status From CODA", Status.INFO);

					BaseSteps.Clicks.clickElement(CodaSearchPage.ordersTabLink);

					orderStateFromCODA = CodaSearchPage.getOrderStatus();
					Validate.takeStepScreenShot("Order State From CODA", Status.INFO);

					BaseSteps.Clicks.clickElement(CodaSearchPage.searchPageLink);

				} catch (Exception e) {
					Validate.takeStepScreenShot("EXCEPTION in CODA", Status.INFO);
				}
				Reporting.logReporter(Status.INFO,
						"Status of TN in CODA : " + tn + " and ban : " + ban + " is: " + statusFromCODA);
				Reporting.logReporter(Status.INFO, "Status of TN in CODA - OrderStateFromCODA : " + tn + " and ban : "
						+ ban + " is: " + orderStateFromCODA);
				Reporting.logReporter(Status.INFO, "Status of TN in CODA - PendingOrdersCountFromCODA : " + tn
						+ " and ban : " + ban + " is: " + pendingOrdersCountFromCODA);
				Reporting.logReporter(Status.INFO, "Status of TN in CODA - TaskErrorsCountFromCODA : " + tn
						+ " and ban : " + ban + " is: " + taskErrorsCountFromCODA);
				Reporting.logReporter(Status.INFO, "Status of TN in CODA - ValidationerrorsCountFromCODA : " + tn
						+ " and ban : " + ban + " is: " + validationerrorsCountFromCODA);
			}

		} catch (Exception e) {
			statusFromCODA = "EXCEPTION OCCURED";
			Validate.takeStepScreenShot("EXCEPTION in CODA", Status.INFO);

			Reporting.logReporter(Status.INFO,
					"Status of TN in CODA : " + tn + " and ban : " + ban + " is: " + statusFromCODA);
			Reporting.logReporter(Status.INFO,
					"OrderStateFromCODA of TN in CODA : " + tn + " and ban : " + ban + " is: " + orderStateFromCODA);
			Reporting.logReporter(Status.INFO, "PendingOrdersCountFromCODA of TN in CODA : " + tn + " and ban : " + ban
					+ " is: " + pendingOrdersCountFromCODA);
			Reporting.logReporter(Status.INFO, "TaskErrorsCountFromCODA of TN in CODA : " + tn + " and ban : " + ban
					+ " is: " + taskErrorsCountFromCODA);
			Reporting.logReporter(Status.INFO, "ValidationerrorsCountFromCODA of TN in CODA : " + tn + " and ban : "
					+ ban + " is: " + validationerrorsCountFromCODA);
		}

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

	}

	public static void spidPreValidationCheck(ArrayList<String> TN, String expectedSPID)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		/*** STEPS FOR SPID CHECK FROM LSMS ***/
		Reporting.setNewGroupName("SPID CHECK");

		String tn = "";
		String ban = "";
		String actualspid = "";

		for (int i = 0; i < TN.size(); i++) {
			tn = TN.get(i);

			try {

				actualspid = getSPID(tn);
				if (actualspid.equalsIgnoreCase(expectedSPID)) {
					Reporting.logReporter(Status.INFO,
							"SPID Status of TN From LSMS: " + tn + " and ban : " + ban + " is: " + actualspid);

				}
			}

			catch (Exception e) {
				actualspid = "EXCEPTION OCCURED";
				Reporting.logReporter(Status.INFO,
						"SPID Status of TN From LSMS: " + tn + " and ban : " + ban + " is: " + actualspid);
			}
		}

		WebDriverSteps.closeTheBrowser();
		Reporting.printAndClearLogGroupStatements();

	}

	public static String getSPID(String sub)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		System.setProperty("karate.TN", sub);

		Map<String, Object> apiOperation = APIJava.runKarateFeature(environment,
				"classpath:tests/WNP/Test_LSMS_SPID.feature");

		Reporting.logReporter(Status.INFO, "API Operation status: " + apiOperation.get("spidAPIfeatureCallStatus"));
		Reporting.logReporter(Status.INFO, "API SIZE : " + apiOperation.size());
		Reporting.logReporter(Status.INFO, "API Operation response: " + apiOperation.get("spidAPIfeatureCallResponse"));
		Reporting.logReporter(Status.INFO, "API Operation response Soap conversion: "
				+ XmlUtils.toXml(apiOperation.get("spidAPIfeatureCallResponse")));

		String resp = XmlUtils.toXml(apiOperation.get("spidAPIfeatureCallResponse"));

		return GenericUtils.returnKeyValueFromXMLNode(resp, "lspId");

	}

}