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
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>

<%@ include file="/init.jsp" %>

<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
long actId=ParamUtil.getLong(request,"actId",0);
boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

	java.util.List<LearningActivity> activities=new java.util.ArrayList<LearningActivity>();
	if(moduleId==0)
	{
		if(actId!=0)
		{
			LearningActivity larn=LearningActivityLocalServiceUtil.getLearningActivity(actId);
			moduleId=larn.getModuleId();
		}
	}
	if(moduleId!=0)
	{
		Module theModule=ModuleLocalServiceUtil.getModule(moduleId);
	
		if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),	Module.class.getName(), moduleId, "ADD_LACT")|| !ModuleLocalServiceUtil.isLocked(theModule.getPrimaryKey(),themeDisplay.getUserId()))
		{
			activities=LearningActivityServiceUtil.getLearningActivitiesOfModule(moduleId);
		}
	}
	if(ModuleLocalServiceUtil.isUserPassed(moduleId,themeDisplay.getUserId()))
	{
	%>
	<div id="modulegreetings"><liferay-ui:message key="module-finissed-greetings" /></div>
	<%
	}
if(actId>0)
{

	LearningActivity prevActivity=null;
	boolean writeNext=false;
	for(LearningActivity activity:activities)
	{
		if(writeNext)
		{
			%>
			<portlet:actionURL name="viewactivity" var="viewnextURL">
			
<portlet:param name="actId" value="<%=Long.toString(activity.getActId()) %>" />
</portlet:actionURL>
<% 
	if(LearningActivityLocalServiceUtil.islocked(activity.getActId(),themeDisplay.getUserId())&&!permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK"))
			{
		%>
		<div id="nextactivity" class="locked"><span><liferay-ui:message key="next" /></span></div>
		<%
			}
	else
	{
		%>
			<div id="nextactivity"><a href="<%=viewnextURL.toString()%>"><liferay-ui:message key="next" /></a></div>
			
			<%
	}
			break;
		}
		else
		{
			if(activity.getActId()==actId)
			{
				writeNext=true;
				if(prevActivity!=null)
				{
				%>
				<portlet:actionURL name="viewactivity" var="viewURL">
				<portlet:param name="actId" value="<%=Long.toString(prevActivity.getActId()) %>" />
				</portlet:actionURL>
				<%
				if(LearningActivityLocalServiceUtil.islocked(prevActivity.getActId(),themeDisplay.getUserId())&&!permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK"))
				{
					%>
					<div id="previusactivity" class="locked"><span><liferay-ui:message key="prev" /></span></div>
					
	<%	
			}
				else
				{
		%>
							<div id="previusactivity"><a href="<%=viewURL.toString()%>"><liferay-ui:message key="prev" /></a></div>
							
			<%
				}
			}			
			}
			
		}
		prevActivity=activity;
	}
}
else
{
	if(activities.size()>0)
	{
		LearningActivity activity=activities.get(0);
		%>
<portlet:actionURL name="viewactivity" var="viewstartURL">
<portlet:param name="actId" value="<%=Long.toString(activity.getActId()) %>" />
</portlet:actionURL>
			<div id="startactivity"><a href="<%=viewstartURL.toString()%>"><liferay-ui:message key="start" /></a></div>
		
		<%
	}
}
%>