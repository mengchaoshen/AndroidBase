package com.smc.androidbase;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;



public class TestAPI2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String url = "http://api.danghongyun.com/rest";
	        String accessKey = "cb170875-15d";//"97df2310-0ed";
	        String accessSecret =  "4FTxLuDSQBK5UdU5SwIb";	    
	        HashMap<String, Object> params = new HashMap<String, Object>();
//	        params.put("accessKey", accessKey);
	        params.put("action", "imServerDomain");
	        
	        params.put("version", "2.0");
	        long time = new Date().getTime();
	        params.put("timestamp","1531123804493");
	       
	        params.put("iamId", "1573");
	 
	        
	        String sig;
			try {
				sig = generateSignature(params, accessSecret);
				 params.put("signature", sig);
				 System.out.println( sig);
//					String result = HttpClientUtil.get(url, params);
//			        System.out.println("result:"+ result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	       		 		 		 			 
		 
	}
	public static  String generateSignature(HashMap<String, Object> param, String secret) throws Exception {

//	    if (StringUtils.isBlank(secret) || MapUtils.isEmpty(param)) {
//	        return null;
//	    }
	    List<String> keyList = new ArrayList<String>(param.keySet());
	    Collections.sort(keyList, String.CASE_INSENSITIVE_ORDER);
	    StringBuilder sb = new StringBuilder(secret);
	    for (String key : keyList) {
	        if (param.get(key) != null) {

	            if (!key.equals("signature")) {
	                sb.append(key);
	                sb.append("=");
	                sb.append(param.get(key));
	            }
	        }
	    }
         System.out.println();
	    String signature = hmacSHA256Encrypt(sb.toString(), secret);
	    return signature;
	}
	/** 
	 * 使用 HMAC-SHA256 签名方法对对plainText进行签名 
	 * @param plainText 被签名的字符串 
	 * @param accessSecret 密钥 
	 * @return 
	 * @throws Exception 
	 */
	public static String hmacSHA256Encrypt(String plainText, String accessSecret) throws Exception {

	    Mac mac = Mac.getInstance("HmacSHA256");
	    byte[] secretByte = accessSecret.getBytes("UTF-8");
	    byte[] dataBytes = plainText.getBytes("UTF-8");
	    SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");

	    mac.init(secret);
	    byte[] doFinal = mac.doFinal(dataBytes);
	    byte[] hexB = new Hex().encode(doFinal);
	    String signature = new String(hexB);
	    return signature;
	}

}
