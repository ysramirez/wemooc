<%@page import="com.liferay.lms.service.SCORMContentLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.SCORMContent"%>

<%@ include file="/init.jsp" %>
<script src="/liferaylms-portlet/js/scorm/sscompat.js" type="text/javascript"></script> 
	<script src="/liferaylms-portlet/js/scorm/sscorlib.js" type="text/javascript"></script>  
	<script src="/liferaylms-portlet/js/scorm/ssfx.Core.js" type="text/javascript"></script> 
 
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/API_BASE.js"></script>    
        <script type="text/javascript" src="/liferaylms-portlet/js/scorm/API.js"></script>  
        <script type="text/javascript" src="/liferaylms-portlet/js/scorm/API_1484_11.js"></script>

        <script type="text/javascript" src="/liferaylms-portlet/js/scorm/Controls.js"></script>        
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/LocalStorage.js"></script>
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/Player.js"></script>
<%


SCORMContent scorm=(SCORMContent)request.getAttribute("scorm");

String urlIndex="http://localhost:8080/liferaylms-portlet/scorm/"+Long.toString(scorm.getCompanyId())+"/"+Long.toString(scorm.getGroupId())+"/"+scorm.getUuid()+"/imsmanifest.xml";

%>

   <script type="text/javascript">
     function InitPlayer() {
       PlayerConfiguration.Debug = true;
       PlayerConfiguration.StorageSupport = false;

       PlayerConfiguration.TreeMinusIcon = "/liferaylms-portlet/icons/scorm/minus.gif";
       PlayerConfiguration.TreePlusIcon = "/liferaylms-portlet/icons/scorm/plus.gif";
       PlayerConfiguration.TreeLeafIcon = "/liferaylms-portlet/icons/scorm/leaf.gif";
       PlayerConfiguration.TreeActiveIcon = "/liferaylms-portlet/icons/scorm/select.gif";

       PlayerConfiguration.BtnPreviousLabel = "Previous";
       PlayerConfiguration.BtnContinueLabel = "Continue";
       PlayerConfiguration.BtnExitLabel = "Exit";
       PlayerConfiguration.BtnExitAllLabel = "Exit All";
       PlayerConfiguration.BtnAbandonLabel = "Abandon";
       PlayerConfiguration.BtnAbandonAllLabel = "Abandon All";
       PlayerConfiguration.BtnSuspendAllLabel = "Suspend All";

       //manifest by URL   
       Run.ManifestByURL("<%=urlIndex%>", false);

     }
     
  </script>
<button onClick="InitPlayer()">Iniciar</button>
<div id="placeholder_navigationContainer" style="padding-top:5px;padding-bottom:5px;"></div>
  <table width="100%" height="100%" border="1" cellspacing="0" cellpadding="0">
    <tr>
      <td height="100%" valign="top">
       <table width="100%" height="100%" style="width:100%;height:800px;"border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" height="1" style="padding-left:5px;"><div id="placeholder_treeContainer" style="width:200px;height:100%;overflow:auto;"></div></td>
          <td width="100%" height="100%" valign="top"><div id="placeholder_contentIFrame" style="width:100%;height:100%;">
          <iframe id="contentIFrame" width="100%" height="100%"></iframe></div></td>
          <td valign="top" height="1"><div id="placeholder_Debugger" style="font-size:10px;width:250px;height:100%;overflow:auto;padding-left:5px;display:none;"></div></td>
        </tr>
       </table>
      </td>
    </tr>
   <div id="debuggerContainer">
   </div>
  </table>
