package com.telus.wnp.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.WebDriverSession;

public class RKPortalPages extends WebDriverSession {

	public RKPortalPages() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//*[@id='txtSIM']")
	public WebElement SIMNumber;

	@FindBy(xpath = "//*[@id='txtFirstName']")
	public WebElement firstName;

	@FindBy(xpath = "//*[@id='txtLastName']")
	public WebElement lastName;

	@FindBy(xpath = "//*[@id='txtStreetNum']")
	public WebElement streetNum;

	@FindBy(xpath = "//*[@id='txtStreetname']")
	public WebElement streetName;

	@FindBy(xpath = "//*[@id='txtCity']")
	public WebElement city;

	@FindBy(xpath = "//select[@id='ddlProvince']")
	public WebElement province;

	@FindBy(xpath = "//select[@id='ddlCountry']")
	public WebElement country;

	@FindBy(xpath = "//*[@id='txtPostalCode']")
	public WebElement postalCode;

	@FindBy(xpath = "//*[@id='txtUserName']")
	public WebElement userEmail;

	@FindBy(xpath = "//*[@id='txtConfirmUserName']")
	public WebElement confirmUserEmail;

	@FindBy(xpath = "//*[@id='txtPassword']")
	public WebElement password;

	@FindBy(xpath = "//*[@id='txtConfirmPassword']")
	public WebElement confirmPassword;

	@FindBy(xpath = "//select[contains(@id, 'SecurityQuestionsDropDown')]")
	public WebElement securityQuestion;

	@FindBy(xpath = "//input[contains(@id, 'AnswerTextBox')]")
	public WebElement securityAnswer;

	@FindBy(xpath = "//select[@id='ddlPreferredLanguage']")
	public WebElement preferredLanguage;

	@FindBy(xpath = "//*[@id='txtChoosePIN']")
	public WebElement pin;

	@FindBy(xpath = "//*[@id='txtConfirmPIN']")
	public WebElement confirmPin;

	@FindBy(xpath = "//*[@id='chkBoxTOS']")
	public WebElement acceptTOSChkBox;

	@FindBy(xpath = "//input[contains(@name, 'NextButton')]")
	public WebElement continueBtn;

	@FindBy(xpath = "//*[@id='ValidationSummary1']")
	public WebElement unableToProceedErrorMsgs;

	/*
	 * Next page opens
	 * 
	 */

	@FindBy(xpath = "//*[@id='radioOptions_0']")
	public WebElement selectNewNumber;

	@FindBy(xpath = "//select [@aria-label='City / Region']")
	public WebElement selectCityRegion;

	@FindBys(@FindBy(xpath = "//select [@id='MainContent_WebForm1_ctl00_NumberLB']/option"))
	public List<WebElement> pickYourNumList;

	@FindBy(xpath = "//*[@id='MainContent_WebForm1_ctl00_selectedLabel']")
	public WebElement selectedMobileNumber;

	@FindBy(xpath = "//select [@aria-label='Province']")
	public WebElement selectProvince;

	@FindBy(xpath = "//*[@id='radioOptions_1']")
	public WebElement transferExistingNumRadioBtn;

	@FindBy(xpath = "//*[@id='txtPhone1' or @aria-label='Area Code']")
	public WebElement txtPhoneAreaCode;

	@FindBy(xpath = "//*[@id='txtPhone2' or @aria-label='First three Digits of Phone Number']")
	public WebElement txtPhoneFirstThreeDigitsOfNum;

	@FindBy(xpath = "//*[@id='txtPhone3' or @aria-label='Last 4 Digits of Phone Number']")
	public WebElement txtPhoneLastFourDigitsOfNum;

	@FindBy(xpath = "//*[@id='checkEligibilityBtn']")
	public WebElement checkEligibilityBtn;

	/*
	 * Next page opens
	 * 
	 */
	@FindBy(xpath = "//input[contains(@id, 'OldAccount')]")
	public WebElement oldAccount;

	@FindBy(xpath = "//input[contains(@id, 'AlternatePhone')]")
	public WebElement alternateContactNumber;

	@FindBy(xpath = "//input[contains(@id, 'AuthorizationName')]")
	public WebElement oldServiceProviderAccountName;

	@FindBy(xpath = "//*[@id='chkAuthorized']")
	public WebElement checkAuthorizedCheckBox;

	/*
	 * Next page opens
	 * 
	 */
	@FindBy(xpath = "//select [@id='ddlPlan']")
	public WebElement basePlan;

	/*
	 * Next page opens
	 * 
	 */

	/*
	 * CREDIT CARD INFORMATION PAGE
	 */

	@FindBy(xpath = "//*[@id='ddlPaymentDropDown']")
	public WebElement paymentDropDown;

	@FindBy(xpath = "//*[@id='creditCardNumber']")
	public WebElement creditCardNumber;

	@FindBy(xpath = "//*[@id='ExpiryMonthDropDownList']")
	public WebElement expiryMonthDropDwon;

	@FindBy(xpath = "//*[@id='ExpiryYearDropDownList']")
	public WebElement expiryYearDropDwon;

	@FindBy(xpath = "//*[@id='cvv2']")
	public WebElement cvv2;

	@FindBy(xpath = "//*[@id='FirstName']")
	public WebElement creditCardFirstName;

	@FindBy(xpath = "//*[@id='LastName']")
	public WebElement creditCardLastName;

	@FindBy(xpath = "//*[@id='street']")
	public WebElement creditCardStreetAddress;

	@FindBy(xpath = "//*[@id='City']")
	public WebElement creditCardCity;

	@FindBy(xpath = "//*[@id='Country']")
	public WebElement creditCardCountry;

	@FindBy(xpath = "//*[@id='Province']")
	public WebElement creditCardProvince;

	@FindBy(xpath = "//*[@id='zip']")
	public WebElement creditCardZipCode;

	@FindBy(xpath = "//*[@id='RadioButtonDoNotRegisterForATU']")
	public WebElement doNotRegisterRadioBtn;

	@FindBy(xpath = "//*[@id='summary']/section[1]/div[2]/div/span[4]")
	public WebElement kpreAccountNumber;

	@FindBy(xpath = "//*[@id='summary']/section[1]/div[3]/div/span[4]")
	public WebElement kprePhoneNumber;

	@FindBy(xpath = "//*[@id='summary']/section[1]/div[2]/div/span[2]")
	public WebElement subscriberName;

	@FindBy(xpath = "//h1[contains(text(), 'Summary')]")
	public WebElement summaryPageLabel;

	@FindBy(xpath = "//input[contains(@name, 'NextButton')]")
	public WebElement clickFinish;

	public String getSelectedMobileNumber() {
		return selectedMobileNumber.getText();

	}

	public boolean getSIMReservedErrorMsg() {
		return unableToProceedErrorMsgs.getText().contains("reserved by another session");

	}

	public boolean getInvalidSIMErrorMsg() {
		return unableToProceedErrorMsgs.getText().contains("Invalid SIM");

	}

	public String getKPREPhoneNumber() {
		return kprePhoneNumber.getText();

	}

	public String getKPREAccountNumber() {
		return kpreAccountNumber.getText();

	}

	public boolean isFinishBtnEnabled() {
		return continueBtn.isEnabled();

	}

	public boolean isHomePageDisplayed() {
		return SIMNumber.isDisplayed();
	}

}
