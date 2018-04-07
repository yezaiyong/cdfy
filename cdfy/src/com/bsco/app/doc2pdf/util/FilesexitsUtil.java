package com.bsco.app.doc2pdf.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;


import com.bsco.framework.dao.HibernateDao;
import com.bsco.framework.dao.impl.HibernateDaoImpl;
import com.bsco.sns.model.Attach;
import com.bsco.sns.model.Course;
import com.bsco.sns.model.Resources;

public class FilesexitsUtil {
	private static final Logger logger = LoggerFactory.getLogger(FilesexitsUtil.class);
	private static WebApplicationContext context = null;
	private static HibernateDao dao  = null;
	
	static{
		   context = org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext();
		   dao = context.getBean(HibernateDaoImpl.class);
	}
	
	public static void gos(final Object obj , final String path,final String pt){
		 new Thread(new Runnable() {
			 int count = 0 ;
				@Override
				public void run() {
					Session session = dao.getHibernateSessionFactory().openSession();
					Transaction tran = session.beginTransaction();  //开始事务
					logger.debug("当前对象："+obj);
					System.out.println("当前对象:"+obj); 
					try {
						while(true && count <1*100){
							if(FilesexitsUtil.isNetFileAvailable(path)){
								//文件存在
								if(obj instanceof Attach){
									Query query = session.createQuery("from Attach t where t.filepath = '"+pt+"'"); 
									List<Attach> res = (List<Attach>) query.list();
									if(res.size() == 0){
										Thread.sleep(6000);
										count++;
									}else{
										for(Attach r : res){
											r.setSwitches("1");
											session.save(r);
										}
										break;
									}
									
								}else if(obj instanceof Resources){
									Query query = session.createQuery("from Resources t where t.resourceUrl = '"+pt+"'"); 
									List<Resources> res = (List<Resources>) query.list();
									if(res.size() == 0){
										Thread.sleep(6000);
										count++;
									}else{
										for(Resources r : res){
											r.setSwitches("1");
											session.save(r);
										}
										break;
									}
									
								}else{
									
								}
								
							}else{
								try {
									Thread.sleep(2000);
									count++;
									System.out.println("循环检测文件:'"+path+"'"+count+1+"次"); 
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					} catch (Exception e) {
						tran.rollback();
						e.printStackTrace();
					}finally{
						tran.commit();
						session.close();
					}
				}}).start();
	}

	
	
	
	
	/**
	* 检测网络资源是否存在　
	* @param strUrl
	* @return
	*/
	public static boolean isNetFileAvailable(String strUrl) {
		InputStream netFileInputStream = null;
		try {
			//设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
			HttpURLConnection.setFollowRedirects(false);
			URL url = new URL(strUrl);
			HttpURLConnection  urlConn = (HttpURLConnection) url.openConnection();
			/* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
			urlConn.setRequestMethod("HEAD");
			urlConn.setConnectTimeout(15000);
			urlConn.setReadTimeout(15000); 
			//从 HTTP 响应消息获取状态码
	        return (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
			/*netFileInputStream = urlConn.getInputStream();
			if (null != netFileInputStream) {
				return true;
			} else {
				return false;
			}*/
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (netFileInputStream != null)
					netFileInputStream.close();
			} catch (IOException e) {
			}
		}
	}
	
	 static boolean exists(String URLName) {
	       try {
	           //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
	           HttpURLConnection.setFollowRedirects(false);
	           //到 URL 所引用的远程对象的连接
	           HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
	           /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
	           con.setRequestMethod("HEAD");
	           //从 HTTP 响应消息获取状态码
	           return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	       } catch (Exception e) {
	           e.printStackTrace();
	           return false;
	        }
	    }
	
	public static void main(String[] args) {
		System.out.println(FilesexitsUtil.isNetFileAvailable("http://www.true.com"));
	    System.out.println(exists("http://www.baidu.com"));
	       System.out.println(exists("http://www.baidu.com/XXXXX.html"));
	}
}
