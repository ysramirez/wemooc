<%@page import="java.util.HashSet"%>
<%@page import="com.liferay.portal.kernel.dao.orm.OrderFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil"%>
<%@page import="com.liferay.portal.model.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="javax.swing.plaf.ListUI"%>
<%@page import="com.liferay.portal.model.Plugin"%>
<%@page import="com.liferay.portal.service.PluginSettingLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.PluginSetting"%>
<%@page import="com.liferay.portal.kernel.plugin.PluginPackage"%>
<%@page import="com.liferay.portal.model.LayoutTemplate"%>
<%@page import="com.liferay.portal.model.LayoutTypePortlet"%>
<%@page import="com.liferay.portal.service.PortletServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.Properties"%>
<%@ include file="/init.jsp"%>

<liferay-ui:header title="<%=LanguageUtil.get(pageContext,\"portaladmin.portlet_titles\")%>"></liferay-ui:header>

<%
	DynamicQuery dq = new DynamicQueryFactoryUtil().forClass(PortletPreferences.class)
	.add(RestrictionsFactoryUtil.like("preferences","%portletSetupTitle%"))
	.add(RestrictionsFactoryUtil.or(RestrictionsFactoryUtil.like("portletId", "%liferaylmsportlet%"),RestrictionsFactoryUtil.like("portletId","%weclassportlet%")))
	.addOrder(OrderFactoryUtil.getOrderFactory().asc("portletId"));

	List<PortletPreferences> prefs = PortletPreferencesLocalServiceUtil.dynamicQuery(dq);
	
	String id = "";
	for(PortletPreferences pre : prefs){
		
		if(pre.getPortletId().equals(id)){
			continue;
		}
		id=pre.getPortletId();
		
		System.out.println(pre.getPortletId());
		%><h4><%=pre.getPortletId() %></h4><%
		Document document = SAXReaderUtil.read(pre.getPreferences());
		Element rootElement = document.getRootElement();
		for (Element element : rootElement.elements("preference")) {
			if(element.elementText("name").startsWith("portletSetupTitle")){
				System.out.println("   " + element.elementText("name") + " - " + element.elementText("value"));
				%><p><%=element.elementText("name") %> - <%=element.elementText("value") %></p><%
			}
		}
	}
%>
