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

import com.liferay.lmssa.NoSuchLearningActivityTryDeletedException;
import com.liferay.lmssa.model.LearningActivityTryDeleted;
import com.liferay.lmssa.model.impl.LearningActivityTryDeletedImpl;
import com.liferay.lmssa.model.impl.LearningActivityTryDeletedModelImpl;

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
 * The persistence implementation for the learning activity try deleted service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author TLS
 * @see LearningActivityTryDeletedPersistence
 * @see LearningActivityTryDeletedUtil
 * @generated
 */
public class LearningActivityTryDeletedPersistenceImpl
	extends BasePersistenceImpl<LearningActivityTryDeleted>
	implements LearningActivityTryDeletedPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LearningActivityTryDeletedUtil} to access the learning activity try deleted persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LearningActivityTryDeletedImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LearningActivityTryDeletedModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTDEL = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByactDel",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTDEL =
		new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByactDel",
			new String[] { Long.class.getName() },
			LearningActivityTryDeletedModelImpl.ACTMANAUDITID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTDEL = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByactDel", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

	/**
	 * Caches the learning activity try deleted in the entity cache if it is enabled.
	 *
	 * @param learningActivityTryDeleted the learning activity try deleted
	 */
	public void cacheResult(
		LearningActivityTryDeleted learningActivityTryDeleted) {
		EntityCacheUtil.putResult(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			learningActivityTryDeleted.getPrimaryKey(),
			learningActivityTryDeleted);

		learningActivityTryDeleted.resetOriginalValues();
	}

	/**
	 * Caches the learning activity try deleteds in the entity cache if it is enabled.
	 *
	 * @param learningActivityTryDeleteds the learning activity try deleteds
	 */
	public void cacheResult(
		List<LearningActivityTryDeleted> learningActivityTryDeleteds) {
		for (LearningActivityTryDeleted learningActivityTryDeleted : learningActivityTryDeleteds) {
			if (EntityCacheUtil.getResult(
						LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityTryDeletedImpl.class,
						learningActivityTryDeleted.getPrimaryKey()) == null) {
				cacheResult(learningActivityTryDeleted);
			}
			else {
				learningActivityTryDeleted.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all learning activity try deleteds.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LearningActivityTryDeletedImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LearningActivityTryDeletedImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the learning activity try deleted.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LearningActivityTryDeleted learningActivityTryDeleted) {
		EntityCacheUtil.removeResult(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			learningActivityTryDeleted.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<LearningActivityTryDeleted> learningActivityTryDeleteds) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LearningActivityTryDeleted learningActivityTryDeleted : learningActivityTryDeleteds) {
			EntityCacheUtil.removeResult(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityTryDeletedImpl.class,
				learningActivityTryDeleted.getPrimaryKey());
		}
	}

	/**
	 * Creates a new learning activity try deleted with the primary key. Does not add the learning activity try deleted to the database.
	 *
	 * @param latDelId the primary key for the new learning activity try deleted
	 * @return the new learning activity try deleted
	 */
	public LearningActivityTryDeleted create(long latDelId) {
		LearningActivityTryDeleted learningActivityTryDeleted = new LearningActivityTryDeletedImpl();

		learningActivityTryDeleted.setNew(true);
		learningActivityTryDeleted.setPrimaryKey(latDelId);

		String uuid = PortalUUIDUtil.generate();

		learningActivityTryDeleted.setUuid(uuid);

		return learningActivityTryDeleted;
	}

	/**
	 * Removes the learning activity try deleted with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param latDelId the primary key of the learning activity try deleted
	 * @return the learning activity try deleted that was removed
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted remove(long latDelId)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		return remove(Long.valueOf(latDelId));
	}

	/**
	 * Removes the learning activity try deleted with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the learning activity try deleted
	 * @return the learning activity try deleted that was removed
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTryDeleted remove(Serializable primaryKey)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LearningActivityTryDeleted learningActivityTryDeleted = (LearningActivityTryDeleted)session.get(LearningActivityTryDeletedImpl.class,
					primaryKey);

			if (learningActivityTryDeleted == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLearningActivityTryDeletedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(learningActivityTryDeleted);
		}
		catch (NoSuchLearningActivityTryDeletedException nsee) {
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
	protected LearningActivityTryDeleted removeImpl(
		LearningActivityTryDeleted learningActivityTryDeleted)
		throws SystemException {
		learningActivityTryDeleted = toUnwrappedModel(learningActivityTryDeleted);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, learningActivityTryDeleted);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(learningActivityTryDeleted);

		return learningActivityTryDeleted;
	}

	@Override
	public LearningActivityTryDeleted updateImpl(
		com.liferay.lmssa.model.LearningActivityTryDeleted learningActivityTryDeleted,
		boolean merge) throws SystemException {
		learningActivityTryDeleted = toUnwrappedModel(learningActivityTryDeleted);

		boolean isNew = learningActivityTryDeleted.isNew();

		LearningActivityTryDeletedModelImpl learningActivityTryDeletedModelImpl = (LearningActivityTryDeletedModelImpl)learningActivityTryDeleted;

		if (Validator.isNull(learningActivityTryDeleted.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			learningActivityTryDeleted.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, learningActivityTryDeleted, merge);

			learningActivityTryDeleted.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew ||
				!LearningActivityTryDeletedModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((learningActivityTryDeletedModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						learningActivityTryDeletedModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						learningActivityTryDeletedModelImpl.getUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((learningActivityTryDeletedModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTDEL.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(learningActivityTryDeletedModelImpl.getOriginalActManAuditId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTDEL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTDEL,
					args);

				args = new Object[] {
						Long.valueOf(learningActivityTryDeletedModelImpl.getActManAuditId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTDEL, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTDEL,
					args);
			}
		}

		EntityCacheUtil.putResult(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
			LearningActivityTryDeletedImpl.class,
			learningActivityTryDeleted.getPrimaryKey(),
			learningActivityTryDeleted);

		return learningActivityTryDeleted;
	}

	protected LearningActivityTryDeleted toUnwrappedModel(
		LearningActivityTryDeleted learningActivityTryDeleted) {
		if (learningActivityTryDeleted instanceof LearningActivityTryDeletedImpl) {
			return learningActivityTryDeleted;
		}

		LearningActivityTryDeletedImpl learningActivityTryDeletedImpl = new LearningActivityTryDeletedImpl();

		learningActivityTryDeletedImpl.setNew(learningActivityTryDeleted.isNew());
		learningActivityTryDeletedImpl.setPrimaryKey(learningActivityTryDeleted.getPrimaryKey());

		learningActivityTryDeletedImpl.setUuid(learningActivityTryDeleted.getUuid());
		learningActivityTryDeletedImpl.setLatDelId(learningActivityTryDeleted.getLatDelId());
		learningActivityTryDeletedImpl.setActManAuditId(learningActivityTryDeleted.getActManAuditId());
		learningActivityTryDeletedImpl.setLatId(learningActivityTryDeleted.getLatId());
		learningActivityTryDeletedImpl.setActId(learningActivityTryDeleted.getActId());
		learningActivityTryDeletedImpl.setUserId(learningActivityTryDeleted.getUserId());
		learningActivityTryDeletedImpl.setStartDate(learningActivityTryDeleted.getStartDate());
		learningActivityTryDeletedImpl.setResult(learningActivityTryDeleted.getResult());
		learningActivityTryDeletedImpl.setEndDate(learningActivityTryDeleted.getEndDate());
		learningActivityTryDeletedImpl.setTryData(learningActivityTryDeleted.getTryData());
		learningActivityTryDeletedImpl.setTryResultData(learningActivityTryDeleted.getTryResultData());
		learningActivityTryDeletedImpl.setComments(learningActivityTryDeleted.getComments());

		return learningActivityTryDeletedImpl;
	}

	/**
	 * Returns the learning activity try deleted with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity try deleted
	 * @return the learning activity try deleted
	 * @throws com.liferay.portal.NoSuchModelException if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTryDeleted findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity try deleted with the primary key or throws a {@link com.liferay.lmssa.NoSuchLearningActivityTryDeletedException} if it could not be found.
	 *
	 * @param latDelId the primary key of the learning activity try deleted
	 * @return the learning activity try deleted
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted findByPrimaryKey(long latDelId)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = fetchByPrimaryKey(latDelId);

		if (learningActivityTryDeleted == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + latDelId);
			}

			throw new NoSuchLearningActivityTryDeletedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				latDelId);
		}

		return learningActivityTryDeleted;
	}

	/**
	 * Returns the learning activity try deleted with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the learning activity try deleted
	 * @return the learning activity try deleted, or <code>null</code> if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LearningActivityTryDeleted fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the learning activity try deleted with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param latDelId the primary key of the learning activity try deleted
	 * @return the learning activity try deleted, or <code>null</code> if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted fetchByPrimaryKey(long latDelId)
		throws SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = (LearningActivityTryDeleted)EntityCacheUtil.getResult(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
				LearningActivityTryDeletedImpl.class, latDelId);

		if (learningActivityTryDeleted == _nullLearningActivityTryDeleted) {
			return null;
		}

		if (learningActivityTryDeleted == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				learningActivityTryDeleted = (LearningActivityTryDeleted)session.get(LearningActivityTryDeletedImpl.class,
						Long.valueOf(latDelId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (learningActivityTryDeleted != null) {
					cacheResult(learningActivityTryDeleted);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LearningActivityTryDeletedModelImpl.ENTITY_CACHE_ENABLED,
						LearningActivityTryDeletedImpl.class, latDelId,
						_nullLearningActivityTryDeleted);
				}

				closeSession(session);
			}
		}

		return learningActivityTryDeleted;
	}

	/**
	 * Returns all the learning activity try deleteds where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity try deleteds where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activity try deleteds
	 * @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	 * @return the range of matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findByUuid(String uuid, int start,
		int end) throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity try deleteds where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of learning activity try deleteds
	 * @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<LearningActivityTryDeleted> list = (List<LearningActivityTryDeleted>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityTryDeleted learningActivityTryDeleted : list) {
				if (!Validator.equals(uuid, learningActivityTryDeleted.getUuid())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_LEARNINGACTIVITYTRYDELETED_WHERE);

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

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<LearningActivityTryDeleted>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first learning activity try deleted in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try deleted
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = fetchByUuid_First(uuid,
				orderByComparator);

		if (learningActivityTryDeleted != null) {
			return learningActivityTryDeleted;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryDeletedException(msg.toString());
	}

	/**
	 * Returns the first learning activity try deleted in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityTryDeleted> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity try deleted in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try deleted
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = fetchByUuid_Last(uuid,
				orderByComparator);

		if (learningActivityTryDeleted != null) {
			return learningActivityTryDeleted;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryDeletedException(msg.toString());
	}

	/**
	 * Returns the last learning activity try deleted in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<LearningActivityTryDeleted> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity try deleteds before and after the current learning activity try deleted in the ordered set where uuid = &#63;.
	 *
	 * @param latDelId the primary key of the current learning activity try deleted
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity try deleted
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted[] findByUuid_PrevAndNext(long latDelId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = findByPrimaryKey(latDelId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityTryDeleted[] array = new LearningActivityTryDeletedImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					learningActivityTryDeleted, uuid, orderByComparator, true);

			array[1] = learningActivityTryDeleted;

			array[2] = getByUuid_PrevAndNext(session,
					learningActivityTryDeleted, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LearningActivityTryDeleted getByUuid_PrevAndNext(
		Session session, LearningActivityTryDeleted learningActivityTryDeleted,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYTRYDELETED_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityTryDeleted);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityTryDeleted> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity try deleteds where actManAuditId = &#63;.
	 *
	 * @param actManAuditId the act man audit ID
	 * @return the matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findByactDel(long actManAuditId)
		throws SystemException {
		return findByactDel(actManAuditId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity try deleteds where actManAuditId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actManAuditId the act man audit ID
	 * @param start the lower bound of the range of learning activity try deleteds
	 * @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	 * @return the range of matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findByactDel(long actManAuditId,
		int start, int end) throws SystemException {
		return findByactDel(actManAuditId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity try deleteds where actManAuditId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param actManAuditId the act man audit ID
	 * @param start the lower bound of the range of learning activity try deleteds
	 * @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findByactDel(long actManAuditId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ACTDEL;
			finderArgs = new Object[] { actManAuditId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ACTDEL;
			finderArgs = new Object[] {
					actManAuditId,
					
					start, end, orderByComparator
				};
		}

		List<LearningActivityTryDeleted> list = (List<LearningActivityTryDeleted>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LearningActivityTryDeleted learningActivityTryDeleted : list) {
				if ((actManAuditId != learningActivityTryDeleted.getActManAuditId())) {
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
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_LEARNINGACTIVITYTRYDELETED_WHERE);

			query.append(_FINDER_COLUMN_ACTDEL_ACTMANAUDITID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actManAuditId);

				list = (List<LearningActivityTryDeleted>)QueryUtil.list(q,
						getDialect(), start, end);
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
	 * Returns the first learning activity try deleted in the ordered set where actManAuditId = &#63;.
	 *
	 * @param actManAuditId the act man audit ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try deleted
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted findByactDel_First(long actManAuditId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = fetchByactDel_First(actManAuditId,
				orderByComparator);

		if (learningActivityTryDeleted != null) {
			return learningActivityTryDeleted;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actManAuditId=");
		msg.append(actManAuditId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryDeletedException(msg.toString());
	}

	/**
	 * Returns the first learning activity try deleted in the ordered set where actManAuditId = &#63;.
	 *
	 * @param actManAuditId the act man audit ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted fetchByactDel_First(long actManAuditId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LearningActivityTryDeleted> list = findByactDel(actManAuditId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last learning activity try deleted in the ordered set where actManAuditId = &#63;.
	 *
	 * @param actManAuditId the act man audit ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try deleted
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted findByactDel_Last(long actManAuditId,
		OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = fetchByactDel_Last(actManAuditId,
				orderByComparator);

		if (learningActivityTryDeleted != null) {
			return learningActivityTryDeleted;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("actManAuditId=");
		msg.append(actManAuditId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLearningActivityTryDeletedException(msg.toString());
	}

	/**
	 * Returns the last learning activity try deleted in the ordered set where actManAuditId = &#63;.
	 *
	 * @param actManAuditId the act man audit ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching learning activity try deleted, or <code>null</code> if a matching learning activity try deleted could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted fetchByactDel_Last(long actManAuditId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByactDel(actManAuditId);

		List<LearningActivityTryDeleted> list = findByactDel(actManAuditId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the learning activity try deleteds before and after the current learning activity try deleted in the ordered set where actManAuditId = &#63;.
	 *
	 * @param latDelId the primary key of the current learning activity try deleted
	 * @param actManAuditId the act man audit ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next learning activity try deleted
	 * @throws com.liferay.lmssa.NoSuchLearningActivityTryDeletedException if a learning activity try deleted with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LearningActivityTryDeleted[] findByactDel_PrevAndNext(
		long latDelId, long actManAuditId, OrderByComparator orderByComparator)
		throws NoSuchLearningActivityTryDeletedException, SystemException {
		LearningActivityTryDeleted learningActivityTryDeleted = findByPrimaryKey(latDelId);

		Session session = null;

		try {
			session = openSession();

			LearningActivityTryDeleted[] array = new LearningActivityTryDeletedImpl[3];

			array[0] = getByactDel_PrevAndNext(session,
					learningActivityTryDeleted, actManAuditId,
					orderByComparator, true);

			array[1] = learningActivityTryDeleted;

			array[2] = getByactDel_PrevAndNext(session,
					learningActivityTryDeleted, actManAuditId,
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

	protected LearningActivityTryDeleted getByactDel_PrevAndNext(
		Session session, LearningActivityTryDeleted learningActivityTryDeleted,
		long actManAuditId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LEARNINGACTIVITYTRYDELETED_WHERE);

		query.append(_FINDER_COLUMN_ACTDEL_ACTMANAUDITID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(actManAuditId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(learningActivityTryDeleted);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LearningActivityTryDeleted> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the learning activity try deleteds.
	 *
	 * @return the learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the learning activity try deleteds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activity try deleteds
	 * @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	 * @return the range of learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the learning activity try deleteds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of learning activity try deleteds
	 * @param end the upper bound of the range of learning activity try deleteds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public List<LearningActivityTryDeleted> findAll(int start, int end,
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

		List<LearningActivityTryDeleted> list = (List<LearningActivityTryDeleted>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LEARNINGACTIVITYTRYDELETED);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LEARNINGACTIVITYTRYDELETED;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LearningActivityTryDeleted>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LearningActivityTryDeleted>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the learning activity try deleteds where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (LearningActivityTryDeleted learningActivityTryDeleted : findByUuid(
				uuid)) {
			remove(learningActivityTryDeleted);
		}
	}

	/**
	 * Removes all the learning activity try deleteds where actManAuditId = &#63; from the database.
	 *
	 * @param actManAuditId the act man audit ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByactDel(long actManAuditId) throws SystemException {
		for (LearningActivityTryDeleted learningActivityTryDeleted : findByactDel(
				actManAuditId)) {
			remove(learningActivityTryDeleted);
		}
	}

	/**
	 * Removes all the learning activity try deleteds from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LearningActivityTryDeleted learningActivityTryDeleted : findAll()) {
			remove(learningActivityTryDeleted);
		}
	}

	/**
	 * Returns the number of learning activity try deleteds where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITYTRYDELETED_WHERE);

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
	 * Returns the number of learning activity try deleteds where actManAuditId = &#63;.
	 *
	 * @param actManAuditId the act man audit ID
	 * @return the number of matching learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public int countByactDel(long actManAuditId) throws SystemException {
		Object[] finderArgs = new Object[] { actManAuditId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTDEL,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LEARNINGACTIVITYTRYDELETED_WHERE);

			query.append(_FINDER_COLUMN_ACTDEL_ACTMANAUDITID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actManAuditId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTDEL,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of learning activity try deleteds.
	 *
	 * @return the number of learning activity try deleteds
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LEARNINGACTIVITYTRYDELETED);

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
	 * Initializes the learning activity try deleted persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lmssa.model.LearningActivityTryDeleted")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LearningActivityTryDeleted>> listenersList = new ArrayList<ModelListener<LearningActivityTryDeleted>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LearningActivityTryDeleted>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LearningActivityTryDeletedImpl.class.getName());
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
	private static final String _SQL_SELECT_LEARNINGACTIVITYTRYDELETED = "SELECT learningActivityTryDeleted FROM LearningActivityTryDeleted learningActivityTryDeleted";
	private static final String _SQL_SELECT_LEARNINGACTIVITYTRYDELETED_WHERE = "SELECT learningActivityTryDeleted FROM LearningActivityTryDeleted learningActivityTryDeleted WHERE ";
	private static final String _SQL_COUNT_LEARNINGACTIVITYTRYDELETED = "SELECT COUNT(learningActivityTryDeleted) FROM LearningActivityTryDeleted learningActivityTryDeleted";
	private static final String _SQL_COUNT_LEARNINGACTIVITYTRYDELETED_WHERE = "SELECT COUNT(learningActivityTryDeleted) FROM LearningActivityTryDeleted learningActivityTryDeleted WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "learningActivityTryDeleted.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "learningActivityTryDeleted.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(learningActivityTryDeleted.uuid IS NULL OR learningActivityTryDeleted.uuid = ?)";
	private static final String _FINDER_COLUMN_ACTDEL_ACTMANAUDITID_2 = "learningActivityTryDeleted.actManAuditId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "learningActivityTryDeleted.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LearningActivityTryDeleted exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LearningActivityTryDeleted exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LearningActivityTryDeletedPersistenceImpl.class);
	private static LearningActivityTryDeleted _nullLearningActivityTryDeleted = new LearningActivityTryDeletedImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LearningActivityTryDeleted> toCacheModel() {
				return _nullLearningActivityTryDeletedCacheModel;
			}
		};

	private static CacheModel<LearningActivityTryDeleted> _nullLearningActivityTryDeletedCacheModel =
		new CacheModel<LearningActivityTryDeleted>() {
			public LearningActivityTryDeleted toEntityModel() {
				return _nullLearningActivityTryDeleted;
			}
		};
}