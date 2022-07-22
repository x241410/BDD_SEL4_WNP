package com.telus.wnp.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.WebDriverSession;

public class RKPortalPages_NEW extends WebDriverSession {

	public RKPortalPages_NEW() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//*[@id='root']//button[text()='Get started']")
	public WebElement getStartedButton;

	@FindBy(xpath = "//*[@id='sim']")
	public WebElement SIMNumber;

	@FindBy(xpath = "//*[@id='firstName']")
	public WebElement firstName;

	@FindBy(xpath = "//*[@id='lastName']")
	public WebElement lastName;

	@FindBy(xpath = "//input[@type='search']")
	public WebElement province;

	@FindBy(xpath = "//*[@id='postalCode']")
	public WebElement postalCode;

	@FindBy(xpath = "//*[@id='email']")
	public WebElement userEmail;

	@FindBy(xpath = "//*[@id='email-confirm']")
	public WebElement confirmUserEmail;

	@FindBy(xpath = "//img[@data-testid='accountSetup-confirmEmail-valid']")
	public WebElement emiailIdConfirmedTickMark;

	@FindBy(xpath = "//input[@data-testid='accountSetup-password']")
	public WebElement password;

	@FindBy(xpath = "//input[@data-testid='accountSetup-confirmPassword']")
	public WebElement confirmPassword;

	@FindBy(xpath = "//select[@data-testid='accountSetup-securityQuestion']")
	public WebElement securityQuestion;

	@FindBy(xpath = "//input[@data-testid='accountSetup-securityAnswer']")
	public WebElement securityAnswer;

	@FindBy(xpath = "//input[@data-testid='accountSetup-pin']")
	public WebElement pin;

	@FindBy(xpath = "//input[@data-testid='accountSetup-confirmPin']")
	public WebElement confirmPin;

	@FindBy(xpath = "//input[@data-testid='accountSetup-languageEnglish']")
	public WebElement preferredLanguage_EN;

	@FindBy(xpath = "//input[@data-testid='accountSetup-languageFrench']")
	public WebElement preferredLanguage_FR;

	@FindBy(xpath = "//button[@data-testid='accountSetup-nextStep']")
	public WebElement step2PhoneNumberBtn;

	// NEXT PAGE

	@FindBy(xpath = "//input[@data-testid='phoneNumber-createCity']")
	public WebElement city;

	@FindBy(xpath = "//a[@data-testid='phoneNumber-seeMore']")
	public WebElement seeMoreOptionsLink;

	@FindBy(xpath = "//a[contains(text(), 'Refresh options')]")
	public WebElement refreshOptionsLink;
	
	@FindBys(@FindBy(xpath = "//*[@id='root']/div/div/div[5]/div[2]//div/label"))
	public List<WebElement> availableOptions;
	
	
	@FindBy(xpath = "//*[@id='root']/div/div/div[5]/div[2]//div/label/input")
	public WebElement displayedTNOptions;
	
	@FindBy(xpath = "//div[@data-testid='phoneNumber-selectedNumber']")
	public WebElement selectedPhoneNumber;
	
	//Transfer Existing Sub
	@FindBy(xpath = "//button[@data-testid='phoneNumber-transferNumber']")
	public WebElement transferExistingNumberBtn;
	
	
	@FindBy(xpath = "//input[@data-testid='phoneNumber-agreement']")
	public WebElement authrizeTransferChkBox;
	
	@FindBy(xpath = "//input[@data-testid='phoneNumber-previousProvider']")
	public WebElement previousServiceProvider;
	
	@FindBy(xpath = "//input[@data-testid='phoneNumber-accountNumber']")
	public WebElement previousServiceProviderAccountNum;
	
	
	@FindBy(xpath = "//input[@data-testid='phoneNumber-alternateContact']")
	public WebElement alterateContactNumber;
	
	
	@FindBy(xpath = "//input[@data-testid='phoneNumber-transferAreaCode']")
	public WebElement txtPhoneAreaCode;

	@FindBy(xpath = "//input[@data-testid='phoneNumber-transferThreeDigits']")
	public WebElement txtPhoneFirstThreeDigitsOfNum;

	@FindBy(xpath = "//input[@data-testid='phoneNumber-transferFourDigits']")
	public WebElement txtPhoneLastFourDigitsOfNum;
	
	
	
	@FindBy(xpath = "//button[@data-testid='phoneNumber-nextStep']")
	public WebElement step3PlansBtn;

	// NEXT PAGE
	
	@FindBy(xpath = "//input[@name='Select Plan'][1]")
	public WebElement selectFirstDefaultPlan;
	
	@FindBy(xpath = "//button[@data-testid='plans-nextStep']")
	public WebElement step4PaymentBtn;
	
	/*
	 * CREDIT CARD INFORMATION PAGE
	 */

	@FindBy(xpath = "//input[@data-testid='payment-creditCard']")
	public WebElement creditCardNumber;

	@FindBy(xpath = "//input[@data-testid='payment-creditExpiry']")
	public WebElement expiryDate;

	@FindBy(xpath = "//input[@data-testid='payment-creditSecurity']")
	public WebElement securityCode;
	
	@FindBy(xpath = "//input[@data-testid='payment-creditPostal']")
	public WebElement creditCardPostalCode;
	
	@FindBy(xpath = "//input[@data-testid='payment-termsCheck']")
	public WebElement agreeToServiceTermsChkBox;
	
	@FindBy(xpath = "//button[@data-testid='payment-activateButton']")
	public WebElement activateSIMCardBtn;
	
	/*
	 * KPRE Phone Number and Account Number
	 */

	@FindBy(xpath = "//*[@id='root']//div[4]/div/div/span[4]")
	public WebElement kprePhoneNumber;
	
	@FindBy(xpath = "//*[@data-testid='transactionSummary-ban']")
	public WebElement kpreAccountNumber;
	
	
	public boolean isHomePageDisplayed() {
		return getStartedButton.isDisplayed();
	}

	public boolean ifENLangRadioBtnChecked() {
		return preferredLanguage_EN.isSelected();
	}

	public boolean ifFRLangRadioBtnChecked() {
		return preferredLanguage_FR.isSelected();
	}

	public String getSelectedPhoneNumberText() {

		try {
			return selectedPhoneNumber.getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	
	public boolean clickExpectedFormatTN(String expectedSeries) {
		boolean status = false;
		
		try {
		for (WebElement webElement : availableOptions) {
			BaseSteps.Waits.waitGeneric(500);
			if (webElement.getText().contains(expectedSeries)) {
				
				webElement.click();
				status = true;
				break;
			}
		}
		}
		catch(Exception e) {
			return false;
		}
		return status;
	}
	
	public String getKPREPhoneNumber() {
		return kprePhoneNumber.getText();

	}

	public String getKPREAccountNumber() {
		return kpreAccountNumber.getText();

	}

}
