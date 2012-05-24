package com.fuss.stats;

import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.MessageDigest;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;

public class Utilities 
{
	
	public static String getUniqueID(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);

		String device_id = digest(tm.getDeviceId());
		if (device_id == null) {
			String wifiInterface = SystemProperties.get("wifi.interface");
			try {
				String wifiMac = new String(NetworkInterface.getByName(
						wifiInterface).getHardwareAddress());
				device_id = digest(wifiMac);
			} catch (Exception e) {
				device_id = null;
			}
		}

		return device_id;
	}

	public static String getCarrier(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String carrier = tm.getNetworkOperatorName();
		if ("".equals(carrier)) {
			carrier = "Unknown";
		}
		return carrier;
	}

	public static String getCarrierId(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String carrierId = tm.getNetworkOperator();
		if ("".equals(carrierId)) {
			carrierId = "0";
		}
		return carrierId;
	}

	public static String getCountryCode(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		String countryCode = tm.getNetworkCountryIso();
		if (countryCode.equals("")) {
			countryCode = "Unknown";
		}
		return countryCode;
	}

	public static String getDevice() {
		return SystemProperties.get("ro.fuss132.device");
	}

	public static String getModName() {
		return SystemProperties.get("ro.fuss132.modname");
	}

	public static String getModVersion() {
		return SystemProperties.get("ro.fuss132.modversion");
	}

	public static String getBuiltDate() {
		return SystemProperties.get("ro.fuss132.builtdate");
	}
	
	public static String digest(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return new BigInteger(1, md.digest(input.getBytes())).toString(20)
					.toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}
}
