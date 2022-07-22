package com.telus.wnp.steps;

import org.json.JSONObject;

import com.test.utils.Status;
import com.telus.wnp.pages.SmartDesktopDashboardPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for smart desktop dashboard page (Steps) > AUTHOR:
 * x241410
 ****************************************************************************
 */

public class SmartDesktopDashboardPageSteps extends BaseSteps {

	/**
	 * This method is used to do submit TN for change from the Subscriber Tab.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param ban
	 *            This is the subscriber ban.
	 * @return nothing.
	 */
	public static void submitTNForChangeFromSubscriberTab_SD(JSONObject testDataJson, String sub, String ban,
			String portedSub, String portedBan) {

		Reporting.logReporter(Status.INFO,
				"STEP === Change phone number --> Change phone number request from Smart Desktop for '" + portedSub
						+ "' ===");

		try {
			LoginPageSteps.appLogin_SD();
			SmartDesktopSearchPageSteps.verifySDHomePageIsDisplayed();
			SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);

			SmartDesktopDashboardPage smartDesktopDashboardPage = new SmartDesktopDashboardPage();

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeLink);

			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.enterNumInStep2d);
			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.enterNumInStep2d);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterNumInStep2d, portedSub);

			Validate.takeStepScreenShot("SD Page Details After Entering TN", Status.INFO);

			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.submitBtnFromStep2d);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitBtnFromStep2d);

			BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospAccountNum, portedBan);
			BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospAccountNumConfirm, portedBan);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.alternateContactNum,
					testDataJson.getString("ALTERNATE_CONTACT_NUMBER"));

			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.authorizedChkBox);

			if (smartDesktopDashboardPage.isAuthorizationNameBlank())
				BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.authorizationName, "TEST USER");

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterCommentsForOSP,
					testDataJson.getString("COMMENTS_FOR_CHANGE_REQUEST"));

			Validate.takeStepFullScreenShot("Port-in details", Status.INFO);
			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.changeReqFinalComments,
					testDataJson.getString("COMMENTS_FOR_CHANGE_REQUEST"));

			Validate.takeStepFullScreenShot("Details entered for Change Request", Status.INFO);

			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.changeReqFinalAcceptBtn);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

			// BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.tnReqSubmittedMsg);

			String portedSubDetails = smartDesktopDashboardPage.getPortedSubDetails();
			String formattedSub = "";

			if (!portedSub.contains("-")) {
				formattedSub = GenericUtils.getHyphenSeparatedTN(portedSub);
			} else {
				formattedSub = portedSub;
			}

			Validate.assertTrue(portedSubDetails.contains(formattedSub), "Change Request SUB Validation Failed", false,
					"Change Request Validation for " + portedSub + " Passed");
			Validate.assertTrue(portedSubDetails.contains("status:active"),
					"Change Request SUB STATUS Validation Failed", true,
					"Change Request Validation for " + portedSub + " Status Passed");
			
			/*Validate.assertTrue(portedSubDetails.contains(GenericUtils.getSystemDateInMMDDYYYY()),
					"Change Request Validation Failed", true, "Change Request Validation for Requested Date Passed");*/

			Validate.takeStepFullScreenShot("Change Request is successful", Status.PASS);
			Reporting.logReporter(Status.PASS, "=== Change Request From Smart Desktop is Successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to complete change# request from Smart Desktop: " + e);
			Validate.assertTrue(false, "Exception Occured:  Unable to submit change Request");
		}

	}

	/**
	 * This method is used to do submit TN for change from the Subscriber Tab.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param ban
	 *            This is the subscriber ban.
	 * @return nothing.
	 */
	public static void submitTNForChangeFromSubscriberTabIncorrectDetails_SD(JSONObject testDataJson, String sub,
			String ban, String portedSub, String param, String paramValue) {

		Reporting.logReporter(Status.INFO,
				"STEP === Change phone number --> Change phone number request from Smart Desktop for '" + portedSub
						+ "' ===");

		try {
			LoginPageSteps.appLogin_SD();
			SmartDesktopSearchPageSteps.verifySDHomePageIsDisplayed();
			SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);
			// SmartDesktopSearchPageSteps.searchPhoneNo(sub);

			SmartDesktopDashboardPage smartDesktopDashboardPage = new SmartDesktopDashboardPage();

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeLink);
			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.enterNumInStep2d);
			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.enterNumInStep2d);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterNumInStep2d, portedSub);

			Validate.takeStepFullScreenShot("SD Page Details After Entering TN", Status.INFO);

			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.submitBtnFromStep2d);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitBtnFromStep2d);

			if (param.equalsIgnoreCase("imei")) {
				BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospIMEINum, paramValue);
				BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospIMEINumConfirm, paramValue);
				Validate.takeStepScreenShot("Entered IMEI : " + paramValue, Status.INFO);
			} else {
				BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospAccountNum, paramValue);
				BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospAccountNumConfirm, paramValue);
				Validate.takeStepScreenShot("Entered Account Number : " + paramValue, Status.INFO);
			}

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.alternateContactNum,
					testDataJson.getString("ALTERNATE_CONTACT_NUMBER"));

			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.authorizedChkBox);

			if (smartDesktopDashboardPage.isAuthorizationNameBlank())
				BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.authorizationName, "TEST USER");

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterCommentsForOSP,
					testDataJson.getString("COMMENTS_FOR_CHANGE_REQUEST"));
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.changeReqFinalComments,
					testDataJson.getString("COMMENTS_FOR_CHANGE_REQUEST"));

			Validate.takeStepScreenShot("Details entered for Change Request");

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

			String portedSubDetails = smartDesktopDashboardPage.getPortedSubDetails();
			String formattedSub = "";

			if (!portedSub.contains("-")) {
				formattedSub = GenericUtils.getHyphenSeparatedTN(portedSub);
			} else {
				formattedSub = portedSub;
			}

			Validate.assertTrue(portedSubDetails.contains(formattedSub), "Change Request SUB Validation Failed", false,
					"Change Request Validation for " + portedSub + " Passed");
			Validate.assertTrue(portedSubDetails.contains("status:active"),
					"Change Request SUB STATUS Validation Failed", true,
					"Change Request Validation for " + portedSub + " Status Passed");

			Validate.takeStepFullScreenShot("Change Request is successful", Status.PASS);
			Reporting.logReporter(Status.PASS, "=== Change Request From Smart Desktop is Successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to complete change# request from Smart Desktop: " + e);
			Validate.assertTrue(false, "Exception Occured:  Unable to submit change Request");
		}

	}

	/**
	 * This method is used to do submit TN for change from the Subscriber Tab.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param ban
	 *            This is the subscriber ban.
	 * @return nothing.
	 */
	public static void submitTNForChangeFromSubscriberTabForWLN_SD(JSONObject testDataJson, String sub, String ban,
			String portedSub, String portedBan) {

		Reporting.logReporter(Status.INFO,
				"STEP === Change phone number --> Change phone number request from Smart Desktop for '" + portedSub
						+ "' ===");

		try {
			LoginPageSteps.appLogin_SD();
			SmartDesktopSearchPageSteps.verifySDHomePageIsDisplayed();
			SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);

			SmartDesktopDashboardPage smartDesktopDashboardPage = new SmartDesktopDashboardPage();

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeLink);
			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.enterNumInStep2d);
			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.enterNumInStep2d);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterNumInStep2d, portedSub);

			Validate.takeStepFullScreenShot("SD Page Details After Entering TN", Status.INFO);

			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.submitBtnFromStep2d);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitBtnFromStep2d);

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.WLN_FirstName, testDataJson.getString("FIRST_NAME"));
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.WLN_LastName, testDataJson.getString("LAST_NAME"));
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.WLN_StreetNumber,
					testDataJson.getString("STREET_NUMBER"));
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.WLN_StreetName, testDataJson.getString("STREET_NAME"));
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.WLN_City, testDataJson.getString("CITY"));
			BaseSteps.Dropdowns.selectByVisibleText(smartDesktopDashboardPage.WLN_ProvinceDrpDown,
					testDataJson.getString("PROVINCE"));
			BaseSteps.Waits.waitForElementToBeClickableLongWait(smartDesktopDashboardPage.WLN_PostalCode);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.WLN_PostalCode, testDataJson.getString("POSTAL_CODE"));
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.WLN_HasVerifiedServiceAddChkBox);

			BaseSteps.Dropdowns.selectByVisibleText(smartDesktopDashboardPage.internetTVOption,
					testDataJson.getString("INTERNET_TV_OPTION"));
			BaseSteps.Waits.waitForElementToBeClickableLongWait(smartDesktopDashboardPage.isMoving);
			BaseSteps.Dropdowns.selectByVisibleText(smartDesktopDashboardPage.isMoving,
					testDataJson.getString("IS_MOVING"));

			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.alternateContactNum);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.alternateContactNum,
					testDataJson.getString("ALTERNATE_CONTACT_NUMBER"));

			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.authorizedChkBox);

			if (smartDesktopDashboardPage.isAuthorizationNameBlank())
				BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.authorizationName, "TEST USER");

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterCommentsForOSP,
					testDataJson.getString("COMMENTS_FOR_CHANGE_REQUEST"));
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.changeReqFinalComments,
					testDataJson.getString("COMMENTS_FOR_CHANGE_REQUEST"));

			Validate.takeStepScreenShot("Details entered for Change Request");

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

			String portedSubDetails = smartDesktopDashboardPage.getPortedSubDetails();
			String formattedSub = "";

			if (!portedSub.contains("-")) {
				formattedSub = GenericUtils.getHyphenSeparatedTN(portedSub);
			} else {
				formattedSub = portedSub;
			}

			Validate.assertTrue(portedSubDetails.contains(formattedSub), "Change Request SUB Validation Failed", false,
					"Change Request Validation for " + portedSub + " Passed");
			Validate.assertTrue(portedSubDetails.contains("status:active"),
					"Change Request SUB STATUS Validation Failed", true,
					"Change Request Validation for " + portedSub + " Status Passed");

			Validate.takeStepFullScreenShot("Change Request is successful", Status.PASS);
			Reporting.logReporter(Status.PASS, "=== Change Request From Smart Desktop is Successful===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to complete change# request from Smart Desktop: " + e);
			Validate.assertTrue(false, "Exception Occured:  Unable to submit change Request");
		}

	}

	/**
	 * Method Description: The purpose of this method is to submit OSP Cancel
	 * Request from SMart Desktop's Subscriber Tab
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 * @param portedSub
	 * @param portedBan
	 */
	public static void submitOSPCancelReqFromSubscriberTab(JSONObject testDataJson, String sub, String ban,
			String portedSub, String portedBan) {

		Reporting.logReporter(Status.INFO, "STEP===Submit OSP Cancel Request for '" + portedSub + "'====");
		try {
			LoginPageSteps.appLogin_SD();

			SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);
			SmartDesktopDashboardPage smartDesktopDashboardPage = new SmartDesktopDashboardPage();

			String date = "";

			try {
				if (testDataJson.getBoolean("FUTURE_DATE_FLAG")) {
					date = GenericUtils.getFutureDateFromCurrentDate(1);
				} else {
					date = GenericUtils.getSystemDateInMMDDYYYY();
				}
			} catch (Exception e) {
				date = GenericUtils.getSystemDateInMMDDYYYY();
			}

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.ospCancelLink);
			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.tnToCancel);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.tnToCancel, portedSub);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.tnCancelDate, date);

			Validate.takeStepScreenShot("OSP Cancel Details");

			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.tnCancelSubmitBtn);

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.ospAccountNum, portedBan);
			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.ospAccountNumConfirm, portedBan);

			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.authorizedChkBox);

			BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterCommentsForOSP,
					testDataJson.getString("COMMENTS_FOR_OSP_CANCEL_REQUEST"));

			Validate.takeStepFullScreenShot("Additional Details entered for OSP", Status.INFO);

			BaseSteps.Debugs.scrollToElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);

			Validate.takeStepScreenShot("OSP Request Deatails");

			BaseSteps.Waits.waitForElementToBeClickable(smartDesktopDashboardPage.changeReqFinalAcceptBtn);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

			if (smartDesktopDashboardPage.isErrorMessageDisplayed()) {

				Reporting.logReporter(Status.DEBUG, "OSP Cancel Request is not successful. Error Message: "
						+ smartDesktopDashboardPage.getErrorMessageText());
				Validate.assertTrue(false, "Exception Occured: Unable to submit OSP Cancel Request");
			}

			try {
				if (smartDesktopDashboardPage.isNoUpdateBtnDisplayed()) {
					BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.noUpdateBtn);
				}
			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "No update button is not displayed");
			}

			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.portedSubDetails);

			String portedSubDetails = smartDesktopDashboardPage.getPortedSubDetails();

			String formattedSub = "";
			if (!sub.contains("-")) {
				formattedSub = GenericUtils.getHyphenSeparatedTN(sub);
			} else {
				formattedSub = sub;
			}

			Validate.assertTrue(portedSubDetails.contains(formattedSub), "OSP Cancel SUB Validation Failed", false,
					"OSP Cancel SUB Validation Passed");
			Validate.assertTrue(portedSubDetails.contains("status:active"), "OSP Cancel SUB STATUS Validation Failed",
					true, "OSP Cancel SUB STATUS Validation Passed");

			Validate.takeStepFullScreenShot("OSP Cancel Request Submission", Status.PASS);
			Reporting.logReporter(Status.PASS,
					"=== OSP Cancel Request From Smart Desktop--> OSP Cancel Request is Successful===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to submit OSP Cancel Request " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to submit OSP Cancel Request");
		}
	}

	/**
	 * Method description : This Method is used to verify porting info for the
	 * subscriber
	 * 
	 * @param testDataJson
	 */
	public static void verifyPortingInfo(JSONObject testDataJson) {

		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		String portedMsg = testDataJson.getString("PORTED_MSG");
		String portedIndicator = testDataJson.getString("PORT_OUT_INDICATOR_TEXT");

		String portingInfo = SmartDesktopDashboardPage.getPortingStatusInfoOnMouseHover();
		Validate.assertContains(portingInfo, portedMsg, "Port In/Out Msg mismatch", false);
		Validate.assertContains(portingInfo, portedIndicator, "Port In/Out Msg mismatch", true);

	}

	/**
	 * Method Description: The purpose of this method is to navigate to
	 * communication history tab
	 */
	public static void navigateToCommunicationHistoryTabToVerifySMS(JSONObject testDataJson, String sub) {
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.communicationHistoryTab);

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.openSSOFromCommHistoryPage);
			if (SmartDesktopDashboardPage.getOpenSSOLabelText().equalsIgnoreCase("OpenSSO")) {
				BaseSteps.Windows.switchToNewWindow();
				LoginPageSteps.appLogin_CommHistory();
			}

		} catch (Exception e) {
			BaseSteps.Windows.switchToNewWindow();
			Reporting.logReporter(Status.INFO, "Landed successfully on Communication History tab");
		}

		BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistoryClearSearchBtn, 30);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistoryClearSearchBtn);

		String expectedCommHistoryDescription = testDataJson.getString("COMM_HISTORY_DESCRIPTION");
		String expectedCommHistoryType = testDataJson.getString("COMM_HISTORY_TYPE");
		String expectedCommHistoryStatus = testDataJson.getString("COMM_HISTORY_STATUS");
		String expectedCommHistoryCategory = testDataJson.getString("COMM_HISTORY_CATEGORY");

		String actualCommHistoryDescription = SmartDesktopDashboardPage.getCommHistorySMSDescription();
		String actualCommHistoryType = SmartDesktopDashboardPage.getCommHistoryEventType();
		String actualCommHistoryStatus = SmartDesktopDashboardPage.getCommHistoryEventTypeStatus();
		String actualCommHistoryCategory = SmartDesktopDashboardPage.getCommHistoryCategory();
		String actualCommHistoryDate = SmartDesktopDashboardPage.getCommHistoryDateTime();
		String actualCommHistoryDestinationSub = SmartDesktopDashboardPage.getCommHistoryDestinationSub();

		Validate.takeStepScreenShot("Communication history page");

		String expectedCommHistoryDate = GenericUtils.getSystemDateInMMMDDYYYY();

		Validate.assertContains(actualCommHistoryDate, expectedCommHistoryDate, true);

		Validate.assertEquals(actualCommHistoryDescription.toUpperCase(), expectedCommHistoryDescription.toUpperCase(),
				"CommHistoryDescription is not as expected", false);
		Validate.assertEquals(actualCommHistoryType.toUpperCase(), expectedCommHistoryType.toUpperCase(),
				"CommHistoryType is not as expected", true);

		Validate.assertEquals(actualCommHistoryCategory.toUpperCase(), expectedCommHistoryCategory.toUpperCase(),
				"CommHistoryCategory is not as expected", true);
		Validate.assertEquals(actualCommHistoryDestinationSub, sub, "CommHistoryDestinationSub is not as expected",
				true);

	}

	/**
	 * Method Description: The purpose of this method is to navigate to
	 * communication history tab
	 */
	public static void navigateToCommunicationHistoryTabToVerifyEmail(JSONObject testDataJson, String sub) {
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.communicationHistoryTab);

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.openSSOFromCommHistoryPage);
			if (SmartDesktopDashboardPage.getOpenSSOLabelText().equalsIgnoreCase("OpenSSO")) {
				BaseSteps.Windows.switchToNewWindow();
				LoginPageSteps.appLogin_CommHistory();
			}

		} catch (Exception e) {
			BaseSteps.Windows.switchToNewWindow();
			Reporting.logReporter(Status.INFO, "Landed successfully on Communication History tab");
		}

		BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistoryClearSearchBtn, 30);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistoryClearSearchBtn);

		String expectedCommHistoryDescription = testDataJson.getString("COMM_HISTORY_DESCRIPTION");
		String expectedCommHistoryType = testDataJson.getString("COMM_HISTORY_TYPE");
		String expectedCommHistoryStatus = testDataJson.getString("COMM_HISTORY_STATUS");
		String expectedCommHistoryCategory = testDataJson.getString("COMM_HISTORY_CATEGORY");

		String actualCommHistoryDescription = SmartDesktopDashboardPage.getCommHistorySMSDescription();
		String actualCommHistoryType = SmartDesktopDashboardPage.getCommHistoryEventType();
		String actualCommHistoryStatus = SmartDesktopDashboardPage.getCommHistoryEventTypeStatus();
		String actualCommHistoryCategory = SmartDesktopDashboardPage.getCommHistoryCategory();
		String actualCommHistoryDate = SmartDesktopDashboardPage.getCommHistoryDateTime();

		String expectedCommHistoryDate = GenericUtils.getSystemDateInMMMDDYYYY();

		Validate.assertContains(actualCommHistoryDescription.toUpperCase(),
				expectedCommHistoryDescription.toUpperCase(), "CommHistoryDescription is not as expected", false);
		Validate.assertEquals(actualCommHistoryType.toUpperCase(), expectedCommHistoryType.toUpperCase(),
				"CommHistoryType is not as expected", true);

		Validate.assertEquals(actualCommHistoryCategory.toUpperCase(), expectedCommHistoryCategory.toUpperCase(),
				"CommHistoryCategory is not as expected", true);
		Validate.assertContains(actualCommHistoryDate, expectedCommHistoryDate, true);

	}

	/**
	 * Method Description: The purpose of this method is to submit Resume Request
	 * from SMart Desktop Subscriber's Tab
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param portedBan
	 */
	public static void submitResumeRequestFromSD(JSONObject testDataJson, String sub, String portedBan) {

		LoginPageSteps.appLogin_SD();
		SmartDesktopSearchPageSteps.searchPhoneNo(sub);

		String comments = testDataJson.getString("COMMENTS");

		SmartDesktopDashboardPage smartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.resumeLink);

		Validate.takeStepScreenShot("After clicking on Resume link");

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.skipPortReqChkBox);
			BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.skipPortReqChkBox);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Skip Port Check Box Not displayed");
		}

		BaseSteps.Waits.waitForElementToBeClickableLongWait(smartDesktopDashboardPage.enterCommentsForOSP);
		BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterCommentsForOSP, comments);
		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);

		BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospIMEINum, portedBan);
		BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.ospIMEINumConfirm, portedBan);
		BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.alternateContactNum,
				testDataJson.getString("ALTERNATE_CONTACT_NUMBER"));

		BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.numberIsActiveConfirmChkBox);
		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.authorizedChkBox);

		if (smartDesktopDashboardPage.isAuthorizationNameBlank())
			BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopDashboardPage.authorizationName, "TEST USER");

		BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterCommentsForOSP, "TEST");

		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);
		BaseSteps.Waits.waitForElementToBeClickableLongWait(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

		Validate.takeStepScreenShot("Resume Request Details");

		String expectedResumeSubSection = testDataJson.getString("RESUME_SUBSCRIBER_CONFIRMATION");
		BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.resumeSectionDisplayedLabel);

		String actualConfrimationMsg = smartDesktopDashboardPage.getResumeRequestHeaderLabelText();
		Validate.assertEquals(actualConfrimationMsg, expectedResumeSubSection,
				"Resume Confimration Message not as expected", false);

		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

		String expectedResumeSummaryHeader = testDataJson.getString("RESUME_SUMMARY_CONFIRMATION");

		BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.resumeRequestConfirmationMsg);
		String actualResumeSummaryHeader = smartDesktopDashboardPage.getResumeRequestHeaderLabelText();
		Validate.assertEquals(actualResumeSummaryHeader, expectedResumeSummaryHeader, "Subscriber Resume Failed",
				false);

	}

	/**
	 * Method Description: This method is used to perform transfer block/allow for
	 * the subscriber
	 * 
	 * @param param
	 * @param comments
	 */
	public static void performPPFromSD(String param, String comments) {
		SmartDesktopDashboardPage smartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.transferBlockTab);

		Validate.takeStepScreenShot("Transfer Block Page");

		BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.subscriberBlockedDrpdwn);

		BaseSteps.Dropdowns.selectByVisibleText(smartDesktopDashboardPage.subscriberBlockedDrpdwn, param);
		BaseSteps.SendKeys.sendKey(smartDesktopDashboardPage.enterCommentsForOSP, comments);

		Validate.takeStepScreenShot("Transfer Block Details before Accept");

		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.submitChangeNumberRequestBtn);

		BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

		Validate.takeStepScreenShot("Transfer Blocked Details after Accept");
		String actualText = smartDesktopDashboardPage.getSubscriberBlockedLabelText();

		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.changeReqFinalAcceptBtn);

		Validate.assertEquals(actualText, param, false);

		BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopDashboardPage.noUpdateBtn);
		BaseSteps.Clicks.clickElement(smartDesktopDashboardPage.noUpdateBtn);
		Validate.takeStepFullScreenShot("Transfer block reuest is successful", Status.INFO);

	}

	/**
	 * Method Description : This method is used to verify the consumer type
	 * 
	 * @param expectedConsumerType
	 */
	public static void verifyConsumerTypeFromSD(String expectedConsumerType) {
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		String actualConsumerType = SmartDesktopDashboardPage.getConsumerTypeText().trim();

		Validate.assertEquals(actualConsumerType, expectedConsumerType, "Consumer Type Mismatch", false);

	}

	/**
	 * Method Description: The purpose of this method is to navigate to
	 * communication history tab
	 */
	public static void verifyNo2FASentFromCommnicationHistory(String expectedText, String sub) {

		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.communicationHistoryTab);

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.openSSOFromCommHistoryPage);
			if (SmartDesktopDashboardPage.getOpenSSOLabelText().equalsIgnoreCase("OpenSSO")) {
				BaseSteps.Windows.switchToNewWindow();
				LoginPageSteps.appLogin_CommHistory();
				Validate.takeStepScreenShot("Communication History Page");

			}

		} catch (Exception e) {
			BaseSteps.Windows.switchToNewWindow();
			System.out.println("Landed successfully on Communication History tab");
		}

		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.noSearchResultsFoundCommHistory);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.noSearchResultsFoundCommHistory);
		String actualText = SmartDesktopDashboardPage.getNoSearchResultsFoundCommHistory();

		Validate.assertContains(actualText.toUpperCase(), expectedText.toUpperCase(),
				"No 2FA Sent Verification in Communication History", false);
		Reporting.logReporter(Status.INFO, "NO 2FA SMS SENT FOR SUB: " + sub);

	}

	/**
	 * 
	 */
	public static void verifyNoSMSEntryForCurrentDay(String sub) {

		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.communicationHistoryTab);

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.openSSOFromCommHistoryPage);
			if (SmartDesktopDashboardPage.getOpenSSOLabelText().equalsIgnoreCase("OpenSSO")) {
				BaseSteps.Windows.switchToNewWindow();
				LoginPageSteps.appLogin_CommHistory();
			}

		} catch (Exception e) {
			BaseSteps.Windows.switchToNewWindow();
			Reporting.logReporter(Status.INFO, "Landed successfully on Communication History tab");
		}

		String expectedCommHistoryDate = GenericUtils.getSystemDateInMMMDDYYYY();
		String actualCommHistoryDate = null;

		try {
			BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistoryDateTime, 30);
			actualCommHistoryDate = SmartDesktopDashboardPage.getCommHistoryDateTime();
			Validate.assertNotContains(actualCommHistoryDate, expectedCommHistoryDate, "SMS Entry Not Present", false);
			Reporting.logReporter(Status.INFO, "No SMS Entries are Present for the Date:" + actualCommHistoryDate);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO,
					"SMS Entries are Present for the Current Date:" + actualCommHistoryDate + ":" + e);
			Validate.assertTrue(false, "Exception Occured: SMS Entries are Present for the Current Date");
		}

	}

	/**
	 * 
	 */
	public static void openPreviewAndCaptureEmail(String subjectLine) {

		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.firstRowPreviewBtnCommHistory);

		BaseSteps.Windows.switchToNewWindow();
		BaseSteps.Windows.maximizeWindow();

		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.subjectTextInEmailFromCommHistory);

		Validate.takeStepScreenShot("Email Details");

		String actualSubjectLine = SmartDesktopDashboardPage.getSubjectTitleInEmailFromCommHistory();
		Validate.assertContains(actualSubjectLine.toUpperCase(), subjectLine.toUpperCase(),
				"Email Sent Verificaton Failed", false);

		Reporting.logReporter(Status.INFO, "Email Verification Passed");
		Validate.takeStepFullScreenShot("Email Capture", Status.INFO);

	}

	/**
	 * 
	 * @param expectedMessageContains
	 * @param sub
	 */
	public static void openPreviewAndVerifySMSDetails(String expectedMessageContains, String sub) {

		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.firstRowPreviewBtnCommHistory);

		BaseSteps.Windows.switchToNewWindow();
		BaseSteps.Windows.maximizeWindow();

		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.oopsMsgSubFromCommHistory);

		Validate.takeStepScreenShot("OOPs SMS Details");

		String actualSub = SmartDesktopDashboardPage.getOOPSMsgSubFromCommHistory();

		Validate.assertEquals(actualSub, sub, false);
		String actualMessageContains = SmartDesktopDashboardPage.getOOPSMsgBodyFromCommHistory();
		Validate.assertContains(actualMessageContains.toUpperCase(), expectedMessageContains.toUpperCase(),
				"OOPS MSG Body Verificaton Failed", false);

		Reporting.logReporter(Status.INFO, "Email Verification Passed");
		Validate.takeStepFullScreenShot("Email Capture", Status.INFO);

	}

	/**
	 * Method Description: The purpose of this method is to navigate to
	 * communication history tab and verify SMS entry
	 */
	public static void navigateToCommunicationHistoryTabToVerifySMS_MVNE(JSONObject testDataJson, String sub) {
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.communicationHistoryTab);

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.openSSOFromCommHistoryPage);
			if (SmartDesktopDashboardPage.getOpenSSOLabelText().equalsIgnoreCase("OpenSSO")) {
				BaseSteps.Windows.switchToNewWindow();
				LoginPageSteps.appLogin_CommHistory();
			}

		} catch (Exception e) {
			BaseSteps.Windows.switchToNewWindow();
			Reporting.logReporter(Status.INFO, "Landed successfully on Communication History tab");
		}

		BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber, 30);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber);
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber);

		BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopDashboardPage.commHistoryPhoneNumberTxtBox);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SmartDesktopDashboardPage.commHistoryPhoneNumberTxtBox, sub);

		Validate.takeStepFullScreenShot("TN before Search", Status.INFO);

		BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopDashboardPage.commHistorySearchBtn);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistorySearchBtn);
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.commHistorySearchBtn);

		BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistoryClearSearchBtn, 30);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistoryClearSearchBtn);

		String expectedCommHistoryDescription = testDataJson.getString("COMM_HISTORY_DESCRIPTION");
		String expectedCommHistoryType = testDataJson.getString("COMM_HISTORY_TYPE");
		String expectedCommHistoryStatus = testDataJson.getString("COMM_HISTORY_STATUS");
		String expectedCommHistoryCategory = testDataJson.getString("COMM_HISTORY_CATEGORY");

		String actualCommHistoryDescription = SmartDesktopDashboardPage.getCommHistorySMSDescription();
		String actualCommHistoryType = SmartDesktopDashboardPage.getCommHistoryEventType();
		String actualCommHistoryStatus = SmartDesktopDashboardPage.getCommHistoryEventTypeStatus();
		String actualCommHistoryCategory = SmartDesktopDashboardPage.getCommHistoryCategory();
		String actualCommHistoryDate = SmartDesktopDashboardPage.getCommHistoryDateTime();
		String actualCommHistoryDestinationSub = SmartDesktopDashboardPage.getCommHistoryDestinationSub();

		Validate.takeStepScreenShot("Communication history page");

		String expectedCommHistoryDate = GenericUtils.getSystemDateInMMMDDYYYY();

		// Validate.assertContains(actualCommHistoryDate, expectedCommHistoryDate,
		// true);

		Validate.assertEquals(actualCommHistoryDescription.toUpperCase(), expectedCommHistoryDescription.toUpperCase(),
				"CommHistoryDescription is not as expected", false);
		Validate.assertEquals(actualCommHistoryType.toUpperCase(), expectedCommHistoryType.toUpperCase(),
				"CommHistoryType is not as expected", true);

		Validate.assertEquals(actualCommHistoryCategory.toUpperCase(), expectedCommHistoryCategory.toUpperCase(),
				"CommHistoryCategory is not as expected", true);
		Validate.assertEquals(actualCommHistoryDestinationSub, sub, "CommHistoryDestinationSub is not as expected",
				true);

	}

	/**
	 * Method Description: The purpose of this method is to navigate to
	 * communication history tab and Veirfy SMS Details
	 */
	public static void navigateToCommunicationHistoryTabToVerifySMSDetails(JSONObject testDataJson, String sub) {
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.communicationHistoryTab);

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.openSSOFromCommHistoryPage);
			if (SmartDesktopDashboardPage.getOpenSSOLabelText().equalsIgnoreCase("OpenSSO")) {
				BaseSteps.Windows.switchToNewWindow();
				LoginPageSteps.appLogin_CommHistory();
			}

		} catch (Exception e) {
			BaseSteps.Windows.switchToNewWindow();
			Reporting.logReporter(Status.INFO, "Landed successfully on Communication History tab");
		}

		searchTNIfNoResultsDisplayed(sub);

		String expectedCommHistoryDescription = testDataJson.getString("COMM_HISTORY_DESCRIPTION");
		String expectedCommHistoryType = testDataJson.getString("COMM_HISTORY_TYPE");
		String expectedCommHistoryCategory = testDataJson.getString("COMM_HISTORY_CATEGORY");
		String expectedMessageContains = testDataJson.getString("EXPECTED_BODY_TEXT");
		String expectedCommHistoryDate = GenericUtils.getSystemDateInMMMDDYYYY();

		String actualCommHistoryDescription = SmartDesktopDashboardPage
				.MessageDescription(expectedCommHistoryDescription).getText();
		String actualCommHistoryType = SmartDesktopDashboardPage.MessageEvent(expectedCommHistoryType).getText();
		String actualCommHistoryCategory = SmartDesktopDashboardPage.MessageCategory(expectedCommHistoryCategory)
				.getText();
		String actualCommHistoryDate = SmartDesktopDashboardPage.MessageDate(expectedCommHistoryDate).getText();

		Validate.takeStepScreenShot("Communication history page");

		Validate.assertContains(actualCommHistoryDate, expectedCommHistoryDate, true);

		Validate.assertEquals(actualCommHistoryDescription.toUpperCase(), expectedCommHistoryDescription.toUpperCase(),
				"CommHistoryDescription is not as expected", false);

		Validate.assertEquals(actualCommHistoryType.toUpperCase(), expectedCommHistoryType.toUpperCase(),
				"CommHistoryType is not as expected", true);

		Validate.assertEquals(actualCommHistoryCategory.toUpperCase(), expectedCommHistoryCategory.toUpperCase(),
				"CommHistoryCategory is not as expected", true);

		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.firstRowPreviewBtnCommHistory);

		BaseSteps.Windows.switchToNewWindow();
		BaseSteps.Windows.maximizeWindow();

		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.oopsMsgSubFromCommHistory);

		Validate.takeStepScreenShot("2FA SMS Details Captured");

		String actualSub = SmartDesktopDashboardPage.getOOPSMsgSubFromCommHistory();

		Validate.assertEquals(actualSub, sub, false);
		String actualMessageContains = SmartDesktopDashboardPage.getOOPSMsgBodyFromCommHistory();
		Validate.assertContains(actualMessageContains.toUpperCase(), expectedMessageContains.toUpperCase(),
				"MSG Body Verificaton Failed", false);

		Reporting.logReporter(Status.INFO, "2FA SMS Body Verification Passed");
		Validate.takeStepFullScreenShot("2FA SMS Body Capture", Status.INFO);

	}

	public static void searchTNIfNoResultsDisplayed(String sub) {

		BaseSteps.Waits.waitGeneric(5000);
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();

		try {
			BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistoryClearSearchBtn, 30);
			BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistoryClearSearchBtn);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "No Records Displayed");
		}

		if (SmartDesktopDashboardPage.getNoResultsFoundStatus()) {
			BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber, 30);
			BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber);
			BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopDashboardPage.commHistoryPhoneNumberTxtBox);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SmartDesktopDashboardPage.commHistoryPhoneNumberTxtBox, sub);
			
			BaseSteps.Waits.waitGeneric(30000);

			Validate.takeStepFullScreenShot("TN before Search", Status.INFO);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopDashboardPage.commHistorySearchBtn);
			BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistorySearchBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.commHistorySearchBtn);

			BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistoryClearSearchBtn, 30);
			BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistoryClearSearchBtn);

		}
	}
	
	/**
	 * Method Description: The purpose of this method is to navigate to
	 * communication history tab and verify SMS entry
	 */
	public static void navigateToCommunicationHistoryTabToVerifySMSDetails_NEWLogin(JSONObject testDataJson, String sub) {
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();

		BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber, 30);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber);
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.commHistorySearchByPhoneNumber);

		BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopDashboardPage.commHistoryPhoneNumberTxtBox);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SmartDesktopDashboardPage.commHistoryPhoneNumberTxtBox, sub);

		Validate.takeStepFullScreenShot("TN before Search", Status.INFO);

		BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopDashboardPage.commHistorySearchBtn);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistorySearchBtn);
		BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.commHistorySearchBtn);

		BaseSteps.Waits.waitForElementVisibility(SmartDesktopDashboardPage.commHistoryClearSearchBtn, 30);
		BaseSteps.Debugs.scrollToElement(SmartDesktopDashboardPage.commHistoryClearSearchBtn);

		String expectedCommHistoryDescription = testDataJson.getString("COMM_HISTORY_DESCRIPTION");
		String expectedCommHistoryType = testDataJson.getString("COMM_HISTORY_TYPE");
		String expectedCommHistoryStatus = testDataJson.getString("COMM_HISTORY_STATUS");
		String expectedCommHistoryCategory = testDataJson.getString("COMM_HISTORY_CATEGORY");

		String actualCommHistoryDescription = SmartDesktopDashboardPage.getCommHistorySMSDescription();
		String actualCommHistoryType = SmartDesktopDashboardPage.getCommHistoryEventType();
		String actualCommHistoryStatus = SmartDesktopDashboardPage.getCommHistoryEventTypeStatus();
		String actualCommHistoryCategory = SmartDesktopDashboardPage.getCommHistoryCategory();
		String actualCommHistoryDate = SmartDesktopDashboardPage.getCommHistoryDateTime();
		String actualCommHistoryDestinationSub = SmartDesktopDashboardPage.getCommHistoryDestinationSub();

		Validate.takeStepScreenShot("Communication history page");

		String expectedCommHistoryDate = GenericUtils.getSystemDateInMMMDDYYYY();

		// Validate.assertContains(actualCommHistoryDate, expectedCommHistoryDate,
		// true);

		Validate.assertEquals(actualCommHistoryDescription.toUpperCase(), expectedCommHistoryDescription.toUpperCase(),
				"CommHistoryDescription is not as expected", false);
		Validate.assertEquals(actualCommHistoryType.toUpperCase(), expectedCommHistoryType.toUpperCase(),
				"CommHistoryType is not as expected", true);

		Validate.assertEquals(actualCommHistoryCategory.toUpperCase(), expectedCommHistoryCategory.toUpperCase(),
				"CommHistoryCategory is not as expected", true);
		Validate.assertEquals(actualCommHistoryDestinationSub, sub, "CommHistoryDestinationSub is not as expected",
				true);

		openPreviewAndCaptureSMSDetails(sub);
	}
	
	public static void openPreviewAndCaptureSMSDetails(String sub) {

		try {
			SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
			BaseSteps.Clicks.clickElement(SmartDesktopDashboardPage.firstRowPreviewBtnCommHistory);

			BaseSteps.Windows.switchToNewWindow();
			BaseSteps.Windows.maximizeWindow();

			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.oopsMsgSubFromCommHistory);

			Validate.takeStepScreenShot("SMS Details");

			String actualSub = SmartDesktopDashboardPage.getOOPSMsgSubFromCommHistory();

			String actualMessageContains = SmartDesktopDashboardPage.getOOPSMsgBodyFromCommHistory();

			Reporting.logReporter(Status.INFO, "Captured SMS Details for sub : "+ sub + " is : " + actualMessageContains);
			Validate.takeStepFullScreenShot("SMS Details Capture", Status.INFO);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to open preview to capture SMS details for sub : " + sub);
		}

	}


}
