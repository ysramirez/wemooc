<%@page import="com.liferay.lms.learningactivity.LearningActivityType"%>
<%@page import="com.liferay.portal.model.PublicRenderParameter"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="java.net.URL"%>
<%@page import="com.liferay.portlet.PortletQNameUtil"%>
<%@page import="javax.portlet.RenderResponse"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.security.permission.ResourceActionsUtil"%>
<%@page import="com.liferay.lms.LmsActivitiesList"%>
<%@page import="com.liferay.lms.asset.LearningActivityAssetRendererFactory"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.liferay.portlet.asset.model.AssetCategory"%>
<%@page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletResponse"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletRequest"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.learningactivity.LearningActivityTypeRegistry"%>

<%@ include file="/init.jsp"%>


<%
long moduleId = ParamUtil.getLong(request, "moduleId", 0);
boolean actionEditing = ParamUtil.getBoolean(request,
		"actionEditing", false);
long actId = ParamUtil.getLong(request, "actId", 0);

NumberFormat resultNumberFormat = NumberFormat.getInstance(locale);
resultNumberFormat.setMinimumIntegerDigits(1);

LearningActivity currentLeaningActivity = null;

if(actId!=0) {
	currentLeaningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
}

LearningActivityTypeRegistry learningActivityTypeRegistry = new LearningActivityTypeRegistry();

java.util.List<LearningActivity> activities = null;
if (moduleId == 0) {
	java.util.List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(themeDisplay.getScopeGroupId());
	if (modules.size() > 0) {
		Module theModule = modules.get(0);
		moduleId = theModule.getModuleId();
	}
}
if (moduleId == 0) {

	activities = LearningActivityServiceUtil
			.getLearningActivitiesOfGroup(scopeGroupId);
} else {
	Module theModule =ModuleLocalServiceUtil.getModule(moduleId);
	if(!permissionChecker.hasPermission(
			themeDisplay.getScopeGroupId(),
			Module.class.getName(), moduleId,
			"ADD_LACT")&& ModuleLocalServiceUtil.isLocked(theModule.getPrimaryKey(),themeDisplay.getUserId()))
	{
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		activities=new ArrayList<LearningActivity>(); 
	}
	else
	{
	activities = LearningActivityServiceUtil
			.getLearningActivitiesOfModule(moduleId);
	}

}


String activityEnd = "desactivado";
Hashtable<AssetCategory, java.util.List<LearningActivity>> catler = new Hashtable<AssetCategory, java.util.List<LearningActivity>>();
for (LearningActivity activity : activities) {
	java.util.List<AssetCategory> categorias = AssetCategoryLocalServiceUtil
			.getCategories(LearningActivity.class.getName(),
					activity.getActId());
	for (AssetCategory categoria : categorias) {
		if (!catler.containsKey(categoria)) {
			catler.put(categoria, new ArrayList());
		}
		catler.get(categoria).add(activity);
	}
}
if ((actionEditing
		&& permissionChecker.hasPermission(
				themeDisplay.getScopeGroupId(),
				Module.class.getName(), moduleId,
				"ADD_LACT"))||(permissionChecker.hasPermission(themeDisplay.getScopeGroupId(),
						"com.liferay.lms.learningactivitymodel", themeDisplay.getScopeGroupId(), "ADD_ACTIVITY")&&moduleId==0)) {
	%>


<script>

Liferay.provide(
        window,
        '<portlet:namespace />refreshPortlet',
        function() {
		     <%-- refreshing the portlet [Liferay.Util.getOpener().] --%>
            var curPortletBoundaryId = '#p_p_id<portlet:namespace />';

            Liferay.Portlet.refresh(curPortletBoundaryId);
        },
        ['aui-dialog','aui-dialog-iframe']
    );
    
</script>
<liferay-portlet:renderURL var="newactivityURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="jspPage" value="/html/lmsactivitieslist/newactivity.jsp"/>
	<liferay-portlet:param name="resModuleId" value="<%=Long.toString(moduleId) %>" />
</liferay-portlet:renderURL>
	<%

	String portletnamespace = renderResponse.getNamespace();
	String newactivitypopup = "javascript:AUI().use('aui-dialog','aui-dialog-iframe', "+
			"	function(A){ "+
			"	new A.Dialog( "+
			"		{ "+
			"    		id: 'editlesson', "+ 
			"			title: '"+LanguageUtil.get(pageContext,"activity.creation")+"', "+
		    "			destroyOnClose: true, "+
		    "			width: 750, "+
		    "			modal:true, "+
		    "			x:50, "+
		    "			y:50, "+
		    "			on: { "+
			"    			close: function(evt){ "+
			"					Liferay.Portlet.refresh(A.one('#p_p_id"+renderResponse.getNamespace()+"')); "+		
			"				} "+
			"			} "+
			"		} "+
			"	).plug( "+
			"		A.Plugin.DialogIframe, "+
			"		{ "+
			"			uri: '" + JavaScriptUtil.markupToStringLiteral(newactivityURL) + "' "+
			"		} "+
			"	).render().show(); "+
			"});";
			 
	%>
	<liferay-ui:icon image="add" label="<%=true%>" message="add"
		url="#" cssClass="newactivity" onClick="<%=newactivitypopup %>"/>
	
<%
}
%>
<liferay-ui:error></liferay-ui:error>
<ul>
			<%
			for (LearningActivity activity : activities) {
				
				String moduletitle = "";
				if (activity.getModuleId() != 0) {
					Module theModule = ModuleLocalServiceUtil
							.getModule(activity.getModuleId());
					moduletitle = theModule.getTitle();
				}
				long result = 0;
				String status = "not-started";
				if (LearningActivityResultLocalServiceUtil
						.existsLearningActivityResult(activity.getActId(),
								themeDisplay.getUserId())) {
					status = "started";

					LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil
							.getByActIdAndUserId(activity.getActId(),
									themeDisplay.getUserId());
					result = learningActivityResult.getResult();
					if (learningActivityResult.isPassed()&&!actionEditing) {
						status = "passed";
					}

				}
				if (actId == activity.getActId()) {
						activityEnd = "activado";
				} else {
					activityEnd = "desactivado";
				}
				String editing="";
				if(actionEditing)
				{
					editing="editing";
				}
				
				if(permissionChecker.hasPermission(activity.getGroupId(),LearningActivity.class.getName(),	activity.getActId(), ActionKeys.VIEW)){
					if(!LearningActivityLocalServiceUtil.islocked(activity.getActId(),themeDisplay.getUserId())||
							(permissionChecker.hasPermission(
							activity.getGroupId(),
							LearningActivity.class.getName(),
							activity.getActId(), ActionKeys.UPDATE)&&actionEditing)){
						LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
						AssetRenderer assetRenderer = laf.getAssetRenderer(activity.getActId());
						String view1URL = assetRenderer.getURLViewInContext((LiferayPortletRequest) renderRequest, (LiferayPortletResponse) renderResponse, "");	
						Portlet view1URLPortlet =PortletLocalServiceUtil.getPortletById(HttpUtil.getParameter(view1URL, "p_p_id",false));
						if(view1URLPortlet!=null) {
							PublicRenderParameter moduleIdPublicParameter = view1URLPortlet.getPublicRenderParameter("moduleId");
							if(moduleIdPublicParameter!=null) {					
								view1URL=HttpUtil.addParameter(view1URL, PortletQNameUtil.getPublicRenderParameterName(moduleIdPublicParameter.getQName()),Long.toString(activity.getModuleId()));
								view1URL=HttpUtil.removeParameter(view1URL, "p_p_state");
								view1URL=HttpUtil.addParameter(view1URL, "p_p_state","normal");
							}
						}
						
						%>
						
						<portlet:actionURL var="goToActivity" windowState="<%= WindowState.NORMAL.toString()%>" >
							<portlet:param name="actId" value="<%=Long.toString(activity.getActId()) %>" />
						</portlet:actionURL>

						<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%>"  <%=(status=="passed")?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":StringPool.BLANK %> >
						
							<a href="<%=goToActivity.toString() %>"  ><%=activity.getTitle(themeDisplay.getLocale())%></a>
							
					<%
					}
					else
					{
					%>
						<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%> locked"  <%=(status=="passed")?"title =\""+LanguageUtil.format(pageContext, "activity.result",new Object[]{resultNumberFormat.format(result)})+"\"":StringPool.BLANK %> >
							<span><%=activity.getTitle(themeDisplay.getLocale())%></span>
					<%
					}
				}

				if (actionEditing
					&& (permissionChecker.hasPermission(
						activity.getGroupId(),
						LearningActivity.class.getName(),
						activity.getActId(), ActionKeys.UPDATE)
						|| permissionChecker.hasPermission(
								activity.getGroupId(),
								LearningActivity.class.getName(),
								activity.getActId(), ActionKeys.DELETE) || permissionChecker
							.hasPermission(activity.getGroupId(),
									LearningActivity.class.getName(),
									activity.getActId(),
									ActionKeys.PERMISSIONS))) {
				%>
				<div class="iconsedit"><%@ include file="/html/lmsactivitieslist/admin_actions.jspf" %></div>
				
				<%
				}
				%>
				</li>
			<%
			}
			%>
</ul>