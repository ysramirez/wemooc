
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>

<%@ include file="/init.jsp" %>



<script type="text/javascript">
	function changeDiv(id){
		if(id=="1"){
			$("#capa1").css('display','block');
		    $("#capa2").css('display','none');
		    $("#capa3").css('display','none');
		    $("#span1").addClass('selected');
		    $("#span2").removeClass('selected');
		    $("#span3").removeClass('selected');
		}else if(id=="2"){
			$("#capa1").css('display','none');
			$("#capa2").css('display','block');
			$("#capa3").css('display','none');
			$("#span1").removeClass('selected');
		    $("#span2").addClass('selected');
		    $("#span3").removeClass('selected');
		}
		else if(id=="3"){
			$("#capa1").css('display','none');
			$("#capa2").css('display','none');
			$("#capa3").css('display','block');
			$("#span1").removeClass('selected');
		    $("#span2").removeClass('selected');
		    $("#span3").addClass('selected');
		}
	}
	
</script>

<%
long actId=ParamUtil.getLong(request,"actId",0);

if(actId==0)
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
	LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	long typeId=activity.getTypeId();
	long latId = ParamUtil.getLong(request,"latId",0);
	if(latId==0){
		if(LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUserCount(actId, themeDisplay.getUserId())>0){
			List<LearningActivityTry> latList = LearningActivityTryLocalServiceUtil.
					getLearningActivityTryByActUser(actId, themeDisplay.getUserId());
			if(!latList.isEmpty())
			{
				for(LearningActivityTry lat :latList){
					latId = lat.getLatId();
				}
			}
		}
	}

	if(typeId==3)
	{
		%>
		<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
		<p class="sub-title"><liferay-ui:message key="p2ptask-explicacion" /></p>
		<div class="description">
			<%=activity.getDescription(themeDisplay.getLocale()) %>
		</div>
		<%
		Long userId = themeDisplay.getUserId();
		P2pActivity myp2pActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);
		
		//request.setAttribute("myp2pActivity", myp2pActivity);
		request.setAttribute("actId", actId);
		request.setAttribute("latId", latId);
		
		LearningActivityResult learnResult = 
				LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId,themeDisplay.getUserId());
		if(myp2pActivity==null){
		%>
		<div class="steps">
			<span id="span1" class="selected"><liferay-ui:message key="p2ptask-step1" />&nbsp;>&nbsp;</span>
			<span id="span2"><liferay-ui:message key="p2ptask-step2" />&nbsp;>&nbsp;</span>
			<span id="span3"><liferay-ui:message key="p2ptask-step3" /></span>
		</div>
		<div class="preg_content" id="capa1">
			<jsp:include page="inc/uploadActivity.jsp" />
		</div>
		<div class="preg_content" id="capa2" style="display:none"></div>
		<div class="preg_content" id="capa3" style="display:none"></div>
		<%
		}
		else{
			String classCSS2="";
			String classCSS3="";
			String passed="";
			String javascript="";
			Long showRevision = ParamUtil.getLong(request, "showRevision",0);
			if(!learnResult.getPassed()){
				classCSS2="selected";
				if(showRevision==1)
					javascript="changeDiv(3);";
				else
					javascript="changeDiv(2);";
			}
			else{
				classCSS3="selected";
				javascript="changeDiv(3);";
				passed="done";
			}
			
			//request.setAttribute("myp2pActivity", myp2pActivity);
			request.setAttribute("actId", actId);
			request.setAttribute("latId", latId);
			
		%>
		<div class="steps">
			<span id="span1" onclick="changeDiv(1)" class="clicable done"><liferay-ui:message key="p2ptask-step1" />&nbsp;>&nbsp;</span>
			<span id="span2" class="<%=classCSS2 %> clicable <%=passed%>" onclick="changeDiv(2)"><liferay-ui:message key="p2ptask-step2" />&nbsp;>&nbsp;</span>
			<span id="span3" class="<%=classCSS3 %> clicable" onclick="changeDiv(3)"><liferay-ui:message key="p2ptask-step3" /></span>
		</div>
		<div class="preg_content" id="capa1" style="display:none">
			<jsp:include page="inc/uploadActivity.jsp" />
		</div>
		<div class="preg_content" id="capa2" style="display:none">
			<jsp:include page="inc/correctActivities.jsp" />
		</div>
		<div class="preg_content" id="capa3" style="display:none">
			<jsp:include page="inc/myCorrections.jsp" />
		</div>
		<script type="text/javascript">
		<%=javascript%>
		</script>
		<%
		}
		%>
		
		<liferay-portlet:renderURL var="correctionPage">
			<liferay-portlet:param name="actId" value="<%=Long.toString(actId) %>" />
			<liferay-portlet:param name="jspPage" value="/html/p2ptaskactivity/revisions.jsp" />
		</liferay-portlet:renderURL>
		
		<%
		if(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),LearningActivity.class.getName() , actId, "CORRECT")){
			String urlCorrection = "self.location = '"+correctionPage.toString()+"';";
			%>
			<aui:button cssClass="floatl" style="margin-top:10px" value="p2ptask-see-corrections" onClick="<%=urlCorrection.toString() %>" type="button" />
			<%
		}
	}
	else
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
}
	%>
	