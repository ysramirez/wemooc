<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<%
	long userId=ParamUtil.getLong(request,"userId",0);
	String returnurl=ParamUtil.getString(request,"returnurl","");
	User usuario=UserLocalServiceUtil.getUser(userId);
	String title = LanguageUtil.get(pageContext,"results") +" "+ usuario.getFullName();
%>

<liferay-ui:header title="<%= title %>" backURL="<%=returnurl %>"></liferay-ui:header>
<liferay-ui:search-container  emptyResultsMessage="there-are-no-results" delta="5" deltaConfigurable="true">
	<liferay-ui:search-container-results>
		<%
			List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());	
			pageContext.setAttribute("results", modules);
		    pageContext.setAttribute("total", modules.size());
		%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row className="com.liferay.lms.model.Module" keyProperty="moduleId" modelVar="theModule">
		<liferay-ui:search-container-column-text name="module">
			<%=theModule.getTitle(themeDisplay.getLocale()) %>
			<% String mstatus ="not-started";
			ModuleResult mr=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),userId);
		    if(mr!=null){
		    	mstatus="started";
		    	if(mr.getPassed()) mstatus="passed";
		    }%>
			<%if(mstatus.equals("passed")){%>
				<liferay-ui:icon image="checked" alt="passed"></liferay-ui:icon>
			<%}
			if(mstatus.equals("not-passed")){%>
				<liferay-ui:icon image="close" alt="not-passed"></liferay-ui:icon>
			<%}
			if(mstatus.equals("started")){%>
				<liferay-ui:icon image="unchecked"></liferay-ui:icon>
			<%}	%>
		</liferay-ui:search-container-column-text>
		<% 	List<LearningActivity> activities = LearningActivityServiceUtil.getLearningActivitiesOfModule(theModule.getModuleId());
		for(LearningActivity learningActivity:activities){
		%>
			<%long result=0;
			String status="not-started";
			if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(learningActivity.getActId(), usuario.getUserId())){
				status="started";
				LearningActivityResult learningActivityResult=LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), usuario.getUserId());
				result=learningActivityResult.getResult();
				if(learningActivityResult.getEndDate()!=null){
						status="not-passed"	;
				}
				if(learningActivityResult.isPassed()){
					status="passed"	;
				}
			}%>
			<liferay-ui:search-container-column-text cssClass="number-column" name = "">
				<p><%=learningActivity.getTitle(themeDisplay.getLocale()) %></p>
				<%=result %>
				<%if(status.equals("passed")){%>
					<liferay-ui:icon image="checked" alt="passed"></liferay-ui:icon>
				<%}
				if(status.equals("not-passed")){%>
					<liferay-ui:icon image="close" alt="not-passed"></liferay-ui:icon>
				<%}
				if(status.equals("started")){%>
					<liferay-ui:icon image="unchecked"></liferay-ui:icon>
				<%}%>
			</liferay-ui:search-container-column-text>
		<%} %>
	</liferay-ui:search-container-row>
 	<liferay-ui:search-iterator />
</liferay-ui:search-container>



	
