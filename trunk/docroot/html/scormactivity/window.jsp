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
</script>
<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>"></liferay-util:include>
<script type="text/javascript">
		if (window.opener) {
			window.onunload = function() {<portlet:namespace />update_scorm(null);};
		} else {
			document.getElementById('close-scorm').style.display = 'block';
		}
        var <portlet:namespace />update_scorm = function(e) {
        	
			var scormpool = localStorage['scormpool'];
			var results = JSON.parse(scormpool, function (key, value) {
				var type;
			    if (value && typeof value === 'object') {
			        type = value.type;
			        if (typeof type === 'string' && typeof window[type] === 'function') {
			            return new (window[type])(value);
			        }
			    }
			    return value;
			});
			var lesson_score = -1;
			var orgs = results.organizations;
			var org = null;
			for (var key in orgs) {
				if (orgs.hasOwnProperty(key)) {
					org = orgs[key];
					break;
				}
			}
			if (org != null) {
				var lessons = org.cmi;
				var lesson = null;
				for (var key2 in lessons) {
					if (lessons.hasOwnProperty(key2)) {
						lesson = lessons[key2];
						break;
					}
				}
				if (lesson != null) {
					var lesson_success_status = lesson["cmi.success_status"] ? lesson["cmi.success_status"].value : lesson["cmi.core.lesson_status"].value;
					var lesson_completion_status = lesson["cmi.completion_status"] ? lesson["cmi.completion_status"].value : lesson["cmi.core.lesson_status"].value;
					var lesson_score_raw = lesson["cmi.score.raw"] ? lesson["cmi.score.raw"].value : lesson["cmi.core.score.raw"].value;
					var lesson_score_min = lesson["cmi.score.min"] ? lesson["cmi.score.min"].value : lesson["cmi.core.score.min"].value;
					if (lesson_score_min = null || lesson_score_min == '') {
						lesson_score_min = 0;
					}
					var lesson_score_max = lesson["cmi.score.max"] ? lesson["cmi.score.max"].value : lesson["cmi.core.score.max"].value;
					if (lesson_score_max = null || lesson_score_max == '') {
						lesson_score_max = 100;
					}
					
					lesson_score = lesson["cmi.score.scaled"] ? lesson["cmi.score.scaled"].value : -1;
					
					if (lesson_score < 0) {
						lesson_score = Math.round((lesson_score_raw*100)/(lesson_score_max-lesson_score_min));
					} else {
						lesson_score = Math.round(lesson_score * 100);
					}				
					
					if (lesson_completion_status == 'completed' || lesson_completion_status == 'passed' || lesson_completion_status == 'failed') {
						
					} else if (lesson_completion_status == 'incomplete') {
						lesson_score = -1;
					} else if (lesson_completion_status == 'not attempted') {
						lesson_score = -1;
					} else if (lesson_completion_status == 'unknown') {
						lesson_score = -1;
					}	
				}
			}
					
			var serviceParameterTypes = [
	      		'long',
	      		'long',
	      		'java.lang.String'
	      	];
						 		
	      	Liferay.Service.Lms.LearningActivityResult.update(
	      		{
	      			latId: <%= learningTry.getLatId() %>,
	      			result: lesson_score,
	      			tryResultData: JSON.stringify(results),
	      			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
	      		},
	      		function(message) {
	                 var exception = message.exception;
	
	                 if (!exception) {
	                     // Process Success - A LearningActivityResult returned
	                     if (window.opener) {
	                    	window.opener.<portlet:namespace />actualizarActividad();
	                     } else {
	                    	document.getElementById('placeholder_contentIFrame').style.display = 'none';
	                     	document.getElementById('open-close-scorm-menu').style.display = 'none';
	                     	document.getElementById('close-scorm').style.display = 'none';
	                    	window.<portlet:namespace />actualizarActividad();
	                     }
	                 }
	                 else {
	                     // Process Exception
	                	 window.location.reload(true);
	                 }
	             }
	      	);
        }

        /*
	if(window.addEventListener) {
	    window.addEventListener('scormevent', <portlet:namespace />update_scorm, false);
	} else {
	    document.getElementById('clicker').attachEvent('onclick', <portlet:namespace />update_scorm);
	}
	document.getElementById('contentIFrame').unload = function() {update_scorm(null); };
	*/
</script>

<% } %>