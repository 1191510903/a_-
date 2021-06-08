package com.zhang.security.utils;

import com.zhang.security.entity.UserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenUtils {
	public static final String MAGIC_KEY = "obfuscate";
	public static String createToken(UserDetails userDetails){
		UserDetail user = (UserDetail)userDetails;
		long expires = System.currentTimeMillis() + 1000L * 60 * 60;
		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(user.getLoginId());
		tokenBuilder.append(":");
		tokenBuilder.append(user.getId());
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));
		return tokenBuilder.toString();
	}
	
	public static String computeSignature(UserDetails userDetails, long expires){
		UserDetail user = (UserDetail)userDetails;
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(user.getLoginId());
		signatureBuilder.append(":");
		signatureBuilder.append(user.getId());
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append(userDetails.getPassword());
		signatureBuilder.append(":");
		signatureBuilder.append(TokenUtils.MAGIC_KEY);
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
	         throw new IllegalStateException("No MD5 algorithm available!");
	      }

	      return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	   }


	   public static String getUserNameFromToken(String authToken)
	   {
	      if (null == authToken) {
	         return null;
	      }
	      String[] parts = authToken.split(":");
	      return parts[0];
	   }

	   public static boolean validateToken(String authToken, UserDetails userDetails)
	   {
	      String[] parts = authToken.split(":");
	      long expires = Long.parseLong(parts[2]);
	      String signature = parts[3];

//	      if (expires < System.currentTimeMillis()) {
//	         return false;
//	      }

	      return signature.equals(TokenUtils.computeSignature(userDetails, expires));
	   }
	   
	   public static String getLoginName(HttpServletRequest request){
		   String authToken = request.getHeader("X-Auth-Token");
		   if (authToken == null) {
			   authToken = request.getParameter("token");
		   }
		   return TokenUtils.getUserNameFromToken(authToken);
	   }
	   public static Long getLoginId(HttpServletRequest request){
		   if(null == request){
			   return null;
		   }
		   String authToken = request.getHeader("X-Auth-Token");
		   if (authToken == null) {
			   authToken = request.getParameter("token");
		   }
		   if (null == authToken) {
			   return null;
		   }
		   String[] parts = authToken.split(":");
		   return Long.parseLong(parts[1]);
	   }
}
