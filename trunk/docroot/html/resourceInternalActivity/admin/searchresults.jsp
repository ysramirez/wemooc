<%@page import="com.liferay.portal.kernel.search.Field"%>
<%@page import="com.liferay.portal.kernel.search.Document"%>
<%@page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil"%>
<%@page import="com.liferay.portal.kernel.search.Hits"%>
<%@ include file="/html/resourceInternalActivity/admin/searchresource.jsp" %>

<liferay-ui:search-container emptyResultsMessage="there-are-no-assets"
 delta="10">
<liferay-ui:search-container-results>
<%
String className=ParamUtil.getString(request,"className","");
String keywords=ParamUtil.getString(request,"keywords","");
long groupIdSelected=ParamUtil.getLong(request,"groupId",themeDisplay.getScopeGroupId());
AssetRendererFactory selarf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);
long[] groupIds=new long[1];
groupIds[0]=groupIdSelected;
total=0;
if(keywords.length()>0)
{

Hits hits = AssetEntryLocalServiceUtil.search(themeDisplay.getCompanyId(),groupIds,selarf.getPortletId(),keywords,searchContainer.getStart(), searchContainer.getEnd());


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
 <liferay-portlet:param value="<%=Long.toString(learnact.getActId()) %>" name="actId"/>
 <liferay-portlet:param value="/html/resourceInternalActivity/admin/edit.jsp" name="jspPage"/>
 <liferay-portlet:param value="<%=Long.toString(assetEntry.getEntryId()) %>" name="entryId"/>
</liferay-portlet:actionURL>
<liferay-ui:search-container-column-text>
<liferay-ui:icon image="add" label="select" url="<%=selectResourceURL.toString() %>"></liferay-ui:icon>
</liferay-ui:search-container-column-text>
</liferay-ui:search-container-row>
 <liferay-ui:search-iterator />
</liferay-ui:search-container>
