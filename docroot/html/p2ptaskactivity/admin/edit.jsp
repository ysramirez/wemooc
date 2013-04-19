<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityLocalServiceImpl"%>
<%@page	import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page	import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@ page import="com.liferay.lms.model.Module" %>
<%@ page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>

<%@ include file="init.jsp"%>


<div class="p2ptaskactivity admin edit">
<%
	long numEvaluaciones = 3;
	boolean anonimous = false;
	String anonimousString = "";
	boolean result = false;
	String resultString = "";
	int numActivity =0;
	boolean disabled=false;
	String type="text";
	boolean deshabilitar= false;
	
	if (learnact.getExtracontent() != null && !learnact.getExtracontent().trim().equals("")) {

		String evaluaciones = LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"validaciones");
		
		try{
			numEvaluaciones = Long.parseLong(evaluaciones);
		}catch(Exception e){}
		
		anonimousString = LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"anonimous");
	
		if(anonimousString.equals("true")){
			anonimous = true;
		}
		
		resultString = LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"result");
		
		if(resultString.equals("true")){
			result = true;
		}
			
		List<P2pActivity> p = P2pActivityLocalServiceUtil.findByActId(learnact.getActId());
		if( p!=null && p.size()>0){
			deshabilitar=true;
		}
		%>
		<liferay-ui:message key="numEval" />:
		<%=numEvaluaciones%>
		<%
	}
%>

	<portlet:actionURL var="numValidacionesURL" name="numValidaciones">
	</portlet:actionURL>
	<portlet:renderURL var="cancel">
		
		<portlet:param name="actId" value="<%=Long.toString(learnact.getActId()) %>"></portlet:param>
		<portlet:param name="moduleId" value="<%=Long.toString(learnact.getModuleId()) %>"></portlet:param>
		<portlet:param name="jspPage" value="/html/p2ptaskactivity/admin/edit.jsp" />
	</portlet:renderURL>
	
	<aui:form action="<%=numValidacionesURL%>" method="post">
		<aui:input name="actId" type="hidden" value="<%=learnact.getActId()%>"></aui:input>
		
		<aui:input type="checkbox" name="anonimous" label="p2ptaskactivity.edit.anonimous" checked="<%=anonimous %>"></aui:input>
		
		<c:if test="<%=deshabilitar %>">
			<aui:input type="hidden" size="5" name="numValidaciones" label="p2ptaskactivity.edit.numvalidations" value="<%=numEvaluaciones%>" ></aui:input>
			<aui:input type="checkbox" name="r" label="test.result" checked="<%=result %>" disabled="<%=true %>"></aui:input>
			<aui:input size="3" name="n" label="p2ptaskactivity.edit.numvalidations" value="<%=numEvaluaciones%>"  disabled="<%=true %>"></aui:input>
			<aui:input type="hidden" name="result" label="test.result" value="<%=result %>" ></aui:input>
		</c:if>
		<c:if test="<%=!deshabilitar %>">
			<aui:input type="checkbox" name="result" label="test.result" checked="<%=result %>" ></aui:input>
			<aui:input size="3" name="numValidaciones" label="p2ptaskactivity.edit.numvalidations" value="<%=numEvaluaciones%>" ></aui:input>
		</c:if>
		
		<%
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
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

		boolean canEdit2 = true;
		if(theModule!= null && theModule.getStartDate()!=null && theModule.getStartDate().before(new Date())
				&&!permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(),course.getCourseId(),"COURSEEDITOR")){
			canEdit2 = false;
		}
		%>
		<aui:button-row>
		<% 
			String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
		%>									
		<% if(canEdit2){ %>
			<aui:button type="submit">Submit</aui:button>
		<% } %>
			<aui:button onClick="<%=cancel %>" value="<%=LanguageUtil.get(pageContext,\"cancel\")%>" type="cancel" />
		</aui:button-row>
	</aui:form>

</div>