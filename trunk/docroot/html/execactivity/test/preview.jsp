<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>

<%
long actId=ParamUtil.getLong(request,"actId",0);
long userId = themeDisplay.getUserId();

if(actId==0)
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
	LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	long typeId=activity.getTypeId();

	if(typeId==0 && LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, userId))
	{
		int tries = LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId, themeDisplay.getUserId());
		
		Object  [] arguments =  new Object[]{tries,activity.getTries()};
		
		Object  [] arguments2 =  new Object[]{activity.getPasspuntuation()};
		%>
		<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
		<h3><liferay-ui:message key="execativity.test.try.notification" /></h3>
		<%if(activity.getTries()>0)
		{
			%>
		
		<h3><liferay-ui:message key="execativity.test.try.count" arguments="<%=arguments %>" /></h3>
		<%
		}
		%>
		<h3><liferay-ui:message key="execativity.test.try.pass.puntuation" arguments="<%=arguments2 %>" /></h3>
		<h3><liferay-ui:message key="execativity.test.try.confirmation" /></h3>
		
		<portlet:renderURL var="correctURL">
			<portlet:param name="actId" value="<%=Long.toString(actId) %>"></portlet:param>
			<portlet:param name="jspPage" value="/html/execactivity/test/view.jsp" />
		</portlet:renderURL>
		
		<portlet:renderURL var="cancel">
			<portlet:param name="actId" value="<%=Long.toString(actId) %>"></portlet:param>
			<portlet:param name="moduleId" value="<%=Long.toString(activity.getModuleId()) %>"></portlet:param>
			<portlet:param name="jspPage" value="/html/moduledescription/view.jsp" />
		</portlet:renderURL>
		
		<aui:form name="formulario" action="<%=correctURL %>" method="POST">
			<aui:button type="submit" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.start\")%>" />
			<aui:button onClick="<%=cancel %>" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.cancel\")%>" type="cancel" />
		</aui:form>
<%
	}else{
		LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
		Object  [] arguments =  new Object[]{result.getResult()};
		Object  [] arg =  new Object[]{activity.getPasspuntuation()};
		%>
		<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
		<h3><liferay-ui:message key="test-done" /></h3>
		<h4><liferay-ui:message key="your-result" arguments="<%=arguments %>" /></h4>
		<%if(!result.getPassed())
			{
			%>
		<h4><liferay-ui:message key="your-result-dont-pass"  arguments="<%=arg %>" /></h4>
		<h4><liferay-ui:message key="your-result-no-more-tries" /></h4>
		<% 
		} 
		List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
		HashMap<Long, Long> answersMap = LearningActivityTryLocalServiceUtil.getMapTryResultData(actId, userId);
		
			for(TestQuestion question:questions)
			{
				String cssclass="question incorrect";
				String feedback="";
				if(answersMap.containsKey(question.getQuestionId()))
				{
					long answerselectedid=answersMap.get(question.getQuestionId());
					TestAnswer answerSelected=TestAnswerLocalServiceUtil.getTestAnswer(answerselectedid);
					if(answerSelected.isIsCorrect())
					{
						 cssclass="question correct";
						 feedback=answerSelected.getFeedbackCorrect();
					}
					else
					{
						feedback=answerSelected.getFeedbacknocorrect();
						cssclass="question incorrect";
					}
				}
				%>
				<div class="<%=cssclass%>">
					<div class="questiontext"><%=question.getText() %></div>
					<div class="content_answer">
				<%
				List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
				for(TestAnswer answer:testAnswers)
				{
					String checked="";
					if(answersMap.containsKey(question.getQuestionId())	&& answersMap.get(question.getQuestionId()) == answer.getAnswerId()){
						checked="checked='checked'";
						
					}
					%>
					<div class="answer">
						<input type="radio" name="question_<%=question.getQuestionId()%>"  <%=checked %> value="<%=answer.getAnswerId() %>"  disabled="disabled"><%=answer.getAnswer() %>
					</div>
					<%
				}
				%>
				</div>
				<div class="questionFeedback"><%=feedback%></div>
				</div>
				<% 
			}		
		
	}
}%>