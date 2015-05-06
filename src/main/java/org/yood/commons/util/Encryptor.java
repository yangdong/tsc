package org.yood.commons.util;
import java.security.MessageDigest;

/**
 * One util of encrypt the string, provider two algorithms they are MD5 and SHA
 * 
 * @author ysjian
 * @version 1.0
 * @email ysjian_pingcx@126.com
 * @QQ 646633781
 * @telephone 18192235667
 * @csdnBlog http://blog.csdn.net/ysjian_pingcx
 * @createTime 2013-12-19 上午9:06:36
 * @copyRight Merit
 */
public final class Encryptor {

	private static final String METHOD_MD5 = "MD5";
	private static final String METHOD_SHA = "SHA";

	/**
	 * Privatize the constructor, can't be instantiated is the client
	 */
	private Encryptor() {

	}

	/**
	 * Encrypt the string by using MD5
	 * 
	 * @param param
	 *            the string need to be encrypted
	 * @return the string after being encrypted if param is not null, otherwise
	 *         null
	 */
	public static String encryptByMD5(String param) {
		return encrypt(param, METHOD_MD5);
	}

	/**
	 * Encrypt the string by using SHA
	 * 
	 * @param param
	 *            the string need to be encrypted
	 * @return the string after being encrypted if param is not null, otherwise
	 *         null
	 */
	public static final String encryptBySHA(final String param) {
		return encrypt(param, METHOD_SHA);
	}

	/**
	 * Encrypt the string with special algorithm
	 * 
	 * @param param
	 *            the string need to be encrypted
	 * @param algorithm
	 *            the special algorithm <code>MD5</code>or<code>SHA</code>,
	 *            assigned by the client
	 * @return the string after being encrypted if param is not null, otherwise
	 *         null
	 */
	private static String encrypt(String param, String algorithm) {
		try {
			MessageDigest md5 = MessageDigest.getInstance(algorithm);
			byte[] byteArray = param.getBytes("ISO-8859-1");
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
