<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletURL"%>
<%@page import="com.liferay.taglib.portlet.RenderURLTag"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityResultLocalServiceImpl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestAnswer"%>
<%@page import="com.liferay.lms.service.TestAnswerLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.TestQuestion"%>
<%@page import="com.liferay.lms.service.TestQuestionLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.util.JavaScriptUtil"%>

<%@ include file="/init.jsp" %>

<%
long actId=ParamUtil.getLong(request,"actId",0);
boolean improve =ParamUtil.getBoolean(request, "improve",false);
long userId = themeDisplay.getUserId();
Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());


//Obtener si puede hacer un intento de mejorar el resultado.
boolean improving = false;
LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
if(result != null){
	int done =  LearningActivityTryLocalServiceUtil.getTriesCountByActivityAndUser(actId,userId);
	LearningActivity act=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	if(result.getResult() < 100
	   && !LearningActivityLocalServiceUtil.islocked(actId, userId)
	   && LearningActivityResultLocalServiceUtil.userPassed(actId, userId)
	   && done < act.getTries()){
		improving = true;
	}
}


if(actId==0)
{
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{
	LearningActivity activity=LearningActivityLocalServiceUtil.getLearningActivity(actId);
	long typeId=activity.getTypeId();
	
	if( typeId==0
			&& (!LearningActivityLocalServiceUtil.islocked(actId,userId)
			|| permissionChecker.hasPermission( activity.getGroupId(),LearningActivity.class.getName(), actId, ActionKeys.UPDATE)
			|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")))
	{
		%>

		<h2 class="description-title"><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
		<p><%=activity.getDescription(themeDisplay.getLocale()) %></p>
		
		<%
		if((!LearningActivityLocalServiceUtil.islocked(actId,userId)
			|| permissionChecker.hasPermission( activity.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE)
			|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")))
		{		
		if((!improve) && (LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId())))
		{
			request.setAttribute("learningActivity",activity);
			request.setAttribute("larntry",LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId));
			%>
			<liferay-util:include page="/html/execactivity/test/results.jsp" servletContext="<%=this.getServletContext() %>">
				<liferay-util:param value="<%=Long.toString(activity.getActId()) %>" name="actId"/>
			</liferay-util:include>
			<% 
		}
		else if (LearningActivityTryLocalServiceUtil.canUserDoANewTry(actId, userId) 
				|| permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(),actId, ActionKeys.UPDATE)
				|| permissionChecker.hasPermission(themeDisplay.getScopeGroupId(), "com.liferay.lms.model",themeDisplay.getScopeGroupId(),"ACCESSLOCK")
			    || improving )
		{
			String passwordParam = ParamUtil.getString(renderRequest, "password",StringPool.BLANK).trim();
			String password = GetterUtil.getString(LearningActivityLocalServiceUtil.getExtraContentValue(actId, "password"),StringPool.BLANK).trim();

						
			if((StringPool.BLANK.equals(password))||(passwordParam.equals(password)))
			{
			ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
			long activityTimestamp=0;
			long timestamp=0;
			
			LearningActivityTry learningTry = LearningActivityTryLocalServiceUtil.getLearningActivityTryNotFinishedByActUser(actId,userId);
			
			//Comprobar si tenemos un try sin fecha de fin, para continuar en ese try.
			if(learningTry == null)
			{
				learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
			}
			else
			{
				activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"timeStamp"));
				timestamp=activityTimestamp*1000 - (new Date().getTime() - learningTry.getStartDate().getTime());
			}
			
			if((activityTimestamp!=0)&&(timestamp<0)){
				request.setAttribute("learningActivity",activity);
				request.setAttribute("larntry",learningTry);
				%>
				<liferay-util:include page="/html/execactivity/test/expired.jsp" servletContext="<%=this.getServletContext() %>">
  					<liferay-util:param value="<%=Long.toString(activity.getActId()) %>" name="actId"/>
  				</liferay-util:include>  	
				<%
			}
			else {	

				
			List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actId);
			Object  [] arg =  new Object[]{activity.getPasspuntuation()};
			%>
			
			<% if (activity.getPasspuntuation()>0){ %><h3><liferay-ui:message key="execativity.test.try.pass.puntuation" arguments="<%=arg %>" /></h3><% } %>
			
			<portlet:actionURL name="correct" var="correctURL">
			<portlet:param name="actId" value="<%=Long.toString(actId) %>" ></portlet:param>
			<portlet:param name="latId" value="<%=Long.toString(learningTry.getLatId()) %>"></portlet:param>
			</portlet:actionURL>
									
			<script type="text/javascript">
			<!--

			<%
				if(activityTimestamp == 0){
					activityTimestamp = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(actId,"timeStamp"));
				}	
			
				if(activityTimestamp !=0){ 
					if(timestamp==0) {
				   		timestamp=activityTimestamp*1000 - (new Date().getTime() - learningTry.getStartDate().getTime());
					}
				
			%>

				AUI().ready('liferay-notice', 'collection', function(A) {

					var TestActivity = function(options) {
						var instance = this;
						instance.timeout=options.timeout||false;
						instance.warningText=options.warningText||'Timeout Warning: <span class="countdown-timer"/>';
						instance.expiredText=options.expiredText||'Text timeout';
						instance.onClose=options.onClose;
						
						instance.banner=null;
						if(instance.timeout) {
							instance.banner=new Liferay.Notice({content:instance.warningText,closeText:false,toggleText:false});
							instance.countdowntext=instance.banner.one('.countdown-timer');
							if(instance.countdowntext){
								instance.countdowntext.text(instance._formatTime(instance.timeout));
							}

							var interval=1000;
							instance.finishtime = new Date().getTime()+instance.timeout;

							instance._countdownTimer = setInterval(
									function() {

										var currentTimeout = instance.finishtime-new Date().getTime();

										if (currentTimeout > 0) {
											instance.countdowntext.text(instance._formatTime(currentTimeout));
										}
										else {
											instance.banner.html(instance.expiredText);
											instance.banner.toggleClass('popup-alert-notice').toggleClass('popup-alert-warning');
											
											if (instance._countdownTimer) {
												clearInterval(instance._countdownTimer);
											}

											if (instance.onClose) {
												instance.onClose();
											}
										}
									},
									interval
								);							

						}
					};

					TestActivity.prototype = {
						_formatNumber: function(num) {
							var instance = this;
					
							if (num <= 9) {
								num = '0' + num;
							}

							return num;
						},
						_formatTime: function(time) {
							var instance = this;

							time = Math.floor(time/1000);

							var hours = Math.floor(time/3600);
							time = time%3600;

							var minutes = Math.floor(time/60);
							time = time%60;

							var seconds = Math.floor(time);
							
							return A.Array.map([hours,minutes,seconds], instance._formatNumber).join(':');
							
						}

					};
					
					new TestActivity({timeout:<%=Long.toString(timestamp)%>,
									  warningText:'<liferay-ui:message key="execActivity.timeout.warning" />',
									  expiredText:'<liferay-ui:message key="execActivity.timeout" />',
									  onClose:function(){
										document.getElementById('<portlet:namespace/>formulario').submit();
										  }});

				});


				<% } %>
				
				function <%= renderResponse.getNamespace() %>formValidation(e){
					var returnValue=true;
					
					AUI().use('node', function(Y){
					    var questions = Y.all('div.question')

					    for(var i=0;i<questions.size();i++){
					        if(questions.item(i).one('div.answer input[type="radio"]:checked')==null){
					        	if(!confirm('<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(pageContext, "execativity.test.questions.without.response")) %>')) {

									if (e.target) targ = e.target.blur();
									else if (e.srcElement) targ = e.srcElement.blur();
							        
							        returnValue=false;  
					        	}
					        	 i=questions.size();
						    }
						}
					    
					});

					return returnValue; 
									    
				}
			//-->
			</script>

			<aui:form name="formulario" action="<%=correctURL %>" method="POST">
			<%
			long random = GetterUtil.getLong(LearningActivityLocalServiceUtil.getExtraContentValue(activity.getActId(),"random"));
			
			if(random!=0){
				questions = new ArrayList<TestQuestion>(questions);
				Collections.shuffle(questions);	
				if(random>questions.size()){
					random=questions.size();
				}
			}
			else{
				random=questions.size();
			}
			
			for(int index=0; index<random; index++) {
				TestQuestion question = questions.get(index);
				%>
				<div class="question">
				<aui:input type="hidden" name="question" value="<%=question.getQuestionId()%>"/>
				<div class="questiontext"><%=question.getText() %></div>
				<%
				List<TestAnswer> testAnswers= TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
				for(TestAnswer answer:testAnswers)
				{
					%>
					<div class="answer"><input type="radio" name="<portlet:namespace/>question_<%=question.getQuestionId()%>" value="<%=answer.getAnswerId() %>" ><%=answer.getAnswer() %>
					</div>
					<%
				}
				%>
				</div>
				<%
			}
			
				if (questions.size()>0){%>
				<aui:button type="submit" onClick='<%= "return  "+renderResponse.getNamespace() + "formValidation(event);" %>' ></aui:button>
				<% }  else {%>
					<p class="color_tercero negrita"><liferay-ui:message key="execativity.test.no.question" /></p>
				<% }  %>
			
			</aui:form>
			<%
			}
			}
			else{
				PortletURL passwordURL = renderResponse.createRenderURL();
				if(improve) {
					passwordURL.setParameter("improve", "true");		
				}			
			%>
			
				<aui:form action="<%=passwordURL%>" method="post">
					<aui:input type="text" name="password" label="execActivity.options.password" />
					<% if(!StringPool.BLANK.equals(passwordParam)){ %>
					<div class='portlet-msg-error'>
						<liferay-ui:message key="execActivity.bad.password" />
					</div>
					<% } %>
					<aui:button type="submit" />
					
				</aui:form>
			<%	
			}
		} 
		//Si no ha pasado el test, ni tiene mas intentos.
		else 
		{
			//LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			Object  [] arguments =  new Object[]{result.getResult()};
			Object  [] arg =  new Object[]{activity.getPasspuntuation()};
			%>
			<h2><%=activity.getTitle(themeDisplay.getLocale()) %></h2>
			<p><liferay-ui:message key="test-done" /></p>
			<p><liferay-ui:message key="your-result" arguments="<%=arguments %>" /></p>
			<p class="color_tercero negrita"><liferay-ui:message key="your-result-dont-pass"  arguments="<%=arg %>" /></p>
			<p><liferay-ui:message key="your-result-no-more-tries" /></p>
			<%
		}
		
		}
	}
}
%>