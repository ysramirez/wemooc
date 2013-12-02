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
<portlet:renderURL var="newactivityURL">
<portlet:param name="jspPage" value="/html/courseadmin/editcourse.jsp"></portlet:param>
<portlet:param name="redirect" value="<%= currentURL %>"></portlet:param>
</portlet:renderURL>
<div class="portlet-toolbar search-form">
<h1 class="taglib-categorization-filter entry-title">
<%

String search = ParamUtil.getString(request, "search","");
String freetext = ParamUtil.getString(request, "freetext","");
long catId=ParamUtil.getLong(request, "categoryId",0);

Enumeration<String> pnames =request.getParameterNames();
ArrayList<String> tparams = new ArrayList<String>();
ArrayList<Long> assetCategoryIds = new ArrayList<Long>();


while(pnames.hasMoreElements()){
	String name = pnames.nextElement();
	if(name.length()>16&&name.substring(0,16).equals("assetCategoryIds")){
		tparams.add(name);
		String value = request.getParameter(name);
		String[] values = value.split(",");
		for(String valuet : values){
			try{
				assetCategoryIds.add(Long.parseLong(valuet));
			}catch(Exception e){
			}
		}
		
	}
}

if(ParamUtil.getString(request, "search").equals("search")){
	portletSession.setAttribute("freetext", freetext);
	portletSession.setAttribute("assetCategoryIds", assetCategoryIds);
}else{
	try{
		String freetextTemp = (String)portletSession.getAttribute("freetext");
		if(freetextTemp!=null){
			freetext = freetextTemp;
		}
	}catch(Exception e){}
	try{
		ArrayList<Long> assetCategoryIdsTemp = (ArrayList<Long>)portletSession.getAttribute("assetCategoryIds");
		if(assetCategoryIdsTemp!=null){
			assetCategoryIds = assetCategoryIdsTemp;
		}
	}catch(Exception e){}
}


boolean scategories = GetterUtil.getBoolean(renderRequest.getPreferences().getValues("categories", new String[]{StringPool.TRUE})[0],true);

HashMap<Long,Document> lucenes = new HashMap<Long,Document>();
if(!freetext.isEmpty()){
	SearchContext scon=new SearchContext();
	if(catId>0){
		try{
			BooleanQuery booleanQueryCategoryId = BooleanQueryFactoryUtil.create(scon);
			booleanQueryCategoryId.addTerm("assetCategoryIds", catId);
			BooleanClause booleanClauseCategoryId = BooleanClauseFactoryUtil.create(scon,booleanQueryCategoryId, BooleanClauseOccur.MUST.getName());
			BooleanClause[] clauses = {booleanClauseCategoryId};
			scon.setBooleanClauses(clauses);
		}catch(ParseException pe){
			pe.printStackTrace();
		}
	}
	scon.setCompanyId(themeDisplay.getCompanyId());
	scon.setKeywords(freetext+"*");
	
	Indexer indexer=IndexerRegistryUtil.getIndexer(Course.class);
	Hits hits=indexer.search(scon);
	Document[] docs=hits.getDocs();
	for(Document doc : docs){
		lucenes.put(Long.parseLong(doc.get(Field.ENTRY_CLASS_PK)), doc);
	}
}

java.util.List<Course> courses=null;
AssetCategory category=null;
long[] catIds=ParamUtil.getLongValues(request, "categoryIds");

StringBuffer sb = new StringBuffer();
for(long cateId : assetCategoryIds){
	sb.append(cateId);
	sb.append(",");
}
String catIdsText = sb.toString();

if((catIds==null||catIds.length<=0)&&(assetCategoryIds!=null&&assetCategoryIds.size()>0)){
	catIds = new long[assetCategoryIds.size()];
	for(int i=0;i<assetCategoryIds.size();i++){
		catIds[i] = assetCategoryIds.get(i);
	}
}
if(assetCategoryIds==null||assetCategoryIds.size()<=0){
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
if( (catIds!=null&&catIds.length>0) || !freetext.isEmpty())
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
	if(!freetext.isEmpty()){
		for(AssetEntry entry:entries)
		{
			if(lucenes.get(entry.getClassPK())!=null){
				courses.add(CourseLocalServiceUtil.getCourse(entry.getClassPK()));
			}
		}
	}else{
		for(AssetEntry entry:entries){
			courses.add(CourseLocalServiceUtil.getCourse(entry.getClassPK()));
		}
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
if( permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.coursemodel",themeDisplay.getScopeGroupId(),"ADD_COURSE"))
{
%>

<portlet:renderURL var="searchURL">
	<portlet:param name="jspPage" value="/html/courseadmin/view.jsp"></portlet:param>
</portlet:renderURL>
	<div class="admin-course-search-form">
		<aui:form action="${searchURL}" method="post" name="search">
			<aui:fieldset cssClass="checkBoxes">
				<aui:input name="search" type="hidden" value="search" />
				<aui:input inlineField="true" name="freetext" type="text" value="" />
				<aui:button type="submit" value="search"></aui:button>
			</aui:fieldset>
			<c:if test="<%=scategories%>">
				<aui:fieldset cssClass="checkBoxes">
					<liferay-ui:asset-categories-selector
						className="<%=Course.class.getName()%>" />
				</aui:fieldset>
			</c:if>
		</aui:form>
	</div>
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

PortletURL portletURL = renderResponse.createRenderURL();
portletURL.setParameter("jspPage","/html/courseadmin/view.jsp");
portletURL.setParameter("freetext",freetext);
for(String param : tparams){
	portletURL.setParameter(param,request.getParameter(param));
}
portletURL.setParameter("search","search");
%>
<liferay-ui:success key="courseadmin.clone.confirmation.success" message="courseadmin.clone.confirmation.success" />
<liferay-ui:error ></liferay-ui:error>
<liferay-ui:search-container iteratorURL="<%=portletURL %>" emptyResultsMessage="there-are-no-courses" delta="10">
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
	%>
		<liferay-ui:search-container-column-text>
		<a href="/web/<%=groupsel.getFriendlyURL()%>"><%=course.getTitle(themeDisplay.getLocale()) %></a>
		
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