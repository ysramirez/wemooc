<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.JavaConstants"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>

<%@ include file="/html/execactivity/test/admin/init.jsp" %>


<%
TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
%>
<portlet:actionURL var="editquestionURL" name="editquestion" />
<portlet:renderURL var="backToQuestionsURL">
	<portlet:param name="jspPage" value="/html/execactivity/test/admin/edit.jsp"></portlet:param>
	<portlet:param name="actId" value="<%=Long.toString(question.getActId()) %>" />
</portlet:renderURL>

<a href="<%=backToQuestionsURL.toString() %>"><%=LanguageUtil.get(pageContext,"back.to.questions")%></a>

<aui:form name="qfm" action="<%=editquestionURL %>" method="post">
	<aui:input name="actId" type="hidden" value="<%=question.getActId() %>"></aui:input>
	<aui:input name="questionId" type="hidden" value="<%=question.getQuestionId() %>"></aui:input>
	<aui:field-wrapper label="enunciation">
		<liferay-ui:input-editor name="DescripcionRichTxt"  initMethod="initEditor" width="80%" />
		<div id="<portlet:namespace/>DescripcionRichTxt"></div>
		<aui:input name="text" type="hidden" />
		<script type="text/javascript">
		    <!--
			    function <portlet:namespace />initEditor()
			    {
			    	return "<%=JavaScriptUtil.markupToStringLiteral(question.getText())%>";
			    }

			    function <portlet:namespace />extractCodeFromEditor()
			    {
					try {
						document.<portlet:namespace />qfm['<portlet:namespace />text'].value = window['<portlet:namespace />DescripcionRichTxt'].getHTML();
					}
					catch (e) {
					}
			    	
			    }
		        //-->
		    </script>	
	</aui:field-wrapper>
	<aui:select name="typeId" label="qtype">
		<aui:option value="0" label="options"></aui:option>
	</aui:select>
	<aui:button-row>
		<% 
			String extractCodeFromEditor = renderResponse.getNamespace() + "extractCodeFromEditor()";
		%>									
		<aui:button type="submit"  onClick="<%=extractCodeFromEditor%>">Submit</aui:button>
	</aui:button-row>
</aui:form>
<%
java.util.List<TestAnswer> testanswers=TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
String iconChecked = "checked";
String iconUnchecked = "unchecked";
%>
<h2><liferay-ui:message key="answers"></liferay-ui:message></h2>
<portlet:actionURL var="addanswerURL" name="addanswer" />
<h3><liferay-ui:message key="add-answer"></liferay-ui:message></h3>
<aui:form action="<%=addanswerURL%>" method="post">
	<aui:input type="textarea" cols="130" rows="4" name="answer" label="answer"></aui:input>
	<liferay-ui:error key="answer-test-required" message="answer-test-required" />
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

<%if(testanswers!=null&&testanswers.size()>0)
	{
	%>
<liferay-ui:search-container emptyResultsMessage="" delta="10" >
	<liferay-ui:search-container-results>
		<%
		results = ListUtil.subList(testanswers, searchContainer.getStart(),
		searchContainer.getEnd());
		total = testanswers.size();
		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);
		
		%>
	</liferay-ui:search-container-results>
	<liferay-ui:search-container-row
		className="com.liferay.lms.model.TestAnswer"
		keyProperty="answerId"
		modelVar="testanswer">


		<liferay-ui:search-container-column-text >
			<portlet:actionURL var="editanswerURL" name="editanswer" />
			<aui:form id="f_<%=testanswer.getAnswerId() %>" action="<%=editanswerURL %>" method="post">
			
				<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
			
				<aui:input type="textarea" cols="130" rows="4" name="answer" value="<%=testanswer.getAnswer() %>"></aui:input>
				<% 
				PortletRequest portletRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
				if(SessionErrors.contains(portletRequest, "answer-test-required_"+testanswer.getAnswerId())){ %>
				<div class="portlet-msg-error">
					<liferay-ui:message key="answer-test-required" />
				</div>
				<% } %>
				<aui:input size="120" name="feedbackCorrect" label="feedback" value="<%=testanswer.getFeedbackCorrect() %>"></aui:input>
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