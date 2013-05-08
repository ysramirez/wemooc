package com.liferay.lms;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.jsoup.Jsoup;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.asset.LearningActivityAssetRendererFactory;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ExecActivity
 */
public class ExecActivity extends MVCPortlet 
{
	HashMap<Long, TestAnswer> answersMap = new HashMap<Long, TestAnswer>(); 
		
	public void correct
	(
			ActionRequest actionRequest,ActionResponse actionResponse)
		throws Exception {
		
		long actId=ParamUtil.getLong(actionRequest, "actId");
		long latId=ParamUtil.getLong(actionRequest,"latId" );
		
		LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
				    
		//Comprobar que el usuario tenga intentos posibles.
		if (larntry.getEndDate() == null)
		{
			
			long correctanswers=0;
			Enumeration<String> params=actionRequest.getParameterNames();
			List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
			Map<Long,Long> resultadosIds=new Hashtable<Long,Long>();
			Map<Long, TestAnswer> resultados=new Hashtable<Long, TestAnswer>();
			long random = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"random"));
			long[] questionIds = ParamUtil.getLongValues(actionRequest, "question");

			
			while(params.hasMoreElements())
			{
				String param=params.nextElement();
				if(param.startsWith("question_"))
				{
					String squestionId=param.substring("question_".length());
					long questionId=Long.parseLong(squestionId);					
					long answerId=ParamUtil.getLong(actionRequest, param);
					TestAnswer testAnswer=TestAnswerLocalServiceUtil.getTestAnswer(answerId);
					resultadosIds.put(questionId, answerId);
					resultados.put(questionId, testAnswer);
					if(testAnswer.isIsCorrect())
					{
						correctanswers++;
					}	
				}
			}
			
			Element resultadosXML=SAXReaderUtil.createElement("results");
			Document resultadosXMLDoc=SAXReaderUtil.createDocument(resultadosXML);
			
			if(random==0){
				for(Map.Entry<Long, Long> questionEntry:resultadosIds.entrySet()){
					Element questionXML=SAXReaderUtil.createElement("question");
					questionXML.addAttribute("id", Long.toString(questionEntry.getKey()));
					Element answerXML=SAXReaderUtil.createElement("answer");
					answerXML.addAttribute("id", Long.toString(questionEntry.getValue()));
					questionXML.add(answerXML);
					resultadosXML.add(questionXML);						
				}
			}
			else
			{
				for (long questionId : questionIds) {
					Element questionXML=SAXReaderUtil.createElement("question");
					questionXML.addAttribute("id", Long.toString(questionId));
					if(resultadosIds.containsKey(questionId)) {
						Element answerXML=SAXReaderUtil.createElement("answer");
						answerXML.addAttribute("id", Long.toString(resultadosIds.get(questionId)));
						questionXML.add(answerXML);
					}
					resultadosXML.add(questionXML);											
				}
			}
						
			long score=correctanswers*100/((random!=0 && random<questions.size())?random:questions.size());
		 	
			LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, PortalUtil.getUserId(actionRequest));
			long oldResult=-1;
			if(learningActivityResult!=null){
				oldResult=learningActivityResult.getResult();
			}
			
			larntry.setResult(score);
			larntry.setTryResultData(resultadosXMLDoc.formattedString());
			larntry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(larntry);
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
			actionRequest.setAttribute("resultados", resultados);
			actionRequest.setAttribute("oldResult", oldResult);
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/results.jsp");
		}
		else
		{
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
			actionRequest.setAttribute("actId", actId);
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/preview.jsp");
		}						
		
	}
		
	public void camposExtra(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
			long actId = ParamUtil.getLong(actionRequest, "actId", 0);
			long randomString=ParamUtil.getLong(actionRequest, "randomString",0);
			String passwordString=ParamUtil.getString(actionRequest, "passwordString",StringPool.BLANK);
			long hourDurationString=ParamUtil.getLong(actionRequest, "hourDurationString",0);
			long minuteDurationString=ParamUtil.getLong(actionRequest, "minuteDurationString",0);
			long secondDurationString=ParamUtil.getLong(actionRequest, "secondDurationString",0);
			long timeStamp = hourDurationString * 3600 + minuteDurationString * 60 + secondDurationString;
			
			String showCorrectAnswer=ParamUtil.getString(actionRequest, "showCorrectAnswer", "false");
			String improve=ParamUtil.getString(actionRequest, "improve", "false");
			
			if(randomString==0) {
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "random", StringPool.BLANK);
			}
			else {
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "random", Long.toString(randomString));
			}
			
			LearningActivityLocalServiceUtil.setExtraContentValue(actId, "password", HtmlUtil.escape(passwordString.trim()));
			
			if(timeStamp==0) {
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "timeStamp", StringPool.BLANK);
			}
			else {
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "timeStamp", Long.toString(timeStamp));
			}
			
			if(showCorrectAnswer.equals("true")) {
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswer", "true");
			}else if(showCorrectAnswer.equals("false")){
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "showCorrectAnswer", "false");
			}
			
			if(improve.equals("true")) {
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "improve", "true");
			}else if(improve.equals("false")) {
				LearningActivityLocalServiceUtil.setExtraContentValue(actId, "improve", "false");
			}

			SessionMessages.add(actionRequest, "activity-saved-successfully");
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/edit.jsp");
						
		}

	public void addanswer(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		long questionId = ParamUtil.getLong(actionRequest, "questionId");
		String answers = ParamUtil.getString(actionRequest, "answer");
		boolean correct = ParamUtil.getBoolean(actionRequest, "correct");
		String feedbackCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
		String feedbackNoCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
		
		if(Validator.isNull(answers)) {
			SessionErrors.add(actionRequest, "answer-test-required");
		}
		else{
			TestAnswerLocalServiceUtil.addTestAnswer(questionId, answers, feedbackCorrect, feedbackNoCorrect, correct);
			SessionMessages.add(actionRequest, "answer-added-successfully");
		}
		
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(TestQuestionLocalServiceUtil.getTestQuestion(questionId).getActId());
		actionResponse.setRenderParameter("questionId", Long.toString(questionId));

		if (learnact.getTypeId() == 0) {
			actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
			actionResponse.setRenderParameter("resId", Long.toString(learnact.getActId()));
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestion.jsp");
		}
	}

	public void importQuestions(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception 
	{

	UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);
	
	long actId = ParamUtil.getLong(actionRequest, "resId");
	String fileName = request.getFileName("fileName");
	if(fileName!=null && !fileName.equals(""))
	{
		File file = request.getFile("fileName");
		Document document=SAXReaderUtil.read(file);
		TestQuestionLocalServiceUtil.importXML(actId, document);
	}
	SessionMessages.add(actionRequest, "questions-added-successfully");
	actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
	actionResponse.setRenderParameter("resId", Long.toString(actId));
	actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestions.jsp");
	
	
	}
	public void addquestion(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		long actid = ParamUtil.getLong(actionRequest, "resId");
	
		String text = ParamUtil.getString(actionRequest, "text");
		long questionType = ParamUtil.getLong(actionRequest, "qtype");
		TestQuestion question = TestQuestionLocalServiceUtil.addQuestion(actid, text, questionType);
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(actid);
		actionResponse.setRenderParameter("questionId", Long.toString(question.getQuestionId()));
		if (learnact.getTypeId() == 0) {
			actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
			actionResponse.setRenderParameter("resId", Long.toString(actid));
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestion.jsp");
		}
		actionRequest.setAttribute("activity", learnact);
		actionRequest.setAttribute("questionId", question.getQuestionId());
		
		actionRequest.setAttribute("primKey", question.getQuestionId());
		
		
	}

	public void deleteanswer(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		TestAnswer answer = TestAnswerLocalServiceUtil.getTestAnswer(ParamUtil.getLong(actionRequest, "answerId"));
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(TestQuestionLocalServiceUtil.getTestQuestion(answer.getQuestionId()).getActId());
		TestAnswerLocalServiceUtil.deleteTestAnswer(ParamUtil.getLong(actionRequest, "answerId"));
		SessionMessages.add(actionRequest, "answer-deleted-successfully");
		actionResponse.setRenderParameter("questionId", Long.toString(answer.getQuestionId()));
	
		if (learnact.getTypeId() == 0) {
			actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
			actionResponse.setRenderParameter("resId", Long.toString(learnact.getActId()));
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestion.jsp");
		}
	
	}

	public void deletequestion(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(actionRequest, "questionId"));
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		TestQuestionLocalServiceUtil.deleteTestQuestion(question.getQuestionId());
		SessionMessages.add(actionRequest, "question-deleted-successfully");
		if (learnact.getTypeId() == 0) {
			actionResponse.setRenderParameter("actionEditingDetails", StringPool.TRUE);
			actionResponse.setRenderParameter("resId", Long.toString(question.getActId()));
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestions.jsp");
		}
		
	
	}

	public void editanswer(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		long answerId = ParamUtil.getLong(actionRequest, "answerId");
		String answer = ParamUtil.getString(actionRequest, "answer");
		boolean correct = ParamUtil.getBoolean(actionRequest, "correct");
		String feedbackCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
		String feedbackNoCorrect = ParamUtil.getString(actionRequest, "feedbackCorrect", "");
	
		TestAnswer testanswer = TestAnswerLocalServiceUtil.getTestAnswer(answerId);
		
		if(Validator.isNull(answer)) {
			SessionErrors.add(actionRequest, "answer-test-required_"+answerId);
		}
		else{
			testanswer.setAnswer(answer);
			testanswer.setIsCorrect(correct);
			testanswer.setFeedbackCorrect(feedbackCorrect);
			testanswer.setFeedbacknocorrect(feedbackNoCorrect);
			TestAnswerLocalServiceUtil.updateTestAnswer(testanswer);
			SessionMessages.add(actionRequest, "answer-added-successfully");
	    }
		
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(TestQuestionLocalServiceUtil.getTestQuestion(testanswer.getQuestionId()).getActId());
		actionResponse.setRenderParameter("questionId", Long.toString(testanswer.getQuestionId()));
		actionResponse.setRenderParameter("actId", Long.toString(learnact.getActId()));
		if (learnact.getTypeId() == 0) {
	
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestion.jsp");
		}
	}

	public void editest(ActionRequest actionRequest, ActionResponse actionResponse) {
	
	}

	public void editquestion(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		String text = ParamUtil.getString(actionRequest, "text");
		long questionType = ParamUtil.getLong(actionRequest, "qtype");
		long questionId = ParamUtil.getLong(actionRequest, "questionId");
		TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(questionId);
		
		question.setQuestionType(questionType);
		question.setText(text);
		TestQuestionLocalServiceUtil.updateTestQuestion(question);
		SessionMessages.add(actionRequest, "question-modified-successfully");
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
		actionResponse.setRenderParameter("questionId", Long.toString(questionId));
		actionResponse.setRenderParameter("actId", Long.toString(learnact.getActId()));
	
		if (learnact.getTypeId() == 0) {
	
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestion.jsp");
		}
	
	}
	public void edit(ActionRequest actionRequest, ActionResponse actionResponse)
	throws PortalException, SystemException, Exception {
		
		actionResponse.setRenderParameters(actionRequest.getParameterMap());
		if(ParamUtil.getLong(actionRequest, "actId", 0)==0)// TODO Auto-generated method stub
		{
			actionResponse.setRenderParameter("jspPage", "/html/lmsactivitieslist/view.jsp");
		}
	}
	public void editactivity(ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException, SystemException, Exception {
		long actId = ParamUtil.getInteger(actionRequest, "actId");
		// LearningActivity learnact =
		// com.liferay.lms.service.LearningActivityServiceUtil.getLearningActivity(actId);
		LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
		if (laf != null) {
			AssetRenderer assetRenderer = laf.getAssetRenderer(actId, 0);

			String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) actionRequest, (LiferayPortletResponse) actionResponse).toString();
			actionResponse.sendRedirect(urlEdit);
		}
		SessionMessages.add(actionRequest, "asset-renderer-not-defined");
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		long actId=0;
		
		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
			
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId", 0);
		}
					
		if(actId==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
				LearningActivity activity;
				try {
					activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
					long typeId=activity.getTypeId();
					
					if(typeId==0)
					{
						super.render(renderRequest, renderResponse);
					}
					else
					{
						renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
					}
				} catch (PortalException e) {
				} catch (SystemException e) {
				}			
		}
	}
	
	
	public void  serveResource(ResourceRequest request, ResourceResponse response)throws PortletException, IOException {

		String action = ParamUtil.getString(request, "action");
		long actId = ParamUtil.getLong(request, "resId",0);
		if(action.equals("export")){
			
			try {
				
				//Necesario para crear el fichero csv.
				response.setCharacterEncoding("ISO-8859-1");
				response.setContentType("text/csv;charset=ISO-8859-1");
				response.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.csv");
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        
		        response.getPortletOutputStream().write(b);
		        
		        CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getPortletOutputStream(),"ISO-8859-1"),';');
		        
	
		        //Crear la cabecera con las preguntas.
		        List<TestQuestion> questionsTitle = TestQuestionLocalServiceUtil.getQuestions(actId);
		        //Añadimos x columnas para mostrar otros datos que no sean las preguntas como nombre de usuario, fecha, etc.
		        int numExtraCols = 3;
		        String[] cabeceras = new String[questionsTitle.size()+numExtraCols];
		        
		        //Guardamos el orden en que obtenemos las preguntas de la base de datos para poner las preguntas en el mismo orden.
		        Long []questionOrder = new Long[questionsTitle.size()];
		        
		        //En las columnas extra ponemos la cabecera
		        cabeceras[0]="User";
		        cabeceras[1]="UserId";
		        cabeceras[2]="Date";
		        
		        for(int i=numExtraCols;i<questionsTitle.size()+numExtraCols;i++){
		        	cabeceras[i]=formatString(questionsTitle.get(i-numExtraCols).getText())+" ("+questionsTitle.get(i-numExtraCols).getQuestionId()+")";
		        	questionOrder[i-numExtraCols]=questionsTitle.get(i-numExtraCols).getQuestionId();
		        }
		        writer.writeNext(cabeceras);
		        
		      //Partiremos del usuario para crear el csv para que sea más facil ver los intentos.
		        List<User> users = LearningActivityTryLocalServiceUtil.getUsersByLearningActivity(actId);
		        
		        for(User user:users){
		        	
		        	//Para cada usuario obtenemos los intentos para la learning activity.
		        	List<LearningActivityTry> activities = LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUser(actId, user.getUserId());
		        	List<Long> answersIds = new ArrayList<Long>();
		        	
		        	for(LearningActivityTry activity:activities){
		        		
		        		String xml = activity.getTryResultData();
		        		
		        		//Leemos el xml que contiene lo que ha respondido el estudiante.
		        		if(!xml.equals("")){
		        			
			        		Document document = SAXReaderUtil.read(xml);
			        		Element rootElement = document.getRootElement();
			        		
			        		//Obtenemos las respuestas que hay introducido.
			        		for(Element question:rootElement.elements("question")){
			        			
				    			for(Element answerElement:question.elements("answer")){
				        			//Guardamos el id de la respuesta para posteriormente obtener su texto.
				        			answersIds.add(Long.valueOf(answerElement.attributeValue("id")));
				    			}
	
			        		}
			        		
			    			//Array con los resultados de los intentos.
			        		String[] resultados = new String[questionOrder.length+numExtraCols];
			        		
			        		//Introducimos los datos de las columnas extra
			        		resultados[0]=user.getFullName();
			        		resultados[1] = String.valueOf(user.getUserId());
			        		resultados[2] = String.valueOf(activity.getEndDate());
			        		
			        		for(int i=numExtraCols;i <questionOrder.length+numExtraCols ; i++){
			        			//Si no tenemos respuesta para la pregunta, guardamos ""
			        			resultados[i] = "";
			        			
			        			for(int j=0;j <answersIds.size() ; j++){
			        				//Cuando la respuesta se corresponda con la pregunta que corresponde.
			        				if(Long.valueOf(getQuestionIdByAnswerId(answersIds.get(j))).compareTo(Long.valueOf(questionOrder[i-numExtraCols])) == 0){
			        					//Guardamos la respuesta en el array de resultados
				        				resultados[i]=getAnswerTextByAnswerId(answersIds.get(j));
				        			}
			        			}
			        			
			        		}
			        		//Escribimos las respuestas obtenidas para el intento en el csv.
			    			writer.writeNext(resultados);
		        		}
		        	}
		        }
				
				writer.flush();
				writer.close();
				response.getPortletOutputStream().flush();
		        response.getPortletOutputStream().close();
			
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}finally{
				response.getPortletOutputStream().flush();
		        response.getPortletOutputStream().close();
			}
		}
	}
	
	 private String formatString(String str) {
		 
		 String res = "";
		 
		//Jsoup elimina todas la etiquetas html del string que se le pasa, devolviendo únicamente el texto plano.
		 res = Jsoup.parse(str).text();
		 
		//Si el texto es muy largo, lo recortamos para que sea más legible.
		 if(res.length() > 50){
			 res = res.substring(0, 50);
		 }
		 	
	      return res;
	    }
	 
	 private String getAnswerTextByAnswerId(Long answerId) throws PortalException, SystemException{
		 //Buscamos la respuesta en el hashmap, si no lo tenemos, lo obtenemos de la bd y lo guardamos.
		 if(!answersMap.containsKey(answerId))
		 {
			TestAnswer answer = TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(answerId));
			answersMap.put(answerId, answer);
		 }
		 
		 return formatString(answersMap.get(answerId).getAnswer())+" ("+answersMap.get(answerId).getAnswerId()+")";
	 }
	 
	 private Long getQuestionIdByAnswerId(Long answerId) throws PortalException, SystemException{
		//Buscamos la respuesta en el hashmap, si no lo tenemos, lo obtenemos y lo guardamos.
		 if(!answersMap.containsKey(answerId))
		 {
			TestAnswer answer = TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(answerId));
			answersMap.put(answerId, answer);
		 }
		 
		 return answersMap.get(answerId).getQuestionId();
	 }
	
}
