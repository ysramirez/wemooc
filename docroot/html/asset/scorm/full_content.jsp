<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.File"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>

<%@ include file="/init.jsp" %>

<% SCORMContent scorm=(SCORMContent)request.getAttribute("scorm"); 
LearningActivityTry learningTry = (LearningActivityTry) request.getAttribute("learningTry");

if (Validator.isNotNull(scorm.getDescription()) && learningTry == null) {
%>

<div class="asset-description"><%=scorm.getDescription() %></div>

<% } %>

<script src="/liferaylms-portlet/js/scorm/sscompat.js" type="text/javascript"></script> 
	<script src="/liferaylms-portlet/js/scorm/sscorlib.js" type="text/javascript"></script>  
	<script src="/liferaylms-portlet/js/scorm/ssfx.Core.js" type="text/javascript"></script> 
 
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/API_BASE.js"></script>    
    <script type="text/javascript" src="/liferaylms-portlet/js/scorm/API.js"></script>  
    <script type="text/javascript" src="/liferaylms-portlet/js/scorm/API_1484_11.js"></script>

    <script type="text/javascript" src="/liferaylms-portlet/js/scorm/Controls.js"></script>        
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/LocalStorage.js"></script>
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/Player.js"></script>
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/PersistenceStoragePatched.js"></script>
<%

String urlIndex=themeDisplay.getPortalURL()+this.getServletContext().getContextPath()+"/scorm/"+Long.toString(scorm.getCompanyId())+"/"+Long.toString(scorm.getGroupId())+"/"+scorm.getUuid()+"/imsmanifest.xml";

String iconsDir = "/liferaylms-portlet";
String conTema = PropsUtil.get("scorm.icons.theme");
try {
	if (conTema != null && Boolean.valueOf(conTema)) {
		iconsDir = themeDisplay.getPathThemeImages()+"/custom";
	}
} catch(Exception e) {
	
}
%>

   <script type="text/javascript">
     function InitPlayer() {
       PlayerConfiguration.Debug = false;
       PlayerConfiguration.StorageSupport = true;

       PlayerConfiguration.TreeMinusIcon = "<%= iconsDir %>/icons/scorm/minus.gif";
       PlayerConfiguration.TreePlusIcon = "<%= iconsDir %>/icons/scorm/plus.gif";
       PlayerConfiguration.TreeLeafIcon = "<%= iconsDir %>/icons/scorm/leaf.gif";
       PlayerConfiguration.TreeActiveIcon = "<%= iconsDir %>/icons/scorm/select.gif";

       PlayerConfiguration.BtnPreviousLabel = "<liferay-ui:message key="scorm.previous" />";
       PlayerConfiguration.BtnContinueLabel = "<liferay-ui:message key="scorm.next" />";
       PlayerConfiguration.BtnExitLabel = "<liferay-ui:message key="scorm.exit" />";
       PlayerConfiguration.BtnExitAllLabel = "<liferay-ui:message key="activity.try.exit" />";
       PlayerConfiguration.BtnAbandonLabel = "<liferay-ui:message key="scorm.abandon" />";
       PlayerConfiguration.BtnAbandonAllLabel = "<liferay-ui:message key="scorm.abandonall" />";
       PlayerConfiguration.BtnSuspendAllLabel = "<liferay-ui:message key="scorm.suspendall" />";

       //manifest by URL   
       Run.ManifestByURL("<%=urlIndex%>", false);
     }
     
     AUI().ready(function() {
    	 InitPlayer();
   	 });
     
  </script>

  <div id="placeholder_clicker" style="display: none"></div>
  <div style="width: 99%;">
 	<div id="placeholder_navigationContainer"></div>
 	<div id="placeholder_treeContainer"></div>
  </div>
<div id="placeholder_treecontentContainer">
	
	<div id="placeholder_contentIFrame" style="height: 670px; width: 99%;">
          <iframe id="contentIFrame" style="height:100%; width:100%"></iframe>
    </div>
</div>
<div id="placeholder_navigationContainer2"></div>

