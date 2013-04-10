<%@page import="java.util.Collection"%>
<%@page import="com.liferay.lms.service.impl.LearningActivityLocalServiceImpl"%>
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
<liferay-ui:error key="p2ptaskactivity-error-file-type" message="p2ptaskactivity.error.file.type" />

<%
String textoCorrecion = LanguageUtil.get(pageContext,"p2ptask-text-corrections");
String correctionsCompleted = ParamUtil.getString(request, "correctionsCompleted", "false");
String correctionsSaved = ParamUtil.getString(request, "correctionSaved", "false");


long actId=ParamUtil.getLong(request,"actId",0);
long latId=ParamUtil.getLong(request,"latId",0);
long resultuser=ParamUtil.getLong(request,"resultuser",0);


LearningActivity activity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
String numCorrecciones = "3";
if(!activity.getExtracontent().equals("")){
	//numCorrecciones=activity.getExtracontent();
	numCorrecciones = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"validaciones");
}
	
boolean anonimous = false;
String anonimousString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"anonimous");

if(anonimousString.equals("true")){
	anonimous = true;
}


boolean result = false;
String resultString = LearningActivityLocalServiceUtil.getExtraContentValue(actId,"result");

if(resultString.equals("true")){
	result = true;
}
%>

<script type="text/javascript">
	var noFile ='<liferay-ui:message key="p2ptaskactivity.inc.nofileselected" />';
	
	function checkDataformC(thisForm){
		var selector = 'form[name="'+thisForm+'"]';
		var descriptionVal = $(selector).contents().find('textarea[name="<portlet:namespace />description"]').val();
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
		var textResult = $('select[name="<portlet:namespace />resultuser"]').val(); // $(selector).contents().find('select[name="<portlet:namespace />resultuser"]').val();

		if(fileName!=""){
			var pos = fileName.lastIndexOf("\\");
			if(pos>0)
				fileName = fileName.substring(pos+1);
			
		}else{
			fileName = noFile;
		}
		$("#contentFileCorrec").html(fileName);
		$("#contentDescriptionCorrec").html(textDesc);
		$("#contentResult").html(textResult);
		$("#submitCorrec").click(function(){commitForm(formName);});
		
		//Start opening popUp
		$('#p2pconfrmCorrec').modal();
	}

	function commitForm(formName){
		var selector = 'form[name="'+formName+'"]';
		$("#submitCorrec").attr("disabled", "disabled");
		$(selector).submit();
	}
	
	function closeModal(){
		$.modal.close();
		window.setTimeout(function() {jQuery('#p2pCorrectionCompleted').modal()}, 300);
	}
	
	function openSaved(){
		$("#p2pSaved").modal();
	}
	
	function openCompleted(){
		$("#p2pCorrectionCompleted").modal();
		
	}
</script>

<!-- Start PopUp confirmation -->

<div id="p2pconfrmCorrec" style="display:none">
	<h1><liferay-ui:message key="p2ptask-uploadcorrect-confirmation" /></h1>
	<div class="desc color_tercero"><liferay-ui:message key="p2ptask-uploadcorrect-description" /></div>
	<br />
	<div class="contDesc description">
		<p><span class="label"><liferay-ui:message key="p2ptask-description-task" />: </span><span id="contentDescriptionCorrec"></span></p>
		<p><span class="label"><liferay-ui:message key="p2ptask-file-name" />: </span> <span id="contentFileCorrec"></span></p>
		<c:if test="<%=result %>">
			<p><span class="label"><liferay-ui:message key="p2ptask-file-result" />: </span> <span id="contentResult"></span></p>
		</c:if>
		<p class="message"><liferay-ui:message key="p2ptask-uploadcorrect-task-message" /></p>
	</div>
	<div class="buttons">
		<input type="button" class="button simplemodal-close" value="<liferay-ui:message key="cancel" />" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" id="submitCorrec" value="<liferay-ui:message key="p2ptask-correction" />" />
	</div>
</div>
<!-- End PopUp confrimation -->


<div id="p2pCorrectionCompleted" style="display:none">
	<h1><liferay-ui:message key="p2ptaskactivity.inc.p2pcorrectioncompleted.title" /></h1>
	<div class="desc color_tercero"><liferay-ui:message key="p2ptaskactivity.inc.p2pcorrectioncompleted.subtitle" /></div>
	<br />
	<div class="contDesc bg-icon-check">
		<p><liferay-ui:message key="p2ptaskactivity.inc.p2pcorrectioncompleted.message" /></p>
	</div>
	<div class="buttons">
		<input type="button" class="button simplemodal-close" id="buttonClose" value="<liferay-ui:message key="close" />" onclick="closeForm()" />
	</div>
</div>

<div id="p2pSaved" style="display:none">
	<h1><liferay-ui:message key="p2ptaskactivity.inc.p2puploadedcompleted.title" /></h1>
	<div class="desc color_tercero"><liferay-ui:message key="p2ptaskactivity.inc.p2puploadedcompleted.subtitle" /></div>
	<br />
	<div class="contDesc bg-icon-ok">
		<p><liferay-ui:message key="p2ptaskactivity.inc.p2puploadedcompleted.message" /></p>
	</div>
	<div class="buttons">
	<%if(correctionsCompleted.equals("true")){ %>
		<input type="button" class="button " id="completed" value="<liferay-ui:message key="close" />" onclick="closeModal();" />
	<%}else{ %>
		<input type="button" class="button simplemodal-close" id="buttonClose" value="<liferay-ui:message key="close" />" onclick="closeForm()" />
	<%} %>
	</div>
</div>


<%

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


int numCorrecl = Integer.parseInt(numCorrecciones);

long userId = themeDisplay.getUserId();
int cont = 0;
List<Long> listIdActivY = new ArrayList<Long>(); 

boolean allCorrected = true;

//Obtenemos todas las correcciones que tiene asignado el usuario.
//List<P2pActivityCorrections> p2pActList = P2pActivityCorrectionsLocalServiceUtil.findByActIdIdAndUserId(actId, userId);
List<P2pActivityCorrections> p2pActList = P2pActivityCorrectionsLocalServiceUtil.findByActIdAndUserIdOrderByDate(actId, userId);

int contaValidations = 0;

LearningActivityTry larEntry=LearningActivityTryLocalServiceUtil.getLearningActivityTry(latId);

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

List<P2pActivityCorrections> listDone = P2pActivityCorrectionsLocalServiceUtil.getCorrectionsDoneByUserInP2PActivity(actId, userId);

String []arg = {String.valueOf(numCorrecl-listDone.size()) , String.valueOf(numCorrecl)};

%>
	<div class="numbervalidations color_tercero font_13">
		<p>
			<c:if test="<%=numCorrecl-listDone.size() > 0 %>">
				<liferay-ui:message key="p2ptaskactivity.inc.numbervalidations" arguments="<%=arg %>" /> 
			</c:if>
			<c:if test="<%=numCorrecl-listDone.size() == 0 %>">
				<liferay-ui:message key="p2ptaskactivity.inc.validationscompleted" /> 
			</c:if>
		</p>
	</div>

<%
if(!p2pActList.isEmpty()){
	
	for (P2pActivityCorrections myP2PActiCor : p2pActList){
		
		if(contaValidations >= numCorrecl){
			break;
		}
		
		P2pActivity myP2PActivity = P2pActivityLocalServiceUtil.getP2pActivity(myP2PActiCor.getP2pActivityId());
		
		//Si no estamos en el usuario actual.
		if(myP2PActivity.getUserId()!=userId){
			
			//Si la fecha de corrección es null, es que no se ha corregido la tarea.
			if(myP2PActiCor.getDate() == null)
			{
				User propietary = UserLocalServiceUtil.getUser(myP2PActivity.getUserId());
				DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActivity.getFileEntryId());
				String urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
				
				cont++;

				allCorrected = false;
				%>
				<div class="option-more">
					
					<span class="label-col"><liferay-ui:message key="p2ptask-exercise" /> 
						<c:if test="<%=!anonimous %>">
							<span class="name">
								<liferay-ui:message key="of" /> 
								<%=propietary.getFullName() %>
							</span>
						</c:if>
						<span class="number">
							<liferay-ui:message key="number" /> 
							<%=cont%>
						</span>
					</span>
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
							<liferay-ui:error key="p2ptaskactivity-error-file-size" message="p2ptaskactivity.error.file.size" />
							<div class="container-file">
								<aui:input inlineLabel="left" inlineField="true"
										  	name="fileName" id="fileName" type="file" value="" />
							</div>
							<c:if test="<%=result %>">
								<div class="container-result color_tercero font_14">
									<liferay-ui:message key="p2ptask.correction.selected.result" />
									<aui:select name="resultuser"  id="resultuser" label="">
								    	<%for(int i=100; i>=0; i=i-10){%>
								        	<option value="<%=i%>"><%=i %></option>
								        <%}%>
								    </aui:select>
								</div>
							</c:if>
							<div>
								<input type="button" class="button floatr" value="<liferay-ui:message key="p2ptask-correction" />" onclick="checkDataformC('<portlet:namespace />f1_<%=cont%>')" />
							</div>
						</form>
					</div>
				</div>
			<%
			} 
			//Si ya ha corregido la tarea, solo debemos mostrar lo que se ha introducido.
			else
			{
				listIdActivY.add(myP2PActivity.getPrimaryKey());
				User propietary = UserLocalServiceUtil.getUser(myP2PActivity.getUserId());
				
				cont++;
				
				String description = myP2PActiCor.getDescription();
				String textButton = "p2ptask-correction";
	
				DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActivity.getFileEntryId());
				String urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid(); 
	
				%>
	
				<div class="option-more">
					<span class="label-col">
						<liferay-ui:message key="p2ptask-exercise" />
						<c:if test="<%=!anonimous %>">
							<span class="name">
								<liferay-ui:message key="of" /> 
								<%=propietary.getFullName() %>
							</span>
						</c:if>
						<span class="number">
							<liferay-ui:message key="number" /> 
							<%=cont%>
						</span>
					</span>
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
					<c:if test="<%=result %>">
							<div class="color_tercero font_13"><liferay-ui:message key="p2ptask.correction.activity" />: <%=myP2PActiCor.getResult() %></div>
							</c:if>
					</div>
				</div>
				<%
			}
		}
		//Mostrar sólo el numero de correcciones que debe corregir.
		contaValidations++;
	}
}



if(p2pActList.isEmpty()){
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
	
	<div style="font-size: 14px;color: #B70050;font-weight: bold;"><liferay-ui:message key="no-p2pActivites-uploaded" /></div>
	<%
}%>

<%
if(correctionsSaved.equals("true")){
	correctionsSaved="false";
	request.setAttribute("correctionsSaved", correctionsSaved);
	%><script>openSaved();</script><%
}

%>