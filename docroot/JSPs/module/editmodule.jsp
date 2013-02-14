<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@ page import="com.liferay.lms.model.Module" %>
<%@ page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>

<%@include file="../init.jsp" %>

<jsp:useBean id="editmoduleURL" class="java.lang.String" scope="request" />
<jsp:useBean id="module" type="com.liferay.lms.model.Module" scope="request"/>
<jsp:useBean id="moduleId" class="java.lang.String" scope="request" />
<jsp:useBean id="title" class="java.lang.String" scope="request" />
<jsp:useBean id="description" class="java.lang.String" scope="request" />
<jsp:useBean id="icon" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateDia" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateMes" class="java.lang.String" scope="request" />
<jsp:useBean id="startDateAno" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateDia" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateMes" class="java.lang.String" scope="request" />
<jsp:useBean id="endDateAno" class="java.lang.String" scope="request" />

<portlet:defineObjects />



<%

long moduleIde = ParamUtil.getLong(request,"moduleId",0);
Module theModule=null;
if(moduleIde>0)
{
	theModule=ModuleLocalServiceUtil.getModule(moduleIde);
%>
<aui:model-context bean="<%= theModule %>" model="<%= Module.class %>" />
<%
}
else
{
%>
<aui:model-context  model="<%= Module.class %>" />
<%
}
%>
<liferay-ui:success key="module-added-successfully" message="module-added-successfully" />

<aui:form name="addmodule" action="<%=editmoduleURL %>" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="resourcePrimKey" value="<%=module.getPrimaryKey() %>">
<%
	if(moduleIde>0)
	{
%>
	<aui:input type="hidden" name="ordern" value="<%=Long.toString(theModule.getOrdern()) %>" />
<%
	}
%>
	<aui:input name="title" label="title" />
	<liferay-ui:error key="module-title-required" message="module-title-required" />
	<aui:field-wrapper label="description">
			<liferay-ui:input-editor name="description" width="100%" />
			<aui:input name="description" type="hidden" />
				<script type="text/javascript">
        function <portlet:namespace />initEditor() { return "<%= UnicodeFormatter.toString(description) %>"; }
    </script>
		</aui:field-wrapper>
	<liferay-ui:error key="module-description-required" message="module-description-required" />
	<aui:input type="hidden" name="icon" />
	<br />
	 
<aui:field-wrapper label="icon">
		<aui:input inlineLabel="left" inlineField="true"
		  	name="fileName" label="" id="fileName" type="file" value="" />
</aui:field-wrapper>	
	
	<liferay-ui:error key="module-icon-required" message="module-icon-required" />
	<liferay-ui:error key="error_number_format" message="error_number_format" />
	<br />

	<aui:field-wrapper label="start-date">
		<liferay-ui:input-date  yearRangeEnd="2020" yearRangeStart="2012"
		 dayParam="startDateDia" dayValue="<%= Integer.valueOf(startDateDia) %>"
		  monthParam="startDateMes" monthValue="<%= Integer.valueOf(startDateMes)-1 %>"
		   yearParam="startDateAno" yearValue="<%= Integer.valueOf(startDateAno) %>"  yearNullable="false" 
				 dayNullable="false" monthNullable="false" ></liferay-ui:input-date>
		  
	</aui:field-wrapper>
	<liferay-ui:error key="module-startDate-required" message="module-startDate-required" />
	<aui:field-wrapper label="end-date">
		<liferay-ui:input-date  yearRangeEnd="2020" yearRangeStart="2012" dayParam="endDateDia" dayValue="<%= Integer.valueOf(endDateDia) %>" monthParam="endDateMes" monthValue="<%= Integer.valueOf(endDateMes)-1 %>" yearParam="endDateAno" yearValue="<%= Integer.valueOf(endDateAno) %>"  yearNullable="false" 
				 dayNullable="false" monthNullable="false" ></liferay-ui:input-date>
	</aui:field-wrapper>
	<liferay-ui:error key="module-endDate-required" message="module-endDate-required" />
	<aui:select label="Modulo predecesor" name="precedence">
<%
	java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
%>
		<aui:option value="0" >Ninguno</aui:option>
<%
	for(Module theModule2:modules)
	{
		boolean selected=false;
		if(moduleIde!= theModule2.getModuleId())
		{
			if(moduleIde>0&& theModule2.getModuleId()==module.getPrecedence())
			{
				selected=true;
			}
%>
		<aui:option value="<%=theModule2.getModuleId() %>" selected="<%=selected %>"><%=theModule2.getTitle(themeDisplay.getLocale()) %></aui:option>
<% 
		}
	}
%>
	</aui:select>       
	<% 
	String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
	%>	
	<portlet:renderURL var="cancelURL">
		<portlet:param name="moduleId" value="<%=Long.toString(moduleIde) %>"></portlet:param>
	</portlet:renderURL>
	<aui:button-row>
	<aui:button type="submit" onClick="<%=extractCodeFromEditor%>"></aui:button>
		<aui:button onClick="<%=cancelURL %>" value="<%=LanguageUtil.get(pageContext,\"cancel\")%>" type="cancel" />
		</aui:button-row>
</aui:form>
