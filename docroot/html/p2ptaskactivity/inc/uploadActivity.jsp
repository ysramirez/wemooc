<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>

<%@include file="/init.jsp" %>

<portlet:actionURL name="addP2PActivity" var="addP2PActivity">
</portlet:actionURL> 

<liferay-ui:error key="campos-necesarios-vacios" message="campos-necesarios-vacios" />
<liferay-ui:error key="error-subir-p2p" message="error-subir-p2p" />
<%
long actId = ParamUtil.getLong(request, "actId",0);
String textCorrection = LanguageUtil.get(pageContext,"p2ptask-text-upload");

Long userId = themeDisplay.getUserId();
P2pActivity myP2PActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);
long p2pActivityId = 0;

%>
	
<script type="text/javascript">
function checkDataform(){
	var idDesc="#<portlet:namespace />description";
	var idFile = "#<portlet:namespace />fileName";
	
	if($(idDesc).val()=="" || $(idDesc).val()=="<%=textCorrection%>"){
		alert('<liferay-ui:message key="p2ptask-empty-desc" />');
		return false;
	}
	if($(idFile).val()==""){
		alert('<liferay-ui:message key="p2ptask-empty-file" />');
		return false;
	}
	openPopUp();
}
function openPopUp(){
	var fileName = $("#<portlet:namespace />fileName").val();
	var idDesc = $("#<portlet:namespace />description").val();
	var pos = fileName.lastIndexOf("\\");
	if(pos>0)
		fileName = fileName.substring(pos+1);
	
	$("#contentFile").html(fileName);
	$("#contentDescription").html(idDesc);
	//Start opening popUp
	$('#p2pconfirmation').modal();
}
function clearText(){
	var idDesc="#<portlet:namespace />description";
	var textReplace = "<%=textCorrection%>";
	if($(idDesc).val()==textReplace){
		$(idDesc).val("");
	}
}
function commitForm(){
	var idForm = "#<portlet:namespace />fm_1";
	$(idForm).submit();
}

</script>
<!-- Start PopUp confirmation -->
<style type="text/css">
	#simplemodal-container{width:auto;height:380px;}
	#simplemodal-container p{color:#000;font-size:1.4em;}
	#simplemodal-container p span.label{font-weight: bold}
	#simplemodal-container div.desc{font-size:1.4em;color:#006C68;font-weight: bold}
</style>

<div id="p2pconfirmation" style="display:none">
	<h1><liferay-ui:message key="p2ptask-upload-confirmation" /></h1>
	<div class="desc"><liferay-ui:message key="p2ptask-upload-description" /></div>
	<br />
	<p><span class="label"><liferay-ui:message key="p2ptask-file-name" />:</span> <span id="contentFile"></span></p>
	<p><span class="label"><liferay-ui:message key="p2ptask-description-task" />:</span> <span id="contentDescription"></span></p>
	<div style="text-align:center">
		<input type="button" class="button simplemodal-close" value="<liferay-ui:message key="cancel" />" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" value="<liferay-ui:message key="p2ptask-send" />" onclick="commitForm()" />
	</div>
</div>
<!-- End PopUp confrimation -->
<div>
	<%
	if(myP2PActivity!=null){

		DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActivity.getFileEntryId());
		String urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
		textCorrection = myP2PActivity.getDescription();
		p2pActivityId = myP2PActivity.getP2pActivityId();
		
		%>
		<div class="container-textarea">
			<textarea rows="6" cols="90" readonly="readonly" ><%=textCorrection %></textarea>
		</div>
		<% 
			int size = Integer.parseInt(String.valueOf(dlfile.getSize()));
			int sizeKb = size/1024; //Lo paso a Kilobytes
		%>
		<div class="doc_descarga">
			<span><%=dlfile.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
			<a href="<%=urlFile%>" class="verMas" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
		</div>
		<%
	}else{%>
		<form name="<portlet:namespace />fm_1" id="<portlet:namespace />fm_1" action="<%=addP2PActivity%>" method="POST" enctype="multipart/form-data" >
			<aui:input name="actId" type="hidden" value="<%=actId %>" />
			<aui:input name="p2pActivityId" type="hidden" value="<%=p2pActivityId %>" />
			<div class="container-textarea">
				<aui:input name="description" type="textarea" label="" onfocus="clearText()" rows="6" cols="90" value="<%=textCorrection %>" id="description" />
			</div>
			<div class="container-file">
				<aui:input inlineLabel="left" inlineField="true"
						  	name="fileName" id="fileName" type="file" value="" />
			</div>
			<!--
			<div>
				<aui:input name="check" id="check" checked="false" label='<liferay-ui:message key="p2ptask-publish-task" />' type="checkbox" value="1"></aui:input>
			</div>
			 -->
			<div>
				<input type="button" class="button floatr" value="<liferay-ui:message key="p2ptask-send" />" onclick="checkDataform()" />
			</div>
		</form>
<%	}%>
</div>