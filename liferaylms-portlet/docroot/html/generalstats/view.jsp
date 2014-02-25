<%@page import="java.util.Locale"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.model.ClassName"%>
<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PortalClassLoaderUtil"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.liferay.portal.kernel.search.ParseException"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClauseFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClauseOccur"%>
<%@page import="com.liferay.portal.kernel.search.BooleanClause"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.search.BooleanQuery"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>
<%	
DecimalFormat df = new DecimalFormat("#.#");
Locale loc = response.getLocale();
 %>
<div class="portlet-toolbar search-form">
<%@ include file="/html/courseadmin/coursesearchform.jsp" %>
<liferay-ui:success key="courseadmin.clone.confirmation.success" message="courseadmin.clone.confirmation.success" />
<liferay-ui:error ></liferay-ui:error>
<liferay-ui:search-container  deltaConfigurable="true" emptyResultsMessage="there-are-no-courses" delta="10">
	<liferay-ui:search-container-results>
	<%
	
		List<Course> orderedCourses = new ArrayList<Course>();
		orderedCourses.addAll(courses);
	    Collections.sort(orderedCourses, new Comparator<Course>() {
	        @Override
	        public int compare(final Course object1, final Course object2) {
	            return object1.getTitle().toLowerCase().compareTo(object2.getTitle().toLowerCase());
	        }
	    } );
	
		results = ListUtil.subList(orderedCourses, searchContainer.getStart(), searchContainer.getEnd());
		total = courses.size();
		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);
		
	%>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
	<%
		
		Group groupsel= GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
		long registered=UserLocalServiceUtil.getGroupUsersCount(course.getGroupCreatedId(),0);
		
		
		long finalizados = CourseResultLocalServiceUtil.countByCourseId(course.getCourseId(), true);
		long iniciados = CourseResultLocalServiceUtil.countByCourseId(course.getCourseId(), false) + finalizados;
		double avgResult=0;
		if(finalizados>0)
		{
			avgResult=CourseResultLocalServiceUtil.avgResult(course.getCourseId());
		}
		long activitiesCount=LearningActivityLocalServiceUtil.countLearningActivitiesOfGroup(course.getGroupCreatedId());
		long modulesCount=ModuleLocalServiceUtil.countByGroupId(course.getGroupCreatedId());
	%>
		<liferay-ui:search-container-column-text name="title">
		<a href='<%=themeDisplay.getPortalURL() +"/"+ loc.getLanguage() +"/web/"+ groupsel.getFriendlyURL()%>'><%=course.getTitle(themeDisplay.getLocale()) %></a>
		
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text valign="right" name="registered">
		<%=registered %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.start.course">
		<%=iniciados %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.end.course">
		<%=finalizados %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="closed">
		<%=course.getClosed() %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.modulestats.marks.average">
		<%=df.format(avgResult) %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.modulecounter">
		<%=modulesCount %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.activitiescounter">
		<%=activitiesCount %>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator  />

</liferay-ui:search-container>
</div>
