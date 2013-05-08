<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);
	PortletURL backUrl = renderResponse.createRenderURL();
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));
	backUrl.setParameter("jspPage", "/html/execactivity/test/admin/editquestions.jsp");
	backUrl.setParameter("actionEditingDetails",StringPool.TRUE);
	request.setAttribute("backUrl", backUrl.toString());
%>
<liferay-util:include page="/html/execactivity/test/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />
<%
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null)
{
	
 learnact=(LearningActivity)request.getAttribute("activity");
}
else
{
	learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
}
%>
<portlet:actionURL name="importQuestions" var="importQuestionsURL">
	<portlet:param name="resId" value="<%=String.valueOf(learnact.getActId()) %>" />
	<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>" />	
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
		<liferay-util:include page="/html/execactivity/test/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
	</aui:button-row>
</aui:form>