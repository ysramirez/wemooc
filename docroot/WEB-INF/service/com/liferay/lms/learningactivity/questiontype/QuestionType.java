package com.liferay.lms.learningactivity.questiontype;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import com.liferay.lms.model.Module;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.InvokableService;
import com.liferay.portal.theme.ThemeDisplay;

public interface QuestionType 
{
	public long getTypeId();
	public String getName();
	public String getTitle(Locale locale);
	public String getDescription(Locale locale);
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception;
	
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception;
	public void delete(long questionId) throws PortalException, SystemException;
	public boolean correct(ActionRequest actionRequest, long questionId);
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay);
	public Document getResults(ActionRequest actionRequest, long questionId);
	public String getHtmlFeedback(Document result,long questionId);
	public void exportQuestion(PortletDataContext context, Element root, long questionId) throws PortalException, SystemException;
	public void importQuestion(PortletDataContext context, Element entryElement) throws SystemException, PortalException;

}
