package com.liferay.lms.learningactivity.questiontype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;

import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class SortableQuestionType extends BaseQuestionType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String inputType = "textarea";

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
		
	public long getTypeId(){
		return 5;
	}
	
	public String getName() {
		return "sortable";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "sortable.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "sortable.description");
	}
	
	public String getURLEdit(){
		return "/html/execactivity/test/admin/editSortableQTAnswers.jsp";
	}
	
	public boolean correct(ActionRequest actionRequest, long questionId){
		String cl = ParamUtil.getString(actionRequest, "question_"+questionId+"_contentlist");
		
		if(cl.equals("")){
			return false;
		}
		
		String[] positions = cl.split("&");
		List<Long> arrayAnswersId = new ArrayList<Long>();
		for(String answerId:positions){
			String[] tmp = answerId.split("=");
			arrayAnswersId.add(Long.parseLong(tmp[1]));
		}
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers = TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		if(!arrayAnswersId.isEmpty()){
			int correctAnswers=0, correctAnswered=0, incorrectAnswered=0;
			int i=0;
			while (i < testAnswers.size()){
				if(isCorrect(testAnswers.get(i))){
					correctAnswers++;
					//System.out.println(i+"  - "+arrayAnswersId.get(i)+" == " + testAnswers.get(i).getAnswerId() );
					if(arrayAnswersId.get(i).equals(testAnswers.get(i).getAnswerId())) correctAnswered++;
				}else if(arrayAnswersId.contains(testAnswers.get(i).getAnswerId())) incorrectAnswered++;
			i++;	
			}
			if(correctAnswers==correctAnswered && incorrectAnswered==0)	return true;
			else return false;
		}
		else{
			return false;
		}
	}
	
	protected boolean isCorrect(TestAnswer testAnswer){
		return (testAnswer!=null)?testAnswer.isIsCorrect():false;
	}
	
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		String view = "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			view += "<div class=\"question\">"+
					"<input type=\"hidden\" name=\""+themeDisplay.getPortletDisplay().getNamespace()+"question\" value=\"" + question.getQuestionId() + "\"/>"+
					"<div class=\"questiontext\">" + question.getText() + "</div>";
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			List<TestAnswer> tmp = ListUtil.copy(testAnswers);
			Collections.shuffle(tmp);
			
			String value="";
			int cont = 0;
			
			view += "<div class=\"question_sortable\"><ul class=\"sortable\" id=\"question_"+question.getQuestionId() + "\" >";
			for(TestAnswer answer:tmp){
				value += "question_"+answer.getQuestionId()+ "["+ (cont++) +"]=" + answer.getAnswerId()+"&";
				view += "<li class=\"ui-sortable-default\" id=\""+answer.getAnswerId()+"\"><div class=\"answer\">"+ answer.getAnswer() + "</div></li> ";
			}
			view += "</ul></div>";
			view += "<input type=hidden id=\""+themeDisplay.getPortletDisplay().getNamespace()+"question_"+question.getQuestionId() +"_contentlist\" name=\""+themeDisplay.getPortletDisplay().getNamespace()+"question_"+question.getQuestionId() +"_contentlist\" value=\""+value+"\"/>";
			view += "</div>";
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		
		return view;
	}
	
	public Element getResults(ActionRequest actionRequest, long questionId){
		
		String answersValue = ParamUtil.getString(actionRequest, "question_"+questionId+"_contentlist");
		//System.out.println(" answers : " + answersValue.toString() );
		
		List<Long> arrayAnswersId = new ArrayList<Long>();
		String answers[] = answersValue.split("&");
		
		for(String answer:answers){
			String values[] = answer.split("=");
			if(values.length > 1){
				arrayAnswersId.add(Long.parseLong(values[1]));
			}
		}
		
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
	
	public String getHtmlFeedback(Document document,long questionId){
		String feedBack = "", answersFeedBack="";
		
		System.out.println(" document : " + document );
		
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			String feedMessage = LanguageUtil.get(Locale.getDefault(),"answer-in-blank") ;
			List<TestAnswer> answersSelected=getAnswerSelected(document, questionId);
			String cssclass="question incorrect";
			List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			int correctAnswers=0, correctAnswered=0, incorrectAnswered=0;
			int i=0;
			while( i < testAnswers.size()){
				String checked="";
				String correct="";
				String showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswer");
				if(isCorrect(testAnswers.get(i))){
					correctAnswers++;
					if(showCorrectAnswer.equals("true")) correct="font_14 color_cuarto negrita";
					if(!answersSelected.isEmpty() && answersSelected.get(i).equals(testAnswers.get(i))){
						correctAnswered++;
						checked="checked='checked'";
						feedMessage=(!LanguageUtil.get(Locale.getDefault(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+testAnswers.get(i).getFeedbackCorrect():testAnswers.get(i).getFeedbackCorrect();
					}
				}else if(!answersSelected.isEmpty() && answersSelected.contains(testAnswers.get(i))){
					incorrectAnswered++;
					checked="checked='checked'";
					feedMessage=(!LanguageUtil.get(Locale.getDefault(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+testAnswers.get(i).getFeedbacknocorrect():testAnswers.get(i).getFeedbacknocorrect();
				}
				
				inputType="hidden";
				answersFeedBack += "<div class=\"answer " + correct + "\">" +
								"<input type=\"" + inputType + "\" name=\"question_" + question.getQuestionId() + "\" " + checked + " value=\"" + testAnswers.get(i).getAnswerId() +"\"  disabled=\"disabled\">" + testAnswers.get(i).getAnswer() +
							"</div>";
				i++;
			}
			
			if(correctAnswers==correctAnswered && incorrectAnswered==0)	cssclass="question correct";
			else cssclass="question incorrect";
			
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
	
	protected List<TestAnswer> getAnswerSelected(Document document,long questionId){
		List<TestAnswer> answerSelected = new ArrayList<TestAnswer>();
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
		return answerSelected;
	}
	
}
