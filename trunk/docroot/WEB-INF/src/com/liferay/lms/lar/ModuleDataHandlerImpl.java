package com.liferay.lms.lar;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import com.liferay.lms.learningactivity.questiontype.QuestionType;
import com.liferay.lms.learningactivity.questiontype.QuestionTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.TestAnswer;
import com.liferay.lms.model.TestQuestion;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.TestAnswerLocalServiceUtil;
import com.liferay.lms.service.TestQuestionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;


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

@SuppressWarnings("unchecked")
@Override
protected PortletPreferences doDeleteData(PortletDataContext context,
		String portletId, PortletPreferences preferences) throws Exception {
	
	System.out.println("  ::: ModuleDataHandlerImpl.doDeleteData ::: " + portletId +" "+context.getGroupId()+" "+context.getScopeGroupId());
	
	try {
		String groupIdStr = String.valueOf(context.getScopeGroupId());
		
		Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());
		
		long groupId = 0;
		
		if(Validator.isNumber(groupIdStr)){
			groupId = Long.parseLong(groupIdStr);
		}
		
		System.out.println("   groupId : " + groupId +", name: "+ group.getName());
		
		List<Module> modules = ModuleLocalServiceUtil.findAllInGroup(groupId);

		for(Module module:modules){
			
			System.out.println("    module : " + module.getModuleId() );
			
			List<LearningActivity> activities = LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(module.getModuleId());
			
			for(LearningActivity activity:activities){
				
				System.out.println("     activity : " + activity.getActId() );
				LearningActivityLocalServiceUtil.deleteLearningactivity(activity);
				
			}
			
			ModuleLocalServiceUtil.deleteModule(module);
			
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	System.out.println("  ::: ModuleDataHandlerImpl.doDeleteData ::: ends " );
	
	return super.doDeleteData(context, portletId, preferences);
}

@Override
protected String doExportData(PortletDataContext context, String portletId, PortletPreferences preferences) throws Exception {

	System.out.println(" doExportData portletId : " + portletId );
	
	context.addPermissions("com.liferay.lms.model.module", context.getScopeGroupId());
	
	Document document = SAXReaderUtil.createDocument();

	Element rootElement = document.addElement("moduledata");

	rootElement.addAttribute("group-id", String.valueOf(context.getScopeGroupId()));
	
	List<Module> entries = ModuleLocalServiceUtil.findAllInGroup(context.getScopeGroupId());
	
	System.out.println(" entries : " + entries.size() );
	
	for (Module entry : entries) {
		exportEntry(context, rootElement, entry);
	}

	return document.formattedString();
}

private void exportEntry(PortletDataContext context, Element root, Module entry) throws PortalException, SystemException {
	
	String path = getEntryPath(context, entry);
	
	System.out.println(" path : " + path );
	
	if (!context.isPathNotProcessed(path)) {
		return;
	}

	Element entryElement = root.addElement("moduleentry");

	entryElement.addAttribute("path", path);

	context.addPermissions(Module.class, entry.getModuleId());
	
	entry.setUserUuid(entry.getUserUuid());
	context.addZipEntry(path, entry);
	List<LearningActivity> actividades=LearningActivityLocalServiceUtil.getLearningActivitiesOfModule(entry.getModuleId());
	for(LearningActivity actividad:actividades)
	{
		System.out.println(" actividad : " + actividad.getTitle(Locale.getDefault()) );
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
	
		//Exportar las imagenes de los resources.
		if(actividad.getTypeId() == 2){
			
			String img = LearningActivityLocalServiceUtil.getExtraContentValue(actividad.getActId(), "document");
		
			try {
				AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.valueOf(img));
				DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
				
				String pathqu = getEntryPath(context, docfile);
				String pathFile = getFilePath(context, docfile,actividad.getActId());
				Element entryElementfe= entryElementLoc.addElement("dlfileentry");
				entryElementfe.addAttribute("path", pathqu);
				entryElementfe.addAttribute("file", pathFile+docfile.getTitle());
				context.addZipEntry(pathqu, docfile);
				
				//Guardar el fichero en el zip.
				InputStream input = DLFileEntryLocalServiceUtil.getFileAsStream(docfile.getUserId(), docfile.getFileEntryId(), docfile.getVersion());
				context.addZipEntry(getFilePath(context, docfile,actividad.getActId())+docfile.getTitle(), input);

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
		
		context.addZipEntry(pathlo, actividad);
		List<TestQuestion> questions=TestQuestionLocalServiceUtil.getQuestions(actividad.getActId());
		
		for(TestQuestion question:questions)
		{
			String pathqu = getEntryPath(context, question);
			Element entryElementq= entryElementLoc.addElement("question");
			entryElementq.addAttribute("path", pathqu);
			context.addZipEntry(pathqu, question);
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.exportQuestionAnswers(context, entryElementq, question.getQuestionId());
			
		}
		
	}
	
}

private String getEntryPath(PortletDataContext context, DLFileEntry file) {

	StringBundler sb = new StringBundler(4);
	sb.append(context.getPortletPath("resourceactivity_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/");
	sb.append(file.getFileEntryId());
	sb.append(".xml");
	return sb.toString();
}

private String getFilePath(PortletDataContext context,DLFileEntry file, long actId) {
	
	StringBundler sb = new StringBundler(4);
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/"+String.valueOf(actId)+"/");
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

private String getEntryPath(PortletDataContext context, LearningActivity actividad) {
	
	StringBundler sb = new StringBundler(4);
	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/activities/");
	sb.append(actividad.getActId());
	sb.append(".xml");
	return sb.toString();
}

protected String getEntryPath(PortletDataContext context, Module entry) {

	StringBundler sb = new StringBundler(4);	
	sb.append(context.getPortletPath("moduleportlet_WAR_liferaylmsportlet"));
	sb.append("/moduleentries/");
	sb.append(entry.getModuleId());
	sb.append(".xml");
	return sb.toString();
}

@Override
protected PortletPreferences doImportData(PortletDataContext context, String portletId, PortletPreferences preferences, String data) throws Exception {
	
	context.importPermissions("com.liferay.lms.model.module", context.getSourceGroupId(),context.getScopeGroupId());

	Document document = SAXReaderUtil.read(data);

	System.out.println("import xml : \n" + data );
	
	Element rootElement = document.getRootElement();
	
	for (Element entryElement : rootElement.elements("moduleentry")) {
		String path = entryElement.attributeValue("path");
		
		System.out.println(" entry : " + path );
		
		if (!context.isPathNotProcessed(path)) {
			continue;
		}
		Module entry = (Module)context.getZipEntryAsObject(path);
		
		System.out.println(" Module : " + entry.getModuleId() );
		
		importEntry(context,entryElement, entry);
	}
	
	return null;
}

private void importEntry(PortletDataContext context, Element entryElement, Module entry) throws SystemException, PortalException {
	
	long userId = context.getUserId(entry.getUserUuid());
	entry.setGroupId(context.getScopeGroupId());
	entry.setUserId(userId);
	
	Module theModule=ModuleLocalServiceUtil.addModule(entry.getCompanyId(), entry.getGroupId(), entry.getUserId(), entry.getTitle(), entry.getDescription(),
			entry.getStartDate(), entry.getEndDate(), entry.getOrdern());
	
	for (Element actElement : entryElement.elements("learningactivity")) {
		
		System.out.println("  Element : " + actElement.getPath() );
		
		String path = actElement.attributeValue("path");

		LearningActivity larn=(LearningActivity)context.getZipEntryAsObject(path);
		
		ServiceContext serviceContext=new ServiceContext();
		serviceContext.setAssetCategoryIds(context.getAssetCategoryIds(LearningActivity.class, larn.getActId()));
		serviceContext.setAssetTagNames(context.getAssetTagNames(LearningActivity.class, larn.getActId()));
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(context.getCompanyId());
		serviceContext.setScopeGroupId(context.getScopeGroupId());
		
		larn.setGroupId(context.getScopeGroupId());
		larn.setModuleId(theModule.getModuleId());
		
		LearningActivity nuevaLarn=LearningActivityLocalServiceUtil.addLearningActivity(larn,serviceContext);
		
		//Importar las imagenes de los resources.
		
		if(actElement.element("dlfileentry") != null){
			
			try {
				System.out.println("   dlfileentry path: "+actElement.element("dlfileentry").attributeValue("path"));
				
				//Crear el folder
				DLFolder dlFolder = createDLFoldersForLearningActivity(userId, context.getScopeGroupId(), nuevaLarn.getActId(), nuevaLarn.getTitle(Locale.getDefault()), serviceContext);
				System.out.println("    DLFolder dlFolder: "+dlFolder.getFolderId()+", title: "+dlFolder.getName());
				
				//Recuperar el fichero del xml.
				InputStream is = context.getZipEntryAsInputStream(actElement.element("dlfileentry").attributeValue("file"));
				System.out.println("    InputStream file: "+is.toString());
					
				//Obtener los datos del dlfileentry del .lar para poner sus campos igual. 
				DLFileEntry oldFile = (DLFileEntry) context.getZipEntryAsObject(actElement.element("dlfileentry").attributeValue("path"));
				System.out.println("    DLFileEntry file: "+oldFile.getTitle()+",getFileEntryId "+oldFile.getFileEntryId()+",getFolderId "+oldFile.getFolderId()+",getGroupId "+oldFile.getGroupId());

				//Crear el dlfileentry
				DLFileEntry newFile = DLFileEntryLocalServiceUtil.addFileEntry(serviceContext.getUserId(), larn.getGroupId(), oldFile.getRepositoryId(), dlFolder.getFolderId(), oldFile.getName(), oldFile.getMimeType(), 
						oldFile.getTitle(), oldFile.getDescription(), "Importation", oldFile.getFileEntryId(), oldFile.getFieldsMap(0), FileUtil.createTempFile(is), is, oldFile.getSize(), serviceContext);
				//.addFileEntry(serviceContext.getUserId(),larn.getGroupId(), dlFolder.getFolderId(), oldFile.getName(), oldFile.getTitle(), oldFile.getDescription(), "", "", IOUtils.toByteArray(is), serviceContext);
				
				System.out.println("    DLFileEntry newFile: "+newFile.getTitle()+",getFileEntryId "+newFile.getFileEntryId()+",getFolderId "+newFile.getFolderId()+",getGroupId "+newFile.getGroupId());
				
				//Resources del file.
				DLFileEntryLocalServiceUtil.addFileEntryResources(newFile, true, false);
				
				AssetEntry asset = AssetEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), newFile.getPrimaryKey());
				
				//Ponemos a la actividad el fichero que hemos recuperado.
				LearningActivityLocalServiceUtil.setExtraContentValue(nuevaLarn.getActId(), "document", String.valueOf(asset.getEntryId()));
				System.out.println("    Extracontent : \n"+nuevaLarn.getExtracontent());
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	
		for(Element qElement:actElement.elements("question"))
		{
			String pathq = qElement.attributeValue("path");
				
			TestQuestion question=(TestQuestion)context.getZipEntryAsObject(pathq);
			question.setActId(nuevaLarn.getActId());
			TestQuestion nuevaQuestion=TestQuestionLocalServiceUtil.addQuestion(question.getActId(), question.getText(), question.getQuestionType());
			QuestionType qt =new QuestionTypeRegistry().getQuestionType(question.getQuestionType());
			qt.importQuestionAnswers(context, qElement, nuevaQuestion.getQuestionId());
		}
	}
	
}

	private DLFolder getMainDLFolder(Long userId, Long groupId, ServiceContext serviceContext) throws PortalException, SystemException{

		DLFolder mainFolder = null;

		try {

			mainFolder = DLFolderLocalServiceUtil.getFolder(groupId,0,"ResourceUploads");

        } catch (Exception ex){

        	long repositoryId = DLFolderConstants.getDataRepositoryId(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
        	//mountPoint -> Si es carpeta raíz.
        	mainFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, repositoryId, true, 0, "ResourceUploads", "ResourceUploads", serviceContext);
        			
        	//.addFolder(userId, groupId, 0, "ResourceUploads", "ResourceUploads", serviceContext);
        	
        	
        	//DLFolderLocalServiceUtil.addFolderResources(mainFolder, true, false);
        	
        	//ex.printStackTrace();
        }
  
        return mainFolder;
	}
	
	/**
	 * Primero se busca si ya existe, si existe se devuelve y sino se crea uno nuevo.
	 */
	private DLFolder createDLFoldersForLearningActivity(Long userId, Long groupId, Long actId, String title, ServiceContext serviceContext) throws PortalException, SystemException{
		
		DLFolder newDLFolder = null;
		
		try {

			DLFolder dlMainFolder = getMainDLFolder(userId, groupId, serviceContext);
			
			System.out.println("     dlMainFolder: "+dlMainFolder.getFolderId()+", userId: "+dlMainFolder.getUserId()+", groupId: "+dlMainFolder.getUserId());

			try {
				newDLFolder = DLFolderLocalServiceUtil.getFolder(groupId, dlMainFolder.getFolderId(), String.valueOf(actId));
				return newDLFolder;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//System.out.println("No existe el directorio, crearemos uno. "+e.getMessage());
			}

			if(newDLFolder == null){
				
				newDLFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, dlMainFolder.getRepositoryId(), false, dlMainFolder.getFolderId(), title, title, serviceContext);
				//.addFolder(userId, groupId, dlMainFolder.getFolderId(), String.valueOf(actId), title, serviceContext);
	
				newDLFolder.setGroupId(serviceContext.getScopeGroupId());
				newDLFolder.setName(String.valueOf(actId));
				newDLFolder.setDescription(title);
				newDLFolder.setParentFolderId(dlMainFolder.getFolderId());
				
				newDLFolder.setCompanyId(dlMainFolder.getCompanyId());
				newDLFolder.setUserId(userId);
				newDLFolder.setUserName(dlMainFolder.getUserName());
				
				Date now = new Date();
				newDLFolder.setCreateDate(now);
				newDLFolder.setModifiedDate(now);
				
				DLFolderLocalServiceUtil.updateDLFolder(newDLFolder);
				
				//DLFolderLocalServiceUtil.addFolderResources(newDLFolder, true, false);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
    	return newDLFolder;
	}

}
