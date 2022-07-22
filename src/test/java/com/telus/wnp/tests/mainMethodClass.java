package com.telus.wnp.tests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import com.telus.wnp.utils.GenericUtils;
import com.test.utils.EncryptDecrypt;
import com.test.utils.EncryptionUtils;

public class mainMethodClass {

	public static void main(String[] args) {

		/*
		 * getAnnotationsInCurrentMethod
		 * 
		 * createNewTestReport
		 * 
		 * GROUP_REPORT
		 * 
		 * Steps Map is empty
		 * 
		 * Class & Method name :
		 * 
		 * try { System.out.println(EncryptDecrypt.generateEncryptData(
		 * "80169665-c0ee-4c74-9d24-77db9307e705", EncryptDecrypt.PASSWORD)); } catch
		 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		String ddt1 = GenericUtils.getDDDTWithDelayInPACFormatForJenkinsNodes(1);
		System.out.println(getSystemDateInMMDDYYYYInPST_LSR().split("/")[1]);
		/*
		 * String ddt2 = GenericUtils.tempDDTMethod(1);
		 * 
		 * 
		 * System.out.println(EncryptionUtils.encode("P@zzW0rd"));
		 * System.out.println(ddt2);
		 * System.out.println(GenericUtils.tempDDTMethod(30)+1);
		 * System.out.println(GenericUtils.tempDDTMethod(30+1));
		 */

	}

	/**
	 * 
	 * @return current system date in mm/dd/yyyy format
	 */
	public static String getSystemDateInMMDDYYYYInPST_LSR() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		// Use Madrid's time zone to format the date in
		df.setTimeZone(TimeZone.getTimeZone("GMT-7"));

		return df.format(date);
	}
}
