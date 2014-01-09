package com.example.aet.data;

import com.example.aet.data.pars.DataPar;

/**
 * 
 * @author Jin Binbin
 *
 * @2013年10月15日
 *
 * @Version 1.0
 */
public class UserInfo {
	
	public UserInfo(String id){
		setId(id);
	}

	private String id;
	
	private String name;
	
	private String idCardNum;
	
	private String eMail;
	
	private String phoneNum;
	
	private int gender;
	
	private int age;
	
	private float height;
	
	private float weight;
	
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public static UserInfo parsUserInfo(final String data,final DataPar dataPar){
		UserInfo userInfo=null;
		if(data!=null&&dataPar!=null){
			userInfo=dataPar.parData(data);
		}
		return userInfo;
	}
}
