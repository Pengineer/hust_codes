package csdc.action.management.recruit;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import csdc.bean.Applicant;
import csdc.bean.Job;
import csdc.tool.ApplicationContainer;

/**
 * 招聘的申请管理
 * @author suwb
 *
 */
public class ApplicantAction extends InnerRecruitAction {
	
	private static final long serialVersionUID = 1L;

	private final static String HQL = "select app.name, j.name, app.applicantDate, app.status, app.id from Applicant app left join app.job j ";
	private final static String[] COLUMN = {
		"app.name",
		"j.name, app.name",
		"app.applicantDate, app.name",
		"app.status, app.name"
	};// 排序列
	private final static String PAGE_NAME = "applicantPage";
	private final static String[] SEARCH_CONDITIONS = {
		"LOWER(app.name) like :keyword",
		"LOWER(j.name) like :keyword",
		"LOWER(app.status) like :keyword"
	};
	private static final String PAGE_BUFFER_ID = "app.id";// 缓存id
	
	@Override
	public void simpleSearchBaseHql(StringBuffer hql, Map map) {
		hql.append(HQL());
	}
	
	//进入审核页面
	public String toVerify(){
		return SUCCESS;
	}
	//审核相应的招聘申请
	public String verify(){
		Applicant app = managementService.getAppById(entityId);
		app.setStatus(verifyResult);
		app.setAuditDate(new Date());
		dao.modify(app);
		return SUCCESS;
	}
	//进入招聘申请的详情页面
	public String toView(){
		jsonMap.put("entityId", entityId);
		return SUCCESS;
	}
	//查看招聘申请的详情
	public String view() throws ParseException{
		Applicant app = managementService.getAppById(entityId);
		String jobId = app.getJob().getId();
		String jobName = dao.query(Job.class, jobId).getName();
		jsonMap = managementService.initAppDate(jsonMap, app);
		jsonMap.put("appId", app.getId());
		jsonMap.put("jobName", jobName);
		jsonMap.put("jobId", jobId);
		String fileSize = null;
		if (app.getFile() != null) {//文件是否存在的判断
			InputStream is = ApplicationContainer.sc.getResourceAsStream(app.getFile());
			if (null != is) {
				try {
					fileSize = baseService.accquireFileSize(is.available());					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
		jsonMap.put("fileSize", fileSize);
		return SUCCESS;
	}
	//文件是否存在的判断
	public String validateFile() throws UnsupportedEncodingException{
		Applicant app = managementService.getAppById(entityId);
		filePath = app.getFile();
		String filename = new String(filePath.getBytes("iso8859-1"),"utf-8");
		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
			session.put("errorInfo", "文件不存在！");
		}
		return SUCCESS;
	}
	//附件流
	public InputStream getTargetFile() throws Exception{
		Applicant app = (Applicant)dao.query(Applicant.class, entityId);
		filePath = app.getFile();
		String filename="";
		if(filePath != null && filePath.length()!=0){
			filename=new String(filePath.getBytes("iso8859-1"),"utf-8");
			filePath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
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
