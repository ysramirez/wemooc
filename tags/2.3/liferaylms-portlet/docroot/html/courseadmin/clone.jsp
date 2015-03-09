<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.util.Time"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ include file="/init.jsp" %>	

<%
	String groupId = request.getParameter("groupId");
	Group groupObj = GroupLocalServiceUtil.getGroup(Long.valueOf(groupId));
	Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(Long.parseLong(groupId));
	
	SimpleDateFormat formatDay = new SimpleDateFormat("dd");
	formatDay.setTimeZone(timeZone);
	SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
	formatMonth.setTimeZone(timeZone);
	SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
	formatYear.setTimeZone(timeZone);
	SimpleDateFormat formatHour = new SimpleDateFormat("HH");
	formatHour.setTimeZone(timeZone);
	SimpleDateFormat formatMin = new SimpleDateFormat("mm");
	formatMin.setTimeZone(timeZone);
	Date today=new Date(System.currentTimeMillis());
	
	int startDay=Integer.parseInt(formatDay.format(today));
	int startMonth=Integer.parseInt(formatMonth.format(today))-1;
	int startYear=Integer.parseInt(formatYear.format(today));
	int startHour=Integer.parseInt(formatHour.format(today));
	int startMin=Integer.parseInt(formatMin.format(today));
	
	int endDay=Integer.parseInt(formatDay.format(today));
	int endMonth=Integer.parseInt(formatMonth.format(today))-1;
	int endYear=Integer.parseInt(formatYear.format(today))+1;
	int endHour=Integer.parseInt(formatHour.format(today));
	int endMin=Integer.parseInt(formatMin.format(today));
%>

<liferay-portlet:renderURL var="backURL"></liferay-portlet:renderURL>
<liferay-ui:header title="<%= course != null ? course.getTitle(themeDisplay.getLocale()) : \"course\" %>" backURL="<%=backURL %>"></liferay-ui:header>

<portlet:actionURL name="cloneCourse" var="cloneCourseURL">
	<portlet:param name="groupId" value="<%= groupId %>" />
</portlet:actionURL>
	
<aui:form name="form" action="<%=cloneCourseURL%>" method="post">

	<aui:input type="text" name="newCourseName" value="<%=groupObj.getName()+\"_\"+Time.getShortTimestamp() %>" label="courseadmin.clone.newcoursename" size="50" helpMessage="courseadmin.clone.newcoursename.help"/>
				
	<aui:field-wrapper label="start-course-date">
		<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"  dayParam="startDay" monthParam="startMon"
				 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
		<liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>"></liferay-ui:input-time>
	</aui:field-wrapper>
	<aui:field-wrapper label="end-course-date">
		<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon"
				 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
		 <liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>"></liferay-ui:input-time></br>
	</aui:field-wrapper>				
				
	<aui:button-row>
		<aui:button type="submit" value="clone" />
	</aui:button-row>
</aui:form>