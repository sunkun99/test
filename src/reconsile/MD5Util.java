package reconsile;


import java.security.MessageDigest;


/**
 * @author kun.sun md5加密工具类
 */
public class MD5Util {

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F" };

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		//字节转换为16进制
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String md5(String plainText) {
		String resultStr = null;
		if(plainText != null && "".equals(plainText)) {
			return "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultStr = byteArrayToHexString(md.digest(plainText.getBytes()));
			return resultStr;
		} catch (Exception exception) {
			return "";
		}
	}

	public static String md5(String plainText, String charsetName) {
		String resultStr = null;
		if(plainText != null && "".equals(plainText)) {
			return "";
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultStr = byteArrayToHexString(md.digest(plainText.getBytes(charsetName)));
			return resultStr;
		} catch (Exception exception) {
			return "";
		}
	}

	public static void main(String[] args) {
		String pwd = "666888";
		String userID = "123";
		String transPwd =  md5(userID + md5(pwd));
		System.out.println(transPwd);

	}


}
