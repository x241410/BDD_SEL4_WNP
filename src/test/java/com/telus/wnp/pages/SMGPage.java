package com.telus.wnp.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.WebDriverSession;
public class SMGPage extends WebDriverSession{

	public SMGPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}
	@FindBy(xpath = "//a[@id='r1:0:np1:cni1j_id_2::disclosureAnchor']")
	public  WebElement CWNPTest;
	
	@FindBy(xpath = "//a[@id='r1:0:pt_region1:1:pt1:t1:0:cl4']")
	public  WebElement PortCenter;
	
	@FindBy(xpath = "//span[contains(text(),'Port Request/Response Administration')]")
	public  WebElement Port_Request;
	
	@FindBy(xpath = "//span[contains(text(),'ICP Port Details')]")
	public  WebElement Port_Details;
	
	@FindBy(xpath = "//span[contains(text(),'ICP Port Selection')]")
	public  WebElement Port_Selection;
	
	@FindBy(xpath = "//span[contains(text(),'Subscription Administration')]")
	public  WebElement Subscription_Administration;
	
	@FindBy(xpath = "//span[contains(text(),'SOA Selection')]")
	public  WebElement SOA_Selection;
	 
	@FindBy(xpath = "//*[@id='btn-ok']")
	public  WebElement multiLoginPopUpOkBtn;
	
	
	@FindBy(xpath = "//a[contains(text(), 'CWNP Test')]")
	public WebElement homePageHelpText;
	
	public boolean isHomePageDisplayed() {
		System.out.println(homePageHelpText.getText().toString());
		return homePageHelpText.getText().toString().contains("CWNP Test");
	}
	
	public boolean ismultiLoginPopUpOkBtnDisplayed() {
		return multiLoginPopUpOkBtn.isDisplayed();
	}
	
	/*public String getRequestNumber() {
		return requestNumber.getAttribute("innerHTML").toString();
	}
	//QRY_REQ_NO_ID
*///	id="BILLFIRSTNM_ID"
//	SAVE_ID
	//id="PORTED_NAME_0"
//			id="BILLLASTNM_ID"
//			id="BUSNM_ID"
//			BILLSTNUM_ID
//			id="BILLSTNM_ID"
//			id="btn-confirm"
	
}