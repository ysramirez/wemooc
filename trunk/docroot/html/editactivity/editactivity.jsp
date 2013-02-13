<%@page import="java.util.Set"%>
<%@page import="com.liferay.lms.LearningTypesProperties"%>
<%@page import="com.liferay.lms.model.LearningType"%>
<%@page import="com.liferay.lms.service.LearningTypeLocalServiceUtil"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp" %>

<portlet:actionURL var="saveactivityURL" name="saveActivity" />

<liferay-util:buffer var="inputEditorHTML" >
	<liferay-ui:input-editor />
</liferay-util:buffer>

<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long actId=ParamUtil.getLong(request, "actId",0);
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null)
{
	learnact=(LearningActivity)request.getAttribute("activity");
}
else
{
	if(actId>0)
	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	}
	
}
ParamUtil.print(request);


String description="";
SimpleDateFormat formatDay    = new SimpleDateFormat("dd");
SimpleDateFormat formatMonth    = new SimpleDateFormat("MM");
SimpleDateFormat formatYear    = new SimpleDateFormat("yyyy");
SimpleDateFormat formatHour   = new SimpleDateFormat("HH");
SimpleDateFormat formatMin = new SimpleDateFormat("mm");
Date today=new Date(System.currentTimeMillis());
int startDay=Integer.parseInt(formatDay.format(today));
int startMonth=Integer.parseInt(formatMonth.format(today))-1;
int startYear=Integer.parseInt(formatYear.format(today));
int startHour=Integer.parseInt(formatHour.format(today));
int startMin=Integer.parseInt(formatMin.format(today));
int endDay=Integer.parseInt(formatDay.format(today));
int endMonth=Integer.parseInt(formatMonth.format(today))-1;
int endYear=Integer.parseInt(formatYear.format(today))+1;
int endHour=Integer.parseInt(formatHour.format(today));
int endMin=Integer.parseInt(formatMin.format(today));
if(learnact!=null)
{
	actId=learnact.getActId();
	description=learnact.getDescription(themeDisplay.getLocale());
	startDay=Integer.parseInt(formatDay.format(learnact.getStartdate()));
	startMonth=Integer.parseInt(formatMonth.format(learnact.getStartdate()))-1;
	startYear=Integer.parseInt(formatYear.format(learnact.getStartdate()));
	startHour=Integer.parseInt(formatHour.format(learnact.getStartdate()));
	startMin=Integer.parseInt(formatMin.format(learnact.getStartdate()));
	endDay=Integer.parseInt(formatDay.format(learnact.getEnddate()));
	endMonth=Integer.parseInt(formatMonth.format(learnact.getEnddate()))-1;
	endYear=Integer.parseInt(formatYear.format(learnact.getEnddate()));
	endHour=Integer.parseInt(formatHour.format(learnact.getEnddate()));
	endMin=Integer.parseInt(formatMin.format(learnact.getEnddate()));
	%>

	<portlet:actionURL name="editactivity" var="editURL">
		<portlet:param name="actId" value="<%=Long.toString(learnact.getActId()) %>" />
	</portlet:actionURL>

	<portlet:actionURL name="deleteMyTries" var="deleteMyTriesURL">
		<portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
		<portlet:param name="actId" value="0" />
		<portlet:param name="redirect" value="<%=redirect %>" />
		<portlet:param name="backURL" value="<%=backURL%>" />
	</portlet:actionURL>

	<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>
	
	<div class="acticons"> 
		<liferay-ui:icon image="edit" message="edit-activity-details" label="true" url="<%=editURL.toString() %>" />
		<liferay-ui:icon-delete label="false" url="<%=deleteMyTriesURL.toString() %>" />
		<a href="<%=deleteMyTriesURL.toString() %>"><liferay-ui:message key="delete-mi-tries"></liferay-ui:message></a>
	</div>
	
	<aui:model-context bean="<%= learnact %>" model="<%= LearningActivity.class %>" />

<%
}
else
{
	%>
	<aui:model-context  model="<%= LearningActivity.class %>" />
	<%
}
%>
<aui:form name="fm" action="<%=saveactivityURL%>"  method="post">
	<aui:fieldset>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="actId" type="hidden" value="<%=actId %>"/>
		<aui:select label="module" name="moduleId">
	<%
	java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	for(Module theModule:modules)
	{
		boolean selected=false;
		if(learnact!=null && learnact.getModuleId()==theModule.getModuleId())
		{
			selected=true;
		}
		else
		{
			if(theModule.getModuleId()==moduleId)
			{
				selected=true;
			}
		}
		%>
			<aui:option value="<%=theModule.getModuleId() %>" selected="<%=selected %>"><%=theModule.getTitle() %></aui:option>
		<% 
	}
%>

		</aui:select>

<%
	if(actId==0)
	{
	%>
		<aui:select name="type" label="type">
		<%
		Set<String> names = LearningTypesProperties.getNames();
		for(String name: names) //for(LearningType type:types)
		{
			String []prop = name.split("\\.");
			if(prop.length-1>0){
				String word = prop[prop.length-1].substring(0, 1).toUpperCase()+prop[prop.length-1].substring(1, prop[prop.length-1].length());
			
		%>	
			<aui:option value="<%=LearningTypesProperties.get(name) %>" label="<%=word %>"></aui:option>
		<%
			}
		}
		%>
		</aui:select>
	<% 
		}
	%>

		<aui:input name="title" label="title"></aui:input>
		<liferay-ui:error key="title-required" message="title-required" />
		<aui:field-wrapper label="description">
			<div id="<portlet:namespace/>DescripcionRichTxt"></div>
			<aui:input name="description" type="hidden" />
			
			<script type="text/javascript">
		    <!--
			    function <portlet:namespace />initEditor()
			    {
			    	return "<%=JavaScriptUtil.markupToStringLiteral(description)%>";
			    }
			
			    function <portlet:namespace />extractCodeFromEditor()
			    {
			    	document.<portlet:namespace />fm.<portlet:namespace />description.value = window.<portlet:namespace />editor.getHTML();
			    }
			    var func = function ()
			    {
			    	var elem = document.getElementById("<portlet:namespace/>DescripcionRichTxt");
			    	elem.innerHTML = "<%=JavaScriptUtil.markupToStringLiteral(inputEditorHTML)%>";
			    };
			
			    AUI().on('domready', func);
		        //-->
		    </script>
		    <liferay-ui:error key="description-required" message="description-required" />
		</aui:field-wrapper>
		<aui:field-wrapper label="start-date">
			<liferay-ui:input-date yearRangeEnd="2020" yearRangeStart="2012"  dayParam="startDay" monthParam="startMon"
				 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>"></liferay-ui:input-time>
		</aui:field-wrapper>
		<aui:field-wrapper label="end-date">
			<liferay-ui:input-date yearRangeEnd="2020" yearRangeStart="2012" dayParam="stopDay" monthParam="stopMon"
				 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>"></liferay-ui:input-time></br>
		</aui:field-wrapper>
		<aui:input size="5" name="tries" label="tries" ></aui:input>
		<aui:input size="5" name="passpuntuation" label="passpuntuation" ></aui:input>	
		<aui:input name="feedbackCorrect" label="feedbackCorrect" ></aui:input>	
		<aui:input name="feedbackNoCorrect" label="feedbackNoCorrect" ></aui:input>	
<%
	boolean optional=false;
	if(learnact!=null)
	{
		optional=(learnact.getWeightinmodule()==0);
	}
%>
		<aui:select name="weightinmodule" label="Optional">
<%
	if(optional)
	{
%>
			<aui:option value="0" selected="true">true</aui:option>
			<aui:option value="1">false</aui:option>
<%
	}
	else
	{
%>
			<aui:option value="0">true</aui:option>
			<aui:option value="1"  selected="true">false</aui:option>
<%
	}
%>
		</aui:select>
		<aui:select label="bloquing-activity" name="precedence">
<%
	java.util.List<LearningActivity> activities=null;
	if(learnact!=null&&learnact.getModuleId()>0)
	{
		activities=LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(learnact.getModuleId());
	}
	if(activities==null)
	{
		activities=new ArrayList<LearningActivity>();
	}
%>
			<aui:option value="0" >Ninguna</aui:option>
<%
	for(LearningActivity activity:activities)
	{
		if(activity.getActId()!=learnact.getActId())
		{
			boolean selected=false;
			if(learnact.getPrecedence()>0 && learnact.getPrecedence()==activity.getActId())
			{
				
				selected=true;
			}
		%>
			<aui:option value="<%=activity.getActId() %>" selected="<%=selected %>"><%=activity.getTitle(themeDisplay.getLocale()) %></aui:option>
		<% 
		}
	}
%>
		</aui:select>
		<aui:input name="tags" type="assetTags" />
		<aui:input name="categories" type="assetCategories" />
	</aui:fieldset>
	
		
	<portlet:renderURL var="cancelURL">
		<portlet:param name="actId" value="<%=Long.toString(actId) %>"></portlet:param>
		<portlet:param name="moduleId" value="<%=Long.toString(moduleId) %>"></portlet:param>
		<portlet:param name="jspPage" value="/html/editactivity/view.jsp" />
	</portlet:renderURL>
	
	
	<aui:button-row>
		<% 
		String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
		%>									

		<aui:button type="submit" onClick="<%=extractCodeFromEditor%>"></aui:button>
		<aui:button onClick="<%=cancelURL %>" value="<%=LanguageUtil.get(pageContext,\"cancel\")%>" type="cancel" />
	</aui:button-row>
</aui:form>