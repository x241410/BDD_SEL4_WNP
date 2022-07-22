package com.telus.wnp.steps;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.test.utils.Status;
import com.telus.wnp.pages.PublicMobilePages;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for Public Mobile page (Steps) > AUTHOR: x241410
 ****************************************************************************
 */
public class PublicMobilePageSteps extends BaseSteps {

	public static String activatedPublicMobileNumber = null;
	public static String activatedPublicMobileAccountNumber = null;

	/**
	 * This method is used to do create Public Mobile Number
	 * 
	 * @param testDataJson
	 *            This is input test data json object
	 * @param sub
	 *            This is subscriber number
	 * @param ban
	 *            This is subscriber ban
	 * @return nothing.
	 */
	public static void activatePublicMobileNumber(JSONObject testDataJson, String sub, String ban) {

		try {

			PublicMobilePages PublicMobilePages = new PublicMobilePages();

			LoginPageSteps.appLogin_PublicMobile();
			verifyPublicMobileHomePageIsDisplayed();

			BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.nextButton);
			BaseSteps.Clicks.clickElement(PublicMobilePages.nextButton);

			enterSIMDetails(testDataJson.getString("SIM_NUMBER"));
			BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.simValidationTickMark);

			BaseSteps.SendKeys.sendKey(PublicMobilePages.firstname, testDataJson.getString("FIRST_NAME"));

			BaseSteps.SendKeys.sendKey(PublicMobilePages.lastname, testDataJson.getString("LAST_NAME"));
			doSelectProvince(testDataJson);

			BaseSteps.SendKeys.sendKey(PublicMobilePages.postalcodeacct, testDataJson.getString("POSTAL_CODE"));

			String emailId = GenericUtils.getRandomEmailId();
			
			BaseSteps.SendKeys.sendKey(PublicMobilePages.email, emailId);
			BaseSteps.SendKeys.sendKey(PublicMobilePages.confirmemail, emailId);

			BaseSteps.SendKeys.sendKey(PublicMobilePages.confirmemail, Keys.TAB);
			BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.emiailIdConfirmedTickMark, 90);
			
			BaseSteps.SendKeys.sendKey(PublicMobilePages.password, testDataJson.getString("PASSWORD"));
			BaseSteps.Waits.waitGeneric(500);
			BaseSteps.SendKeys.sendKey(PublicMobilePages.pin, testDataJson.getString("PIN"));
			BaseSteps.Waits.waitGeneric(500);

			doSelectSecurityQuestion(testDataJson);
			BaseSteps.SendKeys.sendKey(PublicMobilePages.securityQuesAns, testDataJson.getString("SECURITY_ANS"));

			doSelectLanguage(testDataJson);

			BaseSteps.Waits.waitGeneric(500);
			BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
			
			Validate.takeStepFullScreenShot("Public Mobile SIM And Basic Details", Status.INFO);
			
			BaseSteps.Waits.waitForElementToBeClickable(PublicMobilePages.nextButton);
			BaseSteps.Debugs.scrollToElement(PublicMobilePages.nextButton);
			BaseSteps.Clicks.clickElement(PublicMobilePages.nextButton);

			BaseSteps.Waits.waitGeneric(1000);

			if (testDataJson.getBoolean("NEW_NUMBER_FLAG")) {

				activateNewPublicMobileSteps(testDataJson);

			} else {
				BaseSteps.Clicks.clickElement(PublicMobilePages.transferExitingNumber);
				BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.txtPhoneAreaCode);
				enterExistingNumDetails(sub);

				Validate.takeStepScreenShot("Entered Existing Number Details", Status.INFO);
				BaseSteps.Clicks.clickElement(PublicMobilePages.nextButton);
				BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.numebrTickMark);
				BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.authorizeChkBoxLbl, 90);
				BaseSteps.Clicks.clickElement(PublicMobilePages.authorizeChkBoxLbl);

				BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.osp);
				doSelectOldServiceProvider(testDataJson);

				BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.previousSubBanNum);
				BaseSteps.Debugs.scrollToElement(PublicMobilePages.previousSubBanNum);
				
				BaseSteps.SendKeys.sendKey(PublicMobilePages.previousSubBanNum, ban);
				BaseSteps.SendKeys.sendKey(PublicMobilePages.alternatenumber, sub);
				
				Validate.takeStepScreenShot("Entered Existing BAN Details", Status.INFO);

				
				BaseSteps.Waits.waitForElementToBeClickable(PublicMobilePages.nextButton);
				BaseSteps.Debugs.scrollToElement(PublicMobilePages.nextButton);

				BaseSteps.Waits.waitGeneric(500);
				BaseSteps.Clicks.clickElement(PublicMobilePages.nextButton);

			}

			BaseSteps.Clicks.clickElement(PublicMobilePages.nextButton);

			BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.selectFirstDefaultPlan);
			BaseSteps.Clicks.clickElement(PublicMobilePages.selectFirstDefaultPlan);
			
			Validate.takeStepScreenShot("Selected Plan", Status.INFO);


			BaseSteps.Waits.waitGeneric(1000);
			BaseSteps.Debugs.scrollToElement(PublicMobilePages.nextButton);
			BaseSteps.Clicks.clickElement(PublicMobilePages.nextButton);

			BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.payWithCreditCard);
			BaseSteps.Debugs.scrollToElement(PublicMobilePages.payWithCreditCard);
			BaseSteps.Clicks.clickElement(PublicMobilePages.payWithCreditCard);

			BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.cardnumber);
			BaseSteps.SendKeys.sendKey(PublicMobilePages.cardnumber, testDataJson.getString("CREDIT_CARD_NUMBER"));
			BaseSteps.SendKeys.sendKey(PublicMobilePages.postalcode2, testDataJson.getString("POSTAL_CODE"));
			BaseSteps.SendKeys.sendKey(PublicMobilePages.expirydatemmyy, testDataJson.getString("CREDIT_CARD_EXPIRY"));
			BaseSteps.SendKeys.sendKey(PublicMobilePages.cvvNumber, testDataJson.getString("CREDIT_CRAD_CVV"));

			BaseSteps.Debugs.scrollToElement(PublicMobilePages.agreeToTnCChkBox);
			BaseSteps.Clicks.clickElement(PublicMobilePages.agreeToTnCChkBox);
			
			Validate.takeStepScreenShot("Credit Card Details", Status.INFO);

			Validate.takeStepFullScreenShot("Before Activation", Status.INFO);

			// Need to uncomment below for final activation
			BaseSteps.Debugs.scrollToElement(PublicMobilePages.activateSimCardBtn);
			BaseSteps.Clicks.clickElement(PublicMobilePages.activateSimCardBtn);

			/*
			 * READ SUBSCRIBER NUMBER AND BAN DETAILS FOR PUBLIC MOBILE
			 */
			activatedPublicMobileNumber = getPublicMobileNumber();
			System.out.println("==================>");
			System.out.println(activatedPublicMobileNumber);
			System.out.println("==================>");

			activatedPublicMobileAccountNumber = getPublicMobileAccountNumber();
			System.out.println("==================>");
			System.out.println(activatedPublicMobileAccountNumber);
			System.out.println("==================>");

			Validate.takeStepFullScreenShot("Activated TN and BAN Details Captured", Status.PASS);

		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG,
					"Unable to activate Public Mobile number from Public Mobie Portal: " + e);
			Validate.assertTrue(false,
					"Exception Occured: Unable to activate Public Mobile number from Public Mobile Portal");
		}
	}

	/**
	 * This method is used to do verify home page is displayed after successful
	 * login into Public Mobile application.
	 * 
	 * @param none.
	 * @return nothing.
	 */
	public static void verifyPublicMobileHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> Public Mobile homepage is displayed after successful login ");

		PublicMobilePages PublicMobilePages = new PublicMobilePages();

		BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.homePageText);
		boolean actual = PublicMobilePages.isHomePageDisplayed();
		System.out.println("===========> Public Mobile");
		Validate.assertEquals(actual, true, "Unable to login into Public Mobile.", false);
	}

	/**
	 * Method Description: The purpose of this method is to enter sim details
	 * 
	 * @param sim
	 */
	public static void enterSIMDetails_old(String sim) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();

		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.firstsimdigits, sim.substring(0, 5));
		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.secondsimdigits, sim.substring(5, 10));
		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.thirdsimdigits, sim.substring(10, 15));
		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.fourthsimdigits, sim.substring(15));

	}

	/**
	 * Method Description: The purpose of this method is to enter sim details
	 * 
	 * @param sim
	 */
	public static void enterSIMDetails(String sim) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();

		String firstsimdigits = sim.substring(0, 5);
		String secondsimdigits = sim.substring(5, 10);

		JavascriptExecutor js = (JavascriptExecutor) WebDriverSession.getWebDriverSession();

		if (!firstsimdigits.contains("89122")) {
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PublicMobilePages.firstsimdigits);
			BaseSteps.Waits.waitGeneric(500);
			js.executeScript("arguments[0].value='';", PublicMobilePages.firstsimdigits);

			typeSimDigits(PublicMobilePages.firstsimdigits, firstsimdigits);
		}

		if (!secondsimdigits.contains("30000")) {
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PublicMobilePages.secondsimdigits);
			BaseSteps.Waits.waitGeneric(700);
			js.executeScript("arguments[0].value='';", PublicMobilePages.secondsimdigits);

			typeSimDigits(PublicMobilePages.secondsimdigits, secondsimdigits);

		}

		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.thirdsimdigits, sim.substring(10, 15));
		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.fourthsimdigits, sim.substring(15));
	}

	/**
	 * Method Description: The purpose of this method is to enter existing number
	 * details
	 * 
	 * @param sub
	 */
	public static void enterExistingNumDetails(String sub) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();
		String areaCode = sub.substring(0, 3);
		String firstThreeDigits = sub.substring(3, 7);
		String lastFourDigits = sub.substring(6);

		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.txtPhoneAreaCode, areaCode);
		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.txtPhoneFirstThreeDigitsOfNum, firstThreeDigits);
		BaseSteps.SendKeys.clearFieldAndSendKeys(PublicMobilePages.txtPhoneLastFourDigitsOfNum, lastFourDigits);
	}

	/*
	 * public static void listOfDivElements(JSONObject testDataJson) {
	 * PublicMobilePages PublicMobilePages = new PublicMobilePages(); JSONObject
	 * jObj = testDataJson.getJSONObject("PUBLIC_MOBILE_CONSTANTS");
	 * 
	 * 
	 * System.out.println("Second Province XPath=====>");
	 * System.out.println(PublicMobilePages.listOfElements.size());
	 * 
	 * 
	 * }
	 */

	/**
	 * Method Description: The purpose of this method is to select the security
	 * question
	 * 
	 * @param testDataJson
	 */
	public static void doSelectSecurityQuestion(JSONObject testDataJson) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();

		BaseSteps.Clicks.clickElement(PublicMobilePages.securityQuestion);
		BaseSteps.Waits.waitGeneric(1000);

		String secQ = testDataJson.getString("SECURITY_QUES");
		BaseSteps.Waits.waitForElementText(PublicMobilePages.securityQuestion, secQ);
		BaseSteps.Clicks.clickElement(WebDriverSession.getWebDriverSession()
				.findElement(By.xpath("//*[@id='question']//div[contains(text(), '" + secQ + "')]")));

	}

	/**
	 * Method Description: The purpose of this method is to select the old service
	 * provider question
	 * 
	 * @param testDataJson
	 */
	public static void doSelectOldServiceProvider(JSONObject testDataJson) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();

		BaseSteps.Clicks.clickElement(PublicMobilePages.osp);

		String ospName = testDataJson.getString("OLD_SERVICE_PROVIDER");
		BaseSteps.Waits.waitForElementText(PublicMobilePages.osp, ospName);
		BaseSteps.Clicks.clickElement(WebDriverSession.getWebDriverSession()
				.findElement(By.xpath("//*[@id='old-service-provider']//div[contains(text(), '" + ospName + "')]")));

	}

	/**
	 * Method Description: The purpose of this method is to select new subscriber
	 * city
	 * 
	 * @param testDataJson
	 */
	public static void doSelectNewPhoneCity(JSONObject testDataJson) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(PublicMobilePages.newPhoneCity);
		BaseSteps.Clicks.clickElement(PublicMobilePages.newPhoneCity);
		BaseSteps.Waits.waitGeneric(500);

		String newPhoneCity = testDataJson.getString("NEW_PHN_CITY_REGION");
		BaseSteps.Waits.waitForElementText(PublicMobilePages.newPhoneCity, newPhoneCity);

		BaseSteps.Clicks.clickElement(WebDriverSession.getWebDriverSession()
				.findElement(By.xpath("//*[@id='city']//div[contains(text(), '" + newPhoneCity + "')]")));

	}

	/**
	 * Method Description: The purpose of this method is to select province
	 * 
	 * @param testDataJson
	 */
	public static void doSelectProvince(JSONObject testDataJson) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();

		BaseSteps.Waits.waitForElementToBeClickableLongWait(PublicMobilePages.province);
		BaseSteps.Clicks.clickElement(PublicMobilePages.province);
		BaseSteps.Waits.waitGeneric(500);
		String province = testDataJson.getString("PROVINCE");
		BaseSteps.Waits.waitForElementText(PublicMobilePages.province, province);
		BaseSteps.Clicks.clickElement(WebDriverSession.getWebDriverSession()
				.findElement(By.xpath("//*[@id='province']//div[contains(text(), '" + province + "')]")));

	}

	/**
	 * Method Description: The purpose of this method is to select language
	 * 
	 * @param testDataJson
	 */
	public static void doSelectLanguage(JSONObject testDataJson) {

		BaseSteps.Waits.waitGeneric(1000);

		String lang = testDataJson.getString("SELECT_PREFERRED_LANGUAGE");
		BaseSteps.Clicks.clickElement(
				WebDriverSession.getWebDriverSession().findElement(By.xpath("//label[@for='" + lang + "']")));
	}

	/**
	 * Method Description: The purpose of this method is to get generated public
	 * mobile number
	 * 
	 * @return
	 */
	public static String getSelectedPublicMobileNumber_OLD() {
		if (activatedPublicMobileNumber != null)
			return activatedPublicMobileNumber;
		else
			return "";
	}

	/**
	 * Method Description: The purpose of this method is to fetch the value for
	 * Public Mobile Number
	 * 
	 * @return
	 */
	public static String getPublicMobileNumber() {
		PublicMobilePages PublicMobilePages = new PublicMobilePages();
		String publicMobileNumber = null;
		try {
			BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.publicMobileNum, 120);
			publicMobileNumber = PublicMobilePages.getActivatedpublicMobileNum();
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to get the activated Public Mobile Number " + e);
		}
		return publicMobileNumber;
	}

	/**
	 * Method Description: The purpose of this method is to fetch the value for
	 * Public Mobile Account Number
	 * 
	 * @return
	 */
	public static String getPublicMobileAccountNumber() {
		PublicMobilePages PublicMobilePages = new PublicMobilePages();
		String publicMobileAccountNumber = null;
		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(PublicMobilePages.accountNumber);
			publicMobileAccountNumber = PublicMobilePages.getAccountNum();
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to get the activated Public Mobile Account Number " + e);
		}
		return publicMobileAccountNumber;

	}

	/**
	 * Method Description: This method is used to activate new number for Public
	 * Mobile SIM
	 * 
	 * @param testDataJson
	 */
	public static void activateNewPublicMobileSteps(JSONObject testDataJson) {

		PublicMobilePages PublicMobilePages = new PublicMobilePages();
		BaseSteps.Clicks.clickElement(PublicMobilePages.newPhoneNumBtn);
		doSelectProvince(testDataJson);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		doSelectNewPhoneCity(testDataJson);
		BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.selectedPhoneNumber, 90);
		String selectedNumber = PublicMobilePages.getSelectedPhoneNum();

		String expectedSeries = testDataJson.getString("EXPECTED_SERIES");
		if (!selectedNumber.contains(expectedSeries)) {
			BaseSteps.Clicks.clickElement(PublicMobilePages.seeMoreOptionsBtn);
			BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.waitResumeOptionsElement, 30);
			BaseSteps.Clicks.clickElement(PublicMobilePages.waitResumeOptionsElement);

			int i = 0;
			while (!PublicMobilePages.clickExpectedFormatTN(expectedSeries)) {
				BaseSteps.Clicks.clickElement(PublicMobilePages.refreshOptionsBtn);

				try {
					BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.waitResumeOptionsElement, 30);
					BaseSteps.Clicks.clickElement(PublicMobilePages.waitResumeOptionsElement);
				} catch (Exception e) {
					BaseSteps.Waits.waitForElementVisibility(PublicMobilePages.selectedPhoneNumber, 90);
				}

				i = i + 1;
				if (i == 20)
					break;
			}
			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(PublicMobilePages.selectedPhoneNumber);
			selectedNumber = PublicMobilePages.getSelectedPhoneNum();

		}

		selectedNumber = selectedNumber.trim().replaceAll(" ", "").replace("(", "").replace(")", "").replaceAll("-",
				"");

		System.out.println("==================>");
		System.out.println("selectedNumber: " + selectedNumber);
		System.out.println("==================>");

		Validate.takeStepFullScreenShot("Capturing Generated Number", Status.INFO);
		Reporting.logReporter(Status.INFO, "Selected Public Mobile Number is : " + selectedNumber);
	}

	/**
	 * Method Description: The purpose of this method is to type sim digits
	 * 
	 * @param PublicMobilePages
	 * @param digits
	 */
	public static void typeSimDigits(WebElement ele, String digits) {

		for (int i = 0; i < digits.length(); i++) {

			int num = Integer.parseInt(digits.substring(i, i + 1));
			typeNumFromKeyboard(ele, num);
		}
	}

	/**
	 * 
	 * @param PublicMobilePages
	 * @param num
	 */
	public static void typeNumFromKeyboard(WebElement e, int num) {

		switch (num) {

		case 0:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD0);
			break;

		case 1:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD1);
			break;

		case 2:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD2);
			break;

		case 3:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD3);
			break;

		case 4:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD4);
			break;

		case 5:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD5);
			break;

		case 6:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD6);
			break;

		case 7:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD7);
			break;

		case 8:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD8);
			break;

		case 9:
			BaseSteps.SendKeys.sendKey(e, Keys.NUMPAD9);
			break;

		default:
			Reporting.logReporter(Status.INFO, "Enter a Valid Number !");

		}

	}

}
