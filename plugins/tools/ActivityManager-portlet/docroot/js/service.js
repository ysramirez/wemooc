Liferay.Service.register("Liferay.Service.lmssa", "com.liferay.lmssa.service", "ActivityManager-portlet");

Liferay.Service.registerClass(
	Liferay.Service.lmssa, "ActManAudit",
	{
		addActManAudit: true,
		findBycompanyId: true,
		countBycompanyId: true
	}
);