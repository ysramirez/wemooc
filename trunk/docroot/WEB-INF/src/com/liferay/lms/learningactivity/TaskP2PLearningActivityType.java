package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.util.Date;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.TaskP2PAssetRenderer;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskP2PLearningActivityType extends BaseLearningActivityType {

	public static final int DEFAULT_VALIDATION_NUMBER = 3;
	
	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"p2ptaskactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());

	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new TaskP2PAssetRenderer(larn);
	}
	@Override
	public long getDefaultScore() {		
		return 50;
	}

	@Override
	public String getName() {
		return "taskp2p";
	}

	@Override
	public long getTypeId() {
		return 3;
	}
	
	@Override
	public String getExpecificContentPage() {
		return "/html/p2ptaskactivity/admin/edit.jsp";
	}

	@Override
	public boolean hasEditDetails() {
		return false;
	}

	@Override
	public boolean especificValidations(UploadRequest uploadRequest,
			PortletResponse portletResponse) {
		PortletRequest actionRequest = (PortletRequest)uploadRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		boolean validate=true;

		if((Validator.isNotNull(uploadRequest.getParameter("numValidaciones")))&&
				(!Validator.isNumber(uploadRequest.getParameter("numValidaciones"))))
		{
			SessionErrors.add(actionRequest, "p2ptaskactivity.editActivity.numValidaciones.number");
			validate=false;
		}
		return validate;
	}
	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)uploadRequest.getAttribute(WebKeys.THEME_DISPLAY);	
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		Module module = ModuleLocalServiceUtil.getModule(learningActivity.getModuleId());
				
	    if((!module.getStartDate().before(new Date()))||(themeDisplay.getPermissionChecker().hasPermission(course.getGroupId(), Course.class.getName(),course.getCourseId(),"COURSEEDITOR")))
	    {
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("p2p");
			}
			else
			{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
			}
			
			Element anonimous=rootElement.element("anonimous");
			if(anonimous!=null)
			{
				anonimous.detach();
				rootElement.remove(anonimous);
			}
			anonimous = SAXReaderUtil.createElement("anonimous");
			anonimous.setText(Boolean.toString(ParamUtil.get(uploadRequest,"anonimous",false)));		
			rootElement.add(anonimous);	
			
			if(P2pActivityLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(P2pActivity.class).add(PropertyFactoryUtil.forName("actId").eq(learningActivity.getActId())))==0){
			
				Element numValidaciones=rootElement.element("validaciones");
				if(numValidaciones!=null)
				{
					numValidaciones.detach();
					rootElement.remove(numValidaciones);
				}
				numValidaciones = SAXReaderUtil.createElement("validaciones");
				numValidaciones.setText(Long.toString(ParamUtil.getLong(uploadRequest,"numValidaciones",DEFAULT_VALIDATION_NUMBER)));		
				rootElement.add(numValidaciones);	
				
				Element result=rootElement.element("result");
				if(result!=null)
				{
					result.detach();
					rootElement.remove(result);
				}
				result = SAXReaderUtil.createElement("result");
				result.setText(Boolean.toString(ParamUtil.get(uploadRequest,"result",false)));		
				rootElement.add(result);	
			}
		
			learningActivity.setExtracontent(document.formattedString());
	    }
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}
}

