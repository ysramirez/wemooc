package com.liferay.manager;

import java.util.List;

import com.liferay.lms.model.LearningActivityResult;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.lms.service.LearningActivityResultLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.persistence.P2pActivityCorrectionsUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

public class CleanLearningActivityP2P extends CleanLearningActivity implements MessageListener {
	
	@Override
	public void receive(Message message) throws MessageListenerException {

		long groupId  = message.getLong("groupId");
		long moduleId = message.getLong("moduleId");
		long userId   = message.getLong("userId");
		long actId    = message.getLong("actId");
		
		String action = message.getString("action");
		
		if("deleteUserUpload".equals(action)){
			
			deleteUserUpload(actId, userId);
			
		} else if("deleteUserUploadAndCorrections".equals(action)){
			
			//Borrar las asignaciones que tiene asignadas
			deleteP2PCorrections(actId, userId);
			
			//Borrar la propia tarea
			deleteUserUpload(groupId, moduleId);
			
		} 
		
	}
	
	private void deleteUserUpload(long actId, long userId){
		
		try {
			
			P2pActivity p2p = P2pActivityLocalServiceUtil.findByActIdAndUserId(actId, userId);
			
			//Las P2P sólo generan un try cuando se hace la entrega, con obtener la última nos es suficiente.
			LearningActivityTry learnTry = LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId, userId);
			LearningActivityResult learnResult = LearningActivityResultLocalServiceUtil.getByActIdAndUserId(actId, userId);
			
			//Borrar los registros de la base de datos.
			LearningActivityResultLocalServiceUtil.deleteLearningActivityResult(learnResult);
			LearningActivityTryLocalServiceUtil.deleteLearningActivityTry(learnTry);
			P2pActivityLocalServiceUtil.deleteP2pActivity(p2p);
			
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void deleteP2PCorrections(long actId, long userId){
		
		try {
			
			ClassLoader classLoader = (ClassLoader) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),"portletClassLoader");
			DynamicQuery dq=DynamicQueryFactoryUtil.forClass(P2pActivityCorrections.class,classLoader);
		  	Criterion criterion=PropertyFactoryUtil.forName("actId").eq(actId);
			dq.add(criterion);

			List<P2pActivityCorrections> corrections = P2pActivityCorrectionsUtil.findWithDynamicQuery(dq);
			
			for(P2pActivityCorrections correction:corrections){
				System.out.println(" correction : " + correction.getActId()+" - "+correction.getUserId() );
				P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections(correction);
				
			}

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
