<%@page import="java.util.HashMap"%>
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
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>

<%@ include file="/init.jsp" %>

<%
if(ParamUtil.getLong(request,"actId",0 )==0)
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{


	LearningActivity learningActivity=(LearningActivity)request.getAttribute("learningActivity");
	if(learningActivity==null) {
		learningActivity=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId" ));	
	}
	request.setAttribute("actId",learningActivity.getActId());
	request.setAttribute("learningActivity",learningActivity);
	
	LearningActivityTry larntry=(LearningActivityTry)request.getAttribute("larntry");
	if(larntry==null) {
		larntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(ParamUtil.getLong(request,"latId" ));
	}
	request.setAttribute("larntry",larntry);
	
	Long tries = learningActivity.getTries();
	Long userTries = Long.valueOf(LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(learningActivity.getActId(),themeDisplay.getUserId()));
	
	LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), themeDisplay.getUserId());
	
	///A partir del XML
	boolean userPassed=false;
	long oldResult=-1;
	Map<Long, TestAnswer> resultados=(Map<Long, TestAnswer>)request.getAttribute("resultados");
	if(resultados==null) {
		resultados=new HashMap<Long, TestAnswer>();
		Iterator<Element> nodeItr = SAXReaderUtil.read(larntry.getTryResultData()).getRootElement().elementIterator();
		while(nodeItr.hasNext()) {
			Element element = nodeItr.next();
	         if("question".equals(element.getName())) {
	        	 Iterator<Element> elementItr = element.elementIterator();
	        	 while(elementItr.hasNext()) {
	        		 Element elementElement = elementItr.next();
	        		 if("answer".equals(elementElement.getName())) {
	        			 resultados.put(Long.valueOf(element.attributeValue("id")),
	        			 	TestAnswerLocalServiceUtil.getTestAnswer(Long.valueOf(elementElement.attributeValue("id"))));
	        		 }
	        	 }
	         }

	    }	
				
		userPassed=LearningActivityResultLocalServiceUtil.userPassed(learningActivity.getActId(),themeDisplay.getUserId());
	}
	else {
		userPassed=learningActivity.getPasspuntuation()<=larntry.getResult();
		oldResult=(Long)request.getAttribute("oldResult");
	%>
	<% 
	//Cuando estamos mejorando la nota no mostramos el popup.
	if(oldResult <= 0){
	%>
	<jsp:include page="/html/shared/popResult.jsp" />
	<%}%>
	

	<h2><%=learningActivity.getTitle(themeDisplay.getLocale()) %></h2>
	<% } %>
	<p><liferay-ui:message key="test-done" /></p>
	<liferay-util:include page="/html/execactivity/test/timeout.jsp" servletContext="<%=this.getServletContext() %>">
		<liferay-util:param value="<%=Long.toString(learningActivity.getActId()) %>" name="actId"/>
	</liferay-util:include> 
	<p><liferay-ui:message key="your-result" arguments="<%=new Object[]{larntry.getResult()} %>" /></p>
	
	<% 
	if(oldResult>0){
		if(oldResult<larntry.getResult()){
	%>
		<p><liferay-ui:message key="execActivity.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
	<%		
		}
		else {
	%>
		<p><liferay-ui:message key="execActivity.not.improve.result" arguments="<%=new Object[]{oldResult} %>" /></p>
	<%			
		}
	}	
	%>
	
	<%
	if(userPassed)
	{
	%>
		<p class="color_tercero negrita"><liferay-ui:message key="your-result-pass" /></p>
	<%
	}else{
	%>	
		<p class="color_tercero negrita"><liferay-ui:message key="your-result-dont-pass"  arguments="<%=new Object[]{learningActivity.getPasspuntuation()} %>" /></p>
	<% }
	if(tries>0 && userTries >= tries )
	{
	%><p class="color_tercero"><liferay-ui:message key="your-result-no-more-tries" /></p><%
	}
	%>
	
	<p class="negrita"><liferay-ui:message key="your-answers" /></p>
	
	<%

	
	List<TestQuestion> questions=null;
	
	if (GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"random"))==0){
		questions=TestQuestionLocalServiceUtil.getQuestions(learningActivity.getActId());
	}
	else{
		questions= new ArrayList<TestQuestion>();
		Iterator<Element> nodeItr = SAXReaderUtil.read(larntry.getTryResultData()).getRootElement().elementIterator();
		while(nodeItr.hasNext()) {
			Element element = nodeItr.next();
	         if("question".equals(element.getName())) {

					questions.add(TestQuestionLocalServiceUtil.getTestQuestion(Long.valueOf(element.attributeValue("id"))));

	         }

	    }	
		
	}
	
	for(TestQuestion question:questions)
	{
		String feedback=LanguageUtil.get(pageContext,"answer-in-blank");
		TestAnswer answerSelected=null;
		String cssclass="question incorrect";
		if(resultados.containsKey(question.getQuestionId()))
		{
		answerSelected=resultados.get(question.getQuestionId());
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
			
			String correct="";
			String showCorrectAnswer = LearningActivityLocalServiceUtil.getExtraContentValue(ParamUtil.getLong(request,"actId"), "showCorrectAnswer");
			if(showCorrectAnswer.equals("true")){
				if(answer.isIsCorrect())
				{
					correct="font_14 color_cuarto negrita";
				}
			}
			%>
			<div class="answer <%=correct%>">
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
	
	if(tries==0 || userTries < tries ||permissionChecker.hasPermission(
			learningActivity.getGroupId(),LearningActivity.class.getName(),learningActivity.getActId(), ActionKeys.UPDATE)) {
		if(!LearningActivityResultLocalServiceUtil.userPassed(learningActivity.getActId(),themeDisplay.getUserId()))
		{
			if(tries>0)
			{	
			%>
			<liferay-portlet:renderURL var="realizar">
				<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
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
				<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
				<liferay-portlet:param name="jspPage" value="/html/execactivity/test/view.jsp" />
			</liferay-portlet:renderURL>
			<%String enlace="self.location='"+realizardir.toString()+"'"; %>
			<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again\")%>" onClick="<%=enlace %>"></aui:button>
			<%
			}
		}
		else
		{
			
			if(result.getResult()<100)
			{
				String improveStr = LearningActivityLocalServiceUtil.getExtraContentValue(ParamUtil.getLong(request,"actId"), "improve");
				
				if(improveStr.equals("true")){
				
					if(tries>0)
					{	
					%>
						<p class="negrita"><liferay-ui:message key="execativity.test.try.count" arguments="<%=new Object[]{userTries,tries} %>" /></p>
						<p class="color_tercero textcenter negrita"><liferay-ui:message key="execativity.test.try.confirmation.again" /></p>
					<%
					}
					%>
					<liferay-portlet:renderURL var="mejorardir">
						<liferay-portlet:param name="actId" value="<%=Long.toString(learningActivity.getActId()) %>"></liferay-portlet:param>
						<liferay-portlet:param name="improve" value="true"></liferay-portlet:param>
						<liferay-portlet:param name="jspPage" value="/html/execactivity/test/preview.jsp" />
					</liferay-portlet:renderURL>
					<%String enlace="self.location='"+mejorardir.toString()+"'"; %>
					<aui:button name="repetir" value="<%=LanguageUtil.get(pageContext,\"execativity.test.try.again.improve\")%>" onClick="<%=enlace %>"></aui:button>
					<%
				
				}
			}
		}
	}
	
}

%>


