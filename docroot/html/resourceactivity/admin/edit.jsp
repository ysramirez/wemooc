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

<%@ include file="/html/resourceactivity/admin/init.jsp" %>
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
<portlet:renderURL var="cancel">
	<portlet:param name="actId" value="0" />
</portlet:renderURL>

<portlet:actionURL var="editresourceURL" name="addfiles">
<portlet:param name="jspPage" value="/html/resourceactivity/admin/edit.jsp" />
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
			<aui:column>
				<aui:field-wrapper label="mp4-file" >
	    			<aui:input inlineLabel="left" inlineField="true"
					  	name="fileName" label="" id="fileName" type="file" value="" /> <liferay-ui:message key="or" />
					  	<%if(previusvideo!=null)
			  	{
			  	%><br>
			  		<liferay-ui:message key="actual-file" /><aui:a href='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + previusvideo.getGroupId() + StringPool.SLASH + previusvideo.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(previusvideo.getTitle())) %>'>
			<img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= previusvideo.getIcon() %>.png" /><%= HtmlUtil.escape(previusvideo.getTitle()) %>
		</aui:a><aui:input type="checkbox" label="delete" name="deletevideo" value="false" inlineLabel="left"/>
			  	<%
			  	}
			  	%>
				</aui:field-wrapper>
		  	</aui:column>
		  	<aui:column>
		  		<aui:input name="youtubecode" type="textarea" rows="6" cols="45" label="youtube-code" value="<%=youtubecode %>"></aui:input>
		  	</aui:column>
		</aui:field-wrapper>
		<%
		
		%>
		
		<aui:field-wrapper label="complementary-file">		  	
			<aui:input inlineLabel="left" inlineField="true"
			  	name="fileName2" label="" id="fileName" type="file" value="" />
			  	<%if(previusaditionalfile!=null)
			  	{
			  	%><br>
			  		<liferay-ui:message key="actual-file" />
			  					<aui:a href='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + previusaditionalfile.getGroupId() + StringPool.SLASH + previusaditionalfile.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(previusaditionalfile.getTitle())) %>'>
			<img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= previusaditionalfile.getIcon() %>.png" /><%= HtmlUtil.escape(previusaditionalfile.getTitle()) %>
		</aui:a>
			<aui:input type="checkbox" label="delete" name="deletecomplementary" value="false" inlineLabel="left"/>
	
			  	<%
			  	}
			  	%>
		</aui:field-wrapper>	  
		<%
		}
		if(learnact.getExtracontent()==null||learnact.getExtracontent().trim().equals("")|| Validator.isNumber(learnact.getExtracontent()))
		{
		%>			   
		<c:if test="<%= permissionChecker.hasPermission(learnact.getGroupId(), LearningActivity.class.getName(), learnact.getActId(),
			advancedKey) %>">
	    	<liferay-portlet:renderURL var="searchResource">
				<liferay-portlet:param name="jspPage" value="/html/resourceactivity/admin/searchresource.jsp"/>
		 		<liferay-portlet:param value="<%=Long.toString(learnact.getActId()) %>" name="actId"/>
			</liferay-portlet:renderURL>
			<a href="<%=searchResource.toString() %>"><liferay-ui:message key="select-resource"></liferay-ui:message></a>
	   </c:if> 
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