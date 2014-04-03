package com.coursetools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import au.com.bytecode.opencsv.CSVReader;

import com.liferay.lms.learningactivity.calificationtype.CalificationType;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.CourseServiceUtil;
import com.liferay.lms.service.LmsPrefsLocalServiceUtil;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.BaseServiceImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ImportCourses
 */
public class ImportCourses extends MVCPortlet {
	
	public static final String[] ANONYMOUS_NAMES = {
		BaseServiceImpl.JRUN_ANONYMOUS, BaseServiceImpl.ORACLE_ANONYMOUS,
		BaseServiceImpl.SUN_ANONYMOUS, BaseServiceImpl.WEBLOGIC_ANONYMOUS
	};

	public static final String JRUN_ANONYMOUS = "anonymous-guest";
	public static final String ORACLE_ANONYMOUS = "guest";
	public static final String SUN_ANONYMOUS = "ANONYMOUS";
	public static final String WEBLOGIC_ANONYMOUS = "<anonymous>";
	
	public void importCourses(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException, IOException {
		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String fileName = uploadRequest.getFileName("fileName");
		
		InputStream csvFile = uploadRequest.getFileAsStream("fileName");
		
		if(fileName==null || StringPool.BLANK.equals(fileName)){
			SessionErrors.add(actionRequest, "surveyactivity.csverror.fileRequired");
			actionResponse.setRenderParameter("jspPage", "/html/surveyactivity/admin/importquestions.jsp");
		}
		else{ 
			String contentType = uploadRequest.getContentType("fileName");	
			if (!ContentTypes.TEXT_CSV.equals(contentType) && !ContentTypes.TEXT_CSV_UTF8.equals(contentType) ) {
				SessionErrors.add(actionRequest, "coursetools.importcourses.bad-format");	
			}
			else
			{
				CSVReader reader = null;
				try {
					
					System.out.println("----- Import courses -------");
					
					boolean allCorrect=true;
					reader = new CSVReader(new InputStreamReader(csvFile, "ISO-8859-1"), ';');
					int line = 0;
					String[] currLine; 
					while ((currLine = reader.readNext()) != null) {
						line++;
						
						/**
						 * 0 - nombre del curso
						 * 1 - descripcion del curso
						 * 2 - categoria
						 * 3 - nivel
						 * 4 - profesor
						 * 5 - alumnos, separados por comas.
						 */
						if (currLine.length > 0) {
							
							for(int i=0;i < currLine.length;i++){
								System.out.print(currLine[i] +" - ");
							}
							System.out.println("");
							
							String courseStr = 		currLine.length > 0 ? currLine[0] : "";
							String descriptionStr = currLine.length > 1 ? currLine[1] : "";
							String categoryStr = 	currLine.length > 2 ? currLine[2] : "";
							String levelStr = 		currLine.length > 3 ? currLine[3] : "";
							String teachers = 		currLine.length > 4 ? currLine[4] : "";
							String students = 		currLine.length > 5 ? currLine[5] : "";
							
							try {
								Course course = createCourse(courseStr, levelStr, categoryStr);
								
								course.setDescription("<?xml version='1.0' encoding='UTF-8'?><root available-locales=\"es_ES\" default-locale=\"es_ES\"><Description language-id=\"es_ES\">&lt;p>&#xd;"+descriptionStr+"&lt;/p>&#xd;</Description></root>");
								course.setCourseEvalId(1);
								
								CourseLocalServiceUtil.updateCourse(course);
								
								System.out.println(" Course: " + courseStr + ", descriptionStr: " + descriptionStr + ", categoryStr: " + categoryStr + ", levelStr: " + levelStr + ", teacher: " + teachers + ", students: " + students);	
								
								
								//Añadir los profesores
								String [] teachersArray = teachers.split(",");
								
								if(teachersArray.length > 0){
									for(int i=0; i < teachersArray.length; i++){
										
										try {
											
											addTeacherToCourse(course.getCourseId(),teachersArray[i].trim());
																						
										} catch (Exception e) {
											// TODO Auto-generated catch block
											//e.printStackTrace();
											System.out.println("   Error teacher: " + teachersArray[i] );
										}
										
									}
								}
								
								//Añadir los usuarios que tengamos en el último campo separados por comas.
								String [] studentsArray = students.split(",");
								
								if(studentsArray.length > 0){
									for(int i=0; i < studentsArray.length; i++){
										
										try {
											
											User user = UserLocalServiceUtil.fetchUser(Long.valueOf(studentsArray[i].trim()));
											
											if(user != null){
												CourseServiceUtil.addStudentToCourse(course.getCourseId(), user.getLogin());
											}
											
											System.out.println("  Add user: " + user.getFullName() );
											
										} catch (Exception e) {
											// TODO Auto-generated catch block
											//e.printStackTrace();
											System.out.println("   Error student: " + studentsArray[i] );
										}
										
									}
								}
								
							} catch (DuplicateGroupException dge) {
								System.out.println("  Ya existe el nombre del curso.");							
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						} else {
								SessionErrors.add(actionRequest, "coursetools.importcourses.bad-format-line",line);
								actionResponse.setRenderParameter("jspPage", "/html/importcourses/view.jsp");
								allCorrect=false;
						}
					}//while
					if(allCorrect){
						actionResponse.setRenderParameter("jspPage", "/html/importcourses/view.jsp");
						SessionMessages.add(actionRequest, "courses-added-successfully");
					}
	
				} catch (FileNotFoundException e) {
					SessionErrors.add(actionRequest, "coursetools.importcourses.empty-file");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						reader.close();
					}
					if (!SessionErrors.isEmpty(actionRequest)){
						actionResponse.setRenderParameter("jspPage", "/html/importcourses/view.jsp");
					}
				}
				}
		}
		
	}
 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Course createCourse(String nombre,String nivel,String categoria) throws PortalException, SystemException{
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		Course course = null;
		
		if (CourseLocalServiceUtil.existsCourseName(serviceContext.getCompanyId(), null, nombre)) {
			throw new DuplicateGroupException(LanguageUtil.get(LocaleUtil.getDefault(), "title-repeated"));
		}
		try {
			
			
			String[] lspist=LmsPrefsLocalServiceUtil.getLmsPrefsIni(serviceContext.getCompanyId()).getLmsTemplates().split(",");
			Date now = new Date();
			long guestId = GroupLocalServiceUtil.getGroup(serviceContext.getCompanyId(), GroupConstants.GUEST).getGroupId();
			List<Long> assetCategoryIds = new ArrayList<Long>();
			
			try {
				AssetVocabulary rangoDeEdadVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(guestId, "Nivel");

				assetCategoryIds.addAll(
					AssetCategoryLocalServiceUtil.dynamicQuery(		
					DynamicQueryFactoryUtil.forClass(AssetCategory.class,PortalClassLoaderUtil.getClassLoader()).
						add(PropertyFactoryUtil.forName("name").eq(nivel)).
						add(PropertyFactoryUtil.forName("vocabularyId").eq(rangoDeEdadVocabulary.getVocabularyId())).
						setProjection(ProjectionFactoryUtil.property("categoryId"))));
			} catch (PortalException e) {
			}
						
			User user = getUser();
			serviceContext.setUserId(user.getUserId());
			serviceContext.setScopeGroupId(user.getGroupId());
			
			serviceContext.setLanguageId(LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
			
			try {
				AssetVocabulary asignaturaVocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(guestId, "Asignatura");
				System.out.println("");
				List asignaturasIds =  AssetCategoryLocalServiceUtil.dynamicQuery(		
					DynamicQueryFactoryUtil.forClass(AssetCategory.class,PortalClassLoaderUtil.getClassLoader()).
						add(PropertyFactoryUtil.forName("name").like(categoria.toLowerCase())).
						add(PropertyFactoryUtil.forName("vocabularyId").eq(asignaturaVocabulary.getVocabularyId())).
						setProjection(ProjectionFactoryUtil.property("categoryId")));
				if (asignaturasIds.isEmpty()) {
					Map<Locale, String> titleMap = new HashMap<Locale, String>();
					titleMap.put(LocaleUtil.getDefault(), categoria);
					String[] categoryProperties = {"icono:generico.png"};
					AssetCategory newCategory = AssetCategoryLocalServiceUtil.addCategory(user.getUserId(), 0, titleMap, new HashMap<Locale, String>(), asignaturaVocabulary.getVocabularyId(), categoryProperties, serviceContext);
					assetCategoryIds.add(newCategory.getCategoryId());
				} else {
					assetCategoryIds.addAll(asignaturasIds);
				}
			} catch (PortalException e) {
				
			}

			serviceContext.setAssetCategoryIds(ArrayUtil.toArray(assetCategoryIds.toArray(new Long[assetCategoryIds.size()])));
			
			
			course = CourseLocalServiceUtil.addCourse(
					nombre, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					LocaleUtil.getDefault(), now, now, now,Long.parseLong(lspist[0]),GroupConstants.TYPE_SITE_PRIVATE,
					serviceContext,0,0);
			course.setCourseEvalId(1); // EvaluationAvgCourseEval.getTypeId();
			com.liferay.lms.service.CourseLocalServiceUtil.modCourse(course,serviceContext);
			
			course.setGroupId(guestId);
			CourseLocalServiceUtil.updateCourse(course);
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), course.getCourseId());
			assetEntry.setGroupId(guestId);
			AssetEntryLocalServiceUtil.updateAssetEntry(assetEntry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return course;
	}
	
	public User getUser() throws PortalException, SystemException {
		return UserLocalServiceUtil.getUserById(getUserId());
	}

	public long getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (name == null) {
			throw new PrincipalException();
		}

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal cannot be null");
		}
		else {
			for (int i = 0; i < ANONYMOUS_NAMES.length; i++) {
				if (name.equalsIgnoreCase(ANONYMOUS_NAMES[i])) {
					throw new PrincipalException(
						"Principal cannot be " + ANONYMOUS_NAMES[i]);
				}
			}
		}

		return GetterUtil.getLong(name);
	}
	
	public void addTeacherToCourse(long courseId,String login) {
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		User user = null;
		try {
			user = UserLocalServiceUtil.getUserById(Long.valueOf(login));// .getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
			}
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), LmsPrefsLocalServiceUtil.getLmsPrefs(serviceContext.getCompanyId()).getEditorRole());
			
			System.out.println("  Add teacher: " + user.getFullName() );
			
		} catch (NestableException e) {
			System.out.println("   Error teacher: " + login);
		} catch (Exception e){
			System.out.println("   Error teacher: " + login );
		}
		
	}

}
