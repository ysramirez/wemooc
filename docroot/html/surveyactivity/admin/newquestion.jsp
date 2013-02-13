<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@ include file="/html/surveyactivity/admin/init.jsp" %>

<div class="newquestion">
	
	<portlet:actionURL var="addquestionURL" name="addquestion" />
	
	<portlet:renderURL var="backToQuestionsURL">
		<portlet:param name="jspPage" value="/html/surveyactivity/admin/edit.jsp"></portlet:param>
		<portlet:param name="actId" value="<%=Long.toString(learnact.getActId()) %>" />
	</portlet:renderURL>
	
	<liferay-util:buffer var="inputEditorHTML" >
		<liferay-ui:input-editor width="80%" />
	</liferay-util:buffer>
	
	<aui:form name="qfm" action="<%=addquestionURL%>"  method="post">
		<aui:input name="actId" type="hidden" value="<%= learnact.getActId()%>"></aui:input>
		<aui:field-wrapper label="text">
			<div id="<portlet:namespace/>DescripcionRichTxt"></div>
			<aui:input name="text" type="hidden" />
			<script type="text/javascript">
		    <!--
			    function <portlet:namespace />initEditor()
			    {
			    	return "";
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
			<aui:button type="submit" onClick="<%=extractCodeFromEditor%>">Submit</aui:button>
			<aui:button onclick="<%= backToQuestionsURL.toString() %>" type="cancel" />
		</aui:button-row>
	</aui:form>

</div>