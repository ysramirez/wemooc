<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.model.LmsPrefs"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationTypeRegistry"%>
<%@page import="com.liferay.lms.learningactivity.calificationtype.CalificationType"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LmsPrefsLocalServiceUtil"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.portal.kernel.util.UnicodeFormatter"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.CourseServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>

<%@ include file="/init.jsp" %>

<portlet:actionURL var="savecourseURL" name="saveCourse" />
<portlet:renderURL var="cancel" />
<liferay-ui:error key="title-required" message="title-required" />
<liferay-ui:error key="title-empty" message="title-empty" />
<liferay-ui:error key="title-repeated" message="title-repeated" />
<liferay-ui:error key="max-users-violated" message="max-users-violated" />
	
	<%
	String site = PropsUtil.get("lms.site.types");
	Set<Integer> sites = null;
	if(site!=null&&!"".equals(site)){
		sites = new HashSet<Integer>();
		String[] ssites = site.split(",");
		for(int i=0;i<ssites.length;i++){
			try{
				sites.add(Integer.valueOf(ssites[i]));
			}catch(Exception e){}
		}
	}
	
	
	String maxUsersError= ParamUtil.getString(request,"maxUsersError");
	if(SessionErrors.contains(renderRequest, "newCourseErrors")) { %>
		<div class="portlet-msg-error">
			<% 
				List<String> errors = (List<String>)SessionErrors.get(renderRequest, "newCourseErrors");
			   if(errors.size()==1) {
				  %><%=errors.get(0) %><%
			   }	
			   else {
			%>
				<ul>
				<% for(String error : errors){ %>
				 	<li><%=error %></li>
				<% } %>
				</ul>
			<% }
			%>
		</div>
	<% }
	
	if((maxUsersError!=null&&!"".equals(maxUsersError))){
	%>
		<div class="portlet-msg-error"><%=maxUsersError %></div>
	<%} %>


<%

String publishPermission="PUBLISH";
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");
long courseId=ParamUtil.getLong(request, "courseId",0);
Course course=null;
if(request.getAttribute("course")!=null)
{
	course=(Course)request.getAttribute("course");
}
else
{
	if(courseId>0)
	{
		course=CourseLocalServiceUtil.getCourse(courseId);
	}
}	


String description=ParamUtil.get(request, "description", "");
String icon = ParamUtil.get(request, "icon", "0");
SimpleDateFormat formatDay = new SimpleDateFormat("dd");
formatDay.setTimeZone(timeZone);
SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
formatMonth.setTimeZone(timeZone);
SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
formatYear.setTimeZone(timeZone);
SimpleDateFormat formatHour = new SimpleDateFormat("HH");
formatHour.setTimeZone(timeZone);
SimpleDateFormat formatMin = new SimpleDateFormat("mm");
formatMin.setTimeZone(timeZone);
Date today=new Date(System.currentTimeMillis());
int startDay=ParamUtil.getInteger(request, "startDay", Integer.parseInt(formatDay.format(today)));
int startMonth=ParamUtil.getInteger(request, "startMonth", Integer.parseInt(formatMonth.format(today))-1);
int startYear=ParamUtil.getInteger(request, "startYear", Integer.parseInt(formatYear.format(today)));
int startHour=ParamUtil.getInteger(request, "startHour", Integer.parseInt(formatHour.format(today)));
int startMin=ParamUtil.getInteger(request, "startMin", Integer.parseInt(formatMin.format(today)));
int endDay=ParamUtil.getInteger(request, "stopDay", Integer.parseInt(formatDay.format(today)));
int endMonth=ParamUtil.getInteger(request, "stopMonth", Integer.parseInt(formatMonth.format(today))-1);
int endYear=ParamUtil.getInteger(request, "stopYear", Integer.parseInt(formatYear.format(today))+1);
int endHour=ParamUtil.getInteger(request, "stopHour", Integer.parseInt(formatHour.format(today)));
int endMin=ParamUtil.getInteger(request, "stopMin", Integer.parseInt(formatMin.format(today)));
String summary="";
AssetEntry entry=null;
boolean visibleencatalogo=false;
int type = GroupConstants.TYPE_SITE_OPEN;
long maxUsers = 0;
Group groupCreated = null;
if(course!=null)
{
	groupCreated = GroupLocalServiceUtil.getGroup(course.getGroupCreatedId());
	entry=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(),course.getCourseId());
	visibleencatalogo=entry.getVisible();
	summary=entry.getSummary();
	courseId=course.getCourseId();
	description=course.getDescription(themeDisplay.getLocale());
	icon = course.getIcon()+"";
	startDay=Integer.parseInt(formatDay.format(course.getStartDate()));
	startMonth=Integer.parseInt(formatMonth.format(course.getStartDate()))-1;
	startYear=Integer.parseInt(formatYear.format(course.getStartDate()));
	startHour=Integer.parseInt(formatHour.format(course.getStartDate()));
	startMin=Integer.parseInt(formatMin.format(course.getStartDate()));
	endDay=Integer.parseInt(formatDay.format(course.getEndDate()));
	endMonth=Integer.parseInt(formatMonth.format(course.getEndDate()))-1;
	endYear=Integer.parseInt(formatYear.format(course.getEndDate()));
	endHour=Integer.parseInt(formatHour.format(course.getEndDate()));
	endMin=Integer.parseInt(formatMin.format(course.getEndDate()));
	type=course.getStatus(); //TODO
	maxUsers=course.getMaxusers();
	%>
	<aui:model-context bean="<%= course %>" model="<%= Course.class %>" />
	<%
}
else
{
	%>
	<aui:model-context  model="<%= Course.class %>" />
	<%
}
%>

<aui:form name="fm" action="<%=savecourseURL%>"  method="post" enctype="multipart/form-data">

	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="courseId" type="hidden" value="<%=courseId %>"/>
	<aui:input name="title" label="title" id="title"/>
	<aui:input name="friendlyURL" label="FriendlyURL" type="hidden" > <%=groupCreated!=null?groupCreated.getFriendlyURL():"" %> </aui:input>
	
	<aui:field-wrapper label="description" name="description">
			<liferay-ui:input-editor name="description" width="100%" />
			<aui:input name="description" type="hidden"/>
			<script type="text/javascript">
    		    function <portlet:namespace />initEditor() { return "<%= UnicodeFormatter.toString(description) %>"; }
    		</script>
	</aui:field-wrapper>
	
	<c:if test="<%= permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),  Course.class.getName(),0,publishPermission) %>">
		<aui:input type="checkbox" name="visible" label="published-in-catalog" value="<%=visibleencatalogo %>" />
	</c:if>
	
	<% boolean requiredCourseIcon = GetterUtil.getBoolean(PropsUtil.get("lms.course.icon.required"), false); %>
	<aui:input type="hidden" name="icon" >
		<% if (requiredCourseIcon) { %>
			<aui:validator errorMessage="course-icon-required" name="customRequiredCourseIcon1">(function(val, fieldNode, ruleValue) {return (AUI().one('#<portlet:namespace/>fileName').val() != 0 || val != null);})()</aui:validator>
		<% } %>
	</aui:input> 
	<liferay-ui:error key="error-file-size" message="error-file-size" />
	<script type="text/javascript">
	Liferay.provide(
		window,
		'<portlet:namespace/>toggleInputLogo',
		function() {
			var A = AUI();
			var discardCheckbox = A.one('#<portlet:namespace/>discardLogoCheckbox');
			var fileInput = A.one('#<portlet:namespace/>fileName');
			var iconCourse = A.one('#<portlet:namespace/>icon_course');
			if (discardCheckbox.attr('checked')) {
				fileInput.val('');
				fileInput.hide();
				iconCourse.hide();
			} else {
				fileInput.show();
				iconCourse.show();
			}
		},
		['node']
	);
	</script>
	<aui:field-wrapper cssClass="wrapper-icon-course">
		<% if (course != null && course.getIcon() != 0 && !requiredCourseIcon) { %>
				<aui:input type="checkbox" name="discardLogo" label="discard-course-icon" onClick='<%= renderResponse.getNamespace()+"toggleInputLogo()" %>'/>
			<% } %>
			<aui:input name="fileName" label="image" id="fileName" type="file" value="" >
				<aui:validator name="acceptFiles">'jpg, jpeg, png, gif'</aui:validator>
				<% if (requiredCourseIcon) { %>
					<aui:validator errorMessage="course-icon-required" name="customRequiredCourseIcon1">(function(val, fieldNode, ruleValue) {return (AUI().one('#<portlet:namespace/>icon').val() || val != null);})()</aui:validator>
				<% } %>
			</aui:input>
		<%	if(course != null && course.getIcon() != 0) {
			FileEntry image_=DLAppLocalServiceUtil.getFileEntry(course.getIcon());	%>
			<div class="container_ico_course">
				<img id="<portlet:namespace/>icon_course" alt="" class="ico_course" src="<%= DLUtil.getPreviewURL(image_, image_.getFileVersion(), themeDisplay, StringPool.BLANK) %>"/>
			</div>
			
		<%} %>
	</aui:field-wrapper>
	<liferay-ui:error key="course-icon-required" message="course-icon-required" />
	<liferay-ui:error key="error_number_format" message="error_number_format" />
	
	<aui:input type="textarea" cols="100" rows="4" name="summary" label="summary" value="<%=summary %>"/>
	<%
	List<Long> courseEvalIds = ListUtil.toList(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getCourseevals(),",",0L));
	CourseEvalRegistry cer=new CourseEvalRegistry();
	if(courseEvalIds.size()>1)
	{%>
		<aui:select name="courseEvalId" label="course-correction-method" helpMessage="<%=LanguageUtil.get(pageContext,\"course-correction-method-help\")%>">
		<%
		//String[]courseEvals = LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getCourseevals().split(",");
		for(Long ce:courseEvalIds)
		{
			CourseEval cel = cer.getCourseEval(ce);
			boolean selected=false;
			if(course!=null&&course.getCourseEvalId()==ce)
			{
				selected=true;
			}
			
			%>
			<aui:option value="<%=String.valueOf(ce)%>" selected="<%=selected %>"><liferay-ui:message key="<%=cel.getName() %>" /></aui:option>
			<%
		}
		%>
		</aui:select>
	<%
	}
	else{
		CourseEval cel = cer.getCourseEval(courseEvalIds.get(0));
		%>
		<aui:input name="courseEvalId" value="<%=cel.getTypeId()%>" type="hidden"/>
	<%}
	if(course==null)
	{
		String[] layusprsel=null;
		if(renderRequest.getPreferences().getValue("courseTemplates", null)!=null&&renderRequest.getPreferences().getValue("courseTemplates", null).length()>0)
		{
				layusprsel=renderRequest.getPreferences().getValue("courseTemplates", "").split(",");
		}

		String[] lspist=LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getLmsTemplates().split(",");
		if(layusprsel!=null &&layusprsel.length>0)
		{
			lspist=layusprsel;

		}
		if(lspist.length>1){
		%>
			<aui:select name="courseTemplate" label="course-template">
			<%
			for(String lspis:lspist)
			{
				LayoutSetPrototype lsp=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspis));
				%>
				<aui:option value="<%=lsp.getLayoutSetPrototypeId() %>" ><%=lsp.getName(themeDisplay.getLocale()) %></aui:option>
				<%
			}
			%>
			</aui:select>
		<%
		}
		else{
			LayoutSetPrototype lsp=LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(Long.parseLong(lspist[0]));
		%>
			<aui:input name="courseTemplate" value="<%=lsp.getLayoutSetPrototypeId()%>" type="hidden"/>
		<%}
	}
	List <Long> califications = ListUtil.toList(StringUtil.split(LmsPrefsLocalServiceUtil.getLmsPrefsIni(themeDisplay.getCompanyId()).getScoretranslators(),",",0L));	
	CalificationTypeRegistry cal = new CalificationTypeRegistry();
	if(califications.size()>1){
		%>
			<aui:select name="calificationType" label="calificationType">
		<%
		for(Long ct:califications){
			boolean selected = false;
			CalificationType ctype = cal.getCalificationType(ct);
			if((course == null && PropsUtil.get("lms.calification.default.type").equals(String.valueOf(ct))) || (course != null && ct == course.getCalificationType()))
				selected = true;
			%>
				<aui:option value="<%=String.valueOf(ct)%>"  selected="<%=selected %>"><liferay-ui:message key="<%=ctype.getTitle(themeDisplay.getLocale()) %>" /></aui:option>
			<%
		}
		%>
			</aui:select>
		<%
	}
	else{
		CalificationType ctype = cal.getCalificationType(califications.get(0));
		%>
		<aui:input name="calificationType" value="<%=ctype.getTypeId()%>" type="hidden"/>
	<%}
	
	boolean showInscriptionDate = GetterUtil.getBoolean(renderRequest.getPreferences().getValues("showInscriptionDate", new String[]{StringPool.TRUE})[0],true);

	%>
<liferay-ui:panel-container extended="false"  persistState="false">
	<liferay-ui:panel title="lms-inscription-configuration" collapsible="true" defaultState="closed">
		<aui:field-wrapper name="inscriptionDate" label="start-inscription-date" cssClass="<%=(showInscriptionDate)?StringPool.BLANK:\"aui-helper-hidden\" %>">
			<aui:input type="hidden" name="inscriptionDate"/>
			<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>"  dayParam="startDay" monthParam="startMon"
					 yearParam="startYear"  yearNullable="false" dayNullable="false" monthNullable="false" yearValue="<%=startYear %>" monthValue="<%=startMonth %>" dayValue="<%=startDay %>"></liferay-ui:input-date>
			<liferay-ui:input-time minuteParam="startMin" amPmParam="startAMPM" hourParam="startHour" hourValue="<%=startHour %>" minuteValue="<%=startMin %>"></liferay-ui:input-time>
		</aui:field-wrapper>
		<aui:field-wrapper name="endInscriptionDate" label="end-inscription-date"  cssClass="<%=(showInscriptionDate)?StringPool.BLANK:\"aui-helper-hidden\" %>">
			<aui:input type="hidden" name="endInscriptionDate"/>
			<liferay-ui:input-date yearRangeEnd="<%=LiferaylmsUtil.defaultEndYear %>" yearRangeStart="<%=LiferaylmsUtil.defaultStartYear %>" dayParam="stopDay" monthParam="stopMon"
					 yearParam="stopYear"  yearNullable="false" dayNullable="false" monthNullable="false"  yearValue="<%=endYear %>" monthValue="<%=endMonth %>" dayValue="<%=endDay %>"></liferay-ui:input-date>
			 <liferay-ui:input-time minuteParam="stopMin" amPmParam="stopAMPM" hourParam="stopHour"  hourValue="<%=endHour %>" minuteValue="<%=endMin %>"></liferay-ui:input-time></br>
		</aui:field-wrapper>
	
			<c:choose>
	      		<c:when test="<%=sites!=null&&sites.size()==1%>">
					<aui:input name="type" type="hidden" value="<%= sites.toArray()[0] %>" />
	      		</c:when>
	      		<c:otherwise>
					<aui:select name="type" label="registration-type" helpMessage="<%=LanguageUtil.get(pageContext,\"type-method-help\")%>">
						<c:choose>
				    		<c:when test="<%=groupCreated==null%>">
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_OPEN)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_OPEN %>" ><liferay-ui:message key="public" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_PRIVATE)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_PRIVATE %>" ><liferay-ui:message key="private" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_RESTRICTED)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_RESTRICTED %>" ><liferay-ui:message key="restricted" /></aui:option>
								</c:if>
				      		</c:when>
				      		<c:otherwise>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_OPEN)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_OPEN %>" selected="<%=groupCreated.getType()==GroupConstants.TYPE_SITE_OPEN %>" ><liferay-ui:message key="public" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_PRIVATE)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_PRIVATE %>" selected="<%=groupCreated.getType()==GroupConstants.TYPE_SITE_PRIVATE %>" ><liferay-ui:message key="private" /></aui:option>
								</c:if>
				      			<c:if test="<%=sites==null||(sites.size()>0&&sites.contains(GroupConstants.TYPE_SITE_RESTRICTED)) %>">
									<aui:option value="<%=GroupConstants.TYPE_SITE_RESTRICTED %>" selected="<%=groupCreated.getType()==GroupConstants.TYPE_SITE_RESTRICTED %>" ><liferay-ui:message key="restricted" /></aui:option>
								</c:if>
				      		</c:otherwise>
						</c:choose>
					</aui:select>
	      		</c:otherwise>
			</c:choose>
		
		<aui:input name="maxUsers" label="num-of-users" type="text" value="<%=maxUsers %>" helpMessage="<%=LanguageUtil.get(pageContext,\"max-users-method-help\")%>" />  
	</liferay-ui:panel>
	<liferay-ui:panel title="categorization" collapsible="true" defaultState="closed">
	<liferay-ui:custom-attributes-available className="<%= Course.class.getName() %>">
	<liferay-ui:custom-attribute-list 
		className="<%=com.liferay.lms.model.Course.class.getName()%>" classPK="<%=courseId %>" editable="true" label="true"></liferay-ui:custom-attribute-list>
	</liferay-ui:custom-attributes-available>
	<aui:input name="tags" type="assetTags" />
	<aui:input name="categories" type="assetCategories" />
	</liferay-ui:panel>
	</liferay-ui:panel-container>
	<aui:button-row>
		<aui:button type="submit"></aui:button>							
		<aui:button onClick="<%=cancel %>" type="cancel" />
	</aui:button-row>
</aui:form>

<% themeDisplay.setIncludeServiceJs(true); %>
<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>

<script type="text/javascript">
function <portlet:namespace />checkduplicate(val, field) {
	var courseId = document.getElementById('<portlet:namespace />courseId').value;
   	return !Liferay.Service.Lms.Course.existsCourseName(
   		{
   			companyId: themeDisplay.getCompanyId(),
   			courseId: (courseId > 0 ? courseId : null),
   			groupName: val,
   			serviceParameterTypes: JSON.stringify(['java.lang.Long', 'java.lang.Long', 'java.lang.String'])
   		}
   	);
}
</script>

<aui:script use="liferay-form">
	Liferay.Form.register(
	     {
	        id: '<portlet:namespace />fm',
	        
	        fieldRules: [
	            {
	                 body: function(val, fieldNode, ruleValue) {var res = <portlet:namespace />checkduplicate(val, fieldNode); console.log(res); return res; },
	                 custom: true,
	                 errorMessage: '<liferay-ui:message key="title-repeated" />',
	                 fieldName: '<portlet:namespace />title_'+Liferay.ThemeDisplay.getLanguageId(),
	                 validatorName: 'checkduplicate1'
	            },
	            
	        ]
	    }
	);
</aui:script>