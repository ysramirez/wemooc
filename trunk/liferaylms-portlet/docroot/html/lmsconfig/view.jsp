<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
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
		layoutsettemplateids[i]=Long.parseLong(layoutsettemplateidsString[i]);
	}
	List<Long> activityids = ListUtil.toList(StringUtil.split(prefs.getActivities(), ",", 0L));
%>
<liferay-portlet:actionURL name="changeSettings" var="changeSettingsURL">
</liferay-portlet:actionURL>
<aui:form action="<%=changeSettingsURL %>" method="POST">
<aui:input type="hidden" name="redirect" value="<%= currentURL %>" />
<aui:field-wrapper label="site-templates">
<%

for(LayoutSetPrototype layoutsetproto:LayoutSetPrototypeLocalServiceUtil.search(themeDisplay.getCompanyId(),true,0, 1000000,null))
{
	boolean checked=false;
	if(ArrayUtils.contains(layoutsettemplateids, layoutsetproto.getLayoutSetPrototypeId()))
	{
		checked=true;
	}
	%>
	
	<aui:input type="checkbox" name="lmsTemplates" 
	label="<%=layoutsetproto.getName(themeDisplay.getLocale())  %>" checked="<%=checked %>" value="<%=layoutsetproto.getLayoutSetPrototypeId()%>" />
	<%
}
%>
</aui:field-wrapper>
<liferay-ui:header title="lms-activities"/>
<ul id="lms-sortable-activities-contentor">
<%

LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();
List<LearningActivityType> learningActivityTypes = learningActivityTypeRegistry.getLearningActivityTypes();
Map<Long, LearningActivityType> mapaLats = new HashMap<Long, LearningActivityType>();

int numMarkedActivities = activityids.size();

for (LearningActivityType learningActivityType : learningActivityTypes)
{
	mapaLats.put(learningActivityType.getTypeId(), learningActivityType);
	if (!activityids.contains(learningActivityType.getTypeId())) {
		activityids.add(learningActivityType.getTypeId());
	}
}


int i = 0;
for (Long activityId : activityids) {
	LearningActivityType learningActivityType = mapaLats.get(activityId);
	%>
	<li class="lms-sortable-activities">
		<aui:input type="checkbox" name="activities" label="<%= learningActivityType.getName() %>" checked="<%= (i < numMarkedActivities) %>" value="<%= learningActivityType.getTypeId() %>" />
	</li>
	<%
	i++;
}
%>
</ul>

<aui:input type="submit" name="save" value="save" />

</aui:form>
<%
}
%>

<script type="text/javascript">
AUI().ready(
    'aui-sortable',
   function(A) {
        window.<portlet:namespace/>lmsActivitiesSortable = new A.Sortable(
            {
                nodes: '.lms-sortable-activities'
            }
        );
    }
);

</script>