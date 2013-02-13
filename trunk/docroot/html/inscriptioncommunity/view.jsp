<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@include file="/init.jsp" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.util.LiferayViewUtil"%>

<div id="caja_inscripcion">
	
<%
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{
if(themeDisplay.isSignedIn())
{	
	if(GroupLocalServiceUtil.hasUserGroup(themeDisplay.getUserId(),themeDisplay.getScopeGroupId()))
	{
		%>
			<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.inscrito" /></div>	
		<%
		Date now=new Date(System.currentTimeMillis());
		if((course.getStartDate().before(now)&&course.getEndDate().after(now))&&permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"REGISTER"))
		{
		%>
			<portlet:actionURL name="desinscribir"  var="desinscribirURL" windowState="NORMAL"/>
			<div class="boton_inscibirse ">
				<a href="<%=desinscribirURL %>"><liferay-ui:message key="inscripcion.desinscribete" /></a>
			</div>			
		<%
		}
	} else {
		Date now=new Date(System.currentTimeMillis());
		if((course.getStartDate().before(now)&&course.getEndDate().after(now))&&permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"REGISTER"))
		{
		%>
			<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.noinscrito" /></div>
			<portlet:actionURL name="inscribir"  var="inscribirURL" windowState="NORMAL"/>
			<div class="boton_inscibirse ">
				<a href="<%=inscribirURL %>"><liferay-ui:message key="inscripcion.inscribete" /></a>
			</div>			
		<%
		}
	}
} else {
	String urlRedirect= themeDisplay.getURLCurrent();	
	%>
	<div class="mensaje_marcado"><liferay-ui:message key="inscripcion.nologado" /></div>
	<div class="boton_inscibirse ">
		<a href="/c/portal/login?redirect=<%=urlRedirect%>"><liferay-ui:message key="inscripcion.registrate" /></a>
	</div>
	<%	
}
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>
</div>