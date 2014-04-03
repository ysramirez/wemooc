<%@ include file="/init.jsp" %>

<portlet:actionURL name="importCourses" var="importCoursesURL" />

<aui:form name="fm" action="<%=importCoursesURL%>"  method="post" enctype="multipart/form-data">
	<aui:fieldset>
		<aui:input inlineLabel="left" inlineField="true" name="fileName" label="" id="fileName" type="file" value="" />
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="submit" value="acept"></aui:button>
	</aui:button-row>
</aui:form>