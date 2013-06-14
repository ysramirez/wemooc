
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.EvaluationAvgPortlet"%>
<%@page import="com.liferay.portal.service.ResourceBlockLocalServiceUtil"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portal.kernel.workflow.WorkflowConstants"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.liferay.portal.util.comparator.UserFirstNameComparator"%>
<%@page import="com.liferay.lms.OfflineActivity"%>
<%@page import="com.liferay.portal.kernel.dao.orm.CustomSQLParam"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
		boolean needPassPuntuation = courseEval.getNeedPassPuntuation(); 
		long passPuntuation = 5; // (needPassPuntuation)?courseEval.getPassPuntuation(course):0;		
		boolean needPassAllModules = false; //courseEval.getNeedPassAllModules();
		
		String passedStudentMessageKey="evaluationAvg.student.passed";
		String failedStudentMessageKey="evaluationAvg.student.failed";
		
		
		CourseResult result = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), themeDisplay.getUserId());

		
		Object  [] arguments=null;
		
		if(result!=null){	
			arguments =  new Object[]{result.getResult()};
		}
					
			boolean isTeacher=permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(), "VIEW_RESULTS");
					
		%>

			<div class="evaluationAvg view">
										
				<% if(isTeacher){ 
				
					JSONObject courseEvalModel = null;
					try{
						courseEvalModel = courseEval.getEvaluationModel(course);
					}
					catch(Throwable e){
						courseEvalModel = JSONFactoryUtil.createJSONObject();
					}

				%>

				<portlet:renderURL var="viewEvaluationsURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>     
		            <portlet:param name="jspPage" value="/html/evaluationAvg/popups/evaluations.jsp" />           
		        </portlet:renderURL>
					        		        
		        <portlet:renderURL var="setGradesURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">   
		        	<portlet:param name="<%=WebKeys.PORTLET_CONFIGURATOR_VISIBILITY %>" value="<%=StringPool.TRUE %>"/>
					<portlet:param name="ajaxAction" value="setGrades" />      
		            <portlet:param name="jspPage" value="/html/evaluationAvg/popups/grades.jsp" />           
		        </portlet:renderURL>
		        		        
				<script type="text/javascript">
			    <!--
					<% 
					   if(!courseEvalModel.has("firedDate")) {
					%>
      
				    function <portlet:namespace />showPopupEvaluations()
				    {
	
						AUI().use('aui-dialog','liferay-portlet-url', function(A){
							
							
							new A.Dialog({
								id:'<portlet:namespace />showPopupEvaluations',
					            title: '<%=LanguageUtil.format(pageContext, "evaluationAvg.evaluations", new Object[]{})%>',
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
					   if(courseEvalModel.has("evaluations")) {
					%>

				    function <portlet:namespace />calculateEvaluation()
				    {
						if(confirm('<liferay-ui:message key="evaluationAvg.calculate.confirm" />')){
							location.href='<portlet:actionURL name="updateCourse" />';
						}  	
				    }

					<% 
					   }}
					%>

				    function <portlet:namespace />showPopupGrades(userId)
				    {

						AUI().use('aui-dialog','liferay-portlet-url', function(A){
							var renderUrl = Liferay.PortletURL.createRenderURL();							
							renderUrl.setWindowState('<%= LiferayWindowState.EXCLUSIVE.toString() %>');
							renderUrl.setPortletId('<%=portletDisplay.getId()%>');
							renderUrl.setParameter('userId', userId);
							renderUrl.setParameter('jspPage', '/html/evaluationAvg/popups/grades.jsp');

							window.<portlet:namespace />popupGrades = new A.Dialog({
								id:'<portlet:namespace />showPopupGrades',
					            title: '<%=LanguageUtil.get(pageContext, "evaluationAvg.set.grade")%>',
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
				   if(!courseEvalModel.has("firedDate")) {
				%>
				
				<liferay-ui:icon
				image="add"
				label="<%= true %>"
				message="evaluationAvg.evaluation.configuration"
				url='<%="javascript:"+renderResponse.getNamespace() + "showPopupEvaluations();" %>'
				/>
				<% 
				   if(courseEvalModel.has("evaluations")) {
				%>
				<aui:button-row>
					<button name="Calculate" value="calculate" onclick="<portlet:namespace />calculateEvaluation();" type="button">
						<liferay-ui:message key="evaluationAvg.calculate" />
					</button>
				</aui:button-row>
						
				<% }}
				
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
							<aui:select label="evaluationAvg.status" name="gradeFilter" onchange='<%="document.getElementById(\'" + renderResponse.getNamespace() + "studentsearch\').submit();" %>'>
								<aui:option selected='<%= gradeFilter.equals("") %>' value=""><liferay-ui:message key="evaluationAvg.all" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("nocalification") %>' value="nocalification"><liferay-ui:message key="evaluationAvg.status.passed" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("passed") %>' value="passed"><liferay-ui:message key="evaluationAvg.passed" /></aui:option>
								<aui:option selected='<%= gradeFilter.equals("failed") %>' value="failed"><liferay-ui:message key="evaluationAvg.failed" /></aui:option>
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
						<% CourseResult courseResult = CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(course.getCourseId(), user.getUserId()); 
						   if(courseResult!=null) {	   
								   if(courseResult.getPassed()){
									   %><liferay-ui:message key="<%=passedStudentMessageKey %>"  arguments="<%=
											   new Object[]{courseResult.getResult(),passPuntuation} %>" /><%
								   }else {
									   %><liferay-ui:message key="<%=failedStudentMessageKey %>"  arguments="<%=
											   new Object[]{courseResult.getResult(),passPuntuation} %>" /><%
								   }
								   %>
								   <portlet:actionURL name="reCalculate" var="reCalculateURL">
								   		<portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"/>
								   </portlet:actionURL>
						            <p class="see-more">
									<a href="<%=reCalculateURL %>">
										<liferay-ui:message key="evaluationAvg.recalculate"/>
									</a>
									</p>
								     <p class="see-more">
									<a href="javascript:<portlet:namespace />showPopupGrades(<%=Long.toString(user.getUserId()) %>);">
										<liferay-ui:message key="evaluationAvg.set.grades"/>
									</a>
									</p>
								<%
							  
			               	}else{
								   %><liferay-ui:message key="evaluationAvg.student.without.qualification" /><% 
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
	<h2><liferay-ui:message key="evaluationAvg.result.title" /></h2>
	<p><liferay-ui:message key="evaluationAvg.result.youresult" /> <span class="destacado"><%= (arguments.length>0) ? arguments[0]+"%":"" %></span></p>
	<%
	if(result.isPassed()){
	%>
		<p class="nota_superado"><liferay-ui:message key="evaluationAvg.result.pass" /></p>
	<%
	}else{

		if(needPassPuntuation) {
	%>	
		<p class="nota_nosuperado"><liferay-ui:message key="evaluationAvg.result.notpass.notPassPuntuation" /></p>	
	<% 
		}else {
	%>
		<p class="nota_nosuperado"><liferay-ui:message key="evaluationAvg.result.notpass.passPuntuation"  arguments="<%=new Object[]{passPuntuation} %>" /></p>
	<%
		}
	}
	if (!result.getComments().trim().equals("")){ %>
	<p><liferay-ui:message key="evaluationAvg.result.teachercoment" /> <span class="destacado"><%=result.getComments() %></span></p>
	<% } 
}else {
%>
	<p class="nota_nocorregida"><liferay-ui:message key="evaluationAvg.not.qualificated.activity" /></p>
<% 
}	
}%>

</div>
			
</div>

