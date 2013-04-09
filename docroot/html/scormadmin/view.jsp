<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<portlet:renderURL var="newactivityURL">
<portlet:param name="jspPage" value="/html/scormadmin/editscorm.jsp"></portlet:param>
</portlet:renderURL>
<div class="newitem">
<liferay-ui:icon
image="add"
label="<%= true %>"
message="new"
url='<%= newactivityURL %>'
/>
</div>
<liferay-ui:search-container
 delta="10">
<liferay-ui:search-container-results>
<%
results=SCORMContentLocalServiceUtil.getSCORMContentOfGroup(themeDisplay.getScopeGroupId());
total=results.size();
pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row
className="com.liferay.lms.model.SCORMContent"
keyProperty="scormId"
modelVar="scorm">
<liferay-ui:search-container-column-text>
<liferay-portlet:renderURL var="viewSCORMURL">
<liferay-portlet:param name="jspPage" value="/html/scormadmin/scormdetail.jsp"/>
<liferay-portlet:param name="scormId" value="<%=Long.toString(scorm.getScormId()) %>"/>

</liferay-portlet:renderURL>
<a href="<%=viewSCORMURL%>"><%=scorm.getTitle() %></a>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
<liferay-ui:search-iterator />
</liferay-ui:search-container>