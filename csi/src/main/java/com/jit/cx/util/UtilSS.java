package com.jit.cx.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilSS {
	public static String getNow(){
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
		
	}

}
