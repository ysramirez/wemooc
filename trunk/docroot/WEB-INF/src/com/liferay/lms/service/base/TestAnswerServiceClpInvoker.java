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

import com.liferay.lms.service.TestAnswerServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class TestAnswerServiceClpInvoker {
	public TestAnswerServiceClpInvoker() {
		_methodName96 = "getBeanIdentifier";

		_methodParameterTypes96 = new String[] {  };

		_methodName97 = "setBeanIdentifier";

		_methodParameterTypes97 = new String[] { "java.lang.String" };

		_methodName102 = "getTestAnswersByQuestionId";

		_methodParameterTypes102 = new String[] { "long" };

		_methodName103 = "getTestAnswer";

		_methodParameterTypes103 = new String[] { "long" };

		_methodName104 = "modTestAnswer";

		_methodParameterTypes104 = new String[] {
				"com.liferay.lms.model.TestAnswer"
			};

		_methodName105 = "addTestAnswer";

		_methodParameterTypes105 = new String[] {
				"long", "java.lang.String", "java.lang.String",
				"java.lang.String", "boolean"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName96.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes96, parameterTypes)) {
			return TestAnswerServiceUtil.getBeanIdentifier();
		}

		if (_methodName97.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes97, parameterTypes)) {
			TestAnswerServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName102.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes102, parameterTypes)) {
			return TestAnswerServiceUtil.getTestAnswersByQuestionId(((Long)arguments[0]).longValue());
		}

		if (_methodName103.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes103, parameterTypes)) {
			return TestAnswerServiceUtil.getTestAnswer(((Long)arguments[0]).longValue());
		}

		if (_methodName104.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes104, parameterTypes)) {
			return TestAnswerServiceUtil.modTestAnswer((com.liferay.lms.model.TestAnswer)arguments[0]);
		}

		if (_methodName105.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes105, parameterTypes)) {
			return TestAnswerServiceUtil.addTestAnswer(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3],
				((Boolean)arguments[4]).booleanValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName96;
	private String[] _methodParameterTypes96;
	private String _methodName97;
	private String[] _methodParameterTypes97;
	private String _methodName102;
	private String[] _methodParameterTypes102;
	private String _methodName103;
	private String[] _methodParameterTypes103;
	private String _methodName104;
	private String[] _methodParameterTypes104;
	private String _methodName105;
	private String[] _methodParameterTypes105;
}