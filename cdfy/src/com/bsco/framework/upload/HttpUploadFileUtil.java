package com.bsco.framework.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.csource.common.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * HTTP协议文件流跨域上传
 * 
 * @author ht
 * 
 */
public class HttpUploadFileUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(HttpUploadFileUtil.class);
	// private static String url =
	// "http://192.168.1.106:8080/office2pdf/demo/DemoServlet";
	// private static String url =
	// "http://127.0.0.1:7001/office2pdf/demo/DemoServlet";
	private static String url = "http://125.64.14.196:7001/office2pdf/demo/DemoServlet";
	private static String filepath = "http://125.64.14.196:7001/office2pdf/upload/";

	// private static String filepath =
	// "http://127.0.0.1:7001/office2pdf/upload/";
	public HttpUploadFileUtil() {

	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public static Map upload(InputStream in, String fileName, boolean stag,
			String type) throws HttpException, IOException {
		Map result = new HashMap();
		HttpClient clients = new HttpClient();
		System.setProperty("apache.commons.httpclient.cookiespec",
				"COMPATIBILITY");
		String urls = "";
		// String ext = fileName.split("\\.")[1];
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (stag) {
			urls = url + "?ext=" + ext + "&stag=1";
		} else {
			urls = url + "?ext=" + ext + "&stag=0";
		}
		PostMethod postMethod = new PostMethod(urls);
		postMethod.setRequestBody(in);
		int state = clients.executeMethod(postMethod);
		// 获取二进制的byte流
		byte[] b = postMethod.getResponseBody();
		String str = new String(b, "UTF-8");
		postMethod.releaseConnection();
		if (state == 200) {
			JSONObject jsonObj = JSONObject.fromObject(str);
			if ("1000".equals(jsonObj.get("state"))) {
				String urlss = jsonObj.get("fileUrl").toString();
				urlss = urlss.substring(urlss.lastIndexOf("?") + 1);
				if ("attach".equals(type)) {
					result.put("fileUrl", "attach.do?path=?" + urlss);
				} else if ("resource".equals(type)) {
					result.put("fileUrl", "file.do?path=?" + urlss);
				} else {
					throw new RuntimeException("type类型错误");
				}
				result.put("state", jsonObj.get("state"));
				result.put("oldName", jsonObj.get("oldName"));
			} else {
				result.put("state", jsonObj.get("state"));
				result.put("fileUrl", "");
				result.put("oldName", "");
			}
		} else {
			result.put("state", "0000");
			result.put("fileUrl", "");
			result.put("oldName", "");
		}

		return result;
	}

	private static byte[] InputStreamToByte(InputStream is) throws IOException {

		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();

		return imgdata;
	}

	public static void downFile(String file_id, HttpServletResponse response,
			String title, String ext) throws IOException, MyException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		File file = new File(file_id.split("\\?")[1]);
		// response.setContentType("binary/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(title.getBytes("gb2312"), "iso8859-1") + "." + ext);
		//
		try {
			URL urls = new URL(file_id.replace("?", filepath));
			URLConnection conn = urls.openConnection();
			InputStream inStream = conn.getInputStream();
			// byte[] buffer = InputStreamToByte(inStream);
			int ch;
			while ((ch = inStream.read()) != -1) {
				servletOutputStream.write(ch);
			}

			// servletOutputStream.write(buffer);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			logger.info("文件不存在！！");
		} catch (IOException e) {
			// e.printStackTrace();
			logger.info("文件读取失败！！");
		}
	}

}
