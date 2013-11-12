<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
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
<div class="portlet-toolbar search-form">
<h1 class="taglib-categorization-filter entry-title">
<%

java.util.List<Course> courses=null;

long catId=ParamUtil.getLong(request, "categoryId",0);
AssetCategory category=null;
long[] catIds=ParamUtil.getLongValues(request, "categoryIds");
for(long cat:catIds)
{
	if(cat!=0)
	{
	AssetCategory categ=AssetCategoryLocalServiceUtil.getAssetCategory(cat);
	
		String cats=StringUtil.merge(ArrayUtil.remove(catIds, cat));
	    if(cats==null || cats.equals(""))
	    {
	    	cats="0";
	    }
	%>
	<liferay-portlet:renderURL var="choseCatURL" >
		<liferay-portlet:param name="jspPage" value="/html/generalstats/view.jsp"/>
		<liferay-portlet:param name="categoryIds" value="<%=cats %>"/>
		</liferay-portlet:renderURL>
	<span class="asset-entry">
	<%=categ.getTitle(locale) %>
	<a href="<%= choseCatURL %>" title="<liferay-ui:message key="remove" />">
				<span class="aui-icon aui-icon-close aui-textboxlistentry-close"></span>
			</a>
	</span>
	<%
	}
}
%>
</h1>
<%
if(catIds==null ||catIds.length==0)
{
	if(catId!=0)
	{
		catIds=new long[]{catId};
	}
}
if(catIds!=null&&catIds.length>0)
{
	catIds=ArrayUtil.remove(catIds, 0l);
}
if(catIds!=null&&catIds.length>0)
{

	java.util.List<AssetEntry> entries=new java.util.ArrayList<AssetEntry>();
	AssetEntryQuery entryQuery=new AssetEntryQuery();
	long[] groupIds={themeDisplay.getScopeGroupId()};
	entryQuery.setAllCategoryIds(catIds);
	entryQuery.setGroupIds(groupIds);
	entryQuery.setClassName(Course.class.getName());
	entryQuery.setEnablePermissions(true);
	entryQuery.setExcludeZeroViewCount(false);
	entryQuery.setVisible(true);
	courses=new ArrayList<Course>();
	entries.addAll(AssetEntryLocalServiceUtil.getEntries(entryQuery));
	entryQuery.setVisible(false);
	entries.addAll(AssetEntryLocalServiceUtil.getEntries(entryQuery));
	for(AssetEntry entry:entries)
	{
		courses.add(CourseLocalServiceUtil.getCourse(entry.getClassPK()));
	}
}
else
{
	courses=CourseServiceUtil.getCoursesOfGroup(scopeGroupId);
}
java.util.List<Course> finalCourses=new ArrayList<Course>();
for(Course course:courses)
{
	if(!course.isClosed())
	{
		finalCourses.add(course);
	}
}
courses=finalCourses;
%>
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
	%>
		<liferay-ui:search-container-column-text name="coursestats.hay">
		<a href="/web/<%=groupsel.getFriendlyURL()%>"><%=course.getTitle(themeDisplay.getLocale()) %></a>
		
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text valign="right" name="coursestats.hay">
		<%=registered %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.start.course">
		<%=finalizados %>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text  valign="right" name="coursestats.end.course">
		<%=iniciados %>
		</liferay-ui:search-container-column-text>
		
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator  />

</liferay-ui:search-container>
</div>
