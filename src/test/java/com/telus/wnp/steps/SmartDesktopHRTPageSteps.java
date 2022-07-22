package com.telus.wnp.steps;

import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.test.utils.Status;
import com.telus.wnp.pages.ATTPage;
import com.telus.wnp.pages.SmartDesktopHRTPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;

/**
 ****************************************************************************
 *
 * > DESCRIPTION: Support for Smart Desktop - HRT page Steps > AUTHOR: x241410
 ****************************************************************************
 */

public class SmartDesktopHRTPageSteps extends BaseSteps {

	public static void performPostToPreMigration(JSONObject testDataJson) {

		Reporting.logReporter(Status.INFO, "STEP=========HRT Offer Assignment=========");

		try {

			String parentWindow = WebDriverSession.getWebDriverSession().getWindowHandle();
			SmartDesktopHRTPage SmartDesktopHRTPage = new SmartDesktopHRTPage();

			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtLink);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtLink);

			BaseSteps.Windows.switchToNewWindow();
			String HRTPageWindow = WebDriverSession.getWebDriverSession().getWindowHandle();

			BaseSteps.JavaScripts.handleInvalidCertificateError();
			BaseSteps.Waits.waitForAjaxLoaderSpinnerInvisibility(3, 90);

			String offername = testDataJson.getString("REQUIRED_OFFER_TEXT");
			assignSpecificOffer(SmartDesktopHRTPage, testDataJson, offername);

			long t1 = Thread.currentThread().getId();
			WebDriver session1 = WebDriverSession.getWebDriverForCurrentThreat();
			Reporting.logReporter(Status.INFO, "HRT Original Session: " + session1.toString());

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtRedeemLink);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtRedeemLink);
			BaseSteps.Waits.waitForAjaxLoaderSpinnerInvisibility(3, 60);
			BaseSteps.Waits.waitGeneric(3000);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtRedemptionMethodDrpDwn);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtRedemptionMethodDrpDwn);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopHRTPage.hrtRedemptionMethodDrpDwn,
					testDataJson.getString("REDEMPTION_OPTION_VALUE"));

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtRedemptionMethodValidateBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtRedemptionMethodValidateBtn);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtMigrationReasonDrpDwn);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopHRTPage.hrtMigrationReasonDrpDwn,
					testDataJson.getString("MIGRATION_REASON_VALUE"));

			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtStarterKitTypeDrpDwn);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopHRTPage.hrtStarterKitTypeDrpDwn,
					testDataJson.getString("STARTER_KIT_NOT_REQ"));

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtStarterKitValidateBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtStarterKitValidateBtn);

			Validate.takeStepScreenShot("Starter Kit Selection", Status.INFO);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtTitleDrpDwn);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtTitleDrpDwn);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopHRTPage.hrtTitleDrpDwn,
					testDataJson.getString("TITLE"));

			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtEmailId);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtEmailId);
			BaseSteps.SendKeys.clearFieldAndSendKeys(SmartDesktopHRTPage.hrtEmailId, GenericUtils.getRandomEmailId());
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtPin);
			BaseSteps.SendKeys.sendKey(SmartDesktopHRTPage.hrtPin, testDataJson.getString("HRT_PIN"));
			BaseSteps.SendKeys.sendKey(SmartDesktopHRTPage.hrtConfirPin, testDataJson.getString("HRT_PIN"));
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtAccountFormSubmitBtn);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtAccountFormSubmitBtn);

			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtAccountFormSubmitBtn);
			handleSuggestedAddressPopUpifDisplayed(SmartDesktopHRTPage);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtSIMOnlyChkBox);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtSIMOnlyChkBox);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtSIMOnlyChkBox);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtKeepExistingSIMChkBox);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtEquipmentValidateBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtEquipmentValidateBtn);
			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtDeviceTypeDrpDwn);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopHRTPage.hrtDeviceTypeDrpDwn,
					testDataJson.getString("DEVICE_TYPE_VALUE"));

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtDeviceTypeValidateBtn);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtDeviceTypeValidateBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtDeviceTypeValidateBtn);
			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.hrtPPAndSValidateBtn, 60);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtRatePlanTopUpDrpDwn);
			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopHRTPage.hrtRatePlanTopUpDrpDwn,
					testDataJson.getString("RATE_PLAN_TOPUP_VALUE"));

			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtAddTopUpAmountLinkBtn);
			SmartDesktopHRTPage.selectPriceAndPlan(testDataJson.getString("SELECT_PRICE_AND_PLAN_VALUE"));
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtMoveToRightPaneBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtMoveToRightPaneBtn);

			BaseSteps.Waits.waitForAjaxLoaderSpinnerInvisibility(3, 60);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtPPAndSValidateBtn);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtPPAndSValidateBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtPPAndSValidateBtn);
			Validate.takeStepFullScreenShot("Details Before Review", Status.INFO);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtFinalReviewBtn);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtFinalReviewBtn);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtFinalReviewBtn);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtOfferTermsChkBox);

			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtOfferTermsChkBox);

			if (!SmartDesktopHRTPage.hrtOfferTermsChkBox.isSelected()) {
				BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtOfferTermsChkBox);
			}

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtAuthorizeTelusChkBox);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.hrtAuthorizeTelusChkBox);

			if (!SmartDesktopHRTPage.hrtAuthorizeTelusChkBox.isSelected()) {
				BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtAuthorizeTelusChkBox);
			}

			Validate.takeStepFullScreenShot("HRT Page Details before Credit Card Details Retrieved", Status.INFO);

			WebDriverSession.map.remove(t1);
			Reporting.logReporter(Status.INFO, "Removed HRT Original Session from Map: " + session1.toString());

			insertCardDetailsFromATT(testDataJson);

			WebDriverSession.map.put(t1, session1);
			Reporting.logReporter(Status.INFO, "Switching back to HRT Original Session: " + session1.toString());

			WebDriverSession.getWebDriverSession().switchTo().window(HRTPageWindow);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.retrieveCreditCardDetailsBtn);

			JavascriptExecutor js = (JavascriptExecutor) WebDriverSession.getWebDriverSession();
			js.executeScript("arguments[0].scrollIntoView(true);", SmartDesktopHRTPage.retrieveCreditCardDetailsBtn);
			BaseSteps.Waits.waitGeneric(200);
			SmartDesktopHRTPage.retrieveCreditCardDetailsBtn.click();
			Validate.takeStepFullScreenShot("HRT Page Details after Credit Card Details Retrieved", Status.INFO);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtFinalReviewSubmitBtn);
			js.executeScript("arguments[0].scrollIntoView(true);", SmartDesktopHRTPage.hrtFinalReviewSubmitBtn);
			BaseSteps.Waits.waitGeneric(200);
			SmartDesktopHRTPage.hrtFinalReviewSubmitBtn.click();

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.finalOKButtonFromPopUp);
			js.executeScript("arguments[0].click();", SmartDesktopHRTPage.finalOKButtonFromPopUp);
			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.redemptionSummary, 30);

			boolean stat = false;
			try {
				BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.viewErrorBtn, 60);
				stat = SmartDesktopHRTPage.viewErrorBtn.isDisplayed();
			} catch (Exception e) {
				Reporting.logReporter(Status.INFO, "No error observed");
			}

			Validate.takeStepFullScreenShot("Final Status Of The Request", Status.INFO);
			Validate.assertEquals(stat, false, "Error is displayed", false);

			BaseSteps.Windows.closeUniqueWindow(HRTPageWindow);
			BaseSteps.Windows.closeUniqueWindow(parentWindow);

		} catch (Exception e) {

			Reporting.logReporter(Status.DEBUG, "Unable to perform Post to Pre Migartion from HRT" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to perform Post to Pre Migartion from HRT");
			WebDriverSession.getWebDriverSession().close();
		}

	}

	public static void retrieveCreditCardDetails(SmartDesktopHRTPage SmartDesktopHRTPage, WebDriver session1) {

		try {

			BaseSteps.Waits.waitForElementToBeClickable(SmartDesktopHRTPage.retrieveCreditCardDetailsBtn);
			JavascriptExecutor js = (JavascriptExecutor) session1;
			js.executeScript("arguments[0].scrollIntoView(true);",
					SmartDesktopHRTPage.getRetrieveCreditCardDetails(session1));
			BaseSteps.Waits.waitGeneric(500);
			SmartDesktopHRTPage.getRetrieveCreditCardDetails(session1).click();

			String msg = SmartDesktopHRTPage.creditCardUnavailableMsgNotDisplayed(session1);
			if (msg.isEmpty()) {
				Reporting.logReporter(Status.INFO, "Credit Card details retrieved successfully");
			} else {
				String expectedMsg = "New Credit Card data unavailable";
				if (msg.toUpperCase().contains(expectedMsg.toUpperCase())) {
					Reporting.logReporter(Status.INFO, "Unable to retrieve Credit Card details");
				}
			}
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to retrieve Credit Card details");

		}

	}

	public static void captureScreenshot() {
		WebDriverSession.map.clear();
		WebDriverSession.getWebDriverForCurrentThreat();
		Validate.takeStepScreenShot("Details before final submit", Status.INFO);

	}

	public static void performValidationAndCaptureScreenshot(boolean expectedStatus) {

		Validate.assertEquals(true, expectedStatus, "Redemption Summary Not Displayed", false);
		Validate.takeStepScreenShot("Postpaid to Prepaid Migration is Successful", Status.INFO);
	}

	public static void handleSuggestedAddressPopUpifDisplayed(SmartDesktopHRTPage SmartDesktopHRTPage) {

		try {
			BaseSteps.Waits.waitForElementInvisibilityLongWait(SmartDesktopHRTPage.addressAlternative);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.acceptSuggestedAddress);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.acceptSuggestedAddress);
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "No pop-up displayed for alternative address");
		}
	}

	public static void assignSpecificOffer(SmartDesktopHRTPage SmartDesktopHRTPage, JSONObject testDataJson,
			String offerName) {
		try {
			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.hrtAvailableOffersList, 90);
			if (SmartDesktopHRTPage.clickPostToPreOffer(testDataJson.getString("REQUIRED_OFFER_TEXT"))) {
				Reporting.logReporter(Status.INFO, "Required offer was available on the homepage");

			}

			else {
				Reporting.logReporter(Status.INFO, "Required offer not available on the homepage");
				requiredOffer(SmartDesktopHRTPage, testDataJson);
			}
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to assign specific offer");

		}

	}

	public static void requiredOffer(SmartDesktopHRTPage SmartDesktopHRTPage, JSONObject testDataJson) {
		try {

			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.hrtAssignTab, 90);
			BaseSteps.Waits.waitGeneric(3000);
			BaseSteps.Debugs.scrollToElement(SmartDesktopHRTPage.offerAssignmentToolText);
			BaseSteps.Waits.waitForElementToBeClickable(SmartDesktopHRTPage.hrtAssignTab);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtAssignTab);

			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.hrtTransactionTypeDropDown, 60);

			BaseSteps.Dropdowns.selectByVisibleText(SmartDesktopHRTPage.hrtTransactionTypeDropDown,
					testDataJson.getString("OFFER_TYPE"));
			BaseSteps.Waits.waitForAjaxLoaderSpinnerInvisibility(3, 60);

			BaseSteps.Waits.waitForElementToBeClickable(SmartDesktopHRTPage.hrtSearchButton);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtSearchButton);

			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.hrtAvailableOffers, 60);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtAvailableOffers);

			SmartDesktopHRTPage.clickOnAssignSpecificOfferLink(testDataJson.getString("REQUIRED_OFFER_TEXT"));

			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.hrtOkBtnFromPopUp);
			BaseSteps.Clicks.clickElement(SmartDesktopHRTPage.hrtOkBtnFromPopUp);

			BaseSteps.Waits.waitForElementVisibilityLongWait(SmartDesktopHRTPage.hrtOfferAssignmentSuccessMsgLabel);

			String actualText = SmartDesktopHRTPage.getHRTOfferAssignmentSuccessMsgLabelText();
			String expectedText = "Success";

			Validate.assertEquals(actualText, expectedText, "HRT - Offer assignment is successful", false);

			BaseSteps.Waits.waitForAjaxLoaderSpinnerInvisibility(3, 90);
			BaseSteps.Clicks.clickElementUsingJS(SmartDesktopHRTPage.hrtHomeTab);

			BaseSteps.Waits.waitForAjaxLoaderSpinnerInvisibility(2, 60);
			BaseSteps.Waits.waitForElementVisibility(SmartDesktopHRTPage.hrtAvailableOffersList, 120);

			BaseSteps.Waits.waitForElementToBeClickableLongWait(SmartDesktopHRTPage.hrtAvailableOffersList);

			BaseSteps.Waits.waitGeneric(3000);

			SmartDesktopHRTPage.clickPostToPreOffer(testDataJson.getString("REQUIRED_OFFER_TEXT"));

		} catch (Exception e1) {
			Reporting.logReporter(Status.INFO, "Unable to assign offer from HRT" + e1);
			Validate.assertTrue(false, "Exception Occured: nable to assign offer from HRT");
		}
	}

	/**
	 * Method Description: This method is used to insert Credit Card details from AT
	 * application
	 */
	public static void insertCardDetailsFromATT(JSONObject testDataJson) {
		try {

			WebDriverSession.loadNewWebDriverSession();

			WebDriver session = WebDriverSession.getWebDriverForCurrentThreat();
			long t2 = Thread.currentThread().getId();
			Reporting.logReporter(Status.INFO, "ATT Session: " + session.toString());

			LoginPageSteps.appLogin_ATT();

			String parentWindow = WebDriverSession.getWebDriverForCurrentThreat().getWindowHandle();
			ATTPage ATTPage = new ATTPage();
			BaseSteps.Windows.switchToNewWindow();
			String childWindow = WebDriverSession.getWebDriverForCurrentThreat().getWindowHandle();

			BaseSteps.Waits.waitGeneric(5000);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(ATTPage.creditCardInfoCaptureButton);
			BaseSteps.Clicks.clickElement(ATTPage.creditCardInfoCaptureButton);

			BaseSteps.Waits.waitGeneric(8000);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(ATTPage.resizableWindow);
			BaseSteps.Clicks.clickElement(ATTPage.resizableWindow);
			WebDriverSession.getWebDriverSession().switchTo().frame(ATTPage.frameToEnterCCDetails);

			BaseSteps.Waits.waitGeneric(2000);
			BaseSteps.Waits.waitForElementToBeClickableLongWait(ATTPage.okButtonFromPopUp);

			BaseSteps.SendKeys.sendKey(ATTPage.ccNumber, testDataJson.getString("CC_NUMBER"));
			BaseSteps.SendKeys.sendKey(ATTPage.cvvNumber, testDataJson.getString("CC_CVV"));
			BaseSteps.Dropdowns.selectByGoingThroughList(ATTPage.expiryMonth,
					testDataJson.getString("CC_EXPIRY_MONTH"));
			BaseSteps.Dropdowns.selectByGoingThroughList(ATTPage.expiryYear, testDataJson.getString("CC_EXPIRY_YEAR"));

			Validate.takeStepScreenShot("Credit Card Details");

			BaseSteps.Clicks.clickElement(ATTPage.okButtonFromPopUp);

			WebDriverSession.getWebDriverSession().switchTo().defaultContent();

			Validate.takeStepScreenShot("Entered Credit Card Details");

			BaseSteps.Waits.waitForElementToBeClickableLongWait(ATTPage.submitButton);
			BaseSteps.Clicks.clickElement(ATTPage.submitButton);

			BaseSteps.Waits.waitForElementVisibilityLongWait(ATTPage.confirmationMsg);

			String text = ATTPage.getConfirmationMessage();

			Validate.assertContains(text, "Credit card information successfully stored", false);
			BaseSteps.Windows.closeUniqueWindow(parentWindow);
			BaseSteps.Windows.closeUniqueWindow(childWindow);

			WebDriverSession.map.remove(t2);
			Reporting.logReporter(Status.INFO, "Removed ATT Session from Map: " + session.toString());

		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Unable to insert card details from ATT" + e);
			WebDriverSession.getWebDriverForCurrentThreat().close();

		}
	}

}