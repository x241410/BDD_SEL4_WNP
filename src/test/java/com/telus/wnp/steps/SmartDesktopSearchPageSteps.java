package com.telus.wnp.steps;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.test.utils.Status;
import com.telus.wnp.pages.SmartDesktopDashboardPage;
import com.telus.wnp.pages.SmartDesktopSearchPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for smart desktop search page (Steps) > AUTHOR:
 * x241410
 ****************************************************************************
 */
public class SmartDesktopSearchPageSteps extends BaseSteps {

	/**
	 * This method is used to do a validation check for the current status of the
	 * Subscriber TN.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param ban
	 *            This is the subscriber ban.
	 * @param condition
	 *            This can be pre/post for the validation check.
	 * @return nothing.
	 */

	public static void verifySubscriberStatusFromSD(JSONObject testDataJson, String sub, String ban, String condition) {
		Reporting.logReporter(Status.INFO,
				"STEP === Smart Desktop Validation --> Verify that subscriber status is active ===");
		String expectedStatus = "";
		String actualStatus = "";

		try {

			SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();

			LoginPageSteps.appLogin_SD();
			SmartDesktopSearchPageSteps.verifySDHomePageIsDisplayed();

			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopSearchPage.searchByMethod,
					testDataJson.getString("SEARCHBY_PHONE_NUMBER"));

			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_ACTIVE");
				actualStatus = SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);
			} else if (condition.equalsIgnoreCase("inactive")) {
				SmartDesktopSearchPageSteps.searchPhoneNo(sub);
				expectedStatus = testDataJson.getString("NO_MATCHING_ACCOUNT_FOUND_ERR_MSG");
				actualStatus = SmartDesktopSearchPage.getNoAccountFoundMsg();
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_CANCELLED");
				actualStatus = SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
			Reporting.logReporter(Status.PASS, "=== SD Validation --> Sub Status is [" + actualStatus + "]===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG,
					"Unable to verify the status of the Subscriber from Smart desktop: " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to verify the status of the Subscriber from Smart desktop");
		}

	}

	/**
	 * This method is used to do a validation check for the current status of the
	 * Subscriber TN.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param ban
	 *            This is the subscriber ban.
	 * @param condition
	 *            This can be pre/post for the validation check.
	 * @return nothing.
	 */
	public static void verifyAnotherSubscriberStatus(JSONObject testDataJson, String sub, String ban,
			String condition) {
		Reporting.logReporter(Status.INFO,
				"STEP === Smart Desktop Validation --> Verify that subscriber status is active ===");
		String expectedStatus = "";
		String actualStatus = "";

		try {

			SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
			BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.searchPageLink);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopSearchPage.searchByMethod);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopSearchPage.searchByMethod,
					testDataJson.getString("SEARCHBY_PHONE_NUMBER"));

			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_ACTIVE");
				actualStatus = SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);
			} else if (condition.equalsIgnoreCase("inactive")) {
				SmartDesktopSearchPageSteps.searchPhoneNo(sub);
				expectedStatus = testDataJson.getString("NO_MATCHING_ACCOUNT_FOUND_ERR_MSG");
				actualStatus = SmartDesktopSearchPage.getNoAccountFoundMsg();
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_CANCELLED");
				actualStatus = SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
			Reporting.logReporter(Status.PASS, "=== SD Validation --> Sub Status is [" + actualStatus + "]===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG,
					"Unable to verify the status of the Subscriber from Smart desktop: " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to verify the status of the Subscriber from Smart desktop");
		}

	}

	/**
	 * Method Description: The purpose of this method is to perform multiban
	 * validation check
	 * 
	 * @param testDataJson
	 * @param sub1
	 * @param sub2
	 * @param ban
	 * @param condition
	 */
	public static void multiBanValidationCheck_SD(JSONObject testDataJson, String sub1, String sub2, String ban,
			String condition) {
		Reporting.logReporter(Status.INFO, "STEP === SD Validation --> Sub Status check in Smart Desktop ===");

		try {

			LoginPageSteps.appLogin_SD();
			SmartDesktopSearchPageSteps.verifySDHomePageIsDisplayed();
			SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();

			verifySubscriberStatusForMultiBanFromSD(testDataJson, sub1, ban, condition);

			BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.searchPageLink);
			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

			verifySubscriberStatusForMultiBanFromSD(testDataJson, sub2, ban, condition);

		} catch (JSONException e) {
			Reporting.logReporter(Status.ERROR, "Unable to verify the status of the Subscriber from Smart desktop" + e);
			e.printStackTrace();
		}

	}

	/**
	 * Method Description: The purpose of this method is to perform subscriber
	 * status check for multiban
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 * @param condition
	 */
	public static void verifySubscriberStatusForMultiBanFromSD(JSONObject testDataJson, String sub, String ban,
			String condition) {

		JSONObject sdConstantsObj = testDataJson.getJSONObject("SMART_DESKTOP_CONSTANTS");
		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		String expectedStatus = "";
		String actualStatus = "";
		if (condition.equalsIgnoreCase("active")) {
			expectedStatus = sdConstantsObj.getString("SUB_STATUS_ACTIVE");
			actualStatus = SmartDesktopSearchPageSteps.getSubStatus_SD(sub, ban);
		} else if (condition.equalsIgnoreCase("inactive")) {
			SmartDesktopSearchPageSteps.searchPhoneNo(sub);
			expectedStatus = sdConstantsObj.getString("NO_MATCHING_ACCOUNT_FOUND_ERR_MSG");

			actualStatus = SmartDesktopSearchPage.getNoAccountFoundMsg();
		}
		Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
	}

	/**
	 * This method is used to do enter TN.
	 * 
	 * @param sub
	 *            This is the subscriber TN.
	 * @return nothing.
	 */
	public static void enterPhoneNo(String phoneNo) {
		Reporting.logReporter(Status.INFO, "STEP === Smart Desktop Search Page --> Enter Phone Number : " + phoneNo);
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		BaseSteps.SendKeys.sendKey(smartDesktopSearchPage.phoneNumber, phoneNo);

	}

	/**
	 * This method is used to do enter ban of subscriber.
	 * 
	 * @param ban
	 *            This is the subscriber ban.
	 * @return nothing.
	 */
	public static void enterAccountNo(String accountNo) {
		Reporting.logReporter(Status.INFO,
				"STEP === Smart Desktop Search Page --> Enter Account Number : " + accountNo);
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		selectSearchtype();
		BaseSteps.SendKeys.sendKey(smartDesktopSearchPage.accountNumber, accountNo);

	}

	/**
	 * This method is used to do select the search type.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void selectSearchtype() {
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		BaseSteps.Dropdowns.selectByVisibleText(smartDesktopSearchPage.selectType, "Account Number");

	}

	/**
	 * This method is used to do perform the submit.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void clickSubmit() {
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		BaseSteps.Clicks.clickElement(smartDesktopSearchPage.submit);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * This method is used to do perform the submit.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void clickpSubmit() {
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		System.out.println("what is this ");
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		System.out.println(smartDesktopSearchPage.psubmit);
		BaseSteps.Clicks.clickElement(smartDesktopSearchPage.psubmit);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * This method is used to do click on change Number link.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void clickChangeNumber() {
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		BaseSteps.Clicks.clickElement(smartDesktopSearchPage.changeNumber);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * 
	 * @param accountNo
	 */
	public static void enterAcno(String accountNo) {
		Reporting.logReporter(Status.INFO,
				"STEP === Smart Desktop Search Page --> Enter Account Number : " + accountNo);
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

		BaseSteps.SendKeys.sendKey(smartDesktopSearchPage.paccountNumber, accountNo);
		System.out.println("Accno done1");

		BaseSteps.SendKeys.sendKey(smartDesktopSearchPage.pcaccountNumber, accountNo);
		BaseSteps.SendKeys.sendKey(smartDesktopSearchPage.alternateContactNumber, "4162156789");
		BaseSteps.Clicks.clickElement(smartDesktopSearchPage.hasConfirmed);
		BaseSteps.Clicks.clickElement(smartDesktopSearchPage.hasAuthorized);
	}

	/**
	 * This method is used to do perform search for the phone number.
	 * 
	 * @param phoneNo
	 *            This is the phone number to be searched
	 * @return nothing.
	 */
	public static void searchPhoneNo(String phoneNo) {

		Reporting.logReporter(Status.INFO, "STEP === Smart Desktop Search Page --> Enter Phone Number : " + phoneNo);
		SmartDesktopSearchPage smartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(smartDesktopSearchPage.phoneNumber);
		BaseSteps.Waits.waitForElementToBeClickableLongWait(smartDesktopSearchPage.phoneNumber);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		BaseSteps.SendKeys.clearFieldAndSendKeys(smartDesktopSearchPage.phoneNumber, phoneNo);

		Validate.takeStepScreenShot("Entered TN before Search");
		BaseSteps.Waits.waitForElementToBeClickableLongWait(smartDesktopSearchPage.submit);
		BaseSteps.Clicks.clickElement(smartDesktopSearchPage.submit);
		
		Validate.takeStepFullScreenShot("Search Results", Status.INFO);
	}

	/**
	 * Method Description: The purpose of this method is to perform search for a
	 * particular subscriber
	 * 
	 * @param phoneNo
	 * @param accountNumber
	 */
	public static void searchPhoneNo(String phoneNo, String accountNumber) {
		enterPhoneNo(phoneNo);
		clickSubmit();
		/***
		 * Need to remove the below step when we are able to create own test data.
		 */
		BaseSteps.Waits.waitGeneric(2000);
		BaseSteps.Finds.findElement(By.xpath("//a[contains(text(), '" + accountNumber + "')]")).click();
	}

	/**
	 * Method Description: The purpose of this method is to search for an account
	 * number
	 * 
	 * @param accountNo
	 */
	public static void searchAccountNo(String accountNo) {

		enterAccountNo(accountNo);
		clickSubmit();

	}

	/**
	 * Method Description: The purpose of this method is to get the status
	 * 
	 * @param expectedStatus
	 */
	public static void geStatus(String expectedStatus) {
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();
		String actualStatus = SmartDesktopDashboardPage.getTNStatus();
		Assert.assertEquals(actualStatus, expectedStatus);
		System.out.println("Assert passed");
	}

	/**
	 * This method is used to do verify home page is displayed after successful
	 * login into smart desktop application.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void verifySDHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> SD homepage is displayed after successful login ");

		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		boolean actual = SmartDesktopSearchPage.isHomePageDisplayed();
		System.out.println("===========> SD");
		Validate.assertEquals(actual, true, "Unable to login into SD.", false);
	}

	/**
	 * This method is used to do verify TN status.
	 * 
	 * @param sub
	 *            This is the subscriber TN
	 * @param status
	 *            This is the expected status
	 * @param ban
	 *            This is the ban(account number)
	 * @return nothing.
	 */
	public static void doVerifyTNStatus_SD(String sub, String status, String ban) {
		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();

		SmartDesktopSearchPageSteps.searchPhoneNo(sub);
		WebDriver ses = WebDriverSteps.getWebDriverSession();
		BaseSteps.Waits.waitGeneric(3000);
		if ((ses.findElements(By.xpath("//div/a[1]/span[@class='detail2' and 1]"))).size() != 0) {

			System.out.println("Search phone number");

		} else {
			System.out.println("Search phone number by clicking BAN");

			boolean actualBan = SmartDesktopSearchPage.banIsPresent();
			String banStatus = SmartDesktopSearchPage.getBanStatus();
			if (actualBan && banStatus.equalsIgnoreCase("open")) { // Issue is there, if condition needs to be corrected

				BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.latestBan);
			}

		}
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		geStatus(status);
	}

	/**
	 * This method is used to do perform search for the phone number.
	 * 
	 * @param sub
	 *            This is the subscriber TN
	 * @param status
	 *            This is the expected status
	 * @param ban
	 *            This is the ban(account number)
	 * @return return the subscriber TN status
	 */
	public static String getSubStatus_SD(String sub, String ban) {

		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();

		SmartDesktopSearchPageSteps.searchPhoneNo(sub);
		BaseSteps.Waits.waitGeneric(1000);

		String stat;
		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.tnStatus);
			stat = SmartDesktopDashboardPage.getTNStatus();

		} catch (Exception e) {
			System.out.println("Search phone number by clicking BAN");

			boolean banIsPresent = SmartDesktopSearchPage.banIsPresent();
			if (banIsPresent) {
				BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.latestBan);
			}
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.tnStatus);
			stat = SmartDesktopDashboardPage.getTNStatus();

			return stat;
		}

		return stat;

	}
	
	/**
	 * This method is used to do a validation check for the current status of the
	 * Subscriber TN.
	 * 
	 * @param testDataJson
	 *            This is the input test data json object.
	 * @param sub
	 *            This is the subscriber TN.
	 * @param ban
	 *            This is the subscriber ban.
	 * @param condition
	 *            This can be pre/post for the validation check.
	 * @return nothing.
	 */
	public static void verifySubscriberStatusForOSP(JSONObject testDataJson, String sub, String ban,
			String condition) {
		Reporting.logReporter(Status.INFO,
				"STEP === Smart Desktop Validation --> Verify that subscriber status is active ===");
		String expectedStatus = "";
		String actualStatus = "";

		try {

			SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
			BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.searchPageLink);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopSearchPage.searchByMethod);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopSearchPage.searchByMethod,
					testDataJson.getString("SEARCHBY_PHONE_NUMBER"));

			if (condition.equalsIgnoreCase("active")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_ACTIVE");
				actualStatus = SmartDesktopSearchPageSteps.getAnotherSubscriberStatus(sub, ban);
			} else if (condition.equalsIgnoreCase("inactive")) {
				SmartDesktopSearchPageSteps.searchPhoneNo(sub);
				expectedStatus = testDataJson.getString("NO_MATCHING_ACCOUNT_FOUND_ERR_MSG");
				actualStatus = SmartDesktopSearchPage.getNoAccountFoundMsg();
			} else if (condition.equalsIgnoreCase("cancelled")) {
				expectedStatus = testDataJson.getString("SUB_STATUS_CANCELLED");
				actualStatus = SmartDesktopSearchPageSteps.getAnotherSubscriberStatus(sub, ban);
			}

			Validate.assertEquals(actualStatus, expectedStatus, "Subscriber status is not as expected", false);
			Reporting.logReporter(Status.PASS, "=== SD Validation --> Sub Status is [" + actualStatus + "]===");

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG,
					"Unable to verify the status of the Subscriber from Smart desktop: " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to verify the status of the Subscriber from Smart desktop");
		}

	}

	/**
	 * This method is used to do perform search for the phone number.
	 * 
	 * @param sub
	 *            This is the subscriber TN
	 * @param status
	 *            This is the expected status
	 * @param ban
	 *            This is the ban(account number)
	 * @return return the subscriber TN status
	 */
	public static String getAnotherSubscriberStatus(String sub, String ban) {

		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		SmartDesktopDashboardPage SmartDesktopDashboardPage = new SmartDesktopDashboardPage();

		SmartDesktopSearchPageSteps.searchPhoneNo(sub);
		BaseSteps.Waits.waitGeneric(1000);

		String stat;
		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.tnStatus);
			stat = SmartDesktopDashboardPage.getTNStatus();

		} catch (Exception e) {
			System.out.println("Search phone number by clicking BAN");

			boolean banIsPresent = SmartDesktopSearchPage.banIsPresent();
			if (banIsPresent) {
				BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.clickSpecificBan(ban));
			}
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopDashboardPage.tnStatus);
			stat = SmartDesktopDashboardPage.getTNStatus();

			return stat;
		}

		return stat;

	}
	
	public static void verifySubscriberMigrationNotes(String expectedText) {
		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopSearchPage.migrateToPostPaidLink);
		BaseSteps.Debugs.scrollToElement(SmartDesktopSearchPage.migrateToPostPaidLink);
		BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.migrateToPostPaidLink);
		
		Validate.takeStepScreenShot("Migration Details");
		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopSearchPage.systemTextMigration);
		
		String actualSystemText = SmartDesktopSearchPage.getSystemText();
		
		Validate.assertContains(actualSystemText, expectedText,false);
		
		String reqCreatedTime = SmartDesktopSearchPage.getMigrationReqCreatedTime();
		String currentDate = GenericUtils.getSystemDateInMMDDYYYY();
		
		Validate.assertContains(reqCreatedTime, currentDate, false);

		
	}
	
	public static void verifySubscriberMigrationNotesForPostToPre(String expectedText) {
		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopSearchPage.migrateToPrePaidLink);
		BaseSteps.Debugs.scrollToElement(SmartDesktopSearchPage.migrateToPrePaidLink);
		BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.migrateToPrePaidLink);
		
		Validate.takeStepScreenShot("Migration Details");
		BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopSearchPage.systemTextMigrationPostToPre);
		
		String actualSystemText = SmartDesktopSearchPage.getSystemTextPostToPre();
		
		Validate.assertContains(actualSystemText, expectedText,false);
		
		String reqCreatedTime = SmartDesktopSearchPage.getMigrationReqCreatedTime();
		String currentDate = GenericUtils.getSystemDateInMMDDYYYY_Nodes();
		
		Validate.assertContains(reqCreatedTime, currentDate, false);

		
	}

	public static void scrollToMobilePortingStatusImage() {
		SmartDesktopSearchPage SmartDesktopSearchPage = new SmartDesktopSearchPage();
		BaseSteps.Debugs.scrollToElement(SmartDesktopSearchPage.pretopostimg);
		BaseSteps.Clicks.clickElement(SmartDesktopSearchPage.pretopostimg);
		BaseSteps.Waits.waitGeneric(1000);
		Validate.takeStepScreenShot("Prepaid to postpaid");
	}
}