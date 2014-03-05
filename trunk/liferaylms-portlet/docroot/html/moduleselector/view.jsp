<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ include file="/init.jsp"%>

<script type="text/javascript">
			
Liferay.provide(
        window,
        '<portlet:namespace />goToModule',
        function() {
        	
        },
        ['liferay-portlet-url']
    );
	
</script>

<%
/*renderUrl.setParameter('actionEditingDetails', true);*/
	long moduleId = ParamUtil.getLong(request, "moduleId", 0);
	
	if(moduleId > 0){
		List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
		if(modules.size()>0){
			int i=0;
%>
			<aui:select name="moduleSelect" inlineLabel="true" label="">
<%
				for(Module mod:modules){
					i++;
%>
					<aui:option label="<%=LanguageUtil.format(pageContext, \"moduleTitle.chapter\", new Object[]{i,mod.getTitle(themeDisplay.getLocale())})%>" 
					value="<%=mod.getModuleId() %>"/>
<%
				}
%>
			</aui:select>
			<aui:button name="go" value="go" onClick="<%=renderResponse.getNamespace()+\"goToModule();\" %>"/>
<%			
		}
	}
%>