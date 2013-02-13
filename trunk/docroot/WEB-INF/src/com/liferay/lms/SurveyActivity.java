package com.liferay.lms;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.SurveyResult;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.SurveyResultLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class SurveyActivity
 */
public class SurveyActivity extends MVCPortlet {
 
	public void saveSurvey(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		//com.liferay.lms.model.SurveyActivity surveyActivity = null;
		
		//SurveyActivityLocalServiceUtil.addSurveyActivity(surveyActivity);
	}
		
	public void correct(ActionRequest actionRequest,ActionResponse actionResponse)throws Exception {		

		int score = 100;
		long latId=ParamUtil.getLong(actionRequest,"latId" );
		long actId=ParamUtil.getLong(actionRequest,"actId",0 );
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		Enumeration<String> params=actionRequest.getParameterNames();
		java.util.Hashtable<TestQuestion, TestAnswer> resultados=new java.util.Hashtable<TestQuestion, TestAnswer>();
		
		
		LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);

		//Comprobar qsi el usuario se dejo alguna encuesta abierta
		if (larntry.getEndDate() == null )
		{
		    while(params.hasMoreElements())
			{
				String param=params.nextElement();
				if(param.startsWith("question_"))
				{
					String squestionId=param.substring("question_".length());
					long questionId=Long.parseLong(squestionId);
					new TestQuestionLocalServiceUtil();
					TestQuestion question=TestQuestionLocalServiceUtil.getTestQuestion(questionId);
					
					long answerId=ParamUtil.getLong(actionRequest, param);
					TestAnswer testAnswer=TestAnswerLocalServiceUtil.getTestAnswer(answerId);
					resultados.put(question, testAnswer);
					
					//Guardar la encuesta para las estadisticas.
					SurveyResult surveyResult = SurveyResultLocalServiceUtil.createSurveyResult(CounterLocalServiceUtil.increment(SurveyResult.class.getName()));
					surveyResult.setActId(actId);
					surveyResult.setLatId(latId);
					surveyResult.setQuestionId(questionId);
					surveyResult.setAnswerId(answerId);
					surveyResult.setUserId(themeDisplay.getUserId());
					SurveyResultLocalServiceUtil.addSurveyResult(surveyResult);
				}
			}
			
			//Crear xml para guardar las respuestas
			Element resultadosXML=SAXReaderUtil.createElement("results");
			Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);
			for(TestQuestion question:resultados.keySet())
			{
				TestAnswer answer=resultados.get(question);
				Element questionXML=SAXReaderUtil.createElement("question");
				questionXML.addAttribute("id", Long.toString(question.getQuestionId()));
				Element answerXML=SAXReaderUtil.createElement("answer");
				answerXML.addAttribute("id", Long.toString(answer.getAnswerId()));
				questionXML.add(answerXML);
				resultadosXML.add(questionXML);
			}
			//Guardar los resultados
			//LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
			
			larntry.setResult(score);
			larntry.setTryResultData(resultadosXMLDoc.formattedString());
			larntry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(larntry);
		}
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		actionRequest.setAttribute("resultados", resultados);
		
		actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/view.jsp");
		
	}
	
	public void edit(ActionRequest actionRequest,ActionResponse actionResponse)throws Exception {

		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		if(ParamUtil.getLong(actionRequest, "actId", 0)==0)
		{
			actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/view.jsp");
		}
	}
	
	public void addquestion(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		long actid = ParamUtil.getLong(actionRequest, "actId");
	
		String text = ParamUtil.getString(actionRequest, "text");
		long questionType = ParamUtil.getLong(actionRequest, "qtype");
		
		TestQuestion question = TestQuestionLocalServiceUtil.addQuestion(actid, text, questionType);
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(actid);
		
		actionResponse.setRenderParameter("questionId", Long.toString(question.getQuestionId()));
		actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/admin/editquestion.jsp");
		
		actionRequest.setAttribute("activity", learnact);
		actionRequest.setAttribute("questionId", question.getQuestionId());
		
		actionRequest.setAttribute("primKey", question.getQuestionId());
	}
	
	public void editquestion(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		String text = ParamUtil.getString(actionRequest, "text","");
		long questionType = ParamUtil.getLong(actionRequest, "qtype",0);
		long questionId = ParamUtil.getLong(actionRequest, "questionId",0);
	
		TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
		
		question.setQuestionType(questionType);
		question.setText(text);
		
		TestQuestionLocalServiceUtil.updateTestQuestion(question);
		SessionMessages.add(actionRequest, "question-modified-successfully");
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		
		actionResponse.setRenderParameter("questionId", Long.toString(questionId));
		actionResponse.setRenderParameter("actId", Long.toString(learnact.getActId()));
	
		actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/admin/editquestion.jsp");
	}
	
	public void deleteanswer(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		TestAnswer answer = TestAnswerLocalServiceUtil.getTestAnswer(ParamUtil.getLong(actionRequest, "answerId"));
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(TestQuestionLocalServiceUtil.getTestQuestion(answer.getQuestionId()).getActId());
		TestAnswerLocalServiceUtil.deleteTestAnswer(ParamUtil.getLong(actionRequest, "answerId"));
		SessionMessages.add(actionRequest, "answer-deleted-successfully");
		actionResponse.setRenderParameter("questionId", Long.toString(answer.getQuestionId()));
		actionResponse.setRenderParameter("actId", Long.toString(learnact.getActId()));
	
		actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/admin/editquestion.jsp");
	}

	public void addanswer(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		long questionId = ParamUtil.getLong(actionRequest, "questionId");
		String answers = ParamUtil.getString(actionRequest, "answer");
		boolean correct = ParamUtil.getBoolean(actionRequest, "correct");
		String feedbackCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
		String feedbackNoCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
	
		TestAnswer answer = TestAnswerLocalServiceUtil.addTestAnswer(questionId, answers, feedbackCorrect, feedbackNoCorrect, correct);
		SessionMessages.add(actionRequest, "answer-added-successfully");
		
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(TestQuestionLocalServiceUtil.getTestQuestion(answer.getQuestionId()).getActId());
		actionResponse.setRenderParameter("questionId", Long.toString(answer.getQuestionId()));
		actionResponse.setRenderParameter("actId", Long.toString(learnact.getActId()));

		actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/admin/editquestion.jsp");
	}
		
	public void editanswer(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		long answerId = ParamUtil.getLong(actionRequest, "answerId");
		String answer = ParamUtil.getString(actionRequest, "answer");
		boolean correct = ParamUtil.getBoolean(actionRequest, "correct");
		String feedbackCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
		String feedbackNoCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
	
		TestAnswer testanswer = TestAnswerLocalServiceUtil.getTestAnswer(answerId);
		testanswer.setAnswer(answer);
		testanswer.setIsCorrect(correct);
		testanswer.setFeedbackCorrect(feedbackCorrect);
		testanswer.setFeedbacknocorrect(feedbackNoCorrect);
		
		TestAnswerLocalServiceUtil.updateTestAnswer(testanswer);
		SessionMessages.add(actionRequest, "answer-added-successfully");
		
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(TestQuestionLocalServiceUtil.getTestQuestion(testanswer.getQuestionId()).getActId());
		actionResponse.setRenderParameter("questionId", Long.toString(testanswer.getQuestionId()));
		actionResponse.setRenderParameter("actId", Long.toString(learnact.getActId()));
		actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/admin/editquestion.jsp");
	}
	
	public void deletequestion(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(actionRequest, "questionId"));
		TestQuestionLocalServiceUtil.deleteTestQuestion(ParamUtil.getLong(actionRequest, "questionId"));
		editactivity(actionRequest, actionResponse);
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
	
		actionRequest.setAttribute("activity", learnact);
	
		SessionMessages.add(actionRequest, "question-deleted-successfully");
		actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/edit.jsp");
	}
	
	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "actId");
		LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);

			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}
}
