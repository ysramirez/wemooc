<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil" %>
<%@page import="com.liferay.portal.kernel.dao.orm.Criterion" %>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery" %>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil" %>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil" %>
<%@ include file="/init.jsp" %>

<%
long actId=ParamUtil.getLong(request, "resId", 0);
DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityTry.class);
	Criterion criterion=PropertyFactoryUtil.forName("actId").eq(actId);
dq.add(criterion);
long tries = LearningActivityTryLocalServiceUtil.dynamicQueryCount(dq);
%>
<aui:select label="team-activity" name="team" helpMessage="<%=LanguageUtil.get(pageContext,\"helpmessage.team\")%>" disabled="<%=(tries != 0) %>" >
<%
	
	long teamId=ParamUtil.getLong(request, "teamId", 0);

	List<Team> teams=TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
%>
			<aui:option value="0" selected="<%=(teamId==0)%>" ><%=LanguageUtil.get(pageContext,"none")%></aui:option>
<%
	for(Team team : teams)
	{
	%>
			<aui:option value="<%=team.getTeamId() %>" selected="<%=(teamId==team.getTeamId()) %>"><%=team.getName() %></aui:option>
	<% 
	}
%>
</aui:select>