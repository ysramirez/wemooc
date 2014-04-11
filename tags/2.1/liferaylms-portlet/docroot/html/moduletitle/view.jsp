<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
	long moduleId=ParamUtil.getLong(request,"moduleId",0);
	long currentModuleId=0;
	long actId=ParamUtil.getLong(request,"actId",0);
	long themeId=ParamUtil.getLong(request,"themeId");
	boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);
	Module theModule=null;
	if(moduleId!=0)
	{
		theModule=ModuleLocalServiceUtil.getModule(moduleId);
	}
	else
	{
		if(actId!=0)
		{
			LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
			theModule=ModuleLocalServiceUtil.getModule(larn.getModuleId());
		}
		else
		{
			List<Module> modules=(List<Module>)ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			if(modules.size()>0)
			{
				theModule=modules.get(0);
				themeId=1;
			}
		}
	}
	if(theModule!=null)
	{
		if(GetterUtil.getBoolean(PropsUtil.get("module.show.number"),true)) {
			if(themeId==GetterUtil.DEFAULT_INTEGER) {
				for(Module module:(List<Module>)ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId())) {
					themeId++;
					if(module.getModuleId()==theModule.getModuleId()) {
						break;
					}
				}
			}
			%>
				<liferay-ui:header title="<%=LanguageUtil.format(pageContext, \"moduleTitle.chapter2\", new Object[]{themeId,theModule.getTitle(themeDisplay.getLocale())}) %>" />
			<%
		}
		else {
			%>
				<liferay-ui:header title="<%=theModule.getTitle(themeDisplay.getLocale()) %>" />
			<%	
		}
	}
	else
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}

%>