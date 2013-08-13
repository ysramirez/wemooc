package com.liferay.lms.learningactivity.questiontype;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;

import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class OptionsQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;

	public long getTypeId(){
		return 0;
	}

	public String getName() {
		return "options";
	}

	public String getTitle(Locale locale) {
		return "options.title";
	}

	public String getDescription(Locale locale) {
		return "options.description";
	}
	
	public String getURLEdit(){
		return "/html/execactivity/test/admin/editOptionsQTAnswers.jsp";
	}
	
	public void exportQuestionAnswers(PortletDataContext context, Element root, long questionId) throws PortalException, SystemException{
		List<TestAnswer> answers=TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		for(TestAnswer answer:answers){
			String patha = getEntryPath(context, answer);
			Element entryElementa= root.addElement("questionanswer");
			entryElementa.addAttribute("path", patha);
			context.addZipEntry(patha, answer);
		}
	}
	
	public void importQuestionAnswers(PortletDataContext context, Element entryElement, long questionId) throws SystemException, PortalException{
		String patha = entryElement.attributeValue("path");
		TestAnswer answer=(TestAnswer)context.getZipEntryAsObject(patha);
		TestAnswerLocalServiceUtil.addTestAnswer(questionId, answer.getAnswer(), answer.getFeedbackCorrect(), answer.getFeedbacknocorrect(), answer.isIsCorrect());
	}
	
	public boolean correct(ActionRequest actionRequest, long questionId){
		long answerId= ParamUtil.getLong(actionRequest, "question_"+questionId);
		boolean correct = false;
		try {
			if(answerId >0){
				TestAnswer testAnswer = TestAnswerLocalServiceUtil.getTestAnswer(answerId);
				correct = (testAnswer!=null)?testAnswer.isIsCorrect():false;
			}
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return correct;
	}
	
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay){
		String view = "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			view += "<div class=\"question\">"+
					"<input type=\"hidden\" name=\""+themeDisplay.getPortletDisplay().getNamespace()+"question\" value=\"" + question.getQuestionId() + "\"/>"+
					"<div class=\"questiontext\">" + question.getText() + "</div>";
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			for(TestAnswer answer:testAnswers)
				view += "<div class=\"answer\"><input type=\"radio\" name=\""+themeDisplay.getPortletDisplay().getNamespace()+"question_" + question.getQuestionId() + "\" value=\"" + answer.getAnswerId() + "\" >" + answer.getAnswer() + "</div>";
			view += "</div>";
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return view;
	}
	
	public Element getResults(ActionRequest actionRequest, long questionId){
		long answerId= ParamUtil.getLong(actionRequest, "question_"+questionId);
    	Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));
		if(answerId >0){
			Element answerXML=SAXReaderUtil.createElement("answer");
			answerXML.addAttribute("id", Long.toString(answerId));
			questionXML.add(answerXML);
		}
		return questionXML;
	}
	
	public String getHtmlFeedback(Document document,long questionId){
		String feedBack = "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			String feedMessage = LanguageUtil.get(Locale.getDefault(),"answer-in-blank") ;
			TestAnswer answerSelected=getAnswerSelected(document, questionId);
			String cssclass="question incorrect";
			if(answerSelected != null){
		 		feedMessage=answerSelected.getFeedbacknocorrect();
				cssclass="question incorrect";
				if(answerSelected.isIsCorrect()){
			 		cssclass="question correct";
			 		feedMessage=answerSelected.getFeedbackCorrect();
				}
			}
			feedBack += "<div class=\"" + cssclass + "\">" + 
						"<div class=\"questiontext\">" + question.getText() + "</div>" +
						"<div class=\"content_answer\">";
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			for(TestAnswer answer:testAnswers){
				String checked="";
				if(answerSelected!=null && answer.getAnswerId()==answerSelected.getAnswerId()) checked="checked='checked'";
			
				String correct="";
				String showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswer");
				if(showCorrectAnswer.equals("true"))
					if(answer.isIsCorrect()) correct="font_14 color_cuarto negrita";
		
				feedBack += "<div class=\"answer " + correct + "\">" +
								"<input type=\"radio\" name=\"question_" + question.getQuestionId() + "\" " + checked + " value=\"" + answer.getAnswerId() +"\"  disabled=\"disabled\">" + answer.getAnswer() +
							"</div>";
			}
			
			feedBack += "</div>" +
					"<div class=\"questionFeedback\">" + feedMessage + "</div>" +
				"</div>";	
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return feedBack;
	}
	
	private TestAnswer getAnswerSelected(Document document,long questionId){
		TestAnswer answerSelected = null;
		Iterator<Element> nodeItr = document.getRootElement().elementIterator();
		while(nodeItr.hasNext()) {
			Element element = nodeItr.next();
	         if("question".equals(element.getName()) && questionId == Long.valueOf(element.attributeValue("id"))){
	        	 Iterator<Element> elementItr = element.elementIterator();
	        	 if(elementItr.hasNext()) {
	        		 Element elementElement = elementItr.next();
	        		 if("answer".equals(elementElement.getName())) {
	        			 try {
							answerSelected = TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(elementElement.attributeValue("id")));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (PortalException e) {
							e.printStackTrace();
						} catch (SystemException e) {
							e.printStackTrace();
						}
	        		 }
	        	 }
	         }
	    }	
		return answerSelected;
	}
	
	public void importMoodle(long questionId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		for(Element answerElement:question.elements("answer")){
			boolean correct=("100".equals(answerElement.attributeValue("fraction")))? true:false;
			String answer=answerElement.elementText("text");
			String feedback="";
			if(answerElement.element("feedback")!=null && answerElement.element("feedback").element("text")!=null)
			 feedback=answerElement.element("feedback").element("text").getText();	 
			testAnswerLocalService.addTestAnswer(questionId, answer, feedback, feedback, correct);
		}
	}
	
	
}
