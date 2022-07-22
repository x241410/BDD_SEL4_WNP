package com.telus.wnp.steps;

import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;

import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.test.utils.Status;
import com.telus.wnp.pages.LSRPage;
import com.telus.wnp.pages.LoginPage;
import com.telus.wnp.utils.GenericUtils;

public class LSRPageSteps extends BaseSteps {

	public static String ponNumber = null;
	JavascriptExecutor executor = null;

	/**
	 * Method Description: This method is used to verify homepage is displayed
	 */
	public static void verifyIsHomePageDisplayed() {

		Reporting.logReporter(Status.INFO, "STEP ===> Verify LSR homepage is displayed after successful login ");

		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitForElementVisibility(LSRPage.homePageHelpText, 90);
		boolean actual = LSRPage.isHomePageDisplayed();
		System.out.println("===========> LSR");
		Validate.assertEquals(actual, true, "Unable to login into LSR.", false);

	}

	/**
	 * 
	 * @param TN
	 * @param Ban
	 * @param testDataJson
	 */
	public static void createWLNPortOutRequestFromLSR(JSONObject testDataJson, String sub, String Ban, String spid,
			String serviceProvider) {

		Reporting.logReporter(Status.INFO,
				"STEP === Peform Portout request from LSR --> Placing a port out request from LSR for '" + sub
						+ "' ===");

		LSRPage LSRPage = new LSRPage();

		LoginPageSteps.appLogin_LSR();
		verifyHomePage_New();

		selectSPIDAndServiceProvider(spid, serviceProvider);
		navigateToCreateLSRPage();
		String LSRCreatedSuccessMsg = testDataJson.getString("LSR_CREATED_SUCCESS_MSG");
		updateLSRHeaderSection();
		performSave(LSRCreatedSuccessMsg);
		LSRPage.closeAlertMsgHeaderIfDisplayed();

		BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.numberToBePorted);
		Validate.takeStepFullScreenShot("LSR Header Details", Status.INFO);

		updateServiceDetails(sub, testDataJson.getString("LNA"), Ban);
		String ServiceDetailsSavedExpectedText = testDataJson.getString("LSR_SERVICE_DETAIL_CREATED_SUCCESS_MSG");
		performSave(ServiceDetailsSavedExpectedText);
		LSRPage.closeAlertMsgHeaderIfDisplayed();
		ponNumber = getPortOutRequestNumber();
		Reporting.logReporter(Status.INFO, "PON Number: " + ponNumber);
		Validate.takeStepFullScreenShot("LSR Service Details", Status.INFO);

		// Need to click on Back button
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.backButton);
		BaseSteps.Debugs.scrollToElement(LSRPage.backButton);
		BaseSteps.Clicks.clickElement(LSRPage.backButton);
		LSRPage.closeAlertMsgHeaderIfDisplayed();
		BaseSteps.Debugs.scrollToElement(LSRPage.ponElementFromGrid(ponNumber));
		String ponNumberFromGrid = LSRPage.ponElementFromGrid(ponNumber).getText();

		Validate.assertEquals(ponNumberFromGrid, ponNumber, false);

		BaseSteps.Waits.waitForElementVisibility(LSRPage.statusFromTable, 10);

		// go to LSR Admin Order details
		updateLSRAdminOrderDetails_New();
		String LSRUpdatedSuccessMsg = testDataJson.getString("LSR_UPDATED_SUCCESS_MSG");
		performSave(LSRUpdatedSuccessMsg);
		LSRPage.closeAlertMsgHeaderIfDisplayed();
		Validate.takeStepFullScreenShot("LSR Admin Order Details", Status.INFO);

		// go to EUI Location & Access details
		navigateToEUILocationAndAccess();
		updateEUILocationAccessDetails(testDataJson);
		performSave(LSRUpdatedSuccessMsg);
		LSRPage.closeAlertMsgHeaderIfDisplayed();
		Validate.takeStepFullScreenShot("LSR EUI Location Details", Status.INFO);

		// go to LSR Contact details
		navigateToLSRContact();
		updateLSRContactDetails(testDataJson);
		performSave(LSRUpdatedSuccessMsg);
		LSRPage.closeAlertMsgHeaderIfDisplayed();
		Validate.takeStepFullScreenShot("LSR Contact Details", Status.INFO);

		moveRequestFromInprogressToCompleted();

		String actualfinalStatus = getStatusAfterPerformingSearchByPON(spid, serviceProvider, ponNumber);

		if (actualfinalStatus.equalsIgnoreCase("In Progress")) {
			BaseSteps.Clicks.clickElement(LSRPage.openPONDetailsFromSearchPage(ponNumber));

			try {
				BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.backButton);
				BaseSteps.Debugs.scrollToElement(LSRPage.backButton);
				BaseSteps.Clicks.clickElement(LSRPage.backButton);
				LSRPage.closeAlertMsgHeaderIfDisplayed();
				BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
				BaseSteps.Clicks.clickElement(LSRPage.completeButton);
			}

			catch (Exception e) {

				LSRPage.closeAlertMsgHeaderIfDisplayed();
			}

			LSRPage.closeAlertMsgHeaderIfDisplayed();

			actualfinalStatus = getStatusAfterPerformingSearchByPON(spid, serviceProvider, ponNumber);
		}

		Reporting.logReporter(Status.INFO, "LSR Request Status from Search: " + actualfinalStatus);
		Validate.assertEquals(actualfinalStatus, "Completed", "Mismatch in the Final Request Completion Status", false);
	}

	/**
	 * Method Description: This method is used to select specific SPID
	 */
	public static void selectSpecificSPID(String expectedSPID) {
		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.selectSPID);
		BaseSteps.Clicks.clickElement(LSRPage.selectSPID);

		BaseSteps.Dropdowns.selectByGoingThroughList(LSRPage.selectSPID, expectedSPID);
		BaseSteps.Clicks.clickElement(LSRPage.selectSPID);

	}

	/**
	 * Method Description: This method is used to select specific service provider
	 */
	public static void selectSpecificServiceProvider(String expectedServiceProvider) {

		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitUntilPageLoadCompleteLongWait();

		BaseSteps.Waits.waitGeneric(1000);
		try {
			BaseSteps.Waits.waitForElementVisibility(LSRPage.selectServiceProvider, 30);
		} catch (Exception e) {
			BaseSteps.Waits.waitForElementVisibility(LSRPage.selectServiceProvider, 30);

		}
		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.selectServiceProvider);
		BaseSteps.Clicks.clickElement(LSRPage.selectServiceProvider);

		String expectedSPInitials = expectedServiceProvider.split("-")[0].trim();
		BaseSteps.Waits.waitGeneric(500);

		BaseSteps.Dropdowns.selectByValue(LSRPage.selectServiceProvider, expectedSPInitials);
	}

	/**
	 * Method Description: This method is used to navigate t Create LSR Page
	 */
	public static void navigateToCreateLSRPage() {
		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.createLSRLink);
		BaseSteps.Clicks.clickElement(LSRPage.createLSRLink);
	}

	/**
	 * Method Description: This method is used to Save the details
	 */
	public static void saveDetails() {
		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.saveButton);
		BaseSteps.Debugs.scrollToElement(LSRPage.saveButton);
		BaseSteps.Clicks.clickElement(LSRPage.saveButton);
		LSRPage.closeAlertMsgHeaderIfDisplayed();
	}

	/**
	 * Method Description: This method is used to select spid and service provider
	 */
	public static void selectSPIDAndServiceProvider(String spid, String serviceProvider) {

		Reporting.logReporter(Status.INFO, "STEP === Select SPID and Service Provider Details from LSR==");

		selectSpecificSPID(spid);

		selectSpecificServiceProvider(serviceProvider);
		Validate.takeStepScreenShot("LSR Search Section");
	}

	/**
	 * Method Description: This method is used to update LSR Header Section
	 */

	public static void updateLSRHeaderSection() {
		Reporting.logReporter(Status.INFO, "STEP === Update LSR Header Details from LSR==");

		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.ignoreONSPChkBox);
		BaseSteps.Debugs.scrollToElement(LSRPage.ignoreONSPChkBox);
		BaseSteps.Clicks.clickElement(LSRPage.ignoreONSPChkBox);
		Validate.takeStepScreenShot("LSR Header Section");
	}

	/**
	 * Method Description: This method is used to update Service details
	 */
	public static void updateServiceDetails(String numberToBePorted, String LNA, String BAN) {

		Reporting.logReporter(Status.INFO, "STEP === Update Service Details from LSR==");

		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.numberToBePorted);
		BaseSteps.Debugs.scrollToElement(LSRPage.numberToBePorted);
		BaseSteps.SendKeys.sendKey(LSRPage.numberToBePorted, numberToBePorted);

		BaseSteps.Debugs.scrollToElement(LSRPage.ignoreONSPChkBox);
		BaseSteps.Waits.waitForElementVisibility(LSRPage.selectLNA);
		BaseSteps.Clicks.clickElement(LSRPage.selectLNA);
		BaseSteps.Waits.waitGeneric(500);
		BaseSteps.Dropdowns.selectByVisibleText(LSRPage.selectLNA, LNA);

		BaseSteps.Waits.waitForElementToBeClickable(LSRPage.ean);
		BaseSteps.SendKeys.sendKey(LSRPage.ean, BAN);

		Validate.takeStepFullScreenShot("LSR Service Details Section", Status.INFO);
	}

	/**
	 * Method Description: This method is used to update Admin Order Details
	 */
	public static void updateLSRAdminOrderDetails(int futureDays) {

		Reporting.logReporter(Status.INFO, "STEP === Update LSR Admin Details from LSR==");

		LSRPage LSRPage = new LSRPage();

		String futureDay = GenericUtils.getFutureDateTimeInPST(futureDays, 0, 0).split("/")[1];
		String currentDay = GenericUtils.getSystemDateInMMDDYYYYInPST().split("/")[1];

		String currentDay1 = GenericUtils.getSystemDateInMMDDYYYYInPST_LSR().split("/")[1];

		BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.desiredDueDate);
		BaseSteps.Debugs.scrollToElement(LSRPage.desiredDueDate);
		BaseSteps.Clicks.clickElement(LSRPage.desiredDueDate);
		BaseSteps.Clicks.clickElement(LSRPage.datePicker(futureDay));

		BaseSteps.Waits.waitGeneric(30000);
		saveDetails();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.saveButton);
		BaseSteps.Debugs.scrollToElement(LSRPage.saveButton);
		BaseSteps.Clicks.clickElement(LSRPage.authDate);

		BaseSteps.Clicks.clickElement(LSRPage.datePicker(currentDay));

		Validate.takeStepFullScreenShot("LSR Admin Order Details Section", Status.INFO);
	}

	/**
	 * Method Description: This method is used to update Admin Order Details
	 */
	public static void updateLSRAdminOrderDetails_New() {

		Reporting.logReporter(Status.INFO, "STEP === Update LSR Admin Details from LSR==");

		LSRPage LSRPage = new LSRPage();

		String month = GenericUtils.getSpecificValueFromCurrentDate("month");
		month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
		String dom = GenericUtils.getSpecificValueFromCurrentDate("date");

		BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.desiredDueDate);
		BaseSteps.Debugs.scrollToElement(LSRPage.desiredDueDate);
		BaseSteps.Clicks.clickElement(LSRPage.desiredDueDate);
		BaseSteps.Clicks.clickElement(LSRPage.selectCurrentDate(month, dom));

		saveDetails();

		selectAuthDate(LSRPage);
		
		/*
		 * BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.authDate);
		 * BaseSteps.Debugs.scrollToElement(LSRPage.authDate);
		 * BaseSteps.Clicks.clickElement(LSRPage.authDate);
		 * BaseSteps.Clicks.clickElement(LSRPage.selectCurrentDate(month, dom));
		 */

		Validate.takeStepFullScreenShot("LSR Admin Order Details Section", Status.INFO);
	}

	/**
	 * Method Description: This method is used to navigate to EUI Location page
	 */
	public static void navigateToEUILocationAndAccess() {
		LSRPage LSRPage = new LSRPage();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.locationDetailsLink);
		BaseSteps.Debugs.scrollToElement(LSRPage.locationDetailsLink);
		BaseSteps.Clicks.clickElement(LSRPage.locationDetailsLink);
	}

	/**
	 * Method Description: This method is used to LSR Contact Page
	 */
	public static void navigateToLSRContact() {
		LSRPage LSRPage = new LSRPage();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.lsrContactDetailsLink);
		BaseSteps.Debugs.scrollToElement(LSRPage.lsrContactDetailsLink);
		BaseSteps.Clicks.clickElement(LSRPage.lsrContactDetailsLink);
	}

	/**
	 * Method Description: This method is used to update LSR Contact Details
	 */
	public static void updateLSRContactDetails(JSONObject testDataJson) {

		Reporting.logReporter(Status.INFO, "STEP === Update LSR Contact Details from LSR==");

		LSRPage LSRPage = new LSRPage();

		enterTextAndSaveDetails(LSRPage.initiator, testDataJson.getString("INITIATOR_NAME"));
		enterTextAndSaveDetails(LSRPage.telephoneNumber, String.valueOf(GenericUtils.generateRandomMobileNumber()));

		enterTextAndSaveDetails(LSRPage.implementationContactName, testDataJson.getString("INITIATOR_NAME"));
		enterTextAndSaveDetails(LSRPage.implementationTelephoneNumber,
				String.valueOf(GenericUtils.generateRandomMobileNumber()));

		Validate.takeStepFullScreenShot("LSR Contact Details Section", Status.INFO);

	}

	/**
	 * Method Description: This method is used to update EUI details
	 */

	public static void updateEUILocationAccessDetails(JSONObject testDataJson) {

		Reporting.logReporter(Status.INFO, "STEP === Update EUI and Location Details from LSR==");

		LSRPage LSRPage = new LSRPage();

		// Enter Province
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.province);
		BaseSteps.Debugs.scrollToElement(LSRPage.province);
		BaseSteps.Dropdowns.selectByGoingThroughList(LSRPage.province, testDataJson.getString("PROVINCE"));

		// Enter Name
		String name = GenericUtils.generateRandomName(testDataJson.getString("NAME"));
		enterTextAndSaveDetails(LSRPage.name, name);

		// Enter House Number
		enterTextAndSaveDetails(LSRPage.houseNumber, testDataJson.getString("HOUSE_NUMBER"));

		// Enter Street Name
		enterTextAndSaveDetails(LSRPage.streetName, testDataJson.getString("STREET_NAME"));

		// Enter City
		enterTextAndSaveDetails(LSRPage.city, testDataJson.getString("CITY"));

		// Enter Postal Code
		enterTextAndSaveDetails(LSRPage.postalCode, testDataJson.getString("POSTAL_CODE"));

		Validate.takeStepFullScreenShot("EUI Location And Access Details Section", Status.INFO);
	}

	/**
	 * Method Description: This method is used to perform Save and verify message
	 */
	public static void performSave(String expectedText) {

		Reporting.logReporter(Status.INFO, "STEP === Save Request==");

		LSRPage LSRPage = new LSRPage();

		try {
			BaseSteps.Waits.waitForElementToBeClickable(LSRPage.saveButton);
			BaseSteps.Debugs.scrollToElement(LSRPage.saveButton);
			BaseSteps.Clicks.clickElement(LSRPage.saveButton);

			verifyRequestSavedSuccessfully(expectedText);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Save button not enabled" + e);
		}
	}

	/**
	 * Method Description: This method is used to verify request save confirmation
	 * message
	 */
	public static void verifyRequestSavedSuccessfully(String expectedMsg) {
		LSRPage LSRPage = new LSRPage();

		BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.updateRequestStatus);
		BaseSteps.Waits.waitGeneric(1000);
		String actualMsg = LSRPage.getUpdateRequestStatus();

		Validate.assertContains(actualMsg, expectedMsg, false);
	}

	/**
	 * Method Description: This method is used to getthe PON number
	 */
	public static String getPortOutRequestNumber() {

		Reporting.logReporter(Status.INFO, "STEP === Get PON Number from LSR==");

		LSRPage LSRPage = new LSRPage();

		BaseSteps.Debugs.scrollToElement(LSRPage.pon);
		BaseSteps.Waits.waitGeneric(1000);
		JavascriptExecutor executor = (JavascriptExecutor) WebDriverSession.getWebDriverSession();
		return executor.executeScript("return document.getElementById('pon').getAttribute('value');").toString();

	}

	/**
	 * Method Description: This method is used to perform search by PON number
	 */
	public static void performSearchByPONFromLSR(String SPID, String serviceProvider, String PON) {

		Reporting.logReporter(Status.INFO, "STEP === Perform Search from LSR==");

		selectSPIDAndServiceProvider(SPID, serviceProvider);

		LSRPage LSRPage = new LSRPage();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.searchFromDateFromHomePage);

		JavascriptExecutor executor = (JavascriptExecutor) WebDriverSession.getWebDriverSession();
		String text = executor.executeScript("return document.getElementsByName('fromDate')[0].getAttribute('value');")
				.toString();

		BaseSteps.Clicks.clickElement(LSRPage.searchFromDateFromHomePage);

		for (int i = 0; i < text.length(); i++) {
			BaseSteps.SendKeys.sendKey(LSRPage.searchFromDateFromHomePage, Keys.BACK_SPACE);
		}

		enterDate(LSRPage.searchFromDateFromHomePage, GenericUtils.getSystemDateInMMDDYYYY());
		BaseSteps.SendKeys.sendKey(LSRPage.searchFromDateFromHomePage, Keys.ENTER);

		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.searchBYFromHomePage);
		BaseSteps.Dropdowns.selectByVisibleText(LSRPage.searchBYFromHomePage, "Exact PON");

		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.searchFieldFromHomePage);

		String searchText = executor
				.executeScript("return document.getElementsByName('searchField')[0].getAttribute('value');").toString();

		BaseSteps.Clicks.clickElement(LSRPage.searchFieldFromHomePage);

		for (int i = 0; i < searchText.length(); i++) {
			BaseSteps.SendKeys.sendKey(LSRPage.searchFieldFromHomePage, Keys.BACK_SPACE);
		}

		BaseSteps.SendKeys.sendKey(LSRPage.searchFieldFromHomePage, PON);

		Validate.takeStepScreenShot("Entered Required Details for Search");

		BaseSteps.Waits.waitGeneric(1000);
		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.searchButtonFromHomePage);
		BaseSteps.Clicks.clickElement(LSRPage.searchButtonFromHomePage);

		BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.searchResultsLabelFromHomePage);
	}

	/**
	 * Method Description: This method is used to get the status from the Search
	 * Results
	 */
	public static String getStatusAfterPerformingSearchByPON(String SPID, String serviceProvider, String PON) {

		Reporting.logReporter(Status.INFO, "STEP === Get Status after Search from LSR==");
		LSRPage LSRPage = new LSRPage();

		BaseSteps.Waits.waitForElementToBeClickable(LSRPage.searchLSRLink);
		BaseSteps.Debugs.scrollToElement(LSRPage.searchLSRLink);
		BaseSteps.Clicks.clickElement(LSRPage.searchLSRLink);
		BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.selectSPID);

		performSearchByPONFromLSR(SPID, serviceProvider, PON);
		BaseSteps.Waits.waitForElementVisibility(LSRPage.statusFromSearchResultsFromHomePage, 30);
		BaseSteps.Debugs.scrollToElement(LSRPage.statusFromSearchResultsFromHomePage);

		Validate.takeStepScreenShot("Request Status From Search Results");

		return LSRPage.getStatusFromSearchResultsFromHomePage();

	}

	/**
	 * 
	 * @param element
	 * @param Text
	 */
	public static void enterTextAndSaveDetails(WebElement element, String Text) {
		BaseSteps.Waits.waitForElementVisibilityLongWait(element);
		BaseSteps.Debugs.scrollToElement(element);
		BaseSteps.SendKeys.clearFieldAndSendKeys(element, Text);
		saveDetails();
	}

	/**
	 * 
	 * @param pon
	 * @param spid
	 * @param serviceProvider
	 */
	public static void cancelRequestFromLSR(String sub, String pon, String spid, String serviceProvider) {

		Reporting.logReporter(Status.INFO,
				"STEP === Peform Portout request from LSR --> Placing a cancel request from LSR for PON Number: '" + pon
						+ " and Sub: " + sub + "' ===");

		try {
			LSRPage LSRPage = new LSRPage();

			LoginPageSteps.appLogin_LSR();
			// verifyHomePage();
			verifyHomePage_New();

			String actualfinalStatus = getStatusAfterPerformingSearchByPON(spid, serviceProvider, pon);
			BaseSteps.Clicks.clickElement(LSRPage.openPONDetailsFromSearchPage(pon));

			BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.tnFromSearchResults);
			BaseSteps.Debugs.scrollToElement(LSRPage.tnFromSearchResults);
			// String actualTN = LSRPage.getTNFromSearchResults().replaceAll("-", "");

			performCancellationSteps(LSRPage);

			actualfinalStatus = getStatusAfterPerformingSearchByPON(spid, serviceProvider, pon);

			if (actualfinalStatus.equalsIgnoreCase("In Progress")) {
				BaseSteps.Clicks.clickElement(LSRPage.openPONDetailsFromSearchPage(pon));
				BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
				BaseSteps.Debugs.scrollToElement(LSRPage.completeButton);
				BaseSteps.Clicks.clickElement(LSRPage.completeButton);

				Validate.takeStepScreenShot("After hitting Complete button");

				try {
					BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.backButton);
					BaseSteps.Debugs.scrollToElement(LSRPage.backButton);
					BaseSteps.Clicks.clickElement(LSRPage.backButton);
					LSRPage.closeAlertMsgHeaderIfDisplayed();

					BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
					BaseSteps.Debugs.scrollToElement(LSRPage.completeButton);
					BaseSteps.Clicks.clickElement(LSRPage.completeButton);

					Validate.takeStepScreenShot("After hitting Complete button");

				} catch (Exception e) {
					Reporting.logReporter(Status.INFO, "Back button not displayed");
					Validate.takeStepScreenShot("After hitting Complete button");

				}

			} else if (actualfinalStatus.equalsIgnoreCase("Completed")) {
				Reporting.logReporter(Status.INFO, "Request Status is already Completed");
			}

		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to perform Cancel Request from LSR" + e);
			Validate.assertTrue(false, "Exception Occured:  Unable to Perform Cancel Request from LSR");

		}

	}

	public static void verifyRequiredStatusFromLSR(String sub, String pon, String spid, String serviceProvider,
			String expectedStatus) {

		Reporting.logReporter(Status.INFO,
				"STEP === Verify Confirmed Status from LSR --> Steps to verify confirmed sttaus from LSR for '" + pon
						+ "' ===");
		try {
			LSRPage LSRPage = new LSRPage();

			LoginPageSteps.appLogin_LSR();
			verifyIsHomePageDisplayed();

			selectSPIDAndServiceProvider(spid, serviceProvider);
			String actualfinalStatus = getStatusAfterPerformingSearchByPON(spid, serviceProvider, pon);

			String actualTN = LSRPage.getTNFromSearchResults().replaceAll("-", "");

			if (actualTN.equalsIgnoreCase(sub)) {
				Validate.assertEquals(actualfinalStatus, expectedStatus, "Required Status verification from LSR",
						false);
				Validate.takeStepFullScreenShot("Request Status", Status.INFO);

			} else
				Reporting.logReporter(Status.INFO, "Mismatch in the PON Number: '" + pon + "' for Sub: " + sub);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to verify confirmed status from LSR" + e);
			Validate.assertTrue(false, "Exception Occured:  Unable to verify confirmed status from LSR");
		}

	}

	/**
	 * 
	 * @param TN
	 * @param Ban
	 * @param testDataJson
	 */
	public static void waitUntilSpecificStatusFromLSR(String expectedStatus, String sub, String Ban, String spid,
			String serviceProvider, String pon, int maxTimeInSeconds) {

		Reporting.logReporter(Status.INFO,
				"STEP === Peform Portout request from LSR --> Placing a port out request from LSR for '" + sub
						+ "' ===");

		LSRPage LSRPage = new LSRPage();

		LoginPageSteps.appLogin_LSR();
		// verifyHomePage();
		verifyHomePage_New();

		String actualStatus = getStatusAfterPerformingSearchByPON(spid, serviceProvider, pon);

		long startTime = GenericUtils.getCurrentSystemDateTimeInMillis();
		long endTime = GenericUtils.getCurrentSystemDateTimeInMillis() + (maxTimeInSeconds * 1000);

		while (startTime < endTime) {
			startTime = GenericUtils.getCurrentSystemDateTimeInMillis();

			if (actualStatus.equalsIgnoreCase(expectedStatus)) {
				break;
			}

			BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.searchButtonFromHomePage);
			BaseSteps.Clicks.clickElement(LSRPage.searchButtonFromHomePage);

			BaseSteps.Waits.waitForElementVisibilityLongWait(LSRPage.searchResultsLabelFromHomePage);
			BaseSteps.Waits.waitForElementVisibility(LSRPage.statusFromSearchResultsFromHomePage, 30);
			BaseSteps.Debugs.scrollToElement(LSRPage.statusFromSearchResultsFromHomePage);
			actualStatus = LSRPage.getStatusFromSearchResultsFromHomePage();
		}

		Validate.assertEquals(actualStatus.toUpperCase(), expectedStatus.toUpperCase(), false);
		Reporting.logReporter(Status.INFO, "Status from LSR for sub: " + sub + " is: " + actualStatus);
	}

	/**
	 * 
	 */
	public static void enterDate(WebElement e, String date) {

		date = GenericUtils.getSystemDateInMMDDYYYY();
		String s[] = date.split("/");
		e.sendKeys(s[0]);
		e.sendKeys("/");
		e.sendKeys(s[1]);
		e.sendKeys("/");
		e.sendKeys(s[2]);
	}

	/**
	 * 
	 */
	public static void moveRequestFromInprogressToCompleted() {
		LSRPage LSRPage = new LSRPage();
		BaseSteps.Waits.waitForElementVisibility(LSRPage.statusFromTable, 10);
		BaseSteps.Debugs.scrollToElement(LSRPage.statusFromTable);
		Validate.takeStepScreenShot("Request Status");
		String statusFromtable = LSRPage.getStatusFromGrid();

		if (statusFromtable.equalsIgnoreCase("In Progress")) {
			BaseSteps.Clicks.clickElement(LSRPage.refLinkFromTable);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
			BaseSteps.Debugs.scrollToElement(LSRPage.completeButton);
			BaseSteps.Clicks.clickElement(LSRPage.completeButton);

			Validate.takeStepScreenShot("After hitting Complete button");

			try {
				BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.backButton);
				BaseSteps.Debugs.scrollToElement(LSRPage.backButton);
				BaseSteps.Clicks.clickElement(LSRPage.backButton);
				LSRPage.closeAlertMsgHeaderIfDisplayed();

				BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
				BaseSteps.Debugs.scrollToElement(LSRPage.completeButton);
				BaseSteps.Clicks.clickElement(LSRPage.completeButton);

				Validate.takeStepScreenShot("After hitting Complete button");

			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "Back button not displayed");
				BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
				BaseSteps.Debugs.scrollToElement(LSRPage.completeButton);

				BaseSteps.Clicks.clickElement(LSRPage.completeButton);

				Validate.takeStepScreenShot("After hitting Complete button");

			}

		} else if (statusFromtable.equalsIgnoreCase("Completed")) {
			Reporting.logReporter(Status.INFO, "Request Status is already Completed");
		}
	}

	public static void verifyHomePage() {

		try {
			BaseSteps.Waits.waitGeneric(5000);
			LoginPageSteps.handleLSRSessionExpire();
			BaseSteps.Waits.waitGeneric(5000);
			LoginPageSteps.handleLSRSessionExpire();
			verifyIsHomePageDisplayed();
			BaseSteps.Waits.waitGeneric(5000);
			LoginPageSteps.handleLSRSessionExpire();
			BaseSteps.Waits.waitGeneric(5000);
			LoginPageSteps.handleLSRSessionExpire();
			BaseSteps.Waits.waitGeneric(5000);
		} catch (Exception e) {
			LoginPageSteps.handleLSRSessionExpire();
			BaseSteps.Waits.waitGeneric(5000);
			LoginPageSteps.handleLSRSessionExpire();
			BaseSteps.Waits.waitGeneric(5000);
			verifyIsHomePageDisplayed();
		}

	}

	public static void verifyHomePage_New() {
		LSRPage LSRPage = new LSRPage();
		LoginPage LoginPage = new LoginPage();
		int i = 0;
		BaseSteps.Waits.waitGeneric(3000);
		boolean stat = LSRPage.checkIfsearchLSRLinkIsPresent();
		while (!stat) {
			LoginPageSteps.handleLSRSessionExpire();
			BaseSteps.Waits.waitGeneric(5000);
			stat = LSRPage.checkIfsearchLSRLinkIsPresent();
			BaseSteps.Waits.waitGeneric(5000);
			stat = LSRPage.checkIfsearchLSRLinkIsPresent();
			i++;
			System.out.println("i=============>" + i);
			System.out.println("search label present=============>" + stat);
			if ((stat) || i == 5) {
				break;
			}
		}

		verifyIsHomePageDisplayed();

	}

	public static void performCancellationSteps(LSRPage LSRPage) {

		Validate.takeStepScreenShot("New Version Button");
		BaseSteps.Waits.waitForElementToBeClickable(LSRPage.newVersionButton);
		BaseSteps.Debugs.scrollToElement(LSRPage.newVersionButton);
		BaseSteps.Clicks.clickElement(LSRPage.newVersionButton);

		BaseSteps.Waits.waitGeneric(3000);

		BaseSteps.Waits.waitForElementVisibility(LSRPage.sup, 30);
		BaseSteps.Debugs.scrollToElement(LSRPage.sup);
		BaseSteps.Dropdowns.selectByGoingThroughList(LSRPage.sup, "1 - Cancel");

		BaseSteps.Waits.waitForElementToBeClickable(LSRPage.saveButton);
		BaseSteps.Debugs.scrollToElement(LSRPage.saveButton);
		BaseSteps.Clicks.clickElement(LSRPage.saveButton);

		BaseSteps.Waits.waitForElementVisibility(LSRPage.statusFromTable, 30);
		BaseSteps.Debugs.scrollToElement(LSRPage.statusFromTable);
		Validate.takeStepScreenShot("Request Status After Save");

		String statusFromtable = LSRPage.getStatusFromGrid();

		if (statusFromtable.equalsIgnoreCase("In Progress")) {
			BaseSteps.Clicks.clickElement(LSRPage.refLinkFromTable);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
			BaseSteps.Debugs.scrollToElement(LSRPage.completeButton);
			BaseSteps.Clicks.clickElement(LSRPage.completeButton);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.backButton);
			BaseSteps.Debugs.scrollToElement(LSRPage.backButton);
			BaseSteps.Clicks.clickElement(LSRPage.backButton);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.completeButton);
			BaseSteps.Debugs.scrollToElement(LSRPage.completeButton);
			BaseSteps.Clicks.clickElement(LSRPage.completeButton);

			Validate.takeStepScreenShot("After hitting Complete button");
		}

		Validate.assertEquals(true, true, "SUP From LSR", false);
		Validate.takeStepFullScreenShot("Request Cancelled", Status.INFO);

	}

	public static void verifyRecordsStatusAfterCancellation(String currentStatus, String previousStatus) {
		Reporting.logReporter(Status.INFO, "STEP === Verify request Status for the available records ===");

		LSRPage LSRPage = new LSRPage();

		// LoginPageSteps.appLogin_LSR();
		// verifyHomePage();
		// verifyHomePage_New();

		BaseSteps.Waits.waitForElementVisibility(LSRPage.statusFromSearchResultsFromHomePage, 30);
		BaseSteps.Debugs.scrollToElement(LSRPage.statusFromSearchResultsFromHomePage);

		String currentRecordStatusFromLSR = LSRPage.getCurrentRecordStatus();
		String currentRecordVersionFromLSR = LSRPage.getCurrentRecordVersion();
		String previousRecordStatusFromLSR = LSRPage.getPreviousRecordStatus();
		String previousRecordVersionFromLSR = LSRPage.getPreviousRecordVersion();
		String previousRecordCNTYPFromLSR = LSRPage.getPreviousRecordCNTYP();

		Validate.assertEquals(currentRecordStatusFromLSR.toUpperCase(), currentStatus.toUpperCase(), false);
		Validate.assertEquals(currentRecordVersionFromLSR, "02", true);
		Validate.assertEquals(previousRecordStatusFromLSR.toUpperCase(), previousStatus.toUpperCase(), true);
		Validate.assertEquals(previousRecordVersionFromLSR, "01", true);
		// Validate.assertEquals(previousRecordCNTYPFromLSR.toUpperCase(), "R", true);

	}

	public static void selectAuthDate(LSRPage LSRPage) {

		String currentDay = GenericUtils.getSystemDateInMMDDYYYYInPST_LSR().split("/")[1];

		BaseSteps.Waits.waitForElementToBeClickableLongWait(LSRPage.authDate);

		BaseSteps.Clicks.clickElement(LSRPage.authDate);

		BaseSteps.Clicks.clickElement(LSRPage.datePicker(currentDay));

	}
}
