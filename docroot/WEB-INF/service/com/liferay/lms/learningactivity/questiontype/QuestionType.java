package com.liferay.lms.learningactivity.questiontype;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.Module;
import com.liferay.lms.service.TestAnswerLocalService;
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
	public void setLocale(Locale locale);
	public long getTypeId();
	public String getName();
	public String getTitle(Locale locale);
	public String getDescription(Locale locale);
	public String getURLEdit();
	public String getURLBack();
	public void delete(long questionId) throws PortalException, SystemException;
	public boolean correct(ActionRequest actionRequest, long questionId);
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay);
	public Element getResults(ActionRequest actionRequest, long questionId);
	public String getHtmlFeedback(Document document,long questionId);
	public void exportQuestionAnswers(PortletDataContext context, Element root, long questionId) throws PortalException, SystemException;
	public void importQuestionAnswers(PortletDataContext context, Element entryElement, long questionId) throws SystemException, PortalException;
	public void importMoodle(long questionId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException;

}
