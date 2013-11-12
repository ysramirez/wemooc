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

import com.liferay.lms.service.LearningActivityServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class LearningActivityServiceClpInvoker {
	public LearningActivityServiceClpInvoker() {
		_methodName128 = "getBeanIdentifier";

		_methodParameterTypes128 = new String[] {  };

		_methodName129 = "setBeanIdentifier";

		_methodParameterTypes129 = new String[] { "java.lang.String" };

		_methodName134 = "getLearningActivitiesOfGroup";

		_methodParameterTypes134 = new String[] { "long" };

		_methodName135 = "getLearningActivitiesOfModule";

		_methodParameterTypes135 = new String[] { "long" };

		_methodName136 = "deleteLearningactivity";

		_methodParameterTypes136 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName137 = "deleteLearningactivity";

		_methodParameterTypes137 = new String[] { "long" };

		_methodName138 = "getLearningActivity";

		_methodParameterTypes138 = new String[] { "long" };

		_methodName139 = "addLearningActivity";

		_methodParameterTypes139 = new String[] {
				"java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName140 = "modLearningActivity";

		_methodParameterTypes140 = new String[] {
				"com.liferay.lms.model.LearningActivity",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName141 = "modLearningActivity";

		_methodParameterTypes141 = new String[] {
				"com.liferay.lms.model.LearningActivity"
			};

		_methodName142 = "modLearningActivity";

		_methodParameterTypes142 = new String[] {
				"long", "java.lang.String", "java.lang.String", "java.util.Date",
				"java.util.Date", "java.util.Date", "int", "long", "int", "long",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName143 = "isLocked";

		_methodParameterTypes143 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName128.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes128, parameterTypes)) {
			return LearningActivityServiceUtil.getBeanIdentifier();
		}

		if (_methodName129.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes129, parameterTypes)) {
			LearningActivityServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName134.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes134, parameterTypes)) {
			return LearningActivityServiceUtil.getLearningActivitiesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			return LearningActivityServiceUtil.getLearningActivitiesOfModule(((Long)arguments[0]).longValue());
		}

		if (_methodName136.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes136, parameterTypes)) {
			LearningActivityServiceUtil.deleteLearningactivity((com.liferay.lms.model.LearningActivity)arguments[0]);

			return null;
		}

		if (_methodName137.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes137, parameterTypes)) {
			LearningActivityServiceUtil.deleteLearningactivity(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName138.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes138, parameterTypes)) {
			return LearningActivityServiceUtil.getLearningActivity(((Long)arguments[0]).longValue());
		}

		if (_methodName139.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes139, parameterTypes)) {
			return LearningActivityServiceUtil.addLearningActivity((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.util.Date)arguments[2],
				(java.util.Date)arguments[3], (java.util.Date)arguments[4],
				((Integer)arguments[5]).intValue(),
				((Long)arguments[6]).longValue(),
				((Integer)arguments[7]).intValue(),
				((Long)arguments[8]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[9]);
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return LearningActivityServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			return LearningActivityServiceUtil.modLearningActivity((com.liferay.lms.model.LearningActivity)arguments[0]);
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			return LearningActivityServiceUtil.modLearningActivity(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.util.Date)arguments[3], (java.util.Date)arguments[4],
				(java.util.Date)arguments[5],
				((Integer)arguments[6]).intValue(),
				((Long)arguments[7]).longValue(),
				((Integer)arguments[8]).intValue(),
				((Long)arguments[9]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[10]);
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			return LearningActivityServiceUtil.isLocked(((Long)arguments[0]).longValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName128;
	private String[] _methodParameterTypes128;
	private String _methodName129;
	private String[] _methodParameterTypes129;
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
}