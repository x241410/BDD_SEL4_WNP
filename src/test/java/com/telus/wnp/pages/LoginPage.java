package com.telus.wnp.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.utils.Status;
import com.test.reporting.Reporting;
import com.test.ui.actions.WebDriverSession;

public class LoginPage extends WebDriverSession {

	public LoginPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(id = "username")
	public WebElement userNameInputBox;

	@FindBy(id = "password")
	public WebElement passwordInputBox;

	@FindBy(xpath = "//input[@name='Login.Submit'] | //button[contains(text(),'Log in')]")
	public WebElement loginButton;

	@FindBy(id = "username")
	public WebElement userNameInputBox_OBO;

	@FindBy(id = "password")
	public WebElement passwordInputBox_OBO;

	@FindBy(xpath = "//button[contains(text(),'Log in')]")
	public WebElement loginButton_OBO;

	@FindBy(xpath = "//input[@name='IDToken1']")
	public WebElement verificationCodeInputBox;

	@FindBy(xpath = "//input[@value='Submit Verification Code']")
	public WebElement verificationCodeSubmitBtn;

	@FindBy(id = "username")
	public WebElement userName_SmartDesktop;

	@FindBy(id = "password")
	public WebElement password_SmartDesktop;

	@FindBy(xpath = "//button[contains(text(),'Log in')]")
	public WebElement loginButton_SmartDesktop;

	@FindBy(xpath = "//input[@name='j_username']")
	public WebElement username_smp;

	@FindBy(xpath = "//input[@name='j_password']")
	public WebElement password_smp;

	@FindBy(xpath = "//input[@name='submitimg']")
	public WebElement submitButton_smp;

	@FindBy(xpath = "//input[@value='  Login  ']")
	public WebElement loginButton_smg;

	@FindBy(xpath = "//*[@id='proceed-button']")
	public WebElement pacDev_handleSiteNotSecure;

	@FindBy(xpath = "//span[contains(text(),'Please login')]")
	public WebElement sessionExpireMsg;

	@FindBy(xpath = "//*[@data-testid='dependentSvg']")
	public WebElement pleaseLoginCloseIconLSR;
	
	
	@FindBy(xpath = "//a[text()='here']")
	public WebElement clickHereToLogin;
	
	/*
	 * TruNumber Gateway - SMG
	 */
	
	@FindBy(xpath = "//*[@id='userId']")
	public WebElement userId;
	
	@FindBy(xpath = "//*[@id='password']")
	public WebElement userPwd;
	
	@FindBy(xpath = "//*[@id='loginBtn']")
	public WebElement loginBtn;
	
	@FindBy(xpath = "//*[@id='noticeTitle']")
	public WebElement passwordResetNoticeTitle;
	
	@FindBy(xpath = "//*[@name='ok']")
	public WebElement okBtnFromNoticeTitle;
	

	public boolean siteNotSecurePopUpDisplayedStatus() {
		boolean stat = false;
		try {
			stat = pacDev_handleSiteNotSecure.isDisplayed();
		} catch (Exception e) {
			return false;
		}

		return stat;
	}

	public boolean getSessionExpireMessageDisplayedStatus() {
		try {
			return sessionExpireMsg.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	
}
