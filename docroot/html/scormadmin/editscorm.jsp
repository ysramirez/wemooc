<%@page import="com.liferay.portlet.social.service.SocialRelationLocalServiceUtil"%>
<%@page import="com.liferay.portlet.social.model.SocialRelationConstants"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<portlet:actionURL var="savescormURL" name="saveSCORM" />
<portlet:renderURL var="cancel" />


<%
String redirect = ParamUtil.getString(request, "redirect","");
String backURL = ParamUtil.getString(request, "backURL");
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long scormId=ParamUtil.getLong(request, "scormId",0);
String description="";
SCORMContent scorm=null;
if(scormId>0)
{
	scorm=SCORMContentLocalServiceUtil.getSCORMContent(scormId);
	%>
	<aui:model-context bean="<%= scorm %>" model="<%= SCORMContent.class %>" />
	
	<%
	description=scorm.getDescription();
}
else
{
	%>
	<aui:model-context  model="<%= SCORMContent.class %>" />
	
	<%
}
%>

<aui:form name="fm" action="<%=savescormURL%>"  method="post" enctype="multipart/form-data">

	<aui:input type="hidden" name="scormId" value="<%=scormId %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="scormId" type="hidden" value="<%=scormId %>"/>
	<aui:input name="title" label="title"></aui:input>
	<aui:field-wrapper label="description">
			<liferay-ui:input-editor name="description" width="100%" />
			<aui:input name="description" type="hidden" />
				<script type="text/javascript">
        function <portlet:namespace />initEditor() { return "<%= UnicodeFormatter.toString(description) %>"; }
    </script>
    </aui:field-wrapper>
    <c:if test="<%=scormId==0%>">

		<aui:field-wrapper label="zipfile">
		<aui:input inlineLabel="left" inlineField="true"
		  	name="fileName" label="" id="fileName" type="file" value="" />
</aui:field-wrapper>	
	</c:if>
	
		<liferay-ui:custom-attributes-available className="<%= SCORMContent.class.getName() %>">
		<liferay-ui:custom-attribute-list 
			className="<%=com.liferay.lms.model.SCORMContent.class.getName()%>" classPK="<%=scormId %>" editable="true" label="true"></liferay-ui:custom-attribute-list>
	</liferay-ui:custom-attributes-available>
	<aui:input name="tags" type="assetTags" />
	<aui:input name="categories" type="assetCategories" />
	<aui:button-row>
	<%
		String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
	%>									
		<aui:button type="submit" onClick="<%=extractCodeFromEditor%>"></aui:button>
			<%if(scormId>0) 
	{
	%>
	<portlet:actionURL name="deleteSCORM" var="deleteURL">
<portlet:param name="scormId" value="<%= Long.toString(scormId )%>" />
<portlet:param name="redirect" value="<%= redirect%>" />

</portlet:actionURL>
<%
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  SCORMContent.class.getName(),scormId,ActionKeys.DELETE))
{
%>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
<%
}
%>

<c:if test="<%= permissionChecker.hasPermission(scorm.getGroupId(), SCORMContent.class.getName(), scorm.getScormId(),
ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%=SCORMContent.class.getName() %>"
					modelResourceDescription="<%= scorm.getTitle() %>"
					resourcePrimKey="<%= String.valueOf(scorm.getScormId()) %>"
					var="permissionsURL"
				/>
				<liferay-ui:icon image="permissions" message="courseadmin.adminactions.permissions" url="<%=permissionsURL %>" />			
			</c:if>

<%
	}
%>
		<aui:button onClick="<%=cancel %>" type="cancel" />
	</aui:button-row>
		
		
	</aui:form>

	