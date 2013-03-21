<%@page import="com.liferay.lms.service.impl.LearningActivityLocalServiceImpl"%>
<%@page	import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page	import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page	import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page	import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.Criterion"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.DynamicQuery"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil"%>
<%@page	import="com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityResult"%>
<%@ include file="init.jsp"%>

<%
	long numEvaluaciones = 0;
	boolean fichero = false;
	String ficheroString = "";
	boolean textoenr = false;
	String textoenrString = "";
	
	if (learnact.getExtracontent() != null && !learnact.getExtracontent().trim().equals("")) {

		
		ficheroString = LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"fichero");
		
		if(ficheroString.equals("true")){
			fichero = true;
		}
		
		textoenrString = LearningActivityLocalServiceUtil.getExtraContentValue(learnact.getActId(),"textoenr");
	
		if(textoenrString.equals("true")){
			textoenr = true;
		}
			
	}
	
	
    DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityResult.class);
  	Criterion criterion=PropertyFactoryUtil.forName("actId").eq(learnact.getActId());
	dq.add(criterion);
    boolean existTries = LearningActivityTryLocalServiceUtil.dynamicQueryCount(dq)!=0;
%>
<portlet:actionURL var="camposExtraURL" name="camposExtra">
</portlet:actionURL>
<portlet:renderURL var="cancel">
	<portlet:param name="actId" value="0"></portlet:param>
</portlet:renderURL>

<aui:form action="<%=camposExtraURL%>" method="post">
	<aui:input name="actId" type="hidden" value="<%=learnact.getActId()%>"></aui:input>
	<aui:input type="checkbox" name="fichero" label="onlinetaskactivity.save.file" checked="<%=fichero %>" disabled='<%=existTries %>' ></aui:input>
	<aui:input type="checkbox" name="textoenr" label="onlinetaskactivity.richTech" checked="<%=textoenr %>" disabled='<%=existTries %>' ></aui:input>
	<aui:button-row>
		<aui:button type="submit" disabled='<%=existTries %>' value="onlinetaskactivity.save" />
		<aui:button onclick="<%=\"window.location='\"+cancel+\"';\"%>" type="cancel" disabled='<%=existTries %>' value="onlinetaskactivity.cancel" />
	</aui:button-row>
</aui:form>