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
	
<div class="registered">Hay <%=registered %> usuarios inscritos.</div>
<% 
long started=ModuleResultLocalServiceUtil.countByModule(theModule.getModuleId());
	long finished=ModuleResultLocalServiceUtil.countByModulePassed(theModule.getModuleId(),true);
		%>
	<p>Iniciaron:<%=started %><br />
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
<liferay-ui:search-container  deltaConfigurable="true" emptyResultsMessage="activities-empty-results-message" >
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
		%>
	<liferay-ui:search-container-column-text name="Actividad"><%=activity.getTitle(themeDisplay.getLocale()) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="Inicio Actividad"><%=dateFormatDateTime.format(activity.getStartdate()) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="Fin Actividad"><%=dateFormatDateTime.format(activity.getEnddate()) %></liferay-ui:search-container-column-text>
	
	<liferay-ui:search-container-column-text name="Iniciaron"><%=astarted %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="Aprobaron"><%=afinished %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="Suspendieron"><%=notpassed %></liferay-ui:search-container-column-text>
	
	<liferay-ui:search-container-column-text name="Media de intentos"><%=numberFormat.format(triesPerUser) %></liferay-ui:search-container-column-text>
	<liferay-ui:search-container-column-text name="Nota Media"><%=numberFormat.format(avgResult) %></liferay-ui:search-container-column-text>
	
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator />
	</liferay-ui:search-container>