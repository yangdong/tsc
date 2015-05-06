package org.yood.commons.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 通过AES算法对文本进行加密解密
 * 
 * @author ShaoJiang
 *
 */
public class AESUtils2 {

	public static final String ENCODING_UTF8 = "UTF-8";
	private static final String ALGORITHM = "AES";
	private static KeyGenerator kgen;

	static {
		try {
			kgen = KeyGenerator.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param encryptKey
	 *            加密密码
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static String encrypt(String content, String encryptKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		kgen.init(128, new SecureRandom(encryptKey.getBytes(ENCODING_UTF8)));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
		byte[] byteContent = content.getBytes(ENCODING_UTF8);
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		return asHex(cipher.doFinal(byteContent)); // 加密
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param decryptKey
	 *            解密密钥
	 * @return
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(String content, String decryptKey)
			throws IllegalBlockSizeException, BadPaddingException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, UnsupportedEncodingException {
		kgen.init(128, new SecureRandom(decryptKey.getBytes(ENCODING_UTF8)));
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		return new String(cipher.doFinal(asBin(content)));
	}

	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String asHex(byte buf[]) {
		StringBuilder result = new StringBuilder(buf.length * 2);
		for (byte b : buf) {
			// 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
			String value = Integer.toHexString(0xFF & b);
			// 每个字节8为，转为16进制标志，2个16进制位
			result.append(1 == value.length() ? ("0" + value) : (value));
		}
		return result.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组 互为可逆的转换过程
	 *
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] asBin(String strIn)
			throws UnsupportedEncodingException {
		byte[] arrTemp = strIn.getBytes(ENCODING_UTF8);
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] result = new byte[arrTemp.length / 2];
		for (int i = 0; i < arrTemp.length; i = i + 2) {
			String strTmp = new String(arrTemp, i, 2);
			result[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return result;
	}
}
