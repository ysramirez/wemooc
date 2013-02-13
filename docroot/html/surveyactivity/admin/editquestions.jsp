<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>

<%
	LearningActivity learnact=null;
	if(request.getAttribute("activity")!=null)
	{
		learnact=(LearningActivity)request.getAttribute("activity");
	}
	else
	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
	}
%>


	<%
	
	boolean isSurvey = learnact.getTypeId() == 4;
	
	if(isSurvey && permissionChecker.hasPermission(learnact.getGroupId(),LearningActivity.class.getName(),learnact.getActId(), ActionKeys.UPDATE)){		
	%>
	<div class="surveyactivity editquestions">

		<div class="stadistics">
			<portlet:renderURL var="stadisticsURL">
				<portlet:param name="jspPage" value="/html/surveyactivity/stadistics.jsp"></portlet:param>
				<portlet:param name="actId" value="<%=String.valueOf(learnact.getActId()) %>" />
			</portlet:renderURL>
			<liferay-ui:icon image="view" message="surveyactivity.stadistics" label="true" url="<%=stadisticsURL.toString() %>" />
		</div>

	<div class="new_question">
		<portlet:renderURL var="newquestionURL">
			<portlet:param name="actId" value="<%=String.valueOf(learnact.getActId()) %>" />
			<portlet:param name="jspPage" value="/html/surveyactivity/admin/newquestion.jsp"></portlet:param>
		</portlet:renderURL>
		<liferay-ui:icon image="add" label="<%= true %>" message="new Question" url='<%= newquestionURL %>' />
	</div>
	<%
		java.util.List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(learnact.getActId());
	%>
	<liferay-ui:search-container emptyResultsMessage="there-are-no-questions" delta="5">
		<liferay-ui:search-container-results>
			<%
			results = ListUtil.subList(questions, searchContainer.getStart(),
			searchContainer.getEnd());
			total = questions.size();
			pageContext.setAttribute("results", results);
			pageContext.setAttribute("total", total);
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.TestQuestion" keyProperty="actId" modelVar="activity">
			<liferay-ui:search-container-column-text name="text" property="text"/>
			<liferay-ui:search-container-column-text name="questionType" property="questionType"/>
			<liferay-ui:search-container-column-jsp path="/html/surveyactivity/admin/admin_actions.jsp" align="right" />
		</liferay-ui:search-container-row>
		
		<liferay-ui:search-iterator />
	
	</liferay-ui:search-container>
</div>
<%}%>