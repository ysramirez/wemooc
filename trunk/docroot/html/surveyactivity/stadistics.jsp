<%@page import="java.text.DecimalFormat"%>
<%@page import="com.liferay.lms.model.SurveyResult"%>
<%@page import="com.liferay.lms.service.SurveyResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>


<%@ include file="/init.jsp"%>

<%
	if (ParamUtil.getLong(request, "actId", 0) == 0) {
		renderRequest.setAttribute( WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	} 
	else
	{
		long actId = ParamUtil.getLong(request, "actId",0);
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	%>
		<div class="surveyactivity stadistics">
		
			<h2><%=LearningActivityLocalServiceUtil.getLearningActivity(actId).getTitle(themeDisplay.getLocale()) %></h2>
			<p><%=LearningActivityLocalServiceUtil.getLearningActivity(actId).getDescription(themeDisplay.getLocale()) %></p>
		
			<portlet:renderURL var="backToQuestionsURL">
				<portlet:param name="jspPage" value="/html/surveyactivity/view.jsp"></portlet:param>
			</portlet:renderURL>
		
			<%
			java.util.List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
			for(TestQuestion question:questions)
			{
			%>

			<div class="question">
				<h3><%=question.getText() %></h3>
				<span class="total">Total de respuestas: <%=SurveyResultLocalServiceUtil.getTotalAnswersByQuestionId(question.getQuestionId()) %></span>
			</div>
			<ul class="answer">
				<%
				List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
				for(TestAnswer answer:testAnswers)
				{
					String texto = answer.getAnswer().length() > 50 ? answer.getAnswer().substring(50)+"..." : answer.getAnswer();
					DecimalFormat df = new DecimalFormat("###.##");
					String percent = df.format(SurveyResultLocalServiceUtil.getPercentageByQuestionIdAndAnswerId(question.getQuestionId(), answer.getAnswerId()));
				%>
					<li>
						<%=texto %>
						<span  class="porcentaje">Porcentaje: <%=percent %></span>
					</li>
				<%
				}
				%>
			</ul>
			<%
			} 
			%>
				
			<a href="<%=backToQuestionsURL.toString() %>">Volver</a>
		</div>
		
	<%	
	} 
	%>
	
