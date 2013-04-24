<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@ include file="/init.jsp" %>
<%
	String youtubecode=StringPool.BLANK;
	LearningActivity learningActivity=null;
	if(request.getAttribute("activity")!=null) {
		learningActivity=(LearningActivity)request.getAttribute("activity");
		
		if((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0))
		{
			Document document = SAXReaderUtil.read(learningActivity.getExtracontent());
			Element root=document.getRootElement();
			Element video=root.element("video");
			
			if(video!=null)
			{
				youtubecode=video.getText();
			}
		
		}
	}  
	
%>
<aui:field-wrapper label="video" >
  	<aui:input name="youtubecode" type="textarea" rows="6" cols="45" label="youtube-code" value="<%=youtubecode %>" ignoreRequestValue="true"></aui:input>
</aui:field-wrapper>
