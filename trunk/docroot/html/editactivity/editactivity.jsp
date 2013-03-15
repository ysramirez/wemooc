<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistry"%>
<%@page import="com.liferay.lms.asset.LearningActivityAssetRendererFactory"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
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



<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");
long typeId=ParamUtil.getLong(request, "typeId");
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[0], themeDisplay.getLocale());

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long actId=ParamUtil.getLong(request, "actId",0);
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null)
{
	learnact=(LearningActivity)request.getAttribute("activity");
	typeId=learnact.getTypeId();
}
else
{
	if(actId>0)
	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	}
	
}
ParamUtil.print(request);
String typeName=classTypes.get(typeId);
LearningActivityType larntype=new LearningActivityTypeRegistry().getLearningActivityType(typeId);

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
%>
<liferay-ui:header title="<%=typeName %>"></liferay-ui:header>
<%
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
	<aui:input type="hidden" name="type" value="<%=typeId %>"></aui:input>
	<% 
		}
	%>

		<aui:input name="title" label="title"></aui:input>
		<liferay-ui:error key="title-required" message="title-required" />
	<aui:field-wrapper label="description">
			<liferay-ui:input-editor name="description" width="100%" />
			<aui:input name="description" type="hidden" />
				<script type="text/javascript">
        function <portlet:namespace />initEditor() { return "<%= UnicodeFormatter.toString(description) %>"; }
    </script>
		</aui:field-wrapper>
		    <liferay-ui:error key="description-required" message="description-required" />
	
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
		<%
		if(larntype.isTriesConfigurable())
		{
			long tries=larntype.getDefaultTries();
			if(learnact!=null)
			{
				tries=learnact.getTries();
			}
		%>
		
		<aui:input size="5" name="tries" label="tries" value="<%=Long.toString(tries) %>"></aui:input><liferay-ui:icon-help message="number-of-tries"></liferay-ui:icon-help>
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="tries" value="<%=larntype.getDefaultTries() %>" />
			<% 
		}
		if(larntype.isScoreConfigurable())
		{
			long score=larntype.getDefaultScore();
			if(learnact!=null)
			{
				score=learnact.getPasspuntuation();
			}
		%>
		<aui:input size="5" name="passpuntuation" label="passpuntuation" value="<%=Long.toString(score) %>"></aui:input>
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="passpuntuation" value="<%=larntype.getDefaultScore() %>" />
			<% 
		}
		if(larntype.isFeedbackCorrectConfigurable())
		{
			String  feedbacCorrect=larntype.getDefaultFeedbackCorrect();
			if(learnact!=null)
			{
				feedbacCorrect=learnact.getFeedbackCorrect();
			}
		%>	
		<aui:input name="feedbackCorrect" label="feedbackCorrect" value="<%=feedbacCorrect %>" ></aui:input>	
		<%
		}
		else
		{
			
			%>
			<aui:input type="hidden" name="feedbackCorrect" value="<%=larntype.getDefaultFeedbackCorrect() %>" />
			<% 
		}
		if(larntype.isFeedbackNoCorrectConfigurable())
		{
			String  feedbacNoCorrect=larntype.getDefaultFeedbackCorrect();
			if(learnact!=null)
			{
				feedbacNoCorrect=learnact.getFeedbackCorrect();
			}
		%>
		<aui:input name="feedbackNoCorrect" label="feedbackNoCorrect" value="<%=feedbacNoCorrect %>" ></aui:input>	
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="feedbackNoCorrect" value="<%=larntype.getDefaultFeedbackNoCorrect() %>" />
			<% 
		}
		
		%>
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