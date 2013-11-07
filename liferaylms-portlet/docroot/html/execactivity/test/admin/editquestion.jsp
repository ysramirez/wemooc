<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionType"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
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
//<!--

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
});

//-->
</script>

<portlet:actionURL var="editquestionURL" name="editquestion" />
<aui:form name="qfm" action="<%=editquestionURL %>" method="post">

	<%
		String questionTypeName = "";
		long typeId = question.getQuestionType();
		List<QuestionType> qtypes = new QuestionTypeRegistry().getQuestionTypes(); 
		for(QuestionType qt:qtypes){
			if(typeId == qt.getTypeId()){
				questionTypeName = qt.getTitle(themeDisplay.getLocale());
			}
		}
	%>
	<aui:input type="hidden" id="typeId" name="typeId" value="<%=typeId%>" />
	<aui:input type="text" id="typeIdName" name="typeIdName" disabled="<%=true %>" value="<%=questionTypeName %>" />
	<aui:input name="resId" type="hidden" value="<%=question.getActId() %>"></aui:input>
	<aui:input name="questionId" type="hidden" value="<%=question.getQuestionId() %>"></aui:input>
	
	<script type="text/javascript">
	//<!--
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
    
	<aui:field-wrapper label="execativity.editquestions.editquestion.enunciation">
		<liferay-ui:input-editor name="text" width="80%" onChangeMethod="onChangeText" />
		<script type="text/javascript">
	        function <portlet:namespace />initEditor() { 
	            return "<%=JavaScriptUtil.markupToStringLiteral(question.getText())%>";
	        }
	    </script>
	</aui:field-wrapper>
	
	<div id="<portlet:namespace />textError" class="<%=(SessionErrors.contains(renderRequest, "execativity.editquestions.editquestion.error.test.required"))?
   														"portlet-msg-error":StringPool.BLANK %>">
   	<%=(SessionErrors.contains(renderRequest, "execativity.editquestions.editquestion.error.test.required"))?
   			LanguageUtil.get(pageContext,"execativity.editquestions.editquestion.error.test.required"):StringPool.BLANK %>
	</div>

	<aui:button-row>
		<aui:button type="submit" />
		<liferay-util:include page="/html/execactivity/test/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</aui:button-row>
</aui:form>