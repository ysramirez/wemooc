
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

				<h2><%=course.getTitle(themeDisplay.getLocale()) %></h2>
										
				<% if(isTeacher){ %>
				
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


				    function <portlet:namespace />showPopupGrades(studentId)
				    {

						AUI().use('aui-dialog','liferay-portlet-url', function(A){
							var renderUrl = Liferay.PortletURL.createRenderURL();							
							renderUrl.setWindowState('<%= LiferayWindowState.POP_UP.toString() %>');
							renderUrl.setPortletId('<%=portletDisplay.getId()%>');
							renderUrl.setParameter('studentId', studentId);
							renderUrl.setParameter('jspPage', '/html/evaluationAvg/popups/grades.jsp');

							window.<portlet:namespace />popupGrades = new A.Dialog({
								id:'<portlet:namespace />showPopupGrades',
					            title: '<%=LanguageUtil.format(pageContext, "evaluationAvg.set.grades", new Object[]{})%>',
					            centered: true,
					            modal: true,
					            width: 1000,
					            height: 750,
					            after: {   
						          	close: function(event){ 
						          		document.getElementById('<portlet:namespace />studentsearch').submit();
					            	}
					            }
					        }).plug(A.Plugin.IO, {
					            uri: renderUrl.toString(),
					            parseContent: true
					        }).render();
							window.<portlet:namespace />popupGrades.show();   
						});
				    }

				    function <portlet:namespace />doClosePopupGrades()
				    {
				        AUI().use('aui-dialog', function(A) {
				        	A.DialogManager.closeByChild('#<portlet:namespace />showPopupGrades');
				        });
				    }

				    function <portlet:namespace />doSaveGrades()
				    {
				        AUI().use('aui-io-request','io-form', function(A) {
				            A.io.request('<%= setGradesURL %>', { 
				                method : 'POST', 
				                form: {
				                    id: '<portlet:namespace />fn_grades'
				                },
				                dataType : 'html', 
				                on : { 
				                    success : function() { 
				                    	A.one('.aui-dialog-bd form').set('innerHTML',A.Node.create('<div>'+this.get('responseData')+'</div>').one('form').get('innerHTML'));	
				                    	createValidator();			                    	
				                    } 
				                } 
				            });
				        });
				    }
			
				    //-->
				</script>
				
				<aui:button-row>
					<button name="Calculate" value="calculate" onclick="<portlet:namespace />showPopupEvaluations();" type="button">
						<liferay-ui:message key="evaluationAvg.calculate" />
					</button>
				</aui:button-row>

				<liferay-ui:icon
				image="add"
				label="<%= true %>"
				message="evaluationAvg.import.grades"
				url='<%="javascript:"+renderResponse.getNamespace() + "showPopupImportGrades();" %>'
				/>
				<% } %>
				<h5><liferay-ui:message key="evaluationAvg.description" />  </h5>
				<p><%=course.getDescription(themeDisplay.getLocale()) %></p>
				
				
				<% if(isTeacher){ 
					String criteria = request.getParameter("criteria");
					String gradeFilter = request.getParameter("gradeFilter");

					if (criteria == null) criteria = "";	
					if (gradeFilter == null) gradeFilter = "";	
					
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
							  
			               	}else{
								   %><liferay-ui:message key="evaluationAvg.student.without.qualification" /><% 
							}%>
			            <p class="see-more">
							<a href="javascript:<portlet:namespace />showPopupGrades(<%=Long.toString(user.getUserId()) %>);">
								<liferay-ui:message key="evaluationAvg.set.grades"/>
							</a>
						</p>
					</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>
					
				 	<liferay-ui:search-iterator />
				 	
				</liferay-ui:search-container>
				
				
				<% } %>	
				
				<div class="nota"> 

<%if (result!=null){ %>
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
	
}%>

</div>
			
</div>

