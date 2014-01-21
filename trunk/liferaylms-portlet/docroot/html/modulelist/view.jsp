<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.lms.service.ClpSerializer"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
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
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@ include file="/init.jsp"%>

<%
	PortletPreferences preferences = null;
	String portletResource = ParamUtil.getString(request, "portletResource");
	if (Validator.isNotNull(portletResource)) 
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	else
		preferences = renderRequest.getPreferences();
	
	boolean showLockedModulesIcon = (preferences.getValue("showLockedModulesIcon", "0")).compareTo("1") == 0;
	boolean showModuleIcon = (preferences.getValue("showModuleIcon", "1")).compareTo("1") == 0;
	boolean numerateModules = (preferences.getValue("numerateModules", "0")).compareTo("1") == 0;
	boolean moduleTitleLinkable = (preferences.getValue("moduleTitleLinkable", "0")).compareTo("1") == 0;
	boolean showPercentDone = (preferences.getValue("showPercentDone", "1")).compareTo("1") == 0;
	boolean showModuleStartDate = (preferences.getValue("showModuleStartDate", "1")).compareTo("1") == 0;
	boolean showModuleEndDate = (preferences.getValue("showModuleEndDate", "1")).compareTo("1") == 0;
	boolean allowEditionMode = (preferences.getValue("allowEditionMode", "0")).compareTo("1") == 0;
	boolean allowAccessWhenFinishedButNotClosed = (preferences.getValue("allowAccessWhenFinishedButNotClosed", "0")).compareTo("1") == 0;
	

	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
%>
	<table class="coursemodule <%="course-status-".concat(String.valueOf(course.getStatus()))%>">
<%
		Date today=new java.util.Date(System.currentTimeMillis());
		if(course!=null && permissionChecker.hasPermission(course.getGroupCreatedId(),  Course.class.getName(),course.getCourseId(),ActionKeys.VIEW)){
			java.util.List<Module> theModules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			long currentThemeId=0, moduleId=0;
			String currentActivityPortletId =  null;
			long modulesCount=theModules.size();
			int themeId=0;
			for(Module theModule:theModules){
				themeId++;
				boolean canAccessLock = permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model", themeDisplay.getScopeGroupId() , "ACCESSLOCK");
				boolean actionEditing = (permissionChecker.hasPermission(course.getGroupCreatedId(), Course.class.getName(), course.getCourseId() , "UPDATE"))?true:false;
%>
				<tr>
<% 					
					if(showLockedModulesIcon){
						boolean showLock = (actionEditing || canAccessLock)?true:false;
						if(showLock){ 
%>
							<td class="icon">
<%
								if(theModule.getStartDate()!=null &&today.before(theModule.getStartDate())){
%>
									<liferay-ui:icon image="lock" alt="starting-soon" />
<%
								}else if(ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId())){
%>
									<liferay-ui:icon image="lock" alt="closed"/>
<%
								}
%>
							</td>
<%
						}
					}

					if(showModuleIcon){
%>
						<td class="icon">
<% 
							long entryId=theModule.getIcon();
							if(entryId!=0){
								FileEntry image=DLAppLocalServiceUtil.getFileEntry(entryId);
								if(image!=null){
%>
									<img src="<%= themeDisplay.getPathImage()%>/image_gallery?uuid=<%= image.getUuid() %>&groupId=<%=image.getGroupId() %>" />
<%
								}
							}
%>
						</td>
					
<%
					}
					
					Group grupo=themeDisplay.getScopeGroup();
					long retoplid=themeDisplay.getPlid();
					for(Layout theLayout:LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),false))
						if(theLayout.getFriendlyURL().equals("/reto")) retoplid=theLayout.getPlid();
					LiferayPortletURL  gotoModuleURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(renderRequest),
								PortalUtil.getJsSafePortletId("lmsactivitieslist"+PortletConstants.WAR_SEPARATOR+ClpSerializer.getServletContextName()), retoplid, PortletRequest.ACTION_PHASE);		
					gotoModuleURL.setParameter(ActionRequest.ACTION_NAME, "goToModule");
					gotoModuleURL.removePublicRenderParameter("actId");
					gotoModuleURL.setWindowState(WindowState.NORMAL);
					gotoModuleURL.setParameter("moduleId", Long.toString(theModule.getModuleId()));
					gotoModuleURL.setParameter("themeId", Long.toString(themeId));
					gotoModuleURL.setParameter("actionEditing", Boolean.toString(actionEditing));
					gotoModuleURL.setPlid(retoplid);
					gotoModuleURL.setPortletId("lmsactivitieslist_WAR_liferaylmsportlet");
					Object[] arg =  new Object[2];
					if(numerateModules ) arg[0] = themeId;
					else arg[0] = "";
					arg[1] = theModule.getTitle(themeDisplay.getLocale());
					Date startDate = theModule.getStartDate();
					boolean canAccess = (startDate != null )?(!today.before(theModule.getStartDate())):true;
%>
					
					<td class="title">
<%				
						if(moduleTitleLinkable && canAccess && (canAccessLock || !ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId()))){				 
%>
							<a href="<%=gotoModuleURL.toString() %>"><liferay-ui:message key="moduleTitle.chapter"  arguments="<%=arg %>" /></a>
<%		
						}else{
%>
							<liferay-ui:message key="moduleTitle.chapter"  arguments="<%=arg %>" />
<%
						}
						if(allowEditionMode && actionEditing){%>
							<div class="iconsedit"><%@ include file="/JSPs/module/edit_actions.jspf" %></div>
						<%}
%>				
					</td>
					
<%
					if(showPercentDone){
%>
						<td class="percent">
<%
							ModuleResult moduleResult=ModuleResultLocalServiceUtil.getByModuleAndUser(theModule.getModuleId(),themeDisplay.getUserId());
							long done=0;
							if(moduleResult!=null){
								done=moduleResult.getResult();
%>
								<%=done %>%
<%
							}
%>
						</td>
<%
					}

					if(showModuleStartDate || showModuleEndDate ){
%>
						<td class="date">
<%
							if(theModule.getStartDate()!=null &&today.before(theModule.getStartDate())){
								if(showModuleStartDate){
%>
									<liferay-ui:message key="fecha-inicio"/><br />
									<%=	dateFormatDate.format(theModule.getStartDate())%>
<%
								}
							}else{
								if(theModule.getEndDate()!=null&&today.before(theModule.getEndDate())){
									if(showModuleStartDate){
%>
										<liferay-ui:message key="fecha-inicio"/><br />
										<%=	dateFormatDateTime.format(theModule.getStartDate())%><br /><br />
<%
									}
									if(showModuleEndDate){
%>
										<liferay-ui:message key="fecha-fin"/><br />
										<%=	dateFormatDateTime.format(theModule.getEndDate())%>
<%
									}
								}
							}
%>
						</td>
<%
					}
%>
					
					<td class="access">
<%
						if(theModule.getStartDate()!=null &&today.before(theModule.getStartDate()) && !canAccessLock){
%>
							<liferay-ui:message key="starting-soon"/><!-- En LMS esta etiqueta es vacía -->
<%
						}else if(ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId()) && !canAccessLock){
%>
							<liferay-ui:message key="module-closed"/><!-- En LMS esta etiqueta es vacía -->
<%
						}else if(!ModuleLocalServiceUtil.isLocked(theModule.getModuleId(),themeDisplay.getUserId()) || canAccessLock){
							if(!allowAccessWhenFinishedButNotClosed && ModuleLocalServiceUtil.isUserPassed(theModule.getModuleId(),themeDisplay.getUserId())){
%>
								<a href="<%=gotoModuleURL.toString() %>"><liferay-ui:message key="module-finissed" /></a>
<%
							}else if(allowEditionMode && actionEditing){
%>
								<a class="module-list-button-edit" href="<%=gotoModuleURL.toString() %>"><liferay-ui:message key="edit" /></a>
<%
							}else{ 
%>
								<a class="module-list-button-access" href="<%=gotoModuleURL.toString() %>"><liferay-ui:message key="module-access" /></a>
<%
							} 
						}
%>
					</td>
				</tr>
<%
			}
		}else{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
%>
	</table>