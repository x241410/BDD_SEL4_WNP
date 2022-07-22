package com.telus.wnp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.ui.actions.WebDriverSession;

public class MSSPartnersPage extends WebDriverSession {

	public MSSPartnersPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//*[@id='dealerCode']")
	public WebElement dealerCode; // Dealer - 18

	@FindBy(xpath = "//td[2]//td[1]/a/img")
	public WebElement submit;

	@FindBy(xpath = "//a[contains(text(), 'Activate new customer')]")
	public WebElement activateNewCustomerLink;

	/**
	 * Modify Existing Account Section
	 */


	@FindBy(xpath = "//*[@id='first_name']")
	public WebElement firstName_p2p;

	@FindBy(xpath = "//*[@id='last_name']")
	public WebElement lastName_p2p;

	@FindBy(xpath = "//*[@id='phone_number']")
	public WebElement prepaidNum_p2p;

	@FindBy(xpath = "//*[@id='pin']")
	public WebElement pin_p2p;

	@FindBy(xpath = "//*[@id='set1_condition']")
	public WebElement idVeirifedCheckBox_p2p;

	@FindBy(xpath = "//*[@id='phone_number_radio']")
	public WebElement phoneNumberRadioBtn_p2p;

	@FindBy(xpath = "//*[@id='postal_code_radio']")
	public WebElement postalCoderadioBtn_p2p;

	@FindBy(xpath = "//*[@id='imei_sim_radio']")
	public WebElement last6SimDigits_p2p;

	@FindBy(xpath = "//*[@id='postal_code']")
	public WebElement postalCodeTextBox_p2p;

	@FindBy(xpath = "//*[@id='asubmit']")
	public WebElement submitBtn_p2p;

	// next page

	@FindBy(xpath = "//*[@id='main-right-actions-items']//a[contains(text(), 'Renew')]")
	public WebElement renewLink_p2p;

	// next page

	@FindBy(xpath = "//*[@id='device-status-options-not-defective']")
	public WebElement returningDeviceNoRadioBtn_p2p;

	@FindBy(xpath = "//*[@id='account_type_container']/div[2]/div[1]/div/label/span")
	public WebElement accountTypeConsumerRegular;

	@FindBy(xpath = "//*[@id='atSubmit']")
	public WebElement submitButton;

	@FindBy(xpath = "//*[@id='customerInformationPhotoid']")
	public WebElement photoIdDrpDwn; // BC Services Card

	@FindBy(xpath = "//*[@id='customerInformationFirstName']")
	public WebElement firstName;

	@FindBy(xpath = "//*[@id='customerInformationLastName']")
	public WebElement lastName;

	@FindBy(xpath = "//*[@id='customerInformationFullAddress']")
	public WebElement fullAddress; // 200 Consilium Place

	@FindBy(xpath = "//*[@id='customer-information-extended-address-link-container']/div/div[1]/a")
	public WebElement detailedAddress;

	@FindBy(xpath = "//*[@id='customerInformationStreetNumber']")
	public WebElement streetNumber; // 200

	@FindBy(xpath = "//*[@id='customerInformationStreetName']")
	public WebElement streetName; // Consilium Place

	@FindBy(xpath = "//*[@id='customerInformationCityTown']")
	public WebElement customerCity; // Toronto

	@FindBy(xpath = "//*[@id='customerInformationProvince']")
	public WebElement provinceDrpDwn; // Ontario

	@FindBy(xpath = "//*[@id='customerInformationPostalCode']")
	public WebElement postalCode; // M1H3E4

	@FindBy(xpath = "//*[@id='customerInformationPhoneNumber']")
	public WebElement homePhoneNumber;

	@FindBy(xpath = "//*[@id='customerInformationDateofbirthMonth']")
	public WebElement birthMonthDrpDwn; // Full Month Name- January, February, March.. Default MM

	@FindBy(xpath = "//*[@id='customerInformationDateofbirthDay']")
	public WebElement birthDateDrpDwn; // 1, 2,, 31 , . Default DD

	@FindBy(xpath = "//*[@id='customerInformationDateofbirthYear']")
	public WebElement birthYearDrpDwn; // from 1910, . Default YYYY

	// Credit Check Button
	@FindBy(xpath = "//*[@id='customer-information-cta']/a[1]")
	public WebElement creditCheckBtn;

	// After clicking on Confirm Credit Check button, need to select the SIN number
	@FindBy(xpath = "//*[@id='credit-check-sin-link']//a")
	public WebElement sinLink;

	@FindBy(xpath = "//*[@id='creditCheckSinNumber']")
	public WebElement enterSIN;

	@FindBy(xpath = "//*[@id='customer-information-driver-license-cta']/a")
	public WebElement enterSINSubmitButton;

	@FindBy(xpath = "//*[@id='credit-check-content']/div[8]//label/span")
	public WebElement consentCheckBox;

	@FindBy(xpath = "//*[@id='credit-check-cta']/a")
	public WebElement continueToPreferenceBtn;

	@FindBy(xpath = "//*[@id='preferencesPin']")
	public WebElement enterPIN;

	@FindBy(xpath = "//*[@id='customerInformationEmail']")
	public WebElement enterEmailId;

	@FindBy(xpath = "//*[@id='preferences-cta']/a")
	public WebElement continueToSubscriberInfoBtn;

	@FindBy(xpath = "//*[@id='subscriberInformationForm']/div[2]/div[1]/div[1]//label/span")
	public WebElement sameAsCustomerInfoCheckBox;

	@FindBy(xpath = "//*[@id='subscriber-information-cta']/div[2]/a")
	public WebElement submitButtonAferSubInforFilled;

	@FindBy(xpath = "//*[@id='duplicate-account-container']")
	public WebElement duplicateAccountExists;

	@FindBy(xpath = "//body/div[3]//div/div[3]/div/a")
	public WebElement createNewAccountBtnFromPopup;

	@FindBy(xpath = "//div[3]//div[1]/h4")
	public WebElement existingAccountFoundLabel;

	@FindBy(xpath = "//a[contains(text(), 'Create a new account')]")
	public WebElement createNewAccountBtn;

	@FindBy(xpath = "//*[@id='credit-assessment-results-ndp-cta']//a[contains(text(), 'Apply BYOD')]")
	public WebElement applyBYODButton;

	/*
	 * Port details
	 */

	@FindBy(xpath = "//*[@id='introduction-options-choose']")
	public WebElement choosePhoneNumberRadioBtn;

	@FindBy(xpath = "//*[@id='introduction-Province-container']/div[1]/span")
	public WebElement provinceLabel;

	@FindBy(xpath = "//*[@id='introduction-options-port']")
	public WebElement selectPortPhoneNumberRadioBtn;

	@FindBy(xpath = "//*[@id='introductionPhoneNumber']")
	public WebElement enterNumberToBePorted;

	/*
	 * @FindBy(xpath =
	 * "//*[@id='introduction-cta']/a[contains(text(), 'Continue to enter port details')]"
	 * ) public WebElement continueToEnterPortDetailsBtn;
	 */

	@FindBy(xpath = "//a[contains(text(), 'Continue to enter port details')]")
	public WebElement continueToEnterPortDetailsBtn;

	@FindBy(xpath = "//a[contains(text(), 'Continue to select phone number')]")
	public WebElement continueToSelectPhoneNumberBtn;

	@FindBy(xpath = "//select[@id='select-phone-number-choose-areacode-areacode']")
	public WebElement selectAreaCodeWithThreeDigits;

	@FindBy(xpath = "//div[2]//div[3]//div[3]/div[2]/a")
	public WebElement submitBtnOnPhoneNumSelectionPage;

	@FindBy(xpath = "//*[@id='portWirelessFormerAlternatePhoneNumber']")
	public WebElement contactPhoneNumber;

	@FindBy(xpath = "//*[@id='portWirelessAuthorized']")
	public WebElement authorizePortChkBox;

	@FindBy(xpath = "//*[@id='selectPhoneNumberPortInservice']")
	public WebElement confirmNumberInServiceChkBox;

	@FindBy(xpath = "//*[@id='select-phone-number-port-ban-cta']//a")
	public WebElement enterAccountNumberLink;

	@FindBy(xpath = "//*[@id='accountNumber']")
	public WebElement enterAccountNumber;

	@FindBy(xpath = "//*[@id='accountNumberReEnter']")
	public WebElement reEnterAccountNumber;

	@FindBy(xpath = "//*[@id='customer-information-driver-license-cta']/a")
	public WebElement confirmAccountNumberSubmitBtn;

	@FindBy(xpath = "//div[2]/div[4]/div[2]/a[text()='Submit']")
	public WebElement portDetailsSubmitBtn;

	@FindBy(xpath = "//div[2]/div[4]/div/div/input")
	public WebElement simOnlyActivationChkBox;

	@FindBy(xpath = "//div[2]/div[2]/div[6]/a[text()='Select a Device']")
	public WebElement selectADeviceBtn;

	@FindBy(xpath = "//*[@id='introductionOptionsDeviceSim']")
	public WebElement enterValidSIMNumber;

	@FindBy(xpath = "//*[@id='introductionOptionsDeviceType']")
	public WebElement deviceTypeDropDownOfferType;

	@FindBy(xpath = "//*[@id='introduction-cta']/div/div[2]/a")
	public WebElement continueToOffersPage;

	@FindBy(xpath = "//*[@id='program1011885-expand-icon']/span")
	public WebElement MSSAutomationConsumerExpand;

	// @FindBy(xpath =
	// "//*[@id='offers-content']/div[6]//div[2]/span[contains(text(), 'MSS
	// AUTOMATION')]")
	@FindBy(xpath = "//span[text()='MSS AUTOMATION CONSUMER']")
	public WebElement MSSAutomationConsumerOffer;

	// */span[text()='MSS AUTOMATION CONSUMER']
	/*
	 * @FindBy(xpath =
	 * "//*[@id='program1015314-details']/div[2]//label[1]/span[contains(text(), 'MSS AUTOMATION CONSUMER ACTIVATION SIMONLY')]"
	 * ) public WebElement desiredSIMOnlyOffer;
	 */
	@FindBy(xpath = "//*[@id='program1011885-details']/div[2]/div[1]//div[1]/div/div/label[1]")
	public WebElement desiredSIMOnlyFinanceOffer;

	// *[@id="program1015314-details"]/div[2]//label[1]/span

	@FindBy(xpath = "//*[@id='offers-cta']/a[contains(text(), 'Promotions')]")
	public WebElement continueToPromotionsPage;

	@FindBy(xpath = "//*[@id='perkOfferId-decline']")
	public WebElement declinePerkOfferId;

	@FindBy(xpath = "//*[@id='submit-perk-offer']")
	public WebElement submitPerkOffer;

	@FindBy(xpath = "//*[@id='promotions-cta']/div[3]/a")
	public WebElement continueToPricing;

	@FindBy(xpath = "//*[@id='bannertprint']/div[2]//div[6]/div[1]")
	public WebElement continueAfterPricing;

	@FindBy(xpath = "//*[@id='introductionNewRatePlan']")
	public WebElement newRatePlanDropDown;

	@FindBy(xpath = "//*[@id='introduction-cta']/a[1]")
	public WebElement continueToAddOnBtn;

	@FindBy(xpath = "//*[@id='add-ons-cta']/div[2]/a[1]")
	public WebElement continueToFinalPricing;

	@FindBy(xpath = "//*[@id='pricing-content']/div[2]/div[1]/div[1]")
	public WebElement easyRoamLabel;

	@FindBy(xpath = "//*[@id='easy-roam-acknowledgement']")
	public WebElement easyRoamChkBox;

	@FindBy(xpath = "//*[@id='bannertprint']/div[2]//div[6]/div[1]")
	public WebElement finalContinueBtn;

	@FindBy(xpath = "//*[@id='topOfPage']/div[3]/div[1]/div/div[2]/span")
	public WebElement orderConfirmationPage;

	@FindBy(xpath = "//*[@id='offer-hardware-extended-warranty-no-byod']")
	public WebElement doNotWantExtendedOfferRadioBtn;

	@FindBy(xpath = "//*[@id='bannertprint']/div[2]//div[8]/div[1]")
	public WebElement proceedToCheckoutBtn;

	@FindBy(xpath = "//*[@id='bannertprint']/div[2]//div[9]/div[1]")
	public WebElement saveAndShareBtn;

	@FindBy(xpath = "//*[@id='csagSentMethodRadios-mail']")
	public WebElement mailServiceAgreementCopyRadioBtn;

	@FindBy(xpath = "//*[@id='agreeToTermsCheck']")
	public WebElement finalAgreeCheckBox;

	@FindBy(xpath = "//*[@id='chreditCheck']")
	public WebElement finalCreditCheckBox;

	@FindBy(xpath = "//*[@id='bannertprint']/div[2]/div//div[9]/div[7]")
	public WebElement finalSubmitButton;

	/*
	 * Confirmation Page Verification Elements
	 * 
	 */

	@FindBy(xpath = "//*[@id='page']//div[2]/div/a//span[1]")
	public WebElement tnToBeActivated;

	@FindBy(xpath = "//*[@id='page']/div[6]/div[2]/table/tbody/tr[2]/td[1]")
	public WebElement activatedAccountNumber;

	@FindBy(xpath = "//*[@id='page']//div[2]/div/a/h5/span[2]/font")
	public WebElement statusOfActivationProcess;

	@FindBy(xpath = "//*[@id='customer-information-driver-license-cta']/a[text()='Continue']")
	public WebElement continueButtonBeforeMovingToAddOn;

	@FindBy(xpath = "//*[@id='customer-information-driver-license-cta']/a[text()='Cancel']")
	public WebElement cancelButtonBeforeMovingToAddOn;

	/*
	 * @FindBy(xpath = "//*[@id='page']//div[2]/div/a/h5/span[1]") public WebElement
	 * activatedSubscriberNumber;
	 */

	// MSS Partners Page- Pre to Post Migration

	@FindBy(xpath = "//*[@id='selectedLocationSelect']")
	public WebElement selectLocationOutlet;

	@FindBy(xpath = "//a/img[contains(@src, 'submit')]")
	public WebElement submitButtonSetLocationPage;

	@FindBy(xpath = "//a/img[contains(@src, 'reset')]")
	public WebElement resetButtonSetLocationPage;

	@FindBy(xpath = "//a/img[contains(@src, 'carrier_telus')]")
	public WebElement partnersCarrierSelectionPageTelus;

	@FindBy(xpath = "//a/img[contains(@src, 'carrier_koodo')]")
	public WebElement partnersCarrierSelectionPageKoodo;

	@FindBy(xpath = "//a[contains(text(), 'Partners content page')]")
	public WebElement partnersContentHomePage;

	@FindBy(xpath = "//a[contains(text(), 'Modify an existing account')]")
	public WebElement modifyExistingAccount;

	@FindBy(xpath = "//a[contains(text(), 'Partners')]")
	public WebElement partnersHomePage;

	@FindBy(xpath = "//a[contains(text(), 'upgrade, replace or renew your service')]")
	public WebElement linkToUpgradeSubscriber;

	@FindBy(xpath = "//span[contains(text(), 'Offer Type')]")
	public WebElement offerTypeText;

	@FindBy(xpath = "//span[contains(text(),'BIB device')]")
	public WebElement bibDeviceLabel;
	
	
	@FindBy(xpath = "//*[@id='device-status-options-not-defective']")
	public WebElement notADefectiveDeviceRadioBtn;

	@FindBy(xpath = "//*[@id='ex1_value']")
	public WebElement searchDeviceTextBox;

	@FindBy(xpath = "//*[@id='ex1_dropdown']//a[text()='select']")
	public WebElement selectDeviceAfterSearch;

	@FindBy(xpath = "//*[@id='introductionOptionsDeviceImei']")
	public WebElement imeiInputBox;

	@FindBy(xpath = "//*[@id='introduction-confirmation-content']/div[5]/span/text()")
	public WebElement enterValidSIMNumber_Temp;

	// MIGRATION Page

	@FindBy(xpath = "//span[contains(text(), 'Migration')]")
	public WebElement migrationPageText;

	@FindBy(xpath = "//*[@id='accountSelectionRadio1']")
	public WebElement consumerRegularRadioBtn_mpage;

	@FindBy(xpath = "//*[@id='account-selection-cta']/a[1]")
	public WebElement continueToSutomerInfo_mpage;

	@FindBy(xpath = "//form//div[2]/div[2]/div/div[1]/div[6]")
	public WebElement validSimTickMark;

	@FindBy(xpath = "//form//div[2]/div[1]/div/div[1]/div[6]")
	public WebElement validIMEITickMark;

	public boolean isHomePageDisplayed() {
		return accountTypeConsumerRegular.isDisplayed();

	}

	public boolean isPartnersContentHomePageDisplayed() {
		return partnersContentHomePage.isDisplayed();

	}

	public boolean existingAccoundFoundPopUpDisplayed() {
		boolean stat = false;
		if (existingAccountFoundLabel.getText().contains("Existing Account Found")) {
			stat = true;
		}
		return stat;
	}

	public boolean isDuplicateAccountExists() {

		return duplicateAccountExists.isDisplayed();
	}

	public String getTNToBeActivatedText() {

		try {
			return tnToBeActivated.getText();
		} catch (Exception e) {
			return "";
		}

	}

	public String getActivatedAccountNumberText() {

		try {
			return activatedAccountNumber.getText();
		} catch (Exception e) {
			return "";
		}

	}

	public String getActivationStatusText() {
		try {
			return statusOfActivationProcess.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public WebElement searchForSpecificConsumerOffer(String offerName) {
		// span[text()='MSS AUTOMATION CONSUMER']

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + offerName + "']")));

	}

	public WebElement expandSpecificConsumerOffer(String OfferIdNumber) {
		// *[@id='program1011885-expand-icon']/span
		// *[@id='program1016656-expand-icon']/span

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='program" + OfferIdNumber + "-expand-icon']/span")));

	}

	public WebElement searchForSpecificPromotionOffer(String promotionName) {
		// div[@id='promotionId-1035655']

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='promotionId-" + promotionName + "']")));

	}

	public WebElement searchForSpecificPromotionOfferByName_Migration(String promotionName) {
		// ESS Reward Bill Credit EN

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(@id,'promotionId')]//b[contains(text(), '" + promotionName + "')]")));

	}

	public WebElement clickMSSAutomationOfferExpandableButton(String programId) {
		// *[@id="program1015314-expand-icon"]/span

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id='program" + programId + "-expand-icon']/span")));

	}

	public WebElement selectSpecificOfferForActivation(String offerType) {
		// span[text()='MSS AUTOMATION CONSUMER ACTIVATION SIMONLY FINANCE']

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + offerType + "']")));

	}

	public WebElement selectSpecificOfferForActivation_NEW(String offerType) {
		// span[text()='MSS AUTOMATION CONSUMER ACTIVATION SIMONLY FINANCE']

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//label/span[text()='" + offerType + "']")));

	}

	public WebElement selectSpecificCarrierType(String carrierName) {
		// a/img[contains(@src, 'carrier_koodo')]

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a/img[contains(@src, 'carrier_" + carrierName + "')]")));

	}


}
