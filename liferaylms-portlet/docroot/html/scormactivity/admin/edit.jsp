<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.exception.NestableException"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="sun.nio.cs.UnicodeEncoder"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@page import="com.liferay.portal.kernel.exception.PortalException"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp" %>
<link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>
<% 
	long assetId=ParamUtil.getLong(request, "assertId");
	boolean openWindow = true, editDetails = false; 
	String assetTitle=StringPool.BLANK;
	long typeId=ParamUtil.getLong(request, "type");
	LearningActivity learningActivity=null;
	if(assetId!=0){
		try{
			AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(assetId);
			assetId=entry.getEntryId();
			assetTitle=entry.getTitle(renderRequest.getLocale());
			editDetails = GetterUtil.getBoolean(PropsUtil.get("lms.scorm.editable."+entry.getClassName()),false);	
		}
		catch(NestableException e)
		{
			assetId=0;
		}
	}
	
	if(request.getAttribute("activity")!=null) {	
		learningActivity=(LearningActivity)request.getAttribute("activity");
		typeId=learningActivity.getTypeId();
	
		if ((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)) {
			try{
				if(assetId==0){
					AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(
						GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"assetEntry")));
					assetId=entry.getEntryId();
					assetTitle=entry.getTitle(renderRequest.getLocale());
					editDetails = GetterUtil.getBoolean(PropsUtil.get("lms.scorm.editable."+entry.getClassName()),false);
				}	
				openWindow = GetterUtil.getBoolean(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(), "openWindow"));	
			}
			catch(NestableException e)
			{
			}
			
		}	
	}
	
%>

<liferay-portlet:renderURL var="selectResource" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="jspPage" value="/html/scormactivity/admin/searchresource.jsp"/>
</liferay-portlet:renderURL>

<% 

String editResourceUnicode = StringPool.BLANK;
if(learningActivity!=null){  %>
<liferay-util:buffer var="editResource">
	<liferay-portlet:renderURL var="editResourceURL" windowState="<%=LiferayWindowState.POP_UP.toString() %>">
		<liferay-portlet:param name="mvcPath" value="/html/scormactivity/admin/editDetails.jsp"/>
		<liferay-portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
	</liferay-portlet:renderURL>
	<% 
	   String editResourceClick="(function(source){ "+
				"if ((!!window.postMessage)&&(window.parent != window)) { "+
				"	AUI().use('json-stringify', function(A){ "+
				"		parent.postMessage(JSON.stringify({name:'resizeWidthActivity', "+
		        "							   width:'1250px'}), window.location.origin); "+
				"	}); "+
				"} "+
		        "})(this);";	
	%>
</liferay-util:buffer>
<% 
	editResourceUnicode = UnicodeFormatter.toString(editResource);
}
%>

<script type="text/javascript">
<!--

<% if(editDetails){ %>
	AUI().ready('node',function(A) {
		A.one('.acticons').html('<%=editResourceUnicode%>');
		var editResource=A.one('#<portlet:namespace/>editResource');
		editResource.set('href',editResource.get('href')+'&assertId='+
				encodeURIComponent(<%=assetId %>));
	});
<% } %>

function <portlet:namespace />search() {
	AUI().use('node',function(A) {
		var backbutton = A.one('#<portlet:namespace/>backButton').one('span').clone();
		backbutton.setAttribute('id','<portlet:namespace/>backbutton');
		A.one('.portlet-body').prepend(backbutton);
		A.one('#<portlet:namespace/>backbutton').scrollIntoView();
		var iframe = A.Node.create('<iframe id="<portlet:namespace/>finder" src="javascript:false;" onload="<portlet:namespace />load(this)" frameBorder="0" scrolling="no" width="'+A.getBody().width()+'" height="0"></iframe>');
		A.one('.portlet-body').append(iframe);
		A.all('.acticons').each(function(icon){ icon.hide(); });
		A.one('#<portlet:namespace/>fm').hide();
		window.messageHandler = A.one(window).on('message', 
				function(event){
					var html5Event=event._event;
					if(html5Event.data.name=='resizeIframe'){
						var source=iframe.getDOM ();
						source.height=0;

					    if (source.Document && source.Document.body.scrollHeight) 
					        source.height = source.contentWindow.document.body.scrollHeight;
					    else if (source.contentDocument && source.contentDocument.body.scrollHeight) 
					        source.height = source.contentDocument.body.scrollHeight + 35;
					    else if(source.contentDocument && source.contentDocument.body.offsetHeight) 
					        source.height = source.contentDocument.body.offsetHeight + 35;
					    else if(source.height < html5Event.data.height)
					    	source.height = html5Event.data.height;
					}
				});

		iframe.setAttribute('src','<%=selectResource %>');
	});
}
	
function <portlet:namespace />load(source) {

	AUI().use('node','querystring-parse',function(A) {
		
		var params=A.QueryString.parse(source.contentWindow.location.search.replace('?',''));
	
		if((params['<portlet:namespace />jspPage']=='/html/scormactivity/admin/result.jsp')&&
           (A.Lang.isNumber(params['<portlet:namespace />assertId'])) &&
           (A.Lang.isString(params['<portlet:namespace />assertTitle'])) &&
           (A.Lang.isString(params['<portlet:namespace />assertEditable'])) &&
           (A.Lang.isString(params['<portlet:namespace />assertWindowable']))) {
			A.one('#<portlet:namespace/>backbutton').remove();
			A.one('#<portlet:namespace/>finder').remove();
			A.all('.acticons').each(function(icon){ icon.show(); });
			A.one('#<portlet:namespace/>fm').show();
			A.one('#<portlet:namespace/>assetEntryId').set('value',params['<portlet:namespace />assertId']);		
			A.one('#<portlet:namespace/>assetEntryName').set('value',params['<portlet:namespace />assertTitle']);
			
			if(params['<portlet:namespace />assertWindowable']=='true') {
				document.getElementById("<portlet:namespace/>openWindow").value = "true";
				document.getElementById("<portlet:namespace/>openWindowCheckbox").checked = true;
			} else {
				document.getElementById("<portlet:namespace/>openWindow").value = "false";
				document.getElementById("<portlet:namespace/>openWindowCheckbox").checked = false;
			}
			
			<% if(learningActivity!=null){  %>
				if(params['<portlet:namespace />assertEditable']=='true') {
					A.one('.acticons').html('<%=editResourceUnicode%>');
					var editResource=A.one('#<portlet:namespace/>editResource');
					editResource.set('href',editResource.get('href')+'&assertId='+
							encodeURIComponent(params['<portlet:namespace />assertId']));
				}
				else{
					A.one('.acticons').html('');
				}
				
			<% } %>

			window.messageHandler.detach();
			window.messageHandler=null;
		}
		else {
		    if (source.Document && source.Document.body.scrollHeight) 
		        source.height = source.contentWindow.document.body.scrollHeight;
		    else if (source.contentDocument && source.contentDocument.body.scrollHeight) 
		        source.height = source.contentDocument.body.scrollHeight + 35;
		    else if (source.contentDocument && source.contentDocument.body.offsetHeight) 
		        source.height = source.contentDocument.body.offsetHeight + 35;
		}
	
	});
}

function <portlet:namespace />back() {
	AUI().use('node',function(A) {
		A.one('#<portlet:namespace/>backbutton').remove();
		A.one('#<portlet:namespace/>finder').remove();
		A.all('.acticons').each(function(icon){ icon.show(); });
		A.one('#<portlet:namespace/>fm').show();

		window.messageHandler.detach();
		window.messageHandler=null;
	});
}

//-->
</script>

<%
	boolean disabled = false;
	if(LearningActivityLocalServiceUtil.canBeEdited(learningActivity, user.getUserId())){
		disabled = false;
	}
%>

<aui:input type="hidden" name="assetEntryId" ignoreRequestValue="true" value="<%=Long.toString(assetId) %>">
	<aui:validator name="required"></aui:validator>
</aui:input>
<aui:field-wrapper name="activity.edit.asserts" cssClass="search-button-container">
	<aui:input type="text" name="assetEntryName" ignoreRequestValue="true" value="<%=assetTitle %>" label="" inlineField="true" disabled="true"  size="50">
		<aui:validator name="required"></aui:validator>
	</aui:input>
	<c:if test="<%=!disabled %>" >
		<button type="button" id="<portlet:namespace/>searchEntry" onclick="<portlet:namespace/>search();" >
		    <span class="aui-buttonitem-icon aui-icon aui-icon-search"></span>
		    <span class="aui-buttonitem-label"><%= LanguageUtil.get(pageContext, "search") %></span>
		</button>
	</c:if>
</aui:field-wrapper>
<aui:field-wrapper name="activity.edit.openwindow.options">
	<aui:input type="checkbox" name="openWindow" label="activity.edit.openwindow" value="<%= String.valueOf(openWindow) %>" />
</aui:field-wrapper>
		
<div id="<portlet:namespace/>backButton" style="display:none;">
	<liferay-ui:icon image="back" message="back" url="<%=\"javascript:\"+renderResponse.getNamespace()+\"back();\" %>" label="true"  />
</div>

