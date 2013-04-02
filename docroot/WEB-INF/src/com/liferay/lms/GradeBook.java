package com.liferay.lms;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import au.com.bytecode.opencsv.CSVWriter;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class GradeBook
 */
public class GradeBook extends MVCPortlet {
	
	@Override
	public void serveResource(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String action = ParamUtil.getString(resourceRequest, "action");
		long moduleId = ParamUtil.getLong(resourceRequest, "moduleId",0);
		ThemeDisplay themeDisplay  =(ThemeDisplay)resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(action.equals("export")){
			
			try {
				Module module = ModuleLocalServiceUtil.getModule(moduleId);
				List<LearningActivity> learningActivities = LearningActivityServiceUtil
						.getLearningActivitiesOfModule(moduleId);

				//Necesario para crear el fichero csv.
				resourceResponse.setCharacterEncoding("ISO-8859-1");
				resourceResponse.setContentType("text/csv;charset=ISO-8859-1");
				resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=data.csv");
		        byte b[] = {(byte)0xEF, (byte)0xBB, (byte)0xBF};
		        
		        resourceResponse.getPortletOutputStream().write(b);
		        
		        CSVWriter writer = new CSVWriter(new OutputStreamWriter(resourceResponse.getPortletOutputStream(),"ISO-8859-1"),';');
	
		        //Comunidad
		        writer.writeNext(new String[]{themeDisplay.getScopeGroupName()});
		        
		        //Módulo
		        writer.writeNext(new String[]{module.getTitle(themeDisplay.getLocale())});
		        
		        String[] cabeceras = new String[learningActivities.size()+1];
		        
		        int column=1;
		        cabeceras[0]="User";
		        for(LearningActivity learningActivity:learningActivities){
		        	cabeceras[column++]=learningActivity.getTitle(themeDisplay.getLocale());
		        }
		        		    
		        writer.writeNext(cabeceras);
		       
		        for(User usuario:UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId())){
		        	String[] resultados = new String[learningActivities.size()+1];
		        	
		        	column=1;
		        	resultados[0]=usuario.getFullName()+"("+usuario.getUserId()+")";
		        	
			        for(LearningActivity learningActivity:learningActivities){
						LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), usuario.getUserId());
						
						if(learningActivityResult==null) {
							resultados[column++]=StringPool.BLANK;
						}
						else {
							resultados[column++]=Long.toString(learningActivityResult.getResult());
						}

			        }

			        writer.writeNext(resultados);
		        }
				
		        writer.flush();
				writer.close();
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();
			
			} catch (NestableException e) {
			}finally{
				resourceResponse.getPortletOutputStream().flush();
				resourceResponse.getPortletOutputStream().close();
			}
		} 
	}
 
	private void setGrades(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		boolean correct=true;
		long actId = ParamUtil.getLong(renderRequest,"actId"); 
		long studentId = ParamUtil.getLong(renderRequest,"studentId");
				
		String comments = renderRequest.getParameter("comments");
		long result=0;
		try {
			result=Long.parseLong(renderRequest.getParameter("result"));
			if(result<0 || result>100){
				correct=false;
				SessionErrors.add(renderRequest, "offlinetaskactivity.grades.result-bad-format");
			}
		} catch (NumberFormatException e) {
			correct=false;
			SessionErrors.add(renderRequest, "offlinetaskactivity.grades.result-bad-format");
		}
		
		if(correct) {
			try {
				LearningActivityTry  learningActivityTry =  LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, studentId);
				if(learningActivityTry==null){
					ServiceContext serviceContext = new ServiceContext();
					serviceContext.setUserId(studentId);
					learningActivityTry =  LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
				}
				learningActivityTry.setEndDate(new Date());
				learningActivityTry.setResult(result);
				learningActivityTry.setComments(comments);
				updateLearningActivityTryAndResult(learningActivityTry);
				
				SessionMessages.add(renderRequest, "offlinetaskactivity.grades.updating");
			} catch (NestableException e) {
				SessionErrors.add(renderRequest, "offlinetaskactivity.grades.bad-updating");
			}
		}
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
	protected void doDispatch(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		String ajaxAction = renderRequest.getParameter("ajaxAction");
		System.out.println("aloja!: " + ajaxAction);
		if(ajaxAction!=null) {
			if("setGrades".equals(ajaxAction)) {
				setGrades(renderRequest, renderResponse);
			} 
		}
		
		
		super.doDispatch(renderRequest, renderResponse);
	}

}
