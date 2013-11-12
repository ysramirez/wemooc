<%@ include file="/html/init.jsp" %>

<portlet:actionURL name="save" var="save" />

<c:if test="${not empty learningActivity}">
	<liferay-ui:message key="learningactivity.lti.propertiesfor" /> ${learningActivity.getTitle(themeDisplay.locale)}
	<form action="${save}" name="saveform" id="sabeform" method="POST" >
		<input type="hidden" name="actId" id="actId" value="${learningActivity.actId}" >
		<c:choose>
			<c:when test="${not empty ltiItem}">
				<liferay-ui:message key="learningactivity.lti.url" />:<br/><input type="text" name="url" id="url" value="${ltiItem.url}" ><br/>
				<liferay-ui:message key="learningactivity.lti.secret" />:<br/><input type="text" name="secret" id="secret" value="${ltiItem.secret}" ><br/>
				<liferay-ui:message key="learningactivity.lti.rol" />:<br/><input type="text" name="rol" id="rol" value="${ltiItem.rol}" ><br/>
				<liferay-ui:message key="learningactivity.lti.note" />:<br/><input type="text" name="note" id="note" value="${ltiItem.note}" ><br/>
				<liferay-ui:message key="learningactivity.lti.iframe" />:<input type="checkbox" name="iframe" id="iframe" <c:if test="${ltiItem.iframe}">checked="checked"</c:if> ><br/>
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="learningactivity.lti.url" />:<br/><input type="text" name="url" id="url" value="" ><br/>
				<liferay-ui:message key="learningactivity.lti.secret" />:<br/><input type="text" name="secret" id="secret" value="" ><br/>
				<liferay-ui:message key="learningactivity.lti.rol" />:<br/><input type="text" name="rol" id="rol" value="Learner" ><br/>
				<liferay-ui:message key="learningactivity.lti.note" />:<br/><input type="text" name="note" id="note" value="0" ><br/>
				<liferay-ui:message key="learningactivity.lti.iframe" />:<input type="checkbox" name="iframe" id="iframe" ><br/>
			</c:otherwise>
		</c:choose>
		<input type="submit" value="<liferay-ui:message key="save" />" >
	</form>
</c:if>