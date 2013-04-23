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

<%@ include file="/html/resourceExternalActivity/admin/init.jsp" %>
<liferay-portlet:actionURL portletName="editactivity_WAR_liferaylmsportlet" name="editactivityoptions" var="backURL" >
	<liferay-portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
	<liferay-portlet:param name="resModuleId" value="<%=Long.toString(learnact.getModuleId()) %>" />
</liferay-portlet:actionURL>
<liferay-ui:icon image="back" message="back" label="true" url="<%=backURL.toString() %>" />

<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>

<%
String advancedKey="ADVANCED";
AssetEntry pentry=null;
String description=learnact.getDescription(themeDisplay.getLocale(),true);
long actId=learnact.getActId();
if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("") )
{
	/*
	long pentryId=Long.valueOf(learnact.getExtracontent());
	//pentry=AssetEntryLocalServiceUtil.getEntry(entryId);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());

	//AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
	//String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);
	*/

}

%>

<aui:model-context bean="<%= learnact %>" model="<%= LearningActivity.class %>" />

<portlet:actionURL var="editresourceURL" name="addfiles">
	<portlet:param name="jspPage" value="/html/resourceExternalActivity/admin/edit.jsp" />
	<portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
	<portlet:param name="actionEditingDetails" value="true" />
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
		if(learnact.getExtracontent()==null||learnact.getExtracontent().trim().equals("")|| !Validator.isNumber(learnact.getExtracontent()))
		{
			String youtubecode="";
			DLFileVersion previusvideo=null;
			DLFileVersion previusaditionalfile=null;
			if(learnact.getExtracontent()!=null&&!learnact.getExtracontent().trim().equals(""))
			{
			Document document = SAXReaderUtil.read(learnact.getExtracontent());
			Element root=document.getRootElement();
			Element video=root.element("video");
			
			if(video!=null)
			{
				if(!video.attributeValue("id","").equals(""))
				{
					AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
					DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
					DLFileVersion videofileVersion = videofile.getFileVersion();
					String videoURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + videofileVersion.getGroupId() + StringPool.SLASH + videofileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(videofileVersion.getTitle()));
					previusvideo=videofileVersion;
				}
				else
				{
					youtubecode=video.getText();
				}
			}
			Element documento=root.element("document");
			if(documento!=null)
			{
				if(!documento.attributeValue("id","").equals(""))
				{
				AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
				DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
				DLFileVersion docfileVersion = docfile.getFileVersion();
				previusaditionalfile=docfileVersion;
				String docURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + docfileVersion.getGroupId() + StringPool.SLASH + docfileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(docfileVersion.getTitle()));
			
				}
			}
			}
		%>   
		       
        <aui:field-wrapper label="video" >
		  		<aui:input name="youtubecode" type="textarea" rows="6" cols="45" label="youtube-code" value="<%=youtubecode %>"></aui:input>
		</aui:field-wrapper>
	  
		<%
		}
		%>		
	</aui:fieldset>
	<aui:button-row>
		<%
		String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
		%>									
	
		<aui:button type="submit" onClick="<%=extractCodeFromEditor%>"></aui:button>
		
		<script type="text/javascript">
		<!--
		
		Liferay.provide(
		        window,
		        '<portlet:namespace />closeWindow',
		        function() {
			        
					if ((!!window.postMessage)&&(window.parent != window)) {
						parent.postMessage({name:'closeActivity',
							                moduleId:<%=Long.toString(learnact.getModuleId())%>,
							                actId:<%=Long.toString(actId)%>}, window.location.origin);
					}
					else {
						window.location.href='<portlet:renderURL />';
					}
		        }
		    );
		    
		//-->
		</script>
		<aui:button onClick="<%=renderResponse.getNamespace()+\"closeWindow()\" %>" value="<%=LanguageUtil.get(pageContext,\"cancel\")%>" type="cancel" />
	</aui:button-row>
</aui:form>