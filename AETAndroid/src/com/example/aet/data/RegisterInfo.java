package com.example.aet.data;

/**
 * 
 * @author Jin Binbin
 * 
 * @2013年10月15日
 * 
 * @Version 1.0
 */
public class RegisterInfo {

	public RegisterInfo(String phoneNum, String account, String passWord) {
		setPhoneNum(phoneNum);
		setAccount(account);
		setPassWord(passWord);
	}

	private String account;

	private String passWord;

	private String phoneNum;

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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
