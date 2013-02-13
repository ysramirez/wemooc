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

<%@ include file="/init.jsp" %>

<%
if(ParamUtil.getLong(request,"actId",0 )==0)
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
	long latId=ParamUtil.getLong(request,"latId" );
	long actId=ParamUtil.getLong(request,"actId" );
	LearningActivityTry larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);
	LearningActivity learningActivity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	
	request.setAttribute("actId",actId);
	request.setAttribute("learningActivity",learningActivity);
	request.setAttribute("larntry",larntry);
	
	Long tries = learningActivity.getTries();
	Long userTries = Long.valueOf(LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId,themeDisplay.getUserId()));
	
	LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
	Object  [] arguments =  new Object[]{result.getResult()};
	%>
	
	<jsp:include page="/html/shared/popResult.jsp" />
	
	<h2><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
	<h3><liferay-ui:message key="test-done" /></h3>
	<h4><liferay-ui:message key="your-result" arguments="<%=arguments %>" /></h4>
	
	<%
	if(LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
	{
	%>
		<h4><liferay-ui:message key="your-result-pass" /></h4>
	<%
	}else{
		Object  [] arg =  new Object[]{learningActivity.getPasspuntuation()};
	%>	
		<h4><liferay-ui:message key="your-result-dont-pass"  arguments="<%=arg %>" /></h4>
	<%
		if(tries>0 && userTries >= tries )
		{
		%><h4><liferay-ui:message key="your-result-no-more-tries" /></h4><%
		}
	} %>
	
	<h5><liferay-ui:message key="your-answers" /></h5>
	
	<%
	///A partir del XML
	java.util.Hashtable<TestQuestion, TestAnswer> resultados=(java.util.Hashtable<TestQuestion, TestAnswer>)request.getAttribute("resultados");
	List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
	
	for(TestQuestion question:questions)
	{
		String feedback=LanguageUtil.get(pageContext,"answer-in-blank");
		TestAnswer answerSelected=null;
		String cssclass="question incorrect";
		if(resultados.containsKey(question))
		{
		answerSelected=resultados.get(question);
	 	feedback=answerSelected.getFeedbacknocorrect();
		cssclass="question incorrect";
		if(answerSelected.isIsCorrect())
		{
		 cssclass="question correct";
		 feedback=answerSelected.getFeedbackCorrect();
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
			if(answerSelected!=null && answer.getAnswerId()==answerSelected.getAnswerId())
				{
				checked="checked='checked'";
				}
			%>
			<div class="answer">
				<input type="radio" name="question_<%=question.getQuestionId()%>" <%=checked %> value="<%=answer.getAnswerId() %>"  disabled="disabled"><%=answer.getAnswer() %>
			</div>
			<%
		}
		%>
			</div>
			<div class="questionFeedback"><%=feedback%></div>
		</div>	
		<%
	}
	

	if(!LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId())
		&& (tries==0 ||userTries < tries) )
	{
		if(tries>0)
		{	
		%>
		<liferay-portlet:renderURL var="realizar">
			<liferay-portlet:param name="actId" value="<%=Long.toString(actId) %>"></liferay-portlet:param>
			<liferay-portlet:param name="jspPage" value="/html/execactivity/test/preview.jsp" />
		</liferay-portlet:renderURL>
		<%String enlace="self.location='"+realizar.toString()+"'"; %>
		<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again\")%>" onClick="<%=enlace %>"></aui:button>
		<%
		}
		else
		{	
		%>
		<liferay-portlet:renderURL var="realizardir">
			<liferay-portlet:param name="actId" value="<%=Long.toString(actId) %>"></liferay-portlet:param>
			<liferay-portlet:param name="jspPage" value="/html/execactivity/test/view.jsp" />
		</liferay-portlet:renderURL>
		<%String enlace="self.location='"+realizardir.toString()+"'"; %>
		<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again\")%>" onClick="<%=enlace %>"></aui:button>
		<%
		}
	}
	
}

%>


