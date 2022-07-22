package com.telus.wnp.steps;

import org.json.JSONObject;
import org.openqa.selenium.By;

import com.test.utils.Status;
import com.telus.wnp.pages.LoginPage;
import com.telus.wnp.utils.GenericUtils;
import com.test.files.interaction.ReadJSON;
import com.test.reporting.Reporting;
import com.test.ui.actions.BaseSteps;
import com.test.ui.actions.Validate;
import com.test.ui.actions.WebDriverSteps;
import com.test.utils.EncryptionUtils;
import com.test.utils.SystemProperties;

/**
 ****************************************************************************
 * > DESCRIPTION: Support for Login page Steps(common) > AUTHOR: x241410
 ****************************************************************************
 */

public class LoginPageSteps extends BaseSteps {

	private static JSONObject jsonFile = new JSONObject(ReadJSON.parse("Environments.json"));
	private static JSONObject appNames = jsonFile.getJSONObject("APPLICATION_NAMES");
	private static JSONObject userAccessJsonFile = new JSONObject(ReadJSON.parse("UserAccess.json"));

	/**
	 * Method Description: The purpose of this method is to wait until SD Login Page
	 * loads
	 */
	public static void waitUntilPageLoads() {
		LoginPage LoginPage = new LoginPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		BaseSteps.Waits.waitForElementVisibility(LoginPage.userName_SmartDesktop);
		BaseSteps.Waits.waitForElementVisibility(LoginPage.password_SmartDesktop);
		BaseSteps.Waits.waitForElementVisibility(LoginPage.loginButton_SmartDesktop);
	}

	/**
	 * Method Description: The purpose of this method is to wait until SMP page
	 * loads
	 */
	public static void smpwaitUntilPageLoads() {
		LoginPage LoginPage = new LoginPage();
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		BaseSteps.Waits.waitForElementVisibility(LoginPage.username_smp);
		BaseSteps.Waits.waitForElementVisibility(LoginPage.password_smp);
		BaseSteps.Waits.waitForElementVisibility(LoginPage.submitButton_smp);
	}
	
	/**
	 * Method Description: The purpose of this method is to wait until SMGASMS Login Page
	 * loads
	 */
	public static void waitUntilPageLoadsSMGASMS() {
		LoginPage LoginPage = new LoginPage();
		BaseSteps.Waits.waitForElementVisibility(LoginPage.userId, 30);
		BaseSteps.Waits.waitForElementVisibilityLongWait(LoginPage.userPwd);
		BaseSteps.Waits.waitForElementVisibilityLongWait(LoginPage.loginBtn);
	}

	/**
	 * Method Description: The purpose of this method is to login into SMart Desktop
	 * 
	 * @param username
	 * @param password
	 */
	public static void singInSmartDesktop(String username, String password) {
		LoginPage LoginPage = new LoginPage();
		BaseSteps.SendKeys.sendKey(LoginPage.userName_SmartDesktop, username);
		BaseSteps.SendKeys.sendKey(LoginPage.password_SmartDesktop, password);
		Validate.takeStepScreenShot("Login to Smart Desktop");
		BaseSteps.Clicks.clickElement(LoginPage.loginButton_SmartDesktop);
		Reporting.logReporter(Status.INFO,
				"STEP === Login into Smart Desktop --> with Username: " + username + " and Password: " + password);

	}

	/**
	 * Method Description: The purpose of this method is login inot SD app
	 * 
	 * @param appName
	 */
	public static void singInSDApplication(String appName) {

		JSONObject userAccess = userAccessJsonFile.getJSONObject(SystemProperties.EXECUTION_ENVIRONMENT);

		String username = ReadJSON.getString(userAccess, appName + "_username");
		String password = ReadJSON.getString(userAccess, appName + "_password");

		LoginPage LoginPage = new LoginPage();
		waitUntilPageLoads();

		BaseSteps.SendKeys.sendKey(LoginPage.userName_SmartDesktop, username);
		BaseSteps.SendKeys.sendKey(LoginPage.password_SmartDesktop, EncryptionUtils.decode(password));
		Validate.takeStepScreenShot("Login to application");
		BaseSteps.Clicks.clickElement(LoginPage.loginButton_SmartDesktop);
		Reporting.logReporter(Status.INFO,
				"STEP ===> Login into " + appName + " --> with Username: " + username + " and Password: " + password);

	}
	
	/**
	 * Method Description: The purpose of this method is login inot SD app
	 * 
	 * @param appName
	 */
	public static void singInSDApplication_ATT(String appName) {

		JSONObject userAccess = userAccessJsonFile.getJSONObject(SystemProperties.EXECUTION_ENVIRONMENT);

		String username = ReadJSON.getString(userAccess, appName + "_username");
		String password = ReadJSON.getString(userAccess, appName + "_password");

		LoginPage LoginPage = new LoginPage();
		

		BaseSteps.SendKeys.sendKey(LoginPage.userName_SmartDesktop, username);
		BaseSteps.SendKeys.sendKey(LoginPage.password_SmartDesktop, EncryptionUtils.decode(password));
		Validate.takeStepScreenShot("Login to application");
		BaseSteps.Clicks.clickElement(LoginPage.loginButton_SmartDesktop);
		Reporting.logReporter(Status.INFO,
				"STEP ===> Login into " + appName + " --> with Username: " + username + " and Password: " + password);

	}

	/**
	 * Method Description: The purpose of this method is to login into CODA App
	 * 
	 * @param appName
	 */
	public static void singInCODAApplication(String appName) {

		singInSDApplication(appName);
		// HANDLE ERRORS WITH JS IF THE PROFILE TO DO IS NOT VALID OR BROWSER
		// FEATURES DO NOT ALLOW IT (IE11/EDGE)
		BaseSteps.JavaScripts.handleCertificateError();
		BaseSteps.JavaScripts.handleUnsecureConnectionError();
		BaseSteps.JavaScripts.handleInvalidCertificateError();

	}

	/**
	 * Method Description: The purpose of this method is login into SMP
	 * 
	 * @param username
	 * @param password
	 */
	public static void singInSMP(String username, String password) {
		LoginPage LoginPage = new LoginPage();
		BaseSteps.Waits.waitUntilPageLoadCompleteLongWait();
		BaseSteps.Waits.waitGeneric(10000);
		WebDriverSteps.getWebDriverSession().switchTo().frame("smpMain");

		WebDriverSteps.getWebDriverSession().findElement(By.name("j_username")).sendKeys(username);
		WebDriverSteps.getWebDriverSession().findElement(By.name("j_password")).sendKeys(password);
		WebDriverSteps.getWebDriverSession().findElement(By.name("submitimg")).click();

		Validate.takeStepScreenShot("Login to SMP");

		Reporting.logReporter(Status.INFO,
				"STEP === Login into SMP --> with Username: " + username + " and Password: " + password);

	}

	/**
	 * Method Description: The purpose of this method is to login into SMG
	 * 
	 * @param username
	 * @param password
	 */
	public static void singInSMG(String username, String password) {
		LoginPage LoginPage = new LoginPage();
		BaseSteps.Waits.waitUntilPageLoadCompleteLongWait();
		BaseSteps.SendKeys.sendKey(LoginPage.userNameInputBox, username);
		BaseSteps.SendKeys.sendKey(LoginPage.passwordInputBox, password);
		Validate.takeStepScreenShot("Login to SMG");
		BaseSteps.Clicks.clickElement(LoginPage.loginButton_smg);
		Reporting.logReporter(Status.INFO,
				"STEP === Login into SMG --> with Username: " + username + " and Password: " + password);

	}

	/**
	 * Method Description: The purpose of this method is to login into SMG
	 * Application
	 * 
	 * @param appName
	 */
	public static void singInSMG(String appName) {

		JSONObject env = userAccessJsonFile.getJSONObject(SystemProperties.EXECUTION_ENVIRONMENT);

		String username = ReadJSON.getString(env, appName + "_username");
		String password = ReadJSON.getString(env, appName + "_password");

		LoginPage LoginPage = new LoginPage();
		BaseSteps.SendKeys.sendKey(LoginPage.userNameInputBox, username);
		BaseSteps.SendKeys.sendKey(LoginPage.passwordInputBox, EncryptionUtils.decode(password));
		Validate.takeStepScreenShot("Login to SMG");
		BaseSteps.Clicks.clickElement(LoginPage.loginButton_smg);
		Reporting.logReporter(Status.INFO,
				"STEP === Login into SMG --> with Username: " + username + " and Password: " + password);

	}

	/**
	 * Method Description: The purpose of this method is to login into PAC
	 */
	public static void appLogin_PAC() {

		String appName = ReadJSON.getString(appNames, "PAC_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

		LoginPage LoginPage = new LoginPage();
		if (LoginPage.siteNotSecurePopUpDisplayedStatus()) {
			BaseSteps.Clicks.clickElement(LoginPage.pacDev_handleSiteNotSecure);
		}

	}

	/**
	 * Method Description: The purpose of this method is to login into RCM
	 */
	public static void appLogin_RCM() {

		String appName = ReadJSON.getString(appNames, "RCM_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

		LoginPage LoginPage = new LoginPage();
		if (LoginPage.siteNotSecurePopUpDisplayedStatus()) {
			BaseSteps.Clicks.clickElement(LoginPage.pacDev_handleSiteNotSecure);
		}

	}

	/**
	 * Method Description: The purpose of this method is to login into Smart Desktop
	 */
	public static void appLogin_SD() {

		String appName = ReadJSON.getString(appNames, "SMART_DESKTOP_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * Method Description: The purpose of this method is to login into Smart Desktop
	 */
	public static void appLogin_SDCommunicationHistory() {

		String appName = ReadJSON.getString(appNames, "SD_COMM_HISTORY_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}
	/**
	 * Method Description: The purpose of this method is to login into Smart Desktop
	 */
	public static void appLogin_KOODO_MSS() {

		String appName = ReadJSON.getString(appNames, "MSS_KOODO_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * Method Description: The purpose of this method is to login into Smart Desktop
	 */
	public static void appLogin_TELUS_MSS() {

		String appName = ReadJSON.getString(appNames, "MSS_TELUS_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}
	
	/**
	 * Method Description: The purpose of this method is to login into Smart Desktop
	 */
	public static void appLogin_MSS_PreToPost() {

		String appName = ReadJSON.getString(appNames, "MSS_PRETOPOST_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * Method Description: The purpose of this method is to login into Smart Desktop
	 */
	public static void appLogin_RKPortal() {

		String appName = ReadJSON.getString(appNames, "RK_PORTAL_APP_NAME");

		JSONObject env = userAccessJsonFile.getJSONObject(SystemProperties.EXECUTION_ENVIRONMENT);

		String username = ReadJSON.getString(env, appName + "_username");
		String password = ReadJSON.getString(env, appName + "_password");

		GenericUtils.openLoginPopUpBasedApplication(appName, username, EncryptionUtils.decode(password));

		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * Method Description: The purpose of this method is to login into Communication
	 * History from Smart Desktop
	 */
	public static void appLogin_CommHistory() {

		String appName = ReadJSON.getString(appNames, "SMART_DESKTOP_APP_NAME");
		LoginPageSteps.singInSDApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * Method Description: The purpose of this method is to login into CODA
	 */
	public static void appLogin_CODA() {

		String appName = ReadJSON.getString(appNames, "CODA_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInCODAApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * Method Description: The purpose of this method is to login into SMG
	 */
	public static void appLogin_SMG() {

		String appName = ReadJSON.getString(appNames, "SMG_APP_NAME");
		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.singInSMG(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
	}

	/**
	 * Method Description: The purpose of this method is to login into Public Mobile
	 * Application
	 */
	public static void appLogin_PublicMobile() {

		String appName = ReadJSON.getString(appNames, "PUBLIC_MOBILE_APP_NAME");

		JSONObject env = userAccessJsonFile.getJSONObject(SystemProperties.EXECUTION_ENVIRONMENT);

		String username = ReadJSON.getString(env, appName + "_username");
		String password = ReadJSON.getString(env, appName + "_password");
		// WebDriverSteps.openLoginPopUpBasedApplication(appName, username,
		// EncryptionUtils.decode(password));
		GenericUtils.openLoginPopUpBasedApplication(appName, username, EncryptionUtils.decode(password));

		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

	}

	/**
	 * Method Description: The purpose of this method is to launch RK Portal
	 */
	public static void appLauch_RKPortal() {

		String appName = ReadJSON.getString(appNames, "RK_PORTAL_APP_NAME");
		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();
	}

	/**
	 * Method Description: The purpose of this method is to login into PAC
	 */
	public static void appLogin_LSR() {

		String appName = ReadJSON.getString(appNames, "LSR_PORTAL_APP_NAME");

		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		LoginPageSteps.handleLSRSessionExpire();
		LoginPageSteps.handleLSRSessionExpire();

		LoginPageSteps.singInSDApplication(appName);
		
		LoginPageSteps.handleLSRSessionExpire();

		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReadyLongWait();

		LoginPage LoginPage = new LoginPage();
		if (LoginPage.siteNotSecurePopUpDisplayedStatus()) {
			BaseSteps.Clicks.clickElement(LoginPage.pacDev_handleSiteNotSecure);
		}

	}

	/**
	 * Method Description: The purpose of this method is to login into PAC
	 */
	public static void appLogin_SMGASMS() {

		String appName = ReadJSON.getString(appNames, "SMGASMS_PORTAL_APP_NAME");

		WebDriverSteps.openApplication(appName);

		LoginPageSteps.singInSMGASMSApplication(appName);

		LoginPage LoginPage = new LoginPage();
		if (LoginPage.siteNotSecurePopUpDisplayedStatus()) {
			BaseSteps.Clicks.clickElement(LoginPage.pacDev_handleSiteNotSecure);
		}

	}
	
	/**
	 * Method Description: The purpose of this method is to login into Smart Desktop
	 */
	public static void appLogin_ATT() {

		String appName = ReadJSON.getString(appNames, "ATT_APP_NAME");
		WebDriverSteps.openApplication(appName);
		BaseSteps.Waits.waitUntilJsAjaxJqueryAngularAngular5RequestsReady();
		singInSDApplication(appName);
	}

	/**
	 * Method Description: The purpose of this method is login inot SD app
	 * 
	 * @param appName
	 */
	public static void singInSMGASMSApplication(String appName) {

		JSONObject userAccess = userAccessJsonFile.getJSONObject(SystemProperties.EXECUTION_ENVIRONMENT);

		String username = ReadJSON.getString(userAccess, appName + "_username");
		String password = ReadJSON.getString(userAccess, appName + "_password");

		LoginPage LoginPage = new LoginPage();
		waitUntilPageLoadsSMGASMS();

		BaseSteps.SendKeys.sendKey(LoginPage.userId, username);
		BaseSteps.SendKeys.sendKey(LoginPage.userPwd, EncryptionUtils.decode(password));
		Validate.takeStepScreenShot("Login to application");
		BaseSteps.Clicks.clickElement(LoginPage.loginBtn);

		try {
			BaseSteps.Waits.waitForElementVisibilityLongWait(LoginPage.okBtnFromNoticeTitle);
			BaseSteps.Clicks.clickElement(LoginPage.okBtnFromNoticeTitle);

		} catch (Exception e) {

			Reporting.logReporter(Status.INFO, "Unable to login" + e);
		}
		Reporting.logReporter(Status.INFO,
				"STEP ===> Login into " + appName + " --> with Username: " + username + " and Password: " + password);

	}

	/**
	 * 
	 */
	public static void handleLSRSessionExpire() {

		LoginPage LoginPage = new LoginPage();
		try {
			if (LoginPage.sessionExpireMsg.isDisplayed()) {
				LoginPage.clickHereToLogin.click();
			}
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Session Expired Msg Not Displayed");
		}

	}
	
	public static void handlePleaseLoginAgain() {

		LoginPage LoginPage = new LoginPage();
		try {
			if (LoginPage.pleaseLoginCloseIconLSR.isDisplayed()) {
				LoginPage.pleaseLoginCloseIconLSR.click();
			}
		} catch (Exception e) {
			Reporting.logReporter(Status.INFO, "Session Expired Msg Not Displayed");
		}

	}	

}
