<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>

<%@ include file="/init.jsp" %>

<%
	long moduleId = ParamUtil.getLong(request,"moduleId",0);

	if(moduleId != 0)
	{
		Module module = ModuleLocalServiceUtil.getModule(moduleId);
	
		if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),	Module.class.getName(), moduleId, "ADD_LACT")
				|| !ModuleLocalServiceUtil.isLocked(module.getPrimaryKey(),themeDisplay.getUserId())){

			Module previousModule = ModuleLocalServiceUtil.getPreviusModule(moduleId);
			Module nextModule 	  = ModuleLocalServiceUtil.getNextModule(moduleId);
			
			if(previousModule != null){

				LiferayPortletURL  previousModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
				previousModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
				previousModuleURL.removePublicRenderParameter("actId");
				previousModuleURL.setWindowState(WindowState.NORMAL);
				previousModuleURL.setParameter("moduleId", Long.toString(previousModule.getModuleId()));
				previousModuleURL.setParameter("themeId", Long.toString(ParamUtil.getLong(request,"themeId",1)-1));
				previousModuleURL.setPlid(themeDisplay.getPlid());
			%>
				<div id="previousmodule"><a href="<%=previousModuleURL.toString()%>"><liferay-ui:message key="moduleNavigator.previous" /></a></div>
			<%
			}
			
			if(nextModule != null){

				LiferayPortletURL  nextModuleURL = (LiferayPortletURL)renderResponse.createActionURL();	
				nextModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
				nextModuleURL.removePublicRenderParameter("actId");
				nextModuleURL.setWindowState(WindowState.NORMAL);
				nextModuleURL.setParameter("moduleId", Long.toString(nextModule.getModuleId()));
				nextModuleURL.setParameter("themeId", Long.toString(ParamUtil.getLong(request,"themeId",0)+1));
				nextModuleURL.setPlid(themeDisplay.getPlid());
			%>
				<div id="nextmodule"><a href="<%=nextModuleURL.toString()%>"><liferay-ui:message key="moduleNavigator.next" /></a></div>
			<%
			}
		}
	}
%>