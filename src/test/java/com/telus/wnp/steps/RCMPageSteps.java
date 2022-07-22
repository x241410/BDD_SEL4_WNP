package com.telus.wnp.steps;

import org.json.JSONObject;

import com.test.utils.Status;
import com.telus.wnp.pages.RCMPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for RCM page (Steps) > AUTHOR: x241410
 ****************************************************************************
 */
public class RCMPageSteps extends BaseSteps {

	/**
	 * Method Description: The purpose of this method is to do a validation check
	 * from RCM
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void validationCheck_RCM(JSONObject testDataJson, String sub, String condition) {

		Reporting.logReporter(Status.INFO, "STEP === RCM Validation --> Sub Provisional Status check in RCM ===");

		try {

			LoginPageSteps.appLogin_RCM();
			RCMPageSteps.verifyRCMHomePageIsDisplayed();

			JSONObject sdConstantsObj = testDataJson.getJSONObject("RCM_CONSTANTS");
			String resourceType = sdConstantsObj.getString("RESOURCE_TYPE_UMSISDN");
			String expectedStatus = "";

			if (condition.contains("preValidation"))
				expectedStatus = sdConstantsObj.getString("SUB_STATUS_AI");
			else if (condition.contains("postValidation"))
				expectedStatus = sdConstantsObj.getString("SUB_STATUS_AG");

			String actualStatus = RCMPageSteps.getProvisioningTNStatus_RCM(sub, resourceType);

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber provisioning status is not as expected",
					false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from RCM " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify the status of the Subscriber from RCM");
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify subscriber status
	 * from RCM
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifySubscriberStatusFromRCM(JSONObject testDataJson, String sub, String condition) {

		Reporting.logReporter(Status.INFO,
				"STEP === RCM Validation --> Verify that subscriber provisioning status ===");

		try {
			LoginPageSteps.appLogin_RCM();
			RCMPageSteps.verifyRCMHomePageIsDisplayed();

			String resourceType = testDataJson.getString("RESOURCE_TYPE_UMSISDN");
			RCMPage RCMPage = new RCMPage();
			String expectedStatus = "";
			String actualStatus = "";

			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_AI");
				actualStatus = RCMPageSteps.getProvisioningTNStatus_RCM(sub, resourceType);
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_AG");
				actualStatus = RCMPageSteps.getProvisioningTNStatus_RCM(sub, resourceType);
			} else if (condition.equalsIgnoreCase("available")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_AA");
				actualStatus = RCMPageSteps.getProvisioningTNStatus_RCM(sub, resourceType);
			} else if (condition.equalsIgnoreCase("inactive")) {
				expectedStatus = testDataJson.getString("NO_SEARCH_RESULTS_FOUND_ERR_MSG");
				BaseSteps.Clicks.clickElement(RCMPage.inventory);
				RCMPageSteps.enterRequiredDetailsForTNSearchInRCM(sub, resourceType);
				actualStatus = RCMPage.getNoSearchResultFoundMsg();
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber provisioning status is not as expected",
					false);
			Reporting.logReporter(Status.PASS,
					"=== RCM Validation --> Sub Provisional Status is [" + actualStatus + "]===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from RCM " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify the status of the Subscriber from RCM");
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify Porting status
	 * 
	 * @param testDataJson
	 * @param param
	 */
	public static void verifyPortingStatusFromRCM(JSONObject testDataJson, String param) {
		RCMPage RCMPage = new RCMPage();
		String expectedPortingStatus = null;
		if (param.equalsIgnoreCase("poc")) {
			expectedPortingStatus = testDataJson.getString("EXPECTED_PORTING_STATUS_POC");
		} else if (param.equalsIgnoreCase("pos")) {
			expectedPortingStatus = testDataJson.getString("EXPECTED_PORTING_STATUS_POS");
		} else if (param.equalsIgnoreCase("pis")) {
			expectedPortingStatus = testDataJson.getString("EXPECTED_PORTING_STATUS_PIS");
		} else if (param.equalsIgnoreCase("pic")) {
			expectedPortingStatus = testDataJson.getString("EXPECTED_PORTING_STATUS_PIC");
		} else if (param.equalsIgnoreCase("pcp")) {
			expectedPortingStatus = testDataJson.getString("EXPECTED_PORTING_STATUS_PCP");
		}

		String actualPortingStatus = RCMPage.getPortingStatus();

		String expectedDate = GenericUtils.getSystemDateInMMDDYYYY_Nodes();
		String actualDate = RCMPage.getPortingDate();

		Validate.assertEquals(actualPortingStatus, expectedPortingStatus, "Porting Status Mistmatch", true);
		Validate.assertEquals(actualDate, expectedDate.replaceAll("/", "-"), "Porting Date Mistmatch", true);

	}

	/**
	 * Method Description: The purpose of this method is to perform multiban
	 * validation check
	 * 
	 * @param testDataJson
	 * @param sub1
	 * @param sub2
	 * @param condition
	 */
	public static void multiBanValidationCheck_RCM(JSONObject testDataJson, String sub1, String sub2,
			String condition) {

		Reporting.logReporter(Status.INFO,
				"STEP === RCM Validation --> Multiban Sub Provisional Status check in RCM ===");

		try {
			RCMPage RCMPage = new RCMPage();

			LoginPageSteps.appLogin_RCM();
			RCMPageSteps.verifyRCMHomePageIsDisplayed();
			BaseSteps.Clicks.clickElement(RCMPage.inventory);

			verifySubscriberStatusForMultiBanFromRCM(testDataJson, sub1, condition);

			BaseSteps.Clicks.clickElement(RCMPage.reset);
			verifySubscriberStatusForMultiBanFromRCM(testDataJson, sub2, condition);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify Multiban Sub Provisional Status from RCM " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify Multiban Sub Provisional Status from RCM ");
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify subscriber status
	 * for multiban
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifySubscriberStatusForMultiBanFromRCM(JSONObject testDataJson, String sub, String condition) {

		JSONObject rcmConstants = testDataJson.getJSONObject("RCM_CONSTANTS");
		String resourceType = rcmConstants.getString("RESOURCE_TYPE_UMSISDN");
		RCMPage RCMPage = new RCMPage();
		String expectedStatus = "";
		String actualStatus = "";

		enterRequiredDetailsForTNSearchInRCM(sub, resourceType);

		if (condition.equalsIgnoreCase("active")) {
			expectedStatus = rcmConstants.getString("SUB_STATUS_AI");
			actualStatus = RCMPage.provisioningStatus();
		} else if (condition.equalsIgnoreCase("cancelled")) {
			expectedStatus = rcmConstants.getString("SUB_STATUS_AG");
			actualStatus = RCMPage.provisioningStatus();
		} else if (condition.equalsIgnoreCase("inactive")) {
			expectedStatus = rcmConstants.getString("NO_SEARCH_RESULTS_FOUND_ERR_MSG");
			RCMPageSteps.enterRequiredDetailsForTNSearchInRCM(sub, resourceType);
			actualStatus = RCMPage.getNoSearchResultFoundMsg();
		}

		Validate.assertEquals(actualStatus, expectedStatus, "Subscriber provisioning status is not as expected", false);

	}

	/**
	 * Method Description: The purpose of this method is to get provisioning status
	 * from RCM
	 * 
	 * @param status
	 */
	public static void provisioningStatusValidation(String status) {
		RCMPage RCMPage = new RCMPage();
		String stat = RCMPage.provisioningStatus();

		Validate.assertEquals(status, stat, false);
		System.out.println("Assert passed");

	}

	/**
	 * Method Description: The purpose of this method is to verify if the home page
	 * is displayed
	 */
	public static void verifyRCMHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> RCM homepage is displayed after successful login ");
		RCMPage RCMPage = new RCMPage();
		boolean actual = RCMPage.isHomePageDisplayed();
		System.out.println("===========> RCM");
		Validate.assertEquals(actual, true, "Unable to login into RCM.", false);

	}

	/**
	 * Method Description: The purpose of this method is to verify TN status from
	 * RCM
	 * 
	 * @param sub
	 * @param resourceType
	 * @param expectedStatus
	 */
	public static void doVerifyTNStatus_RCM(String sub, String resourceType, String expectedStatus) {

		RCMPage RCMPage = new RCMPage();
		String npa = sub.substring(0, 3);
		String nxx = sub.substring(3, 7);
		String range = sub.substring(6);

		BaseSteps.Clicks.clickElement(RCMPage.inventory);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.npa, npa);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.nxx, nxx);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.rangeStart, range);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.rangeEnd, range);
		BaseSteps.Dropdowns.selectByValue(RCMPage.rtype, resourceType);
		BaseSteps.Clicks.clickElement(RCMPage.search);
		provisioningStatusValidation(expectedStatus);

	}

	/**
	 * Method Description: The purpose of this method is to get TN provisioning
	 * status from RCM
	 * 
	 * @param sub
	 * @param resourceType
	 * @return
	 */
	public static String getProvisioningTNStatus_RCM(String sub, String resourceType) {

		Reporting.logReporter(Status.INFO, "STEP === PAC Search Page --> Enter Phone Number and click Search : " + sub);
		String stat = "";

		try {
			RCMPage RCMPage = new RCMPage();
			BaseSteps.Clicks.clickElement(RCMPage.inventory);

			enterRequiredDetailsForTNSearchInRCM(sub, resourceType);

			stat = RCMPage.provisioningStatus();

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify provisioningStatus: " + e);
			return "";
		}

		return stat;
	}

	/**
	 * Method Description: The purpose of this method is to enter required details
	 * for TN Search
	 * 
	 * @param sub
	 * @param resourceType
	 */
	public static void enterRequiredDetailsForTNSearchInRCM(String sub, String resourceType) {

		String npa = sub.substring(0, 3);
		String nxx = sub.substring(3, 7);
		String range = sub.substring(6);

		RCMPage RCMPage = new RCMPage();

		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.npa, npa);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.nxx, nxx);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.rangeStart, range);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RCMPage.rangeEnd, range);
		BaseSteps.Dropdowns.selectByValue(RCMPage.rtype, resourceType);
		BaseSteps.Clicks.clickElement(RCMPage.search);
	}

	/**
	 * Method Description: The purpose of this method is to get provisioning status
	 * 
	 * @return
	 */
	public static String getProvisioningStatus_RCM() {

		RCMPage RCMPage = new RCMPage();
		String stat;
		try {
			stat = RCMPage.provisioningStatus();
		} catch (Exception e) {
			return "";
		}

		return stat;

	}

	/**
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifyAnotherSubscriberProvisioningStatus(JSONObject testDataJson, String sub,
			String condition) {

		RCMPage RCMPage = new RCMPage();
		String expectedStatus = "";
		String actualStatus = "";
		try {

			BaseSteps.Clicks.clickElement(RCMPage.reset);
			String resourceType = testDataJson.getString("RESOURCE_TYPE_UMSISDN");

			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_AI");
				enterRequiredDetailsForTNSearchInRCM(sub, resourceType);
				actualStatus = RCMPage.provisioningStatus();
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_AG");
				enterRequiredDetailsForTNSearchInRCM(sub, resourceType);
				actualStatus = RCMPage.provisioningStatus();
			} else if (condition.equalsIgnoreCase("available")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_AA");
				enterRequiredDetailsForTNSearchInRCM(sub, resourceType);
				actualStatus = RCMPage.provisioningStatus();
			} else if (condition.equalsIgnoreCase("inactive")) {
				expectedStatus = testDataJson.getString("NO_SEARCH_RESULTS_FOUND_ERR_MSG");
				RCMPageSteps.enterRequiredDetailsForTNSearchInRCM(sub, resourceType);
				actualStatus = RCMPage.getNoSearchResultFoundMsg();
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber provisioning status is not as expected",
					false);
			Reporting.logReporter(Status.PASS,
					"=== RCM Validation --> Sub Provisional Status is [" + actualStatus + "]===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from RCM " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify the status of the Subscriber from RCM");
		}

	}
}
