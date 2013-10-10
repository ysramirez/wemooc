package com.liferay.lms.auditing;

import java.util.Properties;

import com.liferay.portal.kernel.util.PropsUtil;

public class AuditingLogFactory 
{
	static AuditingLog auditLog;
	public static AuditingLog getAuditLog() throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		if(auditLog==null)
		{
		
			
			Class<?> clase=Class.forName(PropsUtil.get("audit.implementation"));
			auditLog=(AuditingLog)clase.newInstance();
		}
		return auditLog;
	}
}
