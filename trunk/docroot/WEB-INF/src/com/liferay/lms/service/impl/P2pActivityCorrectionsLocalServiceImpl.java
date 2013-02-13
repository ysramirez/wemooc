/**
 * 2012 TELEFONICA LEARNING SERVICES. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.lms.service.impl;

import java.util.List;

import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.P2pActivityCorrections;
import com.liferay.lms.service.base.P2pActivityCorrectionsLocalServiceBaseImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;


/**
 * The implementation of the p2p activity corrections local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.P2pActivityCorrectionsLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil} to access the p2p activity corrections local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.P2pActivityCorrectionsLocalServiceBaseImpl
 * @see com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil
 */
public class P2pActivityCorrectionsLocalServiceImpl
	extends P2pActivityCorrectionsLocalServiceBaseImpl{
	
	public P2pActivityCorrections findByP2pActivityIdAndUserId(Long p2pActivityId,
			Long userId){
		
		try{
			List<P2pActivityCorrections> p2pList = p2pActivityCorrectionsPersistence.findByP2pActivityIdAndUserId(p2pActivityId, userId);
			if(!p2pList.isEmpty())
			{
				return p2pList.get(0);
			}else{
				return null;
			}
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByP2pActivityIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public boolean exitsCorP2p(Long p2pActivityId, Long userId){
		
		try{
			List<P2pActivityCorrections> p2pList = p2pActivityCorrectionsPersistence.findByP2pActivityIdAndUserId(p2pActivityId, userId);
			if(!p2pList.isEmpty() && p2pList.size()>0)
			{
				return true;
			}else{
				return false;
			}
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("P2pActivityCorrectionsLocalService.exitsCorP2p no exist correction with p2pActivityId:"+p2pActivityId+" - userId"+userId);
				_log.error(e.getMessage());
			}
			return false;
		}
	}
	public List<P2pActivityCorrections> findByP2pActivityId(Long p2pActivityId){
		
		try{
			return p2pActivityCorrectionsPersistence.findByP2pActivityId(p2pActivityId);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByP2pActivityIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public List<P2pActivityCorrections> findByActIdIdAndUserId(Long actId,
			Long userId){
		
		try{
			return p2pActivityCorrectionsPersistence.findByActIdAndUserId(actId, userId);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByActIdIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public P2pActivityCorrections addorUpdateP2pActivityCorrections(P2pActivityCorrections p2pActCor){
		
		try{
			long p2pActCorId = counterLocalService.increment(P2pActivityCorrections.class.getName());
			p2pActCor.setNew(true);
			p2pActCor.setP2pActivityCorrectionsId(p2pActCorId);
			
			return p2pActivityCorrectionsPersistence.update(p2pActCor, false);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityCorrectionsLocalService.findByActIdIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	private static Log _log = LogFactoryUtil.getLog(P2pActivity.class);
}