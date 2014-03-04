<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%

long actId = ParamUtil.getLong(request, "actId", 0L);
LearningActivityTry learningTry = (LearningActivityTry) request.getAttribute("learningTry");
if (learningTry == null) {
	long latId = ParamUtil.getLong(request, "latId", 0L);
	learningTry = LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
}
request.setAttribute("learningTry", learningTry);

String openWindow = GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "openWindow"), "true");

long entryId = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "assetEntry"), 0);
			
if(entryId != 0) {
	AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
	AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
	String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT); 
	if ("true".equals(openWindow)) {
	themeDisplay.setIncludeServiceJs(true); 
	

	%>
<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>
<% } %>
<script type="text/javascript">
	localStorage.removeItem('scormpool');
	var tryResultDataOld = '<%= learningTry.getTryResultData() %>';
	if (tryResultDataOld != '') {
		localStorage.setItem('scormpool', tryResultDataOld);
	}
</script>
<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>"></liferay-util:include>
<script type="text/javascript">

	if (window.opener) {
		window.onunload = function() {
			if ((!!window.opener.postMessage)) {
    			if (!window.location.origin){
    				var nada = '';
    				window.location.origin = window.location.protocol+"/"+nada+"/"+window.location.host;
    			}
    			window.opener.postMessage({name: 'update_scorm'}, window.location.origin);
    		}			
		};
	}
	
	var finish_scorm = function(e) {
		var serviceParameterTypes = [
   	     	'long',
   	    	'java.lang.String'
   	    ];
		var message = Liferay.Service.Lms.LearningActivityResult.getByActIdAndUser(
		   	{
		  			actId: <%= learningTry.getActId() %>,
		  			login: '<%= themeDisplay.getUser().getLogin() %>',
		  			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
		   	}
		);
		if (message.passed) {
			Liferay.Portlet.refresh('#p_p_id<portlet:namespace />');
		} else {
			
		}
	};

	var update_scorm = function(e) {
        	
		var scormpool = localStorage['scormpool'];
		
		var serviceParameterTypes = [
	     	'long',
	    	'java.lang.String',
	    	'java.lang.String'
	    ];
					 		
	    var message = Liferay.Service.Lms.LearningActivityResult.update(
	    	{
	   			latId: <%= learningTry.getLatId() %>,
	   			tryResultData: scormpool,
	   			imsmanifest: Run.$1.xml,
	   			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
	    	}
	    );
	      	
	    var exception = message.exception;	
	            
		if (!exception) {
			// Process Success - A LearningActivityResult returned
		} else {
			// Process Exception
		}
	};
</script>

<% } %>