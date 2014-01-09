package com.example.aet.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;
/**
 * 
 * @author Jin Binbin
 *
 * @2013年11月21日
 *
 * @Version 1.0
 */
public final class DataUtil {

	private static final String TAG = "DataUtil";

	public static final String UPPER_LETTER_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String LOWER_LETTER_CHAR = "abcdefghijklmnopqrstuvwxyz";

	public static final String ARAB_NUMBER_CHAR = "0123456789";

	public static final String US_ASCII = "US-ASCII";

	public static final String ISO_8859_1 = "ISO-8859-1";

	public static final String UTF_8 = "UTF-8";

	public static final String UTF_16BE = "UTF-16BE";

	public static final String UTF_16LE = "UTF-16LE";

	public static final String UTF_16 = "UTF-16";

	public static final String GBK = "GBK";

	public static final String GB2312 = "GB2312";

	private DataUtil() {
	}

	public static String getOnlyStr() {
		Log.v(TAG, "getOnlyStr");
		return UUID.randomUUID().toString();
	}

	public static String generateStr(int length, String chars) {
		Log.v(TAG, "generateStr");
		String result = null;
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int size = chars.length();
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(random.nextInt(size)));
		}
		result = sb.toString();
		return result;
	}

	public static byte[] hex2byte(String strhex) {
		Log.v(TAG, "hex2byte");
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		Log.v(TAG, "byte2hex");
		StringBuffer hs = new StringBuffer(b.length);
		String stmp = "";
		int len = b.length;
		for (int n = 0; n < len; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs.append("0").append(stmp);
			else {
				hs = hs.append(stmp);
			}
		}
		return String.valueOf(hs);
	}

	public static String encryptData2MD5(String data) {
		Log.v(TAG, "encryptionData2MD5");
		String result = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(data.getBytes("UTF-8"));
			result = byte2hex(md5.digest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String encyptData2SHA(String data) {
		Log.v(TAG, "encyptData2SHA");
		String result = null;
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA");
			sha.update(data.getBytes("UTF-8"));
			result = byte2hex(sha.digest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String encryptDataReversible(String data) {
		Log.v(TAG, "encryptionDataReversible");
		char[] chars = data.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = (char) (chars[i] ^ '1');
		}
		return new String(chars);
	}

	public static KeyPair getKeyCase(String type, int size) {
		Log.v(TAG, "getKeyCase");
		KeyPair keyPair = null;
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(type);
			keyPairGen.initialize(size);
			keyPair = keyPairGen.generateKeyPair();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v(TAG, e.getMessage());
		}
		return keyPair;
	}

	public static String getEncryptHMACMD5(String data, String key) {
		Log.v(TAG, "getEncryptHMACMD5");
		String result = null;
		try {
			byte[] keyByte = key.getBytes("UTF-8");
			SecretKeySpec sks = new SecretKeySpec(keyByte, "HMACMD5");
			Mac mac = Mac.getInstance("HMACMD5");
			mac.init(sks);
			mac.update(data.getBytes());
			byte[] certifyCode = mac.doFinal();
			result = byte2hex(certifyCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] encrypt(byte[] src, byte[] key, String type) {
		Log.v(TAG, "encrypt");
		byte[] reuslt = null;
		try {
			SecretKeySpec sKeySpec = new SecretKeySpec(key, type);
			Cipher cipher = Cipher.getInstance(type);
			cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
			reuslt = cipher.doFinal(src);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return reuslt;
	}

	public static byte[] decrypt(byte[] src, byte[] key, String type) {
		Log.v(TAG, "decrypt");
		byte[] reuslt = null;
		try {
			SecretKeySpec sKeySpec = new SecretKeySpec(key, type);
			Cipher cipher = Cipher.getInstance(type);
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
			reuslt = cipher.doFinal(src);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return reuslt;
	}

	public static byte[] encrypt(PublicKey publicKey, byte[] srcBytes,
			String type) {
		Log.v(TAG, "encrypt");
		byte[] result = null;
		if (publicKey != null) {
			try {
				Cipher cipher = Cipher.getInstance(type);
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				result = cipher.doFinal(srcBytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static byte[] encrypt(PrivateKey privateKey, byte[] srcBytes,
			String type) {
		Log.v(TAG, "encrypt");
		byte[] result = null;
		if (privateKey != null) {
			try {
				Cipher cipher = Cipher.getInstance(type);
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
				result = cipher.doFinal(srcBytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static byte[] decrypt(PrivateKey privateKey, byte[] serBytes,
			String type) {
		Log.v(TAG, "decryot");
		byte[] result = null;
		if (privateKey != null) {
			try {
				Cipher cipher = Cipher.getInstance(type);
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				result = cipher.doFinal(serBytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static byte[] decrypt(PublicKey publicKey, byte[] serBytes,
			String type) {
		Log.v(TAG, "decryot");
		byte[] result = null;
		if (publicKey != null) {
			try {
				Cipher cipher = Cipher.getInstance(type);
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
				result = cipher.doFinal(serBytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}

		}
		return result;
	}

	public static String encryptAESStr(String data, String key) {
		Log.v(TAG, "encryptAESStr");
		String result = null;
		if (key != null && key.length() == 16) {
			try {
				byte[] resultbytes = encrypt(data.getBytes(),
						key.getBytes("UTF-8"), "AES");
				if (resultbytes != null) {
					result = byte2hex(resultbytes);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static String decryptAESStr(String data, String key) {
		Log.v(TAG, "decryptAESStr");
		String result = null;
		if (key != null && key.length() == 16) {
			try {
				byte[] databytes = hex2byte(data);
				byte[] resultbytes = decrypt(databytes, key.getBytes("UTF-8"),
						"AES");
				if (resultbytes != null) {
					result = new String(resultbytes);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static String decrypt3DESStr(String data, String key) {
		Log.v(TAG, "decrypt3DESStr");
		String result = null;
		if (key != null && key.length() == 16) {
			try {
				byte[] databytes = hex2byte(data);
				byte[] resultbytes = decrypt(databytes, key.getBytes("UTF-8"),
						"DESede");
				if (resultbytes != null) {
					result = new String(resultbytes);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static String encrypt3DESStr(String data, String key) {
		Log.v(TAG, "encrypt3DESStr");
		String result = null;
		if (key != null && key.length() == 16) {
			try {
				byte[] resultbytes = encrypt(data.getBytes(),
						key.getBytes("UTF-8"), "DESede");
				if (resultbytes != null) {
					result = byte2hex(resultbytes);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static String decryptDESStr(String data, String key) {
		Log.v(TAG, "decryptDESStr");
		String result = null;
		if (key != null && key.length() == 8) {
			try {
				byte[] databytes = hex2byte(data);
				byte[] resultbytes = decrypt(databytes, key.getBytes("UTF-8"),
						"DES");
				if (resultbytes != null) {
					result = new String(resultbytes);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static String encryptDESStr(String data, String key) {
		Log.v(TAG, "encryptDESStr");
		String result = null;
		if (key != null && key.length() == 8) {
			try {
				byte[] resultbytes = encrypt(data.getBytes(),
						key.getBytes("UTF-8"), "DES");
				if (resultbytes != null) {
					result = byte2hex(resultbytes);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return result;
	}

	public static String encryptPrivateKeyStr(String data,
			PrivateKey privateKey, String type) {
		Log.v(TAG, "encryptPrivateKeyStr");
		String result = null;
		try {
			byte[] resultbytes = encrypt(privateKey, data.getBytes(), type);
			if (resultbytes != null) {
				result = byte2hex(resultbytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

	public static String encryptPublicKeyStr(String data, PublicKey publicKey,
			String type) {
		Log.v(TAG, "encryptPublicKeyStr");
		String result = null;
		try {
			byte[] resultbytes = encrypt(publicKey, data.getBytes(), type);
			if (resultbytes != null) {
				result = byte2hex(resultbytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

	public static String decryptPrivateKeyStr(String data,
			PrivateKey privateKey, String type) {
		Log.v(TAG, "decryptPrivateKeyStr");
		String result = null;
		try {
			byte[] databytes = hex2byte(data);
			byte[] resultbytes = decrypt(privateKey, databytes, type);
			if (resultbytes != null) {
				result = new String(resultbytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

	public static String decryptPublicKeyStr(String data, PublicKey publicKey,
			String type) {
		Log.v(TAG, "decryptPublicKeyStr");
		String result = null;
		try {
			byte[] databytes = hex2byte(data);
			byte[] resultbytes = decrypt(publicKey, databytes, type);
			if (resultbytes != null) {
				result = new String(resultbytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return result;
	}
	
	public static String signetPrivateKeyDSAStr(String data,
			PrivateKey privateKey) {
		String result = null;
		try {
			java.security.Signature signet = java.security.Signature
					.getInstance("DSA");
			signet.initSign(privateKey);
			signet.update(data.getBytes());
			result = byte2hex(signet.sign());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean verificationPublicKeyDSA(String data,
			PublicKey publicKey, String signed) {
		boolean result = false;
		try {
			java.security.Signature signetcheck = java.security.Signature
					.getInstance("DSA");
			signetcheck.initVerify(publicKey);
			signetcheck.update(data.getBytes());
			if (signetcheck.verify(hex2byte(signed))) {
				result = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] zipData(byte[] data) {
		byte[] result = null;
		if (data != null) {
			ByteArrayOutputStream baops = new ByteArrayOutputStream();
			ZipOutputStream zout = new ZipOutputStream(baops);
			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			try {
				zout.putNextEntry(entry);
				zout.write(data);
				zout.closeEntry();
				zout.close();
				result = baops.toByteArray();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			} finally {
				if (baops != null) {
					try {
						baops.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (zout != null) {
					try {
						zout.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		return result;
	}

	public static byte[] unZipData(byte[] data) {
		byte[] result = null;
		if (data != null) {
			ByteArrayInputStream baips = new ByteArrayInputStream(data);
			ZipInputStream zips = new ZipInputStream(baips);
			try {
				while (zips.getNextEntry() != null) {
					byte[] buf = new byte[1024];
					int len = -1;
					int length = buf.length;
					ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
					while ((len = zips.read(buf, 0, length)) != -1) {
						baos.write(buf, 0, len);
					}
					result = baos.toByteArray();
					baos.flush();
					baos.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			} finally {
				if (baips != null) {
					try {
						baips.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (zips != null) {
					try {
						zips.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	public static String toASCII(String str)
			throws UnsupportedEncodingException {
		return changeCharset(str, US_ASCII);
	}

	public static String toISO_8859_1(String str)
			throws UnsupportedEncodingException {
		return changeCharset(str, ISO_8859_1);
	}

	public static String toUTF_8(String str)
			throws UnsupportedEncodingException {
		return changeCharset(str, UTF_8);
	}

	public static String toUTF_16BE(String str)
			throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16BE);
	}

	public static String toUTF_16LE(String str)
			throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16LE);
	}

	public static String toUTF_16(String str)
			throws UnsupportedEncodingException {
		return changeCharset(str, UTF_16);
	}

	public static String toGBK(String str) throws UnsupportedEncodingException {
		return changeCharset(str, GBK);
	}

	public static String toGB2312(String str)
			throws UnsupportedEncodingException {
		return changeCharset(str, GB2312);
	}

	public static String changeCharset(String str, String newCharset)
			throws UnsupportedEncodingException {
		if (str != null) {
			byte[] bs = str.getBytes();
			return new String(bs, newCharset);
		}
		return null;
	}


	public static String changeCharset(String str, String oldCharset,
			String newCharset) throws UnsupportedEncodingException {
		if (str != null) {

			byte[] bs = str.getBytes(oldCharset);

			return new String(bs, newCharset);
		}
		return null;
	}

	public static String getStrEncoding(String str) {
		try {
			if (str.equals(new String(str.getBytes(US_ASCII), US_ASCII))) {
				return US_ASCII;
			}
		} catch (Exception exception) {
		}
		try {
			if (str.equals(new String(str.getBytes(ISO_8859_1), ISO_8859_1))) {
				return ISO_8859_1;
			}
		} catch (Exception exception) {
		}
		try {
			if (str.equals(new String(str.getBytes(UTF_8), UTF_8))) {
				return UTF_8;
			}
		} catch (Exception exception2) {
		}
		try {
			if (str.equals(new String(str.getBytes(UTF_16), UTF_16))) {
				return UTF_16;
			}
		} catch (Exception exception3) {
		}
		try {
			if (str.equals(new String(str.getBytes(UTF_16BE), UTF_16BE))) {
				return UTF_16BE;
			}
		} catch (Exception exception3) {
		}
		try {
			if (str.equals(new String(str.getBytes(UTF_16LE), UTF_16LE))) {
				return UTF_16LE;
			}
		} catch (Exception exception3) {
		}
		try {
			if (str.equals(new String(str.getBytes(GBK), GBK))) {
				return GBK;
			}
		} catch (Exception exception3) {
		}
		try {
			if (str.equals(new String(str.getBytes(GB2312), GB2312))) {
				return GB2312;
			}
		} catch (Exception exception3) {
		}
		return "";
	}


	public static boolean isHaveChinese(String input) {
		String regex = "[\\u4e00-\\u9fa5]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	public static boolean isChinese(char ch) {
		char[] chars = new char[] { ch };
		String chStr = new String(chars);
		return isHaveChinese(chStr);
	}

}
