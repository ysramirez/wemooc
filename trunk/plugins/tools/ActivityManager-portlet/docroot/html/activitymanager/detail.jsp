
<%@include file="/init.jsp" %>

<%@page import="java.util.Collections"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>

<portlet:actionURL var="fixURL" name="fix" />
<script type="text/javascript">
	var url = '${fixURL}';
	function removeTest(id){
		if(confirm('<liferay-ui:message key="actmanager.confirm-delete" />')){
			window.location.href = url+"&id="+id;
		}
	}
</script>
<c:if test="${empty modules}"><liferay-ui:message key="there-are-no-results" />
</c:if>
<c:if test="${not empty modules}">
	<c:forEach var="module" items="${modules}">
		<div>${module.getTitle(themeDisplay.locale)}</div>
		<c:forEach var="activity" items="${learningActivities[module.moduleId]}">
			<div>${activity.getTitle(themeDisplay.locale)}
			<c:if test="${activity.typeId eq 0 || activity.typeId eq 2 || activity.typeId eq 3 || activity.typeId eq 7 }">
				<span>
					<liferay-ui:icon-menu>
						<liferay-ui:icon image="close" message="coursestats.modulestats.trials.numbers" onClick='removeTest(${activity.actId})' url="#" />
					</liferay-ui:icon-menu>
				</span>
			</c:if>
			</div>
		</c:forEach>
	</c:forEach>
</c:if>