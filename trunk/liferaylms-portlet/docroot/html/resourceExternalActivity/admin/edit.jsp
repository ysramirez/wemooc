<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
	DLFileVersion previusaditionalfile=null;
	String youtubecode=StringPool.BLANK;
	LearningActivity learningActivity=null;
	if(request.getAttribute("activity")!=null) {
		learningActivity=(LearningActivity)request.getAttribute("activity");
		
		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0))
		{
			Document document = SAXReaderUtil.read(learningActivity.getExtracontent());
			Element root=document.getRootElement();
			Element video=root.element("video");
			
			if(video!=null)
			{
				youtubecode=video.getText();
			}
			
			Element documento=root.element("document");
			if(documento!=null)
			{
				if(documento.attributeValue("id","").length()!=0)
				{
					AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
					DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
					previusaditionalfile = docfile.getFileVersion();				
				}		
			}
		
		}
	}  
	
%>
<aui:field-wrapper label="video" >
  	<aui:input name="youtubecode" type="textarea" rows="6" cols="45" label="youtube-code" value="<%=youtubecode %>" ignoreRequestValue="true" helpMessage="<%=LanguageUtil.get(pageContext,\"youtube-code-help\")%>"></aui:input>
</aui:field-wrapper>
<aui:field-wrapper label="complementary-file" helpMessage="<%=LanguageUtil.get(pageContext,\"additionalFile-help\")%>" >		  	
		<aui:input inlineLabel="left" inlineField="true" name="additionalFile" label="" id="additionalFile" type="file" value="" />
	  	<%if(previusaditionalfile!=null)
	  	{
	  	%><br>
	  		<liferay-ui:message key="actual-file"  />
	  		<aui:a href='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + previusaditionalfile.getGroupId() + StringPool.SLASH + previusaditionalfile.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(previusaditionalfile.getTitle())) %>'>
				<img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= previusaditionalfile.getIcon() %>.png" /><%= HtmlUtil.escape(previusaditionalfile.getTitle()) %>
			</aui:a>
			<aui:input type="checkbox" label="delete" name="deleteAdditionalFile" value="false" inlineLabel="left"/>
	  	<%
	  	}
	  	%>
</aui:field-wrapper>	
