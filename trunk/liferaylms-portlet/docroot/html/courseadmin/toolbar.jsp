<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@include file="/init.jsp" %>
<%@page import="com.liferay.portal.service.permission.PortalPermissionUtil"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<% 
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view");
long courseId=ParamUtil.getLong(request, "courseId",0);
long roleId=ParamUtil.getLong(request, "roleId",0);
Course course=CourseLocalServiceUtil.getCourse(courseId);
long createdGroupId=course.getGroupCreatedId();


LmsPrefs prefs=LmsPrefsLocalServiceUtil.getLmsPrefs(themeDisplay.getCompanyId());
Role commmanager=RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER) ;
String teacherName=RoleLocalServiceUtil.getRole(prefs.getTeacherRole()).getTitle(locale);
String editorName=RoleLocalServiceUtil.getRole(prefs.getEditorRole()).getTitle(locale);
String tab="";
if(roleId==commmanager.getRoleId()){
	tab =  LanguageUtil.get(pageContext,"courseadmin.adminactions.students");
}else if(roleId==prefs.getEditorRole()){
	tab = editorName;
}else{
	tab = teacherName;
}
%>
<liferay-portlet:actionURL name="removeAll" var="removeAllURL">
		<portlet:param name="jspPage" value="/html/courseadmin/rolememberstab.jsp" /> 
		<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"></liferay-portlet:param>
		<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"></liferay-portlet:param>
		<liferay-portlet:param name="tabs1" value="<%=tab %>"></liferay-portlet:param>
	</liferay-portlet:actionURL>
<script type="text/javascript">
	function areyousure(){
		if(confirm('<liferay-ui:message key="are-you-sure-you-want-to-permanently-delete-the-selected-users" />')){
			window.location.href="<%= removeAllURL %>";
		}
	}
</script>
<portlet:renderURL var="importUsersURL"  windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="courseId" value="<%=ParamUtil.getString(renderRequest, \"courseId\") %>" /> 
	<portlet:param name="roleId" value="<%=Long.toString(roleId) %>" /> 
	<portlet:param name="jspPage" value="/html/courseadmin/popups/importUsers.jsp" /> 
</portlet:renderURL>

<aui:fieldset>
	<div class="bt_newuser">	
		<portlet:renderURL var="adduserURL">
			<portlet:param name="jspPage" value="/html/courseadmin/usersresults.jsp" />
			<liferay-portlet:param name="courseId" value="<%=Long.toString(courseId) %>"></liferay-portlet:param>
			<liferay-portlet:param name="roleId" value="<%=Long.toString(roleId) %>"></liferay-portlet:param>
		</portlet:renderURL>		
		<liferay-ui:icon
			image="add_user" 
			message="add-user"
			label="true"
			url="<%= adduserURL %>"
		/>
	</div>
		<liferay-ui:icon 
			image="leave" 
			message="delete-all-users"
			label="true"
			url="javascript:areyousure()"
		/>
<liferay-ui:icon-menu cssClass='bt_importexport' direction="down" extended="<%= false %>"  message="export-import" showWhenSingleIcon="<%= false %>">
	<div>
		<liferay-portlet:resourceURL var="exportURL" >
			<portlet:param name="action" value="export"/>
			<portlet:param name="groupId" value="<%=Long.toString(createdGroupId) %>"/>
			<portlet:param name="roleId" value="<%=Long.toString(roleId) %>"/>
		</liferay-portlet:resourceURL>
		<liferay-ui:icon 
			image="export" 
			label="<%= true %>" 
			message="courseadmin.exportuser.csv"
			method="get" 
			url="<%=exportURL%>" />
	</div>
	<div>
		<liferay-ui:icon
			image="add"
			label="<%= true %>"
			message="courseadmin.importuserrole"
			url='<%="javascript:"+renderResponse.getNamespace() + "showPopupImportUsers();" %>'
		/>
	</div>
</liferay-ui:icon-menu>
</aui:fieldset>

<script type="text/javascript">
<!--

    function <portlet:namespace />doImportUsers()
    {
        var uploadMessages = document.getElementById('<portlet:namespace />uploadMessages');
        if(uploadMessages){
			var importUsersDIV=document.getElementById('<portlet:namespace />import_frame').
								contentDocument.getElementById('<portlet:namespace />uploadMessages');
		
			if(importUsersDIV){
				uploadMessages.innerHTML=importUsersDIV.innerHTML;
			}
			else {
				uploadMessages.innerHTML='';
			}
        }
    }
    
	function <portlet:namespace/>showPopupImportUsers()
    {
		AUI().use('aui-dialog', function(A){
			new A.Dialog({
				id:'<portlet:namespace />showPopupImportUsers',
	            title: '<liferay-ui:message key="import" />',
	            modal: true,
	            centered: true,
	            resizable: true,
	            width: 650,
	            height: 350,
	            destroyOnClose: true,
	            after: {   
		          	close: function(event){ 
		          		Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0,
			          																   '<portlet:namespace />courseId':'<%=Long.toString(courseId) %>',
			          																   '<portlet:namespace />roleId':'<%=Long.toString(roleId) %>',
			          																   '<portlet:namespace />jspPage':'/html/courseadmin/rolememberstab.jsp'});	
	            	}
	            }
	        }).plug(A.Plugin.IO, {
	            uri: '<%=importUsersURL %>',
	            parseContent: true
	        }).render().show();   
		}); 

    }
    
//-->
</script>
