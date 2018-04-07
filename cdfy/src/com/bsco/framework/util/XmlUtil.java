package com.bsco.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtil {
	
	public static void main(String args[]) {
		/**
		 * string转xml
		 */
	    StringBuffer condition = new StringBuffer();
		condition.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
		.append("<personList>")
		.append("<name>onePerson</name>").append("<age>25</age>")
		.append("<name>twoPerson</name>").append("<age>26</age>")
		.append("<name>threePerson</name>").append("<age>27</age>")
		.append("</personList>");
		String strXML = condition.toString();
		System.out.println(strXML);
		Document doc =stringConvertXML(strXML,"");
		//从xml中解析name数据
		NodeList nl = doc.getElementsByTagName("name");//根据标签获得列表
        Node classNode=nl.item(0);//取列表中第一个对象
        String cName=classNode.getTextContent();//获取对象的值
        System.out.println(cName);
        classNode.setTextContent("ddddddddddd");//修改xml中对象的值
    	//从xml中解析age数据
        NodeList n2 = doc.getElementsByTagName("age");
        Node classNode2 = n2.item(2);
        System.out.println(classNode2.getTextContent());
        /**
         * xml转string
         */
        String strDoc = xmlConvertString(doc);
        System.out.println(strDoc);
	}
	
	/**
	 * xml转字符串
	 */
	public static String xmlConvertString(Document doc) {
 
		String xmlStr = null;
		try {
			// XML转字符串
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty("encoding", "utf-8");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(doc), new StreamResult(bos));
			xmlStr = bos.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return xmlStr;
	}
	
	/**
	 * string转xml
	 */
	public static Document stringConvertXML(String data, String code) {
 
		StringBuilder sXML = new StringBuilder(code);
		sXML.append(data);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc = null;
		try {
			InputStream is = new ByteArrayInputStream(sXML.toString().getBytes(
					"utf-8"));
			doc = dbf.newDocumentBuilder().parse(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

}
