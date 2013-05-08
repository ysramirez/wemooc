package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.util.Date;

import javax.portlet.PortletResponse;

import com.liferay.lms.asset.TestAssetRenderer;
import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TestLearningActivityType extends BaseLearningActivityType 
{

	@Override
	public long getDefaultScore() {
		return 50;
	}


	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public boolean isTriesConfigurable() {
		return true;
	}

	@Override
	public boolean isFeedbackCorrectConfigurable() {
		return true;
	}

	@Override
	public boolean isFeedbackNoCorrectConfigurable() {
		return true;
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {	
		return new TestAssetRenderer(learningactivity);
	}


	@Override
	public String getName() {
		return "test";
	}


	@Override
	public long getTypeId() {
		return 0;
	}

	
	@Override
	public String getExpecificContentPage() {
		return "/html/execactivity/test/admin/editoptions.jsp";
	}
	
	@Override
	public boolean especificValidations(UploadRequest uploadRequest,
			PortletResponse portletResponse) {
		boolean validate=true;
		
		if((Validator.isNotNull(uploadRequest.getParameter("random")))&&
		   ((!Validator.isNumber(uploadRequest.getParameter("random")))||
		    (Long.parseLong(uploadRequest.getParameter("random"))<0)))
		{
			SessionErrors.add(uploadRequest, "execactivity.editActivity.random.number");
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
				rootElement = document.addElement("test");
			}
			else
			{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
			}
			
			Element random=rootElement.element("random");
			if(random!=null)
			{
				random.detach();
				rootElement.remove(random);
			}
			random = SAXReaderUtil.createElement("random");
			random.setText(Long.toString(ParamUtil.get(uploadRequest,"random",0l)));		
			rootElement.add(random);	
			
			Element password=rootElement.element("password");
			if(password!=null)
			{
				password.detach();
				rootElement.remove(password);
			}
			password = SAXReaderUtil.createElement("password");
			password.setText(HtmlUtil.escape(ParamUtil.get(uploadRequest,"password",StringPool.BLANK).trim()));		
			rootElement.add(password);	
						
			Element timeStamp=rootElement.element("timeStamp");
			if(timeStamp!=null)
			{
				timeStamp.detach();
				rootElement.remove(timeStamp);
			}
			timeStamp = SAXReaderUtil.createElement("timeStamp");
			timeStamp.setText(Long.toString(ParamUtil.getLong(uploadRequest, "hourDuration",0) * 3600 
					                      + ParamUtil.getLong(uploadRequest, "minuteDuration",0) * 60 
					                      + ParamUtil.getLong(uploadRequest, "secondDuration",0)));		
			rootElement.add(timeStamp);	

			Element showCorrectAnswer=rootElement.element("showCorrectAnswer");
			if(showCorrectAnswer!=null)
			{
				showCorrectAnswer.detach();
				rootElement.remove(showCorrectAnswer);
			}
			showCorrectAnswer = SAXReaderUtil.createElement("showCorrectAnswer");
			showCorrectAnswer.setText(Boolean.toString(ParamUtil.get(uploadRequest,"showCorrectAnswer",false)));		
			rootElement.add(showCorrectAnswer);	
			

			Element improve=rootElement.element("improve");
			if(improve!=null)
			{
				improve.detach();
				rootElement.remove(improve);
			}
			improve = SAXReaderUtil.createElement("improve");
			improve.setText(Boolean.toString(ParamUtil.get(uploadRequest,"improve",false)));		
			rootElement.add(improve);	
			
			learningActivity.setExtracontent(document.formattedString());
	    }
	}
	
}
