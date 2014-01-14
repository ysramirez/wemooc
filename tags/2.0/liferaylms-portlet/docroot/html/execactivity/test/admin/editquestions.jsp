<%@page import="com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry"%>
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
<liferay-util:include page="/html/execactivity/test/admin/editHeader.jsp" servletContext="<%=this.getServletContext() %>" />

<portlet:renderURL var="newquestionURL">
	<portlet:param name="resId" value="<%=String.valueOf(learningActivity.getActId()) %>" />
	<portlet:param name="jspPage" value="/html/execactivity/test/admin/newquestion.jsp"></portlet:param>
	<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>"></portlet:param>	
</portlet:renderURL>
<script type="text/javascript">
<!--
AUI().ready(function(A) {
	A.one('.newitem2 a').focus();
	A.one("#<portlet:namespace/>TabsBack").scrollIntoView();
});
//-->
</script>
<div class="container-toolbar">
	<div class="bt_new">
		<liferay-ui:icon 
		label="<%= true %>"
		message="execativity.editquestions.newquestion"
		url='<%= newquestionURL %>'
		/>
	</div>
	<liferay-ui:icon-menu align="right" direction="down" extended="false" showWhenSingleIcon="false" cssClass='bt_importexport' message="import-export" showArrow="true">
		<portlet:renderURL var="importquestionsURL">
			<portlet:param name="resId" value="<%=String.valueOf(learningActivity.getActId()) %>" />
			<portlet:param name="actionEditingDetails" value="<%=StringPool.TRUE %>"></portlet:param>	
			<portlet:param name="jspPage" value="/html/execactivity/test/admin/importquestions.jsp"></portlet:param>
		</portlet:renderURL>
		<liferay-ui:icon
		image="add"
		label="<%= true %>"
		message="execativity.editquestions.importquestions"
		url='<%= importquestionsURL %>'
		/>
		
		<liferay-portlet:resourceURL var="exportURL" >
			<portlet:param name="action" value="export"/>
			<portlet:param name="resId" value="<%=Long.toString(learningActivity.getActId()) %>"/>
		</liferay-portlet:resourceURL>
		<liferay-ui:icon image="export" label="<%= true %>" message="execativity.editquestions.exportcsv" method="get" url="<%=exportURL%>" />
	</liferay-ui:icon-menu>
</div>
<%
	PortletURL editQuestionsURL = renderResponse.createRenderURL();
	editQuestionsURL.setParameter("jspPage","/html/execactivity/test/admin/editquestions.jsp");
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
	<liferay-ui:search-container-row className="com.liferay.lms.model.TestQuestion" keyProperty="actId" modelVar="activity">
		<liferay-ui:search-container-column-text name="text">
		<% String titleQuestion = activity.getText();
			if(titleQuestion.length() > 80) titleQuestion = titleQuestion.substring(0, 80) + " ...";%>
			<%=titleQuestion %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text name="questionType">
			<%=(new QuestionTypeRegistry().getQuestionType(activity.getQuestionType())).getTitle(themeDisplay.getLocale()) %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-jsp path="/html/execactivity/test/admin/admin_actions.jsp" align="right"/>
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:button-row>		
	<liferay-util:include page="/html/execactivity/test/admin/editFooter.jsp" servletContext="<%=this.getServletContext() %>" />
</aui:button-row>