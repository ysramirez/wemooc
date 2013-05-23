package com.liferay.lms;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.ProcessEvent;

import com.liferay.lms.events.ThemeIdEvent;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ModuleTitle extends MVCPortlet {
    @ProcessEvent(qname = "{http://www.wemooc.com/}themeId")
    public void handlethemeEvent(EventRequest eventRequest, EventResponse eventResponse) {
       if (eventRequest.getEvent().getValue() instanceof ThemeIdEvent){
    	   ThemeIdEvent themeIdEvent = (ThemeIdEvent) eventRequest.getEvent().getValue();
    	   if(ParamUtil.getLong(eventRequest, "moduleId",0L)==themeIdEvent.getModuleId()){
    		   eventResponse.setRenderParameter("themeId",Long.toString(themeIdEvent.getThemeId()));
    	   }    	   
       }
    }

}
