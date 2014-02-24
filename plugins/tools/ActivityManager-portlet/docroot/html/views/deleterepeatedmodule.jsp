<%@ include file="/init.jsp"%>

<portlet:actionURL var="removeRepeatedModuleResultURL" name="removeRepeatedModule">
	<portlet:param name="courseId" value="${param.courseId}"/>
	<portlet:param name="result" value="moduleresult"/>
</portlet:actionURL>

<portlet:renderURL var="cancel" />

<div class="action">
	<h4><liferay-ui:message key="actmanager.deleteRepeatedModuleResult" /></h4>
	<aui:form action="<%=removeRepeatedModuleResultURL %>" method="POST" name="form_mail">
		<aui:button-row>
			<aui:button type="submit" value="acept" class="submit" ></aui:button>
			<aui:button onClick="<%=cancel %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>