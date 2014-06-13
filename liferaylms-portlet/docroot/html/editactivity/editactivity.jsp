<%@page import="com.liferay.portal.model.ModelHintsUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.lms.asset.LearningActivityBaseAssetRenderer"%>
<%@page import="com.liferay.portal.service.ResourcePermissionServiceUtil"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.portal.service.ResourceActionLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.ResourceConstants"%>
<%@page import="com.liferay.portal.service.ResourcePermissionLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.PortletConstants"%>
<%@page import="com.liferay.portal.service.permission.PortletPermissionUtil"%>
<%@page import="com.liferay.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.lms.learningactivity.BaseLearningActivityType"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.portlet.PortletQNameUtil"%>
<%@page import="com.liferay.portal.model.PublicRenderParameter"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistry"%>
<%@page import="com.liferay.lms.asset.LearningActivityAssetRendererFactory"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="java.util.Set"%>
<%@page import="com.liferay.lms.LearningTypesProperties"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>
<%@ include file="/init.jsp" %>
 <liferay-ui:success key="activity-saved-successfully" message="activity-saved-successfully" />
<portlet:actionURL var="saveactivityURL" name="saveActivity" >
	<portlet:param name="editing" value="<%=StringPool.TRUE %>"/>
</portlet:actionURL>

<%
renderResponse.setProperty(
		"clear-request-parameters", Boolean.TRUE.toString());

long moduleId=ParamUtil.getLong(request,"resModuleId",0);
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");
long typeId=ParamUtil.getLong(request, "type");
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[]{themeDisplay.getScopeGroupId()}, themeDisplay.getLocale());

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long actId=ParamUtil.getLong(request, "resId",0);
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null){
	learnact=(LearningActivity)request.getAttribute("activity");
	typeId=learnact.getTypeId();
	moduleId=learnact.getModuleId();
}else{
	if(actId>0)	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		typeId=learnact.getTypeId();
		moduleId=learnact.getModuleId();
	}
}

Module module = null;
try{
	module = ModuleLocalServiceUtil.getModule(moduleId);
}catch(Exception e){}

boolean disabled = true;
if(LearningActivityLocalServiceUtil.canBeEdited(learnact, user.getUserId())){
	disabled = false;
}

String typeName=classTypes.get(typeId);
LearningActivityType larntype=new LearningActivityTypeRegistry().getLearningActivityType(typeId);

String description="", startCalendarClass="", endCalendarClass="";
SimpleDateFormat formatDay    = new SimpleDateFormat("dd");
formatDay.setTimeZone(timeZone);
SimpleDateFormat formatMonth    = new SimpleDateFormat("MM");
formatMonth.setTimeZone(timeZone);
SimpleDateFormat formatYear    = new SimpleDateFormat("yyyy");
formatYear.setTimeZone(timeZone);
SimpleDateFormat formatHour   = new SimpleDateFormat("HH");
formatHour.setTimeZone(timeZone);
SimpleDateFormat formatMin = new SimpleDateFormat("mm");
formatMin.setTimeZone(timeZone);
Date today=new Date(System.currentTimeMillis());
int startDay=Integer.parseInt(formatDay.format(today));
int startMonth=Integer.parseInt(formatMonth.format(today))-1;
int startYear=Integer.parseInt(formatYear.format(today));
int startHour=Integer.parseInt(formatHour.format(today));
int startMin=Integer.parseInt(formatMin.format(today));
int endDay=Integer.parseInt(formatDay.format(today));
int endMonth=Integer.parseInt(formatMonth.format(today))-1;
int endYear=Integer.parseInt(formatYear.format(today))+1;
int endHour=Integer.parseInt(formatHour.format(today));
int endMin=Integer.parseInt(formatMin.format(today));
%>

<%
if(learnact!=null)
{
	actId=learnact.getActId();
	description=learnact.getDescription(themeDisplay.getLocale());
	
	if(learnact.getStartdate() != null){
		startDay=Integer.parseInt(formatDay.format(learnact.getStartdate()));
		startMonth=Integer.parseInt(formatMonth.format(learnact.getStartdate()))-1;
		startYear=Integer.parseInt(formatYear.format(learnact.getStartdate()));
		startHour=Integer.parseInt(formatHour.format(learnact.getStartdate()));
		startMin=Integer.parseInt(formatMin.format(learnact.getStartdate()));
	}
	
	if(learnact.getEnddate() != null){
		endDay=Integer.parseInt(formatDay.format(learnact.getEnddate()));
		endMonth=Integer.parseInt(formatMonth.format(learnact.getEnddate()))-1;
		endYear=Integer.parseInt(formatYear.format(learnact.getEnddate()));
		endHour=Integer.parseInt(formatHour.format(learnact.getEnddate()));
		endMin=Integer.parseInt(formatMin.format(learnact.getEnddate()));
	}
	%>
	
	<portlet:actionURL name="deleteMyTries" var="deleteMyTriesURL">
		<portlet:param name="editing" value="<%=StringPool.TRUE %>"/>
		<portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
		<portlet:param name="redirect" value="<%=redirect %>" />
		<portlet:param name="backURL" value="<%=backURL%>" />
	</portlet:actionURL>

	
	
<div class="acticons">
	<%
		if(larntype.hasEditDetails() && !disabled){
			AssetRenderer  assetRenderer=larntype.getAssetRenderer(learnact);
			if(assetRenderer!=null) {
				liferayPortletRequest.setAttribute(LearningActivityBaseAssetRenderer.EDIT_DETAILS, true);
				PortletURL urlEditDetails = assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse);
				if(Validator.isNotNull(urlEditDetails)){
					urlEditDetails.setWindowState(LiferayWindowState.POP_UP);
					String urlEdit = urlEditDetails.toString();
					Portlet urlEditPortlet = PortletLocalServiceUtil.getPortletById(HttpUtil.getParameter(urlEdit, "p_p_id",false));
					
					PublicRenderParameter actIdPublicParameter = urlEditPortlet.getPublicRenderParameter("actId");
					if(Validator.isNotNull(actIdPublicParameter)) {
						urlEdit=HttpUtil.removeParameter(urlEdit,PortletQNameUtil.getPublicRenderParameterName(actIdPublicParameter.getQName()));
					}
					
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId", Long.toString(learnact.getActId()));
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resModuleId");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resModuleId", Long.toString(learnact.getModuleId()) );
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails", true);
					
					%>
					<liferay-ui:icon image="edit" message="<%=larntype.getMesageEditDetails()%>" label="true" 
									 url="<%=urlEdit%>" />
					<% 
				}
			}
		}
		else{
			if(larntype.hasEditDetails()){
			%>
			<liferay-ui:icon image="edit" message="<%=larntype.getMesageEditDetails()%>" label="true" />
			<% 
			}
		}
		if(larntype.hasDeleteTries()) {
			%>
			<liferay-ui:icon-delete label="true" url="<%=deleteMyTriesURL.toString()%>" message="delete-mi-tries" />	
			<%		
		}
	%>
</div>
	
	<aui:model-context bean="<%= learnact %>" model="<%= LearningActivity.class %>" />

<%
}else{
	%>
	<aui:model-context model="<%= LearningActivity.class %>" />
	<%
}
%>

<script type="text/javascript">
<!--

AUI().ready('node-base' ,'aui-form-validator', 'aui-overlay-context-panel', 'widget-locale', function(A) {
	
	if ((!!window.postMessage)&&(window.parent != window)) {
		if (!window.location.origin){
			window.location.origin = window.location.protocol+"//"+window.location.host;
		}
		
		if(AUI().UA.ie==0) {
			parent.postMessage({name:'setTitleActivity',
				                moduleId:<%=Long.toString(moduleId)%>,
				                actId:<%=Long.toString(actId)%>,
				                title:'<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, actId==0?"activity.creation":"activity.edition"))+" "+ 
				                	UnicodeFormatter.toString(LanguageUtil.get(pageContext, new LearningActivityTypeRegistry().getLearningActivityType(typeId).getName()))%>'}, window.location.origin);
		}
		else {
			parent.postMessage(JSON.stringify({name:'setTitleActivity',
                							   moduleId:<%=Long.toString(moduleId)%>,
                							   actId:<%=Long.toString(actId)%>,
                							   title:'<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext,actId==0?"activity.creation":"activity.edition"))+" "+ 
                								   UnicodeFormatter.toString(LanguageUtil.get(pageContext, new LearningActivityTypeRegistry().getLearningActivityType(typeId).getName()))%>'}), window.location.origin);
		}
	}

	try{
		<% if(larntype.hasEditDetails()){ %>
			A.one('.taglib-icon').focus();
		<% } %>
	}catch (err){
		
	}
	
	var enabledStart = document.getElementById('<%=renderResponse.getNamespace() %>startdate-enabledCheckbox').checked;
	if(enabledStart){
		A.all("#startDate").one(".aui-datepicker-button-wrapper").show();
		A.all("#startDate").one("#startDateSpan").removeClass('aui-helper-hidden');
	}else  A.all("#startDate").one(".aui-datepicker-button-wrapper").hide();

	var enabledEnd = document.getElementById('<%=renderResponse.getNamespace() %>stopdate-enabledCheckbox').checked; 
	if(enabledEnd){
		A.all("#endDate").one(".aui-datepicker-button-wrapper").show();
		A.all("#endDate").one("#endDateSpan").removeClass('aui-helper-hidden');
	}else A.all("#endDate").one(".aui-datepicker-button-wrapper").hide();

	var rules = {			
			<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>: {
				required: true
			},
        	<portlet:namespace />description: {
        		required: false
            }
			<% if(larntype.isTriesConfigurable()) { %>
			,<portlet:namespace />tries: {
				required: true,
				number: true,
				range: [0,100]
			}
			<% } if(larntype.isScoreConfigurable()) { %>
			,<portlet:namespace />passpuntuation: {
				required: true,
				number: true,
				range: [0,100]
			}
			<% } %>
		};

	var fieldStrings = {			
        	<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "activity-title-required")) %>'
            },
        	<portlet:namespace />description: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "description-required")) %>'
            }
			<% if(larntype.isTriesConfigurable()) { %>
        	,<portlet:namespace />tries: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.tries.required")) %>',
        		number: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.tries.number")) %>',
        		range: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.tries.range")) %>',       		
            }
			<% } if(larntype.isScoreConfigurable()) { %>
        	,<portlet:namespace />passpuntuation: {
        		required: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.passpuntuation.required")) %>',
        		number: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.passpuntuation.number")) %>',
        		range: '<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "editActivity.passpuntuation.range")) %>',       		
            }
			<% } %> 
		};
	
	A.each(A.Object.keys(window), function(fieldName){
		var field=window[fieldName];
	    if((fieldName.indexOf('<portlet:namespace />validate_')==0)&&
	       (A.Lang.isObject(field))&&
	       (A.Object.hasKey(field,'rules'))&&
	       (A.Object.hasKey(field,'fieldStrings'))) {
			A.mix(rules,field.rules,true);
			A.mix(fieldStrings,field.fieldStrings,true);
		}
	});

	
	window.<portlet:namespace />validateActivity = new A.FormValidator({
		boundingBox: '#<portlet:namespace />fm',
		validateOnBlur: true,
		validateOnInput: true,
		selectText: true,
		showMessages: false,
		containerErrorClass: '',
		errorClass: '',
		rules: rules,
        fieldStrings: fieldStrings,
		
		on: {		
            errorField: function(event) {
            	var instance = this;
				var field = event.validator.field;
				var divError = A.one('#'+field.get('name')+'Error');
				if(divError) {
					divError.addClass('portlet-msg-error');
					divError.setContent(instance.getFieldErrorMessage(field,event.validator.errors[0]));
				}
            },		
            validField: function(event) {
				var divError = A.one('#'+event.validator.field.get('name')+'Error');
				if(divError) {
					divError.removeClass('portlet-msg-error');
					divError.setContent('');
				}
            }
		}
	});
	A.one('#<portlet:namespace/>resModuleId').scrollIntoView();
});



	function validate(){
		
		var start = document.getElementById('<portlet:namespace />startdate-enabledCheckbox').checked;
		var stop = document.getElementById('<portlet:namespace />stopdate-enabledCheckbox').checked;
		
		if(start&&stop){	
					
			var start = getStartDate();
			var end = getEndDate();
					
			if(start.getTime()>=end.getTime()){
				alert("<%=UnicodeFormatter.toString(LanguageUtil.get(pageContext, "please-enter-a-start-date-that-comes-before-the-end-date")) %>");
				return;
			}
// 			else{
// 				if(document.getElementById('<portlet:namespace />uploadDay')!=null){
// 					var upload = getUpdateDate();
// 					if(start.getTime()>upload.getTime()||upload.getTime()>end.getTime()){
// 						alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 						return;
// 					}
// 				}
// 			}
// 		}else if(start){
// 			if(document.getElementById('<portlet:namespace />uploadDay')!=null){
// 				var start = getStartDate();
// 				var upload = getUpdateDate();
				
// 				if(start.getTime()>upload.getTime()){
// 					alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 					return;
// 				}
// 			}
// 		}else if(stop){
// 			if(document.getElementById('<portlet:namespace />uploadDay')!=null){
// 				var end = getEndDate();
// 				var upload = getUpdateDate();

// 				if(upload.getTime()>end.getTime()){
// 					alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 					return;
// 				}
// 			}
// 		}else{
// 			if(document.getElementById('<portlet:namespace />uploadDay')!=null){
<%-- 				var startm = new Date(<%= module.getStartDate().getTime() %>); --%>
<%-- 				var endm = new Date(<%= module.getEndDate().getTime() %>); --%>
// 				var upload = getUpdateDate();

// 				if(startm.getTime()>upload.getTime()||upload.getTime()>endm.getTime()){
// 					alert("<liferay-ui:message key="please-enter-a-correct-update-date" />");
// 					return;
// 				}
// 			}
 		}


		var form = document.getElementById('<portlet:namespace />fm');
		var inputsform = form.getElementsByTagName("input");
		var selector = document.getElementById('dpcqlanguageSelector');
		if(selector){
			var parents = selector.getElementsByClassName("lfr-form-row");
			for (var i=0; i < parents.length; i++){
				if(!parents[i].className.match(/.*hidden.*/)){
					var inputs = parents[i].getElementsByTagName("input");
					for (var j=0; j < inputs.length; j++){
						var input = document.createElement('input');
					    input.type = 'hidden';
					    input.name = inputs[j].name;
					    input.id = inputs[j].id;
					    input.value = inputs[j].value;
					    form.appendChild(input);
					}
				}
			}
		}
		
		document.getElementById('<portlet:namespace />fm').submit();
	}

	function getStartDate(){
		var startDateDia = document.getElementById('<portlet:namespace />startDay').value;
		var startDateMes = document.getElementById('<portlet:namespace />startMon').value;
		var startDateAno = document.getElementById('<portlet:namespace />startYear').value;
		var startDateHora = document.querySelectorAll('[name="<portlet:namespace />startHour"]')[0].value;
		var startDateMinuto = document.querySelectorAll('[name="<portlet:namespace />startMin"]')[0].value;

		var start = new Date(startDateAno,startDateMes,startDateDia,startDateHora,startDateMinuto);
		return start;
	}
	
	function getEndDate(){
		var endDateDia = document.getElementById('<portlet:namespace />stopDay').value;
		var endDateMes = document.getElementById('<portlet:namespace />stopMon').value;
		var endDateAno = document.getElementById('<portlet:namespace />stopYear').value;
		var endDateHora = document.querySelectorAll('[name="<portlet:namespace />stopHour"]')[0].value;
		var endDateMinuto = document.querySelectorAll('[name="<portlet:namespace />stopMin"]')[0].value;

		var end = new Date(endDateAno,endDateMes,endDateDia,endDateHora,endDateMinuto);
		return end;
	}
	
	function getUpdateDate(){
		var uploadDateDia = document.getElementById('<portlet:namespace />uploadDay').value;
		var uploadDateMes = document.getElementById('<portlet:namespace />uploadMon').value;
		var uploadDateAno = document.getElementById('<portlet:namespace />uploadYear').value;
		var uploadDateHora = document.querySelectorAll('[name="<portlet:namespace />uploadHour"]')[0].value;
		var uploadDateMinuto = document.querySelectorAll('[name="<portlet:namespace />uploadMin"]')[0].value;

		var upload = new Date(uploadDateAno,uploadDateMes,uploadDateDia,uploadDateHora,uploadDateMinuto);
		return upload;
	}
//-->
</script>
<aui:form name="fm" action="<%=saveactivityURL%>"  method="post"  enctype="multipart/form-data">
	<aui:fieldset>
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="resId" type="hidden" value="<%=actId %>"/>

<script type="text/javascript">
<!--

Liferay.provide(
        window,
        '<portlet:namespace />reloadComboActivities',
        function(moduleId) {
        	var A = AUI();
			var renderUrl = Liferay.PortletURL.createRenderURL();							
			renderUrl.setWindowState('<%= LiferayWindowState.EXCLUSIVE.toString() %>');
			renderUrl.setPortletId('<%=portletDisplay.getId()%>');
			renderUrl.setParameter('jspPage','/html/editactivity/comboActivities.jsp');
			renderUrl.setParameter('resId','<%=Long.toString(actId) %>');
			renderUrl.setParameter('resModuleId',moduleId);
			renderUrl.setParameter('precedence','<%=Long.toString((learnact!=null)?learnact.getPrecedence():0) %>');

			A.io.request(renderUrl.toString(),
			{  
			  dataType : 'html',	 
			  on: 
				{   
					success: function() { 
						A.one('#<portlet:namespace />precedence').
							replace(A.Node.create(this.get('responseData')).one('select'));   
					}   
				}
			}); 
			
        },
        ['liferay-portlet-url']
    );
    
//-->
</script>

	<c:if test="<%=!ParamUtil.getBoolean(renderRequest,\"noModule\",false) %>">
		<aui:select id="resModuleId" label="module" name="resModuleId" onChange="<%=renderResponse.getNamespace()+\"reloadComboActivities(this.options[this.selectedIndex].value);\" %>">
		<%
			java.util.List<Module> modules=ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			for(Module theModule:modules)
			{
				boolean selected=false;
				if(learnact!=null && learnact.getModuleId()==theModule.getModuleId())
				{
					selected=true;
				}
				else
				{
					if(theModule.getModuleId()==moduleId)
					{
						selected=true;
					}
				}
				%>
					<aui:option value="<%=theModule.getModuleId() %>" selected="<%=selected %>"><%=theModule.getTitle(themeDisplay.getLocale()) %></aui:option>
				<% 
			}
		%>

		</aui:select>
	</c:if>

<%
	if(actId==0)
	{
	%>
	<aui:input type="hidden" name="type" value="<%=typeId %>"></aui:input>
	<% 
	}
	%>

		<aui:input name="title" label="title" defaultLanguageId="<%=renderRequest.getLocale().toString() %>" id="title">
		</aui:input>
		<div id="<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>Error" class="<%=(SessionErrors.contains(renderRequest, "activity-title-required"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "activity-title-required"))?
	    			LanguageUtil.get(pageContext,"activity-title-required"):StringPool.BLANK %>
	    </div>
	    
	       <script type="text/javascript">
		<!--
			Liferay.provide(
		        window,
		        '<portlet:namespace />onChangeDescription',
		        function(val) {
		        	var A = AUI();
					A.one('#<portlet:namespace />description').set('value',val);
					if(window.<portlet:namespace />validateActivity){
						window.<portlet:namespace />validateActivity.validateField('<portlet:namespace />description');
					}
		        },
		        ['node']
		    );
		    
		//-->
		</script>
	    
		<aui:field-wrapper label="description" name="description">
			<liferay-ui:input-editor name="description" width="100%" onChangeMethod="onChangeDescription" />
			<script type="text/javascript">
		        function <portlet:namespace />initEditor() 
		        { 
		            return "<%= UnicodeFormatter.toString(description) %>"; 
		        }
		    </script>
		</aui:field-wrapper>
		<div id="<portlet:namespace />descriptionError" class="<%=(SessionErrors.contains(renderRequest, "description-required"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "description-required"))?
	    			LanguageUtil.get(pageContext,"description-required"):StringPool.BLANK %>
	    </div>
	    
	    <%
			boolean optional=false;
			boolean mandatory = true;
			if(learnact!=null)
			{
				request.setAttribute("activity", learnact);
				request.setAttribute("activityId", learnact.getActId());
				optional=(learnact.getWeightinmodule()==0);
				mandatory = (learnact.getWeightinmodule() != 0);
			}
		%>
		<aui:field-wrapper label="editactivity.mandatory" cssClass="editactivity-mandatory-field" name="mandatorylabel">
			<aui:input label="editactivity.mandatory.yes" type="radio" name="weightinmodule" value="1" checked="<%= mandatory %>" inlineField="true" />
			<aui:input label="editactivity.mandatory.no" type="radio" name="weightinmodule" value="0" checked="<%= !mandatory %>" inlineField="true" />
			<aui:input type="hidden" name="mandatorylabel" />
		</aui:field-wrapper>
	 <liferay-ui:panel-container extended="false" persistState="false">
	 <%
	 boolean showSpecificPanel = larntype.isTriesConfigurable() || larntype.isScoreConfigurable() || larntype.isFeedbackCorrectConfigurable() || 
	 								larntype.isFeedbackNoCorrectConfigurable() || larntype.getExpecificContentPage()!=null;
	 if(showSpecificPanel){
	 
		 String defaultState="open";
		 if(actId>0&&renderRequest.getAttribute("preferencesOpen")==null)
		 {
			 defaultState="closed";
		 }
	 	%>
	 		<liferay-ui:panel title="activity-specifics" collapsible="true" defaultState="<%=defaultState %>">
	  
		<%
		if(larntype.isTriesConfigurable())
		{
			long tries=larntype.getDefaultTries();
			if(learnact!=null)
			{
				tries=learnact.getTries();
			}
		%>
		
		<aui:input size="5" name="tries" label="tries" value="<%=Long.toString(tries) %>" type="number" disabled="<%=disabled%>">
		</aui:input><%--liferay-ui:icon-help message="number-of-tries"></liferay-ui:icon-help--%>
  		<div id="<portlet:namespace />triesError" class="<%=((SessionErrors.contains(renderRequest, "editActivity.tries.required"))||
														      (SessionErrors.contains(renderRequest, "editActivity.tries.number"))||
														      (SessionErrors.contains(renderRequest, "editActivity.tries.range")))?
   														      "portlet-msg-error":StringPool.BLANK %>">
   			<%=(SessionErrors.contains(renderRequest, "editActivity.tries.required"))?
   			    	LanguageUtil.get(pageContext,"editActivity.tries.required"):
 			   (SessionErrors.contains(renderRequest, "editActivity.tries.number"))?
 		    		LanguageUtil.get(pageContext,"editActivity.tries.number"):
 			   (SessionErrors.contains(renderRequest, "editActivity.tries.range"))?
 		    		LanguageUtil.get(pageContext,"editActivity.tries.range"):StringPool.BLANK %>
	    </div>
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="tries" value="<%=larntype.getDefaultTries() %>" />
			<% 
		}
				
		if(larntype.isScoreConfigurable())
		{
			long score=Long.valueOf(larntype.getDefaultScore());
			if(learnact!=null)
			{
				score=learnact.getPasspuntuation();
			}
			
		%>
		<aui:input size="5" name="passpuntuation" label="passpuntuation" type="text" value="<%=Long.toString(score) %>" disabled="<%=disabled %>" helpMessage="<%=LanguageUtil.get(pageContext,\"editActivity.passpuntuation.help\")%>">
		</aui:input>
		<% if (disabled) { %>
		<input name="<portlet:namespace />passpuntuation" type="hidden" value="<%=Long.toString(score) %>" />
		<% } %>
  		<div id="<portlet:namespace />passpuntuationError" class="<%=((SessionErrors.contains(renderRequest, "editActivity.passpuntuation.required"))||
																      (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.number"))||
																      (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.range")))?
	    														      "portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "editActivity.passpuntuation.required"))?
	    			LanguageUtil.get(pageContext,"editActivity.passpuntuation.required"):
   			   (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.number"))?
   		    		LanguageUtil.get(pageContext,"editActivity.passpuntuation.number"):
   			   (SessionErrors.contains(renderRequest, "editActivity.passpuntuation.range"))?
   		    		LanguageUtil.get(pageContext,"editActivity.passpuntuation.range"):StringPool.BLANK %>
	    </div>
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="passpuntuation" value="<%=larntype.getDefaultScore() %>" />
			<% 
		}
		%>
		
		
		
	
		<%
		if(larntype.isFeedbackCorrectConfigurable())
		{
			String  feedbacCorrect=larntype.getDefaultFeedbackCorrect();
			if(learnact!=null)
			{
				feedbacCorrect=learnact.getFeedbackCorrect();
			}
		%>	
		<aui:input name="feedbackCorrect" label="feedbackCorrect" value="<%=feedbacCorrect %>" helpMessage="feedbackCorrect.helpMessage" type="text" maxLength="<%=ModelHintsUtil.getHints(LearningActivity.class.getName(), \"feedbackCorrect\").get(\"max-length\") %>" ></aui:input>	
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="feedbackCorrect" value="<%=larntype.getDefaultFeedbackCorrect() %>" helpMessage="feedbackCorrect.helpMessage"/>
			<% 
		}
		if(larntype.isFeedbackNoCorrectConfigurable())
		{
			String  feedbacNoCorrect=larntype.getDefaultFeedbackCorrect();
			if(learnact!=null)
			{
				feedbacNoCorrect=learnact.getFeedbackCorrect();
			}
		%>
		<aui:input name="feedbackNoCorrect" label="feedbackNoCorrect" value="<%=feedbacNoCorrect %>" helpMessage="feedbackNoCorrect.helpMessage" type="text"></aui:input>	
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="feedbackNoCorrect" value="<%=larntype.getDefaultFeedbackNoCorrect() %>" helpMessage="feedbackNoCorrect.helpMessage"/>
			<% 
		}
		
		%>

		
		<% if(larntype.getExpecificContentPage()!=null) { %>
			<liferay-util:include page="<%=larntype.getExpecificContentPage() %>" servletContext="<%=getServletContext() %>" portletId="<%= larntype.getPortletId() %>">
				<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
				<liferay-util:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
			</liferay-util:include>	
		<% } %>
	</liferay-ui:panel>
<%} %>
	 
	 <liferay-ui:panel title="activity-constraints" collapsible="true" defaultState="closed">
	   
	    <script type="text/javascript">


		    
		    function setStarDateState(){
		    	AUI().use('node',function(A) {
			    	var enabled = document.getElementById('<%=renderResponse.getNamespace() %>startdate-enabledCheckbox').checked; 
		    		var selector = 'form[name="<%=renderResponse.getNamespace() %>fm"]';
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startDay"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startMon"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startYear"]').set('disabled', !enabled);
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startMin"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startHour"]').set('disabled', !enabled);
		    		
		    		if(enabled) {
		    			A.all("#startDate").one(".aui-datepicker-button-wrapper").show();
		    			A.all("#startDate").one("#startDateSpan").removeClass('aui-helper-hidden');
		    		}else {
		    			A.all("#startDate").one(".aui-datepicker-button-wrapper").hide();
		    			A.all("#startDate").one("#startDateSpan").addClass('aui-helper-hidden');
		    		}

		    	});
		    }
		    
		    function setStopDateState(){
		    	AUI().use('node',function(A) {
			    	var enabled = document.getElementById('<%=renderResponse.getNamespace() %>stopdate-enabledCheckbox').checked; 

		    		var selector = 'form[name="<%=renderResponse.getNamespace() %>fm"]';
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopDay"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopMon"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopYear"]').set('disabled', !enabled);
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopMin"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopHour"]').set('disabled', !enabled);
		    		
		    		if(enabled) {
		    			A.all("#endDate").one(".aui-datepicker-button-wrapper").show();
		    			A.all("#endDate").one("#endDateSpan").removeClass('aui-helper-hidden');
		    		}else {
		    			A.all("#endDate").one(".aui-datepicker-button-wrapper").hide();
		    			A.all("#endDate").one("#endDateSpan").addClass('aui-helper-hidden');
		    		}

		    	});
		    }

	    </script>
		<div id="startDate">
			<aui:field-wrapper label="start-date">
				<aui:input id="startdate-enabled" name="startdate-enabled" checked="<%=learnact != null && learnact.getStartdate() != null  %>" type="checkbox" label="editActivity.startdate.enabled" onClick="setStarDateState();" helpMessage="editActivity.startdate.enabled.help"  ignoreRequestValue="true" />
				<div id="startDateSpan" class="aui-helper-hidden">
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"  dayParam="startDay" monthParam="startMon" disabled="<%=learnact == null || learnact.getStartdate() == null  %>"
					 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
					 <liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>" disabled="<%=learnact == null || learnact.getStartdate() == null  %>"></liferay-ui:input-time>
				</div>
			</aui:field-wrapper>
		</div>
		
		<div id="endDate">
			<aui:field-wrapper label="end-date">
				<aui:input id="stopdate-enabled" name="stopdate-enabled" checked="<%=learnact != null && learnact.getEnddate()!= null  %>" type="checkbox" label="editActivity.stopdate.enabled" onClick="setStopDateState();" helpMessage="editActivity.stopdate.enabled.help"  ignoreRequestValue="true" />
				<div id="endDateSpan" class="aui-helper-hidden">
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon" disabled="<%=learnact == null || learnact.getEnddate() == null  %>"
					 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
					 <liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>" disabled="<%=learnact == null || learnact.getEnddate() == null  %>"></liferay-ui:input-time></br>
				</div>
			</aui:field-wrapper>
		</div>
			<liferay-util:include page="/html/editactivity/comboActivities.jsp" servletContext="<%=getServletContext() %>">
			<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
			<liferay-util:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
			<liferay-util:param name="precedence" value="<%=Long.toString((learnact!=null)?learnact.getPrecedence():0) %>" />
		</liferay-util:include>
		
		<%
			
		if(larntype.getTypeId()!=8 && !TeamLocalServiceUtil.getGroupTeams(themeDisplay.getScopeGroupId()).isEmpty()){ %>		
			<liferay-util:include page="/html/editactivity/comboTeams.jsp" servletContext="<%=getServletContext() %>">
				<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
				<liferay-util:param name="teamId" value='<%=(learnact!=null)?LearningActivityLocalServiceUtil.getExtraContentValue(actId,"team"):Long.toString(0) %>' />
			</liferay-util:include>
		<%}
		%>
		</liferay-ui:panel>
	
		<c:if test="${showcategorization}">
			<liferay-ui:panel title="categorization" collapsible="true" defaultState="closed">
				<aui:input name="tags" type="assetTags" />
				<aui:input name="categories" type="assetCategories" />
				</liferay-ui:panel>
		</c:if>
	</liferay-ui:panel-container>
	</aui:fieldset>
	
	<aui:button-row>
		<input type="button" value="<liferay-ui:message key="savechanges" />" onclick="javascript:validate()" >
		<aui:button  type="cancel" value="canceledition" />
	</aui:button-row>
</aui:form>
 <liferay-ui:success key="activity-saved-successfully" message="activity-saved-successfully" />