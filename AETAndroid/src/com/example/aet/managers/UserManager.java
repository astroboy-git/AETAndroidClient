package com.example.aet.managers;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.text.TextUtils;

import com.example.aet.core.Urls;
import com.example.aet.data.LoginInfo;
import com.example.aet.data.RegisterInfo;
import com.example.aet.data.RequestResult;
import com.example.aet.data.UserInfo;
import com.example.aet.utils.ProfileUtil;
/**
 * 
 * @author Jin Binbin
 *
 * @2013年10月15日
 *
 * @Version 1.0
 */
public class UserManager {
	
	public static final int LOGIN_INFO_INCOMPLETE=0x2001;
	
	private static Context mAppContext;
	
	private int[] lock=new int[1];
	
	private String mToken;
	
	public interface RegisterType{
		
		public static final int REGISTER_TYPE_STUDENT=0x01;
		
		public static final int REGISTER_TYPE_TEACHER=0x02;
		
		public static final int REGISTER_TYPE_ORGAN=0x03;
	}
	
	private UserManager() {

	}
	
	private UserInfo mUser;

	private static class SingletonHolder {
		public static final UserManager USER_MANAGER = new UserManager();
	}

	public static final UserManager getInstance(Context context) {
		mAppContext=context.getApplicationContext();
		return SingletonHolder.USER_MANAGER;
	}
	
	
	public UserInfo getUserInfo(){

		return mUser;
	}
	
	private void setUserInfo(UserInfo user){
		mUser=user;
	}
	
	private void clearUserInfo(){
		mUser=null;
	}
	
	public RequestResult login(final LoginInfo loginInfo){
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new BasicNameValuePair("account", loginInfo.getAccount());
		params[1] = new BasicNameValuePair("password", loginInfo.getPassWord());
		String url = new StringBuilder(Urls.SERVER_IP).append(Urls.URL_TO_LOGIN)
				.toString();
		RequestResult result=RequestManager.getInstance().doPost(url, params);
		if (result!=null&&result.getResultCode() == 200) {
			String json=result.getResultContent();
			UserInfo user=UserInfo.paresJsonToObject(json, UserInfo.class);
			setUserInfo(user);
			setToken("");
		}
		return result;
	}
	
	public void logout(UserInfo info){
		clearUserInfo();
	}

	public RequestResult register(NameValuePair[] params, int registerType) {
		String url = null;
		switch (registerType) {
		case RegisterType.REGISTER_TYPE_STUDENT:
			url = new StringBuilder(Urls.SERVER_IP).append(
					Urls.URL_STUDENT_TO_REGISTER).toString();
			break;
		case RegisterType.REGISTER_TYPE_TEACHER:
			url = new StringBuilder(Urls.SERVER_IP).append(
					Urls.URL_TEACHER_TO_REGISTER).toString();
			break;
		case RegisterType.REGISTER_TYPE_ORGAN:
			new StringBuilder(Urls.SERVER_IP).append(Urls.URL_ORG_TO_REGISTER)
					.toString();
			break;
		default:
			url = new StringBuilder(Urls.SERVER_IP).append(
					Urls.URL_STUDENT_TO_REGISTER).toString();
			break;
		}
		RequestResult result = RequestManager.getInstance().doPost(url, params);
		if (result != null && result.getResultCode() == 200) {
			String json = result.getResultContent();
			UserInfo user = UserInfo.paresJsonToObject(json, UserInfo.class);
			setUserInfo(user);
			setToken("");
		}
		return result;
	}
	
	public RequestResult switchUser(LoginInfo info){
		UserInfo user=null;
		setUserInfo(user);
		RequestResult result=null;
		return result;
	}
	
	public RequestResult modifyUserInfo(RegisterInfo info)
	{
		UserInfo user=null;
		setUserInfo(user);
		RequestResult result=null;
		return result;
	}
	
	public void cancalUser(LoginInfo info){
		clearUserInfo();
	}
	
	public LoginInfo getLoginInfo()
	{
		return ProfileUtil.getLoginInfo(mAppContext);
	}
	
	public void setLoginInfo(LoginInfo loginInfo)
	{
		ProfileUtil.saveLoginInfo(mAppContext, loginInfo);
	}
	
	public RequestResult doAutologin() {
		LoginInfo loginInfo = ProfileUtil.getLoginInfo(mAppContext);
		if (loginInfo != null && !TextUtils.isEmpty(loginInfo.getPassWord())) {
			return login(loginInfo);
		}
		return null;
	}
	
	public boolean isLogin()
	{
		synchronized(lock)
		{
			return mToken!=null;
		}
	}
	
	private void setToken(String token)
	{
		synchronized(lock)
		{
			mToken=token;
		}
	}
}
