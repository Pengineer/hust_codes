package csdc.action.portal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import csdc.bean.Applicant;
import csdc.bean.Job;
import csdc.bean.Template;
import csdc.dao.IHibernateBaseDao;
import csdc.service.IPortalService;
import csdc.service.IUploadService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.WordExport;
import csdc.tool.bean.FileRecord;

/**
 * 门户网站的招聘管理
 * @author suwb
 *
 */
public class OuterRecruitAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	@Autowired
	protected IHibernateBaseDao dao;
	@Autowired
	private IPortalService portalService;
	@Autowired
	protected IUploadService uploadService;	//异步文件上传
	
	//前后台交互数据
	private List<String[]> jobList;//职位列表数据
	private List<String[]> applicantList;//职位申请列表
	private String errorInfo;//前台错误提示
	private Map session = ActionContext.getContext().getSession();
	private Map json = new HashMap();// json对象容器
	private String fileFileName;//导出文件名
	private String filePath;//文件路径
	//前后台交互参数
	private int applicantType;//[0：新建招聘信息；1：修改本次招聘信息]
	private String templateId;//模板id
	private String jobId;//职位id
	private String appId;//招聘申请id（新建或修改时的id，用于跳转到详情页面）
	private int verifyResult;//审核结果[1：审核通过；2：审核不通过]
	private String email;//邮箱[用于用户校验]
	private String idCardNumber;//身份证[用于用户校验]
	private int fileType;//文件类型[1:申请书；2：协议书]
	//人员信息
	private String name;//姓名
	private String gender;//性别
	private String ethnic;//民族
	private String birthplace;//籍贯（省、市、自治区）
	private String birthday;//生日
	private String membership;//政治面貌
	private String mobilePhone;//电话
	private String qq;//qq
	private String address;//地址
	private String photoFile;//照片
	//招聘自有信息
	private String major;//专业
	private String education;//学历

	//主页的职位列表[显示条数<=5]
	public String searchJob(){
		jobList = portalService.searchJob();
		if(jobList.size()>5){
			jobList = jobList.subList(0, 5);
		}
		return SUCCESS;
	}
	//招聘信息的职位列表[显示所有]
	public String searchJobs(){
		jobList = portalService.searchJob();
		return SUCCESS;
	}
	//职位详情
	public String viewJob() throws IOException{
		Job job = dao.query(Job.class, jobId);
		List<String> tIds = dao.query("select t.id from JobTemplate jt left join jt.template t left join jt.job j where j.id =?", jobId); 
		List templateList = new ArrayList<Map>();
		for(String tId : tIds){
			Map templateMap = new HashMap();
			Template t = dao.query(Template.class, tId);
			templateMap.put("id", t.getId());
			templateMap.put("name", t.getName());
			InputStream is = ApplicationContainer.sc.getResourceAsStream(t.getTemplateFile());
			String fileSize = null;
			if(is != null){
				fileSize = portalService.accquireFileSize(is.available());
			}
			templateMap.put("fileSize", fileSize);
			templateList.add(templateMap);
		}
		session.put("templateList", templateList);
		session.put("job", job);
		return SUCCESS;
	}
	//进入职位申请
	public String toApply(){
		session.put("jobId", jobId);
		String photoGroupId = "photo_applicant_add";
		uploadService.resetGroup(photoGroupId);
		String fileGroupId = "file_applicant_add";
		uploadService.resetGroup(fileGroupId);
		return SUCCESS;
	}
	//用户身份信息校验
	//TODO 用户信息填充时的照片处理
	public String getInfo() throws ParseException{
		Map map = new HashMap();
		map.put("jobId", jobId);
		map.put("email", email);
		map.put("idCardNumber", idCardNumber);
		String hql = "select app from Applicant app left join app.job j where app.email=:email and app.idCardNumber=:idCardNumber and j.id=:jobId";
		Applicant app = (Applicant)dao.queryUnique(hql, map);
		if(email.length()<=0||idCardNumber.length()<=0) {
			json.put("errorInfo", "身份证号和邮箱不能为空！");
		}else { 
			if(app!=null){
				if(app.getStatus()!=0){
					json.put("errorInfo", "您已申请过此职位并且该申请已进入审核流程，无法再次申请！");
				}else {//默认为修改该次申请信息
					json = portalService.initInfo(json, app);
					json.put("applicantInfo", 1);//[1:由前台填充申请的个人信息和招聘信息；0：无须填充数据]
					json.put("applicantType", 1);
				}
			}else {//新建信息
				String l = "select app.id from Applicant app where app.email=:email and app.idCardNumber=:idCardNumber order by app.applicantDate";
				List idList = dao.query(l, map);			
				if(idList.size()>0){//表示数据库中有记录
					String id = idList.get(0).toString();
					map.put("id", id);
					app = (Applicant)dao.queryUnique("select app from Applicant app where app.id=:id", map);
					json = portalService.initInfo(json, app);
					json.put("applicantInfo", 1);//[1:由前台填充申请的个人信息和招聘信息；0：无须填充数据]
					json.put("applicantType", 0);//表示要添加一条数据
					json.put("photoFile", app.getPhotoFile());//[信息直接填充时无需重新上传照片]
				}else {//表示数据库中无记录
					json.put("applicantInfo", 0);//[1:由前台填充申请的个人信息和招聘信息；0：无须填充数据]
					json.put("applicantType", 0);
				}
			}
		}		
		return SUCCESS;
	}
	//职位申请
	public String apply() throws ParseException{
		Map map = new HashMap();
		map.put("jobId", jobId);
		Job thisJob = (Job)dao.queryUnique("select j from Job j where j.id=:jobId", map);
		Applicant app = null;
		if(applicantType==0){
			app = new Applicant();
			appId = dao.add(app);
		}else if(applicantType==1){
			map.put("appId", appId);
			app = (Applicant)dao.queryUnique("select app from Applicant app where app.id=:appId", map);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		app.setApplicantDate(new Date());
		app.setBirthday(sdf.parse(birthday));
		app.setBirthplace(birthplace);
		app.setEmail(email);
		app.setEthnic(ethnic);
		app.setGender(gender);
		app.setIdCardNumber(idCardNumber);
		app.setEducation(education);
		app.setJob(thisJob);
		app.setMajor(major);
		app.setMembership(membership);
		app.setMobilePhone(mobilePhone);
		app.setName(name);
		app.setQq(qq);
		app.setAddress(address);
		//处理照片
		String photoGroupId = "photo_applicant_add";
		String PhotoSavePath = (String) ApplicationContainer.sc.getAttribute("RecruitPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(photoGroupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(PhotoSavePath, fileRecord.getOriginal());
			app.setPhotoFile(newPhotoPath);//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(app.getPhotoFile())));//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		//处理附件
		String fileGroupId = "file_applicant_add";
		String fileSavePath = (String) ApplicationContainer.sc.getAttribute("RecruitFileUploadPath");//设置附件的路径
		for (FileRecord fileRecord : uploadService.getGroupFiles(fileGroupId)) {
			String newFilePath = FileTool.getAvailableFilename(fileSavePath, fileRecord.getOriginal());
			app.setFile(newFilePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(app.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		uploadService.flush(photoGroupId);
		uploadService.flush(fileGroupId);
		dao.modify(app);
		return SUCCESS;
	}
	
	//我的职位申请-列表 验证
	public void validateSearchApplicant() {
		Map map = new HashMap();
		map.put("email", email);
		map.put("idCardNumber", idCardNumber);
		applicantList = (List<String[]>)dao.query("select j.name, app.id, app.name, app.applicantDate, app.status from Applicant app left join app.job j where app.email=:email and app.idCardNumber=:idCardNumber", map);
		if(applicantList.size()<=0) {
			this.addFieldError("errorInfo", "您还未申请任何职位");
		}else {
			applicantList = (List<String[]>)dao.query("select j.name, app.id, app.name, app.applicantDate, app.status from Applicant app left join app.job j where app.idCardNumber=:idCardNumber", map);
		}
	}
	//我的职位申请-列表
	public String searchApplicant(){
			return SUCCESS;
	}
	//进入修改职位申请
	public String toModifyApplicant(){
		Map map = new HashMap();
		map.put("appId", appId);
		Applicant app = (Applicant)dao.queryUnique("select app from Applicant app where app.id=:appId", map);		
		//将已有的照片加入文件组，以在编辑页面显示
		String photoGroupId = "photo_" + app.getId();
		uploadService.resetGroup(photoGroupId);
		String photoFileRealPath = ApplicationContainer.sc.getRealPath(app.getPhotoFile());
		if (photoFileRealPath != null) {
			uploadService.addFile(photoGroupId, new File(photoFileRealPath));
		}
		//将已有的附件加入文件组，以在编辑页面显示
		String fileGroupId = "file_" + app.getId();
		uploadService.resetGroup(fileGroupId);
		String fileRealPath = ApplicationContainer.sc.getRealPath(app.getFile());
		if (fileRealPath != null) {
			uploadService.addFile(fileGroupId, new File(fileRealPath));
		}
		if(app.getStatus()!=0){
			session.put("errorInfo", "此职位申请已进入审核流程，无法修改！");
			session.put("app", app);
		}else {
			session.put("app", app);
		}
		return SUCCESS;
	}
	//修改职位申请
	public String modifyApplicant() throws ParseException{
		Map map = new HashMap();
		map.put("appId", appId);
		Applicant app = (Applicant)dao.queryUnique("select app from Applicant app where app.id=:appId", map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		app.setBirthday(sdf.parse(birthday));
		app.setBirthplace(birthplace);
		app.setEmail(email);
		app.setEthnic(ethnic);
		app.setGender(gender);
		app.setIdCardNumber(idCardNumber);
		app.setEducation(education);
		app.setMajor(major);
		app.setMembership(membership);
		app.setMobilePhone(mobilePhone);
		app.setName(name);
		app.setQq(qq);
		app.setAddress(address);
		//处理照片
		String photoGroupId = "photo_" + appId;
		String photoSavePath = (String) ApplicationContainer.sc.getAttribute("RecruitPictureUploadPath");
		for (FileRecord fileRecord : uploadService.getGroupFiles(photoGroupId)) {
			String newPhotoPath = FileTool.getAvailableFilename(photoSavePath, fileRecord.getOriginal());
			app.setPhotoFile(newPhotoPath);	//设置用户照片的新路径
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(app.getPhotoFile())));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		//处理附件
		String fileGroupId = "file_" + app.getId();
		String fileSavePath = (String) ApplicationContainer.sc.getAttribute("RecruitFileUploadPath");//设置附件的路径
		for (FileRecord fileRecord : uploadService.getGroupFiles(fileGroupId)) {
			String newFilePath = FileTool.getAvailableFilename(fileSavePath, fileRecord.getOriginal());
			app.setFile(newFilePath);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(app.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		uploadService.flush(photoGroupId);
		uploadService.flush(fileGroupId);
		dao.modify(app);
		return SUCCESS;
	}
	//我的职位申请-详情
	public String viewApplicant(){
		Map map = new HashMap();
		map.put("appId", appId);
		Applicant app = (Applicant)dao.queryUnique("select app from Applicant app where app.id=:appId", map);
		String jobName = dao.query(Job.class, app.getJob().getId()).getName();
		session.put("jobName", jobName);
		session.put("app", app);
		String fileSize = null;
		if (app.getFile() != null) {//文件是否存在的判断
			InputStream is = null;
			is = ApplicationContainer.sc.getResourceAsStream(app.getFile());
			if (null != is) {
				try {
					fileSize = portalService.accquireFileSize(is.available());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
		session.put("fileSize", fileSize);
		return SUCCESS;
	}
	//附件下载
	public String fileDownload() {
		return SUCCESS;
	}
	//附件流
	public InputStream getTargetFile() throws Exception{
		Applicant app = (Applicant)dao.query(Applicant.class, appId);
		filePath = app.getFile();
		String filename="";
		if(filePath != null && filePath.length()!=0){
			filename=new String(filePath.getBytes("iso8859-1"),"utf-8");
			filePath=new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			return ApplicationContainer.sc.getResourceAsStream(filename);
		 }
		return null;
	}
	//模板下载
	public String templateDownload() {
		return SUCCESS;
	}
	//模板流
	public InputStream getTargetTemplate() throws Exception{
		Template t = (Template)dao.query(Template.class, templateId);
		filePath = t.getTemplateFile();
		String filename="";
		if(filePath != null && filePath.length()!=0){
			filename=new String(filePath.getBytes("iso8859-1"),"utf-8");
			filePath=new String(t.getName().getBytes(), "ISO8859-1") + "." + filename.split("\\.")[1];
			return ApplicationContainer.sc.getResourceAsStream(filename);
		}
		return null;
	}
	//文件是否存在的判断
	public String validateFile() throws UnsupportedEncodingException{
		if(fileType==1){
			Applicant app = (Applicant)dao.query(Applicant.class, appId);
			filePath = app.getFile();			
		}else if(fileType==2){
			Template t = (Template)dao.query(Template.class, templateId);
			filePath = t.getTemplateFile();			
		}
		String filename = new String(filePath.getBytes("iso8859-1"),"utf-8");
		if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
			session.put("errorInfo", "文件不存在！");
		}
		return SUCCESS;
	}
	//word导出
	public String toWord() throws IOException{
		WordExport we = null;
		Applicant app = dao.query(Applicant.class, appId);
		if(fileType==1){
			fileFileName = "中心（助理研究员）岗位申请书.doc";
			fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
			json.put("name", app.getName()!=null?app.getName():"");
			json.put("gender", app.getGender()!=null?app.getGender():"");
			json.put("major", app.getMajor()!=null?app.getMajor():"");
			json.put("email", app.getEmail()!=null?app.getEmail():"");
			json.put("qq", app.getQq()!=null?app.getQq():"");
			json.put("phone", app.getMobilePhone()!=null?app.getMobilePhone():"");
			json.put("address", app.getAddress()!=null?app.getAddress():"");
			we = new WordExport(json, "applyWord.ftl", fileFileName);			
		}else if(fileType ==2){
			fileFileName = "聘用协议书.doc";
			fileFileName = new String(fileFileName.getBytes(), "ISO8859-1");
			json.put("name", app.getName()!=null?app.getName():"");
			json.put("phone", app.getMobilePhone()!=null?app.getMobilePhone():"");
			json.put("address", app.getAddress()!=null?app.getAddress():"");
			we = new WordExport(json, "agreementWord.ftl", fileFileName);
		}
		we.createWord();
		return SUCCESS;
	}

	public Map getJson() {
		return json;
	}
	public void setJson(Map json) {
		this.json = json;
	}
	public List<String[]> getApplicantList() {
		return applicantList;
	}
	public void setApplicantList(List<String[]> applicantList) {
		this.applicantList = applicantList;
	}
	public List<String[]> getJobList() {
		return jobList;
	}
	public void setJobList(List<String[]> jobList) {
		this.jobList = jobList;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getApplicantType() {
		return applicantType;
	}
	public void setApplicantType(int applicantType) {
		this.applicantType = applicantType;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getVerifyResult() {
		return verifyResult;
	}
	public void setVerifyResult(int verifyResult) {
		this.verifyResult = verifyResult;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEthnic() {
		return ethnic;
	}
	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getMembership() {
		return membership;
	}
	public void setMembership(String membership) {
		this.membership = membership;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
}
