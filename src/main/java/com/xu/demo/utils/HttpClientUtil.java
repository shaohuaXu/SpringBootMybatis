package com.xu.demo.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * http工具类
 *
 * @author xushaohua on 2017-12-13.
 */
public class HttpClientUtil {


	/**
	 * 封装HTTP POST方法
	 *
	 * @param url      请求地址
	 * @param paramMap 参数封装
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String post(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> formparams = setHttpParams(paramMap);
		UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(param);
		HttpResponse response = httpClient.execute(httpPost);
		String httpEntityContent = getHttpEntityContent(response);
		httpPost.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP POST方法
	 *
	 * @param url      url
	 * @param paramMap 参数
	 * @param headers  headers
	 * @return 结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String post(String url, Map<String, String> paramMap, Map<String, String> headers) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> formparams = setHttpParams(paramMap);
		UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
		if (headers != null) {
			Set<String> keys = headers.keySet();
			for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
				String key = (String) i.next();
				httpPost.addHeader(key, headers.get(key));
			}
		}
		httpPost.setEntity(param);
		HttpResponse response = httpClient.execute(httpPost);
		String httpEntityContent = getHttpEntityContent(response);
		httpPost.abort();
		return httpEntityContent;
	}

	/**
	 * 封装HTTP POST方法,对响应结果未做处理
	 *
	 * @param url      请求地址
	 * @param paramMap 参数封装
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static HttpResponse simplePost(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> formparams = setHttpParams(paramMap);
		UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(param);
		HttpResponse response = httpClient.execute(httpPost);
		httpPost.abort();
		return response;
	}


	/**
	 * post请求
	 *
	 * @param url  请求地址
	 * @param data 请求参数
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String post(String url, String data) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "text/json; charset=utf-8");
		httpPost.setEntity(new StringEntity(URLEncoder.encode(data, "UTF-8")));
		HttpResponse response = httpClient.execute(httpPost);
		String httpEntityContent = getHttpEntityContent(response);
		httpPost.abort();
		return httpEntityContent;
	}

	/**
	 * post请求-不转码
	 *
	 * @param url  请求地址
	 * @param data 请求参数
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String postNoEncode(String url, String data) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
		httpPost.setEntity(new StringEntity(data, "UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);
		String httpEntityContent = getHttpEntityContent(response);
		httpPost.abort();
		return httpEntityContent;
	}

	/**
	 * 刷新Config的refresh请求
	 *
	 * @param url url
	 * @return 结果
	 * @throws IOException 异常
	 */
	public static String postToRefresh(String url) throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		Map paramMap = new HashMap();
		List<NameValuePair> formparams = setHttpParams(paramMap);
		UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(param);
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		httpPost.abort();
		String result = "failure";
		if (String.valueOf(statusCode).equals("200")) {
			result = "success";
		}
		return result;
	}

	/**
	 * GET请求
	 *
	 * @param url 请求地址
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String get(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		httpGet.setURI(URI.create(url));
		HttpResponse response = httpClient.execute(httpGet);
		String httpEntityContent = getHttpEntityContent(response);
		httpGet.abort();
		return httpEntityContent;
	}

	/**
	 * GET请求
	 *
	 * @param url 请求地址
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static HttpResponse simpleGet(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		httpGet.setURI(URI.create(url));
		HttpResponse response = httpClient.execute(httpGet);
		httpGet.abort();
		return response;
	}

	/**
	 * GET请求
	 *
	 * @param url      请求地址
	 * @param paramMap 参数封装
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String get(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		List<NameValuePair> formparams = setHttpParams(paramMap);
		String param = URLEncodedUtils.format(formparams, "UTF-8");
		httpGet.setURI(URI.create(url + "?" + param));
		HttpResponse response = httpClient.execute(httpGet);
		String httpEntityContent = getHttpEntityContent(response);
		httpGet.abort();
		return httpEntityContent;
	}

	/**
	 * PUT请求
	 *
	 * @param url      请求地址
	 * @param paramMap 请求参数封装
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String put(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		List<NameValuePair> formparams = setHttpParams(paramMap);
		UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPut.setEntity(param);
		HttpResponse response = httpClient.execute(httpPut);
		String httpEntityContent = getHttpEntityContent(response);
		httpPut.abort();
		return httpEntityContent;
	}

	/**
	 * delete
	 *
	 * @param url 请求地址
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String delete(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete();
		httpDelete.setURI(URI.create(url));
		HttpResponse response = httpClient.execute(httpDelete);
		String httpEntityContent = getHttpEntityContent(response);
		httpDelete.abort();
		return httpEntityContent;
	}

	/**
	 * delete
	 *
	 * @param url 请求地址
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static HttpResponse simpleDelete(String url) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete();
		httpDelete.setURI(URI.create(url));
		HttpResponse response = httpClient.execute(httpDelete);

		httpDelete.abort();
		return response;
	}

	/**
	 * 封装HTTP DELETE方法
	 *
	 * @param url      请求地址
	 * @param paramMap 请求参数封装
	 * @return 请求结果
	 * @throws ClientProtocolException 异常
	 * @throws IOException             异常
	 */
	public static String delete(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete();
		List<NameValuePair> formparams = setHttpParams(paramMap);
		String param = URLEncodedUtils.format(formparams, "UTF-8");
		httpDelete.setURI(URI.create(url + "?" + param));
		HttpResponse response = httpClient.execute(httpDelete);
		String httpEntityContent = getHttpEntityContent(response);
		httpDelete.abort();
		return httpEntityContent;
	}


	/**
	 * 设置请求参数
	 *
	 * @param paramMap 请求参数
	 * @return 查询结果
	 */
	private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Set<Map.Entry<String, String>> set = paramMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return formparams;
	}

	/**
	 * 获得响应HTTP实体内容
	 *
	 * @param response 请求参数
	 * @return 请求结果
	 * @throws IOException                  异常
	 * @throws UnsupportedEncodingException 异常
	 */
	private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
				if (line != null) {
					sb.append("\n");
				}
			}
			return sb.toString();
		}
		return "";
	}
}
