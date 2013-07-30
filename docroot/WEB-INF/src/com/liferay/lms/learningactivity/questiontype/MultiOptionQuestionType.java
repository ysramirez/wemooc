package com.liferay.lms.learningactivity.questiontype;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.TestQuestionServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;

public class MultiOptionQuestionType implements QuestionType {

	

	@Override
	public long getTypeId(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "multioption";
	}

	@Override
	public String getTitle(Locale locale) {
		// TODO Auto-generated method stub
		return "multioption";
	}

	@Override
	public String getDescription(Locale locale) {
		// TODO Auto-generated method stub
		return "multioption";
	}

	@Override
	public PortletURL getURLAdd(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long questionId) throws PortalException, SystemException 
	{
	TestQuestionLocalServiceUtil.deleteTestQuestion(questionId);

	}

	@Override
	public boolean correct(ActionRequest actionRequest, long questionId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Document getResults(ActionRequest actionRequest, long questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHtmlFeedback(Document result, long questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportQuestion(PortletDataContext context, Element root,
			long questionId) throws PortalException, SystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void importQuestion(PortletDataContext context, Element entryElement)
			throws SystemException, PortalException {
		// TODO Auto-generated method stub

	}

}
