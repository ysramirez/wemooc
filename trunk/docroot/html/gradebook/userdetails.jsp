<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<%
long userId=ParamUtil.getLong(request,"userId",0);
if(userId==0)
{
	userId=themeDisplay.getUserId();
}
String returnurl=ParamUtil.getString(request,"returnurl","");
User usuario=UserLocalServiceUtil.getUser(userId);
java.util.List<LearningActivity> activities = null;

	java.util.List<Module> modules = ModuleLocalServiceUtil
			.findAllInGroup(themeDisplay.getScopeGroupId());
%>
<liferay-ui:header title="Resultados" backURL="<%=returnurl %>"></liferay-ui:header>
<liferay-ui:user-display userId="<%=userId %>">
</liferay-ui:user-display>
<%
	for(Module theModule:modules)
	{

	activities = LearningActivityServiceUtil
			.getLearningActivitiesOfModule(theModule.getModuleId());


%>

<h2><%=theModule.getTitle(themeDisplay.getLocale()) %>
<%
    String mstatus ="not-started";
	ModuleResult mr=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),userId);
    if(mr!=null)
    {
    	mstatus="started";
    	if(mr.getPassed())
    	{
    		mstatus="passed";
    	}
    	else
    	{
    		
    	}
    }
	%>
	<%if(mstatus.equals("passed"))
		{%>
	 <liferay-ui:icon image="checked"></liferay-ui:icon>
	 <%}
	%>
	<%if(mstatus.equals("not-passed"))
		{%>
	 <liferay-ui:icon image="close"></liferay-ui:icon>
	 <%}
	%>
	<%if(mstatus.equals("started"))
		{%>
	 <liferay-ui:icon image="unchecked"></liferay-ui:icon>
	 <%}
	%>
	</h2>
<table class="gradeuser">
<tr>

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

	%>
	<tr>
	
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

%>
</table>

<%
}

%>
