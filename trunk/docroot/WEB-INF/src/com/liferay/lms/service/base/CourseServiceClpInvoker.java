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

import com.liferay.lms.service.CourseServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class CourseServiceClpInvoker {
	public CourseServiceClpInvoker() {
		_methodName112 = "getBeanIdentifier";

		_methodParameterTypes112 = new String[] {  };

		_methodName113 = "setBeanIdentifier";

		_methodParameterTypes113 = new String[] { "java.lang.String" };

		_methodName118 = "getCoursesOfGroup";

		_methodParameterTypes118 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName112.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes112, parameterTypes)) {
			return CourseServiceUtil.getBeanIdentifier();
		}

		if (_methodName113.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes113, parameterTypes)) {
			CourseServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName118.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes118, parameterTypes)) {
			return CourseServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName112;
	private String[] _methodParameterTypes112;
	private String _methodName113;
	private String[] _methodParameterTypes113;
	private String _methodName118;
	private String[] _methodParameterTypes118;
}