package com.telus.wnp.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.WebDriverSession;

public class SMGASMSPage extends WebDriverSession {

	public SMGASMSPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//span[contains(text(),'Subscription Administration')]")
	public WebElement subscription_Administration;

	@FindBy(xpath = "//span[contains(text(),'Gateway Main Menu')]")
	public WebElement homePageText;
	
	@FindBy(xpath = "//span[contains(text(),'SOA Subscription Details')]")
	public  WebElement subscriptionDetails;
	
	@FindBy(xpath = "//*[@id='fromTn']")
	public  WebElement subscriptionDetailsFromTN;
	
	@FindBy(xpath = "//*[@id='queryButton']")
	public  WebElement subscriptionQueryBtn;
	
	@FindBy(xpath = "//*[@id='INFO_MSD_ID_0']")
	public  WebElement subscriptionDetailsInfoMsg;
	
	@FindBy(xpath = "//*[@id='select-action']")
	public  WebElement subscriptionDetailsSelectAction;
	
	//Select Action Options: New SP Create, Old SP Create, Modify Pending, Modify Active, Activate, Cancel, Undo Cancel & Modify etc.
	
	@FindBy(xpath = "//input[@id='newSpid']")
	public  WebElement subscriptionDetailsSelectNewSPID;  //8084 - Telus, 8086 - TELUS BC
	
	@FindBy(xpath = "//*[@id='svSpecificInfo']/div[2]//td[2]//button/span[1]")
	public  WebElement clickSubscriptionDetailsSelectNewSPIDDrpDwnBtn;
	
	@FindBys(@FindBy(xpath = "//*[@id='svSpecificInfo']/div[2]//div/ul/li" ))
	public List<WebElement> listOfSPIDs;
	
	 
	@FindBy(xpath = "//input[@id='oldSpid']")
	public  WebElement subscriptionDetailsSelectOldSPID; //TU04 - TELUS Mobility-TU04/2
	
	@FindBy(xpath = "//*[@id='svSpecificInfo']/div[2]//td[3]//button/span[1]")
	public  WebElement clickSubscriptionDetailsSelectOldSPIDDrpDwnBtn;
	
	@FindBy(xpath = "//select[@id='select-lnpType']")
	public  WebElement subscriptionDetailsLNPType;  // LSPP, LISP
	
	@FindBy(xpath = "//input[@id='newSpDueDate']")
	public  WebElement subscriptionDetailsNewSPDueDate;
	
	@FindBy(xpath = "//input[@id='lrn']")
	public  WebElement subscriptionDetailsLRN; // 403-515-0001
	
	@FindBy(xpath = "//button[@id='saveButton']")
	public  WebElement subscriptionDetailsSaveBtn;
	
	@FindBy(xpath = "//button[@id='clearButton']")
	public  WebElement subscriptionDetailsClearBtn;
	
	@FindBy(xpath = "//*[@id='svTimestamps']//tr[2]/td[2]/span")
	public  WebElement subscriptionDetailsRecordTypeState;
	
	@FindBy(xpath = "//div[contains(text(), 'Version Timestamps')]")
	public  WebElement recordTypeCollapsedSection;
	
	@FindBy(xpath = "//img[@title='Logout']")
	public  WebElement logOutBtnFromMainPage;
	
	
	@FindBy(xpath = "//*[@id='btn-util-confE']")
	public  WebElement okBtnConfirmLogOutFromPopUp;
	
	
	
	public String getRequestStatusMsg() {
		return subscriptionDetailsInfoMsg.getText();

	}
	
	public boolean isHomePageDisplayed() {
		return homePageText.getText().contains("Gateway Main Menu");
	}
	
	public String getRecordTypeState() {
		return subscriptionDetailsRecordTypeState.getText();
	}

	public void clickSpecificSPIDFromDropDown(String expectedSPID) {
		for (WebElement e : listOfSPIDs) {
			if (e.getText().contains(expectedSPID)) {
				e.click();
				break;
			}
		}
	}
	
}
