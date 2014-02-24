<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
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
LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId());
if(prefs!=null)
{
	long editorRoleId=prefs.getEditorRole();
	Role editor=RoleLocalServiceUtil.getRole(editorRoleId);
	long teacherRoleId=prefs.getTeacherRole();
	Role teacher=RoleLocalServiceUtil.getRole(teacherRoleId);
	String[] layoutsettemplateidsString=prefs.getLmsTemplates().split(",");
	long[] layoutsettemplateids=new long[layoutsettemplateidsString.length];
	long[] actids=new long[layoutsettemplateidsString.length];
	for(int i=0;i<layoutsettemplateidsString.length;i++)
	{
		if(!StringPool.BLANK.equals(layoutsettemplateidsString[i])){
			layoutsettemplateids[i]=Long.parseLong(layoutsettemplateidsString[i]);
		}
	}
	String[] activityidsString=prefs.getActivities().split(",");
	long[] activityids=new long[activityidsString.length];
	for(int i=0;i<activityidsString.length;i++)
	{
		if(!StringPool.BLANK.equals(activityidsString[i])){
			activityids[i]=Long.parseLong(activityidsString[i]);
		}
	}
%>
<liferay-portlet:actionURL name="changeSettings" var="changeSettingsURL">
</liferay-portlet:actionURL>
<aui:form action="<%=changeSettingsURL %>" method="POST">
<aui:field-wrapper label="site-templates">
<%

for(LayoutSetPrototype layoutsetproto:LayoutSetPrototypeLocalServiceUtil.search(themeDisplay.getCompanyId(),true,0, 1000000,null))
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
<aui:field-wrapper label="lms-activities">
<%
LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
for(LearningActivityType learningActivityType:learningActivityTypeRegistry.getLearningActivityTypes())
{
	boolean checked=false;
	String writechecked="false";
	if(activityids!=null &&activityids.length>0 && ArrayUtils.contains(activityids, learningActivityType.getTypeId()))
	{
		checked=true;
		writechecked="true";
	}
	%>
	
	<aui:input type="checkbox" name="activities" 
	label="<%=learningActivityType.getName()  %>" checked="<%=checked %>" value="<%=learningActivityType.getTypeId()%>" />
	<%
}
%>
</aui:field-wrapper>
<aui:input type="submit" name="save" value="save" />

</aui:form>
<%
}
%>