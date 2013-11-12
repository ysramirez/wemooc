package com.tls.liferaylms.mail;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.liferaylms.mail.model.MailTemplate;
import com.tls.liferaylms.mail.service.MailTemplateLocalServiceUtil;

/**
 * Portlet implementation class ManageTemplates
 */
public class ManageTemplates extends MVCPortlet {
	
	private static Log _log = LogFactoryUtil.getLog(ManageTemplates.class);

	public void saveTemplate(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String subject = ParamUtil.getString(actionRequest, "subject", "");
		String body = ParamUtil.getString(actionRequest, "message", "");
		boolean editing = ParamUtil.getBoolean(actionRequest, "editing", true);
		String idTemplate = ParamUtil.getString(actionRequest, "idTemplate", "");
			
		if(editing)
		{
			MailTemplate template = MailTemplateLocalServiceUtil.getMailTemplate(Long.parseLong(idTemplate));
			template.setSubject(subject);
			template.setBody(changeToURL(body, themeDisplay.getURLPortal()));
			template.setGroupId(themeDisplay.getScopeGroupId());
			template.setCompanyId(themeDisplay.getCompanyId());
			template.setUserId(themeDisplay.getUserId());
			MailTemplateLocalServiceUtil.updateMailTemplate(template);
			
			if(_log.isInfoEnabled())
				_log.trace("ManageTemplates: updateMailTemplate " + template.getSubject());
		}	
		else
		{
			MailTemplate mailTemplate = MailTemplateLocalServiceUtil.createMailTemplate(CounterLocalServiceUtil.increment(MailTemplate.class.getName()));
			mailTemplate.setSubject(subject);
			mailTemplate.setBody(changeToURL(body, themeDisplay.getURLPortal()));
			mailTemplate.setGroupId(themeDisplay.getScopeGroupId());
			mailTemplate.setCompanyId(themeDisplay.getCompanyId());
			mailTemplate.setUserId(themeDisplay.getUserId());
			MailTemplateLocalServiceUtil.addMailTemplate(mailTemplate);
			
			if(_log.isInfoEnabled())
				_log.trace("ManageTemplates: addMailTemplate " + mailTemplate.getSubject());
		}
	}
	
	public void deleteTemplate(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		String idTemplate = ParamUtil.getString(actionRequest, "templateId", "");

		if(!idTemplate.equals("")){
			MailTemplateLocalServiceUtil.deleteMailTemplate(Long.parseLong(idTemplate));
		}else{
			SessionErrors.add(actionRequest, "error-delete");
		}
	}
	
	private String changeToURL(String text, String url){
		String res ="";

		//Para imágenes
		res = text.replaceAll("src=\"/image/image_gallery", "src=\""+url+"/image/image_gallery");
		
		return res;
	}
	
}
