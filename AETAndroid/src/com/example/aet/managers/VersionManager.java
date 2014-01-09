package com.example.aet.managers;

import org.apache.http.HttpStatus;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;

import com.example.aet.data.AppVersionInfo;
import com.example.aet.data.RequestResult;
import com.example.aet.data.utils.JsonParsUtil;

public class VersionManager {

	private VersionManager() {

	}

	private static Context mAppContext;

	private static class SingletonHolder {
		public static final VersionManager VERSION_MANAGER = new VersionManager();
	}

	public static final VersionManager getInstance(Context context) {
		mAppContext = context.getApplicationContext();
		return SingletonHolder.VERSION_MANAGER;
	}

	public AppVersionInfo getCurrentVersionInfo() {
		PackageManager pm = mAppContext.getPackageManager();
		AppVersionInfo versionInfo = null;
		try {
			PackageInfo pi = pm.getPackageInfo(mAppContext.getPackageName(), 0);
			String versionName = pi.versionName;
			int versioncode = pi.versionCode;
			versionInfo = new AppVersionInfo();
			versionInfo.setVersionCode(versioncode);
			versionInfo.setVersionName(versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionInfo;
	}

	public AppVersionInfo getNewVersionInfo() {
		AppVersionInfo newVersionInfo = null;
		AppVersionInfo currentVersionInfo = getCurrentVersionInfo();
		if (currentVersionInfo != null) {
			RequestResult result = RequestManager.getInstance()
					.getNewVersionInfo();
			if (result.getResultCode() == HttpStatus.SC_OK) {
				String content = result.getResultContent();
				newVersionInfo = JsonParsUtil.parsAppVersionInfo(content);
			}
			if (newVersionInfo != null
					&& newVersionInfo.getVersionCode() <= currentVersionInfo
							.getVersionCode()) {
				newVersionInfo = null;
			}
		}
		return newVersionInfo;
	}

	@SuppressLint("NewApi")
	public void upDateAppVersion(AppVersionInfo versionInfo) {
		if (versionInfo != null
				&& !TextUtils.isEmpty(versionInfo.getDownLoadUrl())) {
			DownloadManager manager = (DownloadManager) mAppContext
					.getSystemService(Context.DOWNLOAD_SERVICE);
			// 创建下载请求
			DownloadManager.Request down = new DownloadManager.Request(
					Uri.parse(versionInfo.getDownLoadUrl()));
			// 设置允许使用的网络类型，这里是移动网络和wifi都可以
			down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
					| DownloadManager.Request.NETWORK_WIFI);
			// 禁止发出通知，既后台下载
			down.setShowRunningNotification(false);
			// 不显示下载界面
			down.setVisibleInDownloadsUi(false);
			// 设置下载后文件存放的位置
			down.setDestinationInExternalFilesDir(mAppContext, null,
					versionInfo.getAppName());
			// 将下载请求放入队列
			manager.enqueue(down);
		}
	}
}
