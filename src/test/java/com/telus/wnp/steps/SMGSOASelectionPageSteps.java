package com.telus.wnp.steps;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for SMG SOA Selection Page (Steps) > AUTHOR:
 * x241410
 ****************************************************************************
 */

import com.test.utils.Status;
import com.telus.wnp.pages.SMGASMSPage;
import com.telus.wnp.pages.SMGSOASelectionPage;
import com.telus.wnp.pages.SMGSOASubscriptionDetailsPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;

import org.json.JSONObject;

public class SMGSOASelectionPageSteps extends BaseSteps {

	/**
	 * Method Description: Navigate to SMG SOA Selection Page
	 */
	public static void navigateToSMGSOASelectionPage() {
		LoginPageSteps.appLogin_SMG();
		SMGPageSteps.verifySMGHomePageIsDisplayed();
		SMGPageSteps.clickCWNPTest();
		SMGPageSteps.clickPortCenterCT6();
		// BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.maxUserLoggedInPopUpDisplayed();
		BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.clickSOASelection();
		BaseSteps.Windows.switchToNewWindow();

	}
	
	/**
	 * Method Description: Navigate to SMG SOA Selection Page
	 */
	public static void navigateToSMGSOASubscriptionPage() {
		LoginPageSteps.appLogin_SMG();
		SMGPageSteps.verifySMGHomePageIsDisplayed();
		SMGPageSteps.clickCWNPTest();
		SMGPageSteps.clickPortCenterCT6();
		// BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.maxUserLoggedInPopUpDisplayed();
		BaseSteps.Windows.switchToNewWindow();
		clickSOASubscription();
		BaseSteps.Windows.switchToNewWindow();

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

	/**
	 * Method Description: To query From SOASelectionPage
	 */
	public static void queryFromSOASelectionPage(String param, String value) {
		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASelectionPage.queryBtn);

		selectQueryParameter(param, value);
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.queryBtn);
		Validate.takeStepScreenShot("Search Results From SOA" , Status.INFO);

	}

	/**
	 * Method Description: To perform Cancel From SOA
	 * 
	 * @param testDataJson
	 */
	public static void performCancelFromSOA(JSONObject testDataJson) {

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();

		BaseSteps.Debugs.scrollToElement(SMGSOASelectionPage.bottomElement);
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickLatestTNCheckBox());

		String selectOption = testDataJson.getString("SELECT_ACTION_DROPDOWN_CANCEL");
		BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.selectActionSOADrpDwn, selectOption);

		Validate.takeStepScreenShot("After Selecting Cancel From SOA Page", Status.INFO);
		BaseSteps.Waits.waitGeneric(2000);
		if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed()) {
			BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);
		}
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);

		String expectedConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");
		String actualConfirmationMsg = SMGSOASubscriptionDetailsPage.getConfirmationMsg();

		Validate.assertEquals(actualConfirmationMsg, expectedConfirmationMsg, "Request not submitted successfully",
				false);
		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.PASS);

	}

	/**
	 * Method Description: To perform Cancel For Specific RequestType From SOA
	 * 
	 * @param testDataJson
	 */
	public static void performCancelForSpecificRequestTypeFromSOA(JSONObject testDataJson, String expectedReqLabel) {

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASelectionPage.viewBtn);
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickSpecificRequestTypeTNCheckBox(expectedReqLabel));

		/*
		 * String selectOption =
		 * testDataJson.getString("SELECT_ACTION_DROPDOWN_CANCEL");
		 * BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.
		 * selectActionSOADrpDwn, selectOption);
		 * 
		 * if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed())
		 * BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);
		 * saveDetails(testDataJson);
		 */

	}

	/**
	 * Method Description: To perform Cancel From SOA As NSP
	 * 
	 * @param testDataJson
	 */
	public static void performCancelFromSOAAsNSP(JSONObject testDataJson) {

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();

		BaseSteps.Debugs.scrollToElement(SMGSOASelectionPage.bottomElement);
		String expectedRecord = testDataJson.getString("NSP_ACTION_PORT_TYPE");
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickTNCheckBoxBasedOnPortType(expectedRecord));

		String selectOption = testDataJson.getString("SELECT_ACTION_DROPDOWN_CANCEL");
		BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.selectActionSOADrpDwn, selectOption);

		Validate.takeStepScreenShot("Selected Cancel from SOA Page", Status.INFO);
		BaseSteps.Waits.waitGeneric(2000);
		if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed()) {
			BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);
		}
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);
		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);
		
		String expectedConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");
		String actualConfirmationMsg = SMGSOASubscriptionDetailsPage.getConfirmationMsg();

		Validate.assertEquals(actualConfirmationMsg, expectedConfirmationMsg, "Request not submitted successfully",
				false);
		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.PASS);

	}

	/**
	 * Method Description: To perform Acknowledge Cancellation From SOA As NSP
	 * 
	 * @param testDataJson
	 */
	public static void performAcknowledgeCancellationFromSOA(JSONObject testDataJson) {

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();

		BaseSteps.Debugs.scrollToElement(SMGSOASelectionPage.bottomElement);
		String expectedRecord = testDataJson.getString("OSP_ACTION_PORT_TYPE");
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickTNCheckBoxBasedOnPortType(expectedRecord));

		String selectOption = testDataJson.getString("SELECT_ACTION_DROPDOWN_ACKNOWLEDGE_CANCEL");
		BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.selectActionSOADrpDwn, selectOption);

		BaseSteps.Waits.waitGeneric(2000);
		if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed()) {
			BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);
		}
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);

		String expectedConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");
		String actualConfirmationMsg = SMGSOASubscriptionDetailsPage.getConfirmationMsg();

		Validate.assertEquals(actualConfirmationMsg, expectedConfirmationMsg, "Request not submitted successfully",
				false);
		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.PASS);

	}

	/**
	 * Method Description: To perform Modify Active
	 * 
	 * @param testDataJson
	 */
	public static void performModifyActive(JSONObject testDataJson, String sub, String lrn) {

		String selectOption = testDataJson.getString("SELECT_ACTION_DROPDOWN_MODIFY_ACTIVE");

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASelectionPage.viewBtn);
		System.out.println(SMGSOASelectionPage.returnNumberOfRecordsPresent());
		BaseSteps.Debugs.scrollToElement(SMGSOASelectionPage.clickLatestTNCheckBox());
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickLatestTNCheckBox());

		BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.selectActionSOADrpDwn, selectOption);

		BaseSteps.Windows.switchToNewWindow();
		if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed())
			BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);

		// will navigate to Selection Page
		BaseSteps.Windows.switchToNewWindow();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.fromTNSelection);

		String expectedNPACStatus = testDataJson.getString("NPAC_STATUS");
		String actualNPACStatus = SMGSOASubscriptionDetailsPage.getNPACStatus();

		Validate.assertEquals(actualNPACStatus, expectedNPACStatus, false);
		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.lrnSelection);
		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGSOASubscriptionDetailsPage.lrnSelection, lrn);

		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.saveBtnSelection);
		BaseSteps.Clicks.clickElement(SMGSOASubscriptionDetailsPage.saveBtnSelection);

		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Windows.switchToNewWindow();
		if (SMGSOASubscriptionDetailsPage.isOKBtnPopUpAfterLRNUpdateDisplayed())
			BaseSteps.Clicks.clickElement(SMGSOASubscriptionDetailsPage.okBtnPopUpAfterLRNUpdateSelection);

		// always use 647-213-7704 as modified LRN
		String expectedReqConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);
		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);
		String actualReqConfirmationMsg = SMGSOASubscriptionDetailsPage.getReqSubmitConfirmationMsg();
		BaseSteps.Waits.waitGeneric(2000);

		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.INFO);
		Validate.assertEquals(actualReqConfirmationMsg, expectedReqConfirmationMsg,
				"Request not submitted successfully", false);

	}

	/**
	 * Method Description: To perform Modify Pending
	 * 
	 * @param testDataJson
	 */
	public static void performModifyPending(JSONObject testDataJson, String sub, String ddt) {

		String selectOption = testDataJson.getString("SELECT_ACTION_DROPDOWN_MODIFY_PENDING");

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASelectionPage.viewBtn);
		System.out.println(SMGSOASelectionPage.returnNumberOfRecordsPresent());
		BaseSteps.Debugs.scrollToElement(SMGSOASelectionPage.clickLatestTNCheckBox());
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickLatestTNCheckBox());

		BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.selectActionSOADrpDwn, selectOption);

		Validate.takeStepScreenShot("After Selecting Modify Pending From SOA Page", Status.INFO);
		
		BaseSteps.Windows.switchToNewWindow();
		if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed())
			BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);

		// will navigate to Selection Page
		BaseSteps.Windows.switchToNewWindow();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.fromTNSelection);

		Validate.takeStepScreenShot("Current NPAC Status", Status.INFO);

		
		String expectedNPACStatus = testDataJson.getString("NPAC_STATUS");
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.npacStatus);
		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.npacStatus);
		String actualNPACStatus = SMGSOASubscriptionDetailsPage.getNPACStatus();
		Validate.assertEquals(actualNPACStatus, expectedNPACStatus, false);

		/*
		 * 
		 * Read DDT Modify DDT date and Time, and give activate time as +2 minutes
		 * 
		 */
		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.osp_dddt_date);
		BaseSteps.Waits.waitGeneric(2000);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGSOASubscriptionDetailsPage.osp_dddt_date, ddt.split(" ")[0]);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGSOASubscriptionDetailsPage.osp_dddt_time, ddt.split(" ")[1]);

		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.saveBtnSelection);
		BaseSteps.Clicks.clickElement(SMGSOASubscriptionDetailsPage.saveBtnSelection);

		if (SMGSOASubscriptionDetailsPage.isOKBtnPopUpAfterLRNUpdateDisplayed())
			BaseSteps.Clicks.clickElement(SMGSOASubscriptionDetailsPage.okBtnPopUpAfterLRNUpdateSelection);

		// always use 647-213-7704 as modified LRN
		String expectedReqConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

		String actualReqConfirmationMsg = null;
		BaseSteps.Waits.waitGeneric(2000);

		try {

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg1);
			BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg1);
			actualReqConfirmationMsg = SMGSOASubscriptionDetailsPage.getAdditionalRequestSubmissionMsg();
			
			

		} catch (Exception e) {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);
			BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);
			actualReqConfirmationMsg = SMGSOASubscriptionDetailsPage.getReqSubmitConfirmationMsg();
		}

		BaseSteps.Waits.waitGeneric(2000);

		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.INFO);
		Validate.assertEquals(actualReqConfirmationMsg, expectedReqConfirmationMsg,
				"Request not submitted successfully", false);

	}

	
	
	
	/**
	 * * Method Description: To perform UndoCancel From SOA
	 * 
	 * @param testDataJson
	 */
	public static void performUndoCancelFromSOA(JSONObject testDataJson) {

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();

		BaseSteps.Debugs.scrollToElement(SMGSOASelectionPage.bottomElement);
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickLatestTNCheckBox());

		String selectOption = testDataJson.getString("SELECT_ACTION_DROPDOWN_UNDO_CANCEL");
		BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.selectActionSOADrpDwn, selectOption);

		Validate.takeStepScreenShot("After Selecting Undo Cancel From SOA Page", Status.INFO);

		BaseSteps.Waits.waitGeneric(2000);
		if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed()) {
			BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);
		}
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);

		String expectedConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");
		String actualConfirmationMsg = SMGSOASubscriptionDetailsPage.getConfirmationMsg();

		Validate.assertEquals(actualConfirmationMsg, expectedConfirmationMsg, "Request not submitted successfully",
				false);
		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.PASS);

	}

	/**
	 * Method Description: To perform UndoCancelModify From SOA
	 * 
	 * @param testDataJson
	 */
	public static void performUndoCancelModifyFromSOA(JSONObject testDataJson, String ddt) {

		String selectOption = testDataJson.getString("SELECT_ACTION_DROPDOWN_UNDO_CANCEL_MODIFY");

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASelectionPage.viewBtn);
		System.out.println(SMGSOASelectionPage.returnNumberOfRecordsPresent());
		BaseSteps.Debugs.scrollToElement(SMGSOASelectionPage.clickLatestTNCheckBox());
		BaseSteps.Clicks.clickElement(SMGSOASelectionPage.clickLatestTNCheckBox());

		BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.selectActionSOADrpDwn, selectOption);

		BaseSteps.Windows.switchToNewWindow();
		if (SMGSOASelectionPage.isOkBtnFrompopUpDisplayed())
			BaseSteps.Clicks.clickElement(SMGSOASelectionPage.okBtnFrompopUp);

		// will navigate to Selection Page
		BaseSteps.Windows.switchToNewWindow();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.fromTNSelection);

		/*
		 * 
		 * Read DDT Modify DDT date and Time, and give activate time as +2 minutes
		 * 
		 */
		BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGSOASubscriptionDetailsPage.osp_dddt_date);

		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.osp_dddt_date);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGSOASubscriptionDetailsPage.osp_dddt_date, ddt.split(" ")[0]);
		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.SendKeys.clearFieldAndSendKeys(SMGSOASubscriptionDetailsPage.osp_dddt_time, ddt.split(" ")[1]);
		BaseSteps.Waits.waitGeneric(500);

		BaseSteps.Debugs.scrollToElement(SMGSOASubscriptionDetailsPage.saveBtnSelection);
		BaseSteps.Clicks.clickElement(SMGSOASubscriptionDetailsPage.saveBtnSelection);

		if (SMGSOASubscriptionDetailsPage.isOKBtnPopUpAfterLRNUpdateDisplayed())
			BaseSteps.Clicks.clickElement(SMGSOASubscriptionDetailsPage.okBtnPopUpAfterLRNUpdateSelection);

		// always use 647-213-7704 as modified LRN
		String expectedReqConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");

		String actualReqConfirmationMsg = null;
		try {

			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg1);
			actualReqConfirmationMsg = SMGSOASubscriptionDetailsPage.getAdditionalRequestSubmissionMsg();

		} catch (Exception e) {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);
			actualReqConfirmationMsg = SMGSOASubscriptionDetailsPage.getReqSubmitConfirmationMsg();
		}

		BaseSteps.Waits.waitGeneric(2000);

		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.INFO);
		Validate.assertEquals(actualReqConfirmationMsg, expectedReqConfirmationMsg,
				"Request not submitted successfully", false);

	}

	/**
	 * Method Description: To perform saveDetails From SOA
	 * 
	 * @param testDataJson
	 */
	public static void saveDetails(JSONObject testDataJson) {
		SMGSOASubscriptionDetailsPage SMGSOASubscriptionDetailsPage = new SMGSOASubscriptionDetailsPage();

		// click on Save button
		BaseSteps.Clicks.clickElement(SMGSOASubscriptionDetailsPage.saveBtnSelection);

		BaseSteps.Waits.waitForElementVisibilityLongWait(SMGSOASubscriptionDetailsPage.reqSubmitCnfrmMsg);

		String expectedConfirmationMsg = testDataJson.getString("REQUEST_SUBMISSION_MESSAGE");
		String actualConfirmationMsg = SMGSOASubscriptionDetailsPage.getConfirmationMsg();

		Validate.assertEquals(actualConfirmationMsg, expectedConfirmationMsg, "Request not submitted successfully",
				false);
		Validate.takeStepFullScreenShot("SOA Page Sreenshot", Status.PASS);

	}

	/**
	 * Method Description: To select Query Parameter
	 * 
	 * @param param
	 * @param value
	 */
	public static void selectQueryParameter(String param, String value) {

		SMGSOASelectionPage SMGSOASelectionPage = new SMGSOASelectionPage();
		BaseSteps.Waits.waitForElementToBeClickableLongWait(SMGSOASelectionPage.fromTN);
		BaseSteps.Waits.waitGeneric(1000);
		
		switch (param.toUpperCase()) {

		case "FROMTN":
			BaseSteps.SendKeys.sendKey(SMGSOASelectionPage.fromTN, value);
			break;

		case "TOTN":
			BaseSteps.SendKeys.sendKey(SMGSOASelectionPage.toTN, value);
			break;

		case "OSP":
			BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.SPID_oldSP, "");
			break;

		case "NSP":
			BaseSteps.Dropdowns.selectByVisibleText(SMGSOASelectionPage.SPID_newSP, "");
			break;

		default:
			BaseSteps.SendKeys.sendKey(SMGSOASelectionPage.fromTN, value);
			break;
		}
	}
	
	/**
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void createNewSPRequestFromSMGASMS_New(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO,
				"STEP === NPAC Activate --> Performing NPAC activation for '" + sub + "' ===");

		SMGASMSPage SMGASMSPage = new SMGASMSPage();

		try {
			navigateToSMGSOASubscriptionPage();
			
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
			BaseSteps.Waits.waitGeneric(1000);
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
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Create New SP Create Request Failed: " + e);
			Validate.assertTrue(false, "Exception Occured: Create New SP Create Request Failed");
		}

	}
}
