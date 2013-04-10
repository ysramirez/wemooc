<%@ include file="/init.jsp" %>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@page import="com.liferay.lms.indexer.CourseIndexer"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="com.liferay.portal.kernel.search.Indexer"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.kernel.search.SearchContextFactory"%>
<jsp:include page="/html/coursesearch/searchform.jsp"></jsp:include>
<%
String text=ParamUtil.getString(request, "text","").trim();
if(!"".equals(text))
{
	SearchContext scon=new SearchContext();
	scon.setKeywords(text);
	scon.setCompanyId(themeDisplay.getCompanyId());
	scon.setPortletIds(new String[]{CourseIndexer.PORTLET_ID});
	Indexer indexer=IndexerRegistryUtil.getIndexer(Course.class);
	Hits hits=indexer.search(scon);
	Document[] docs=hits.getDocs();
	List<Course> courses=new ArrayList<Course>();
	for(Document doc:docs)
	{
		AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),Long.parseLong(doc.get(Field.ENTRY_CLASS_PK)));
		Course course=CourseLocalServiceUtil.getCourse(entry.getClassPK());
		if(entry.isVisible() &&permissionChecker.hasPermission(course.getGroupId(),Course.class.getName(),entry.getClassPK(),ActionKeys.VIEW))
		{
			courses.add(course);
		}
		
	}

%>

<liferay-ui:search-container emptyResultsMessage="there-are-no-courses"
 delta="20" >
 <liferay-ui:search-container-results>
<%
results = ListUtil.subList(courses, searchContainer.getStart(),
searchContainer.getEnd());
total = courses.size();
pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
 <liferay-ui:search-container-row
className="com.liferay.lms.model.Course"
keyProperty="courseId"
modelVar="course">
<liferay-ui:search-container-column-text>
<%
AssetRendererFactory rendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(Course.class.getName());
AssetRenderer renderer=rendererFactory.getAssetRenderer(course.getCourseId());
String path=renderer.render(renderRequest,renderResponse,AssetRenderer.TEMPLATE_FULL_CONTENT);
%>
<liferay-ui:header title="<%=course.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>
<liferay-util:include page="<%= path %>" portletId="<%= rendererFactory.getPortletId() %>" />
<div class="asset-more">
<a href="/web/<%=course.getFriendlyURL()%>" ><liferay-ui:message key="searchresults.seecoursecard" /></a>
</div>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
<liferay-ui:search-iterator />
</liferay-ui:search-container>
<%
}
%>

