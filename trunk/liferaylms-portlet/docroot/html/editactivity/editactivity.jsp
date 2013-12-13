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
<portlet:actionURL var="saveactivityURL" name="saveActivity" />

<%
long moduleId=ParamUtil.getLong(request,"resModuleId",0);
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");
long typeId=ParamUtil.getLong(request, "type");
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[0], themeDisplay.getLocale());

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long actId=ParamUtil.getLong(request, "resId",0);
LearningActivity learnact=null;
if(request.getAttribute("activity")!=null)
{
	learnact=(LearningActivity)request.getAttribute("activity");
	typeId=learnact.getTypeId();
	moduleId=learnact.getModuleId();
}
else
{
	if(actId>0)
	{
		learnact=LearningActivityLocalServiceUtil.getLearningActivity(actId);
		typeId=learnact.getTypeId();
		moduleId=learnact.getModuleId();
	}
	
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
		<portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
		<portlet:param name="redirect" value="<%=redirect %>" />
		<portlet:param name="backURL" value="<%=backURL%>" />
	</portlet:actionURL>

	
	
<div class="acticons">
	<%
		if(larntype.hasEditDetails()){
			AssetRenderer  assetRenderer=larntype.getAssetRenderer(learnact);
			if(assetRenderer!=null) {
				String urlEdit = assetRenderer.getURLEdit((LiferayPortletRequest) renderRequest, (LiferayPortletResponse) renderResponse).toString();			
				Portlet urlEditPortlet = PortletLocalServiceUtil.getPortletById(HttpUtil.getParameter(urlEdit, "p_p_id",false));
				if(urlEditPortlet!=null) {
					PublicRenderParameter actIdPublicParameter = urlEditPortlet.getPublicRenderParameter("actId");
					if(actIdPublicParameter!=null) {
						urlEdit=HttpUtil.removeParameter(urlEdit,PortletQNameUtil.getPublicRenderParameterName(actIdPublicParameter.getQName()));
					}
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resId", Long.toString(learnact.getActId()));
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resModuleId");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"resModuleId", Long.toString(learnact.getModuleId()) );
					urlEdit=HttpUtil.removeParameter(urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails");
					urlEdit=HttpUtil.addParameter   (urlEdit, StringPool.UNDERLINE+urlEditPortlet.getPortletId()+StringPool.UNDERLINE+"actionEditingDetails", true);
				}
				%>
				<liferay-ui:icon image="edit" message="<%=larntype.getMesageEditDetails()%>" label="true" url="<%=urlEdit%>" />
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
				                title:'<%=LanguageUtil.get(pageContext, actId==0?"activity.creation":"activity.edition")+" "+ 
				                	LanguageUtil.get(pageContext, new LearningActivityTypeRegistry().getLearningActivityType(typeId).getName())%>'}, window.location.origin);
		}
		else {
			parent.postMessage(JSON.stringify({name:'setTitleActivity',
                							   moduleId:<%=Long.toString(moduleId)%>,
                							   actId:<%=Long.toString(actId)%>,
                							   title:'<%=LanguageUtil.get(pageContext,actId==0?"activity.creation":"activity.edition")+" "+ 
                							   		LanguageUtil.get(pageContext, new LearningActivityTypeRegistry().getLearningActivityType(typeId).getName())%>'}), window.location.origin);
		}
	}

	try{
		<% if(larntype.hasEditDetails()){ %>
			A.one('.taglib-icon').focus();
		<% } %>
	}catch (err){
		
	}
	
	var enabledStart = document.getElementById('<%=renderResponse.getNamespace()+renderResponse.getNamespace() %>startdate-enabledCheckbox').checked;
	if(enabledStart){
		A.all("#startDate").one("#container-calendar-icon").show();
		A.all("#startDate").one("#startDateSpan").removeClass('hide-calendar');
	}else  A.all("#startDate").one("#container-calendar-icon").hide();

	var enabledEnd = document.getElementById('<%=renderResponse.getNamespace()+renderResponse.getNamespace() %>stopdate-enabledCheckbox').checked; 
	if(enabledEnd){
		A.all("#endDate").one("#container-calendar-icon").show();
		A.all("#endDate").one("#endDateSpan").removeClass('hide-calendar');
	}else A.all("#endDate").one("#container-calendar-icon").hide();

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
        		required: '<liferay-ui:message key="title-required" />'
            },
        	<portlet:namespace />description: {
        		required: '<liferay-ui:message key="description-required" />'
            }
			<% if(larntype.isTriesConfigurable()) { %>
        	,<portlet:namespace />tries: {
        		required: '<liferay-ui:message key="editActivity.tries.required" />',
        		number: '<liferay-ui:message key="editActivity.tries.number" />',
        		range: '<liferay-ui:message key="editActivity.tries.range" />',       		
            }
			<% } if(larntype.isScoreConfigurable()) { %>
        	,<portlet:namespace />passpuntuation: {
        		required: '<liferay-ui:message key="editActivity.passpuntuation.required" />',
        		number: '<liferay-ui:message key="editActivity.passpuntuation.number" />',
        		range: '<liferay-ui:message key="editActivity.passpuntuation.range" />',       		
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
			<aui:option value="<%=theModule.getModuleId() %>" selected="<%=selected %>"><%=theModule.getTitle() %></aui:option>
		<% 
	}
%>

		</aui:select>

<%
	if(actId==0)
	{
	%>
	<aui:input type="hidden" name="type" value="<%=typeId %>"></aui:input>
	<% 
	}
	%>

		<aui:input name="title" label="title" defaultLanguageId="<%=renderRequest.getLocale().toString() %>">
		</aui:input>
		<div id="<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>Error" class="<%=(SessionErrors.contains(renderRequest, "title-required"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "title-required"))?
	    			LanguageUtil.get(pageContext,"title-required"):StringPool.BLANK %>
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
	    
		<aui:field-wrapper label="description">
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
		<aui:field-wrapper label="editactivity.mandatory" cssClass="editactivity-mandatory-field">
			<aui:input label="editactivity.mandatory.yes" type="radio" name="weightinmodule" value="1" checked="<%= mandatory %>" inlineField="true" />
			<aui:input label="editactivity.mandatory.no" type="radio" name="weightinmodule" value="0" checked="<%= !mandatory %>" inlineField="true" />
		</aui:field-wrapper>
	 <liferay-ui:panel-container extended="false" persistState="false">
	 <%
	 boolean showSpecificPanel = larntype.isTriesConfigurable() || larntype.isScoreConfigurable() || larntype.isFeedbackCorrectConfigurable() || 
	 								larntype.isFeedbackNoCorrectConfigurable() || larntype.getExpecificContentPage()!=null;
	 if(showSpecificPanel){
	 
		 String defaultState="open";
		 if(actId>0)
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
		
		<aui:input size="5" name="tries" label="tries" value="<%=Long.toString(tries) %>">
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
			long score=larntype.getDefaultScore();
			if(learnact!=null)
			{
				score=learnact.getPasspuntuation();
			}
			boolean disabled = false;
			boolean isOmniadmin = false;
			
			try{
				isOmniadmin  = themeDisplay.getPermissionChecker().isOmniadmin()|| permissionChecker.hasPermission(learnact.getGroupId(), LearningActivity.class.getName(),learnact.getActId(),"UPDATE_ACTIVE");
				disabled = LearningActivityTryLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(LearningActivityTry.class).add(PropertyFactoryUtil.forName("actId").eq(learnact.getActId()))) != 0;
			}catch(Exception e){
				
			}
			
			if(isOmniadmin ){
				disabled = false;
			}
		%>
		<aui:input size="5" name="passpuntuation" label="passpuntuation" type="text" value="<%=Long.toString(score) %>" disabled="<%=disabled %>" helpMessage="<%=LanguageUtil.get(pageContext,\"editActivity.passpuntuation.help\")%>">
		</aui:input>
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
		<aui:input name="feedbackCorrect" label="feedbackCorrect" value="<%=feedbacCorrect %>" helpMessage="feedbackCorrect.helpMessage"></aui:input>	
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
		<aui:input name="feedbackNoCorrect" label="feedbackNoCorrect" value="<%=feedbacNoCorrect %>" helpMessage="feedbackNoCorrect.helpMessage"></aui:input>	
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
			    	var enabled = document.getElementById('<%=renderResponse.getNamespace()+renderResponse.getNamespace() %>startdate-enabledCheckbox').checked; 

		    		var selector = 'form[name="<%=renderResponse.getNamespace() %>fm"]';
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startDay"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startMon"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startYear"]').set('disabled', !enabled);
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startMin"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>startHour"]').set('disabled', !enabled);
		    		
		    		if(enabled) {
		    			A.all("#startDate").one("#container-calendar-icon").show();
		    			A.all("#startDate").one("#startDateSpan").removeClass('hide-calendar');
		    		}else {
		    			A.all("#startDate").one("#container-calendar-icon").hide();
		    			A.all("#startDate").one("#startDateSpan").addClass('hide-calendar');
		    		}

		    	});
		    }
		    
		    function setStopDateState(){
		    	AUI().use('node',function(A) {
			    	var enabled = document.getElementById('<%=renderResponse.getNamespace()+renderResponse.getNamespace() %>stopdate-enabledCheckbox').checked; 

		    		var selector = 'form[name="<%=renderResponse.getNamespace() %>fm"]';
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopDay"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopMon"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopYear"]').set('disabled', !enabled);
		    		
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopMin"]').set('disabled', !enabled);
		    		A.one(selector).one('select[name="<%=renderResponse.getNamespace() %>stopHour"]').set('disabled', !enabled);
		    		
		    		if(enabled) {
		    			A.all("#endDate").one("#container-calendar-icon").show();
		    			A.all("#endDate").one("#endDateSpan").removeClass('hide-calendar');
		    		}else {
		    			A.all("#endDate").one("#container-calendar-icon").hide();
		    			A.all("#endDate").one("#endDateSpan").addClass('hide-calendar');
		    		}

		    	});
		    }

	    </script>
		<div id="startDate">
			<aui:field-wrapper label="start-date">
				<aui:input id="<%=renderResponse.getNamespace()+\"startdate-enabled\" %>" name="startdate-enabled" checked="<%=learnact != null && learnact.getStartdate() != null  %>" type="checkbox" label="editActivity.startdate.enabled" onClick="setStarDateState();" helpMessage="editActivity.startdate.enabled.help"  ignoreRequestValue="true" />
				<span id="startDateSpan" class="hide-calendar">
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"  dayParam="startDay" monthParam="startMon" disabled="<%=learnact == null || learnact.getStartdate() == null  %>"
					 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
				</span>
				<liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>" disabled="<%=learnact == null || learnact.getStartdate() == null  %>"></liferay-ui:input-time>
			</aui:field-wrapper>
		</div>
		
		<div id="endDate">
			<aui:field-wrapper label="end-date">
				<aui:input id="<%=renderResponse.getNamespace()+\"stopdate-enabled\" %>" name="stopdate-enabled" checked="<%=learnact != null && learnact.getEnddate()!= null  %>" type="checkbox" label="editActivity.stopdate.enabled" onClick="setStopDateState();" helpMessage="editActivity.stopdate.enabled.help"  ignoreRequestValue="true" />
				<span id="endDateSpan" class="hide-calendar">
					<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon" disabled="<%=learnact == null || learnact.getEnddate() == null  %>"
					 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
				</span>
				<liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>" disabled="<%=learnact == null || learnact.getEnddate() == null  %>"></liferay-ui:input-time></br>
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
	<liferay-ui:panel title="categorization" collapsible="true" defaultState="closed">
		<aui:input name="tags" type="assetTags" />
		<aui:input name="categories" type="assetCategories" />
		</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
	
	<aui:button-row>
		
		
		<script type="text/javascript">
		<!--
		
		Liferay.provide(
		        window,
		        '<portlet:namespace />closeWindow',
		        function() {
			        
					if ((!!window.postMessage)&&(window.parent != window)) {
						if (!window.location.origin){
							window.location.origin = window.location.protocol+"//"+window.location.host;
						}
						
						if(AUI().UA.ie==0) {
							parent.postMessage({name:'closeActivity',
								                moduleId:<%=Long.toString(moduleId)%>,
								                actId:<%=Long.toString(actId)%>}, window.location.origin);
						}
						else {
							parent.postMessage(JSON.stringify({name:'closeActivity',
				                							   moduleId:<%=Long.toString(moduleId)%>,
				                							   actId:<%=Long.toString(actId)%>}), window.location.origin);
						}
					}
					else {
						window.location.href='<portlet:renderURL />';
					}
		        }
		    );
		    
		//-->
		</script>
		<aui:button type="submit" value="savechanges"></aui:button>
		<aui:button onClick="<%=renderResponse.getNamespace()+\"closeWindow()\" %>" type="cancel" value="canceledition"/>
		
	</aui:button-row>
</aui:form>
 <liferay-ui:success key="activity-saved-successfully" message="activity-saved-successfully" />