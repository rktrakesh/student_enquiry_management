package com.sem.project.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtils {
	
	public static String randomPasswordGenetator() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pwd = RandomStringUtils.random( 15, characters );
		return pwd;
	}

}
