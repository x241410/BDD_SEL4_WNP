package com.telus.wnp.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.utils.Status;
import com.test.reporting.Reporting;
import com.test.ui.actions.WebDriverSession;

public class PACDashboardPage extends WebDriverSession {

	public PACDashboardPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBys(@FindBy(xpath = "//tr[5]/td[1]/table/tbody/tr[1]//td/table/tbody/tr//a"))
	public List<WebElement> portSummaryEvents;

	@FindBy(xpath = "//*[contains(text(), 'Port Summary')]")
	public WebElement portSummaryTable;

	@FindBy(xpath = "//a[normalize-space(text())='Search']")
	public WebElement searchPageLinkBtn;

	@FindBy(xpath = "//tbody/tr[2]/td[16]")
	public WebElement subBrand;

	@FindBy(xpath = "//*[@id='middlePane']//tr[3]/td[2]")
	public WebElement ospName;

	@FindBy(xpath = "//*[@id='middlePane']//tr[4]/td[2]") // NSP Name
	public WebElement newLocalServiceProvider;

	@FindBy(xpath = "//*[@id='middlePane']//tr[5]/td[2]") // NLSP Name
	public WebElement nspName;

	@FindBy(xpath = "//*[@id='middlePane']//tr[3]/td[2]")
	public WebElement timerExpiredMsg;

	@FindBy(xpath = "//a[text()='Modify']")
	public WebElement modifyBtn;

	@FindBy(xpath = "//a[contains(text(), 'Cancel')]")
	public WebElement cancelBtn;

	@FindBy(xpath = "//a[contains(text(), 'NPAC Activate')]")
	public WebElement NPACActivate;

	@FindBy(xpath = "//select[@name='reasonCode']") // Stuck port at PAC GUI, Port request expired, Cancel due to
													// incorrect port information
	public WebElement reasonCodeCancelPage;

	@FindBy(xpath = "//textarea[@name='remarks']")
	public WebElement remarksCancelPage;

	@FindBy(xpath = "//select[@name='tranType']") // CANC
	public WebElement noteCategoryCancelPage;

	@FindBy(xpath = "//select[@name='noteType']") // CAN
	public WebElement noteTypeCancelPage;

	@FindBy(xpath = "//textarea[@name='note']")
	public WebElement noteCancelPage;

	@FindBy(xpath = "//*[@id='middlePane']/form/font/ul/li")
	public WebElement minDDDTErrorMsg;

	@FindBy(xpath = "//input[@type='submit']")
	public WebElement submitBtnCancelPage;

	@FindBy(xpath = "//*[@id='middlePane']/table/tbody/tr[2]/td/li")
	public WebElement reqCancelledConfirmMsg;

	@FindBy(xpath = "//a[contains(text(), 'Clone')]")
	public WebElement cloneBtn;

	@FindBy(xpath = "//*[@id='middlePane']//tr[46]/td[2]/input")
	public WebElement streetNumber;

	@FindBy(xpath = "//*[@id='middlePane']//tr[45]/td[2]/input")
	public WebElement streetNum;

	@FindBy(xpath = "//input[@name='activeHoldIndicator']")
	public WebElement AnHIndicator;

	@FindBy(xpath = "//select[@name='confirmForm']")
	public WebElement lineIsActiveConfirmationDD;

	@FindBy(xpath = "//select[@name='addConf']")
	public WebElement addConfirmationChkBox;

	@FindBy(xpath = "//a[contains(text(), 'Refresh')]")
	public WebElement refreshBtn;

	@FindBy(xpath = "//a[contains(text(), 'Skip Validation')]")
	public WebElement skipValidationBtn;

	@FindBy(xpath = "//*[@id='middlePane']/table/tbody/tr[2]/td/li")
	public WebElement skipValidationMsgLabel;

	@FindBy(xpath = "//*[@id='noteDetailsPaneDecorator_Customer Response : yes']")
	public WebElement customerRespondedYesMsgLabel;

	@FindBy(xpath = "//*[@id='currentRowObject']/tbody/tr/td[1]")
	public WebElement dateWhenCustomerResponded;

	@FindBy(xpath = "//a[contains(text(), 'Complete')]")
	public WebElement completeBtn;

	@FindBy(xpath = "//input[@name='acctNumber']")
	public WebElement modifyAccountNum;

	@FindBy(xpath = "//input[@name='esn']")
	public WebElement modifyIMEINum;

	@FindBy(xpath = "//input[@name='esnReType']")
	public WebElement confirmIMEINum;

	@FindBy(xpath = "//input[@name='acctNumberReType']")
	public WebElement modifyConfirmAccountNum;

	@FindBy(xpath = "//*[@name='billFirstName']")
	public WebElement modifyFirstName;

	@FindBy(xpath = "//*[@name='billLastName']")
	public WebElement modifyLastName;

	@FindBy(xpath = "//*[@id='middlePane']//*[@name='date']")
	public WebElement dateTimeSent;

	@FindBy(xpath = "//*[@id='trigger']")
	public WebElement dddtTrigger;

	@FindBy(xpath = "//*[@id='middlePane']/form/font/ul")
	public WebElement listofErrorMsgs;

	@FindBy(xpath = "//*[@id='desiredDueDateTime']")
	public WebElement modifyDDT;

	@FindBy(xpath = "//*[@id='middlePane']//tr[2]/td")
	public WebElement modifyConfirmationMsg;

	@FindBy(xpath = "//*[@id='middlePane']/table/tbody/tr[24]/td[2]")
	public WebElement modifiedDDTFromMiddlePane;

	@FindBy(xpath = "//table/tbody/tr[2]/td[14]")
	public WebElement portStatus;

	@FindBy(xpath = "//table/tbody/tr[4]/td/table/tbody/tr[1]/td[10]")
	public WebElement pacInternalReqNumber;

	@FindBy(xpath = "//a[contains(text(), 'Auth SMS Delay')]")
	public WebElement authSMSDelayLink;

	@FindBys(@FindBy(xpath = "//*[@id='currentRowObject']/tbody/tr"))
	public List<WebElement> noOfRows;

	@FindBy(xpath = "//a[contains(text(), 'Customer Auth')]")
	public WebElement customerAuthEvent;

	@FindBy(xpath = "//a[contains(text(), 'WPRR Res Required En')]")
	public WebElement WPRRResReqEvent;

	@FindBy(xpath = "//a[contains(text(), 'Resolution Required')]")
	public WebElement WPRRResolutionRequiredEvent;

	@FindBy(xpath = "//*[@id='middlePane']//tr[3]/td[5]")
	public WebElement resReqCode;

	// pre port bypass

	@FindBy(xpath = "//a[normalize-space(text())='Preport Skip']")
	public WebElement preportSkipLinkBtn;

	@FindBy(xpath = "//input[@name='tnPreport']")
	public WebElement tnToPreport;

	@FindBy(xpath = "//input[@id='startDateandTimeStr']")
	public WebElement startDateTimePreport;

	@FindBy(xpath = "//textarea[@name='remarks']")
	public WebElement remarksPreport;

	@FindBy(xpath = "//input[@value='Wireless']")
	public WebElement spWirelessRadioBtnPreport;

	@FindBy(xpath = "//input[@value='Wireline']")
	public WebElement spWirelineRadioBtnPreport;

	@FindBy(xpath = "//input[@value='Add']")
	public WebElement addButtonPreport;

	@FindBy(xpath = "//tr[3]//ul/li")
	public WebElement successMessagePreport;

	public boolean getCustomerAuthEventStatus() {

		try {
			return customerAuthEvent.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getPortStatus() {
		return portStatus.getText();
	}

	public String getPACInternalReqNumber() {
		return pacInternalReqNumber.getText();
	}

	public String getBanText() {
		return getBanFromLatestEntry().getText();
	}

	public String getStatusText() {
		return getstatusFromLatestEntry().getText();
	}

	public String getSourceBrandText() {
		return getSourceBrandFromLatestEntry().getText();
	}

	public String getTargetBrandText() {
		return getTargetBrandFromLatestEntry().getText();
	}

	public String getModifyConfirmMessageText() {
		return modifyConfirmationMsg.getText();
	}

	public String getModifiedDDTFromMiddlePane() {
		return modifiedDDTFromMiddlePane.getText();
	}

	public boolean isPortSummaryTableDisplayed() {
		return portSummaryTable.isDisplayed();
	}

	public String getBrandName() {
		return subBrand.getText();
	}

	public String getOSPName() {
		return ospName.getText();
	}

	public String getNSPName() {
		return nspName.getText();
	}

	public String getNLSPName() {
		return newLocalServiceProvider.getText();
	}

	public String getSkipValidationMsgLabelText() {
		return skipValidationMsgLabel.getText();
	}

	public String getCustomerRespondedYesMsgLabelText() {
		return customerRespondedYesMsgLabel.getText();
	}

	public String getDateWhenCustomerRespondedText() {
		return dateWhenCustomerResponded.getText();
	}

	public WebElement getBanFromLatestEntry() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='currentRowObject']/tbody/tr[" + noOfRows.size() + "]/td[6]")));
	}

	public WebElement getLatestSUBEntry() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='currentRowObject']/tbody/tr[" + noOfRows.size() + "]/td[2]")));
	}

	public WebElement getstatusFromLatestEntry() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='currentRowObject']/tbody/tr[" + noOfRows.size() + "]/td[9]")));
	}

	public WebElement getSourceBrandFromLatestEntry() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='currentRowObject']/tbody/tr[" + noOfRows.size() + "]/td[16]")));
	}

	public WebElement getTargetBrandFromLatestEntry() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='currentRowObject']/tbody/tr[" + noOfRows.size() + "]/td[17]")));
	}

	public WebElement getBrandNameFromPAC(String brandName) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + brandName + "')]")));
	}

	public int returnNumberOfRecordsPresent() {

		return noOfRows.size();

	}

	public boolean isOSPNameDisplayed() {
		return ospName.isDisplayed();
	}

	public WebElement customerResponseElement(String resp) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='noteDetailsPaneDecorator_Customer Response : " + resp + "']")));
		// *[@id='noteDetailsPaneDecorator_Customer Response : yes']

	}

	public String getCustomerResponseFromNotes(String resp) {
		return customerResponseElement(resp).getText();
	}

	public String getReqCancelledConfirmMsg() {
		return reqCancelledConfirmMsg.getText();
	}

	public String getResReqCodeText() {
		return resReqCode.getText();
	}

	public String getListofErrorMsgs() {
		try {
			if (listofErrorMsgs.isDisplayed()) {
				return listofErrorMsgs.getText();
			} else
				return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getTimerExpiredMsgText() {
		return timerExpiredMsg.getText();
	}

	public void clickSpecificElementFromPortSummary(String expectedElementText) {
		for (WebElement e : portSummaryEvents) {
			if (e.getText().contains(expectedElementText)) {
				e.click();
				break;
			}
		}
	}

	public boolean isMinDDDTErrorMsgDisplayed() {
		boolean stat = false;
		try {
			if (minDDDTErrorMsg.getText().contains("at least 30 minutes")) {
				stat = true;
			}

		} catch (Exception e) {
			return false;
		}
		return stat;
	}

	public String getSuccessMsgTextPreport() {
		return successMessagePreport.getText();
	}
	
	
	public String getPortStatus_New() {
		
		String stat;
		try {
			stat = portStatus.getText();
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "Unable to verify Port/OSP Status");
			return "";
		}

		return stat;
	}

}
