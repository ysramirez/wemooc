/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.base.TestQuestionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

/**
 * The implementation of the test question local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.TestQuestionLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.TestQuestionLocalServiceUtil} to access the test question local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.TestQuestionLocalServiceBaseImpl
 * @see com.liferay.lms.service.TestQuestionLocalServiceUtil
 */
public class TestQuestionLocalServiceImpl
	extends TestQuestionLocalServiceBaseImpl {
	public void importXML(long actId, Document document) throws DocumentException, SystemException, PortalException
	{
		Element rootElement = document.getRootElement();
		for(Element question:rootElement.elements("question"))
		{
			importXMLQuestion(actId,question);
		}
		
	}
	private void importXMLQuestion(long actId, Element question) throws SystemException, PortalException {
		List<String> stringList = Arrays.asList(new String[]{"multichoice","truefalse","essay","numerical","shortanswer","cloze"});
		if(stringList.contains(question.attributeValue("type"))){
			long type = getQuestionType(question);
			QuestionType qt = new QuestionTypeRegistry().getQuestionType(type);
			qt.importMoodle(actId, question, testAnswerLocalService);
		}
		
	}
	private long getQuestionType(Element question) {
		long type = 0; //Respuesta única (truefalse o multichoice con single = true)
		if("multichoice".equals(question.attributeValue("type")) && "false".equals(question.element("single").getText())) type = 1;
		else if("essay".equals(question.attributeValue("type")) || "numerical".equals(question.attributeValue("type")) || "shortanswer".equals(question.attributeValue("type"))) type = 2;
		else if("cloze".equals(question.attributeValue("type"))) type = 3;
		return type;
	}
	public TestQuestion addQuestion(long actId,String text,long questionType) throws SystemException
	{
		TestQuestion tq =
			testQuestionPersistence.create(counterLocalService.increment(
					TestQuestion.class.getName()));
		tq.setText(text);
		tq.setQuestionType(questionType);
		tq.setActId(actId);
		testQuestionPersistence.update(tq, false);
		return tq;
	}
	public java.util.List<TestQuestion> getQuestions(long actid) throws SystemException
	{
	 return testQuestionPersistence.findByac(actid);
	}
}