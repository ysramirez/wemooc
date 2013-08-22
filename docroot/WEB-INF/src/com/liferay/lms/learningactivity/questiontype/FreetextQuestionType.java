package com.liferay.lms.learningactivity.questiontype;

import java.text.Collator;
import java.util.ArrayList;
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

public class FreetextQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;

	public void setLocale(Locale locale){
		super.setLocale(locale);
	}
	
	public long getTypeId(){
		return 2;
	}
	
	public String getName() {
		return "freetext";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "freetext.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "freetext.description");
	}
	
	public String getURLEdit(){
		return "/html/execactivity/test/admin/editFreetextQTAnswers.jsp";
	}
	
	public boolean correct(ActionRequest actionRequest, long questionId){
		String answer= ParamUtil.getString(actionRequest, "question_"+questionId, "");
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		if(testAnswers!=null && testAnswers.size()>0){
			TestAnswer solution = testAnswers.get(0);
			return isCorrect(solution, answer);
		}
		return false;
	}
	
	protected boolean isCorrect(TestAnswer solution, String answer){
		Collator c = Collator.getInstance();
		c.setStrength(Collator.PRIMARY);
		if(c.compare(solution.getAnswer(), answer) == 0) return true;
		return false;
	}
	
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay){
		String view = "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			view += "<div class=\"question\">"+
						"<input type=\"hidden\" name=\""+themeDisplay.getPortletDisplay().getNamespace()+"question\" value=\"" + question.getQuestionId() + "\"/>"+
						"<div class=\"questiontext\">" + question.getText() + "</div>"+
						"<div class=\"answer\"><textarea rows=\"4\" cols=\"60\" maxlength=\"1000\" name=\""+themeDisplay.getPortletDisplay().getNamespace()+"question_" + question.getQuestionId() + "\" label=\"answer\"></textarea></div>"+
					"</div>";
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		
		return view;
	}
	
	public Element getResults(ActionRequest actionRequest, long questionId){
		String answer= ParamUtil.getString(actionRequest, "question_"+questionId, "");
    	
		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));
		
		Element answerXML=SAXReaderUtil.createElement("answer");
		answerXML.addText(answer);
		questionXML.add(answerXML);
		
		return questionXML;
	}
	
	public String getHtmlFeedback(Document document,long questionId){
		String feedBack = "", answersFeedBack="";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			String feedMessage = LanguageUtil.get(Locale.getDefault(),"answer-in-blank") ;
			String answer="";
			Iterator<Element> nodeItr = document.getRootElement().elementIterator();
			while(nodeItr.hasNext()) {
				Element element = nodeItr.next();
		         if("question".equals(element.getName()) && questionId == Long.valueOf(element.attributeValue("id"))){
		        	 Iterator<Element> elementItr = element.elementIterator();
		        	 if(elementItr.hasNext()) {
		        		 Element elementElement = elementItr.next();
		        		 if("answer".equals(elementElement.getName())) {
		        			 try {
								answer = elementElement.getText();
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
		        		 }
		        	 }
		         }
		    }	
			String cssclass="question incorrect";
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			if(testAnswers!=null && testAnswers.size()>0){//el profesor puso alguna solución para corrección automática
				TestAnswer solution = testAnswers.get(0);
				String showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswer");
				if(isCorrect(solution, answer)){
						feedMessage=solution.getFeedbackCorrect();
						cssclass="question correct";
				}else {
					feedMessage=solution.getFeedbacknocorrect();
					cssclass="question incorrect";
				}
				
				answersFeedBack = answer;
				if("true".equals(showCorrectAnswer)) answersFeedBack += "<br/>" +"<div class=\"answer font_14 color_cuarto negrita\">" +
																			solution.getAnswer() +
																		"</div>";
			}else{//el profesor lo corregirá manualmente
				answersFeedBack = answer;
				cssclass="question";
				feedMessage = (locale!=null)?LanguageUtil.get(locale, "manually-correction"):"Se evaluará manualmente por el profesor";
			}
			
			feedBack += "<div class=\"" + cssclass + "\">" + 
							"<div class=\"questiontext\">" + question.getText() + "</div>" +
							"<div class=\"content_answer\">" +
								answersFeedBack +
							"</div>" +
							"<div class=\"questionFeedback\">" + feedMessage + "</div>" +
						"</div>";	
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return feedBack;
	}
	
	public void importMoodle(long questionId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		//"essay","numerical","shortanswer"
		if(!"essay".equals(question.attributeValue("type"))){//los essay en moodle nunca tienen respuesta
			for(Element answerElement:question.elements("answer")){
				boolean correct=("100".equals(answerElement.attributeValue("fraction")))? true:false;
				String answer=answerElement.elementText("text");
				String feedback="";
				if(answerElement.element("feedback")!=null && answerElement.element("feedback").element("text")!=null)
				 feedback=answerElement.element("feedback").element("text").getText();	 
				testAnswerLocalService.addTestAnswer(questionId, answer, feedback, feedback, correct);
				return;//porque inicialmente solo aceptamos una respuesta
			}
		}
	}
	
}
