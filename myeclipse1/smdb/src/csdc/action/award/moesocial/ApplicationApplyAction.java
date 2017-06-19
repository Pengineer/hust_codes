package csdc.action.award.moesocial;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.AwardApplication;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.InputValidate;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.bean.FileRecord;
import csdc.tool.info.AwardInfo;
import csdc.tool.info.GlobalInfo;

/**
 * 社科奖励申请数据管理
 * @author 余潜玉  王燕
 */
public class ApplicationApplyAction extends ApplicationAction {
	
	private static final long serialVersionUID = -4512234546584536466L;
	private int reviewStatus;//评审状态
	private int reviewAuditStatus;//评审审核状态
	private int type;//1:奖励申请成功
	private String filePath;
	private String valView;//申请奖励校验返回视图
	private Person person;//人员
	private Academic academic;//学术
	private String resultType;//成果类型
	private String proId;//成果id
	private int isSelectOrgan;//是否从已有团队中选择
	private int deptInstFlag;//院系或研究机构标志位	1：研究机构	2：院系
	//异步文件上传所需
	private String[] fileIds;	//标题提交上来的特征码list
	private String uploadKey;	//文件上传授权码
	private Map<String, String> unitDetails;//研究人员所在的所有机构
	private String unitId;//研究人员所选单位	学校id、院系id和研究基地id以'; '连接
	private static final String HQL1 = "select aa.id, aa.productName, aa.applicantName, aa.agencyName, aa.disciplineType, pr.productType, aa.session, aa.status, aa.applicant.id, un.id, aa.file";
	private static final String HQL2 = " from AwardApplication aa left outer join aa.university un, Product pr where pr.id = aa.product.id ";
	private static final String PAGE_NAME = "awardApplicationpages";// 列表页面名称
	
	private InputValidate inputValidate = new InputValidate();//校验工具类
	
	public String pageName() {
		return ApplicationApplyAction.PAGE_NAME;
	}
	public Object[] simpleSearchCondition() {
		int cloumnLabel = 0;//排序列位置
		AccountType accountType = loginer.getCurrentType();
		Map map = new HashMap();
		StringBuffer hql = this.awardService.getHql(HQL1, HQL2, accountType);
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			map.put("belongId",baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.compareTo(AccountType.MINISTRY)>0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
		}
		if (keyword1 > 0) {
			hql.append(" and aa.session =:session ");
			map.put("session", keyword1);
		}
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.isEmpty()){
			hql.append(" and ");
			hql = this.awardService.getHql(hql, searchType, 1);
			map.put("keyword", "%" + keyword + "%");
		}
	    if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//系统管理员或部级
	    	cloumnLabel = 9;
	    }else if(accountType.equals(AccountType.PROVINCE)){//省级
	    	cloumnLabel = 8;
	    }else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//校级
	    	cloumnLabel = 7;
	    }else if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){//院系或 研究基地
	    	cloumnLabel = 6;
	    }else{//研究人员
	    	cloumnLabel = 5;
	    }
		return new Object[]{
			hql.toString(),
			map,
			cloumnLabel,
			null
		};
	}

	//高级检索
	public Object[] advSearchCondition(){
		int cloumnLabel = 0;
		AccountType accountType = loginer.getCurrentType();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql = this.awardService.getHql(HQL1, HQL2, accountType);
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
			map.put("belongId", baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.compareTo(AccountType.MINISTRY)>0){//部级以下管理人员
			map.put("belongId", loginer.getCurrentBelongUnitId());
		}
		if (productName != null && !productName.isEmpty()) {
			hql.append(" and LOWER(aa.productName) like :productName");
			map.put("productName", "%" + productName.toLowerCase() + "%");
		}
		if (ptype != null && !"-1".equals(ptype)) {
			String ptypeid = awardService.fetchProductEnglish(ptype);
			hql.append(" and LOWER(pr.productType) like :ptypeid");
			map.put("ptypeid", ptypeid);
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			String[] dtypes = dtypeNames.split("; ");
			int len = dtypes.length;
			if(len > 0){
				hql.append(" and (");
				for(int i = 0; i < len; i++){
					map.put("disciplineType" + i, "%" + dtypes[i].toLowerCase() + "%");
					hql.append("LOWER(aa.disciplineType) like :disciplineType" + i);
					if (i != len-1)
						hql.append(" or ");
				}hql.append(")");
			}
		}
		if (applicantName != null && !applicantName.isEmpty()) {
			hql.append(" and LOWER(aa.applicantName) like :applicantName");
			map.put("applicantName", "%" + applicantName.toLowerCase() + "%");
		}
		if (universityName != null && !universityName.isEmpty()) {
			hql.append(" and LOWER(un.name) like :universityName");
			map.put("universityName", "%" + universityName.toLowerCase() + "%");
		}
		if (provinceName != null && !provinceName.isEmpty()) {
			hql.append(" and LOWER(aa.provinceName) like :provinceName");
			map.put("provinceName", "%" + provinceName.toLowerCase() + "%");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			if(accountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
				hql.append(" and aa.finalAuditDate is not null and to_char(aa.finalAuditDate,'yyyy-MM-dd') >=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY)){//部级
				hql.append(" and aa.reviewAuditDate is not null and to_char(aa.reviewAuditDate,'yyyy-MM-dd') >=:startDate");
			}else if(accountType.equals(AccountType.PROVINCE)){//省厅
				hql.append(" and aa.provinceAuditDate is not null and to_char(aa.provinceAuditDate,'yyyy-MM-dd') >=:startDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
				hql.append(" and aa.universityAuditDate is not null and to_char(aa.universityAuditDate,'yyyy-MM-dd') >=:startDate");
			}else if(accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)){//院系或研究基地
				hql.append(" and aa.deptInstAuditDate is not null and to_char(aa.deptInstAuditDate,'yyyy-MM-dd') >=:startDate");
			}
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(accountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
				if(startDate == null){
					hql.append(" and aa.finalAuditDate is not null");
				}
				hql.append(" and to_char(aa.finalAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if( accountType.equals(AccountType.MINISTRY)){//部级
				if(startDate == null){
					hql.append(" and aa.reviewAuditDate is not null");
				}
				hql.append(" and to_char(aa.reviewAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.PROVINCE)){//省厅
				if(startDate == null){
					hql.append(" and aa.provinceAuditDate is not null");
				}
				hql.append(" and to_char(aa.provinceAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
				if(startDate == null){
					hql.append(" and aa.universityAuditDate is not null");
				}
				hql.append(" and to_char(aa.universityAuditDate,'yyyy-MM-dd')<=:endDate");
			}else if(accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)){//院系或研究基地
				if(startDate == null){
					hql.append(" and aa.deptInstAuditDate is not null");
				}
				hql.append(" and to_char(aa.deptInstAuditDate,'yyyy-MM-dd')<=:endDate");
			}
		}
		if(session3 > 0){
			hql.append(" and aa.session >=:session3");
			map.put("session3", session3 );
		}
		if(session2 > 0){
			hql.append(" and aa.session <=:session2");
			map.put("session2", session2);
		}
		if((accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)) && auditStatus > 0){//研究人员
			map.put("submitStatus", auditStatus);
			hql.append(" and aa.applicantSubmitStatus =:submitStatus");
		}
		int resultStatus,saveStatus;
		if(status != -1){
			saveStatus = status/10;
			resultStatus = status%10;
			map.put("auditStatus", saveStatus);
			map.put("auditResult", resultStatus);
			if(accountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
				hql.append(" and aa.finalAuditStatus =:auditStatus and aa.finalAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY)){//部级 
				hql.append(" and aa.ministryAuditStatus =:auditStatus and aa.ministryAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.PROVINCE)){//省厅
				hql.append(" and aa.provinceAuditStatus =:auditStatus and aa.provinceAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
				hql.append(" and aa.universityAuditStatus =:auditStatus and aa.universityAuditResult =:auditResult");
			}else if(accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)){//院系或研究基地
				hql.append(" and aa.deptInstAuditStatus =:auditStatus and aa.deptInstAuditResult =:auditResult");
			}
		}
		if(accountType.equals(AccountType.MINISTRY)){
			if(reviewAuditStatus != -1){
				saveStatus = reviewAuditStatus/10;
				resultStatus = reviewAuditStatus%10;
				map.put("reviewAuditStatus", saveStatus);
				map.put("reviewAuditResult", resultStatus);
				hql.append(" and aa.reviewAuditStatus =:reviewAuditStatus and aa.reviewAuditResult =:reviewAuditResult");
			}
		}
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//系统管理员或部级
	    	cloumnLabel = 9;
	    }else if(accountType.equals(AccountType.PROVINCE)){//省级
	    	cloumnLabel = 8;
	    }else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//校级
	    	cloumnLabel = 7;
	    }else if(accountType.equals(AccountType.DEPARTMENT) ||  accountType.equals(AccountType.INSTITUTE)){//院系或 研究基地
	    	cloumnLabel = 6;
	    }else{//研究人员
	    	cloumnLabel = 5;
	    }
		return new Object[]{
			hql.toString(),
			map,
			cloumnLabel,
			null
		};
	}
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author wangyan
	 */
	public void saveAdvSearchQuery(Map searchQuery) {
		AccountType accountType = loginer.getCurrentType();
		if(null != productName && !productName.isEmpty()){
			searchQuery.put("productName", productName);
		}
		if(dtypeNames != null && !dtypeNames.isEmpty()){
			searchQuery.put("dtypeNames", dtypeNames);
		} 
		if (ptype != null && !"-1".equals(ptype)) {
			searchQuery.put("ptype", ptype);
		}
		if (applicantName != null && !applicantName.isEmpty()) {
			searchQuery.put("applicantName", applicantName);
		}
		if (universityName != null && !universityName.isEmpty()) {
			searchQuery.put("universityName", universityName.toLowerCase());
		}
		if (provinceName != null && !provinceName.isEmpty()) {
			searchQuery.put("provinceName", provinceName.toLowerCase());
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (null != startDate) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (null != endDate) {
			searchQuery.put("endDate", df.format(endDate));
		}
		if(session3 > 0){
			searchQuery.put("session3", session3);
		}
		if(session2 > 0){
			searchQuery.put("session2", session2);
		}
		if((accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)) && auditStatus > 0){//研究人员
			searchQuery.put("auditStatus", auditStatus);
		}
		if(status != -1){
			searchQuery.put("status", status);
		}
		if(accountType.equals(AccountType.MINISTRY)){
			if(reviewAuditStatus != -1){
				searchQuery.put("reviewAuditStatus", reviewAuditStatus);
			}
		}
	}
	
	//获取团队信息
	public String fetchOrganInfo(){
		String organId = request.getParameter("organId");
		if(null == organId || organId.isEmpty()){
			jsonMap.put(GlobalInfo.ERROR_INFO, "当前团队信息不存在");
			return INPUT;
		}
		Product organ = (Product) dao.query(Product.class, organId.trim());
		jsonMap.put("organ", organ);
		jsonMap.put("instituteId", (null != organ.getInstitute()) ? organ.getInstitute().getId() : "");
		jsonMap.put("departmentId", (null != organ.getDepartment()) ? organ.getDepartment().getId() : "");
		return SUCCESS;
	}
	
	//准备申请奖励
	public String toAdd(){
		//获取个人申请申请人的基本信息
		Map session = ActionContext.getContext().getSession();
		AccountType accountType = loginer.getCurrentType();
		if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){
			String groupId = "file_add";
			uploadService.flush(groupId);
			person = dao.query(Person.class, loginer.getPerson().getId());
		    academic = (Academic) dao.queryUnique("from Academic ac where ac.person.id = ? ", person.getId());
			//获取团队申请团队的基本信息
			Map<String,String> organizations = this.awardService.getOrganization(baseService.getBelongIdByLoginer(loginer));
			session.put("organizations", organizations);
			//成果信息
			Map ptypes = Product.typeMap;
			unitDetails = this.awardService.getUnitDetailByAccountInfo(baseService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
			session.put("ptypes", ptypes);
			session.put("ptypeid", "paper");
			session.put("unitDetails", unitDetails);
			session.put("addOrModify", 1);//添加
			request.setAttribute("applicationType", 1);//以个人名义申请
		}
		return SUCCESS;
	}

    //申请奖励
	@Transactional
	public String add(){
		try{
			//基本信息
			Person oldPerson = dao.query(Person.class, loginer.getPerson().getId());
			Academic oldAcademic = (Academic) dao.queryUnique("from Academic ac where ac.person.id = ? ", baseService.getBelongIdByLoginer(loginer));
			Product product = (Product) dao.query(Product.class,proId);
			if(awardApplication.getApplicationType() == 1){//以个人名义申请申请人的基本信息
				oldPerson.setName(personExtService.regularNames(person.getName()));//申请人姓名
				oldAcademic.setSpecialityTitle((null != academic.getSpecialityTitle()) ? academic.getSpecialityTitle().trim() : null);//专业技术职务
				oldAcademic.setLastDegree((null != academic.getLastDegree()) ? academic.getLastDegree().trim() : null);//最后学历
				oldAcademic.setLastDegree((null != academic.getLanguage()) ? academic.getLanguage().trim() : null);//语言
				oldPerson.setOfficePhone(person.getOfficePhone().trim());//办公电话
				oldPerson.setHomePhone((null != person.getHomePhone()) ? person.getHomePhone().trim() : null);//家庭电话
				oldPerson.setEmail(person.getEmail().trim());//电子邮件
				oldPerson.setMobilePhone(person.getMobilePhone().trim());//家庭电话
				oldPerson.setIdcardType(person.getIdcardType().trim());//证件类型
				oldPerson.setIdcardNumber(person.getIdcardNumber().trim());//证件号码
				oldPerson.setBirthday(person.getBirthday());//出生日期
				dao.modify(oldPerson);
				if(null != oldAcademic){
					dao.modify(oldAcademic);	
				}
			}else if(awardApplication.getApplicationType() == 2){//以团队等名义申请团队等的基本信息
				product.setOrgPerson(oldPerson);
				if(isSelectOrgan == 0){//添加新团队
					//设置团队所在机构信息
					@SuppressWarnings("unused")
					String univid = "";
					if(deptInstFlag == 1){//研究基地
						Institute institute = (Institute) dao.query(Institute.class, product.getInstitute().getId());
						product.setDepartment(null);
						product.setInstitute(institute);
						if(institute != null){
							product.setDivisionName(institute.getName());
							Agency university = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
							product.setUniversity(university);
							product.setAgencyName(university != null ? university.getName() : null);
							product.setProvinceName(university != null ? university.getProvince().getName() : null);
							product.setProvince(university != null ? university.getProvince() : null);
							univid = university.getId();
						}
					}else if(deptInstFlag == 2){//院系
						Department department = (Department) dao.query(Department.class, product.getDepartment().getId());
						product.setDepartment(department);
						product.setInstitute(null);
						if(department != null){
							product.setDivisionName(department.getName());
							Agency university = (Agency) dao.query(Agency.class, department.getUniversity().getId());
							product.setUniversity(university);
							product.setAgencyName(university != null ? university.getName() : null);
							product.setProvinceName(university != null ? university.getProvince().getName() : null);
							product.setProvince(university != null ? university.getProvince() : null);
							univid = university.getId();
						}
					}
					dao.add(product);
				}else{//从已有团队中选择
					Product oldOrganization = (Product) dao.query(Product.class, product.getId());
					oldOrganization.setDiscipline((null != product.getOrgDiscipline()) ? product.getOrgDiscipline().trim(): null);//学科代码
					oldOrganization.setOrgOfficeAddress(product.getOrgOfficeAddress().trim());//通信地址
					oldOrganization.setOrgOfficePhone(product.getOrgOfficePhone().trim());//办公电话
					oldOrganization.setOrgEmail((null != product.getOrgEmail()) ? product.getOrgEmail().trim(): null);//电子邮件
					oldOrganization.setOrgOfficePostcode(product.getOrgOfficePostcode().trim());//邮政编码
					oldOrganization.setOrgMobilePhone(product.getOrgMobilePhone().trim());//手机
					//设置团队所在机构信息
					@SuppressWarnings("unused")
					String univid = "";
					if(deptInstFlag == 1){//研究基地
						Institute institute = (Institute) dao.query(Institute.class, product.getInstitute().getId());
						oldOrganization.setDepartment(null);
						oldOrganization.setInstitute(institute);
						if(institute != null){
							oldOrganization.setDivisionName(institute.getName());
							Agency university = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
							oldOrganization.setUniversity(university);
							oldOrganization.setAgencyName(university != null ? university.getName() : null);
							oldOrganization.setProvinceName(university != null ? university.getProvince().getName() : null);
							oldOrganization.setProvince(university != null ? university.getProvince() : null);
							univid = university.getId();
						}
					}else if(deptInstFlag == 2){//院系
						Department department = (Department) dao.query(Department.class, product.getDepartment().getId());
						oldOrganization.setDepartment(department);
						oldOrganization.setInstitute(null);
						if(department != null){
							oldOrganization.setDivisionName(department.getName());
							Agency university = (Agency) dao.query(Agency.class, department.getUniversity().getId());
							oldOrganization.setUniversity(university);
							oldOrganization.setAgencyName(university != null ? university.getName() : null);
							oldOrganization.setProvinceName(university != null ? university.getProvince().getName() : null);
							oldOrganization.setProvince(university != null ? university.getProvince() : null);
							univid = university.getId();
						}
					}
					dao.modify(oldOrganization);
				}
//				awardApplication.setOrganization(organization);
//				product.setOrganization(organization);
				dao.modify(product);
			}else{
				addActionError("请以团队或个人名义申请奖励！");
				return ERROR;
			}
			
			//成果文件信息
			//处理附件
			String groupId = "file_" + awardApplication.getId();
			for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
					File curFile = fileRecord.getOriginal();
					String savePath = this.awardService.getFileName(curFile);
					awardApplication.setFile(savePath);
					fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(awardApplication.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
			}
			//成果信息部分
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 2, awardApplication.getApplicantSubmitStatus(), null);
			auditMap.put("auditInfo",auditInfo);
			auditMap.put("isSubUni", 0);
			awardApplication.edit(auditMap);//保存操作结果
			if(awardApplication.getApplicantSubmitStatus() == 3){//提交申请
				if(loginer.getCurrentType().equals(AccountType.TEACHER) || loginer.getCurrentType().equals(AccountType.STUDENT)){//教师或学生跳过部门审核
					/* 以下代码为跳过部门审核*/
					AuditInfo auditInfoDept = new AuditInfo();
					auditInfoDept.setAuditResult(2);
					auditInfoDept.setAuditStatus(3);
					auditMap.put("auditInfo",auditInfoDept);
					awardApplication.edit(auditMap);
					/* 结束 */
				}else if(loginer.getCurrentType().equals(AccountType.EXPERT)){//外部专家直接跳到部级审核
					awardApplication.setStatus(5);
				}
			}
			/* 结束 */	
			if(awardApplication.getAdoption() != null){
				awardApplication.setAdoption(("A"+awardApplication.getAdoption()).trim().substring(1));
			}
			if(awardApplication.getIntroduction() != null){
				awardApplication.setIntroduction(("A"+awardApplication.getIntroduction()).trim().substring(1));
			}
			if(awardApplication.getPrizeObtained() != null){
				awardApplication.setPrizeObtained(("A"+awardApplication.getPrizeObtained()).trim().substring(1));
			}
			if(awardApplication.getResponse() != null){
				awardApplication.setResponse(("A"+awardApplication.getResponse()).trim().substring(1));
			}
			Person person = dao.query(Person.class, loginer.getPerson().getId());
			awardApplication.setApplicant(person);
			awardApplication.setApplicantName(person.getName());
			//设置奖励所在机构信息
			String[] unitIds = unitId.split("; ");
			if(unitIds.length == 3){
				if(loginer.getCurrentType().equals(AccountType.TEACHER) || loginer.getCurrentType().equals(AccountType.STUDENT)){
					Agency university = (Agency) dao.query(Agency.class, unitIds[0].trim());
					awardApplication.setUniversity(university);
			    	awardApplication.setAgencyName(university != null ? university.getName() : null);
			    	awardApplication.setProvinceName(university != null ? university.getProvince().getName() : null);
			    	awardApplication.setProvince(university != null ? university.getProvince() : null);
			    	if(unitIds[1] != null && unitIds[1].trim().length() > 0){//院系id不为null
			    		Department department = (Department) dao.query(Department.class, unitIds[1].trim());
						awardApplication.setDepartment(department);
						awardApplication.setInstitute(null);
			    		awardApplication.setDivisionName(department!= null? department.getName() : null);
			    	}else if(unitIds[2] != null && unitIds[2].trim().length() > 0){//研究基地id不为null
			    		Institute institute = (Institute) dao.query(Institute.class, unitIds[2].trim());
						awardApplication.setDepartment(null);
						awardApplication.setInstitute(institute);
			    		awardApplication.setDivisionName(institute != null ? institute.getName() : null);
			    	}
				}else if(loginer.getCurrentType().equals(AccountType.EXPERT)){//外部专家账号
					awardApplication.setAgencyName(unitIds[0]);
					awardApplication.setDivisionName(unitIds[1]);
		    	}
			}else{
				addActionError("所选机构信息有误！");
				return ERROR;
			}
	   	    awardApplication.setSubType(soDao.query("awardType", "01"));
	   	    awardApplication.setProduct(product);
	   	    awardApplication.setProductName(product.getChineseName());
	   	    awardApplication.setCreateMode(1);
	   	    awardApplication.setCreateDate(new Date());
	   	    dao.add(awardApplication);
	        this.entityId = awardApplication.getId();
	        type = 1;//奖励申请成功
	        if(null != request.getParameter("ex_appflag") && Integer.parseInt(request.getParameter("ex_appflag")) == 1){
	        	unitDetails = this.awardService.getUnitDetailByAccountInfo(baseService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
	        	return LOGIN;
	        }
	        uploadService.flush(groupId);
	        //同步至DMSS
	        awardService.flushToDmss(awardApplication);
	    	return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
    }
	
	//校验奖励申请信息
	public String validateAdd(){
//		validateBasicInfo(person, true);
//		validateEdit();
		if(0 == awardApplication.getSession() || -1 == awardApplication.getSession()){
			this.addFieldError("awardApplication.session", AwardInfo.ERROR_SESSION_NULL);
		}
		if(hasErrors()){
			//成果模块报奖校验
			if(null != request.getParameter("ex_appflag") && Integer.parseInt(request.getParameter("ex_appflag"))==1){
				valView = "/product/popAddApply.jsp";
			}else{
				valView = "/award/moesocial/application/apply/add.jsp";
			}
			unitDetails = this.awardService.getUnitDetailByAccountInfo(baseService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
			Product product = (Product) dao.query(Product.class,proId);
			if(product.getProductType()!= null)
				ptypeid = product.getProductType();
			return INPUT;
		}else{
			return null;
		}
	}
	
	//提交申请
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String submit(){
		try{
			if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
				jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}
			awardApplication = (AwardApplication) dao.query(AwardApplication.class, entityId.trim());
			if(awardApplication.getFile() != null && awardApplication.getFile().endsWith(".doc")) {
				String basePath = ApplicationContainer.sc.getRealPath("/");
				File curFile = new File(basePath + awardApplication.getFile());
				awardService.updateDirectorData(curFile, baseService.getBelongIdByLoginer(loginer));	//更新负责人
			}
			awardService.submitAwardAppProduct(awardApplication.getProduct().getId());
			if(awardApplication.getApplicantSubmitStatus() == 3 || awardApplication.getStatus() != 1 || awardApplication.getFinalAuditStatus() == 3){//奖励申请已提交
				jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_SUBMIT_ALREADY);
				return INPUT;
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 0, 3, null);
			auditMap.put("auditInfo",auditInfo);
			auditMap.put("isSubUni", 0);
			awardApplication.submit(auditMap);//提交操作结果
			if(loginer.getCurrentType().equals(AccountType.TEACHER) || loginer.getCurrentType().equals(AccountType.STUDENT)){//教师或学生跳过部门审核
				/* 以下代码为跳过部门审核*/
				AuditInfo auditInfoDept = new AuditInfo();
				auditInfoDept.setAuditResult(2);
				auditInfoDept.setAuditStatus(3);
				auditMap.put("auditInfo",auditInfoDept);
				awardApplication.edit(auditMap);//部门审核通过
				/* 结束 */
			}else if(loginer.getCurrentType().equals(AccountType.EXPERT)){//外部专家直接跳到部级审核
				awardApplication.setStatus(5);
			}
			awardApplication.setUpdateDate(new Date());
			dao.modify(awardApplication);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
	}
	
	public void validateSubmit() {
		publicValidate(AwardInfo.ERROR_SUBMIT_APPLY_NULL);
	}

    //准备修改申请
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toModify(){
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
	  	awardApplication = (AwardApplication) dao.query(AwardApplication.class,entityId.trim());
	  	request.setAttribute("applicationType", awardApplication.getApplicationType());
		//获取团队申请的基本信息
	    if(null != awardApplication.getApplicationType() && awardApplication.getApplicationType() == 2){
	    	product = (Product) dao.query(Product.class, awardApplication.getProduct().getId());
	    	session.put("organizationId", product.getId());
	    	}
	        //获取个人申请人信息，学术信息，任职信息
			else if(null != awardApplication.getApplicationType() && awardApplication.getApplicationType() == 1){
			    person = dao.query(Person.class, loginer.getPerson().getId());
			    academic = (Academic) dao.queryUnique("from Academic ac where ac.person.id = ? ", person.getId());
			}
	  	if(awardApplication.getFile() != null && awardApplication.getFile().endsWith(".doc")) {
			return "award2012";
		}
	  	if(awardApplication.getApplicantSubmitStatus() == 3 || awardApplication.getStatus() != 1 || awardApplication.getFinalAuditStatus() == 3){//奖励申请已提交
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_SUBMIT_ALREADY);
			return INPUT;
		}
	  	//获取成果信息部分
	  	unitDetails = this.awardService.getUnitDetailByAccountInfo(baseService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
	  	session.put("unitDetails", unitDetails);
	  	unitId = "";
	  	if(!loginer.getCurrentType().equals(AccountType.EXPERT)){//非外部专家
		  	unitId = unitId + ((awardApplication.getUniversity() != null) ? awardApplication.getUniversity().getId().trim() : " ") + "; ";
			unitId = unitId + ((awardApplication.getDepartment() != null) ? awardApplication.getDepartment().getId().trim() : " ") + "; ";
			unitId = unitId + ((awardApplication.getInstitute() != null) ? awardApplication.getInstitute().getId().trim() : " ");
	  	}else{
	  		unitId = unitId + ((awardApplication.getAgencyName() != null) ? awardApplication.getAgencyName() : " ") + "; ";
	  		unitId = unitId + ((awardApplication.getDivisionName() != null) ? awardApplication.getDivisionName() : " ") + ";  ";
	  	}
	  	
	  //处理附件
		String groupId = "file_" + awardApplication.getId();
		for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
				File curFile = fileRecord.getOriginal();
				String savePath = this.awardService.getFileName(curFile);
				awardApplication.setFile(savePath);
				fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(awardApplication.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
		}
	  	
		proId = awardApplication.getProduct().getId();
		Product product = (Product) dao.query(Product.class, proId);
		resultType = product.getProductType();
	  	Map<String, String> productmap = this.awardService.getProduct(resultType, awardApplication.getApplicant().getId(), (null != product) ? product.getId() : null);
	  	Map<String, String> disciplinemap = this.awardService.getDtype(resultType, proId);
	  	Map ptypes = Product.typeMap;
	    session.put("ptypes", ptypes);
    	session.put("ptypeid", "paper");
	    session.put("ptypes",ptypes);
	    session.put("productmap", productmap);
	    session.put("dtypemap", disciplinemap);
	    session.put("addOrModify", 2);
	    session.put("awardApplicationId", entityId.trim());
	    Map<String,String> organizations = this.awardService.getOrganization(baseService.getBelongIdByLoginer(loginer));
		session.put("organizations", organizations);
		uploadService.flush(groupId);
	    return SUCCESS;
    }
	
	public void validateToModify() {
		publicValidate(AwardInfo.ERROR_SUBMIT_APPLY_NULL);
	}
	
    //修改申请
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String modify(){
		try{
			//基本信息
			Person oldPerson = dao.query(Person.class, loginer.getPerson().getId());
			Academic oldAcademic = (Academic) dao.queryUnique("from Academic ac where ac.person.id = ? ", baseService.getBelongIdByLoginer(loginer));
			Product product = (Product) dao.query(Product.class, proId);
			if(awardApplication.getApplicationType() == 1){//以个人名义申请申请人的基本信息
				oldPerson.setName(personExtService.regularNames(person.getName()));//申请人姓名
				oldAcademic.setTutorType((null != academic.getTutorType()) ? academic.getTutorType().trim() : null);//导师类型
				oldAcademic.setSpecialityTitle((null != academic.getSpecialityTitle()) ? academic.getSpecialityTitle().trim() : null);//专业技术职务
				oldAcademic.setLastDegree((null != academic.getLastDegree()) ? academic.getLastDegree().trim() : null);//最后学历
				oldAcademic.setLastDegree((null != academic.getLanguage()) ? academic.getLanguage().trim() : null);//语言
				oldAcademic.setPostdoctor(academic.getPostdoctor());//博士后
				oldAcademic.setDiscipline(academic.getDiscipline().trim());
				oldPerson.setOfficePhone(person.getOfficePhone().trim());//办公电话
				oldPerson.setHomePhone((null != person.getHomePhone()) ? person.getHomePhone().trim() : null);//住宅电话
				oldPerson.setEmail(person.getEmail().trim());//电子邮件
				oldPerson.setMobilePhone(person.getMobilePhone().trim());//家庭电话
				oldPerson.setIdcardType(person.getIdcardType().trim());//证件类型
				oldPerson.setIdcardNumber(person.getIdcardNumber().trim());//证件号码
				oldPerson.setBirthday(person.getBirthday());//出生日期
				dao.modify(oldPerson);
				if(null != oldAcademic){
					dao.modify(oldAcademic);	
				}
			}else if(awardApplication.getApplicationType() == 2){//以团队等名义申请团队的基本信息
				product.setOrgPerson(oldPerson);
				Product oldOrganization = (Product) dao.query(Product.class, (String)session.get("organizationId"));
				oldOrganization.setOrgName(product.getOrgName().trim());//申请人
				oldOrganization.setOrgDiscipline((null != product.getOrgDiscipline()) ? product.getOrgDiscipline().trim(): null);//学科代码
				oldOrganization.setOrgOfficeAddress(product.getOrgOfficeAddress().trim());//通信地址
				oldOrganization.setOrgOfficePhone(product.getOrgOfficePhone().trim());//办公电话
				oldOrganization.setOrgEmail((null != product.getOrgEmail()) ? product.getOrgEmail().trim(): null);//电子邮件
				oldOrganization.setOrgOfficePostcode(product.getOrgOfficePostcode().trim());//邮政编码
				oldOrganization.setOrgMobilePhone(product.getOrgMobilePhone().trim());//手机
				//设置团队所在机构信息
				@SuppressWarnings("unused")
				String univid = "";
				if(deptInstFlag == 1){//研究基地
					Institute institute = (Institute) dao.query(Institute.class, product.getInstitute().getId());
					oldOrganization.setDepartment(null);
					oldOrganization.setInstitute(institute);
					if(institute != null){
						oldOrganization.setDivisionName(institute.getName());
						Agency university = (Agency) dao.query(Agency.class, institute.getSubjection().getId());
						oldOrganization.setUniversity(university);
						oldOrganization.setAgencyName(university != null ? university.getName() : null);
						oldOrganization.setProvinceName(university != null ? university.getProvince().getName() : null);
						oldOrganization.setProvince(university != null ? university.getProvince() : null);
						univid = university.getId();
					}
				}else if(deptInstFlag == 2){//院系
					Department department = (Department) dao.query(Department.class, product.getDepartment().getId());
					oldOrganization.setDepartment(department);
					oldOrganization.setInstitute(null);
					if(department != null){
						oldOrganization.setDivisionName(department.getName());
						Agency university = (Agency) dao.query(Agency.class, department.getUniversity().getId());
						oldOrganization.setUniversity(university);
						oldOrganization.setAgencyName(university != null ? university.getName() : null);
						oldOrganization.setProvinceName(university != null ? university.getProvince().getName() : null);
						oldOrganization.setProvince(university != null ? university.getProvince() : null);
						univid = university.getId();
					}
				}
				dao.modify(oldOrganization);
//				product.setOrganization(oldOrganization);
				dao.modify(product);
			}else{
				addActionError("请以团队或个人名义申请奖励");
				return ERROR;
			}
		
			//成果信息部分
			Map session = ActionContext.getContext().getSession();
			String awardApplicationId = (String)session.get("awardApplicationId");
			if(!this.awardService.checkIfUnderControl(loginer, awardApplicationId.trim(), 18, true)){
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
				return INPUT;
			}else{
				AwardApplication oldAwardApplication = (AwardApplication) dao.query(AwardApplication.class, awardApplicationId.trim());
				if(oldAwardApplication.getApplicantSubmitStatus() == 3 || oldAwardApplication.getStatus() != 1 || oldAwardApplication.getFinalAuditStatus() == 3){//奖励申请已提交
					this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_SUBMIT_ALREADY);
					return INPUT;
				}
				//保存操作结果
				Map auditMap = new HashMap();
				AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 2, awardApplication.getApplicantSubmitStatus(), null);
				auditMap.put("auditInfo",auditInfo);
				auditMap.put("isSubUni", 0);
				oldAwardApplication.edit(auditMap);
				if(awardApplication.getApplicantSubmitStatus() == 3){//提交申请
					if(loginer.getCurrentType().equals(AccountType.TEACHER) || loginer.getCurrentType().equals(AccountType.STUDENT)){//教师或学生跳过部门审核
						/* 以下代码为跳过部门审核*/
						AuditInfo auditInfoDept = new AuditInfo();
						auditInfoDept.setAuditResult(2);
						auditInfoDept.setAuditStatus(3);
						auditMap.put("auditInfo",auditInfoDept);
						oldAwardApplication.edit(auditMap);//部门审核通过
						/* 结束 */
					}else if(loginer.getCurrentType().equals(AccountType.EXPERT)){//外部专家直接跳到部级审核
						awardApplication.setStatus(5);
					}
				}
				oldAwardApplication.setSession(awardApplication.getSession());
				if(awardApplication.getAdoption() != null){
					oldAwardApplication.setAdoption(("A"+awardApplication.getAdoption()).trim().substring(1));
				}else{
					oldAwardApplication.setAdoption(awardApplication.getAdoption());
				}
				if(awardApplication.getIntroduction() != null){
					oldAwardApplication.setIntroduction(("A"+awardApplication.getIntroduction()).trim().substring(1));
				}else{
					oldAwardApplication.setIntroduction(awardApplication.getIntroduction());
				}
				if(awardApplication.getPrizeObtained()!= null){
					oldAwardApplication.setPrizeObtained(("A"+awardApplication.getPrizeObtained()).trim().substring(1));
				}else{
					oldAwardApplication.setPrizeObtained(awardApplication.getPrizeObtained());
				}
				if(awardApplication.getResponse()!= null){
					oldAwardApplication.setResponse(("A"+awardApplication.getResponse()).trim().substring(1));
				}else{
					oldAwardApplication.setResponse(awardApplication.getResponse());
				}
				oldAwardApplication.setProductName(awardApplication.getProductName());
				oldAwardApplication.setDisciplineType(awardApplication.getDisciplineType());
				//设置奖励所在机构信息
				String[] unitIds = unitId.split("; ");
				if(unitIds.length == 3){
					if(loginer.getCurrentType().equals(AccountType.TEACHER) || loginer.getCurrentType().equals(AccountType.STUDENT)){
						Agency university = (Agency) dao.query(Agency.class, unitIds[0].trim());
						oldAwardApplication.setUniversity(university);
				    	oldAwardApplication.setAgencyName(university != null ? university.getName() : null);
				    	oldAwardApplication.setProvinceName(university != null ? university.getProvince().getName() : null);
				    	oldAwardApplication.setProvince(university != null ? university.getProvince() : null);
				    	if(unitIds[1] != null && unitIds[1].trim().length() > 0){//院系id不为null
				    		Department department = (Department) dao.query(Department.class, unitIds[1].trim());
							oldAwardApplication.setDepartment(department);
							oldAwardApplication.setInstitute(null);
				    		oldAwardApplication.setDivisionName(department!= null? department.getName() : null);
				    	}else if(unitIds[2] != null && unitIds[2].trim().length() > 0){//研究基地id不为null
				    		Institute institute = (Institute) dao.query(Institute.class, unitIds[2].trim());
							oldAwardApplication.setDepartment(null);
							oldAwardApplication.setInstitute(institute);
							oldAwardApplication.setDivisionName(institute != null ? institute.getName() : null);
				    	}
					}else if(loginer.getCurrentType().equals(AccountType.EXPERT)){//外部专家账号
						oldAwardApplication.setAgencyName(unitIds[0]);
						oldAwardApplication.setDivisionName(unitIds[1]);
			    	}
				}else{
					addActionError("所选机构信息有误！");
					return ERROR;
				}
				//保存奖励申请书信息
				if (fileIds != null && fileIds.length == 1){
					Map<String, Object> sc = ActionContext.getContext().getApplication();
					String sessionId = request.getSession().getId();
					String basePath = ApplicationContainer.sc.getRealPath((String) sc.get("tempUploadPath") + "/" + sessionId);
					File path = new File(basePath + "/" + fileIds[0]);
					if (path.exists()){
						Iterator it = FileUtils.iterateFiles(path, null, false);
						File curFile = it.hasNext()? (File)it.next() : new File("nicaiwobudao31416");
						String fileName = curFile.getName();
						if (curFile.exists() && !fileName.contains("|") && !fileName.contains(";") && !fileName.contains("\\") && !fileName.contains("/") ){
							try {
								if(oldAwardApplication.getFile()!=null &&!oldAwardApplication.getFile().trim().isEmpty()){
									FileTool.fileDelete(oldAwardApplication.getFile());
								}
								String savePath = this.awardService.getFileName(curFile);
								oldAwardApplication.setFile(savePath);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			
				//处理附件
				String groupId = "file_" + oldAwardApplication.getId();
				for (FileRecord fileRecord : uploadService.getGroupFiles(groupId)) {
						File curFile = fileRecord.getOriginal();
						String savePath = this.awardService.getFileName(curFile);
						oldAwardApplication.setFile(savePath);
						fileRecord.setDest(new File(ApplicationContainer.sc.getRealPath(oldAwardApplication.getFile())));//将文件移至新的位置(不立刻执行，而在uploadService.flush时再执行)
				}
				
				
		   	    oldAwardApplication.setProductName(product.getChineseName());
		   	    dao.modify(oldAwardApplication);
				entityId = awardApplicationId;
				session.remove("awardApplicationId");
				type = 1;//奖励申请成功
				if(null != request.getParameter("ex_appflag") && Integer.parseInt(request.getParameter("ex_appflag")) == 1){
					unitDetails = this.awardService.getUnitDetailByAccountInfo(baseService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
					return LOGIN;
				}
     			uploadService.flush(groupId);
     			//同步至DMSS
    	        awardService.flushToDmss(awardApplication);
				return SUCCESS;
			}
		}catch(Exception e){
			e.printStackTrace();
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
	}

	//校验奖励申请的修改信息
	public String validateModify(){
		validateBasicInfo(person, true);
		validateEdit();
		if(hasErrors()){
			//成果模块报奖校验
			if(null != request.getParameter("ex_appflag") && Integer.parseInt(request.getParameter("ex_appflag"))==1){
				valView = "/product/popModifyApply.jsp";
			}else{
				valView = "/award/moesocial/application/apply/modify.jsp";
			}
			unitDetails = this.awardService.getUnitDetailByAccountInfo(baseService.getBelongIdByLoginer(loginer), loginer.getCurrentType());
		  	unitId = "";
		  	unitId = unitId + ((awardApplication.getUniversity() != null) ? awardApplication.getUniversity().getId().trim() : "") + "; ";
			unitId = unitId + ((awardApplication.getDepartment() != null) ? awardApplication.getDepartment().getId().trim() : "") + "; ";
			unitId = unitId + ((awardApplication.getInstitute() != null) ? awardApplication.getInstitute().getId().trim() : "");
			Product product = (Product) dao.query(Product.class,proId);
			if(product.getProductType()!= null)
				ptypeid = product.getProductType();
			return INPUT;
		}else{
			return null;
		}
	}
	
	/**
	 * 校验添加、修改申请人基本信息
	 */
	public void validateBasicInfo(Person person, boolean inAdd) {
		if(awardApplication.getApplicationType() == 1){//以个人名义申请申请人的基本信息
			if (inAdd && person.getIdcardNumber() != null && personExtService.checkIdcard(person.getIdcardNumber())){
				return;
			}
//			if (person.getOfficeAddress() != null && person.getOfficeAddress().trim().length() > 50){
//				this.addFieldError("person.officeAddress", "联系信息 —— 办公地址过长");
//			}
	//		if (person.getOfficePhone() != null && !inputValidate.checkFixedphone(person.getOfficePhone())){
	//			this.addFieldError("person.officePhone", "联系信息 —— 办公电话不合法");
	//		}
//			if (person.getOfficePostcode() != null && !inputValidate.checkPostcode(person.getOfficePostcode())){
//				this.addFieldError("person.officePostcode", "联系信息 —— 办公邮编不合法");
//			}
	//		if (person.getOfficeFax() != null && !inputValidate.checkFax(person.getOfficeFax())){
	//			this.addFieldError("person.officeFax", "联系信息 —— 办公传真不合法");
	//		}
	//		if (person.getEmail() == null || person.getEmail().trim().isEmpty()){
	//			this.addFieldError("person.email", "联系信息 —— 邮箱不应为空");
	//		} else 
				if(!inputValidate.checkEmail(person.getEmail())){
				this.addFieldError("person.email", "联系信息 —— 邮箱不合法");
			}
			if (person.getMobilePhone() != null && !inputValidate.checkCellphone(person.getMobilePhone())){
				this.addFieldError("person.mobilePhone", "联系信息 —— 移动电话不合法");
			}
			if (academic.getSpecialityTitle() != null){
				if ("-1".equals(academic.getSpecialityTitle()) || academic.getSpecialityTitle().isEmpty()) {
					academic.setSpecialityTitle(null);
				} else if (!Pattern.matches("(教授)|(副教授)|(讲师)|(助教)|(初级Ⅰ)|(初级Ⅱ)|(辅助人员)", academic.getSpecialityTitle())){
					this.addFieldError("academic.specialityTitle", "学术信息 —— 专业职称不合法");
				}
			}
			if (academic.getDiscipline() != null){
				if (academic.getDiscipline().trim().length() > 100){
					this.addFieldError("academic.discipline", "学术信息 —— 学科过长，最长100字符");
				}
			}
			if (academic.getTutorType() != null){
				if ("-1".equals(academic.getTutorType()) || academic.getTutorType().isEmpty()) {
					academic.setTutorType(null);
				} else if (!Pattern.matches("(博士生导师)|(硕士生导师)|(其他)", academic.getTutorType())) {
					this.addFieldError("academic.tutorType", "学术信息 —— 导师类型不合法");
				}
			}
			if (academic.getPostdoctor() != -1){
				if (academic.getPostdoctor() > 2 && academic.getPostdoctor() < 0){
					this.addFieldError("academic.postdoctor", "学术信息 —— 是否博士后数据不合法");
				}
			}
			if (academic.getLastDegree() != null){
				if ("-1".equals(academic.getLastDegree()) || academic.getLastDegree().isEmpty()) {
					academic.setLastDegree(null);
				} else if (!Pattern.matches("(学士)|(硕士)|(博士)", academic.getLastDegree())) {
					this.addFieldError("academic.lastDegree", "学术信息 —— 最后学位不合法");
				}
			}
		}else if(awardApplication.getApplicationType() == 2){//以团队等名义申请团队等的基本信息
			if (product.getOrgOfficeAddress() != null && product.getOrgOfficeAddress().trim().length() > 50){
				this.addFieldError("product.orgOfficeAddress", "联系信息 —— 办公地址过长");
			}
			if (product.getOrgOfficePhone() != null && !inputValidate.checkFixedphone(product.getOrgOfficePhone())){
				this.addFieldError("product.orgOfficePhone", "联系信息 —— 办公电话不合法");
			}
			if (product.getOrgOfficePostcode() != null && !inputValidate.checkPostcode(product.getOrgOfficePostcode())){
				this.addFieldError("product.orgOfficePostcode", "联系信息 —— 办公邮编不合法");
			}
//			if (organization.getEmail() == null || organization.getEmail().trim().isEmpty()){
//				this.addFieldError("organization.email", "联系信息 —— 邮箱不应为空");
//			} else 
//				if(!inputValidate.checkEmail(organization.getEmail())){
//				this.addFieldError("organization.email", "联系信息 —— 邮箱不合法");
//			}
			if (product.getOrgDiscipline() != null){
				if (product.getOrgDiscipline().trim().length() > 100){
					this.addFieldError("product.orgDiscipline", "学术信息 —— 学科过长，最长100字符");
				}
			}
//			if((deptInstFlag == 1 && organization.getInstitute().getId() == null) || (deptInstFlag == 2 && organization.getDepartment().getId() == null)){
//				this.addFieldError("organization.agencyName", ProjectInfo.ERROR_AGENCY_NULL);
//			}
//			if(organization.getDivisionName()==null || organization.getDivisionName().trim().isEmpty()){
//				this.addActionError(ProjectInfo.ERROR_PROJECT_DEPT_INST_NULL);
//			}
		}
	}	
	
	//校验添加、修改成果信息
	public void validateEdit(){
		Product product = (Product) dao.query(Product.class, proId);
		if(null == product.getProductType() || product.getProductType().trim().length()==0){
			this.addFieldError("awardApplication.productType", AwardInfo.ERROR_PRODUCT_TYPE_NULL);
		}
		if(null == proId || "-1".equals(proId)){
			this.addFieldError("productId", AwardInfo.ERROR_PRODUCT_NULL);
		}
		if(null == awardApplication.getDisciplineType() || "-1".equals(awardApplication.getDisciplineType())){
			this.addFieldError("awardApplication.disciplineType", AwardInfo.ERROR_DTYPE_NULL);
		}
		if(null == unitId || ("-1").equals(unitId)){
			this.addFieldError("unitId", AwardInfo.ERROR_UNITID_NULL);
		}
		if(null != awardApplication.getPrizeObtained() && awardApplication.getPrizeObtained().trim().length()>500){
			this.addFieldError("awardApplication.prizeObtained", AwardInfo.ERROR_PRIZE_OBTAINED_OUT);
		}
		if(null != awardApplication.getResponse() && awardApplication.getResponse().trim().length()>500){
			this.addFieldError("awardApplication.response", AwardInfo.ERROR_RESPONSE_OUT);
		}
		if(null != awardApplication.getAdoption() && awardApplication.getAdoption().trim().length()>500){
			this.addFieldError("awardApplication.adoption", AwardInfo.ERROR_ADOPTION_OUT);
		}
		if(null != awardApplication.getIntroduction() && awardApplication.getIntroduction().trim().length()>20000){
			this.addFieldError("awardApplication.introduction", AwardInfo.ERROR_INTRODUCTION_OUT);
		}
	}
    //根据奖励申请id删除申请
    @SuppressWarnings("unchecked")
    @Transactional
	public String delete(){
    	try{
			for(int i = 0; i < entityIds.size(); i++){
				Boolean isRight = this.awardService.checkIfUnderControl(loginer, entityIds.get(i),18, true);
				if(!isRight){
					jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
					return INPUT;
				}
			}
			AccountType accountType = loginer.getCurrentType();
	    	for(int i = 0; i < entityIds.size(); i++){
	    		awardApplication = (AwardApplication) dao.query(AwardApplication.class, entityIds.get(i));
	    		if(accountType.equals(AccountType.ADMINISTRATOR) || (accountType.compareTo(AccountType.INSTITUTE)>0 && awardApplication.getApplicantSubmitStatus() != 3 && awardApplication.getStatus() == 1 && awardApplication.getFinalAuditStatus() != 3)){
		    		String filename = awardApplication.getFile();
		    		Product product = (Product) dao.query(Product.class, awardApplication.getProduct().getId());
					if(product.getSubmitStatus() != 3) {
						dao.delete(product);
					}
		    		dao.delete(AwardApplication.class, entityIds.get(i));
		    		if(null != filename && !"".equals(filename)){
					   FileTool.fileDelete(filename);
		    		}
	    		}
	    	}
		    return SUCCESS;
    	}catch(Exception e){
			e.printStackTrace();
			this.addFieldError(GlobalInfo.ERROR_INFO, GlobalInfo.ERROR_EXCEPTION_INFO);
			return INPUT;
		}
   }
    
	@SuppressWarnings("unchecked")
	public void validateDelete(){
		if (entityIds == null || entityIds.isEmpty()) {
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_DELETE_NULL);
			jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_DELETE_NULL);
		}
	}

	//奖励申请表下载
	public String download()throws Exception{
		return SUCCESS;
	}
	
	//文件下载流
	public InputStream getTargetFile() throws Exception{
		String filename = "";
		if(filePath != null && filePath.length()!= 0){
			filename = new String(filePath.getBytes("iso8859-1"),"utf-8");
			filePath = new String(filename.substring(filename.lastIndexOf("/")+1).getBytes(), "ISO-8859-1");
			return ApplicationContainer.sc.getResourceAsStream(filename);
		 }
		return null;
	}
	
	/**
	 * 文件是否存在校验
	 */
	@SuppressWarnings("unchecked")
	public String validateFile()throws Exception{
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
			addActionError("您所选择的奖励不在您的管辖范围内！");
			return ERROR;
		}
		if (null == entityId || entityId.trim().isEmpty()) {//奖励申请id不能为空
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_FILE_NOT_MATCH);
		} else {
			awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());
			if(null == awardApplication){//奖励申请不能为空
				jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARDAPPLIY_NULL);
			} else if(awardApplication.getFile() == null || (!awardApplication.getFile().equals(filePath))){//文件名匹配
				jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_FILE_NOT_MATCH);
			} else {//文件存在
				String filename = new String(filePath.getBytes("iso8859-1"),"utf-8");
				if(null == ApplicationContainer.sc.getResourceAsStream(filename)){
					jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_FILE_NOT_EXIST);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 下载申请书的模板文档
	 * @throws UnsupportedEncodingException
	 */
	public String downloadModel() throws UnsupportedEncodingException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String("MOEAward2012.exe".getBytes(), "ISO-8859-1"));
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String add2012() {
		System.out.println("add2012");
		try {
			if(entityId != null) {
				if(!awardService.checkIfUnderControl(loginer, entityId.trim(), 18, true)){
					jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_NOT_IN_SCOPE);
					return INPUT;
				}
				AccountType accountType = loginer.getCurrentType();
				if(accountType.equals(AccountType.ADMINISTRATOR) || (accountType.compareTo(AccountType.INSTITUTE)>0 && awardApplication.getApplicantSubmitStatus() != 3 && awardApplication.getStatus() == 1 && awardApplication.getFinalAuditStatus() != 3)){
					AwardApplication oldApplication = (AwardApplication) dao.query(AwardApplication.class, entityId);
					Product product = (Product) dao.query(Product.class, oldApplication.getProduct().getId());
					if(product.getSubmitStatus() != 3) {
						dao.delete(product);
					}
					String filename = oldApplication.getFile();
					if(null != filename && !filename.isEmpty()){
						  FileTool.fileDelete(filename);
			    	}
					dao.delete(oldApplication);
				} else {
					jsonMap.put(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_REVIEW_SUBMIT_AlREADY);
					return INPUT;
				}
			}
			Map auditMap = new HashMap();
			AuditInfo auditInfo = this.awardService.getAuditInfo(loginer, 2, awardApplication.getApplicantSubmitStatus(), null);
			auditMap.put("auditInfo",auditInfo);
			auditMap.put("isSubUni", 0);
			awardApplication.edit(auditMap);
			String groupId = "file_add";
			for(FileRecord fileRecord : uploadService.getGroupFiles(groupId)){
				File curFile = fileRecord.getOriginal();
				String productId = awardService.importAwardFileData(curFile, baseService.getBelongIdByLoginer(loginer), awardApplication.getApplicantSubmitStatus());
				if(productId != null && !productId.isEmpty()) {
					if(awardApplication.getApplicantSubmitStatus() == 3){//提交申请更新人员信息
						awardService.updateDirectorData(curFile, baseService.getBelongIdByLoginer(loginer));
					}
					awardApplication = awardService.importBigSection(curFile, awardApplication);
					String savePath = this.awardService.getFileName(curFile);
					awardApplication.setFile(savePath);
					awardApplication.setCreateMode(0);
					awardApplication.setCreateDate(new Date());
					// 设置参数
					awardApplication = awardService.setAwardApplicationParam(awardApplication, productId);
					dao.add(awardApplication);
				    this.entityId = awardApplication.getId();
				    System.out.println("awardApplication entityId " + entityId);
				    type = 1;
				}
			}
			return SUCCESS;
			
		/*	Map<String, Object> sc = ActionContext.getContext().getApplication();
			String sessionId = request.getSession().getId();
			String basePath = ApplicationContainer.sc.getRealPath((String) sc.get("tempUploadPath") + "/" + sessionId);
			Iterator it = FileUtils.iterateFiles(new File(basePath + "/" + fileIds[0]), null, false);
		    productId = awardService.importAwardFileData(curFile, loginer.getCurrentBelongId(), awardApplication.getApplicantSubmitStatus());
			System.out.println("成果导入完成. productId: " + productId);
			if(productId != null && !productId.isEmpty()) {
				if(awardApplication.getApplicantSubmitStatus() == 3){//提交申请更新人员信息
					awardService.updateDirectorData(curFile, loginer.getCurrentBelongId());
				}
				awardApplication = awardService.importBigSection(curFile, awardApplication);
				String savePath = this.awardService.getFileName(curFile);
				awardApplication.setFile(savePath);
				awardApplication.setCreateMode(0);
				awardApplication.setCreateDate(new Date());
				// 设置参数
				awardApplication = awardService.setAwardApplicationParam(awardApplication, productId);
				dao.add(awardApplication);
			    this.entityId = awardApplication.getId();
			    System.out.println("awardApplication entityId " + entityId);
			    type = 1;
			}*/
			
		} 
			catch (Exception e) {
			e.printStackTrace();
			this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_AWARDAPPLIY_UPLOAD_ERROR);
			return INPUT;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String validateAdd2012() {
		System.out.println("validateAdd2012");
		if(fileIds != null && fileIds.length > 1){
			this.addFieldError("file", AwardInfo.ERROR_FILE_OUT);
		}
		else if (fileIds != null && fileIds.length == 1){
			Map<String, Object> sc = ActionContext.getContext().getApplication();
			String sessionId = request.getSession().getId();
			String basePath = ApplicationContainer.sc.getRealPath((String) sc.get("tempUploadPath") + "/" + sessionId);
			File path = new File(basePath + "/" + fileIds[0]);
			if (path.exists()){
				Iterator it = FileUtils.iterateFiles(path, null, false);
				File curFile = it.hasNext()? (File)it.next() : new File("nicaiwobudao31416");
				String fileName = curFile.getName();
				System.out.println(path);
				if (curFile.exists() && !fileName.contains("|") && !fileName.contains(";") && !fileName.contains("\\") && !fileName.contains("/") ){
					System.out.println("校验word...");
					String wordErrorInfo = awardService.checkAwardWordFile(curFile, baseService.getBelongIdByLoginer(loginer));
					if(wordErrorInfo != null) {
						this.addFieldError(GlobalInfo.ERROR_INFO, wordErrorInfo);
					}
				} else {
					this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_FILE_NOT_EXIST);
				}
			} else {
				this.addFieldError(GlobalInfo.ERROR_INFO, AwardInfo.ERROR_FILE_NOT_EXIST);
			}
		}
		if(hasErrors()){
			valView = "/award/moesocial/application/apply/add.jsp";
			return INPUT;
		} else {
			return null;
		}
	}
	
	public int getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(int reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public int getReviewAuditStatus() {
		return reviewAuditStatus;
	}

	public void setReviewAuditStatus(int reviewAuditStatus) {
		this.reviewAuditStatus = reviewAuditStatus;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getValView() {
		return valView;
	}

	public void setValView(String valView) {
		this.valView = valView;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Map<String, String> getUnitDetails() {
		return unitDetails;
	}
	public void setUnitDetails(Map<String, String> unitDetails) {
		this.unitDetails = unitDetails;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Academic getAcademic() {
		return academic;
	}
	public void setAcademic(Academic academic) {
		this.academic = academic;
	}

	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public int getDeptInstFlag() {
		return deptInstFlag;
	}

	public void setDeptInstFlag(int deptInstFlag) {
		this.deptInstFlag = deptInstFlag;
	}

	public int getIsSelectOrgan() {
		return isSelectOrgan;
	}

	public void setIsSelectOrgan(int isSelectOrgan) {
		this.isSelectOrgan = isSelectOrgan;
	}

}
