<%@page import="com.liferay.portlet.PortletURLUtil"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.theme.PortletDisplay"%>
<%@page import="javax.portlet.PortletMode"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.service.ClassNameLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%

List<AssetRendererFactory> factories= AssetRendererFactoryRegistryUtil.getAssetRendererFactories();
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
long searchGroupId=themeDisplay.getScopeGroupId();
if(course!=null)
{
	searchGroupId=course.getGroupId();
}
String assetTypes=PropsUtil.get("lms.internalresource.assettypes");
String[] allowedAssetTypes=assetTypes.split(",");

%>
<liferay-ui:icon-menu message="add">
<%
for(String assetType:allowedAssetTypes)
{
	PortletURL pURL=getAddPortletURL(course.getGroupId(), liferayPortletRequest, liferayPortletResponse, assetType);
	AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetType);
	String addPortletURLString=pURL.toString();
	Group theGroup = GroupLocalServiceUtil.getGroup(course.getGroupId());
	addPortletURLString = HttpUtil.addParameter(addPortletURLString, "doAsGroupId", course.getGroupId());
	addPortletURLString = HttpUtil.addParameter(addPortletURLString, "refererPlid", plid);
	addPortletURLString = HttpUtil.addParameter(addPortletURLString, "layoutUuid", layout.getUuid());
	String taglibEditURL = "javascript:Liferay.Util.openWindow({dialog: {width: 960}, id: '" +
	liferayPortletResponse.getNamespace() + "editAsset', title: '" + ResourceActionsUtil.getModelResource(locale, assetType) +
	"', uri:'" + HtmlUtil.escapeURL(addPortletURLString) + "'});";
	
	%>
	<liferay-ui:icon
				message="<%= HtmlUtil.escape(assetType) %>"
				src="<%= assetRendererFactory.getIconPath(renderRequest) %>"
				url="<%= addPortletURLString %>"
			/>
	<%
}
%>
</liferay-ui:icon-menu>
<liferay-portlet:renderURL var="selectResource">
	<liferay-portlet:param name="jspPage" value="/html/resourceInternalActivity/admin/searchresults.jsp"/>
</liferay-portlet:renderURL>
<aui:form name="<portlet:namespace />ressearch" action="<%=selectResource %>" method="POST">
<aui:select name="className" label="asset-type">
<% 

for(String className:allowedAssetTypes)
{	
	String assettypename=LanguageUtil.get(pageContext, "model.resource." + className);	
	%>
	<aui:option value="<%=className%>" label="<%=assettypename%>"></aui:option>
	<%
	
}
%>
</aui:select>
<aui:input name="keywords" size="20" type="text"/>

<aui:input name="groupId" type="hidden" value="<%=Long.toString(searchGroupId) %>" />

<%@ include file="/html/resourceInternalActivity/admin/catselector.jspf" %>

<aui:button type="submit" value="search" />
</aui:form>
<%!
public PortletURL getAddPortletURL(long groupId,LiferayPortletRequest liferayPortletRequest, LiferayPortletResponse liferayPortletResponse, String className) throws Exception {
	ThemeDisplay themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);

	AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

	if (assetRendererFactory == null) {
		return null;
	}

	liferayPortletRequest.setAttribute(WebKeys.ASSET_RENDERER_FACTORY_CLASS_TYPE_ID, 0);

	PortletURL addPortletURL = assetRendererFactory.getURLAdd(liferayPortletRequest, liferayPortletResponse);

	if (addPortletURL == null) {
		return null;
	}

	addPortletURL.setWindowState(LiferayWindowState.POP_UP);
	addPortletURL.setParameter("groupId", String.valueOf(groupId));


	addPortletURL.setPortletMode(PortletMode.VIEW);
	PortletURL redirectPortletURL=liferayPortletResponse.createRenderURL();
	redirectPortletURL.setParameter("className",className);
	redirectPortletURL.setParameter("jspPage","/html/resourceInternalActivity/admin/searchresults.jsp");
	redirectPortletURL.setParameter("groupId",Long.toString(groupId));

	addPortletURL.setParameter("redirect", redirectPortletURL.toString());

	String referringPortletResource = ParamUtil.getString(liferayPortletRequest, "portletResource");

	if (Validator.isNotNull(referringPortletResource)) {
		addPortletURL.setParameter("referringPortletResource", referringPortletResource);
	}
	else {
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		addPortletURL.setParameter("referringPortletResource", portletDisplay.getId());

		
	}

	

	return addPortletURL;
}

%>