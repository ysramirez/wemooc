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

package com.liferay.lms.service.base;

import com.liferay.lms.service.P2pActivityLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class P2pActivityLocalServiceClpInvoker {
	public P2pActivityLocalServiceClpInvoker() {
		_methodName0 = "addP2pActivity";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.P2pActivity"
			};

		_methodName1 = "createP2pActivity";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteP2pActivity";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteP2pActivity";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.P2pActivity"
			};

		_methodName4 = "dynamicQuery";

		_methodParameterTypes4 = new String[] {  };

		_methodName5 = "dynamicQuery";

		_methodParameterTypes5 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName6 = "dynamicQuery";

		_methodParameterTypes6 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int"
			};

		_methodName7 = "dynamicQuery";

		_methodParameterTypes7 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName8 = "dynamicQueryCount";

		_methodParameterTypes8 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName9 = "fetchP2pActivity";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getP2pActivity";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getP2pActivities";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getP2pActivitiesCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateP2pActivity";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.P2pActivity"
			};

		_methodName15 = "updateP2pActivity";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.P2pActivity", "boolean"
			};

		_methodName118 = "getBeanIdentifier";

		_methodParameterTypes118 = new String[] {  };

		_methodName119 = "setBeanIdentifier";

		_methodParameterTypes119 = new String[] { "java.lang.String" };

		_methodName124 = "findByActIdAndUserId";

		_methodParameterTypes124 = new String[] { "long", "long" };

		_methodName125 = "existP2pAct";

		_methodParameterTypes125 = new String[] { "long", "long" };

		_methodName126 = "findByActId";

		_methodParameterTypes126 = new String[] { "long" };

		_methodName127 = "findByActIdOrderByP2pId";

		_methodParameterTypes127 = new String[] { "long" };

		_methodName128 = "addP2pActivity";

		_methodParameterTypes128 = new String[] {
				"com.liferay.lms.model.P2pActivity"
			};

		_methodName129 = "updateP2pActivity";

		_methodParameterTypes129 = new String[] {
				"com.liferay.lms.model.P2pActivity"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return P2pActivityLocalServiceUtil.addP2pActivity((com.liferay.lms.model.P2pActivity)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return P2pActivityLocalServiceUtil.createP2pActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return P2pActivityLocalServiceUtil.deleteP2pActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return P2pActivityLocalServiceUtil.deleteP2pActivity((com.liferay.lms.model.P2pActivity)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return P2pActivityLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return P2pActivityLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return P2pActivityLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return P2pActivityLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return P2pActivityLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return P2pActivityLocalServiceUtil.fetchP2pActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return P2pActivityLocalServiceUtil.getP2pActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return P2pActivityLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return P2pActivityLocalServiceUtil.getP2pActivities(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return P2pActivityLocalServiceUtil.getP2pActivitiesCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return P2pActivityLocalServiceUtil.updateP2pActivity((com.liferay.lms.model.P2pActivity)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return P2pActivityLocalServiceUtil.updateP2pActivity((com.liferay.lms.model.P2pActivity)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return P2pActivityLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName119.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes119, parameterTypes)) {
			P2pActivityLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName124.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes124, parameterTypes)) {
			return P2pActivityLocalServiceUtil.findByActIdAndUserId(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName125.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes125, parameterTypes)) {
			return P2pActivityLocalServiceUtil.existP2pAct(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName126.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes126, parameterTypes)) {
			return P2pActivityLocalServiceUtil.findByActId(((Long)arguments[0]).longValue());
		}

		if (_methodName127.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes127, parameterTypes)) {
			return P2pActivityLocalServiceUtil.findByActIdOrderByP2pId(((Long)arguments[0]).longValue());
		}

		if (_methodName128.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes128, parameterTypes)) {
			return P2pActivityLocalServiceUtil.addP2pActivity((com.liferay.lms.model.P2pActivity)arguments[0]);
		}

		if (_methodName129.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes129, parameterTypes)) {
			return P2pActivityLocalServiceUtil.updateP2pActivity((com.liferay.lms.model.P2pActivity)arguments[0]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
	private String _methodName14;
	private String[] _methodParameterTypes14;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName118;
	private String[] _methodParameterTypes118;
	private String _methodName119;
	private String[] _methodParameterTypes119;
	private String _methodName124;
	private String[] _methodParameterTypes124;
	private String _methodName125;
	private String[] _methodParameterTypes125;
	private String _methodName126;
	private String[] _methodParameterTypes126;
	private String _methodName127;
	private String[] _methodParameterTypes127;
	private String _methodName128;
	private String[] _methodParameterTypes128;
	private String _methodName129;
	private String[] _methodParameterTypes129;
}