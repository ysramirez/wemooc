package com.coursetools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import au.com.bytecode.opencsv.CSVReader;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.kernel.exception.NestableException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ImportStudentToCourse
 */
public class ImportStudentToCourse extends MVCPortlet {
 
public void importStudentToCourse(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException, IOException {
		
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String fileName = uploadRequest.getFileName("fileName");
		
		InputStream csvFile = uploadRequest.getFileAsStream("fileName");
		
		if(fileName==null || StringPool.BLANK.equals(fileName)){
			SessionErrors.add(actionRequest, "surveyactivity.csverror.fileRequired");
			actionResponse.setRenderParameter("jspPage", "/html/importstudenttocourse/view.jsp");
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
					boolean allCorrect=true;
					reader = new CSVReader(new InputStreamReader(csvFile, "ISO-8859-1"), ';');
					int line = 0;
					String[] currLine; 
					while ((currLine = reader.readNext()) != null) {
						line++;
						
						/**
						 * 0 - id del curso
						 * 1..n - ids de alumnos
						 */
						if (currLine.length > 1) {

							for(int i=1;i<currLine.length;i++){
								System.out.print(currLine[i] +" - ");
								
								try {
									addStudentToCourse(Long.valueOf(currLine[0]), currLine[i]);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
							System.out.println("");
							
						} else {
								SessionErrors.add(actionRequest, "coursetools.importcourses.bad-format-line",line);
								actionResponse.setRenderParameter("jspPage", "/html/importstudenttocourse/view.jsp");
								allCorrect=false;
						}
					}//while
					if(allCorrect){
						actionResponse.setRenderParameter("jspPage", "/html/importstudenttocourse/view.jsp");
						SessionMessages.add(actionRequest, "courses-added-successfully");
					}
	
				} catch (FileNotFoundException e) {
					SessionErrors.add(actionRequest, "coursetools.importstudenttocourse.empty-file");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						reader.close();
					}
					if (!SessionErrors.isEmpty(actionRequest)){
						actionResponse.setRenderParameter("jspPage", "/html/importstudenttocourse/view.jsp");
					}
				}
				}
		}
		
	}

	public void addStudentToCourse(long courseId,String login) {
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		
		try {
			User user = UserLocalServiceUtil.getUserByScreenName(serviceContext.getCompanyId(), login);
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			if (!GroupLocalServiceUtil.hasUserGroup(user.getUserId(), course.getGroupCreatedId())) {
				GroupLocalServiceUtil.addUserGroups(user.getUserId(), new long[] { course.getGroupCreatedId() });
			}
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(new long[] { user.getUserId() },
					course.getGroupCreatedId(), RoleLocalServiceUtil.getRole(serviceContext.getCompanyId(), RoleConstants.SITE_MEMBER).getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
