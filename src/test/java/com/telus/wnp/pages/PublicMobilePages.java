package com.telus.wnp.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.WebDriverSession;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for Public mobile pages Object Mapping > AUTHOR:
 * x241410
 ****************************************************************************
 */

public class PublicMobilePages extends WebDriverSession {

	public PublicMobilePages() {

		PageFactory.initElements(getWebDriverSession(), this);

	}
	// Page 1

	@FindBy(xpath = "//*[@id='nextPage']") // can be reused
	public WebElement nextButton;

	@FindBy(xpath = "//button[contains(text(), 'Payment')]")
	public WebElement clickPaymentBtn;

	@FindBy(xpath = "//*[@id='view']//div[2]/div[2]//div[3]//img")
	public WebElement numebrTickMark;
	
	@FindBy(xpath = "//*[@id='firstsimdigits']")
	public WebElement firstsimdigits;
	
	@FindBy(xpath = "//*[@id='secondsimdigits']")
	public WebElement secondsimdigits;

	@FindBy(xpath = "//*[@id='thirdsimdigits']")
	public WebElement thirdsimdigits;

	@FindBy(xpath = "//*[@id='fourthsimdigits']")
	public WebElement fourthsimdigits;
	
	@FindBy(xpath = "//*[@id='view']/div[1]/div[1]/div[2]/div[4]//div/img")
	public WebElement simValidationTickMark;
	

	@FindBy(xpath = "//*[@id='firstname']")
	public WebElement firstname;

	@FindBy(xpath = "//*[@id='lastname']")
	public WebElement lastname;

	@FindBy(xpath = "//*[@id='province']")
	public WebElement province;

	@FindBy(xpath = "//*[@id='postalcodeacct']")
	public WebElement postalcodeacct;

	@FindBy(xpath = "//*[@id='email']")
	public WebElement email;

	@FindBy(xpath = "//*[@id='confirmemail']")
	public WebElement confirmemail;
	
	@FindBy(xpath = "//div[4]/div[2]//div[2]//img")
	public WebElement emiailIdConfirmedTickMark;

	@FindBy(xpath = "//*[@id='password']")
	public WebElement password;

	@FindBy(xpath = "//*[@id='pin']")
	public WebElement pin;

	@FindBy(xpath = "//*[@id='question']")
	public WebElement securityQuestion;

	@FindBy(xpath = "//*[@id='answer']")
	public WebElement securityQuesAns;

	@FindBy(xpath = "//label[@for='english']")
	public WebElement englishLang;

	@FindBy(xpath = "//button [@id='newNumber']")
	public WebElement newPhoneNumBtn;

	@FindBy(xpath = "//button [@id='portNumber']")
	public WebElement transferExitingNumber;

	@FindBy(xpath = "//*[@id='city']")
	public WebElement newPhoneCity;

	@FindBy(xpath = "//*[@id='view']//div[3]/div[2]/div/div/p")
	public WebElement selectedPhoneNumber;

	@FindBy(xpath = "//button[contains(text(), 'See More Options')]")
	public WebElement seeMoreOptionsBtn;

	@FindBy(xpath = "//button[contains(text(), 'Refresh Options')]")
	public WebElement refreshOptionsBtn;

	@FindBy(xpath = "//button[@id='portNumber']")
	public WebElement portExistingNumber;

	@FindBy(xpath = "//*[@id='areacode']")
	public WebElement txtPhoneAreaCode;

	@FindBy(xpath = "//*[@id='threedigits']")
	public WebElement txtPhoneFirstThreeDigitsOfNum;

	@FindBy(xpath = "//*[@id='fourdigits']")
	public WebElement txtPhoneLastFourDigitsOfNum;

	@FindBy(xpath = "//*[@id='errorIdCheckBox']")
	public WebElement authorizeChkBoxLbl;

	@FindBy(xpath = "//*[@id='old-service-provider']")
	public WebElement osp;

	@FindBy(xpath = "//*[@id='previousaccountnumber']")
	public WebElement previousSubBanNum;

	@FindBy(xpath = "//*[@id='alternatenumber']")
	public WebElement alternatenumber;

	@FindBy(xpath = "//*[@id='container']/div[1]//div[1]/div[2]/button/img")
	public WebElement selectFirstDefaultPlan;

	@FindBy(xpath = "//button//h3[contains(text(), 'Pay with Credit or Visa Debit')]")
	public WebElement payWithCreditCard;

	@FindBy(xpath = "//*[@id='view']")
	public WebElement clickViewPage;

	@FindBys(@FindBy(xpath = "//*[@id='view']//div[2]/div//label/span"))
	public List<WebElement> availableOptions;
	
	@FindBy(xpath = "//*[@id='view']//div[2]/div[2]/div/div[1]/div//label")
	public WebElement waitResumeOptionsElement;
	
	@FindBy(xpath= "//*[@class='css-9xtvg4-loader-loader']")
	public WebElement spinnerIcon;
	
	// @FindBy(xpath = "//*[@id=\"view\"]/div[3]/div/div/div[1]/div[2]/button[1]")
	// public WebElement payWithCreditCard;

	@FindBy(xpath = "//*[@id='cardnumber']")
	public WebElement cardnumber;

	@FindBy(xpath = "//*[@id='postalcode']")
	public WebElement postalcode2;

	@FindBy(xpath = "//*[@id='expirydate']")
	public WebElement expirydatemmyy;

	@FindBy(xpath = "//*[@id='securitycode']")
	public WebElement cvvNumber;

	@FindBy(xpath = "//h1[contains(text(),'Activate your Public Mobile SIM card')]")
	public WebElement homePageText;

	@FindBy(xpath = "//*[@id='errorIdCheckBox']/label/div/p[contains(text(), 'I agree to the service terms')]")
	public WebElement agreeToTnCChkBox;

	// @FindBy(xpath = "//*[@class='activateBtn-0-2-299
	// css-smcvfg-base-primary-primary-fullWidth']")
	@FindBy(xpath = "//*[@id='view']//button[contains(text(),'Activate SIM Card')]")
	public WebElement activateSimCardBtn;

	@FindBy(xpath = "//*[@id='view']//div[3]//div[1]/div[2]/div[1]/div[2]/p")
	public WebElement accountNumber;

	@FindBy(xpath = "//*[@id='view']/div/div[3]//div[2]/div[2]/div[2]/div[2]/p")
	public WebElement publicMobileNum;

	public boolean isHomePageDisplayed() {
		System.out.println(homePageText.getText());
		return homePageText.getText().contains("Activate your Public Mobile SIM card");

	}

	public String getSelectedPhoneNum() {

		return selectedPhoneNumber.getText();
	}

	public String getAccountNum() {

		return accountNumber.getText();
	}

	public String getActivatedpublicMobileNum() {

		return publicMobileNum.getText();
	}

	public List<String> getAvailableTNOptions() {
		List<String> list = new ArrayList<String>();
		for (WebElement webElement : availableOptions) {
			list.add(webElement.getText());
		}
		return list;
	}

	public boolean clickExpectedFormatTN(String expectedSeries) {
		boolean status = false;
		for (WebElement webElement : availableOptions) {
			if (webElement.getText().contains(expectedSeries)) {
				webElement.click();
				status = true;
				break;
			}
		}
		return status;
	}
	
	public boolean spinnerRunningStatus() {
		boolean status = false;
		try{
			return spinnerIcon.isDisplayed();
		}
		catch(Exception e) {
			return status;
		}
	}
}
