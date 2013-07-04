package com.tls.lms.util;

import java.util.HashMap;
import java.util.Map;

import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
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
	
}
