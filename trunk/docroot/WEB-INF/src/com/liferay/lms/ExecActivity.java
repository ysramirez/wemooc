package com.liferay.lms;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

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
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
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
			java.util.Hashtable<TestQuestion, TestAnswer> resultados=new java.util.Hashtable<TestQuestion, TestAnswer>();
			
			while(params.hasMoreElements())
			{
				String param=params.nextElement();
				if(param.startsWith("question_"))
				{
					String squestionId=param.substring("question_".length());
					long questionId=Long.parseLong(squestionId);
					TestQuestion question=new TestQuestionLocalServiceUtil().getTestQuestion(questionId);
					
					long answerId=ParamUtil.getLong(actionRequest, param);
					TestAnswer testAnswer=TestAnswerLocalServiceUtil.getTestAnswer(answerId);
					resultados.put(question, testAnswer);
					if(testAnswer.isIsCorrect())
					{
						correctanswers++;
					}	
				}
			}
			
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
			
			long score=correctanswers*100/questions.size();
			larntry.setResult(score);
			larntry.setTryResultData(resultadosXMLDoc.formattedString());
			larntry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(larntry);
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
			actionRequest.setAttribute("resultados", resultados);
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/results.jsp");
		}
		else
		{
			actionResponse.setRenderParameters(actionRequest.getParameterMap());
			actionRequest.setAttribute("actId", actId);
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/preview.jsp");
		}						
		
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
		if (learnact.getTypeId() == 0) {
	
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestion.jsp");
		}
	}

	public void importQuestions(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception 
	{

	UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actionRequest);
	
	long actId = ParamUtil.getLong(actionRequest, "actId");
	String fileName = request.getFileName("fileName");
	if(fileName!=null && !fileName.equals(""))
	{
		File file = request.getFile("fileName");
		Document document=SAXReaderUtil.read(file);
		TestQuestionLocalServiceUtil.importXML(actId, document);
	}
	SessionMessages.add(actionRequest, "questions-added-successfully");
	actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestions.jsp");
	
	
	}
	public void addquestion(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		long actid = ParamUtil.getLong(actionRequest, "actId");
	
		String text = ParamUtil.getString(actionRequest, "text");
		long questionType = ParamUtil.getLong(actionRequest, "qtype");
		TestQuestion question = TestQuestionLocalServiceUtil.addQuestion(actid, text, questionType);
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(actid);
		actionResponse.setRenderParameter("questionId", Long.toString(question.getQuestionId()));
		if (learnact.getTypeId() == 0) {
	
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
		actionResponse.setRenderParameter("actId", Long.toString(learnact.getActId()));
	
		if (learnact.getTypeId() == 0) {
	
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/editquestion.jsp");
		}
	
	}

	public void deletequestion(ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {
	
		TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(actionRequest, "questionId"));
		TestQuestionLocalServiceUtil.deleteTestQuestion(ParamUtil.getLong(actionRequest, "questionId"));
		editactivity(actionRequest, actionResponse);
		LearningActivity learnact = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
	
		actionRequest.setAttribute("activity", learnact);
	
		SessionMessages.add(actionRequest, "question-deleted-successfully");
		if (learnact.getTypeId() == 0) {
			actionResponse.setRenderParameter("jspPage", "/html/execactivity/test/admin/edit.jsp");
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
		testanswer.setAnswer(answer);
		testanswer.setIsCorrect(correct);
		testanswer.setFeedbackCorrect(feedbackCorrect);
		testanswer.setFeedbacknocorrect(feedbackNoCorrect);
		TestAnswerLocalServiceUtil.updateTestAnswer(testanswer);
		SessionMessages.add(actionRequest, "answer-added-successfully");
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
		// TODO Auto-generated method stub
		if(ParamUtil.getLong(renderRequest, "actId", 0)==0)// TODO Auto-generated method stub
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else
		{
				LearningActivity activity;
				try {
					activity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(renderRequest, "actId", 0));
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
		}
	}
	
	
	public void  serveResource(ResourceRequest request, ResourceResponse response)throws PortletException, IOException {

		String action = ParamUtil.getString(request, "action");
		long actId = ParamUtil.getLong(request, "actId",0);
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
		        //Añadimos 2 columnas para mostrar el nombre del usuario y la fecha del try
		        String[] cabeceras = new String[questionsTitle.size()+2];
		        
		        //Guardamos el orden en que obtenemos las preguntas de la base de datos para poner las preguntas en el mismo orden.
		        Long []questionOrder = new Long[questionsTitle.size()];
		        
		        //En esta columna vamos a tener el nombre del usuario.
		        cabeceras[0]="User";
		        cabeceras[1]="Date";
		        
		        for(int i=2;i<questionsTitle.size()+2;i++){
		        	cabeceras[i]=formatString(questionsTitle.get(i-2).getText())+" ("+questionsTitle.get(i-2).getQuestionId()+")";
		        	questionOrder[i-2]=questionsTitle.get(i-2).getQuestionId();
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
			        		String[] resultados = new String[questionOrder.length+2];
			        		//En la primera columna del csv introducidos el nombre del estudiante.
			        		//resultados[0]=user.getFullName()+" ("+user.getUserId()+")";
			        		resultados[0] = String.valueOf(user.getUuid());
			        		
			        		//En la segunda columna metemos la fecha del try.
			        		resultados[1] = String.valueOf(activity.getEndDate());
			        		for(int i=2;i <questionOrder.length+2 ; i++){
			        			//Si no tenemos respuesta para la pregunta, guardamos ""
			        			resultados[i] = "";
			        			
			        			for(int j=0;j <answersIds.size() ; j++){
			        				//Cuando la respuesta se corresponda con la pregunta que corresponde.
			        				if(Long.valueOf(getQuestionIdByAnswerId(answersIds.get(j))).compareTo(Long.valueOf(questionOrder[i-2])) == 0){
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
		 	//Jsoup elimina todas la etiquetas html del string que se le pasa, devolviendo únicamente el texto plano.
	        return Jsoup.parse(str).text();
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
