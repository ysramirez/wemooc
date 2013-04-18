<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>

<%@ include file="/html/scormactivity/admin/init.jsp" %>
<%
String advancedKey="ADVANCED";
AssetEntry pentry=null;
String description=learnact.getDescription(themeDisplay.getLocale(),true);
long actId=learnact.getActId();

%>

<aui:model-context bean="<%= learnact %>" model="<%= LearningActivity.class %>" />
<portlet:renderURL var="cancel">
	<portlet:param name="actId" value="0" />
</portlet:renderURL>

<portlet:actionURL var="editresourceURL" name="addfiles">
<portlet:param name="jspPage" value="/html/scormactivity/admin/edit.jsp" />
</portlet:actionURL>
<aui:form name="fm" action="<%=editresourceURL%>"  method="post" enctype="multipart/form-data">
	<aui:fieldset>
		<aui:field-wrapper label="description">
			<liferay-ui:input-editor name="DescripcionRichTxt"  initMethod="initEditor" />
			<div id="<portlet:namespace/>DescripcionRichTxt"></div>
			<aui:input name="description" type="hidden" />
			<script type="text/javascript">
		    <!--
			    function <portlet:namespace />initEditor()
			    {
			    	return "<%=JavaScriptUtil.markupToStringLiteral(description)%>";
			    }
			    
			    function <portlet:namespace />extractCodeFromEditor()
			    {
					try {
						document.<portlet:namespace />fm['<portlet:namespace />description'].value = window['<portlet:namespace />DescripcionRichTxt'].getHTML();
					}
					catch (e) {
					}
			    	
			    }
			    
		        //-->
		    </script>
			<liferay-ui:error key="description-required" message="description-required" />
		</aui:field-wrapper>
		
		<%
		if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("") )
		{
			
			long entryId=Long.valueOf(learnact.getExtracontent());
			AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
			AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());

			AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
			String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);

		%>
			<h2><liferay-ui:message key="selected-content"></liferay-ui:message></h2>
			<liferay-ui:header title="<%=assetRenderer.getTitle(themeDisplay.getLocale())%>"></liferay-ui:header>
			<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
			<%
		}
		if(learnact.getExtracontent()==null||learnact.getExtracontent().trim().equals("")|| Validator.isNumber(learnact.getExtracontent()))
		{
		%>			   
			
	    	<liferay-portlet:renderURL var="searchResource">
				<liferay-portlet:param name="jspPage" value="/html/scormactivity/admin/searchresource.jsp"/>
		 		<liferay-portlet:param value="<%=Long.toString(learnact.getActId()) %>" name="actId"/>
			</liferay-portlet:renderURL>
			<a href="<%=searchResource.toString() %>"><liferay-ui:message key="select-resource"></liferay-ui:message></a>
		<%} %>
	</aui:fieldset>
	<aui:button-row>
		<%
		String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
		%>									
	
		<aui:button type="submit" onClick="<%=extractCodeFromEditor%>"></aui:button>
		<aui:button onClick="<%= cancel %>" type="cancel" />
	</aui:button-row>
</aui:form>