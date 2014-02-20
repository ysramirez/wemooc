package com.liferay.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.Module;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.ModuleResultLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;

public class CleanRepeatedModuleResult extends CleanLearningActivity implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityTriesUser.class);
	
	public CleanRepeatedModuleResult(){
		super();
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		
		try{
			System.out.println(" receive : " + true );
			boolean updateDB = message.getBoolean("updateDB");
			
			long courseId = message.getLong("courseId");
			
			Course course = CourseLocalServiceUtil.getCourse(courseId);
			User user = (User)message.get("user");

			createInstance(course.getCompanyId(),course.getGroupCreatedId(), user.getUserId(), 0, 0);
			
			deleteRepeatedModuleResult(course, updateDB);
			
		}catch(Exception e){
			if(log.isInfoEnabled())log.info(e.getMessage());
			if(log.isDebugEnabled())e.printStackTrace();
		} finally {
			endInstance();
		}
		
	}
	
	public static void deleteRepeatedModuleResult(Course course, boolean updateBD) throws SystemException{

		int deleted = 0;
		String resultados = "";
		String traza = "";
		
		Calendar start = Calendar.getInstance();
		System.out.println(" ## START ## "+start.getTime());
		
		System.out.println(" ## course groupId ## "+course.getGroupCreatedId());
		
		List<Long> moduleResultDeleted = new ArrayList<Long>();
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
		
		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(Module.class, classLoader)
				.add(PropertyFactoryUtil.forName("groupId").eq(course.getGroupCreatedId()));

		List<Module> modules = ModuleLocalServiceUtil.dynamicQuery(dq);
		
		for(Module mod: modules){
			
			DynamicQuery dq1 = DynamicQueryFactoryUtil.forClass(ModuleResult.class, classLoader)
					.add(PropertyFactoryUtil.forName("moduleId").eq(mod.getModuleId()));

			List<ModuleResult> moduleResultList = ModuleResultLocalServiceUtil.dynamicQuery(dq1);
			
			for(ModuleResult moduleResult: moduleResultList){
				
				//Para no volver a hacer lo mismo cuando ya borramos un module result
				if(!moduleResultDeleted.contains(moduleResult.getMrId())){
	
					List<ModuleResult> moduleResultUserList = ModuleResultLocalServiceUtil.getListModuleResultByModuleAndUser(moduleResult.getModuleId(), moduleResult.getUserId());
					
					if(moduleResultUserList.size() > 1){
					
						System.out.println("  module: "+moduleResult.getModuleId()+", user:  "+moduleResult.getUserId()+", Repetidos: "+moduleResultUserList.size());
						
						boolean isFirst = true;
						for(ModuleResult moduleRepeat: moduleResultUserList){
							if(!isFirst){
								
								System.out.println("   * Borrado ( total borrados: "+deleted+"): "+moduleRepeat.getMrId());
								traza += "  * Borrado ( total borrados: "+deleted+") mrId: "+moduleRepeat.getMrId();
								
								moduleResultDeleted.add(moduleRepeat.getMrId());
							
								try {
									ModuleResultLocalServiceUtil.deleteModuleResult(moduleRepeat.getMrId());
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								resultados += "delete from lms_moduleresult where mrId = "+moduleRepeat.getMrId()+"; \n";
								
								deleted++;
								
							}else{
								System.out.println("   Se mantiene : "+moduleRepeat.getMrId());
								traza += "  Se mantiene : "+moduleRepeat.getMrId();
							}
								
							isFirst = false;
						}
					}
				}
			}
		}
		
		Calendar end = Calendar.getInstance();
		System.out.println("------------------------------------------------");
		System.out.println(" ## START ## "+start.getTime());
		System.out.println(" ##  END  ## "+end.getTime());
		System.out.println(" ##  DELETED  ## "+deleted);
		System.out.println("------------------------------------------------");
				
		
	}

}
