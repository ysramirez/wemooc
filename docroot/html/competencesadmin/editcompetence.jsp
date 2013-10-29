<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.lms.service.CompetenceLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Competence"%>
<%@ include file="/init.jsp" %>

<portlet:actionURL var="savecompetenceURL" name="saveCompetence" />
<portlet:renderURL var="cancel" />
<liferay-ui:error key="title-required" message="title-required" />
<liferay-ui:error key="title-empty" message="title-empty" />
<liferay-ui:error key="title-repeated" message="title-repeated" />
<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long competenceId=ParamUtil.getLong(request, "competenceId",0);
Competence competence=null;
if(request.getAttribute("competence")!=null)
{
	competence=(Competence)request.getAttribute("competence");
}
else
{
	if(competenceId>0)
	{
		competence=CompetenceLocalServiceUtil.getCompetence(competenceId);
	}
}	
String description="";
if(competence!=null)
{
	description=competence.getDescription(themeDisplay.getLocale(),true);
	
	%>
	<aui:model-context bean="<%= competence %>" model="<%= Competence.class %>" />
	<%
}
else
{
	%>
	<aui:model-context  model="<%= Competence.class %>" />
	<%
}
%>
<aui:form name="fm" action="<%=savecompetenceURL%>"  method="post" >

	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="competenceId" type="hidden" value="<%=competenceId %>"/>
	<aui:input name="title" label="title">
	</aui:input>
	<aui:field-wrapper label="description">
			<liferay-ui:input-editor name="description" width="100%" />
			<aui:input name="description" type="hidden" />
				<script type="text/javascript">
        function <portlet:namespace />initEditor() { return "<%= UnicodeFormatter.toString(description) %>"; }
    </script>
		</aui:field-wrapper>
		<liferay-ui:panel-container>
		<liferay-ui:panel title="categorization" extended="false">
	<liferay-ui:custom-attributes-available className="<%= Competence.class.getName() %>">
		<liferay-ui:custom-attribute-list 
			className="<%=com.liferay.lms.model.Competence.class.getName()%>" classPK="<%=competenceId %>" editable="true" label="true"></liferay-ui:custom-attribute-list>
	</liferay-ui:custom-attributes-available>
	<aui:input name="tags" type="assetTags" />
	<aui:input name="categories" type="assetCategories" />
	</liferay-ui:panel>
	</liferay-ui:panel-container>
	<aui:button-row>
		<aui:button type="submit"></aui:button>							
		<aui:button onClick="<%=cancel %>" type="cancel" />
	</aui:button-row>
</aui:form>