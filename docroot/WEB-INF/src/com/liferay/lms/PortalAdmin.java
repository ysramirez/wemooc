package com.liferay.lms;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ProcessAction;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.portlet.p2p.P2PActivityPortlet;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class PortalAdmin
 */
public class PortalAdmin extends MVCPortlet {
 
	
	@ProcessAction(name = "asignP2pActivity")
	public void asignP2pActivity (ActionRequest request, ActionResponse response) throws Exception {
		P2PAssignations asignations = new P2PAssignations();
		asignations.asignCorrectionsToP2PActivities();
	}
	
	public void updateResultP2PActivities (ActionRequest request, ActionResponse response) throws Exception {
		
		
		List<P2pActivity> p2pActivityList = P2pActivityLocalServiceUtil.getP2pActivities(0, P2pActivityLocalServiceUtil.getP2pActivitiesCount());
		
		Calendar start = Calendar.getInstance();
		System.out.println(" ## START ## "+start.getTime());
		System.out.println(" tamano "+p2pActivityList.size());
		
		for(P2pActivity p2pActivity: p2pActivityList){
			//System.out.println("------------------------------------------------"+p2pActivity.getActId());
			//LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(p2pActivity.getActId(),p2pActivity.getUserId());
			//System.out.println(" :: OLD :: getUserId: "+learningActivityResult.getUserId()+", getResult: "+learningActivityResult.getResult()+", getPassed: "+learningActivityResult.getPassed());
			
			P2PActivityPortlet.updateResultP2PActivity(p2pActivity.getP2pActivityId(), p2pActivity.getUserId());
			
			//System.out.println(" :: NEW :: getUserId: "+learningActivityResult.getUserId()+", getResult: "+learningActivityResult.getResult()+", getPassed: "+learningActivityResult.getPassed());
		}
		
		Calendar end = Calendar.getInstance();
		System.out.println("------------------------------------------------");
		System.out.println(" ## START ## "+start.getTime());
		System.out.println(" ##  END  ## "+end.getTime());
		System.out.println("------------------------------------------------");
	}
	
	
	public void updateExtraContentMultimediaActivities (ActionRequest request, ActionResponse response) throws Exception {
		
		
		List<LearningActivity> learningActivityList = LearningActivityLocalServiceUtil.getLearningActivities(0, LearningActivityLocalServiceUtil.getLearningActivitiesCount());
		
		int conta = 1;
		
		for(LearningActivity activity: learningActivityList){
			
			//Si es un multimediaentry
			if(activity.getTypeId() == 2 && activity.getExtracontent().contains("<p2p>")){
				
				//System.out.println("  **  "+activity.getActId()+"  ** "+" conta: "+conta++);
				
				//System.out.println(" :: xml multimedia old :: " + activity.getExtracontent());
				
				String xmlMultimedia = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "validaciones");
				String xmlVideo = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "video"); 
				String xmlDocument = LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(), "document");
							
				//System.out.println(" :: xmlMultimedia :: " + xmlMultimedia);
				//System.out.println(" :: xmlVideo :: " + xmlVideo);
				//System.out.println(" :: xmlDocument :: " + xmlDocument);
				
				
				//Calculamos el nuevo extracontent
				try {			
					HashMap<String, String> hashMap = new HashMap<String, String>();
					Document document;
					
					document = SAXReaderUtil.read(xmlMultimedia);
					Element rootElement = document.getRootElement();
					
					for(Element key:rootElement.elements()){
						hashMap.put(key.getName(), key.getText());
					}
					
					for(Element key:rootElement.elements()){
						if(hashMap.get(key.getName()) != null && key.attributeValue("id") != null){
							hashMap.put(key.getName(), key.attributeValue("id"));
						}
					}
					
					//System.out.println(" :: newVideo :: " + hashMap.get("video"));
					//System.out.println(" :: newDocument :: " + hashMap.get("document"));
					
					
					String videoToUpdate = "";
					String documentToUpdate = "";

					if(xmlVideo == ""){
						videoToUpdate = hashMap.get("video");
					}else{
						videoToUpdate = xmlVideo;
					}
					
					if(xmlDocument == "" || xmlDocument == null){
						if(hashMap.get("document") != null){
							documentToUpdate = hashMap.get("document");
						}
					}else{
						documentToUpdate = xmlVideo;
					}
					
					String newExtraContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <multimediaentry>";
					if(documentToUpdate != ""){
						newExtraContent += "<document id=\""+documentToUpdate +"\" />";
					}
					newExtraContent += "<video>"+videoToUpdate +"</video>";
					newExtraContent += "</multimediaentry>";
					
					String updateQuery = " update lms_learningactivity set extracontent='"+newExtraContent+"' where actId="+activity.getActId()+";";
					
					System.out.println(updateQuery);
					//Actualizamos en la bd.
					//LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "video", videoToUpdate);
					//LearningActivityLocalServiceUtil.setExtraContentValue(activity.getActId(), "document", documentToUpdate);
					
				} catch (DocumentException e) {
					e.printStackTrace();
				}
		
				//System.out.println(" :: xml multimedia new :: "+activity.getExtracontent());
				//System.out.println("**********************************");
			}
		}
		
	}
	
}
