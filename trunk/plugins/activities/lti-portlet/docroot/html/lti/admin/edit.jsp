<%@ include file="/html/init.jsp" %>

<portlet:actionURL name="save" var="save" />

<script type="text/javascript">
	var error = "error";
	function check(){
		if(document.getElementById('url').value==null||document.getElementById('url').value==""){
			alert('<liferay-ui:message key="learningactivity.lti.error.url" />');
			return;
		}
		if(document.getElementById('secret').value==null||document.getElementById('secret').value==""){
			alert('<liferay-ui:message key="learningactivity.lti.error.secret" />');
			return;
		}
		if(document.getElementById('rol').value==null||document.getElementById('rol').value==""){
			alert('<liferay-ui:message key="learningactivity.lti.error.rol" />');
			return;
		}
		if(document.getElementById('note').value==null||document.getElementById('note').value==""){
			alert('<liferay-ui:message key="learningactivity.lti.error.note" />');
			return;
		}
		if(!isInt(document.getElementById('note').value)){
			alert('<liferay-ui:message key="learningactivity.lti.error.note.nan" />');
			return;
		}
		document.getElementById("sabeform").submit();
	}
	
	function isInt(x) {
		  var y=parseInt(x);
		  if (isNaN(y)) return false;
		  return x==y && x.toString()==y.toString();
	}
</script>
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
		<input type="button" value="<liferay-ui:message key="save" />" onclick="javascript:check()" >
	</form>
</c:if>