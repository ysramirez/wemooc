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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class DraganddropQuestionType extends BaseQuestionType {

	private static final long serialVersionUID = 1L;

	public long getTypeId(){
		return 4;
	}

	public String getName() {
		return "draganddrop";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "draganddrop.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "draganddrop.description");
	}

	public String getAnswerEditingAdvise(Locale locale) {
		return LanguageUtil.get(locale, "draganddrop.advise");
	}

	public String getURLEdit(){
		return "/html/execactivity/test/admin/editOptionsQTAnswers.jsp";
	}

	public boolean correct(ActionRequest actionRequest, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers.addAll(TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId));
		} catch (SystemException e) {
			e.printStackTrace();
		}

		//me quedo solo con un array con la solucion
		for(java.util.ListIterator<TestAnswer> itr = testAnswers.listIterator(); itr.hasNext();){
			TestAnswer tanswer = itr.next();
			if(!tanswer.isIsCorrect()) itr.remove();
		}

		List<Long> answersId = new ArrayList<Long>();
		for(int i=0;i<testAnswers.size();i++){
			answersId.add(ParamUtil.getLong(actionRequest, "question_"+questionId+"_"+i+"hidden"));
		}

		if(!isCorrect(answersId, testAnswers)) return false;
		return true;
	}

	protected boolean isCorrect(List<Long> answersId, List<TestAnswer> testAnswers){
		for(int i=0;i<testAnswers.size();i++) 
			if(answersId.get(i) == -1 || answersId.get(i) != testAnswers.get(i).getAnswerId())	return false;
		return true;
	}

	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		return getHtml(document, questionId, false, themeDisplay);
	}

	public Element getResults(ActionRequest actionRequest, long questionId){
		List<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
		try {
			testAnswers.addAll(TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(questionId));
		} catch (SystemException e) {
			e.printStackTrace();
		}

		//me quedo solo con un array con la solucion
		for(java.util.ListIterator<TestAnswer> itr = testAnswers.listIterator(); itr.hasNext();){
			TestAnswer tanswer = itr.next();
			if(!tanswer.isIsCorrect()) itr.remove();
		}

		List<Long> answersId = new ArrayList<Long>();
		for(int i=0;i<testAnswers.size();i++){
			answersId.add(ParamUtil.getLong(actionRequest, "question_"+questionId+"_"+i+"hidden"));
		}

		Element questionXML=SAXReaderUtil.createElement("question");
		questionXML.addAttribute("id", Long.toString(questionId));
		
		long currentQuestionId = ParamUtil.getLong(actionRequest, "currentQuestionId");
		if (currentQuestionId == questionId) {
			questionXML.addAttribute("current", "true");
		}

		for(long answer:answersId){
			Element answerXML=SAXReaderUtil.createElement("answer");
			answerXML.addAttribute("id", Long.toString(answer));
			questionXML.add(answerXML);
		}
		return questionXML;
	}

	private String getHtml(Document document, long questionId, boolean feedback, ThemeDisplay themeDisplay){
		String html = "", leftCol="", rightCol = "", feedMessage="", feedsol="", showCorrectAnswer="false";
		String namespace = themeDisplay != null ? themeDisplay.getPortletDisplay().getNamespace() : "";
		try {
			TestQuestion question = TestQuestionLocalServiceUtil.fetchTestQuestion(questionId);
			//String feedMessage = LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank") ;
			List<TestAnswer> answersSelected=getAnswersSelected(document, questionId);
			List<TestAnswer> tA= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			List<Long>answersSelectedIds = new ArrayList<Long>();
			List<TestAnswer> sols = new ArrayList<TestAnswer>();

			//array con todas las respuestas posibles desordenadas
			ArrayList<TestAnswer> testAnswers = new ArrayList<TestAnswer>();
			testAnswers.addAll(tA);
			Collections.shuffle(testAnswers);

			//la lista tA la reutilizo como lista con la solucion
			for(TestAnswer an:tA){
				if(an.isIsCorrect()) sols.add(an);
			}

			//Si el alumno ha pasado por la pregunta alguna vez, eliminamos de testAnswers las que el alumno puso en answersSelected
			if(answersSelected != null && answersSelected.size() == sols.size()){ 
				for(int k=0; k<answersSelected.size(); k++){
					if(answersSelected.get(k) != null){
						testAnswers.remove(answersSelected.get(k));
						answersSelectedIds.add(answersSelected.get(k).getAnswerId());
					}else
						answersSelectedIds.add(new Long(-1));
				}
				//sino, creamos el array de respuestas con el tamano que tiene q tener para pintar las cajas grises vacias.
			}else{
				for(int k=0; k<sols.size(); k++) answersSelectedIds.add(new Long(-1));
			}
			String correctionClass = "";
			if(feedback){
				feedMessage = LanguageUtil.get(locale,"answer-in-blank");
				showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(question.getActId(), "showCorrectAnswer");
				if(isCorrect(answersSelectedIds, sols)) correctionClass = " correct";
				else correctionClass = " incorrect";
			}

			html += "<div id=\"id"+questionId+"\" class=\"question draganddrop"+correctionClass + " questiontype_" + getName() + " questiontype_" + getTypeId() +"\">"+
						"<input type=\"hidden\" name=\""+namespace+"question\" value=\"" + question.getQuestionId() + "\"/>"+
						"<div class=\"questiontext\">" + question.getText() + "</div>";

			//en la columna de la izq el contenido de testAnswers, con las que el estudiante dejo sin arrastrar
			leftCol +=	"<div class=\"items\">";
			for(TestAnswer answer:testAnswers){
				leftCol += "<div id=\""+answer.getAnswerId()+"\" class=\"ui-corner-all\">"+answer.getAnswer()+"</div>";
			}
			leftCol +=	"</div>";

			//en la columna de la derecha el contenido de answersSelected, con las respuestas que dio el estudiante
			rightCol +=	"<div class=\"drop\">";
			for(int i=0;i<answersSelectedIds.size();i++){
				int aux = i+1;
				long value = -1;
				String text = LanguageUtil.format(locale, "drop", aux);
				if(answersSelectedIds.get(i)!= -1 && answersSelected.get(i) != null){
					value = answersSelected.get(i).getAnswerId();
					text = answersSelected.get(i).getAnswer();
				}
				
				if(feedback){
					if(answersSelectedIds.get(i) == sols.get(i).getAnswerId()) {
						feedMessage = (!LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+sols.get(i).getFeedbackCorrect():sols.get(i).getFeedbackCorrect();
					}
					else {
						feedMessage = (!LanguageUtil.get(themeDisplay.getLocale(),"answer-in-blank").equals(feedMessage))?feedMessage+"<br/>"+sols.get(i).getFeedbacknocorrect():sols.get(i).getFeedbacknocorrect();
					}
					if("true".equals(showCorrectAnswer)) {
						feedsol = "<div class=\" font_14 color_cuarto negrita\">" + sols.get(i).getAnswer() + "</div>";
					}
				}
				
				rightCol +=	"<input type=\"hidden\" name=\""+namespace+"question_" + question.getQuestionId() + "_" + i +"hidden\"  value=\""+value+"\"/>" +
						"<div name=\""+namespace+"question_" + question.getQuestionId() + "_" + i +"\" id=\"Drop"+aux +"\" class=\"drop-containers ui-corner-all background "+(value == -1 ? "base" : "occupied")+"\">"+
						(value == -1 ? "" : "<div id=\""+value+"\" class=\"ui-corner-all ui-draggable\">") +
						text +
						(value == -1 ? "" : "</div>") +
						"</div>"
						+ feedsol;
			}
			rightCol += "</div>";

			if(feedback) {
				html += "<div class=\"content_answer\">" + leftCol + rightCol + "</div>";
				if (!"".equals(feedMessage)) {
					html += "<div class=\"questionFeedback\">" + feedMessage + "</div>";
				}
			}
			else html += leftCol + rightCol;
			html+=	"</div>";

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
								long id = Long.valueOf(elementElement.attributeValue("id"));
								if(id != -1)
									answerSelected.add(TestAnswerLocalServiceUtil.getTestAnswer(id));
								else answerSelected.add(null);
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

}
