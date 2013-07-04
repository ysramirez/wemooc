<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.File"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
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
	<script type="text/javascript" src="/liferaylms-portlet/js/scorm/PersistenceStoragePatched.js"></script>
<%
	
SCORMContent scorm=(SCORMContent)request.getAttribute("scorm");

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
     
     Liferay.provide(
    	window,
    	'adjustScorm',
    	function() {
    		var A = AUI();
    		var iframe = A.one('#placeholder_contentIFrame');
	       	 var treeContainer = A.one('#placeholder_treeContainer');
	       	 var menuA = A.one('#open-close-scorm-menu');
	       	 
	       	 if (treeContainer.getStyle('display') != 'none') {
	       		 iframe.setStyle('width', '80%');
	       		 treeContainer.setStyle('width', '20%');
	       		 menuA.replaceClass('open-scorm-menu', 'close-scorm-menu');
	       	 } else {
	       		iframe.setStyle('width', '100%');
	       		menuA.replaceClass('close-scorm-menu', 'open-scorm-menu');
	       	 }
	       	 var initiated = (treeContainer.all('img[src*="select.gif"]').size() > 0);
	       	 if (!initiated) {
	       		treeContainer.one('a[href="#"]').simulate('click');
	       	 }
	       	 /*
	       	 var iFrameID = document.getElementById('contentIFrame');
	       	 iFrameID.style.height = "";
	       	 iFrameID.style.height = (iFrameID.contentWindow.document.body.scrollHeight > 200 ? iFrameID.contentWindow.document.body.scrollHeight : 600) + "px";
	       	 iframe.setStyle('height', iFrameID.style.height + "px");
	       	 var iframes = iFrameID.contentWindow.document.getElementsByTagName('frame');
	       	 for (var i = 0; i < iframes.length; i++) {
	       		var ifra = iframes[0];
	       		ifra.style.height = ifra.contentWindow.document.body.scrollHeight + "px";
	       		
	       		if (parseInt(iframe.getStyle('height').replace('px', '')) < parseInt(ifra.style.height.replace('px', ''))) {
	       			iFrameID.style.height = "";
	       			iFrameID.style.height = ifra.style.height;
	       			iframe.setStyle('height', ifra.style.height);
	       		}
	       	 }
	       	 */
	       	 A.one("#close-scorm").on('click', function() {<portlet:namespace/>update_scorm(event);});
    	},
    	['node', 'event', 'node-event-simulate']
     );
     
     AUI().ready(function() {
    	 InitPlayer();
    	 
    	 document.getElementById('contentIFrame').onload = function() {setTimeout(adjustScorm, 400);}
   	 });
     
     function toggleScormBar(evt) {
    	 var treeContainer = document.getElementById('placeholder_treeContainer');
    	 var iframe = document.getElementById('placeholder_contentIFrame');
    	 var menuA = document.getElementById('open-close-scorm-menu');
    	 if (treeContainer.style.display != 'none') {
    		 treeContainer.style.display = 'none';
    		 iframe.style.width = '100%';
    		 menuA.setAttribute('class', 'open-scorm-menu');
    	 } else {
    		 treeContainer.style.display = 'block';
    		 iframe.style.width = '80%';
    		 menuA.setAttribute('class', 'close-scorm-menu');
    	 }
    	 if (evt.preventDefault) {
     	 	evt.preventDefault();
     	 }
     	 evt.returnValue = false;
     }
     
  </script>
  <div id="placeholder_barContainer">
  	<a id="clicker" href="#" style="display: none">__</a>
  	<aui:button id="close-scorm" value="activity.try.exit" style="float: right; display: none" />
  	<a id="open-close-scorm-menu" href="#" onclick="javascript:toggleScormBar(event)">+</a>
  	
  </div>
<div id="placeholder_treecontentContainer">
	<div id="placeholder_treeContainer"></div>
	<div id="placeholder_contentIFrame" style="height: 670px;">
          <iframe id="contentIFrame" style="height:100%; width:100%"></iframe>
    </div>          
</div>

