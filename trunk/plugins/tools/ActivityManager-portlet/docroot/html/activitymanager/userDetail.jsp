<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="com.liferay.lms.model.ModuleResult"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.ModuleResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.persistence.LearningActivityResultUtil"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@include file="/init.jsp" %>
<%@ include file="/html/head.jsp" %>

<c:if test="${empty users}"><liferay-ui:message key="there-are-no-results" />
</c:if>
<c:if test="${not empty users}">
	<% 
		PortletURL filter = renderResponse.createRenderURL();
		long[] users = (long[])request.getAttribute("users");
		Course course = (Course)request.getAttribute("course");
		LearningActivity la = (LearningActivity)request.getAttribute("la");
	
		filter.setParameter("course", String.valueOf(course.getCourseId()));
		filter.setParameter("la", String.valueOf(la.getActId()));
	%>
	<liferay-ui:search-container iteratorURL="<%=filter %>" emptyResultsMessage="there-are-no-courses" delta="50">
		<liferay-ui:search-container-results>
			<%

				int containerStart;
				int containerEnd;
				try {
					containerStart = ParamUtil.getInteger(request, "containerStart");
					containerEnd = ParamUtil.getInteger(request, "containerEnd");
				} catch (Exception e) {
					containerStart = searchContainer.getStart();
					containerEnd = searchContainer.getEnd();
				}
				if (containerStart <=0) {
					containerStart = searchContainer.getStart();
					containerEnd = searchContainer.getEnd();
				}
			
				List<Course> orderedCourses = new ArrayList<Course>();

				List<Long> lusers = ListUtil.fromArray(ArrayUtils.toObject(users));
				lusers = ListUtil.subList(lusers, containerStart, containerEnd);
				
				List<User> uusers = new ArrayList<User>();
				for(Long userId : lusers){
					try{
						User useriter = UserLocalServiceUtil.getUser(userId);
						if(useriter!=null){
							LearningActivityResult lar = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(la.getActId(), userId);
							ModuleResult mr = ModuleResultLocalServiceUtil.getByModuleAndUser(la.getModuleId(), userId);
							//ModuleResult
							if(lar!=null){
								useriter.setOpenId(String.valueOf(mr.getResult()));
							}else{
								useriter.setOpenId("-");
							}
							//ActResult
							if(lar!=null){
								useriter.setComments(String.valueOf(lar.getResult()));
							}else{
								useriter.setComments("-");
							}
							uusers.add(useriter);
						}
					}catch(Exception e){e.printStackTrace();}
				}
				
				System.out.println(uusers.size());
				pageContext.setAttribute("results", uusers);
				pageContext.setAttribute("total", users.length);
			%>
		</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usert">
			<liferay-ui:search-container-column-text name="fullName" >
				<%=usert.getFullName() %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="Activity note">
				<%=usert.getComments() %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="Module note">
				<%=usert.getOpenId() %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text>
				<liferay-ui:icon-menu>
					<liferay-ui:icon image="close" message="actmanager.recalculate-module" onClick='recalculateUser(<%=usert.getUserId() %>)' url="#"  />
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>