package com.liferay.lms.learningactivity;

import java.io.IOException;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.lms.asset.ResourceExternalAssetRenderer;
import com.liferay.lms.model.LearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;

public class ResourceExternalLearningActivityType extends BaseLearningActivityType 
{

	@Override
	public boolean gradebook() {
		return false;
	}


	@Override
	public long getDefaultScore() {
		return 0;
	}


	@Override
	public String getName(Locale locale) {
		
		return "resourceExternal";
	}


	@Override
	public AssetRenderer getAssetRenderer(LearningActivity learningactivity) {
		
		return new ResourceExternalAssetRenderer(learningactivity);
	}


	@Override
	public long getTypeId() {
		return 2;
	}
	
	@Override
	public String getExpecificContentPage() {
		return "/html/resourceExternalActivity/admin/edit.jsp";
	}
	
	@Override
	public boolean hasEditDetails() {
		return false;
	}

	
	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, LearningActivity learningActivity)
			throws PortalException, SystemException, DocumentException,IOException {
		String youtubecode=ParamUtil.getString(uploadRequest,"youtubecode");
		if(!StringPool.BLANK.equals(youtubecode.trim())){
			Document document = null;
			Element rootElement = null;
			if((learningActivity.getExtracontent()==null)||(learningActivity.getExtracontent().trim().length()==0)){
				document = SAXReaderUtil.createDocument();
				rootElement = document.addElement("multimediaentry");
			}
			else
			{
				document=SAXReaderUtil.read(learningActivity.getExtracontent());
				rootElement =document.getRootElement();
			}
			
			Element video=rootElement.element("video");
			if(video!=null)
			{
				video.detach();
				rootElement.remove(video);
			}
			video = SAXReaderUtil.createElement("video");
			video.setText(youtubecode);		
			rootElement.add(video);
			learningActivity.setExtracontent(document.formattedString());	
		}
		
	}
		
}
