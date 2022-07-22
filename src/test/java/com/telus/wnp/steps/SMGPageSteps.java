package com.telus.wnp.steps;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for SMG page (Steps) 
 * > AUTHOR: x241410
 ****************************************************************************
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;

import com.test.utils.Status;
import com.telus.wnp.pages.SMGICPPortDetailsPage;
import com.telus.wnp.pages.SMGICPPortSelectionPage;
import com.telus.wnp.pages.SMGPage;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;

public class SMGPageSteps extends BaseSteps {

	public static String requestNumber;

	/**
	 * Method Description: The purpose of this method is to verify if SMG home page
	 * is displayed
	 */
	public static void verifySMGHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> SMG homepage is displayed after successful login ");
		SMGPage SMGPage = new SMGPage();
		boolean actual = SMGPage.isHomePageDisplayed();

		Validate.assertEquals(actual, true, "SMG Homepage is not displayed!", false);
	}

	/**
	 * Method Description: The purpose of this method is to verify Confirmed status
	 * from SMG
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifyConfirmedStatusInSMG(JSONObject testDataJson, String sub) {

		navigateToSMGICPPortSelectionPage();

		SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPage(sub, requestNumber);

		String actualText = SMGICPPortDetailsPageSteps.getResponseTypeFromICPPortDetails();

		System.out.println("Actual text from ICP Port Details: " + actualText);

		JSONObject smgConstantsObj = testDataJson.getJSONObject("SMG_CONSTANTS");
		String expectedText = smgConstantsObj.getString("SMG_STATUS_CONFIRMED");

		Validate.assertEquals(actualText, expectedText, "Actual and Expected Status mismatch", false);

	}

	/**
	 * Method Description: The purpose of this method is to verify confirmed status
	 * for Receive Cancellation
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifyConfirmedStatusInSMGForReceiveCancellation(JSONObject testDataJson, String sub) {

		try {

			navigateToSMGICPPortSelectionPage();

			SMGICPPortSelectionPage SMGICPPortSelectionPage = new SMGICPPortSelectionPage();

			JSONObject smgConstantsObj = testDataJson.getJSONObject("SMG_CONSTANTS");
			String portType = smgConstantsObj.getString("PORT_TYPE_SELECT_PORT_OUT_CANCEL_SVC_RECEIVED");
			String oldNetwork = smgConstantsObj.getString("TELUS_TU04_MOBILITY");

			String expectedText = smgConstantsObj.getString("SMG_STATUS_CONFIRMED");

			SMGICPPortSelectionPageSteps.queryFromICPPortSelectionPageFromOSP(sub, portType, oldNetwork);

			String actualText = SMGICPPortSelectionPage.getResponseStatusFromICPPortSelection();

			System.out.println("Actual text from ICP Port Details: " + actualText);

			Validate.assertEquals(actualText, expectedText, "Actual and Expected Status mismatch", false);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify SMG Status: " + e);
			Validate.takeStepFullScreenShot("Exception Occured", Status.ERROR);
		}

	}

	/**
	 * Method Description: Navigate to SMG ICP Port Details Page
	 */
	public static void navigateToSMGICPPortDetailsPage() {
		LoginPageSteps.appLogin_SMG();
		SMGPageSteps.verifySMGHomePageIsDisplayed();
		SMGPageSteps.clickCWNPTest();
		SMGPageSteps.clickPortCenterCT6();
		BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.maxUserLoggedInPopUpDisplayed();
		BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.clickICPPortDetails();
		BaseSteps.Windows.switchToNewWindow();

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
		BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.maxUserLoggedInPopUpDisplayed();
		BaseSteps.Windows.switchToNewWindow();
		SMGPageSteps.clickICPPortSelection();
		BaseSteps.Windows.switchToNewWindow();

	}

	/**
	 * Method Description: To check if maxUserLoggedInPopUpDisplayed
	 */
	public static void maxUserLoggedInPopUpDisplayed() {

		SMGPage SMGPage = new SMGPage();
		try {
			BaseSteps.Windows.switchToNewWindow();
			BaseSteps.Waits.waitGeneric(200);
			BaseSteps.Waits.waitForElementVisibility(SMGPage.multiLoginPopUpOkBtn, 10);
			if (SMGPage.ismultiLoginPopUpOkBtnDisplayed())
				BaseSteps.Clicks.clickElement(SMGPage.multiLoginPopUpOkBtn);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Multi user logged in pop-up not displayed");
		}

	}

	/**
	 * Method Description: To click CWNPTest
	 */
	public static void clickCWNPTest() {
		SMGPage SMGPage = new SMGPage();
		// BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Waits.waitForElementVisibility(SMGPage.CWNPTest, 40);
		BaseSteps.Clicks.clickElement(SMGPage.CWNPTest);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
	}

	/**
	 * Method Description: To click PortCenterCT6
	 */
	public static void clickPortCenterCT6() {
		SMGPage SMGPage = new SMGPage();
		// BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Waits.waitForElementVisibility(SMGPage.PortCenter, 20);
		BaseSteps.Clicks.clickElement(SMGPage.PortCenter);
	}

	/**
	 * Method Description: To click ICPPortDetails
	 */
	public static void clickICPPortDetails() {
		SMGPage SMGPage = new SMGPage();
		BaseSteps.Waits.waitForElementVisibility(SMGPage.Port_Request, 20);
		BaseSteps.Clicks.clickElement(SMGPage.Port_Request);
		// BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Waits.waitForElementVisibility(SMGPage.Port_Details, 10);
		BaseSteps.Clicks.clickElement(SMGPage.Port_Details);
	}

	/**
	 * Method Description: To click ICPPortSelection
	 */
	public static void clickICPPortSelection() {
		SMGPage SMGPage = new SMGPage();
		// BaseSteps.Waits.waitGeneric(100);
		BaseSteps.Waits.waitForElementVisibility(SMGPage.Port_Request, 10);
		BaseSteps.Clicks.clickElement(SMGPage.Port_Request);
		// BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Waits.waitForElementVisibility(SMGPage.Port_Selection, 10);
		BaseSteps.Clicks.clickElement(SMGPage.Port_Selection);
	}

	/**
	 * Method Description: To click SOASelection Option
	 */
	public static void clickSOASelection() {
		SMGPage SMGPage = new SMGPage();
		BaseSteps.Waits.waitForElementVisibility(SMGPage.Subscription_Administration, 20);
		BaseSteps.Clicks.clickElement(SMGPage.Subscription_Administration);
		// BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Waits.waitForElementVisibility(SMGPage.SOA_Selection, 10);
		BaseSteps.Clicks.clickElement(SMGPage.SOA_Selection);
	}

	/**
	 * 
	 * Method Description: To fillCreateCancelSVCRequestDetails
	 *
	 * @param testDataJson
	 */
	public static void fillCreateCancelSVCRequestDetails(JSONObject testDataJson) {

		JSONObject smgConstantsObj = testDataJson.getJSONObject("SMG_CONSTANTS");

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.select_Port_Details,
				smgConstantsObj.getString("CREATE_CANCEL_SVC_REQUEST"));
		// BaseSteps.Waits.waitGeneric(5000);
		BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.NNSP_ID, 10);
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.NNSP_ID,
				smgConstantsObj.getString("TELUS_TU04_MOBILITY"));
		// BaseSteps.Waits.waitGeneric(2000);
		BaseSteps.Waits.waitForElementVisibility(SMGICPPortDetailsPage.NLSP_ID, 10);
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.NLSP_ID,
				smgConstantsObj.getString("TELUS_TU04_MOBILITY"));
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.ONSP_ID,
				smgConstantsObj.getString("EXTERNAL_TU02_MOBILITY"));

		String desiredDueDT = getDDDTWithDelay(30);
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.desired_Due_Date, desiredDueDT);

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.requestor, smgConstantsObj.getString("REQUEST_CREATOR"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.contact_TN, smgConstantsObj.getString("CONTACT_TN"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.new_SP_contact, smgConstantsObj.getString("NEW_SP_CONTACT"));

	}

	/**
	 * /** Method Description: To fillRequestDataDetails from SMG page
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param portedTN
	 * @param accountNumber
	 */
	public static void fillRequestDataDetails(JSONObject testDataJson, String sub, String portedTN,
			String accountNumber) {

		JSONObject smgConstantsObj = testDataJson.getJSONObject("SMG_CONSTANTS");

		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.PORTED_TN, portedTN);

		if (smgConstantsObj.getBoolean("PORTED_TN_SELECT_CHECKBOX_STATUS")) {
			BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.portedTNCheckBox);
		}

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.ACCT_ID, accountNumber);

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.subName, smgConstantsObj.getString("SUBSCRIOBER_NAME"));

		String agencyDate = getCurrentDateInMMDDYYYY();
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.agency_date, agencyDate);

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.agency_Name, smgConstantsObj.getString("AGENCY_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.BILLFIRSTNM, smgConstantsObj.getString("BILLING_FIRST_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.BILLLASTNM, smgConstantsObj.getString("BILLING_LAST_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.BUSNM, smgConstantsObj.getString("BILLING_BUS_NAME"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.Street, smgConstantsObj.getString("BILLING_ADDRESS_STREET"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.city, smgConstantsObj.getString("BILLING_ADDRESS_CITY"));
		BaseSteps.Dropdowns.selectByValue(SMGICPPortDetailsPage.state,
				smgConstantsObj.getString("BILLING_ADDRESS_STATE"));
		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.zip_code,
				smgConstantsObj.getString("BILLING_ADDRESS_ZIPCODE"));

		BaseSteps.SendKeys.sendKey(SMGICPPortDetailsPage.remarks, smgConstantsObj.getString("INFORMATIONAL_REMARKS"));

	}

	/**
	 * Method Description: To getDDDTWithDelay
	 * 
	 * @param delayInMinutes
	 * @return Date/Time in MM/dd/yyyy HH:mm with delay
	 */

	public static String getDDDTWithDelay(int delayInMinutes) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		date = DateUtils.addMinutes(date, delayInMinutes);
		return dateFormat.format(date);
	}

	/**
	 * Method Description: To getCurrentDateInMMDDYYYY
	 * 
	 * @return date/time in MM/dd/yyyy HH:mm format
	 */

	public static String getCurrentDateInMMDDYYYY() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return dateFormat.format(date);
	}

	/**
	 * Method Description: To click save SMG from Page details
	 */
	public static void SMGSaveDetails() {
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();
		BaseSteps.Clicks.clickElement(SMGICPPortDetailsPage.save);
	}

	/**
	 * Method Description: To get the RequestNumberDetails
	 */
	public static String getRequestNumberDetails() {
		SMGICPPortDetailsPage SMGICPPortDetailsPage = new SMGICPPortDetailsPage();

		return (String) ((JavascriptExecutor) WebDriverSession.getWebDriverSession())
				.executeScript("return jQuery(arguments[0]).text();", SMGICPPortDetailsPage.requestNumber);
	}
}
