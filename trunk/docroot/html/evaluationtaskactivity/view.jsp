<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@ include file="/init.jsp" %>

<%
	long actId = ParamUtil.getLong(request,"actId",0);
	
	if(actId==0)
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		long typeId=activity.getTypeId();
		
		LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
		Object  [] arguments=null;
		
		if(result!=null){	
			arguments =  new Object[]{result.getResult()};
		}
		
		if(typeId==5&&(!LearningActivityLocalServiceUtil.islocked(actId,themeDisplay.getUserId())||
				permissionChecker.hasPermission(
				activity.getGroupId(),
				LearningActivity.class.getName(),
				actId, ActionKeys.UPDATE)||permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"ACCESSLOCK")))
		{

			
			boolean isTeacher=false;
			for(Role role : themeDisplay.getUser().getRoles()){
				if(("courseTeacher".equals(role.getName()))||(RoleConstants.ADMINISTRATOR.equals(role.getName()))) {
					isTeacher=true;
					break;
				}
			}
		%>

			<div class="evaluationtaskactivity view">

			
			</div>
			<%
		}
	}
%>