<%@ include file="/init.jsp" %>

<liferay-ui:icon-menu>
	<liferay-ui:icon image="close" message="actmanager.delete-all-tries" onClick='removeAll(${param.activity})' url="#"  />
	<liferay-ui:icon image="close" message="actmanager.delete-notpased-tries" onClick='removeNotPassed(${param.activity})' url="#" />
	<liferay-ui:icon image="assign" message="actmanager.delete-users" onClick='removeUser(${param.activity})' url="#" />
</liferay-ui:icon-menu> 