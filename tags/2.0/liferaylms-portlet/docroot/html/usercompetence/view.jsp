<%@page import="com.liferay.lms.model.Competence"%>

<%@ include file="/init.jsp" %>

<jsp:useBean id="competences" type="java.util.List" scope="request" />
<jsp:useBean id="totale" class="java.lang.String" scope="request" />
<jsp:useBean id="delta" class="java.lang.String" scope="request" />

<% 
	PortletURL viewURL = renderResponse.createRenderURL(); 
	viewURL.setParameter("delta", delta);
%>
<div id="user_competences">
	<liferay-ui:search-container curParam="act" emptyResultsMessage="there-are-no-competences" delta="10" deltaConfigurable="true" iteratorURL="<%=viewURL%>"  >
		<liferay-ui:search-container-results>
		<%
			pageContext.setAttribute("results", competences);
			try{
				pageContext.setAttribute("total", Integer.valueOf(totale));
			}catch(NumberFormatException nfe){
				pageContext.setAttribute("total", competences.size());
			}
		%>
		</liferay-ui:search-container-results>
		<liferay-ui:search-container-row className="com.liferay.lms.views.CompetenceView" keyProperty="competenceId" modelVar="cc">
			<liferay-ui:search-container-column-text name="competence.label" >
				<%=cc.getTitle(themeDisplay.getLocale()) %>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="review-date" >
				<%=cc.getFormatDate() %>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</div>