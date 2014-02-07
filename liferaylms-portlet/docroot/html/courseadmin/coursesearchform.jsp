<!-- <h1 class="taglib-categorization-filter entry-title"> -->
<%@page import="com.liferay.lms.service.util.CourseComparator"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>

<%

String search = ParamUtil.getString(request, "search","");
String freetext = ParamUtil.getString(request, "freetext","");
Integer state = ParamUtil.getInteger(request, "state",WorkflowConstants.STATUS_APPROVED);

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
	portletSession.setAttribute("state", state);
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
	try{
		Integer stateTemp = (Integer)portletSession.getAttribute("state");
		if(stateTemp!=null){
			state = stateTemp;
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
if( (catIds!=null&&catIds.length>0) || !freetext.isEmpty() || state!=WorkflowConstants.STATUS_ANY)
{
	List<AssetEntry> entries=new ArrayList<AssetEntry>();
	AssetEntryQuery entryQuery=new AssetEntryQuery();
	long[] groupIds={themeDisplay.getScopeGroupId()};
	entryQuery.setAllCategoryIds(catIds);
	entryQuery.setGroupIds(groupIds);
	entryQuery.setClassName(Course.class.getName());
	entryQuery.setEnablePermissions(true);
	entryQuery.setExcludeZeroViewCount(false);
	entryQuery.setVisible(true);
	entries.addAll(AssetEntryLocalServiceUtil.getEntries(entryQuery));
	entryQuery.setVisible(false);
	entries.addAll(AssetEntryLocalServiceUtil.getEntries(entryQuery));

	courses=new ArrayList<Course>();
	if(!freetext.isEmpty()){
		for(AssetEntry entry:entries)
		{
			if(lucenes.get(entry.getClassPK())!=null){
				Course course = null;
				try{
					course = CourseLocalServiceUtil.getCourse(entry.getClassPK());
				}catch(Exception e){
					continue;
				}
				
				if(state!=WorkflowConstants.STATUS_ANY){
					if(state==WorkflowConstants.STATUS_APPROVED){
						if(!course.getClosed())
							courses.add(course);
					}
					else if(state==WorkflowConstants.STATUS_INACTIVE){
						if(course.getClosed())
							courses.add(course);
					}
				}else{
					courses.add(course);
				}
			}
		}
	}else{
		for(AssetEntry entry:entries){
			Course course = null;
			try{
				course = CourseLocalServiceUtil.getCourse(entry.getClassPK());
			}catch(Exception e){
				continue;
			}
			
			if(state!=WorkflowConstants.STATUS_ANY){
				if(state==WorkflowConstants.STATUS_APPROVED){
					if(!course.getClosed())
						courses.add(course);
				}
				else if(state==WorkflowConstants.STATUS_INACTIVE){
					if(course.getClosed())
						courses.add(course);
				}
			}else{
				courses.add(course);
			}
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
		finalCourses.add(course);
}
courses=finalCourses;

//Order courses

if(courses!=null&&courses.size()>0){
	CourseComparator courseComparator = new CourseComparator(themeDisplay.getLocale());
	Collections.sort(courses, courseComparator );
}
%>

<portlet:renderURL var="searchURL">
</portlet:renderURL>
	<div class="admin-course-search-form">
		<aui:form action="${searchURL}" method="post" name="search">
			<aui:fieldset cssClass="checkBoxes">
				<aui:input name="search" type="hidden" value="search" />
				<aui:input inlineField="true" name="freetext" type="text" value="<%=freetext %>" />
				<aui:select inlineField="true" name="state">
					<aui:option label="active" selected="<%= (state == WorkflowConstants.STATUS_APPROVED) %>" value="<%= WorkflowConstants.STATUS_APPROVED %>" />
					<aui:option label="inactive" selected="<%= (state == WorkflowConstants.STATUS_INACTIVE) %>" value="<%= WorkflowConstants.STATUS_INACTIVE %>" />
					<aui:option label="any-status" selected="<%= (state == WorkflowConstants.STATUS_ANY) %>" value="<%= WorkflowConstants.STATUS_ANY %>" />
				</aui:select>
				<aui:button type="submit" value="search"></aui:button>
			</aui:fieldset>
			<c:if test="<%=scategories%>">
				<liferay-ui:panel title="categorization" collapsible="true" defaultState="<%=catIds.length>0?\"open\":\"closed\" %>">
					<aui:fieldset cssClass="checkBoxes">
						<liferay-ui:asset-categories-selector  className="<%= Course.class.getName() %>" curCategoryIds="<%=catIdsText %>" />
					</aui:fieldset>
				</liferay-ui:panel>
			</c:if>
		</aui:form>
	</div>
	