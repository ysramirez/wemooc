<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<aui:select label="bloquing-activity" name="precedence">
<%
	long actId=ParamUtil.getLong(renderRequest, "resId", 0);
	long moduleId=ParamUtil.getLong(renderRequest, "resModuleId", 0);
	long precedence=ParamUtil.getLong(renderRequest, "precedence", 0);

	List<LearningActivity> activities=null;
	if(moduleId>0)
	{
		activities=LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
	}
	else
	{
		activities=new ArrayList<LearningActivity>();
	}
%>
			<aui:option value="0" >Ninguna</aui:option>
<%
	for(LearningActivity activity:activities)
	{
		if(activity.getActId()!=actId)
		{
		%>
			<aui:option value="<%=activity.getActId() %>" selected="<%=(precedence==activity.getActId()) %>"><%=activity.getTitle(themeDisplay.getLocale()) %></aui:option>
		<% 
		}
	}
%>
</aui:select>