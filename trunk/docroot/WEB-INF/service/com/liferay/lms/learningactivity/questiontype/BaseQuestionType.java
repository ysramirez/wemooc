package com.liferay.lms.learningactivity.questiontype;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.theme.ThemeDisplay;


public abstract class BaseQuestionType implements QuestionType, Serializable {
	
	protected static Locale locale=null;

	@Override
	public void setLocale(Locale locale){
		this.locale=locale;
	}
	
	@Override
	public long getTypeId(){
		return -1;
	}
	
	@Override
	public String getName(){
		return "";
	}
	
	@Override
	public String getTitle(Locale locale){
		return "";
	}
	
	@Override
	public String getDescription(Locale locale){
		return "";
	}
	
	@Override
	public String getURLEdit(){
		return "";
	}
	
	@Override
	public String getURLBack(){
		return "/html/execactivity/test/admin/editquestions.jsp";
	}
	
	@Override
	public void delete(long questionId) throws PortalException, SystemException{
		List<TestAnswer> answers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		for(TestAnswer answer:answers )
			TestAnswerLocalServiceUtil.deleteTestAnswer(answer.getAnswerId());
		TestQuestionLocalServiceUtil.deleteTestQuestion(questionId);
	}
	
	@Override
	public boolean correct(ActionRequest actionRequest, long questionId){
		return false;
	}
	
	@Override
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay){
		return "";
	}
	
	@Override
	public Element getResults(ActionRequest actionRequest, long questionId){
		return null;
	}
	
	@Override
	public String getHtmlFeedback(Document document,long questionId){
		return "";
	}
	
	@Override
	public void exportQuestionAnswers(PortletDataContext context, Element root, long questionId) throws PortalException, SystemException{}
	
	@Override
	public void importQuestionAnswers(PortletDataContext context, Element entryElement, long questionId) throws SystemException, PortalException{}
	
	protected String getEntryPath(PortletDataContext context, TestAnswer answer) {
		
		StringBundler sb = new StringBundler(4);
		sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/activities/questions/answers");
		sb.append(answer.getAnswerId());
		sb.append(".xml");
		return sb.toString();
	}
	
	public void importMoodle(long questionId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {}
	
}
