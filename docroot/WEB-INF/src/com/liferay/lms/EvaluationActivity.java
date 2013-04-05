package com.liferay.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.lms.learningactivity.TaskEvaluationLearningActivityType;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class EvaluationActivity
 */
public class EvaluationActivity extends MVCPortlet implements MessageListener{
	
	private static Log _log = LogFactoryUtil.getLog(EvaluationActivity.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public void receive(Message message) throws MessageListenerException {
		long actId = message.getLong("actId");
		
		if(actId!=0){
			try {
				evaluate(actId);
			} catch (NestableException e) {
				_log.error("Error during evaluation: "+actId, e);
			}
		}
		else{
			// Scheduler trigger this execution. We must evaluate all activities.
			
			try {
				
				for (LearningActivity learningActivity : (List<LearningActivity>)LearningActivityLocalServiceUtil.dynamicQuery(
						DynamicQueryFactoryUtil.forClass(LearningActivity.class).
						add(PropertyFactoryUtil.forName("typeId").eq((int)new TaskEvaluationLearningActivityType().getTypeId())))) {
					try {
						evaluate(learningActivity.getActId());
					} catch (NestableException e) {
						_log.error("Error during evaluation: "+actId, e);
					}					
				}
			} catch (SystemException e) {
				_log.error("Error during evaluation job ");
			}

		}
	
			
	}	
	
	@ProcessAction(name = "execEvaluation")
	public void execEvaluation (ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		long actId = ParamUtil.getLong(actionRequest,"actId",0);
		
		if(actId!=0) {
			 try {
				Message message = new Message();
				message.put("actId", actId);
				MessageBusUtil.sendMessage("liferay/lms/evaluation", message);
			} catch (Exception e) {
			}
		}
		
	}

	private void evaluate(long actId)
			throws SystemException,PortalException {
		
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		Map<Long,Long> activities= new HashMap<Long,Long>();
		
		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().length()!=0)) {
			try{
				
				Iterator<Element> elements = SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement().elementIterator();
				
				while(elements.hasNext()){
					Element element =elements.next();
					if("activities".equals(element.getName())){
						Iterator<Element> activitiesElement = element.elementIterator();
						while(activitiesElement.hasNext()) {
							Element activity =activitiesElement.next();
							if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
								try{
									activities.put(Long.valueOf(activity.attribute("id").getValue()),Long.valueOf(activity.getText()));
								}
								catch(NumberFormatException e){}
							}
						}
						
					}
				}
			
				if(activities.size()!=0){
					for(User user:UserLocalServiceUtil.getGroupUsers(learningActivity.getGroupId())){
						boolean all=true;
						double[] values = new double[activities.size()];
						double[] weights = new double[activities.size()];
						
						int i=0;
						for(Map.Entry<Long, Long> evalAct:activities.entrySet()){
							LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(evalAct.getKey(), user.getUserId());
							if(learningActivityResult==null){
								all=false;
								break;
							}
							values[i]=learningActivityResult.getResult();
							weights[i]=evalAct.getValue();		
							i++;
						}
						
						if(all){
							
							try {
								LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, user.getUserId());
								if(learningActivityTry==null){
									ServiceContext serviceContext = new ServiceContext();
									serviceContext.setUserId(user.getUserId());
									learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
								}
								learningActivityTry.setEndDate(new Date());
								learningActivityTry.setResult((long)calculateMean(values, weights));
								updateLearningActivityTryAndResult(learningActivityTry);
								
							} catch (NestableException e) {
								_log.error("Error updating evaluation: "+learningActivity.getActId()+" result of user: "+user.getUserId(), e);
							}						
						}
					}	
				}
			}catch(DocumentException e){}
			
		}
	}

	private double calculateMean(double[] values, double[] weights) {
		int i;
		double sumWeight=0;
		for (i = 0; i < weights.length; i++) {
			sumWeight+=weights[i];
		}
		
		double mean=0;
		for (i = 0; i < values.length; i++) {
			mean+=weights[i]*values[i];
		}
		mean/=sumWeight;
		
		//Correction factor
		double correction=0;
		for (i = 0; i < values.length; i++) {
			correction += weights[i] * (values[i] - mean);
		}
		
		return mean + (correction/sumWeight);
	}
	
	private void updateLearningActivityTryAndResult(
			LearningActivityTry learningActivityTry) throws PortalException,
			SystemException {
		LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningActivityTry);
		
		LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivityTry.getActId(), learningActivityTry.getUserId());
		if(learningActivityResult.getResult() != learningActivityTry.getResult()) {
			LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(learningActivityTry.getActId());
			learningActivityResult.setResult(learningActivityTry.getResult());
			learningActivityResult.setPassed(learningActivityTry.getResult()>=learningActivity.getPasspuntuation());
			LearningActivityResultLocalServiceUtil.updateLearningActivityResult(learningActivityResult);
		}
	}
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		PortletConfig portletConfig = (PortletConfig)resourceRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		
		if("updateEvaluation".equals(resourceRequest.getParameter("ajaxAction"))){
			resourceResponse.setContentType("text/javascript");
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			try{
	
				
				LearningActivity evaluationLearningActivity=null;
				try {
					evaluationLearningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(resourceRequest, "actId",-1));
				} catch (NestableException e) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "evaluationTask.ajax.error.badEvaluationActivity"));	
					return;
				} 
				
				LearningActivity learningActivity=null;
				try {
					learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(resourceRequest, "evalActId",-1));
				} catch (NestableException e) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "evaluationTask.ajax.error.badActivity"));	
					return;
				} 
				
				boolean state=ParamUtil.getBoolean(resourceRequest, "state", false);

				long weight=0;		
				if(state){
					weight=ParamUtil.getLong(resourceRequest, "weight",0);
					if(weight==0) {
						jsonObject.put("returnStatus", false);
						jsonObject.put("returnMessage", LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "evaluationTask.ajax.error.weight"));	
						return;					
					}
				}
								
				Document extraContentDocument =null;
				try{
					extraContentDocument = SAXReaderUtil.read(evaluationLearningActivity.getExtracontent());
				}
				catch(DocumentException e){
					extraContentDocument = SAXReaderUtil.createDocument(SAXReaderUtil.createElement("evaluation"));
				}
				
				Element rootElement = extraContentDocument.getRootElement();
				Iterator<Element> elements = rootElement.elementIterator();
				boolean existNode=false;
				
				while(elements.hasNext()){
					Element element = elements.next();
					if("activities".equals(element.getName())){
						
						Iterator<Element> activitiesElement = element.elementIterator();
						while(activitiesElement.hasNext()) {
							Element activity = activitiesElement.next();
							if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
								try{
									long activityNodeId=Long.valueOf(activity.attribute("id").getValue());
									if(learningActivity.getActId()==activityNodeId){
										
										if(state){
											activity.setText(Long.toString(weight));
										}
										else {
											element.remove(activity);
										}
										
										existNode=true;
										break;
									}
									
								}
								catch(NumberFormatException e){}
							}
						}
						
						if(existNode==false){
							if(state) {
								Element activity = element.addElement("activity");
								activity.addAttribute("id", Long.toString(learningActivity.getActId()));
								activity.setText(Long.toString(weight));	
							}
							
							existNode=true;
						}
						
						break;
					}
				}
				
				if((existNode==false)&&(state)){
					Element activity = rootElement.addElement("activities").addElement("activity");
					activity.addAttribute("id", Long.toString(learningActivity.getActId()));
					activity.setText(Long.toString(weight));	
				}
				evaluationLearningActivity.setExtracontent(extraContentDocument.formattedString());

				try {
					LearningActivityLocalServiceUtil.updateLearningActivity(evaluationLearningActivity);
				} catch (NestableException e) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "evaluationTask.ajax.error.updating"));	
					return;
				} 
					
	
	
				jsonObject.put("returnStatus", true);
				jsonObject.put("returnMessage", LanguageUtil.get(portletConfig,resourceRequest.getLocale(), "evaluationTask.ajax.ok"));			
			}finally{
				PrintWriter writer = resourceResponse.getWriter();
				writer.write(jsonObject.toString());			
			}
		}	
	}

}
