package com.telus.wnp.steps;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;

import com.test.utils.Status;
import com.telus.wnp.pages.PACDashboardPage;
import com.telus.wnp.pages.PACSearchPage;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSession;
import com.test.ui.actions.WebDriverSteps;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for PAC Search page (Steps) > AUTHOR: x241410
 ****************************************************************************
 */

public class PACSearchPageSteps extends BaseSteps {

	/**
	 * Method Description: The purpose of this method is to verifySubscriber Status
	 * from PAC
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifySubscriberStatusFromPAC(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO, "STEP === PAC Validation --> PORT/OSP Cancel Request check in PAC ===");

		try {

			LoginPageSteps.appLogin_PAC();
			PACSearchPageSteps.verifyPACHomePageIsDisplayed();

			JSONObject pacConstants = testDataJson.getJSONObject("PAC_CONSTANTS");
			String expectedErrMsg = pacConstants.getString("NO_PORT_OSP_CANCEL_REQ_PRESENCE_ERR_MSG");

			PACSearchPageSteps.searchPhoneNo(sub);

			String actualErrMsg = PACSearchPageSteps.getPortOSPCancelStatus_PAC();

			Validate.assertEquals(actualErrMsg, expectedErrMsg, "Port/OSP Cancel request Msg validation fails", false);
			Reporting.logReporter(Status.PASS,
					"STEP === PAC Validation --> PORT/OSP Cancel Request Message not displayed ===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify Subscriber Status From PAC" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify Subscriber Status From PAC");
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify no port request
	 * present for the subscriber
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifyNoPortReqForSubFromPAC(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO,
				"STEP === PAC Validation --> PORT/OSP Cancel Request Not Present in PAC ===");

		try {

			LoginPageSteps.appLogin_PAC();

			PACSearchPageSteps.verifyPACHomePageIsDisplayed();
			String expectedErrMsg = testDataJson.getString("NO_PORT_OSP_CANCEL_REQ_PRESENCE_ERR_MSG");

			PACSearchPageSteps.searchPhoneNo(sub);
			String actualErrMsg = PACSearchPageSteps.getPortOSPCancelStatus_PAC();

			Validate.assertEquals(actualErrMsg, expectedErrMsg, "Port/OSP Cancel request Msg validation fails", false);
			// Validate.takeStepFullScreenShot("Port/OSP Cancel request Msg validation is
			// PASS", Status.PASS);
			Reporting.logReporter(Status.PASS,
					"====PAC Validation --> Displayed Error Message for '" + sub + "' is [" + actualErrMsg + "]===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify PORT/OSP Cancel Request Status Msg" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify PORT/OSP Cancel Request Status Msg");
		}

	}

	/**
	 * Method Description: The purpose of this method is to verify no port request
	 * present for the subscriber
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifyNoPortReqForAnotherSubFromPAC(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO,
				"STEP === PAC Validation --> PORT/OSP Cancel Request Not Present in PAC ===");

		try {

			String expectedErrMsg = testDataJson.getString("NO_PORT_OSP_CANCEL_REQ_PRESENCE_ERR_MSG");

			PACSearchPageSteps.searchPhoneNo(sub);
			String actualErrMsg = PACSearchPageSteps.getPortOSPCancelStatus_PAC();

			Validate.assertEquals(actualErrMsg, expectedErrMsg, "Port/OSP Cancel request Msg validation fails", false);
			// Validate.takeStepFullScreenShot("Port/OSP Cancel request Msg validation is
			// PASS", Status.PASS);
			Reporting.logReporter(Status.PASS,
					"====PAC Validation --> Displayed Error Message for '" + sub + "' is [" + actualErrMsg + "]===");
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify PORT/OSP Cancel Request Status Msg" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify PORT/OSP Cancel Request Status Msg");
		}

	}

	/**
	 * Method Description: The purpose of this method is to do validation check for
	 * multiban
	 * 
	 * @param testDataJson
	 * @param sub1
	 * @param sub2
	 */
	public static void multiBanValidationCheck_PAC(JSONObject testDataJson, String sub1, String sub2) {

		Reporting.logReporter(Status.INFO, "STEP === PAC Validation --> PORT/OSP Cancel Request check in PAC ===");

		try {

			LoginPageSteps.appLogin_PAC();
			PACSearchPageSteps.verifyPACHomePageIsDisplayed();

			verifySubscriberStatusForMultiBanFromPAC(testDataJson, sub1);
			verifySubscriberStatusForMultiBanFromPAC(testDataJson, sub2);

		} catch (JSONException e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify the expected error message from PAC " + e);
			Validate.takeStepFullScreenShot("MultiBan validation failed", Status.ERROR);
		}

	}

	/**
	 * Method Description: The purpose of this method is to perform status check for
	 * multiban
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifySubscriberStatusForMultiBanFromPAC(JSONObject testDataJson, String sub) {

		JSONObject pacConstants = testDataJson.getJSONObject("PAC_CONSTANTS");
		String expectedErrMsg = pacConstants.getString("NO_PORT_OSP_CANCEL_REQ_PRESENCE_ERR_MSG");

		PACSearchPageSteps.searchPhoneNo(sub);
		String actualErrMsg = PACSearchPageSteps.getPortOSPCancelStatus_PAC();
		Validate.assertEquals(actualErrMsg, expectedErrMsg, "Port/OSP Cancel request Msg validation fails", false);
	}

	/**
	 * Method Description: The purpose of this method is to perform search for
	 * particular subscriber
	 * 
	 * @param phoneNo
	 */
	public static void searchPhoneNo(String phoneNo) {
		Reporting.logReporter(Status.INFO,
				"STEP === PAC Search Page --> Enter Phone Number and click submit : " + phoneNo);
		PACSearchPage PACSearchPage = new PACSearchPage();
		BaseSteps.Waits.waitForElementVisibility(PACSearchPage.ctn, 10);
		BaseSteps.Waits.waitGeneric(10000);
		BaseSteps.SendKeys.clearFieldAndSendKeysUsingJS(PACSearchPage.ctn, phoneNo);

		Validate.takeStepScreenShot("Entered TN for Search");

		BaseSteps.Clicks.clickElement(PACSearchPage.submit);
	}

	/**
	 * Method Description: The purpose of this method is to get he status of the
	 * subscriber
	 */
	public static void getStatus() {

		String status = WebDriverSession.getWebDriverSession().findElement(By.xpath("//font/ul[1]/li[1]")).getText();
		Validate.assertEquals(status, "There is no Port/OSP Cancel request that meets this criteria.", false);
		System.out.println("Assert passed");

	}

	/**
	 * Method Description: The purpose of this method is to get status based on
	 * porting status
	 * 
	 * @param portingStatus
	 */
	public static void getStatus(String portingStatus) {

		if (portingStatus.equals("BeforePort")) {
			String status = WebDriverSession.getWebDriverSession().findElement(By.xpath("//font/ul[1]/li[1]"))
					.getText();
			Validate.assertEquals(status, "There is no Port/OSP Cancel request that meets this criteria.", false);
			System.out.println("Assert passed");
		} else
			System.out.println("Assert Failed");

	}

	/**
	 * Method Description: OLD: The purpose of this method is to perform pac
	 * validation
	 * 
	 * @param sub
	 */
	public static void pacValidation(String sub) {
		WebDriverSteps.openApplication("TELUS_PAC");
		PACSearchPage PACSearchPage = new PACSearchPage();
		LoginPageSteps.singInSmartDesktop("x108557", "Passw0rd");
		PACSearchPageSteps.searchPhoneNo(sub);
		PACSearchPageSteps.getStatus();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
		BaseSteps.Clicks.clickElement(PACSearchPage.logout);
		WebDriverSteps.closeTheBrowser();

	}

	/**
	 * Method Description: The purpose of this method is to verify if PAC home page
	 * is displayed
	 */
	public static void verifyPACHomePageIsDisplayed() {
		Reporting.logReporter(Status.INFO, "STEP ===> PAC homepage is displayed after successful login ");

		PACSearchPage PACSearchPage = new PACSearchPage();
		boolean actual = PACSearchPage.isHomePageDisplayed();
		System.out.println("===========> PAC");

		Validate.assertEquals(actual, true, "Unable to login into PAC.", false);
	}

	/**
	 * Method Description: The purpose of this method is to verify OSP Cancel Status
	 * 
	 * @return status if successful else return empty string
	 */
	public static String getPortOSPCancelStatus_PAC() { // pass one more

		PACSearchPage PACSearchPage = new PACSearchPage();
		String stat;
		try {
			stat = PACSearchPage.portOSPCancelRequestStatus();
		} catch (Exception e) {
			Reporting.logReporter(Status.ERROR, "========>Port/OSP Cancel Request Exists in PAC<========");
			return "";
		}

		return stat;
	}

	/**
	 * Method Description: The purpose of this method is to get status message
	 * 
	 * @return status if successful else return empty string
	 */
	public static String getStatusMsg_PAC() {

		PACSearchPage PACSearchPage = new PACSearchPage();
		String stat;
		try {
			stat = PACSearchPage.portOSPCancelRequestStatus();
		} catch (Exception e) {
			return "";
		}

		return stat;

	}

	public static void verifyCancelAndClonedRequestStatus(String cancelledReqStatus, String clonedReqStatus) {
		PACSearchPage PACSearchPage = new PACSearchPage();

		if (PACSearchPage.getNumberOfRecords() > 0) {

			Validate.assertEquals(PACSearchPage.getCancelledRequestEntryStatus(), cancelledReqStatus,
					"Cancelled Request Status Mismatch", false);
			Validate.assertEquals(PACSearchPage.getClonedRequestEntryStatus(), clonedReqStatus,
					"Cloned Request Status Mismatch", false);

		} else
			Reporting.logReporter(Status.FAIL, "Multiple Records not displayed !");

	}

	/**
	 * Method Description: The purpose of this method is to verify no port request
	 * present for the subscriber
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifyNoPortReqForSubFromPAC_NEW(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO,
				"STEP === PAC Validation --> PORT/OSP Cancel Request Status Check in PAC ===");

		try {

			LoginPageSteps.appLogin_PAC();

			PACSearchPageSteps.verifyPACHomePageIsDisplayed();
			PACSearchPageSteps.searchPhoneNo(sub);			
			verifyNoPortRequestInternal(testDataJson, sub);
			
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify PORT/OSP Cancel Request Status" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify PORT/OSP Cancel Request Status");
		}

	}

	public static void verifyNoPortRequestInternal(JSONObject testDataJson, String sub) {
		try {
			String expectedErrMsg = testDataJson.getString("NO_PORT_OSP_CANCEL_REQ_PRESENCE_ERR_MSG");
			String actualErrMsg = PACSearchPageSteps.getPortOSPCancelStatus_PAC();
			if (!actualErrMsg.isEmpty()) {
				Validate.assertEquals(actualErrMsg, expectedErrMsg, "Port/OSP Cancel request Msg validation fails",
						false);
				Reporting.logReporter(Status.INFO,
						"Port Request Status for the Subscriber: " + sub + " in PAC is: " + actualErrMsg);

			} else {
				PACDashboardPage PACDashboardPage = new PACDashboardPage();
				PACDashboardPageSteps.handleMultipleRecordsDisplayed();
				String actualPortStatus = PACDashboardPage.getPortStatus_New();

				boolean stat = false;
				if (actualPortStatus.equalsIgnoreCase("Cancelled") || actualPortStatus.equalsIgnoreCase("Complete")) {
					stat = true;
				}

				Validate.assertEquals(true, stat, "========>Port Status is not as expected. Current Port Status is : " + actualPortStatus, false);
				Reporting.logReporter(Status.INFO,
						"Current Request Status for the Subscriber: " + sub + " in PAC is: " + actualPortStatus);
			}

		}
		catch(Exception e) {
			Reporting.logReporter(Status.INFO, "Current Request Status for the Subscriber: " + sub + " in PAC is not as Expected");
			Validate.assertTrue(false, "Exception Occured: Unable to verify PORT/OSP Cancel Request Status");

		}

	}

	
	/**
	 * Method Description: The purpose of this method is to verify no port request
	 * present for the subscriber
	 * 
	 * @param testDataJson
	 * @param sub
	 */
	public static void verifyNoPortReqForAnotherSubFromPAC_NEW(JSONObject testDataJson, String sub) {

		Reporting.logReporter(Status.INFO,
				"STEP === PAC Validation --> PORT/OSP Cancel Request Status Check in PAC ===");

		try {
			PACDashboardPage PACDashboardPage = new PACDashboardPage();
			BaseSteps.Waits.waitForElementToBeClickable(PACDashboardPage.searchPageLinkBtn);
			BaseSteps.Clicks.clickElement(PACDashboardPage.searchPageLinkBtn);
		
			PACSearchPageSteps.searchPhoneNo(sub);			
			verifyNoPortRequestInternal(testDataJson, sub);
			
		} catch (Exception e) {
			Reporting.logReporter(Status.DEBUG, "Unable to verify PORT/OSP Cancel Request Status" + e);
			Validate.assertTrue(false, "Exception Occured: Unable to verify PORT/OSP Cancel Request Status");
		}

	}

}
