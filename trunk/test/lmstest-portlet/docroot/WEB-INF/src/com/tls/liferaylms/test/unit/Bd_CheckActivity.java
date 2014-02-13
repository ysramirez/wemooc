package com.tls.liferaylms.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.tls.liferaylms.test.SeleniumTestCase;
import com.tls.liferaylms.test.util.CheckPage;
import com.tls.liferaylms.test.util.Context;
import com.tls.liferaylms.test.util.CourseActivityMenu;
import com.tls.liferaylms.test.util.GetPage;
import com.tls.liferaylms.test.util.InstancePortlet;
import com.tls.liferaylms.test.util.Login;
import com.tls.liferaylms.test.util.Sleep;
import com.tls.liferaylms.test.util.TestProperties;

/**
 * @author Diego Renedo Delgado
 */
public class Bd_CheckActivity extends SeleniumTestCase {
	  String test = TestProperties.get("act.test");
	  String ext = TestProperties.get("act.ext");
	  String p2p = TestProperties.get("act.p2p");
	  String enc = TestProperties.get("act.enc");
	  String pres = TestProperties.get("act.pres");
	  String scorm = TestProperties.get("act.scorm");
	  String desa = TestProperties.get("act.desa");
	  String eval = TestProperties.get("act.eval");

	  @Test
	  public void checkActivities() throws Exception {
		  if(getLog().isInfoEnabled())getLog().info("init");
		  GetPage.getPage(driver, "", Context.getTestPage());
		  
		  Login login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();

		  boolean testLogin = login.login();
		  assertTrue("Error not logued test",testLogin);

		  if(testLogin){
			  configureP2P();
		  }
		  

		  login = new Login(driver, Context.getStudentUser2(), Context.getStudentPass2(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();

		  boolean student2Login = login.login();
		  assertTrue("Error not logued Student2",student2Login);

		  if(student2Login){
			  fillBadActivities();
		  }
		  
		  
		  login = new Login(driver, Context.getStudentUser(), Context.getStudentPass(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();
			
		  boolean studentLogin = login.login();
		  assertTrue("Error not logued",studentLogin);

		  if(studentLogin){
			  fillActivities();
		  }
		  
		  login = new Login(driver, Context.getTeacherUser(), Context.getTeacherPass(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();

		  boolean teacherLogin = login.login();
		  
		  if(teacherLogin){
			  teacherActivities();
		  }

		  login = new Login(driver, Context.getUser(), Context.getPass(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();

		  testLogin = login.login();

		  if(testLogin){
			  testActivities();
		  }

		  //Login login = new Login(driver, Context.getStudentUser(), Context.getStudentPass(), Context.getBaseUrl());
		  login = new Login(driver, Context.getStudentUser(), Context.getStudentPass(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();
		  
		  //boolean studentLogin = login.login();
		  studentLogin = login.login();

		  if(studentLogin){
			  fillP2PActivity();
		  }

		  //Login login = new Login(driver, Context.getStudentUser2(), Context.getStudentPass2(), Context.getBaseUrl());
		  login = new Login(driver, Context.getStudentUser2(), Context.getStudentPass2(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();
		  
		  //boolean studentLogin = login.login();
		  studentLogin = login.login();

		  if(studentLogin){
			  fillP2PActivity();
		  }
		  
		  //Login login = new Login(driver, Context.getTeacherUser(), Context.getTeacherPass(), Context.getBaseUrl());
		  login = new Login(driver, Context.getTeacherUser(), Context.getTeacherPass(), Context.getBaseUrl());

		  if(login.isLogin())
			  login.logout();

		  //boolean teacherLogin = login.login();
		  teacherLogin = login.login();
		  
		  if(teacherLogin){
			  teacherEval();
		  }
		  
		  if(getLog().isInfoEnabled())getLog().info("end!");
	  }
	  
	  private void teacherEval(){
		  try{
			  GetPage.getPage(driver, Context.getCoursePage(),"/reto");

			  for(String id : Context.getActivities().keySet()){

				  if(id.length()>eval.length()&&id.substring(0, eval.length()).equals(eval)){
					  WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
					  assertNotNull("Not activity found:"+id, a);
					  
					  if(getLog().isInfoEnabled())getLog().info("Click!");
					  //double click
					  a.click();
					  try{
						  a.click();
					  }catch(Exception e){}

					  String url = driver.getCurrentUrl();
					  
					  WebElement evaluation = getElement(By.id("p_p_id_evaluationtaskactivity_WAR_liferaylmsportlet_"));
					  assertNotNull("Portlet evaluation not found", evaluation);
					  WebElement conta = getElement(evaluation, By.className("newitem2"));
					  assertNotNull("Container for new item button not found", conta);
					  WebElement newitem = getElement(conta, By.tagName("a"));
					  assertNotNull("link for new item button not found", conta);
					  newitem.click();
					  
					  Sleep.sleep(1000);
					  
					  WebElement popup = getElement(By.id("_evaluationtaskactivity_WAR_liferaylmsportlet_showPopupActivities"));
					  assertNotNull("Popup for config Eval not found", popup);
					  
					  List<WebElement> inputText = getElements(popup,By.className("aui-field-input-text"));
					  assertEquals("Number of inputs not match",5, inputText.size());
					  for(WebElement input: inputText){
						  input.clear();
						  input.sendKeys("20");
					  }

					  ((JavascriptExecutor)driver).executeScript("_evaluationtaskactivity_WAR_liferaylmsportlet_doSaveActivities();");
					  
					  Sleep.sleep(1000);

					  ((JavascriptExecutor)driver).executeScript("_evaluationtaskactivity_WAR_liferaylmsportlet_doClosePopupActivities();");
					  
					  driver.get(url);
					  System.out.println("URL:"+url);
					  
					  evaluation = getElement(By.id("p_p_id_evaluationtaskactivity_WAR_liferaylmsportlet_"));
					  assertNotNull("Portlet evaluation not found", evaluation);
					  
					  WebElement butcontainer = getElement(evaluation, By.className("evaluationAvg"));
					  assertNotNull("Container for new item button not found", butcontainer);
					  
					  WebElement button = getElement(butcontainer,By.tagName("button"));
					  assertNotNull("calculate button not found", button);
					  
					  button.click();
					  try{
						  button.click();
					  }catch(Exception e){}
					  
					  WebElement submit = getElement(By.id("_evaluationtaskactivity_WAR_liferaylmsportlet_calculate"));
					  assertNotNull("No submit button found", submit);

					  submit.click();
					  try{
						  submit.click();
					  }catch(Exception e){}
					  
					  Sleep.sleep(6000);

					  driver.get(url);
					  
					  List<WebElement> results = getElements(By.className("floatl"));
					  assertEquals("Number of results not match",2, results.size());
					  
					  assertTrue("Number of text length not correct",results.get(0).getText().length()>2);
					  assertEquals("Number of result for student not match","97", results.get(0).getText().substring(0,2));
					  assertTrue("Number of text length not correct",results.get(1).getText().length()>2);
					  assertEquals("Number of result for student2 not match","77", results.get(1).getText().substring(0,2));
					  
					  butcontainer = getElement(evaluation, By.className("evaluationAvg"));
					  assertNotNull("Container for new item button not found", butcontainer);
					  
					  button = getElement(butcontainer,By.tagName("button"));
					  assertNotNull("calculate button not found", button);
					  
					  button.click();
					  try{
						  button.click();
					  }catch(Exception e){}
					  
					  WebElement publish = getElement(By.id("_evaluationtaskactivity_WAR_liferaylmsportlet_publish"));
					  publish.click();
					  try{
						  publish.click();
					  }catch(Exception e){}
				  }
			  }
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }

	  private void fillP2PActivity(){
		  try{
			  GetPage.getPage(driver, Context.getCoursePage(),"/reto");
			  
			  for(String id : Context.getActivities().keySet()){
				  if(getLog().isInfoEnabled())getLog().info("Check activity::"+id);
				  
				  

				  assertEquals("Errors in activity "+id, 0, CheckPage.check(driver));

				  if(id.length()>p2p.length()&&id.substring(0, p2p.length()).equals(p2p)){
					  WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
					  assertNotNull("Not activity found:"+id, a);

					  if(getLog().isInfoEnabled())getLog().info("Click!");
					  //double click
					  a.click();
					  try{
						  a.click();
					  }catch(Exception e){}

					  WebElement portlet = getElement(By.id("p_p_id_p2ptaskactivity_WAR_liferaylmsportlet_"));
					  assertNotNull("Not P2P portlet found", portlet);
					  					  
					  WebElement optionless = getElement(portlet, By.className("option-more"));
					  
					  //if(getLog().isInfoEnabled())getLog().info("HTML::"+portlet.getAttribute("innerHTML"));
					  
					  
					  assertNotNull("Not P2P activity found", optionless);
					  WebElement labelcol = getElement(portlet, By.className("label-col"));
					  assertNotNull("Not P2P activity span for click found", labelcol);
					  
					  optionless.click();
					  labelcol.click();
					  
					  Sleep.sleep(4000);
					  
					  WebElement textarea = getElement(optionless,By.tagName("textarea"));
					  assertNotNull("No textarea activity found", textarea);
					  textarea.clear();
					  textarea.sendKeys(TestProperties.get("act.test.3.answer"));

					  WebElement fileName = getElement(optionless,By.id("_p2ptaskactivity_WAR_liferaylmsportlet_fileName"));
					  assertNotNull("No file activity found", fileName);
					  File f = new File("docroot"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"resources"+File.separator+"encuesta.csv");
					  fileName.sendKeys(f.getAbsolutePath());

					  WebElement resultuser = getElement(optionless,By.id("_p2ptaskactivity_WAR_liferaylmsportlet_resultuser"));
					  assertNotNull("No result activity found", resultuser);
					  resultuser.sendKeys("70");
					  

					  WebElement button = getElement(optionless,By.className("button"));
					  assertNotNull("No button activity found", button);
					  button.click();
					  
					  WebElement correc = getElement(By.id("_p2ptaskactivity_WAR_liferaylmsportlet_showp2pconfrmCorrec"));
					  assertNotNull("No window activity found", button);
					  
					  WebElement submitCorrec = getElement(correc,By.id("submitCorrec"));
					  assertNotNull("No submit in window activity found", submitCorrec);
					  submitCorrec.click();
					  
					  WebElement completed = getElement(By.id("_p2ptaskactivity_WAR_liferaylmsportlet_completed"));
					  assertNotNull("Response in P2P not correct", completed);
					  completed.click();
					  
					  Sleep.sleep(1000);
					  
					  WebElement buttonClose = getElement(By.id("buttonClose"));
					  assertNotNull("Response in P2P not correct", buttonClose);
					  buttonClose.click();
					  
					  WebElement resultFather =getElement(By.id("capa3"));
					  assertNotNull("Not resultFather for P2P found", resultFather);
					  WebElement result =getElement(resultFather,By.className("color_tercero"));
					  assertNotNull("Not result for P2P found", result);
					  String text = result.getText();
					  assertTrue("Not result lenght expeted",result.getText().length()>=2);
					  String rText = text.substring(text.length()-2, text.length());
					  assertTrue("Result not expected, needed 85 or 50:"+rText,rText.equals("50")||rText.equals("85"));
				  }
			  }
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }
	  
	  private void configureP2P(){

		  try{
			  GetPage.getPage(driver, Context.getCoursePage(),"/reto");
			  changeEditMode();
			  
			  Sleep.sleep(2000);
			  for(String id : Context.getActivities().keySet()){
				  if(id.length()>p2p.length()&&id.substring(0, p2p.length()).equals(p2p)){
					  WebElement a = CourseActivityMenu.findElementActivityMenuEdit(driver,id);
					  assertNotNull("Edit link for P2P not found", a);
					  
					  a.click();
					  Sleep.sleep(2000);
						
					  driver.switchTo().frame(0);
					  
					  openColapsables();
					  
					  WebElement numVal = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_numValidaciones"));
					  assertNotNull("Not value found for P2P Activity", a);
					  numVal.clear();
					  numVal.sendKeys("1");

					  WebElement checkbox = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_resultCheckbox"));
					  assertNotNull("Not result input found for P2P Activity", checkbox);
					  checkbox.click();

					  WebElement anon = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_anonimousCheckbox"));
					  assertNotNull("Not anon input found for P2P Activity", anon);
					  anon.click();

					  WebElement uploadYear = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_uploadYear"));
					  assertNotNull("Not uploadYear input found for P2P Activity", uploadYear);
					  Calendar calendar = Calendar.getInstance();
					  uploadYear.sendKeys(String.valueOf(calendar.get(Calendar.YEAR)));
					  
					  WebElement bHolder = getElement(By.className("aui-button-holder"));
					  assertNotNull("Not bHolder found", bHolder);
					  List<WebElement> inputs = getElements(bHolder,By.tagName("input"));
					  assertEquals("Menu inputs have size incorrect", inputs.size(),2);
					  
					  //Doubleclick
					  try{
						  inputs.get(0).click();
						  inputs.get(0).click();
					  }catch(Exception e){}
	
					  Sleep.sleep(2000);
	
					  numVal = getElement(By.id("_lmsactivitieslist_WAR_liferaylmsportlet_numValidaciones"));
					  assertEquals("Not valid P2P Num Validation","1", numVal.getAttribute("value"));
					  
					  GetPage.getPage(driver, Context.getCoursePage(),"/reto");
				  }
			  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }

	  private void testActivities(){
		  try{
			  GetPage.getPage(driver, Context.getTestPage(),"");
			  			  
			  InstancePortlet.createInstance(driver, "Administraci\u00f3n de portal", "portlet_portaladmin_WAR_liferaylmsportlet");
			  
			  WebElement portaladmin = getElement(By.id("portlet_portaladmin_WAR_liferaylmsportlet"));
			  assertNotNull("Not portaladmin portlet found", portaladmin);
			  WebElement actions = getElement(portaladmin,By.className("actions"));
			  assertNotNull("Not actions portaladmin portlet found", actions);
			  WebElement p2pAct = getElement(actions,By.className("taglib-icon"));
			  p2pAct.click();
			  
			  Sleep.sleep(2000);
			  
			  boolean destroy = InstancePortlet.destroyInstance(driver, "portlet_portaladmin_WAR_liferaylmsportlet");

			  assertEquals("Portlet not destroy",true, destroy);
			  
			  
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }
	  
	  private void teacherActivities(){

		  try{
			  GetPage.getPage(driver, Context.getCoursePage(),"/reto");

			  for(String id : Context.getActivities().keySet()){
				  if(getLog().isInfoEnabled())getLog().info("Check activity::"+id);
				  WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
				  assertNotNull("Not activity found:"+id, a);

				  if(getLog().isInfoEnabled())getLog().info("Click!");
				  //double click
				  a.click();
				  try{
					  a.click();
				  }catch(Exception e){}

				  assertEquals("Errors in activity "+id, 0, CheckPage.check(driver));
				  
				  if((id.length()>pres.length()&&id.substring(0, pres.length()).equals(pres))||(id.length()>desa.length()&&id.substring(0, desa.length()).equals(desa))){
					  WebElement container = getElement(By.id("_offlinetaskactivity_WAR_liferaylmsportlet_usersSearchContainerSearchContainer"));
					  if(container==null)
						  container = getElement(By.id("_onlinetaskactivity_WAR_liferaylmsportlet_usersSearchContainerSearchContainer"));
					  
					  assertNotNull("Not container for table of Desa activities", container);
					  List<WebElement> cols2 = getElements(container,By.className("col-2"));
					  assertNotNull("Not col in table found", cols2);
					  
					  for(int i =0;i<cols2.size();i++){
						  if(i==0)
							  continue;
						  
						  if(getLog().isInfoEnabled())getLog().info("col2:"+cols2.get(i).getText());
						  WebElement al = getElement(cols2.get(i),By.tagName("a"));
						  assertNotNull("Not link for check activity", al);
						  al.click();
						  
						  Sleep.sleep(2000);
						  
						  //WebElement showppg = getElement(By.id("_offlinetaskactivity_WAR_liferaylmsportlet_showPopupGrades"));
						  WebElement showppg = getElement(By.id("_offlinetaskactivity_WAR_liferaylmsportlet_result"));
						  if(showppg==null)
							  showppg = getElement(By.id("_onlinetaskactivity_WAR_liferaylmsportlet_result"));
						  
						  assertNotNull("Not found note textarea", showppg);
						  showppg.clear();
						  showppg.sendKeys("100");
						  
						  showppg = getElement(By.id("_offlinetaskactivity_WAR_liferaylmsportlet_comments"));
						  if(showppg==null)
							  showppg = getElement(By.id("_onlinetaskactivity_WAR_liferaylmsportlet_comments"));
						  
						  assertNotNull("Not found comments for note textarea", showppg);
						  showppg.clear();
						  showppg.sendKeys(TestProperties.get("act.test.3.answer"));
						  
						  try{
							  ((JavascriptExecutor)driver).executeScript("_offlinetaskactivity_WAR_liferaylmsportlet_doSaveGrades()");
						  }catch(Exception e){
							  ((JavascriptExecutor)driver).executeScript("_onlinetaskactivity_WAR_liferaylmsportlet_doSaveGrades()");
						  }

						  Sleep.sleep(2000);
						  
						  WebElement close = getElement(By.id("closethick"));
						  close.click();

						  Sleep.sleep(2000);
						  
						  //reload DOM
						  container = getElement(By.id("_offlinetaskactivity_WAR_liferaylmsportlet_usersSearchContainerSearchContainer"));
						  if(container==null)
							  container = getElement(By.id("_onlinetaskactivity_WAR_liferaylmsportlet_usersSearchContainerSearchContainer"));
						  
						  assertNotNull("Not container for table of Desa activities", a);
						  cols2 = getElements(container,By.className("col-2"));
						  
					  }				  					  
					  
					  //SCORM
				  }else if(id.length()>scorm.length()&&id.substring(0, scorm.length()).equals(scorm)){
					  checkAndCloseScorm();
				  }
			  }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  }
	  

	  private boolean fillBadActivities(){
		  try{
			  GetPage.getPage(driver, Context.getCoursePage(),"/reto");

			  for(String id : Context.getActivities().keySet()){
				  if(getLog().isInfoEnabled())getLog().info("Check activity::"+id);
				  WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
				  assertNotNull("Not activity found:"+id, a);

				  if(getLog().isInfoEnabled())getLog().info("Click!");
				  //double click
				  a.click();
				  try{
					  a.click();
				  }catch(Exception e){}

				  assertEquals("Errors in activity "+id, 0, CheckPage.check(driver));

				  	  //Test
				  if(id.length()>test.length()&&id.substring(0, test.length()).equals(test)){
					  WebElement submit = driver.findElement(By.className("aui-button-input-submit"));
					  submit.click();
					  
					  Sleep.sleep(1000);
					  
					  WebElement popup = getElement(By.className("aui-dialog-content"));
					  assertNotNull("Not found popup container", popup);
					  WebElement submitIframe = getElement(popup,By.className("aui-buttonitem-label-only"));
					  assertNotNull("Not submit found in popup", submitIframe);
					  submitIframe.click();
					  
					  Sleep.sleep(2000);
					  
					  WebElement portletContainer = getElement(By.id("p_p_id_execactivity_WAR_liferaylmsportlet_"));
					  assertNotNull("Not found portlet container", portletContainer);
					  
					  List<WebElement> pes = getElements(portletContainer,By.tagName("p"));
					  
					  String percent = pes.get(1).getText();
					  
					  assertNotEquals("Check test note fail", "100%", percent.substring(percent.length()-4,percent.length()));
					  assertEquals("Check test note fail", "0%", percent.substring(percent.length()-2,percent.length()));

					  //Scorm
				  }else if(id.length()>scorm.length()&&id.substring(0, scorm.length()).equals(scorm)){
					  checkAndCloseScorm();
					  //Enc
				  }else if(id.length()>enc.length()&&id.substring(0, enc.length()).equals(enc)){
					  checkEnc();
					  
					  //P2P
				  }else if(id.length()>p2p.length()&&id.substring(0, p2p.length()).equals(p2p)){
					  WebElement capa1 = getElement(By.id("capa1"));
					  WebElement ta = getElement(capa1,By.tagName("textarea"));
					  ta.clear();
					  ta.sendKeys(TestProperties.get("act.p2p.text"));

					  WebElement fileName = getElement(By.id("_p2ptaskactivity_WAR_liferaylmsportlet_fileName"));
					  File f = new File("docroot"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"resources"+File.separator+"encuesta.csv");
					  fileName.sendKeys(f.getAbsolutePath());
					  
					  WebElement cb = getElement(capa1,By.className("container-buttons"));
					  WebElement input = getElement(cb,By.tagName("input"));
					  
					  input.click();
					  try{
						  input.click();
					  }catch(Exception e){}
					  
					  Sleep.sleep(1000);
					  
					  WebElement but = getElement(By.id("buttonSendP2P"));
					  assertNotNull("Not found Button for submit p2p", but);
					  but.click();
					  try{
						  input.click();
					  }catch(Exception e){}
					  
					  //Desa
				  }else if(id.length()>desa.length()&&id.substring(0, desa.length()).equals(desa)){
					  fillDesa();
				  }
				  
			  }
		  }catch(Exception e){
			  e.printStackTrace();
			  return false;
		  }
		  
		  return true;
	  }
	  
	  private boolean fillActivities(){
		  try{
			  GetPage.getPage(driver, Context.getCoursePage(),"/reto");
			  
			  for(String id : Context.getActivities().keySet()){
				  if(getLog().isInfoEnabled())getLog().info("Check activity::"+id);

				  WebElement a = CourseActivityMenu.findElementActivityMenuTotal(driver,id);
				  assertNotNull("Not activity found:"+id, a);

				  if(getLog().isInfoEnabled())getLog().info("Click!");
				  //double click
				  a.click();
				  try{
					  a.click();
				  }catch(Exception e){}
				  
				  assertEquals("Errors in activity "+id, 0, CheckPage.check(driver));
				  
				  //Test
				  if(id.length()>test.length()&&id.substring(0, test.length()).equals(test)){
					  if(getLog().isInfoEnabled())getLog().info("Test activity");
					  WebElement we = getElement(By.className("questiontype_1"));
					  assertNotNull("Not found Multiple Question container", we);
					  List<WebElement> options = getElements(we,By.className("answer"));
					  assertNotNull("Not found options", options);
					  WebElement input = getElement(options.get(0),By.tagName("input"));
					  assertNotNull("Not correct input", input);
					  input.click();

					  we = getElement(By.className("questiontype_0"));
					  assertNotNull("Not found Option Question container", we);
					  options = getElements(we,By.className("answer"));
					  assertNotNull("Not found options", options);
					  input = getElement(options.get(0),By.tagName("input"));
					  assertNotNull("Not correct input", input);
					  input.click();

					  we = getElement(By.className("questiontype_3"));
					  assertNotNull("Not found Option Question container", we);
					  WebElement ge = getElement(we,By.className("answer-fillblank"));
					  assertNotNull("Not found Father for input fillblank", ge);
					  input = getElement(ge,By.tagName("input"));
					  input.clear();
					  input.sendKeys(TestProperties.get("act.test.3.answer"));

					  we = getElement(By.className("questiontype_2"));
					  assertNotNull("Not found Option Question container", we);
					  WebElement textarea = getElement(we,By.tagName("textarea"));
					  textarea.sendKeys(TestProperties.get("act.test.3.answer"));

					  we = getElement(By.className("ui-droppable"));
					  assertNotNull("Not found Dropable container", we);
					  //first element
					  WebElement divDropable = null;
					  List<WebElement> divsDropable = getElements(we,By.tagName("div"));
					  
					  for(WebElement div: divsDropable){
						  
						  String[] etext = div.getText().split(" ");
						  if(etext.length>2&&etext[1].equals("0")){
							 divDropable = div;
							 break;
						  }
					  }
					  
					  assertNotNull("Not found Dropable option", divDropable);
					  
					  ((JavascriptExecutor)driver).executeScript("javascript:document.getElementsByClassName(\"drop\")[0].getElementsByTagName(\"input\")[0].value = '"+divDropable.getAttribute("id")+"';");

					  we = getElement(By.className("questiontype_sortable"));
					  assertNotNull("Not found Question sortable container", we);
					  
					  List<WebElement> inputSort = getElements(we, By.tagName("input"));
					  assertNotNull("Not found input for sort destination", inputSort);
					  assertEquals("Not found correct inputs number for sort",2, inputSort.size());
					  
					  String value = inputSort.get(1).getAttribute("value");
					  String[] values = value.split("&");
					  
					  StringBuilder result = new StringBuilder();
					  int sum = 0;
					  for(String valuet:values){
						  String[] ident =valuet.split("=");
						  assertEquals("Not found correct parts in sort input",2, ident.length);
						  result.append(ident[0]);
						  result.append("=");
						  
						  WebElement sorts = getElement(By.className("question_sortable"));
						  List<WebElement> lis = getElements(sorts, By.tagName("li"));
						  boolean find = false;
						  while(!find){
							  for(WebElement li : lis){
								  String text = li.getText();
								  String[] te = text.split(" ");
								  if(getLog().isInfoEnabled())getLog().info("text!"+li.getText());
								  if(te.length==3){
									  String num = ident[0].substring(ident[0].length()-2, ident[0].length()-1);
									  Integer onum = Integer.valueOf(num)+sum;
									  if(te[1].equals(String.valueOf(onum))){
										  result.append(li.getAttribute("id"));
										  find = true;
										  break;
									  }
								  }
							  }
							  if(!find){
								  sum++;
							  }
						  }
						  result.append("&");
					  }
					  
					  ((JavascriptExecutor)driver).executeScript("javascript:document.getElementsByClassName(\"questiontype_sortable\")[0].getElementsByTagName(\"input\")[1].value = '"+result.toString()+"';");

					  
					  WebElement submit = driver.findElement(By.className("aui-button-input-submit"));
					  submit.click();
					  
					  Sleep.sleep(1000);
					  
					  WebElement portletContainer = getElement(By.id("p_p_id_execactivity_WAR_liferaylmsportlet_"));
					  assertNotNull("Not found portlet container", portletContainer);
					  
					  List<WebElement> pes = getElements(portletContainer,By.tagName("p"));
					  
					  String percent = pes.get(1).getText();
					  
					  assertEquals("Check test note fail", "100%", percent.substring(percent.length()-4,percent.length()));
					  
					  //Recurso externo
				  }else if(id.length()>ext.length()&&id.substring(0, ext.length()).equals(ext)){
					  WebElement video = getElement(By.className("video"));
					  WebElement iframe = getElement(video,By.tagName("iframe"));
					  assertNotNull("No iframe for video", iframe);
					  
					  //P2P
				  }else if(id.length()>p2p.length()&&id.substring(0, p2p.length()).equals(p2p)){
					  WebElement capa1 = getElement(By.id("capa1"));
					  WebElement ta = getElement(capa1,By.tagName("textarea"));
					  ta.clear();
					  ta.sendKeys(TestProperties.get("act.p2p.text"));

					  WebElement fileName = getElement(By.id("_p2ptaskactivity_WAR_liferaylmsportlet_fileName"));
					  File f = new File("docroot"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"resources"+File.separator+"encuesta.csv");
					  fileName.sendKeys(f.getAbsolutePath());
					  
					  WebElement cb = getElement(capa1,By.className("container-buttons"));
					  WebElement input = getElement(cb,By.tagName("input"));
					  
					  input.click();
					  try{
						  input.click();
					  }catch(Exception e){}
					  
					  Sleep.sleep(1000);

					  //driver.switchTo().frame(0);
					  
					  WebElement but = getElement(By.id("buttonSendP2P"));
					  assertNotNull("Not found Button for submit p2p", but);
					  but.click();
					  try{
						  input.click();
					  }catch(Exception e){}

					  //driver.switchTo().activeElement();
					  
					  //SCORM scorm
				  }else if(id.length()>scorm.length()&&id.substring(0, scorm.length()).equals(scorm)){
					  checkAndCloseScorm();
					  //Encuesta
				  }else if(id.length()>enc.length()&&id.substring(0, enc.length()).equals(enc)){
					  checkEnc();
					  //Desa
				  }else if(id.length()>desa.length()&&id.substring(0, desa.length()).equals(desa)){
					  fillDesa();
				  }
			  }
		  }catch(Exception e){
			  e.printStackTrace();
			  return false;
		  }
		  
		  return true;
	  }
	  
	  
	  private String readFile(File file){
		  BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			if(br==null)
				return null;
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

		        while (line != null) {
		            sb.append(line);
		            sb.append('\n');
		            try {
						line = br.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		        return sb.toString();
		    } finally {
		        try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
	  }
	  
	  private boolean checkAndCloseScorm(){
		  Set<String> windows = driver.getWindowHandles();
		  assertEquals("SCORM Activity not found", 2, windows.size());
		  if(getLog().isInfoEnabled())getLog().info("do!"+windows.size());
		  
		  boolean present = false;
		  for(String window:windows){
			  driver.switchTo().window(window);
			  if (driver.getTitle().contains("SCORM Activity")) {
				  try{
					  Alert confirm =driver.switchTo().alert();
					  confirm.accept();
				  }catch(Exception e){}
				  present = true;
				  driver.close();
			  }
		  }
		  assertEquals("SCORM Activity not found", true, present);
		  
		  windows = driver.getWindowHandles();
		  assertEquals("SCORM Activity not close", 1, windows.size());

		  for(String window:windows){
			  driver.switchTo().window(window);
		  }
		  
		  WebElement webElement = getElement(By.className("description-title"));
		  String title = webElement.getText();
		  
		  assertEquals("Not correct Window", true,title.length()>scorm.length()&&title.substring(0, scorm.length()).equals(scorm));
		  
		  return true;
	  }
	  
	  private void checkEnc(){
		  List<WebElement> questions = getElements(By.className("question"));
		  
		  File f = new File("docroot"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"resources"+File.separator+"encuesta.csv");
		  String text = readFile(f);
		  StringBuilder sb = new StringBuilder();
		  for(WebElement question : questions){
			  List<WebElement> answers = getElements(question,By.className("answer"));
			  WebElement questiontext = getElement(question,By.className("questiontext"));
			  sb.append(questiontext.getText());
			  sb.append(";");
			  for(int i=0;i<answers.size();i++){
				  sb.append(answers.get(i).getText());
				  if(i+1!=answers.size())
					  sb.append(",");
			  }
			  sb.append("\n");
		  }
		  if(getLog().isInfoEnabled())getLog().info("Una!"+text);
		  if(getLog().isInfoEnabled())getLog().info("Otra!"+sb.toString());
		  
		  assertEquals("CSV not match whit questions",text, sb.toString());
		  

		  for(WebElement question : questions){
			  WebElement answer = getElement(question,By.className("answer"));
			  WebElement input = getElement(answer,By.tagName("input"));
			  input.click();
		  }
		  
		  WebElement submit = driver.findElement(By.className("aui-button-input-submit"));
		  
		  submit.click();
		  try{
			  submit.click();
		  }catch(Exception e){}
	  }
	  
	  private void fillDesa(){
		  WebElement text = getElement(By.id("_onlinetaskactivity_WAR_liferaylmsportlet_text"));
		  text.clear();
		  text.sendKeys(TestProperties.get("act.test.3.answer"));

		  WebElement submit = driver.findElement(By.className("aui-button-input-submit"));
		  submit.click();
	  }

	  private void changeEditMode(){
		  WebElement editPortlet = getElement(By.id("p_p_id_changeEditingMode_WAR_liferaylmsportlet_"));
		  assertNotNull("Not Edit portlet found", editPortlet);
			
		  List<WebElement> inputs = getElements(editPortlet,By.tagName("input"));
		  assertEquals("Not Edit portlet found", 1,inputs.size());
		  inputs.get(0).click();
	  }
	  
	  private void openColapsables(){
			List<WebElement> pcontainer = getElements(By.className("lfr-panel-titlebar"));
			assertNotNull("Not found pcontainer", pcontainer);
			
			for(WebElement we : pcontainer){
				if(getLog().isInfoEnabled())getLog().info("Click::"+we.getText());
				try{
					we.click();
				}catch(Exception e){}
				List<WebElement> spans = getElements(we,By.tagName("span"));
				if(spans!=null){
					for(WebElement span : spans){
						try{
							span.click();
						}catch(Exception e){}
					}
				}
				List<WebElement> divs = getElements(we,By.tagName("div"));
				if(spans!=null){
					for(WebElement div : divs){
						try{
							div.click();
						}catch(Exception e){}
					}
				}
				Sleep.sleep(1000);
			}
		}
}
