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
import com.liferay.portal.kernel.util.Validator;
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

	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		return getHtml(document, questionId, false);
	}

	public Element getResults(ActionRequest actionRequest, long questionId){
		String answer= ParamUtil.getString(actionRequest, "question_"+questionId, "");

		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));

		long currentQuestionId = ParamUtil.getLong(actionRequest, "currentQuestionId");
		if (currentQuestionId == questionId) {
			questionXML.addAttribute("current", "true");
		}

		Element answerXML=SAXReaderUtil.createElement("answer");
		answerXML.addText(answer);
		questionXML.add(answerXML);

		return questionXML;
	}

	private String getHtml(Document document, long questionId, boolean feedback){
		String feedBack = "", answersFeedBack= "", cssclass = "", showCorrectAnswer = "false";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			String feedMessage = LanguageUtil.get(Locale.getDefault(),"answer-in-blank") ;
			String answer=getAnswersSelected(document, questionId);
	
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			if(testAnswers!=null && testAnswers.size()>0){//el profesor puso alguna soluci�n para correcci�n autom�tica
				TestAnswer solution = testAnswers.get(0);
				if(feedback){
					showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswer");
					if(isCorrect(solution, answer)){
						feedMessage=solution.getFeedbackCorrect();
						cssclass=" correct";
					}else {
						feedMessage=solution.getFeedbacknocorrect();
						cssclass=" incorrect";
					}
				}

				answersFeedBack = answer;
				if("true".equals(showCorrectAnswer)) answersFeedBack += "<br/>" +"<div class=\"answer font_14 color_cuarto negrita\">" +
																						solution.getAnswer() +
																				"</div>";
			}else{//el profesor lo corregira manualmente
				answersFeedBack = answer;
				if(feedback) feedMessage = (locale!=null)?LanguageUtil.get(locale, "manually-correction"):"A evaluar manualmente por el profesor";
			}

			if(feedback) answersFeedBack = "<div class=\"content_answer\">" + answersFeedBack + "</div><div class=\"questionFeedback\">" + feedMessage + "</div>";

			feedBack += "<div class=\"question" + cssclass + "\">" + 
					"<input type=\"hidden\" name=\"question\" value=\"" + question.getQuestionId() + "\"/>"+
					"<div class=\"questiontext\">" + question.getText() + "</div>" +
					answersFeedBack +
					"</div>";	
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return feedBack;
	}

	public String getHtmlFeedback(Document document,long questionId){
		return getHtml(document, questionId, true);
	}

	protected String getAnswersSelected(Document document,long questionId){
		String answerGiven = "";
		if(document != null){
			Iterator<Element> nodeItr = document.getRootElement().elementIterator();
			while(nodeItr.hasNext()) {
				Element element = nodeItr.next();
				if("question".equals(element.getName()) && questionId == Long.valueOf(element.attributeValue("id"))){
					Iterator<Element> elementItr = element.elementIterator();
					while(elementItr.hasNext()) {
						Element elementElement = elementItr.next();
						if("answer".equals(elementElement.getName())) {
							try {
								answerGiven = elementElement.getText();
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}	
		}
		return answerGiven;
	}

	public void importMoodle(long actId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		//"essay","numerical","shortanswer"
		Element questiontext=question.element("questiontext");
		String description=questiontext.elementText("text");
		TestQuestion theQuestion=TestQuestionLocalServiceUtil.addQuestion(actId,description,getTypeId());
		if(!"essay".equals(question.attributeValue("type"))){//los essay en moodle nunca tienen respuesta
			for(Element answerElement:question.elements("answer")){
				boolean correct=("100".equals(answerElement.attributeValue("fraction")))? true:false;
				String answer=answerElement.elementText("text");
				String feedback="";
				if(answerElement.element("feedback")!=null && answerElement.element("feedback").element("text")!=null)
					feedback=answerElement.element("feedback").element("text").getText();	 
				testAnswerLocalService.addTestAnswer(theQuestion.getQuestionId(), answer, feedback, feedback, correct);
				return;//porque inicialmente solo aceptamos una respuesta
			}
		}
	}

}
