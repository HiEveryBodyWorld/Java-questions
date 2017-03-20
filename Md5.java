package com.recharge.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5 {
	/**
	 * Md5加密
	 * 
	 * @param param
	 * @return
	 */
	public static String encode(String param)
	{
		if (param == null)
		{
			return null;
		}
		java.security.MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
			byte[] result = md5.digest(param.getBytes("UTF-8"));
			return byte2String(result);
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
		catch (UnsupportedEncodingException e)
		{
			return null;
		}
	}

	/**
	 * byte2String
	 * 
	 * @param in
	 * @return
	 */
	private static String byte2String(byte[] in)
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(in));
		String str = "";
		try
		{
			for (int j = 0; j < in.length; j++)
			{
				String tmp = Integer.toHexString(data.readUnsignedByte());
				if (tmp.length() == 1)
				{
					tmp = "0" + tmp;
				}
				str = str + tmp;
			}
		}
		catch (Exception ex)
		{
		}
		return str;
	}

	/**
	 * Md5加密
	 * 
	 * @param param
	 * @return
	 */
	public static String encode(byte[] b)
	{
		java.security.MessageDigest md5 = null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
			byte[] result = md5.digest(b);
			return byte2String(result);
		}
		catch (NoSuchAlgorithmException e)
		{
			return null;
		}
	}

	public static void main(String[] args){
		System.out.println(Md5.encode("111111"));
	}
	
	
	/**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}
