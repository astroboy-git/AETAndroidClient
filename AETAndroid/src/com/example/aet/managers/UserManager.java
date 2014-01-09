package com.example.aet.managers;

import android.content.Context;
import android.text.TextUtils;

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
		RequestResult result=RequestManager.getInstance().login(loginInfo);
		if (result!=null&&result.getResultCode() == 200) {
			UserInfo user=new UserInfo(loginInfo.getAccount());
			setLoginInfo(loginInfo);
			setUserInfo(user);
			setToken("token");
		}
		return result;
	}
	
	public void logout(UserInfo info){
		clearUserInfo();
	}
	
	public RequestResult register(RegisterInfo registerInfo){
		RequestResult result=RequestManager.getInstance().register(registerInfo);
		if(result!=null&&result.getResultCode() == 200)
		{
			UserInfo user=null;
			user=new UserInfo("");
		}
		return result;
	}
	
	public void switchUser(LoginInfo info){
		UserInfo user=null;
		setUserInfo(user);
	}
	
	public void modifyUserInfo(RegisterInfo info)
	{
		UserInfo user=null;
		setUserInfo(user);
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
