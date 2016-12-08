package com.taskapp.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class Md5Encryptor {
public String encryptPassword(String Password) throws NoSuchAlgorithmException {
		
		MessageDigest messageDigest;
		String hashedPass;
	
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(Password.getBytes(), 0, Password.length());
			hashedPass = new BigInteger(1, messageDigest.digest()).toString(16);
			if (hashedPass.length() < 32) {
				hashedPass = "0" + hashedPass;
			}
			return hashedPass;
		
	}
}
