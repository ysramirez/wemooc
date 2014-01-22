<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
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
		if(!StringPool.BLANK.equals(layoutsettemplateidsString[i])){
			layoutsettemplateids[i]=Long.parseLong(layoutsettemplateidsString[i]);
		}
	}
	List<Long> activityids = ListUtil.toList(StringUtil.split(prefs.getActivities(), ",", 0L));
	
	String[] courseEvalIdsString=prefs.getCourseevals().split(",");
	long[] courseEvalIds = new long[courseEvalIdsString.length];
	for(int i=0;i<courseEvalIdsString.length;i++){
		if(!StringPool.BLANK.equals(courseEvalIdsString[i])){
			courseEvalIds[i]=Long.parseLong(courseEvalIdsString[i]);
		}
	}
	String[] calificationTypeIdsString=prefs.getScoretranslators().split(",");
	long[] calificationTypeIds = new long[calificationTypeIdsString.length];
	for(int i=0;i<calificationTypeIdsString.length;i++){
		if(!StringPool.BLANK.equals(calificationTypeIdsString[i])){
			calificationTypeIds[i]=Long.parseLong(calificationTypeIdsString[i]);
		}
	}
	
%>
<liferay-portlet:actionURL name="changeSettings" var="changeSettingsURL">
</liferay-portlet:actionURL>
<aui:form action="<%=changeSettingsURL %>" method="POST">
<aui:input type="hidden" name="redirect" value="<%= currentURL %>" />
<aui:field-wrapper label="allowed-site-templates">
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
LearningActivityType [] learningActivityTypesCopy = new LearningActivityType[learningActivityTypes.size()];
Set<Long> learningActivityTypeIds = new HashSet<Long>();
Map<Long, Integer> mapaLats = new HashMap<Long, Integer>();

for (LearningActivityType learningActivityType : learningActivityTypes) {
	learningActivityTypeIds.add(learningActivityType.getTypeId());
}

int index = 0;
for (Long curActId : activityids)
{
	if (learningActivityTypeIds.contains(curActId)) {
		mapaLats.put(curActId, index++);
	}
}

for (LearningActivityType learningActivityType : learningActivityTypes) {
	Integer orderInt = mapaLats.get(learningActivityType.getTypeId());
	if (orderInt != null) {
		learningActivityTypesCopy[orderInt] = learningActivityType;
	} else {
		learningActivityTypesCopy[index++] = learningActivityType;
	}
}

for (LearningActivityType learningActivityType : learningActivityTypesCopy) {
	%>
	<li class="lms-sortable-activities">
		<aui:input type="checkbox" name="activities" label="<%= learningActivityType.getName() %>" checked="<%= (mapaLats.containsKey(learningActivityType.getTypeId())) %>" value="<%= learningActivityType.getTypeId() %>" />
	</li>
	<%
}
%>
</ul>

<aui:field-wrapper label="course-correction-method">
<%
CourseEvalRegistry courseEvalRegistry = new CourseEvalRegistry();
for(CourseEval courseEval:courseEvalRegistry.getCourseEvals())
{
	boolean checked=false;
	String writechecked="false";
	if(courseEvalIds!=null &&courseEvalIds.length>0 && ArrayUtils.contains(courseEvalIds, courseEval.getTypeId()))
	{
		checked=true;
		writechecked="true";
	}
	%>
	
	<aui:input type="checkbox" name="courseEvals" 
	label="<%=courseEval.getName()  %>" checked="<%=checked %>" value="<%=courseEval.getTypeId()%>" />
	<%
}
%>
</aui:field-wrapper>
<aui:field-wrapper label="calificationType">
<%
CalificationTypeRegistry calificationTypeRegistry = new CalificationTypeRegistry();
for(CalificationType calificationType :calificationTypeRegistry.getCalificationTypes())
{
	boolean checked=false;
	String writechecked="false";
	if(calificationTypeIds!=null &&calificationTypeIds.length>0 && ArrayUtils.contains(calificationTypeIds, calificationType.getTypeId()))
	{
		checked=true;
		writechecked="true";
	}
	%>
	
	<aui:input type="checkbox" name="calificationTypes" 
	label="<%=LanguageUtil.get(locale, calificationType.getTitle(locale))  %>" checked="<%=checked %>" value="<%=calificationType.getTypeId()%>" />
	<%
}
%>
</aui:field-wrapper>
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