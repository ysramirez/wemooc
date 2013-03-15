<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<liferay-ui:panel-container >
<%
java.util.List<LearningActivity> activities = null;

	java.util.List<Module> modules = ModuleLocalServiceUtil
			.findAllInGroup(themeDisplay.getScopeGroupId());

	for(Module theModule:modules)
	{

	activities = LearningActivityServiceUtil
			.getLearningActivitiesOfModule(theModule.getModuleId());


%>
<liferay-ui:panel id="<%=Long.toString(theModule.getModuleId()) %>" title="<%=theModule.getTitle(themeDisplay.getLocale()) %>" collapsible="true" extended="false" defaultState="collapsed">

<table>
<tr>
<th>
<liferay-ui:message key="user" />
</th>
<%
for(LearningActivity learningActivity:activities)
{
	%>
	<th>
	<%=learningActivity.getTitle(themeDisplay.getLocale()) %>
	</th>
	<%
}
	%>
	</tr>
<%
for(User usuario:UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId()))
{
	%>
	<liferay-portlet:renderURL var="userDetailsURL">
	<liferay-portlet:param name="jspPage" value="/html/gradebook/userdetails.jsp"></liferay-portlet:param>
	<liferay-portlet:param name="userId" value="<%=Long.toString(usuario.getUserId()) %>"></liferay-portlet:param>
	</liferay-portlet:renderURL>
	<tr>
	<td class="user">
	<liferay-ui:user-display userId="<%=usuario.getUserId() %>" url="<%=userDetailsURL %>"></liferay-ui:user-display>
	</td>
	<%
for(LearningActivity learningActivity:activities)
{
	long result=0;
	String status="not-started";
	if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(learningActivity.getActId(), usuario.getUserId()))
			{
		status="started";
				
			LearningActivityResult learningActivityResult=
				LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), usuario.getUserId());
			result=learningActivityResult.getResult();
			if(learningActivityResult.getEndDate()!=null)
			{
				status="not-passed"	;
			}
			if(learningActivityResult.isPassed())
			{
				status="passed"	;
			}
			
			}
	%>
	<td>
	<%=result %>
	<%if(status.equals("passed"))
		{%>
	 <liferay-ui:icon image="checked"></liferay-ui:icon>
	 <%}
	%>
	<%if(status.equals("not-passed"))
		{%>
	 <liferay-ui:icon image="close"></liferay-ui:icon>
	 <%}
	%>
	<%if(status.equals("started"))
		{%>
	 <liferay-ui:icon image="unchecked"></liferay-ui:icon>
	 <%}
	%>
	</td>
	<%
}
	%>
	</tr>
	<%
}
%>
</table>
</liferay-ui:panel>
<%
}

%>
</liferay-ui:panel-container>