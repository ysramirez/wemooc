<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null)
{
	
 learnact=(LearningActivity)request.getAttribute("activity");
}
else
{
	learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
}
%>
<portlet:actionURL name="importQuestions" var="importQuestionsURL">
<portlet:param name="actId" value="<%=String.valueOf(learnact.getActId()) %>" />
<portlet:param name="jspPage" value="/html/execactivity/test/admin/editquestions.jsp"></portlet:param>
</portlet:actionURL>
<aui:form name="fm" action="<%=importQuestionsURL%>"  method="post" enctype="multipart/form-data">
	<aui:fieldset>
		<aui:field-wrapper label="file" >
	    			<aui:input inlineLabel="left" inlineField="true"
					  	name="fileName" label="" id="fileName" type="file" value="" />
				</aui:field-wrapper>
	</aui:fieldset>
	<aui:button-row>
	<aui:button type="submit" value="upload"></aui:button>
	</aui:button-row>
</aui:form>