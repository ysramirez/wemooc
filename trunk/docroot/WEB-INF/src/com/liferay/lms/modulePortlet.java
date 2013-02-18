package com.liferay.lms;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import com.liferay.lms.model.Module;
import com.liferay.lms.model.impl.ModuleImpl;
import com.liferay.lms.service.ModuleLocalServiceUtil;


import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import com.liferay.util.bridges.mvc.MVCPortlet;
import com.tls.util.liferay.patch.MethodKeyPatched;
import com.tls.util.liferay.patch.PortalClassInvokerPatched;

/**
 * Portlet implementation class module
 */
public class modulePortlet extends MVCPortlet {



public static String SEPARATOR = "_";
	public static String IMAGEGALLERY_MAINFOLDER = "icons";
	public static String IMAGEGALLERY_PORTLETFOLDER = "module";
	public static String IMAGEGALLERY_MAINFOLDER_DESCRIPTION = "Module Image Uploads";
	public static String IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION = "";
	
	private long igFolderId;

	public void init() throws PortletException {

		// Edit Mode Pages
		editJSP = getInitParameter("edit-jsp");

		// Help Mode Pages
		helpJSP = getInitParameter("help-jsp");

		// View Mode Pages
		viewJSP = getInitParameter("view-jsp");

		// View Mode Edit module
		editmoduleJSP = getInitParameter("edit-module-jsp");
	}

	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			// do nothing
			// _log.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		String jsp = (String) renderRequest.getParameter("view");
		if (jsp == null || jsp.equals("")) {
			showViewDefault(renderRequest, renderResponse);
		} else if (jsp.equalsIgnoreCase("editmodule")) {
			try {
				showViewEditmodule(renderRequest, renderResponse);
			} catch (Exception ex) {
				_log.debug(ex);
				try {
					showViewDefault(renderRequest, renderResponse);
				} catch (Exception ex1) {
					_log.debug(ex1);
				}
			}
		}
	}
	public void doEdit(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		showEditDefault(renderRequest, renderResponse);
	}

	public void doHelp(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		include(helpJSP, renderRequest, renderResponse);
	}

	@SuppressWarnings("unchecked")
	public void showViewDefault(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getScopeGroupId();

		PermissionChecker permissionChecker = themeDisplay
				.getPermissionChecker();

		boolean hasAddPermission = permissionChecker.hasPermission(groupId,
				"com.liferay.lms.model", groupId, "ADD_MODULE");

		List<Module> tempResults = Collections.EMPTY_LIST;
		try {
				tempResults = ModuleLocalServiceUtil.findAllInGroup(groupId);
			

		} catch (Exception e) {
			_log.debug(e);
		}
		renderRequest.setAttribute("highlightRowWithKey", renderRequest.getParameter("highlightRowWithKey"));
		renderRequest.setAttribute("containerStart", renderRequest.getParameter("containerStart"));
		renderRequest.setAttribute("containerEnd", renderRequest.getParameter("containerEnd"));
		renderRequest.setAttribute("tempResults", tempResults);
		renderRequest.setAttribute("hasAddPermission", hasAddPermission);

		LiferayPortletResponse liferayPortletResponse=(LiferayPortletResponse)renderResponse;
		PortletURL addmoduleURL = liferayPortletResponse.createActionURL(
		 "moduleEditing_WAR_liferaylmsportlet");
		addmoduleURL.setWindowState(LiferayWindowState.POP_UP);
		addmoduleURL.setParameter("javax.portlet.action", "newmodule");
		renderRequest.setAttribute("addmoduleURL", addmoduleURL.toString());
		
		PortletURL moduleFilterURL = renderResponse.createRenderURL();
		moduleFilterURL.setParameter("javax.portlet.action", "doView");
		renderRequest.setAttribute("moduleFilterURL", moduleFilterURL.toString());

		include(viewJSP, renderRequest, renderResponse);
	}

	public void showViewEditmodule(RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {

		SimpleDateFormat formatDia    = new SimpleDateFormat("dd");
		SimpleDateFormat formatMes    = new SimpleDateFormat("MM");
		SimpleDateFormat formatAno    = new SimpleDateFormat("yyyy");
		SimpleDateFormat formatHora   = new SimpleDateFormat("HH");
		SimpleDateFormat formatMinuto = new SimpleDateFormat("mm");
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
		.getAttribute(WebKeys.THEME_DISPLAY);

		LiferayPortletResponse liferayPortletResponse=(LiferayPortletResponse)renderResponse;
		PortletURL editmoduleURL = liferayPortletResponse.createActionURL(
		 "moduleEditing_WAR_liferaylmsportlet");

		String editType = (String) renderRequest.getParameter("editType");
		if (editType.equalsIgnoreCase("edit")) {
			editmoduleURL.setParameter("javax.portlet.action", "updatemodule");
			long moduleId = Long.parseLong(renderRequest.getParameter("moduleId"));
			Module module = ModuleLocalServiceUtil.getModule(moduleId);
			String title = module.getTitle();
			renderRequest.setAttribute("title", title);
			String description = module.getDescription(themeDisplay.getLocale())+"";
			renderRequest.setAttribute("description", description);
			String order = module.getOrdern()+"";
			renderRequest.setAttribute("ordern", order);
			String icon = module.getIcon()+"";
			renderRequest.setAttribute("icon", icon);
			if(module.getStartDate()==null)
			{
				module.setStartDate(new java.util.Date(System.currentTimeMillis()));
			}
			if(module.getEndDate()==null)
			{
				module.setEndDate(new java.util.Date(System.currentTimeMillis()+1000*84000*365));
			}
			renderRequest.setAttribute("startDateDia", formatDia.format(module.getStartDate()));
			renderRequest.setAttribute("startDateMes", formatMes.format(module.getStartDate()));
			renderRequest.setAttribute("startDateAno", formatAno.format(module.getStartDate()));
			String startDate = dateToJsp(renderRequest, module.getStartDate());
			renderRequest.setAttribute("startDate", startDate);
			renderRequest.setAttribute("endDateDia", formatDia.format(module.getEndDate()));
			renderRequest.setAttribute("endDateMes", formatMes.format(module.getEndDate()));
			renderRequest.setAttribute("endDateAno", formatAno.format(module.getEndDate()));
			String endDate = dateToJsp(renderRequest, module.getEndDate());
			renderRequest.setAttribute("endDate", endDate);
            renderRequest.setAttribute("module", module);
		} else {
			editmoduleURL.setParameter("javax.portlet.action", "addmodule");
			Module errormodule = (Module) renderRequest.getAttribute("errormodule");
			if (errormodule != null) {
				if (editType.equalsIgnoreCase("update")) {
					editmoduleURL.setParameter("javax.portlet.action", "updatemodule");
                }
				renderRequest.setAttribute("module", errormodule);
				 renderRequest.setAttribute("startDateDia", formatDia.format(errormodule.getStartDate()));
	                renderRequest.setAttribute("startDateMes", formatMes.format(errormodule.getStartDate()));
	                renderRequest.setAttribute("startDateAno", formatAno.format(errormodule.getStartDate()));
					String startDate = dateToJsp(renderRequest,errormodule.getStartDate());
					renderRequest.setAttribute("startDate", startDate);
	                renderRequest.setAttribute("endDateDia", formatDia.format(errormodule.getEndDate()));
	                renderRequest.setAttribute("endDateMes", formatMes.format(errormodule.getEndDate()));
	                renderRequest.setAttribute("endDateAno", formatAno.format(errormodule.getEndDate()));
					String endDate = dateToJsp(renderRequest,errormodule.getEndDate());
					renderRequest.setAttribute("endDate", endDate);
			} else {
				ModuleImpl blankmodule = new ModuleImpl();
				blankmodule.setModuleId(0);
				blankmodule.setTitle("");
				blankmodule.setDescription("");
				blankmodule.setOrdern(0);
				blankmodule.setIcon(0);
				blankmodule.setStartDate(new Date());
				renderRequest.setAttribute("startDateDia", formatDia.format(blankmodule.getStartDate()));
				renderRequest.setAttribute("startDateMes", formatMes.format(blankmodule.getStartDate()));
				renderRequest.setAttribute("startDateAno", formatAno.format(blankmodule.getStartDate()));
				String startDate = dateToJsp(renderRequest, blankmodule.getStartDate());
				renderRequest.setAttribute("startDate", startDate);
				blankmodule.setEndDate(new Date());
				renderRequest.setAttribute("endDateDia", formatDia.format(blankmodule.getEndDate()));
				renderRequest.setAttribute("endDateMes", formatMes.format(blankmodule.getEndDate()));
				renderRequest.setAttribute("endDateAno", formatAno.format(blankmodule.getEndDate()));
				String endDate = dateToJsp(renderRequest, blankmodule.getEndDate());
				renderRequest.setAttribute("endDate", endDate);
				renderRequest.setAttribute("module", blankmodule);
			}

		}

	
		renderRequest.setAttribute("editmoduleURL", editmoduleURL.toString());
		if(ParamUtil.getLong(renderRequest, "actId",0)==0)
		{
			
			include(editmoduleJSP, renderRequest, renderResponse);
		}
		else
		{
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
	}

	private String dateToJsp(ActionRequest request, Date date) {
		PortletPreferences prefs = request.getPreferences();
		return dateToJsp(prefs, date);
	}
	private String dateToJsp(RenderRequest request, Date date) {
		PortletPreferences prefs = request.getPreferences();
		return dateToJsp(prefs, date);
	}
	private String dateToJsp(PortletPreferences prefs, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(prefs.getValue("module-date-format", "yyyy/MM/dd"));
		String stringDate = format.format(date);
		return stringDate;
	}
	private String dateTimeToJsp(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String stringDate = format.format(date);
		return stringDate;
	}

	public void showEditDefault(RenderRequest renderRequest,
			RenderResponse renderResponse) throws PortletException, IOException {

		include(editJSP, renderRequest, renderResponse);
	}

	/* Portlet Actions */

	@ProcessAction(name = "newmodule")
	public void newmodule(ActionRequest request, ActionResponse response) {
		response.setRenderParameter("view", "editmodule");
		response.setRenderParameter("moduleId", "0");
		response.setRenderParameter("editType", "add");
	}
	

	@ProcessAction(name = "addmodule")
	public void addmodule(ActionRequest request, ActionResponse response) throws Exception {
            Module module = moduleFromRequest(request);
            ArrayList<String> errors = moduleValidator.validatemodule(module, request);
            ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);

            if (errors.isEmpty()) {
				try {
					ModuleLocalServiceUtil.addmodule(module);
                	
                	response.setRenderParameter("view", "");
                	SessionMessages.add(request, "module-added-successfully");
            	} catch (Exception cvex) {
            		SessionErrors.add(request, "please-enter-a-unique-code");
                    response.setRenderParameter("view", "editmodule");
                    response.setRenderParameter("editType", "add");
                    response.setRenderParameter("moduleId", module.getModuleId()+"");
                    response.setRenderParameter("title", module.getTitle()+"");
                    response.setRenderParameter("description", module.getDescription(themeDisplay.getLocale())+"");
                    response.setRenderParameter("icon", module.getIcon()+"");
                    response.setRenderParameter("startDate", module.getStartDate()+"");
                    response.setRenderParameter("endDate", module.getEndDate()+"");
            	}
            } else {
                for (String error : errors) {
                        SessionErrors.add(request, error);
                }
                response.setRenderParameter("view", "editmodule");
                response.setRenderParameter("editType", "add");
                    response.setRenderParameter("moduleId", module.getModuleId()+"");
                    response.setRenderParameter("title", module.getTitle()+"");
                    response.setRenderParameter("description", module.getDescription(themeDisplay.getLocale())+"");
                    response.setRenderParameter("ordern", module.getOrdern()+"");
                    response.setRenderParameter("icon", module.getIcon()+"");
                    response.setRenderParameter("startDate", module.getStartDate()+"");
                    response.setRenderParameter("endDate", module.getEndDate()+"");
            }
	}

	@ProcessAction(name = "eventmodule")
	public void eventmodule(ActionRequest request, ActionResponse response)
			throws Exception {
		long key = ParamUtil.getLong(request, "resourcePrimKey");
		int containerStart = ParamUtil.getInteger(request, "containerStart");
		int containerEnd = ParamUtil.getInteger(request, "containerEnd");
		if (Validator.isNotNull(key)) {
            response.setRenderParameter("highlightRowWithKey", Long.toString(key));
            response.setRenderParameter("containerStart", Integer.toString(containerStart));
            response.setRenderParameter("containerEnd", Integer.toString(containerEnd));
		}
	}
	public void upmodule(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	String portletId = PortalUtil.getPortletId(actionRequest);
	long moduleId = ParamUtil.getLong(actionRequest, "moduleId",0);
	
	if(moduleId>0)
	{
		com.liferay.lms.service.ModuleLocalServiceUtil.goUpModule(moduleId);
	}
	
}
	public void downmodule(ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {

	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	String portletId = PortalUtil.getPortletId(actionRequest);
	long moduleId = ParamUtil.getLong(actionRequest, "moduleId",0);
	
	if(moduleId>0)
	{
		com.liferay.lms.service.ModuleLocalServiceUtil.goDownModule(moduleId);
	}
	
}
	@ProcessAction(name = "editmodule")
	public void editmodule(ActionRequest request, ActionResponse response)
			throws Exception {
		long key = ParamUtil.getLong(request, "resourcePrimKey");
		if (Validator.isNotNull(key)) {
			response.setRenderParameter("moduleId", Long.toString(key));
			response.setRenderParameter("view", "editmodule");
			response.setRenderParameter("editType", "edit");
		}
	}

	@ProcessAction(name = "deletemodule")
	public void deletemodule(ActionRequest request, ActionResponse response)throws Exception {
		long id = ParamUtil.getLong(request, "resourcePrimKey");
		if (Validator.isNotNull(id)) {
			Module module = ModuleLocalServiceUtil.getModule(id);
			ModuleLocalServiceUtil.deleteModule(module);
            MultiVMPoolUtil.clear();
			SessionMessages.add(request, "module-deleted-successfully");
		} else {
			SessionErrors.add(request, "module-error-deleting");
		}
	}

	@ProcessAction(name = "updatemodule")
	public void updatemodule(ActionRequest request, ActionResponse response) throws Exception {
            Module module = moduleFromRequest(request);
            ArrayList<String> errors = moduleValidator.validatemodule(module, request);
            ThemeDisplay themeDisplay = (ThemeDisplay) request
			.getAttribute(WebKeys.THEME_DISPLAY);
            
            if (errors.isEmpty()) {
            	try {
                	ModuleLocalServiceUtil.updateModule(module);
                	MultiVMPoolUtil.clear();
                	response.setRenderParameter("view", "");
                	SessionMessages.add(request, "module-updated-successfully");
            	} catch (Exception cvex) {
            		SessionErrors.add(request, "please-enter-a-unique-code");
                    response.setRenderParameter("view", "editmodule");
                    response.setRenderParameter("editType", "edit");
                    response.setRenderParameter("moduleId", module.getModuleId()+"");
                    response.setRenderParameter("title", module.getTitle()+"");
                    response.setRenderParameter("description", module.getDescription(themeDisplay.getLocale())+"");
                    response.setRenderParameter("ordern", module.getOrdern()+"");
                    response.setRenderParameter("icon", module.getIcon()+"");
                    response.setRenderParameter("startDate", module.getStartDate()+"");
                    response.setRenderParameter("endDate", module.getEndDate()+"");
            	}
            } else {
                for (String error : errors) {
                        SessionErrors.add(request, error);
                }
				response.setRenderParameter("moduleId)",Long.toString(module.getPrimaryKey()));
				response.setRenderParameter("view", "editmodule");
				response.setRenderParameter("editType", "edit");
                    response.setRenderParameter("moduleId", module.getModuleId()+"");
                    response.setRenderParameter("title", module.getTitle()+"");
                    response.setRenderParameter("description", module.getDescription(themeDisplay.getLocale())+"");
                    response.setRenderParameter("ordern", module.getOrdern()+"");
                    response.setRenderParameter("icon", module.getIcon()+"");
                    response.setRenderParameter("startDate", module.getStartDate()+"");
                    response.setRenderParameter("endDate", module.getEndDate()+"");
            }
        }

	@ProcessAction(name = "setmodulePref")
	public void setmodulePref(ActionRequest request, ActionResponse response) throws Exception {

		String rowsPerPage = ParamUtil.getString(request, "module-rows-per-page");
		String dateFormat = ParamUtil.getString(request, "module-date-format");
		String datetimeFormat = ParamUtil.getString(request, "module-datetime-format");

		ArrayList<String> errors = new ArrayList();
		if (moduleValidator.validateEditmodule(rowsPerPage, dateFormat, datetimeFormat, errors)) {
			response.setRenderParameter("module-rows-per-page", "");
			response.setRenderParameter("module-date-format", "");
			response.setRenderParameter("module-datetime-format", "");

			PortletPreferences prefs = request.getPreferences();
			prefs.setValue("module-rows-per-page", rowsPerPage);
			prefs.setValue("module-date-format", dateFormat);
			prefs.setValue("module-datetime-format", datetimeFormat);
			prefs.store();

			SessionMessages.add(request, "module-prefs-success");
		}
	}

	private Module moduleFromRequest(ActionRequest actRequest) throws PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actRequest.getAttribute(WebKeys.THEME_DISPLAY);
		UploadPortletRequest request = PortalUtil.getUploadPortletRequest(actRequest);
		ServiceContext serviceContext = null;
		ServiceContext serviceContext2=null;
		try {
			serviceContext =  ServiceContextFactory.getInstance(Module.class.getName(), actRequest);

			serviceContext2 = ServiceContextFactory.getInstance(actRequest);
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		Module module = null;
        long moduleId=ParamUtil.getLong(request, "resourcePrimKey",0);
        
        if(moduleId>0)
        {
        	 module=ModuleLocalServiceUtil.getModule(ParamUtil.getLong(request, "resourcePrimKey",0));
        }
        else
        {
        	module=new ModuleImpl();
        }
        
		Enumeration<String> parNames= request.getParameterNames();
		while(parNames.hasMoreElements())
		{
		  String paramName=parNames.nextElement();
		  if(paramName.startsWith("title_")&&paramName.length()>6)
		  {
			  String language=paramName.substring(6);
			  Locale locale=LocaleUtil.fromLanguageId(language);
			  module.setTitle(request.getParameter(paramName),locale);
		  }
		}
		module.setDescription(ParamUtil.getString(request, "description"),themeDisplay.getLocale());
      
        try {
            module.setIcon(ParamUtil.getLong(request, "icon"));
        } catch (Exception nfe) {
		    //Controled en Validator
        }
	    PortletPreferences prefs = actRequest.getPreferences();
        SimpleDateFormat format = new SimpleDateFormat(prefs.getValue("module-date-format", "yyyy/MM/dd"));
        int startDateAno = ParamUtil.getInteger(request, "startDateAno");
        int startDateMes = ParamUtil.getInteger(request, "startDateMes")+1;
        int startDateDia = ParamUtil.getInteger(request, "startDateDia");
        long precedence=ParamUtil.getLong(request, "precedence");
        try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            module.setStartDate(formatter.parse(startDateAno + "/" + startDateMes + "/" + startDateDia));
        } catch (ParseException e) {
			module.setStartDate(new Date());
        }
        int endDateAno = ParamUtil.getInteger(request, "endDateAno");
        int endDateMes = ParamUtil.getInteger(request, "endDateMes")+1;
        int endDateDia = ParamUtil.getInteger(request, "endDateDia");
        try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            module.setEndDate(formatter.parse(endDateAno + "/" + endDateMes + "/" + endDateDia));
        } catch (ParseException e) {
			module.setEndDate(new Date());
        }
		try {
		    module.setPrimaryKey(ParamUtil.getLong(request,"resourcePrimKey"));
		} catch (NumberFormatException nfe) {
			//Controled en Validator
        }
		module.setCompanyId(themeDisplay.getCompanyId());
		module.setGroupId(themeDisplay.getScopeGroupId());
		module.setUserId(themeDisplay.getUserId());
		module.setPrecedence(precedence);
		//module.setExpandoBridgeAttributes(serviceContext);	
		//Saving image
		String fileName = request.getFileName("fileName");
		String title = fileName;// + " uploaded by " + user.getFullName();
		File file = request.getFile("fileName");
		/*TODO no funciona
		if(fileName!=null && !fileName.equals("")){
			createIGFolders(actRequest, themeDisplay.getUserId(),themeDisplay.getScopeGroupId(), serviceContext);
			String imFormat="jpg";
			if(fileName.toLowerCase().endsWith(".png"))
			{
				imFormat="png";
			}
			if(fileName.toLowerCase().endsWith(".gif"))
			{
				imFormat="gif";
			}
			String contentType = MimeTypesUtil.getContentType(fileName);
			
			FileEntry image=DLAppServiceUtil.addFileEntry(module.getGroupId(), igFolderId, fileName, contentType, title, title, "", file, serviceContext2);
					
			module.setIcon(image.getFileEntryId());			
			
		}
			*/
		return module;
	}

	public static Object invoke(
	            boolean newInstance, String className, String methodName,
	            String[] parameterTypeNames, Object... arguments)
	    throws Exception {
	
	    Thread currentThread = Thread.currentThread();
	
	    ClassLoader contextClassLoader = currentThread.getContextClassLoader();
	
	    try {
	            currentThread.setContextClassLoader(
	                    PortalClassLoaderUtil.getClassLoader());
	
	            MethodKey methodKey = new MethodKeyPatched(
	                    className, methodName, parameterTypeNames,
	                    PortalClassLoaderUtil.getClassLoader());
	
	            MethodHandler methodHandler = new MethodHandler(
	                    methodKey, arguments);
	
	            return methodHandler.invoke(newInstance);
	    }
	    catch (InvocationTargetException ite) {
	            throw (Exception)ite.getCause();
	    }
	    finally {
	            currentThread.setContextClassLoader(contextClassLoader);
	    }
	}

	public void invokeTaglibDiscussion(ActionRequest actionRequest, ActionResponse actionResponse)
	    throws Exception 
	{
	
	// Ver notas en el invokeTaglibDiscussion llamado
		 PortalClassInvokerPatched.invoke(  // Notar el "Patched"
	             true,
	             "com.liferay.portlet.messageboards.action.EditDiscussionAction",
	             "processAction",
	             new String[] {
	                     "org.apache.struts.action.ActionMapping",
	                     "org.apache.struts.action.ActionForm",
	                     PortletConfig.class.getName(), ActionRequest.class.getName(),
	                     ActionResponse.class.getName()
	             },
	             null, null, this.getPortletConfig(), actionRequest, actionResponse);	}

	/**
	 * Create folders for upload images from our portlet to ImageGallery portlet
	 * @param request
	 * @param userId
	 * @param groupId
	 * @param serviceContext
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void createIGFolders(ActionRequest request,long userId,long groupId, ServiceContext serviceContext) throws PortalException, SystemException{
		//Variables for folder ids
		Long igMainFolderId = 0L;
		Long igPortletFolderId = 0L;
		Long igRecordFolderId = 0L;
	    //Search for folders
	    boolean igMainFolderFound = false;
	    boolean igPortletFolderFound = false;
	    try {
	    	//Get the main folder
	    	DLFolder igMainFolder = DLFolderLocalServiceUtil.getFolder(groupId,0,IMAGEGALLERY_MAINFOLDER);
	    	igMainFolderId = igMainFolder.getFolderId();
	    	igMainFolderFound = true;
	    	//Get the portlet folder
	    	DLFolder igPortletFolder = DLFolderLocalServiceUtil.getFolder(groupId,igMainFolderId,IMAGEGALLERY_PORTLETFOLDER);
	    	igPortletFolderId = igPortletFolder.getFolderId();
	    	igPortletFolderFound = true;
	    } catch (Exception ex) {
	    	ex.printStackTrace(); //Not found main folder
	    }
	    //Create main folder if not exist
	    if(!igMainFolderFound) {
	    	Folder newImageMainFolder=DLAppLocalServiceUtil.addFolder(userId, groupId, 0, IMAGEGALLERY_MAINFOLDER, IMAGEGALLERY_MAINFOLDER_DESCRIPTION, serviceContext);
	    	igMainFolderId = newImageMainFolder.getFolderId();
	    	igMainFolderFound = true;
	    }
	    //Create portlet folder if not exist
	    if(igMainFolderFound && !igPortletFolderFound){
	    	Folder newImagePortletFolder = DLAppLocalServiceUtil.addFolder(userId, groupId, igMainFolderId, IMAGEGALLERY_PORTLETFOLDER, IMAGEGALLERY_PORTLETFOLDER_DESCRIPTION, serviceContext);	    	
	    	igPortletFolderFound = true;
	    	igPortletFolderId = newImagePortletFolder.getFolderId();
	    }
	    //Create this record folder
	    if(igPortletFolderFound){
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	    	Date date = new Date();
	    	String igRecordFolderName=dateFormat.format(date)+SEPARATOR+userId;
	    	Folder newImageRecordFolder = DLAppLocalServiceUtil.addFolder(userId,groupId, igPortletFolderId,igRecordFolderName, "", serviceContext);
	    	igRecordFolderId = newImageRecordFolder.getFolderId();
	    }
	    igFolderId = igRecordFolderId;
	  }

	protected String editmoduleJSP;
	protected String editJSP;
	protected String helpJSP;
	protected String viewJSP;

	private static Log _log = LogFactoryUtil.getLog(modulePortlet.class);

}
