package com.tls.liferaylms.test.util;

public class Sleep {
    public static void sleep(long time){
    	try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
