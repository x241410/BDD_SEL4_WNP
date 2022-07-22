package com.telus.wnp.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.utils.Status;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.WebDriverSession;

public class SmartDesktopHRTPage extends WebDriverSession {

	public SmartDesktopHRTPage() {

		PageFactory.initElements(getWebDriverSession(), this);

	}

	@FindBy(xpath = "//a[contains(text(), 'HRT')]")
	public WebElement hrtLink;

	@FindBy(xpath = "//a[contains(text(), 'Home')]")
	public WebElement hrtHomeTab;

	@FindBy(xpath = "//a[contains(text(), 'Assign')]")
	public WebElement hrtAssignTab;

	@FindBy(xpath = "//*[@id='offers-search-form']/div[1]/div[3]/div/select[2]")
	public WebElement hrtTransactionTypeDropDown; // Migration Offer Type, Contract Renewal Offers

	@FindBy(xpath = "//*[@id='offers-search-form']/button[2]")
	public WebElement hrtSearchButton;

	@FindBy(xpath = "//*[@id='assignable-offers']//div[2]/div/div[1]/a")
	public WebElement hrtAvailableOffers;

	@FindBy(xpath = "//*[@id='assignable-offers']//a[contains(text(), 'Assign')]")
	public WebElement hrtAssignOfferLink;

	@FindBy(xpath = "//button[contains(text(), 'OK')]")
	public WebElement hrtOkBtnFromPopUp;

	@FindBy(xpath = "//div[contains(text(), 'Success')]")
	public WebElement hrtOfferAssignmentSuccessMsgLabel;

	@FindBy(xpath = "//*[@id='assignable-offers']/div/span[2]")
	public WebElement hrtNoOffersAvailable;
	
	@FindBy(xpath = "//h4[contains(text(), 'Offering Assignment Tool')]")
	public WebElement offerAssignmentToolText;

	@FindBy(xpath = "//*[@id='available-offers']/div[4]/div/div[1]/div/div[1]/a/span[1]")
	public WebElement hrtAvailableOffersList;

	@FindBys(@FindBy(xpath = "//*[@id='assignable-offers']/div/div"))
	public List<WebElement> listofAvailableOffers;

	@FindBys(@FindBy(xpath = "//*[@id='available-offers']/div[4]/div/div"))
	public List<WebElement> hrtHomePageAvailableOffers;

	@FindBys(@FindBy(xpath = "//*[@id='offer-group']/div[1]/a"))
	public List<WebElement> hrtPostToPreOfferFromHome;

	@FindBys(@FindBy(xpath = "//*[@id='offer-group']//a[contains(text(), 'Redeem')]"))
	public List<WebElement> hrtPostToPreOfferRedeemLink;

	@FindBy(xpath = "//*[@id='offer-group']//a[contains(text(), 'Redeem')]")
	public WebElement hrtRedeemLink;

	@FindBy(xpath = "//*[@id='redemption-method']//select")
	public WebElement hrtRedemptionMethodDrpDwn;

	@FindBy(xpath = "//*[@id='redemption-method']//button[normalize-space(text())='Validate']")
	public WebElement hrtRedemptionMethodValidateBtn;

	@FindBy(xpath = "//*[@id='post-to-pre']/div[2]//tr/td[2]/select")
	public WebElement hrtMigrationReasonDrpDwn;

	@FindBy(xpath = "//*[@id='post-to-pre']/div[3]//tr/td[2]/select")
	public WebElement hrtStarterKitTypeDrpDwn;

	@FindBy(xpath = "//*[@id='post-to-pre']//button[normalize-space(text())='Validate']")
	public WebElement hrtStarterKitValidateBtn;

	@FindBy(xpath = "//*[@name='title']")
	public WebElement hrtTitleDrpDwn;

	@FindBy(xpath = "//*[@name='email']")
	public WebElement hrtEmailId;

	@FindBy(xpath = "//*[@name='pin']")
	public WebElement hrtPin;

	@FindBy(xpath = "//*[@name='confirm']")
	public WebElement hrtConfirPin;

	@FindBy(xpath = "//*[@id='account-form']//button[normalize-space(text())='Submit']")
	public WebElement hrtAccountFormSubmitBtn;

	@FindBy(xpath = "//*[@id='equipment-validation']/div[2]//tr[1]/td[3]/label/input")
	public WebElement hrtSIMOnlyChkBox;

	@FindBy(xpath = "//*[@id='equipment-validation']/div[2]//tr[2]/td[3]/label/input")
	public WebElement hrtKeepExistingSIMChkBox;

	@FindBy(xpath = "//*[@id='equipment-validation']//div[2]//button[normalize-space(text())='Validate']")
	public WebElement hrtEquipmentValidateBtn;

	@FindBy(xpath = "//*[@id='equipment-validation']/div[2]//tr[3]/td[2]/select")
	public WebElement hrtDeviceTypeDrpDwn;

	@FindBy(xpath = "//*[@id='equipment-validation']//div[4]//button[normalize-space(text())='Validate']")
	public WebElement hrtDeviceTypeValidateBtn;

	@FindBy(xpath = "//*[@id='price-plan-and-services']/div[3]/span[2]/select")
	public WebElement hrtRatePlanTopUpDrpDwn;

	@FindBy(xpath = "//*[@id='price-plan-and-services']//a[(text()= 'Add')]")
	public WebElement hrtAddTopUpAmountLinkBtn;

	@FindBy(xpath = "//*[@id='price-plan-and-services']//div[5]//button[normalize-space(text())='Validate']")
	public WebElement hrtPPAndSValidateBtn;

	@FindBys(@FindBy(xpath = "//*[@id='price-plan-and-services']/div[4]/table/tbody/tr[1]/td[1]//a")) // Prepaid data
																										// Services
																										// Block
																										// ($0.00),
																										// Nationwide
																										// Talk & Text
																										// 25 ($25.00)
	public List<WebElement> hrtHomePriceAndPlanToSelect;

	@FindBy(xpath = "//*[@id='price-plan-and-services']/div[4]//tr[1]/td[2]/button[1]/i")
	public WebElement hrtMoveToRightPaneBtn;

	@FindBy(xpath = "//*[@id='RedeemReviewAndSubmit']/button")
	public WebElement hrtFinalReviewBtn;

	/*
	 * 
	 * CREDIT CARD LOCATORS
	 * 
	 * 
	 */

	@FindBy(xpath = "//div[10]//div[3]//fieldset/table/tbody/tr[7]/td[2]/div/button[1]")
	public WebElement retrieveCreditCardDetailsBtn;

	@FindBy(xpath = "//form/div[3]//div[2]/li[2]")
	public WebElement cardDetailsNotAvailableLabel;

	@FindBy(xpath = "//*[@id='redemption-summary']/div[13]/div[2]/div[5]//tr[1]/td[1]/input")
	public WebElement hrtOfferTermsChkBox;

	@FindBy(xpath = "//*[@id='redemption-summary']/div[13]/div[2]/div[5]//tr[2]/td[1]/input")
	public WebElement hrtAuthorizeTelusChkBox;

	@FindBy(xpath = "//*[@id='redemption-summary']/div[14]/button")
	public WebElement hrtFinalReviewSubmitBtn;

	@FindBy(xpath = "//button[contains(text(), 'OK')]")
	public WebElement finalOKButtonFromPopUp;

	@FindBy(xpath = "//*[@id='submit-progress']//h3[contains(text(), 'Redemption Summary')]")
	public WebElement redemptionSummary;
	
	@FindBy(xpath = "//h4[contains(text(), 'Address Alternative')]")
	public WebElement addressAlternative;
	
	@FindBy(xpath = "//button[@ng-click='acceptAddress(address)']")
	public WebElement acceptSuggestedAddress;
	
	
	@FindBy(xpath = "//button[contains(text(),'View Error')]")
	public WebElement viewErrorBtn;
	
	

	public boolean clickPostToPreOffer(String offerName) {
		boolean flag = false;

		for (WebElement ele : hrtHomePageAvailableOffers) {

			if (ele.getText().toUpperCase().contains(offerName.toUpperCase())) {
				BaseSteps.Waits.waitForElementToBeClickableLongWait(ele);
				ele.click();
				flag = true;
				break;
			}
		}
		
		return flag;
	}

	public void selectPriceAndPlan(String plan) {

		for (WebElement ele : hrtHomePriceAndPlanToSelect) {

			if (ele.getText().contains(plan)) {
				ele.click();
				break;
			}
		}
	}

	public void clickRedeemPostToPreOfferLink() {

		for (WebElement ele : hrtPostToPreOfferFromHome) {

			if (ele.getText().equalsIgnoreCase(" Postpaid to Prepaid MIgration")) {
				hrtRedeemLink.click();
				break;

			}
		}
	}

	public String getHRTOfferAssignmentSuccessMsgLabelText() {
		return hrtOfferAssignmentSuccessMsgLabel.getText();
	}

	public boolean getHRTNoOffersAvailableMsgDisplayedStatus() {
		boolean status = false;

		if (hrtNoOffersAvailable.isDisplayed()) {
			if (hrtNoOffersAvailable.getText().contains("There are no offers matching the search criteria.")) {
				return true;
			}
		}

		return status;
	}

	public boolean getPPnSValidateBtnEnabledStatus() {
		if (hrtPPAndSValidateBtn.isDisplayed()) {
			return hrtPPAndSValidateBtn.isEnabled();
		} else
			return false;

	}

	public String getFirstOfferText() {
		return hrtAvailableOffersList.getText();
	}

	public void clickOnAssignSpecificOfferLink(String offerName) {
		// POST2PRE-TOM - Postpaid to Prepaid Migration
		for (int i = 2; i < listofAvailableOffers.size(); i++) {

			if (listofAvailableOffers.get(i).getText().contains(offerName)) {
				WebDriverWait wait = new WebDriverWait(WebDriverSession.getWebDriverSession(), Duration.ofSeconds(10));
				/*
				 * WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(
				 * By.xpath("//*[@id='noteDetailsPaneDecorator_Customer Response : " + i +
				 * "']")));
				 */

				WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id='assignable-offers']/div/div[" + i + "]/div/div[1]/span[4]/span/a")));
				e.click();
			}
		}

	}

	public boolean isRedemptionSummaryDisplayed() {

		try {
			return redemptionSummary.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public WebElement getRetrieveCreditCardDetails(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[10]//div[3]//fieldset/table/tbody/tr[7]/td[2]/div/button[1]")));

	}

	public WebElement getCardDetailsNotAvailableMsg(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form/div[3]//div[2]/li[2]")));

	}
	
	public String creditCardUnavailableMsgNotDisplayed(WebDriver driver) {
		String msg = null;
		try {
			msg = getCardDetailsNotAvailableMsg(driver).getText();
			return msg;
		}
		catch(Exception e) {
			Reporting.logReporter(Status.INFO, "Credit Card Details Unavailable Msg Not displayed"+ e);
			return "";
		}
		
	}

	
	public WebElement getHrtOfferTermsChkBox(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id='redemption-summary']/div[13]/div[2]/div[5]//tr[1]/td[1]/input")));

	}
	
	public WebElement getHrtAuthorizeTelusChkBox(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id='redemption-summary']/div[13]/div[2]/div[5]//tr[2]/td[1]/input")));

	}
	
	
	public WebElement getHrtFinalReviewSubmitBtn(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id='redemption-summary']/div[14]/button")));

	}
	
	public WebElement getFinalOKButtonFromPopUp(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[contains(text(), 'OK')]")));

	}
	

	public WebElement getRedemptionSummary(WebDriver driver) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='submit-progress']//h3[contains(text(), 'Redemption Summary')]")));

	}
	
	public boolean isRedemptionSummaryDisplayed_2(WebDriver driver) {

		try {
			return getRedemptionSummary(driver).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
}
