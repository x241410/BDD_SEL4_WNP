package com.telus.wnp.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.test.ui.actions.WebDriverSession;

public class RCMPage extends WebDriverSession {

	public RCMPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//input[@name='npa']")
	public WebElement npa;
	@FindBy(xpath = "//input[@name='nxx']")
	public WebElement nxx;

	@FindBy(xpath = "//input[@name = 'rangeStart']")
	public WebElement rangeStart;

	@FindBy(xpath = "//input[@name = 'rangeEnd']")
	public WebElement rangeEnd;

	@FindBy(xpath = "//select[id='rtype'] | //select[@name='resourceType'] ")
	@CacheLookup
	public WebElement rtype;

	@FindBy(xpath = "//input[@value='search']")
	public WebElement search;

	@FindBy(xpath = "//input[@name='Reset']")
	public WebElement reset;
	
	@FindBy(xpath = "//tr[@class='even']/td[2]")
	public WebElement status;

	@FindBy(xpath = "//table[3]/tbody/tr[1]/td[3]/table/tbody/tr[2]/td/form/table/tbody/tr[12]/td/a[5]")
	public WebElement inventory;

	@FindBy(xpath = "//div[contains(text(), 'RCM - Rate Centre Inventory Report')]")
	public WebElement rcmInventoryPageText;

	@FindBy(xpath = "//a[contains(text(), 'help')]")
	public WebElement homePageHelpText;
	
	@FindBy(xpath = "//form/table/tbody/tr[1]/td")
	public WebElement noSearchResultsFound;
	
	@FindBy(xpath="//form/div/table/tbody/tr[1]/td[4]")
	public WebElement portingStatus;
	
	@FindBy(xpath="//form/div/table/tbody/tr[1]/td[5]")
	public WebElement portingDate;

	public boolean isHomePageDisplayed() {
		//System.out.println(homePageHelpText.getText().toString());
		return homePageHelpText.getText().toString().contains("help");

	}
	
	public String provisioningStatus() {
		return status.getText();

	}
	
	public String getNoSearchResultFoundMsg() {
		return noSearchResultsFound.getText().toString();

	}
	
	public String getPortingStatus() {
		return portingStatus.getText().toString();

	}
	
	public String getPortingDate() {
		return portingDate.getText().toString();

	}
	

	
}
// table[3]/tbody/tr[1]/td[3]/table/tbody/tr[2]/td/form/table/tbody/tr[12]/td/a[5]z