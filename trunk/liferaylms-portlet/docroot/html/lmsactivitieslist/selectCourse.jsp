<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.ArrayUtil"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.util.PortalClassLoaderUtil"%>
<%@ include file="/init.jsp"%>
<% 
	long typeId = ParamUtil.getLong(renderRequest, "type",-1);
	long assetRendererPlid = ParamUtil.getLong(renderRequest, "assetRendererPlid");
	Class<?> assetPublisherUtilClass = PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.assetpublisher.util.AssetPublisherUtil");
	Method  getGroupIds = assetPublisherUtilClass.getMethod("getGroupIds", PortletPreferences.class, Long.TYPE, Layout.class);
    PortletPreferences assetRendererPreferences = PortletPreferencesLocalServiceUtil.getPreferences(themeDisplay.getCompanyId(),
													PortletKeys.PREFS_OWNER_ID_DEFAULT,
													PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
													assetRendererPlid,
													ParamUtil.getString(renderRequest, "assetRendererId"));
    Layout assetRendererLayout = LayoutLocalServiceUtil.fetchLayout(assetRendererPlid);
    List<Course> courses = CourseLocalServiceUtil.dynamicQuery(
			CourseLocalServiceUtil.dynamicQuery().
				add(PropertyFactoryUtil.forName("groupCreatedId").
					in(ArrayUtil.toArray((long[])
						getGroupIds.invoke(null,assetRendererPreferences, 
								assetRendererLayout.getScopeGroup().getGroupId(), assetRendererLayout)))));
%>


<link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>





