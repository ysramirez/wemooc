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

<%@ include file="/init.jsp" %>
 <liferay-ui:success key="activity-saved-successfully" message="activity-saved-successfully" />
<portlet:actionURL var="saveactivityURL" name="saveActivity" />

<%
long moduleId=ParamUtil.getLong(request,"resModuleId",0);
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");
long typeId=ParamUtil.getLong(request, "typeId");
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

String description="";
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
<liferay-ui:header title="<%=typeName%>"></liferay-ui:header>
<%
if(learnact!=null)
{
	actId=learnact.getActId();
	description=learnact.getDescription(themeDisplay.getLocale());
	startDay=Integer.parseInt(formatDay.format(learnact.getStartdate()));
	startMonth=Integer.parseInt(formatMonth.format(learnact.getStartdate()))-1;
	startYear=Integer.parseInt(formatYear.format(learnact.getStartdate()));
	startHour=Integer.parseInt(formatHour.format(learnact.getStartdate()));
	startMin=Integer.parseInt(formatMin.format(learnact.getStartdate()));
	endDay=Integer.parseInt(formatDay.format(learnact.getEnddate()));
	endMonth=Integer.parseInt(formatMonth.format(learnact.getEnddate()))-1;
	endYear=Integer.parseInt(formatYear.format(learnact.getEnddate()));
	endHour=Integer.parseInt(formatHour.format(learnact.getEnddate()));
	endMin=Integer.parseInt(formatMin.format(learnact.getEnddate()));
	%>
	
	<portlet:actionURL name="deleteMyTries" var="deleteMyTriesURL">
		<portlet:param name="resId" value="<%=Long.toString(learnact.getActId()) %>" />
		<portlet:param name="redirect" value="<%=redirect %>" />
		<portlet:param name="backURL" value="<%=backURL%>" />
	</portlet:actionURL>

	<liferay-ui:header title="<%=learnact.getTitle(themeDisplay.getLocale()) %>"></liferay-ui:header>
	
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
					
				<liferay-ui:icon-delete label="false" url="<%=deleteMyTriesURL.toString()%>" />
				<a href="<%=deleteMyTriesURL.toString()%>"><liferay-ui:message key="delete-mi-tries"></liferay-ui:message></a>
				
				<%
			}
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

AUI().ready('node-base' ,'aui-form-validator', 'aui-overlay-context-panel', function(A) {


	var rules = {			
			<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>: {
				required: true
			},
        	<portlet:namespace />description: {
        		required: true
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

		<aui:select label="module" name="resModuleId" onChange="<%=renderResponse.getNamespace()+\"reloadComboActivities(this.options[this.selectedIndex].value);\" %>">
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

		<aui:input name="title" label="title">
		</aui:input>
		<div id="<portlet:namespace />title_<%=renderRequest.getLocale().toString()%>Error" class="<%=(SessionErrors.contains(renderRequest, "title-required"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "title-required"))?
	    			LanguageUtil.get(pageContext,"title-required"):StringPool.BLANK %>
	    </div>

	
		<aui:field-wrapper label="start-date">
			<liferay-ui:input-date yearRangeEnd="2020" yearRangeStart="2012"  dayParam="startDay" monthParam="startMon"
				 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>"></liferay-ui:input-time>
		</aui:field-wrapper>
		<aui:field-wrapper label="end-date">
			<liferay-ui:input-date yearRangeEnd="2020" yearRangeStart="2012" dayParam="stopDay" monthParam="stopMon"
				 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>"></liferay-ui:input-time></br>
		</aui:field-wrapper>
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
		%>
		<aui:input size="5" name="passpuntuation" label="passpuntuation" type="text" value="<%=Long.toString(score) %>">
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
		
				
		<liferay-util:include page="/html/editactivity/comboActivities.jsp" servletContext="<%=getServletContext() %>">
			<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
			<liferay-util:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
			<liferay-util:param name="precedence" value="<%=Long.toString((learnact!=null)?learnact.getPrecedence():0) %>" />
		</liferay-util:include>
		
		<%
		if(larntype.isFeedbackCorrectConfigurable())
		{
			String  feedbacCorrect=larntype.getDefaultFeedbackCorrect();
			if(learnact!=null)
			{
				feedbacCorrect=learnact.getFeedbackCorrect();
			}
		%>	
		<aui:input name="feedbackCorrect" label="feedbackCorrect" value="<%=feedbacCorrect %>" ></aui:input>	
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="feedbackCorrect" value="<%=larntype.getDefaultFeedbackCorrect() %>" />
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
		<aui:input name="feedbackNoCorrect" label="feedbackNoCorrect" value="<%=feedbacNoCorrect %>" ></aui:input>	
		<%
		}
		else
		{
			%>
			<aui:input type="hidden" name="feedbackNoCorrect" value="<%=larntype.getDefaultFeedbackNoCorrect() %>" />
			<% 
		}
		
		%>

		
		<% if(larntype.getExpecificContentPage()!=null) { %>
			<liferay-util:include page="<%=larntype.getExpecificContentPage() %>" servletContext="<%=getServletContext() %>" portletId="<%= larntype.getPortletId() %>">
				<liferay-util:param name="resId" value="<%=Long.toString(actId) %>" />
				<liferay-util:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
			</liferay-util:include>	
		<% } %>


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

		<aui:input name="tags" type="assetTags" />
		
		<aui:input name="categories" type="assetCategories" />
		
	</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit" value="savechanges"></aui:button>
		
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
						parent.postMessage({name:'closeActivity',
							                moduleId:<%=Long.toString(moduleId)%>,
							                actId:<%=Long.toString(actId)%>}, window.location.origin);
					}
					else {
						window.location.href='<portlet:renderURL />';
					}
		        }
		    );
		    
		//-->
		</script>
		<aui:button onClick="<%=renderResponse.getNamespace()+\"closeWindow()\" %>" type="cancel" value="canceledition"/>
	</aui:button-row>
</aui:form>
 <liferay-ui:success key="activity-saved-successfully" message="activity-saved-successfully" />