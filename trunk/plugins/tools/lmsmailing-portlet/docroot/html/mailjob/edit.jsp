
<%@ include file="/init.jsp"%>

<portlet:actionURL var="saveURL" name="save">
</portlet:actionURL>

<portlet:actionURL var="updateURL" name="update">
</portlet:actionURL>

<portlet:renderURL var="cancel" />

<script type="text/javascript">
	var hash = {};
	<c:forEach items="${modules}" var="module" >
		hash['${module.moduleId}']= [
		<c:forEach items="${activities[module.moduleId]}" var="activity" varStatus="loop">
			{id : '${activity.actId}', title : '${activity.getTitle(themeDisplay.locale)}'} ${!loop.last ? ',' : ''}
		</c:forEach>
		];
	</c:forEach>
	
	function changeModule(pre){
		var sel = document.getElementById(pre+"_module");
		var act = document.getElementById(pre+"_activity");
		
		var val = sel.options[sel.selectedIndex].value;
		
		act.options.length = 0;
		
		var arr = hash[val];
		
		var arrayLength = arr.length;
		
		for (var i = 0; i < arrayLength; i++) {
		    var obj = arr[i];
		    var opt = document.createElement('option');
		    opt.value = obj.id;
		    opt.innerHTML = obj.title;
		    act.appendChild(opt);
		}
	}
</script>



<c:choose>
	<c:when test="${empty mailjob}">
		<form action="${saveURL}" method="POST">
	</c:when>
	<c:otherwise>
		<form action="${updateURL}" method="POST">
			<input type="hidden" value="${mailjob.idJob}" name="idJob" >
	</c:otherwise>
</c:choose>
	<div>
		<liferay-ui:message key="template" />
		<select name="idTemplate" id="idTemplate"  >
		 	<c:forEach items="${templates}" var="item" >
	  			<option <c:if test="${mailjob.idTemplate eq item.idTemplate}"> selected="selected"</c:if> value="${item.idTemplate}">${item.subject}</option>
	  		</c:forEach>
		</select>
	</div>
	<div>
		<h3><liferay-ui:message key="condition" /></h3>
		<liferay-ui:message key="groupmailing.condition" />
	</div>
	<div>
		<liferay-ui:message key="groupmailing.condition-class" />
		<select name="conditionClassName" id="conditionClassName">
		 	<c:forEach items="${conditions}" var="contition" >
	  			<option <c:if test="${mailjob.conditionClassName eq contition.className}"> selected="selected"</c:if> value="${contition.className}">${contition.getName(themeDisplay.locale)}</option>
	  		</c:forEach>
		</select>
	</div>
	<div>
		<liferay-ui:message key="module" />
		<select name="con_module" id="con_module" onchange="changeModule('con')">
		 	<c:forEach items="${modules}" var="module" >
	  			<option <c:if test="${condition.modConditionPK eq module.moduleId}"> selected="selected"</c:if> value="${module.moduleId}">${module.getTitle(themeDisplay.locale)}</option>
	  		</c:forEach>
		</select>
	</div>
	<div>
		<liferay-ui:message key="activity" />
		<select name="con_activity" id="con_activity">
		 	<c:forEach items="${activitiestemp}" var="activity" >
	  			<option <c:if test="${condition.actConditionPK eq activity.actId}"> selected="selected"</c:if> value="${activity.actId}">${activity.getTitle(themeDisplay.locale)}</option>
	  		</c:forEach>
		</select>
	</div>
	<div>
		<liferay-ui:message key="state" />
		<select multiple="multiple" name="con_state" id="con_state">
			<option
			<c:forEach items="${conditionStatus}" var="conditionSta">
				<c:if test="${conditionSta eq '0'}">selected="selected"</c:if>
			</c:forEach>
			 value="0"><liferay-ui:message key="groupmailing.not-started" /></option>
			<option 
			<c:forEach items="${conditionStatus}" var="conditionSta">
				<c:if test="${conditionSta eq '1'}">selected="selected"</c:if>
			</c:forEach>
			 value="1"><liferay-ui:message key="groupmailing.started" /></option>
			<option 
			<c:forEach items="${conditionStatus}" var="conditionSta">
				<c:if test="${conditionSta eq '2'}">selected="selected"</c:if>
			</c:forEach>
			 value="2"><liferay-ui:message key="not-passed" /></option>
			<option 
			<c:forEach items="${conditionStatus}" var="conditionSta">
				<c:if test="${conditionSta eq '3'}">selected="selected"</c:if>
			</c:forEach>
			 value="3"><liferay-ui:message key="passed" /></option>
		</select>
	</div>
	<div>
		<h3><liferay-ui:message key="reference" /></h3>
		<liferay-ui:message key="groupmailing.reference" />
	</div>
	<div>
		<liferay-ui:message key="groupmailing.reference-class" />
		<select name="referenceClassName" id="referenceClassName">
		 	<c:forEach items="${conditions}" var="contition" >
	  			<option <c:if test="${mailjob.dateClassName eq contition.className}"> selected="selected"</c:if> value="${contition.className}">${contition.getName(themeDisplay.locale)}</option>
	  		</c:forEach>
		</select>
	</div>
	<div>
		<liferay-ui:message key="module" />
		<select name="ref_module" id="ref_module" onchange="changeModule('ref')">
		 	<c:forEach items="${modules}" var="module" >
	  			<option <c:if test="${reference.modConditionPK eq module.moduleId}"> selected="selected"</c:if> value="${module.moduleId}">${module.getTitle(themeDisplay.locale)}</option>
	  		</c:forEach>
		</select>
	</div>
	<div>
		<liferay-ui:message key="activity" />
		<select name="ref_activity" id="ref_activity">
		 	<c:forEach items="${activitiestemp}" var="activity" >
	  			<option <c:if test="${reference.actConditionPK eq activity.actId}"> selected="selected"</c:if> value="${activity.actId}">${activity.getTitle(themeDisplay.locale)}</option>
	  		</c:forEach>
		</select>
	</div>
	<div>
		<liferay-ui:message key="state" />
		<select name="ref_state" id="ref_state">
			<option <c:if test="${mailjob.dateReferenceDate eq 0}"> selected="selected"</c:if> value="0"><liferay-ui:message key="groupmailing.init-date" /></option>
			<option <c:if test="${mailjob.dateReferenceDate eq 1}"> selected="selected"</c:if> value="1"><liferay-ui:message key="groupmailing.end-date" /></option>
			<option <c:if test="${mailjob.dateReferenceDate eq 0}"> selected="selected"</c:if> value="2"><liferay-ui:message key="groupmailing.inscription-date" /></option>
		</select>
	</div>
	<div>
		<input type="text" value="${days}" name="days" id="days" >
		<liferay-ui:message key="days" />
		<select id="dateShift" name="dateShift">
			<option value="-1"><liferay-ui:message key="before" /></option>
			<option <c:if test="${time eq 1}"> selected="selected"</c:if> value="1"><liferay-ui:message key="after" /></option>
		</select>
	</div>
	<div>
		<input type="submit" name="<liferay-ui:message key="submit" />">
	</div>
	<aui:button onClick="${cancel}" type="cancel" />
</form>