<%@page import="com.liferay.lms.service.CompetenceServiceUtil"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<portlet:renderURL var="newcompetenceURL">
<portlet:param name="jspPage" value="/html/competencesadmin/editcompetence.jsp"></portlet:param>
</portlet:renderURL>

<div class="newitem2">

<%
	if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Competence.class.getName(),0L,ActionKeys.UPDATE)){
%>
<liferay-ui:icon image="add" label="<%= true %>"
message="new-competence"
url='<%= newcompetenceURL %>' />
<%
	}
%>

</div>
<liferay-ui:search-container emptyResultsMessage="there-are-no-competences" delta="10">
	<liferay-ui:search-container-results>
<%
long groupId=themeDisplay.getScopeGroupId();
results=CompetenceServiceUtil.getCompetencesOfGroup(groupId,searchContainer.getStart(), searchContainer.getEnd());
total=CompetenceServiceUtil.getCountCompetencesOfGroup(groupId);
pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);

%>
	</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.lms.model.Competence" keyProperty="competenceId" modelVar="competence">
			<liferay-ui:search-container-column-text>
				<%=competence.getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-jsp path="/html/competencesadmin/actions.jsp" align="right" />
		</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />

</liferay-ui:search-container>