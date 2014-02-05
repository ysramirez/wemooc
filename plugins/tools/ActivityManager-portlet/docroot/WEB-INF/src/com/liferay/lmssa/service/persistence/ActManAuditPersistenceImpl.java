/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.lmssa.service.persistence;

import com.liferay.lmssa.NoSuchActManAuditException;
import com.liferay.lmssa.model.ActManAudit;
import com.liferay.lmssa.model.impl.ActManAuditImpl;
import com.liferay.lmssa.model.impl.ActManAuditModelImpl;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the act man audit service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see ActManAuditPersistence
 * @see ActManAuditUtil
 * @generated
 */
public class ActManAuditPersistenceImpl extends BasePersistenceImpl<ActManAudit>
	implements ActManAuditPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ActManAuditUtil} to access the act man audit persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ActManAuditImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, ActManAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, ActManAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			ActManAuditModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, ActManAuditImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			ActManAuditModelImpl.UUID_COLUMN_BITMASK |
			ActManAuditModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, ActManAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBycompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, ActManAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBycompanyId",
			new String[] { Long.class.getName() },
			ActManAuditModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBycompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, ActManAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, ActManAuditImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the act man audit in the entity cache if it is enabled.
	 *
	 * @param actManAudit the act man audit
	 */
	public void cacheResult(ActManAudit actManAudit) {
		EntityCacheUtil.putResult(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditImpl.class, actManAudit.getPrimaryKey(), actManAudit);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				actManAudit.getUuid(), Long.valueOf(actManAudit.getGroupId())
			}, actManAudit);

		actManAudit.resetOriginalValues();
	}

	/**
	 * Caches the act man audits in the entity cache if it is enabled.
	 *
	 * @param actManAudits the act man audits
	 */
	public void cacheResult(List<ActManAudit> actManAudits) {
		for (ActManAudit actManAudit : actManAudits) {
			if (EntityCacheUtil.getResult(
						ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
						ActManAuditImpl.class, actManAudit.getPrimaryKey()) == null) {
				cacheResult(actManAudit);
			}
			else {
				actManAudit.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all act man audits.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ActManAuditImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ActManAuditImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the act man audit.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ActManAudit actManAudit) {
		EntityCacheUtil.removeResult(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditImpl.class, actManAudit.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(actManAudit);
	}

	@Override
	public void clearCache(List<ActManAudit> actManAudits) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ActManAudit actManAudit : actManAudits) {
			EntityCacheUtil.removeResult(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
				ActManAuditImpl.class, actManAudit.getPrimaryKey());

			clearUniqueFindersCache(actManAudit);
		}
	}

	protected void clearUniqueFindersCache(ActManAudit actManAudit) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				actManAudit.getUuid(), Long.valueOf(actManAudit.getGroupId())
			});
	}

	/**
	 * Creates a new act man audit with the primary key. Does not add the act man audit to the database.
	 *
	 * @param actManAuditId the primary key for the new act man audit
	 * @return the new act man audit
	 */
	public ActManAudit create(long actManAuditId) {
		ActManAudit actManAudit = new ActManAuditImpl();

		actManAudit.setNew(true);
		actManAudit.setPrimaryKey(actManAuditId);

		String uuid = PortalUUIDUtil.generate();

		actManAudit.setUuid(uuid);

		return actManAudit;
	}

	/**
	 * Removes the act man audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param actManAuditId the primary key of the act man audit
	 * @return the act man audit that was removed
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit remove(long actManAuditId)
		throws NoSuchActManAuditException, SystemException {
		return remove(Long.valueOf(actManAuditId));
	}

	/**
	 * Removes the act man audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the act man audit
	 * @return the act man audit that was removed
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ActManAudit remove(Serializable primaryKey)
		throws NoSuchActManAuditException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ActManAudit actManAudit = (ActManAudit)session.get(ActManAuditImpl.class,
					primaryKey);

			if (actManAudit == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActManAuditException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(actManAudit);
		}
		catch (NoSuchActManAuditException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ActManAudit removeImpl(ActManAudit actManAudit)
		throws SystemException {
		actManAudit = toUnwrappedModel(actManAudit);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, actManAudit);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(actManAudit);

		return actManAudit;
	}

	@Override
	public ActManAudit updateImpl(
		com.liferay.lmssa.model.ActManAudit actManAudit, boolean merge)
		throws SystemException {
		actManAudit = toUnwrappedModel(actManAudit);

		boolean isNew = actManAudit.isNew();

		ActManAuditModelImpl actManAuditModelImpl = (ActManAuditModelImpl)actManAudit;

		if (Validator.isNull(actManAudit.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			actManAudit.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, actManAudit, merge);

			actManAudit.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ActManAuditModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((actManAuditModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						actManAuditModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { actManAuditModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((actManAuditModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(actManAuditModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(actManAuditModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
			ActManAuditImpl.class, actManAudit.getPrimaryKey(), actManAudit);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					actManAudit.getUuid(),
					Long.valueOf(actManAudit.getGroupId())
				}, actManAudit);
		}
		else {
			if ((actManAuditModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						actManAuditModelImpl.getOriginalUuid(),
						Long.valueOf(actManAuditModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						actManAudit.getUuid(),
						Long.valueOf(actManAudit.getGroupId())
					}, actManAudit);
			}
		}

		return actManAudit;
	}

	protected ActManAudit toUnwrappedModel(ActManAudit actManAudit) {
		if (actManAudit instanceof ActManAuditImpl) {
			return actManAudit;
		}

		ActManAuditImpl actManAuditImpl = new ActManAuditImpl();

		actManAuditImpl.setNew(actManAudit.isNew());
		actManAuditImpl.setPrimaryKey(actManAudit.getPrimaryKey());

		actManAuditImpl.setUuid(actManAudit.getUuid());
		actManAuditImpl.setActManAuditId(actManAudit.getActManAuditId());
		actManAuditImpl.setCompanyId(actManAudit.getCompanyId());
		actManAuditImpl.setGroupId(actManAudit.getGroupId());
		actManAuditImpl.setUserId(actManAudit.getUserId());
		actManAuditImpl.setCourseId(actManAudit.getCourseId());
		actManAuditImpl.setClassName(actManAudit.getClassName());
		actManAuditImpl.setStart(actManAudit.getStart());
		actManAuditImpl.setEnd(actManAudit.getEnd());
		actManAuditImpl.setState(actManAudit.getState());
		actManAuditImpl.setNumber(actManAudit.getNumber());
		actManAuditImpl.setModuleId(actManAudit.getModuleId());
		actManAuditImpl.setActId(actManAudit.getActId());

		return actManAuditImpl;
	}

	/**
	 * Returns the act man audit with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the act man audit
	 * @return the act man audit
	 * @throws com.liferay.portal.NoSuchModelException if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ActManAudit findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the act man audit with the primary key or throws a {@link com.liferay.lmssa.NoSuchActManAuditException} if it could not be found.
	 *
	 * @param actManAuditId the primary key of the act man audit
	 * @return the act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit findByPrimaryKey(long actManAuditId)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = fetchByPrimaryKey(actManAuditId);

		if (actManAudit == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + actManAuditId);
			}

			throw new NoSuchActManAuditException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				actManAuditId);
		}

		return actManAudit;
	}

	/**
	 * Returns the act man audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the act man audit
	 * @return the act man audit, or <code>null</code> if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ActManAudit fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the act man audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param actManAuditId the primary key of the act man audit
	 * @return the act man audit, or <code>null</code> if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit fetchByPrimaryKey(long actManAuditId)
		throws SystemException {
		ActManAudit actManAudit = (ActManAudit)EntityCacheUtil.getResult(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
				ActManAuditImpl.class, actManAuditId);

		if (actManAudit == _nullActManAudit) {
			return null;
		}

		if (actManAudit == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				actManAudit = (ActManAudit)session.get(ActManAuditImpl.class,
						Long.valueOf(actManAuditId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (actManAudit != null) {
					cacheResult(actManAudit);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(ActManAuditModelImpl.ENTITY_CACHE_ENABLED,
						ActManAuditImpl.class, actManAuditId, _nullActManAudit);
				}

				closeSession(session);
			}
		}

		return actManAudit;
	}

	/**
	 * Returns all the act man audits where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the act man audits where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of act man audits
	 * @param end the upper bound of the range of act man audits (not inclusive)
	 * @return the range of matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the act man audits where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of act man audits
	 * @param end the upper bound of the range of act man audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<ActManAudit> list = (List<ActManAudit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ActManAudit actManAudit : list) {
				if (!Validator.equals(uuid, actManAudit.getUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ACTMANAUDIT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ActManAuditModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<ActManAudit>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first act man audit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = fetchByUuid_First(uuid, orderByComparator);

		if (actManAudit != null) {
			return actManAudit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActManAuditException(msg.toString());
	}

	/**
	 * Returns the first act man audit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching act man audit, or <code>null</code> if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<ActManAudit> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last act man audit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = fetchByUuid_Last(uuid, orderByComparator);

		if (actManAudit != null) {
			return actManAudit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActManAuditException(msg.toString());
	}

	/**
	 * Returns the last act man audit in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching act man audit, or <code>null</code> if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<ActManAudit> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the act man audits before and after the current act man audit in the ordered set where uuid = &#63;.
	 *
	 * @param actManAuditId the primary key of the current act man audit
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit[] findByUuid_PrevAndNext(long actManAuditId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = findByPrimaryKey(actManAuditId);

		Session session = null;

		try {
			session = openSession();

			ActManAudit[] array = new ActManAuditImpl[3];

			array[0] = getByUuid_PrevAndNext(session, actManAudit, uuid,
					orderByComparator, true);

			array[1] = actManAudit;

			array[2] = getByUuid_PrevAndNext(session, actManAudit, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ActManAudit getByUuid_PrevAndNext(Session session,
		ActManAudit actManAudit, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ACTMANAUDIT_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(ActManAuditModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(actManAudit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ActManAudit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the act man audit where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lmssa.NoSuchActManAuditException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit findByUUID_G(String uuid, long groupId)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = fetchByUUID_G(uuid, groupId);

		if (actManAudit == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActManAuditException(msg.toString());
		}

		return actManAudit;
	}

	/**
	 * Returns the act man audit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching act man audit, or <code>null</code> if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the act man audit where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching act man audit, or <code>null</code> if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof ActManAudit) {
			ActManAudit actManAudit = (ActManAudit)result;

			if (!Validator.equals(uuid, actManAudit.getUuid()) ||
					(groupId != actManAudit.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ACTMANAUDIT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			query.append(ActManAuditModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<ActManAudit> list = q.list();

				result = list;

				ActManAudit actManAudit = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					actManAudit = list.get(0);

					cacheResult(actManAudit);

					if ((actManAudit.getUuid() == null) ||
							!actManAudit.getUuid().equals(uuid) ||
							(actManAudit.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, actManAudit);
					}
				}

				return actManAudit;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ActManAudit)result;
			}
		}
	}

	/**
	 * Returns all the act man audits where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findBycompanyId(long companyId)
		throws SystemException {
		return findBycompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the act man audits where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of act man audits
	 * @param end the upper bound of the range of act man audits (not inclusive)
	 * @return the range of matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findBycompanyId(long companyId, int start, int end)
		throws SystemException {
		return findBycompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the act man audits where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of act man audits
	 * @param end the upper bound of the range of act man audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findBycompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<ActManAudit> list = (List<ActManAudit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ActManAudit actManAudit : list) {
				if ((companyId != actManAudit.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ACTMANAUDIT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(ActManAuditModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<ActManAudit>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first act man audit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit findBycompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = fetchBycompanyId_First(companyId,
				orderByComparator);

		if (actManAudit != null) {
			return actManAudit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActManAuditException(msg.toString());
	}

	/**
	 * Returns the first act man audit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching act man audit, or <code>null</code> if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit fetchBycompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ActManAudit> list = findBycompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last act man audit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit findBycompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = fetchBycompanyId_Last(companyId,
				orderByComparator);

		if (actManAudit != null) {
			return actManAudit;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchActManAuditException(msg.toString());
	}

	/**
	 * Returns the last act man audit in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching act man audit, or <code>null</code> if a matching act man audit could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit fetchBycompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countBycompanyId(companyId);

		List<ActManAudit> list = findBycompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the act man audits before and after the current act man audit in the ordered set where companyId = &#63;.
	 *
	 * @param actManAuditId the primary key of the current act man audit
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next act man audit
	 * @throws com.liferay.lmssa.NoSuchActManAuditException if a act man audit with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit[] findBycompanyId_PrevAndNext(long actManAuditId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = findByPrimaryKey(actManAuditId);

		Session session = null;

		try {
			session = openSession();

			ActManAudit[] array = new ActManAuditImpl[3];

			array[0] = getBycompanyId_PrevAndNext(session, actManAudit,
					companyId, orderByComparator, true);

			array[1] = actManAudit;

			array[2] = getBycompanyId_PrevAndNext(session, actManAudit,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ActManAudit getBycompanyId_PrevAndNext(Session session,
		ActManAudit actManAudit, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ACTMANAUDIT_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(ActManAuditModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(actManAudit);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ActManAudit> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the act man audits.
	 *
	 * @return the act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the act man audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of act man audits
	 * @param end the upper bound of the range of act man audits (not inclusive)
	 * @return the range of act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the act man audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of act man audits
	 * @param end the upper bound of the range of act man audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public List<ActManAudit> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ActManAudit> list = (List<ActManAudit>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ACTMANAUDIT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ACTMANAUDIT.concat(ActManAuditModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ActManAudit>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ActManAudit>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the act man audits where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (ActManAudit actManAudit : findByUuid(uuid)) {
			remove(actManAudit);
		}
	}

	/**
	 * Removes the act man audit where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the act man audit that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ActManAudit removeByUUID_G(String uuid, long groupId)
		throws NoSuchActManAuditException, SystemException {
		ActManAudit actManAudit = findByUUID_G(uuid, groupId);

		return remove(actManAudit);
	}

	/**
	 * Removes all the act man audits where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeBycompanyId(long companyId) throws SystemException {
		for (ActManAudit actManAudit : findBycompanyId(companyId)) {
			remove(actManAudit);
		}
	}

	/**
	 * Removes all the act man audits from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ActManAudit actManAudit : findAll()) {
			remove(actManAudit);
		}
	}

	/**
	 * Returns the number of act man audits where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ACTMANAUDIT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of act man audits where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ACTMANAUDIT_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of act man audits where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public int countBycompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ACTMANAUDIT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of act man audits.
	 *
	 * @return the number of act man audits
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ACTMANAUDIT);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the act man audit persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lmssa.model.ActManAudit")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ActManAudit>> listenersList = new ArrayList<ModelListener<ActManAudit>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ActManAudit>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ActManAuditImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = ActManAuditPersistence.class)
	protected ActManAuditPersistence actManAuditPersistence;
	@BeanReference(type = LearningActivityTryDeletedPersistence.class)
	protected LearningActivityTryDeletedPersistence learningActivityTryDeletedPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ACTMANAUDIT = "SELECT actManAudit FROM ActManAudit actManAudit";
	private static final String _SQL_SELECT_ACTMANAUDIT_WHERE = "SELECT actManAudit FROM ActManAudit actManAudit WHERE ";
	private static final String _SQL_COUNT_ACTMANAUDIT = "SELECT COUNT(actManAudit) FROM ActManAudit actManAudit";
	private static final String _SQL_COUNT_ACTMANAUDIT_WHERE = "SELECT COUNT(actManAudit) FROM ActManAudit actManAudit WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "actManAudit.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "actManAudit.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(actManAudit.uuid IS NULL OR actManAudit.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "actManAudit.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "actManAudit.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(actManAudit.uuid IS NULL OR actManAudit.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "actManAudit.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "actManAudit.companyId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "actManAudit.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ActManAudit exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ActManAudit exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(ActManAuditPersistenceImpl.class);
	private static ActManAudit _nullActManAudit = new ActManAuditImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ActManAudit> toCacheModel() {
				return _nullActManAuditCacheModel;
			}
		};

	private static CacheModel<ActManAudit> _nullActManAuditCacheModel = new CacheModel<ActManAudit>() {
			public ActManAudit toEntityModel() {
				return _nullActManAudit;
			}
		};
}