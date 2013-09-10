<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>

<%@ include file="/init.jsp"%>

<%
	long actId = ParamUtil.getLong(request, "resId", 0);
	LearningActivity learnActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
%>

<h2><%=learnActivity.getTitle(themeDisplay.getLocale()) %></h2>

<portlet:renderURL var="calificacionesURL">
	<portlet:param name="jspPage" value="/html/lmsactivitieslist/califications.jsp"></portlet:param>
</portlet:renderURL>

<liferay-ui:search-container deltaConfigurable="true" emptyResultsMessage="there-are-no-results" delta="25">

   	<liferay-ui:search-container-results>
		<%
			List<LearningActivityResult> learnResults = LearningActivityResultLocalServiceUtil.getByActId(actId);

			pageContext.setAttribute("results", learnResults);
		    pageContext.setAttribute("total", results.size());
		%>
	</liferay-ui:search-container-results>
	
	<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivityResult" keyProperty="larId" modelVar="result">
		
		<%
			User usu = UserLocalServiceUtil.getUser(result.getUserId());
		%>

		<liferay-ui:search-container-column-text name="user"		><%=usu.getFullName() %> </liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="result"		><%=result.getResult() %> </liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="passed"		><%=result.getPassed()?LanguageUtil.get(themeDisplay.getLocale(),"passed"):LanguageUtil.get(themeDisplay.getLocale(),"not-passed") %> </liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="start-date"	><%=result.getStartDate() %> </liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="end-date" 	><%=result.getEndDate() %> </liferay-ui:search-container-column-text>
		
	</liferay-ui:search-container-row>
	
 	<liferay-ui:search-iterator />
 	
</liferay-ui:search-container>

