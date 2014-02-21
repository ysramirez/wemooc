<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.JavaConstants"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ include file="/init.jsp" %>

<span class="question">
	<aui:input type="radio" id="includeSolution" name="includeSolution" label="includeSolution.yes" value="y" onClick="showHideSolution();" checked="true"/>
	<aui:input type="radio" id="notIncludeSolution" name="includeSolution" label="includeSolution.no" value="n" onClick="showHideSolution();"/>
</span>

<% 
	long questionId = ParamUtil.getLong(request,"questionId", 0);
	TestQuestion question = null;
	if(questionId != 0){
		question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
	}
	int totalAnswer=(question!=null)?(int)TestAnswerLocalServiceUtil.dynamicQueryCount( DynamicQueryFactoryUtil.forClass(TestAnswer.class).
															add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId()))):0;
%>

<script type="text/javascript">
function showHideSolution(){
	var A = AUI();
	if(A.one('input[id=<portlet:namespace />includeSolution]').attr('checked')) {
		//<portlet:namespace />addNode();
		A.one('.solution').removeClass('aui-helper-hidden');
		A.one('.noSolution').addClass('aui-helper-hidden');
	}else if(A.one('input[id=<portlet:namespace />notIncludeSolution]').attr('checked')) {
		A.one('.solution').addClass('aui-helper-hidden');
		A.one('.noSolution').removeClass('aui-helper-hidden');
	}
}

AUI().ready('aui-base',
   	function() {
		<portlet:namespace />initialize();
   	}
);

function <portlet:namespace />initialize(){
	var A = AUI();
	var totalAnswer = <%=totalAnswer%>;
	console.log(totalAnswer);
	if(totalAnswer>0){ 
		A.one('input[id=<portlet:namespace />includeSolution]').attr('checked','true');
		A.one('input[id=<portlet:namespace />notIncludeSolution]').attr('checked','false');
		A.one('.solution').removeClass('aui-helper-hidden');
		A.one('.noSolution').addClass('aui-helper-hidden');
	}else{
		A.one('input[id=<portlet:namespace />includeSolution]').attr('checked','false');
		A.one('input[id=<portlet:namespace />notIncludeSolution]').attr('checked','true');
		A.one('.solution').addClass('aui-helper-hidden');
		A.one('.noSolution').removeClass('aui-helper-hidden');
	}
}
</script>

<span class="noSolution aui-helper-hidden">
<%	if(question!=null){ %>
		<aui:input type="hidden" name="questionId" value="<%=question.getQuestionId() %>"></aui:input>
<%	} %>
</span>

<span class="solution aui-helper-hidden">
	<liferay-ui:success key="answer-added-successfully" message="answer-added-successfully" />
	<%
	if(totalAnswer>0){
		List<TestAnswer> testAnswers = TestAnswerLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(TestAnswer.class).
				add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())));
		for(TestAnswer testanswer:testAnswers){
	%>
		<div id="testAnswer_<%=testanswer.getAnswerId() %>">
			<div id="<portlet:namespace />answerError_<%=testanswer.getAnswerId() %>" class="aui-helper-hidden portlet-msg-error">
				<liferay-ui:message key="answer-test-required"/>
			</div>
			<liferay-ui:panel id="<%=\"testAnswer_\"+testanswer.getAnswerId() %>" title="answer" collapsible="true" extended="true" defaultState="open" persistState="false">
				<div class="leftSideAnswer">
					<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
					<aui:input  type="hidden" name="iterator" value="1"></aui:input>
					<aui:field-wrapper label="">
						<div class="container-textarea">
							<%String name="answer_"+testanswer.getAnswerId(); %>
							<textarea rows="10" cols="88" name="<%=name%>"><%=testanswer.getAnswer()%></textarea>
						</div>
					</aui:field-wrapper>
					<aui:input  name="feedbackCorrect_<%=testanswer.getAnswerId() %>" label="feedbackCorrect" value="<%=testanswer.getFeedbackCorrect() %>">
						<aui:validator name="maxLength">300</aui:validator>
					</aui:input>
					<aui:input  name="feedbackNoCorrect_<%=testanswer.getAnswerId() %>" label="feedbackNoCorrect" value="<%=testanswer.getFeedbacknocorrect() %>">
						<aui:validator name="maxLength">300</aui:validator>
					</aui:input>
					<aui:input  type="hidden" name="correct_<%=testanswer.getAnswerId() %>" label="correct" value="true"/>
				</div>
				<div class="rightSideAnswer">
					<span class="newitem2"><a href="#" class="newitem2" onclick="<portlet:namespace />deleteNode('testAnswer_<%=testanswer.getAnswerId() %>');"><liferay-ui:message key="delete"/></a></span>
				</div>
			</liferay-ui:panel>
		</div>
	<%
		}
	}
	%>
</span>
