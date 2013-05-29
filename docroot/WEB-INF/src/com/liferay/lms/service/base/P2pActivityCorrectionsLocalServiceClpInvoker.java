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

import com.liferay.lms.service.P2pActivityCorrectionsLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class P2pActivityCorrectionsLocalServiceClpInvoker {
	public P2pActivityCorrectionsLocalServiceClpInvoker() {
		_methodName0 = "addP2pActivityCorrections";

		_methodParameterTypes0 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
			};

		_methodName1 = "createP2pActivityCorrections";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteP2pActivityCorrections";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteP2pActivityCorrections";

		_methodParameterTypes3 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
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

		_methodName9 = "fetchP2pActivityCorrections";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getP2pActivityCorrections";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getP2pActivityCorrectionses";

		_methodParameterTypes12 = new String[] { "int", "int" };

		_methodName13 = "getP2pActivityCorrectionsesCount";

		_methodParameterTypes13 = new String[] {  };

		_methodName14 = "updateP2pActivityCorrections";

		_methodParameterTypes14 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
			};

		_methodName15 = "updateP2pActivityCorrections";

		_methodParameterTypes15 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections", "boolean"
			};

		_methodName112 = "getBeanIdentifier";

		_methodParameterTypes112 = new String[] {  };

		_methodName113 = "setBeanIdentifier";

		_methodParameterTypes113 = new String[] { "java.lang.String" };

		_methodName118 = "findByP2pActivityIdAndUserId";

		_methodParameterTypes118 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName119 = "exitsCorP2p";

		_methodParameterTypes119 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName120 = "findByP2pActivityId";

		_methodParameterTypes120 = new String[] { "java.lang.Long" };

		_methodName121 = "findByActIdIdAndUserId";

		_methodParameterTypes121 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName122 = "findByActIdAndUserIdOrderByDate";

		_methodParameterTypes122 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName123 = "addorUpdateP2pActivityCorrections";

		_methodParameterTypes123 = new String[] {
				"com.liferay.lms.model.P2pActivityCorrections"
			};

		_methodName124 = "asignP2PCorrectionsToUsers";

		_methodParameterTypes124 = new String[] { "long", "long", "java.util.List" };

		_methodName125 = "asignCorrectionsToP2PActivities";

		_methodParameterTypes125 = new String[] {
				"long", "long", "int", "java.util.List", "long"
			};

		_methodName126 = "getNumCorrectionsAsignToP2P";

		_methodParameterTypes126 = new String[] { "long" };

		_methodName127 = "getNumCorrectionsAsignToUser";

		_methodParameterTypes127 = new String[] { "long", "long" };

		_methodName128 = "getCorrectionsDoneByUserInP2PActivity";

		_methodParameterTypes128 = new String[] { "long", "long" };

		_methodName129 = "areAllCorrectionsDoneByUserInP2PActivity";

		_methodParameterTypes129 = new String[] { "long", "long" };

		_methodName130 = "getCorrectionByP2PActivity";

		_methodParameterTypes130 = new String[] { "long" };

		_methodName131 = "getAVGCorrectionsResults";

		_methodParameterTypes131 = new String[] { "long" };

		_methodName132 = "isP2PAsignationDone";

		_methodParameterTypes132 = new String[] { "long", "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.addP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.createP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.deleteP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.fetchP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getP2pActivityCorrections(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getP2pActivityCorrectionses(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getP2pActivityCorrectionsesCount();
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.updateP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.updateP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName112.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes112, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName113.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes113, parameterTypes)) {
			P2pActivityCorrectionsLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);
		}

		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityIdAndUserId((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName119.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes119, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.exitsCorP2p((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName120.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes120, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByP2pActivityId((java.lang.Long)arguments[0]);
		}

		if (_methodName121.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes121, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByActIdIdAndUserId((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName122.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes122, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.findByActIdAndUserIdOrderByDate((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName123.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes123, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.addorUpdateP2pActivityCorrections((com.liferay.lms.model.P2pActivityCorrections)arguments[0]);
		}

		if (_methodName124.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes124, parameterTypes)) {
			P2pActivityCorrectionsLocalServiceUtil.asignP2PCorrectionsToUsers(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(java.util.List<com.liferay.portal.model.User>)arguments[2]);
		}

		if (_methodName125.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes125, parameterTypes)) {
			P2pActivityCorrectionsLocalServiceUtil.asignCorrectionsToP2PActivities(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				(java.util.List<com.liferay.lms.model.P2pActivity>)arguments[3],
				((Long)arguments[4]).longValue());
		}

		if (_methodName126.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes126, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToP2P(((Long)arguments[0]).longValue());
		}

		if (_methodName127.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes127, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getNumCorrectionsAsignToUser(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName128.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes128, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getCorrectionsDoneByUserInP2PActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName129.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes129, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.areAllCorrectionsDoneByUserInP2PActivity(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName130.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes130, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getCorrectionByP2PActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName131.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes131, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.getAVGCorrectionsResults(((Long)arguments[0]).longValue());
		}

		if (_methodName132.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes132, parameterTypes)) {
			return P2pActivityCorrectionsLocalServiceUtil.isP2PAsignationDone(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
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
	private String _methodName112;
	private String[] _methodParameterTypes112;
	private String _methodName113;
	private String[] _methodParameterTypes113;
	private String _methodName118;
	private String[] _methodParameterTypes118;
	private String _methodName119;
	private String[] _methodParameterTypes119;
	private String _methodName120;
	private String[] _methodParameterTypes120;
	private String _methodName121;
	private String[] _methodParameterTypes121;
	private String _methodName122;
	private String[] _methodParameterTypes122;
	private String _methodName123;
	private String[] _methodParameterTypes123;
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
	private String _methodName130;
	private String[] _methodParameterTypes130;
	private String _methodName131;
	private String[] _methodParameterTypes131;
	private String _methodName132;
	private String[] _methodParameterTypes132;
}