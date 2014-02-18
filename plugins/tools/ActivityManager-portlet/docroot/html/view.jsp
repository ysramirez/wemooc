<%@page import="org.jsoup.safety.Whitelist"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
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
				//Document[] docs = (Document[])request.getAttribute("docs");

				//List<Document> documents = ListUtil.fromArray(docs);
				List<Document> documents = (List<Document>)request.getAttribute("docs");
				int size = documents.size();
				documents = ListUtil.subList(documents, containerStart, containerEnd);
				
				List<Course> courses = new ArrayList<Course>();
				for(Document doc : documents){
					try{
						Course course = CourseLocalServiceUtil.getCourse(Long.parseLong(doc.get(Field.ENTRY_CLASS_PK)));
						courses.add(course);
					}catch(Exception e){}
				}
				
				pageContext.setAttribute("results", courses);
				System.out.println(documents.size());
				pageContext.setAttribute("total", size);
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
				<%
					String text = course.getDescription(themeDisplay.getLocale());
					text = Jsoup.clean(text, "", Whitelist.none());
					if(text.length()>300){
						text = text.substring(0,300)+"...";
					}
				%>
					<span class="cclosed"><%=text %></span>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="actmanager.num_modules">
				<span><%=ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId()).size() %></span>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="model.resource.com.liferay.portal.model.Group">
				<%
					Group g = null; 
					try{
						g = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
					}catch(Exception e){}
				%>
				<span><%if(g==null){ %><liferay-ui:message key="no" /><%}else{%><liferay-ui:message key="yes" /><%}%></span>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="model.resource.com.liferay.portlet.asset">
				<%
					AssetEntry ae = null; 
					try{
						ae = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
					}catch(Exception e){}
				%>
				<span><%if(ae==null){ %><liferay-ui:message key="no" /><%}else{%><liferay-ui:message key="yes" /><%}%></span>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>

<%@ include file="/html/activitymanager/audit.jspf" %>