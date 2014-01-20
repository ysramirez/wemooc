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
					}catch(Exception e){e.printStackTrace();}
				}
				
				System.out.print(docs.length);
				pageContext.setAttribute("results", courses);
				pageContext.setAttribute("total", docs.length);
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
			<portlet:actionURL var="viewCourseURL" name="viewCourse">
        		<portlet:param name="id" value="<%=String.valueOf(course.getPrimaryKey()) %>" />
			</portlet:actionURL>
			<liferay-ui:search-container-column-text>
				<a href="<%=viewCourseURL%>"><span class="cclosed"><%=course.getTitle(themeDisplay.getLocale()) %></span></a>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text>
				<a href="<%=viewCourseURL%>"><span class="cclosed"><%=course.getDescription(themeDisplay.getLocale()) %></span></a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>
<c:if test="${auditsnum ne 0}">
	<liferay-ui:search-container iteratorURL="<%=filter %>" curParam="audit" emptyResultsMessage="there-are-no-courses" delta="10">
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
				
				int auditsnum = ParamUtil.getInteger(request, "auditsnum");

				List<ActManAudit> audits = new ArrayList<ActManAudit>();

				try{
					audits = ActManAuditLocalServiceUtil.findBycompanyId(themeDisplay.getCompanyId(), containerStart, containerEnd);
				}catch(Exception e){}

				pageContext.setAttribute("results", audits);
				pageContext.setAttribute("total", auditsnum);
			%>
		</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.lmssa.model.ActManAudit" keyProperty="actManAuditId" modelVar="audit">
			<liferay-ui:search-container-column-text>
				<%=audit.getActManAuditId()%>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>