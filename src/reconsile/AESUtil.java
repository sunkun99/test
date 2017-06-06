package reconsile;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;




/**
 * @author kun.sun 
 * aes加密工具类11
 */
public class AESUtil {
//beta
//	private static String STORE_TRANS_INFO_KEY = "VSF2M6HM8D6QPJQO";
	private static String STORE_TRANS_INFO_KEY = "pk2eu2b74t88juyb";

	private static String SET_TRANS_PWD_KEY = "VSF2M6HM8D6QPJAB";
	
//	public static final String SET_TRANS_PWD_KEY =
//	    PropertiesLoaderSupportUtils.getProperty("pay-account-info-service.set.trans.pwd.aes.key");
	
//	private static final String STORE_TRANS_INFO_KEY =
//		PropertiesLoaderSupportUtils.getProperty("pay-account-info-service.store.trans.info.ase.key");
	
	private static String ALGORITHM = "AES";

	private static final String CIPHER_MODE = "AES/ECB/PKCS7Padding";

	private static Base64 base64 = new Base64();

	private static final SecretKey AES_KEY_4_STORE_TRANS_INFO = new SecretKeySpec(STORE_TRANS_INFO_KEY.getBytes(), ALGORITHM);
	
	private static final SecretKey AES_KEY_4_SET_TRANS_PWD = new SecretKeySpec(SET_TRANS_PWD_KEY.getBytes(), ALGORITHM);

	public static Cipher ENCRYPT_CIPHER_4_STORE_TRANS_INFO;
	
	public static Cipher DECRYPT_CIPHER_4_STORE_TRANS_INFO;
	
    public static Cipher ENCRYPT_CIPHER_4_SET_TRANS_PWD;
	
    public static Cipher DECRYPT_CIPHER_4_SET_TRANS_PWD;
	static {
		try {
            //加载第三方加解密服务提供商
            Security.addProvider(new BouncyCastleProvider());
            //初始化交易信息加密类
            ENCRYPT_CIPHER_4_STORE_TRANS_INFO = Cipher.getInstance(CIPHER_MODE);
            ENCRYPT_CIPHER_4_STORE_TRANS_INFO.init(Cipher.ENCRYPT_MODE, AES_KEY_4_STORE_TRANS_INFO);
            //初始化交易信息解密类
            DECRYPT_CIPHER_4_STORE_TRANS_INFO = Cipher.getInstance(CIPHER_MODE);
            DECRYPT_CIPHER_4_STORE_TRANS_INFO.init(Cipher.DECRYPT_MODE, AES_KEY_4_STORE_TRANS_INFO);
            //初始化交易密码加密类
            ENCRYPT_CIPHER_4_SET_TRANS_PWD = Cipher.getInstance(CIPHER_MODE);
            ENCRYPT_CIPHER_4_SET_TRANS_PWD.init(Cipher.ENCRYPT_MODE, AES_KEY_4_SET_TRANS_PWD);
            //初始化交易密码解密类
            DECRYPT_CIPHER_4_SET_TRANS_PWD = Cipher.getInstance(CIPHER_MODE);
            DECRYPT_CIPHER_4_SET_TRANS_PWD.init(Cipher.DECRYPT_MODE, AES_KEY_4_SET_TRANS_PWD);
		} catch (NoSuchAlgorithmException e) {
			try {
				throw e;
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		} catch (NoSuchPaddingException e) {
			try {
				throw e;
			} catch (NoSuchPaddingException e1) {
				e1.printStackTrace();
			}
		} catch (InvalidKeyException e) {
		    try {
				throw e;
			} catch (InvalidKeyException e1) {
				e1.printStackTrace();
			}
		}
	}
     
    /*
    * 默认密钥对字符串加密,结果以base64方式转码存放
    *  
    * @param str 
    * @return 
    * @throws java.security.InvalidKeyException
    * @throws javax.crypto.IllegalBlockSizeException
    * @throws javax.crypto.BadPaddingException
    * @throws javax.crypto.NoSuchPaddingException
    * @throws java.security.NoSuchAlgorithmException
    * @throws java.io.UnsupportedEncodingException
    */
   public static String encryt(String plainText, Cipher cipher) throws InvalidKeyException,  
       IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
       // 加密，结果保存进cipherByte  
       byte[] cipherByteArray = cipher.doFinal(plainText.getBytes());
       String cipherStr = base64.encodeAsString(cipherByteArray);
       return cipherStr;
   }  
   
    /*
    * 指定密钥对字符串加密，结果以base64方式转码存放
    *  
    * @param str 
    * @return 
    * @throws java.security.InvalidKeyException
    * @throws javax.crypto.IllegalBlockSizeException
    * @throws javax.crypto.BadPaddingException
	* @throws javax.crypto.NoSuchPaddingException
	* @throws java.security.NoSuchAlgorithmException
	* @throws java.io.UnsupportedEncodingException
    */
   public static String encrytBySpecificKey(String plainText, String specificKey) throws InvalidKeyException,  
       IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {  
       // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式  
	   SecretKey specificSeceretKey = new SecretKeySpec(specificKey.getBytes(), "AES");
	   Cipher c = Cipher.getInstance(CIPHER_MODE);
       c.init(Cipher.ENCRYPT_MODE, specificSeceretKey);
       // 加密，结果保存进cipherByte  
       byte[] cipherByteArray = c.doFinal(plainText.getBytes());
       String cipherStr = base64.encodeAsString(cipherByteArray);
       return cipherStr;
   }  
   
   /**
    * 指定密钥对字符串解密，先将字符串进行base64转码，再进行解密 
    *  
    * @param buff 
    * @return 
    * @throws java.security.InvalidKeyException
    * @throws javax.crypto.IllegalBlockSizeException
    * @throws javax.crypto.BadPaddingException
    * @throws javax.crypto.NoSuchPaddingException
    * @throws java.security.NoSuchAlgorithmException
    * @throws java.io.UnsupportedEncodingException
    */
   public static String decrypt(String cipherText, Cipher cipher) throws InvalidKeyException,  
   IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
	   // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式  
	   byte[] cipherByteArray = base64.decode(cipherText);
	   byte[] plainText = cipher.doFinal(cipherByteArray);  
	   return new String(plainText);
   }
   
 
   /**
    * 默认密钥对字符串解密，先将字符串进行base64转码，再进行解密
    *  
    * @param buff 
    * @return 
    * @throws java.security.InvalidKeyException
    * @throws javax.crypto.IllegalBlockSizeException
    * @throws javax.crypto.BadPaddingException
	* @throws javax.crypto.NoSuchPaddingException
	* @throws java.security.NoSuchAlgorithmException
	* @throws java.io.UnsupportedEncodingException
    */
   public static String decryptBySpecificKey(String cipherText, String specificKey) throws InvalidKeyException,  
       IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
       // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
	   SecretKey deskey = new SecretKeySpec(specificKey.getBytes(), ALGORITHM);
	   Cipher c = Cipher.getInstance(CIPHER_MODE);
       c.init(Cipher.DECRYPT_MODE, deskey); 
	   byte[] cipherByteArray = base64.decode(cipherText);
       byte[] plainText = c.doFinal(cipherByteArray);  
       return new String(plainText);
   }  
   
   /**
    * 将二进制转换成16进制 
    * @method parseByte2HexStr 
    * @param buf 
    * @return 
    * @throws  
    * @since v1.0 
    */
   public static String parseByte2HexStr(byte buf[]){  
       StringBuffer sb = new StringBuffer();  
       for(int i = 0; i < buf.length; i++){  
           String hex = Integer.toHexString(buf[i] & 0xFF);  
           if (hex.length() == 1) {  
               hex = '0' + hex;  
           }  
           sb.append(hex.toUpperCase());  
       }  
       return sb.toString();  
   }  
     
   /**
    * 将16进制转换为二进制 
    * @method parseHexStr2Byte 
    * @param hexStr 
    * @return 
    * @throws  
    * @since v1.0 
    */
   public static byte[] parseHexStr2Byte(String hexStr){  
       if(hexStr.length() < 1)  
           return null;  
       byte[] result = new byte[hexStr.length()/2];  
       for (int i = 0;i< hexStr.length()/2; i++) {  
           int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
           int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
           result[i] = (byte) (high * 16 + low);  
       }  
       return result;  
   }
   
   public static void method4StoreTransInfo() throws Exception {
	   String userName = "一二三四五六七八九十一二三四五六七";
	   String mobilePhone = "13601945906";
	   String credentialID = "320923199001010202";
       
       String encryptedCredentialID = MD5Util.md5(credentialID);
       String encryptedUserName = encryt(userName, ENCRYPT_CIPHER_4_STORE_TRANS_INFO); 
       String encryptedMobilePhone = encryt(mobilePhone, ENCRYPT_CIPHER_4_STORE_TRANS_INFO);
       
       System.out.println("身份证原文:" + credentialID);
       System.out.println("身份证密文: " + encryptedCredentialID);
       System.out.println("姓名原文:" + userName);
       System.out.println("姓名密文: " + encryptedUserName);
       System.out.println("手机号原文:" + mobilePhone);
       System.out.println("手机号密文: " + encryptedMobilePhone);
   }
   
   public static void aseDecrypt4ValidPwd() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
	   String transPwd = "666888";
	   String plainText = MD5Util.md5(transPwd);
	   System.out.println("加密原文：" + plainText);
	   String key = "33A453E1730D08F6712F9E6488A8B7B0abcdefghijfieafea";
	   String md54Key = MD5Util.md5(key).substring(0, 16);
	   String encryptStr = encrytBySpecificKey(plainText, md54Key);
	   System.out.println("AES密文：" + encryptStr);
	   plainText = decryptBySpecificKey(encryptStr, md54Key);
	   System.out.println("AES解密：" + plainText);
   }
   
   public static void simpleTest(String originStr) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
	   System.out.println("原文: " + originStr);
	   String encryptTransPwd = encryt(originStr, ENCRYPT_CIPHER_4_SET_TRANS_PWD);

//       String encryptTransPwd = "S+SI3fBDIa7EgwYCXQrCAtmHaaoNslS0i0M7MOH/LXE=";
	   System.out.println("密文: " + encryptTransPwd);
	   String originStr1 = decrypt(encryptTransPwd, DECRYPT_CIPHER_4_SET_TRANS_PWD);
	   System.out.println("原文: " + originStr1);
   }
 
   /**
    * @param args 
    * @throws javax.crypto.NoSuchPaddingException
    * @throws java.security.NoSuchAlgorithmException
    * @throws javax.crypto.BadPaddingException
    * @throws javax.crypto.IllegalBlockSizeException
    * @throws java.security.InvalidKeyException
    */
   public static void main(String[] args) throws Exception {
//	   aseDecrypt4ValidPwd();
	   
//	   method4StoreTransInfo();
//	   simpleTest("nKitq2LVf9dzmZKutAUcJyi1cw1YXBp14+wCberQrpo=");
	   simpleTest("4895920201057378");
//	   simpleTest("12345678");
//	   simpleTest("123456789");
//	   simpleTest("1234567890123456");
//	   simpleTest("12345678901234561");
//	   simpleTest("12345678901234561234567890123456");
//	   simpleTest("123456789012345612345678901234561");
	   
   }  

}
