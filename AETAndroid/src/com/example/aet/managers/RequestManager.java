package com.example.aet.managers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.aet.core.Constants;
import com.example.aet.core.Urls;
import com.example.aet.data.LoginInfo;
import com.example.aet.data.OrganInfo;
import com.example.aet.data.RegisterInfo;
import com.example.aet.data.RequestResult;
import com.example.aet.data.utils.JsonParsUtil;

/**
 * 
 * @author Jin Binbin
 * 
 * @2013年11月25日
 * 
 * @Version 1.0
 */
public class RequestManager {

	/**
	 * 连接池获取连接超时时间
	 */
	private static final int CONN_POOL_TOMEOUT = 2000;
	/**
	 * 连接超时时间
	 */
	private static final int CONN_TOMEOUT = 3000;
	/**
	 * 请求超时时间
	 */
	private static final int REQ_TIMEOUT = 3000;

	private static String serverUrl;

	private RequestManager() {

	}

	private static class SingletonHolder {
		public static final RequestManager USER_MANAGER = new RequestManager();
	}

	public static final RequestManager getInstance() {
		if (serverUrl == null) {
			serverUrl = new StringBuilder(Urls.SERVER_IP).toString();
		}
		return SingletonHolder.USER_MANAGER;
	}

	private HttpClient mHttpClient;

	private synchronized HttpClient getHttpClient() {
		if (null == mHttpClient) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params, Constants.USER_AGENT);
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, CONN_POOL_TOMEOUT);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(params, CONN_TOMEOUT);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, REQ_TIMEOUT);

			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			mHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return mHttpClient;
	}
	/**
	 * 
	 * @param url
	 * @return
	 */
	private RequestResult doGet(String url) {
		RequestResult result = new RequestResult();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = getHttpClient().execute(request);
			result.setResultCode(response.getStatusLine().getStatusCode());
			if (result.getResultCode() != HttpStatus.SC_OK) {
				request.abort();
			}
			HttpEntity resEntity = response.getEntity();
			String content = (resEntity == null) ? null : EntityUtils.toString(
					resEntity, HTTP.UTF_8);
			result.setResultContent(content);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private RequestResult doPost(String url, NameValuePair... params) {
		RequestResult result = new RequestResult();
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (NameValuePair p : params) {
				formparams.add(p);
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					HTTP.UTF_8);
			HttpPost request = new HttpPost(url);
			request.setEntity(entity);
			HttpResponse response = getHttpClient().execute(request);
			result.setResultCode(response.getStatusLine().getStatusCode());
			HttpEntity resEntity = response.getEntity();
			String content = (resEntity == null) ? null : EntityUtils.toString(
					resEntity, HTTP.UTF_8);
			result.setResultContent(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	public RequestResult getNewVersionInfo() {
		// TODO Auto-generated method stub
		String url = new StringBuilder(serverUrl).append(Urls.URL_GET_VERSION)
				.toString();
		return doGet(url);
	}
	/**
	 * 
	 * @param loginInfo
	 * @return
	 */
	public RequestResult login(final LoginInfo loginInfo) {
		// TODO Auto-generated method stub
		NameValuePair[] params = new NameValuePair[2];
		params[0] = new BasicNameValuePair("account", loginInfo.getAccount());
		params[1] = new BasicNameValuePair("password", loginInfo.getPassWord());
		String url = new StringBuilder(serverUrl).append(Urls.URL_TO_LOGIN)
				.toString();
		doPost(url, params);
		RequestResult result = new RequestResult();
		result.setResultCode(200);
		return result;
	}
	
	public RequestResult register(final RegisterInfo registerInfo)
	{
		RequestResult result = new RequestResult();
		registerInfo.getAccount();
		registerInfo.getPassWord();
		return result;
	}
	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<OrganInfo> getOrganList(final String type) {
		List<OrganInfo> organInfos = new ArrayList<OrganInfo>();
		String url = new StringBuilder(serverUrl)
				.append(Urls.URL_GET_ORGANLIST).append(type).toString();
		RequestResult result = doGet(url);
		if (result.getResultCode() == 200) {
			organInfos.addAll(JsonParsUtil.parsOrganList(result
					.getResultContent()));
		}
		for(int i=0;i<10;i++){
			OrganInfo organInfo=new OrganInfo();
			organInfo.setId(String.valueOf(i));
			organInfo.setName("lufei");
			organInfo.setType(type);
			organInfo.setVip(true);
			organInfos.add(organInfo);
		}
		return organInfos;
	}

}
