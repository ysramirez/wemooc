<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<%
long actId=ParamUtil.getLong(request,"actId",0);

if(actId==0 )
{
	
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{

	LearningActivity learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
	
	if(learnact.getTypeId()!=2 )
	{
		
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		if(!LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
		{
			ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);

			LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
			learningTry.setEndDate(new java.util.Date(System.currentTimeMillis()));
			learningTry.setResult(100);
			LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningTry);
			

		}
		if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("") )
		{
			
			if(Validator.isNumber(learnact.getExtracontent()))
			{
				long entryId=Long.valueOf(learnact.getExtracontent());
				AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
				AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
				AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
				String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);
				if(!entry.getClassName().equals(JournalArticle.class.getName()))
				{
					%>
					<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale())%>"></liferay-ui:header>
					<%
					
				}
				if(entry.getClassName().equals(DLFileEntry.class.getName()))
				{
					DLFileEntry file=DLFileEntryLocalServiceUtil.getDLFileEntry(entry.getClassPK());
					DLFileVersion fileVersion = file.getFileVersion();
					
					%>
					<div id="resourcedescription"><%=learnact.getDescription(themeDisplay.getLocale()) %></div>
					
					<aui:a href='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + fileVersion.getGroupId() + StringPool.SLASH + fileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileVersion.getTitle())) %>'>
			<img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= fileVersion.getIcon() %>.png" /><%= HtmlUtil.escape(fileVersion.getTitle()) %>
		</aui:a>
					<%
				}
				else
				{
			%>
				<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
				<%
				}
			}
			else
			{
				%>
				<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale())%>"></liferay-ui:header>
				<div id="resourcedescription"><%=learnact.getDescription(themeDisplay.getLocale()) %></div>
				<%
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
				
				%>
		
				
 	<embed type="application/x-shockwave-flash" src="<%=request.getContextPath()%>/flash/flvplayer/playervideo.swf" 
			  width="560" height="315" style="undefined" id="cab" name="cab" bgcolor="#FFFFFF" quality="high" allowfullscreen="true" allowscriptaccess="always" wmode="transparent" menu="false" 
			  flashvars="file=<%=videoURL%>&type=flv" />
			  <%-- 
 			<video width="320" height="240" controls="controls">
  					<source src="<%=videoURL %>" type="video/mp4">
  					</video>
--%>
				<%
				}
				else
				{
					%>
					<%=video.getText()%>
					<%
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
				
				String docURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + docfileVersion.getGroupId() + StringPool.SLASH + docfileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(docfileVersion.getTitle()));
				%>
				<div class="additionalDocument">
				<a href="<%=docURL%>"><img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= docfileVersion.getIcon() %>.png" /><%= HtmlUtil.escape(docfileVersion.getTitle()) %></a>
				</div>
				<%
				}
			}
			
			}
		}
		else
		{
			%>
			<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale())%>"></liferay-ui:header>
			<div id="resourcedescription"><%=learnact.getDescription(themeDisplay.getLocale()) %></div>
			
		<%
		}
	}
}
%>