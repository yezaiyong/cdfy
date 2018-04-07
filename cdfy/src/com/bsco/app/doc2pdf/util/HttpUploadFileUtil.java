package com.bsco.app.doc2pdf.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.csource.common.MyException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.bsco.framework.dao.HibernateDao;
import com.bsco.framework.dao.impl.HibernateDaoImpl;
import com.bsco.sns.model.Attach;
import com.bsco.sns.model.Resources;

/**
 * 
 * HTTP协议文件流跨域上传
 * 
 * @author ht
 * 
 */
public class HttpUploadFileUtil {

	private static List<String> array = Arrays.asList(new String[] {"doc","docx" , "xls" ,"xlsx" ,"pdf" ,"txt" ,"ppt","pptx"});
	private static final Logger logger = LoggerFactory.getLogger(HttpUploadFileUtil.class);
	//private static String url = "http://192.168.1.106:8080/office2pdf/demo/DemoServlet";
	private static String url = "http://127.0.0.1/office2pdf/demo/DemoServlet";
	//private static String url = "http://125.64.14.196:7001/office2pdf/demo/DemoServlet";
	//private static String filepath = "http://125.64.14.196:7001/office2pdf/upload/";
	private static String filepath = "http://127.0.0.1/office2pdf/upload/";
	public HttpUploadFileUtil(){   
		
		
	}
	
	
	/*
	private static String  httpClient(final InputStream in, final String fileName ,final boolean stag){
		final String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		String newPath = UploadUtils.generateFilename();
		final String newfile = newPath+"."+ext;
		new Thread(new Runnable() {
			public void run() {
				HttpClient clients = new HttpClient();
				System.setProperty("apache.commons.httpclient.cookiespec",
						"COMPATIBILITY");
				String urls = "";
				if(stag){ 
					urls = url + "?ext="+newfile+"&stag=1"; 
				}else{
					urls = url + "?ext="+newfile+"&stag=0";
				}
				PostMethod postMethod = new PostMethod(urls);
				postMethod.setRequestBody(in);
				try {
					int state = clients.executeMethod(postMethod);
					
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		return newfile;
	}*/
	
	
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public static Map upload(InputStream in, String fileName ,boolean stag ,String type,Object obj)
			throws HttpException, IOException {
		Map result = new HashMap();
		HttpClient clients = new HttpClient();
		System.setProperty("apache.commons.httpclient.cookiespec",
				"COMPATIBILITY");
		String urls = "";
		//String ext = fileName.split("\\.")[1];
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(stag){
			urls = url + "?ext="+ext+"&stag=1";
		}else{
			urls = url + "?ext="+ext+"&stag=0";
		}
		PostMethod postMethod = new PostMethod(urls);
		postMethod.setRequestBody(in);
		int state = clients.executeMethod(postMethod);
		// 获取二进制的byte流
		byte[] b = postMethod.getResponseBody();
		String str = new String(b, "UTF-8");
		postMethod.releaseConnection();
		if(state == 200){
			 JSONObject jsonObj = JSONObject.fromObject(str);
			 if("1000".equals(jsonObj.get("state"))){
				String urlss = jsonObj.get("fileUrl").toString();
				 urlss = urlss.substring(urlss.lastIndexOf("?")+1);
				 if("attach".equals(type)){
					 result.put("fileUrl", "attach.do?path=?"+urlss);
				 }else if("resource".equals(type)){
					 result.put("fileUrl", "file.do?path=?"+urlss);
				 }else{
					 throw new RuntimeException("type类型错误"); 
				 }
				 result.put("state", jsonObj.get("state"));
				 result.put("oldName", jsonObj.get("oldName"));
				 String ex = urlss.substring(urlss.lastIndexOf(".")+1);
				 String names = urlss.substring(0,urlss.lastIndexOf("."));
				 if(array.contains(ex)){
					 FilesexitsUtil.gos(obj, filepath+names+".swf",result.get("fileUrl").toString());  
				 }
			 }else{
				 result.put("state", jsonObj.get("state"));
				 result.put("fileUrl", "");
				 result.put("oldName", "");
			 }
		}
		else{
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
	
	public static void downFile(String file_id, HttpServletResponse response,Resources resource)
			throws IOException, MyException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		File file = new File(file_id.split("\\?")[1]);
//		response.setContentType("binary/octet-stream");
		if(resource == null){
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(file.getName().getBytes("gb2312"), 
							"iso8859-1")); 
		}else{
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(resource.getResourceTitle().getBytes("gb2312"),
							"iso8859-1")+"."+  resource.getExt()); 
		}
//		
	   try {
		URL urls = new URL(file_id.replace("?", filepath));
            URLConnection conn = urls.openConnection();
            InputStream inStream = conn.getInputStream();
           // byte[] buffer =  InputStreamToByte(inStream);
            int ch;
            while((ch = inStream.read()) != -1){
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
	
	
	public static void downFile2(String file_id, HttpServletResponse response,Attach resource)
			throws IOException, MyException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		File file = new File(file_id.split("\\?")[1]);
//		response.setContentType("binary/octet-stream");
		if(resource == null){
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(file.getName().getBytes("gb2312"), 
							"iso8859-1")); 
		}else{
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(resource.getTitle().getBytes("gb2312"),
							"iso8859-1")+"."+ file.getName().substring(file.getName().lastIndexOf(".")+1)); 
		}
//		
	   try {
		URL urls = new URL(file_id.replace("?", filepath));
            URLConnection conn = urls.openConnection();
            InputStream inStream = conn.getInputStream();
           // byte[] buffer =  InputStreamToByte(inStream);
            int ch;
            while((ch = inStream.read()) != -1){
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
