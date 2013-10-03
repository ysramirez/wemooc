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

long entryId = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "assetEntry"), 0);
			
if(entryId != 0) {
	AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
	AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
	String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT); 
	themeDisplay.setIncludeServiceJs(true); 
	

	%>
<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>
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
			window.onbeforeunload = function() {
				if (document.getElementById("contentIFrame").style.display == 'none') {
					return null;
				}
				return '<liferay-ui:message key="activity.beforeclose"></liferay-ui:message>'; 
			};
		}
		
        var <portlet:namespace />update_scorm = function(e) {
        	
			var scormpool = localStorage['scormpool'];
			
			var serviceParameterTypes = [
	      		'long',
	      		'java.lang.String'
	      	];
						 		
	      	Liferay.Service.Lms.LearningActivityResult.update(
	      		{
	      			latId: <%= learningTry.getLatId() %>,
	      			tryResultData: scormpool,
	      			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
	      		},
	      		function(message) {
	                 var exception = message.exception;
	
	                 if (!exception) {
	                     // Process Success - A LearningActivityResult returned
	                     if (window.opener) {
	                    	 if ((!!window.opener.postMessage)) {
                    			if (!window.location.origin){
                    				var nada = '';
                    				window.location.origin = window.location.protocol+"/"+nada+"/"+window.location.host;
                    			}
                    			window.opener.postMessage({name: 'update_scorm', passed: message.passed}, window.location.origin);
                    		}
	                    	 window.close();
	                     } else {
	                    	 if (message.passed) {
	                    		 window.location.reload(true);
	                    	 }
	                     }
	                 }
	                 else {
	                     // Process Exception
	                	 window.location.reload(true);
	                 }
	             }
	      	);
        }
        var update_scorm = <portlet:namespace />update_scorm;
	
</script>

<% } %>