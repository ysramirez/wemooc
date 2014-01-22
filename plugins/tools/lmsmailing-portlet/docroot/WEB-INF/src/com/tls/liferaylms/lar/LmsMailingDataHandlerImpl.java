package com.tls.liferaylms.lar;

import java.util.List;

import javax.portlet.PortletPreferences;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.tls.liferaylms.mail.model.MailTemplate;
import com.tls.liferaylms.mail.service.MailTemplateLocalServiceUtil;

public class LmsMailingDataHandlerImpl extends BasePortletDataHandler {
	
	private static final String _NAMESPACE = "module";

	
	private static PortletDataHandlerBoolean _entries =
		new PortletDataHandlerBoolean(_NAMESPACE, "entries", true);


	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_entries
		};
	}
	
	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_entries
		};
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences) throws Exception {
		// TODO Auto-generated method stub
		return super.doDeleteData(portletDataContext, portletId, portletPreferences);
	}

	@Override
	protected String doExportData(PortletDataContext portletDataContext, String portletId, PortletPreferences portletPreferences) throws Exception {

		portletDataContext.addPermissions("com.liferay.lms.model.module", portletDataContext.getScopeGroupId());
		
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("moduledata");

		rootElement.addAttribute("group-id", String.valueOf(portletDataContext.getScopeGroupId()));
		
		List<MailTemplate> templates = MailTemplateLocalServiceUtil.getMailTemplates(0, MailTemplateLocalServiceUtil.getMailTemplatesCount());
			
		for (MailTemplate template : templates) {
	
			String path = getEntryPath(portletDataContext, template);
				
			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			Element entryElement = rootElement.addElement("moduleentry");

			entryElement.addAttribute("path", path);

			portletDataContext.addPermissions(MailTemplate.class, template.getIdTemplate());
			
			template.setUserUuid(template.getUserUuid());
			portletDataContext.addZipEntry(path, template);
			
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(PortletDataContext portletDataContext, String portletId, PortletPreferences portletPreferences, String data) throws Exception {

		portletDataContext.importPermissions("com.liferay.lms.model.module", portletDataContext.getSourceGroupId(), portletDataContext.getScopeGroupId());
		
		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();
		
		for (Element entryElement : rootElement.elements("moduleentry")) {
			String path = entryElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}
			MailTemplate template = (MailTemplate)portletDataContext.getZipEntryAsObject(path);
			
			MailTemplate newTemplate = MailTemplateLocalServiceUtil.createMailTemplate(CounterLocalServiceUtil.increment(MailTemplate.class.getName()));
			newTemplate.setCompanyId(template.getCompanyId());
			newTemplate.setGroupId(template.getGroupId());
			newTemplate.setUserId(template.getUserId());
			newTemplate.setSubject(template.getSubject());
			newTemplate.setBody(template.getBody());

			MailTemplateLocalServiceUtil.updateMailTemplate(newTemplate);
		}
		
		return null;
	}
	
	protected String getEntryPath(PortletDataContext context, MailTemplate template) {

		StringBundler sb = new StringBundler(4);	
		sb.append(context.getPortletPath("managetemplates_WAR_lmsmailingportlet"));
		sb.append("/mailtemplates/");
		sb.append(template.getIdTemplate());
		sb.append(".xml");
		return sb.toString();
	}

}
