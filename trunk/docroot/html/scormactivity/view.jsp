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
<div class="container-activity">
<%
	long actId=ParamUtil.getLong(request,"actId",0);
	boolean improve =ParamUtil.getBoolean(request, "improve",false);
	long userId = themeDisplay.getUserId();
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());

	String openWindow = GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "openWindow"), "true");

	//Obtener si puede hacer un intento de mejorar el resultado.
	boolean improving = false;
	LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
	if (result != null) {
		int done =  LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId,userId);
		LearningActivity act=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		if (result.getResult() < 100
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

<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale())%></h2>
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
			Object[] arguments = new Object[] { result.getResult() };
			Object[] arg = new Object[] { activity.getPasspuntuation() };
			%>
			<p>
				<liferay-ui:message key="activity-done" />
			</p>
			<p>
				<liferay-ui:message key="activity.your-result" arguments="<%=arguments%>" />
			</p>
			<p class="color_tercero negrita">
				<liferay-ui:message key="activity.your-result-pass" arguments="<%=arg%>" />
			</p>
			
			<% 
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
<h3><liferay-ui:message key="activity.try.pass.puntuation" arguments="<%= arg %>" /></h3>
<% } %>

<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>
<script type="text/javascript">
Liferay.provide(
    window,
    '<portlet:namespace />actualizarActividad',
    function() {
    	var A = AUI();
    	var serviceParameterTypes = ['long'];
    	Liferay.Service.Lms.LearningActivityResult.getByActId(
	      		{
	      			actId: <%= learningTry.getActId() %>,
	      			serviceParameterTypes: JSON.stringify(serviceParameterTypes)
	      		},
	      		function(message) {
	                 var exception = message.exception;
	
	                 if (!exception) {
	                     // Process Success - A LearningActivityResult returned
	                     if (message.passed) {
	                    	 window.location.reload(true);
	                     } else {
	                    	A.one('h3').html('<liferay-ui:message key="activity.try.finished"/>');
	 						A.one('h3').setStyle('display', 'inline');
	                     }
	                 }
	                 else {
	                     // Process Exception
	                	 window.location.reload(true);
	                 }
	             }
	      	);
    },
    ['node']
);
</script>

<% if ("true".equals(openWindow)) { %>
				<liferay-portlet:renderURL var="scormwindow" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<liferay-portlet:param name="jspPage" value="/html/scormactivity/window.jsp"/>
					<liferay-portlet:param name="latId" value="<%= String.valueOf(learningTry.getLatId()) %>"/>
				</liferay-portlet:renderURL>
				
			<script type="text/javascript">
			Liferay.provide(
					window,
					'<portlet:namespace />checker',
					function() {
						window.temporizador = setInterval(function() {
							if (window.ventana.closed) {
								<portlet:namespace />actualizarActividad();
								clearInterval(window.temporizador);
							}
						}, 500);
					}
				);
			
			Liferay.provide(
					window,
					'<portlet:namespace />abrirActividad',
					function(e) {
						var A = AUI();
						window.ventana = window.open('','scormactivity','height=768,width=1024');
						window.ventana.location = '<%= scormwindow %>';
						
						if (ventana != null) {
							A.one('p.activity-message').setStyle('display', 'none');
							A.one('span.newitem2').setStyle('display', 'none');
						}
						<portlet:namespace />checker();
						if (e != null && e.preventDefault) {
							e.preventDefault();
						} else {
							return false;
						}
					},
					['node']
			);
			AUI().ready(function() {<portlet:namespace />abrirActividad(null);});
			</script>
				
				<p class="activity-message"><liferay-ui:message key="activity.openwindow"></liferay-ui:message></p>
				
				<span class="newitem2"><a class="newitem2" href="#" onclick="<portlet:namespace />abrirActividad();" ><liferay-ui:message key="activity.go"></liferay-ui:message></a></span>
				<%
			} else {
				request.setAttribute("learningTry", learningTry);
				%>
			<liferay-util:include page="/html/scormactivity/window.jsp" servletContext="<%=this.getServletContext() %>"></liferay-util:include>
			<%
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
	<liferay-ui:message key="activity-done" />
</p>
<p>
	<liferay-ui:message key="activity.your-result" arguments="<%=arguments%>" />
</p>
<p class="color_tercero negrita">
	<liferay-ui:message key="activity.your-result-dont-pass" arguments="<%=arg%>" />
</p>
<p>
	<liferay-ui:message key="activity.your-result-no-more-tries" />
</p>
<%
	}

			}
		}
	}
%>
</div>