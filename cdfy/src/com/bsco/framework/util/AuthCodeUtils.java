/**
 * 
 */
package com.bsco.framework.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

/**
 * @author jack.li
 *
 */
public class AuthCodeUtils {

	private static final String SEP = "\0";
	
	public static final String encryptAuthCode(Object... args) {
		String tmp = StringUtils.join(args, SEP);
		return DesSecretUtils.encrypt(URLEncoder.encode(tmp));
	}
	
	public static final String[] decryptAuthCode(String authcode) {
		String temp = DesSecretUtils.decrypt(authcode);
		temp = URLDecoder.decode(temp);
		return temp.split(SEP);
	}
	
}
