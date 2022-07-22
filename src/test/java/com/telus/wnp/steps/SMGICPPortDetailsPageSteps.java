package com.telus.wnp.steps;

import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.test.utils.Status;
import com.telus.wnp.pages.SMGICPPortDetailsPage;
import com.telus.wnp.pages.SMGICPPortSelectionPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for SMG ICP Port Details page (Steps) > AUTHOR:
 * x241410
 ****************************************************************************
 */

public class SMGICPPortDetailsPageSteps extends BaseSteps {

	public static String SMGRequestNumber;

	/**
	 * Method Description: This method is used to do save Create Cancel SVC Request
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void saveCreateCancelSVCRequest(JSONObject testDataJson, String sub, String ban) {

		Reporting.logReporter(Status.INFO,
				"STEP === SMG Create SVC Cancel Request -->  Save Create- SVC Cancel request in SMG ===");
		try {
			navigateToSMGICPPortDetailsPage();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.select_Port_Details);
			fillCreateCancelSVCRequestDetails(testDataJson);
			fillRequestDataDetails(testDataJson, sub, "", ban);

			SMGSaveDetails();

			BaseSteps.Waits.waitGeneric(2000);
			SMGRequestNumber = getSMGRequestNumberDetailsFromUI();
			System.out.println("=====================>");
			System.out.println(SMGRequestNumber);
			System.out.println("=====================>");

			String expectedStatusMsg = testDataJson.getString("REQUEST_SUBMIT_CONFIRMATION_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			
			String actualStatusMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			Validate.assertEquals(actualStatusMsg, expectedStatusMsg,
					"Unable to Save Create SVC Cancel Request from ICP POrt Details", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Save Create - Cancel SVC Request" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Save Create - Cancel SVC Request");
		}
	}

	/**
	 * This method is used to fill the Create - Cancel SVC Request.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @return nothing.
	 */
	public static void fillCreateCancelSVCRequestDetails(JSONObject testDataJson) {

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.select_Port_Details,
				testDataJson.getString("SELECT_ACTION_CREATE_CANCEL_SVC_REQUEST"));

		BaseSteps.Waits.waitUntilPageLoadComplete();

		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NNSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NLSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.ONSP_ID,
				testDataJson.getString("TELUS_TU04_MOBILITY"));

		String desiredDueDT = "";
		int dddtDelayInMinutes;

		try {
			if (testDataJson.getBoolean("DDT_FUTURE_DATE_FLAG")) {
				desiredDueDT = GenericUtils.getDDDTWithDelay(1440);
			} else {
				dddtDelayInMinutes = testDataJson.getInt("DDDT_DELAY_IN_MINUTES");
				desiredDueDT = GenericUtils.getDDDTWithDelay(dddtDelayInMinutes);
			}
		} catch (Exception e) {
			dddtDelayInMinutes = testDataJson.getInt("DDDT_DELAY_IN_MINUTES");
			desiredDueDT = GenericUtils.getDDDTWithDelay(dddtDelayInMinutes);
		}

		BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.desired_Due_Date, 10);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, desiredDueDT);

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.requestor, testDataJson.getString("REQUEST_CREATOR"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contact_TN, testDataJson.getString("CONTACT_TN"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.new_SP_contact, testDataJson.getString("NEW_SP_CONTACT"));

	}

	/**
	 * Method Description: This method is used to fill the Request Data details.
	 * 
	 * @param testDataJson
	 * @param sub1
	 * @param sub2
	 * @param accountNumber
	 */
	public static void fillRequestDataDetails(JSONObject testDataJson, String sub1, String sub2, String accountNumber) {

		Reporting.logReporter(Status.INFO,
				"STEP === Request Data --> Fill request data from SMG ICP Port Details Page===");
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.PORTED_TN, sub1);

		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.portedTNCheckBox);
		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNCheckBox);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.subName, testDataJson.getString("SUBSCRIBER_NAME"));

		if (testDataJson.getBoolean("MULTIBAN_SUB")) {
			BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.addMutipleTNBtn, 10);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.addMutipleTNBtn);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.PORTED_TN2, sub2);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNMultiBanChkBox);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.subName2, testDataJson.getString("SUBSCRIBER_NAME_2"));
		}

		try {
			if (testDataJson.getBoolean("INCORRECT_ACCOUNT_NUMBER_FLAG")) {
				String incorrectAccountNumber = testDataJson.getString("INCORRECT_ACCOUNT_NUMBER");
				BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ACCT_ID, incorrectAccountNumber);
			} else
				BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ACCT_ID, accountNumber);
		} catch (Exception e) {
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ACCT_ID, accountNumber);
		}

		enterAgencyAndBillingDetails(testDataJson);

	}

	/**
	 * Method Description: This method is used to fill the Request Data details.
	 * 
	 * @param testDataJson
	 * @param sub1
	 * @param sub2
	 * @param accountNumber
	 */
	public static void fillRequestDataDetailsWithIncorrectIMEI(JSONObject testDataJson, String sub,
			String incorrectIMEI) {

		Reporting.logReporter(Status.INFO,
				"STEP === Request Data --> Fill request data from SMG ICP Port Details Page===");
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.PORTED_TN);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.PORTED_TN, sub);

		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.portedTNCheckBox);
		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNCheckBox);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.subName, testDataJson.getString("SUBSCRIBER_NAME"));

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ESN_IMEI, incorrectIMEI);

		enterAgencyAndBillingDetails(testDataJson);

	}

	/**
	 * Method Description: This method is used to save Create- Port In Request
	 * details for single subscriber with ban details.
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 * @param portReqType
	 */
	public static void saveCreatePortRequestDetailsForSingleSub(JSONObject testDataJson, String sub, String ban,
			String portReqType) {

		try {
			navigateToSMGICPPortDetailsPage();

			fillCreatePortInRequestDetails(testDataJson, portReqType);
			Validate.takeStepScreenShot("Port In Request Details");

			fillRequestDataDetails(testDataJson, sub, "", ban);
			Validate.takeStepScreenShot("Port In Request Data Details");

			Validate.takeStepFullScreenShot("SMG Details", Status.INFO);

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			SMGSaveDetails();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualRequestSubmissionMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			String expectedRequestSubmissionMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");

			Validate.assertEquals(actualRequestSubmissionMsg, expectedRequestSubmissionMsg,
					"SMG Request Submission Expected Message not displayed", false);

			BaseSteps.Waits.waitGeneric(2000);
			SMGRequestNumber = getSMGRequestNumberDetailsFromUI();
			System.out.println("=========================>");
			System.out.println(SMGRequestNumber);
			System.out.println("=========================>");

			Reporting.logReporter(Status.PASS, "Port Out request details saved with request number :'"
					+ SMGRequestNumber + "'" + " for sub : " + sub);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to save portout details from SMG " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to save portout details from SMG");
		}

	}

	/**
	 * This method is used to save Create- Port In Request details for single sub.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param portedTN
	 *            This is the portedTN.
	 * @param ban
	 *            This is the ban(account number).
	 * @return nothing.
	 */
	public static void saveCreatePortOutRequestDetailsForMultiBan(JSONObject testDataJson, String sub1, String sub2,
			String ban, String portReqType) {

		try {
			navigateToSMGICPPortDetailsPage();

			fillCreatePortInRequestDetails(testDataJson, portReqType);
			fillRequestDataDetails(testDataJson, sub1, sub2, ban);

			Validate.takeStepFullScreenShot("SMG Details", Status.INFO);

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			SMGSaveDetails();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualRequestSubmissionMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			String expectedRequestSubmissionMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");

			Validate.assertEquals(actualRequestSubmissionMsg, expectedRequestSubmissionMsg,
					"SMG Request Submission Expected Message not displayed", false);

			BaseSteps.Waits.waitGeneric(2000);
			SMGRequestNumber = getSMGRequestNumberDetailsFromUI();
			System.out.println("=========================>");
			System.out.println(SMGRequestNumber);
			System.out.println("=========================>");

			Reporting.logReporter(Status.PASS, "Port Out request details saved with request number :'"
					+ SMGRequestNumber + "'" + " for sub(s) : " + sub1 + " and " + sub2);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to save portout details from SMG " + e);
			Validate.assertTrue(false, "Exeption Occured: Unable to save portout details from SMG");
		}
	}

	/**
	 * This method is used to fill the Create - Port In Request details.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @return nothing.
	 */
	public static void fillCreatePortInRequestDetails(JSONObject testDataJson, String portReqType) {

		Reporting.logReporter(Status.INFO,
				"STEP === Request Details--> Fill Request details from SMG ICP Port Details Page===");

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Waits.waitUntilPageLoadComplete();
		BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGICPPortDetailsPage.select_Port_Details);
		if (portReqType.equalsIgnoreCase("portout")) {
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.select_Port_Details,
					testDataJson.getString("CREATE_PORT_IN_REQUEST"));
		} else if (portReqType.equalsIgnoreCase("portin")) {
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.select_Port_Details,
					testDataJson.getString("CREATE_PORT_OUT_REQUEST"));
		}
		BaseSteps.Waits.waitUntilPageLoadComplete();

		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NNSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NLSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.ONSP_ID,
				testDataJson.getString("TELUS_TU04_MOBILITY"));

		if (testDataJson.getBoolean("AUTO_ACTIVATE_STATUS")) {
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.AUTOACT_ID,
					testDataJson.getString("AUTO_ACTIVATE_VALUE"));
		}

		String desiredDueDT = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, desiredDueDT);

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.LRN_ID, testDataJson.getString("LRN_ID"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.requestor, testDataJson.getString("REQUEST_CREATOR"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contact_TN, testDataJson.getString("CONTACT_TN"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.new_SP_contact, testDataJson.getString("NEW_SP_CONTACT"));

	}

	/**
	 * This method is used to perform SMG page save.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void SMGSaveDetails() {
		Reporting.logReporter(Status.INFO, "STEP === Save Port details --> Save Port request details from SMG ===");
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Waits.waitGeneric(500);

		BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.save, 10);
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.save);
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

		BaseSteps.Waits.waitGeneric(2000);
		if (SMGICPPortDetailsPage.isDDDTConfirmPopUpDisplayed()) {
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.DDDTConfirmPopUpOKBtn);
		}

	}

	/**
	 * This method is used to return request details after SMG save is done.
	 * 
	 * @param none.
	 * @return if success, request number else return ""
	 */
	public static String getSMGRequestNumberDetailsFromUI() {
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		String reqNum;
		try {
			reqNum = (String) ((JavascriptExecutor) WebDriverSession.getWebDriverSession())
					.executeScript("return jQuery(arguments[0]).text();", SMGICPPortDetailsPage.requestNumber);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to retrieve the request number from ICP port details page");
			return "";
		}

		return reqNum;
	}

	/**
	 * Method Description: The purpose of this method is to verify Response and Code
	 * from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param response
	 * @param crespCode
	 */
	public static void verifyResponseAndCodeFromSMGICPPortDetails(JSONObject testDataJson, String sub) {

		try {

			String expectedResponseType = testDataJson.getString("EXPECTED_RESPONSE_TYPE");
			String expectedResponseCode = testDataJson.getString("EXPECTED_RESPONSE_CODE");

			/**
			 * Available options for portRecordType drop down Port In Request Port Out
			 * Request Port In Response Port Out Response
			 */
			navigateToSMGICPPortDetailsPage();
			queryFromICPPortDetailsPage(testDataJson, sub);
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			if (SMGICPPortDetailsPage.expandResponseDataSection())
				BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.responseDataCollapsedStatus);
			// responseDataCollapsedStatus.click();
			String actualResponseType = SMGICPPortDetailsPage.responseTypeFromPortResponse();
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.responseCodeVerifyText);
			String actualResponseCode = SMGICPPortDetailsPage.getResponseCodeVerifyText();

			Validate.assertEquals(actualResponseType, expectedResponseType, "Response Type Mismatch", false);
			Validate.assertEquals(actualResponseCode, expectedResponseCode, "Response Code Detail Text Mismatch",
					false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify ResponseType and Code for sub '" + sub + "'");
			Validate.assertTrue(false, "Exception Occured: Unable to verify ResponseType and Code for: " + sub);
		}
	}

	/**
	 * Method Description: The purpose of this method is to get Response Type from
	 * ICP Port Details Page - SMG
	 * 
	 * @return responseType
	 */
	public static String getResponseTypeFromICPPortDetails() {

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		String responseType;

		try {

			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.searchRowFirstcheckBox);

			for (WebElement e : SMGICPPortSelectionPage.requestNumbersFromSearchResult) {
				String request = e.getText().toString();
			}

			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowReqNumLink);

			// BaseSteps.Waits.waitGeneric(10000);

			BaseSteps.Windows.switchToNewWindow();
			BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.responseType, 15);
			responseType = SMGICPPortDetailsPage.responseTypeFromPortResponse();
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to retrieve the request type from ICP Port Details");
			return "";
		}

		return responseType;
	}

	/**
	 * Method Description: Send Confirm Response from SMG
	 * 
	 * @param testData
	 */
	public static void SendConfirmedResponseFromSMG(JSONObject testDataJson) {
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
				testDataJson.getString("SMG_STATUS_CONFIRMED"));
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.remarksResponse,
				testDataJson.getString("INFORMATIONAL_REMARKS"));

		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
		String reqMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();
		System.out.println(reqMsg);

	}

	public static void queryFromICPPortDetailsPage(JSONObject testDataJson, String sub) {

		String ownerSPID = testDataJson.getString("OWNER_SPID");
		String portRecordType = testDataJson.getString("PORT_RECORD_TYPE");

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.queryPortRecordType);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryPortRecordType, portRecordType);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.queryPortedTN, sub);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryOwnerSPID, ownerSPID);

		if (SMGICPPortDetailsPage.isQueryButtonEnabled()) {
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.queryBtn);
		}
	}

	public static void enterAgencyAndBillingDetails(JSONObject testDataJson) {

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

		String agencyDate = GenericUtils.getCurrentDateInMMDDYYYY();
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.agency_date, agencyDate);

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.agency_Name, testDataJson.getString("AGENCY_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.BILLFIRSTNM, testDataJson.getString("BILLING_FIRST_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.BILLLASTNM, testDataJson.getString("BILLING_LAST_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.BUSNM, testDataJson.getString("BILLING_BUS_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.Street, testDataJson.getString("BILLING_ADDRESS_STREET"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.city, testDataJson.getString("BILLING_ADDRESS_CITY"));
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.state, testDataJson.getString("BILLING_ADDRESS_STATE"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.zip_code, testDataJson.getString("BILLING_ADDRESS_ZIPCODE"));

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.remarks, testDataJson.getString("INFORMATIONAL_REMARKS"));

	}

	public static void performSUP1FromSMGICPPortDetailsPage(JSONObject testDataJson, String sub) {
		try {
			navigateToSMGICPPortDetailsPage();
			queryFromICPPortDetailsPage(testDataJson, sub);
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.checkSUBCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.checkSUBCheckBox);

			BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.select_Port_Details,
					testDataJson.getString("SELECT_ACTION_CANCEL"));

			Validate.takeStepScreenShot("Selected Cancel from SMG Page", Status.INFO);
			SMGSaveDetails();
			String expectedMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			Validate.assertEquals(actualMsg, expectedMsg, "Unable to perform SUP1 from SMG", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to perform SUP1 from SMG " + e);
			Validate.takeStepFullScreenShot("Unable to perform SUP1 from SMG", Status.FAIL);
			Validate.assertTrue(false, "Unable to perform SUP1 from SMG");
		}
	}

	public static void performSUP3FromSMGICPPortDetailsPage(JSONObject testDataJson, String modifyParam,
			String modifyValue) {
		try {

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.select_Port_Details);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.select_Port_Details,
					testDataJson.getString("SELECT_ACTION_MODIFY"));

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.checkSUBCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.checkSUBCheckBox);

			if (modifyParam.equalsIgnoreCase("BAN")) {
				BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.ACCT_ID);
				BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.ACCT_ID, modifyValue);
				Validate.takeStepScreenShot("Modified Account Number Details", Status.INFO);
			} else if (modifyParam.equalsIgnoreCase("IMEI")) {
				BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.ESN_IMEI);
				BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.ESN_IMEI, modifyValue);
				Validate.takeStepScreenShot("Modified IMEI Number Details", Status.INFO);
			}

			SMGSaveDetails();
			String expectedMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			Validate.assertEquals(actualMsg, expectedMsg, "Unable to perform SUP3 from SMG", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to perform SUP3 from SMG " + e);
			Validate.assertTrue(false, "Unable to perform SUP3 from SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to navigate to SMG and
	 * Verify delay code of 8k
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param smgRequestNumber
	 */
	public static void navigateToSMGAndVerifyDelyCode8k(JSONObject testDataJson, String sub) {

		// goToICPPortSelectionPage();
		navigateToSMGICPPortDetailsPage();

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.clearBtn);
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.clearBtn);

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryPortRecordType,
				testDataJson.getString("QUERY_PORT_RECORD_TYPE"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.queryPortedTN, sub);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryOwnerSPID,
				testDataJson.getString("OWNER_SPID"));

		if (SMGICPPortDetailsPage.isQueryButtonEnabled()) {
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.queryBtn);
		}

		String expectedResponseType = testDataJson.getString("EXPECTED_DELAY_RESPONSE_TYPE");
		String expectedResponseCodeLabel = testDataJson.getString("EXPECTED_DELAY_RESPONSE_CODE_LABEL");
		/*
		 * String expectedResponseCodeText =
		 * testDataJson.getString("EXPECTED_DELAY_RESPONSE_CODE_TEXT");; String
		 * expectedResponseCodeDetailText =
		 * testDataJson.getString("EXPECTED_DELAY_RESPONSE_CODE_DETAIL_TEXT");;
		 */
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.responseType);
		String actualResponseType = SMGICPPortDetailsPage.responseTypeFromPortResponse();

		if (SMGICPPortDetailsPage.expandResponseDataSection())
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.responseDataCollapsedStatus);

		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.responseCodeText);
		String actualResponseCodeLabel = SMGICPPortDetailsPage.getResponseCodeText();
		/*
		 * String actualResponseCodeText =
		 * SMGICPPortDetailsPage.getResponseCodeVerifyText(); String
		 * actualResponseCodeDetailText =
		 * SMGICPPortDetailsPage.getResponseCodeDetailsVerifyText();
		 */
		Validate.assertEquals(actualResponseType, expectedResponseType, "Unable to Compare ResponseType from SMG",
				false);
		Validate.assertEquals(actualResponseCodeLabel, expectedResponseCodeLabel,
				"Unable Compare ResposeCodeLabel from SMG", false);

		Reporting.logReporter(Status.PASS, "Actual Port Response Type is: " + actualResponseCodeLabel
				+ " and Expected Response Type is: " + expectedResponseType);
		Reporting.logReporter(Status.PASS, "Actual Response Detail is: " + actualResponseType
				+ " and Expected Response Detail is: " + expectedResponseCodeLabel);

		/*
		 * Validate.assertEquals(actualResponseCodeText, expectedResponseCodeText,
		 * "Unable Compare ResposeCodeText from SMG", true);
		 * Validate.assertEquals(actualResponseCodeDetailText,
		 * expectedResponseCodeDetailText,
		 * "Unable Compare ResposeCodeDetailText from SMG", true);
		 */
	}

	/**
	 * Method Description: The purpose of this method is to navigate to SMG and
	 * Verify delay code of 9A
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param smgRequestNumber
	 */
	public static void navigateToSMGAndVerifyRejectionCode9A(JSONObject testDataJson, String sub) {

		// goToICPPortSelectionPage();
		navigateToSMGICPPortDetailsPage();

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.clearBtn);

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryPortRecordType,
				testDataJson.getString("QUERY_PORT_RECORD_TYPE"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.queryPortedTN, sub);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryOwnerSPID,
				testDataJson.getString("OWNER_SPID"));

		if (SMGICPPortDetailsPage.isQueryButtonEnabled()) {
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.queryBtn);
		}

		String expectedResponseType = testDataJson.getString("EXPECTED_REJECTED_RESPONSE_TYPE");
		String expectedResponseCodeText = testDataJson.getString("EXPECTED_REJECTED_RESPONSE_CODE_TEXT");
		String expectedResponseCodeDetailText = testDataJson.getString("EXPECTED_REJECTED_RESPONSE_CODE_DETAIL_TEXT");

		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.responseType);
		String actualResponseType = SMGICPPortDetailsPage.responseTypeFromPortResponse();

		if (SMGICPPortDetailsPage.expandResponseDataSection())
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.responseDataCollapsedStatus);

		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.responseCodeVerifyText);
		String actualResponseCodeText = SMGICPPortDetailsPage.getResponseCodeVerifyText();
		String actualResponseCodeDetailText = SMGICPPortDetailsPage.getResponseCodeDetailsVerifyText();

		Validate.assertEquals(actualResponseType, expectedResponseType, "Unable to Compare ResponseType from SMG",
				false);
		Validate.assertEquals(actualResponseCodeText, expectedResponseCodeText,
				"Unable Compare ResposeCodeText from SMG", true);
		Validate.assertEquals(actualResponseCodeDetailText, expectedResponseCodeDetailText,
				"Unable Compare ResposeCodeDetailText from SMG", true);

	}

	/**
	 * Method Description: The purpose of this method is to navigate to SMG and
	 * Verify delay code of 8k
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param smgRequestNumber
	 */
	public static void navigateToSMGAndVerifyRejectionCode(JSONObject testDataJson, String sub) {

		// goToICPPortSelectionPage();
		navigateToSMGICPPortDetailsPage();

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.clearBtn);

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryPortRecordType,
				testDataJson.getString("QUERY_PORT_RECORD_TYPE"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.queryPortedTN, sub);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.queryOwnerSPID,
				testDataJson.getString("OWNER_SPID"));

		if (SMGICPPortDetailsPage.isQueryButtonEnabled()) {
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.queryBtn);
		}

		String expectedResponseType = testDataJson.getString("EXPECTED_RESPONSE_TYPE_RR");
		String expectedResponseCodeText = testDataJson.getString("EXPECTED_REJECTED_RESPONSE_CODE_TEXT");
		String expectedResponseCodeDetailText = testDataJson.getString("EXPECTED_RESPONSE_CODE_DETAIL_TEXT");

		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.responseType);
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.responseType);
		String actualResponseType = SMGICPPortDetailsPage.responseTypeFromPortResponse();

		if (SMGICPPortDetailsPage.expandResponseDataSection())
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.responseDataCollapsedStatus);

		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.responseCodeVerifyText);
		String actualResponseCodeText = SMGICPPortDetailsPage.getResponseCodeVerifyText();
		String actualResponseCodeDetailText = SMGICPPortDetailsPage.getResponseCodeDetailsVerifyText();

		Validate.assertEquals(actualResponseType, expectedResponseType, "Unable to Compare ResponseType from SMG",
				false);
		Validate.assertEquals(actualResponseCodeText, expectedResponseCodeText,
				"Unable Compare ResposeCodeText from SMG", true);
		Validate.assertEquals(actualResponseCodeDetailText, expectedResponseCodeDetailText,
				"Unable Compare ResposeCodeDetailText from SMG", true);

	}

	/**
	 * Method Description: This method is used to fill the Request Data details.
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param imei
	 */
	public static void fillRequestDataDetails(JSONObject testDataJson, String sub, String imei) {

		Reporting.logReporter(Status.INFO,
				"STEP === Request Data --> Fill request data from SMG ICP Port Details Page with ESN/MEID Number===");

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.PORTED_TN, sub);

		BaseSteps.Waits.waitForElementToBeClickable(SMGICPPortDetailsPage.portedTNCheckBox);
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNCheckBox);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.subName, testDataJson.getString("SUBSCRIBER_NAME"));

		BaseSteps.Waits.waitGeneric(1000);

		try {
			if (testDataJson.getBoolean("INCORRECT_ESN_NUMBER_FLAG")) {
				String incorrectESNNumber = testDataJson.getString("INCORRECT_IMEI_NUMBER");
				BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ESN_IMEI, incorrectESNNumber);
			} else
				BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ESN_IMEI, imei);
		} catch (Exception e) {
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ESN_IMEI, imei);
		}

		enterAgencyAndBillingDetails(testDataJson);

	}

	/**
	 * Method Description: This method is used to save Create- Port In Request
	 * details for single subscriber with ban details.
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 * @param portReqType
	 */
	public static void saveCreatePortRequestDetailsWithIncorrectIMEI(JSONObject testDataJson, String sub,
			String incorrectImei, String portReqType) {

		try {
			navigateToSMGICPPortDetailsPage();

			fillCreatePortInRequestDetails(testDataJson, portReqType);
			fillRequestDataDetailsWithIncorrectIMEI(testDataJson, sub, incorrectImei);

			Validate.takeStepFullScreenShot("SMG Details", Status.INFO);

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			SMGSaveDetails();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualRequestSubmissionMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			String expectedRequestSubmissionMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");

			Validate.assertEquals(actualRequestSubmissionMsg, expectedRequestSubmissionMsg,
					"SMG Request Submission Expected Message not displayed", false);

			BaseSteps.Waits.waitGeneric(2000);
			SMGRequestNumber = getSMGRequestNumberDetailsFromUI();
			System.out.println("=========================>");
			System.out.println(SMGRequestNumber);
			System.out.println("=========================>");

			Reporting.logReporter(Status.PASS, "Port Out request details saved with request number :'"
					+ SMGRequestNumber + "'" + " for sub : " + sub);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to save portout details from SMG " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to save portout details from SMG");
		}

	}

	/**
	 * This method is used to fill the Create - Port In Request details.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @return nothing.
	 */
	public static void fillCreatePortInRequestWithModifiableDDDTAndLRN(JSONObject testDataJson, String portReqType,
			String valueToBeUpdated) {

		Reporting.logReporter(Status.INFO,
				"STEP === Request Details--> Fill Request details from SMG ICP Port Details Page===");

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

		if (portReqType.equalsIgnoreCase("portout"))
			BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.select_Port_Details,
					testDataJson.getString("SELECT_ACTION_CREATE_PORT_IN_REQUEST"));
		else if (portReqType.equalsIgnoreCase("portin"))
			BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.select_Port_Details,
					testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_REQUEST"));

		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NNSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NLSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.ONSP_ID,
				testDataJson.getString("TELUS_TU04_MOBILITY"));

		if (testDataJson.getBoolean("AUTO_ACTIVATE_STATUS")) {
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.AUTOACT_ID,
					testDataJson.getString("AUTO_ACTIVATE_VALUE"));
		}

		String desiredDueDT = null;

		if (valueToBeUpdated.equalsIgnoreCase("dddtandlrn")) {
			desiredDueDT = GenericUtils.getDDDTWithDelay(testDataJson.getInt("OLD_DDDT_IN_MINUTES"));
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, desiredDueDT);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.LRN_ID, testDataJson.getString("OLD_LRN_ID"));
		} else if (valueToBeUpdated.equalsIgnoreCase("onlydddt")) {
			desiredDueDT = GenericUtils.getDDDTWithDelay(testDataJson.getInt("OLD_DDDT_IN_MINUTES"));
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, desiredDueDT);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.LRN_ID, testDataJson.getString("LRN_ID"));
		}

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.requestor, testDataJson.getString("REQUEST_CREATOR"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contact_TN, testDataJson.getString("CONTACT_TN"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.new_SP_contact, testDataJson.getString("NEW_SP_CONTACT"));

	}

	public static void fillDetailsWIthUpdatedDDDTAndLRN(JSONObject testDataJson) {

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		String desiredDueDT = GenericUtils.getDDDTWithDelay(testDataJson.getInt("UPDATED_DDDT_VALUE"));

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, desiredDueDT);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.LRN_ID, testDataJson.getString("UPDATED_LRN_ID"));
	}

	/**
	 * This method is used to save Create- Port In Request details for single
	 * subscriber with ban details.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param portedTN
	 *            This is the portedTN.
	 * @param ban
	 *            This is the ban(account number).
	 * @return nothing.
	 */
	public static void saveCreatePortRequestDetailsWithModifiableDDDTAndLRN(JSONObject testDataJson, String sub,
			String ban, String portReqType, String valueToBeUpdated) {

		try {
			navigateToSMGICPPortDetailsPage();

			fillCreatePortInRequestWithModifiableDDDTAndLRN(testDataJson, portReqType, valueToBeUpdated);
			fillRequestDataDetails(testDataJson, sub, "", ban);

			Validate.takeStepFullScreenShot("SMG Details", Status.INFO);

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			SMGSaveDetails();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualRequestSubmissionMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			String expectedRequestSubmissionMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");

			Validate.assertEquals(actualRequestSubmissionMsg, expectedRequestSubmissionMsg,
					"SMG Request Submission Expected Message not displayed", false);

			BaseSteps.Waits.waitGeneric(2000);
			SMGRequestNumber = getSMGRequestNumberDetailsFromUI();
			System.out.println("=========================>");
			System.out.println(SMGRequestNumber);
			System.out.println("=========================>");

			Reporting.logReporter(Status.PASS,
					"Port Out request details saved with request number :'" + SMGRequestNumber + "'");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to save portout details from SMG " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to save portout details from SMG fro sub " + sub);
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify Response and Code
	 * from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param response
	 * @param crespCode
	 */
	public static void verifyResponseFromSMGICPPortDetails(JSONObject testDataJson, String sub,
			String expectedResponseType) {

		try {

			/**
			 * Available options for portRecordType drop down Port In Request Port Out
			 * Request Port In Response Port Out Response
			 */
			navigateToSMGICPPortDetailsPage();
			queryFromICPPortDetailsPage(testDataJson, sub);
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.responseType);
			BaseSteps.Waits.waitGeneric(1000);
			String actualResponseType = SMGICPPortDetailsPage.responseTypeFromPortResponse();

			Validate.assertEquals(actualResponseType, expectedResponseType,
					"Unable to Response type from ICP POrt Details", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify ResponseType for sub '" + sub + "'" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify Response from SMG for sub :" + sub);
		}
	}

	public static void updateDDDTAndLRNFromSMG(JSONObject testDataJson, String sub, String date) {
		navigateToSMGICPPortDetailsPage();
		queryFromICPPortDetailsPage(testDataJson, sub);

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.select_Port_Details);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.select_Port_Details,
				testDataJson.getString("SELECT_ACTION_MODIFY"));
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.desired_Due_Date);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.desired_Due_Date, date);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.LRN_ID, testDataJson.getString("LRN_ID"));

		SMGSaveDetails();

		String expectedStatusMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
		String actualStatusMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

		Validate.assertEquals(actualStatusMsg, expectedStatusMsg, "Unable to Modify Request from ICP POrt Details",
				false);
	}

	public static void updateDDDTFromSMG(JSONObject testDataJson, String sub, String date) {
		navigateToSMGICPPortDetailsPage();
		queryFromICPPortDetailsPage(testDataJson, sub);

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.select_Port_Details);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.select_Port_Details,
				testDataJson.getString("SELECT_ACTION_CHANGE_DUE_DATE"));
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.desired_Due_Date);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, date);

		SMGSaveDetails();

		String expectedStatusMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
		String actualStatusMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

		Validate.assertEquals(actualStatusMsg, expectedStatusMsg, "Unable to Modify DDDT from ICP Port Details", false);
	}

	/**
	 * Method Description: Navigate to SMG ICP Port Details Page
	 */
	public static void navigateToSMGICPPortDetailsPage() {
		LoginPageSteps.appLogin_SMG();
		SMGPageSteps.verifySMGHomePageIsDisplayed();
		SMGPageSteps.clickCWNPTest();

		SMGPageSteps.clickPortCenterCT6();
		// BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.maxUserLoggedInPopUpDisplayed();
		BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.clickICPPortDetails();
		BaseSteps.Windows.switchToNewWindow();

	}

	/**
	 * Method Description: The purpose of this method is to return SMG Request
	 * Number
	 * 
	 * @return
	 */
	public static String getSMGReqNumber() {

		if (SMGRequestNumber != null)
			return SMGRequestNumber;
		else
			return "";
	}

	public static void verifyUpdatedDDDTFromSMG(JSONObject testDataJson, String sub) {
		navigateToSMGICPPortDetailsPage();
		queryFromICPPortDetailsPage(testDataJson, sub);

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		/**
		 * Write steps to read DDDT after search and validate
		 * 
		 */
	}

	public static void verifyUnableToPerformSUP3FromSMGFor9BRequest(JSONObject testDataJson, String modifyParam,
			String modifyValue) {
		try {

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.select_Port_Details);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.select_Port_Details,
					testDataJson.getString("SELECT_ACTION_MODIFY"));

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.checkSUBCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.checkSUBCheckBox);

			if (modifyParam.equalsIgnoreCase("BAN")) {
				BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.ACCT_ID);
				BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.ACCT_ID, modifyValue);
			} else if (modifyParam.equalsIgnoreCase("IMEI")) {
				BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.ESN_IMEI);
				BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.ESN_IMEI, modifyValue);
			}

			SMGSaveDetails();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			String expectedMsg = testDataJson.getString("MODIFY_AFTER_9B_ERROR_MSG");
			// String expectedMsg =
			// testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");

			Validate.assertEquals(actualMsg, expectedMsg, "Unable to perform SUP3 from SMG", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to perform SUP3 from SMG " + e);
			Validate.takeStepFullScreenShot("Unable to perform SUP3 from SMG", Status.FAIL);
			// Validate.assertTrue(false, "Unable to perform SUP3 from SMG");
		}
	}

	/**
	 * This method is used to fill the Create - Cancel SVC Request.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @return nothing.
	 */
	public static void fillCreateCancelSVCRequestDetailsWithFutureDate(JSONObject testDataJson, String desiredDueDT) {

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.select_Port_Details,
				testDataJson.getString("SELECT_ACTION_CREATE_CANCEL_SVC_REQUEST"));

		BaseSteps.Waits.waitUntilPageLoadComplete();

		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NNSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.NLSP_ID,
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.ONSP_ID,
				testDataJson.getString("TELUS_TU04_MOBILITY"));

		BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.desired_Due_Date, 10);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, desiredDueDT);

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.requestor, testDataJson.getString("REQUEST_CREATOR"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contact_TN, testDataJson.getString("CONTACT_TN"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.new_SP_contact, testDataJson.getString("NEW_SP_CONTACT"));

	}

	/**
	 * Method Description: This method is used to do save Create Cancel SVC Request
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void saveCreateCancelSVCRequestWithFutureDate(JSONObject testDataJson, String sub, String ban,
			String futureDate) {

		Reporting.logReporter(Status.INFO,
				"STEP === SMG Create SVC Cancel Request -->  Save Create- SVC Cancel request in SMG ===");
		try {
			navigateToSMGICPPortDetailsPage();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.select_Port_Details);
			fillCreateCancelSVCRequestDetailsWithFutureDate(testDataJson, futureDate);
			fillRequestDataDetails(testDataJson, sub, "", ban);

			SMGSaveDetails();

			BaseSteps.Waits.waitGeneric(2000);
			SMGRequestNumber = getSMGRequestNumberDetailsFromUI();
			System.out.println("=====================>");
			System.out.println(SMGRequestNumber);
			System.out.println("=====================>");

			String expectedStatusMsg = testDataJson.getString("REQUEST_SUBMIT_CONFIRMATION_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualStatusMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

			Validate.assertEquals(actualStatusMsg, expectedStatusMsg,
					"Unable to Save Create SVC Cancel Request from ICP POrt Details", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Save Create - Cancel SVC Request" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Save Create - Cancel SVC Request");
		}
	}

}
