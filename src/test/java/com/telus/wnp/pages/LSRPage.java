package com.telus.wnp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.utils.Status;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.WebDriverSession;

public class LSRPage extends WebDriverSession {

	public LSRPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	// HomePage

	@FindBy(xpath = "//a[text()='Logout']")
	public WebElement homePageHelpText;

	@FindBy(xpath = "//*[@id='region']")
	public WebElement selectSPID;

	@FindBy(xpath = "//*[@id='serviceprovider']")
	public WebElement selectServiceProvider;

	@FindBy(xpath = "//a[text()='LSR Search']")
	public WebElement searchLSRLink;

	@FindBy(xpath = "//a[text()='Create LSR']")
	public WebElement createLSRLink;

	@FindBy(xpath = "//*[@id='searchfield']")
	public WebElement searchFieldFromHomePage;

	@FindBy(xpath = "//*[@id='searchby']")
	public WebElement searchBYFromHomePage;

	@FindBy(xpath = "//input[@name='fromDate']")
	public WebElement searchFromDateFromHomePage;

	@FindBy(xpath = "//button/span[text()='Search']")
	public WebElement searchButtonFromHomePage;

	@FindBy(xpath = "//*[contains(text(), 'Search Results')]")
	public WebElement searchResultsLabelFromHomePage;

	@FindBy(xpath = "//*[@id='root']//div[4]//td[2]")
	public WebElement statusFromSearchResultsFromHomePage;

	@FindBy(xpath = "//*[@id='root']//td[2][text()='Superseded']")
	public WebElement status_Superseded;

	@FindBy(xpath = "//*[@id='root']//td[2][text()='Completed']")
	public WebElement status_Completed;

	@FindBy(xpath = "//*[@id='root']//tr[1]/td[5]")
	public WebElement currentRecordVersion;

	@FindBy(xpath = "//*[@id='root']//tr[2]/td[5]")
	public WebElement previousRecordVersion;

	@FindBy(xpath = "//*[@id='root']//td[3][text()='R']")
	public WebElement previousRecordCNTYP;

	/**
	 * Create LSR Page WebElements
	 */

	// LSR Headers Section

	@FindBy(xpath = "//*[@id='onspTD']//label/div/span")
	public WebElement ignoreONSPChkBox;

	// Service Details Section

	@FindBy(xpath = "//*[@id='ported-number']")
	public WebElement numberToBePorted;

	@FindBy(xpath = "//*[@id='lna']")
	public WebElement selectLNA;

	@FindBy(xpath = "//*[@id='ean']")
	public WebElement ean;

	@FindBy(xpath = "//*[@id='pon']")
	public WebElement pon;

	// Record Details Grid Elements

	@FindBy(xpath = "//*[contains(text(), 'LSR Service Detail Records')]")
	public WebElement recordDetailsTable;

	@FindBy(xpath = "//*[@id='sdRecordsDiv']//td[3]")
	public WebElement statusFromTable;

	@FindBy(xpath = "//*[@id='sdRecordsDiv']//td[2]/a")
	public WebElement refLinkFromTable;

	// LSR Admin Elements

	@FindBy(xpath = "//input[@name='desiredDueDate']")
	public WebElement desiredDueDate;

	@FindBy(xpath = "//input[@name='agencyAuthorizationDate']")
	public WebElement authDate;

	@FindBy(xpath = "//*[@id='sup']")
	public WebElement sup;

	@FindBy(xpath = "//*[@id='sdRecordsDiv']//td[7]")
	public WebElement tnFromSearchResults;

	// EUI Location & Access details WebElements

	@FindBy(xpath = "//a[text()='EUI Location & Access']")
	public WebElement locationDetailsLink;

	@FindBy(xpath = "//*[@id='name']")
	public WebElement name;

	@FindBy(xpath = "//*[@id='house-no']")
	public WebElement houseNumber;

	@FindBy(xpath = "//*[@id='street-name']")
	public WebElement streetName;

	@FindBy(xpath = "//*[@id='city']")
	public WebElement city;

	@FindBy(xpath = "//*[@id='province']")
	public WebElement province;

	@FindBy(xpath = "//*[@id='postal-code']")
	public WebElement postalCode;

	@FindBy(xpath = "//*[@id='eumi']")
	public WebElement eumi;

	// LSR Contact details WebElements

	@FindBy(xpath = "//a[text()='LSR Contact']")
	public WebElement lsrContactDetailsLink;

	@FindBy(xpath = "//*[@id='initiator']")
	public WebElement initiator;

	@FindBy(xpath = "//*[@id='telephone-no']")
	public WebElement telephoneNumber;

	@FindBy(xpath = "//*[@id='contact']")
	public WebElement implementationContactName;

	@FindBy(xpath = "//div[2]/div[1]//div[2]//div[3]//tr[1]/td[2]//div[2]/input")
	public WebElement implementationTelephoneNumber;

	// Common WebElements

	@FindBy(xpath = "//*[@id='root']//div[1]/p/span")
	public WebElement updateRequestStatus;

	@FindBy(xpath = "//button/span[text()='Save']")
	public WebElement saveButton;

	@FindBy(xpath = "//button/span[text()='Back']")
	public WebElement backButton;

	@FindBy(xpath = "//button/span[text()='Complete']")
	public WebElement completeButton;

	@FindBy(xpath = "//button/span[text()='Delete']")
	public WebElement deleteButton;

	@FindBy(xpath = "//button/span[text()='Copy SD']")
	public WebElement copySDButton;

	@FindBy(xpath = "//button/span[text()='Un-Complete']")
	public WebElement unCompleteButton;

	@FindBy(xpath = "//button/span[text()='New Version']")
	public WebElement newVersionButton;

	@FindBy(xpath = "//button/span[text()='Insert Details']")
	public WebElement insertDetailsButton;

	@FindBy(xpath = "//button/span[text()='Edit']")
	public WebElement editButton;

	@FindBy(xpath = "//*[@id='root']/div[2]/div[1]//button")
	public WebElement alertMsgHeader;
	
	
	@FindBy(xpath = "//div[@class='react-datepicker__current-month']")
	public WebElement currentMonthYear;

	public boolean isHomePageDisplayed() {
		return homePageHelpText.getText().contains("Logout");

	}

	public String getUpdateRequestStatus() {
		try {
			if (updateRequestStatus.isDisplayed()) {
				return updateRequestStatus.getText();
			}
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Update Status Mismatch");
			return "";
		}
		return "";

	}

	public String getPortOutRequestNumber() {

		return pon.getAttribute("value");
	}

	public WebElement ponElementFromGrid(String ponNumber) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='sdRecordsDiv']//td[contains(text(), '" + ponNumber + "')]")));

	}

	public String getStatusFromGrid() {
		return statusFromTable.getText();
	}

	public String getStatusFromSearchResultsFromHomePage() {
		return statusFromSearchResultsFromHomePage.getText();
	}

	public boolean statusOfUnCompleteButton() {

		try {
			return unCompleteButton.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean statusOfEditButton() {

		try {
			return editButton.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void closeAlertMsgHeaderIfDisplayed() {

		try {
			BaseSteps.Waits.waitForElementVisibility(alertMsgHeader);
			alertMsgHeader.click();
			Reporting.logReporter(Status.INFO, "Alert Message Header closed successfully");

		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Alert Message Header Not displayed");
		}
	}

	public WebElement datePicker(String day) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'day--0" + day + "')]")));
		// By.xpath("//*[@class='react-datepicker__day react-datepicker__day--0" + day +
		// "']")));

	}

	public WebElement openPONDetailsFromSearchPage(String ponNumber) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(30));
		return wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'" + ponNumber + "')]")));
	}

	public String getTNFromSearchResults() {
		return tnFromSearchResults.getText();
	}

	public boolean getSerachResultsLabel() {
		try {
			return searchResultsLabelFromHomePage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkIfsearchLSRLinkIsPresent() {

		try {
			return searchLSRLink.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getPreviousRecordStatus(String expectedStatus) {
		// Superseded
		try {
			WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
			return wait
					.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//td[2][text()='" + expectedStatus + "']")))
					.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getCurrentRecordVersion() {
		try {
			return currentRecordVersion.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPreviousRecordVersion() {
		try {
			return previousRecordVersion.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getCurrentRecordStatus() {
		try {
			return status_Completed.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPreviousRecordStatus() {
		try {
			return status_Superseded.getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	
	public String getPreviousRecordCNTYP() {
		try {
			return previousRecordCNTYP.getText();
		} catch (Exception e) {
			return "";
		}
	}
	
	public WebElement selectCurrentDate(String month, String dom) {
		
			WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(20));
			return wait
					.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//*[contains(@aria-label, '"+month+" "+dom+"')]")));
		
	}

	// a[contains(text(),'AB92899063')]
}
