package com.example.aet.data;

import com.google.gson.Gson;
/**
 * 
 * @author Jin Binbin
 *
 * @2014年2月16日
 *
 * @Version 1.0
 */
public class BaseInfo {
	/**
	 * 
	 * @param json
	 * @param c
	 * @return
	 */
	public static <T> T paresJsonToObject(String json,Class<T> c)
	{
		Gson gson=new Gson();
		return (T) gson.fromJson(json, c);
	}
}
