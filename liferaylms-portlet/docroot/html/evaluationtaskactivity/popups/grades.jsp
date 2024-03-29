<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>
<script type="text/javascript">
<!--

function <portlet:namespace />doClosePopupGrades()
{
    AUI().use('aui-dialog', function(A) {
    	A.DialogManager.closeByChild('#<portlet:namespace />showPopupGrades');
    });
}

function <portlet:namespace />doSaveGrades(){
	AUI().use('node-base','aui-io-request', function(A) {
		if(!window.<portlet:namespace />validator.hasErrors()){
			A.io.request(
					'<portlet:actionURL name="setGrade" />',
					{
						data: {userId:'<%=renderRequest.getParameter("userId") %>',
							   result:A.one('#<portlet:namespace />result').get('value'),
							   comments:A.one('#<portlet:namespace />comments').get('value')},
						dataType: 'json',
						on: {
							failure: function(event, id, obj) {
								var portlet = A.one('#p_p_id<portlet:namespace />');
								portlet.hide();
								portlet.placeAfter('<div class="portlet-msg-error"><liferay-ui:message key="there-was-an-unexpected-error.-please-refresh-the-current-page"/></div>');
							},
							success: function(event, id, obj) {
								var responseData = this.get('responseData');
								var divResult = A.one('#<portlet:namespace />evaluationResult');
								if(responseData.responseCode==1){
									divResult.removeClass('portlet-msg-error');
									divResult.addClass('portlet-msg-success');
								}
								else{
									divResult.addClass('portlet-msg-error');
									divResult.removeClass('portlet-msg-success');
								}
								divResult.setContent('');
								
								var messageList = A.Node.create('<ul></ul>');
								if(responseData.messages.length==1){
									divResult.setContent(responseData.messages[0]);
								}
								else {
									for (var i = 0; i < responseData.messages.length; i++) {
										var messageItem = A.Node.create('<li></li>');  
										messageItem.setContent(responseData.messages[i]);   
										messageList.appendChild(messageItem);
									}
									divResult.appendChild(messageList);
								}

							}
						}
					}
				);
			
		}
	});	
}

function <portlet:namespace />createValidator(){

	AUI().use('node-base' ,'aui-form-validator', function(A) {
		A.one('#<portlet:namespace />result').on('input',function(event){

			if(event.target.get('value')=='') {
				var divError = A.one('#<portlet:namespace />resultError');
				divError.removeClass('portlet-msg-error');
				divError.setContent('');
			}
			var divResult = A.one('#<portlet:namespace />evaluationResult');
			divResult.removeClass('portlet-msg-error');
			divResult.removeClass('portlet-msg-success');
			divResult.setContent('');
		});
			
		window.<portlet:namespace />validator = new A.FormValidator({
			boundingBox: '#<portlet:namespace />fn_grades',
			validateOnBlur: true,
			validateOnInput: true,
			selectText: true,
			showMessages: false,
			containerErrorClass: '',
			errorClass: '',
			rules: {			
				<portlet:namespace />result: {
					required: true,
					number: true,
					range: [0,100]
				}
			},
	
	        fieldStrings: {
	        	<portlet:namespace />result: {
	        		required: '<liferay-ui:message key="evaluationtaskactivity.result.required" />',
	        		number: '<liferay-ui:message key="evaluationtaskactivity.result.number" />',
	        		range: '<liferay-ui:message key="evaluationtaskactivity.result.range" />'     
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

}

AUI().ready('node-base',function(A) {

	<portlet:namespace />createValidator();	
	var divResult = A.one('#<portlet:namespace />evaluationResult');
	divResult.removeClass('portlet-msg-error');
	divResult.removeClass('portlet-msg-success');
	divResult.setContent('');

});

//-->
</script>

<%
long actId = ParamUtil.getLong(request,"actId",0);
long userId = ParamUtil.getLong(renderRequest, "userId");
LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);


String resultHelpMessage=LanguageUtil.format(pageContext, "evaluationtaskactivity.grades.resultMessage", new Object[]{result.getResult()});

%>

<aui:form  name="fn_grades" method="post" >
	<aui:fieldset>
		<h1><%=UserLocalServiceUtil.getUser(userId).getFullName() %></h1>
		<aui:input type="hidden" name="userId" value='<%=userId %>' />
	    <aui:input type="text" name="result" size="3" helpMessage="<%=resultHelpMessage %>" label="evaluationtaskactivity.grades" value='<%=result.getResult() %>' />
				<div id="<portlet:namespace />resultError" class="<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.result.bad-format"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    														<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.result.bad-format"))?
	    															LanguageUtil.get(pageContext,"evaluationtaskactivity.result.bad-format"):StringPool.BLANK %>
	    		</div>
	    <liferay-ui:message key="evaluationtaskactivity.result.percent"  arguments="<%=new Object[]{learningActivity.getPasspuntuation()} %>" />

		<aui:input type="textarea"  helpMessage="<%=LanguageUtil.get(pageContext, \"evaluationtaskactivity.grades.commentsMessage\")%>"  maxLength="350" cols="70"  rows="3" name="comments" label="evaluationtaskactivity.comments" value='<%=(result.getComments()!=null)?result.getComments():"" %>'>
			<aui:validator name="range">[0, 350]</aui:validator>
		</aui:input>
		<liferay-ui:message key="evaluationtaskactivity.comments.maxLength" />
	</aui:fieldset>
</aui:form>
<aui:button-row>
	<button name="Save" value="save" onclick="<portlet:namespace />doSaveGrades();" type="button">
		<liferay-ui:message key="evaluationtaskactivity.save" />
	</button>

	<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button">
		<liferay-ui:message key="evaluationtaskactivity.cancel" />
	</button>
</aui:button-row>
<div id="<portlet:namespace />evaluationResult" class="<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.grades.bad-updating"))?
									   "portlet-msg-error":((SessionMessages.contains(renderRequest, "evaluationtaskactivity.grades.updating"))?
									   "portlet-msg-success":StringPool.BLANK) %>">
	<%=(SessionErrors.contains(renderRequest, "evaluationtaskactivity.grades.bad-updating"))?LanguageUtil.get(pageContext,"evaluationtaskactivity.grades.bad-updating"):
	    ((SessionMessages.contains(renderRequest, "evaluationtaskactivity.grades.updating"))?LanguageUtil.get(pageContext,"evaluationtaskactivity.grades.updating"):StringPool.BLANK) %>
</div>
