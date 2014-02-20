package com.liferay.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;

public class CleanRepeatedLearningActivityResult extends CleanLearningActivity implements MessageListener{
	Log log = LogFactoryUtil.getLog(CleanLearningActivityTriesUser.class);
	
	public CleanRepeatedLearningActivityResult(){
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
	
	@SuppressWarnings("unchecked")
	public static void deleteRepeatedModuleResult(Course course, boolean updateBD) throws SystemException{

		int deleted = 0;
		
		Calendar start = Calendar.getInstance();
		System.out.println(" ## START ## "+start.getTime());
		
		System.out.println(" ## course groupId ## "+course.getGroupCreatedId());
		
		List<Long> activityResultDeleted = new ArrayList<Long>();
		
		ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
		
		DynamicQuery dq = DynamicQueryFactoryUtil.forClass(LearningActivity.class, classLoader)
				.add(PropertyFactoryUtil.forName("groupId").eq(course.getGroupCreatedId()));

		List<LearningActivity> actities = LearningActivityLocalServiceUtil.dynamicQuery(dq);
		
		for(LearningActivity activity:actities){
			
			System.out.println("\n  learning activity: "+activity.getActId()+", user:  "+activity.getUserId()+", module: "+activity.getModuleId());
			
			DynamicQuery dqResults = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
					.add(PropertyFactoryUtil.forName("actId").eq(activity.getActId()))
					.addOrder(OrderFactoryUtil.getOrderFactory().asc("actId"));
	
			List< LearningActivityResult> actResultList = LearningActivityResultLocalServiceUtil.dynamicQuery(dqResults);
			
			for(LearningActivityResult actResult: actResultList){
				
				//Para no volver a hacer lo mismo cuando ya borramos un module result
				if(!activityResultDeleted.contains(actResult.getActId())){
					
					System.out.println("    learning activity result: "+actResult.getActId()+", user:  "+actResult.getUserId()+", result: "+actResult.getResult());
					
					DynamicQuery dqla = DynamicQueryFactoryUtil.forClass(LearningActivityResult.class, classLoader)
							.add(PropertyFactoryUtil.forName("actId").eq(actResult.getActId()))
							.add(PropertyFactoryUtil.forName("userId").eq(actResult.getUserId()))
							.addOrder(OrderFactoryUtil.getOrderFactory().desc("endDate"))
							.addOrder(OrderFactoryUtil.getOrderFactory().desc("result"));
	
					List< LearningActivityResult> activityResultList = LearningActivityResultLocalServiceUtil.dynamicQuery(dqla);
					
					if(activityResultList.size() > 1){
						
						System.out.println("   Repeated  ");
					
						System.out.println("      learning activity result repeated: "+actResult.getActId()+", user:  "+actResult.getUserId()+", Repetidos: "+activityResultList.size());
						
						boolean isFirst = true;
						for(LearningActivityResult activityRepeat: activityResultList){
							
							if(!isFirst){
								
								System.out.println("      * Borrado ( total borrados: "+(deleted+1)+"): "+activityRepeat.getLarId());
								
								activityResultDeleted.add(activityRepeat.getLarId());
								
								try {
									LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(activityRepeat.getLarId());
								} catch (Exception e) {
									e.printStackTrace();
								}
																
								deleted++;
								
							}else{
								System.out.println("      Se mantiene : "+activityRepeat.getLarId());
							}
								
							isFirst = false;
						}
					}else{
						break;
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
