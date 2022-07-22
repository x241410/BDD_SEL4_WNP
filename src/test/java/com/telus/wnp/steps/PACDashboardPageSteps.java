package com.telus.wnp.steps;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import com.test.utils.Status;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.pages.PACSearchPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for PAC Dashboard page (Steps) > AUTHOR: x241410
 ****************************************************************************
 */

public class PACDashboardPageSteps extends BaseSteps {

	public static String PACRequestNumber = null;

	/**
	 * Method Description: The purpose of this method is to Verify Port Summary
	 * Event List from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifyPortSummaryEventListfromPAC(JSONObject testDataJson, String sub, String condition) {
		Reporting.logReporter(Status.INFO,
				"STEP === Port Summary in PAC --> List of Events validations in Port Summary ===");

		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.searchPhoneNo(sub);
		handleMultipleRecordsDisplayed();

		verifyPortSummary(testDataJson, condition);

	}

	/**
	 * Method Description: The purpose of this method is to verify POrt Details for
	 * Interbrand
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void verifyPortDetailsForInterbrand(JSONObject testDataJson, String sub, String ban) {

		Reporting.logReporter(Status.INFO,
				"STEP === Port Status validation --> Verify Port Status validation from PAC ===");
		try {
			LoginPageSteps.appLogin_PAC();
			PACSearchPageSteps.searchPhoneNo(sub);

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			String expectedStatus = testDataJson.getString("PORT_STATUS");
			String expectedSourceBrand = testDataJson.getString("SOURCE_BRAND");
			String expectedTargetBrand = testDataJson.getString("TARGET_BRAND");
			String expectedCurrentBrand = testDataJson.getString("CURRENT_BRAND");

			handleMultipleRecordsDisplayed();

			if (PACDashboardPage.isPortSummaryTableDisplayed()) {
				String actualPortStatus = PACDashboardPage.getPortStatus();
				Validate.assertEquals(actualPortStatus, expectedStatus, "Status Mismatch", false);

				String actualBrandName = PACDashboardPage.getBrandNameFromPAC(expectedCurrentBrand).getText()
						.toString();
				Validate.assertEquals(actualBrandName, expectedCurrentBrand, "Brand Mismatch", false);

			} else {

				Validate.assertEquals(PACDashboardPage.getBanText(), ban, "Ban Mismatch", false);
				Validate.assertEquals(PACDashboardPage.getStatusText(), expectedStatus, "Subscriber Status Mismatch",
						true);
				Validate.assertEquals(PACDashboardPage.getSourceBrandText(), expectedSourceBrand,
						"Source Brand Mismatch", true);
				Validate.assertEquals(PACDashboardPage.getTargetBrandText(), expectedTargetBrand,
						"Target Brand Mismatch", true);
			}

			Validate.takeStepFullScreenShot("Port details for Interbrand ", Status.PASS);
			Reporting.logReporter(Status.PASS,
					"=== Port Validation from PAC--> Port Validation for interbrand is as expected===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify Port Status from PAC " + e);
			Validate.assertTrue(false, "Unable to verify Port Details from PAC");
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify Port Status from
	 * PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifyPortStatus(JSONObject testDataJson, String sub, String condition) {
		Reporting.logReporter(Status.INFO,
				"STEP === Port Status validation --> Verify Port Status validation from PAC ===");
		try {
			LoginPageSteps.appLogin_PAC();
			PACSearchPageSteps.searchPhoneNo(sub);
			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			handleMultipleRecordsDisplayed();

			String expectedPortStatus = "";

			if (condition.equalsIgnoreCase("preValidation"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_BEFORE");
			else if (condition.equalsIgnoreCase("postValidation"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_AFTER");
			else
				expectedPortStatus = testDataJson.getString("PORT_STATUS");

			String actualPortStatus = PACDashboardPage.getPortStatus();

			Validate.assertEquals(actualPortStatus, expectedPortStatus, "Port status is not as expected", false);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify Port Status from PAC " + e);
			Validate.assertTrue(false, "Unable to verify Port Details from PAC");
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify Port Summary and
	 * Port Status
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void VerifyPortSummaryAndPortStatus(JSONObject testDataJson, String sub, String condition) {

		try {
			verifyPortSummaryEventListfromPAC(testDataJson, sub, condition);

			String expectedPortStatus = "";

			if (condition.equalsIgnoreCase("preValidation"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_BEFORE");
			else if (condition.equalsIgnoreCase("postValidation"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_AFTER");
			else if (condition.equalsIgnoreCase("intermediate1"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_1");
			else if (condition.equalsIgnoreCase("intermediate2"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_2");
			else if (condition.equalsIgnoreCase("intermediate3"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_3");
			else if (condition.equalsIgnoreCase("intermediate4"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_4");
			else if (condition.equalsIgnoreCase("intermediate5"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_5");
			else
				expectedPortStatus = testDataJson.getString("PORT_STATUS");

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			String actualPortStatus = PACDashboardPage.getPortStatus();

			long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
			long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (600 * 1000);

			while (startTime < endTime) {
				startTime = GenericUtils.getCurrentSystemDateTimeInMillis();

				if (actualPortStatus.equalsIgnoreCase(expectedPortStatus)) {
					break;
				}

				BaseSteps.Waits.waitGeneric(5000);
				BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.refreshBtn);
				BaseSteps.Clicks.clickElement(PACDashboardPage.refreshBtn);

				actualPortStatus = PACDashboardPage.getPortStatus();
			}

			Validate.assertEquals(actualPortStatus, expectedPortStatus, "Port status is not as expected", false);
			Reporting.logReporter(Status.PASS, "Actual Port Status is: " + actualPortStatus
					+ " and Expected Port Status is: " + expectedPortStatus);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify Port Summary and Status from PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Port status is not as expected");
		}

	}

	public static void doSkipValidationsFromPAC(JSONObject testDataJson, String sub) {
		LoginPageSteps.appLogin_PAC();
		PACSearchPageSteps.searchPhoneNo(sub);

		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		handleMultipleRecordsDisplayed();

		Reporting.logReporter(Status.INFO, "STEP === SKIP validation --> Skip validation from PAC ===");
		try {

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.skipValidationBtn);
			Validate.takeStepScreenShot("Before performing Skip Validations");
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.skipValidationBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.skipValidationBtn);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("SKIP_VALIDATION_CONFIRM_MSG");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.skipValidationMsgLabel);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.skipValidationMsgLabel);
			String actualText = PACDashboardPage.getSkipValidationMsgLabelText();

			Validate.assertEquals(actualText, expectedtext, "Skip Validation Failed", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to perform Skip Validation from PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to perform Skip Validation from PAC");
		}

	}

	/**
	 * Method Description: The purpose of this method is to perform Changed Brand
	 * Verification
	 * 
	 * @param testDataJson
	 */
	public static void changedBrandVerification(JSONObject testDataJson) {

		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		BaseSteps.Clicks.clickElement(PACDashboardPage.refreshBtn);
		BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.ospName);

		Validate.assertEquals(PACDashboardPage.getBrandName(), testDataJson.get("EXPECTED_BRAND_NAME"),
				"Brand Name mismatch", false);
		Validate.assertEquals(PACDashboardPage.getOSPName(), testDataJson.get("EXPECTED_OSP_NAME"), "OSP Name mismatch",
				true);
		Validate.assertEquals(PACDashboardPage.getNSPName(), testDataJson.get("EXPECTED_NSP_NAME"), "NSP Name mismatch",
				true);
		Validate.assertEquals(PACDashboardPage.getNLSPName(), testDataJson.get("EXPECTED_NLSP_NAME"),
				"NLSP Name mismatch", true);

	}

	/**
	 * Method Description: The purpose of this method is to return PAC Internal
	 * request Number
	 * 
	 * @return
	 */
	public static String getPACInternalRequestNumber() {
		return PACRequestNumber;
	}

	public static String getPACRequestNumber() {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		PACRequestNumber = PACDashboardPage.getPACInternalReqNumber();
		System.out.println(PACRequestNumber);
		return PACRequestNumber;

	}

	/**
	 * Method Description: The purpose of this method is to modify request from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void modifyRequestFromPAC(JSONObject testDataJson, String sub, String ban, String ddt) {
		Reporting.logReporter(Status.INFO,
				"STEP === Modify Details From PAC --> Modify ACCOUNT Number Details From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.modifyBtn);

			Validate.takeStepScreenShot("Modify button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyAccountNum);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.modifyAccountNum);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyAccountNum, ban);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyConfirmAccountNum, ban);

			Validate.takeStepScreenShot("Edited Account Number");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

			Validate.takeStepScreenShot("Modified DDDT");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.streetNum);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.streetNum);
			BaseSteps.SendKeys.sendKey(PACDashboardPage.streetNum, testDataJson.getString("STREET_NUMBER"));

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("MODIFY_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertContains(actualText, expectedtext, "Modify Request Submission Failed", false);
			Validate.takeStepFullScreenShot("Modify Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Modify request from PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify request from PAC");
		}
	}

	/**
	 * Method Description: The purpose of this method is to cancel request from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void cancelRequestFromPAC(JSONObject testDataJson) {
		Reporting.logReporter(Status.INFO, "STEP === Request Cancellation --> CANCEL request From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.cancelBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.cancelBtn);

			Validate.takeStepScreenShot("Cancel button clicked");

			String reasonCode = testDataJson.getString("REASON_CATEGORY_CANCEL_PAGE");
			String remarks = testDataJson.getString("REMARKS_CANCEL_PAGE");
			String noteCateogory = testDataJson.getString("NOTE_CATEGORY_CANCEL_PAGE");
			String noteType = testDataJson.getString("NOTE_TYPE_CANCEL_PAGE");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.reasonCodeCancelPage);
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.reasonCodeCancelPage, reasonCode);
			BaseSteps.SendKeys.sendKey(PACDashboardPage.remarksCancelPage, remarks);
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.noteCategoryCancelPage, noteCateogory);
			BaseSteps.Waits.waitForElementToBeClickable(PACDashboardPage.noteTypeCancelPage);
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.noteTypeCancelPage, noteType);

			Validate.takeStepScreenShot("Cancellation Details");

			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("CANCEL_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertContains(actualText.toUpperCase(), expectedtext.toUpperCase(), false);
			Validate.takeStepFullScreenShot("Cancel Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to cancel request from PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to cancel request from PAC");
		}
	}

	/**
	 * Method Description: The purpose of this method is to cancel request from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void cloneRequestWithANHIndicatorAsYFromPAC(JSONObject testDataJson, String ban) {
		Reporting.logReporter(Status.INFO, "STEP === Clone Request with AnH as Y --> Clone Request from PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.cloneBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.cloneBtn);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			Validate.takeStepScreenShot("After Clone button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.AnHIndicator);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.AnHIndicator);

			String AnHIndicatorValue = testDataJson.getString("ANH_INDICATOR_VALUE");
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.AnHIndicator, AnHIndicatorValue);

			Validate.takeStepScreenShot("Modified AnH Value");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.modifyAccountNum);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyAccountNum, ban);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyConfirmAccountNum, ban);

			try {
				BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyFirstName,
						testDataJson.getString("FIRST_NAME"));
				BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyLastName,
						testDataJson.getString("LAST_NAME"));

				Validate.takeStepScreenShot("Modified First and Last Name");
			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Exception in Updating First Name and Last Name");
			}

			Validate.takeStepScreenShot("Account Number Edited");

			try {
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.addConfirmationChkBox);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.addConfirmationChkBox,
						testDataJson.getString("ADDRESS_CONFIRMATION_OPTION"));

			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Address Confirmation checkbox not present.");
			}

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			/*
			 * Write assertion for the clone request confirmation message
			 * 
			 */
			String expectedtext = testDataJson.getString("CLONE_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertContains(actualText.toUpperCase(), expectedtext.toUpperCase(), false);

			Validate.takeStepFullScreenShot("Clone Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to clone Request With ANH Indicator As Y From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to clone Request With ANH Indicator As Y From PAC");
		}
	}

	/**
	 * 
	 * @param testDataJson
	 * @param ban
	 */
	public static void cloneRequestFromPAC(JSONObject testDataJson, String ban) {
		Reporting.logReporter(Status.INFO, "STEP === Clone Request --> CLONE request From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.cloneBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.cloneBtn);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			Validate.takeStepScreenShot("Clone button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.AnHIndicator);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.AnHIndicator);

			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyAccountNum, ban);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyConfirmAccountNum, ban);

			Validate.takeStepScreenShot("Edited Account Number");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			/*
			 * Write assertion for the clone request confirmation message
			 * 
			 */
			String expectedtext = testDataJson.getString("CLONE_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertContains(actualText.toUpperCase(), expectedtext.toUpperCase(), false);

			Validate.takeStepFullScreenShot("Cancel Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to clone Request From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to clone Request From PAC");
		}
	}

	/**
	 * 
	 * @param testDataJson
	 * @param ban
	 */
	public static void cloneRequestWithMissingInputsFromPAC(JSONObject testDataJson, String ban) {
		Reporting.logReporter(Status.INFO, "STEP === Clone Request --> CLONE request From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.cloneBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.cloneBtn);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			Validate.takeStepScreenShot("Clone button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.AnHIndicator);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.AnHIndicator);

			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyAccountNum, ban);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyConfirmAccountNum, ban);

			Validate.takeStepScreenShot("Edited Account Number");

			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyFirstName,
					testDataJson.getString("FIRST_NAME"));
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyLastName,
					testDataJson.getString("LAST_NAME"));

			Validate.takeStepScreenShot("Modified First and Last Name");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			/*
			 * Write assertion for the clone request confirmation message
			 * 
			 */
			String expectedtext = testDataJson.getString("CLONE_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertContains(actualText.toUpperCase(), expectedtext.toUpperCase(), false);

			Validate.takeStepFullScreenShot("Cancel Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to clone Request From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to clone Request From PAC");
		}
	}

	/**
	 * 
	 * @param testDataJson
	 * @param ban
	 */
	public static void cloneRequestFromPACWithoutModifyingAnything(JSONObject testDataJson, String ban) {
		Reporting.logReporter(Status.INFO, "STEP === Clone Request --> CLONE request From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.cloneBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.cloneBtn);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			Validate.takeStepScreenShot("Clone button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.streetNumber);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.streetNumber);
			BaseSteps.SendKeys.sendKey(PACDashboardPage.streetNumber, testDataJson.getString("STREET_NUMBER"));

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			/*
			 * Write assertion for the clone request confirmation message
			 * 
			 */
			String expectedtext = testDataJson.getString("CLONE_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertContains(actualText.toUpperCase(), expectedtext.toUpperCase(), false);
			Validate.takeStepFullScreenShot("Clone Request is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to clone Request From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to clone Request From PAC");
		}
	}

	/**
	 * Method Description: The purpose of this method is to modify IMEI from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void modifyIMEIRequestFromPAC(JSONObject testDataJson, String imei, String ddt) {
		Reporting.logReporter(Status.INFO,
				"STEP === Modify Details From PAC --> Modify IMEI Number Details From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.modifyBtn);

			Validate.takeStepScreenShot("Modify button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, "");
			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

			Validate.takeStepScreenShot("Modified DDT");

			try {
				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyFirstName);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.modifyFirstName);
				if (PACDashboardPage.modifyFirstName.getText().isEmpty()
						|| PACDashboardPage.modifyLastName.getText().isEmpty()) {
					BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyFirstName,
							testDataJson.getString("FIRST_NAME"));
					BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyLastName,
							testDataJson.getString("LAST_NAME"));
				}
			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Name fields are not empty");
			}

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyIMEINum);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.modifyIMEINum);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyIMEINum, imei);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.confirmIMEINum, imei);

			Validate.takeStepScreenShot("Modified IMEI details");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			if (PACDashboardPage.isMinDDDTErrorMsgDisplayed()) {

				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);
				ddt = GenericUtils.getDDDTWithDelayInPACFormatForJenkinsNodes(40);
				// String ddt = GenericUtils.updateDDDTFromPAC_PST(30);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
				BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

				Validate.takeStepScreenShot("Modified DDDT");

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD,
						activeConfirmation);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
				BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			}

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("MODIFY_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertEquals(actualText, expectedtext, "Modify Request Submission Failed", false);
			Validate.takeStepFullScreenShot("Modify Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Modify IMEI Number Details From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify IMEI Number Details From PAC");
		}
	}

	/**
	 * Method Description: The purpose of this method is to modify IMEI from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void modifyAccountRequestFromPAC(JSONObject testDataJson, String sub, String ban, String ddt) {
		Reporting.logReporter(Status.INFO,
				"STEP === Modify Details From PAC --> Modify IMEI/Account Number Details From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.modifyBtn);

			Validate.takeStepScreenShot("Modify button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

			Validate.takeStepScreenShot("Modified DDDT");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyAccountNum);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.modifyAccountNum);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyAccountNum, ban);
			BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyConfirmAccountNum, ban);

			Validate.takeStepScreenShot("Account Number Modified");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.streetNum);
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.streetNum);
			BaseSteps.SendKeys.sendKey(PACDashboardPage.streetNum, testDataJson.getString("STREET_NUMBER"));

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			while (PACDashboardPage.isMinDDDTErrorMsgDisplayed()) {

				int i = 0;

				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);
				ddt = GenericUtils.getDDDTWithDelayInPACFormatForJenkinsNodes(30);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
				BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD,
						activeConfirmation);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
				BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

				i++;

				if (i == 5) {
					break;
				}

			}

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("MODIFY_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertContains(actualText, expectedtext, "Modify Request Submission Failed", false);
			Validate.takeStepFullScreenShot("Modify Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Modify DDDT Details From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify DDDT Details From PAC");
		}
	}

	/**
	 * Method Description: The purpose of this method is to modify DDDT from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void modifyDDDTFromPAC(JSONObject testDataJson, String ddt) {
		Reporting.logReporter(Status.INFO, "STEP === Modify DDT Details From PAC --> Modify DDDT Details From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.modifyBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.modifyBtn);

			Validate.takeStepScreenShot("Modify button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);

			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

			Validate.takeStepScreenShot("Modified DDDT");

			try {
				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyFirstName);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.modifyFirstName);
				if (PACDashboardPage.modifyFirstName.getText().isEmpty()
						|| PACDashboardPage.modifyLastName.getText().isEmpty()) {
					BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyFirstName,
							testDataJson.getString("FIRST_NAME"));
					BaseSteps.SendKeys.clearFieldAndSendKeys(PACDashboardPage.modifyLastName,
							testDataJson.getString("LAST_NAME"));
				}
			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Name fields are not empty");
			}

			try {
				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.streetNum);
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.streetNum);
				BaseSteps.SendKeys.sendKey(PACDashboardPage.streetNum, testDataJson.getString("STREET_NUMBER"));
			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Street Name field is not empty");
			}

			// Temp wait
			BaseSteps.Waits.waitGeneric(3000);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("MODIFY_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertEquals(actualText, expectedtext, "Modify Request Submission Failed", false);
			Validate.takeStepFullScreenShot("Modify Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Modify DDDT Details From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify DDDT Details From PAC");
		}
	}

	/**
	 * Method Description: The purpose of this method is to modify DDDT from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void modifyDDDTFromPAC_WLN(JSONObject testDataJson, String ddt) {
		Reporting.logReporter(Status.INFO, "STEP === Modify DDT Details From PAC --> Modify DDDT Details From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.modifyBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.modifyBtn);

			Validate.takeStepScreenShot("Modify button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

			Validate.takeStepScreenShot("Modified DDDT");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.addConfirmationChkBox);
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.addConfirmationChkBox,
					testDataJson.getString("EXISTING_SERVICE_ADDRESS_CONFIRMATION_VALUE"));

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			if (PACDashboardPage.getListofErrorMsgs().contains("Sunday")) {
				
				Validate.takeStepScreenShot("DDDT Falls on Sunday", Status.INFO);
				
				int updatedDDTMinutes = testDataJson.getInt("WLN_DDT_DELAY_IN_MINUTES");
				updatedDDTMinutes = updatedDDTMinutes + 1440 ;
				ddt = GenericUtils.getDDDTWithDelayInPACFormat(updatedDDTMinutes)+1;
				
				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
				BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

				Validate.takeStepScreenShot("Updated Modified DDDT");
				
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.addConfirmationChkBox);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.addConfirmationChkBox,
						testDataJson.getString("EXISTING_SERVICE_ADDRESS_CONFIRMATION_VALUE"));
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD,
						activeConfirmation);
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
				BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);
			}
			else if (PACDashboardPage.getListofErrorMsgs().contains("Saturday")) {
				
				Validate.takeStepScreenShot("DDDT Falls on Saturday", Status.INFO);
				
				int updatedDDTMinutes = testDataJson.getInt("WLN_DDT_DELAY_IN_MINUTES");
				updatedDDTMinutes = updatedDDTMinutes + 2880 ;
				ddt = GenericUtils.getDDDTWithDelayInPACFormat(updatedDDTMinutes)+1;
				
				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
				BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

				Validate.takeStepScreenShot("Updated Modified DDDT");
				
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.addConfirmationChkBox);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.addConfirmationChkBox,
						testDataJson.getString("EXISTING_SERVICE_ADDRESS_CONFIRMATION_VALUE"));
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD,
						activeConfirmation);
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
				BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);
			}

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("MODIFY_REQUEST_CONFIRM_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyConfirmationMsg);
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertEquals(actualText, expectedtext, "Modify Request Submission Failed", false);
			Validate.takeStepFullScreenShot("Modify Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Modify DDDT Details From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify DDDT Details From PAC");
		}
	}

	/**
	 * Method Description: The purpose of this method is to handle if Multiple
	 * Records present in PAC for any subscriber
	 * 
	 */
	public static void handleMultipleRecordsDisplayed() {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		if (PACDashboardPage.returnNumberOfRecordsPresent() > 0) {
			BaseSteps.Debugs.scrollToElement(PACDashboardPage.getLatestSUBEntry());
			BaseSteps.Clicks.clickElement(PACDashboardPage.getLatestSUBEntry());
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.portSummaryTable);
		}
	}

	/**
	 * Method Description: The purpose of this method is to verify SMS Details
	 * Captured Under Notes In PAC
	 * 
	 * @param testDataJson
	 * @param param
	 */
	public static void verifySMSDetailsCapturedUnderNotesInPAC(JSONObject testDataJson, String param) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		String expectedResponse = null;
		String responseVal = null;
		if (param.equalsIgnoreCase("YES")) {
			responseVal = testDataJson.getString("RESPONSE_YES");
			expectedResponse = testDataJson.getString("RESPONSE_YES_TEXT");
		} else if (param.equalsIgnoreCase("NO")) {
			responseVal = testDataJson.getString("RESPONSE_NO");
			expectedResponse = testDataJson.getString("RESPONSE_NO_TEXT");
		}

		String actualResponse = PACDashboardPage.getCustomerResponseFromNotes(responseVal);
		Validate.assertEquals(actualResponse, expectedResponse, "Mismatch in Response captured under Notes section",
				false);

	}

	/**
	 * Method Description: The purpose of this method is to check Modified DDT From
	 * PAC
	 * 
	 * @param expectedDDT
	 */
	public static void checkModifiedDDTFromPAC(String expectedDDT) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifiedDDTFromMiddlePane);
		BaseSteps.Debugs.scrollToElement(PACDashboardPage.modifiedDDTFromMiddlePane);
		String actualDDT = PACDashboardPage.getModifiedDDTFromMiddlePane();

		Validate.takeStepScreenShot("Modified DDT");

		GenericUtils.verifyModifiedDDTFromPAC(actualDDT, expectedDDT);
		Reporting.logReporter(Status.INFO, "expected ddt date changed from SMG: " + expectedDDT);
		Reporting.logReporter(Status.INFO, "actual ddt date from PAC: " + actualDDT);

	}

	/**
	 * Method Description: The purpose of this method is to verify Port Summary
	 * 
	 * @param testDataJson
	 * @param condition
	 */
	public static void verifyPortSummary(JSONObject testDataJson, String condition) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		String expectedPortSummaryEvents;

		if (condition.equalsIgnoreCase("preValidation")) {
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS_BEFORE");
		} else if (condition.equalsIgnoreCase("postValidation")) {
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS_AFTER");
		} else if (condition.equalsIgnoreCase("intermediate1")) {
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS_INTERMEDIATE_1");
		} else if (condition.equalsIgnoreCase("intermediate2")) {
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS_INTERMEDIATE_2");
		} else if (condition.equalsIgnoreCase("intermediate3")) {
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS_INTERMEDIATE_3");
		} else if (condition.equalsIgnoreCase("intermediate4")) {
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS_INTERMEDIATE_4");
		} else if (condition.equalsIgnoreCase("intermediate5")) {
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS_INTERMEDIATE_5");
		} else
			expectedPortSummaryEvents = testDataJson.getString("PORT_SUMMARY_EVENTS");

		ArrayList<String> expectedListOfEventsFromPortSummary = new ArrayList<>();
		expectedListOfEventsFromPortSummary.addAll(Arrays.asList(expectedPortSummaryEvents.split(",")));

		ArrayList<String> actualListOfEventsFromPortSummary = new ArrayList<String>();
		for (WebElement e : PACDashboardPage.portSummaryEvents) {
			actualListOfEventsFromPortSummary.add(e.getText());
		}

		long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
		long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (600 * 1000);

		while (startTime < endTime) {
			startTime = GenericUtils.getCurrentSystemDateTimeInMillis();

			if (actualListOfEventsFromPortSummary.containsAll(expectedListOfEventsFromPortSummary)) {
				break;
			}

			BaseSteps.Waits.waitGeneric(5000);
			actualListOfEventsFromPortSummary.clear();
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.refreshBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.refreshBtn);

			for (WebElement e : PACDashboardPage.portSummaryEvents) {
				actualListOfEventsFromPortSummary.add(e.getText());
			}
		}

		PACRequestNumber = PACDashboardPage.getPACInternalReqNumber();
		Reporting.logReporter(Status.INFO, "Internal Request Number From PAC" + PACRequestNumber);
		System.out.println(PACRequestNumber);
		// System.out.println(PACDashboardPage.getPortStatus());

		boolean stat = actualListOfEventsFromPortSummary.containsAll(expectedListOfEventsFromPortSummary);

		System.out.println("actualListOfEventsFromPortSummary Events: " + actualListOfEventsFromPortSummary.size());
		System.out.println("actualListOfEventsFromPortSummary Events: " + expectedListOfEventsFromPortSummary.size());

		Validate.assertTrue(stat,
				"Actual and Expected Port Summary Events mismatch. actual List: " + actualListOfEventsFromPortSummary
						+ "expected list: " + expectedListOfEventsFromPortSummary,
				false, "actual List: " + actualListOfEventsFromPortSummary.toString() + "expected list: "
						+ expectedListOfEventsFromPortSummary);
	}

	/**
	 * Method Description: This method is used to verify response code value from
	 * PAC
	 * 
	 * @param expectedCode
	 */
	public static void verifyResponseCodeFromPAC(String expectedCode) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		BaseSteps.Clicks.clickElement(PACDashboardPage.WPRRResReqEvent);
		BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.resReqCode);
		BaseSteps.Debugs.scrollToElement(PACDashboardPage.resReqCode);
		String actualCode = PACDashboardPage.getResReqCodeText();

		Validate.assertEquals(actualCode, expectedCode, "Mismatch in Response Code", false);

	}

	/**
	 * Method Description: This method is used to verify response code value from
	 * PAC- Event 'Resolution Required Response En'
	 * 
	 * @param expectedCode
	 */
	public static void verifyResponseCodeForResolutionRequiredFromPAC(String expectedCode) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		BaseSteps.Clicks.clickElement(PACDashboardPage.WPRRResolutionRequiredEvent);
		BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.resReqCode);
		BaseSteps.Debugs.scrollToElement(PACDashboardPage.resReqCode);
		String actualCode = PACDashboardPage.getResReqCodeText();

		Validate.assertEquals(actualCode, expectedCode, "Mismatch in Response Code", false);

	}

	//
	public static void performNPACActivate(JSONObject testDataJson) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.NPACActivate);
		BaseSteps.Clicks.clickElement(PACDashboardPage.NPACActivate);

		if (BaseSteps.Alerts.isAlertDisplayed()) {
			BaseSteps.Alerts.confirmationAlertAccept();
		}

		String expectedtext = testDataJson.getString("NPAC_ACTIVATE_CONFIRM_MSG");
		String actualText = PACDashboardPage.getModifyConfirmMessageText();

		Validate.assertContains(actualText.toUpperCase(), expectedtext.toUpperCase(), false);
	}

	public static void waitForSpecificPortStatus(String expectedPortStatus) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		String actualPortStatus = PACDashboardPage.getPortStatus();

		String currentTime = GenericUtils.getCurrentSystemDateTime();
		String expectedWaitTime = GenericUtils.getCurrentSystemDateTimeWithDelay(30);

		while (!expectedWaitTime.equalsIgnoreCase(currentTime)) {
			currentTime = GenericUtils.getCurrentSystemDateTime();

			if (actualPortStatus.equalsIgnoreCase(expectedPortStatus)) {
				break;
			}

			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.refreshBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.refreshBtn);

			actualPortStatus = PACDashboardPage.getPortStatus();
		}
	}

	/**
	 * Method Description: The purpose of this method is to modify DDDT from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void performSUP2FromPAC_PST(JSONObject testDataJson, int delay) {
		Reporting.logReporter(Status.INFO, "STEP === Modify DDT Details From PAC --> Modify DDDT Details From PAC ===");
		try {

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.modifyBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.modifyBtn);
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);

			String updated_ddt = GenericUtils.updateDDDTFromPAC_PST(delay);

			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, updated_ddt);

			// Temp wait
			BaseSteps.Waits.waitGeneric(3000);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			try {
				if (PACDashboardPage.isMinDDDTErrorMsgDisplayed()) {

					BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
					updated_ddt = GenericUtils.updateDDDTFromPAC_PST(delay);
					BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, updated_ddt);
					BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
					BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD,
							activeConfirmation);

					BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);
					Reporting.logReporter(Status.INFO, "DDDT update is succesful");

				}

			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Exception While Updating DDDT from PAC" + e);
			}

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("MODIFY_REQUEST_CONFIRM_MSG");
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertEquals(actualText, expectedtext, "Modify Request Submission Failed", false);
			Validate.takeStepFullScreenShot("Modify Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Modify DDDT Details From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify DDDT Details From PAC");
		}
	}

	/**
	 * 
	 * @param sub
	 */
	public static void enablePreportAuthorization(JSONObject testDataJson, String sub, int delay,
			String targetServiceProvider) {

		LoginPageSteps.appLogin_PAC();
		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		BaseSteps.Clicks.clickElement(PACDashboardPage.preportSkipLinkBtn);
		BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.addButtonPreport);

		Validate.takeStepScreenShot("Navigate to Preport Tab");

		// 4161263038
		BaseSteps.SendKeys.sendKey(PACDashboardPage.tnToPreport, sub);
		String currentTime = GenericUtils.dateTimeInESTWithDelayInMinutes(delay);
		BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.startDateTimePreport, currentTime);

		BaseSteps.SendKeys.sendKey(PACDashboardPage.remarksPreport, testDataJson.getString("PREPORT_REMARKS"));

		if (targetServiceProvider.equalsIgnoreCase("wireline")) {
			BaseSteps.Clicks.clickElement(PACDashboardPage.spWirelineRadioBtnPreport);
			Validate.takeStepScreenShot("Preport Request for Wireline");
		} else {
			BaseSteps.Clicks.clickElement(PACDashboardPage.spWirelessRadioBtnPreport);
			Validate.takeStepScreenShot("Preport Request for Wireless");
		}

		BaseSteps.Clicks.clickElement(PACDashboardPage.addButtonPreport);

		BaseSteps.Waits.waitGeneric(500);

		if (BaseSteps.Alerts.isAlertDisplayed()) {
			BaseSteps.Alerts.confirmationAlertAccept();
			BaseSteps.Waits.waitGeneric(500);
		}

		BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.successMessagePreport);
		String actualText = PACDashboardPage.getSuccessMsgTextPreport();
		// String expectedText = "Preport Bypass";

		String expectedText = testDataJson.getString("PREPORT_CONFIRMATION_MSG");
		// "Preport Bypass is added succesfully.";

		Validate.assertEquals(actualText, expectedText, false);
		BaseSteps.Waits.waitGeneric(60000);

	}

	public static void performSearchForPACRecordsDisplayed(String sub, int maxTimeInSeconds) {

		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		PACSearchPage PACSearchPage = new PACSearchPage();

		long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
		long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (maxTimeInSeconds * 1000);

		while (startTime < endTime) {
			startTime = GenericUtils.getCurrentSystemDateTimeInMillis();

			try {
				if (PACDashboardPage.portStatus.isDisplayed()) {
					break;
				}
			} catch (Exception e) {
				// PACSearchPageSteps.searchPhoneNo(sub);

				BaseSteps.Waits.waitForElementVisibility(PACSearchPage.ctn, 10);
				BaseSteps.SendKeys.clearFieldAndSendKeysUsingJS(PACSearchPage.ctn, sub);
				BaseSteps.Clicks.clickElement(PACSearchPage.submit);
			}

		}

		Validate.takeStepScreenShot("Entered TN for Search");

	}

	/**
	 * 
	 * @param testDataJson
	 * @param condition
	 */
	public static void verifyPortStatusFromPAC(JSONObject testDataJson, String condition) {
		String expectedPortStatus = "";

		if (condition.equalsIgnoreCase("preValidation"))
			expectedPortStatus = testDataJson.getString("PORT_STATUS_BEFORE");
		else if (condition.equalsIgnoreCase("postValidation"))
			expectedPortStatus = testDataJson.getString("PORT_STATUS_AFTER");
		else if (condition.equalsIgnoreCase("intermediate1"))
			expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_1");
		else if (condition.equalsIgnoreCase("intermediate2"))
			expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_2");
		else if (condition.equalsIgnoreCase("intermediate3"))
			expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_3");
		else if (condition.equalsIgnoreCase("intermediate4"))
			expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_4");
		else if (condition.equalsIgnoreCase("intermediate5"))
			expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_5");
		else
			expectedPortStatus = testDataJson.getString("PORT_STATUS");

		PACDashboardPage PACDashboardPage = new PACDashboardPage();
		String actualPortStatus = PACDashboardPage.getPortStatus();

		long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
		long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (600 * 1000);

		while (startTime < endTime) {
			startTime = GenericUtils.getCurrentSystemDateTimeInMillis();

			if (actualPortStatus.equalsIgnoreCase(expectedPortStatus)) {
				break;
			}

			BaseSteps.Waits.waitGeneric(5000);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.refreshBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.refreshBtn);

			actualPortStatus = PACDashboardPage.getPortStatus();
		}

		Validate.assertEquals(actualPortStatus, expectedPortStatus, "Port status is not as expected", false);
		Reporting.logReporter(Status.PASS,
				"Actual Port Status is: " + actualPortStatus + " and Expected Port Status is: " + expectedPortStatus);
	}

	/**
	 * Method Description: The purpose of this method is to verify SMS Details
	 * Captured Under Notes In PAC
	 * 
	 * @param testDataJson
	 * @param param
	 */
	public static void verifySMSDetailsCapturedUnderNotesInPAC_NEW(String expectedResponse) {
		PACDashboardPage PACDashboardPage = new PACDashboardPage();

		String actualResponse = PACDashboardPage.getCustomerResponseFromNotes(expectedResponse);
		expectedResponse = "Customer Response : " + expectedResponse;
		Validate.assertEquals(actualResponse, expectedResponse, "Mismatch in Response captured under Notes section",
				false);

	}

	/**
	 * Method Description: The purpose of this method is to verify Another Port
	 * Summary and Port Status
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void VerifyAnotherPortStatusAndSummary(JSONObject testDataJson, String sub, String condition) {

		try {
			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Clicks.clickElement(PACDashboardPage.searchPageLinkBtn);
			PACSearchPageSteps.searchPhoneNo(sub);
			handleMultipleRecordsDisplayed();

			verifyPortSummary(testDataJson, condition);

			String expectedPortStatus = "";

			if (condition.equalsIgnoreCase("preValidation"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_BEFORE");
			else if (condition.equalsIgnoreCase("postValidation"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_AFTER");
			else if (condition.equalsIgnoreCase("intermediate1"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_1");
			else if (condition.equalsIgnoreCase("intermediate2"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_2");
			else if (condition.equalsIgnoreCase("intermediate3"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_3");
			else if (condition.equalsIgnoreCase("intermediate4"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_4");
			else if (condition.equalsIgnoreCase("intermediate5"))
				expectedPortStatus = testDataJson.getString("PORT_STATUS_INTERMEDIATE_5");
			else
				expectedPortStatus = testDataJson.getString("PORT_STATUS");

			String actualPortStatus = PACDashboardPage.getPortStatus();

			long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
			long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (600 * 1000);

			while (startTime < endTime) {
				startTime = GenericUtils.getCurrentSystemDateTimeInMillis();

				if (actualPortStatus.equalsIgnoreCase(expectedPortStatus)) {
					break;
				}

				BaseSteps.Waits.waitGeneric(5000);
				BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.refreshBtn);
				BaseSteps.Clicks.clickElement(PACDashboardPage.refreshBtn);

				actualPortStatus = PACDashboardPage.getPortStatus();
			}

			Validate.assertEquals(actualPortStatus, expectedPortStatus, "Port status is not as expected", false);
			Reporting.logReporter(Status.PASS, "Actual Port Status is: " + actualPortStatus
					+ " and Expected Port Status is: " + expectedPortStatus);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify Port Summary and Status from PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Port status is not as expected");
		}

	}

	/**
	 * Method Description: The purpose of this method is to modify DDDT from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void modifyDDDTFromPACExcludingWeekends_WLN(JSONObject testDataJson, int dddtDelayMinutes) {
		Reporting.logReporter(Status.INFO, "STEP === Modify DDT Details From PAC --> Modify DDDT Details From PAC ===");

		try {

			int delay = dddtDelayMinutes;
			String ddt = GenericUtils.getDDDTWithDelayInPACFormat(dddtDelayMinutes);

			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PACDashboardPage.modifyBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.modifyBtn);

			Validate.takeStepScreenShot("Modify button clicked");

			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
			BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

			Validate.takeStepScreenShot("Modified DDDT");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.addConfirmationChkBox);
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.addConfirmationChkBox,
					testDataJson.getString("EXISTING_SERVICE_ADDRESS_CONFIRMATION_VALUE"));

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
			String activeConfirmation = testDataJson.getString("LINE_IS_ACTIVE_CONFIRMATION_VALUE");
			BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD, activeConfirmation);

			Validate.takeStepScreenShot("Confirmation");

			BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
			BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);

			if (PACDashboardPage.getListofErrorMsgs().contains("Sunday")) {
				
				Validate.takeStepScreenShot("DDDT Falls on Sunday", Status.INFO);

				// Adding minutes to Monday from Sunday
				int del = delay + 1440;
				ddt = GenericUtils.getDDDTWithDelayInPACFormat(del);
				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
				BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

				Validate.takeStepScreenShot("Modified DDDT");

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.addConfirmationChkBox);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.addConfirmationChkBox,
						testDataJson.getString("EXISTING_SERVICE_ADDRESS_CONFIRMATION_VALUE"));
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD,
						activeConfirmation);
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
				BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);
			} else if (PACDashboardPage.getListofErrorMsgs().contains("Saturday")) {
				
				Validate.takeStepScreenShot("DDDT Falls on Saturday", Status.INFO);

				// Adding minutes to Monday from Saturday
				int del = delay + 2880;
				ddt = GenericUtils.getDDDTWithDelayInPACFormat(del);
				BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.dddtTrigger);

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.dateTimeSent);
				BaseSteps.SendKeys.sendKeyUsingJS(PACDashboardPage.modifyDDT, ddt);

				Validate.takeStepScreenShot("Modified DDDT");

				BaseSteps.Debugs.scrollToElement(PACDashboardPage.addConfirmationChkBox);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.addConfirmationChkBox,
						testDataJson.getString("EXISTING_SERVICE_ADDRESS_CONFIRMATION_VALUE"));
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.lineIsActiveConfirmationDD);
				BaseSteps.Dropdowns.selectByVisibleText(PACDashboardPage.lineIsActiveConfirmationDD,
						activeConfirmation);
				BaseSteps.Debugs.scrollToElement(PACDashboardPage.submitBtnCancelPage);
				BaseSteps.Clicks.clickElement(PACDashboardPage.submitBtnCancelPage);
			}

			if (BaseSteps.Alerts.isAlertDisplayed()) {
				BaseSteps.Alerts.confirmationAlertAccept();
			}

			String expectedtext = testDataJson.getString("MODIFY_REQUEST_CONFIRM_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(PACDashboardPage.modifyConfirmationMsg);
			String actualText = PACDashboardPage.getModifyConfirmMessageText();

			Validate.assertEquals(actualText, expectedtext, "Modify Request Submission Failed", false);
			Validate.takeStepFullScreenShot("Modify Request Submission is successful", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to Modify DDDT Details From PAC: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to Modify DDDT Details From PAC");
		}
	}

}
