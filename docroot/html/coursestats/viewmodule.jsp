<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.lms.LearningTypesProperties"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@ include file="/init.jsp" %>
<%
long registered=UserLocalServiceUtil.getGroupUsersCount(themeDisplay.getScopeGroupId(),0);
long moduleId=ParamUtil.getLong(request,"moduleId",0);
Module theModule=ModuleLocalServiceUtil.getModule(moduleId);
%>
<portlet:renderURL var="cancelURL" />

<liferay-ui:header backLabel="back" title="<%=theModule.getTitle(themeDisplay.getLocale())%>" backURL="<%=cancelURL.toString() %>"></liferay-ui:header>
	
<div class="registered"><liferay-ui:message key="coursestats.modulestats.inscritos" arguments="<%=new Object[]{registered} %>"></liferay-ui:message>.</div>
<% 
long started=ModuleResultLocalServiceUtil.countByModule(theModule.getModuleId());
	long finished=ModuleResultLocalServiceUtil.countByModulePassed(theModule.getModuleId(),true);
		%>
	<p><liferay-ui:message key="coursestats.modulestats.iniciaron" arguments="<%=new Object[]{started} %>">></liferay-ui:message><br />
	<%if(theModule.getStartDate()!=null)
			{
			%>
		<%=dateFormatDate.format(theModule.getStartDate()) %>
		<%
		}
		%>
		/
			
			<%if(theModule.getEndDate()!=null)
			{
			%>
			<%=dateFormatDate.format(theModule.getEndDate()) %>
			<%
			}
			%>
			
<%
Map<Integer,String> types= new HashMap<Integer,String>(); 
for(String name: LearningTypesProperties.getNames()) //for(LearningType type:types)
{
	String []prop = name.split("\\.");
	if(prop.length-1>0){
		try{
		types.put(Integer.parseInt(LearningTypesProperties.get(name)),
				  prop[prop.length-1].substring(0, 1).toUpperCase()+prop[prop.length-1].substring(1, prop[prop.length-1].length()));
		}
		catch(NumberFormatException e){}
	}
}
request.setAttribute("types", types);

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/coursestats/viewmodule.jsp");
portletURL.setParameter("moduleId", String.valueOf(moduleId)); 
%>			
<liferay-ui:search-container iteratorURL="<%=portletURL%>" deltaConfigurable="true" emptyResultsMessage="activities-empty-results-message" >
	<liferay-ui:search-container-results>
	<%
	int containerStart;
	int containerEnd;
	try {
		containerStart = ParamUtil.getInteger(request, "containerStart");
		containerEnd = ParamUtil.getInteger(request, "containerEnd");
	} catch (Exception e) {
		containerStart = searchContainer.getStart();
		containerEnd = searchContainer.getEnd();
	}
	if (containerStart <=0) {
		containerStart = searchContainer.getStart();
		containerEnd = searchContainer.getEnd();
	}
	
	List<LearningActivity> tempResults = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(moduleId);
	results = ListUtil.subList(tempResults, containerStart, containerEnd);
	total = tempResults.size();

	pageContext.setAttribute("results", results);
	pageContext.setAttribute("total", total);

	request.setAttribute("containerStart",String.valueOf(containerStart));
	request.setAttribute("containerEnd",String.valueOf(containerEnd));
	%>

	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity"
		keyProperty="actId"
		modelVar="activity"
	>
	<%
	long astarted=LearningActivityResultLocalServiceUtil.countStarted(activity.getActId());
	
	
	long afinished=LearningActivityResultLocalServiceUtil.countPassed(activity.getActId());
	long notpassed=LearningActivityResultLocalServiceUtil.countNotPassed(activity.getActId());
	double avgResult=0;
	if(afinished+notpassed>0)
	{
		avgResult=LearningActivityResultLocalServiceUtil.avgResult(activity.getActId());
	}
	double triesPerUser=LearningActivityResultLocalServiceUtil.triesPerUser(activity.getActId());
	
	String title=activity.getTitle(themeDisplay.getLocale());
	int maxNameLength=GetterUtil.getInteger(LanguageUtil.get(pageContext, "coursestats.modulestats.large.name.length"),20);
	
	if(title.length()>maxNameLength) {
		title="<span title='"+title+"'>"+LanguageUtil.format(pageContext, "coursestats.modulestats.large.name", new Object[]{title.subSequence(0, maxNameLength+1)},false)+"</span>";
	}
	
	boolean hasPrecedence = false;
	System.out.println("number of precedence: " + activity.getPrecedence());
	if(activity.getPrecedence() > 0)
		hasPrecedence = true;
		%>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.activity"><%=title %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.activity.start"><%=dateFormatDateTime.format(activity.getStartdate()) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.activity.end"><%=dateFormatDateTime.format(activity.getEnddate()) %></liferay-ui:search-container-column-text>
	
	<liferay-ui:search-container-column-text name="coursestats.modulestats.init"><%=astarted %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.passed"><%=afinished %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.failed"><%=notpassed %></liferay-ui:search-container-column-text>
	
	<liferay-ui:search-container-column-text name="coursestats.modulestats.trials.average"><%=numberFormat.format(triesPerUser) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.marks.average"><%=numberFormat.format(avgResult) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.pass.mark"><%=numberFormat.format(activity.getPasspuntuation()) %></liferay-ui:search-container-column-text>	
	<liferay-ui:search-container-column-text name="coursestats.modulestats.trials.numbers"><%=numberFormat.format(activity.getTries()) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.dependencies"><%=LanguageUtil.get(pageContext, "dependencies."+String.valueOf(hasPrecedence)) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="coursestats.modulestats.type"><%=types.get(activity.getTypeId()) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-jsp name=" " align="right" path="/html/coursestats/viewextras.jsp" />
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator />
	</liferay-ui:search-container>