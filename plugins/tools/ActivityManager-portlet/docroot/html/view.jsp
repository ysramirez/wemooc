<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.manager.CleanLearningActivity"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lmssa.service.ActManAuditLocalServiceUtil"%>
<%@page import="com.liferay.lmssa.model.ActManAudit"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.mysql.jdbc.DocsConnectionPropsHelper"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@ include file="/html/init.jsp" %>
<%@ include file="/html/head.jsp" %>

<h3><liferay-ui:message key="moduleupdateresult.allcourses" /></h3>
<portlet:actionURL var="searchURL" name="search" />

	<div class="admin-course-search-form">
		<aui:form action="${searchURL}" method="post" name="search">
			<aui:fieldset cssClass="checkBoxes">
				<aui:input name="search" type="hidden" value="search" />
				<aui:input inlineField="true" name="freetext" type="text" value="${freetext}" />
			<%--
				<aui:select inlineField="true" name="state">
					<aui:option label="active" selected="${state eq WorkflowConstants.STATUS_APPROVED}" value="${WorkflowConstants.STATUS_APPROVED}" />
					<aui:option label="inactive" selected="${state eq WorkflowConstants.STATUS_INACTIVE}" value="${WorkflowConstants.STATUS_INACTIVE}" />
					<aui:option label="any-status" selected="${state eq WorkflowConstants.STATUS_ANY}" value="${WorkflowConstants.STATUS_ANY}" />
				</aui:select>
			 --%>
				<aui:button type="submit" value="search"></aui:button>
			</aui:fieldset>
		</aui:form>
	</div>
	
<% 
	PortletURL filter = renderResponse.createRenderURL();
	filter.setParameter("freetext", ParamUtil.getString(request, "freetext",""));
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>
<c:if test="${not empty docs}">
	<liferay-ui:search-container curParam="courses" iteratorURL="<%=filter %>" emptyResultsMessage="there-are-no-courses" delta="10">
		<liferay-ui:search-container-results>
			<%

				int containerStart;
				int containerEnd;
				try {
					containerStart = ParamUtil.getInteger(request, "containerStart");
					containerEnd = ParamUtil.getInteger(request, "containerEnd");
				} catch (Exception e) {
					containerStart = searchContainer.getStart();
					containerEnd = searchContainer.getEnd();
				}
				if (containerStart <=0) {
					containerStart = searchContainer.getStart();
					containerEnd = searchContainer.getEnd();
				}
			
				List<Course> orderedCourses = new ArrayList<Course>();
				Document[] docs = (Document[])request.getAttribute("docs");

				List<Document> documents = ListUtil.fromArray(docs);
				documents = ListUtil.subList(documents, containerStart, containerEnd);
				
				List<Course> courses = new ArrayList<Course>();
				for(Document doc : documents){
					try{
						Course course = CourseLocalServiceUtil.getCourse(Long.parseLong(doc.get(Field.ENTRY_CLASS_PK)));
						courses.add(course);
					}catch(Exception e){}
				}
				
				pageContext.setAttribute("results", courses);
				pageContext.setAttribute("total", docs.length);
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
			<portlet:actionURL var="viewCourseURL" name="viewCourse">
        		<portlet:param name="id" value="<%=String.valueOf(course.getPrimaryKey()) %>" />
			</portlet:actionURL>
			<liferay-ui:search-container-column-text name="title">
				<c:if test="${!active}">
					<a href="<%=viewCourseURL%>"><span class="cclosed"><%=course.getTitle(themeDisplay.getLocale()) %></span></a>
				</c:if>
				<c:if test="${active}">
					<span class="cclosed"><%=course.getTitle(themeDisplay.getLocale()) %></span>
				</c:if>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="description">
				<c:if test="${!active}">
					<a href="<%=viewCourseURL%>"><span class="cclosed"><%=course.getDescription(themeDisplay.getLocale()) %></span></a>
				</c:if>
				<c:if test="${active}">
					<span class="cclosed"><%=course.getDescription(themeDisplay.getLocale()) %></span>
				</c:if>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="actmanager.num_modules">
				<span><%=ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId()).size() %></span>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>

<%@ include file="/html/activitymanager/audit.jspf" %>