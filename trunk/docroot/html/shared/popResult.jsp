
<%@ include file="/init.jsp" %>

<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>

<%

Long actId = (Long)request.getAttribute("actId");
LearningActivity learningActivity = (LearningActivity)request.getAttribute("learningActivity");
LearningActivityTry learnTry = (LearningActivityTry)request.getAttribute("larntry");

LearningActivityResult learnResult = 
	LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId,themeDisplay.getUserId());
String actFeedback=learningActivity.getFeedbackNoCorrect();
String title = "";
%>
<style type="text/css">
	#simplemodal-container{width:500px;height:280px;}
</style>
 
<%
boolean isP2P=false;
if(learnResult.getPassed())
{
	actFeedback=learningActivity.getFeedbackCorrect();
	if(learningActivity.getTypeId()==3){
		title = LanguageUtil.get(pageContext,"p2ptask-superada");
		isP2P=true;
	}else{
		title = LanguageUtil.get(pageContext,"test-superado");
	}

%>
<div id="activityResult" style="display:none">
<h1><%=title %></h1>
<div id="actfeedback"><%=actFeedback %></div>
<div id="score" style='<%=(true)? "display:none":""%>'>
	<b><liferay-ui:message key="shared-you-guess" /> <%=learnResult.getResult()%>% <liferay-ui:message key="shared-in-tarea" /></b>
</div>
<%--div class="negrita color_principal"><liferay-ui:message key="shared-guess-badget" /></div--%>
<!-- <a class="button">Ver revisi&oacute;n</a>-->
</div>
<script type="text/javascript">
<!--
$('#activityResult').modal();
//-->
</script>
<%
}
else
{
	actFeedback=learningActivity.getFeedbackNoCorrect();
	if(learningActivity.getTypeId()==3){
		title = LanguageUtil.get(pageContext,"p2ptask-nosuperada");
	}else{
		title = LanguageUtil.get(pageContext,"test-nosuperado");
	}

%>
<div id="activityResult" style="display:none">
<h1><%=title %></h1>
<div id="actfeedback"><%=actFeedback %></div>
<!--  <a class="button">Ver revisi&oacute;n</a>-->
</div>
<script type="text/javascript">
<!--
$('#activityResult').modal();
//-->
</script>
<%
}
 %>