

<%@include file="/init.jsp"%>

<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.model.Group"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Layout"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>


<portlet:renderURL var="recargarURL">
</portlet:renderURL>

<%
String layoutidr=ParamUtil.getString(request, "layoutid","");
if(layoutidr.length()>0)
{
%>
	<script>
	location.href="<%=recargarURL%>";
	</script>
<%
}
else
{
	Layout actual=themeDisplay.getLayout();	
	Layout padre=actual;
	
	if(actual.getParentPlid()!=0)
	{
	 	padre=LayoutLocalServiceUtil.getLayout(actual.getParentPlid());
	}
	Group grupo=themeDisplay.getScopeGroup();
	List<Layout> loslayouts=LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),actual.isPrivateLayout());
	
	if(loslayouts.size()>2) // Debe de haber al menos 2 páginas porque la primera y la última (la del tutor/configuración donde está este portlet) no se pueden gestionar con este portlet
	{
%>
		<table width="100%" cellspacing="2">
		<tbody>
<%

	
		for(int i=1;i<(loslayouts.size()-1);i++) // Evitamos la primera, que no es optativa, y la última que es la del tutor y la que contiene este portlet de administración 
		{
			Layout ellayout=loslayouts.get(i);	
			if(!ellayout.getFriendlyURL().equals("/reto"))
			{
							
	%>
			<tr><td width="95%"><b><%=ellayout.getHTMLTitle(themeDisplay.getLocale()) %></b></td>
			<td>
			<portlet:actionURL name="changeLayout" var="changeLayoutURL">
				<portlet:param name="layoutid" value="<%=Long.toString(ellayout.getPlid()) %>"></portlet:param>
			</portlet:actionURL>
	
	<%
				if(ellayout.isHidden())
				{
	%>
					<liferay-ui:icon  image="deactivate" label="add" url="<%=changeLayoutURL %>" message="<%=LanguageUtil.get(pageContext,\"test.activate\")%>" />
	<%
				}
				else
				{
	%>
					<liferay-ui:icon image="activate" label="remove" url="<%=changeLayoutURL %>" message="<%=LanguageUtil.get(pageContext,\"test.desactivate\")%>" />
	<%
				}
	%>
			</td>
			</tr>
	<%
			}
		}
%>
		</tbody>
		</table>
<%
	}
}
%>

