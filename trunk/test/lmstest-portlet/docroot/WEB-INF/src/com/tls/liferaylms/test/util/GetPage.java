package com.tls.liferaylms.test.util;


import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class GetPage {
	private static Log log = LogFactoryUtil.getLog(GetPage.class);

	public static void getPage(WebDriver driver,String path,String source){
		Date date = new Date();

		String page = null;
		if(source.length()>0){
			if(source.substring(0, 1).equals("/")){
				if(path.length()>0){
					if(path.substring(path.length()-1, path.length()).equals("/")){
						page = path.substring(0, path.length()-1)+source;
					}
				}
			}else{
				page = path+source;
			}
		}else{
			page = source;
		}
		Context.setPage(page);
		driver.get(page);
		String current = driver.getCurrentUrl();

		if(log.isInfoEnabled())log.info("Get::"+page);
		if(!page.equals(current)){
			if(log.isInfoEnabled())log.info("redirect:"+current);
		}else{
			Date dateEnd = new Date();
			if(log.isInfoEnabled())log.info("time::"+(dateEnd.getTime()-date.getTime()));
		}
	}
	
}
