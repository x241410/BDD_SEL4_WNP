package com.telus.wnp.steps;

import org.json.JSONObject;
import org.openqa.selenium.By;

import com.test.utils.Status;
import com.telus.wnp.pages.CODASearchPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for CODA Search page Steps > AUTHOR: x241410
 ****************************************************************************
 */

public class CODASearchPageSteps extends BaseSteps {

	/**
	 * This method is used to do verify subscriber status from CODA
	 * 
	 * @param testDataJson
	 *            This is input test data json object
	 * @param sub
	 *            This is subscriber number
	 * @param condition
	 *            This is to specify pre or post validation condition
	 * @return nothing.
	 */
	public static void validationCheck_CODA(JSONObject testDataJson, String sub, String condition) {

		Reporting.logReporter(Status.INFO, "STEP === CODA Validation --> Sub Status check in CODA ===");

		try {
			JSONObject codaConstantsObj = testDataJson.getJSONObject("CODA_CONSTANTS");
			LoginPageSteps.appLogin_CODA();
			CODASearchPageSteps.verifyCODAHomePageIsDisplayed();

			selectEnvironment();
			String expectedStatus = "";

			if (condition.contains("preValidation"))
				expectedStatus = codaConstantsObj.getString("ACTIVE_STATUS");
			else if (condition.contains("postValidation"))
				expectedStatus = codaConstantsObj.getString("INACTIVE_STATUS");

			CODASearchPage CodaSearchPage = new CODASearchPage();

			searchPhoneNo(testDataJson, sub);
			String actualStatus = CodaSearchPage.getSubscriberStatus();
			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from CODA" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to perform validation check in coda");
		}

	}

	/**
	 *
	 * Method Description: Verify Subscriber Status from CODA
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifySubscriberStatusFromCODA(JSONObject testDataJson, String sub, String condition) {

		Reporting.logReporter(Status.INFO,
				"STEP === CODA Validation --> Verify that subscriber status is ===" + condition);

		try {
			LoginPageSteps.appLogin_CODA();

			CODASearchPageSteps.verifyCODAHomePageIsDisplayed();
			BaseSteps.Waits.waitGeneric(1000);
			selectEnvironment();
			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			CODASearchPage CodaSearchPage = new CODASearchPage();

			searchPhoneNo(testDataJson, sub);

			if (CodaSearchPage.checkMultipleRecordsPresence()) {
				BaseSteps.Clicks.clickElement(CodaSearchPage.firstRowFromSearchRecords);
			}

			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			String expectedStatus = "";
			String actualStatus = "";

			BaseSteps.Waits.waitGeneric(3000);
			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("ACTIVE_STATUS");
				actualStatus = CodaSearchPage.getSubscriberStatus();
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("CANCELLED_STATUS");
				actualStatus = CodaSearchPage.getSubscriberCancelledStatus();
			} else if (condition.equalsIgnoreCase("inactive")) {
				expectedStatus = testDataJson.getString("INACTIVE_SUB_MSG");
				actualStatus = CodaSearchPage.getNoSearchResultsMsg();
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
			Reporting.logReporter(Status.PASS,
					"=== CODA Validation --> Subscriber Status is [" + actualStatus + "]===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from CODA: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify subscriber status from CODA");
		}

	}

	/**
	 * Method Description: Method to verify PortOut Indicator Value
	 * 
	 * @param testDataJson
	 * @param param
	 */
	public static void verifyPortOutIndicatorValue(JSONObject testDataJson, String param) {

		CODASearchPage CodaSearchPage = new CODASearchPage();
		BaseSteps.Debugs.scrollToElement(CodaSearchPage.portOutIndicator);
		String actualPortOutIndicatorValue = CodaSearchPage.getPortOutIndicatorValue();
		Validate.assertEquals(actualPortOutIndicatorValue, param, "Portout indicator is not as expected", false);

	}

	/**
	 *
	 * Method Description: Multiban validation check
	 *
	 * @param testDataJson
	 * @param sub1
	 * @param sub2
	 * @param condition
	 */
	public static void multiBanValidationCheck_CODA(JSONObject testDataJson, String sub1, String sub2,
			String condition) {

		Reporting.logReporter(Status.INFO, "STEP === CODA Validation --> Sub Status check in CODA ===");

		try {

			LoginPageSteps.appLogin_CODA();
			CODASearchPageSteps.verifyCODAHomePageIsDisplayed();

			selectEnvironment();
			verifySubscriberStatusForMultiBanFromCODA(testDataJson, sub1, condition);

			CODASearchPage CodaSearchPage = new CODASearchPage();
			BaseSteps.Clicks.clickElement(CodaSearchPage.searchPageLink);

			verifySubscriberStatusForMultiBanFromCODA(testDataJson, sub2, condition);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from CODA" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify subscriber status from CODA");
		}

	}

	/**
	 * Method Description: Verify Subscriber Status for Multiban from CODA
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifySubscriberStatusForMultiBanFromCODA(JSONObject testDataJson, String sub,
			String condition) {
		JSONObject codaConstantsObj = testDataJson.getJSONObject("CODA_CONSTANTS");
		String expectedStatus = "";
		String actualStatus = "";

		searchPhoneNo(testDataJson, sub);

		CODASearchPage CodaSearchPage = new CODASearchPage();

		if (condition.equalsIgnoreCase("active")) {
			expectedStatus = codaConstantsObj.getString("ACTIVE_STATUS");
			actualStatus = CodaSearchPage.getSubscriberStatus();
		} else if (condition.equalsIgnoreCase("inactive")) {
			expectedStatus = codaConstantsObj.getString("INACTIVE_SUB_MSG");
			actualStatus = CodaSearchPage.getNoSearchResultsMsg();
		}

		Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);

	}

	/**
	 * This method is used to do verify home page is displayed after successful
	 * login into CODA application.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void verifyCODAHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> Verify CODA homepage is displayed after successful login ");

		CODASearchPage CODASearchPage = new CODASearchPage();

		BaseSteps.Waits.waitForElementVisibility(CODASearchPage.homePageHelpText, 90);
		boolean actual = CODASearchPage.isHomePageDisplayed();
		System.out.println("===========> CODA");
		Validate.assertEquals(actual, true, "Unable to login into CODA.", false);
	}

	/**
	 * Method Description: Select the respective Test environment based on the
	 * execution environment
	 */
	public static void selectEnvironment() {

		String env = SystemProperties.EXECUTION_ENVIRONMENT;
		Reporting.logReporter(Status.INFO,
				"STEP === Select Environment --> Selecting the execution environment as : " + env);
		CODASearchPage CodaSearchPage = new CODASearchPage();
		String reqEnvDynamicXpath = "//span[contains(text(), '" + env.toLowerCase() + "')]";

		if (!CodaSearchPage.getSelectedEnvText().equalsIgnoreCase(env)) {
			BaseSteps.Clicks.clickElement(CodaSearchPage.selectedEnvironment);
			BaseSteps.Waits.waitGeneric(2000);
			WebDriverSession.getWebDriverSession().findElement(By.xpath(reqEnvDynamicXpath)).click();
		}

		else
			BaseSteps.Clicks.clickElement(CodaSearchPage.selectedEnvironment);
	}

	/**
	 * Method Description: Perform search for particular subscriber from CODA
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void searchPhoneNo(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO,
				"STEP === CODA Search Page --> Enter Phone Number and click submit : " + sub);
		String searchByType = testDataJson.getString("SEARCH_BY_PHONE_NUMBER");

		CODASearchPage CODASearchPage = new CODASearchPage();
		BaseSteps.Dropdowns.selectByValue(CODASearchPage.searchByField, searchByType);
		BaseSteps.SendKeys.sendKey(CODASearchPage.searchByValue, sub);

		Validate.takeStepScreenShot("Entered TN for Search");
		BaseSteps.Clicks.clickElement(CODASearchPage.searchBtn);

	}

	/**
	 * Method Description: The purpose of this method is to verify sub order status
	 * based on state
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param state
	 */
	public static void verifySubOrderStatus(JSONObject testDataJson, String sub, String state) {

		Reporting.logReporter(Status.INFO, "STEP === CODA Orders Page --> Verify Sub Orders State for : " + sub);
		try {
			CODASearchPage CodaSearchPage = new CODASearchPage();
			BaseSteps.Clicks.clickElement(CodaSearchPage.ordersTabLink);

			String expectedOrderState = testDataJson.getString("ORDER_STATE");

			String actualOrderState = CodaSearchPage.getOrderStatus();
			Validate.assertEquals(actualOrderState, expectedOrderState, "Subscriber order status is not as expected",
					false);
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify order state from CODA: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify order state from CODA");

		}

	}

	/**
	 * Method Description: The purpose of this method is to verify sub order status
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifySubOrderStatus(JSONObject testDataJson, String sub) {
		Reporting.logReporter(Status.INFO, "STEP === CODA Validation --> Verify subscriber order status ===");
		try {
			CODASearchPage CodaSearchPage = new CODASearchPage();
			int actualPendingOrdersCount = CodaSearchPage.getPendingOrderCount();

			BaseSteps.Clicks.clickElement(CodaSearchPage.ordersTabLink);

			String expectedOrderState = testDataJson.getString("ORDER_STATE");
			int expectedPendingOrdersCount = testDataJson.getInt("PENIDNG_ORDERS_COUNT");

			String actualOrderState = CodaSearchPage.getOrderStatus();

			Validate.assertEquals(actualOrderState, expectedOrderState,
					"Subscriber order status is not as expected. expectedCount = '" + expectedOrderState
							+ "' actualCount = '" + actualOrderState + "'",
					false);
			Validate.assertEquals(actualPendingOrdersCount, expectedPendingOrdersCount,
					"Subscriber pending order count is not as expected. expectedCount = '" + expectedPendingOrdersCount
							+ "' actualCount = '" + actualPendingOrdersCount + "'",
					false);
			Reporting.logReporter(Status.PASS, "=== CODA Validation --> Subscriber Order Status is [" + actualOrderState
					+ " and pending order count is " + actualPendingOrdersCount + "]===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify order status from CODA: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify order status from CODA");

		}

	}

	/**
	 * Method Description: This Method is used to verify Current Brand of the
	 * Subscriber
	 * 
	 * @param expectedBrand
	 */
	public static void verifyCurrentBrandFromCODA(String expectedBrand) {
		CODASearchPage CODASearchPage = new CODASearchPage();
		String actualBrand = CODASearchPage.getBrandText();

		Validate.assertContains(actualBrand.toUpperCase(), expectedBrand.toUpperCase(),
				"Subscriber Brand is not as Expected", false);
	}

	/**
	 * Method Description: This Method is used to verify Billing Type of the
	 * Subscriber
	 * 
	 * @param expectedBrand
	 */
	public static void verifyConsumerTypeFromCODA(String expectedBillingType) {
		CODASearchPage CODASearchPage = new CODASearchPage();
		String actualBillingType = CODASearchPage.getConsumerTypeText();

		Validate.assertEquals(actualBillingType, expectedBillingType, "Consumer Type Mismatch", false);
	}

	/**
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifyAnotherSubStatusFromCODA(JSONObject testDataJson, String sub, String condition) {
		try {
			CODASearchPage CodaSearchPage = new CODASearchPage();

			BaseSteps.Clicks.clickElement(CodaSearchPage.searchPageLink);
			searchPhoneNo(testDataJson, sub);

			if (CodaSearchPage.checkMultipleRecordsPresence()) {
				BaseSteps.Clicks.clickElement(CodaSearchPage.firstRowFromSearchRecords);
			}

			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			String expectedStatus = "";
			String actualStatus = "";

			BaseSteps.Waits.waitGeneric(3000);
			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("ACTIVE_STATUS");
				actualStatus = CodaSearchPage.getSubscriberStatus();
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("CANCELLED_STATUS");
				actualStatus = CodaSearchPage.getSubscriberCancelledStatus();
			} else if (condition.equalsIgnoreCase("inactive")) {
				expectedStatus = testDataJson.getString("INACTIVE_SUB_MSG");
				actualStatus = CodaSearchPage.getNoSearchResultsMsg();
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
			Reporting.logReporter(Status.PASS,
					"=== CODA Validation --> Subscriber Status is [" + actualStatus + "]===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from CODA: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify subscriber status from CODA");
		}
	}

	public static void verifyNoPendingOrderAndErrors() {

		Reporting.logReporter(Status.INFO, "STEP === CODA Validation --> Verify Pending Orders and Errors Status ===");
		try {
			CODASearchPage CodaSearchPage = new CODASearchPage();

			int actualPendingOrdersCount = Integer.parseInt(CodaSearchPage.pendingOrderCount.getText());
			int actualTaskErrorsCount = Integer.parseInt(CodaSearchPage.taskErrorCount.getText());
			int actualValidationErrorsCount = Integer.parseInt(CodaSearchPage.validationErrorCount.getText());

			BaseSteps.Clicks.clickElement(CodaSearchPage.ordersTabLink);

			String actualOrderState = CodaSearchPage.getOrderStatus();

			Validate.assertEquals(actualOrderState, "complete",
					"Subscriber order status is not as expected. expectedStatus = complete'" + "' actualStatus = '"
							+ actualOrderState + "'",
					false);

			Reporting.logReporter(Status.INFO, "Orders Status: " + actualOrderState);
			Reporting.logReporter(Status.INFO, "Pending Orders count: " + actualPendingOrdersCount);
			Reporting.logReporter(Status.INFO, "Task Errors count: " + actualTaskErrorsCount);
			Reporting.logReporter(Status.INFO, "Validation Errors count: " + actualValidationErrorsCount);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify order status from CODA: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify order status from CODA");

		}
	}

	/**
	 *
	 * Method Description: Verify Subscriber Status from CODA
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param condition
	 */
	public static void verifySubscriberStatusFromCODAForPostValidation(JSONObject testDataJson, String sub,
			String condition) {

		Reporting.logReporter(Status.INFO,
				"STEP === CODA Validation --> Verify that subscriber status is ===" + condition);

		try {
			LoginPageSteps.appLogin_CODA();

			CODASearchPageSteps.verifyCODAHomePageIsDisplayed();
			BaseSteps.Waits.waitGeneric(1000);
			selectEnvironment();
			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			CODASearchPage CodaSearchPage = new CODASearchPage();

			searchPhoneNo(testDataJson, sub);

			if (CodaSearchPage.checkMultipleRecordsPresence()) {
				BaseSteps.Clicks.clickElement(CodaSearchPage.firstRowFromSearchRecords);
			}

			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			String expectedStatus = "";
			String actualStatus = "";

			BaseSteps.Waits.waitGeneric(3000);
			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("ACTIVE_STATUS");
				actualStatus = CodaSearchPage.getSubscriberStatus();
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("CANCELLED_STATUS");
				actualStatus = CodaSearchPage.getSubscriberCancelledStatus();
			}

			long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
			long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (30 * 1000);

			while (startTime < endTime) {

				if (actualStatus.equalsIgnoreCase(expectedStatus)) {
					break;
				}

/*				BaseSteps.Waits.waitForElementToBeClickableLongWait(CodaSearchPage.searchBtnFromServicesPage);
				BaseSteps.Clicks.clickElement(CodaSearchPage.searchBtnFromServicesPage);*/
				
				BaseSteps.Clicks.clickElement(CodaSearchPage.searchPageLink);
				searchPhoneNo(testDataJson, sub);

				if (CodaSearchPage.checkMultipleRecordsPresence()) {
					BaseSteps.Clicks.clickElement(CodaSearchPage.firstRowFromSearchRecords);
				}
				
				if (condition.equalsIgnoreCase("active")) {
					actualStatus = CodaSearchPage.getSubscriberStatus();
				} else if (condition.equalsIgnoreCase("cancelled")) {
					actualStatus = CodaSearchPage.getSubscriberCancelledStatus();
				} 
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
			Reporting.logReporter(Status.PASS,
					"=== CODA Validation --> Subscriber Status is [" + actualStatus + "]===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the status of the Subscriber from CODA: " + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify subscriber status from CODA");
		}

	}
}
