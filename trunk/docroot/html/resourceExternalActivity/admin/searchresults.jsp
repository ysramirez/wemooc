<%@page import="java.util.HashMap"%>
<%@page import="java.io.Serializable"%>
<%@page import="com.liferay.portal.kernel.search.IndexerRegistryUtil"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.search.SearchContext"%>
<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@ include file="/html/resourceExternalActivity/admin/searchresource.jsp" %>

<liferay-ui:search-container emptyResultsMessage="there-are-no-assets"
 delta="10">
<liferay-ui:search-container-results>
<%
String className=ParamUtil.getString(request,"className","");
String keywords=ParamUtil.getString(request,"keywords","");
AssetRendererFactory selarf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);
long[] groupIds=new long[1];
groupIds[0]=themeDisplay.getScopeGroupId();
total=0;
if(keywords.length()>0)
{

SearchContext searchContext = new SearchContext();

searchContext.setAndSearch(false);

Map<String, Serializable> attributes = new HashMap<String, Serializable>();

attributes.put(Field.DESCRIPTION, keywords);
attributes.put(Field.TITLE, keywords);
attributes.put(Field.USER_NAME, keywords);

searchContext.setAttributes(attributes);

searchContext.setCompanyId(themeDisplay.getCompanyId());
searchContext.setEnd(searchContainer.getEnd());
searchContext.setGroupIds(groupIds);
searchContext.setPortletIds(new String[] {selarf.getPortletId()});
searchContext.setStart(searchContainer.getStart());

Hits hits = IndexerRegistryUtil.getIndexer(AssetEntry.class).search(searchContext);

results = new ArrayList<AssetEntry>();
for(Document doc : hits.getDocs()) { 
	Long classPK = Long.parseLong(doc.get(Field.ENTRY_CLASS_PK)); 
    
    AssetEntry asset = AssetEntryLocalServiceUtil.getEntry(className, classPK);
    results.add(asset);
    total=hits.getLength();
}
}
else
{
	AssetEntryQuery query=new AssetEntryQuery();
	query.setClassName(className);
	query.setGroupIds(groupIds);
	query.setStart(searchContainer.getStart());
	query.setStart(searchContainer.getEnd());
	total = AssetEntryLocalServiceUtil.getEntriesCount(query);
	results = AssetEntryServiceUtil.getEntries(query);
}



pageContext.setAttribute("results", results);
pageContext.setAttribute("total", total);
%>
</liferay-ui:search-container-results>
<liferay-ui:search-container-row
className="com.liferay.portlet.asset.model.AssetEntry"
keyProperty="entryId"
modelVar="assetEntry">
<liferay-ui:search-container-column-text
name="title"
property="title"
orderable="false"
/>
<liferay-portlet:actionURL name="selectResource" var="selectResourceURL">
 <liferay-portlet:param value="<%=Long.toString(learnact.getActId()) %>" name="resId"/>
 <liferay-portlet:param value="true" name="actionEditingDetails"/>
 <liferay-portlet:param value="/html/resourceExternalActivity/admin/resource.jsp" name="jspPage"/>
 <liferay-portlet:param value="<%=Long.toString(assetEntry.getEntryId()) %>" name="entryId"/>
</liferay-portlet:actionURL>
<liferay-ui:search-container-column-text>
<liferay-ui:icon image="add" label="select" url="<%=selectResourceURL.toString() %>"></liferay-ui:icon>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
 <liferay-ui:search-iterator />
</liferay-ui:search-container>
