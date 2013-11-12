<%@ include file="/html/init.jsp" %>

<c:if test="${not empty learningActivity}">
	<h2 id="h2" class="description-title">${learningActivity.getTitle(themeDisplay.locale)}</h2>
	<div id="resourcedescription">${learningActivity.getDescription(themeDisplay.locale)}</div>
	<c:choose>
		<c:when test="${times >= learningActivity.tries}">
			<liferay-ui:message key="learningactivity.lti.blocked" />
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${ltiItem.iframe}">
					${postLaunch}</br>
					<div id="fcontetn" style="height: 670px; width: 99%;">
						<a name="content"></a>
			          	<iframe id="output_frame" style="display:none;height:100%; width:100%" name="output_frame"><liferay-ui:message key="learningactivity.lti.noframes" /></iframe>
			    	</div>
				</c:when>
				<c:otherwise>
					${postLaunch}
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</c:if>