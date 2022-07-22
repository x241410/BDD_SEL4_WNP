package com.telus.wnp.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.WebDriverSession;

public class SMGICPPortSelectionPage extends WebDriverSession {

	public SMGICPPortSelectionPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//*[@id='portedTnid']")
	public WebElement portedTN;

	@FindBy(xpath = "//select [@id='select-portTypeField']")
	public WebElement portType;

	@FindBy(xpath = "//select [@id='select-onspField']")
	public WebElement oldNetwrok;

	@FindBy(xpath = "//select [@id='select-ACTION_ID']")
	public WebElement selectAction;

	@FindBy(xpath = "//*[@id='reqnotxt']")
	public WebElement requestNumber;

	@FindBy(xpath = "//*[@id='QUERY_ID']")
	public WebElement queryBtn;

	@FindBy(xpath = "//*[@id='CLEAR_ID']")
	public WebElement clearBtn;

	@FindBy(xpath = "//*[@id=':select:10']/input")
	public WebElement searchRowFirstcheckBox;

	@FindBy(xpath = "//*[@id=':reqNo:10']")
	public WebElement firstRowReqNumLink;

	@FindBy(xpath = "//*[@id=':icpMessageStatus:10']")
	public WebElement smgStatusFromICPPortSelection;
	
	@FindBy(xpath = "//*[@id=':icpPortStatus:1']")
	public WebElement smgICPPortStatusFromICPPortSelection;
	
	@FindBy(xpath ="//td/input[@type='checkbox']")
	public WebElement firstRowCheckBox;
	
	@FindBy(xpath ="//*[@id='select-nnspField']")
	public WebElement newNetworkProviderForQuery;
	
	
	
	@FindBy(xpath = "//*[@id='ServiceProviderAccordion']//div[2]//tr[8]/td[2]/span/smg-select/div/div/span")
	public WebElement responseTypeFromSearchResult;

	@FindBys(@FindBy(xpath = "//*[contains(@id,'reqNo:')]"))
	public List<WebElement> requestNumbersFromSearchResult;
	
	@FindBy(xpath = "//*[@id='btn-ok']")
	public WebElement cancelSVCRequestConfirmationPopUpBtn;
	
	@FindBy(xpath = "//*[@id=':reqNo:1']")
	public WebElement clickFirstRequestNumFromSearchResult;
	
	
	@FindBy(xpath = "//div/smg-dialog[2]/div/footer/div/button[1]")
	public WebElement cancelReqConfirmationPopUpBtn;
	
	public int RequestNumberCountOnSearchForPortedTN() {
		return requestNumbersFromSearchResult.size();

	}

	public String getResponseTypeFromSearchResult() {
		return responseTypeFromSearchResult.getText();

	}

	public String getResponseStatusFromICPPortSelection() {
		return smgStatusFromICPPortSelection.getText();

	}
	
	public String getICPPortStatusFromICPPortSelection() {
		return smgICPPortStatusFromICPPortSelection.getText();

	}
	
	public boolean getcanSVCRequestConfirmationPopUpBtnStatus() {
		return cancelSVCRequestConfirmationPopUpBtn.isDisplayed();

	}
	
	public boolean getCancelReqConfirmationPopUpBtn() {
		return cancelReqConfirmationPopUpBtn.isDisplayed();
	}

	public boolean isQueryButtonEnabled() {
		return queryBtn.isEnabled();

	}

}
