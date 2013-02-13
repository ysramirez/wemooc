package com.liferay.lms.asset;

import javax.portlet.PortletURL;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;

public class LearningActivityAssetRendererFactory extends BaseAssetRendererFactory
 {

	public static final String CLASS_NAME = LearningActivity.class.getName();
	public static final String TYPE = "learningactivity";

	@Override
	public String getClassName() {
	
		return CLASS_NAME;
	}

	public AssetRenderer getAssetRenderer(long classPK, int type)
	throws PortalException, SystemException {
	LearningActivity learningactivity = LearningActivityLocalServiceUtil.getLearningActivity(classPK);
		if(learningactivity.getTypeId()==0)
		{
		return new TestAssetRenderer(learningactivity);
		}
		if(learningactivity.getTypeId()==1)
		{
		return new TaskAssetRenderer(learningactivity);
		}
		if(learningactivity.getTypeId()==2)
		{
		return new ResourceAssetRenderer(learningactivity);
		}
		if(learningactivity.getTypeId()==3)
		{
		return new TaskP2PAssetRenderer(learningactivity);
		}
		if(learningactivity.getTypeId()==4)
		{
		return new SurveyAssetRenderer(learningactivity);
		}
		return null;
	}
	public PortletURL getURLAdd(
            LiferayPortletRequest liferayPortletRequest,
            LiferayPortletResponse liferayPortletResponse)
        {
		
        return null;
/*
        HttpServletRequest request =
            liferayPortletRequest.getHttpServletRequest();

        ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
            WebKeys.THEME_DISPLAY);

      try
      {
    	  PortletURL portletURL= 
    			  PortletURLFactoryUtil.create(request,"lmsactivitieslist_WAR_liferaylmsportlet",getControlPanelPlid(themeDisplay), PortletRequest.RENDER_PHASE);
          portletURL.setParameter("mvcPath", "/html/lmsactivitieslist/editactivity.jsp");
         return portletURL;
      }
      catch(Exception e)
      {
    	  e.printStackTrace();
    	  
      }
        return null;
        */
    }
	@Override
	public boolean hasPermission(PermissionChecker permissionChecker,
			long classPK, String actionId) throws Exception {
	
		//eturn permissionChecker.hasPermission(theme, LearningActivity.class.getName(), 0,
		//		actionId);
		return true;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	



}
