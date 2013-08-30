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

<%
	int totalAnswer=(int)TestAnswerLocalServiceUtil.dynamicQueryCount( DynamicQueryFactoryUtil.forClass(TestAnswer.class).
															add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())));
%>

	<liferay-ui:success key="answer-added-successfully" message="answer-added-successfully" />
	<%
	if(totalAnswer>0){
	%>
		<liferay-ui:search-container emptyResultsMessage="" delta="10" >
			<liferay-ui:search-container-results>
	<%
				pageContext.setAttribute("results", TestAnswerLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(TestAnswer.class).
								add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())), searchContainer.getStart(), searchContainer.getEnd()));
				pageContext.setAttribute("total", totalAnswer);
	%>
			</liferay-ui:search-container-results>
			<liferay-ui:search-container-row className="com.liferay.lms.model.TestAnswer" keyProperty="answerId" modelVar="testanswer">
				<liferay-ui:search-container-column-text>
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
					<liferay-ui:message key="fillblank.advise"/>
					<portlet:actionURL var="editanswerURL" name="editanswer" />
					<aui:form name="<%=\"afm_\"+testanswer.getAnswerId() %>" action="<%=editanswerURL %>" method="post">
						<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
						<aui:input type="textarea"  rows="20" cols="70" maxlength="4000" name="answer" value="<%=testanswer.getAnswer() %>"></aui:input>
						<div id="<portlet:namespace />answerError_<%=Long.toString(testanswer.getAnswerId()) %>" class="<%=(SessionErrors.contains(renderRequest, "answer-test-required_"+testanswer.getAnswerId()))?
						   														"portlet-msg-error":StringPool.BLANK %>">
						   	<%=(SessionErrors.contains(renderRequest, "answer-test-required_"+testanswer.getAnswerId()))?
						   			LanguageUtil.get(pageContext,"answer-test-required"):StringPool.BLANK %>
						</div>
						<aui:input  name="feedbackCorrect" label="feedbackCorrect" value="<%=testanswer.getFeedbackCorrect() %>"></aui:input>
						<aui:input  name="feedbackNoCorrect" label="feedbackNoCorrect" value="<%=testanswer.getFeedbacknocorrect() %>"></aui:input>
						<aui:button type="submit" value="modify"></aui:button>
					</aui:form>
				</liferay-ui:search-container-column-text>
				<liferay-ui:search-container-column-text align="right">
					<liferay-ui:icon-menu align="right">
						<portlet:actionURL name="deleteanswer" var="deleteURL">
							<portlet:param name="answerId" value="<%=String.valueOf(testanswer.getPrimaryKey()) %>" />
							<portlet:param name="resId" value="<%=String.valueOf(testanswer.getActId()) %>" />
							<portlet:param name="questionId" value="<%= String.valueOf(testanswer.getQuestionId()) %>" />
							<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />	
						</portlet:actionURL>
						<liferay-ui:icon-delete url="<%=deleteURL.toString() %>" />
					</liferay-ui:icon-menu>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>
			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	<%
	}else{
	%>
		<liferay-ui:message key="fillblank.advise"/>
		<portlet:actionURL var="addanswerURL" name="addanswer" />
		<aui:form name="afm" action="<%=addanswerURL%>" method="post">
			<aui:input type="textarea" rows="4" cols="60" maxlength="1000" name="answer" label="answer"></aui:input>
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
