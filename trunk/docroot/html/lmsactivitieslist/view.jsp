<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ImageLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>

<%@ include file="/init.jsp"%>

<%
long moduleId = ParamUtil.getLong(request, "moduleId", 0);
boolean actionEditing = ParamUtil.getBoolean(request,
		"actionEditing", false);
long actId = ParamUtil.getLong(request, "actId", 0);
if (moduleId == 0) 
{
	java.util.List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	if (modules.size() > 0) {
		Module theModule = modules.get(0);
		moduleId = theModule.getModuleId();
	}
}
%>
<script type="text/javascript">
<!--

AUI().ready('event', 'node','aui-base','aui-dialog','aui-dialog-iframe','anim',function(A) {
	
	A.one(window).on('message', 
		function(event){
			var html5Event=event._event;
			if(html5Event.data.name=='reloadModule'){
				if(html5Event.data.moduleId==<%=Long.toString(moduleId)%>){
					var moduleTitlePortlet=A.one('#p_p_id<%=StringPool.UNDERLINE+PortalUtil.getJsSafePortletId("ModuleTitle"+
							PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName())+StringPool.UNDERLINE %>');
					if(moduleTitlePortlet!=null) {
						Liferay.Portlet.refresh(moduleTitlePortlet);
					}

					var moduleDescriptionPortlet=A.one('#p_p_id<%=PortalUtil.getJsSafePortletId(StringPool.UNDERLINE+"moduleDescription"+
							PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName())+StringPool.UNDERLINE %>');
					if(moduleDescriptionPortlet!=null) {
						Liferay.Portlet.refresh(moduleDescriptionPortlet);
					}
				}
				Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'));	
  
			}
			else if(html5Event.data.name=='closeModule'){
				A.DialogManager.closeByChild('#editModule');
			} 
			else if(html5Event.data.name=='closeActivity'){
				A.DialogManager.closeByChild('#editlesson');
			}

		});

		A.all('.lms-tree ul li.option-more ul').each(function(ul){
			ul.hide();
		});

		A.all('.lms-tree ul li span.desplegar').each(function(span){
			var parentNode=span.get('parentNode');
			var wrapper = A.Node.create('<div style="overflow: hidden;" ></div>');
			wrapper.append(parentNode.one('ul').replace(wrapper));
			var height=wrapper.height();
			var open = new A.Anim({node: wrapper, to: {height:  height},
			     easing: A.Easing.easeOut});
     		var close = new A.Anim({node: wrapper, to: {height:  -100},
			     easing: A.Easing.easeIn});
			span.on('click',function(){
				
				if(parentNode.hasClass('option-more')) {
					parentNode.removeClass("option-more");
					parentNode.addClass("option-less");
					close.run();
				
				}
				else {
					parentNode.removeClass("option-less");
					parentNode.addClass("option-more");
					open.run();
				}
	
			})
		});

});

//-->
</script>
<div class="modulo portlet-toolbar search-form lms-tree">
	<ul>
<%
renderRequest.setAttribute("moduleId", Long.toString(moduleId));
boolean registrado=UserLocalServiceUtil.hasGroupUser(themeDisplay.getScopeGroupId(),themeDisplay.getUserId());
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
if(course!=null && permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW))
{
java.util.List<Module> theModules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
Date today=new java.util.Date(System.currentTimeMillis());
for(Module theModule:theModules)
{
	%>
	
	<li class='<%= moduleId == theModule.getModuleId() ? "option-less" : "option-more"%>'>
	<span class="desplegar"></span>
	
	<%
	
	ModuleResult moduleResult=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),themeDisplay.getUserId());
	long done=0;
	if(moduleResult!=null)
	{
		done=moduleResult.getResult();
	
	%>
	
	<%
	}
	%>

	<%
	java.util.Date now=new java.util.Date(System.currentTimeMillis());
	if(!ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId())||actionEditing)
	{
	
		Group grupo=themeDisplay.getScopeGroup();
		long retoplid=themeDisplay.getPlid();
		for(Layout theLayout:LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),false))
		{
	
			if(theLayout.getFriendlyURL().equals("/reto"))
			{
				retoplid=theLayout.getPlid();
				
			}
		}
		
		LiferayPortletURL  gotoModuleURL = (LiferayPortletURL)renderResponse.createRenderURL();
	    gotoModuleURL.removePublicRenderParameter("actId");
	    gotoModuleURL.setWindowState(WindowState.NORMAL);
	    gotoModuleURL.setParameter("moduleId", Long.toString(theModule.getModuleId()));
	    gotoModuleURL.setPlid(retoplid);
	
	%>
	
	<a href="<%=gotoModuleURL.toString() %>"><%=theModule.getTitle(themeDisplay.getLocale()) %></a>
	<%if(actionEditing)
		{
		%>
			<div class="iconsedit"><%@ include file="/JSPs/module/edit_actions.jspf" %></div>
			
	
	
	<%
		}
	%>
		
		<%
	}
	else
	{
		%>
		<%=theModule.getTitle(themeDisplay.getLocale()) %> <%=done %>%
		<%
		if(actionEditing)
		{
		%>
		
		
	
	<%
		}
	
		
	}
	if(theModule.getModuleId()==moduleId)
	{
	%>
	
	<jsp:include page="/html/lmsactivitieslist/viewactivities.jsp"></jsp:include>
	
	<%
	}
	%>
	</li>
	
	<%
}
%>
</ul>
	</div>
	<%
}
else
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
%>
