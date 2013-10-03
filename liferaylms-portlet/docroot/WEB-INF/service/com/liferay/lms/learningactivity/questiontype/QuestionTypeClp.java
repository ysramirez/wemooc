package com.liferay.lms.learningactivity.questiontype;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityClp;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;

public class QuestionTypeClp implements QuestionType {

	private ClassLoaderProxy clp;
	
	public QuestionTypeClp(ClassLoaderProxy clp) {
		this.clp = clp;
	}
		
	public java.lang.Object invokeMethod(java.lang.String name,
			java.lang.String[] parameterTypes, java.lang.Object[] arguments)
			throws java.lang.Throwable {
		throw new UnsupportedOperationException();
	}
	
	public void setLocale(Locale locale){
		//?
	}
	
	public long getTypeId(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getTypeId",	new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Long)returnObj).longValue();
	}
	
	public String getName(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getName", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getTitle(Locale locale){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getTitle", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getDescription(Locale locale){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getDescription", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getAnswerEditingAdvise(Locale locale){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getAnswerEditingAdvise", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getURLEdit(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getURLEdit", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public String getURLBack(){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getURLBack", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public void delete(long questionId) throws PortalException, SystemException{
		/*try {
			ClassLoader classLoader = clp.getClassLoader();
			Class questionTypeClass = Class.forName(QuestionType.class.getName(),true, classLoader);
			
			Method deleteResourcesMethod = questionTypeClass.getMethod("delete");    
			Object questionObj = translateQuestion(learningActivity);
			clp.invoke(new MethodHandler(deleteResourcesMethod, actionRequest, actionResponse, learningActivityObj));
			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(learningActivityObj, clp.getClassLoader());
			learningActivity.setModelAttributes((Map<String, Object>) classLoaderProxy.invoke("getModelAttributes", new Object[]{}));
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);
			
			if (t instanceof com.liferay.portal.kernel.exception.PortalException) {
				throw (com.liferay.portal.kernel.exception.PortalException)t;
			}
			
			if (t instanceof IOException) {
				throw (IOException)t;
			}
			
			if (t instanceof DocumentException) {
				throw (DocumentException)t;
			}
			
			if (t instanceof com.liferay.portal.kernel.exception.SystemException) {
				throw (com.liferay.portal.kernel.exception.SystemException)t;
			}

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}*/
	}
	
	public boolean correct(ActionRequest actionRequest, long questionId){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("correct", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Boolean)returnObj);
	}
	
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getHtmlView", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public Element getResults(ActionRequest actionRequest, long questionId){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getResults", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((Element)returnObj);
	}
	
	public String getHtmlFeedback(Document document,long questionId){
		Object returnObj = null;

		try {
			returnObj = clp.invoke("getHtmlFeedback", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

		return ((String)returnObj);
	}
	
	public void exportQuestionAnswers(PortletDataContext context, Element root, long questionId, LearningActivity activity) throws PortalException, SystemException{
		Object returnObj = null;

		try {
			returnObj = clp.invoke("exportQuestion", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

	}
	
	public void importQuestionAnswers(PortletDataContext context, Element entryElement, long questionId, long userId, ServiceContext serviceContext) throws SystemException, PortalException{
		Object returnObj = null;

		try {
			returnObj = clp.invoke("importQuestion", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}

	}
	
	public void importMoodle(long actId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		Object returnObj = null;

		try {
			returnObj = clp.invoke("importMoodle", new Object[] {});
		}
		catch (Throwable t) {
			t = ClpSerializer.translateThrowable(t);

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException(t.getClass().getName() +
					" is not a valid exception");
			}
		}
	}

}
