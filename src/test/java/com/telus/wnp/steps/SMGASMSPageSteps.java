package com.telus.wnp.steps;

import com.test.utils.Status;
import com.telus.wnp.pages.LSRPage;
import com.telus.wnp.pages.SMGASMSPage;
import com.telus.wnp.pages.SMGPage;
import com.telus.wnp.utils.GenericUtils;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for SMGASMS page Steps > AUTHOR: x241410
 ****************************************************************************
 */

public class SMGASMSPageSteps extends BaseSteps {

	/**
	 * Method Description: The purpose of this method is to verify if SMG home page
	 * is displayed
	 */
	public static void verifySMGASMSHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> SMGASMS homepage is displayed after successful login ");
		SMGASMSPage SMGASMSPage = new SMGASMSPage();
		boolean actual = SMGASMSPage.isHomePageDisplayed();

		Validate.assertEquals(actual, true, "SMGASMS Homepage is not displayed!", false);
	}

	/**
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void createNewSPRequestFromSMGASMS(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO,
				"STEP === NPAC Activate --> Performing NPAC activation for '" + sub + "' ===");

		SMGASMSPage SMGASMSPage = new SMGASMSPage();

		try {
			navigateToSMGSOASubscriptionDetailsPage();
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGASMSPage.subscriptionDetailsClearBtn);

			BaseSteps.Waits.waitForElementVisibility(SMGASMSPage.subscriptionDetailsFromTN);
			BaseSteps.SendKeys.sendKey(SMGASMSPage.subscriptionDetailsFromTN, sub);

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionQueryBtn);
			BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionQueryBtn);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGASMSPage.subscriptionDetailsSelectAction);
			BaseSteps.Dropdowns.selectByVisibleText(SMGASMSPage.subscriptionDetailsSelectAction,
					testDataJson.getString("SELECT_ACTION_NEWSPCREATE_SMGASMS"));

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsSelectNewSPID);
			BaseSteps.Debugs.scrollToElement(SMGASMSPage.subscriptionDetailsSelectNewSPID);
			BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionDetailsSelectNewSPID);
			BaseSteps.SendKeys.sendKey(SMGASMSPage.subscriptionDetailsSelectNewSPID,
					testDataJson.getString("NEW_SPID_SMGASMS"));

			SMGASMSPage.clickSpecificSPIDFromDropDown(testDataJson.getString("NEW_SPID_SMGASMS"));
			BaseSteps.Waits.waitGeneric(1000);

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsSelectOldSPID);
			BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionDetailsSelectOldSPID);
			BaseSteps.SendKeys.sendKey(SMGASMSPage.subscriptionDetailsSelectOldSPID,
					testDataJson.getString("OLD_SPID_SMGASMS"));

			SMGASMSPage.clickSpecificSPIDFromDropDown(testDataJson.getString("OLD_SPID_SMGASMS"));
			BaseSteps.Waits.waitGeneric(1000);

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsLNPType);
			BaseSteps.Dropdowns.selectByVisibleText(SMGASMSPage.subscriptionDetailsLNPType,
					testDataJson.getString("LNP_TYPE_SMGASMS"));

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsNewSPDueDate);
			BaseSteps.Debugs.scrollToElement(SMGASMSPage.subscriptionDetailsNewSPDueDate);
			BaseSteps.SendKeys.sendKey(SMGASMSPage.subscriptionDetailsNewSPDueDate,
					GenericUtils.getSystemDateInMMDDYYYY());

			BaseSteps.Waits.waitGeneric(1000);
			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsLRN);
			BaseSteps.SendKeys.sendKey(SMGASMSPage.subscriptionDetailsLRN, testDataJson.getString("LRN_SMGASMS"));

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsSaveBtn);
			BaseSteps.Debugs.scrollToElement(SMGASMSPage.subscriptionDetailsSaveBtn);
			BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionDetailsSaveBtn);

			BaseSteps.Waits.waitGeneric(1000);

			// "YOUR REQUEST HAS BEEN SUBMITTED."
			String expectedStatusMsg = testDataJson.getString("REQUEST_SUBMIT_CONFIRMATION_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGASMSPage.subscriptionDetailsInfoMsg);
			BaseSteps.Debugs.scrollToElement(SMGASMSPage.subscriptionDetailsInfoMsg);
			String actualStatusMsg = SMGASMSPage.getRequestStatusMsg();

			Validate.assertEquals(actualStatusMsg, expectedStatusMsg, "Unable to Create New SP Create Request", false);
			Validate.takeStepFullScreenShot("Create SP request details", Status.INFO);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Create New SP Create Request Failed: " + e);
			Validate.assertTrue(false, "Exception Occured: Create New SP Create Request Failed");
		}

	}

	
	
	public static void queryForNPACPendingStatus(String sub, String expectedState) {

		SMGASMSPage SMGASMSPage = new SMGASMSPage();

		try {
			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsClearBtn);
			BaseSteps.Debugs.scrollToElement(SMGASMSPage.subscriptionDetailsClearBtn);
			BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionDetailsClearBtn);

			BaseSteps.Waits.waitForElementVisibility(SMGASMSPage.subscriptionDetailsFromTN);
			BaseSteps.SendKeys.sendKey(SMGASMSPage.subscriptionDetailsFromTN, sub);

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionQueryBtn);
			BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionQueryBtn);

			try {
				BaseSteps.Waits.waitForElementVisibility(SMGASMSPage.subscriptionDetailsRecordTypeState);
			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Record Type Section Not Expanded");
				BaseSteps.Clicks.clickElement(SMGASMSPage.recordTypeCollapsedSection);
				BaseSteps.Waits.waitForElementVisibility(SMGASMSPage.subscriptionDetailsRecordTypeState);

			}
			String actualState = SMGASMSPage.getRecordTypeState();

			/*
			 * String currentTime = GenericUtils.getCurrentSystemDateTime(); String
			 * expectedWaitTime = GenericUtils.getCurrentSystemDateTimeWithDelay(30);
			 * 
			 * while (!expectedWaitTime.equalsIgnoreCase(currentTime)) { currentTime =
			 * GenericUtils.getCurrentSystemDateTime();
			 */

			long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
			long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (30 * 1000);

			while (startTime < endTime) {
				startTime = GenericUtils.getCurrentSystemDateTimeInMillis();

				if (actualState.equalsIgnoreCase(expectedState)) {
					break;
				}
				BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGASMSPage.subscriptionQueryBtn);
				BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionQueryBtn);
				BaseSteps.Waits.waitForElementVisibility(SMGASMSPage.subscriptionDetailsRecordTypeState);
				actualState = SMGASMSPage.getRecordTypeState();

			}

			Validate.assertEquals(actualState.toUpperCase(), expectedState.toUpperCase(), false);
			Reporting.logReporter(Status.INFO, "Required State displayed for sub: " + sub + " is: " + actualState);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Required State from SMGASMS is not displayed: " + e);
			Validate.assertTrue(false, "Exception Occured: Required State from SMGASMS is not displayed");
		}

	}

	/**
	 * 
	 * @param testDataJson
	 */
	public static void performNPACActivateSteps(JSONObject testDataJson) {

		SMGASMSPage SMGASMSPage = new SMGASMSPage();

		try {
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGASMSPage.subscriptionDetailsSelectAction);
			BaseSteps.Debugs.scrollToElement(SMGASMSPage.subscriptionDetailsSelectAction);
			BaseSteps.Dropdowns.selectByVisibleText(SMGASMSPage.subscriptionDetailsSelectAction,
					testDataJson.getString("SELECT_ACTION_ACTIVATE_SMGASMS"));
			
			Validate.takeStepScreenShot("Selected NPAC Activate option", Status.INFO);

			BaseSteps.Waits.waitForElementToBeClickable(SMGASMSPage.subscriptionDetailsSaveBtn);
			BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionDetailsSaveBtn);

			BaseSteps.Waits.waitGeneric(1000);
			String expectedStatusMsg = testDataJson.getString("REQUEST_SUBMIT_CONFIRMATION_MSG");
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGASMSPage.subscriptionDetailsInfoMsg);
			BaseSteps.Debugs.scrollToElement(SMGASMSPage.subscriptionDetailsInfoMsg);
			String actualStatusMsg = SMGASMSPage.getRequestStatusMsg();

			Validate.assertEquals(actualStatusMsg, expectedStatusMsg, "NPAC Activate", false);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to perform NPAC Activate Steps: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to perform NPAC Activate Steps");

		}
	}

	/**
	 * Method Description: Navigate to SMG SOA Selection Page
	 */
	public static void navigateToSMGSOASubscriptionDetailsPage() {

		try {
			LoginPageSteps.appLogin_SMGASMS();
			verifySMGASMSHomePageIsDisplayed();

			clickSOASubscription();
			BaseSteps.Windows.switchToNewWindow();
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to navigate to SMG SAO Subscription page");
		}

	}
	
	/**
	 * Method Description: Navigate to SMG SOA Selection Page
	 */
	public static void navigateToSMGSOASubscriptionDetailsPage_New() {

		try {
			LoginPageSteps.appLogin_SMGASMS();
			verifySMGASMSHomePageIsDisplayed();

			clickSOASubscription();
			BaseSteps.Windows.switchToNewWindow();
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to navigate to SMG SAO Subscription page");
		}

	}

	/**
	 * Method Description: To click SOASelection Option
	 */
	public static void clickSOASubscription() {
		SMGASMSPage SMGASMSPage = new SMGASMSPage();
		BaseSteps.Waits.waitForElementVisibility(SMGASMSPage.subscription_Administration, 20);
		BaseSteps.Clicks.clickElement(SMGASMSPage.subscription_Administration);
		BaseSteps.Waits.waitForElementVisibility(SMGASMSPage.subscriptionDetails, 10);
		BaseSteps.Clicks.clickElement(SMGASMSPage.subscriptionDetails);

	}

	public static void logOffFromTruNumberGateway() {

		try {
			SMGASMSPage SMGASMSPage = new SMGASMSPage();

			BaseSteps.Windows.switchToWindowThatContainsPartialUrl("SMGASMS/Login/dist/Login/mainmenu");
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGASMSPage.logOutBtnFromMainPage);

			BaseSteps.Clicks.clickElement(SMGASMSPage.logOutBtnFromMainPage);
			BaseSteps.Waits.waitGeneric(500);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGASMSPage.okBtnConfirmLogOutFromPopUp);
			BaseSteps.Clicks.clickElement(SMGASMSPage.okBtnConfirmLogOutFromPopUp);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to perform proper Logout from SMGASMS Application");
		}

	}
}
