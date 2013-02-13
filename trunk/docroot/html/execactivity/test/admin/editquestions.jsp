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

<portlet:renderURL var="newquestionURL">
<portlet:param name="actId" value="<%=String.valueOf(learnact.getActId()) %>" />
<portlet:param name="jspPage" value="/html/execactivity/test/admin/newquestion.jsp"></portlet:param>
</portlet:renderURL>
<liferay-ui:icon
image="add"
label="<%= true %>"
message="execativity.editquestions.newquestion"
url='<%= newquestionURL %>'
/>
<%
PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/execactivity/test/admin/editquestions.jsp");
portletURL.setParameter("actId",Long.toString(learnact.getActId()));
%>
<portlet:renderURL var="importquestionsURL">
<portlet:param name="actId" value="<%=String.valueOf(learnact.getActId()) %>" />
<portlet:param name="jspPage" value="/html/execactivity/test/admin/importquestions.jsp"></portlet:param>
</portlet:renderURL>
<liferay-ui:icon
image="add"
label="<%= true %>"
message="execativity.editquestions.importquestions"
url='<%= importquestionsURL %>'
/>

<%if(learnact.getTypeId() == 0 || true){ %>	
<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="export"/>
</liferay-portlet:resourceURL>
<liferay-ui:icon image="export" label="<%= true %>" message="execativity.editquestions.exportcsv" method="get" url="<%=exportURL%>" />
<%}%>	

<%
java.util.List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(learnact.getActId());
%>
<liferay-ui:search-container emptyResultsMessage="there-are-no-questions"
 delta="10" iteratorURL="<%=portletURL%>">
<liferay-ui:search-container-results>
<%
results = ListUtil.subList(questions, searchContainer.getStart(),
searchContainer.getEnd());
total = questions.size();
pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row
className="com.liferay.lms.model.TestQuestion"
keyProperty="actId"
modelVar="activity">
<liferay-ui:search-container-column-text
name="text"
property="text"
/>
<liferay-ui:search-container-column-text
name="questionType"
property="questionType"
/>
<liferay-ui:search-container-column-jsp
path="/html/execactivity/test/admin/admin_actions.jsp"
align="right"
/>

</liferay-ui:search-container-row>
<liferay-ui:search-iterator />

</liferay-ui:search-container>