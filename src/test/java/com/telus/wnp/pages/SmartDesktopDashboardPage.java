package com.telus.wnp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.ui.actions.WebDriverSession;

public class SmartDesktopDashboardPage extends WebDriverSession {

	public SmartDesktopDashboardPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//tr[1]/td[1]/span[1]/text()[1]")
	public WebElement prepaidSubscriberName;

	@FindBy(xpath = "//tr[1]/td[1]/span[1]/text()[4]")
	public WebElement prepaidSubscriberPostalCode;

	@FindBy(xpath = "//tr[2]//tr[1]/td[1]/span[1]")
	public WebElement prepaidSubscriberDetails;

	@FindBy(xpath = "//tr[1]/td[3]/span[1]")
	public WebElement consumerType;

	@FindBy(xpath = "//div/a[1]/span[@class='detail2']")
	public WebElement tnStatus;

	@FindBy(xpath = "//a[contains(text(), 'change #')]")
	public WebElement changeLink;

	@FindBy(xpath = "//a[contains(text(), 'resume')]")
	public WebElement resumeLink;

	@FindBy(xpath = "//*[@id='portInNumber']")
	public WebElement enterNumInStep2d;

	@FindBy(xpath = "//table[6]/tbody/tr[4]/td/div/a")
	public WebElement submitBtnFromStep2d;

	// Change # link fields
	@FindBy(xpath = "//*[@id='securityPIN1']/input")
	public WebElement ospSecurityPIN;

	@FindBy(xpath = "//*[@id='accountNumber1']/input")
	public WebElement ospAccountNum;

	@FindBy(xpath = "//*[@id='accountNumber3']/input")
	public WebElement ospAccountNumConfirm;

	@FindBy(xpath = "//*[@id='serialNumber1']/input")
	public WebElement ospIMEINum;

	@FindBy(xpath = "//*[@id='serialNumber3']/input")
	public WebElement ospIMEINumConfirm;

	@FindBy(xpath = "//input[@name='alternateContactNumber']")
	public WebElement alternateContactNum;

	@FindBy(xpath = "//input[@name='hasConfirmed']")
	public WebElement numberIsActiveConfirmChkBox;

	@FindBy(xpath = "//input[@name='hasAuthorized']")
	public WebElement authorizedChkBox;

	@FindBy(xpath = "//textarea[@name='comment']")
	public WebElement enterCommentsForOSP;

	// WLN Details

	@FindBy(xpath = "//input[@name='firstName']")
	public WebElement WLN_FirstName;

	@FindBy(xpath = "//input[@name='lastName']")
	public WebElement WLN_LastName;

	@FindBy(xpath = "//input[@name='streetNumber']")
	public WebElement WLN_StreetNumber;

	@FindBy(xpath = "//input[@name='streetName']")
	public WebElement WLN_StreetName;

	@FindBy(xpath = "//input[@name='city']")
	public WebElement WLN_City;

	@FindBy(xpath = "//select[@name='province']")
	public WebElement WLN_ProvinceDrpDown;

	@FindBy(xpath = "//input[@name='postalCode']")
	public WebElement WLN_PostalCode;

	@FindBy(xpath = "//input[@name='hasVerifiedServiceAddress']")
	public WebElement WLN_HasVerifiedServiceAddChkBox;

	@FindBy(xpath = "//*[@id='landlineAccountInformationData']//tr[1]/td[2]/select") // no change
	public WebElement internetTVOption;

	@FindBy(xpath = "//*[@id='landlineAccountInformationData']//tr[2]/td[2]/select") // no
	public WebElement isMoving;

	@FindBy(xpath = "//a[contains(text(), 'submit')]")
	public WebElement submitChangeNumberRequestBtn;

	@FindBy(xpath = "//*[@class='formHeader']//td[contains(text(), 'resume')]")
	public WebElement resumeRequestHeader;

	@FindBy(xpath = "//tr[7]/td")
	public WebElement resumeConfirmationMsg;

	@FindBy(xpath = "//tr[6]/td")
	public WebElement resumeRequestConfirmationMsg;

	@FindBy(xpath = "//*[@id='portSection']/td[2]/input")
	public WebElement skipPortReqChkBox;

	@FindBy(xpath = "//form/table/tbody/tr[1]/td")
	public WebElement resumeSectionDisplayedLabel;

	@FindBy(xpath = "//table[3]//tr[1]/td[2]//tr[7]/td")
	public WebElement subscriberResumedSuccessLabel;

	@FindBy(xpath = "//*[@id='comment']")
	public WebElement changeReqFinalComments;

	@FindBy(xpath = "//a[contains(text(), 'accept')]")
	public WebElement changeReqFinalAcceptBtn;

	@FindBy(xpath = "//tr[1]/td[2]/table//tr/td")
	public WebElement errorMessageDisplayed;

	@FindBy(xpath = "//td[3]/table[2]/tbody/tr[2]/td")
	public WebElement portedSubDetails;

	@FindBy(xpath = "//*[@id='securityPIN1']/input")
	public WebElement enterOSPSecurityPIN;

	@FindBy(xpath = "//a[contains(text(), 'OSP cancel request')]")
	public WebElement ospCancelLink;

	@FindBy(xpath = "//*[@id='phoneNumber']")
	public WebElement tnToCancel;

	@FindBy(xpath = "//*[@id='requestedCancelDate']")
	public WebElement tnCancelDate;

	@FindBy(xpath = "//a[contains(text(), 'submit')]")
	public WebElement tnCancelSubmitBtn;

	@FindBy(xpath = "//]")
	public WebElement tnReqSubmittedMsg;

	@FindBy(xpath = "//input---->")
	public WebElement ospRequestMemo;

	@FindBy(xpath = "//a[@name='NoUpdate']")
	public WebElement noUpdateBtn;

	@FindBy(xpath = "//tr[1]/td[2]/table/tbody/tr/td")
	public WebElement requestSubmissionErrText;

	@FindBy(xpath = "//td[2]/a/img")
	public WebElement portInPortOutIndicator;

	@FindBy(xpath = "//a[contains(text(), 'communication history')]")
	public WebElement communicationHistoryTab;

	@FindBy(xpath = "//button[contains(text(), 'Search by phone number')]")
	public WebElement searchByNumFromCommHistoryPage;

	@FindBy(xpath = "//*[@id='phone-number']")
	public WebElement enterPhoneNumFromCommHistoryPage; // hyphen separated

	@FindBy(xpath = "//*[@id='from']")
	public WebElement fromDateFromCommHistoryPage;

	@FindBy(xpath = "//*[@id='to']")
	public WebElement toDateFromCommHistoryPage;

	@FindBy(xpath = "//div[3]/div/div/button[contains(text(), 'Search')]")
	public WebElement searchBtnFromCommHistoryPage;

	@FindBy(xpath = "//a[contains(text(),'Return to Login page')]")
	public WebElement returnToLoginLinkFromCommHistoryPage;

	@FindBy(xpath = "//img[@name='Login.productLogo']")
	public WebElement openSSOFromCommHistoryPage;

	@FindBy(xpath = "//*[contains(text(), 'No results found')]")
	public WebElement noSearchResultsFoundCommHistory;

	@FindBy(xpath = "//*[@id='app']//div[5]//div[3]//button/i")
	public WebElement firstRowPreviewBtnCommHistory;

	@FindBy(xpath = "//*[contains(text(), 'Subject')]")
	public WebElement subjectTextInEmailFromCommHistory;

	@FindBy(xpath = "//*[@id='popout-content-container']/div/div[1]")
	public WebElement oopsMsgSubFromCommHistory;

	@FindBy(xpath = "//*[@id='popout-content-container']//div[2]/div/div[1]")
	public WebElement oopsMsgBodyFromCommHistory;

	@FindBy(xpath = "//*[contains(text(), 'Cancelled service')]")
	public WebElement subjectTitleInEmailFromCommHistory;

	@FindBy(xpath = "//*[@id='header']//strong")
	public WebElement sENotificationDelivery;

	@FindBy(xpath = "//*[@id='ban']")
	public WebElement banFromSENotificationDelivery;

	@FindBy(xpath = "//*[@id='wireless-search']//button")
	public WebElement searchBtnFromSENotificationDelivery;

	@FindBy(xpath = "//tr[2]/td//a[@class='portOut']")
	public WebElement portingStatusInfo;

	@FindBy(xpath = "//input[@name='authorizationName']")
	public WebElement authorizationName;

	@FindBy(xpath = "//a[contains(text(), 'transfer block')]")
	public WebElement transferBlockTab;

	@FindBy(xpath = "//*[@id='subscriberTB']")
	public WebElement subscriberBlockedDrpdwn;

	@FindBy(xpath = "//td[7]/strong")
	public WebElement overallSubscriberTransferBlockText;

	//

	@FindBy(xpath = "//div[2]//div/p[contains(text(), 'No results found')]")
	public WebElement commHistoryNoResultsFound;

	@FindBy(xpath = "//button [contains(text(), 'clear search')]")
	public WebElement commHistoryClearSearchBtn;

	@FindBy(xpath = "//*[@id='app']/div/div[5]//div[1]/div/div/div[1]/div/span")
	public WebElement commHistoryDateTime;

	@FindBy(xpath = "//*[@id='app']/div/div[5]/div/div/div/div/div/div[1]//div[2]/div/span")
	public WebElement commHistoryCategory;

	@FindBy(xpath = "//*[@id='app']/div/div[5]//div[1]/div[3]/div/span")
	public WebElement commHistorySMSDescription;

	@FindBy(xpath = "//*[@id='app']/div/div[5]//div[1]/div[4]/div/span")
	public WebElement commHistoryDestinationSub;

	@FindBy(xpath = "//*[@id='app']/div/div[5]//div[2]//div[1]/div/span")
	public WebElement commHistoryEventType;

	@FindBy(xpath = "//*[@id='app']/div/div[5]//div[2]//div[2]/div/span")
	public WebElement commHistoryEventTypeStatus;

	@FindBy(xpath = "//button[contains(text(),'Search by phone number')]")
	public WebElement commHistorySearchByPhoneNumber;

	@FindBy(xpath = "//*[@id='phone-number']")
	public WebElement commHistoryPhoneNumberTxtBox;

	@FindBy(xpath = "//button[text()='Search']")
	public WebElement commHistorySearchBtn;

	//

	public String getTNStatus() {
		return tnStatus.getText().toString();
	}

	public String getPortingStatusInfo() {
		return portingStatusInfo.getText().toString();
	}

	public String getPortingStatusInfoOnMouseHover() {
		return portingStatusInfo.getAttribute("onmouseover").toString();
	}

	public String getOSPReqMemoText() {
		return ospRequestMemo.getText().toString();
	}

	public String getRequestSubmittedMsg() {
		return tnReqSubmittedMsg.getText().toString();
	}

	public String getsENotificationDeliveryText() {
		return sENotificationDelivery.getText().toString();
	}

	public String getOpenSSOLabelText() {
		return openSSOFromCommHistoryPage.getText().toString();
	}

	public String getPortedSubDetails() {
		return portedSubDetails.getText().toString();
	}

	public String getRequestSubmissionErrText() {
		return requestSubmissionErrText.getText().toString();
	}

	public boolean isRequestSubmissionErrDisplayed() {
		return requestSubmissionErrText.isDisplayed();
	}

	public boolean isNoUpdateBtnDisplayed() {
		return noUpdateBtn.isDisplayed();
	}

	public String getCommHistoryDateTime() {
		try {
			return commHistoryDateTime.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getCommHistoryCategory() {
		return commHistoryCategory.getText();
	}

	public String getCommHistorySMSDescription() {
		return commHistorySMSDescription.getText();
	}

	public String getCommHistoryDestinationSub() {
		return commHistoryDestinationSub.getText();
	}

	public String getCommHistoryEventType() {
		return commHistoryEventType.getText();
	}

	public String getCommHistoryEventTypeStatus() {
		return commHistoryEventTypeStatus.getText();
	}

	public String getResumeRequestHeaderLabelText() {
		return resumeRequestHeader.getText();
	}

	public String getResumeConfirmationMsg() {
		return resumeRequestConfirmationMsg.getText();
	}

	public boolean isAuthorizationNameBlank() {
		if (authorizationName.getText().isEmpty())
			return true;
		else
			return false;
	}

	public String getSubscriberBlockedLabelText() {
		return overallSubscriberTransferBlockText.getText();
	}

	public String getConsumerTypeText() {
		if (consumerType.isDisplayed()) {
			return consumerType.getText();
		} else
			return "";
	}

	public boolean isErrorMessageDisplayed() {
		try {
			return errorMessageDisplayed.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getErrorMessageText() {
		return errorMessageDisplayed.getText();
	}

	public String getNoSearchResultsFoundCommHistory() {
		try {
			return noSearchResultsFoundCommHistory.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getSubjectTitleInEmailFromCommHistory() {
		try {
			return subjectTitleInEmailFromCommHistory.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getOOPSMsgSubFromCommHistory() {
		try {
			return oopsMsgSubFromCommHistory.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getOOPSMsgBodyFromCommHistory() {
		try {
			return oopsMsgBodyFromCommHistory.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPrepaidSubscriberName() {

		try {
			return prepaidSubscriberName.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPrepaidSubscriberPostalCode() {

		try {
			return prepaidSubscriberPostalCode.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPrepaidSubscriberDetails() {

		try {
			return prepaidSubscriberDetails.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public WebElement MessageDescription(String description) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(20));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div/span[contains(text(),'" + description + "')]")));
	}

	public WebElement MessageCategory(String category) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(20));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[1]//div[2]/div/span[contains(text(),'" + category + "')]")));
	}

	public WebElement MessageDate(String date) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(20));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[5]//div[1]//span[contains(text(),'" + date + "')]")));
	}

	public WebElement MessageEvent(String event) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(20));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[5]//div[2]//div[1]//span[contains(text(),'" + event + "')]")));
	}

	public boolean getNoResultsFoundStatus() {
		boolean stat = false;
		try {
			stat = commHistoryNoResultsFound.isDisplayed();
		} catch (Exception e) {
			return false;
		}
		return stat;
	}

}
