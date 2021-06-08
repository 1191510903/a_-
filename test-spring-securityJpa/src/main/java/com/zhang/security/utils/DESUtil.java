package com.zhang.security.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class DESUtil {
	//定义加密算法，有DES、DESede(即3DES)、Blowfish
    private static final String ALGORITHM = "DESede";
    // 算法名称/加密模式/填充方式
    private static final String CIPHER_ALGORITHM_ECB = "DESede/ECB/PKCS5Padding";
  
    private static Cipher cipher = null;
  
  
    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if(cipher == null ){
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
        }
        return cipher;
    }
  
    /**
    * 加密方法
    * @param src
    * @param key
    * @return
    */
    public static String encrypt(String src, String key, boolean hex) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), ALGORITHM);    //生成密钥
            Cipher c = getCipher();    //获取Cipher工具类
            c.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
            if(hex) {
                return bytesToHexString(c.doFinal(src.getBytes()));
            }else{
                return Base64.getEncoder().encodeToString(c.doFinal(src.getBytes()));
            }
        } catch (Exception e) {
        }
        return StringUtils.EMPTY;
    }
  
    /**
    * byte数组转换为16进制字符
    * @param bArray
    * @return
    */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
  
    /**
    * Base64解码
    * @param src
    * @return
    */
    public static byte[] base64Decode(String src){
        return Base64.getDecoder().decode(src);
    }
  
    /**
    * Convert hex string to byte[]
    * @param hexString the hex string
    * @return byte[]
    */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return new byte[0];
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
    * char 转换 byte
    * @param c char
    * @return byte
    */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
  
    /**
    * 解密函数
    * @param src 密文的字节数组
    * @param key 密钥
    * @return
    */
    public static byte[] decrypt(byte[] src, String key) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(key), ALGORITHM);
            Cipher c = getCipher(); //获取Cipher工具类
            c.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式
            return c.doFinal(src);
        } catch (Exception e) {
        }
        return new byte[0];
    }
  
    /**
    * 根据字符串生成密钥字节数组
    * @param keyStr 密钥字符串
    * @return
    * @throws UnsupportedEncodingException
    */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
  
        ///执行数组拷贝
        if (key.length > temp.length) {
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }
  
    public static String decryptToString(String src, String key) {
        if(StringUtils.isBlank(src) || StringUtils.isBlank(key)) return "";
  
        byte[] decrypted = decrypt(Base64.getDecoder().decode(src), key);
        return decrypted != null?new String(decrypted):"";
    }
}
