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
	
<%
long actId = ParamUtil.getLong(request,"actId",0);
String res = ParamUtil.getString(request,"resultados","");

if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),LearningActivity.class.getName() , actId, "CORRECT")){
%>	

<style>
	.action{border: 1px solid #000;margin:10px;padding:10px;}
</style>


<%

	Properties prop = new Properties();
	long buildNumber = 0;
	Date date = new Date(0);
	try {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		prop.load(classLoader.getResourceAsStream("service.properties"));
	
		buildNumber = Long.valueOf(prop.getProperty("build.date",""));
		
		date = new Date(buildNumber);
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

%>



<%

List<String> portletIds = new ArrayList<String>();

List<Layout> layoutForPortletName = LayoutLocalServiceUtil.getLayouts(0, LayoutLocalServiceUtil.getLayoutsCount()); 

//System.out.println("\n\nLayouts:  " +layoutForPortletName.size()); 

for(Layout l:layoutForPortletName){
	
	//System.out.println("  Layout:  " +l.getName(themeDisplay.getLocale()));

	LayoutTypePortlet layoutTypePortletName = (LayoutTypePortlet)l.getLayoutType(); 
	LayoutTemplate layoutTemplate=layoutTypePortletName.getLayoutTemplate(); 
	List columnCount = layoutTemplate.getColumns(); 

	for(int i=0; i < columnCount.size() ; i++){
		
		String column = (String)columnCount.get(i); 
		List<Portlet> portlets = layoutTypePortletName.getAllPortlets(column); 

		for(Portlet portlet : portlets){ 
			
			//System.out.println("    portletId = "+portlet.getPortletId()+" "+" Title = " + PortalUtil.getPortletTitle(portlet, application, locale)); 
			
			if(!portletIds.contains(portlet.getPortletId()) && portlet.getPortletId().contains("_WAR_liferaylmsportlet")){
				portletIds.add(portlet.getPortletId());
			}
		}  
	}	

}

%>

<h3><%= "Build date: "	+ date.toString() %></h3>
<h3><%= "Build number: "+ prop.getProperty("build.number","") %></h3>
<h3><%= "Auto upgrade: "+ prop.getProperty("build.auto.upgrade","") %></h3>


<div class="actions">

	<div class="action">
		<portlet:actionURL name="asignP2pActivity" var="asignP2pActivityURL" />
		<liferay-ui:icon image="assign" label="<%=true %>" message="p2ptaskactivity.edit.asignp2p" url='<%= asignP2pActivityURL %>'/>
	</div>
	
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.updateModulePassedDate" /></h4>
		<portlet:actionURL name="updateModulePassedDate" var="updateModulePassedDateURL" />
		<aui:form action="<%=updateModulePassedDateURL %>" method="POST" name="form_mail">		
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox" helpMessage="portaladmin.updateModulePassedDate"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.updateModulePassedDate" class="submit"></aui:button>
			</aui:button-row>
		</aui:form>
		<div>
			<a onClick="openLogs('/custom_logs/updateModulePassedDate.txt')" style="Cursor:pointer;">updateModulePassedDate.txt</a>
		</div>
	</div>
	
	<div class="action">
		<h4><liferay-ui:message key="cambiar nombre del portlet" /></h4>
		<portlet:actionURL name="changePortletName" var="changePortletNameURL" />
		<aui:form action="<%=changePortletNameURL %>" method="POST" name="form_mail">
		
			<aui:select name="before" label="portaladmin.portletname.before">
			<%
			 	java.util.Collections.sort(portletIds);
				for(String  name:portletIds){
					%>
					<aui:option value="<%=name %>"><%=name %></aui:option>
					<%
				}

			%>
			</aui:select>
			
			<aui:select name="after" label="portaladmin.portletname.after">
			<%
				List<Portlet> portlets = PortletLocalServiceUtil.getPortlets();
				for(Portlet  portlet:portlets){
					if(portlet.getPortletId().contains("_WAR_liferaylmsportlet")){
						%>
						<aui:option value="<%=portlet.getPortletId() %>"><%=portlet.getPortletId() %></aui:option>
						<%
					}
				}
			%>
			</aui:select>
			
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<div class="action">
		<portlet:actionURL name="updateResultP2PActivities" var="updateResultP2PActivitiesURL" />
		<liferay-ui:icon image="recent_changes" label="<%=true %>" message="p2ptaskactivity.updateResult" url='<%= updateResultP2PActivitiesURL %>'/>
		<div>
			<a onClick="openLogs('/custom_logs/updateResultP2PActivities.txt')" style="Cursor:pointer;">updateResultP2PActivities.txt</a>
		</div>
	</div>
	
	<portlet:actionURL name="updateExtraContentMultimediaActivities" var="updateExtraContentMultimediaActivitiesURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.multimedia.updateextracontent" /></h4>
		<aui:form action="<%=updateExtraContentMultimediaActivitiesURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<portlet:actionURL name="updateExtraContentScormActivities" var="updateExtraContentScormActivitiesURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.scorm.updateextracontent" /></h4>
		<aui:form action="<%=updateExtraContentScormActivitiesURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="send" label="portaladmin.multimedia.updatebd" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
	</div>
	
	<portlet:actionURL name="deleteRepeatedModuleResult" var="deleteRepeatedModuleResultURL" />
	<div class="action">
		<h4><liferay-ui:message key="portaladmin.multimedia.deleteRepeatedModuleResult" /></h4>
		<aui:form action="<%=deleteRepeatedModuleResultURL %>" method="POST" name="form_mail">
			<aui:input name="updateBD" label="portaladmin.multimedia.updatebd" type="checkbox"></aui:input>
			<aui:button-row>
				<aui:button type="submit" value="update" class="submit" ></aui:button>
			</aui:button-row>
		</aui:form>
		<div>
			<a onClick="openLogs('/custom_logs/deleteRepeatedModuleResult.txt')" style="Cursor:pointer;">deleteRepeatedModuleResult.txt</a>
		</div>
	</div>

	<div class="resultado">
		<%=res %>
	</div>

</div>
<%
}
%>