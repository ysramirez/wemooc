package com.tls.lms.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityTryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class LiferaylmsUtil {
	
	public static final int defaultStartYear = 2012; //porque es necesario mantener los valores antiguos para los cursos antiguos
	public static final int defaultEndYear = Calendar.getInstance().get(Calendar.YEAR) + 8;

	public static void setPermission(ThemeDisplay themeDisplay, String classname, Role role, String[] actionIds, long primaryKey) throws Exception{
		Resource resource =  ResourceLocalServiceUtil.getResource(themeDisplay.getCompanyId(), classname, ResourceConstants.SCOPE_INDIVIDUAL, Long.toString(primaryKey));
		if(ResourceBlockLocalServiceUtil.isSupported(classname)){
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(role.getRoleId(), actionIds);
			ResourceBlockServiceUtil.setIndividualScopePermissions(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), resource.getName(),
					primaryKey, roleIdsToActionIds);
		}else{
			Map<Long, String[]> roleIdsToActionIds = new HashMap<Long, String[]>();
			roleIdsToActionIds.put(role.getRoleId(), actionIds);
			ResourcePermissionServiceUtil.setIndividualResourcePermissions(
					themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(), resource.getName(),
					Long.toString(primaryKey), roleIdsToActionIds);	
		}
	}

	public static List<LearningActivity> getVisibleActivities(ThemeDisplay themeDisplay,
			List<LearningActivity> res, PermissionChecker permissionChecker)
			throws PortalException, SystemException {
		List<LearningActivity> res2 = null;
		if(res != null && res.size()>0){
			res2 = new ArrayList<LearningActivity>();
			res2.addAll(res);
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			for(java.util.ListIterator<LearningActivity> itr = res2.listIterator(); itr.hasNext();){
				LearningActivity activity = itr.next();
				try {
					if(!ResourcePermissionLocalServiceUtil.hasResourcePermission(activity.getCompanyId(), LearningActivity.class.getName(), 
							ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW)
							&& !permissionChecker.hasPermission(activity.getGroupId(), LearningActivity.class.getName(), activity.getActId() , "CORRECT"))
						itr.remove();
				} catch (SystemException e) {
					e.printStackTrace();
				}
			}
		}
		return res2;
	}
	
	public static boolean canBeEdited(LearningActivity activity, long companyId){
		try {
			Role siteMemberRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.SITE_MEMBER);
			boolean isVisible = ResourcePermissionLocalServiceUtil.hasResourcePermission(activity.getCompanyId(), LearningActivity.class.getName(), 
					ResourceConstants.SCOPE_INDIVIDUAL,	Long.toString(activity.getActId()),siteMemberRole.getRoleId(), ActionKeys.VIEW);
			long latry = LearningActivityTryLocalServiceUtil.getTriesCountByActivity(activity.getActId());
			boolean hasLearningActivityTry = latry>0;
			return !isVisible && !hasLearningActivityTry;
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
