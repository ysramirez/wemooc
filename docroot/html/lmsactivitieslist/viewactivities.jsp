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

<%@ include file="/init.jsp"%>


<div class="portlet-toolbar search-form lms-tree">
<%
long moduleId = ParamUtil.getLong(request, "moduleId", 0);
boolean actionEditing = ParamUtil.getBoolean(request,
		"actionEditing", false);
long actId = ParamUtil.getLong(request, "actId", 0);

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


%>
<portlet:renderURL var="selectModuleURL">
	<portlet:param name="moduleId" value="<%=Long.toString(moduleId)%>"></portlet:param>
	<portlet:param name="actId" value="0"></portlet:param>
</portlet:renderURL>
<%
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
if (actionEditing
		&& permissionChecker.hasPermission(
				themeDisplay.getScopeGroupId(),
				Module.class.getName(), moduleId,
				"ADD_LACT")) {
	%>


<script>
function <portlet:namespace />closedialog()
{
	alert("a");
	}
</script>
<liferay-ui:icon-menu  icon="add" message="new">
<%
AssetRendererFactory arf=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(LearningActivity.class.getName());
Map<Long,String> classTypes=arf.getClassTypes(new long[0], themeDisplay.getLocale());
for(Long key:classTypes.keySet())
{
	
%>	
<liferay-portlet:actionURL name="editactivityoptions" var="newactivityURL"  windowState="<%= LiferayWindowState.POP_UP.toString() %>">
<liferay-portlet:param name="actId" value="0" />
<liferay-portlet:param name="resId" value="0" />
<liferay-portlet:param name="typeId" value="<%=key.toString() %>" />
</liferay-portlet:actionURL>
<%
	String classname=classTypes.get(key);

String newactivitypopup = "javascript:Liferay.Util.openWindow({dialog: {width: 960,modal: true ,destroyOnClose: true}, id: 'editlesson', title: '" +
		ResourceActionsUtil.getModelResource(locale, LearningActivity.class.getName()) + "', uri:'" + HtmlUtil.escapeURL(newactivityURL) + "'});";
		
		

%>
	<div class="newitem">
	<liferay-ui:icon image="add" label="<%=true%>" message="<%=classTypes.get(key) %>"
		url="<%=newactivitypopup%>" />
		</div>
<%
}
%>
</liferay-ui:icon-menu>
<%
}
%>
<liferay-ui:error></liferay-ui:error>
<ul>
	<li class="iniciarRetoModule option-less"><span class="desplegar"></span>
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
				
				if(!LearningActivityLocalServiceUtil.islocked(activity.getActId(),themeDisplay.getUserId())||
						(permissionChecker.hasPermission(
						activity.getGroupId(),
						LearningActivity.class.getName(),
						activity.getActId(), ActionKeys.UPDATE)&&actionEditing))
				{
					LearningActivityAssetRendererFactory laf = new LearningActivityAssetRendererFactory();
					
						AssetRenderer assetRenderer = laf.getAssetRenderer(activity.getActId());
						String view1URL = assetRenderer.getURLViewInContext((LiferayPortletRequest) renderRequest, (LiferayPortletResponse) renderResponse, "").toString();					
				%>
					<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%>">
					
						<a href="<%=view1URL.toString()%>"><%=activity.getTitle(themeDisplay.getLocale())%></a>
						
				<%
				}
				else
				{
				%>
					<li class="learningActivity <%=activityEnd%> <%=editing %> <%=status%> locked">
						<span><%=activity.getTitle(themeDisplay.getLocale())%></span>
				<%
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

		</ul></li>

	<%
	Enumeration<AssetCategory> keys=catler.keys();
	while (keys.hasMoreElements()) {
		AssetCategory categoria =keys.nextElement();
		%>
		<li class="learningCategory  option-more"><span class="desplegar"><%=categoria.getTitle(themeDisplay.getLocale())%></span>

			<ul>
				<%
				for (LearningActivity activity : catler.get(categoria)) {
				%>
				<portlet:actionURL name="viewactivity" var="viewURL">
					<portlet:param name="actId" value="<%=Long.toString(activity.getActId())%>" />
				</portlet:actionURL>
				<%
					if (actId == activity.getActId()) {
						activityEnd = "activado";
					} else {
						activityEnd = "desactivado";
					}
	
					String status = "not-started";
					long result = 0;
					if (LearningActivityResultLocalServiceUtil
							.existsLearningActivityResult(activity.getActId(),
									themeDisplay.getUserId())) {
						status = "started";

						LearningActivityResult learningActivityResult = LearningActivityResultLocalServiceUtil
								.getByActIdAndUserId(activity.getActId(),
										themeDisplay.getUserId());
						result = learningActivityResult.getResult();
						if (learningActivityResult.isPassed()) {
							status = "passed";
						}

					}
	
					if(!LearningActivityLocalServiceUtil.islocked(activity.getActId(),themeDisplay.getUserId()))
					{
					%>
					<li class="learningActivity <%=activityEnd%> <%=status%>"><a
						href="<%=viewURL.toString()%>"><%=activity.getTitle(themeDisplay.getLocale())%></a></li>
					<%
					}
					else
					{
						%>
						<li class="learningActivity <%=activityEnd%> <%=status%> locked"><span><%=activity.getTitle(themeDisplay.getLocale())%></span></li>
						<%
					}
				}
				%>
			</ul>
		</li>
	<%
	}
	%>
</ul>
</div>