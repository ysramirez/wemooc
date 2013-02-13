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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.lms.model.P2pActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.P2pActivityLocalServiceUtil;
import com.liferay.lms.service.base.P2pActivityLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * The implementation of the p2p activity local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.P2pActivityLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.P2pActivityLocalServiceUtil} to access the p2p activity local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.P2pActivityLocalServiceBaseImpl
 * @see com.liferay.lms.service.P2pActivityLocalServiceUtil
 */
public class P2pActivityLocalServiceImpl extends P2pActivityLocalServiceBaseImpl {
	
	public P2pActivity findByActIdAndUserId(long actId, long userId)
			throws SystemException {
		try{
			List<P2pActivity> myp2ps = p2pActivityPersistence.findByActIdAndUserId(actId, userId);

			if(!myp2ps.isEmpty()){
				for(P2pActivity myActivity : myp2ps){
					return myActivity;
				}
			}
			return null;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityLocalService.findByActIdAndUserId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public boolean existP2pAct(long actId, long userId)
			throws SystemException {
		try{
			List<P2pActivity> myp2ps = p2pActivityPersistence.findByActIdAndUserId(actId, userId);

			if(!myp2ps.isEmpty() && myp2ps.size()>0){
				return true;
			}
			return false;
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) {
				_log.error("No P2pActivity. exitP2pAct");
				_log.error(e.getMessage());
			}
			return false;
		}
	}
	public List<P2pActivity> findByActId(long actId)
			throws SystemException {
		try{
			return p2pActivityPersistence.findByActId(actId);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityLocalService.findByActId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	public List<P2pActivity> findByActIdOrderByP2pId(long actId)
			throws SystemException {
		try{
			
			DynamicQuery dq=DynamicQueryFactoryUtil.forClass(P2pActivity.class);
			Criterion criterion=PropertyFactoryUtil.forName("actId").eq(actId);
			dq.add(criterion);
			Order createOrder=OrderFactoryUtil.getOrderFactory().asc("p2pActivityId");
			dq.addOrder(createOrder);

			List<P2pActivity> modulesp=(List<P2pActivity>)P2pActivityLocalServiceUtil.dynamicQuery(dq);
			
			return modulesp;
			

		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error getting P2pActivityLocalService.findByActIdOrderByP2pId");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	@Override
	public P2pActivity addP2pActivity(P2pActivity newp2pAct)
			throws SystemException {
		try{
			
			long actP2PId = CounterLocalServiceUtil.increment(P2pActivity.class.getName());
			
			P2pActivity fileobj = p2pActivityPersistence.create(actP2PId);
			
			fileobj.setActId(newp2pAct.getActId());
			fileobj.setP2pActivityId(actP2PId);
			fileobj.setCountCorrections(0);
			fileobj.setDescription(newp2pAct.getDescription());
			fileobj.setFileEntryId(newp2pAct.getFileEntryId());
			fileobj.setUserId(newp2pAct.getUserId());

			return p2pActivityPersistence.update(fileobj, false);
			
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error doing P2pActivityLocalService.addP2pActivity");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	@Override
	public P2pActivity updateP2pActivity(P2pActivity newp2pAct)
			throws SystemException {
		try{
			return p2pActivityPersistence.update(newp2pAct, false);
		}
		catch(Exception e){
			if (_log.isErrorEnabled()) { 
				_log.error("Error doing P2pActivityLocalService.updateP2pActivity");
				_log.error(e.getMessage());
			}
			return null;
		}
	}
	private static Log _log = LogFactoryUtil.getLog(P2pActivity.class);
}