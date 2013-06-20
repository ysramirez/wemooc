<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>
<%
int abstractLength = (Integer)request.getAttribute(WebKeys.ASSET_PUBLISHER_ABSTRACT_LENGTH);

SCORMContent scorm=(SCORMContent)request.getAttribute("scorm");
%>
<div class="asset-resource-info">
	<liferay-ui:icon
					image='<%= "../file_system/large/scorm" %>'
					label="<%= false %>"
					message="<%= scorm.getTitle() %>"	
				/>
	
	<p class="asset-description">
	<%= HtmlUtil.escape(StringUtil.shorten(scorm.getDescription() , abstractLength)) %>
</p>
</div>