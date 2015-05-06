package org.yood.commons.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * 通过AES算法对文本进行加密解密
 * 
 * @author ShaoJiang
 * 
 */
public class AESUtils0 {

	private static final String ALGORITHM_ARGS = "1234567812345678"; // 参数必须为16位

	private static SecretKey secretKey; // 加密密钥
	private static AlgorithmParameterSpec paramSpec; // 算法参数

	private static Cipher encryptCipher;

	private static Cipher decryptCipher;

	private static KeyGenerator kgen;

	static {
		// 加密并转换成16进制字符串
		try {
			kgen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} // 为指定算法生成一个密钥生成器对象。
	}

	private static Cipher initCipher(int opmode, Key key,
			AlgorithmParameterSpec params) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {

		// Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		// cipher.init(opmode, key, new SecureRandom());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(opmode, key, params);

		return cipher;
	}

	/**
	 * 加密，使用指定数据源生成密钥，使用用户数据作为算法参数进行AES加密
	 * 
	 * @param strIn
	 *            加密的数据
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException
	 */
	public static String encrypt(String key, String strIn)
			throws NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		// 用密钥和一组算法参数初始化此 cipher

		// 使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥长度。
		kgen.init(128, new SecureRandom(key.getBytes("UTF-8")));
		// 使用KeyGenerator生成（对称）密钥。
		secretKey = kgen.generateKey();
		// 使用iv中的字节作为IV来构造一个 算法参数。
		paramSpec = new IvParameterSpec(ALGORITHM_ARGS.getBytes("UTF-8"));
		// 生成一个实现指定转换的 Cipher 对象
		encryptCipher = initCipher(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
		return asHex(encryptCipher.doFinal(strIn.getBytes("UTF-8")));
	}

	/**
	 * 解密，对生成的16进制的字符串进行解密
	 * 
	 * 
	 * @param strIn
	 *            解密的数据
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws UnsupportedEncodingException
	 */
	public static String decrypt(String key, String strIn)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		kgen.init(128, new SecureRandom(key.getBytes()));
		// 使用KeyGenerator生成（对称）密钥。
		secretKey = kgen.generateKey();
		// 使用iv中的字节作为IV来构造一个 算法参数。
		paramSpec = new IvParameterSpec(ALGORITHM_ARGS.getBytes("UTF-8"));
		decryptCipher = initCipher(Cipher.DECRYPT_MODE, secretKey, paramSpec);
		return new String(decryptCipher.doFinal(asBin(strIn)));
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
	 */
	public static byte[] asBin(String strIn) {
		byte[] arrTemp = strIn.getBytes();
		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] result = new byte[arrTemp.length / 2];
		for (int i = 0; i < arrTemp.length; i = i + 2) {
			String strTmp = new String(arrTemp, i, 2);
			result[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return result;
	}
}
