package com.liferay.lms.portlet.inscriptioncommunity;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;


/** Portlet implementation class CommunityInscription */
public class CommunityInscription extends MVCPortlet {

	public void inscribir(ActionRequest request, ActionResponse response) throws Exception{

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		if (!themeDisplay.isSignedIn()) {return;}

		long[] groupId = new long[1];
    	groupId[0] = themeDisplay.getScopeGroupId();	
    	
    	GroupLocalServiceUtil.addUserGroups(themeDisplay.getUserId(), groupId);
    	
    	// Enviar un email al usuario cuando se inscribe al curso.
    	
    	String emailTo = themeDisplay.getUser().getEmailAddress();
    	String nameTo = themeDisplay.getUser().getFullName();
    	InternetAddress to = new InternetAddress(emailTo, nameTo);
    	
    	String fromName = PrefsPropsUtil.getString(themeDisplay.getCompanyId(),
				PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(themeDisplay.getCompanyId(),
				PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		
		InternetAddress from = new InternetAddress(fromAddress, fromName);
		
		String portal = themeDisplay.getCompany().getName();
		String curso = themeDisplay.getCompany().getGroup().getName();
		String user = themeDisplay.getUser().getFullName();
    	String subject = PrefsPropsUtil.getString(themeDisplay.getCompanyId(),"inscriptioncommunity.mail.inscripcion.subject");
    	String body = PrefsPropsUtil.getString(themeDisplay.getCompanyId(),"inscriptioncommunity.mail.inscripcion.body");
    	String url = themeDisplay.getURLPortal();
    	String urlcourse = themeDisplay.getURLPortal()+"/web"+themeDisplay.getCompany().getGroup().getFriendlyURL();
    	
    	if(body!=null &&!body.equals("")){
	    	
    		body = createMessage(body, portal, curso, user, fromName ,url,urlcourse);
	    	

//		    	System.out.println("");
//				System.out.println("----------------------");
//				System.out.println(" from: "+from.getAddress());
//				System.out.println(" to: "+to.getAddress());
//				System.out.println(" subject: "+subject);
//				System.out.println(" body: "+body);
//				System.out.println("----------------------");
//				System.out.println("");
    		
	    	
			try{
				MailMessage mailm = new MailMessage(from, to, subject, body, true);
				MailServiceUtil.sendEmail(mailm);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}		
    	}
	}
	
	private String createMessage(String text, String portal, String community, String student, String teacher, String url, String urlcourse){
		String res = "";

		res = text.replace("[@portal]", 	portal);
		res = res.replace ("[@course]", 	community);
		res = res.replace ("[@student]", 	student);
		res = res.replace ("[@teacher]", 	teacher);
		res = res.replace ("[@url]", 		"<a href=\""+url+"\">"+portal+"</a>");
		res = res.replace ("[@urlcourse]", 	"<a href=\""+urlcourse+"\">"+community+"</a>");
				
		return res;
	}
	
	public void desinscribir(ActionRequest request, ActionResponse response) throws Exception{

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		if (!themeDisplay.isSignedIn()) {return;}

		long[] groupId = new long[1];
    	groupId[0] = themeDisplay.getScopeGroupId();						
		GroupLocalServiceUtil.unsetUserGroups(themeDisplay.getUserId(), groupId);
				
	}
}
