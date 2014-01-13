<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@include file="/init.jsp" %>

<%

long moduleId = ParamUtil.getLong(request, "moduleId", 0);

List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId()); 
List<LearningActivity> activities = Collections.EMPTY_LIST;

if(moduleId > 0){
	LearningActivityServiceUtil.getLearningActivitiesOfModule(moduleId);
}
%>

<aui:select label="modules" name="modules" onChange='<%= renderResponse.getNamespace() + "actualizarCombos(this.value);"%>'>
	<aui:option  value="all-modules"><liferay-ui:message key="all"/></aui:option>
	<%for(Module module:modules){	%>
		<aui:option value="<%=module.getModuleId() %>"><%=module.getTitle()%></aui:option>
	<%}%>
</aui:select>

<aui:select label="activities" name="activities" onChange='<%= renderResponse.getNamespace() + "actualizarCombos(this.value);"%>'>
	<aui:option  value="all-modules"><liferay-ui:message key="all"/></aui:option>
	<%for(LearningActivity activity:activities){	%>
		<aui:option value="<%=activity.getActId() %>"><%=activity.getTitle(themeDisplay.getLanguageId())%></aui:option>
	<%}%>
</aui:select>