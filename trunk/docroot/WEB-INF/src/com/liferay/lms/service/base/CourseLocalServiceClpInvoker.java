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

import com.liferay.lms.service.CourseLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class CourseLocalServiceClpInvoker {
	public CourseLocalServiceClpInvoker() {
		_methodName0 = "addCourse";

		_methodParameterTypes0 = new String[] { "com.liferay.lms.model.Course" };

		_methodName1 = "createCourse";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteCourse";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteCourse";

		_methodParameterTypes3 = new String[] { "com.liferay.lms.model.Course" };

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

		_methodName9 = "fetchCourse";

		_methodParameterTypes9 = new String[] { "long" };

		_methodName10 = "getCourse";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getPersistedModel";

		_methodParameterTypes11 = new String[] { "java.io.Serializable" };

		_methodName12 = "getCourseByUuidAndGroupId";

		_methodParameterTypes12 = new String[] { "java.lang.String", "long" };

		_methodName13 = "getCourses";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getCoursesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateCourse";

		_methodParameterTypes15 = new String[] { "com.liferay.lms.model.Course" };

		_methodName16 = "updateCourse";

		_methodParameterTypes16 = new String[] {
				"com.liferay.lms.model.Course", "boolean"
			};

		_methodName129 = "getBeanIdentifier";

		_methodParameterTypes129 = new String[] {  };

		_methodName130 = "setBeanIdentifier";

		_methodParameterTypes130 = new String[] { "java.lang.String" };

		_methodName135 = "getCoursesOfGroup";

		_methodParameterTypes135 = new String[] { "long" };

		_methodName136 = "getCourses";

		_methodParameterTypes136 = new String[] { "long" };

		_methodName137 = "countByGroupId";

		_methodParameterTypes137 = new String[] { "long" };

		_methodName138 = "fetchByGroupCreatedId";

		_methodParameterTypes138 = new String[] { "long" };

		_methodName139 = "addCourse";

		_methodParameterTypes139 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.lang.String", "java.util.Locale", "java.util.Date",
				"java.util.Date", "java.util.Date",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName140 = "addCourse";

		_methodParameterTypes140 = new String[] {
				"java.lang.String", "java.lang.String", "java.lang.String",
				"java.util.Locale", "java.util.Date", "java.util.Date",
				"java.util.Date", "com.liferay.portal.service.ServiceContext"
			};

		_methodName145 = "setVisible";

		_methodParameterTypes145 = new String[] { "long", "boolean" };

		_methodName146 = "modCourse";

		_methodParameterTypes146 = new String[] {
				"com.liferay.lms.model.Course", "java.lang.String",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName147 = "modCourse";

		_methodParameterTypes147 = new String[] {
				"com.liferay.lms.model.Course",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName148 = "deleteCourse";

		_methodParameterTypes148 = new String[] { "com.liferay.lms.model.Course" };

		_methodName149 = "deleteCourse";

		_methodParameterTypes149 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return CourseLocalServiceUtil.createCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return CourseLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return CourseLocalServiceUtil.fetchCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return CourseLocalServiceUtil.getCourse(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return CourseLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return CourseLocalServiceUtil.getCourseByUuidAndGroupId((java.lang.String)arguments[0],
				((Long)arguments[1]).longValue());
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return CourseLocalServiceUtil.getCourses(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return CourseLocalServiceUtil.getCoursesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return CourseLocalServiceUtil.updateCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return CourseLocalServiceUtil.updateCourse((com.liferay.lms.model.Course)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName129.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes129, parameterTypes)) {
			return CourseLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName130.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes130, parameterTypes)) {
			CourseLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName135.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes135, parameterTypes)) {
			return CourseLocalServiceUtil.getCoursesOfGroup(((Long)arguments[0]).longValue());
		}

		if (_methodName136.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes136, parameterTypes)) {
			return CourseLocalServiceUtil.getCourses(((Long)arguments[0]).longValue());
		}

		if (_methodName137.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes137, parameterTypes)) {
			return CourseLocalServiceUtil.countByGroupId(((Long)arguments[0]).longValue());
		}

		if (_methodName138.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes138, parameterTypes)) {
			return CourseLocalServiceUtil.fetchByGroupCreatedId(((Long)arguments[0]).longValue());
		}

		if (_methodName139.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes139, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.lang.String)arguments[3], (java.util.Locale)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(java.util.Date)arguments[7],
				(com.liferay.portal.service.ServiceContext)arguments[8]);
		}

		if (_methodName140.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes140, parameterTypes)) {
			return CourseLocalServiceUtil.addCourse((java.lang.String)arguments[0],
				(java.lang.String)arguments[1], (java.lang.String)arguments[2],
				(java.util.Locale)arguments[3], (java.util.Date)arguments[4],
				(java.util.Date)arguments[5], (java.util.Date)arguments[6],
				(com.liferay.portal.service.ServiceContext)arguments[7]);
		}

		if (_methodName145.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes145, parameterTypes)) {
			CourseLocalServiceUtil.setVisible(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());

			return null;
		}

		if (_methodName146.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes146, parameterTypes)) {
			return CourseLocalServiceUtil.modCourse((com.liferay.lms.model.Course)arguments[0],
				(java.lang.String)arguments[1],
				(com.liferay.portal.service.ServiceContext)arguments[2]);
		}

		if (_methodName147.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes147, parameterTypes)) {
			return CourseLocalServiceUtil.modCourse((com.liferay.lms.model.Course)arguments[0],
				(com.liferay.portal.service.ServiceContext)arguments[1]);
		}

		if (_methodName148.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes148, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse((com.liferay.lms.model.Course)arguments[0]);
		}

		if (_methodName149.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes149, parameterTypes)) {
			return CourseLocalServiceUtil.deleteCourse(((Long)arguments[0]).longValue());
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
	private String _methodName129;
	private String[] _methodParameterTypes129;
	private String _methodName130;
	private String[] _methodParameterTypes130;
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
	private String _methodName145;
	private String[] _methodParameterTypes145;
	private String _methodName146;
	private String[] _methodParameterTypes146;
	private String _methodName147;
	private String[] _methodParameterTypes147;
	private String _methodName148;
	private String[] _methodParameterTypes148;
	private String _methodName149;
	private String[] _methodParameterTypes149;
}