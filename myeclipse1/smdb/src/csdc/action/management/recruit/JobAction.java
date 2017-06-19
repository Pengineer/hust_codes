package csdc.action.management.recruit;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csdc.bean.Job;
import csdc.bean.JobTemplate;
import csdc.bean.Template;
import csdc.tool.ApplicationContainer;

/**
 * 招聘的职位管理
 * @author suwb
 *
 */
public class JobAction extends InnerRecruitAction {

	private static final long serialVersionUID = 1L;
	
	private final static String HQL = "select job.name, job.number, job.publishDate, job.endDate, job.id from Job job ";
	private final static String[] COLUMN = {
		"job.name",
		"job.number, job.name",
		"job.publishDate, job.name",
		"job.endDate, job.name"
	};// 排序列
	private final static String PAGE_NAME = "jobPage";
	private final static String[] SEARCH_CONDITIONS = {
		"LOWER(job.name) like :keyword",
		"LOWER(job.number) like :keyword",
	};
	private static final String PAGE_BUFFER_ID = "job.id";// 缓存id
	
	@Override
	public void simpleSearchBaseHql(StringBuffer hql, Map map) {
		hql.append(HQL());
	}
	
	//选择模板
	public String selectTemplate(){
		return SUCCESS;
	}
	//进入职位发布
	public String toAdd(){
		return SUCCESS;
	}
	//职位发布
	public String add(){
		Job job = new Job();
		job.setAge(age);
		job.setEndDate(endDate);
		job.setName(name);
		job.setNumber(number);
		job.setDegree(degree);
		job.setRequirement(requirement);
		job.setPublishDate(new Date());
		entityId = dao.add(job);
		String[] templateIds = selectedTemplateIds.split("; ");
		for(String templateId : templateIds){
			JobTemplate jt = new JobTemplate();
			jt.setJob(job);
			jt.setTemplate(dao.query(Template.class, templateId));
			dao.add(jt);
		}
		return SUCCESS;
	}
	//进入职位修改
	public String toModify(){
		Job job = dao.query(Job.class, entityId);
		List<String> tIds = dao.query("select t.id from JobTemplate jt left join jt.template t left join jt.job j where j.id =?", entityId); 
		List templateList = new ArrayList<Map>();
		Map templateMap = new HashMap();
		for(String tId : tIds){
			Template t = dao.query(Template.class, tId);
			templateMap.put("id", t.getId());
			templateMap.put("name", t.getName());
			templateList.add(templateMap);
		}
		jsonMap.put("templateList", templateList);
		jsonMap.put("age", job.getAge());
		jsonMap.put("endDate", job.getEndDate());
		jsonMap.put("name", job.getName());
		jsonMap.put("degree", job.getDegree());
		jsonMap.put("number", job.getNumber());
		jsonMap.put("requirement", job.getRequirement());
		return SUCCESS;
	}
	//职位修改
	public String modify(){
		Job job = dao.query(Job.class, entityId);
		List<String> jtIds = dao.query("select jt.id from JobTemplate jt left join jt.job j where j.id =?", entityId); 
		for(String jtId : jtIds){
			dao.delete(JobTemplate.class, jtId);
		}
		job.setAge(age);
		job.setEndDate(endDate);
		job.setName(name);
		job.setNumber(number);
		job.setDegree(degree);
		job.setRequirement(requirement);
		dao.modify(job);
		String[] templateIds = selectedTemplateIds.split("; ");
		for(String templateId : templateIds){
			JobTemplate jt = new JobTemplate();
			jt.setJob(job);
			jt.setTemplate(dao.query(Template.class, templateId));
			dao.add(jt);
		}
		return SUCCESS;
	}
	//进入职位查看
	public String toView(){
		jsonMap.put("entityId", entityId);
		return SUCCESS;
	}
	//职位查看
	public String view() throws IOException{
		Job job = dao.query(Job.class, entityId);
		List<String> tIds = dao.query("select t.id from JobTemplate jt left join jt.template t left join jt.job j where j.id =?", entityId); 
		List templateList = new ArrayList<Map>();
		for(String tId : tIds){
			Map templateMap = new HashMap();
			Template t = dao.query(Template.class, tId);
			templateMap.put("id", t.getId());
			templateMap.put("name", t.getName());
			InputStream is = ApplicationContainer.sc.getResourceAsStream(t.getTemplateFile());
			String fileSize = null;
			if(is != null){
				fileSize = baseService.accquireFileSize(is.available());
			}
			templateMap.put("fileSize", fileSize);			
			templateList.add(templateMap);
		}
		jsonMap.put("templateList", templateList);
		jsonMap.put("age", job.getAge());
		jsonMap.put("publishDate", job.getPublishDate());
		jsonMap.put("endDate", job.getEndDate());
		jsonMap.put("name", job.getName());
		jsonMap.put("number", job.getNumber());
		jsonMap.put("requirement", job.getRequirement());
		jsonMap.put("degree", job.getDegree());
		jsonMap.put("jobId", job.getId());
		return SUCCESS;
	}
	//职位删除
	public String delete(){
		for(String id : entityIds){
			Job job = dao.query(Job.class, id);
			List<String> jtIds = (List<String>)dao.query("select jt.id from JobTemplate jt left join jt.job j where j.id =?", id);
			for(String jtId : jtIds){
				JobTemplate jt = dao.query(JobTemplate.class, jtId);
				dao.delete(jt);
			}
			dao.delete(job);
		}
		return SUCCESS; 
	}
	//删除校验[如果已经有人申请过次职位，则无法删除]
//	public void validateDelete(){
//		for(String id : entityIds){
//			List<Applicant> applicant = (List<Applicant>)dao.query("select app from Applicant app left join app.job j where j.id =?", id);
//			if(applicant!=null || !applicant.isEmpty()){
//				this.addFieldError("errorInfo", "此职位已有人申请，无法删除!");
//			}
//		}
//	}
	//文件是否存在的判断
	public String validateFile() throws UnsupportedEncodingException{
		Template t = dao.query(Template.class, entityId);
		filePath = t.getTemplateFile();
		String filename = new String(filePath.getBytes("iso8859-1"),"utf-8");
		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
			session.put("errorInfo", "文件不存在！");
		}
		return SUCCESS;
	}
	//附件流
	public InputStream getTargetFile() throws Exception{
		Template t = (Template)dao.query(Template.class, entityId);
		filePath = t.getTemplateFile();
		String filename="";
		if(filePath != null && filePath.length()!=0){
			filename=new String(filePath.getBytes("iso8859-1"),"utf-8");
			filePath=new String(t.getName().getBytes(), "ISO8859-1") + "." + filename.split("\\.")[1];
			return ApplicationContainer.sc.getResourceAsStream(filename);
		}
		return null;
	}
	
	@Override
	public String pageName() {
		return PAGE_NAME;
	}

	@Override
	public String[] column() {
		return COLUMN;
	}

	@Override
	public String HQL() {
		return HQL;
	}

	@Override
	public String[] searchConditions() {
		return SEARCH_CONDITIONS;
	}

	@Override
	public Object[] advSearchCondition() {
		return null;
	}
	@Override
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
}
