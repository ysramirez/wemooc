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
	Role teacher=RoleLocalServiceUtil.getRole(teacherRoleId);com.liferay.portal.kernel.util.HtmlUtilcom.liferay.portal.kernel.utilcom.liferay.portal.kernel.util
	String[] layoutsettemplateidString=prefs.getLmsTemplates().split(",");
%>

<aui:form>
<aui:input name="" value=<%=pre %>></aui:input>
</aui:form>
<%
}
%>