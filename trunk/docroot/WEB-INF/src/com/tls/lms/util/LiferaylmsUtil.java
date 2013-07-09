package com.tls.lms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.lms.model.LearningActivity;
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

	public static void setPermission(ThemeDisplay themeDisplay, String classname, Role role, String[] actionIds, long primaryKey) throws Exception{
		Resource resource =  ResourceLocalServiceUtil.getResource(themeDisplay.getCompanyId(), classname, ResourceConstants.SCOPE_INDIVIDUAL, Long.toString(primaryKey));
		if(ResourceBlockLocalServiceUtil.isSupported(classname)){
		   System.out.println("por aqui");
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
		if(res != null && res.size()>0){
			Role siteMemberRole = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(), RoleConstants.SITE_MEMBER);
			for(java.util.ListIterator<LearningActivity> itr = res.listIterator(); itr.hasNext();){
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
		return res;
	}
	
}
