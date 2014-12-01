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
		_methodName162 = "getBeanIdentifier";

		_methodParameterTypes162 = new String[] {  };

		_methodName163 = "setBeanIdentifier";

		_methodParameterTypes163 = new String[] { "java.lang.String" };

		_methodName168 = "getCoursesOfGroup";

		_methodParameterTypes168 = new String[] { "long" };

		_methodName169 = "createCourse";

		_methodParameterTypes169 = new String[] {
				"long", "java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "int", "int", "int", "int", "int",
				"java.util.Date", "java.util.Date"
			};

		_methodName170 = "createCourse";

		_methodParameterTypes170 = new String[] {
				"java.lang.String", "java.lang.String", "boolean",
				"java.lang.String", "int", "int", "int", "int", "int",
				"java.util.Date", "java.util.Date"
			};

		_methodName171 = "getCourses";

		_methodParameterTypes171 = new String[] {  };

		_methodName172 = "getCourseStudents";

		_methodParameterTypes172 = new String[] { "long" };

		_methodName173 = "getCourseTeachers";

		_methodParameterTypes173 = new String[] { "long" };

		_methodName174 = "getCourseEditors";

		_methodParameterTypes174 = new String[] { "long" };

		_methodName175 = "addStudentToCourse";

		_methodParameterTypes175 = new String[] { "long", "java.lang.String" };

		_methodName176 = "addTeacherToCourse";

		_methodParameterTypes176 = new String[] { "long", "java.lang.String" };

		_methodName177 = "addEditorToCourse";

		_methodParameterTypes177 = new String[] { "long", "java.lang.String" };

		_methodName178 = "removeStudentFromCourse";

		_methodParameterTypes178 = new String[] { "long", "java.lang.String" };

		_methodName179 = "removeTeacherFromCourse";

		_methodParameterTypes179 = new String[] { "long", "java.lang.String" };

		_methodName180 = "removeEditorFromCourse";

		_methodParameterTypes180 = new String[] { "long", "java.lang.String" };

		_methodName181 = "getUserResult";

		_methodParameterTypes181 = new String[] { "long", "java.lang.String" };

		_methodName182 = "myCourses";

		_methodParameterTypes182 = new String[] {  };

		_methodName184 = "addUser";

		_methodParameterTypes184 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String"
			};

		_methodName185 = "updateUser";

		_methodParameterTypes185 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String"
			};

		_methodName186 = "existsCourseName";

		_methodParameterTypes186 = new String[] {
				"java.lang.Long", "java.lang.Long", "java.lang.String"
			};

		_methodName187 = "getLogoUrl";

		_methodParameterTypes187 = new String[] { "java.lang.Long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName162.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes162, parameterTypes)) {
			return CourseServiceUtil.getBeanIdentifier();
		}

		if (_methodName163.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes163, parameterTypes)) {
			CourseServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName168.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes168, parameterTypes)) {
			return CourseServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName169.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes169, parameterTypes)) {
			return CourseServiceUtil.createCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String)arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue(),
				((Integer)arguments[9]).intValue(),
				(java.util.Date)arguments[10], (java.util.Date)arguments[11]);
		}

		if (_methodName170.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes170, parameterTypes)) {
			return CourseServiceUtil.createCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				(java.lang.String)arguments[3],
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue(),
				((Integer)arguments[7]).intValue(),
				((Integer)arguments[8]).intValue(),
				(java.util.Date)arguments[9], (java.util.Date)arguments[10]);
		}

		if (_methodName171.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes171, parameterTypes)) {
			return CourseServiceUtil.getCourses();
		}

		if (_methodName172.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes172, parameterTypes)) {
			return CourseServiceUtil.getCourseStudents(((Long)arguments[0]).longValue());
		}

		if (_methodName173.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes173, parameterTypes)) {
			return CourseServiceUtil.getCourseTeachers(((Long)arguments[0]).longValue());
		}

		if (_methodName174.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes174, parameterTypes)) {
			return CourseServiceUtil.getCourseEditors(((Long)arguments[0]).longValue());
		}

		if (_methodName175.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes175, parameterTypes)) {
			CourseServiceUtil.addStudentToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName176.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes176, parameterTypes)) {
			CourseServiceUtil.addTeacherToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName177.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes177, parameterTypes)) {
			CourseServiceUtil.addEditorToCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName178.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes178, parameterTypes)) {
			CourseServiceUtil.removeStudentFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName179.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes179, parameterTypes)) {
			CourseServiceUtil.removeTeacherFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName180.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes180, parameterTypes)) {
			CourseServiceUtil.removeEditorFromCourse(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName181.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes181, parameterTypes)) {
			return CourseServiceUtil.getUserResult(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);
		}

		if (_methodName182.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes182, parameterTypes)) {
			return CourseServiceUtil.myCourses();
		}

		if (_methodName184.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes184, parameterTypes)) {
			CourseServiceUtil.addUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3]);

			return null;
		}

		if (_methodName185.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes185, parameterTypes)) {
			CourseServiceUtil.updateUser((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3]);

			return null;
		}

		if (_methodName186.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes186, parameterTypes)) {
			return CourseServiceUtil.existsCourseName((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName187.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes187, parameterTypes)) {
			return CourseServiceUtil.getLogoUrl((java.lang.Long)arguments[0]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName162;
	private String[] _methodParameterTypes162;
	private String _methodName163;
	private String[] _methodParameterTypes163;
	private String _methodName168;
	private String[] _methodParameterTypes168;
	private String _methodName169;
	private String[] _methodParameterTypes169;
	private String _methodName170;
	private String[] _methodParameterTypes170;
	private String _methodName171;
	private String[] _methodParameterTypes171;
	private String _methodName172;
	private String[] _methodParameterTypes172;
	private String _methodName173;
	private String[] _methodParameterTypes173;
	private String _methodName174;
	private String[] _methodParameterTypes174;
	private String _methodName175;
	private String[] _methodParameterTypes175;
	private String _methodName176;
	private String[] _methodParameterTypes176;
	private String _methodName177;
	private String[] _methodParameterTypes177;
	private String _methodName178;
	private String[] _methodParameterTypes178;
	private String _methodName179;
	private String[] _methodParameterTypes179;
	private String _methodName180;
	private String[] _methodParameterTypes180;
	private String _methodName181;
	private String[] _methodParameterTypes181;
	private String _methodName182;
	private String[] _methodParameterTypes182;
	private String _methodName184;
	private String[] _methodParameterTypes184;
	private String _methodName185;
	private String[] _methodParameterTypes185;
	private String _methodName186;
	private String[] _methodParameterTypes186;
	private String _methodName187;
	private String[] _methodParameterTypes187;
}