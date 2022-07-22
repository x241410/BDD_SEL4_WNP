package com.telus.wnp.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.ui.actions.WebDriverSession;

public class PACSearchPage extends WebDriverSession {

	public PACSearchPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//input[@name = 'submit']")
	public WebElement submit;

	@FindBy(xpath = "//input[@name = 'tn']")
	public WebElement ctn;

	@FindBy(xpath = "//a[contains(text(),'Logout')]")
	public WebElement logout;

	@FindBy(xpath = "//li[contains(text(), 'no Port/OSP Cancel request')]")
	public WebElement portStatus;

	@FindBy(xpath = "//a[contains(text(), 'Logout')]")
	public WebElement homePageHelpText;

	@FindBy(xpath = "//li[contains(text(), 'no Port/OSP Cancel request')]")
	public WebElement noPortOSPReqErrMsg;
	
	@FindBys(@FindBy(xpath = "//*[@id='currentRowObject']/tbody/tr"))
	public List<WebElement> numberOfRecords;
	
	@FindBy(xpath = "//*[@id='currentRowObject']/tbody/tr[1]/td[9]")
	public WebElement cancelledReq;
	
	@FindBy(xpath = "//*[@id='currentRowObject']/tbody/tr[2]/td[9]")
	public WebElement clonedReq;
	
	public int getNumberOfRecords() {
		if (numberOfRecords.size() >= 1) {
			return numberOfRecords.size();
		} else
			return 0;
	}
	
	
	public String getCancelledRequestEntryStatus() {

		return cancelledReq.getText();
	}
	
	public String getClonedRequestEntryStatus() {

		return clonedReq.getText();
	}

	public boolean isHomePageDisplayed() {
		System.out.println(homePageHelpText.getText());
		return homePageHelpText.getText().contains("Logout");

	}

	public String portOSPCancelRequestStatus() {
		return noPortOSPReqErrMsg.getText();
	}

}
