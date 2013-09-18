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
<portlet:renderURL var="newactivityURL">
<portlet:param name="jspPage" value="/html/courseadmin/editcourse.jsp"></portlet:param>
</portlet:renderURL>
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
		<liferay-portlet:param name="jspPage" value="/html/courseadmin/view.jsp"/>
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

if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.coursemodel",themeDisplay.getScopeGroupId(),"ADD_COURSE"))
{
%>
<div class="newitem2">
<liferay-ui:icon
image="add"
label="<%= true %>"
message="new-course"
url='<%= newactivityURL %>'
/>


</div>
<%
}
%>
<liferay-ui:error ></liferay-ui:error>
<liferay-ui:search-container emptyResultsMessage="there-are-no-courses" delta="10">
	<liferay-ui:search-container-results>
	<%
		results = ListUtil.subList(courses, searchContainer.getStart(), searchContainer.getEnd());
		total = courses.size();
		pageContext.setAttribute("results", results);
		pageContext.setAttribute("total", total);
	%>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row className="com.liferay.lms.model.Course" keyProperty="courseId" modelVar="course">
	
		<liferay-ui:search-container-column-text>
		<a href="/web/<%=course.getFriendlyURL()%>"><%=course.getTitle(themeDisplay.getLocale()) %></a>
		
		</liferay-ui:search-container-column-text>
		
	<%
	if( permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.UPDATE)
		||permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.DELETE)
		||permissionChecker.hasPermission(course.getGroupId(), Course.class.getName(), course.getCourseId(), ActionKeys.PERMISSIONS)){
	%>		
		<liferay-ui:search-container-column-jsp path="/html/courseadmin/admin_actions.jsp" align="right" />
	<%
	}
	%>
	</liferay-ui:search-container-row>
	
	<liferay-ui:search-iterator />

</liferay-ui:search-container>
</div>