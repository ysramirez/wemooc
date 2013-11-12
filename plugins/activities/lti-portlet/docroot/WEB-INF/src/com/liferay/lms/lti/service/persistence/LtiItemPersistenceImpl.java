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

package com.liferay.lms.lti.service.persistence;

import com.liferay.lms.lti.NoSuchLtiItemException;
import com.liferay.lms.lti.model.LtiItem;
import com.liferay.lms.lti.model.impl.LtiItemImpl;
import com.liferay.lms.lti.model.impl.LtiItemModelImpl;

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
 * The persistence implementation for the lti item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Diego Renedo Delgado
 * @see LtiItemPersistence
 * @see LtiItemUtil
 * @generated
 */
public class LtiItemPersistenceImpl extends BasePersistenceImpl<LtiItem>
	implements LtiItemPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LtiItemUtil} to access the lti item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LtiItemImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, LtiItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, LtiItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			LtiItemModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, LtiItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			LtiItemModelImpl.UUID_COLUMN_BITMASK |
			LtiItemModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_ACTID = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, LtiItemImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByactId",
			new String[] { Long.class.getName() },
			LtiItemModelImpl.ACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ACTID = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByactId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, LtiItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, LtiItemImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the lti item in the entity cache if it is enabled.
	 *
	 * @param ltiItem the lti item
	 */
	public void cacheResult(LtiItem ltiItem) {
		EntityCacheUtil.putResult(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemImpl.class, ltiItem.getPrimaryKey(), ltiItem);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { ltiItem.getUuid(), Long.valueOf(ltiItem.getGroupId()) },
			ltiItem);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
			new Object[] { Long.valueOf(ltiItem.getActId()) }, ltiItem);

		ltiItem.resetOriginalValues();
	}

	/**
	 * Caches the lti items in the entity cache if it is enabled.
	 *
	 * @param ltiItems the lti items
	 */
	public void cacheResult(List<LtiItem> ltiItems) {
		for (LtiItem ltiItem : ltiItems) {
			if (EntityCacheUtil.getResult(
						LtiItemModelImpl.ENTITY_CACHE_ENABLED,
						LtiItemImpl.class, ltiItem.getPrimaryKey()) == null) {
				cacheResult(ltiItem);
			}
			else {
				ltiItem.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lti items.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LtiItemImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LtiItemImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lti item.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LtiItem ltiItem) {
		EntityCacheUtil.removeResult(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemImpl.class, ltiItem.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ltiItem);
	}

	@Override
	public void clearCache(List<LtiItem> ltiItems) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LtiItem ltiItem : ltiItems) {
			EntityCacheUtil.removeResult(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
				LtiItemImpl.class, ltiItem.getPrimaryKey());

			clearUniqueFindersCache(ltiItem);
		}
	}

	protected void clearUniqueFindersCache(LtiItem ltiItem) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { ltiItem.getUuid(), Long.valueOf(ltiItem.getGroupId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACTID,
			new Object[] { Long.valueOf(ltiItem.getActId()) });
	}

	/**
	 * Creates a new lti item with the primary key. Does not add the lti item to the database.
	 *
	 * @param ltiItemId the primary key for the new lti item
	 * @return the new lti item
	 */
	public LtiItem create(long ltiItemId) {
		LtiItem ltiItem = new LtiItemImpl();

		ltiItem.setNew(true);
		ltiItem.setPrimaryKey(ltiItemId);

		String uuid = PortalUUIDUtil.generate();

		ltiItem.setUuid(uuid);

		return ltiItem;
	}

	/**
	 * Removes the lti item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ltiItemId the primary key of the lti item
	 * @return the lti item that was removed
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem remove(long ltiItemId)
		throws NoSuchLtiItemException, SystemException {
		return remove(Long.valueOf(ltiItemId));
	}

	/**
	 * Removes the lti item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lti item
	 * @return the lti item that was removed
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LtiItem remove(Serializable primaryKey)
		throws NoSuchLtiItemException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LtiItem ltiItem = (LtiItem)session.get(LtiItemImpl.class, primaryKey);

			if (ltiItem == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLtiItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ltiItem);
		}
		catch (NoSuchLtiItemException nsee) {
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
	protected LtiItem removeImpl(LtiItem ltiItem) throws SystemException {
		ltiItem = toUnwrappedModel(ltiItem);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ltiItem);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		clearCache(ltiItem);

		return ltiItem;
	}

	@Override
	public LtiItem updateImpl(com.liferay.lms.lti.model.LtiItem ltiItem,
		boolean merge) throws SystemException {
		ltiItem = toUnwrappedModel(ltiItem);

		boolean isNew = ltiItem.isNew();

		LtiItemModelImpl ltiItemModelImpl = (LtiItemModelImpl)ltiItem;

		if (Validator.isNull(ltiItem.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ltiItem.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ltiItem, merge);

			ltiItem.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !LtiItemModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ltiItemModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { ltiItemModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ltiItemModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}
		}

		EntityCacheUtil.putResult(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
			LtiItemImpl.class, ltiItem.getPrimaryKey(), ltiItem);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					ltiItem.getUuid(), Long.valueOf(ltiItem.getGroupId())
				}, ltiItem);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
				new Object[] { Long.valueOf(ltiItem.getActId()) }, ltiItem);
		}
		else {
			if ((ltiItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ltiItemModelImpl.getOriginalUuid(),
						Long.valueOf(ltiItemModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						ltiItem.getUuid(), Long.valueOf(ltiItem.getGroupId())
					}, ltiItem);
			}

			if ((ltiItemModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_ACTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(ltiItemModelImpl.getOriginalActId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ACTID, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACTID, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
					new Object[] { Long.valueOf(ltiItem.getActId()) }, ltiItem);
			}
		}

		return ltiItem;
	}

	protected LtiItem toUnwrappedModel(LtiItem ltiItem) {
		if (ltiItem instanceof LtiItemImpl) {
			return ltiItem;
		}

		LtiItemImpl ltiItemImpl = new LtiItemImpl();

		ltiItemImpl.setNew(ltiItem.isNew());
		ltiItemImpl.setPrimaryKey(ltiItem.getPrimaryKey());

		ltiItemImpl.setUuid(ltiItem.getUuid());
		ltiItemImpl.setLtiItemId(ltiItem.getLtiItemId());
		ltiItemImpl.setActId(ltiItem.getActId());
		ltiItemImpl.setCompanyId(ltiItem.getCompanyId());
		ltiItemImpl.setGroupId(ltiItem.getGroupId());
		ltiItemImpl.setCreateDate(ltiItem.getCreateDate());
		ltiItemImpl.setUserId(ltiItem.getUserId());
		ltiItemImpl.setName(ltiItem.getName());
		ltiItemImpl.setDescription(ltiItem.getDescription());
		ltiItemImpl.setUrl(ltiItem.getUrl());
		ltiItemImpl.setSecret(ltiItem.getSecret());
		ltiItemImpl.setRol(ltiItem.getRol());
		ltiItemImpl.setContenType(ltiItem.getContenType());
		ltiItemImpl.setIframe(ltiItem.isIframe());
		ltiItemImpl.setNote(ltiItem.getNote());

		return ltiItemImpl;
	}

	/**
	 * Returns the lti item with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the lti item
	 * @return the lti item
	 * @throws com.liferay.portal.NoSuchModelException if a lti item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LtiItem findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the lti item with the primary key or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	 *
	 * @param ltiItemId the primary key of the lti item
	 * @return the lti item
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem findByPrimaryKey(long ltiItemId)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = fetchByPrimaryKey(ltiItemId);

		if (ltiItem == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + ltiItemId);
			}

			throw new NoSuchLtiItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				ltiItemId);
		}

		return ltiItem;
	}

	/**
	 * Returns the lti item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lti item
	 * @return the lti item, or <code>null</code> if a lti item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LtiItem fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the lti item with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ltiItemId the primary key of the lti item
	 * @return the lti item, or <code>null</code> if a lti item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem fetchByPrimaryKey(long ltiItemId) throws SystemException {
		LtiItem ltiItem = (LtiItem)EntityCacheUtil.getResult(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
				LtiItemImpl.class, ltiItemId);

		if (ltiItem == _nullLtiItem) {
			return null;
		}

		if (ltiItem == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ltiItem = (LtiItem)session.get(LtiItemImpl.class,
						Long.valueOf(ltiItemId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ltiItem != null) {
					cacheResult(ltiItem);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(LtiItemModelImpl.ENTITY_CACHE_ENABLED,
						LtiItemImpl.class, ltiItemId, _nullLtiItem);
				}

				closeSession(session);
			}
		}

		return ltiItem;
	}

	/**
	 * Returns all the lti items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lti items
	 * @throws SystemException if a system exception occurred
	 */
	public List<LtiItem> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lti items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lti items
	 * @param end the upper bound of the range of lti items (not inclusive)
	 * @return the range of matching lti items
	 * @throws SystemException if a system exception occurred
	 */
	public List<LtiItem> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lti items where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lti items
	 * @param end the upper bound of the range of lti items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lti items
	 * @throws SystemException if a system exception occurred
	 */
	public List<LtiItem> findByUuid(String uuid, int start, int end,
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

		List<LtiItem> list = (List<LtiItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LtiItem ltiItem : list) {
				if (!Validator.equals(uuid, ltiItem.getUuid())) {
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

			query.append(_SQL_SELECT_LTIITEM_WHERE);

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

				list = (List<LtiItem>)QueryUtil.list(q, getDialect(), start, end);
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
	 * Returns the first lti item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lti item
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = fetchByUuid_First(uuid, orderByComparator);

		if (ltiItem != null) {
			return ltiItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLtiItemException(msg.toString());
	}

	/**
	 * Returns the first lti item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lti item, or <code>null</code> if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<LtiItem> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lti item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lti item
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = fetchByUuid_Last(uuid, orderByComparator);

		if (ltiItem != null) {
			return ltiItem;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchLtiItemException(msg.toString());
	}

	/**
	 * Returns the last lti item in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lti item, or <code>null</code> if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<LtiItem> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lti items before and after the current lti item in the ordered set where uuid = &#63;.
	 *
	 * @param ltiItemId the primary key of the current lti item
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lti item
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a lti item with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem[] findByUuid_PrevAndNext(long ltiItemId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = findByPrimaryKey(ltiItemId);

		Session session = null;

		try {
			session = openSession();

			LtiItem[] array = new LtiItemImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ltiItem, uuid,
					orderByComparator, true);

			array[1] = ltiItem;

			array[2] = getByUuid_PrevAndNext(session, ltiItem, uuid,
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

	protected LtiItem getByUuid_PrevAndNext(Session session, LtiItem ltiItem,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LTIITEM_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(ltiItem);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LtiItem> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the lti item where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lti item
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem findByUUID_G(String uuid, long groupId)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = fetchByUUID_G(uuid, groupId);

		if (ltiItem == null) {
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

			throw new NoSuchLtiItemException(msg.toString());
		}

		return ltiItem;
	}

	/**
	 * Returns the lti item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the lti item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof LtiItem) {
			LtiItem ltiItem = (LtiItem)result;

			if (!Validator.equals(uuid, ltiItem.getUuid()) ||
					(groupId != ltiItem.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_LTIITEM_WHERE);

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

				List<LtiItem> list = q.list();

				result = list;

				LtiItem ltiItem = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					ltiItem = list.get(0);

					cacheResult(ltiItem);

					if ((ltiItem.getUuid() == null) ||
							!ltiItem.getUuid().equals(uuid) ||
							(ltiItem.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ltiItem);
					}
				}

				return ltiItem;
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
				return (LtiItem)result;
			}
		}
	}

	/**
	 * Returns the lti item where actId = &#63; or throws a {@link com.liferay.lms.lti.NoSuchLtiItemException} if it could not be found.
	 *
	 * @param actId the act ID
	 * @return the matching lti item
	 * @throws com.liferay.lms.lti.NoSuchLtiItemException if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem findByactId(long actId)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = fetchByactId(actId);

		if (ltiItem == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("actId=");
			msg.append(actId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLtiItemException(msg.toString());
		}

		return ltiItem;
	}

	/**
	 * Returns the lti item where actId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param actId the act ID
	 * @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem fetchByactId(long actId) throws SystemException {
		return fetchByactId(actId, true);
	}

	/**
	 * Returns the lti item where actId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param actId the act ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching lti item, or <code>null</code> if a matching lti item could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem fetchByactId(long actId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_ACTID,
					finderArgs, this);
		}

		if (result instanceof LtiItem) {
			LtiItem ltiItem = (LtiItem)result;

			if ((actId != ltiItem.getActId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_LTIITEM_WHERE);

			query.append(_FINDER_COLUMN_ACTID_ACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				List<LtiItem> list = q.list();

				result = list;

				LtiItem ltiItem = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
						finderArgs, list);
				}
				else {
					ltiItem = list.get(0);

					cacheResult(ltiItem);

					if ((ltiItem.getActId() != actId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_ACTID,
							finderArgs, ltiItem);
					}
				}

				return ltiItem;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_ACTID,
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
				return (LtiItem)result;
			}
		}
	}

	/**
	 * Returns all the lti items.
	 *
	 * @return the lti items
	 * @throws SystemException if a system exception occurred
	 */
	public List<LtiItem> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lti items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of lti items
	 * @param end the upper bound of the range of lti items (not inclusive)
	 * @return the range of lti items
	 * @throws SystemException if a system exception occurred
	 */
	public List<LtiItem> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lti items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of lti items
	 * @param end the upper bound of the range of lti items (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lti items
	 * @throws SystemException if a system exception occurred
	 */
	public List<LtiItem> findAll(int start, int end,
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

		List<LtiItem> list = (List<LtiItem>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LTIITEM);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LTIITEM;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LtiItem>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LtiItem>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the lti items where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (LtiItem ltiItem : findByUuid(uuid)) {
			remove(ltiItem);
		}
	}

	/**
	 * Removes the lti item where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the lti item that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem removeByUUID_G(String uuid, long groupId)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = findByUUID_G(uuid, groupId);

		return remove(ltiItem);
	}

	/**
	 * Removes the lti item where actId = &#63; from the database.
	 *
	 * @param actId the act ID
	 * @return the lti item that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public LtiItem removeByactId(long actId)
		throws NoSuchLtiItemException, SystemException {
		LtiItem ltiItem = findByactId(actId);

		return remove(ltiItem);
	}

	/**
	 * Removes all the lti items from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LtiItem ltiItem : findAll()) {
			remove(ltiItem);
		}
	}

	/**
	 * Returns the number of lti items where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lti items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LTIITEM_WHERE);

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
	 * Returns the number of lti items where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lti items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LTIITEM_WHERE);

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
	 * Returns the number of lti items where actId = &#63;.
	 *
	 * @param actId the act ID
	 * @return the number of matching lti items
	 * @throws SystemException if a system exception occurred
	 */
	public int countByactId(long actId) throws SystemException {
		Object[] finderArgs = new Object[] { actId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ACTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LTIITEM_WHERE);

			query.append(_FINDER_COLUMN_ACTID_ACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(actId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ACTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of lti items.
	 *
	 * @return the number of lti items
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LTIITEM);

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
	 * Initializes the lti item persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.lms.lti.model.LtiItem")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LtiItem>> listenersList = new ArrayList<ModelListener<LtiItem>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LtiItem>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LtiItemImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = LtiItemPersistence.class)
	protected LtiItemPersistence ltiItemPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_LTIITEM = "SELECT ltiItem FROM LtiItem ltiItem";
	private static final String _SQL_SELECT_LTIITEM_WHERE = "SELECT ltiItem FROM LtiItem ltiItem WHERE ";
	private static final String _SQL_COUNT_LTIITEM = "SELECT COUNT(ltiItem) FROM LtiItem ltiItem";
	private static final String _SQL_COUNT_LTIITEM_WHERE = "SELECT COUNT(ltiItem) FROM LtiItem ltiItem WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ltiItem.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ltiItem.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ltiItem.uuid IS NULL OR ltiItem.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ltiItem.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ltiItem.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ltiItem.uuid IS NULL OR ltiItem.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ltiItem.groupId = ?";
	private static final String _FINDER_COLUMN_ACTID_ACTID_2 = "ltiItem.actId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ltiItem.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LtiItem exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LtiItem exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LtiItemPersistenceImpl.class);
	private static LtiItem _nullLtiItem = new LtiItemImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LtiItem> toCacheModel() {
				return _nullLtiItemCacheModel;
			}
		};

	private static CacheModel<LtiItem> _nullLtiItemCacheModel = new CacheModel<LtiItem>() {
			public LtiItem toEntityModel() {
				return _nullLtiItem;
			}
		};
}