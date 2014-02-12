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
	
	String contentDescriptiones_ES = "";
	String contentLongTitlees_ES = "";
	String contentTitlees_ES = "";
	
	String contentDescriptionen_US = "";
	String contentLongTitleen_US = "";
	String contentTitleen_US = "";
	
	String contentDescriptionpt_BR = "";
	String contentLongTitlept_BR= "";
	String contentTitlept_BR = "";
	
	String contentDescriptioneu_ES = "";
	String contentLongTitleeu_ES = "";
	String contentTitleeu_ES = "";
	
	String contentDescriptiongl_ES = "";
	String contentLongTitlegl_ES = "";
	String contentTitlegl_ES = "";
	
	String contentDescriptionca_ES = "";
	String contentLongTitleca_ES = "";
	String contentTitleca_ES = "";
	
	String es_ES = "";
	String en_US = "";
	String pt_BR = "";
	String gl_ES = "";
	String ca_ES = "";
	String eu_ES = "";
	
	%><liferay-ui:panel id="portlets" title="portlet" collapsible="true" defaultState="open"><%
	
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
						
				//Nombre portlet
				String portletStr = "";
				String name = pre.getPortletId();
				String[] parts = name.split("_WAR_");
				if(parts.length > 0){
					portletStr = parts[0];
				}
				
				//Guardarlo en cada idioma correspondiente
				if(element.elementText("name").endsWith("es_ES")){
					contentDescriptiones_ES += "javax.portlet.description."+portletStr+"="+element.elementText("value")+"<br />";
					contentLongTitlees_ES += "javax.portlet.long-title."+portletStr+"="+element.elementText("value")+"<br />";
					contentTitlees_ES += "javax.portlet.title."+portletStr+"="+element.elementText("value")+"<br />";
					
					es_ES += "<b>"+portletStr+"</b><br />";
					es_ES += "javax.portlet.title="+element.elementText("value")+"<br />";
					es_ES += "javax.portlet.description="+element.elementText("value")+"<br />";
					es_ES += "javax.portlet.long-title="+element.elementText("value")+"<br />";
				} 
				else if(element.elementText("name").endsWith("en_US")){
					contentDescriptionen_US += "javax.portlet.description."+portletStr+"="+element.elementText("value")+"<br />";
					contentLongTitleen_US += "javax.portlet.long-title."+portletStr+"="+element.elementText("value")+"<br />";
					contentTitleen_US += "javax.portlet.title."+portletStr+"="+element.elementText("value")+"<br />";
					
					en_US += "<b>"+portletStr+"</b><br />";
					en_US += "javax.portlet.title="+element.elementText("value")+"<br />";
					en_US += "javax.portlet.description="+element.elementText("value")+"<br />";
					en_US += "javax.portlet.long-title="+element.elementText("value")+"<br />";
				}
				else if(element.elementText("name").endsWith("gl_ES")){
					contentDescriptiongl_ES += "javax.portlet.description."+portletStr+"="+element.elementText("value")+"<br />";
					contentLongTitlegl_ES += "javax.portlet.long-title."+portletStr+"="+element.elementText("value")+"<br />";
					contentTitlegl_ES += "javax.portlet.title."+portletStr+"="+element.elementText("value")+"<br />";
					
					gl_ES += "<b>"+portletStr+"</b><br />";
					gl_ES += "javax.portlet.title="+element.elementText("value")+"<br />";
					gl_ES += "javax.portlet.description="+element.elementText("value")+"<br />";
					gl_ES += "javax.portlet.long-title="+element.elementText("value")+"<br />";
				}
				else if(element.elementText("name").endsWith("ca_ES")){
					contentDescriptionca_ES += "javax.portlet.description."+portletStr+"="+element.elementText("value")+"<br />";
					contentLongTitleca_ES += "javax.portlet.long-title."+portletStr+"="+element.elementText("value")+"<br />";
					contentTitleca_ES += "javax.portlet.title."+portletStr+"="+element.elementText("value")+"<br />";
					
					ca_ES += "<b>"+portletStr+"</b><br />";
					ca_ES += "javax.portlet.title="+element.elementText("value")+"<br />";
					ca_ES += "javax.portlet.description="+element.elementText("value")+"<br />";
					ca_ES += "javax.portlet.long-title="+element.elementText("value")+"<br />";
				}
				else if(element.elementText("name").endsWith("eu_ES")){
					contentDescriptioneu_ES += "javax.portlet.description."+portletStr+"="+element.elementText("value")+"<br />";
					contentLongTitleeu_ES += "javax.portlet.long-title."+portletStr+"="+element.elementText("value")+"<br />";
					contentTitleeu_ES += "javax.portlet.title."+portletStr+"="+element.elementText("value")+"<br />";
					
					eu_ES += "<b>"+portletStr+"</b><br />";
					eu_ES += "javax.portlet.title="+element.elementText("value")+"<br />";
					eu_ES += "javax.portlet.description="+element.elementText("value")+"<br />";
					eu_ES += "javax.portlet.long-title="+element.elementText("value")+"<br />";
				}
				else if(element.elementText("name").endsWith("pt_BR")){
					contentDescriptionpt_BR += "javax.portlet.description."+portletStr+"="+element.elementText("value")+"<br />";
					contentLongTitlept_BR += "javax.portlet.long-title."+portletStr+"="+element.elementText("value")+"<br />";
					contentTitlept_BR += "javax.portlet.title."+portletStr+"="+element.elementText("value")+"<br />";
					
					pt_BR += "<b>"+portletStr+"</b><br />";
					pt_BR += "javax.portlet.title="+element.elementText("value")+"<br />";
					pt_BR += "javax.portlet.description="+element.elementText("value")+"<br />";
					pt_BR += "javax.portlet.long-title="+element.elementText("value")+"<br />";
				}
				
			}
		}
	}
%>
</liferay-ui:panel>

<liferay-ui:panel id="es_ES" title="es_ES" collapsible="true" defaultState="closed"><%=es_ES%><br /><%=contentDescriptiones_ES %><br /><%=contentLongTitlees_ES %><br /><%=contentTitlees_ES %></liferay-ui:panel>
<liferay-ui:panel id="en_US" title="en_US" collapsible="true" defaultState="closed"><%=en_US%><br /><%=contentDescriptionen_US %><br /><%=contentLongTitleen_US %><br /><%=contentTitleen_US %></liferay-ui:panel>
<liferay-ui:panel id="gl_ES" title="gl_ES" collapsible="true" defaultState="closed"><%=gl_ES%><br /><%=contentDescriptiongl_ES %><br /><%=contentLongTitlegl_ES %><br /><%=contentTitlegl_ES %></liferay-ui:panel>
<liferay-ui:panel id="ca_ES" title="ca_ES" collapsible="true" defaultState="closed"><%=ca_ES%><br /><%=contentDescriptionca_ES %><br /><%=contentLongTitleca_ES %><br /><%=contentTitleca_ES %></liferay-ui:panel>
<liferay-ui:panel id="eu_ES" title="eu_ES" collapsible="true" defaultState="closed"><%=eu_ES%><br /><%=contentDescriptioneu_ES %><br /><%=contentLongTitleeu_ES %><br /><%=contentTitleeu_ES %></liferay-ui:panel>
<liferay-ui:panel id="pt_BR" title="pt_BR" collapsible="true" defaultState="closed"><%=pt_BR%><br /><%=contentDescriptionpt_BR %><br /><%=contentLongTitlept_BR %><br /><%=contentTitlept_BR %></liferay-ui:panel>