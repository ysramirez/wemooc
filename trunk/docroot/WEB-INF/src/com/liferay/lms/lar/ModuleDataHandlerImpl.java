package com.liferay.lms.lar;

import java.util.List;

import javax.portlet.PortletPreferences;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.impl.LearningActivityLocalServiceImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;



public class ModuleDataHandlerImpl extends BasePortletDataHandler {

	public PortletDataHandlerControl[] getExportControls() {
return new PortletDataHandlerControl[] {
		_entries, _categories, _comments, _ratings, _tags
	};
}

@Override
public PortletDataHandlerControl[] getImportControls() {
return new PortletDataHandlerControl[] {
		_entries, _categories, _comments, _ratings, _tags
	};
}
private static final String _NAMESPACE = "module";

private static PortletDataHandlerBoolean _categories =
	new PortletDataHandlerBoolean(_NAMESPACE, "categories");

private static PortletDataHandlerBoolean _comments =
	new PortletDataHandlerBoolean(_NAMESPACE, "comments");

private static PortletDataHandlerBoolean _entries =
	new PortletDataHandlerBoolean(_NAMESPACE, "entries", true, true);

private static PortletDataHandlerBoolean _ratings =
	new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

private static PortletDataHandlerBoolean _tags =
	new PortletDataHandlerBoolean(_NAMESPACE, "tags");

@Override
protected PortletPreferences doDeleteData(PortletDataContext context,
		String portletId, PortletPreferences preferences) throws Exception {
	// TODO Auto-generated method stub
	return super.doDeleteData(context, portletId, preferences);
}

@Override
protected String doExportData(PortletDataContext context, String portletId,
		PortletPreferences preferences) throws Exception {
	// TODO Auto-generated method stub
	context.addPermissions(
			"com.liferay.lms.model.Module", context.getScopeGroupId());
		
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("moduledata");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		List<Module> entries = ModuleLocalServiceUtil.findAllInGroup(
			context.getScopeGroupId());

		for (Module entry : entries) {
			exportEntry(context, rootElement, entry);
		}

		return document.formattedString();
}

private void exportEntry(PortletDataContext context, Element root,
		Module entry) throws PortalException, SystemException {
	String path = getEntryPath(context, entry);

	if (!context.isPathNotProcessed(path)) {
		return;
	}

	Element entryElement = root.addElement("moduleentry");

	entryElement.addAttribute("path", path);

	context.addPermissions(Module.class, entry.getModuleId());
	/*
	if (context.getBooleanParameter(_NAMESPACE, "categories")) {
		context.addAssetCategories(MediatecaEntry.class, entry.getMedId());
	}

	if (context.getBooleanParameter(_NAMESPACE, "comments")) {
		context.addComments(MediatecaEntry.class, entry.getMedId());
	}

	if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
		context.addRatingsEntries(MediatecaEntry.class, entry.getMedId());
	}

	if (context.getBooleanParameter(_NAMESPACE, "tags")) {
		context.addAssetTags(MediatecaEntry.class, entry.getMedId());
	}
*/
	
	entry.setUserUuid(entry.getUserUuid());
	context.addZipEntry(path, entry);
	List<LearningActivity> actividades=LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(entry.getModuleId());
	for(LearningActivity actividad:actividades)
	{
		
		String pathlo = getEntryPath(context, actividad);
		Element entryElementLoc= entryElement.addElement("learningactivity");
		entryElementLoc.addAttribute("path", pathlo);
		context.addPermissions(Module.class, entry.getModuleId());
		
		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			context.addAssetCategories(LearningActivity.class, actividad.getActId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(LearningActivity.class, actividad.getActId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.addRatingsEntries(LearningActivity.class, actividad.getActId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addAssetTags(LearningActivity.class, actividad.getActId());
		}
		
	
		context.addZipEntry(pathlo, actividad);
		List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actividad.getActId());
		for(TestQuestion question:questions)
		{
			String pathqu = getEntryPath(context, question);
			Element entryElementq= entryElementLoc.addElement("question");
			entryElementq.addAttribute("path", pathqu);
			context.addZipEntry(pathqu, question);
			List<TestAnswer> answers=TestAnswerLocalServiceUtil.getTestAnswersByQuestionId(question.getQuestionId());
			for(TestAnswer answer:answers)
			{
				String patha = getEntryPath(context, answer);
				Element entryElementa= entryElementq.addElement("questionanswer");
				entryElementa.addAttribute("path", patha);
				context.addZipEntry(patha, answer);
			
			}
			
			
		}
		
	}
	
}

private String getEntryPath(PortletDataContext context, TestAnswer answer) {
	StringBundler sb = new StringBundler(4);
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/questions/answers");
	sb.append(answer.getAnswerId());
	sb.append(".xml");
	return sb.toString();
}

private String getEntryPath(PortletDataContext context, TestQuestion question) {
StringBundler sb = new StringBundler(4);
	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/questions/");
	sb.append(question.getQuestionId());
	sb.append(".xml");
	return sb.toString();
}

private String getEntryPath(PortletDataContext context,
		LearningActivity actividad) {
	StringBundler sb = new StringBundler(4);
	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/");
	sb.append(actividad.getActId());
	sb.append(".xml");
	return sb.toString();
}

protected String getEntryPath(
		PortletDataContext context, Module entry) {

		StringBundler sb = new StringBundler(4);	
		sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
		sb.append("/moduleentries/");
		sb.append(entry.getModuleId());
		sb.append(".xml");
		return sb.toString();
	}
@Override
protected PortletPreferences doImportData(PortletDataContext context,
		String portletId, PortletPreferences preferences, String data)
		throws Exception {
	context.importPermissions(
			"com.liferay.lms.model.Module", context.getSourceGroupId(),
			context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		for (Element entryElement : rootElement.elements("moduleentry")) {
			String path = entryElement.attributeValue("path");

			if (!context.isPathNotProcessed(path)) {
				continue;
			}
			Module entry = (Module)context.getZipEntryAsObject(path);
			importEntry(context,entryElement, entry);
		}
		return null;
}

private void importEntry(PortletDataContext context, Element entryElement,
		Module entry) throws SystemException, PortalException {
	long userId = context.getUserId(entry.getUserUuid());
	entry.setGroupId(context.getScopeGroupId());
	entry.setUserId(userId);
	Module theModule=ModuleLocalServiceUtil.addmodule(entry);
	for (Element actElement : entryElement.elements("learningactivity")) {
		String path = actElement.attributeValue("path");
		LearningActivity larn=(LearningActivity)context.getZipEntryAsObject(path);
		ServiceContext serviceContext=new ServiceContext();
		serviceContext.setAssetCategoryIds(context.getAssetCategoryIds(LearningActivity.class, larn.getActId()));
		serviceContext.setAssetTagNames(context.getAssetTagNames(LearningActivity.class, larn.getActId()));
		larn.setGroupId(context.getScopeGroupId());
		larn.setModuleId(theModule.getModuleId());
		LearningActivity nueva=LearningActivityLocalServiceUtil.addLearningActivity(larn,serviceContext);
		for(Element qElement:actElement.elements("question"))
		{
			String pathq = qElement.attributeValue("path");
			TestQuestion question=(TestQuestion)context.getZipEntryAsObject(pathq);
			question.setActId(nueva.getActId());
			TestQuestion nuevaQuestion=TestQuestionLocalServiceUtil.addQuestion(question.getActId(), question.getText(), question.getQuestionType());
			for(Element aElement:qElement.elements("questionanswer"))
			{
				String patha = aElement.attributeValue("path");
				TestAnswer answer=(TestAnswer)context.getZipEntryAsObject(patha);
				TestAnswer nuevaAnser=TestAnswerLocalServiceUtil.addTestAnswer(nuevaQuestion.getQuestionId(), answer.getAnswer(), answer.getFeedbackCorrect(), answer.getFeedbacknocorrect(), answer.isIsCorrect());
				
			}
		}
	}
	
}

}
