package com.coursetools;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class portletlanguages
 */
public class PortletLanguages extends MVCPortlet {
 
	@SuppressWarnings("unchecked")
	public void changeLanguage(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException, IOException {
		
		String value=ParamUtil.getString(actionRequest,"value","es_ES");
		
		try {
			
			//82 es el portlet de idioma.
			List<PortletPreferences> portletPreferences = PortletPreferencesLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(PortletPreferences.class).add(PropertyFactoryUtil.forName("portletId").eq("82")));
			
			System.out.println(" portletPreferences 82 : " + portletPreferences.size());
			
			int replaced = 0;
			String portletIds = "";
			
			for(PortletPreferences portlet:portletPreferences){
				
				System.out.println("\n----------\n"+portlet.getPortletPreferencesId());
				
				Document document = SAXReaderUtil.read(portlet.getPreferences());
				Element rootElement = document.getRootElement();
				
				for(Element key:rootElement.elements()){
					
					boolean found = false;
					
					for(Element preference:key.elements()){

						if(found){
							
							System.out.println(" languageIds : " + preference.getName() + ", text " + preference.getText());
							
							preference.setText(value);
							portlet.setPreferences(document.formattedString());
							PortletPreferencesLocalServiceUtil.updatePortletPreferences(portlet);
							
							found = false;
							replaced++;
							portletIds += portlet.getPortletPreferencesId() + ", ";
							
							System.out.println("   --> PortletPreferencesId : " +portlet.getPortletPreferencesId());
							System.out.println("     --> new text : " +value );
						}
						
						if(preference.getName().equals("name") && preference.getText().equals("languageIds")){
							found = true;
						}
					}
				}
			}
			
			System.out.println("\n languageIds replaced : " + replaced);
			System.out.println(" portletIds : " + portletIds);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
