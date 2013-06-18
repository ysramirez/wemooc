<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%@ include file="/init.jsp"%>

<%

String abstractLength = renderRequest.getPreferences().getValue("abstractLength", "200");

SCORMContent scorm=(SCORMContent) request.getAttribute("scorm");
String summary = scorm.getDescription();

summary = StringUtil.shorten(summary, Integer.valueOf(abstractLength), "...");

%>
<p><%= summary %></p>