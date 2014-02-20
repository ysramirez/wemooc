<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference.Elements"%>
<%@page import="com.liferay.lms.learningactivity.ResourceExternalLearningActivityType"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.Criterion"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.kernel.bean.PortletBeanLocatorUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
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
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyy",themeDisplay.getLocale());
	sdf.setTimeZone(themeDisplay.getTimeZone());
	DLFileVersion previusaditionalfile=null;
	String youtubecode=StringPool.BLANK;
	LearningActivity learningActivity=null;
	boolean readonly = true;

	List<AssetEntry> elements = new ArrayList<AssetEntry>(); 
	Integer maxfile = ResourceExternalLearningActivityType.DEFAULT_FILENUMBER;
	try{
		maxfile = Integer.valueOf(PropsUtil.get("lms.learningactivity.maxfile"));
	}catch(NumberFormatException nfe){
	}
		
	if(request.getAttribute("activity")!=null) {
		learningActivity=(LearningActivity)request.getAttribute("activity");
		
		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0))	{
			Document document = SAXReaderUtil.read(learningActivity.getExtracontent());
			Element root=document.getRootElement();
			Element video=root.element("video");
			
			if(video!=null) youtubecode=video.getText();

			Element documento=null;
			int i = 0;
			do{
				String documentt = "document";
				if(i>0){
					documentt = documentt+(i-1);
				}
				documento=root.element(documentt);
				if(documento!=null){
					try{
						AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
						elements.add(docAsset);
					}catch(Exception e){}
				}
				i++;
			}while(documento!=null);
		}
	}
	
	if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())) readonly=false;
	
%>
<aui:field-wrapper label="video" >
  	<aui:input disabled="<%=readonly %>" name="youtubecode" type="textarea" rows="6" cols="45" label="youtube-code" value="<%=youtubecode %>" ignoreRequestValue="true" helpMessage="<%=LanguageUtil.get(pageContext,\"youtube-code-help\")%>"></aui:input>
</aui:field-wrapper>
<script type="text/javascript">
	function deleteFile(id){
		var divfiles = document.getElementById("files");
		var file = document.getElementById("file"+id);
		var inputs = file.getElementsByTagName("input");
		if(inputs[0].value!=null&&inputs[0].value!=""){
			if(confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this"  />')){
				divfiles.removeChild(file);
			}	
		}else{
			divfiles.removeChild(file);
		}
		var files = divfiles.getElementsByTagName("input");
		if(files.length<<%=maxfile%>){
			document.getElementById("add_attachment").style.display = 'block';
		}
	}
	function addFileInput(){
		var divfiles = document.getElementById("files");
		var files = divfiles.getElementsByTagName("input");
		var append = 1;
		if(files.length+1>=<%=maxfile%>){
			document.getElementById("add_attachment").style.display = 'none';
		}
		for (var i=0; i<files.length; i++) {
			var div = document.getElementById("file"+i);
			if(div==null){
				append=i;
				break;
			}
			append=i;
		}
		var div = document.createElement("div");
		div.id = "file"+append;
		//div.className = "aui-field aui-field-text aui-field-inline";
		var input = document.createElement("input");
		div.appendChild(input);
		var a = document.createElement("a");
		a.onclick = function() { deleteFile(append); return false; };
		a.href="#";
		var img = document.createElement("img");
		img.className="icon";
		img.tittle="<%=LanguageUtil.get(pageContext,"delete")%>";
		img.alt="<%=LanguageUtil.get(pageContext,"delete") %>";
		img.src="/html/themes/classic/images/common/delete.png";
		a.appendChild(img);
		input.type = "file";
		input.name = "<portlet:namespace />additionalFile"+append;
		input.id = "<portlet:namespace />additionalFile"+append;

		div.appendChild(input);
		div.appendChild(a);
		divfiles.appendChild(div);
	}
</script>
<aui:field-wrapper label="complementary-file" helpMessage="<%=LanguageUtil.get(pageContext,\"additionalFile-help\")%>" >		  	
		<div id="files">
			<% for(int i=0;i<elements.size();i++){ 
				String append = "";
				if(i>0){
					append = append+(i-1);
				}
			%>
				<div id="file<%=append%>">
					<% 	AssetEntry aEntry = elements.get(i);
					%>
					<input type="hidden" name="<portlet:namespace />additionalFile<%=append %>" id="<portlet:namespace />additionalFile<%=append %>" value="<%= aEntry.getEntryId() %>">
					<span class="upfile"><%= aEntry.getTitle(themeDisplay.getLocale()) %></span> <span class="upuser"><%=aEntry.getUserName() %></span> <span><%=sdf.format(aEntry.getCreateDate()) %></span>
					<a href="#" onclick="javascript: deleteFile('<%=append %>'); return false;">
						<img class="icon" title="<%=LanguageUtil.get(pageContext,"delete")%>" alt="<%=LanguageUtil.get(pageContext,"delete")%>" src="/html/themes/classic/images/common/delete.png">
					</a>
				</div>
			<%}
			
			if(elements.size()==0){
			%>			
				<aui:input disabled="<%=readonly %>" inlineLabel="left" inlineField="true" name="additionalFile" label="" id="additionalFile" type="file" value="" />
			<%} %>
		</div>
	  	<%if(previusaditionalfile!=null)
	  	{
	  	%><br>
	  		<liferay-ui:message key="actual-file"  />
	  		<aui:a href='<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + previusaditionalfile.getGroupId() + StringPool.SLASH + previusaditionalfile.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(previusaditionalfile.getTitle())) %>'>
				<img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= previusaditionalfile.getIcon() %>.png" /><%= HtmlUtil.escape(previusaditionalfile.getTitle()) %>
			</aui:a>
			<aui:input type="checkbox" label="delete" name="deleteAdditionalFile" value="false" disabled="<%=readonly %>" inlineLabel="left"/>
	  	<%
	  	}
	  	%>
	  	<a href="#" id="add_attachment" class="add_attachment" onclick="addFileInput(); return false;">Añadir otro fichero</a>
</aui:field-wrapper>	
