package com.example.aet.data.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.example.aet.data.AppVersionInfo;
import com.example.aet.data.OrganInfo;
import com.example.aet.data.UserInfo;

public class JsonParsUtil {

	public static UserInfo parsUserInfo(String result) {
		UserInfo userInfo = null;
		if (!TextUtils.isEmpty(result)) {
			JSONObject userJson = null;
			try {
				userJson = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userJson != null) {
				userInfo = new UserInfo(userJson.optString("id"));
				userInfo.setName(userJson.optString("name"));
				userInfo.setAddress(userJson.optString("address"));
				userInfo.setAge(userJson.optInt("age"));
				userInfo.seteMail(userJson.optString("mail"));
				userInfo.setGender(userJson.optInt("gender"));
				userInfo.setPhoneNum(userJson.optString("phoneNum"));
				userInfo.setHeight(userJson.optInt("height"));
				userInfo.setWeight(userJson.optInt("weight"));
			}
		}
		return userInfo;
	}

	public static AppVersionInfo parsAppVersionInfo(String result) {
		AppVersionInfo versionInfo = null;
		if (!TextUtils.isEmpty(result)) {
			JSONObject versionJson = null;
			try {
				versionJson = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (versionJson != null) {
				versionInfo = new AppVersionInfo();
				versionInfo.setVersionCode(versionJson.optInt("versionCode"));
				versionInfo
						.setVersionName(versionJson.optString("versionName"));
				versionInfo.setAppName(versionJson.optString("appName"));
				versionInfo
						.setPackageName(versionJson.optString("packageName"));
				versionInfo
						.setDownLoadUrl(versionJson.optString("downLoadUrl"));
			}
		}
		return versionInfo;
	}
	
	public static List<OrganInfo> parsOrganList(String result) {
		List<OrganInfo> organInfos = new ArrayList<OrganInfo>();
		if (!TextUtils.isEmpty(result)) {
			JSONArray organListArray = null;
			try {
				organListArray = new JSONArray(result);
				if (organListArray != null) {
					for (int i = 0, count = organListArray.length(); i < count; i++) {
						JSONObject json = organListArray.getJSONObject(i);
						OrganInfo organInfo = parsOrganInfo(json);
						if (organInfo != null) {
							organInfos.add(organInfo);
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return organInfos;
	}

	private static OrganInfo parsOrganInfo(JSONObject json) {
		OrganInfo organInfo = null;
		if (json != null) {
			organInfo = new OrganInfo();
			organInfo.setId(json.optString("id"));
			organInfo.setName(json.optString("name"));
			organInfo.setType(json.optString("type"));
			organInfo.setVip(json.optBoolean("vip"));
		}
		return organInfo;
	}

}
