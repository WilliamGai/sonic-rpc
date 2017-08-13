package org.sonic.rpc.core;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.function.Consumer;

/**
 * UTF-8
 */
public class HttpUtil {
	private static final int TIME_OUT_5000 = 5000;
	private static final int TIME_OUT_200 = 200;

	public static String TAG_POST = "POST";

	public static byte[] getStreamBytes(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		byte[] b = baos.toByteArray();
		is.close();
		baos.close();
		return b;
	}

	/**
	 * 只允许使用utf8
	 */
	public static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LogCore.BASE.error("urlEncode utf8 error", e);
			return null;
		}
	}

	public static boolean isUrlAvailable(String path) {
		if (Util.isEmpty(path)) {
			return false;
		}
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TIME_OUT_200);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				return true;
			}
			return false;
		} catch (Exception e) {
			LogCore.BASE.info("GET Url detected fail,url={}", path);
			return false;
		}
	}

	public static String sendGet(String path) {
		LogCore.BASE.debug("GET mothod invoke:\n" + path);
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TIME_OUT_5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				byte[] data = getStreamBytes(is);
				LogCore.BASE.debug(new String(data), "utf-8");
				return new String(data, "utf-8");
			}
		} catch (Exception e) {
			LogCore.BASE.error("GET Method exception,", e);
			e.printStackTrace();
		}
		return null;
	}

	public static int size(String path) {
		System.out.println("gt mothod invoke:" + path);
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				byte[] data = getStreamBytes(is);
				return data.length;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String post(String url, Map<String, String> params) {
		String content = "";
		if (Util.isEmpty(params)) {
			return sendPost(url, content);
		}
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			content += key + "=" + value + "&";
		}

		content = content.substring(0, content.length() - 1);
		return sendPost(url, content);
	}

	public static String get(String url, Map<String, String> params) {
		String content = "";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			content += key + "=" + value + "&";
		}
		content = content.substring(0, content.length() - 1);
		return sendGet(url + "?" + content);
	}

	/**
	 * 如果需要记录消耗的时间,使用此方法
	 * 
	 * @since jse1.8
	 */
	public static String getWithRecordTimeUsed(String url, Map<String, String> params,
			Consumer<Long> timeConsumer) {
		String content = "";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			content += key + "=" + value + "&";
		}

		content = content.substring(0, content.length() - 1);
		long begin_nao_time = System.nanoTime();
		String result = sendGet(url + "?" + content);
		long interval = System.nanoTime() - begin_nao_time;
		timeConsumer.accept(interval);
		return result;
	}

	public static String sendPost(String path, String content) {
		int resultCode = -1;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", " application/x-www-form-urlencoded");
			// String content = "name="+name+"&password="+password;
			byte[] data = content.getBytes();
			conn.setRequestProperty("Content-Length", data.length + "");
			conn.setDoOutput(true);
			conn.getOutputStream().write(data);
			resultCode = conn.getResponseCode();
			if (resultCode == 200) {
				InputStream is = conn.getInputStream();
				byte[] d = getStreamBytes(is);
				LogCore.BASE.debug(TAG_POST + "<<<< sucess {}", new String(d));
				return new String(d);
			} else {
				LogCore.BASE.debug(TAG_POST + "<<<< failed {}" + conn.getResponseCode());
			}
		} catch (Exception e) {
			LogCore.BASE.error(TAG_POST + "<<<< error {},path={}", resultCode,path, e);
		}
		return null;
	}
}
