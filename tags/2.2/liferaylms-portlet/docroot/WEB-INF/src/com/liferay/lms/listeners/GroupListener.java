package com.liferay.lms.listeners;

import com.liferay.lms.auditing.AuditConstants;
import com.liferay.lms.auditing.AuditingLogFactory;
import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;

public class GroupListener extends BaseModelListener<Group> {

	@Override
	public void onAfterAddAssociation(Object classPK,
			String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		long groupId = GetterUtil.getLong(classPK);
		long userId = GetterUtil.getLong(associationClassPK);
		try {
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
			if((User.class.getName().equals(associationClassName))&&
				(Validator.isNotNull(course))){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(), userId, PrincipalThreadLocal.getUserId(), AuditConstants.REGISTER, null);
			}
		} catch (SystemException e) {
			throw new ModelListenerException(e);
		}
	}
	
	
	@Override
	public void onAfterRemoveAssociation(Object classPK,
			String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		long groupId = GetterUtil.getLong(classPK);
		long userId = GetterUtil.getLong(associationClassPK);
		try {
			Course course = CourseLocalServiceUtil.fetchByGroupCreatedId(groupId);
			if((User.class.getName().equals(associationClassName))&&
				(Validator.isNotNull(course))){
				AuditingLogFactory.audit(course.getCompanyId(), course.getGroupCreatedId(), Course.class.getName(), 
						course.getCourseId(), userId, PrincipalThreadLocal.getUserId(), AuditConstants.UNREGISTER, null);
			}
		} catch (SystemException e) {
			throw new ModelListenerException(e);
		}
	}
}
