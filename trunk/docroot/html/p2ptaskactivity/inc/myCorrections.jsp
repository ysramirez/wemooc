
<%@page import="com.liferay.util.P2pCheckActivity"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.lms.service.P2pActivityLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.P2pActivity"%>
<%@page import="com.liferay.lms.model.P2pActivityCorrections"%>
<%@page import="com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil"%>

<%@include file="/init.jsp" %>
<%
long actId=ParamUtil.getLong(request,"actId",0);
long userId = themeDisplay.getUserId();

P2pActivity myp2pActivity = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);

List<P2pActivityCorrections> p2pActCorList = P2pActivityCorrectionsLocalServiceUtil.
		findByP2pActivityId(myp2pActivity.getP2pActivityId());

DLFileEntry dlfile = null;
String urlFile = "";

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
String correctionDate = "";
int cont=0;

if(!p2pActCorList.isEmpty()){
	for (P2pActivityCorrections myP2PActCor : p2pActCorList){
		
		cont++;
		User propietary = UserLocalServiceUtil.getUser(myP2PActCor.getUserId());
		String correctionText = myP2PActCor.getDescription();
		if(myP2PActCor.getFileEntryId()!=0)
		{
			dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(myP2PActCor.getFileEntryId());
			urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid();
		}
		Date date = myP2PActCor.getDate();
		correctionDate = dateFormat.format(date);
		
		%>
		<div class="option-more">
		<span class="label-col"><liferay-ui:message key="p2ptask-correction-title" /> <span class="name"><liferay-ui:message key="by" /> <%=propietary.getFullName() %></span><span class="number"><liferay-ui:message key="number" /> <%=cont%></span> <%=correctionDate %></span>
			<div class="collapsable" style="padding-left:10px">
				<div class="container-textarea">
					<textarea rows="6" cols="90" readonly="readonly" ><%=correctionText %></textarea>
				</div>
				<%
				if(dlfile!=null){
					int size = Integer.parseInt(String.valueOf(dlfile.getSize()));
					int sizeKb = size/1024; //Lo paso a Kilobytes
				%>
				<div class="doc_descarga">
					<span><%=dlfile.getTitle()%>&nbsp;(<%= sizeKb%> Kb)&nbsp;</span>
					<a href="<%=urlFile%>" class="verMas" target="_blank"><liferay-ui:message key="p2ptask-donwload" /></a>
				</div>
				<%
				}
				%>
			</div>
		</div>
		<%
	}
}else{
	%>
	<div style="font-size: 14px;color: #B70050;font-weight: bold;">
		<liferay-ui:message key="no-p2pActivites-corretion" />
	</div>
	<%
}%>