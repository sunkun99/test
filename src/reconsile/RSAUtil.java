package reconsile;

import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAUtil {

/*	private static final String DEFAULT_PUBLIC_KEY=
       "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSL+5qyAfU6s4nm0pc36nLfV2t" + "\r" +  
       "kE0tdMhCdcNzc66RoG1uf4pACfVFUUB+Gnv7fP5O9UmNUdayfv4JrB8m3Nlz8Lib" + "\r" +  
       "r5938h/dSRdAyUawEEVRZVRaHN3wV0G8iBTZz0J2XCkdzNVTmNd1wTHiD0R20KVs" + "\r" +  
       "IBEOkkzkaFwkYtXCyQIDAQAB" + "\r";  
	
	private static final String DEFAULT_PRIVATE_KEY=  
		"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANIv7mrIB9Tqzieb" + "\r" +  
        "Slzfqct9Xa2QTS10yEJ1w3NzrpGgbW5/ikAJ9UVRQH4ae/t8/k71SY1R1rJ+/gms" + "\r" +  
        "Hybc2XPwuJuvn3fyH91JF0DJRrAQRVFlVFoc3fBXQbyIFNnPQnZcKR3M1VOY13XB" + "\r" +  
        "MeIPRHbQpWwgEQ6STORoXCRi1cLJAgMBAAECgYEAtap/4wMo4bpgnJL33vXiB+M/" + "\r" +
        "SHhkfFlnjSnD2NGvnGaQruTFVTxGJbdoipXRezg5hX00KVUGwBhK8Yk3E5vmxWHC" + "\r" +  
        "p4WL4QB33phCX2MszXR5y+/vAWBS0T3JmTZCf9Rty0VSOn+rGiHg5R3q0AtwjRgH" + "\r" +  
        "arF7wk4K+hTq8cYhiEECQQDs91H3L3ZMIDg76MkxwB5J2FuoRE/4viEtI4zYPgWP" + "\r" +  
        "+Py90VcCT0hSCI0RLPZ82hesoXbqu7mjYBX2shu8iFIdAkEA4xH2puedqI/NLwTR" + "\r" +
        "RNrdC5zh4ZpK+KDONCNlr5sACVqBd+JzyYpIwTxSCEskxy6V/HV5aEibHw96p15m" + "\r" +  
        "8ExTnQJAd80Nzmdbal87ruYgmZtkdLcCl855k+sjwLthYI3yp/gcZybn004b5QVX" + "\r" +  
        "tGBD+ZkVMPysrz3bO4K/d+pR5X4R7QJBAN4rxlS6DIVP5ieaJBOlvV/OaShu6eno" + "\r" +  
        "lLlo5OZz1w5P0pt1I2tPdS40keLxexO0HiZ83oRNfhFe+Nhnwnpdg/UCQF9qWvmE" + "\r" +
        "FE98p2yEmiSnDQYOlWDpM8wZDe0FY3Z4l8vCMtiCy4pgmGJ5jIWAP5+Ar+JywB6l" + "\r" +
        "zMTerQDdKzzAIpQ=" + "\r";*/
	
	private static final String DEFAULT_PUBLIC_KEY=   
	       "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSL+5qyAfU6s4nm0pc36nLfV2t" +
	       "kE0tdMhCdcNzc66RoG1uf4pACfVFUUB+Gnv7fP5O9UmNUdayfv4JrB8m3Nlz8Lib" +
	       "r5938h/dSRdAyUawEEVRZVRaHN3wV0G8iBTZz0J2XCkdzNVTmNd1wTHiD0R20KVs" +
	       "IBEOkkzkaFwkYtXCyQIDAQAB";  
			
	private static final String DEFAULT_PRIVATE_KEY=  
				"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANIv7mrIB9Tqzieb" +
		        "Slzfqct9Xa2QTS10yEJ1w3NzrpGgbW5/ikAJ9UVRQH4ae/t8/k71SY1R1rJ+/gms" +
		        "Hybc2XPwuJuvn3fyH91JF0DJRrAQRVFlVFoc3fBXQbyIFNnPQnZcKR3M1VOY13XB" +
		        "MeIPRHbQpWwgEQ6STORoXCRi1cLJAgMBAAECgYEAtap/4wMo4bpgnJL33vXiB+M/" +
		        "SHhkfFlnjSnD2NGvnGaQruTFVTxGJbdoipXRezg5hX00KVUGwBhK8Yk3E5vmxWHC" +
		        "p4WL4QB33phCX2MszXR5y+/vAWBS0T3JmTZCf9Rty0VSOn+rGiHg5R3q0AtwjRgH" +
		        "arF7wk4K+hTq8cYhiEECQQDs91H3L3ZMIDg76MkxwB5J2FuoRE/4viEtI4zYPgWP" +
		        "+Py90VcCT0hSCI0RLPZ82hesoXbqu7mjYBX2shu8iFIdAkEA4xH2puedqI/NLwTR" +
		        "RNrdC5zh4ZpK+KDONCNlr5sACVqBd+JzyYpIwTxSCEskxy6V/HV5aEibHw96p15m" +
		        "8ExTnQJAd80Nzmdbal87ruYgmZtkdLcCl855k+sjwLthYI3yp/gcZybn004b5QVX" +
		        "tGBD+ZkVMPysrz3bO4K/d+pR5X4R7QJBAN4rxlS6DIVP5ieaJBOlvV/OaShu6eno" +
		        "lLlo5OZz1w5P0pt1I2tPdS40keLxexO0HiZ83oRNfhFe+Nhnwnpdg/UCQF9qWvmE" +
		        "FE98p2yEmiSnDQYOlWDpM8wZDe0FY3Z4l8vCMtiCy4pgmGJ5jIWAP5+Ar+JywB6l" +
		        "zMTerQDdKzzAIpQ=";
	
	//TODO sk 切换lion
	      
/*   private static final String DEFAULT_PRIVATE_KEY=
	   PropertiesLoaderSupportUtils.getProperty("pay-account-info-service.store.trans.info.rsa.private.key");*/
   
 
/**
    * 私钥 
    */
   public static RSAPrivateKey privateKey;  
 
   /**
    * 公钥 
    */
   public static RSAPublicKey publicKey;  
   
   /**
    * 加密类 
    */
   private static Cipher encryptCipher;
   
   /**
    * 解密类 
    */
   private static Cipher decryptCipher;
   
   private static Base64 base64 = new Base64();
 
   /**
    * 从字符串中加载公钥 
    * @param publicKeyStr 公钥数据字符串 
    * @throws Exception 加载公钥时产生的异常 
    */
	static {
		Security.addProvider(new BouncyCastleProvider());
		//加载公钥
		try {
			byte[] buffer = base64.decode(DEFAULT_PUBLIC_KEY);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
			encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey); 
		} catch (Exception e ) {
			try {
				throw new Exception("加载公钥失败!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
		//加载私钥
		try {
			byte[] buffer = base64.decode(DEFAULT_PRIVATE_KEY);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
			decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
		} catch (Exception e ) {
			try {
				throw new Exception("加载私钥失败!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
	}  
 
   /**
    * 加密过程 
    * @param plainTextData 明文 
    * @return byte[]
    * @throws Exception 加密过程中的异常信息 
    */
   public static byte[] encrypt(byte[] plainTextData) throws Exception{
       try { 
           byte[] output= encryptCipher.doFinal(plainTextData);
           return output;  
       } catch (Exception e) {  
           throw new Exception("加密失败");  
       }  
   }  
   
   /**
    * 加密过程 
    * @param plainTextData 明文
    * @return String
    * @throws Exception 加密过程中的异常信息 
    */
   public static String encryptByBase64(byte[] plainText) throws Exception{
       try { 
           byte[] output= encrypt(plainText);
           return base64.encodeAsString(output);  
       } catch (Exception e) {  
           throw new Exception("加密失败");  
       }  
   }
 
   /**
    * 解密过程 
    * @param privateKey 私钥 
    * @param cipherData 密文数据 
    * @return byte[] 
    * @throws Exception 解密过程中的异常信息 
    */
   public static byte[] decrypt(byte[] cipherText) throws Exception{
       try {  
           byte[] output= decryptCipher.doFinal(cipherText);  
           return output;  
       }  catch (Exception e) {  
           throw new Exception("解密失败");  
       }        
   }
   
   /**
    * 解密过程 
    * @param privateKey 私钥
    * @return String 
    * @throws Exception 解密过程中的异常信息 
    */
   public static String decryptByBase64(String cipherData) throws Exception{
       try {
    	   byte[] cipherByteArray = base64.decode(cipherData);
           byte[] output= decrypt(cipherByteArray);  
           return new String(output);  
       }  catch (Exception e) {  
           throw new Exception("解密失败");  
       }        
   }
 
   public static void getSetTransPwdStr(String userID, String transPwd) {
	   String md54TransPwd = MD5Util.md5(userID + MD5Util.md5(transPwd));
	   try {  
		   System.out.println("RSA原文: " + md54TransPwd);
           //加密
           String cipherStr = encryptByBase64(md54TransPwd.getBytes());
           System.out.println("Baes64密文：" + cipherStr);
           String plainText = decryptByBase64(cipherStr);
           System.out.println("RSA原文：" + plainText);
       } catch (Exception e) {  
           System.err.println(e.getMessage());  
       }  
   }
 
   public static void main(String[] args){
//	   Security.addProvider(new BouncyCastleProvider());
	   getSetTransPwdStr("123", "666888");  
   }
}
