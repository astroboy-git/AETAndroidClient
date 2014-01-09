package com.example.aet.data;
/**
 * 
 * @author Jin Binbin
 *
 * @2013年10月15日
 *
 * @Version 1.0
 */
public class RegisterInfo{
	
	public RegisterInfo(String account,String passWord){
		setAccount(account);
		setPassWord(passWord);
	}
	
	public RegisterInfo(String account,String passWord,String verificationCode){
		this(account, passWord);
		setVerificationCode(verificationCode);
	}
	
	public RegisterInfo(String account,String passWord,UserInfo user){
		this(account, passWord);
		setUser(user);
	}
	
	public RegisterInfo(String account,String passWord,String verificationCode,UserInfo user){
		this(account, passWord,verificationCode);
		setUser(user);
	}
	
	private UserInfo user;
	
	private String account;
	
	private String passWord;
	
	private String verificationCode;

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

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

}
