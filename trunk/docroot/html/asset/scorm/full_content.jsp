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

%>

   <script type="text/javascript">
     function InitPlayer() {
       PlayerConfiguration.Debug = false;
       PlayerConfiguration.StorageSupport = true;

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
     
     Liferay.provide(
    	        window,
    	        '<portlet:namespace />patchTree',
    	        function() {
    				var A = AUI();
			    	A.all('#placeholder_treeContainer a').on('click', function(event) {
			    		if (event.preventDefault) {
			         	 	event.preventDefault();
			         	 }
			         	 event.returnValue = false;
			    	});
     			},
     			['node']
     			);
     
     AUI().ready(function() {
    	 InitPlayer();
    	 var iframe = document.getElementById('placeholder_contentIFrame');
    	 var contentIFrame = document.getElementById('contentIFrame');
    	 var treeContainer = document.getElementById('placeholder_treeContainer');
    	 
    	 if (treeContainer.style.display != 'none') {
    		 iframe.style.width = '80%';
    		 treeContainer.style.width = '20%';
    	 } else {
    		 iframe.style.width = '100%';
    	 }
    	 <portlet:namespace />patchTree();
   	 });
     
     function iResize() {
    	 console.log("Resized");
    	 document.getElementById('placeholder_contentIFrame').style.height = 
    	    document.getElementById('contentIFrame').contentWindow.document.body.offsetHeight + 'px';
    	 console.log(document.getElementById('placeholder_contentIFrame').style.height);
     }
     
     function toggleScormBar(evt) {
    	 var treeContainer = document.getElementById('placeholder_treeContainer');
    	 var iframe = document.getElementById('placeholder_contentIFrame');
    	 if (treeContainer.style.display != 'none') {
    		 treeContainer.style.display = 'none';
    		 iframe.style.width = '100%';
    	 } else {
    		 treeContainer.style.display = 'block';
    		 iframe.style.width = '80%';
    	 }
    	 if (evt.preventDefault) {
     	 	evt.preventDefault();
     	 }
     	 evt.returnValue = false;
     }
     
  </script>
  <div id="placeholder_barContainer">
  	<a id="clicker" href="#" style="display: none">__</a>
  	<a href="#" onclick="javascript:toggleScormBar(event)">+</a>
  </div>
<div id="placeholder_treecontentContainer">
	<div id="placeholder_treeContainer"></div>
	<div id="placeholder_contentIFrame">
          <iframe id="contentIFrame" width="100%" height="100%"></iframe>
    </div>          
</div>

