package com.telus.wnp.steps;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.utils.Status;
import com.telus.wnp.pages.SMGICPPortDetailsPage;
import com.telus.wnp.pages.SMGICPPortSelectionPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for SMG ICP Port Selection page (Steps) > AUTHOR:
 * x241410
 ****************************************************************************
 */

public class SMGICPPortSelectionPageSteps extends BaseSteps {

	/**
	 * This method is used to do query for a Ported TN from ICP Selection Page.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param portedTN
	 *            This is the portedTN.
	 * @return nothing.
	 */
	public static void queryFromICPPortSelectionPage(String portedTN, String requestNumber) {

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

		if (requestNumber.isEmpty()) {
			BaseSteps.SendKeys.clearFieldAndSendKeysUsingJS(SMGICPPortSelectionPage.portedTN, portedTN);
		} else {
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.clearBtn);
			BaseSteps.SendKeys.sendKey(SMGICPPortSelectionPage.requestNumber, requestNumber);
		}

		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.queryBtn);
		Validate.takeStepFullScreenShot("After Query", Status.INFO);

	}

	/**
	 * Method Description: The purpose of this method is to query for a subscriber
	 * from ICP Port Selection Page
	 * 
	 * @param portedTN
	 * @param requestNumber
	 * @param portType
	 * @param oldNetwork
	 */
	public static void queryFromICPPortSelectionPageFromOSP(String sub, String portType, String oldNetwork) {

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();


		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.portType, portType);
		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.oldNetwrok, oldNetwork);
		

		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortSelectionPage.portedTN, sub);

		if (SMGICPPortSelectionPage.isQueryButtonEnabled()) {
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.queryBtn);
		}
		
		Validate.takeStepFullScreenShot("Records displayed after Query", Status.INFO);

	}

	/**
	 * Method Description: The purpose of this method is to perform query from ICP
	 * POrt Selection Page- SMG
	 * 
	 * @param portedTN
	 * @param portType
	 * @param oldNetwork
	 */
	public static void queryFromICPPortSelectionPage(String portedTN, String portType, String oldNetwork) {

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.portType, portType);
		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.oldNetwrok, oldNetwork);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortSelectionPage.portedTN, portedTN);
		// BaseSteps.SendKeys.clearFieldAndSendKeysUsingJS(SMGICPPortSelectionPage.portedTN,
		// portedTN);
		BaseSteps.Waits.waitForElementToBeClickable(SMGICPPortSelectionPage.queryBtn);
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.queryBtn);
		
		Validate.takeStepFullScreenShot("Records displayed after Query", Status.INFO);

	}

	/**
	 * Method Description: The purpose of this method is to perform query from ICP
	 * POrt Selection Page- SMG
	 * 
	 * @param portedTN
	 * @param portType
	 * @param oldNetwork
	 */
	public static void queryFromICPPortSelectionPagePortIn(String portedTN, String portType, String newNetwork) {

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.portType, portType);
		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.newNetworkProviderForQuery, newNetwork);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortSelectionPage.portedTN, portedTN);

		BaseSteps.Waits.waitGeneric(5000);
		
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.queryBtn);
		
		Validate.takeStepFullScreenShot("Records displayed after Query", Status.INFO);

	}

	/**
	 * 
	 * @param testDataJson
	 * @param expectedLRN
	 */
	public static void verifyUpdatedLRNValueFromSMG(JSONObject testDataJson, String sub, String expectedLRN) {

		navigateToSMGICPPortSelectionPage();

		String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
		String newNetwork = testDataJson.getString("QUERY_NETWORK");

		SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		
		BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
		Validate.takeStepScreenShot("Records after performing Query");
		
		BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.clickFirstRequestNumFromSearchResult);
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.clickFirstRequestNumFromSearchResult);
		BaseSteps.Windows.switchToNewWindow();
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.UPDATED_LRN_ID);
		String actualLRN = SMGICPPortDetailsPage.getUpdatedLRNText().replaceAll("-", "");
		System.out.println("actualLRN from SMG: " + actualLRN);

		Validate.assertEquals(actualLRN, expectedLRN, "LRN Mismatch", false);

	}

	/**
	 * Method Description: The purpose of this method is to select the latest record
	 * from the search grid
	 * 
	 * @param requestNumber
	 */
	public static void selectLatestRecordFromGridAndNavigateToICPPortDetails(String requestNumber) {

		// select first checkbox
		// click on the requestNumber
		// navigate to other page
		// read response Type and assert for Confirmed Status

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.searchRowFirstcheckBox);

		String requestNumberXpath = "";
		WebDriverSteps.getWebDriverSession().findElement(By.xpath(requestNumberXpath));
		/**
		 * 
		 * Need to find rowcount of the grid and then find check box referring to the
		 * request number and click on it
		 * 
		 */

		for (WebElement e : SMGICPPortSelectionPage.requestNumbersFromSearchResult) {
			if (e.getAttribute("innerHTML").equalsIgnoreCase(requestNumber)) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.searchRowFirstcheckBox);
				BaseSteps.Clicks.clickElement(e);
			}
		}

		BaseSteps.Windows.switchToNewWindow();

		/*
		 * ArrayList<String> listOfTabs1 = new
		 * ArrayList<String>(WebDriverSteps.getWebDriverSession().getWindowHandles());
		 * System.out.println(listOfTabs1);
		 * WebDriverSteps.getWebDriverSession().switchTo().window(listOfTabs1.get(2));
		 */

		String respTypeFromSMG = SMGICPPortSelectionPage.getResponseTypeFromSearchResult();
		System.out.println("respTypeFromSMG:   " + respTypeFromSMG);

	}

	/**
	 * Method Description: The purpose of this method is to create cancel SVC
	 * response- Old
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void createCancelSVCResponseFromSMG_Old(JSONObject testDataJson, String sub) {

		try {
			navigateToSMGICPPortSelectionPage();

			BaseSteps.Windows.switchToNewWindow();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT_CANCEL_SVC_RECEIVED");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPageFromOSP(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_CANCEL_SVC_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Windows.switchToNewWindow();

		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to create Cancel SVC Response from SMG" + e);
			Validate.assertTrue(false, "Exception Occured : Unable to create Cancel SVC Response from SMG");
		}

	}

	/**
	 * Method Description: The purpose of this method is to Create Port Out Response
	 * from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void createPortOutResponseFromSMG(JSONObject testDataJson, String sub, String ban) {

		// goToICPPortSelectionPage();
		navigateToSMGICPPortSelectionPage();

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

		// JSONObject smgConstantsObj = testDataJson.getJSONObject("SMG_CONSTANTS");
		String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT_CANCEL_SVC_RECEIVED");
		String newNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

		SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPageFromOSP(sub, portType, newNetwork);

		BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
		Validate.takeStepScreenShot("Records after performing Query");

		String selectActionForSearchresul = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresul);
		/*
		 * 
		 * need to write steps for confirming checkbox then save
		 * 
		 * 
		 */
	}

	/**
	 * Method Description: The purpose of this method is to navigate to SMG and
	 * Verify delay code of 8k
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param smgRequestNumber
	 */
	public static void navigateToSMGAndVerifyDelyCode8k(JSONObject testDataJson, String sub, String smgRequestNumber) {

		// goToICPPortSelectionPage();
		navigateToSMGICPPortSelectionPage();

		// JSONObject smgConstants = testDataJson.getJSONObject("SMG_CONSTANTS");

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

		if (smgRequestNumber.isEmpty() || smgRequestNumber == null) {
			BaseSteps.SendKeys.sendKey(SMGICPPortSelectionPage.portedTN, sub);

		} else {
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortSelectionPage.portedTN, "");
			BaseSteps.SendKeys.sendKey(SMGICPPortSelectionPage.portedTN, sub);
			BaseSteps.SendKeys.sendKey(SMGICPPortSelectionPage.requestNumber, smgRequestNumber);
		}

		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortSelectionPage.portType,
				testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT"));
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortSelectionPage.oldNetwrok,
				testDataJson.getString("TELUS_TU04_MOBILITY"));

		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.queryBtn);

		BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
		Validate.takeStepScreenShot("Records after performing Query");

		/*
		 * 
		 * Write steps how to verify 8k delay code
		 */

	}

	public static void enterPortedTNUnderICPPortSelectionPage(String sub) {
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		BaseSteps.SendKeys.clearFieldAndSendKeysUsingJS(SMGICPPortSelectionPage.portedTN, sub);
	}

	public static void selectPortTypeUnderICPPortSelectionPage(String portType) {
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.portType, portType);
	}

	public static void selectOldNetworkUnderICPPortSelectionPage(String oldNetwork) {
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.oldNetwrok, oldNetwork);
	}

	public static void selectActionrequestUnderICPPortSelectionPage(String selectAction) {
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectAction);
	}

	public static void clickQueryBtnUnderICPPortSelectionPage() {
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.queryBtn);
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with delay
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */

	public static void createPortOutResponseWithDelayFromSMG(JSONObject testDataJson, String sub, String oldSP) {

		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Delay ResponseType From SMG for " + sub + " ===");

		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_DELAY"));

			String time = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime, time);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.selectResponseCodeFromResponseData);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
					testDataJson.getString("RESPONSE_VALUE_PORT_RESPONSE"));

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Waits.waitGeneric(1000);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG DELAY-WAIT Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Delay ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Delay ResponseType From SMG");
		}

	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with delay
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */

	public static void createPortOutResponseModifyDDDTThenConfirmedFromSMG(JSONObject testDataJson, String sub,
			String oldSP) {

		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Delay ResponseType From SMG for " + sub + " ===");

		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));

			String time = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime, time);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.selectResponseCodeFromResponseData);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
					testDataJson.getString("RESPONSE_VALUE_PORT_RESPONSE"));

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Waits.waitGeneric(1000);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG DELAY-WAIT Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Delay ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Delay ResponseType From SMG");
		}

	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with delay
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */

	public static void createPortOutResponseWithDelayFromSMGAnH(JSONObject testDataJson, String sub, String oldSP) {

		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Delay ResponseType From SMG for " + sub + " ===");

		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);
			
			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_DELAY"));

			String time = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime, time);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.selectResponseCodeFromResponseData);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
					testDataJson.getString("RESPONSE_VALUE_PORT_RESPONSE"));

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Waits.waitGeneric(1000);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG DELAY-WAIT Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Delay ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Delay ResponseType From SMG");
		}

	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void createPortOutResponseWithConfirmFromSMG(JSONObject testDataJson, String sub, String oldSP) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);

			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);
			
			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void createPortOutResponseWithConfirmFromSMGAsOSP(JSONObject testDataJson, String sub,
			String oldNetwork) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			// String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);

			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Waits.waitGeneric(2000);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void createPortOutResponseWithConfirmFromSMG_IMEI(JSONObject testDataJson, String sub, String oldSP) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGICPPortDetailsPage.provideResponseType);

			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));

			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void createPortOutResponseWithConfirmFromSMGForWLN(JSONObject testDataJson, String sub,
			String oldSP) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_IN_RESPONSE");
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, testDataJson.getString("CONTACT_TN"));

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.olsp_Id_wln, testDataJson.getString("OLSP_ID"));
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.lsProvider_Id_wln,
					testDataJson.getString("LS_PROVIDER_ID"));

			String newdddt = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime, newdddt);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void modifyAndConfirmRequestFromSMGForWLN(JSONObject testDataJson, String sub, String ban) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_MODIFY");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.lsProvider_Id_wln,
					testDataJson.getString("LS_PROVIDER_ID"));

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.ACCT_ID);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.ACCT_ID, ban);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.save);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void createPortOutResponseWithRRFromSMGForWLN(JSONObject testDataJson, String sub, String oldSP) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_IN_RESPONSE");
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_RR"));

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, testDataJson.getString("CONTACT_TN"));

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.olsp_Id_wln, testDataJson.getString("OLSP_ID"));
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.lsProvider_Id_wln,
					testDataJson.getString("LS_PROVIDER_ID"));

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.selectResponseCodeFromResponseData);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
					testDataJson.getString("RESPONSE_VALUE_PORT_RESPONSE"));

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to create PortOut Response With RR ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void createPortOutResponseWithConfirmFromSMGAnH(JSONObject testDataJson, String sub, String oldSP) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String oldNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);

			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create cancel svc
	 * response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */

	public static void createCancelSVCResponseFromSMG(JSONObject testDataJson, String sub, String oldSP,
			String scenario) {
		Reporting.logReporter(Status.INFO, "STEP === Create Cancel SVC Response From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT_CANCEL_SVC_RECEIVED");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_CANCEL_SVC_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits
					.waitForElementVisibilityLongWait(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);

			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);
			/*
			 * 
			 * String newdddt =
			 * GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			 * BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime,
			 * newdddt);
			 * 
			 */
			if (scenario.equalsIgnoreCase("fallout")) {
				BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
						testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_RESOLUTION_REQUIRED"));

				BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
						testDataJson.getString("RESPONSE_DATA_CODE_RESOLUTION_REQUIRED"));
			} else {
				BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
						testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));
			}

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.remarksResponse,
					testDataJson.getString("INFORMATIONAL_REMARKS"));

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.save);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(1000);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS, "=== Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to create Cancel SVC Response From SMG " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to create Cancel SVC Response From SMG ");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create cancel svc
	 * response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */

	public static void createCancelSVCResponseFromSMGWithNewDDDT(JSONObject testDataJson, String sub, String oldSP) {
		Reporting.logReporter(Status.INFO, "STEP === Create Cancel SVC Response From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT_CANCEL_SVC_RECEIVED");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_CANCEL_SVC_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits
					.waitForElementVisibilityLongWait(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);

			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);
			/*
			 * String newdddt =
			 * GenericUtils.getDDDTWithDelay(testDataJson.getInt("NEW_DDDT_DELAY_IN_MINUTES"
			 * ));
			 * BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime,
			 * newdddt);
			 */

			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);

			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.remarksResponse,
					testDataJson.getString("INFORMATIONAL_REMARKS"));

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.save);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(1000);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS, "=== Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to create Cancel SVC Response From SMG " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to create Cancel SVC Response From SMG ");
		}
	}

	/**
	 * Method Description: The purpose of this method is to navigate to ICP port
	 * details page
	 */
	public static void navigateToSMGICPPortSelectionPage() {
		LoginPageSteps.appLogin_SMG();
		SMGPageSteps.verifySMGHomePageIsDisplayed();
		SMGPageSteps.clickCWNPTest();
		SMGPageSteps.clickPortCenterCT6();
		// BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.maxUserLoggedInPopUpDisplayed();
		BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.clickICPPortSelection();
		BaseSteps.Windows.switchToNewWindow();

	}

	/**
	 * Method Description: The purpose of this method is to perform SUP1-Cancel from
	 * SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void performSUP1FromSMG(JSONObject testDataJson, String sub) {

		try {
			navigateToSMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("QUERY_NETWORK");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);
			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
			
			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CANCEL");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);
			BaseSteps.Waits.waitGeneric(1000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Windows.switchToNewWindow();
			saveRequest();

			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);
			
			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG DDDT Change Request Submission for '" + sub + "' is successful===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to perform SUP1 from SMG " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to perform SUP1 from SMG");
		}

	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with RR and 8H code
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	
	public static void createPortOut8HResponseWithRRFromSMG(JSONObject testDataJson, String sub, String oldSP) {

		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Delay ResponseType From SMG for " + sub + " ===");

		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGICPPortDetailsPage.provideResponseType);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_RR"));

			/*
			 * String time =
			 * GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			 * BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime,
			 * time);
			 */

			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.selectResponseCodeFromResponseData);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
					testDataJson.getString("RESPONSE_VALUE_PORT_RESPONSE_RR"));

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG DELAY-WAIT Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With 8H/8C Response RR From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With 8H/8C Response RR From SMG");
		}

	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with RR and 8A code
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */

	public static void createPortOut8AResponseWithRRFromSMG(JSONObject testDataJson, String sub, String oldSP) {

		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Delay ResponseType From SMG for " + sub + " ===");

		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGICPPortDetailsPage.provideResponseType);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_RR"));

			String time = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));

			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime, time);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.selectResponseCodeFromResponseData);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
					testDataJson.getString("RESPONSE_VALUE_PORT_RESPONSE_RR"));

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(1000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG DELAY-WAIT Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to create PortOut Response With 8A Response RR From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With 8A Response RR From SMG");
		}

	}



	/**
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void performDDDTChange(JSONObject testDataJson, String sub) {
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

		String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
		String newNetwork = testDataJson.getString("QUERY_NETWORK");

		SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);
		
		BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
		Validate.takeStepScreenShot("Records after performing Query");
		
		String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CHANGE_DDDT");
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Windows.switchToNewWindow();
		if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
		}

		BaseSteps.Windows.switchToNewWindow();

		int intNewDelay = testDataJson.getInt("DDDT_DELAY_IN_MINUTES");
		String updatedDDDT = GenericUtils.getDDDTWithDelay(intNewDelay);

		BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.changeDDDTField, 60);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.changeDDDTField, updatedDDDT);

	}

	public static void saveRequest() {
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.save, 10);
		BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.save);
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

		BaseSteps.Waits.waitGeneric(2000);
		if (SMGICPPortDetailsPage.isDDDTConfirmPopUpDisplayed()) {
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.DDDTConfirmPopUpOKBtn);
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * WinBack with delay
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */

	public static void createPortOutResponseWithDelayWinBack(JSONObject testDataJson, String sub, String oldSP) {

		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Delay ResponseType From SMG for " + sub + " ===");

		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}

			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_DELAY"));

			String time = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime, time);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.oldSPContact, oldSP);
			BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contactTN, oldSP);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.selectResponseCodeFromResponseData);
			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.selectResponseCodeFromResponseData,
					testDataJson.getString("RESPONSE_VALUE_PORT_RESPONSE"));

			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			String expectedText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String actualText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG DELAY-WAIT Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Delay ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Delay ResponseType From SMG");
		}

	}

	public static void performSUP2FromSMG(JSONObject testDataJson, String sub, String date) {

		navigateToSMGICPPortSelectionPage();

		queryFromICPPortSelectionPagePortIn(sub, testDataJson.getString("PORT_TYPE_SELECT_PORT_IN"),
				testDataJson.getString("EXTERNAL_TU02_MOBILITY"));

		SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();
		
		BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
		BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
		Validate.takeStepScreenShot("Records after performing Query");
		
		BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortSelectionPage.selectAction,
				testDataJson.getString("SELECT_ACTION_CHANGE_DUE_DATE"));

		BaseSteps.Waits.waitGeneric(2000);
		if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
		}

		BaseSteps.Windows.switchToNewWindow();
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.desired_Due_Date);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.desired_Due_Date, date);

		SMGICPPortDetailsPageSteps.SMGSaveDetails();

		String expectedStatusMsg = testDataJson.getString("REQUEST_SUBMISSION_CONFIRMATION_MSG");
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
		BaseSteps.Waits.waitGeneric(200);
		String actualStatusMsg = SMGICPPortDetailsPage.getRequestSubmissionMsg();

		Validate.assertEquals(actualStatusMsg, expectedStatusMsg, "Unable to Modify DDDT from ICP POrt Details", false);
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void createPortOutResponseWithConfirmFromSMGWithChangedDDT(JSONObject testDataJson, String sub,
			String oldSP) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_OUT");
			String oldNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, portType, oldNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_PORT_OUT_RESPONSE");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.provideResponseType);

			BaseSteps.Dropdowns.selectByVisibleText(SMGICPPortDetailsPage.provideResponseType,
					testDataJson.getString("RESPONSE_TYPE_PORT_RESPONSE_CONFIRMED"));

			String time = GenericUtils.getDDDTWithDelay(testDataJson.getInt("DDDT_DELAY_IN_MINUTES"));
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.dueDateTime, time);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponse);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void modifyRequestFromSMG(JSONObject testDataJson, String sub, String ban) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_MODIFY");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);

			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.remarks,
					testDataJson.getString("INFORMATIONAL_REMARKS"));

			saveRequest();

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void verifyUnableToPerformSUP3FromSMGFor9BRequest(JSONObject testDataJson, String sub,
			String updatedMsg) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("EXTERNAL_TU02_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_MODIFY");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);

			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.remarks, updatedMsg);

			saveRequest();

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.errorMsgOnSubmission);
			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.errorMsgOnSubmission);
			String actualText = SMGICPPortDetailsPage.getErrorMsgTextOnSubmission();

			String expectedText = testDataJson.getString("MODIFY_AFTER_9B_ERROR_MSG");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Reporting.logReporter(Status.PASS, "=== UNABLE to Modify request on top of 9B for '" + sub + "' ===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to Modify request From SMG " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify request From SMG");
		}
	}

	/**
	 * Method Description: The purpose of this method is to verify confirmed status
	 * for Receive Cancellation
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifyRequestStatusFromICPPortSelectionPage(String sub, String portType, String oldNetwork,
			String expectedText) {

		try {

			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPageFromOSP(sub, portType, oldNetwork);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortSelectionPage.smgICPPortStatusFromICPPortSelection);
			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.smgICPPortStatusFromICPPortSelection);
			
			String actualText = SMGICPPortSelectionPage.getICPPortStatusFromICPPortSelection();

			Reporting.logReporter(Status.INFO, "Actual text from ICP Port Details: " + actualText);

			Validate.assertEquals(actualText, expectedText, "Actual and Expected Status mismatch", false);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify SMG Status: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify SMG Status From SMG");
		}

	}
	
	/**
	 * Method Description: The purpose of this method is to create port out response
	 * with confirmed response from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param oldSP
	 */
	public static void modifyAccountNumberAndConfirmRequestFromSMG(JSONObject testDataJson, String sub, String ban) {
		Reporting.logReporter(Status.INFO,
				"STEP === Create PortOut Response With Confirmed ResponseType From SMG for " + sub + " ===");
		try {
			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			String portType = testDataJson.getString("PORT_TYPE_SELECT_PORT_IN");
			String newNetwork = testDataJson.getString("TELUS_TU04_MOBILITY");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPagePortIn(sub, portType, newNetwork);

			BaseSteps.Debugs.scrollToElement(SMGICPPortSelectionPage.firstRowCheckBox);
			BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.firstRowCheckBox);
			Validate.takeStepScreenShot("Records after performing Query");

			String selectActionForSearchresult = testDataJson.getString("SELECT_ACTION_CREATE_MODIFY");
			BaseSteps.Dropdowns.selectByValue(SMGICPPortSelectionPage.selectAction, selectActionForSearchresult);

			BaseSteps.Waits.waitGeneric(2000);
			if (SMGICPPortSelectionPage.getcanSVCRequestConfirmationPopUpBtnStatus()) {
				BaseSteps.Clicks.clickElement(SMGICPPortSelectionPage.cancelSVCRequestConfirmationPopUpBtn);
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Windows.switchToNewWindow();
			SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.ACCT_ID);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SMGICPPortDetailsPage.ACCT_ID, ban);

			BaseSteps.Debugs.scrollToElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNChkBoxResponseWLN);

			saveRequest();

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGICPPortDetailsPage.requestSubmissionMsg);
			String actualText = SMGICPPortDetailsPage.getRequestSubmissionMsg();
			String expectedText = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

			Validate.assertEquals(actualText, expectedText, "Request not submitted successfully", false);

			Validate.takeStepFullScreenShot("SMG Page details after submission", Status.INFO);

			Reporting.logReporter(Status.PASS,
					"=== SMG CONFIRMED Request Submission for '" + sub + "' is successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR,
					"Unable to create PortOut Response With Confirmed ResponseType From SMG " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to create PortOut Response With Confirmed ResponseType From SMG");
		}
	}

}
