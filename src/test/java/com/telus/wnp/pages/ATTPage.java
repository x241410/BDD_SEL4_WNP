package com.telus.wnp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.ui.actions.WebDriverSession;

public class ATTPage extends WebDriverSession {

	public ATTPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//*[@id='ccTypeID']")
	public WebElement creditCardType;
	
	@FindBy(xpath = "//*[@id='lastfourDigitID']")
	public WebElement creditCardLastFourDigits;
	
	@FindBy(xpath = "//*[@id='expiryDateID']")
	public WebElement creditCardExpiry;
	
	@FindBy(xpath = "//*[@id='captureCardInformationButton']")
	public WebElement creditCardInfoCaptureButton;
	
	@FindBy(xpath = "//*[@id='accountNumberDivID_input']")
	public WebElement accountNumberInput;
	
	@FindBy(xpath = "//*[@id='ttID_ID']")
	public WebElement tokenTransactionid;
	
	@FindBy(xpath = "//*[@id='submitButtonID']")
	public WebElement submitButton;
	
	@FindBy(xpath = "//*[@id='ui-id-1']")
	public WebElement captureCardInfoPopUp;
	
	@FindBy(xpath = "//*[@id='cardnumber_input']")
	public WebElement ccNumber;
	
	@FindBy(xpath = "//*[@id='securitycode_input']")
	public WebElement cvvNumber;
	
	@FindBy(xpath = "//*[@id='expirymonth_input']")
	public WebElement expiryMonth;
	
	@FindBy(xpath = "//*[@id='expiryyear_input']")
	public WebElement expiryYear;
	
	@FindBy(xpath = "//*[@id='captureCardInformationButton']")
	public WebElement captureCCDetailsBtn;
	
	@FindBy(xpath = "//*[@id='button_ok']")
	public WebElement okButtonFromPopUp;
	
	@FindBy(xpath = "/html/body/div[18]/div[6]")
	public WebElement resizableWindow;
	
	@FindBy(xpath = "//*[@id='frame']")
	public WebElement frameToEnterCCDetails;
	
	@FindBy(xpath = "//*[@id='successErrorMessageID_input']")
	public WebElement confirmationMsg;
	
	
	
	//Credit card information successfully stored
	
	public WebElement getCreditCardType(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='ccTypeID']")));
	}
	
	public WebElement getCreditCardLastFourDigits(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='lastfourDigitID']")));
	}
	
	public WebElement getCreditCardExpiry(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='expiryDateID']")));
	}
	
	public WebElement inputCreditCardNumber(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='cardnumber_input']")));
	}
	
	public WebElement inputCreditCardCVV(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='securitycode_input']")));
	}
	
	public WebElement inputCreditCardExpiryMonth(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//select[@id='expirymonth_input']")));
	}
	
	
	public WebElement inputCreditCardExpiryYear(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//select[@id='expiryyear_input']']")));
	}
	
	public WebElement okBtnFromCCDetails(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='button_ok']")));
	}
	
	
	public WebElement getCreditCardInfoCaptureButton(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='captureCardInformationButton']")));
	}
	
	public WebElement getAccountNumberInput(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='accountNumberDivID_input']")));
	}
	
	public WebElement getTokenTransactionid(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='ttID_ID']")));
	}
	
	public WebElement getCaptureCardInfoPopUp(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='ui-id-1']")));
	}
	
	public WebElement getSubmitButton(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='submitButtonID']")));
	}
	
	public String getConfirmationMessage_Old(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		try {
		WebElement e =  wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='submitButtonID']")));
		return e.getText();
		}
		catch(Exception e) {
			return "";
		}
	}
	
	
	public String getConfirmationMessage() {	
		try {
		return confirmationMsg.getText();
		}
		catch(Exception e) {
			return "";
		}
	}
	
	//*[@id="successErrorMessageID_input"]
	
}
