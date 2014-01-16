
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>

<%@include file="/init.jsp" %>
<%@ include file="/html/head.jsp" %>

<portlet:actionURL var="fixAllURL" name="fix">
	<portlet:param name="action" value="all"/>
</portlet:actionURL>

<portlet:actionURL var="fixNotPassedURL" name="fix">
	<portlet:param name="action" value="notpassed"/>
</portlet:actionURL>

<portlet:actionURL var="fixUserURL" name="users">
	<portlet:param name="course" value="${course.courseId}"/>
</portlet:actionURL>

<script type="text/javascript">
	var urlall = '${fixAllURL}';
	var urlnotpassed = '${fixNotPassedURL}';
	var urluser = '${fixUserURL}';
	function removeAll(id){
		if(confirm('<liferay-ui:message key="actmanager.confirm-delete" />')){
			window.location.href = urlall+"&id="+id;
		}
	}
	function removeNotPassed(id){
		if(confirm('<liferay-ui:message key="actmanager.confirm-delete" />')){
			window.location.href = urlnotpassed+"&id="+id;
		}
	}
	function removeUser(id){
			window.location.href = urluser+"&id="+id;
	}
	function recalculateModule(moduleId){
		
	}
</script>
<c:if test="${empty modules}"><liferay-ui:message key="there-are-no-results" />
</c:if>
<c:if test="${not empty modules}">
	<div class="lfr-search-container "><div class="yui3-widget aui-component aui-searchcontainer aui-searchcontainer-focused"><div class="results-grid aui-searchcontainer-content">
		<table class="taglib-search-iterator"><tbody>
		<c:forEach var="module" items="${modules}">
			<tr class="portlet-section-header results-header">
			<th class="col-1 col-1 first">${module.getTitle(themeDisplay.locale)}</th>
			<th class="col-1 col-1 first">
				<%-- liferay-ui:icon-menu>
					<liferay-ui:icon image="close" message="actmanager.recalculate-module" onClick='recalculateModule(${module.moduleId})' url="#"  />
					<liferay-ui:icon image="close" message="actmanager.recalculate-module" onClick='recalculateModule(${module.moduleId})' url="#"  />
				</liferay-ui:icon-menu --%>
			</th>
			</tr>
			<c:forEach var="activity" items="${learningActivities[module.moduleId]}">
				<tr class="portlet-section-body results-row">
				<td class="align-left col-1 col-1 first valign-middle">${activity.getTitle(themeDisplay.locale)}</td>
				<td class="align-left col-1 col-1 first valign-middle">
					<c:if test="${activity.isNew()}">
						<liferay-util:include page="/html/activitymanager/actions/module_actions.jsp" servletContext="<%=this.getServletContext() %>">
							<liferay-util:param name="activity" value="${activity.actId}" />
						</liferay-util:include>
					</c:if>
				</td>
				</tr>
			</c:forEach>
		</c:forEach>
		</tbody></table>
	</div></div></div>
</c:if>