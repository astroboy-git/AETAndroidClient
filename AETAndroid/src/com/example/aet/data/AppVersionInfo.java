package com.example.aet.data;
/**
 * 
 * @author Jin Binbin
 *
 * @2013年11月25日
 *
 * @Version 1.0
 */
public class AppVersionInfo extends BaseInfo{
	
	private int versionCode;
	
	private String versionName;
	
	private String AppName;
	
	private String packageName;
	
	private String downLoadUrl;

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getAppName() {
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

}
