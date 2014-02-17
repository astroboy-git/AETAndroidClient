package com.example.aet.managers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.aet.data.OrganInfo;

/**
 * 
 * @author Jin Binbin
 *
 * @2014年2月17日
 *
 * @Version 1.0
 */
public class OrganManager {
	
	private static Context mAppContext;
	
	private static class SingletonHolder {
		public static final OrganManager USER_MANAGER = new OrganManager();
	}

	public static final OrganManager getInstance(Context context) {
		mAppContext=context.getApplicationContext();
		return SingletonHolder.USER_MANAGER;
	}
	
	public List<OrganInfo> getOrganList(String type){
		List<OrganInfo> organInfos=new ArrayList<OrganInfo>();
		return organInfos;
	}

}
