<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.DocumentException"%>
<%@page import="com.liferay.portal.kernel.xml.Node"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portlet.documentlibrary.FileSizeException"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.lms.OnlineActivity"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>

<%@ include file="/init.jsp" %>
<%

LearningActivityTry lATry = null;
LearningActivityResult result = null;
boolean ownGrade=false;

if(renderRequest.getParameter("actId")!=null)
{
	
	if(renderRequest.getParameter("studentId")!=null){
		ownGrade=false;
		lATry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(ParamUtil.getLong(renderRequest,"actId"), ParamUtil.getLong(renderRequest,"studentId"));
		result = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(ParamUtil.getLong(renderRequest,"actId"), ParamUtil.getLong(renderRequest,"studentId"));	
	}
	else {
		ownGrade=true;	
		lATry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(ParamUtil.getLong(renderRequest,"actId"), themeDisplay.getUserId());
	}
	
}

String urlFile=null;
String titleFile=null;
//String iconFile=null;
long sizeKbFile=0;

String text=null;
String richtext=null;


if(lATry!=null){
	try {
		Iterator<Node> nodeItr = SAXReaderUtil.read(lATry.getTryResultData()).getRootElement().nodeIterator();
		while(nodeItr.hasNext()) {
	         Node element = nodeItr.next();
	         if(OnlineActivity.FILE_XML.equals(element.getName())) {
        		try{
	        	 	DLFileEntry dlfile = DLFileEntryLocalServiceUtil.getDLFileEntry(Long.parseLong(((Element)element).attributeValue("id")));
	    			urlFile = themeDisplay.getPortalURL()+"/documents/"+dlfile.getGroupId()+"/"+dlfile.getUuid(); 
	    			titleFile = dlfile.getTitle();
	    			sizeKbFile = dlfile.getSize()/1024;
	    			//iconFile=DLUtil.getFileIcon(dlfile.getExtension());
        		}
        		catch(Throwable a){}
	         }
	         else if(OnlineActivity.RICH_TEXT_XML.equals(element.getName())) {
	        	 richtext=element.getText();
	         }	
	         else if(OnlineActivity.TEXT_XML.equals(element.getName())) {
	        	 text=element.getText(); 
	         }	
	    }	
	}
	catch(DocumentException de)
	{}
}

 if(richtext!=null) { %>
	<aui:field-wrapper label="onlinetaskactivity.text" >
		<liferay-ui:panel-container >
			<liferay-ui:panel id="panelId" title="" collapsible="false" extended="false" >
				<%=richtext %>
			</liferay-ui:panel>
		</liferay-ui:panel-container >
	</aui:field-wrapper>
<% } 
 if(text!=null) {
%>
	<aui:input type="textarea" cols="100" rows="5" name="text" label="onlinetaskactivity.text" value='<%=text %>' readonly='readonly'/>
<% } 
 if(urlFile!=null) {
%>
	<liferay-ui:icon image="export" label="<%= true %>" message='<%=titleFile+"&nbsp;("+ sizeKbFile +"Kb)&nbsp;"%>' method="get" url="<%=urlFile%>"  />

<% } 

if(!ownGrade){
%>

<aui:form  name="fn_grades" method="post" >
	<aui:fieldset>
		<aui:input type="hidden" name="studentId" value='<%=renderRequest.getParameter("studentId") %>' />
	    <aui:input type="text" name="result" label="onlinetaskactivity.grades" value='<%=((result!=null)&&(result.getResult()>0))?Long.toString(result.getResult()):"" %>' />
	    <liferay-ui:error key="onlinetaskactivity.grades.result-bad-format" message="onlinetaskactivity.grades.result-bad-format" />
		<aui:input type="textarea" cols="40" rows="2" name="comments" label="onlinetaskactivity.comments" value='<%=((result!=null)&&(result.getComments()!=null))?result.getComments():"" %>'/>
	</aui:fieldset>
	<aui:button-row>
	<button name="Save" value="save" onclick="<portlet:namespace />doSaveGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.save" />
	</button>
	<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button">
		<liferay-ui:message key="offlinetaskactivity.cancel" />
	</button>
	</aui:button-row>
	<liferay-ui:success key="onlinetaskactivity.grades.updating" message="onlinetaskactivity.correct.saved" />
	<liferay-ui:error key="onlinetaskactivity.grades.bad-updating" message="onlinetaskactivity.grades.bad-updating" />
</aui:form>

<% } 
   else {%>
	<aui:button-row>
		<button name="Close" value="close" onclick="<portlet:namespace />doClosePopupGrades();" type="button">Close</button>
	</aui:button-row>
<% } %>
