package com.liferay.lms.indexer;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletURL;

import com.liferay.lms.model.Course;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

public class CourseIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {Course.class.getName()};
	public static final String PORTLET_ID = "courseadmin_WAR_liferaylmsportlet"; 
	@Override
	public String[] getClassNames() {
		// TODO Auto-generated method stub
		return CLASS_NAMES;
	}

	
	public Summary getSummary(Document document, String snippet,
			PortletURL portletURL) {
		// TODO Auto-generated method stub
		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String groupId = document.get("groupId");
		String articleId = document.get(Field.ENTRY_CLASS_PK);
		String version = document.get("version");
		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		Course entry = (Course)obj;

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entry.getCourseId());

		SearchEngineUtil.deleteDocument(
			entry.getCompanyId(), document.get(Field.UID));

	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Course entry = (Course)obj;
		
		long companyId = entry.getCompanyId();
		long groupId = getParentGroupId(entry.getGroupId());
		long scopeGroupId = entry.getGroupId();
		long userId = entry.getUserId();
		String userName = UserLocalServiceUtil.getUser(userId).getFullName();
		long entryId = entry.getCourseId();
		String title = entry.getTitle();
		
		AssetEntry assetEntry=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), entryId);
		String content=assetEntry.getSummary();
			content=content+" "+HtmlUtil.extractText(entry.getDescription(LocaleUtil.getDefault(),true));
			
		String contentSinAcentos=content.toLowerCase();
		contentSinAcentos=contentSinAcentos.replace('á', 'a');
		contentSinAcentos=contentSinAcentos.replace('é', 'e');
		contentSinAcentos=contentSinAcentos.replace('í', 'i');
		contentSinAcentos=contentSinAcentos.replace('ó', 'o');
		contentSinAcentos=contentSinAcentos.replace('ú', 'u');
		content=content+" "+contentSinAcentos;
		Date displayDate = assetEntry.getPublishDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			Course.class.getName(), entryId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			Course.class.getName(), entryId);
		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		Document document = new DocumentImpl();

		document.addUID(PORTLET_ID, entryId);

		document.addModifiedDate(displayDate);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.PORTLET_ID, PORTLET_ID);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.SCOPE_GROUP_ID, scopeGroupId);
		document.addKeyword(Field.USER_ID, userId);
		document.addText(Field.USER_NAME, userName);

		document.addText(Field.TITLE, title);
		document.addText(Field.CONTENT, content);
		document.addKeyword(Field.ASSET_CATEGORY_IDS, assetCategoryIds);
		document.addKeyword(Field.ASSET_TAG_NAMES, assetTagNames);

		document.addKeyword(Field.ENTRY_CLASS_NAME, Course.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, entryId);
		String[] assetCategoryTitles = new String[assetCategoryIds.length];
		for (int i=0; i<assetCategoryIds.length;i++){
			AssetCategory categoria = AssetCategoryLocalServiceUtil.getCategory(assetCategoryIds[i]);
			assetCategoryTitles[i]=categoria.getName();
		}

		document.addKeyword("assetCategoryTitles", assetCategoryTitles);
		ExpandoBridgeIndexerUtil.addAttributes(document, expandoBridge);

		return document;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		Course entry = (Course)obj;
		try
		{
			AssetEntry assetEntry=AssetEntryLocalServiceUtil.getEntry(Course.class.getName(), entry.getCourseId());
			if (!assetEntry.getVisible()) {
				return;
			}

			Document document = getDocument(entry);

			SearchEngineUtil.updateDocument(entry.getCompanyId(), document);
		}
		catch(com.liferay.portlet.asset.NoSuchEntryException ae)
		{
			return;
		}
		
		

	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Course entry = CourseLocalServiceUtil.getCourse(classPK);
		doReindex(entry);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);

	}

	private void reindexEntries(long companyId) throws Exception {
		java.util.List<Course> entries=CourseLocalServiceUtil.getCourses(companyId);
		if(entries==null ||entries.isEmpty())
		{
			return;
		}
		for(Course entry:entries)
		{
			doReindex(entry);
		}
	}
	

	@Override
	protected String getPortletId(SearchContext searchContext) {
		// TODO Auto-generated method stub
		return PORTLET_ID;
	}


	@Override
	public String getPortletId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected Summary doGetSummary(Document document, Locale locale, String snippet,
			PortletURL portletURL) throws Exception {
		// TODO Auto-generated method stub
		return getSummary(document, snippet, portletURL);
	}

}
