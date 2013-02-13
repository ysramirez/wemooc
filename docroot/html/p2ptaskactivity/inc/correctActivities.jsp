<%@page import="com.liferay.lms.model.Module"%>
<%@page import="com.liferay.lms.service.ModuleLocalServiceUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.P2pActivityCorrections"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.portlet.p2p.P2PActivityPortlet"%>

<%@include file="/init.jsp" %>

<liferay-ui:error key="p2ptask-no-empty-answer" message="p2ptask-no-empty-answer" />
<liferay-ui:error key="error-p2ptask-correction" message="error-p2ptask-correction" />

<%
String textoCorrecion = LanguageUtil.get(pageContext,"p2ptask-text-corrections");
%>

<script type="text/javascript">
	function checkDataformC(thisForm){
		var selector = 'form[name="'+thisForm+'"]';
		var descriptionVal = $(selector).contents().find('textarea[name="description"]').val();
		if(descriptionVal=="" || descriptionVal=="<%=textoCorrecion%>"){
			alert('<liferay-ui:message key="p2ptask-no-empty-answer" />');
		}
		else{
			openPopUp(thisForm);
		}
	}
	function clearText(id){
		var idtext = "#"+id;
		var textReplace = "<%=textoCorrecion%>";
		if($(idtext).val()==textReplace){
			$(idtext).val("");
		}
	}
	
	function openPopUp(formName){
		var selector = 'form[name="'+formName+'"]';
		var fileName =$(selector).contents().find('input[name="<portlet:namespace />fileName"]').val();
		var textDesc = $(selector).contents().find('textarea[name="<portlet:namespace />description"]').val();
		if(fileName!=""){
			var pos = fileName.lastIndexOf("\\");
			if(pos>0)
				fileName = fileName.substring(pos+1);
			$("#contentFileCorrec").html(fileName);
		}
		$("#contentDescriptionCorrec").html(textDesc);
		$("#submitCorrec").click(function(){commitForm(formName);});
		
		//Start opening popUp
		$('#p2pconfrmCorrec').modal();
	}

	function commitForm(formName){
		var selector = 'form[name="'+formName+'"]';
		$(selector).submit();
	}
</script>

<!-- Start PopUp confirmation -->
<style type="text/css">
	#simplemodal-container{width:auto;height:380px;}
	#simplemodal-container p{color:#000;font-size:1.4em;}
	#simplemodal-container p span.label{font-weight: bold}
	#simplemodal-container div.desc{font-size:1.4em;color:#006C68;font-weight: bold}
</style>

<div id="p2pconfrmCorrec" style="display:none">
	<h1><liferay-ui:message key="p2ptask-upload-confirmation" /></h1>
	<div class="desc"><liferay-ui:message key="p2ptask-upload-description" /></div>
	<br />
	<p><span class="label"><liferay-ui:message key="p2ptask-file-name" />:</span> <span id="contentFileCorrec"></span></p>
	<p><span class="label"><liferay-ui:message key="p2ptask-description-task" />:</span> <span id="contentDescriptionCorrec"></span></p>
	<div style="text-align:center">
		<input type="button" class="button simplemodal-close" value="<liferay-ui:message key="cancel" />" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" id="submitCorrec" value="<liferay-ui:message key="p2ptask-correction" />" />
	</div>
</div>
<!-- End PopUp confrimation -->


<%

long actId=ParamUtil.getLong(request,"actId",0);
long latId=ParamUtil.getLong(request,"latId",0);


if(latId==0){
	if(LearningActivityTryLocalServiceUtil.getLearningActivityTryByActUserCount(actId, themeDisplay.getUserId())>0){
		List<LearningActivityTry> latList = LearningActivityTryLocalServiceUtil.
				getLearningActivityTryByActUser(actId, themeDisplay.getUserId());
		if(!latList.isEmpty())
		{
			for(LearningActivityTry lat :latList){
				latId = lat.getLatId();
			}
		}
	}
}


LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
String numCorrecciones = "3";
if(!activity.getExtracontent().equals(""))
	numCorrecciones=activity.getExtracontent();

long numCorrecl = Long.parseLong(numCorrecciones);

long userId = themeDisplay.getUserId();
int cont = 0;
List<Long> listIdActivY = new ArrayList<Long>(); 

List<P2pActivityCorrections> p2pActList = P2pActivityCorrectionsLocalServiceUtil.findByActIdIdAndUserId(actId, userId);
LearningActivityTry larEntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

if(!p2pActList.isEmpty()){
	
	for (P2pActivityCorrections myP2PActiCor : p2pActList){
		P2pActivity myP2PActivity = P2pActivityLocalServiceUtil.getP2pActivity(myP2PActiCor.getP2pActivityId());
		
		if(myP2PActivity.getUserId()!=userId){
			
			cont++;
			listIdActivY.add(myP2PActivity.getPrimaryKey());
			User propietary = UserLocalServiceUtil.getUser(myP2PActivity.getUserId());
			
			String description = myP2PActiCor.getDescription();
			String textButton = "p2ptask-correction";
			/*String correctionDate="";
			if(myP2PActivity.getDate()!=null){
				correctionDate = dateFormat.format(myP2PActivity.getDate());
			}*/
			
			DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActivity.getFileEntryId());
			String urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid(); 
			
			%>
			<div class="option-more">
				<span class="label-col"><liferay-ui:message key="p2ptask-exercise" /> <span class="name"><liferay-ui:message key="of" /> <%=propietary.getFullName() %></span><span class="number"><liferay-ui:message key="number" /> <%=cont%></span></span>
				<div class="collapsable">
					<%
					String descriptionFile = "";
					if(myP2PActivity.getDescription()!=null){
						descriptionFile = myP2PActivity.getDescription();
					}
					%>
					<div class="description"><%=descriptionFile %></div>
					<%
					int size = Integer.parseInt(String.valueOf(dlfile.getSize()));
					int sizeKb = size/1024; //Lo paso a Kilobytes
					%>
					<div class="doc_descarga">
						<span><%=dlfile.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
						<a href="<%=urlFile%>" class="verMas" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
					</div>
					<div class="degradade">
						<div class="subtitle"><liferay-ui:message key="p2ptask-your-valoration" /> :</div>
						<div class="container-textarea">
							<textarea rows="6" cols="80" name="description" readonly="readonly"><%=description %></textarea>
						</div>
						<%
						if(myP2PActiCor.getFileEntryId()!=0){
							DLFileEntry dlfileCor = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActiCor.getFileEntryId());
							String urlFileCor = themeDisplay.getPortalURL()+"/documents/"+dlfileCor.getGroupId()+"/"+dlfileCor.getUuid();
							size = Integer.parseInt(String.valueOf(dlfileCor.getSize()));
							sizeKb = size/1024; //Lo paso a Kilobytes
						%>
						<div class="doc_descarga">
							<span><%=dlfileCor.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
							<a href="<%=urlFileCor%>" class="verMas" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
						</div>
						<%} %>
					</div>
				</div>
			</div>
			<%
		}
		if(cont >= numCorrecl){
			P2PActivityPortlet.updateStateActivity(latId);
			break;
		}
	}
}
//Si no ha suficientes tareas corregidas. Obtenemos las que faltas para completar la actividad.
if(cont < numCorrecl){
	List<P2pActivity> ListP2PinAct = P2pActivityLocalServiceUtil.findByActId(actId);
	if(!ListP2PinAct.isEmpty()){
		
		for (P2pActivity myP2PActivity :ListP2PinAct){
			
			boolean hasPuntuated = false;
			
			if(!listIdActivY.isEmpty()){
				for(int i=0;i<listIdActivY.size();i++){
					if(myP2PActivity.getPrimaryKey()==listIdActivY.get(i))
						hasPuntuated = true;
				}
			}
			
			
			if(myP2PActivity.getUserId()!=userId && !hasPuntuated)
			{
				cont++;
				User propietary = UserLocalServiceUtil.getUser(myP2PActivity.getUserId());
				DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActivity.getFileEntryId());
				String urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
				/*String correctionDate="";
				if(myP2PActivity.getDate()!=null){
					correctionDate = dateFormat.format(myP2PActivity.getDate());
				}*/
				
				%>
				<div class="option-more">
					
					<span class="label-col"><liferay-ui:message key="p2ptask-exercise" /> <span class="name"><liferay-ui:message key="of" /> <%=propietary.getFullName() %></span><span class="number"><liferay-ui:message key="number" /> <%=cont%></span></span>
					<div class="collapsable">
						<form enctype="multipart/form-data" method="post" action="<portlet:actionURL name="saveCorrection"></portlet:actionURL>" name="<portlet:namespace />f1_<%=cont%>" id="<portlet:namespace />f1_<%=cont%>">
							<input type="hidden" name="actId" value="<%=actId%>"  />
							<input type="hidden" name="latId" value="<%=latId%>"  />
							<input type="hidden" name="p2pActivityCorrectionId" value="0"  />
							<input type="hidden" name="p2pActivityId" value="<%=myP2PActivity.getP2pActivityId()%>"  />
							<input type="hidden" name="userId" value="<%=userId%>"  />
							<%
							String descriptionFile = dlfile.getDescription();
							if(descriptionFile.equals(""))
							{
								if(myP2PActivity.getDescription()!=null && !myP2PActivity.getDescription().equals("")){
									descriptionFile = myP2PActivity.getDescription();
								}
							}
							
							%>
							<div class="description">
								<%=descriptionFile %>
							</div>
							<%
							int size = Integer.parseInt(String.valueOf(dlfile.getSize()));
							int sizeMb = size/1024; 
							%>
							<div class="doc_descarga">
								<span><%=dlfile.getTitle()%>&nbsp;(<%= sizeMb%> Kb)&nbsp;</span>
								<a href="<%=urlFile%>" class="verMas" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
							</div>
							
							<div class="container-textarea">
								<textarea rows="6" cols="90" name="<portlet:namespace />description" onfocus="clearText('<portlet:namespace />description_<%=cont%>')" id="<portlet:namespace />description_<%=cont%>"><%=textoCorrecion %></textarea>
							</div>
							<div class="container-file">
								<aui:input inlineLabel="left" inlineField="true"
										  	name="fileName" id="fileName" type="file" value="" />
							</div>
							
							<div>
								<input type="button" class="button floatr" value="<liferay-ui:message key="p2ptask-correction" />" onclick="checkDataformC('<portlet:namespace />f1_<%=cont%>')" />
							</div>
						</form>
					</div>
				</div>
			<%
			}
			if(cont >= numCorrecl){
				break;	
			}
		}
	}
}
else{
	request.setAttribute("actId",actId);
	request.setAttribute("learningActivity",activity);
	request.setAttribute("larntry",larEntry);
	%>
	<jsp:include page="/html/shared/popResult.jsp" />
	<%
}

if(cont==0){
	Group grupo=themeDisplay.getScopeGroup();
	long retoplid=themeDisplay.getPlid();
	for(Layout theLayout:LayoutLocalServiceUtil.getLayouts(grupo.getGroupId(),false))
	{

		if(theLayout.getFriendlyURL().equals("/reto"))
		{
			retoplid=theLayout.getPlid();
		}
	}
	LearningActivity  act = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	Module myModule = ModuleLocalServiceUtil.getModule(act.getModuleId());
	
	%>
	<liferay-portlet:renderURL plid="<%=retoplid %>" portletName="lmsactivitieslist_WAR_liferaylmsportlet" var="gotoModuleURL">
	<liferay-portlet:param name="moduleId" value="<%=Long.toString(myModule.getModuleId()) %>"></liferay-portlet:param>
	</liferay-portlet:renderURL>
	<%
	P2PActivityPortlet.sendMailNoCorrection(user, actId, themeDisplay, portletConfig, gotoModuleURL.toString());
	%>
	
	<div style="font-size: 14px;color: #B70050;font-weight: bold;"><liferay-ui:message key="no-p2pActivites-uploaded" /></div>
	<%
}%>

