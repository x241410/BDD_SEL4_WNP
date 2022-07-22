package com.telus.wnp.steps;

import java.util.ArrayList;

import org.json.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.test.utils.Status;
import com.telus.wnp.pages.RKPortalPages;
import com.telus.wnp.pages.RKPortalPages_NEW;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for RK Portal page (Steps) > AUTHOR: x241410
 ****************************************************************************
 */
public class RKPortalPageSteps extends BaseSteps {

	public static String activatedKoodoPrepaidMobileNumber = null;
	public static String activatedKoodoPrepaidAccountNumber = null;
	public static String selectedNumber = null;

	/**
	 * This method is used to do create Koodo prepaid number from RK Portal
	 * 
	 * @param testDataJson
	 *            This is input test data json object
	 * @param sub
	 *            This is subscriber number
	 * @param ban
	 *            This is subscriber ban
	 * @return nothing.
	 */
	public static String activateKoodoPrepaidNumber(JSONObject testDataJson, String sub, String ban) {

		try {

			RKPortalPages RKPortalPage = new RKPortalPages();

			LoginPageSteps.appLauch_RKPortal();
			verifyRKPortalHomePageIsDisplayed();

			BaseSteps.SendKeys.sendKey(RKPortalPage.SIMNumber, testDataJson.getString("SIM_NUMBER"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.firstName, testDataJson.getString("FIRST_NAME"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.lastName, testDataJson.getString("LAST_NAME"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.streetNum, testDataJson.getString("STREET_NUMBER"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.streetName, testDataJson.getString("STREET_NAME"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.city, testDataJson.getString("CITY"));

			BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.province, testDataJson.getString("PROVINCE"));
			BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.country, testDataJson.getString("COUNTRY"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.postalCode, testDataJson.getString("POSTAL_CODE"));

			String randomVal = String.valueOf(GenericUtils.generateRandomInteger(1000));
			String emailId = testDataJson.getString("USER_EMAIL_BEFORE") + randomVal
					+ testDataJson.getString("USER_EMAIL_AFTER");

			BaseSteps.SendKeys.sendKey(RKPortalPage.userEmail, emailId);
			BaseSteps.SendKeys.sendKey(RKPortalPage.confirmUserEmail, emailId);

			BaseSteps.SendKeys.sendKey(RKPortalPage.password, testDataJson.getString("PASSWORD"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.confirmPassword, testDataJson.getString("PASSWORD"));

			BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.securityQuestion,
					testDataJson.getString("SECURITY_QUES"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.securityAnswer, testDataJson.getString("SECURITY_ANS"));
			BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.preferredLanguage,
					testDataJson.getString("SELECT_PREFERRED_LANGUAGE"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.pin, testDataJson.getString("PIN"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.confirmPin, testDataJson.getString("PIN"));

			boolean stat = testDataJson.getBoolean("ACCEPT_CHECKBOX_STATE");
			if (stat)
				BaseSteps.Clicks.clickElement(RKPortalPage.acceptTOSChkBox);

			BaseSteps.Clicks.clickElement(RKPortalPage.continueBtn);

			boolean simEngagedStatus = checkForSimEngagedStatus();

			if (!simEngagedStatus) {

				activationStepsKoodoPrepaid(testDataJson, sub, ban);

			} else {
				Reporting.logReporter(Status.DEBUG,
						"The SIM number you’ve entered is currently reserved by another session or Invalid SIM.");
				Assert.assertFalse(true,
						"Unable to activate Koodo Prepaid Number as SIM Number is reserved by another session or Invalid.");
			}

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to activate Koodo prepaid number from RK Portal: " + e);
			Validate.takeStepFullScreenShot("Exception Occured", Status.ERROR);
			Assert.assertTrue(false, "Unable to activate Koodo Prepaid Number");
		}
		return sub;
	}

	public static void verifyRKPortalHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> RK Portal homepage is displayed after successful url launch ");

		RKPortalPages RKPortalPages = new RKPortalPages();

		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPages.SIMNumber);
		boolean actual = RKPortalPages.isHomePageDisplayed();
		System.out.println("===========> RK Portal");
		Validate.assertEquals(actual, true, "Unable to launch RK Portal URL.", false);
	}

	public static void verifyRKPortalHomePageIsDisplayed_NEW() {
		Reporting.logReporter(Status.INFO, "STEP ===> RK Portal homepage is displayed after successful url launch ");

		RKPortalPages_NEW RKPortalPages = new RKPortalPages_NEW();

		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPages.getStartedButton);
		boolean actual = RKPortalPages.isHomePageDisplayed();
		System.out.println("===========> RK Portal");
		Validate.assertEquals(actual, true, "Unable to launch RK Portal URL.", false);
	}

	/**
	 * Method Description: The purpose of this method is to enter existing
	 * subscriber number details
	 * 
	 * @param sub
	 */
	public static void enterExistingNumDetails(String sub) {

		RKPortalPages RKPortalPage = new RKPortalPages();
		String areaCode = sub.substring(0, 3);
		String firstThreeDigits = sub.substring(3, 7);
		String lastFourDigits = sub.substring(6);

		BaseSteps.SendKeys.clearFieldAndSendKeys(RKPortalPage.txtPhoneAreaCode, areaCode);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RKPortalPage.txtPhoneFirstThreeDigitsOfNum, firstThreeDigits);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RKPortalPage.txtPhoneLastFourDigitsOfNum, lastFourDigits);
	}

	/**
	 * Method Description: The purpose of this method is to enter existing
	 * subscriber number details
	 * 
	 * @param sub
	 */
	public static void enterExistingNumDetails_NEW(String sub) {

		RKPortalPages_NEW RKPortalPage = new RKPortalPages_NEW();
		String areaCode = sub.substring(0, 3);
		String firstThreeDigits = sub.substring(3, 6);
		String lastFourDigits = sub.substring(6);

		BaseSteps.SendKeys.clearFieldAndSendKeys(RKPortalPage.txtPhoneAreaCode, areaCode);
		BaseSteps.SendKeys.clearFieldAndSendKeys(RKPortalPage.txtPhoneFirstThreeDigitsOfNum, firstThreeDigits);
		BaseSteps.SendKeys.sendKey(RKPortalPage.txtPhoneLastFourDigitsOfNum, lastFourDigits);
		BaseSteps.SendKeys.sendKey(RKPortalPage.txtPhoneLastFourDigitsOfNum, Keys.TAB);
	}

	/**
	 * Method Description: The purpose of this method is to get the displayed
	 * options available for TN
	 * 
	 * @return
	 */
	public static int sizeOfPickYourNumList() {

		ArrayList<String> listOfAvailableNums = new ArrayList<String>();
		RKPortalPages RKPortalPage = new RKPortalPages();

		for (WebElement e : RKPortalPage.pickYourNumList) {
			listOfAvailableNums.add(e.getText().toString());
		}

		return listOfAvailableNums.size();
	}

	/**
	 * Method Description: The purpose of this method is to get the Koodo Prepaid
	 * Mobile Number
	 * 
	 * @return
	 */
	public static String getKoodoPrepaidMobileNumber() {
		RKPortalPages RKPortalPage = new RKPortalPages();
		String koodoPrepaidMobileNumber = null;
		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.kprePhoneNumber);
			koodoPrepaidMobileNumber = RKPortalPage.getKPREPhoneNumber();
			koodoPrepaidMobileNumber = koodoPrepaidMobileNumber.trim().replaceAll(" ", "").replaceAll("-", "");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to get the activated Koodo Prepaid Mobile Number " + e);
		}
		return koodoPrepaidMobileNumber;
	}

	/**
	 * Method Description: The purpose of this method is to get Koodo Prepaid
	 * Account Number
	 * 
	 * @return
	 */
	public static String getKoodoPrepaidAccountNumber() {
		RKPortalPages RKPortalPage = new RKPortalPages();
		String koodoPrepaidAccountNumber = null;
		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.kpreAccountNumber);
			koodoPrepaidAccountNumber = RKPortalPage.getKPREAccountNumber();
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to get the activated Koodo Prepaid Account Number " + e);
		}
		return koodoPrepaidAccountNumber;

	}

	/**
	 * Method Description: The purpose of this method is to get the Koodo Prepaid
	 * Mobile Number
	 * 
	 * @return
	 */
	public static String getKoodoPrepaidMobileNumber_NEW() {
		RKPortalPages_NEW RKPortalPage = new RKPortalPages_NEW();
		String koodoPrepaidMobileNumber = "";
		try {
			BaseSteps.Waits.waitForElementVisibility(RKPortalPage.kprePhoneNumber, 60);
			koodoPrepaidMobileNumber = RKPortalPage.getKPREPhoneNumber();
			koodoPrepaidMobileNumber = koodoPrepaidMobileNumber.trim().replaceAll(" ", "").replaceAll("-", "");
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to get the activated Koodo Prepaid Mobile Number " + e);
		}
		return koodoPrepaidMobileNumber;
	}

	/**
	 * Method Description: The purpose of this method is to get Koodo Prepaid
	 * Account Number
	 * 
	 * @return
	 */
	public static String getKoodoPrepaidAccountNumber_NEW() {
		RKPortalPages_NEW RKPortalPage = new RKPortalPages_NEW();
		String koodoPrepaidAccountNumber = "";
		try {
			BaseSteps.Waits.waitForElementVisibility(RKPortalPage.kpreAccountNumber, 60);
			koodoPrepaidAccountNumber = RKPortalPage.getKPREAccountNumber();
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to get the activated Koodo Prepaid Account Number " + e);
		}
		return koodoPrepaidAccountNumber;

	}

	/**
	 * Method description: This method is used to activate the Koodo Prepaid Number
	 * 
	 * @param testDataJson
	 * @param sub
	 * @param ban
	 */
	public static void activationStepsKoodoPrepaid(JSONObject testDataJson, String sub, String ban) {
		RKPortalPages RKPortalPage = new RKPortalPages();

		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.transferExistingNumRadioBtn);

		boolean newNum = testDataJson.getBoolean("NEW_NUMBER_FLAG");
		if (newNum) {
			BaseSteps.Clicks.clickElement(RKPortalPage.selectNewNumber);
			BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.selectProvince,
					testDataJson.getString("NEW_PHN_PROVINCE"));
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.selectCityRegion,
					testDataJson.getString("NEW_PHN_CITY_REGION"));

			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			BaseSteps.Waits.waitGeneric(2000);
			try {
				if (sizeOfPickYourNumList() >= 1) {
					BaseSteps.Clicks.clickElement(RKPortalPage.pickYourNumList.get(0));
					BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.selectedMobileNumber);
					selectedNumber = RKPortalPage.getSelectedMobileNumber();

					System.out.println("==================>");
					System.out.println("selectedNumber: " + selectedNumber);
					System.out.println("==================>");

					Validate.takeStepScreenShot("Capturing Generated Number");

				} else
					Reporting.logReporter(Status.DEBUG, "Unable to select the number from "
							+ testDataJson.getString("NEW_PHN_CITY_REGION") + " region for activation");
			} catch (Exception e) {
				Reporting.logReporter(Status.DEBUG, "Unable to activate number for activation");
				Validate.takeStepFullScreenShot("Exception Occured", Status.ERROR);
				Validate.assertFalse(true, "Unable to activate number for activation");
			}
		} else {
			BaseSteps.Clicks.clickElement(RKPortalPage.transferExistingNumRadioBtn);
			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			enterExistingNumDetails(sub);
			BaseSteps.Clicks.clickElement(RKPortalPage.checkEligibilityBtn);

			BaseSteps.Waits.waitGeneric(3000);
			BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.oldAccount);
			BaseSteps.SendKeys.sendKey(RKPortalPage.oldAccount, ban);
			BaseSteps.SendKeys.sendKey(RKPortalPage.alternateContactNumber,
					testDataJson.getString("ALTERNATE_CONTACT_NUMBER"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.oldServiceProviderAccountName,
					testDataJson.getString("OSP_ACCOUNT_NAME"));

			boolean authorizeStat = testDataJson.getBoolean("AUTHORIZE_CHECKBOX_STATE");
			if (authorizeStat)
				BaseSteps.Clicks.clickElement(RKPortalPage.checkAuthorizedCheckBox);
		}

		Validate.takeStepScreenShot("Existing Number Details", Status.INFO);
		BaseSteps.Clicks.clickElement(RKPortalPage.continueBtn);

		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.basePlan);
		BaseSteps.Debugs.scrollToElement(RKPortalPage.basePlan);

		BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.basePlan, testDataJson.getString("BASE_PLAN"));
		Validate.takeStepScreenShot("Selected Plan", Status.INFO);

		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.continueBtn);
		BaseSteps.Debugs.scrollToElement(RKPortalPage.continueBtn);
		BaseSteps.Clicks.clickElement(RKPortalPage.continueBtn);

		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.continueBtn);
		BaseSteps.Clicks.clickElement(RKPortalPage.continueBtn);

		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.paymentDropDown);
		BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.paymentDropDown,
				testDataJson.getString("SELECT_PAYMENT_METHOD"));

		BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardNumber, testDataJson.getString("CREDIT_CARD_NUMBER"));
		BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.expiryMonthDropDwon,
				testDataJson.getString("SELECT_EXPIRY_MONTH"));
		BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.expiryYearDropDwon,
				testDataJson.getString("SELECT_EXPIRY_YEAR"));

		BaseSteps.SendKeys.sendKey(RKPortalPage.cvv2, testDataJson.getString("CVV2"));
		BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardFirstName, testDataJson.getString("FIRST_NAME"));
		BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardLastName, testDataJson.getString("LAST_NAME"));

		BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardStreetAddress, testDataJson.getString("STREET_NAME"));
		BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardCity, testDataJson.getString("CITY"));
		BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardProvince, testDataJson.getString("PROVINCE"));

		BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.creditCardCountry, testDataJson.getString("COUNTRY"));

		BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardZipCode, testDataJson.getString("ZIP_CODE"));

		boolean noThanksRadioBtnStat = testDataJson.getBoolean("NO_THANKS_RADIO_BTN_STATE");
		if (noThanksRadioBtnStat)
			BaseSteps.Clicks.clickElement(RKPortalPage.doNotRegisterRadioBtn);

		// Click on Activate Button
		BaseSteps.Clicks.clickElement(RKPortalPage.continueBtn);
		BaseSteps.Waits.waitForElementVisibility(RKPortalPage.summaryPageLabel, 120);

		/*
		 * READ SUBSCRIBER NUMBER AND BAN DETAILS FOR RK MOBILE
		 */

		activatedKoodoPrepaidMobileNumber = getKoodoPrepaidMobileNumber();
		System.out.println("==================>");
		System.out.println(activatedKoodoPrepaidMobileNumber);
		System.out.println("==================>");

		activatedKoodoPrepaidAccountNumber = getKoodoPrepaidAccountNumber();
		System.out.println("==================>");
		System.out.println(activatedKoodoPrepaidAccountNumber);
		System.out.println("==================>");
		Validate.takeStepFullScreenShot("Koodo Prepaid Number and Ban Created Successfully", Status.PASS);

		Reporting.logReporter(Status.INFO,
				"activatedKoodoPrepaidMobileNumber is: " + activatedKoodoPrepaidMobileNumber);
		Reporting.logReporter(Status.INFO,
				"activatedKoodoPrepaidAccountNumber is: " + activatedKoodoPrepaidAccountNumber);

		BaseSteps.Waits.waitForElementVisibility(RKPortalPage.continueBtn, 120);
		// Click on Finish button
		BaseSteps.Debugs.scrollToElement(RKPortalPage.continueBtn);
		BaseSteps.Clicks.clickElement(RKPortalPage.continueBtn);

	}

	/**
	 * Method Description: This method is used to check SIM status
	 * 
	 * @return false is SIM is available to use else return true if SIM is
	 *         Invalid/Not available for use
	 */
	public static boolean checkForSimEngagedStatus() {
		boolean simEngagedStatus = false;
		RKPortalPages RKPortalPage = new RKPortalPages();
		try {
			if (RKPortalPage.getSIMReservedErrorMsg() || RKPortalPage.getInvalidSIMErrorMsg())
				simEngagedStatus = true;

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Issue with SIM");
			return false;
		}
		return simEngagedStatus;

	}

	/**
	 * This method is used to do create Koodo prepaid number from RK Portal
	 * 
	 * @param testDataJson
	 *            This is input test data json object
	 * @param sub
	 *            This is subscriber number
	 * @param ban
	 *            This is subscriber ban
	 * @return nothing.
	 */
	public static void activateKoodoPrepaidNumber_NEW(JSONObject testDataJson, String sub, String ban,
			boolean newNumberFlag) {

		try {

			RKPortalPages_NEW RKPortalPage = new RKPortalPages_NEW();

			LoginPageSteps.appLogin_RKPortal();
			verifyRKPortalHomePageIsDisplayed_NEW();

			BaseSteps.Clicks.clickElement(RKPortalPage.getStartedButton);
			BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.SIMNumber);

			BaseSteps.SendKeys.sendKey(RKPortalPage.SIMNumber, testDataJson.getString("SIM_NUMBER"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.firstName, testDataJson.getString("FIRST_NAME"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.lastName, testDataJson.getString("LAST_NAME"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.province, testDataJson.getString("PROVINCE"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.postalCode, testDataJson.getString("POSTAL_CODE"));

			String emailId = GenericUtils.getRandomEmailId();

			BaseSteps.SendKeys.sendKey(RKPortalPage.userEmail, emailId);
			BaseSteps.SendKeys.sendKey(RKPortalPage.confirmUserEmail, emailId);

			BaseSteps.SendKeys.sendKey(RKPortalPage.confirmUserEmail, Keys.TAB);
			BaseSteps.Waits.waitForElementVisibility(RKPortalPage.emiailIdConfirmedTickMark, 90);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.password);
			BaseSteps.SendKeys.sendKey(RKPortalPage.password, testDataJson.getString("PASSWORD"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.confirmPassword, testDataJson.getString("PASSWORD"));

			BaseSteps.Dropdowns.selectByVisibleText(RKPortalPage.securityQuestion,
					testDataJson.getString("SECURITY_QUES"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.securityAnswer, testDataJson.getString("SECURITY_ANS"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.pin, testDataJson.getString("PIN"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.confirmPin, testDataJson.getString("PIN"));

			String preferredLanguage = testDataJson.getString("SELECT_PREFERRED_LANGUAGE");
			doSelectPreferredLanguage(preferredLanguage);

			Validate.takeStepFullScreenShot("SIM and other basic details", Status.INFO);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.step2PhoneNumberBtn);
			BaseSteps.Clicks.clickElement(RKPortalPage.step2PhoneNumberBtn);

			if (!newNumberFlag) {

				String alternateContact = testDataJson.getString("ALTERNATE_CONTACT_NUMBER");
				String OSP = testDataJson.getString("OLD_SERVICE_PROVIDER");
				activateExistingNumber(sub, ban, alternateContact, OSP);
			}

			else {
				BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.city);
				BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.city);
				BaseSteps.SendKeys.sendKey(RKPortalPage.city, testDataJson.getString("CITY"));

				BaseSteps.Waits.waitForElementVisibility(RKPortalPage.seeMoreOptionsLink, 60);

				selectedNumber = RKPortalPage.getSelectedPhoneNumberText();

				System.out.println("=================>");
				System.out.println(selectedNumber);
				System.out.println("=================>");

				String expectedSeries = testDataJson.getString("EXPECTED_SERIES");

				selectExpectedSeriesNumber(expectedSeries);
				Reporting.logReporter(Status.INFO, "Selected Number is : " + selectedNumber);

			}

			BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.step3PlansBtn);
			BaseSteps.Clicks.clickElement(RKPortalPage.step3PlansBtn);

			BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.selectFirstDefaultPlan);
			BaseSteps.Clicks.clickElement(RKPortalPage.selectFirstDefaultPlan);
			
			BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.step4PaymentBtn);
			BaseSteps.Debugs.scrollToElement(RKPortalPage.step4PaymentBtn);
			BaseSteps.Clicks.clickElement(RKPortalPage.step4PaymentBtn);

			BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.creditCardNumber);
			BaseSteps.Debugs.scrollToElement(RKPortalPage.creditCardNumber);
			BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardNumber, testDataJson.getString("CREDIT_CARD_NUMBER"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.expiryDate, testDataJson.getString("EXPIRY_DATE"));
			BaseSteps.SendKeys.sendKey(RKPortalPage.securityCode, testDataJson.getString("CVV2"));

			BaseSteps.SendKeys.sendKey(RKPortalPage.creditCardPostalCode, testDataJson.getString("POSTAL_CODE"));

			BaseSteps.Clicks.clickElement(RKPortalPage.agreeToServiceTermsChkBox);

			Validate.takeStepScreenShot("Credit Card Details", Status.INFO);

			Validate.takeStepFullScreenShot("Before Activation", Status.INFO);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.activateSIMCardBtn);
			BaseSteps.Debugs.scrollToElement(RKPortalPage.activateSIMCardBtn);
			BaseSteps.Clicks.clickElement(RKPortalPage.activateSIMCardBtn);

			/*
			 * Need to update below methods for new locators
			 * 
			 */

			activatedKoodoPrepaidAccountNumber = getKoodoPrepaidAccountNumber_NEW();
			System.out.println("==================>");
			System.out.println(activatedKoodoPrepaidAccountNumber);
			System.out.println("==================>");

			activatedKoodoPrepaidMobileNumber = getKoodoPrepaidMobileNumber_NEW();
			System.out.println("==================>");
			System.out.println(activatedKoodoPrepaidMobileNumber);
			System.out.println("==================>");

			Validate.takeStepFullScreenShot("Koodo Prepaid Number and Ban Details", Status.PASS);

			Validate.assertEquals(false, activatedKoodoPrepaidMobileNumber.isEmpty(), false);
			Validate.assertEquals(false, activatedKoodoPrepaidAccountNumber.isEmpty(), true);

			Reporting.logReporter(Status.INFO,
					"activatedKoodoPrepaidMobileNumber is: " + activatedKoodoPrepaidMobileNumber);
			Reporting.logReporter(Status.INFO,
					"activatedKoodoPrepaidAccountNumber is: " + activatedKoodoPrepaidAccountNumber);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to activate Koodo prepaid number from RK Portal: " + e);
			Validate.takeStepFullScreenShot("Exception Occured", Status.ERROR);
			Assert.assertTrue(false, "Unable to activate Koodo Prepaid Number");
		}

	}

	public static void doSelectPreferredLanguage(String language) {
		RKPortalPages_NEW RKPortalPage = new RKPortalPages_NEW();

		if (language.equalsIgnoreCase("FR") || language.equalsIgnoreCase("FRENCH")) {

			BaseSteps.Waits.waitForElementToBeClickable(RKPortalPage.preferredLanguage_FR);
			BaseSteps.Clicks.clickElement(RKPortalPage.preferredLanguage_FR);

		}

	}

	public static String selectExpectedSeriesNumber(String expectedSeries) {

		RKPortalPages_NEW RKPortalPage = new RKPortalPages_NEW();

		int i = 0;
		if (!selectedNumber.contains(expectedSeries)) {
			BaseSteps.Clicks.clickElement(RKPortalPage.seeMoreOptionsLink);
			BaseSteps.Waits.waitForElementVisibility(RKPortalPage.refreshOptionsLink, 30);
			// BaseSteps.Clicks.clickElement(RKPortalPage.refreshOptionsLink);

			while (!RKPortalPage.clickExpectedFormatTN(expectedSeries) && i < 50) {
				BaseSteps.Clicks.clickElement(RKPortalPage.refreshOptionsLink);

				try {
					BaseSteps.Waits.waitForElementVisibility(RKPortalPage.refreshOptionsLink, 30);
					BaseSteps.Clicks.clickElement(RKPortalPage.refreshOptionsLink);
					BaseSteps.Waits.waitForElementVisibility(RKPortalPage.displayedTNOptions);

				} catch (Exception e) {
					BaseSteps.Waits.waitForElementVisibility(RKPortalPage.selectedPhoneNumber, 90);
				}

				i = i + 1;
			}
			BaseSteps.Waits.waitGeneric(2000);
			selectedNumber = RKPortalPage.getSelectedPhoneNumberText();
			Validate.takeStepFullScreenShot("Expected Series Number is Selected", Status.INFO);

		}

		selectedNumber = selectedNumber.trim().replaceAll(" ", "").replace("(", "").replace(")", "").replaceAll("-",
				"");

		return selectedNumber;
	}

	/**
	 * 
	 * 
	 */
	public static void activateExistingNumber(String sub, String ban, String alternateContact, String OSP) {
		RKPortalPages_NEW RKPortalPage = new RKPortalPages_NEW();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.transferExistingNumberBtn);
		BaseSteps.Clicks.clickElement(RKPortalPage.transferExistingNumberBtn);

		enterExistingNumDetails_NEW(sub);

		BaseSteps.Waits.waitForElementVisibility(RKPortalPage.authrizeTransferChkBox, 90);
		BaseSteps.Clicks.clickElement(RKPortalPage.authrizeTransferChkBox);

		BaseSteps.Waits.waitForElementVisibilityLongWait(RKPortalPage.previousServiceProvider);
		BaseSteps.SendKeys.sendKey(RKPortalPage.previousServiceProvider, OSP);

		BaseSteps.SendKeys.sendKey(RKPortalPage.previousServiceProvider, Keys.TAB);

		BaseSteps.Waits.waitForElementToBeClickableLongWait(RKPortalPage.previousServiceProviderAccountNum);
		BaseSteps.SendKeys.sendKey(RKPortalPage.previousServiceProviderAccountNum, ban);

		BaseSteps.SendKeys.sendKey(RKPortalPage.alterateContactNumber, alternateContact);
		Reporting.logReporter(Status.INFO, "Ported In Number is : " + sub);
		Validate.takeStepFullScreenShot("Existing Number Portin Details", Status.INFO);

	}

}
