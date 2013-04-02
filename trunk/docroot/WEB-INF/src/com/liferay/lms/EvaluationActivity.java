package com.liferay.lms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;


/**
 * Portlet implementation class EvaluationActivity
 */
public class EvaluationActivity extends MVCPortlet {
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		if("updateEvaluation".equals(resourceRequest.getAttribute("ajaxAction"))){
			resourceResponse.setContentType("text/javascript");
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
			try{
	
				
				LearningActivity evaluationLearningActivity=null;
				try {
					evaluationLearningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(resourceRequest, "actId",-1));
				} catch (NestableException e) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(resourceRequest.getLocale(), "KO"));	
					return;
				} 
				
				LearningActivity learningActivity=null;
				try {
					learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(resourceRequest, "evalActId",-1));
				} catch (NestableException e) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(resourceRequest.getLocale(), "KO"));	
					return;
				} 
				
				int state=ParamUtil.getInteger(resourceRequest, "state",-1);
				if((state!=0)&&(state!=1)) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(resourceRequest.getLocale(), "KO"));	
					return;					
				}
				
				
				long weight=ParamUtil.getLong(resourceRequest, "weight",-1);
				if(weight==-1) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(resourceRequest.getLocale(), "KO"));	
					return;					
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
										if(state==0){
											element.remove(activity);
										}
										else {
											element.setText(Long.toString(weight));
										}
										
										existNode=true;
										break;
									}
									
								}
								catch(NumberFormatException e){}
							}
						}
						
						if(existNode==false){
							if(state==1) {
								Element activity = element.addElement("activity");
								activity.addAttribute("id", Long.toString(learningActivity.getActId()));
								activity.setText(Long.toString(weight));	
							}
							
							existNode=true;
						}
						
						break;
					}
				}
				
				if((existNode==false)&&(state==1)){
					Element activity = rootElement.addElement("activities").addElement("activity");
					activity.addAttribute("id", Long.toString(learningActivity.getActId()));
					activity.setText(Long.toString(weight));	
				}
				evaluationLearningActivity.setExtracontent(extraContentDocument.formattedString());

				try {
					LearningActivityLocalServiceUtil.updateLearningActivity(evaluationLearningActivity);
				} catch (NestableException e) {
					jsonObject.put("returnStatus", false);
					jsonObject.put("returnMessage", LanguageUtil.get(resourceRequest.getLocale(), "OK"));	
					return;
				} 
					
	
	
				jsonObject.put("returnStatus", true);
				jsonObject.put("returnMessage", LanguageUtil.get(resourceRequest.getLocale(), "OK"));			
			}finally{
				PrintWriter writer = resourceResponse.getWriter();
				writer.write(jsonObject.toString());			
			}
		}	
	}	
}
