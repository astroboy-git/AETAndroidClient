package com.example.aet.data;
/**
 * 
 * @author Jin Binbin
 *
 * @2013年10月15日
 *
 * @Version 1.0
 */
public class LoginInfo {
	
	public LoginInfo(String account,String passWord){
		setAccount(account);
		setPassWord(passWord);
	}
	
	private String account;
	
	private String passWord;
	
	private boolean isAutoLogin;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public boolean isAutoLogin() {
		return isAutoLogin;
	}

	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}
	
}
