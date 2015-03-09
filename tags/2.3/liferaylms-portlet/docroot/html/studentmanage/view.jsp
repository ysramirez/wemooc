<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Team"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.model.CourseResult"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@include file="/init.jsp" %>

<%
	String criteria = request.getParameter("criteria");
	long teamId=ParamUtil.getLong(request, "teamId",0);

	if (criteria == null) criteria = "";	
	
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("jspPage","/html/studentmanage/view.jsp");
	portletURL.setParameter("criteria", criteria); 
	portletURL.setParameter("teamId", Long.toString(teamId)); 
	
	Course course=CourseLocalServiceUtil.getCourseByGroupCreatedId(themeDisplay.getScopeGroupId());
	long courseId=course.getCourseId();
	java.util.List<Team> userTeams=TeamLocalServiceUtil.getUserTeams(themeDisplay.getUserId(), themeDisplay.getScopeGroupId());
	
	Team theTeam=null;
	boolean hasNullTeam=false;
	if(teamId>0 && (TeamLocalServiceUtil.hasUserTeam(themeDisplay.getUserId(), teamId)||userTeams.size()==0))
	{		
		theTeam=TeamLocalServiceUtil.fetchTeam(teamId);	
	}
	else
	{
		if(userTeams!=null&& userTeams.size()>0)
		{
			theTeam=userTeams.get(0);	
			teamId=theTeam.getTeamId();
		}
	}
	if(userTeams.size()==0)
	{
		userTeams=TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId());
		hasNullTeam=true;
	}

%>

<liferay-portlet:renderURL var="returnurl">
<liferay-portlet:param name="jspPage" value="/html/studentmanage/view.jsp"></liferay-portlet:param>

			
</liferay-portlet:renderURL>
<div class="student_search"> 

	<portlet:renderURL var="buscarURL">
		<portlet:param name="jspPage" value="/html/studentmanage/view.jsp"></portlet:param>
	</portlet:renderURL>
<%if(theTeam==null&&(userTeams==null||userTeams.size()==0))
{
	%>
	<aui:form name="studentsearch" action="<%=buscarURL %>" method="post">
		<aui:fieldset>
			<aui:column>
				<aui:input label="studentsearch.criteria" name="criteria" size="20" value="<%=criteria %>" />	
			</aui:column>	
			<aui:column cssClass="search_lms_button">
				<aui:button-row>
					<aui:button name="searchUsers" value="search" type="submit" />
				</aui:button-row>
			</aui:column>	
		</aui:fieldset>
	</aui:form>
	<%
}
else	
{
%>
	<aui:form name="studentsearch" action="<%=buscarURL %>" method="post">
		<aui:fieldset>
			<aui:column>
				<aui:input label="studentsearch.criteria" name="criteria" size="20" value="<%=criteria %>" />	
				<aui:select name="teamId" id="teamIdSelect" label="team">
				<%
				if(hasNullTeam)
				{
					if(teamId==0)
					{
					%>
					<aui:option label="--" value="0" selected="true"></aui:option>
					<%
					}
					else
					{
						%>
						<aui:option label="--" value="0"></aui:option>
						<%
					}
				}
				for(Team team:userTeams)
				{
					if(teamId==team.getTeamId())
					{
					%>
					<aui:option label="<%=team.getName() %>" value="<%=team.getTeamId() %>" selected="true"></aui:option>
					<%
					}
					else
					{
					%>
					<aui:option label="<%=team.getName() %>" value="<%=team.getTeamId() %>"></aui:option>
					<%
					}
				}
				%>
				</aui:select>
			</aui:column>	
			<aui:button-row>
				<aui:button name="searchUsers" value="select" type="submit" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
	<%if(theTeam!=null)
	{ %>
	<liferay-ui:header title="<%=theTeam.getName() %>" showBackURL="false"></liferay-ui:header>
	
<%
	}
}
	%>
	<liferay-ui:search-container iteratorURL="<%=portletURL%>" emptyResultsMessage="there-are-no-results" delta="10" deltaConfigurable="true">

	   	<liferay-ui:search-container-results>
			<%
			if(theTeam==null)
			{
				String middleName = null;
		
				LinkedHashMap<String,Object> params = new LinkedHashMap<String,Object>();
				params.put("usersGroups", new Long(themeDisplay.getScopeGroupId()));
				
				OrderByComparator obc = null;
				
				List<User> userListPage = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, 0, params, searchContainer.getStart(), searchContainer.getEnd(), obc);
				int userCount = UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria, 0, params);
						
				pageContext.setAttribute("results", userListPage);
			    pageContext.setAttribute("total", userCount);
			}
			else
			{
				LinkedHashMap userParams = new LinkedHashMap();
				userParams.put("usersGroups", theTeam.getGroupId());
				userParams.put("usersTeams", theTeam.getTeamId());
				OrderByComparator obc = null;
				total=UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), criteria, 0, userParams);
				results  = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), criteria, 0, userParams, searchContainer.getStart(), searchContainer.getEnd(), obc);
				pageContext.setAttribute("results", results);
			    pageContext.setAttribute("total", total);
			}
			    	
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="user">
		<liferay-ui:search-container-column-text>
			<liferay-ui:user-display userId="<%=user.getUserId() %>"></liferay-ui:user-display>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text>
			<%			
			CourseResult courseResult=CourseResultLocalServiceUtil.getCourseResultByCourseAndUser(courseId, user.getUserId());
			String result="-";
			String status="not-started";
			if(courseResult!=null)
			{
				status="started";
				result=Long.toString(courseResult.getResult());
				if(courseResult.getPassedDate()!=null){
					status="not-passed"	;
				}
				if(courseResult.isPassed()){
					status="passed"	;
				}
			}
			%>
			<%=result %>
			<% if(status.equals("passed")){%>
							 	<liferay-ui:icon image="checked" alt="passed"></liferay-ui:icon>
							<%} else if(status.equals("not-passed")){%>
							 	<liferay-ui:icon image="close" alt="not-passed"></liferay-ui:icon>
							<%} else if(status.equals("started")){%>
						 		<liferay-ui:icon image="unchecked" alt="unchecked"></liferay-ui:icon>
						 	<%}%>
		</liferay-ui:search-container-column-text>
		<liferay-ui:search-container-column-text>
			<liferay-portlet:renderURL var="viewGradeURL">
			<liferay-portlet:param name="jspPage" value="/html/gradebook/userdetails.jsp"></liferay-portlet:param>
			<liferay-portlet:param name="userId" value="<%=Long.toString(user.getUserId()) %>"></liferay-portlet:param>
			<liferay-portlet:param name="returnurl" value="<%=returnurl %>"></liferay-portlet:param>
			
			</liferay-portlet:renderURL>
			<a href="<%=viewGradeURL %>" ><liferay-ui:message key="searchresults.viewresults" /></a>
		</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		
	 	<liferay-ui:search-iterator />
	 	
	</liferay-ui:search-container>
	
</div>
