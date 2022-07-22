package com.telus.wnp.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.test.utils.Status;
import com.intuit.karate.XmlUtils;
import com.telus.api.test.utils.APIJava;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.pages.PACSearchPage;
import com.telus.wnp.steps.LoginPageSteps;
import com.telus.wnp.steps.PACDashboardPageSteps;
import com.telus.wnp.steps.PACSearchPageSteps;
import com.telus.wnp.steps.SmartDesktopSearchPageSteps;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.BaseTest;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.SystemProperties;

public class ForeignTN_Validation extends BaseTest {

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

		sub = tnTestData.getString("KOODO_SUB");
		ban = tnTestData.getString("KOODO_BAN");

	}

	@Test(groups = { "ForeignTN_Validation" })
	public void testMethod_UITestDemo(ITestContext iTestContext) throws Exception {

		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.verifyPACHomePageIsDisplayed();
		
		 validateForeignOrWLNSubscriber("PT168", "Foreign");
		 //validateForeignOrWLNSubscriber("PT168", "WLN");

	}

	public static String getSPID(String sub, String environment)
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

	public static ArrayList<String> getListOfTNs() throws FileNotFoundException {
		ArrayList<String> arryList = new ArrayList<String>();

		Scanner sc = new Scanner(new File("C:\\Users\\mukesh.pandit\\Desktop\\ForeignTN.csv"));
		sc.useDelimiter(","); // sets the delimiter pattern
		while (sc.hasNext()) {
			String s = sc.next();
			arryList.add(s);
		}
		sc.close(); // closes the scanner
		return arryList;

	}

	public static void getForeignTN(String environment, String fielpath, String SPID) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		PACSearchPage PACSearchPage = new PACSearchPage();

		try (BufferedReader br = new BufferedReader(new FileReader(fielpath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String tn = line.toString();

				searchPhoneNo(tn);
				String actualErrMsg = "";

				actualErrMsg = PACSearchPageSteps.getPortOSPCancelStatus_PAC();
				if (actualErrMsg.isEmpty()) {
					BaseSteps.Clicks.clickElement(PACDashboardPage.searchPageLinkBtn);
					BaseSteps.Waits.waitForElementVisibilityLongWait(PACSearchPage.ctn);
				} else {
					String spid = getSPID(tn, environment);
					if (spid.equalsIgnoreCase(SPID)) {
						System.out.println("====================>");
						System.out.println("==========" + tn + "=========" + spid + "=========");
						Reporting.logReporter(Status.INFO,"==========" + tn + "=========" + spid + "=========");
						Reporting.logReporter(Status.INFO, " : " + tn + " : " + spid);
						System.out.println("====================>");
					}
				}

			}
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}

	public static void validateForeignOrWLNSubscriber(String environment, String tnType) {
		String filePath = null;
		if (tnType.equalsIgnoreCase("Foreign")) {
			filePath = "C:\\Users\\mukesh.pandit\\Desktop\\ForeignTN.csv";
			getForeignTN(environment, filePath, "TU02");
		} else if (tnType.equalsIgnoreCase("WLN")) {
			filePath = "C:\\Users\\mukesh.pandit\\Desktop\\WLNTN.csv";
			getForeignTN(environment, filePath, "TU07");
		}
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
	
	/**
	 * Method Description: The purpose of this method is to perform search for
	 * particular subscriber
	 * 
	 * @param phoneNo
	 */
	public static void searchPhoneNo(String phoneNo) {
		Reporting.logReporter(Status.INFO,
				"STEP === PAC Search Page --> Enter Phone Number and click submit : " + phoneNo);
		PACSearchPage PACSearchPage = new PACSearchPage();
		BaseSteps.Waits.waitForElementVisibility(PACSearchPage.ctn, 10);
		BaseSteps.SendKeys.clearFieldAndSendKeysUsingJS(PACSearchPage.ctn, phoneNo);

		Validate.takeStepScreenShot("Entered TN for Search");

		BaseSteps.Clicks.clickElement(PACSearchPage.submit);
	}

}
