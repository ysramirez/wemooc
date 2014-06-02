<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>
<c:if test="<%=request.getAttribute("course")==null %>">
	<aui:input name="numOfEvaluations" label="num-of-evaluations" type="text" 
			value="<%=GetterUtil.DEFAULT_LONG %>" helpMessage="<%=LanguageUtil.get(pageContext,\"num-of-evaluations-help\")%>" >
		<aui:validator name="digits" />
		<aui:validator name="required" />
	</aui:input>
</c:if>