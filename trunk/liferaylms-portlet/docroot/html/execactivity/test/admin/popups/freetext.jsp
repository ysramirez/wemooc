<%@ include file="/init.jsp" %>
<%
Long iterator = ParamUtil.getLong(request, "iterator", -1);
%>

	<div id="<portlet:namespace />answerError_new<%=iterator%>" class="aui-helper-hidden portlet-msg-error">
		<liferay-ui:message key="answer-test-required"/>
	</div>
	<liferay-ui:panel id="<%=\"panel_new\"+iterator %>" title="answer" collapsible="true" extended="true" defaultState="open" persistState="false">
		<div class="leftSideAnswer">
			<aui:input  type="hidden" name="answerId" value="<%=\"new\"+iterator %>"></aui:input>
			<aui:input  type="hidden" name="iterator" value="<%=iterator%>"></aui:input>
			<aui:field-wrapper label="">
				<div class="container-textarea">
					<%String name="answer_new"+iterator; %>
					<textarea rows="10" cols="88" name="<%=name%>"></textarea>
				</div>
			</aui:field-wrapper>
			<aui:input name="<%=\"feedbackCorrect_new\"+iterator %>" label="feedbackCorrect" value=""></aui:input>
			<aui:input name="<%=\"feedbackNoCorrect_new\"+iterator %>" label="feedbackNoCorrect" value=""></aui:input>
			<aui:input  type="hidden" name="correct_new<%=iterator%>" label="correct" value="true"/>
		</div>
		<div class="rightSideAnswer">
			<span class="newitem2"><a href="#" class="newitem2" onclick="<portlet:namespace />deleteNode('testAnswer_new<%=iterator %>');"><liferay-ui:message key="delete"/></a></span>
		</div>
	</liferay-ui:panel>
	