package com.tool;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;


public class JsoupWrapper  {

	public enum HTTPMethod {
		GET, POST, PUT, DELETE;
	}

	private Log logger = LogFactory.getLog(JsoupWrapper.class);

	public JsoupWrapper(int timeout) {
		super();
		this.timeout = timeout;
	}

	public JsoupWrapper() {
		super();
	}

	private int timeout = 60000;

	public Connection getJsoup(String link) {
		Connection conn = Jsoup.connect(link)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
		// conn.maxBodySize(500 * 1048576); // Max size: 500MB // 0 means unlimit
		conn.maxBodySize(0); // Max size: 500MB // 0 means unlimit
		conn.ignoreContentType(true);
		conn.timeout(timeout);
		return conn;
	}

	public JSONObject request(String url, Map<String, String> data) throws Exception, IOException {
		Connection conn = getJsoup(url);
		if (data != null && !data.isEmpty())
			conn.data(data);
		JSONObject result = getResponse(conn);
		return result;
	}
	public JSONObject request(String url, String data) throws Exception, IOException {
		return request(url, data, HTTPMethod.POST);
	}

	public JSONObject request(String url, String data, HTTPMethod method) throws Exception, IOException {
		return request(url, data, method, "application/json; charSet=UTF-8", "application/json",false);
	}

	public JSONObject request(String url, String data, HTTPMethod method, String header, String accept,boolean useStringResponse)
			throws Exception, IOException {
		Connection conn = getJsoup(url);
		conn.header("Content-Type", header);
		if (accept != null)
			conn.header("Accept", accept);
		switch (method) {
		case POST:
			conn.method(Method.POST);
			break;
		case GET:
			conn.method(Method.GET);
			break;
		case PUT:
			conn.method(Method.PUT);
			break;
		case DELETE:
			conn.method(Method.DELETE);
			break;
		default:
			conn.method(Method.GET);
		}
		if (data != null && !data.isEmpty())
			conn.requestBody(data); // for sending text in json format
		JSONObject result = getResponse(conn,useStringResponse);
		return result;
	}
	
	private JSONObject getResponse(Connection conn) throws Exception {
		return getResponse(conn ,false);
	}

	private JSONObject getResponse(Connection conn,boolean useStringResponse) throws Exception {
		JSONObject result = new JSONObject();
		for (int i = 0; i < 5; i++) {
			try {
				if(!useStringResponse)
				result = new JSONObject(conn.execute().body());
				else
					result=new JSONObject().put("result", conn.execute().body());
				break;
			} catch (HttpStatusException e) {
				if (e.getStatusCode() == 500) {
					try {
						result = new JSONObject(conn.ignoreHttpErrors(true).execute().body());
					} catch (Exception e1) {
						result = new JSONObject().put("error", conn.ignoreHttpErrors(true).execute().body());
					}
					if (i >= 4)
						throw new Exception(result.getJSONObject("error").getString("message"));
				}
				logger.error(e.getMessage(), e);
			} catch (SocketTimeoutException sockTimeout) {
				throw new Exception(
						"Exception Message:" + sockTimeout.getMessage() + ", Json content:" + result.toString());
			}
		}
		return result;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
