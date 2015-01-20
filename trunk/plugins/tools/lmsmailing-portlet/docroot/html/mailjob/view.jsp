
<%@page import="com.tls.liferaylms.job.condition.Condition"%>
<%@page import="com.tls.liferaylms.job.condition.ConditionUtil"%>
<%@ include file="/init.jsp"%>

<portlet:actionURL var="newURL" name="newMailJob">
</portlet:actionURL>

<a href="${newURL}"><liferay-ui:message key="groupmailing.new-mail-job" /></a>


<liferay-ui:search-container curParam="mailJobPag" emptyResultsMessage="there-are-no-mailjobs" delta="10">
		<liferay-ui:search-container-results>
			<%
				List mailjobs = (List)request.getAttribute("mailjobs");
				
				pageContext.setAttribute("results", mailjobs);
				
				pageContext.setAttribute("total", request.getAttribute("count"));
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.tls.liferaylms.mail.model.MailJob" keyProperty="idJob" modelVar="mailJob">
			<%
				Condition condition = ConditionUtil.instance(mailJob.getConditionClassName(), mailJob);
				Condition reference = ConditionUtil.instance(mailJob.getDateClassName(), mailJob);
			%>
				
			<portlet:actionURL var="viewURL" name="viewMailJob">
        		<portlet:param name="mailjob" value="<%=String.valueOf(mailJob.getPrimaryKey()) %>" />
			</portlet:actionURL>
			<liferay-ui:search-container-column-text name="condition">
				<c:choose>
					<c:when test="${!mailJob.processed}">
						<a href="<%=viewURL%>"><span class="mailJob"><%= condition.getName(themeDisplay.getLocale()) %> <%= condition.getConditionName(locale) %></span></a>
					</c:when>
					<c:otherwise>
						<span class="mailJob"><%= condition.getName(themeDisplay.getLocale()) %> <%= condition.getConditionName(locale) %></span>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="reference">
				<c:choose>
					<c:when test="${!mailJob.processed}">
						<a href="<%=viewURL%>"><span class="mailJob"><%= reference.getName(themeDisplay.getLocale()) %> <%= reference.getReferenceName(locale) %></span></a>
					</c:when>
					<c:otherwise>
						<span class="mailJob"><%= reference.getName(themeDisplay.getLocale()) %> <%= reference.getReferenceName(locale) %></span>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="days">
				<c:choose>
					<c:when test="${mailJob.dateShift lt 0}">
						${mailJob.dateShift*-1} <liferay-ui:message key="days-before" />
					</c:when>
					<c:otherwise>
						${mailJob.dateShift} <liferay-ui:message key="days-after" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>		
			<liferay-ui:search-container-column-text name="processed">
				<c:choose>
					<c:when test="${mailJob.processed}">
						<liferay-ui:icon image="checked" alt="checked"></liferay-ui:icon>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon image="unchecked" alt="unchecked"></liferay-ui:icon>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>			
			
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>