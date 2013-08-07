<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.model.ResourcePermission"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.Resource"%>
<%@page import="com.liferay.portal.service.ResourceActionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ResourceLocalServiceUtil"%>
<%@page import="com.liferay.portlet.social.service.SocialRelationLocalServiceUtil"%>
<%@page import="com.liferay.portlet.social.model.SocialRelationConstants"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<portlet:actionURL var="savescormURL" name="saveSCORM" />
<portlet:renderURL var="cancel" />

<script type="text/javascript" >
<!--
setTimeout(resizeThisFrame, 500);

function resizeThisFrame() {
	if ((!!window.postMessage)&&(window.parent != window)) {
		if (!window.location.origin){
			var nada = '';
			window.location.origin = window.location.protocol+"/"+nada+"/"+window.location.host;
		}
		var bodyHeight = document.getElementsByTagName('body')[0].offsetHeight;
		parent.postMessage({name:'resizeIframe', height : bodyHeight}, window.location.origin);
	}
	setTimeout(resizeThisFrame, 500);
}
//-->
</script>
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
<liferay-ui:error key="scormadmin.error.nozip" message="scormadmin.error.nozip"/>
<liferay-ui:error key="scormadmin.error.nomanifest" message="scormadmin.error.nomanifest"/>
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
<aui:field-wrapper name="permissions">
		<liferay-ui:input-permissions modelName="<%= SCORMContent.class.getName() %>">
		</liferay-ui:input-permissions>
	</aui:field-wrapper>
	</c:if>
	<aui:field-wrapper label="ciphered.options">
		<aui:input inlineLabel="right" inlineField="true" name="ciphered" label="ciphered" id="ciphered" type="checkbox" value="<%= scormId == 0 ? false : scorm.getCiphered() %>"/>
	</aui:field-wrapper>
	
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
	

<c:if test="<%= permissionChecker.hasPermission(scorm.getGroupId(), SCORMContent.class.getName(), scorm.getScormId(),
ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="<%=SCORMContent.class.getName() %>"
					modelResourceDescription="<%= scorm.getTitle() %>"
					resourcePrimKey="<%= String.valueOf(scorm.getScormId()) %>"
					var="permissionsURL"
				/>
				
				<aui:button href="<%=permissionsURL.toString() %>" value="courseadmin.adminactions.permissions" />	
			</c:if>

<%
	}
%>
<portlet:actionURL name="deleteSCORM" var="deleteURL">
<portlet:param name="scormId" value="<%= Long.toString(scormId )%>" />
<portlet:param name="redirect" value="<%= redirect%>" />

</portlet:actionURL>
<%
if(scormId > 0 && permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  SCORMContent.class.getName(),scormId,ActionKeys.DELETE))
{
%>
<aui:button href="<%=deleteURL.toString() %>" value="delete" />
<%
}
%>
		<aui:button onClick="<%=cancel %>" type="cancel" />
	</aui:button-row>
		
		
	</aui:form>

	