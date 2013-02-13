<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@ include file="/html/surveyactivity/admin/init.jsp" %>

<%
	TestQuestion question = TestQuestionLocalServiceUtil.getTestQuestion(ParamUtil.getLong(request,"questionId"));
%>

<div class="editquestion">
	<portlet:actionURL var="editquestionURL" name="editquestion" />
	
	<portlet:renderURL var="backToQuestionsURL">
		<portlet:param name="jspPage" value="/html/surveyactivity/admin/edit.jsp"></portlet:param>
		<portlet:param name="actId" value="<%=Long.toString(question.getActId()) %>" />
	</portlet:renderURL>
	
	<liferay-util:buffer var="inputEditorHTML" >
		<liferay-ui:input-editor  width="80%"/>
	</liferay-util:buffer>
	
	<a href="<%=backToQuestionsURL.toString() %>"><%=LanguageUtil.get(pageContext,"back.to.questions")%></a>
	
	<aui:form name="qfm" action="<%=editquestionURL %>" method="post">
		<aui:input name="actId" type="hidden" value="<%=question.getActId() %>"></aui:input>
		<aui:input name="questionId" type="hidden" value="<%=question.getQuestionId() %>"></aui:input>
		<aui:field-wrapper label="enunciado">
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
				    	document.<portlet:namespace />qfm.<portlet:namespace />text.value =	window.<portlet:namespace />editor.getHTML();
				    }
				    var func = function ()
				    {
				    	var elem = document.getElementById("<portlet:namespace/>DescripcionRichTxt");
				    	elem.innerHTML = "<%=JavaScriptUtil.markupToStringLiteral(inputEditorHTML)%>";
				    };
				
				    AUI().on('domready', func);
			        //-->
			    </script>	
		</aui:field-wrapper>
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
		<aui:input type="textarea" cols="130" rows="4" name="answer" label="Answer"></aui:input>
		<aui:input type="hidden" name="questionId" value="<%=question.getQuestionId() %>"></aui:input>
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
		<liferay-ui:search-container-row className="com.liferay.lms.model.TestAnswer" keyProperty="answerId" modelVar="testanswer">
	
			<liferay-ui:search-container-column-text >
				<portlet:actionURL var="editanswerURL" name="editanswer" />
				<aui:form id="f_<%=testanswer.getAnswerId() %>" action="<%=editanswerURL %>" method="post">
					<aui:input  type="hidden" name="answerId" value="<%=testanswer.getAnswerId() %>"></aui:input>
					<aui:input type="textarea" cols="130" rows="4" name="answer" value="<%=testanswer.getAnswer() %>"></aui:input>
					<aui:column>
						<aui:button type="submit" value="modify"></aui:button>
					</aui:column>
				</aui:form>
			</liferay-ui:search-container-column-text>
	
			<liferay-ui:search-container-column-jsp path="/html/surveyactivity/admin/admin_answer_actions.jsp" align="right" />
	
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	
	</liferay-ui:search-container>
	<%
	}
	%>
</div>