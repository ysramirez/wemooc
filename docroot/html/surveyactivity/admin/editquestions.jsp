<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="javax.portlet.RenderRequest"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@ include file="/init.jsp" %>
<%
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"resId"));
	request.setAttribute("activity", learningActivity);

	LiferayPortletURL backUrl = PortletURLFactoryUtil.create(request, PortalUtil.getJsSafePortletId("lmsactivitieslist"+
				PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName()), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);
	backUrl.setWindowState(LiferayWindowState.POP_UP);
	backUrl.setParameter("resId", String.valueOf(learningActivity.getActId()));
	backUrl.setParameter("jspPage", "/html/editactivity/editactivity.jsp");
	request.setAttribute("backUrl", backUrl.toString());

%>
<liferay-util:include page="/html/surveyactivity/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<portlet:renderURL var="newquestionURL">
	<portlet:param name="resId" value="<%=String.valueOf(learningActivity.getActId()) %>" />
	<portlet:param name="jspPage" value="/html/surveyactivity/admin/newquestion.jsp"></portlet:param>
	<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>"></portlet:param>	
</portlet:renderURL>

<liferay-ui:icon
image="add" cssClass="newitem2"
label="<%= true %>"
message="surveyactivity.editquestions.newquestion"
url='<%= newquestionURL %>'
/>
<script type="text/javascript">
<!--
AUI().ready(function(A) {
	A.one('.newitem2 a').focus();

});
//-->
</script>

<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="export"/>
	<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
</liferay-portlet:resourceURL>
<liferay-ui:icon image="export" label="<%= true %>" message="surveyactivity.editquestions.exportcsv" method="get" url="<%=exportURL%>" />

<%
	PortletURL editQuestionsURL = renderResponse.createRenderURL();
	editQuestionsURL.setParameter("jspPage","/html/surveyactivity/admin/editquestions.jsp");
	editQuestionsURL.setParameter("resId",Long.toString(learningActivity.getActId()));
	editQuestionsURL.setParameter("actionEditingDetails",StringPool.TRUE);
%>
<liferay-ui:search-container emptyResultsMessage="there-are-no-questions"
 delta="10" iteratorURL="<%=editQuestionsURL%>">
<liferay-ui:search-container-results>
<%
  DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TestQuestion.class).add(PropertyFactoryUtil.forName("actId").eq(learningActivity.getActId()));
  pageContext.setAttribute("results", TestQuestionLocalServiceUtil.dynamicQuery(dynamicQuery,searchContainer.getStart(),
		searchContainer.getEnd()));
  pageContext.setAttribute("total", (int)TestQuestionLocalServiceUtil.dynamicQueryCount(
		  DynamicQueryFactoryUtil.forClass(TestQuestion.class).add(PropertyFactoryUtil.forName("actId").eq(learningActivity.getActId()))));
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
<liferay-ui:search-container-column-jsp
path="/html/surveyactivity/admin/admin_actions.jsp"
align="right"
/>

</liferay-ui:search-container-row>
<liferay-ui:search-iterator />

</liferay-ui:search-container>

<aui:button-row>		
	<liferay-util:include page="/html/surveyactivity/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
</aui:button-row>