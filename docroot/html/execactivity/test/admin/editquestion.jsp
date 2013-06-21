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
<%
	TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(question.getActId());
	request.setAttribute("activity", learningActivity);
	PortletURL backUrl = renderResponse.createRenderURL();
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));
	backUrl.setParameter("jspPage", "/html/execactivity/test/admin/editquestions.jsp");
	backUrl.setParameter("actionEditingDetails",StringPool.TRUE);
	request.setAttribute("backUrl", backUrl.toString());
%>
<liferay-util:include page="/html/execactivity/test/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<script type="text/javascript">
<!--

AUI().ready('node-base' ,'aui-form-validator', 'aui-overlay-context-panel', function(A) {

	window.<portlet:namespace />validateQuestion = new A.FormValidator({
		boundingBox: '#<portlet:namespace />qfm',
		validateOnBlur: true,
		validateOnInput: true,
		selectText: true,
		showMessages: false,
		containerErrorClass: '',
		errorClass: '',
		rules: {
            <portlet:namespace />text: {
	    		required: true
	        }
		},
        fieldStrings: {
            <portlet:namespace />text: {
	    		required: '<liferay-ui:message key="execativity.editquestions.editquestion.error.text.required" />'
	        }
	    },
		on: {		
            errorField: function(event) {
            	var instance = this;
				var field = event.validator.field;
				var divError = A.one('#'+field.get('name')+'Error');
				if(divError) {
					divError.addClass('portlet-msg-error');
					divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
				}
            },		
            validField: function(event) {
				var divError = A.one('#'+event.validator.field.get('name')+'Error');
				if(divError) {
					divError.removeClass('portlet-msg-error');
					divError.setContent('');
				}
            }
		}
	});

	window.<portlet:namespace />validateAnswer = new A.FormValidator({
		boundingBox: '#<portlet:namespace />afm',
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
				var divError = A.one('#'+field.get('name')+'Error');
				if(divError) {
					divError.addClass('portlet-msg-error');
					divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
				}
            },		
            validField: function(event) {
				var divError = A.one('#'+event.validator.field.get('name')+'Error');
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

<portlet:actionURL var="editquestionURL" name="editquestion" />
<aui:form name="qfm" action="<%=editquestionURL %>" method="post">
	<aui:input name="resId" type="hidden" value="<%=question.getActId() %>"></aui:input>
	<aui:input name="questionId" type="hidden" value="<%=question.getQuestionId() %>"></aui:input>
	
	<script type="text/javascript">
	<!--
		Liferay.provide(
	        window,
	        '<portlet:namespace />onChangeText',
	        function(val) {
	        	var A = AUI();
				A.one('#<portlet:namespace />text').set('value',val);
				if(window.<portlet:namespace />validateQuestion){
					window.<portlet:namespace />validateQuestion.validateField('<portlet:namespace />text');
				}
	        },
	        ['node']
	    );
	    
	//-->
	</script>
    
	<aui:field-wrapper label="enunciation">
		<liferay-ui:input-editor name="text" width="80%" onChangeMethod="onChangeText" />
		<script type="text/javascript">
	        function <portlet:namespace />initEditor() 
	        { 
	            return "<%=JavaScriptUtil.markupToStringLiteral(question.getText())%>";
	        }
	    </script>
	</aui:field-wrapper>
	
	<div id="<portlet:namespace />textError" class="<%=(SessionErrors.contains(renderRequest, "execativity.editquestions.editquestion.error.test.required"))?
   														"portlet-msg-error":StringPool.BLANK %>">
   	<%=(SessionErrors.contains(renderRequest, "execativity.editquestions.editquestion.error.test.required"))?
   			LanguageUtil.get(pageContext,"execativity.editquestions.editquestion.error.test.required"):StringPool.BLANK %>
	</div>
	
	<aui:select name="typeId" label="qtype">
		<aui:option value="0" label="options"></aui:option>
	</aui:select>
	<aui:button-row>
		<aui:button type="submit" />
		<liferay-util:include page="/html/execactivity/test/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</aui:button-row>
</aui:form>

<h2><liferay-ui:message key="answers"></liferay-ui:message></h2>
<portlet:actionURL var="addanswerURL" name="addanswer" />
<h3><liferay-ui:message key="add-answer"></liferay-ui:message></h3>
<aui:form name="afm" action="<%=addanswerURL%>" method="post">
	<aui:input type="textarea" rows="4" name="answer" label="answer"></aui:input>
	<div id="<portlet:namespace />answerError" class="<%=(SessionErrors.contains(renderRequest, "answer-test-required"))?
	   														"portlet-msg-error":StringPool.BLANK %>">
	   	<%=(SessionErrors.contains(renderRequest, "answer-test-required"))?
	   			LanguageUtil.get(pageContext,"answer-test-required"):StringPool.BLANK %>
	</div>
	<aui:input type="hidden" name="questionId" value="<%=question.getQuestionId() %>"></aui:input>
	<aui:input size="120" name="feedbackCorrect" label="feedback"></aui:input>
	<br/>
	<aui:column>
		<aui:input  type="checkbox" name="correct" label="correct"></aui:input>
	</aui:column>
	<aui:column>
		<aui:button type="submit" value="add"></aui:button>
	</aui:column>
</aui:form>
<br />

<%
int totalAnswer=(int)TestAnswerLocalServiceUtil.dynamicQueryCount( DynamicQueryFactoryUtil.forClass(TestAnswer.class).
														add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())));


	if(totalAnswer>0)
	{
	%>
<liferay-ui:search-container emptyResultsMessage="" delta="10" >
	<liferay-ui:search-container-results>
	<%
		pageContext.setAttribute("results", 
				TestAnswerLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(TestAnswer.class).
							add(PropertyFactoryUtil.forName("questionId").eq(question.getQuestionId())),
						searchContainer.getStart(),
						searchContainer.getEnd()));
		pageContext.setAttribute("total", totalAnswer);
		
	%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row
		className="com.liferay.lms.model.TestAnswer"
		keyProperty="answerId"
		modelVar="testanswer">

		<liferay-ui:search-container-column-text>
		<script type="text/javascript">
		<!--
		
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
			<portlet:actionURL var="editanswerURL" name="editanswer" />
			<aui:form name="<%=\"afm_\"+testanswer.getAnswerId() %>" action="<%=editanswerURL %>" method="post">
			
				<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
			
				<aui:input type="textarea"  rows="4" name="answer" value="<%=testanswer.getAnswer() %>"></aui:input>
				<div id="<portlet:namespace />answerError_<%=Long.toString(testanswer.getAnswerId()) %>" class="<%=(SessionErrors.contains(renderRequest, "answer-test-required_"+testanswer.getAnswerId()))?
				   														"portlet-msg-error":StringPool.BLANK %>">
				   	<%=(SessionErrors.contains(renderRequest, "answer-test-required_"+testanswer.getAnswerId()))?
				   			LanguageUtil.get(pageContext,"answer-test-required"):StringPool.BLANK %>
				</div>

				<aui:input  name="feedbackCorrect" label="feedback" value="<%=testanswer.getFeedbackCorrect() %>"></aui:input>
				<br />
				<aui:column>
					<aui:input type="checkbox" name="correct" checked="<%=testanswer.getIsCorrect() %>"></aui:input>
				</aui:column>
				<aui:column>
					<aui:button type="submit" value="modify"></aui:button>
				</aui:column>
			</aui:form>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			path="/html/execactivity/test/admin/admin_answer_actions.jsp"
			align="right"
			/>

	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />

</liferay-ui:search-container>
<%
}
%>