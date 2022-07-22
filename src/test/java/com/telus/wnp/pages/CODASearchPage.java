package com.telus.wnp.pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.WebDriverSession;

public class CODASearchPage extends WebDriverSession {

	public CODASearchPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//form/div/select [@id='type']")
	public WebElement searchByField;

	@FindBy(xpath = "//*[@id='criteria']")
	public WebElement searchByValue;

	@FindBy(xpath = "//form/button[contains(text(), 'Search')]")
	public WebElement searchBtn;

	@FindBy(xpath = "//span[contains(text(), 'pt')]")
	public WebElement selectedEnvironment;

	@FindBy(xpath = "//label[contains(text(),'Search By')]")
	public WebElement homePageHelpText;

	@FindBy(xpath = "//li/ul/li[3]/button/span[2]")
	public WebElement subscriberStatus;

	@FindBy(xpath = "//a[contains(text(), 'Orders')]")
	public WebElement ordersTabLink;

	@FindBy(xpath = "//div/h2")
	public WebElement noResultsFound;

	@FindBy(xpath = "//a[contains(text(), 'Search')]")
	public WebElement searchPageLink;

	@FindBy(xpath = "//*[@id='order-attributes']/tr[4]/td[2]")
	public WebElement orderStatus;

	@FindBy(xpath = "//span [@class='status status-active']")
	public WebElement status;

	@FindBy(xpath = "//span [@class='status status-deleted']")
	public WebElement deletedStatus;

	@FindBy(xpath = "//*[@id='pendingOrdersCounter']")
	public WebElement pendingOrderCount;
	
	@FindBy(xpath = "//*[@id='taskErrorCounter']")
	public WebElement taskErrorCount;
	
	@FindBy(xpath = "//*[@id='validationErrorCounter']")
	public WebElement validationErrorCount;

	@FindBy(xpath = "//*[@id='attributes']//div[24]/div[2]")
	public WebElement portOutIndicator;
	
	@FindBy(xpath = "//*[@id='attributes']//div[4]/div[2]")
	public WebElement billingType;

	@FindBy(xpath = "//div[@class='portal-header-body']//span[1]")
	public WebElement brand;
	

	@FindBys(@FindBy(xpath = "//*[@id='task-errors']/tbody/tr"))
	public List<WebElement> multipleSearchRecords;
	
	@FindBy(xpath = "//*[@id='task-errors']/tbody/tr[1]/td[1]")
	public WebElement firstRowFromSearchRecords;

	public String getSelectedEnvText() {
		return selectedEnvironment.getText();
	}

	public String getSubscriberStatus() {
		return status.getText();
	}

	public String getSubscriberCancelledStatus() {
		return deletedStatus.getText();
	}

	public boolean checkMultipleRecordsPresence() {
		if (multipleSearchRecords.size() > 0)
			return true;
		else
			return false;
	}

	public String getNoSearchResultsMsg() {
		return noResultsFound.getText();
	}

	public String getOrderStatus() {
		return orderStatus.getText();
	}

	public String getPortOutIndicatorValue() {
		return portOutIndicator.getText();
	}
	
	public String getSubscriberBrand() {
		return brand.getText();
	}

	public boolean isHomePageDisplayed() {
		System.out.println(homePageHelpText.getText().toString());
		return homePageHelpText.getText().contains("Search By");

	}

	public int getPendingOrderCount() {

		if (pendingOrderCount.isDisplayed()) {
			return Integer.parseInt(pendingOrderCount.getText());
		}
		return -1;
	}
	
	public String getBrandText() {
		return brand.getText();
	}

	public String getConsumerTypeText() {
		if(billingType.isDisplayed()) {
			return billingType.getText();
		}
		else return "";
	}
	
}