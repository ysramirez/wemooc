
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>
<div class="container-activity">
<%
	long actId = ParamUtil.getLong(request,"actId",0);
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	if(actId==0)
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		
		LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		long typeId=activity.getTypeId();
		boolean isSurvey = activity.getTypeId() == 4;
		
		
		if(typeId==4&&(!LearningActivityLocalServiceUtil.islocked(actId,themeDisplay.getUserId())||
				permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE)||
				permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")))
		{

	
		%>
          
			<div class="surveyactivity view">
				<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
				<div class="description"><%=activity.getDescription(themeDisplay.getLocale()) %></div>
				
				<%if(permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(), activity.getActId(), ActionKeys.UPDATE)){ %>
					<portlet:renderURL var="stadisticsURL">
						<portlet:param name="jspPage" value="/html/surveyactivity/stadistics.jsp"></portlet:param>
						<portlet:param name="actId" value="<%=String.valueOf(actId) %>" />
					</portlet:renderURL>
					<liferay-ui:icon image="view" message="surveyactivity.stadistics" label="true" url="<%=stadisticsURL.toString() %>" />
				<%
				}
				
				
					if(LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
					{
					%>
						<h2 class="description-title"><liferay-ui:message key="surveyactivity.survey.done" /></h2>
					<%
					}
					else
					{
						ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
						
						LearningActivityTry learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
						List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
						
					%>
						<portlet:actionURL name="correct" var="correctURL">
							<portlet:param name="actId" value="<%=Long.toString(actId) %>"></portlet:param>
							<portlet:param name="latId" value="<%=Long.toString(learningTry.getLatId()) %>"></portlet:param>
						</portlet:actionURL>
																	
						<script type="text/javascript">
						<!--
							function <%= renderResponse.getNamespace() %>formValidation(e){
								var returnValue=true;
								
								AUI().use('node', function(Y){
								    var questions = Y.all('div.question')
			
								    for(var i=0;i<questions.size();i++){
								        if(questions.item(i).one('div.answer input[type="radio"]:checked')==null){
								        	if(!confirm('<liferay-ui:message key="execativity.test.questions.without.response" />')) {
			
												if (e.target) targ = e.target.blur();
												else if (e.srcElement) targ = e.srcElement.blur();
										        
										        returnValue=false;  
								        	}
								        	 i=questions.size();
									    }
									}
								    
								});
			
								return returnValue; 
												    
							}
						//-->
						</script>
						
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
						
						if(questions.size() > 0)
						{
						%>
						<aui:button-row>
						<aui:button type="submit"  onClick='<%= "return  "+renderResponse.getNamespace() + "formValidation(event);" %>' ></aui:button>
						</aui:button-row>
						<%}%>
						</aui:form>
						<%
						}
					%></div> <%
					}	
				}
			%>


</div>