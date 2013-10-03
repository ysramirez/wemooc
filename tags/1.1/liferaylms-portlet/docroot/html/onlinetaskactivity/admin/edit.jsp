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
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@ include file="/init.jsp" %>

<%
	boolean fichero = false;
	boolean textoenr = false;
	boolean existTries = false;
	if(request.getAttribute("activity")!=null) {
		LearningActivity learningActivity=(LearningActivity)request.getAttribute("activity");	
		if ((learningActivity.getExtracontent()!=null)&&(learningActivity.getExtracontent().trim().length()!=0)) {
	
			fichero = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"fichero"));
			textoenr = StringPool.TRUE.equals(LearningActivityLocalServiceUtil.getExtraContentValue(learningActivity.getActId(),"textoenr")); 
				
		}
		
		
	    DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityTry.class);
	  	Criterion criterion=PropertyFactoryUtil.forName("actId").eq(learningActivity.getActId());
		dq.add(criterion);
	    existTries = LearningActivityTryLocalServiceUtil.dynamicQueryCount(dq)!=0;
	}
%>
<p><liferay-ui:message key="onlinetaskactivity.permitStudents"/></p>
<aui:input type="checkbox" name="fichero" label="onlinetaskactivity.save.file" checked="<%=fichero %>" disabled='<%=existTries %>' inlineField="true"></aui:input>
<aui:input type="checkbox" name="textoenr" label="onlinetaskactivity.richTech" checked="<%=textoenr %>" disabled='<%=existTries %>' inlineField="true"></aui:input>
