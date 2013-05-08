package com.liferay.lms.learningactivity;

import java.io.IOException;

import javax.portlet.PortletResponse;

import com.liferay.lms.asset.SCORMAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class SCORMLearningActivityType extends BaseLearningActivityType 
{

	@Override
	public boolean gradebook() {
		return false;
	}


	@Override
	public long getDefaultScore() {
		return 50;
	}
	@Override
	public boolean isScoreConfigurable() {
		return true;
	}

	@Override
	public String getName() {
		
		return "SCORM";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new SCORMAssetRenderer(learningactivity);
	}


	@Override
	public long getTypeId() {
		return 9;
	}
	
	
	@Override
	public String getExpecificContentPage() {
		return "/html/scormactivity/admin/edit.jsp";
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}

	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		
		Document document = null;
		Element rootElement = null;
		if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
			document = SAXReaderUtil.createDocument();
			rootElement = document.addElement("scorm");
		}
		else
		{
			document=SAXReaderUtil.read(learningActivity.getExtracontent());
			rootElement =document.getRootElement();
		}

		Element assetEntry=rootElement.element("assetEntry");
		if(assetEntry!=null)
		{
			assetEntry.detach();
			rootElement.remove(assetEntry);
		}
		assetEntry = SAXReaderUtil.createElement("assetEntry");
		assetEntry.setText(ParamUtil.getString(uploadRequest,"assetEntryId","0"));		
		rootElement.add(assetEntry);	
		
		learningActivity.setExtracontent(document.formattedString());	
	}

}
