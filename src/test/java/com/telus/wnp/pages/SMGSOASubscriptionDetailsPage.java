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

import com.test.ui.actions.WebDriverSession;

public class SMGSOASubscriptionDetailsPage extends WebDriverSession {

	public SMGSOASubscriptionDetailsPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	/*
	 * 
	 * Need to update below locators
	 */
	@FindBy(xpath = "//*")
	public WebElement dddt_date;

	@FindBy(xpath = "//*")
	public WebElement dddt_time;

	@FindBy(xpath = "//*")
	public WebElement dddt_activateTime;

	// Undo Cancel and Modify DDT field

	@FindBy(xpath = "//*[@id='oldSpDueDate']")
	public WebElement osp_dddt_date;

	@FindBy(xpath = "//*[@id='oldSpDueTime']")
	public WebElement osp_dddt_time;

	@FindBy(xpath = "//*[@id='svTimestamps']/div[2]//div[2]//tr[2]/td[2]/span")
	public WebElement npacStatus;

	@FindBy(xpath = "//*[@id='btn-confirm']")
	public WebElement okBtnFrompopUp;

	/*
	 * Selection Page
	 */
	@FindBy(xpath = "//*[@id='fromTn']")
	public WebElement fromTNSelection;

	@FindBy(xpath = "//*[@id='toTn']")
	public WebElement toTNSelection;

	@FindBy(xpath = "//*[@id='select-action']")
	public WebElement selectActionSOADrpDwnSelection;

	@FindBy(xpath = "//*[@id='lrn']")
	public WebElement lrnSelection;

	@FindBy(xpath = "//*[@id='saveButton']")
	public WebElement saveBtnSelection;

	@FindBy(xpath = "//*[@id='btn-confirmS']")
	public WebElement okBtnPopUpAfterLRNUpdateSelection;

	@FindBy(xpath = "//*[@id='INFO_MSD_ID_0']")
	public WebElement reqSubmitCnfrmMsg;

	@FindBy(xpath = "//*[@id='INFO_MSD_ID_1']")
	public WebElement reqSubmitCnfrmMsg1;

	@FindBys(@FindBy(xpath = "//td[contains(@class, 'rowId')]"))
	public List<WebElement> noOfRows;

	@FindBy(xpath = "//input[@name='View']")
	public WebElement viewBtn;

	public int returnNumberOfRecordsPresent() {

		return noOfRows.size();

	}

	public WebElement clickLatestFormattedTN() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id=':formattedTnBlock:" + noOfRows.size() + "']")));

	}

	public WebElement clickLatestTNCheckBox() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id=':select:" + noOfRows.size() + "']/input")));

	}

	public String getConfirmationMsg() {
		return reqSubmitCnfrmMsg.getText().toString();
	}

	public boolean isOkBtnFrompopUpDisplayed() {

		try {
			return okBtnFrompopUp.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isOKBtnPopUpAfterLRNUpdateDisplayed() {

		try {
			return okBtnPopUpAfterLRNUpdateSelection.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getNPACStatus() {
		return npacStatus.getText();
	}

	public String getReqSubmitConfirmationMsg() {
		return reqSubmitCnfrmMsg.getText();
	}

	public boolean getAdditionalRequestSubmissionMsgStatus() {
		return reqSubmitCnfrmMsg1.isDisplayed();
	}
	
	public String getAdditionalRequestSubmissionMsg() {
		return reqSubmitCnfrmMsg1.getText();
	}
}
