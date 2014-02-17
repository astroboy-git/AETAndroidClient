package com.example.aet.data;
/**
 * 
 * @author Jin Binbin
 *
 * @2013年11月27日
 *
 * @Version 1.0
 */
public class RequestResult extends BaseInfo{
	
	private int resultCode;
	
	private String resultContent;
	
	private Object extraAttr;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	public Object getExtraAttr() {
		return extraAttr;
	}

	public void setExtraAttr(Object extraAttr) {
		this.extraAttr = extraAttr;
	}

}
