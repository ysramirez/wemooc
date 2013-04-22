<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.lms.asset.LearningActivityAssetRendererFactory"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="java.util.Arrays"%>
<%@ include file="/init.jsp" %>
<liferay-ui:panel-container >
<%
java.util.List<LearningActivity> activities = null;

	java.util.List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());

	for(Module theModule:modules)
	{

	activities = LearningActivityServiceUtil.getLearningActivitiesOfModule(theModule.getModuleId());


%>
<liferay-ui:panel id="<%=Long.toString(theModule.getModuleId()) %>" title="<%=theModule.getTitle(themeDisplay.getLocale()) %>" collapsible="true" extended="true" defaultState="collapsed">
<liferay-portlet:resourceURL var="exportURL" >
	<portlet:param name="action" value="export"/>
	<portlet:param name="moduleId" value="<%=Long.toString(theModule.getModuleId()) %>"/>
</liferay-portlet:resourceURL>
<liferay-ui:icon image="export" label="<%= true %>" message="offlinetaskactivity.csv.export" method="get" url="<%=exportURL%>" />
<table>
<tr>
<th>
<liferay-ui:message key="user" />
</th>
<%
for(LearningActivity learningActivity:activities)
{
	%>
	<th>
	<%=learningActivity.getTitle(themeDisplay.getLocale()) %>
	</th>
	<%
}
	%>
	</tr>
<%
for(User usuario:UserLocalServiceUtil.getGroupUsers(themeDisplay.getScopeGroupId()))
{
	String[] troles = {"courseTeacher", "Site Owner", "courseEditor", "Administrator", "Site Administrator"};
	List<String> teacherRoles = Arrays.asList(troles);
	
	boolean isTeacher=false; 
	for(Role role : RoleLocalServiceUtil.getUserGroupRoles(usuario.getUserId(), themeDisplay.getScopeGroupId())){
		if(teacherRoles.contains(role.getName())) {
			isTeacher=true;
			break;
		}
	}
	
	boolean isUserWatchingATeacher=permissionChecker.isOmniadmin();
	if(!isUserWatchingATeacher){
		for(Role role : RoleLocalServiceUtil.getUserGroupRoles(themeDisplay.getUser().getUserId(), themeDisplay.getScopeGroupId())){
			System.out.println(role.getName());
			if(teacherRoles.contains(role.getName())) {
				isUserWatchingATeacher=true;
				break;
			}
		}
	}
	
	if(!isTeacher){
		%>
		<liferay-portlet:renderURL var="userDetailsURL">
		<liferay-portlet:param name="jspPage" value="/html/gradebook/userdetails.jsp"></liferay-portlet:param>
		<liferay-portlet:param name="userId" value="<%=Long.toString(usuario.getUserId()) %>"></liferay-portlet:param>
		</liferay-portlet:renderURL>
		<tr>
		<td class="user">
		<liferay-ui:user-display userId="<%=usuario.getUserId() %>" url="<%=userDetailsURL %>"></liferay-ui:user-display>
		</td>
		<%
		for(LearningActivity learningActivity:activities)
		{
			long result=0;
			String status="not-started";
			if(LearningActivityResultLocalServiceUtil.existsLearningActivityResult(learningActivity.getActId(), usuario.getUserId()))
					{
				status="started";
						
					LearningActivityResult learningActivityResult=
						LearningActivityResultLocalServiceUtil.getByActIdAndUserId(learningActivity.getActId(), usuario.getUserId());
					result=learningActivityResult.getResult();
					if(learningActivityResult.getEndDate()!=null)
					{
						status="not-passed"	;
					}
					if(learningActivityResult.isPassed())
					{
						status="passed"	;
					}
					
					}
			%>
			
			<portlet:renderURL var="viewUrlPopGrades" windowState="<%= LiferayWindowState.POP_UP.toString() %>">   
				<portlet:param name="actId" value="<%=String.valueOf(learningActivity.getActId()) %>" />      
			    <portlet:param name="jspPage" value="/html/gradebook/popups/grades.jsp" />           
			</portlet:renderURL>
			<portlet:renderURL var="setGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
				<portlet:param name="ajaxAction" value="setGrades" />      
			   	<portlet:param name="jspPage" value="/html/gradebook/popups/grades.jsp" />           
			</portlet:renderURL>
			<script type="text/javascript">
			function <portlet:namespace />showPopupActivity(studentId, actId, actType) {
				
				var url = '/html/gradebook/popups/activity.jsp';
				AUI().use('aui-dialog','liferay-portlet-url', function(A){
					var renderUrl = Liferay.PortletURL.createRenderURL();							
					renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
					renderUrl.setPortletId('<%=portletDisplay.getId()%>');
					renderUrl.setParameter('actId', actId);
					renderUrl.setParameter('studentId', studentId);
					renderUrl.setParameter('actType', actType);
					renderUrl.setParameter('jspPage', url);

					window.<portlet:namespace />popupActivity = new A.Dialog({
						id:'<portlet:namespace />showPopupActivity',
			            title: '<liferay-ui:message key="coursestats.modulestats.activity" />',
			            centered: true,
			            modal: true,
			            width: 700,
			            height: 800,
			            after: {   
				          	close: function(event){ 
				          		document.location.reload();
			            	}
			            }
			        }).plug(A.Plugin.IO, {
			            uri: renderUrl.toString()
			        }).render();
					window.<portlet:namespace />popupActivity.show();   
				});
		    }
			
			function <portlet:namespace />doClosePopupActivity(){
			    AUI().use('aui-dialog', function(A) {
			    	window.<portlet:namespace />popupActivity.close();
			    });
			}
			
			function <portlet:namespace />showPopupGrades(studentId, actId) {

					AUI().use('aui-dialog','liferay-portlet-url', function(A){
						var renderUrl = Liferay.PortletURL.createRenderURL();							
						renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
						renderUrl.setPortletId('<%=portletDisplay.getId()%>');
						renderUrl.setParameter('actId', actId);
						renderUrl.setParameter('studentId', studentId);
						renderUrl.setParameter('jspPage', '/html/gradebook/popups/grades.jsp');

						window.<portlet:namespace />popupGrades = new A.Dialog({
							id:'<portlet:namespace />showPopupGrades',
				            title: '<liferay-ui:message key="offlinetaskactivity.set.grades" />',
				            centered: true,
				            modal: true,
				            width: 370,
				            height: 300,
				            after: {   
					          	close: function(event){ 
					          		document.location.reload();
				            	}
				            }
				        }).plug(A.Plugin.IO, {
				            uri: renderUrl.toString()
				        }).render();
						window.<portlet:namespace />popupGrades.show();   
					});
			    }
			    
				function <portlet:namespace />doClosePopupGrades(){
				    AUI().use('aui-dialog', function(A) {
				    	window.<portlet:namespace />popupGrades.close();
				    });
				}
				
				function <portlet:namespace />doSaveGrades(studentId, actId) {
			        AUI().use('aui-io-request','io-form', function(A) {
			            A.io.request('<%= setGradesURL %>', { 
			                method : 'POST', 
			                form: {
			                    id: '<portlet:namespace />fn_grades'
			                },
			                dataType : 'html', 
			                headers:{
			                	actId: actId,
			                	studentId: studentId
			                },
			                on : { 
			                    success : function() { 
			                    	A.one('.aui-dialog-bd').set('innerHTML',this.get('responseData'));				                    	
			                    } 
			                } 
			            });
			        });
			    }
				
				</script>
			
			<td>
				<%=result %>
				<%
				if(status.equals("passed")){%>
				 	<liferay-ui:icon image="checked"></liferay-ui:icon>
				<%} else if(status.equals("not-passed")){%>
				 	<liferay-ui:icon image="close"></liferay-ui:icon>
				<%} else if(status.equals("started")){%>
			 		<liferay-ui:icon image="unchecked"></liferay-ui:icon>
			 	<%}
				
	 			if(status.equals("passed") || status.equals("not-passed")){
	 				if(isUserWatchingATeacher){%>
			 			<liferay-ui:icon image="edit" url='<%="javascript:"+renderResponse.getNamespace() + "showPopupGrades("+Long.toString(usuario.getUserId())+","+String.valueOf(learningActivity.getActId())+");" %>' />
				 		<% String typesThatCanBeSeen = "0,3,6";
				 		if(typesThatCanBeSeen.contains(String.valueOf(learningActivity.getTypeId()))){
				 			%>
				 			<liferay-ui:icon image="view" url='<%="javascript:"+renderResponse.getNamespace() + "showPopupActivity("+Long.toString(usuario.getUserId())+","+String.valueOf(learningActivity.getActId())+","+String.valueOf(learningActivity.getTypeId())+");" %>'/>
				 		<%}
			  		}
	 			}%>
			</td>
		<%}%>
		</tr>
	<%}
}%>
</table>
</liferay-ui:panel>
<%}%>
</liferay-ui:panel-container>
