<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@ include file="/init.jsp" %>
<%

LearningActivityResult result = null;

if((renderRequest.getParameter("actId")!=null)&&(renderRequest.getParameter("studentId")!=null))
{
	result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(ParamUtil.getLong(renderRequest,"actId"), ParamUtil.getLong(renderRequest,"studentId"));	
}


%>

<aui:form  name="fn_grades" method="post" >
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=renderRequest.getParameter("studentId") %>' />
	    <aui:input type="text" name="result" label="offlinetaskactivity.grades" value='<%=((result!=null)&&(result.getResult()>0))?Long.toString(result.getResult()):"" %>' />
	    <liferay-ui:error key="offlinetaskactivity.grades.result-bad-format" message="offlinetaskactivity.grades.result-bad-format" />
		<aui:input type="textarea" cols="40" rows="2" name="comments" label="offlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'/>
	</aui:fieldset>
	<aui:button-row>
	<button name="Save" value="save" onclick="<portlet:namespace />doSaveGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.save" />
	</button>
	<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.cancel" />
	</button>
	</aui:button-row>
	<liferay-ui:success key="offlinetaskactivity.grades.updating" message="offlinetaskactivity.correct.saved" />
	<liferay-ui:error key="offlinetaskactivity.grades.bad-updating" message="offlinetaskactivity.grades.bad-updating" />
</aui:form>