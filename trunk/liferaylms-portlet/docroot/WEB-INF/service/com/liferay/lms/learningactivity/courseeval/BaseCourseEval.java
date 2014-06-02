package com.liferay.lms.learningactivity.courseeval;

import java.io.IOException;

import javax.portlet.PortletResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.ClpSerializer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

public abstract class BaseCourseEval implements CourseEval {

	public static String PORTLET_ID = 
			PortalUtil.getJsSafePortletId(
					"courseadmin" + PortletConstants.WAR_SEPARATOR + ClpSerializer.getServletContextName());

	@Override
	public String getExpecificContentPage() {
		return StringPool.BLANK;
	}

	@Override
	public void setExtraContent(UploadRequest uploadRequest,
			PortletResponse portletResponse, Course course)
			throws PortalException, SystemException, DocumentException,
			IOException {
	}

	@Override
	public boolean especificValidations(UploadRequest uploadRequest,
			PortletResponse portletResponse) {
		return true;
	}

	@Override
	public String getPortletId(){
		return PORTLET_ID;
	}

}
