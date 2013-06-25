
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.service.ResourceBlockLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.lms.EvaluationAvgPortlet"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>

<%@ include file="/init.jsp" %>
<div class="container-activity">
<%
long actId = ParamUtil.getLong(request,"actId",0);

if(actId==0)
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	long typeId=learningActivity.getTypeId();
					
	if(typeId==8&&(!LearningActivityLocalServiceUtil.islocked(actId,themeDisplay.getUserId())||
			permissionChecker.hasPermission(learningActivity.getGroupId(),	LearningActivity.class.getName(), actId, ActionKeys.UPDATE)||
			permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")))
	{
		
		String passedStudentMessageKey="evaluationtaskactivity.student.passed";
		String failedStudentMessageKey="evaluationtaskactivity.student.failed";
				
		LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
		
		Object  [] arguments=null;
		
		if(result!=null){	
			arguments =  new Object[]{result.getResult()};
		}
					
			boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
					
		%>

			<div class="evaluationAvg view">
										
				<% if(isTeacher){ 
					boolean hasFiredDate=false;
					boolean hasPublishDate=false;
					boolean hasActivities=false;
					try{
						if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().length()!=0)) {	
							Element rootElement = SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement();
							hasFiredDate = rootElement.element("firedDate")!=null;
							hasPublishDate = rootElement.element("publishDate")!=null;
							hasActivities = rootElement.element("activities")!=null;
						}
					}
					catch(Throwable e){}

				%>

				<portlet:renderURL var="viewEvaluationsURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>     
		            <portlet:param name="jspPage" value="/html/evaluationtaskactivity/popups/activities.jsp" />           
		        </portlet:renderURL>
					        		        
		        <portlet:renderURL var="setGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>
					<portlet:param name="ajaxAction" value="setGrades" />      
		            <portlet:param name="jspPage" value="/html/evaluationtaskactivity/popups/grades.jsp" />           
		        </portlet:renderURL>
		        		        
				<script type="text/javascript">
			    <!--
					<% 
					   if(!hasFiredDate) {
					%>
      
				    function <portlet:namespace />showPopupActivities()
				    {
	
						AUI().use('aui-dialog','liferay-portlet-url', function(A){
							
							
							new A.Dialog({
								id:'<portlet:namespace />showPopupActivities',
					            title: '<%=LanguageUtil.format(pageContext, "evaluationtaskactivity.evaluations", new Object[]{})%>',
					            modal: true,
					            xy:A.one('#p_p_id<portlet:namespace />').getXY (),
					            resizable: true,
					            after: {   
						          	close: function(event){ 
						          		Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0,'<%=renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>':'<%=StringPool.TRUE %>'});	
					            	}
					            }
					        }).plug(A.Plugin.IO, {
					            uri: '<%=viewEvaluationsURL %>',
					            parseContent: true
					        }).render().show();   
						});
				    }

					<% 
					   if(hasActivities) {
					%>

				    function <portlet:namespace />calculateEvaluation()
				    {
				    	AUI().use('aui-dialog', function(A) {
					    	var dialog1 = new A.Dialog({
					    		title: '<liferay-ui:message key="evaluationtaskactivity.calculate" />',
					    		bodyContent: '<liferay-ui:message key="evaluationtaskactivity.calculate.confirm" />',
					    		height: 150,
					    		width: 300,
					    		modal: true,
					    		centered: true,
					    		buttons: [{
					    			label: '<liferay-ui:message key="ok" />',
					    			handler: function() {
					    				location.href='<portlet:actionURL name="update" />';
					    			}
					    		},
					    		{
					    			label: '<liferay-ui:message key="cancel" />',
					    			handler: function() {
					    				this.close();
					    			}
					    		}]
					    	}).render().show();
					    }); 	
				    }

					<% 
					   }}
					   else if (!hasPublishDate) {
					%>

				    function <portlet:namespace />publish()
				    {
				    	AUI().use('aui-dialog', function(A) {
					    	var dialog1 = new A.Dialog({
					    		title: '<liferay-ui:message key="evaluationtaskactivity.publish" />',
					    		bodyContent: '<liferay-ui:message key="evaluationtaskactivity.publish.confirm" />',
					    		height: 150,
					    		width: 300,
					    		modal: true,
					    		centered: true,
					    		buttons: [{
					    			label: '<liferay-ui:message key="ok" />',
					    			handler: function() {
					    				location.href='<portlet:actionURL name="publish" />';
					    			}
					    		},
					    		{
					    			label: '<liferay-ui:message key="cancel" />',
					    			handler: function() {
					    				this.close();
					    			}
					    		}]
					    	}).render().show();
					    });  	
				    }

					<%
					   }
			        %>

				    function <portlet:namespace />showPopupGrades(userId)
				    {

						AUI().use('aui-dialog','liferay-portlet-url', function(A){
							var renderUrl = Liferay.PortletURL.createRenderURL();							
							renderUrl.setWindowState('<%= LiferayWindowState.EXCLUSIVE.toString() %>');
							renderUrl.setPortletId('<%=portletDisplay.getId()%>');
							renderUrl.setParameter('userId', userId);
							renderUrl.setParameter('jspPage', '/html/evaluationtaskactivity/popups/grades.jsp');

							window.<portlet:namespace />popupGrades = new A.Dialog({
								id:'<portlet:namespace />showPopupGrades',
					            title: '<%=LanguageUtil.get(pageContext, "evaluationtaskactivity.set.grades")%>',
					            centered: true,
					            modal: true,
					            after: {   
						          	close: function(event){ 
						          		Liferay.Portlet.refresh(A.one('#p_p_id<portlet:namespace />'),{'p_t_lifecycle':0,'<%=renderResponse.getNamespace()+WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>':'<%=StringPool.TRUE %>'});	
					            	}
					            }
					        }).plug(A.Plugin.IO, {
					            uri: renderUrl.toString(),
					            parseContent: true
					        }).render().show();   
						});

				    }
			
				    //-->
				</script>
				
				<% 
				   if(!hasFiredDate) {
				%>
				
				<liferay-ui:icon
				image="add" cssClass="newitem2"
				label="<%= true %>"
				message="evaluationtaskactivity.evaluation.configuration"
				url='<%="javascript:"+renderResponse.getNamespace() + "showPopupActivities();" %>'
				/>
				<% 
				   if(hasActivities) {
				%>
				<aui:button-row>
					<button name="Calculate" value="calculate" onclick="<portlet:namespace />calculateEvaluation();" type="button">
						<liferay-ui:message key="evaluationtaskactivity.calculate" />
					</button>
				</aui:button-row>
						
				<% }}
				   else if(!hasPublishDate) {
				%>
				<aui:button-row>
					<button name="publish" value="publish" onclick="<portlet:namespace />publish();" type="button">
						<liferay-ui:message key="evaluationtaskactivity.publish" />
					</button>
				</aui:button-row>
						
				<% }
				
				String criteria = ParamUtil.get(request,"criteria",StringPool.BLANK);
				String gradeFilter = ParamUtil.get(request,"gradeFilter",StringPool.BLANK);
				
				PortletURL portletURL = renderResponse.createRenderURL();
				portletURL.setParameter("jspPage","/html/evaluationAvg/view.jsp");
				portletURL.setParameter("criteria", criteria); 
				portletURL.setParameter("gradeFilter", gradeFilter);
				
				%>
				
				<liferay-portlet:renderURL var="returnurl" >
					<liferay-portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>
				</liferay-portlet:renderURL>
				
				<h5><liferay-ui:message key="studentsearch"/></h5>
				<aui:form name="studentsearch" action="<%=returnurl %>" method="post">
					<aui:fieldset>
						<aui:column>
							<aui:input label="studentsearch.text.criteria" name="criteria" size="25" value="<%=criteria %>" />	
						</aui:column>	
						<aui:column>
							<aui:select label="evaluationtaskactivity.status" name="gradeFilter" onchange='<%="document.getElementById(\'" + renderResponse.getNamespace() + "studentsearch\').submit();" %>'>
								<aui:option selected='<%= gradeFilter.equals("") %>' value=""><liferay-ui:message key="evaluationtaskactivity.all" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("nocalification") %>' value="nocalification"><liferay-ui:message key="evaluationtaskactivity.status.passed" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("passed") %>' value="passed"><liferay-ui:message key="evaluationtaskactivity.passed" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("failed") %>' value="failed"><liferay-ui:message key="evaluationtaskactivity.failed" /></aui:option>
							</aui:select>
						</aui:column>	
						<aui:column>
							<aui:button name="searchUsers" value="search" type="submit" />
						</aui:column>
					</aui:fieldset>
				</aui:form>
				
					
					<liferay-ui:search-container iteratorURL="<%=portletURL%>" emptyResultsMessage="there-are-no-results" delta="10" deltaConfigurable="true">

				   	<liferay-ui:search-container-results>
						<%
							OrderByComparator obc = new UserFirstNameComparator(true);
							LinkedHashMap<String,Object> params = new LinkedHashMap<String,Object>();
							params.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
												
							if(gradeFilter.equals("passed")) {
								params.put("passed",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_PASSED_SQL,course.getCourseId()));
							}
							else {
								if(gradeFilter.equals("failed")) {
									params.put("failed",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_FAIL_SQL,course.getCourseId()));
								} else {
									if (gradeFilter.equals("nocalification")) {
										params.put("nocalification",new CustomSQLParam(EvaluationAvgPortlet.COURSE_RESULT_NO_CALIFICATION_SQL,course.getCourseId()));
									}
								}
							}
							
							if ((GetterUtil.getInteger(PropsUtil.get(PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM))==6)&&(!ResourceBlockLocalServiceUtil.isSupported("com.liferay.lms.model"))){		
								
								params.put("notTeacher",new CustomSQLParam(EvaluationAvgPortlet.NOT_TEACHER_SQL,themeDisplay.getScopeGroupId()));
								List<User> userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_ANY, params, searchContainer.getStart(), searchContainer.getEnd(), obc);
								int userCount = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria,  WorkflowConstants.STATUS_ANY, params);
								pageContext.setAttribute("results", userListPage);
							    pageContext.setAttribute("total", userCount);
							    
							}
							else{
						
								List<User> userListsOfCourse = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, WorkflowConstants.STATUS_ANY, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
								List<User> userLists =  new ArrayList<User>(userListsOfCourse.size());
								
								for(User userOfCourse:userListsOfCourse){							
									if(!PermissionCheckerFactoryUtil.create(userOfCourse).hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS")){
										userLists.add(userOfCourse);
									}
								}	
								
								pageContext.setAttribute("results", ListUtil.subList(userLists, searchContainer.getStart(), searchContainer.getEnd()));
							    pageContext.setAttribute("total", userLists.size());	
							}
															

						%>
					</liferay-ui:search-container-results>
					
					<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="user">
					<liferay-ui:search-container-column-text name="name">
						<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
					</liferay-ui:search-container-column-text>
					<liferay-ui:search-container-column-text name="calification">
						<% 
						   LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, user.getUserId());
						   if(learningActivityResult!=null) {	   
								   if(learningActivityResult.getPassed()){
									   %><liferay-ui:message key="<%=passedStudentMessageKey %>"  arguments="<%=
											   new Object[]{learningActivityResult.getResult(),learningActivity.getPasspuntuation()} %>" /><%
								   }else {
									   %><liferay-ui:message key="<%=failedStudentMessageKey %>"  arguments="<%=
											   new Object[]{learningActivityResult.getResult(),learningActivity.getPasspuntuation()} %>" /><%
								   }
								   
								   if(!hasPublishDate) {
								   %>
								   <portlet:actionURL name="reCalculate" var="reCalculateURL">
								   		<portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
								   </portlet:actionURL>
						            <p class="see-more">
									<a onClick="AUI().use('aui-dialog', function(A) {
											    	var dialog1 = new A.Dialog({
											    		title: '<liferay-ui:message key="evaluationtaskactivity.recalculate" />',
											    		bodyContent: '<liferay-ui:message key="evaluationtaskactivity.recalculate.confirm" />',
											    		height: 150,
											    		width: 300,
											    		modal: true,
											    		centered: true,
											    		buttons: [{
											    			label: '<liferay-ui:message key="ok" />',
											    			handler: function() {
											    				location.href='<%=reCalculateURL %>';
											    			}
											    		},
											    		{
											    			label: '<liferay-ui:message key="cancel" />',
											    			handler: function() {
											    				this.close();
											    			}
											    		}]
											    	}).render().show();
											    });">
										<liferay-ui:message key="evaluationtaskactivity.recalculate"/>
									</a>
									</p>
								     <p class="see-more">
									<a href="javascript:<portlet:namespace />showPopupGrades(<%=Long.toString(user.getUserId()) %>);">
										<liferay-ui:message key="evaluationtaskactivity.set.grades"/>
									</a>
									</p>
								<%
								   }
							  
			               	}else{
								   %><liferay-ui:message key="evaluationtaskactivity.student.without.qualification" /><% 
							}%>

					</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>
					
				 	<liferay-ui:search-iterator />
				 	
				</liferay-ui:search-container>
				
				
				<% } %>	
				
				<div class="nota"> 

<%
	if(!isTeacher){ 
	if (result!=null){ %>
	<h2 class="description-title"><liferay-ui:message key="evaluationtaskactivity.result.title" /></h2>
	<p><liferay-ui:message key="evaluationtaskactivity.result.youresult" /> <span class="destacado"><%= (arguments.length>0) ? arguments[0]+"%":"" %></span></p>
	<%
	if(LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId())){
	%>
		<p class="nota_superado"><liferay-ui:message key="evaluationtaskactivity.result.pass" arguments="<%=new Object[]{result.getResult(),learningActivity.getPasspuntuation()} %>" /></p>
	<%
	}else{
	%>
		<p class="nota_nosuperado"><liferay-ui:message key="evaluationtaskactivity.result.notpass.passPuntuation"  arguments="<%=new Object[]{result.getResult(),learningActivity.getPasspuntuation()} %>" /></p>
	<%
	}
	if (!result.getComments().trim().equals("")){ %>
	<p><liferay-ui:message key="evaluationtaskactivity.result.teachercoment" /> 
	   <div class="activity-comments"><%=result.getComments() %></div></p>
	<% } 
}else {
%>
	<p class="nota_nocorregida"><liferay-ui:message key="evaluationtaskactivity.not.qualificated.activity" /></p>
<% 
}}}}

%>

</div>
			
</div>

</div>