
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@include file="../init.jsp" %>

<%@ page import="com.liferay.lms.model.Module" %>
<%@ page import="com.liferay.portlet.PortalPreferences" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>


<jsp:useBean id="addmoduleURL" class="java.lang.String" scope="request" />
<jsp:useBean id="moduleFilterURL" class="java.lang.String" scope="request" />
<jsp:useBean id="moduleFilter" class="java.lang.String" scope="request" />

<link rel="stylesheet" type="text/css" href="/Lms-portlet/css/Portlet_module.css" />

<liferay-ui:success key="module-prefs-success" message="module-prefs-success" />
<liferay-ui:success key="module-added-successfully" message="module-added-successfully" />
<liferay-ui:success key="module-deleted-successfully" message="module-deleted-successfully" />
<liferay-ui:success key="module-updated-successfully" message="module-updated-successfully" />
<liferay-ui:error key="module-error-deleting" message="module-error-deleting" />
<liferay-ui:error key="dependent-rows-exist-error-deleting" message="dependent-rows-exist-error-deleting" />
<script>
Liferay.provide(
        window,
        'closePopup',
        function(popupIdToClose) {
            var A = AUI();

            A.DialogManager.closeByChild('#' + popupIdToClose);
        },
        ['aui-base','aui-dialog','aui-dialog-iframe']
    );
Liferay.provide(
        window,
        'refreshPortlet',
        function() {

            <%-- refreshing the portlet [Liferay.Util.getOpener().] --%>
            var curPortletBoundaryId = '#p_p_id<portlet:namespace />';
            Liferay.Portlet.refresh(curPortletBoundaryId);
        },
        ['aui-dialog','aui-dialog-iframe']
    );
</script>
<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);

if((Boolean)request.getAttribute("hasAddPermission")&&actionEditing)
{
	
	String moduleaddpopup = "javascript:Liferay.Util.openWindow({dialog: {width: 960,modal:true}, id: 'editModule', title: 'Add', uri:'" + HtmlUtil.escapeURL(addmoduleURL) + "'});";
	%>
	

<div class="newitem">
<liferay-ui:icon image="add" label="true" message="new" url="<%=moduleaddpopup %>" />
</div>

<%
}
%>
