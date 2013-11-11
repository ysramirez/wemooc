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

<%@ include file="/html/execactivity/test/admin/editquestion.jsp" %>

<div class="question">
	<INPUT TYPE=RADIO id="includeSolution" NAME="includeSolution" VALUE="y" onclick="showHideSolution()" checked><%=LanguageUtil.get(pageContext,"includeSolution.yes")%><br/>
	<INPUT TYPE=RADIO id="notIncludeSolution" NAME="includeSolution" VALUE="n" onclick="showHideSolution()"><%=LanguageUtil.get(pageContext,"includeSolution.no") %>
</div>

<%
	String advise = ParamUtil.getString(request, "advise", "");
	int totalAnswer=(int)TestAnswerLocalServiceUtil.dynamicQueryCount( DynamicQueryFactoryUtil.forClass(TestAnswer.class).
															add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())));
%>

<script type="text/javascript">
function showHideSolution(){
	if(document.getElementById('includeSolution').checked) {
	  document.getElementById('solution').style.display = 'block';
	  document.getElementById('noSolution').style.display = 'none';
	}else if(document.getElementById('notIncludeSolution').checked) {
		document.getElementById('solution').style.display = 'none';
		document.getElementById('noSolution').style.display = 'block';
	}
}

window.onload = function(){
		inicializar(<%=totalAnswer%>)
	};

function inicializar(totalAnswer){
	if(totalAnswer==0){ 
		document.getElementById('includeSolution').checked = false;
		document.getElementById('notIncludeSolution').checked = true;
		document.getElementById('solution').style.display = 'none';
		document.getElementById('noSolution').style.display = 'block';
	}else{
		document.getElementById('includeSolution').checked = true;
		document.getElementById('notIncludeSolution').checked = false;
		document.getElementById('solution').style.display = 'block';
		document.getElementById('noSolution').style.display = 'none';
	}
}
</script>

<div id="noSolution">
	<portlet:actionURL var="saveQuestionWithoutAnswerURL" name="saveQuestionWithoutAnswer" />
	<aui:form name="qwa" action="<%=saveQuestionWithoutAnswerURL %>" method="post">
		<aui:input type="hidden" name="questionId" value="<%=question.getQuestionId() %>"></aui:input>
		<aui:button type="submit"/>
	</aui:form>
</div>

<div id="solution">
	<liferay-ui:success key="answer-added-successfully" message="answer-added-successfully" />
	<%
	if(totalAnswer>0){
		List<TestAnswer> testAnswers = TestAnswerLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(TestAnswer.class).
				add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())));
		for(TestAnswer testanswer:testAnswers){
	%>
			<liferay-ui:panel id="<%=\"testAnswer_\"+testanswer.getAnswerId() %>" title="answer" collapsible="true" extended="true" defaultState="collapsed">
				<script type="text/javascript">
				//<!--
				AUI().ready('node-base' ,'aui-form-validator', 'aui-overlay-context-panel', function(A) {
				
					window.<portlet:namespace />validateAnswer<%=testanswer.getAnswerId() %> = new A.FormValidator({
						boundingBox: '#<portlet:namespace />afm_<%=testanswer.getAnswerId() %>',
						validateOnBlur: true,
						validateOnInput: true,
						selectText: true,
						showMessages: false,
						containerErrorClass: '',
						errorClass: '',
						rules: {
				            <portlet:namespace />answer: {
					    		required: true
					        }
						},
				        fieldStrings: {
				            <portlet:namespace />answer: {
					    		required: '<liferay-ui:message key="answer-test-required" />'
					        }
					    },
						on: {		
				            errorField: function(event) {
				            	var instance = this;
								var field = event.validator.field;
								var divError = A.one('#'+field.get('name')+'Error_<%=Long.toString(testanswer.getAnswerId()) %>');
								if(divError) {
									divError.addClass('portlet-msg-error');
									divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
								}
				            },		
				            validField: function(event) {
								var divError = A.one('#'+event.validator.field.get('name')+'Error_<%=Long.toString(testanswer.getAnswerId()) %>');
								if(divError) {
									divError.removeClass('portlet-msg-error');
									divError.setContent('');
								}
				            }
						}
					});
		
				});
				
				//-->
				</script>
				<% if(!"".equals(advise)){ 	%>
					<liferay-ui:message key="<%=advise %>"/>
				<% 	} %>
				<div id="leftSideAnswer">
					<portlet:actionURL var="editanswerURL" name="editanswer" />
					<aui:form name="<%=\"afm_\"+testanswer.getAnswerId() %>" action="<%=editanswerURL %>" method="post">
						<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
						<aui:field-wrapper label="">
							<div class="container-textarea">
								<%String name="answer_"+testanswer.getAnswerId(); %>
								<textarea rows="10" cols="88" name="<%=name%>"><%=testanswer.getAnswer()%></textarea>
							</div>
						</aui:field-wrapper>
						<div id="<portlet:namespace />answerError_<%=Long.toString(testanswer.getAnswerId()) %>" class="<%=(SessionErrors.contains(renderRequest, "answer-test-required_"+testanswer.getAnswerId()))?
						   														"portlet-msg-error":StringPool.BLANK %>">
						   	<%=(SessionErrors.contains(renderRequest, "answer-test-required_"+testanswer.getAnswerId()))?
						   			LanguageUtil.get(pageContext,"answer-test-required"):StringPool.BLANK %>
						</div>
						<aui:input  name="feedbackCorrect" label="feedbackCorrect" value="<%=testanswer.getFeedbackCorrect() %>"></aui:input>
						<aui:input  name="feedbackNoCorrect" label="feedbackNoCorrect" value="<%=testanswer.getFeedbacknocorrect() %>"></aui:input>
						<aui:button type="submit" value="modify"></aui:button>
					</aui:form>
				</div>
				<div id="rightSideAnswer">
					<liferay-ui:icon-menu align="right">
						<portlet:actionURL name="deleteanswer" var="deleteURL">
							<portlet:param name="answerId" value="<%=String.valueOf(testanswer.getPrimaryKey()) %>" />
							<portlet:param name="resId" value="<%=String.valueOf(testanswer.getActId()) %>" />
							<portlet:param name="questionId" value="<%= String.valueOf(testanswer.getQuestionId()) %>" />
							<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />	
						</portlet:actionURL>
						<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
					</liferay-ui:icon-menu>
				</div>
			</liferay-ui:panel>
	<%
		}
	}else{
		if(!"".equals(advise)){ 	%>
				<liferay-ui:message key="<%=advise %>"/>
			<% 	} %>
		<portlet:actionURL var="addanswerURL" name="addanswer" />
		<aui:form name="afm" action="<%=addanswerURL%>" method="post">
			
			<aui:field-wrapper label="answer">
				<div class="container-textarea">
					<textarea rows="10" cols="88" name="answer"></textarea>
				</div>
			</aui:field-wrapper>
			
			<div id="<portlet:namespace />answerError" class="<%=(SessionErrors.contains(renderRequest, "answer-test-required"))?"portlet-msg-error":StringPool.BLANK %>">
			   	<%=(SessionErrors.contains(renderRequest, "answer-test-required"))?LanguageUtil.get(pageContext,"answer-test-required"):StringPool.BLANK %>
			</div>
			<aui:input type="hidden" name="questionId" value="<%=question.getQuestionId() %>"></aui:input>
			<aui:input cssClass="input-comment" name="feedbackCorrect" label="feedbackCorrect"></aui:input>
			<aui:input cssClass="input-comment" name="feedbackNoCorrect" label="feedbackNoCorrect"></aui:input>
			<aui:input  type="hidden" name="correct" value="true"/>
			<aui:button type="submit" />
		</aui:form>
	<%
	}
	%>
</div>



