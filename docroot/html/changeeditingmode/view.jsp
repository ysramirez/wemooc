<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);
String text="enable-edition";
if(actionEditing)
{
    text="disable-edition";
}
%>

<portlet:renderURL var="changeEditingMode">
<portlet:param name="actionEditing" value="<%=Boolean.toString(!actionEditing)%>">
</portlet:param>
</portlet:renderURL>
<%
String redirect="self.location='"+changeEditingMode.toString()+"'";
%>
<aui:button type="submit"  value="<%=text %>" onClick="<%=redirect %>"></aui:button>
