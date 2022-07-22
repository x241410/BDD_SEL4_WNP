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

/**
 ****************************************************************************
 * > DESCRIPTION: Support for Smart Desktop search Page Object Mapping > AUTHOR:
 * x217794 > USER STORY: BIB flow
 ****************************************************************************
 */
public class SmartDesktopSearchPage extends WebDriverSession {

	public SmartDesktopSearchPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//select[@name = 'searchMethod']")
	public WebElement selectType;

	@FindBy(xpath = "//input[@name = 'phoneNumberSearch_PhoneNumber']")
	public WebElement phoneNumber;

	@FindBy(xpath = "//a[contains(text(), 'submit')]")
	public WebElement submit;

	@FindBy(xpath = "//input[@name = 'accountNumberSearch_AccountNumber']")
	public WebElement accountNumber;

	@FindBy(xpath = "//a[contains(text(), 'change #')]")
	public WebElement changeNumber;

	@FindBy(xpath = "//input[@name = 'portInNumber']")
	public WebElement portInNumber;

	@FindBy(xpath = "//table[6]//div/a[contains(text(), 'submit')]")
	public WebElement psubmit;

	@FindBy(xpath = "//table[6]//div/a[contains(text(), 'submit')]")
	public WebElement psubmit1;

	@FindBy(xpath = "//input[@name = 'accountNumber']")
	public WebElement paccountNumber;

	@FindBy(xpath = "//input[@name = 'confirmAccountNumber']")
	public WebElement pcaccountNumber;

	@FindBy(xpath = "//input[@name = 'alternateContactNumber']")
	public WebElement alternateContactNumber;

	@FindBy(xpath = "//input[@name = 'hasConfirmed']")
	public WebElement hasConfirmed;

	@FindBy(xpath = "//input[@name = 'hasAuthorized']")
	public WebElement hasAuthorized;

	@FindBy(xpath = "//a[contains(text(),'accept')]")
	public WebElement accept;

	@FindBy(xpath = "//div[contains(text(), 'Search')]")
	public WebElement homePageHelpText;

	@FindBy(xpath = "//*[@id='outterPaginatedList']/table/tbody/tr[2]/td[1]/a")
	public WebElement latestBan;

	@FindBy(xpath = "//*[@id='outterPaginatedList']/table/tbody/tr[2]/td[3]")
	public WebElement banStatus;

	@FindBy(xpath = "//td[2]/table/tbody/tr/td")
	public WebElement noAccountFoundMsg;

	@FindBys(@FindBy(xpath = "//input[@name = 'phoneNumberSearch_PhoneNumber']"))
	public List<WebElement> phnNumberSearch;

	@FindBy(xpath = "//input[@name = 'confirmAccountNumber']")
	public WebElement confirmAccountNumber;

	@FindBy(xpath = "//div[contains(text(), 'Search')]")
	public WebElement searchPageLink;

	@FindBy(xpath = "//select [@name='searchMethod']")
	public WebElement searchByMethod;
	
	@FindBy(xpath = "//a[contains(text(),'Migrate to Post-paid')]")
	public WebElement migrateToPostPaidLink;
	
	@FindBy(xpath = "//a[contains(text(),'Migrate to Pre-paid')]")
	public WebElement migrateToPrePaidLink;
	
	
	@FindBy(xpath = "//td[contains(text(),'Subscriber migrated from Pre-paid to Post-paid')]")
	public WebElement systemTextMigration;
	
	
	@FindBy(xpath = "//td[contains(text(),'Post-paid to Pre-paid')]")
	public WebElement systemTextMigrationPostToPre;
	
	@FindBy(xpath = "//table[2]//td/a/img")
	public WebElement pretopostimg;
	
	@FindBy(xpath = "//tr[1]//tr[4]/td[2]")
	public WebElement creationTime;
	

	public boolean isHomePageDisplayed() {
		return homePageHelpText.getText().contains("Search");

	}

	public boolean banIsPresent() {
		return latestBan.isDisplayed();
	}

	public String getBanStatus() {
		return banStatus.getText();
	}

	public String getNoAccountFoundMsg() {
		return noAccountFoundMsg.getText();
	}

	public int sizeOfPhnNumberSearch() {
		return phnNumberSearch.size();
	}

	public boolean isconfirmAccountNumberEnabled() {
		return confirmAccountNumber.isEnabled();
	}

	public WebElement clickSpecificBan(String ban) {

		WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='" + ban + "']")));

	}
	
	public String getSystemText() {
		try{
			return systemTextMigration.getText();
		}
		catch(Exception e) {
			return "";
			
		}
	}
	
	public String getSystemTextPostToPre() {
		try{
			return systemTextMigrationPostToPre.getText();
		}
		catch(Exception e) {
			return "";
			
		}
	}
	
	public String getMigrationReqCreatedTime() {
		try{
			return creationTime.getText();
		}
		catch(Exception e) {
			return "";
			
		}
	}
	
	
	
}
