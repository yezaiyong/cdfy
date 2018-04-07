package com.bsco.framework.web.filter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

public final class CommonRequestWrapper extends HttpServletRequestWrapper {

	private static final String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
	private static List<String> injList = Arrays.asList(inj_str.split("\\|"));
	private static final String encoding = "UTF-8";
	private static List<String> badwords = null;
	static {
		if (badwords == null) {
			badwords = new ArrayList<String>();
		}
		try {
			InputStream is = CommonRequestWrapper.class
					.getResourceAsStream("/badwords.txt");
			if (is != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						is, "UTF-8"));
				String line = null;
				while ((line = in.readLine()) != null) {
					badwords.add(line);
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public CommonRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public String getParameter(String name) {
		return replaceCheck(super.getParameter(name));
	}

	public String[] getParameterValues(String name) {
		String[] arrayOfString = (String[]) super.getParameterValues(name);
		if (arrayOfString == null)
			return null;
		String[] tmpString = new String[arrayOfString.length];
		for (int i = 0; arrayOfString != null && i < arrayOfString.length; i++)
			tmpString[i] = replaceCheck(arrayOfString[i]);
		return tmpString;
	}

	public Map getParameterMap() {
		Map map = new HashMap();
		Map localMap = super.getParameterMap();
		Set localSet = localMap.keySet();
		Iterator localIterator = localSet.iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			String[] arrayOfString = (String[]) localMap.get(str);
			if (arrayOfString == null)
				continue;
			String[] tmpArrayString = new String[arrayOfString.length];
			for (int i = 0; i < arrayOfString.length; i++) {
				tmpArrayString[i] = replaceCheck(arrayOfString[i]);
			}
			map.put(str, tmpArrayString);
		}
		return map;
	}

	private String replaceCheck(String paramString) {
		if (StringUtils.isEmpty(paramString))
			return null;
		String method = getMethod();
		if (method.equals("GET")) {
			try {
				paramString = new String(paramString.trim().getBytes(
						"ISO-8859-1"), encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (sql_inj(paramString)) {
				return "";
			}
		}
		for (String word : badwords) {
			if (paramString.contains(word)) {
				paramString = StringUtils.replace(paramString, word, "**");
			}
		}
		return paramString.trim();
	}

	public boolean sql_inj(String str) {
		for (String inj : injList) {
			if (str.contains(" " + inj + " ")) {
				return true;
			}
		}
		return false;
	}

}
