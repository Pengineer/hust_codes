package eoas.action.recruitment;

import java.io.File;
import java.sql.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import eoas.tool.FileTool;
import eoas.tool.SignID;


import eoas.bean.Account;
import eoas.bean.Education;
import eoas.bean.Experience;
import eoas.bean.Resume;
import eoas.service.IEducationService;
import eoas.service.IResumeService;
import eoas.service.IExperienceService;


public class ResumeAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private IResumeService resumeService;
	private IEducationService educationService;
	private IExperienceService experienceService;

	private Resume resume;
	private Account account; 
	private String name, mobilephone, hometown, idcardType, idcardNumber, phone;


	private int gender;
	private Date birthday;
	private String scholarship, award, leaderExperience, note;
	private String eleresume, production, otherAttachent;
	private int type;
	private int isTalent;
	private String resumeName, eleresumeName, otherAttachmentName, productionName;
	
	private Education education;
	private String degree, school, specialty, description;
	private Date stime,etime;
	
	private Experience experience;
	private String  company, position, positionDescription, project, dutyDescription;
	private Date companyStime, companyEtime, projectStime, projectEtime;
	
	//异步文件上传所需
	private String[] fileIds;	//标题提交上来的特征码list
	private String uploadKey;	//文件上传授权码
	private String fileFileName;
	public HttpServletRequest request;
	


	public String listMyResume() {
		
		return SUCCESS;
	}
	
	public String toAddResume() {
		return SUCCESS;
	}
	
	public String add() {
		
		resume.setName(resume.getName());
		resume.setGender(resume.getGender());
		resume.setBirthday(resume.getBirthday());
		resume.setMobilephone(resume.getMobilephone());
		resume.setHometown(resume.getHometown());
		resume.setIdcardType(resume.getIdcardType());
		resume.setIdcardNumber(resume.getIdcardNumber());
		
		
		// 设置主表信息
		String signID = SignID.getInstance().getSignID();
		//处理照片
		if (fileIds != null && fileIds.length == 1){
			Map<String, Object> sc = ActionContext.getContext().getApplication();
			try {
				request.getSession().getId();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String sessionId = request.getSession().getId();
			String basePath = ServletActionContext.getServletContext().getRealPath((String) sc.get("tempUploadPath") + "/" + sessionId);
			File path = new File(basePath + "/" + fileIds[0]);
			if (path.exists()){
				Iterator it = FileUtils.iterateFiles(path, null, false);
				File curFile = it.hasNext()? (File)it.next() : new File("nicaiwobudao31416");
				String fileName = curFile.getName();
				if (curFile.exists() && !fileName.contains("|") && !fileName.contains(";") && !fileName.contains("\\") && !fileName.contains("/") ){
					String savePath = (String) sc.get("ResumePictureUploadPath");
					signID = SignID.getInstance().getSignID();
					String realName;
					try {
						realName = FileTool.saveUpload(curFile, curFile.getName(), savePath, signID);
						System.out.println(realName);
						resume.setPhoto(resume.getPhoto());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	
		resume.setScholarship(resume.getScholarship());
		resume.setAward(resume.getAward());
		resume.setLeaderExperience(resume.getLeaderExperience());
		resume.setNote(resume.getNote());
		resume.setEleresume(resume.getEleresume());
		resume.setProduction(resume.getProduction());
		resume.setOtherAttachment(resume.getOtherAttachment());
		resume.setType(resume.getType());
			
		resume.setAccount(account);

		resume.setIsTalent(resume.getIsTalent());
		resume.setResumeName(resume.getResumeName());
		resume.setEleresumeName(resume.getEleresumeName());
		resume.setOtherAttachmentName(resume.getOtherAttachmentName());
	
		resumeService.add(resume);

		
		education.setDegree(education.getDegree());
		education.setSchool(education.getSchool());
		education.setStime(education.getStime());
		education.setEtime(education.getEtime());
		education.setSpecialty(education.getSpecialty());
		education.setResume(resume);
		System.out.println(education.getResume().getId());
		education.setDescription(education.getDescription());
		educationService.add(education);
			
		experience.setCompany(experience.getCompany());
		experience.setCompanyStime(experience.getCompanyStime());
		experience.setCompanyEtime(experience.getCompanyEtime());
		experience.setPosition(experience.getPosition());
		experience.setPositionDescription(experience.getPositionDescription());
		experience.setProject(experience.getProject());
		experience.setProjectStime(experience.getProjectStime());
		experience.setProjectEtime(experience.getProjectEtime());
		experience.setDutyDescription(experience.getDutyDescription());
		experience.setResume(resume);
		experienceService.add(experience);
		
		return SUCCESS;
	}
	
	public String listAllResume() {		
		return SUCCESS;
	}
	
	public String listPositionResume() {
		return SUCCESS;
	}
	
	public String listInnerTalent() {
		 return SUCCESS;
	}
	
	public String listOuterTalent() {
		
		return SUCCESS;
	}
	
	public IResumeService getResumeService() {
		return resumeService;
	}

	public void setResumeService(IResumeService resumeService) {
		this.resumeService = resumeService;
	}
	
	public IEducationService getEducationService() {
		return educationService;
	}

	public void setEducationService(IEducationService educationService) {
		this.educationService = educationService;
	}
	
	public IExperienceService getExperienceService() {
		return experienceService;
	}

	public void setExperienceService(IExperienceService experienceService) {
		this.experienceService = experienceService;
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
	
	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
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
}