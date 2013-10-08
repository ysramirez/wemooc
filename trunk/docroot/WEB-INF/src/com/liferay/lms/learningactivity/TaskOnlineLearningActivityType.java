package com.liferay.lms.learningactivity;

import java.io.IOException;

import javax.portlet.PortletResponse;

import com.liferay.lms.asset.TaskOnlineAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class TaskOnlineLearningActivityType extends BaseLearningActivityType {

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"onlinetaskactivity" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());
	
	@Override
	public AssetRenderer getAssetRenderer(LearningActivity larn) {
		return new TaskOnlineAssetRenderer(larn);
	}

	@Override
	public String getName() {	
		return "learningactivity.online";
	}
	
	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public long getTypeId() {
		return 6;
	}
		
	@Override
	public String getExpecificContentPage() {
		return "/html/onlinetaskactivity/admin/edit.jsp";
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}
	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		
	    DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityTry.class);
	  	Criterion criterion=PropertyFactoryUtil.forName("actId").eq(learningActivity.getActId());
		dq.add(criterion);
		
	    if(LearningActivityTryLocalServiceUtil.dynamicQueryCount(dq)==0) {
		
			String fichero = ParamUtil.getString(uploadRequest, "fichero", StringPool.FALSE);
			String textoenr = ParamUtil.getString(uploadRequest, "textoenr", StringPool.FALSE);
			
			Document document = SAXReaderUtil.createDocument();
			Element rootElement = document.addElement("online");
			
			Element ficheroXML=SAXReaderUtil.createElement("fichero");
			ficheroXML.addText(fichero);
			rootElement.add(ficheroXML);
			
			Element textoenrXML=SAXReaderUtil.createElement("textoenr");
			textoenrXML.addText(textoenr);
			rootElement.add(textoenrXML);
			
			learningActivity.setExtracontent(document.formattedString());
		
	    }
			
	}
	
	@Override
	public String getDescription() {
		return "learningactivity.online.helpmessage";
	}
	
	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

}
