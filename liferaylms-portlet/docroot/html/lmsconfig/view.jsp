<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeServiceUtil"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@ include file="/init.jsp"%>
<%
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
if(prefs!=null)
{
	long editorRoleId=prefs.getEditorRole();
	Role editor=RoleLocalServiceUtil.getRole(editorRoleId);
	long teacherRoleId=prefs.getTeacherRole();
	Role teacher=RoleLocalServiceUtil.getRole(teacherRoleId);
	String[] layoutsettemplateidsString=prefs.getLmsTemplates().split(",");
	long[] layoutsettemplateids=new long[layoutsettemplateidsString.length];
	for(int i=0;i<layoutsettemplateidsString.length;i++)
	{
		layoutsettemplateids[i]=Long.parseLong(layoutsettemplateidsString[i]);
	}
%>
<liferay-portlet:actionURL name="changeSettings" var="changeSettingsURL">
</liferay-portlet:actionURL>
<aui:form action="<%=changeSettingsURL %>" method="POST">
<aui:field-wrapper label="site-templates">
<%


for(LayoutSetPrototype layoutsetproto:LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(0, 1000000))
{
	boolean checked=false;
	String writechecked="false";
	if(ArrayUtils.contains(layoutsettemplateids, layoutsetproto.getLayoutSetPrototypeId()))
	{
		checked=true;
		writechecked="true";
	}
	%>
	
	<aui:input type="checkbox" name="lmsTemplates" 
	label="<%=layoutsetproto.getName(themeDisplay.getLocale())  %>" checked="<%=checked %>" value="<%=layoutsetproto.getLayoutSetPrototypeId()%>" />
	<%
}
%>
</aui:field-wrapper>
<aui:input type="submit" name="save" value="save" />

</aui:form>
<%
}
%>