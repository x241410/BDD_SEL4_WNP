package com.telus.wnp.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.test.ui.actions.WebDriverSession;

public class SMGICPPortDetailsPage extends WebDriverSession {

	public SMGICPPortDetailsPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//*[@id='CLEAR_ID']")
	public WebElement clearBtn;

	@FindBy(xpath = "//select [@id='select-PORT_DETAILS_ACTION_ID']")
	public WebElement select_Port_Details;

	@FindBy(xpath = "//option [@value='CreatePortOutRequest']")
	public WebElement CreatePortOutRequest;

	@FindBy(xpath = "//option [@value='CreatePortInRequest']")
	public WebElement CreatePortInRequest;

	@FindBy(xpath = "//option [@value='CreateCancelSvcRequest']")
	public WebElement CreateCancelSvcRequest;

	@FindBy(xpath = "//select [@id='select-NNSP_ID']")
	public WebElement NNSP_ID;

	@FindBy(xpath = "//select [@id='select-NLSP_ID']")
	public WebElement NLSP_ID;

	@FindBy(xpath = "//select [@id='select-ONSP_ID']")
	public WebElement ONSP_ID;

	@FindBy(xpath = "//select [@id='select-AUTOACT_ID']")
	public WebElement AUTOACT_ID;

	@FindBy(xpath = "//input [@id='DDDT_ID']")
	public WebElement desired_Due_Date;

	@FindBy(xpath = "//input [@id='LRN_ID']")
	public WebElement LRN_ID;
	
	@FindBy(xpath = "//*[@id='LRN_ID:textOnly']")
	public WebElement UPDATED_LRN_ID;
	

	@FindBy(xpath = "//input [@id='INIT_ID']")
	public WebElement requestor;

	@FindBy(xpath = "//input [@id='IMPCON_ID']")
	public WebElement new_SP_contact;

	@FindBy(xpath = "//input [@id='TEL_NO_IMPCON_ID']")
	public WebElement contact_TN;

	@FindBy(xpath = "//input [@id='PORTED_TN_0']")
	public WebElement PORTED_TN;

	@FindBy(xpath = "//input [@id='PORTED_TN_1']")
	public WebElement PORTED_TN2;

	@FindBy(xpath = "//input [@id='DATED_ID']")
	public WebElement agency_date;

	@FindBy(xpath = "//input [@id='AUTHNM_ID']")
	public WebElement agency_Name;

	@FindBy(xpath = "//input [@id='ACCT_ID']")
	public WebElement ACCT_ID;

	@FindBy(xpath = "//input [@id='ESN_ID']")
	public WebElement ESN_IMEI;

	@FindBy(xpath = "//input [@id='BILLFIRSTNM_ID']")
	public WebElement BILLFIRSTNM;

	@FindBy(xpath = "//input [@id='BILLLASTNM_ID']")
	public WebElement BILLLASTNM;

	@FindBy(xpath = "//input [@id='BUSNM_ID']")
	public WebElement BUSNM;

	@FindBy(xpath = "//input [@id='BILLSTNM_ID']")
	public WebElement Street;

	@FindBy(xpath = "//input [@id='CITY_ID']")
	public WebElement city;

	@FindBy(xpath = "//input [@id='ZIP_CODE_ID']")
	public WebElement zip_code;

	@FindBy(xpath = "//*[@id='ADD_REQTNS_ID']")
	public WebElement addMutipleTNBtn;

	// @FindBy(name = "select")
	@FindBy(xpath = "//input [@name='select']")
	public WebElement portedTNCheckBox;

	@FindBy(xpath = "//*[@id='RequestDataAccordion']/div[2]//td[2]//tbody[3]/tr/td[2]/input")
	public WebElement portedTNMultiBanChkBox;

	@FindBy(xpath = "//*[@id='ResponseDataAccordion']//input")
	public WebElement portedTNChkBoxResponse;
	
	@FindBy(xpath = "//*[@id='RequestDataAccordion']//input")
	public WebElement portedTNChkBoxResponseWLN;

	@FindBy(xpath = "//*[@id='RequestDataAccordion']/div[2]//tbody[2]/tr/td[2]/input")
	public WebElement checkSUBCheckBox;

	@FindBy(xpath = "//*[@id='DDT_ID']")
	public WebElement dueDateTime;

	@FindBy(xpath = "//*[@id='REP_ID']")
	public WebElement oldSPContact;

	@FindBy(xpath = "//*[@id='TEL_NO_IMPCON_ID']")
	public WebElement contactTN;

	@FindBy(xpath = "//*[@id='CD_TSENT_ID:textOnly']")
	public WebElement dueDateTimeSentForReference;

	@FindBy(xpath = "//*[@id='QRY_REQ_NO_ID:textOnly']")
	public WebElement requestNumber;

	@FindBy(xpath = "//select [@id='select-STATE_ID']")
	public WebElement state;

	@FindBy(xpath = "//input [@id='PORTED_NAME_0']")
	public WebElement subName;

	@FindBy(xpath = "//input [@id='PORTED_NAME_1']")
	public WebElement subName2;

	@FindBy(xpath = "//button [@id='SAVE_ID']")
	public WebElement save;

	@FindBy(xpath = "//div [@id='QRY_REQ_NO_ID:textOnly']")
	public WebElement reqId;

	@FindBy(xpath = "//*[@id='remarksreq']")
	public WebElement remarks;

	@FindBy(xpath = "//*[@id='remarksresp']")
	public WebElement remarksResponse;

	@FindBy(xpath = "//*[@id='ServiceProviderAccordion']/div[2]//tr[8]/td[2]/span/smg-select/div/div/span")
	public WebElement responseType;

	@FindBy(xpath = "//select [@id='select-RT_ID']")
	public WebElement provideResponseType;

	@FindBy(xpath = "//*[@id='select-VIEW_DCODE_RCODE_JCODELIST_ID']")
	public WebElement selectResponseCodeFromResponseData;

	@FindBy(xpath = "//*[@id='INFO_MSD_ID_0']")
	public WebElement requestSubmissionMsg;

	@FindBy(xpath = "//*[@id='ERROR_MSD_ID_0']")
	public WebElement errorMsgOnSubmission;

	@FindBy(xpath = "//*[@id='ResponseDataAccordion']/div[1]/div")
	public WebElement responseDataSection;

	@FindBy(xpath = "//*[@id='VIEW_DCODE_RCODE_JCODELIST_ID:textOnly']")
	public WebElement responseCodeText;

	@FindBy(xpath = "//div/smg-dialog[2]/div/footer/div/button[1]")
	public WebElement DDDTConfirmPopUpOKBtn;

	@FindBy(xpath = "//select [@id='select-PORT_REC_TYPE_ID']")
	public WebElement queryPortRecordType;

	@FindBy(xpath = "//input [@id='QRY_PORTED_NUM_ID']")
	public WebElement queryPortedTN;

	@FindBy(xpath = "//select [@id='select-QRY_OWNER_ID']")
	public WebElement queryOwnerSPID;

	@FindBy(xpath = "//button [@id='QUERY_ID']")
	public WebElement queryBtn;

	@FindBy(xpath = "//*[@id='RCODE_0:textOnly']")
	public WebElement responseCodeVerifyText;

	@FindBy(xpath = "//*[@id='RDET_0:textOnly']")
	public WebElement responseCodeDetailVerifyText;

	@FindBy(xpath = "//*[@id='ResponseDataAccordion']/div[1]/div/span")
	public WebElement responseDataCollapsedStatus;

	@FindBy(xpath = "//*[@id='DDDT_ID']")
	public WebElement changeDDDTField;
	
	/*
	 * WLN Related Fields
	 */
	
	@FindBy(xpath = "//*[@id='OLSP_ID']")
	public WebElement olsp_Id_wln;
	
	@FindBy(xpath = "//*[@id='ORSELLNM_ID']")
	public WebElement lsProvider_Id_wln;
	

	public boolean expandResponseDataSection() {
		String typeValue = responseDataCollapsedStatus.getAttribute("class");

		if (typeValue.contains("right"))
			return true;
		else
			return false;
	}

	public String responseTypeFromPortResponse() {
		return responseType.getText();

	}

	public String getRequestSubmissionMsg() {
		return requestSubmissionMsg.getText();

	}

	public String getResponseCodeText() {
		return responseCodeText.getText();

	}

	public String getResponseCodeDetailsVerifyText() {
		return responseCodeDetailVerifyText.getText();

	}

	public String getResponseCodeVerifyText() {
		return responseCodeVerifyText.getText();

	}

	public String getDueDateTimeSentText() {
		return dueDateTimeSentForReference.getText();

	}

	public String getErrorMsgTextOnSubmission() {
		return errorMsgOnSubmission.getText();
	}

	public boolean isDDDTConfirmPopUpDisplayed() {
		return DDDTConfirmPopUpOKBtn.isDisplayed();

	}

	public boolean isQueryButtonEnabled() {
		return queryBtn.isEnabled();

	}

	public String getResponseType() {
		return responseType.getText();
	}
	
	public String getUpdatedLRNText() {
		return UPDATED_LRN_ID.getText();
	}
	
	public boolean portTypeDisplayedStatus() {
		return select_Port_Details.isDisplayed();
	}

}