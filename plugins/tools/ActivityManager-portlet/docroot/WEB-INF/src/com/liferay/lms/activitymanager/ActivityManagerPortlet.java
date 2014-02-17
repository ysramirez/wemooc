package com.liferay.lms.activitymanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.lms.model.Course;
import com.liferay.lms.model.LearningActivity;
import com.liferay.lms.model.Module;
import com.liferay.lms.service.CourseLocalServiceUtil;
import com.liferay.lms.service.LearningActivityLocalServiceUtil;
import com.liferay.lms.service.ModuleLocalServiceUtil;
import com.liferay.lms.util.CourseComparator;
import com.liferay.lmssa.service.ActManAuditLocalServiceUtil;
import com.liferay.manager.CleanLearningActivity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ActivityManagerPortlet extends MVCPortlet {
	Log log = LogFactoryUtil.getLog(ActivityManagerPortlet.class);

	protected String viewJSP;
	protected String detailJSP;
	protected String userDetailJSP;

	public void init() throws PortletException {
		this.viewJSP = getInitParameter("view-template");
		this.detailJSP = getInitParameter("detail-template");
	    this.userDetailJSP = getInitParameter("userdetail-template");
	}

	public void doView(RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		String view = ParamUtil.getString(renderRequest, "view", "");
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);  
		if (view != null && "detail".equals(view)) {
			String id = ParamUtil.getString(renderRequest, "id", "");
			long activeModuleId = ParamUtil.getLong(renderRequest, "activeModuleId", 0);;
			Long lid = null;
			HashMap<Long, List<LearningActivity>> learningActivities = new HashMap<Long, List<LearningActivity>>();
			try{
				lid = Long.parseLong(id);
			}catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
			
			if(lid!=null){
				Course course = null;
				try {
					course = CourseLocalServiceUtil.getCourse(lid);
				} catch (PortalException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				}
				if(course!=null){
					List<Module> modules = null;
					try {
						modules = ModuleLocalServiceUtil.findAllInGroup(course.getGroupCreatedId());
					} catch (SystemException e) {
						e.printStackTrace();
					}
					
					if(modules!=null){
						for(Module module:modules){
							List<Long> lacts = null;
							try {
								lacts = LearningActivityLocalServiceUtil.getLearningActivityIdsOfModule(module.getModuleId());
							} catch (SystemException e) {
								e.printStackTrace();
							}
							
							if(lacts!=null){
								List<LearningActivity> acts = new ArrayList<LearningActivity>();
								for(Long idact : lacts){
									try {
										LearningActivity la = LearningActivityLocalServiceUtil.getLearningActivity(idact);
										if(la!=null){
											la.setNew( LearningActivityLocalServiceUtil.isLearningActivityDeleteTries(Long.valueOf(la.getTypeId())));
											acts.add(la);
										}
										
									} catch (PortalException e) {
										e.printStackTrace();
									} catch (SystemException e) {
										e.printStackTrace();
									}
								}
								
								learningActivities.put(module.getModuleId(), acts);
							}
						}
					}
					renderRequest.setAttribute("activeModuleId", activeModuleId);
					renderRequest.setAttribute("modules", modules);
					renderRequest.setAttribute("learningActivities", learningActivities);
					renderRequest.setAttribute("course", course);
				}

				include(this.detailJSP, renderRequest, renderResponse);
			}
		}else if ((view != null) && ("users".equals(view))) {
		      String scourse = ParamUtil.getString(renderRequest, "course", "");
		      String sla = ParamUtil.getString(renderRequest, "la", "");
		      String text = ParamUtil.getString(renderRequest, "text", "");

		      Course course = null;
		      try {
		        course = CourseLocalServiceUtil.getCourse(Long.parseLong(scourse));
		      } catch (PortalException e){
		        if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
		        if (this.log.isDebugEnabled()) e.printStackTrace(); 
		      }
		      catch (SystemException e){
		    	  if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
		    	  if (this.log.isDebugEnabled()) e.printStackTrace();  
		      }catch (NumberFormatException e){
		        if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
		        if (this.log.isDebugEnabled()) e.printStackTrace();
		      }

		      LearningActivity la = null;
		      try {
		        la = LearningActivityLocalServiceUtil.getLearningActivity(Long.parseLong(sla));
		      } catch (PortalException e) {
		        if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
		        if (this.log.isDebugEnabled()) e.printStackTrace(); 
		      }
		      catch (SystemException e) { if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
		        if (this.log.isDebugEnabled()) e.printStackTrace();  } catch (NumberFormatException e)
		      {
		        if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
		        if (this.log.isDebugEnabled()) e.printStackTrace();
		      }

		      if ((la != null) && (course != null))
		      {
		    	  
		    	  renderRequest.setAttribute("course", course);
		    	  renderRequest.setAttribute("la", la);
		    	  renderRequest.setAttribute("text", text);
		    	  long[] users = null;
		    	  try {
		    		  users = UserLocalServiceUtil.getGroupUserIds(course.getGroupCreatedId());
		    	  } catch (SystemException e) {
				      if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
				      if (this.log.isDebugEnabled()) e.printStackTrace();
		    	  }

		    	  if(text!=null&&!"".equals(text)){
		    		  SearchContext searchContext=new SearchContext();
		    		  searchContext.setCompanyId(themeDisplay.getCompanyId());
		    		  
		    		  BooleanQuery query = BooleanQueryFactoryUtil.create(searchContext);
		    		  query.addRequiredTerm("companyId", themeDisplay.getCompanyId());
		    		  query.addRequiredTerm("entryClassName", User.class.getName());

		    		  BooleanQuery queryText = BooleanQueryFactoryUtil.create(searchContext);
		    		  BooleanQuery queryClass = BooleanQueryFactoryUtil.create(searchContext);
		    		  try {
		    			  queryClass.addTerm("emailAddress", text, true);
		    			  queryText.add(queryClass,  BooleanClauseOccur.SHOULD);
		    		  } catch (ParseException e) {
		    			  e.printStackTrace();
		    		  }

		    		  queryClass = BooleanQueryFactoryUtil.create(searchContext);
		    		  try {
		    			  queryClass.addTerm("firstName", text, true);
		    			  queryText.add(queryClass,  BooleanClauseOccur.SHOULD);
		    		  } catch (ParseException e) {
		    			  e.printStackTrace();
		    		  }

		    		  queryClass = BooleanQueryFactoryUtil.create(searchContext);
		    		  try {
		    			  queryClass.addTerm("lastName", text, true);
		    			  queryText.add(queryClass,  BooleanClauseOccur.SHOULD);
		    		  } catch (ParseException e) {
		    			  e.printStackTrace();
		    		  }

		    		  queryClass = BooleanQueryFactoryUtil.create(searchContext);
		    		  try {
		    			  queryClass.addTerm("middleName", text, true);
		    			  queryText.add(queryClass,  BooleanClauseOccur.SHOULD);
		    		  } catch (ParseException e) {
		    			  e.printStackTrace();
		    		  }

		    		  queryClass = BooleanQueryFactoryUtil.create(searchContext);
		    		  try {
		    			  queryClass.addTerm("screenName", text, true);
		    			  queryText.add(queryClass,  BooleanClauseOccur.SHOULD);
		    		  } catch (ParseException e) {
		    			  e.printStackTrace();
		    		  }

		    		  try {
		    			  queryClass.addTerm("screenName", text, true);
		    			  query.add(queryText,  BooleanClauseOccur.MUST);
		    		  } catch (ParseException e) {
		    			  e.printStackTrace();
		    		  }
		    		  
		    		  
		    		  try {
		    			  Hits hits = SearchEngineUtil.search(searchContext, query);
		    			  Document[] docs = hits.getDocs();
		    			  if(docs.length<=0){
		    				  users = null;
		    			  }else{
		    				  List<Long> prov = new ArrayList<Long>();
		    				  for(Document doc : docs){
		    					  try{
			    					  long id = Long.parseLong(doc.get(Field.ENTRY_CLASS_PK));
			    					  for(long userId : users){
			    						  if(userId==id){
			    							  prov.add(id);
			    							  break;
			    						  }
			    					  }
		    					  }catch(Exception e){}
		    				  }
		    				  users = new long[prov.size()];
		    				  for(int i = 0; i < prov.size(); i++) users[i] = prov.get(i);
		    			  }
		    		  } catch (SearchException e) {
		    			  e.printStackTrace();
		    		  }
		    	  }
		    	  renderRequest.setAttribute("users", users);
		    	  include(this.userDetailJSP, renderRequest, renderResponse);
		      }
		    } else {
			String freetext = ParamUtil
					.getString(renderRequest, "freetext", "");

			SearchContext scon = new SearchContext();
			scon.setCompanyId(themeDisplay.getCompanyId());
			Sort sort = new Sort(Field.TITLE, Sort.STRING_TYPE, true);
			scon.setSorts(new Sort[]{sort});

			if ((freetext != null) && (!"".equals(freetext))) {
				renderRequest.setAttribute("freetext", freetext);
				scon.setKeywords(freetext + "*");
			}

			Indexer indexer = IndexerRegistryUtil.getIndexer(Course.class);
			Hits hits = null;
			Document[] docs = (Document[]) null;
			try {
				hits = indexer.search(scon);
				docs = hits.getDocs();
			} catch (SearchException e) {
				e.printStackTrace();
			}

			if (docs != null) {
				if (this.log.isDebugEnabled())this.log.debug("Result size::" + docs.length);
				List<Document> ldocs = new ArrayList<Document>(Arrays.asList(docs));
				
				CourseComparator comparator = new CourseComparator(themeDisplay.getLocale());
				
				Collections.sort(ldocs, new Comparator<Document>() {
					
				    @Override
				    public int compare(final Document object1, final Document object2) {
				        return object1.get(Field.TITLE).compareTo(object2.get(Field.TITLE));
				    }
				} );
				
				renderRequest.setAttribute("docs", ldocs);
			}
			
			String error = ParamUtil.getString(renderRequest, "error", "");
			String status = ParamUtil.getString(renderRequest, "status", "");
			
			if(!"".equals(error)){
				SessionErrors.add(renderRequest, "error1");
			}
			
			if(!"".equals(status)){
				if("ko".equals(status)){
					SessionErrors.add(renderRequest, "ko");
				}else{
					SessionMessages.add(renderRequest, "ok");
				}
			}
			
			int auditsnum = ActManAuditLocalServiceUtil.countBycompanyId(themeDisplay.getCompanyId());
			renderRequest.setAttribute("auditsnum", auditsnum);
			renderRequest.setAttribute("state", Integer.valueOf(0));
			renderRequest.setAttribute("active", CleanLearningActivity.isRunning());
			include(this.viewJSP, renderRequest, renderResponse);
		}
	}

	@ProcessAction(name = "search")
	public void search(ActionRequest request, ActionResponse response) {
		String freetext = ParamUtil.getString(request, "freetext", "");
		String state = ParamUtil.getString(request, "state", "");

		response.setRenderParameter("freetext", freetext);
		response.setRenderParameter("state", state);
	}

	@ProcessAction(name = "viewCourse")
	public void viewCourse(ActionRequest request, ActionResponse response) {
		if (log.isDebugEnabled())log.debug("viewCourse");
		String view = ParamUtil.getString(request, "view", "detail");
		String id = ParamUtil.getString(request, "id", "");

		response.setRenderParameter("view", view);
		response.setRenderParameter("id", id);
	}

	@ProcessAction(name = "fix")
	public void fix(ActionRequest request, ActionResponse response) {
		if (log.isDebugEnabled())log.debug("fix");
		String sid = ParamUtil.getString(request, "id", "");
		String action = ParamUtil.getString(request, "action", "");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute("THEME_DISPLAY");
		
		Long id = null;
		LearningActivity la = null;
		try{
			id = Long.parseLong(sid);
			la = LearningActivityLocalServiceUtil.getLearningActivity(id);
		}catch(Exception e){
	        if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
	        if (this.log.isDebugEnabled()) e.printStackTrace();
			response.setRenderParameter("error", "no-activity");
		}

		if(la!=null){
			PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();
			if( permissionChecker.hasPermission(la.getGroupId(), LearningActivity.class.getName(), la.getActId(), ActionKeys.UPDATE)){
				
				if(action.equals("all")){
					cleanLearningActivityTries(la,themeDisplay.getUser());
					response.setRenderParameter("status", "ok");
				}else if(action.equals("notpassed")){
					cleanLearningActivityTriesPassed(la,themeDisplay.getUser());
					response.setRenderParameter("status", "ok");
				}
				
			}else{
				response.setRenderParameter("error", "no-permissions");
			}
		}else{
			response.setRenderParameter("error", "no-activity");
		}
		
		Course course = null;
		String courseId = "0";
		try {
			course = CourseLocalServiceUtil.getCourseByGroupCreatedId(ModuleLocalServiceUtil.getModule(la.getModuleId()).getGroupId());
			courseId = String.valueOf(course.getCourseId());
		} catch (PortalException e) {
			if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
			if (this.log.isDebugEnabled()) e.printStackTrace();
		} catch (Exception e) { e.printStackTrace(); return;}
		
	    response.setRenderParameter("id", courseId);
	    response.setRenderParameter("la", sid);
	    response.setRenderParameter("view", "detail");
	    response.setRenderParameter("activeModuleId", String.valueOf(la.getModuleId()));
	}
	
	@ProcessAction(name="users")
	public void users(ActionRequest request, ActionResponse response) {
		if (this.log.isDebugEnabled()) this.log.debug("users");
	    String sid = ParamUtil.getString(request, "id", "");
	    String course = ParamUtil.getString(request, "course", "");

	    response.setRenderParameter("text", ParamUtil.getString(request, "text", ""));
	    response.setRenderParameter("view", "users");
	    response.setRenderParameter("course", course);
	    response.setRenderParameter("la", sid);
	}

	@ProcessAction(name="fixUser")
	public void fixUser(ActionRequest request, ActionResponse response) throws IOException, PortletException {
		if (log.isDebugEnabled())log.debug("fixUser");
		String userid = ParamUtil.getString(request, "userid", "");
		String actid = ParamUtil.getString(request, "id", "");
		String action = ParamUtil.getString(request, "action", "");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute("THEME_DISPLAY");
		
		LearningActivity la = null;
		try{
			la = LearningActivityLocalServiceUtil.getLearningActivity(Long.parseLong(actid));
		}catch(Exception e){
			e.printStackTrace();
			response.setRenderParameter("error", "no-activity");
		}
		
		User user = null;
		try{
			user = UserLocalServiceUtil.getUser(Long.parseLong(userid));
		}catch(Exception e){
	        if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
	        if (this.log.isDebugEnabled()) e.printStackTrace();
			response.setRenderParameter("error", "no-user");
		}
		
		if(user!=null&&la!=null){
			if(action.equals("deleteTries")){
				cleanLearningActivityTriesUser(la,user,themeDisplay.getUser());
				response.setRenderParameter("status", "ok");
			}else{
				response.setRenderParameter("status", "ko");
			}
		}else{
			response.setRenderParameter("status", "ko");
		}
		
		Course course = null;
		String courseId = "0";
		try {
			course = CourseLocalServiceUtil.getCourseByGroupCreatedId(ModuleLocalServiceUtil.getModule(la.getModuleId()).getGroupId());
			courseId = String.valueOf(course.getCourseId());
		} catch (PortalException e) {
			if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
			if (this.log.isDebugEnabled()) e.printStackTrace();
		} catch (Exception e) { e.printStackTrace(); return;}
		
	    response.setRenderParameter("course", courseId);
	    response.setRenderParameter("la", actid);
	    response.setRenderParameter("view", "users");
	}
	
	@ProcessAction(name="recalculate")
	public void recalculate(ActionRequest request, ActionResponse response) {
		if (log.isDebugEnabled())log.debug("recalculate");
		String moduleId = ParamUtil.getString(request, "id", "");
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute("THEME_DISPLAY");
		
		Module module = null;
		try{
			module = ModuleLocalServiceUtil.getModule(Long.parseLong(moduleId));
		}catch(Exception e){
	        if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
	        if (this.log.isDebugEnabled()) e.printStackTrace();
		}
		
		if(module!=null){
			recalculateModule(module,themeDisplay.getUser());
			response.setRenderParameter("status", "ok");
		}else{
			response.setRenderParameter("status", "ko");
		}
		
		Course course = null;
		String courseId = "0";
		try {
			course = CourseLocalServiceUtil.getCourseByGroupCreatedId(ModuleLocalServiceUtil.getModule(Long.parseLong(moduleId)).getGroupId());
			courseId = String.valueOf(course.getCourseId());
		} catch (PortalException e) {
			if (this.log.isInfoEnabled()) this.log.info(e.getMessage());
			if (this.log.isDebugEnabled()) e.printStackTrace();
		} catch (Exception e) { e.printStackTrace(); return;}
		
	    response.setRenderParameter("id", courseId);
	    response.setRenderParameter("view", "detail");
	    response.setRenderParameter("activeModuleId", moduleId);
	}
	
	private void cleanLearningActivityTriesPassed(LearningActivity la,User userc){
		if (this.log.isDebugEnabled()) this.log.debug("senMessage liferay/lms/cleanTriesNotPassed");
		Message message=new Message();
		message.put("learningActivity",la);
		message.put("userc",userc);
		MessageBusUtil.sendMessage("liferay/lms/cleanTriesNotPassed", message);
	}
	
	private void cleanLearningActivityTries(LearningActivity la, User userc){
		if (this.log.isDebugEnabled()) this.log.debug("senMessage liferay/lms/cleanTries");
		Message message=new Message();
		message.put("learningActivity",la);
		message.put("userc",userc);
		MessageBusUtil.sendMessage("liferay/lms/cleanTries", message);
	}
	
	private void recalculateModule(Module module,User userc){
		if (this.log.isDebugEnabled()) this.log.debug("senMessage liferay/lms/recalculateModule");
		Message message=new Message();
		message.put("module",module);
		message.put("userc",userc);
		MessageBusUtil.sendMessage("liferay/lms/recalculateModule", message);
	}

	private void cleanLearningActivityTriesUser(LearningActivity la,User user,User userc){
		if (this.log.isDebugEnabled()) this.log.debug("senMessage liferay/lms/cleanTriesUser");
		Message message=new Message();
		message.put("learningActivity",la);
		message.put("user",user);
		message.put("userc",userc);
		MessageBusUtil.sendMessage("liferay/lms/cleanTriesUser", message);
	}

	protected void include(String path, RenderRequest renderRequest,RenderResponse renderResponse) throws IOException, PortletException {
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}
}