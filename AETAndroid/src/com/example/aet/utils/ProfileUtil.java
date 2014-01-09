package com.example.aet.utils;

import com.example.aet.data.LoginInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
/**
 * 
 * @author Jin Binbin
 *
 * @2013年11月21日
 *
 * @Version 1.0
 */
public final class ProfileUtil {

	private static final String PRO_FILE_NAME = "aet_profile";

	public static final String KEY_LOGIN_ACCOUNT = "loginAccount";

	public static final String KEY_LOGIN_PASSWORD = "loginPassword";

	public static final String KEY_NEED_AUTOLOGIN = "needAutoLogin";

	private ProfileUtil() {

	}
	
	public static LoginInfo getLoginInfo(Context context) {
		String account = getStringFProfile(context, KEY_LOGIN_ACCOUNT);
		String password = getStringFProfile(context, KEY_LOGIN_PASSWORD);
		boolean isAutoLogin=getBooleanFProfile(context, KEY_NEED_AUTOLOGIN);
		if (!TextUtils.isEmpty(account)) {
			LoginInfo loginInfo =new LoginInfo(account, password);
			loginInfo.setAutoLogin(isAutoLogin);
			return loginInfo;
		}
		return null;
	}

	public static void saveLoginInfo(Context context, LoginInfo loginInfo) {
		if (loginInfo != null) {
			String account = loginInfo.getAccount();
			String passwrod = loginInfo.getPassWord();
			boolean isAutoLogin=loginInfo.isAutoLogin();
			if (!TextUtils.isEmpty(account)) {
				putString2Profile(context, KEY_LOGIN_ACCOUNT, account);
			}
			if (!TextUtils.isEmpty(passwrod)) {
				putString2Profile(context, KEY_LOGIN_PASSWORD, passwrod);
			}
			if(isAutoLogin)
			{
				putBoolean2Profile(context, KEY_NEED_AUTOLOGIN, isAutoLogin);
			}
		}
	}

	private static boolean getBooleanFProfile(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PRO_FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, false);
	}

	private static void putBoolean2Profile(Context context, String key,
			boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PRO_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	private static String getStringFProfile(Context context, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PRO_FILE_NAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, "");
	}

	private static void putString2Profile(Context context, String key,
			String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PRO_FILE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

}
