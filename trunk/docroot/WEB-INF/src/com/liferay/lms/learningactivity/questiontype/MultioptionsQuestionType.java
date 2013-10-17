package com.liferay.lms.learningactivity.questiontype;

import java.util.Locale;

import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.TestAnswerLocalService;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.theme.ThemeDisplay;

/**
 * Heredado todo de OptionsQuestionType (caso base de esta clase), salvo la creaci�n y la importaci�n, 
 * donde se limita si s�lo puede haber una respuesta correcta o varias.
 * 
 * @author je10396
 *
 */
public class MultioptionsQuestionType extends OptionsQuestionType {

	private static final long serialVersionUID = 1L;

	public long getTypeId(){
		return 1;
	}

	public String getName() {
		return "multioptions";
	}

	public String getTitle(Locale locale) {
		return LanguageUtil.get(locale, "multioptions.title");
	}

	public String getDescription(Locale locale) {
		return LanguageUtil.get(locale, "multioptions.description");
	}
	
	/**
	 * Lo mismo que el caso base pero como puede tener varias respuestas correctas se muestra checkboxes en vez de radio buttons.
	 */
	public String getHtmlView(long questionId, ThemeDisplay themeDisplay, Document document){
		super.setInputType("checkbox");
		return super.getHtmlView(questionId, themeDisplay, document);
	}
	
	/**
	 * Lo mismo que el caso base pero como puede tener varias respuestas correctas se muestra checkboxes en vez de radio buttons.
	 */
	public String getHtmlFeedback(Document document,long questionId, ThemeDisplay themeDisplay){
		super.setInputType("checkbox");
		return super.getHtmlFeedback(document, questionId, themeDisplay);
	}
	
	/**
	 * Una respuesta es correcta si tiene cualquier valor distinto de cero.
	 */
	public void importMoodle(long actId, Element question, TestAnswerLocalService testAnswerLocalService)throws SystemException, PortalException {
		//"multichoice" (not single)
		Element questiontext=question.element("questiontext");
		String description=questiontext.elementText("text");
		TestQuestion theQuestion=TestQuestionLocalServiceUtil.addQuestion(actId,description,getTypeId());
		for(Element answerElement:question.elements("answer")){
			boolean correct=(!"0".equals(answerElement.attributeValue("fraction")))? true:false;
			String answer=answerElement.elementText("text");
			String feedback="";
			if(answerElement.element("feedback")!=null && answerElement.element("feedback").element("text")!=null)
				feedback=answerElement.element("feedback").element("text").getText();	 
			testAnswerLocalService.addTestAnswer(theQuestion.getQuestionId(), answer, feedback, feedback, correct);
		}
	}
	
	
}
