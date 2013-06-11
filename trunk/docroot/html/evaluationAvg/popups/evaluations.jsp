<%@page import="com.liferay.portal.kernel.dao.orm.OrderFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEvalRegistry"%>
<%@page import="com.liferay.lms.learningactivity.courseeval.CourseEval"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Course"%>
<%@ include file="/init.jsp" %>
<%
	Course course=CourseLocalServiceUtil.fetchByGroupCreatedId(themeDisplay.getScopeGroupId());
	CourseEval courseEval = new CourseEvalRegistry().getCourseEval(course.getCourseEvalId());
	
	PortletURL viewEvaluationsURL = renderResponse.createRenderURL();
	viewEvaluationsURL.setParameter(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY,StringPool.TRUE);
	viewEvaluationsURL.setParameter("jspPage","/html/evaluationAvg/popups/evaluations.jsp");

%>

<script type="text/javascript">
<!--

	function <portlet:namespace />doClosePopupEvaluations()
	{
	    AUI().use('aui-dialog', function(A) {
	    	A.DialogManager.closeByChild('#<portlet:namespace />showPopupEvaluations');
	    });
	}

	YUI.add('<portlet:namespace />eval-model', function(A) {
	    A.<portlet:namespace />EvaluationModel = A.Base.create('<portlet:namespace />EvaluationModel', A.Model, [], {
	    }, {
	        ATTRS: {
	            name: {
	                value: ''
	            },
				weight: {
					value: 0
				}
	        }
	    });

	    A.<portlet:namespace />EvaluationModelList = A.Base.create('<portlet:namespace />EvaluationModelList', A.ModelList, [], {
	        comparator: function (model) {
	            return model.get('id');
	        },
	        model: A.<portlet:namespace />EvaluationModel
	    });
	
	}, '' ,{requires:['model-list']});

	function <portlet:namespace />addEvaluation(evalId, evalName,evalWeight){
		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			var existingEvaluation=window.<portlet:namespace />selectedEvaluations.getById(evalId);
			if(existingEvaluation!=null){
				window.<portlet:namespace />selectedEvaluations.remove(existingEvaluation);
			}	
			window.<portlet:namespace />selectedEvaluations.add(
					new A.<portlet:namespace />EvaluationModel({id:evalId,name:evalName,weight:evalWeight}));
		});			
	}
	
	function <portlet:namespace />deleteEvaluation(evalId){
		AUI().use('node-base','<portlet:namespace />eval-model', function(A) {
			var existingEvaluation=window.<portlet:namespace />selectedEvaluations.getById(evalId);
			if(existingEvaluation!=null){
				window.<portlet:namespace />selectedEvaluations.remove(existingEvaluation);
			}		
		});		
	}

	function <portlet:namespace />createValidator(){
		AUI().use('aui-form-validator', function(A) {

			if(!!window.<portlet:namespace />validateEvaluations){
				delete window.<portlet:namespace />validateEvaluations;
			}

			var rules = {			
					<portlet:namespace />passPuntuation: {
						required: true,
						number: true,
						range: [0,100]
					}
				};

			var fieldStrings = {			
		        	<portlet:namespace />passPuntuation: {
		        		required: '<liferay-ui:message key="evaluationAvg.passPuntuation.required" />',
		        		number: '<liferay-ui:message key="evaluationAvg.passPuntuation.number" />',
		        		range: '<liferay-ui:message key="evaluationAvg.passPuntuation.range" />',       		
		            }
				};

			A.all('#<portlet:namespace />evaluations * input').each(
				function(evaluationWeight){
					if(evaluationWeight.get('id').indexOf('<portlet:namespace />weight_')==0){
						
						rules[evaluationWeight.get('id')] = {
								number: true,
								range: [0,100]
							};

						fieldStrings[evaluationWeight.get('id')] = {
								number: '<liferay-ui:message key="evaluationAvg.weight.number" />',
				        		range: '<liferay-ui:message key="evaluationAvg.weight.range" />',   
							};

						evaluationWeight.on('input',function(){
								if(evaluationWeight.get('value')=='') {
									var divError = A.one('#'+evaluationWeight.get('name')+'Error');
									divError.removeClass('portlet-msg-error');
									divError.setContent('');
									<portlet:namespace />deleteEvaluation(evalId)
								}
								else{
									window.<portlet:namespace />validateEvaluations.validateField(evaluationWeight.get('name'))
									if(!window.<portlet:namespace />validateEvaluations.getFieldError(window.<portlet:namespace />validateEvaluations.getField(evaluationWeight.get('name')))){
										var evalId=evaluationWeight.get('id').substring('<portlet:namespace />weight_'.length);
										alert(evalId);
									}
								}
							});
					}
			});
					
			window.<portlet:namespace />validateEvaluations = new A.FormValidator({
				boundingBox: '#<portlet:namespace />evaluations',
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
	}

	AUI().ready('node-base','<portlet:namespace />eval-model', function(A) {
		window.<portlet:namespace />selectedEvaluations = new A.<portlet:namespace />EvaluationModelList();

		var searchContainer = A.one('#<%=renderResponse.getNamespace() %>learningActivitiesSearchContainerSearchContainer').ancestor('.lfr-search-container');

		searchContainer.on('ajaxLoaded',function(){

			window.<portlet:namespace />selectedEvaluations.each(
				function(evalModel){
					A.all('#<portlet:namespace />weight_'+evalModel.get('id')).each(function(evalModelDiv){ evalModelDiv.setContent(evalModel.get('weight')); });  					 
				}
			);
			<portlet:namespace />createValidator();	
		});

		<portlet:namespace />createValidator();		
	});

//-->
</script>

<aui:form name="evaluations">
<% if(courseEval.getNeedPassPuntuation()) { 
	long passPuntuation = courseEval.getPassPuntuation(course);
%>
	<aui:fieldset>
	    <aui:input type="text" name="passPuntuation" label="evaluationAvg.passPuntuation"
	               value='<%= (passPuntuation==0)?0:passPuntuation %>' />
	    <div id="<portlet:namespace />passPuntuationError" class="<%=(SessionErrors.contains(renderRequest, "evaluationAvg.passPuntuation.result-bad-format"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    	<%=(SessionErrors.contains(renderRequest, "evaluationAvg.passPuntuation.result-bad-format"))?
	    			LanguageUtil.get(pageContext,"evaluationAvg.passPuntuation.result-bad-format"):StringPool.BLANK %>
	    </div>
	</aui:fieldset>
<% } %>

	<liferay-ui:search-container iteratorURL="<%=viewEvaluationsURL%>" emptyResultsMessage="there-are-no-results" delta="5" deltaConfigurable="true" >

	   	<liferay-ui:search-container-results>
			<%
				pageContext.setAttribute("results", LearningActivityLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
		    		add(PropertyFactoryUtil.forName("moduleId").in(
			    			DynamicQueryFactoryUtil.forClass(Module.class).add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId())).
			    			setProjection(ProjectionFactoryUtil.property("moduleId"))	
			    		)).
			    		add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId()))/*.
			    	    add(PropertyFactoryUtil.forName("typeId").eq(8))*/.
			    	    addOrder(OrderFactoryUtil.asc("moduleId")), 
			    	searchContainer.getStart(), searchContainer.getEnd()));
			
			    pageContext.setAttribute("total",(int)LearningActivityLocalServiceUtil.dynamicQueryCount(DynamicQueryFactoryUtil.forClass(LearningActivity.class).
			    		add(PropertyFactoryUtil.forName("moduleId").in(
				    			DynamicQueryFactoryUtil.forClass(Module.class).add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId())).
				    			setProjection(ProjectionFactoryUtil.property("moduleId"))	
				    		)).
				    		add(PropertyFactoryUtil.forName("groupId").eq(themeDisplay.getScopeGroupId()))/*.
				    	    add(PropertyFactoryUtil.forName("typeId").eq(8))*/	
			    		));			
			%>
		</liferay-ui:search-container-results>
		
		<liferay-ui:search-container-row className="com.liferay.lms.model.LearningActivity" keyProperty="actId" modelVar="evaluation">
			<liferay-ui:search-container-column-text name="evaluationAvg.evaluation.module" title="evaluationAvg.evaluation.module">
				<%=ModuleLocalServiceUtil.getModule(evaluation.getModuleId()).getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="evaluationAvg.evaluation.name" title="evaluationAvg.evaluation.name">
				<%=evaluation.getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="evaluationAvg.evaluation.weight" title="evaluationAvg.evaluation.weight">
				<aui:input type="text" label="" name="<%=\"weight_\"+evaluation.getActId() %>"  />
				<div id="<portlet:namespace />weight_<%=evaluation.getActId() %>Error" class="<%=(SessionErrors.contains(renderRequest, "evaluationAvg.weight_"+evaluation.getActId()+".result-bad-format"))?
	    														"portlet-msg-error":StringPool.BLANK %>">
	    														<%=(SessionErrors.contains(renderRequest, "evaluationAvg.weight_"+evaluation.getActId()+".result-bad-format"))?
	    															LanguageUtil.get(pageContext,"evaluationAvg.weight_"+evaluation.getActId()+".result-bad-format"):StringPool.BLANK %>
	    		</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

	 	<liferay-ui:search-iterator />
		
		<script type="text/javascript">
		<!--
			function <portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A) {

				var searchContainer = A.one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container');
				
				function <portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(url){

					var params = {};
					var urlPieces = url.split('?');
					if (urlPieces.length > 1) {
						params = A.QueryString.parse(urlPieces[1]);
						params.p_p_state='<%=LiferayWindowState.EXCLUSIVE.toString() %>';
						url = urlPieces[0];
					}
	
					A.io.request(
						url,
						{
							data: params,
							dataType: 'html',
							on: {
								failure: function(event, id, obj) {
									var portlet = A.one('#p_p_id<portlet:namespace />');
									portlet.hide();
									portlet.placeAfter('<div class="portlet-msg-error"><liferay-ui:message key="there-was-an-unexpected-error.-please-refresh-the-current-page"/></div>');
								},
								success: function(event, id, obj) {
									searchContainer.setContent(A.Node.create(this.get('responseData')).one('#<%=renderResponse.getNamespace() %><%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer').ancestor('.lfr-search-container').getContent ());
									<portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(A);
									searchContainer.fire('ajaxLoaded');
								}
							}
						}
					);
				}

				
				<portlet:namespace /><%= searchContainer.getCurParam() %>updateCur = function(box){
					<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
					%>SearchContainer('<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getCurParam()) 
						%>&<%= renderResponse.getNamespace() + searchContainer.getCurParam() %>=' + A.one(box).val());
				};

				<portlet:namespace /><%= searchContainer.getDeltaParam() %>updateDelta = function(box){
					<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) 
						%>SearchContainer('<%=HttpUtil.removeParameter(searchContainer.getIteratorURL().toString(), renderResponse.getNamespace() + searchContainer.getDeltaParam()) 
							%>&<%= renderResponse.getNamespace() + searchContainer.getDeltaParam() %>=' + A.one(box).val());
				};

				searchContainer.all('.taglib-page-iterator').each(
					function(pageIterator){
						pageIterator.all('a').each(
							function(anchor){
								var url=anchor.get('href');
								anchor.set('href','#');
							    anchor.on('click',
									function(){
							    		<portlet:namespace />reload<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer(url);
								    }
							    );
							}
						);
					}
				);

			};

			AUI().ready('aui-io-request','querystring-parse','aui-parse-content',<portlet:namespace />ajaxMode<%= searchContainer.getId(request, renderResponse.getNamespace()) %>SearchContainer);
		//-->
		</script>

	</liferay-ui:search-container>
</aui:form>

<aui:button-row>
	<button name="Save" value="save" onclick="<portlet:namespace />doSaveGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.save" />
	</button>
	<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupEvaluations();" type="button">
		<liferay-ui:message key="offlinetaskactivity.cancel" />
	</button>
</aui:button-row>
<liferay-ui:success key="offlinetaskactivity.grades.updating" message="offlinetaskactivity.correct.saved" />
<liferay-ui:error key="offlinetaskactivity.grades.bad-updating" message="offlinetaskactivity.grades.bad-updating" />

