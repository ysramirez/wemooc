package com.liferay.lms.learningactivity.questiontype;

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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class OptionsQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;
	private String inputType = "radio";

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public long getTypeId(){
		return 0;
	}

	public String getName() {
		return "options";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "options.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "options.description");
	}

	public String getURLEdit(){
		return "/html/execactivity/test/admin/editOptionsQTAnswers.jsp";
	}

	public boolean correct(ActionRequest actionRequest, long questionId){
		long[] answersId= ParamUtil.getLongValues(actionRequest, "question_"+questionId);
		List<Long> arrayAnswersId = new ArrayList<Long>();
		for(long answerId:answersId) arrayAnswersId.add(answerId);
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		int correctAnswers=0, correctAnswered=0, incorrectAnswered=0;
		for(TestAnswer answer:testAnswers){
			if(isCorrect(answer)){
				correctAnswers++;
				if(arrayAnswersId.contains(answer.getAnswerId())) correctAnswered++;
			}else if(arrayAnswersId.contains(answer.getAnswerId())) incorrectAnswered++;
		}

		if(correctAnswers==correctAnswered && incorrectAnswered==0)	return true;
		else return false;
	}

	protected boolean isCorrect(TestAnswer testAnswer){
		return (testAnswer!=null)?testAnswer.isIsCorrect():false;
	}

	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		return getHtml(document, questionId, false, themeDisplay);
	}

	public Element getResults(ActionRequest actionRequest, long questionId){
		long[] answersId= ParamUtil.getLongValues(actionRequest, "question_"+questionId);

		List<Long> arrayAnswersId = new ArrayList<Long>();
		for(long answerId:answersId) arrayAnswersId.add(answerId);

		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));

		long currentQuestionId = ParamUtil.getLong(actionRequest, "currentQuestionId");
		if (currentQuestionId == questionId) {
			questionXML.addAttribute("current", "true");
		}

		for(long answer:arrayAnswersId){
			if(answer >0){
				Element answerXML=SAXReaderUtil.createElement("answer");
				answerXML.addAttribute("id", Long.toString(answer));
				questionXML.add(answerXML);
			}
		}
		return questionXML;
	}

	private String getHtml(Document document, long questionId, boolean feedback, ThemeDisplay themeDisplay){
		String html = "", answersFeedBack="", feedMessage = "", cssclass="";
		String namespace = themeDisplay != null ? themeDisplay.getPortletDisplay().getNamespace() : "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			List<TestAnswer> answersSelected=getAnswersSelected(document, questionId);
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			int correctAnswers=0, correctAnswered=0, incorrectAnswered=0;
			
			if(feedback) feedMessage = LanguageUtil.get(Locale.getDefault(),"answer-in-blank") ;
			
			for(TestAnswer answer:testAnswers){
				String correct="", checked="", showCorrectAnswer="false", disabled ="";
				if(feedback) {
					showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswer");
					disabled = "disabled='disabled'";
				}
				if(isCorrect(answer)){
					correctAnswers++;
					if("true".equals(showCorrectAnswer)) correct="font_14 color_cuarto negrita";
					if(answersSelected.contains(answer)){
						correctAnswered++;
						checked="checked='checked'";
						feedMessage=(!LanguageUtil.get(Locale.getDefault(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+answer.getFeedbackCorrect():answer.getFeedbackCorrect();
					}
				}else if(answersSelected.contains(answer)){
					incorrectAnswered++;
					checked="checked='checked'";
					feedMessage=(!LanguageUtil.get(Locale.getDefault(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+answer.getFeedbacknocorrect():answer.getFeedbacknocorrect();
				}

				answersFeedBack += "<div class=\"answer " + correct + "\">" +
										"<input type=\"" + inputType + "\" name=\""+namespace+"question_" + question.getQuestionId() + "\" " + checked + " value=\"" + answer.getAnswerId() +"\" " + disabled + ">" + answer.getAnswer() +
									"</div>";
			}

			if(feedback){
				if(correctAnswers==correctAnswered && incorrectAnswered==0)	cssclass=" correct";
				else cssclass=" incorrect";
				answersFeedBack = "<div class=\"content_answer\">" + answersFeedBack + "</div><div class=\"questionFeedback\">" + feedMessage + "</div>";
			}

			html += "<div class=\"question" + cssclass + "\">" + 
						"<input type=\"hidden\" name=\""+namespace+"question\" value=\"" + question.getQuestionId() + "\"/>"+
						"<div class=\"questiontext\">" + question.getText() + "</div>" +
						answersFeedBack +
					"</div>";	
			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return html;
	}

	public String getHtmlFeedback(Document document,long questionId){
		return getHtml(document, questionId, true, null);
	}

	protected List<TestAnswer> getAnswersSelected(Document document,long questionId){
		List<TestAnswer> answerSelected = new ArrayList<TestAnswer>();
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
								answerSelected.add(TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(elementElement.attributeValue("id"))));
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
		}
		return answerSelected;
	}

	/**
	 * S�lo hay una respuesta correcta, por lo que �sta debe tener valor 100
	 */
	public void importMoodle(long actId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		//"multichoice" (single),"truefalse"
		Element questiontext=question.element("questiontext");
		String description=questiontext.elementText("text");
		TestQuestion theQuestion=TestQuestionLocalServiceUtil.addQuestion(actId,description,getTypeId());
		for(Element answerElement:question.elements("answer")){
			boolean correct=("100".equals(answerElement.attributeValue("fraction")))? true:false;
			String answer=answerElement.elementText("text");
			String feedback="";
			if(answerElement.element("feedback")!=null && answerElement.element("feedback").element("text")!=null)
				feedback=answerElement.element("feedback").element("text").getText();	 
			testAnswerLocalService.addTestAnswer(theQuestion.getQuestionId(), answer, feedback, feedback, correct);
		}
	}

}
