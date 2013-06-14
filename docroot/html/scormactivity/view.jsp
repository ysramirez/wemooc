<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.taglib.portlet.RenderURLTag"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>

<%@ include file="/init.jsp"%>

<%
	long actId=ParamUtil.getLong(request,"actId",0);
	boolean improve =ParamUtil.getBoolean(request, "improve",false);
	long userId = themeDisplay.getUserId();
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());


	//Obtener si puede hacer un intento de mejorar el resultado.
	boolean improving = false;
	LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
	if (result != null) {
		int done =  LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId,userId);
		LearningActivity act=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		if(result.getResult() < 100
		   && !LearningActivityLocalServiceUtil.islocked(actId, userId)
		   && LearningActivityResultLocalServiceUtil.userPassed(actId, userId)
		   && done < act.getTries()){
			improving = true;
		}
	}


	if (actId==0) {
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	} else {
		LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		long typeId=activity.getTypeId();
		
		if( typeId==9
		&& (!LearningActivityLocalServiceUtil.islocked(actId,userId)
		|| permissionChecker.hasPermission( activity.getGroupId(),LearningActivity.class.getName(), actId, ActionKeys.UPDATE)
		|| permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"ACCESSLOCK")))
		{
%>

<h2><%=activity.getTitle(themeDisplay.getLocale())%></h2>
<p><%=activity.getDescription(themeDisplay.getLocale())%></p>

<%
	if((!LearningActivityLocalServiceUtil.islocked(actId,userId)
	|| permissionChecker.hasPermission( activity.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE)
	|| permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"ACCESSLOCK")))
		{		
		if((!improve) && (LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId())))
		{
			request.setAttribute("learningActivity",activity);
			request.setAttribute("larntry",LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId));
		}
		else if (LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, userId) 
		|| permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(),actId, ActionKeys.UPDATE)
		|| permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(),course.getCourseId(),"ACCESSLOCK")
	    || improving )
		{

	ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
	long activityTimestamp=0;
	long timestamp=0;
	
	LearningActivityTry learningTry = LearningActivityTryLocalServiceUtil.getLearningActivityTryNotFinishedByActUser(actId,userId);
	
	//Comprobar si tenemos un try sin fecha de fin, para continuar en ese try.
	if(learningTry == null)
	{
		learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
	}
	else
	{
		activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"timeStamp"));
		timestamp=activityTimestamp*1000 - (new Date().getTime() - learningTry.getStartDate().getTime());
	}
	
	if((activityTimestamp!=0)&&(timestamp<0)){
		request.setAttribute("learningActivity",activity);
		request.setAttribute("larntry",learningTry);
%>
<liferay-util:include page="/html/execactivity/test/expired.jsp"
	servletContext="<%=this.getServletContext()%>">
	<liferay-util:param value="<%=Long.toString(activity.getActId())%>"
		name="actId" />
</liferay-util:include>
<%
	}
	else {
	Object [] arg =  new Object [] { activity.getPasspuntuation() };
%>

<% if (activity.getPasspuntuation() > 0) { %>
<h3><liferay-ui:message key="scormactivity.try.pass.puntuation" arguments="<%= arg %>" /></h3>
<% }
String openWindow = GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "openWindow"), "true");
if ("true".equals(openWindow) && !themeDisplay.isStateMaximized()) { %>
				<liferay-portlet:renderURL var="scormwindow" windowState="<%= LiferayWindowState.MAXIMIZED.toString() %>"/>
				<script type="text/javascript">
					function abrirActividad() {
						ventana = window.open('<%= scormwindow %>', "scormactivity");
						return ventana;
					}
					abrirActividad();
				</script>
				
				<liferay-ui:message key="scormactivity.openwindow"></liferay-ui:message>
				
				<a href="<%= scormwindow %>" target="_blank" >Go</a>
				<%
			} else {
			
			long entryId = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"assetEntry"),0);
			
			if(entryId!=0)
			{
				AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
				AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
				AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
				String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT); %>
				<% themeDisplay.setIncludeServiceJs(true); %>
				<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>
				<script type="text/javascript">
					localStorage.removeItem('scormpool');
				</script>
				<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>"></liferay-util:include>
				<script type="text/javascript">
		
					var update_scorm = function(e) {
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
								var lesson_success_status = lesson["cmi.success_status"].value;
								var lesson_completion_status = lesson["cmi.completion_status"].value;
								var lesson_score_raw = lesson["cmi.score.raw"].value;
								var lesson_score_min = lesson["cmi.score.min"].value;
								var lesson_score_max = lesson["cmi.score.max"].value;
								
								var lesson_score = lesson["cmi.score.scaled"].value;
								
								if (lesson_score < 0) {
									lesson_score = 0;
								} else {
									lesson_score *= 100;
								}				
								
								// if (estado finished) launch service
								if (lesson_completion_status == 'completed') {
									var serviceParameterTypes = [
							      		'long',
							      		'long',
							      		'java.lang.String'
							      	];
												 		
							      	Liferay.Service.Lms.LearningActivityResult.update(
							      		{
							      			latId: <%= learningTry.getLatId() %>,
							      			result: lesson_score,
							      			tryResultData: JSON.stringify(lesson),
							      			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
							      		},
							      		function(message) {
							                 var exception = message.exception;
							
							                 if (!exception) {
							                     // Process Success - A LearningActivityResult returned
							                 	console.log(message);
							                 }
							                 else {
							                     // Process Exception
							                 	
							                 }
							             }
							      	);
								} else if (lesson_completion_status == 'incomplete') {
									
								} else if (lesson_completion_status == 'not attempted') {
									
								} else if (lesson_completion_status == 'unknown') {
									
								}
							
							}
						}
					};
					
					if(window.addEventListener) {
					    window.addEventListener('scormevent', update_scorm, false);
					} else {
					    window.attachEvent('onscormevent', update_scorm);
					}
	
				</script>
				<%
			}
		}
	}
}
//Si no ha pasado el test, ni tiene mas intentos.
else {
	//LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
	Object[] arguments = new Object[] { result.getResult() };
	Object[] arg = new Object[] { activity.getPasspuntuation() };
%>
<h2><%=activity.getTitle(themeDisplay.getLocale())%></h2>
<p>
	<liferay-ui:message key="test-done" />
</p>
<p>
	<liferay-ui:message key="your-result" arguments="<%=arguments%>" />
</p>
<p class="color_tercero negrita">
	<liferay-ui:message key="your-result-dont-pass" arguments="<%=arg%>" />
</p>
<p>
	<liferay-ui:message key="your-result-no-more-tries" />
</p>
<%
	}

			}
		}
	}
%>