<%@page import="com.liferay.portlet.expando.model.ExpandoTableConstants"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<div class="coursedasset">
<%
Course course=(Course)request.getAttribute("course");
AssetEntry asset=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
Group generatedGroup=GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
Group university=GroupLocalServiceUtil.getGroup(course.getGroupId());
%>
<div class="courselogodiv">
<%
if(generatedGroup.getPublicLayoutSet().getLogo())
				{
					long logoId = generatedGroup.getPublicLayoutSet().getLogoId();
					%>
					<img class="courselogo" src="/image/layout_set_logo?img_id=<%=logoId%>">
					
					<%
				}
%>
</div>
<p class="description"><%=asset.getSummary() %></p>
<%
String uniurl="#";
if(university.getPublicLayoutsPageCount()>0)
{
uniurl="/web"+university.getFriendlyURL();
}
%>
<a class="university" href="<%=uniurl%>"><%=university.getName() %></a>

</div>