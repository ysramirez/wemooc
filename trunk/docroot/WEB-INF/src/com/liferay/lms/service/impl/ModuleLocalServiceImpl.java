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


import java.util.Date;
import java.util.List;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.LearningActivityTry;
import com.liferay.lms.model.ModuleResult;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.service.base.ModuleLocalServiceBaseImpl;
import com.liferay.lms.service.persistence.ModuleUtil;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * The implementation of the module local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.lms.service.moduleLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.lms.service.moduleLocalServiceUtil} to access the module local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author cvicente
 * @see com.liferay.lms.service.base.moduleLocalServiceBaseImpl
 * @see com.liferay.lms.service.moduleLocalServiceUtil
 */
public class ModuleLocalServiceImpl extends ModuleLocalServiceBaseImpl 
{

	@SuppressWarnings("unchecked")
	public List findAllInUser(long userId)throws SystemException {
		List<Module> list = (List<Module>) ModuleUtil.findByUserId(userId);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List findAllInUser(long userId, OrderByComparator orderByComparator) throws SystemException {
		List<Module> list = (List<Module>) ModuleUtil.findByUserId(userId, QueryUtil.ALL_POS,QueryUtil.ALL_POS, orderByComparator);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List findAllInGroup(long groupId) throws SystemException {
		List<Module> list = (List<Module>) ModuleUtil.findByGroupId(groupId);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List findAllInGroup(long groupId, OrderByComparator orderByComparator) throws SystemException{
		List <Module> list = (List<Module>) ModuleUtil.findByGroupId(groupId,QueryUtil.ALL_POS,QueryUtil.ALL_POS, orderByComparator);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List findAllInUserAndGroup(long userId, long groupId) throws SystemException {
		List<Module> list = (List<Module>) ModuleUtil.findByUserIdGroupId(userId, groupId);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List findAllInUserAndGroup(long userId, long groupId, OrderByComparator orderByComparator) throws SystemException {
		List<Module> list = (List<Module>) ModuleUtil.findByUserIdGroupId(userId, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
		return list;
	}


	public Module getPreviusModule(long moduleId) throws SystemException
	{
		Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
		return getPreviusModule(theModule);
	}

	public Module getPreviusModule(Module theModule) throws SystemException {
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(Module.class);
		Criterion criterion=PropertyFactoryUtil.forName("ordern").lt(theModule.getOrdern());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("groupId").eq(theModule.getGroupId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().desc("ordern");
		dq.addOrder(createOrder);

		java.util.List<Module> modulesp=(java.util.List<Module>)moduleLocalService.dynamicQuery(dq,0,1);
		if(modulesp!=null&& modulesp.size()>0)
		{
			return modulesp.get(0);
		}
		else
		{
			return null;
		}
	}
	public Module getNextModule(long moduleId) throws SystemException
	{
		Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
		return getNextModule(theModule);
	}

	public Module getNextModule(Module theModule) throws SystemException {
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(Module.class);
		Criterion criterion=PropertyFactoryUtil.forName("ordern").gt(theModule.getOrdern());
		dq.add(criterion);
		criterion=PropertyFactoryUtil.forName("groupId").eq(theModule.getGroupId());
		dq.add(criterion);
		Order createOrder=OrderFactoryUtil.getOrderFactory().asc("ordern");
		dq.addOrder(createOrder);

		java.util.List<Module> modulesp=(java.util.List<Module>)moduleLocalService.dynamicQuery(dq,0,1);
		if(modulesp!=null&& modulesp.size()>0)
		{
			return modulesp.get(0);
		}
		else
		{
			return null;
		}
	}

	public void goUpModule(long moduleId ) throws SystemException
	{
		Module previusModule=getPreviusModule(moduleId);
		if(previusModule!=null)
		{
			
			Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
			long priority=theModule.getOrdern();
			theModule.setOrdern(previusModule.getOrdern());
			previusModule.setOrdern(priority);
			modulePersistence.update(theModule, true);
			modulePersistence.update(previusModule, true);
		}
		
	}
	public void goDownModule(long moduleId ) throws SystemException
	{
		Module nextModule=getNextModule(moduleId);
		if(nextModule!=null)
		{
			
			Module theModule=modulePersistence.fetchByPrimaryKey(moduleId);
			long priority=theModule.getOrdern();
			theModule.setOrdern(nextModule.getOrdern());
			nextModule.setOrdern(priority);
			modulePersistence.update(theModule, true);
			modulePersistence.update(nextModule, true);
		}
		
	}
	public Module addmodule (Module validmodule) throws SystemException {
	    Module fileobj = ModuleUtil.create(CounterLocalServiceUtil.increment(Module.class.getName()));

	    fileobj.setCompanyId(validmodule.getCompanyId());
	    fileobj.setGroupId(validmodule.getGroupId());
	    fileobj.setUserId(validmodule.getUserId());
	    fileobj.setStartDate(validmodule.getStartDate());
	    fileobj.setEndDate(validmodule.getEndDate());
	    fileobj.setTitle(validmodule.getTitle());
	    fileobj.setDescription(validmodule.getDescription());
	    fileobj.setOrdern(fileobj.getModuleId());
	    try {
			resourceLocalService.addResources(
					validmodule.getCompanyId(), validmodule.getGroupId(), validmodule.getUserId(),
			Module.class.getName(), validmodule.getPrimaryKey(), false,
			true, true);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e);
		}
	    return ModuleUtil.update(fileobj, false);
	}
	
	public Module addModule(Long companyId, Long courseId, Long userId, 
			String title, String description,
			Date startDate, Date endDate, Long ordern) throws SystemException {
		Module fileobj = ModuleUtil.create(CounterLocalServiceUtil.increment(Module.class.getName()));

	    fileobj.setCompanyId(companyId);
	    fileobj.setGroupId(courseId);
	    fileobj.setUserId(userId);
	    fileobj.setStartDate(startDate);
	    fileobj.setEndDate(endDate);
	    fileobj.setTitle(title);
	    fileobj.setDescription(description);
	    fileobj.setOrdern(ordern != null ? ordern : fileobj.getModuleId());
	    try {
			resourceLocalService.addResources(
					companyId, courseId, userId,
					Module.class.getName(), fileobj.getPrimaryKey(), 
					false, true, true);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e);
		}
	    return ModuleUtil.update(fileobj, false);
	}

	public void remove(Module fileobj) throws SystemException {

//		modulePersistence.remove(fileobj);
		try {
			resourceLocalService.deleteResource(
					fileobj.getCompanyId(), Module.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, fileobj.getPrimaryKey());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e);
		}
		modulePersistence.remove(fileobj);
	}

	@Override
	public Module updateModule(Module module) throws SystemException {
		// TODO Auto-generated method stub
		try {
			if(resourceLocalService.getResource(module.getCompanyId(), Module.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,Long.toString( module.getPrimaryKey()))==null)
					{
				resourceLocalService.addResources(
						module.getCompanyId(), module.getGroupId(), module.getUserId(),
				Module.class.getName(), module.getPrimaryKey(), false,
				true, true);
					}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e);
		}
		return super.updateModule(module);
	}

	@Override
	public Module updateModule(Module module, boolean merge)
			throws SystemException {
		// TODO Auto-generated method stub
		return super.updateModule(module, merge);
	}
	public boolean isUserPassed(long moduleId,long userId) throws SystemException
	{
		ModuleResult moduleResult=moduleResultLocalService.getByModuleAndUser(moduleId, userId);
		if(moduleResult==null ||!moduleResult.getPassed())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean isLocked(long moduleId,long userId) throws Exception
	{
		Module theModule=ModuleLocalServiceUtil.getModule(moduleId);
		java.util.Date now=new java.util.Date(System.currentTimeMillis());
		Course course=courseLocalService.fetchByGroupCreatedId(theModule.getGroupId());
		if(!UserLocalServiceUtil.hasGroupUser(theModule.getGroupId(), userId))
		{
			return true;
		}
		User user=UserLocalServiceUtil.getUser(userId);
		
		PermissionChecker permissionChecker=PermissionCheckerFactoryUtil.create(user, true);
		if(!permissionChecker.hasPermission(course.getGroupId(),  Course.class.getName(),course.getCourseId(),ActionKeys.ACCESS))
		{
			return true;
		}
		if((theModule.getEndDate()!=null&&theModule.getEndDate().before(now)) ||(theModule.getStartDate()!=null&&theModule.getStartDate().after(now)))
		{
			return true;
		}
		if(theModule.getPrecedence()>0)
		{
			return !isUserPassed(theModule.getPrecedence(), userId);
		}
		return false;
	}
	public long countByGroupId(long groupId) throws SystemException
	{
		return modulePersistence.countByGroupId(groupId);
	}
	public long usersStarted(long moduleId) throws SystemException
	{
		DynamicQuery dq=DynamicQueryFactoryUtil.forClass(LearningActivityTry.class);
		java.util.List<Long> actIds=LearningActivityLocalServiceUtil.getLearningActivityIdsOfModule(moduleId);
		long result=0;
		Criterion crit;
		crit = PropertyFactoryUtil.forName("actId").in(actIds.toArray());
		dq.add(crit);
		dq.setProjection(ProjectionFactoryUtil.distinct(ProjectionFactoryUtil.property("userId")));
		result=LearningActivityTryLocalServiceUtil.dynamicQueryCount(dq);
		return result;
	}
	public long modulesUserPassed(long groupId, long userId) throws SystemException
	{
		java.util.List<Module> themodules=moduleLocalService.findAllInGroup(groupId);
		long count=0;
		for(Module theModule:themodules)
		{
			if(moduleLocalService.isUserPassed(theModule.getModuleId(), userId))
			{
				count++;
			}
		}
		return count;
	}
}