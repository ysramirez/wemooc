<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp"%>

<script type="text/javascript">
			
Liferay.provide(
        window,
        '<portlet:namespace />goToModule',
        function() {
        	var A = AUI();
        	var val = A.one('#<portlet:namespace />moduleSelect > :selected').val();
        	var modId = val.split("_")[0];
        	var i = val.split("_")[1];
			var renderUrl = Liferay.PortletURL.createActionURL();	
			renderUrl.setWindowState('<%= LiferayWindowState.NORMAL.toString() %>');
			renderUrl.setPortletId("lmsactivitieslist_WAR_liferaylmsportlet");
			renderUrl.setParameter('actionEditing', <%=ParamUtil.getBoolean(request, "actionEditing", false)%>);
			renderUrl.setParameter('moduleId', modId);
			renderUrl.setParameter('themeId', i);
			renderUrl.setParameter('<%=ActionRequest.ACTION_NAME%>', "goToModule");
			location.href=renderUrl.toString();
        },
        ['liferay-portlet-url']
    );
	
</script>

<div class="modules_selector_container">
	<%
	List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());

	if(modules.size()>1){
		int i=0;
		long moduleId = ParamUtil.getLong(request, "moduleId", modules.get(0).getModuleId());
%>
		<aui:select name="moduleSelect" inlineLabel="true" label="" cssClass="modules_selector_list">
<%
			for(Module mod:modules){
				i++;
%>
				<aui:option selected="<%= mod.getModuleId()==moduleId %>" 
					label="<%=LanguageUtil.format(pageContext, \"moduleTitle.chapter\", new Object[]{i,mod.getTitle(themeDisplay.getLocale())})%>" 
					value="<%=mod.getModuleId() + \"_\" + i%>" />
				
<%
			}
%>
		</aui:select>
		<aui:button name="go" value="go" onClick="<%=renderResponse.getNamespace()+\"goToModule();\" %>"/>
<%			
	}else if(modules.size() == 1){
%>
		<aui:field-wrapper label="<%=LanguageUtil.format(pageContext, \"moduleTitle.chapter\", new Object[]{1,modules.get(0).getTitle(themeDisplay.getLocale())})%>"/>
<%		
	}
	%>
</div>
