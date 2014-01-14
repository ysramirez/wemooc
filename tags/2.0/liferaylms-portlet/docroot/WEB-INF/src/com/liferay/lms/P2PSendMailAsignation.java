package com.liferay.lms;

import java.util.Locale;

import javax.mail.internet.InternetAddress;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;

public class P2PSendMailAsignation {
	
	public static void sendMail(String emailTo, String nameTo, String content[], long companyId) {
		
		try{
			InternetAddress to = new InternetAddress(emailTo, nameTo);
			
			String portal = LanguageUtil.get(Locale.getDefault(), "p2ptaskactivity.mail.portal");
			String subject= LanguageUtil.get(Locale.getDefault(), "p2ptaskactivity.mail.subject");
			
			String bodyTitle   = LanguageUtil.format(Locale.getDefault(), "p2ptaskactivity.mail.body.title", nameTo);
			String bodyMessage = LanguageUtil.format(Locale.getDefault(), "p2ptaskactivity.mail.body.message", content);
			String bodyEnd     = LanguageUtil.get(Locale.getDefault(), "p2ptaskactivity.mail.body.end");
			
			String body = bodyTitle +"\n\n"+ bodyMessage +"\n\n"+ bodyEnd +"\n\n"+ portal+"\n";
			
			String fromName=PrefsPropsUtil.getString(companyId,PropsKeys.ADMIN_EMAIL_FROM_NAME,"");
			String fromAddress=PrefsPropsUtil.getString(companyId,PropsKeys.ADMIN_EMAIL_FROM_ADDRESS,"");
						
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			
			MailMessage mailm = new MailMessage(from, to, subject, body, true);
			MailServiceUtil.sendEmail(mailm);
		}
		catch(Exception ex)
		{
		}	
	
	}

}
