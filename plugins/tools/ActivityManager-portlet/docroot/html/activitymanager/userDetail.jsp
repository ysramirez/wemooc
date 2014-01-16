<%@page import="com.liferay.portal.kernel.dao.search.RowChecker"%>
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

<portlet:actionURL var="fixUserTries" name="fixUser">
	<portlet:param name="action" value="deleteTries"/>
	<portlet:param name="id" value="${la.actId}"/>
</portlet:actionURL>
<script type="text/javascript">
	var urldeletetries = '${fixUserTries}';
	function deleteTries(id){
		if(confirm('<liferay-ui:message key="actmanager.confirm-delete" />')){
			window.location.href = urldeletetries+"&userid="+id;
		}
	}
</script>
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
								useriter.setEmailAddressVerified(mr.getPassed());
							}else{
								useriter.setOpenId("-");
							}
							//ActResult
							if(lar!=null){
								useriter.setComments(String.valueOf(lar.getResult()));
								useriter.setCachedModel(lar.getPassed());
							}else{
								useriter.setComments("-");
							}
							uusers.add(useriter);
						}
					}catch(Exception e){e.printStackTrace();}
				}
				
				pageContext.setAttribute("results", uusers);
				pageContext.setAttribute("total", users.length);
			%>
		</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.portal.model.User" keyProperty="userId" modelVar="usert">
			<liferay-ui:search-container-column-text name="full-name" >
				<%=usert.getFullName() %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="actmanager.act-result" property="cachedModel" align="center"/>
			<liferay-ui:search-container-column-text name="actmanager.act-note">
				<%=usert.getComments() %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="actmanager.mod-result" property="emailAddressVerified" align="center"/>
			<liferay-ui:search-container-column-text name="actmanager.mod-note">
				<%=usert.getOpenId() %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text>
				<liferay-util:include page="/html/activitymanager/actions/usermodule_actions_${la.typeId}.jsp" servletContext="<%=this.getServletContext() %>">
					<liferay-util:param name="userId" value="<%=String.valueOf(usert.getUserId()) %>" />
				</liferay-util:include>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</c:if>