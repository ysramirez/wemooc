
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@include file="../init.jsp" %>

<%@ page import="com.liferay.lms.model.Module" %>
<%@ page import="com.liferay.portlet.PortalPreferences" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.util.JavaScriptUtil"%>
<%@ page import="com.liferay.portal.model.PortletConstants"%>


<jsp:useBean id="addmoduleURL" class="java.lang.String" scope="request" />
<jsp:useBean id="moduleFilterURL" class="java.lang.String" scope="request" />
<jsp:useBean id="moduleFilter" class="java.lang.String" scope="request" />

<%--liferay-ui:success key="module-prefs-success" message="module-prefs-success" />
<liferay-ui:success key="module-added-successfully" message="module-added-successfully" />
<liferay-ui:success key="module-deleted-successfully" message="module-deleted-successfully" />
<liferay-ui:success key="module-updated-successfully" message="module-updated-successfully" />
<liferay-ui:error key="module-error-deleting" message="module-error-deleting" />
<liferay-ui:error key="dependent-rows-exist-error-deleting" message="dependent-rows-exist-error-deleting" /--%>
<%
long moduleId=ParamUtil.getLong(request,"moduleId",0);
boolean actionEditing=ParamUtil.getBoolean(request,"actionEditing",false);

if((Boolean)request.getAttribute("hasAddPermission")/*&&actionEditing*/)
{

%>
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

Liferay.provide(
        window,
        '<portlet:namespace />openPopup',
        function() {
        	var A = AUI();

			new A.Dialog(
				{
		    		id: 'editModule', 
					title: 'Add',
	    			destroyOnClose: true,
	    			width: 750,
	    			modal:true,
	    			x:50,
	    			y:50,
	    			on: {
		    			close: function(evt){
							var lmsactivitieslistPortlet=A.one('#p_p_id<%=PortalUtil.getJsSafePortletId(StringPool.UNDERLINE+"lmsactivitieslist"+
									PortletConstants.WAR_SEPARATOR+portletConfig.getPortletContext().getPortletContextName())+StringPool.UNDERLINE %>');
							if(lmsactivitieslistPortlet!=null) {
								Liferay.Portlet.refresh(lmsactivitieslistPortlet);
							}
							Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'));		
						}
					}

				}
			).plug(
				A.Plugin.DialogIframe,
				{
					uri: '<%=JavaScriptUtil.markupToStringLiteral(addmoduleURL)%>'
				}
			).render().show();

        },
        ['aui-dialog','aui-dialog-iframe']
    );
</script>
<div class="newitem">
	<liferay-ui:icon image="add" label="true" message="new" url="<%= \"javascript:\"+renderResponse.getNamespace()+\"openPopup()\" %>" />
</div>

<% 
}
%>
