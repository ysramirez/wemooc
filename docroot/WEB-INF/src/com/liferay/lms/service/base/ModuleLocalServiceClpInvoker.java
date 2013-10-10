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

import com.liferay.lms.service.ModuleLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class ModuleLocalServiceClpInvoker {
	public ModuleLocalServiceClpInvoker() {
		_methodName0 = "addModule";

		_methodParameterTypes0 = new String[] { "com.liferay.lms.model.Module" };

		_methodName1 = "createModule";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteModule";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteModule";

		_methodParameterTypes3 = new String[] { "com.liferay.lms.model.Module" };

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

		_methodName9 = "fetchModule";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getModule";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getModuleByUuidAndGroupId";

		_methodParameterTypes12 = new String[] { "java.lang.String", "long" };

		_methodName13 = "getModules";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getModulesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateModule";

		_methodParameterTypes15 = new String[] { "com.liferay.lms.model.Module" };

		_methodName16 = "updateModule";

		_methodParameterTypes16 = new String[] {
				"com.liferay.lms.model.Module", "boolean"
			};

		_methodName117 = "getBeanIdentifier";

		_methodParameterTypes117 = new String[] {  };

		_methodName118 = "setBeanIdentifier";

		_methodParameterTypes118 = new String[] { "java.lang.String" };

		_methodName123 = "findAllInUser";

		_methodParameterTypes123 = new String[] { "long" };

		_methodName124 = "findAllInUser";

		_methodParameterTypes124 = new String[] {
				"long", "com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName125 = "findAllInGroup";

		_methodParameterTypes125 = new String[] { "long" };

		_methodName126 = "findAllInGroup";

		_methodParameterTypes126 = new String[] {
				"long", "com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName127 = "findAllInUserAndGroup";

		_methodParameterTypes127 = new String[] { "long", "long" };

		_methodName128 = "findAllInUserAndGroup";

		_methodParameterTypes128 = new String[] {
				"long", "long",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName129 = "getPreviusModule";

		_methodParameterTypes129 = new String[] { "long" };

		_methodName130 = "getPreviusModule";

		_methodParameterTypes130 = new String[] { "com.liferay.lms.model.Module" };

		_methodName131 = "getNextModule";

		_methodParameterTypes131 = new String[] { "long" };

		_methodName132 = "getNextModule";

		_methodParameterTypes132 = new String[] { "com.liferay.lms.model.Module" };

		_methodName133 = "goUpModule";

		_methodParameterTypes133 = new String[] { "long" };

		_methodName134 = "goDownModule";

		_methodParameterTypes134 = new String[] { "long" };

		_methodName135 = "addmodule";

		_methodParameterTypes135 = new String[] { "com.liferay.lms.model.Module" };

		_methodName136 = "addModule";

		_methodParameterTypes136 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.Long",
				"java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.lang.Long"
			};

		_methodName137 = "remove";

		_methodParameterTypes137 = new String[] { "com.liferay.lms.model.Module" };

		_methodName138 = "updateModule";

		_methodParameterTypes138 = new String[] { "com.liferay.lms.model.Module" };

		_methodName139 = "updateModule";

		_methodParameterTypes139 = new String[] {
				"com.liferay.lms.model.Module", "boolean"
			};

		_methodName140 = "isUserPassed";

		_methodParameterTypes140 = new String[] { "long", "long" };

		_methodName141 = "isLocked";

		_methodParameterTypes141 = new String[] { "long", "long" };

		_methodName142 = "countByGroupId";

		_methodParameterTypes142 = new String[] { "long" };

		_methodName143 = "usersStarted";

		_methodParameterTypes143 = new String[] { "long" };

		_methodName144 = "modulesUserPassed";

		_methodParameterTypes144 = new String[] { "long", "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return ModuleLocalServiceUtil.addModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return ModuleLocalServiceUtil.createModule(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return ModuleLocalServiceUtil.deleteModule(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return ModuleLocalServiceUtil.deleteModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return ModuleLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return ModuleLocalServiceUtil.fetchModule(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return ModuleLocalServiceUtil.getModule(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return ModuleLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return ModuleLocalServiceUtil.getModuleByUuidAndGroupId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return ModuleLocalServiceUtil.getModules(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return ModuleLocalServiceUtil.getModulesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName117.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes117, parameterTypes)) {
			return ModuleLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			ModuleLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName123.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes123, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUser(((Long)arguments[0]).longValue());
		}

		if (_methodName124.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes124, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUser(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[1]);
		}

		if (_methodName125.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes125, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName126.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes126, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInGroup(((Long)arguments[0]).longValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[1]);
		}

		if (_methodName127.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes127, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUserAndGroup(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName128.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes128, parameterTypes)) {
			return ModuleLocalServiceUtil.findAllInUserAndGroup(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[2]);
		}

		if (_methodName129.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes129, parameterTypes)) {
			return ModuleLocalServiceUtil.getPreviusModule(((Long)arguments[0]).longValue());
		}

		if (_methodName130.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes130, parameterTypes)) {
			return ModuleLocalServiceUtil.getPreviusModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName131.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes131, parameterTypes)) {
			return ModuleLocalServiceUtil.getNextModule(((Long)arguments[0]).longValue());
		}

		if (_methodName132.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes132, parameterTypes)) {
			return ModuleLocalServiceUtil.getNextModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName133.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes133, parameterTypes)) {
			ModuleLocalServiceUtil.goUpModule(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName134.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes134, parameterTypes)) {
			ModuleLocalServiceUtil.goDownModule(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			return ModuleLocalServiceUtil.addmodule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName136.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes136, parameterTypes)) {
			return ModuleLocalServiceUtil.addModule((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1], (java.lang.Long)arguments[2],
				(java.lang.String)arguments[3], (java.lang.String)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(java.lang.Long)arguments[7]);
		}

		if (_methodName137.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes137, parameterTypes)) {
			ModuleLocalServiceUtil.remove((com.liferay.lms.model.Module)arguments[0]);

			return null;
		}

		if (_methodName138.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes138, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0]);
		}

		if (_methodName139.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes139, parameterTypes)) {
			return ModuleLocalServiceUtil.updateModule((com.liferay.lms.model.Module)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return ModuleLocalServiceUtil.isUserPassed(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return ModuleLocalServiceUtil.isLocked(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return ModuleLocalServiceUtil.countByGroupId(((Long)arguments[0]).longValue());
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return ModuleLocalServiceUtil.usersStarted(((Long)arguments[0]).longValue());
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			return ModuleLocalServiceUtil.modulesUserPassed(((Long)arguments[0]).longValue(),
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
	private String _methodName16;
	private String[] _methodParameterTypes16;
	private String _methodName117;
	private String[] _methodParameterTypes117;
	private String _methodName118;
	private String[] _methodParameterTypes118;
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
	private String _methodName133;
	private String[] _methodParameterTypes133;
	private String _methodName134;
	private String[] _methodParameterTypes134;
	private String _methodName135;
	private String[] _methodParameterTypes135;
	private String _methodName136;
	private String[] _methodParameterTypes136;
	private String _methodName137;
	private String[] _methodParameterTypes137;
	private String _methodName138;
	private String[] _methodParameterTypes138;
	private String _methodName139;
	private String[] _methodParameterTypes139;
	private String _methodName140;
	private String[] _methodParameterTypes140;
	private String _methodName141;
	private String[] _methodParameterTypes141;
	private String _methodName142;
	private String[] _methodParameterTypes142;
	private String _methodName143;
	private String[] _methodParameterTypes143;
	private String _methodName144;
	private String[] _methodParameterTypes144;
}