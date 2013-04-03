<%@page import="com.liferay.util.JavaScriptUtil"%>
<%@page import="com.liferay.lms.learningactivity.TaskEvaluationLearningActivityType"%>
<%@page import="java.util.Collection"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.util.StringBundler"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.model.Role"%>
<%@page import="com.liferay.portal.model.RoleConstants"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.DocumentException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>

<%@ include file="/init.jsp" %>

<%
	long actId = ParamUtil.getLong(request,"actId",0);
	
	if(actId==0)
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
		Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
		LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
		long typeId=learningActivity.getTypeId();
				
		if(typeId==8&&(!LearningActivityLocalServiceUtil.islocked(actId,themeDisplay.getUserId())||
				permissionChecker.hasPermission(
				learningActivity.getGroupId(),
				LearningActivity.class.getName(),
				actId, ActionKeys.UPDATE)||permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),"ACCESSLOCK")))
		{
			
			boolean isTeacher=false;
			
			for(Role role : RoleLocalServiceUtil.getUserGroupRoles(themeDisplay.getUserId(), themeDisplay.getScopeGroupId())){
				if("courseTeacher".equals(role.getName())) {
					isTeacher=true;
					break;
				}
			}
				
			if(isTeacher==false){
				for(Role role : themeDisplay.getUser().getRoles()){
					if(RoleConstants.ADMINISTRATOR.equals(role.getName())) {
						isTeacher=true;
						break;
					}
				}
			}
			
			List<Module> moduleList = (List<Module>)ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
			StringBundler sbTitles = new StringBundler(2 * moduleList.size() - 1);
			StringBundler sbModuleId = new StringBundler(2 * moduleList.size() - 1);	

			for (int i = 0; i < moduleList.size(); i++) {
				Module module = moduleList.get(i);
			
				sbTitles.append(module.getTitle(themeDisplay.getLocale()));
				sbModuleId.append(module.getModuleId());

			
				if ((i + 1) != moduleList.size()) {
					sbTitles.append(StringPool.COMMA);
					sbModuleId.append(StringPool.COMMA);
				}
			}
			
			long moduleId=ParamUtil.get(request, "moduleId",moduleList.get(0).getModuleId());
			
			Map<Long,Long> activities= new HashMap<Long,Long>();
			
			if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().length()!=0)) {
				try{
					
					Iterator<Element> elements = SAXReaderUtil.read(learningActivity.getExtracontent()).getRootElement().elementIterator();
					
					while(elements.hasNext()){
						Element element =elements.next();
						if("activities".equals(element.getName())){
							Iterator<Element> activitiesElement = element.elementIterator();
							while(activitiesElement.hasNext()) {
								Element activity =activitiesElement.next();
								if(("activity".equals(activity.getName()))&&(activity.attribute("id")!=null)&&(activity.attribute("id").getValue().length()!=0)){
									try{
										activities.put(Long.valueOf(activity.attribute("id").getValue()),Long.valueOf(activity.getText()));
									}
									catch(NumberFormatException e){}
								}
							}
							
						}
					}
				}
				catch(DocumentException e){}
				
			}
		
			
			LearningActivityResult result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, themeDisplay.getUserId());
			Object  [] arguments=null;
			
			if(result!=null){	
				arguments =  new Object[]{result.getResult()};
			}
			
			learningActivity.getExtracontent();

			
		%>
			<div class="evaluationtaskactivity view">
			
			<div id="<portlet:namespace />activityPopup" style="display: none;">
				<%@ include file="popups/activity.jspf" %>
			</div>
			
			<portlet:resourceURL var="updateEvaluationURL" >   
				<portlet:param name="ajaxAction" value="updateEvaluation" />                 
	        </portlet:resourceURL>
			
			<script type="text/javascript">
		    <!--

		    	AUI().ready('node-base' ,'aui-form-validator', function(A) {
			    	
		    		A.mix(
		    				YUI.AUI.defaults.FormValidator.STRINGS,
		    				{
		    					required:'<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(themeDisplay.getLocale(), "execActivity.options.error.weight")) %>',
		    					weightRule: '<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(themeDisplay.getLocale(), "execActivity.options.error.weight")) %>'
		    				},
		    				true
		    			);

	    			A.mix(
	    					YUI.AUI.defaults.FormValidator.REGEX,
	    					{
	    						positiveLong: /^[0-9]*$/			
	    					},
	    					true
	    				);

	    			var oldRequired=YUI.AUI.defaults.FormValidator.RULES.required;
	    			
	    			A.mix(
	    				YUI.AUI.defaults.FormValidator.RULES,
	    				{
	    					required: function(val, fieldNode, ruleValue) {
	    						switch (fieldNode.get('name')) {
	    					    case "<portlet:namespace />weight":
	    					    	if(A.Node.getDOMNode(fieldNode).form.<portlet:namespace />weight.disabled){
	    								return true;	 
	    					    	}       
	    					    default:
	    					    	return oldRequired(val, fieldNode, ruleValue);
	    						}				
	    					},
	    					weightRule: function(val, fieldNode, ruleValue) {
	    						if(!A.Node.getDOMNode(fieldNode).form.<portlet:namespace />weight.disabled){
	    							return YUI.AUI.defaults.FormValidator.REGEX.positiveLong.test(val);
	    						}
	    						return true;
	    					}		
	    				},
	    				true
	    			);
		    	});
		    
			    function <portlet:namespace />showPopupActivities(activityId)
			    {
					AUI().use('node','aui-form-validator','aui-dialog', function(A){

						if(A.one('#<portlet:namespace />activityPopup')){
							window.<portlet:namespace />popupActivitiesBody = A.one('#<portlet:namespace />activityPopup').get('innerHTML');
							A.one('#<portlet:namespace />activityPopup').remove();
						}

						window.<portlet:namespace />popupActivities = new A.Dialog({
							id:'<portlet:namespace />showPopupActivities',
				            title: A.one('#<portlet:namespace />title_'+activityId).get ('innerHTML'),
				            bodyContent: window.<portlet:namespace />popupActivitiesBody,
				            centered: true,
				            modal: true,
				            width: 370,
				            height: 270
				        }).render();

						A.one('#<portlet:namespace />evalActId').set ('value',activityId);
						A.one('#<portlet:namespace />state').set ('value',A.one('#<portlet:namespace />selected_'+activityId).get ('value'));
						A.one('#<portlet:namespace />stateCheckbox').set ('checked',(A.one('#<portlet:namespace />selected_'+activityId).get ('value')=='true')?'checked':'');
						A.one('#<portlet:namespace />weight').set ('value',A.one('#<portlet:namespace />weight_'+activityId).get ('innerHTML'));
						A.one('#<portlet:namespace />weight').set ('disabled',(A.one('#<portlet:namespace />selected_'+activityId).get ('value')=='true')?'':'disabled');
						
						window.<portlet:namespace />validator = new A.FormValidator({
							boundingBox: '#<portlet:namespace />fm_activities',
							validateOnBlur: true,
							validateOnInput: true,
							selectText: true,
							showMessages: false,
							containerErrorClass: '',
							errorClass: '',
							rules: {			
								<portlet:namespace />weight: {
									required: true,
									weightRule: true
								}
							},

					        fieldStrings: {
					        	<portlet:namespace />weight: {
					        		required:'<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(themeDisplay.getLocale(), "execActivity.options.error.weight")) %>',
					    			weightRule: '<%=JavaScriptUtil.markupToStringLiteral(LanguageUtil.get(themeDisplay.getLocale(), "execActivity.options.error.weight")) %>'
					            }
					        },
							
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
				        
						window.<portlet:namespace />popupActivities.show();   
					});
			    }

			    function <portlet:namespace />stateActivitiesChange(stateCheckbox)
			    {
			        AUI().use('node','aui-form-validator', function(A) {
			        	A.one('#<portlet:namespace />weight').set ('disabled',(stateCheckbox.checked)?'':'disabled');
			        	window.<portlet:namespace />validator.validate();
			        });
			    }
	
			    function <portlet:namespace />doClosePopupActivities()
			    {
			        AUI().use('aui-dialog', function(A) {
			        	window.<portlet:namespace />popupActivities.close();
			        });
			    }
		    
		    	function <portlet:namespace />saveActivity(){

		    		AUI().use('node','liferay-portlet-url','aui-io-request','aui-form-validator', function(A){

		    			window.<portlet:namespace />validator.validate();
		    			if(!window.<portlet:namespace />validator.hasErrors()){

							A.io.request('<%=updateEvaluationURL.toString()%>', 
								{ 
								  method : 'POST', 
					              form: {
					                id: '<portlet:namespace />fm_activities'
					              },
								  dataType: 'json',
								  on: { 
									success: function() {
										var divError = A.one('#<portlet:namespace />fm_activitiesError');    
										if(this.get('responseData').returnStatus){
											if(divError) {
												var activityId = A.one('#<portlet:namespace />evalActId').get ('value');
												A.one('#<portlet:namespace />selected_'+activityId).set ('value',A.one('#<portlet:namespace />state').get ('value'));
												A.one('#<portlet:namespace />selected_'+activityId+'Checkbox').set ('checked',(A.one('#<portlet:namespace />state').get ('value')=='true')?'checked':'');
												A.one('#<portlet:namespace />weight_'+activityId).set ('innerHTML',A.one('#<portlet:namespace />weight').get ('value'));
												divError.addClass('portlet-msg-success');
												divError.setContent(this.get('responseData').returnMessage);
											}
										}
										else{
											
											if(divError) {
												divError.addClass('portlet-msg-error');
												divError.setContent(this.get('responseData').returnMessage);
											}
										}
										  
									},  
									failure: function() {
										alert('Error deleting the notification.');
									} 
								} 
							});

		    			}
		    		});
			    }
 
		    //-->
			</script>
			
			<liferay-ui:tabs
			   names="<%=sbTitles.toString() %>"
			   tabsValues="<%=sbModuleId.toString() %>"
			   param="<%=renderResponse.getNamespace()+\"moduleId\"%>"
			   url="<%=renderResponse.createRenderURL().toString()%>"
			   value="<%=Long.toString(moduleId) %>"
			/>

			<%
				PortletURL portletURL = renderResponse.createRenderURL();
				portletURL.setParameter("jspPage","/html/evaluationtaskactivity/view.jsp");
			
			%>
				<liferay-ui:search-container deltaConfigurable="true" delta="10" emptyResultsMessage="Ahhhh" hover="true"  >
					<liferay-ui:search-container-results >
					<%
					int containerStart;
					int containerEnd;
					try {
						containerStart = ParamUtil.getInteger(request, "containerStart");
						containerEnd = ParamUtil.getInteger(request, "containerEnd");
					} catch (Exception e) {
						containerStart = searchContainer.getStart();
						containerEnd = searchContainer.getEnd();
					}
					if (containerStart <=0) {
						containerStart = searchContainer.getStart();
						containerEnd = searchContainer.getEnd();
					}
					
					List<Object> gradeBookActivities = new ArrayList<Object>();
					
					for (LearningActivityType learningActivityType:new LearningActivityTypeRegistry().getLearningActivityTypes()){
						if((learningActivityType.gradebook())&&(!(learningActivityType instanceof TaskEvaluationLearningActivityType))) {
							gradeBookActivities.add((int)learningActivityType.getTypeId());
						}
					}
			
					List<LearningActivity> tempResults =  LearningActivityLocalServiceUtil.dynamicQuery(
							DynamicQueryFactoryUtil.forClass(LearningActivity.class).
									add(PropertyFactoryUtil.forName("moduleId").eq(moduleId)).
									add(PropertyFactoryUtil.forName("typeId").in(gradeBookActivities)));
					results = ListUtil.subList(tempResults, containerStart, containerEnd);
					total = tempResults.size();
						
					pageContext.setAttribute("results", results);
					pageContext.setAttribute("total", total);
								
					request.setAttribute("containerStart",String.valueOf(containerStart));
					request.setAttribute("containerEnd",String.valueOf(containerEnd));
					%>
				
					</liferay-ui:search-container-results>
					
					<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity"
						keyProperty="actId"
						modelVar="learningActivityModel"
						rowVar="learningActivityRow"
					>
					<%
					
					
					%>
						<liferay-ui:search-container-column-text name="selected">
							<aui:input type="checkbox" 
							           inlineLabel="right" 
							           label="" 
							           name="<%=\"selected_\"+learningActivityModel.getActId() %>"  
							           checked="<%=activities.containsKey(learningActivityModel.getActId())%>" 
							           disabled="true"
							></aui:input>
						</liferay-ui:search-container-column-text>
						<liferay-ui:search-container-column-text name="weight">
							<span id="<portlet:namespace />weight_<%=learningActivityModel.getActId() %>"
								><%=(activities.containsKey(learningActivityModel.getActId()))?activities.get(learningActivityModel.getActId()):StringPool.BLANK %></span>
						</liferay-ui:search-container-column-text>
						<liferay-ui:search-container-column-text name="title">
							<span id="<portlet:namespace />title_<%=learningActivityModel.getActId() %>"
								><%=learningActivityModel.getTitle(themeDisplay.getLocale()) %></span>
						</liferay-ui:search-container-column-text>
						<liferay-ui:search-container-column-text name="Configuration">
							<liferay-ui:icon image="configuration" toolTip="config" url="<%=\"javascript:\"+renderResponse.getNamespace()+\"showPopupActivities(\"+learningActivityModel.getActId()+\");\" %>" />
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
			
			</div>
			<%
		}
	}
%>