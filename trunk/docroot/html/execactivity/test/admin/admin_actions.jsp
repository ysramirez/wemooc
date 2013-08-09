<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
TestQuestion question = (TestQuestion)row.getObject();
long groupId = themeDisplay.getLayout().getGroupId();
String name = TestQuestion.class.getName();
String primKey = String.valueOf(question.getQuestionId());
String actId = String.valueOf(question.getActId());
QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
%>

<portlet:renderURL var="editURL">
	<portlet:param name="jspPage" value="<%=qt.getURLEdit() %>"></portlet:param>
	<portlet:param name="questionId" value="<%=primKey %>" />
	<portlet:param name="resId" value="<%= actId %>" />
	<portlet:param name="actionEditingDetails" value="<%= StringPool.TRUE %>" />	
</portlet:renderURL>
<liferay-ui:icon image="edit" message="edit" url="<%=editURL.toString() %>" />
<portlet:actionURL name="deletequestion" var="deleteURL">
	<portlet:param name="questionId" value="<%= primKey %>" />
	<portlet:param name="resId" value="<%= actId %>" />
</portlet:actionURL>
<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />

