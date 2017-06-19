package csdc.action.recruitment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;

import csdc.bean.Account;
import csdc.bean.Education;
import csdc.bean.Experience;
import csdc.bean.PositionResume;
import csdc.bean.Resume;
import csdc.service.IBaseService;


import csdc.service.IUploadService;
import csdc.service.imp.AccountService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.StringTool;
import csdc.tool.bean.FileRecord;




public class ResumeAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private IUploadService uploadService;// 文件上传
	private IBaseService baseService;
	private Resume resume;// 简历对象
	private Account account;// 帐号对象
	private String name, mobilephone, hometown, idcardType, idcardNumber,phone;
	private int gender;
	private Date birthday, createDate, modifyDate;
	private String scholarship, award, leaderExperience, note;
	private String eleresume, production, otherAttachent;
	private int type;
	private int isTalent;
	private String resumeName, eleresumeName, otherAttachmentName,productionName;

	private List<Education> edus;// 教育背景列表
	private List<Experience> exps;// 工作经历

	private String degree, school, specialty, description;
	private Date stime, etime;
	private Experience experience;
	private String company, position, positionDescription, project,dutyDescription;
	private Date companyStime, companyEtime, projectStime, projectEtime;
	private String resumeId;

	String entityId;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	private File file;// 代表上传文件的对象
	private String fileName;// 上传文件名
	private String fileContentType;// 上传文件的MIME类型
	private String uploadDir;// 保存上传文件的目录，相对于web应用程序的根路径在struts.xml文件中配置
	// 异步文件上传所需
	private String fileId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	private String[] fileIds; // 标题提交上来的特征码list
	private String uploadKey; // 文件上传授权码
	private String fileFileName;
	public HttpServletRequest request;
	private Map jsonMap = new HashMap();

/*	public String toListMyResume() {
		return SUCCESS;
	}

	// 简历的列表显示
	public String listMyResume() {
		ArrayList<Resume> resumeList = new ArrayList<Resume>();
		List<Object[]> rList = new ArrayList<Object[]>();
		Map session = ActionContext.getContext().getSession();
		account = (Account) session.get("account");
		resumeList = (ArrayList<Resume>) resumeService
		String[] item;
		for (Resume r : resumeList) {
			item = new String[5];
			item[0] = r.getResumeName();
			item[1] = r.getType() + "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (null != r.getCreateDate()) {
				item[2] = sdf.format(r.getCreateDate());
			}
			if (null != r.getModifyDate()) {
				item[3] = sdf.format(r.getModifyDate());
			}
			item[4] = r.getId();
			rList.add(item);
		}
		jsonMap.put("aaData", rList);
		return SUCCESS;
	}*/

	public String toList() {
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String list() {
		ArrayList<Resume> resumeList = new ArrayList<Resume>();
		List<Object[]> rList = new ArrayList<Object[]>();
		resumeList = (ArrayList<Resume>) baseService.list(Resume.class, null);
		String[] item;
		for (Resume r : resumeList) {
			item = new String[5];
			item[0] = r.getResumeName();
			item[1] = r.getType() + "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (null != r.getCreateDate()) {
				item[2] = sdf.format(r.getCreateDate());
			}
			if (null != r.getModifyDate()) {
				item[3] = sdf.format(r.getModifyDate());
			}
			item[4] = r.getId();
			rList.add(item);
		}
		jsonMap.put("aaData", rList);
		return SUCCESS;
	}

	public String toAddResume() {
		String groupId = "file_add";
		uploadService.resetGroup(groupId);
		return SUCCESS;
	}

	// 创建简历
	public String addResume() throws ParseException {
		resume.setGender(resume.getGender());
		resume.setName(resume.getName());
		resume.setBirthday(resume.getBirthday());
		resume.setMobilephone(resume.getMobilephone());
		resume.setHometown(resume.getHometown());
		resume.setIdcardType(resume.getIdcardType());
		resume.setIdcardNumber(resume.getIdcardNumber());

		resume.setScholarship(resume.getScholarship());
		resume.setAward(resume.getAward());
		resume.setLeaderExperience(resume.getLeaderExperience());
		resume.setNote(resume.getNote());
		resume.setType(resume.getType());
		@SuppressWarnings("rawtypes")
		Map session = ActionContext.getContext().getSession();
		account = (Account) session.get("account");
		resume.setAccount(account);
		resume.setIsTalent(0);

		resume.setResumeName(resume.getResumeName());
		resume.setEleresumeName(resume.getEleresumeName());
		resume.setOtherAttachmentName(resume.getOtherAttachmentName());
		resume.setEleresume(resume.getEleresume());
		resume.setProduction(resume.getProduction());
		resume.setOtherAttachment(resume.getOtherAttachment());
		resume.setCreateDate(new Date());
		baseService.add(resume);
		// 处理照片
		String groupId = "file_add";
		String savePath = "upload/photo/";
		entityId = resume.getId();
		List<String> attachments = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
			String newFilePath = savePath + fileRecord.getFileName();// 文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();// 原始文件名
			// 将文件放入list中暂存
			attachments.add(newFilePath);
			fileRecord.setDest(new File(ApplicationContainer.sc
					.getRealPath(newFilePath))); // 将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		resume.setPhoto(StringTool.joinString(
				attachments.toArray(new String[0]), "; "));
		baseService.modify(resume);
		uploadService.flush(groupId);


		// 处理电子简历
		//处理附件
		String groupId1 = "eleresume_add";
		String savePath1 = "upload/eleresume/";
		List<String> eleresume = new ArrayList<String>();
		List<String> eleresumeName = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId1)) {
			String newFilePath = savePath1 + fileRecord.getFileName();// 文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			eleresume.add(newFilePath);
			eleresumeName.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		resume.setEleresume(StringTool.joinString(eleresume.toArray(new String[0]), "; "));
		resume.setEleresumeName(StringTool.joinString(eleresumeName.toArray(new String[0]), "; "));
		baseService.modify(resume);
		uploadService.flush(groupId1);

		
		// 处理上传作品
		String groupId2 = "production_add";
		String savePath2 = "upload/production/";
		List<String> production = new ArrayList<String>();
		List<String> productionNameName = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId2)) {
			String newFilePath = savePath2 + fileRecord.getFileName();// 文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			production.add(newFilePath);
			productionNameName.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		resume.setProduction(StringTool.joinString(production.toArray(new String[0]), "; "));
		resume.setProductionName(StringTool.joinString(productionNameName.toArray(new String[0]), "; "));
		baseService.modify(resume);
		uploadService.flush(groupId2);
		// 处理其他附件
		String groupId3 = "otherAttachment_add";
		String savePath3 = "upload/otherAttachment/";
		List<String> otherAttachment = new ArrayList<String>();
		List<String> otherAttachmentName = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId3)) {
			String newFilePath = savePath3 + fileRecord.getFileName();// 文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			otherAttachment.add(newFilePath);
			otherAttachmentName.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		resume.setOtherAttachment(StringTool.joinString(otherAttachment.toArray(new String[0]), "; "));
		resume.setOtherAttachmentName(StringTool.joinString(otherAttachmentName.toArray(new String[0]), "; "));
		baseService.modify(resume);
		uploadService.flush(groupId3);

		
		// 设置教育背景信息
		Education edu = new Education();
		for (int i = 0; i < edus.size(); i++) {
			edu = edus.get(i);
			if (edu != null) {
				edu.setResume(resume);
				baseService.add(edu);
			}
		}

		// 设置教育背景信息
		Experience exp = new Experience();
		for (int i = 0; i < exps.size(); i++) {
			exp = exps.get(i);
			if (exp != null) {
				exp.setResume(resume);
				baseService.add(exp);
			}
		}

		return SUCCESS;
	}

	
	public String deleteResume(){
		Map map = new HashMap();
		map.put("resumeId", resumeId);
		resume = new Resume();
		resume = (Resume)baseService.load(Resume.class,resumeId);
		edus = new ArrayList<Education>(this.resume.getEducation());
		edus = baseService.list(Education.class, map);
		for(int i = 0; i < edus.size(); i++){
			baseService.delete(Education.class, edus.get(i).getId());
		}
		exps = new ArrayList<Experience>(this.resume.getExperience());
		exps = baseService.list(Experience.class,map); 
		for(int i = 0; i < exps.size(); i++){
			baseService.delete(Experience.class, exps.get(i).getId());
		} 
		
		List<PositionResume> positionResumeList = new ArrayList<PositionResume>();
		positionResumeList = baseService.list(PositionResume.class, map);
		for(int i = 0;i < positionResumeList.size(); i++) {
			baseService.delete(PositionResume.class, positionResumeList.get(i).getId());
		}
		baseService.delete(Resume.class, resumeId);
		return SUCCESS; 
	}
	 

	// 进入修改简历页面
	public String toModifyResume() {
		resume = (Resume)baseService.load(Resume.class, resumeId);
		Map map = ActionContext.getContext().getApplication();
		map.put("resumeId", resume.getId());
		/*
		 * edus = new ArrayList<Education>(this.resume.getEducation()); exps =
		 * new ArrayList<Experience>(this.resume.getExperience());
		 */

		edus = baseService.list(Education.class, map);
		exps = baseService.list(Experience.class, map);
		
		//将已有附件加入文件组，在编辑页面显示
		String groupId = "file_" + resume.getId();
		uploadService.resetGroup(groupId);
		if (resume.getEleresumeName() != null && resume.getEleresume() != null) {
			String[] tempFileRealpath = resume.getEleresume().split("; ");
			String[] attchmentNames = resume.getEleresumeName().split("; ");
			//遍历要修改的新闻中已有的文件
			for (int i = 0; i < tempFileRealpath.length; i++) {
				String filePath = tempFileRealpath[i];
				String fileRealpath = ApplicationContainer.sc.getRealPath(filePath);
				if (fileRealpath != null) {
					uploadService.addFile(groupId, new File(fileRealpath), attchmentNames[i]);
				}
			}
		}
		
		
		

		return SUCCESS;
	}

	public String modifyResume() {
		Map map = ActionContext.getContext().getApplication();
		Resume r = (Resume)baseService.load(Resume.class, ((Resume) map.get("resume")).getId());
		// 更新教育信息
		List<Education> educations = new ArrayList<Education>(r.getEducation());
		for (int i = 0; i < educations.size(); i++) {
			baseService.delete(Education.class, educations.get(i).getId());
		}
		if (edus != null) {// 设置教育背景信息
			Education edu = new Education();
			for (int i = 0; i < edus.size(); i++) {
				edu = edus.get(i);
				if (edu != null) {
					edu.setResume(r);
					baseService.add(edu);
				}
			}
		}
		// 更新工作经历
		List<Experience> experiences = new ArrayList<Experience>(
				r.getExperience());
		for (int i = 0; i < experiences.size(); i++) {
			baseService.delete(Experience.class, experiences.get(i).getId());
		}
		if (exps != null) {// 设置教育背景信息
			Experience exp = new Experience();
			for (int i = 0; i < exps.size(); i++) {
				exp = exps.get(i);
				if (exp != null) {
					exp.setResume(r);
					baseService.add(exp);
				}
			}
		}
		// 修改简历基本信息
		r.setName(resume.getName());
		r.setGender(resume.getGender());
		r.setBirthday(resume.getBirthday());
		r.setMobilephone(resume.getMobilephone());
		r.setHometown(resume.getHometown());
		r.setIdcardType(resume.getIdcardType());
		r.setIdcardNumber(resume.getIdcardNumber());
		r.setScholarship(resume.getScholarship());
		r.setAward(resume.getAward());
		r.setLeaderExperience(resume.getLeaderExperience());
		r.setNote(resume.getNote());

		r.setType(resume.getType());
		r.setIsTalent(0);

		r.setModifyDate(new Date());
		
		// 处理电子简历
		//处理附件
		String groupId1 = "eleresume_add";
		String savePath1 = "upload/eleresume/";
		List<String> eleresume = new ArrayList<String>();
		List<String> eleresumeName = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId1)) {
			String newFilePath = savePath1 + fileRecord.getFileName();// 文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			eleresume.add(newFilePath);
			eleresumeName.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		r.setEleresume(StringTool.joinString(eleresume.toArray(new String[0]), "; "));
		r.setEleresumeName(StringTool.joinString(eleresumeName.toArray(new String[0]), "; "));
		baseService.modify(r);
		uploadService.flush(groupId1);

		
		// 处理上传作品
		String groupId2 = "production_add";
		String savePath2 = "upload/production/";
		List<String> production = new ArrayList<String>();
		List<String> productionNameName = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId2)) {
			String newFilePath = savePath2 + fileRecord.getFileName();// 文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			production.add(newFilePath);
			productionNameName.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		r.setProduction(StringTool.joinString(production.toArray(new String[0]), "; "));
		r.setProductionName(StringTool.joinString(productionNameName.toArray(new String[0]), "; "));
		baseService.modify(r);
		uploadService.flush(groupId2);
		// 处理其他附件
		String groupId3 = "otherAttachment_add";
		String savePath3 = "upload/otherAttachment/";
		List<String> otherAttachment = new ArrayList<String>();
		List<String> otherAttachmentName = new ArrayList<String>();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId3)) {
			String newFilePath = savePath3 + fileRecord.getFileName();// 文件在数据库中的存储地址（包括存储路径和新的文件名）
			String orignalFileName = fileRecord.getOriginal().getName();//原始文件名
			//将文件放入list中暂存
			otherAttachment.add(newFilePath);
			otherAttachmentName.add(orignalFileName);
			fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(newFilePath)));	//将照片文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
		r.setOtherAttachment(StringTool.joinString(otherAttachment.toArray(new String[0]), "; "));
		r.setOtherAttachmentName(StringTool.joinString(otherAttachmentName.toArray(new String[0]), "; "));
		baseService.modify(r);
		uploadService.flush(groupId3);
		
		
		
		
		
		
		
		baseService.modify(r);
		return SUCCESS;
	}

	// 查看简历详情
	public String view() throws IOException {
		if(resumeId != null) {
			resume = (Resume)baseService.load(Resume.class,resumeId);
		} else {
			Map map = ActionContext.getContext().getSession();
			String accountId = ((Account)map.get("account")).getId();
			map.put("accountId", accountId);
			resume = (Resume)(baseService.list(Resume.class, map).get(0));
		}	
		if (resume.getProductionName() != null) {// 处理附件名称用于显示，去掉最后一个"; "
			resume.setProductionName(resume.getProductionName().substring(0, resume.getProductionName().length()));
			//获取文件大小
			List<String> productionSizeList = new ArrayList<String>();
			String[] productionPath = resume.getProduction().split("; ");
			InputStream is = null;
			for (String path : productionPath) {
				is = ServletActionContext.getServletContext().getResourceAsStream(path);
				if (null != is) {
					productionSizeList.add(baseService.accquireFileSize(is.available()));
				} else {// 附件不存在
					productionSizeList.add(null);
				}
				jsonMap.put("productionSizeList", productionSizeList);
			}
		}
		Map map = new HashMap();
		map.put("resumeId", resume.getId());
		edus = baseService.list(Education.class, map);
		exps = baseService.list(Experience.class, map);
		return SUCCESS;
	}

	public String listPositionResume() {
		return SUCCESS;
	}

	// 加入人才库
	public String addTalent() {
		resume = (Resume)this.baseService.load(Resume.class,resumeId);
		if (resume.getIsTalent() == 0) {
			resume.setIsTalent(1);
			baseService.modify(resume);
		}
		return SUCCESS;
	}

	public String toListInnerTalent() {
		return SUCCESS;
	}

	public String listInnerTalent() {
		ArrayList<Resume> resumeList = new ArrayList<Resume>();
		resumeList = (ArrayList<Resume>) this.baseService.list(Resume.class.getName()+".listTalent",null);
		List<Object> rList = new ArrayList<Object>();
		String[] item;
		for (Resume r : resumeList) {
			item = new String[3];
			item[0] = r.getName();
			item[1] = r.getMobilephone();
			item[2] = r.getId();
			rList.add(item);
		}
		jsonMap.put("aaData", rList);
		return SUCCESS;
	}

	public String listOuterTalent() {

		return SUCCESS;
	}


	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getIdcardType() {
		return idcardType;
	}

	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getScholarship() {
		return scholarship;
	}

	public void setScholarship(String scholarship) {
		this.scholarship = scholarship;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getLeaderExperience() {
		return leaderExperience;
	}

	public void setLeaderExperience(String leaderExperience) {
		this.leaderExperience = leaderExperience;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEleresume() {
		return eleresume;
	}

	public void setEleresume(String eleresume) {
		this.eleresume = eleresume;
	}

	public String getProduction() {
		return production;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public String getOtherAttachent() {
		return otherAttachent;
	}

	public void setOtherAttachent(String otherAttachent) {
		this.otherAttachent = otherAttachent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsTalent() {
		return isTalent;
	}

	public void setIsTalent(int isTalent) {
		this.isTalent = isTalent;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getResumeName() {
		return resumeName;
	}

	public void setResumeName(String resumeName) {
		this.resumeName = resumeName;
	}

	public String getEleresumeName() {
		return eleresumeName;
	}

	public void setEleresumeName(String eleresumeName) {
		this.eleresumeName = eleresumeName;
	}

	public String getOtherAttachmentName() {
		return otherAttachmentName;
	}

	public void setOtherAttachmentName(String otherAttachmentName) {
		this.otherAttachmentName = otherAttachmentName;
	}

	public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	public List<Education> getEdus() {
		return edus;
	}

	public void setEdus(List<Education> edus) {
		this.edus = edus;
	}

	public List<Experience> getExps() {
		return exps;
	}

	public void setExps(List<Experience> exps) {
		this.exps = exps;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPositionDescription() {
		return positionDescription;
	}

	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getDutyDescription() {
		return dutyDescription;
	}

	public void setDutyDescription(String dutyDescription) {
		this.dutyDescription = dutyDescription;
	}

	public Date getCompanyStime() {
		return companyStime;
	}

	public void setCompanyStime(Date companyStime) {
		this.companyStime = companyStime;
	}

	public Date getCompanyEtime() {
		return companyEtime;
	}

	public void setCompanyEtime(Date companyEtime) {
		this.companyEtime = companyEtime;
	}

	public Date getProjectStime() {
		return projectStime;
	}

	public void setProjectStime(Date projectStime) {
		this.projectStime = projectStime;
	}

	public Date getProjectEtime() {
		return projectEtime;
	}

	public void setProjectEtime(Date projectEtime) {
		this.projectEtime = projectEtime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getFileIds() {
		return fileIds;
	}

	public void setFileIds(String[] fileIds) {
		this.fileIds = fileIds;
	}

	public String getUploadKey() {
		return uploadKey;
	}

	public void setUploadKey(String uploadKey) {
		this.uploadKey = uploadKey;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public String getResumeId() {
		return resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

	public IUploadService getUploadService() {
		return uploadService;
	}

	public void setUploadService(IUploadService uploadService) {
		this.uploadService = uploadService;
	}
	
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	
}