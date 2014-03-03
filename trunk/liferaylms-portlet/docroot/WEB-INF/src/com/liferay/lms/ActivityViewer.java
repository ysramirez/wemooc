package com.liferay.lms;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.learningactivity.LearningActivityType;
import com.liferay.lms.learningactivity.LearningActivityTypeRegistry;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletWrapper;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ActivityViewer
 */
public class ActivityViewer extends MVCPortlet 
{
	
	static Set<String> reservedAttrs = new HashSet<String>();

	static {
		reservedAttrs.add(WebKeys.PAGE_TOP);
	}

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {
		long actId=GetterUtil.DEFAULT_LONG;		
		if(ParamUtil.getBoolean(renderRequest, "actionEditingDetails", false)){
			actId=ParamUtil.getLong(renderRequest, "resId", 0);
			renderResponse.setProperty("clear-request-parameters",Boolean.TRUE.toString());
		}
		else{
			actId=ParamUtil.getLong(renderRequest, "actId");
		}
		
		if(Validator.isNull(actId)) {
			renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
		}
		else {
			try {
				LearningActivity learningActivity = LearningActivityLocalServiceUtil.getLearningActivity(actId);
	
				if(Validator.isNull(learningActivity)) {
					renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
				}
				else {
					LearningActivityType learningActivityType=new LearningActivityTypeRegistry().getLearningActivityType(learningActivity.getTypeId());
					
					if(Validator.isNull(learningActivityType)) {
						renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
					}
					else {
						ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
						if(themeDisplay.getLayoutTypePortlet().getPortletIds().contains(learningActivityType.getPortletId())) {
							renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
						}
						else {
							PortletPreferencesFactoryUtil.getLayoutPortletSetup(themeDisplay.getLayout(), learningActivityType.getPortletId());
							String activityContent = renderPortlet(renderRequest, renderResponse, 
									themeDisplay, themeDisplay.getScopeGroupId(), learningActivityType.getPortletId());
							renderResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
							renderResponse.getWriter().print(activityContent);
							
							String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
									themeDisplay.getPlid(), learningActivityType.getPortletId());
							String portletName = learningActivityType.getPortletId();

							int warSeparatorIndex = portletName.indexOf(PortletConstants.WAR_SEPARATOR);
							if (warSeparatorIndex != -1) {
								portletName = portletName.substring(0, warSeparatorIndex);
							}

							if ((ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
									themeDisplay.getCompanyId(), portletName,
									ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey) == 0)&&
								(ResourceActionLocalServiceUtil.fetchResourceAction(portletName, "ACTION_VIEW")!=null)) {
					        	Role siteMember = RoleLocalServiceUtil.getRole(themeDisplay.getCompanyId(),RoleConstants.SITE_MEMBER);
				        		ResourcePermissionServiceUtil.setIndividualResourcePermissions(themeDisplay.getScopeGroupId(), themeDisplay.getCompanyId(), 
				        				portletName, resourcePrimKey, siteMember.getRoleId(), new String[]{"ACTION_VIEW"});
							}
						}
					}
				}
			}
			catch(Throwable throwable) {
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
			}
		}
		
		//super.render(renderRequest, renderResponse);
	}

    /**
     * Renders the given portlet as a runtime portlet and returns the portlet's HTML.
     * Based on http://www.devatwork.nl/2011/07/liferay-embedding-portlets-in-your-portlet/
     * @throws PortalException 
     */
    @SuppressWarnings("unchecked")
	public static String renderPortlet(final RenderRequest request, final RenderResponse response,final ThemeDisplay themeDisplay,final long scopeGroup, final String portletId) throws SystemException, IOException, ServletException, ValidatorException, PortalException {
        // Get servlet request / response
        HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
        HttpServletResponse servletResponse = PortalUtil.getHttpServletResponse(response);
        
        PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
        PortletDisplay portletDisplayClone = new PortletDisplay();
        portletDisplay.copyTo(portletDisplayClone);
        final Map<String, Object> requestAttributeBackup = new HashMap<String, Object>();
        for (final String key : Collections.list((Enumeration<String>) servletRequest.getAttributeNames())) {
            requestAttributeBackup.put(key, servletRequest.getAttribute(key));
        }
        // Render the portlet as a runtime portlet
        String result=null;
        long currentScopeGroup = themeDisplay.getScopeGroupId();
        String currentOuterPortlet = (String) servletRequest.getAttribute("OUTER_PORTLET_ID");
        Layout currentLayout = (Layout)servletRequest.getAttribute(WebKeys.LAYOUT);
        try {
			ServletContext servletContext = (ServletContext)servletRequest.getAttribute(WebKeys.CTX);
            com.liferay.portal.model.Portlet portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getCompanyId(request), portletId);
        	servletRequest.setAttribute(WebKeys.RENDER_PORTLET_RESOURCE, Boolean.TRUE);
        	long defaultGroupPlid = LayoutLocalServiceUtil.getDefaultPlid(scopeGroup);
        	if(defaultGroupPlid!=LayoutConstants.DEFAULT_PLID) {
        		servletRequest.setAttribute(WebKeys.LAYOUT, LayoutLocalServiceUtil.getLayout(defaultGroupPlid));
        	}

        	servletRequest.setAttribute("OUTER_PORTLET_ID",PortalUtil.getPortletId(request));
        	
        	StringBundler queryString = new StringBundler();
        	for (Entry<String, String[]> entry : request.getPublicParameterMap().entrySet()) {
        		String[] values = entry.getValue();
				for(int itrValues=values.length-1;itrValues>=0;itrValues--) {
					if(queryString.index()!=0) {
						queryString.append(StringPool.AMPERSAND);
					}
					queryString.append(entry.getKey());
	        		queryString.append(StringPool.EQUAL);
	        		queryString.append(values[itrValues]);
				}
			}

            String renderedPortlet = PortalUtil.renderPortlet(servletContext, servletRequest, servletResponse, 
            		new PortletWrapper(portlet){
						private static final long serialVersionUID = 229422682924083706L;
		
						@Override
		        		public boolean isUseDefaultTemplate() {
		        			return false;
		        		}
					}, queryString.toString(), false);

			List<String> markupHeaders = (List<String>)servletRequest.getAttribute(MimeResponse.MARKUP_HEAD_ELEMENT);
			if((Validator.isNotNull(markupHeaders))&&(!markupHeaders.isEmpty())) {
				StringBundler pageTopStringBundler = (StringBundler)request.getAttribute(WebKeys.PAGE_TOP);

				if (pageTopStringBundler == null) {
					pageTopStringBundler = new StringBundler();
					request.setAttribute(WebKeys.PAGE_TOP, pageTopStringBundler);
				}
				pageTopStringBundler.append(markupHeaders);
			}

            if(portlet.isUseDefaultTemplate()) {
				String  portletHeader = StringPool.BLANK, 
						portletBody = renderedPortlet,
						portletQueue = StringPool.BLANK;
				
				int portletBodyBegin = renderedPortlet.indexOf(PORTLET_BODY);
				if(portletBodyBegin>0) {
					int portletBodyEnd = renderedPortlet.lastIndexOf(DIV_END, renderedPortlet.lastIndexOf(DIV_END)-1);
					portletBodyBegin+=PORTLET_BODY.length();
					portletHeader = renderedPortlet.substring(0, portletBodyBegin);
					portletBody = renderedPortlet.substring(portletBodyBegin, portletBodyEnd);
					portletQueue = renderedPortlet.substring(portletBodyEnd);
				}
				
				servletRequest.setAttribute(PortletRequest.LIFECYCLE_PHASE,PortletRequest.RENDER_PHASE);
				servletRequest.setAttribute(WebKeys.RENDER_PORTLET, portlet);
				servletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_REQUEST, request);
				servletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE, response);
				servletRequest.setAttribute(JavaConstants.JAVAX_PORTLET_CONFIG, PortletConfigFactoryUtil.create(portlet, servletContext));
				servletRequest.setAttribute("PORTLET_CONTENT", portletBody);
				result = portletHeader+
						 PortalUtil.renderPage(servletContext, servletRequest, servletResponse, "/html/common/themes/portlet.jsp",false)+
						 portletQueue;
            }
            else {
            	result = renderedPortlet;
            }

			Set<String> runtimePortletIds = (Set<String>)request.getAttribute(
					WebKeys.RUNTIME_PORTLET_IDS);

			if (runtimePortletIds == null) {
				runtimePortletIds = new HashSet<String>();
			}

			runtimePortletIds.add(portletId);

			request.setAttribute(WebKeys.RUNTIME_PORTLET_IDS, runtimePortletIds);

        }finally {
            // Restore the state
        	themeDisplay.setScopeGroupId(currentScopeGroup);
            servletRequest.setAttribute(WebKeys.LAYOUT, currentLayout);
            servletRequest.setAttribute("OUTER_PORTLET_ID",currentOuterPortlet);
            portletDisplay.copyFrom(portletDisplayClone);
            portletDisplayClone.recycle();
            for (final String key : Collections.list((Enumeration<String>) servletRequest.getAttributeNames())) {
                if ((!requestAttributeBackup.containsKey(key))&&(!reservedAttrs.contains(key))) {
                    servletRequest.removeAttribute(key);
                }
            }
            for (final Map.Entry<String, Object> entry : requestAttributeBackup.entrySet()) {
                servletRequest.setAttribute(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }
    
    private static final String PORTLET_BODY = "<div class=\"portlet-body\">";
    private static final String DIV_END = "</div>";
}
