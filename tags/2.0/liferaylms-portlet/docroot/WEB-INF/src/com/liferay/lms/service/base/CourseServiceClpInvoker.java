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
		_methodName128 = "getBeanIdentifier";

		_methodParameterTypes128 = new String[] {  };

		_methodName129 = "setBeanIdentifier";

		_methodParameterTypes129 = new String[] { "java.lang.String" };

		_methodName134 = "getCoursesOfGroup";

		_methodParameterTypes134 = new String[] { "long" };

		_methodName135 = "createCourse";

		_methodParameterTypes135 = new String[] { "java.lang.String" };

		_methodName136 = "getCourseStudents";

		_methodParameterTypes136 = new String[] { "long" };

		_methodName137 = "getCourseTeachers";

		_methodParameterTypes137 = new String[] { "long" };

		_methodName138 = "getCourseEditors";

		_methodParameterTypes138 = new String[] { "long" };

		_methodName139 = "addStudentToCourse";

		_methodParameterTypes139 = new String[] { "long", "java.lang.String" };

		_methodName140 = "addTeacherToCourse";

		_methodParameterTypes140 = new String[] { "long", "java.lang.String" };

		_methodName141 = "addEditorToCourse";

		_methodParameterTypes141 = new String[] { "long", "java.lang.String" };

		_methodName142 = "removeStudentFromCourse";

		_methodParameterTypes142 = new String[] { "long", "java.lang.String" };

		_methodName143 = "removeTeacherFromCourse";

		_methodParameterTypes143 = new String[] { "long", "java.lang.String" };

		_methodName144 = "removeEditorFromCourse";

		_methodParameterTypes144 = new String[] { "long", "java.lang.String" };

		_methodName145 = "getUserResult";

		_methodParameterTypes145 = new String[] { "long", "java.lang.String" };

		_methodName146 = "myCourses";

		_methodParameterTypes146 = new String[] {  };

		_methodName148 = "addUser";

		_methodParameterTypes148 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "boolean", "boolean", "boolean"
			};

		_methodName149 = "updateUser";

		_methodParameterTypes149 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "boolean", "boolean", "boolean"
			};

		_methodName150 = "setParent";

		_methodParameterTypes150 = new String[] {
				"java.lang.String", "java.lang.String"
			};

		_methodName151 = "unsetParent";

		_methodParameterTypes151 = new String[] {
				"java.lang.String", "java.lang.String"
			};

		_methodName152 = "setTutor";

		_methodParameterTypes152 = new String[] {
				"java.lang.String", "java.lang.String"
			};

		_methodName153 = "unsetTutor";

		_methodParameterTypes153 = new String[] {
				"java.lang.String", "java.lang.String"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName128.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes128, parameterTypes)) {
			return CourseServiceUtil.getBeanIdentifier();
		}

		if (_methodName129.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes129, parameterTypes)) {
			CourseServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName134.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes134, parameterTypes)) {
			return CourseServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			return CourseServiceUtil.createCourse((java.lang.String)arguments[0]);
		}

		if (_methodName136.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes136, parameterTypes)) {
			return CourseServiceUtil.getCourseStudents(((Long)arguments[0]).longValue());
		}

		if (_methodName137.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes137, parameterTypes)) {
			return CourseServiceUtil.getCourseTeachers(((Long)arguments[0]).longValue());
		}

		if (_methodName138.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes138, parameterTypes)) {
			return CourseServiceUtil.getCourseEditors(((Long)arguments[0]).longValue());
		}

		if (_methodName139.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes139, parameterTypes)) {
			CourseServiceUtil.addStudentToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			CourseServiceUtil.addTeacherToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName141.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes141, parameterTypes)) {
			CourseServiceUtil.addEditorToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName142.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes142, parameterTypes)) {
			CourseServiceUtil.removeStudentFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName143.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes143, parameterTypes)) {
			CourseServiceUtil.removeTeacherFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName144.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes144, parameterTypes)) {
			CourseServiceUtil.removeEditorFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			return CourseServiceUtil.getUserResult(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return CourseServiceUtil.myCourses();
		}

		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			CourseServiceUtil.addUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3],
				((Boolean)arguments[4]).booleanValue(),
				((Boolean)arguments[5]).booleanValue(),
				((Boolean)arguments[6]).booleanValue());

			return null;
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			CourseServiceUtil.updateUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3],
				((Boolean)arguments[4]).booleanValue(),
				((Boolean)arguments[5]).booleanValue(),
				((Boolean)arguments[6]).booleanValue());

			return null;
		}

		if (_methodName150.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes150, parameterTypes)) {
			CourseServiceUtil.setParent((java.lang.String)arguments[0],
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName151.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes151, parameterTypes)) {
			CourseServiceUtil.unsetParent((java.lang.String)arguments[0],
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName152.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes152, parameterTypes)) {
			CourseServiceUtil.setTutor((java.lang.String)arguments[0],
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName153.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes153, parameterTypes)) {
			CourseServiceUtil.unsetTutor((java.lang.String)arguments[0],
				(java.lang.String)arguments[1]);

			return null;
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
	private String _methodName144;
	private String[] _methodParameterTypes144;
	private String _methodName145;
	private String[] _methodParameterTypes145;
	private String _methodName146;
	private String[] _methodParameterTypes146;
	private String _methodName148;
	private String[] _methodParameterTypes148;
	private String _methodName149;
	private String[] _methodParameterTypes149;
	private String _methodName150;
	private String[] _methodParameterTypes150;
	private String _methodName151;
	private String[] _methodParameterTypes151;
	private String _methodName152;
	private String[] _methodParameterTypes152;
	private String _methodName153;
	private String[] _methodParameterTypes153;
}