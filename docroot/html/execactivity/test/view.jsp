<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>

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
	
	if(typeId==0&&(!LearningActivityLocalServiceUtil.islocked(actId,userId)||
			permissionChecker.hasPermission(
			activity.getGroupId(),
			LearningActivity.class.getName(),
			actId, ActionKeys.UPDATE)))
	{
		%>

		<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
		<p><%=activity.getDescription(themeDisplay.getLocale()) %></p>
		
		<%
		if((!LearningActivityLocalServiceUtil.islocked(actId,userId)||
				permissionChecker.hasPermission(
						activity.getGroupId(),
						LearningActivity.class.getName(),
						actId, ActionKeys.UPDATE)))
		{		
		if(LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
		{
			LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			Object  [] arguments =  new Object[]{result.getResult()};
			%>
			<h3><liferay-ui:message key="test-done" /></h3>
			<h4><liferay-ui:message key="your-result" arguments="<%=arguments %>" /></h4>
			<h4><liferay-ui:message key="your-result-pass" /></h4>
			<h5><liferay-ui:message key="your-answers" /></h5>
			<%
			
			HashMap<Long, Long> answersMap = LearningActivityTryLocalServiceUtil.getMapTryResultData(actId, userId);
			List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
			
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
		else if (LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, userId))
		{
			ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
			
			LearningActivityTry learningTry = LearningActivityTryLocalServiceUtil.getLearningActivityTryNotFinishedByActUser(actId,userId);
			
			//Comprobar si tenemos un try sin fecha de fin, para continuar en ese try.
			if(learningTry == null)
			{
				learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
			}
			
			List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
			Object  [] arg =  new Object[]{activity.getPasspuntuation()};
			%>
			
			<h3><liferay-ui:message key="execativity.test.try.pass.puntuation" arguments="<%=arg %>" /></h3>
			
			<portlet:actionURL name="correct" var="correctURL">
			<portlet:param name="actId" value="<%=Long.toString(actId) %>" ></portlet:param>
			<portlet:param name="latId" value="<%=Long.toString(learningTry.getLatId()) %>"></portlet:param>
			<
			</portlet:actionURL>
			<aui:form name="formulario" action="<%=correctURL %>" method="POST">
			<%
			for(TestQuestion question:questions)
			{
				%>
				<div class="question">
				<div class="questiontext"><%=question.getText() %></div>
				<%
				List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
				for(TestAnswer answer:testAnswers)
				{
					%>
					<div class="answer"><input type="radio" name="question_<%=question.getQuestionId()%>" value="<%=answer.getAnswerId() %>" ><%=answer.getAnswer() %>
					</div>
					<%
				}
				%>
				</div>
				<%
			}
			%>
			<aui:button type="submit"></aui:button>
			</aui:form>
			<%
		} 
		//Si no ha pasado el test, ni tiene más intentos.
		else 
		{
			LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			Object  [] arguments =  new Object[]{result.getResult()};
			Object  [] arg =  new Object[]{activity.getPasspuntuation()};
			%>
			<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
			<h3><liferay-ui:message key="test-done" /></h3>
			<h4><liferay-ui:message key="your-result" arguments="<%=arguments %>" /></h4>
			<h4><liferay-ui:message key="your-result-dont-pass"  arguments="<%=arg %>" /></h4>
			<h4><liferay-ui:message key="your-result-no-more-tries" /></h4>
			<%
		}
		
		}
	}
}
%>