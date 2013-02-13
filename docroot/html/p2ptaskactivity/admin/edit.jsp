<%@page	import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page	import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ include file="init.jsp"%>

<%
	long numEvaluaciones = 0;
	if (learnact.getExtracontent() != null && !learnact.getExtracontent().trim().equals("")) {
		numEvaluaciones = Long.valueOf(learnact.getExtracontent());
		%>
		<liferay-ui:message key="numEval" />:
		<%=numEvaluaciones%>
		<%
	}
%>
<portlet:actionURL var="numValidacionesURL" name="numValidaciones">
</portlet:actionURL>
<portlet:renderURL var="cancel">
	<portlet:param name="actId" value="0"></portlet:param>
</portlet:renderURL>

<aui:form action="<%=numValidacionesURL%>" method="post">
	<aui:input name="actId" type="hidden" value="<%=learnact.getActId()%>"></aui:input>
	<aui:input size="5" name="numValidaciones" label="numValidaciones" value="<%=numEvaluaciones%>"></aui:input>

	<aui:button-row>
		<aui:button type="submit">Submit</aui:button>
		<aui:button onclick="<%=cancel%>" type="cancel" />
	</aui:button-row>
</aui:form>