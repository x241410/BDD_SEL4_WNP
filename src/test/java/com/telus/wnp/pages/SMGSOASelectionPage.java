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

import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.WebDriverSession;

public class SMGSOASelectionPage extends WebDriverSession {

	public SMGSOASelectionPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//*[@id='portedTnid']")
	public WebElement portedTN;

	@FindBy(xpath = "//*[@id='startTn']")
	public WebElement fromTN;

	@FindBy(xpath = "//*[@id='endTn']")
	public WebElement toTN;

	@FindBy(xpath = "//*[@id='select-oldSp']")
	public WebElement SPID_oldSP;

	@FindBy(xpath = "//*[@id='select-newSp']")
	public WebElement SPID_newSP;

	@FindBy(xpath = "//*[@id='query-btn']")
	public WebElement queryBtn;

	@FindBy(xpath = "//*[@id='clear-btn']")
	public WebElement clearBtn;

	@FindBy(xpath = "//*[@id='btn-confirm']")
	public WebElement okBtnFrompopUp;

	@FindBy(xpath = "//*[@id='INFO_MSD_ID_0']")
	public WebElement reqSubmitCnfrmMsg;

	@FindBy(xpath = "//*[@id='select-portType']")
	public WebElement portType; // Available Options: Port In, Port Out, Port Within, Other, Pool

	@FindBy(xpath = "//td/input[@type='checkbox']")
	public WebElement checkBoxesFromSearchResult;

	@FindBy(xpath = "//*[@id='select-soaSelectionAction']")
	public WebElement selectActionSOADrpDwn; // Modify Active, Modify Pending, Activate, Cancel, Undo Cancel, Undo
												// Cancel & Modify

	@FindBys(@FindBy(xpath = "//td[contains(@class, 'rowId')]"))
	public List<WebElement> noOfRows;

	@FindBys(@FindBy(xpath = "//div[contains(@id, 'requestTypeLabel')]"))
	public List<WebElement> listOfReqLabel;
	
	@FindBys(@FindBy(xpath = "//div[contains(@id, 'portTypeLabel')]"))
	public List<WebElement> listOfPortTypesLabel;

	@FindBy(xpath = "//input[@name='View']")
	public WebElement viewBtn;
	
	@FindBy(xpath = "//*[@class='hyperlink2']")
	public WebElement bottomElement;
	

	public int returnNumberOfRecordsPresent() {

		return noOfRows.size();

	}

	public int returnListOfReqLabelNames() {

		return listOfReqLabel.size();

	}

	public WebElement clickLatestFormattedTN() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id=':formattedTnBlock:" + noOfRows.size() + "']")));

	}

	public WebElement clickLatestTNCheckBox() {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id=':select:" + returnNumberOfRecordsPresent() + "']/input")));

	}

	public WebElement clickSpecificRequestTypeTNCheckBox(String expectedReqLabel) {

		int selectedRow = 1;
		WebElement ele = null;
		for (int i = 0; i < returnListOfReqLabelNames(); i++) {

			String getReqLabelText = listOfReqLabel.get(i).getText();
			if (getReqLabelText.equalsIgnoreCase(expectedReqLabel)) {

				selectedRow = selectedRow + i;
				ele = WebDriverSession.getWebDriverSession().findElement(By.xpath("//*[@id=':select:" + selectedRow + "']/input"));
				break;
			}
			
		}
		BaseSteps.Debugs.scrollToElement(ele);
		return ele;
	}
	
	
	public WebElement clickTNCheckBoxBasedOnPortType(String expectedPortTypeLabel) {

		int selectedRow = 1;
		WebElement ele = null;
		for (int i = 0; i < listOfPortTypesLabel.size(); i++) {

			String getReqLabelText = listOfPortTypesLabel.get(i).getText();
			if (getReqLabelText.equalsIgnoreCase(expectedPortTypeLabel)) {

				selectedRow = selectedRow + i;
				ele = WebDriverSession.getWebDriverSession().findElement(By.xpath("//*[@id=':select:" + selectedRow + "']/input"));
				break;
			}
			
		}
		BaseSteps.Debugs.scrollToElement(ele);
		return ele;
	}

	public boolean isOkBtnFrompopUpDisplayed() {

			return okBtnFrompopUp.isDisplayed();
		
	}

}
