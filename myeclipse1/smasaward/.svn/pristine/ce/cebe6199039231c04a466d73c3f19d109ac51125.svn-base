package csdc.tool.webService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.dom4j.Element;
import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Expert;
import csdc.bean.ProjectApplication;
import csdc.bean.ProjectFee;
import csdc.bean.SmdbProjectApplication;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.StringTool;
import csdc.tool.beanutil.mergeStrategy.Append;
import csdc.tool.execution.importer.Tools;

/**
 * 项目申请解析入库
 * 2015-1-12
 */
public class ProjectApplicationRecordResolver extends BaseRecordResolver implements RecordResolver {
	private Element itemRoot;//已经读取了xml的document文件
	private SimpleDateFormat sdf; 
	private String projectType;
	private Tools tools;//申报数据同步用到的工具类
	private Set<String> existingExperts;//本年度专家库中专家申报项目的专家统计，记录 "申请者姓名+申请者id"
	public ProjectApplicationRecordResolver() {
		
	}
	
	public ProjectApplicationRecordResolver(
			Element item, 
			SimpleDateFormat sdf, 
			IHibernateBaseDao dao, 
			String projectType, 
			List<String> badIDList) {
		this.itemRoot = item;
		this.dao = dao;
		this.sdf = sdf;
		this.projectType = projectType;
		this.badItemsList = badIDList;
	}
	
	public ProjectApplicationRecordResolver(
			Element item, 
			SimpleDateFormat sdf, 
			Tools tools,
			IHibernateBaseDao dao, 
			String projectType, 
			List<String> badIDList, 
			Set existingExperts) {
		this.itemRoot = item;
		this.dao = dao;
		this.tools = tools;
		this.sdf = sdf;
		this.projectType = projectType;
		this.badItemsList = badIDList;
		this.existingExperts = existingExperts;
	}
	//方式二
	public void parse(
			Element item, 
			SimpleDateFormat sdf,
			IHibernateBaseDao dao, 
			String projectType, 
			List<String> badIDList) throws ParseException {
		this.itemRoot = item;
		this.dao = dao;
		this.sdf = sdf;
		this.projectType = projectType;
		this.badItemsList = badIDList;
		//解析
		parse();
	}
	
	/**
	 * 修复往年数据入库同步
	 * @return
	 * @throws ParseException 
	 */
	public void parse() throws ParseException{
		if (projectType.equals("general")) {
			parseGeneralResolver();//一般项目
		} else if (projectType.equals("instp")) {
			parseInstpResolver();//基地项目
		}
	}
	/**
	 * 2015年（含2015年）的项目申报解析数据同步
	 * @throws ParseException
	 */
	public void work() throws ParseException{
		if (projectType.equals("general")) {
			workGeneralResolver();//一般项目
		} else if (projectType.equals("instp")) {
			workInstpResolver();//基地项目
		}
	}
	//2015年（含2015年）的一般申报项目解析
	//同步新项目与修复往年项目不同，新项目同步要包含基本信息且不需要同步评审结果信息
	//1.查询中间表是否存在，不存在跳2；存在不处理；
	//2.解析入库申报表，在中间表建立相应记录；
	//3.smdb的数据默认是规范的；
	//查询smas中是否有关联，若关联取出关联smas中的id，然后添加到中间表；否则增加smas记录，然后添加到中间表
	@Transactional
	private void workGeneralResolver() throws ParseException {
		String smdbAppID = getArgumentByTag(itemRoot, "id");
		String directorNames = getArgumentByTag(itemRoot, "director");

		String projectName = getArgumentByTag(itemRoot, "projectName");
		/**
		 * 说明：
		 * （1）同步的申报项目，默认参评状态即设置 isReawiewAble = 1
		 * （2）同步的数据，类型IsImported = 0，即从smdb同步产生
		 * （3）smdb中的申报项目有多人情况，已在同步之前处理，所以smas获取的都是项目负责人为一个的数据，且项目负责人信息时排在首位的项目负责人
		 * （4）项目申请人姓名，同步多个项目申请人姓名。原因，需要在smas如库时修改项目申报人往年申报项目字段信息，如果只同步一个人，则项目信息此项统计信息不全。
		 * （5）项目对应测库内专家的一般项目申报年份字段依据：只要申报了当前年份的一般项目（不包含专项任务项目，2015极其以后的年份专项任务不同步且不管参评与否）都将改年份信息进行追加。
		 */
		if (!smdbAppID.equals("null") &&!directorNames.equals("null") && !directorNames.equals("") && !projectName.equals("null") && !projectName.equals("")) {
			Map argsMap = new HashMap<String, String>();
			argsMap.put("projectName", projectName);
			argsMap.put("director", directorNames);
			//查询一般项目申报表中是否存在
			if (!checkIsAlreadyInSmdbProjectApp(smdbAppID)) {
				//不存在入库，然后建立中间表
				String universityCode = getArgumentByTag(itemRoot, "universityCode");
				String universityName = getArgumentByTag(itemRoot, "universityName");
				String projectType = getArgumentByTag(itemRoot, "projectType");
				String year = getArgumentByTag(itemRoot, "year");
				String applyDate = getArgumentByTag(itemRoot, "applyDate");
				String planEndDate = getArgumentByTag(itemRoot, "planEndDate");
				String disciplineType = getArgumentByTag(itemRoot, "disciplineType");
				String discipline = getArgumentByTag(itemRoot, "discipline");
				String researchType = getArgumentByTag(itemRoot, "researchType");
				String finalResultType = getArgumentByTag(itemRoot, "finalResultType");
				String productType = getArgumentByTag(itemRoot, "productType");//--
				String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
				
				String applyFee = getArgumentByTag(itemRoot, "applyFee");
				String otherFee = getArgumentByTag(itemRoot, "otherFee");
				String file = getArgumentByTag(itemRoot, "file");
				String note = getArgumentByTag(itemRoot, "note");
				String type = getArgumentByTag(itemRoot, "type");
			
				String auditStatus = getArgumentByTag(itemRoot, "auditStatus");//
				String members = getArgumentByTag(itemRoot, "members");
				String job = getArgumentByTag(itemRoot, "job");
				String title = getArgumentByTag(itemRoot, "title");
				String birthday = getArgumentByTag(itemRoot, "birthday");
				String email = getArgumentByTag(itemRoot, "email");
				String idcardType = getArgumentByTag(itemRoot, "idcardType");
				String idcardNumber = getArgumentByTag(itemRoot, "idcardNumber");
				String mobile = getArgumentByTag(itemRoot, "mobile");
				String phone = getArgumentByTag(itemRoot, "phone");
				String postcode = getArgumentByTag(itemRoot, "postcode");
				String address = getArgumentByTag(itemRoot, "address");
				String gender = getArgumentByTag(itemRoot, "gender");
				String foreign = getArgumentByTag(itemRoot, "foreign");
				String degree = getArgumentByTag(itemRoot, "degree");
				String education = getArgumentByTag(itemRoot, "education");
				String department = getArgumentByTag(itemRoot, "department");
				String SinossApplicationID = getArgumentByTag(itemRoot, "SinossApplicationID");//新增
			
				ProjectApplication projectApp = new ProjectApplication();
				//添加导入时间
				projectApp.setIsImported(0);// 0：正常流程产生，1，从smas中录入产生
				//入库数据默认为参评状态
				projectApp.setIsReviewable(1);
				projectApp.setImportedDate(new Date()); 
				//存在多种申请人情况
				projectApp.setDirector(directorNames);
				projectApp.setProjectName(projectName);
				
				if (!SinossApplicationID.equals("null")) {
					projectApp.setSinossID(SinossApplicationID);
				}
				if (!universityName.equals("null")) {
					projectApp.setUniversityName(universityName);
				}
				if (!universityCode.equals("null")) {
					projectApp.setUniversityCode(universityCode);
				}
				if (!projectType.equals("null")) {
					projectApp.setProjectType(projectType);
				}
				if (!year.equals("null")) {
					projectApp.setYear(Integer.parseInt(year));
				}
				if (!applyDate.equals("null") && !applyDate.equals("")) {
					projectApp.setApplyDate(_date(applyDate));
				}
				if (!planEndDate.equals("null") && !planEndDate.equals("")) {
					projectApp.setPlanEndDate(_date(planEndDate));
				}
				if (!discipline.equals("null")) {
					projectApp.setDiscipline(discipline);				
				}
				if (!disciplineType.equals("null")) {
					projectApp.setDisciplineType(disciplineType);
				}
				if (!researchType.equals("null")) {
					projectApp.setResearchType(researchType);
				}
				if (!finalResultType.equals("null")) {
					projectApp.setFinalResultType(finalResultType);
				}
				if (!productType.equals("null")) {
					projectApp.setProductType(productType);
				}
				if (!productTypeOther.equals("null")) {
					projectApp.setProductTypeOther(productTypeOther);
				}
				if (!applyFee.equals("null")) {
					projectApp.setApplyFee(applyFee);
				}
				if (!otherFee.equals("null")) {
					projectApp.setOtherFee(otherFee);
				}
				if (!file.equals("null")) {
					projectApp.setFile(file);
				}
				if (!note.equals("null")) {
					projectApp.setNote(note);
				}
				if (!type.equals("null")) {
					projectApp.setType(type);
				}
				if (!auditStatus.equals("null")) {
					projectApp.setAuditStatus(auditStatus);
				}
				if (!members.equals("null")) {
					projectApp.setMembers(members);
				}
				if (!job.equals("null")) {
					projectApp.setJob(job);
				}
				if (!title.equals("null")) {
					projectApp.setTitle(title);
				}
				if (!birthday.equals("null") && !birthday.equals("")) {
					projectApp.setBirthday(_date(birthday));
				}
				if (!email.equals("null")) {
					projectApp.setEmail(email);
				}
				if (!idcardNumber.equals("null")) {
					projectApp.setIdcard(idcardNumber);
				}
				if (!mobile.equals("null")) {
					projectApp.setMobile(mobile);
				}
				if (!phone.equals("null")) {
					projectApp.setPhone(phone);
				}
				if (!postcode.equals("null")) {
					projectApp.setPostcode(postcode);
				}
				if (!address.equals("null")) {
					projectApp.setAddress(address);
				}
				if (!gender.equals("null")) {
					projectApp.setGender(gender);
				}
				if (!foreign.equals("null")) {
					projectApp.setForeign(foreign);
				}
				if (!degree.equals("null")) {
					projectApp.setDegree(degree);
				}
				if (!education.equals("null")) {
					projectApp.setEducation(education);
				}
				if (!department.equals("null")) {
					projectApp.setDepartment(department);
				}
				String projectFeeContent = getArgumentByTag(itemRoot, "projectFeeItem");
				ProjectFee pojFee = null;
				if (!projectFeeContent.equals("null") && !projectFeeContent.equals("")) {
					Element feeElementRoot = (Element) itemRoot.selectNodes("//projectFeeItem").get(0);
					pojFee = getProjectFeeObject(feeElementRoot, type);
					dao.add(pojFee);
				}
				if (pojFee != null) {
					projectApp.setProjectFee(pojFee);
				}
				//为申请人添加当前当前申请申报年份
				//根据每个申请人姓名，与所在高校姓名，如果专家库中有对应专家，则修改该专家往年申报年份字段信息
				String directorNameFirst = !(directorNames.indexOf(";") > 0) ? directorNames : directorNames.substring(0, directorNames.indexOf(";"));
				String[] applicants = directorNames.split(";");
				for (int i = 0; i < applicants.length; i++) {
					String applicantName = applicants[i].trim();//去除前后空格
					
					//修改项目申报者信息
					Expert expert = null;
					if (applicantName.equals(directorNameFirst)) {
						expert = tools.getExpert(applicantName, universityCode);
					} else {
						//从memeber中获取相应的高校名称
						String univName = _searchDirectUniversity(members, applicantName);
						if (univName == null) {
							continue;
						}
						try {
							String univCode = tools.getUnivByName(univName).getCode();
							expert = tools.getExpert(applicantName, univCode);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					//追加专家当前申请年份
					if (expert != null) {
						existingExperts.add(expert.getName() + "/" + expert.getId());
						Append append2 = new Append("\\s*;\\s*", "; ", false);
						String v1 = expert.getGeneralApplyYears();
						String v2 = year;
						String end = append2.merge(v1, v2);
						expert.setGeneralApplyYears(end);
						dao.modify(expert);
					}	
				}//for
				
				String smasPojAppID = (String) dao.add(projectApp);
				SmdbProjectApplication smdbPojApp = new SmdbProjectApplication();
				smdbPojApp.setImportedDate(new Date());
				//TODO 代以后替换为，执行操作账号
				smdbPojApp.setImportPerson("张楠");
				smdbPojApp.setIsAdded(1);
				smdbPojApp.setSmasApplID(smasPojAppID);
				smdbPojApp.setSmdbApplID(smdbAppID);
				dao.add(smdbPojApp);
			} else {
				//存在数据库中的其他情形_修复代码
			}
		} else {//记录
			badItemsList.add(smdbAppID);
		}
	}
	@Transactional
	private void workInstpResolver() throws ParseException {
		String smdbAppID = getArgumentByTag(itemRoot, "id");
		String directorNames = getArgumentByTag(itemRoot, "director");
		String projectName = getArgumentByTag(itemRoot, "projectName");
		/**
		 * （1）同步的申报项目，默认参评状态即设置 isReawiewAble = 1
		 * （2）同步的数据，类型IsImported = 0，即从smdb同步产生
		 * （3）smdb中的申报项目有多人情况，已在同步之前处理，所以smas获取的都是项目负责人为一个的数据，且项目负责人信息时排在首位的项目负责人
		 * （4）项目申请人姓名，同步多个项目申请人姓名。原因，需要在smas如库时修改项目申报人往年申报项目字段信息，如果只同步一个人，则项目信息此项统计信息不全,无法统计。
		 * 
		 */
		if (!smdbAppID.equals("null") &&!directorNames.equals("null") && !directorNames.equals("") && !projectName.equals("null") && !projectName.equals("")) {
			Map argsMap = new HashMap<String, String>();
			argsMap.put("projectName", projectName);
			argsMap.put("director", directorNames);
			if (!checkIsAlreadyInSmdbProjectApp(smdbAppID)) {
				//不存在,获取数据，然后入库，并建立中间表
				String instituteName = getArgumentByTag(itemRoot, "instituteName");
				String directorUniversity = getArgumentByTag(itemRoot, "directorUniversity");
				String directorDivisionName = getArgumentByTag(itemRoot, "directorDivisionName");
				String disciplineDirection = getArgumentByTag(itemRoot, "disciplineDirection");
				String universityCode = getArgumentByTag(itemRoot, "universityCode");
				String universityName = getArgumentByTag(itemRoot, "universityName");
				String projectType = getArgumentByTag(itemRoot, "projectType");
				String year = getArgumentByTag(itemRoot, "year");
				String applyDate = getArgumentByTag(itemRoot, "applyDate");
				String planEndDate = getArgumentByTag(itemRoot, "planEndDate");
				String disciplineType = getArgumentByTag(itemRoot, "disciplineType");
				String discipline = getArgumentByTag(itemRoot, "discipline");
				
				String finalResultType = getArgumentByTag(itemRoot, "finalResultType");
				String productType = getArgumentByTag(itemRoot, "productType");
				String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");
				String file = getArgumentByTag(itemRoot, "file");
				String note = getArgumentByTag(itemRoot, "note");
				String auditStatus = getArgumentByTag(itemRoot, "auditStatus");//
				String firstAuditResult = getArgumentByTag(itemRoot, "firstAuditResult");
				String firstAuditDate = getArgumentByTag(itemRoot, "firstAuditDate");
				
				String type = getArgumentByTag(itemRoot, "type");
				String members = getArgumentByTag(itemRoot, "members");
				String job = getArgumentByTag(itemRoot, "job");
				String title = getArgumentByTag(itemRoot, "title");
				String birthday = getArgumentByTag(itemRoot, "birthday");
				
				String SinossApplicationID = getArgumentByTag(itemRoot, "SinossApplicationID");//新增

				ProjectApplication projectApp = new ProjectApplication();
				projectApp.setIsImported(0);// 0：正常流程产生，1，从smas中录入产生
				projectApp.setIsReviewable(1);//入库数据默认为参评状态
				projectApp.setImportedDate(new Date()); 
				//存在多种申请人情况
				projectApp.setDirector(directorNames);
				projectApp.setProjectName(projectName);
				
				if (!SinossApplicationID.equals("null")) {
					projectApp.setSinossID(SinossApplicationID);
				}
				
				if (!type.equals("null")) {
					projectApp.setType(type);
				}
				
				if (!instituteName.equals("null")) {
					projectApp.setInstituteName(instituteName);
				}
				if (!directorUniversity.equals("null")) {
					projectApp.setDirectorUniversity(directorUniversity);
				}
				if (!directorDivisionName.equals("null")) {
					projectApp.setDirectorDivisionName(directorDivisionName);
				}
				if (!disciplineDirection.equals("null")) {
					projectApp.setDisciplineDirection(disciplineDirection);
				}
				
				if (!universityName.equals("null")) {
					projectApp.setUniversityName(universityName);
				}
				if (!universityCode.equals("null")) {
					projectApp.setUniversityCode(universityCode);
				}
				if (!projectType.equals("null")) {
					projectApp.setProjectType(projectType);
				}
				if (!year.equals("null")) {
					projectApp.setYear(Integer.parseInt(year));
				}
				if (!applyDate.equals("null") && !applyDate.equals("")) {
					projectApp.setApplyDate(_date(applyDate));
				}
				if (!planEndDate.equals("null") && !planEndDate.equals("")) {
					projectApp.setPlanEndDate(_date(planEndDate));
				}
				if (!discipline.equals("null")) {
					projectApp.setDiscipline(discipline);				
				}
				if (!disciplineType.equals("null")) {
					projectApp.setDisciplineType(disciplineType);
				}
				if (!finalResultType.equals("null")) {
					projectApp.setFinalResultType(finalResultType);
				}
				if (!productType.equals("null")) {
					projectApp.setProductType(productType);
				}
				if (!productTypeOther.equals("null")) {
					projectApp.setProductTypeOther(productTypeOther);
				}
				if (!file.equals("null")) {
					projectApp.setFile(file);
				}
				if (!note.equals("null")) {
					projectApp.setNote(note);
				}
				if (!firstAuditResult.equals("null")) {
					projectApp.setFirstAuditResult(firstAuditResult);
				}
				if (!firstAuditDate.equals("null")) {
					projectApp.setFirstAuditDate(_date(firstAuditDate));
				}
				if (!auditStatus.equals("null")) {
					projectApp.setAuditStatus(auditStatus);
				}
				if (!members.equals("null")) {
					projectApp.setMembers(members);
				}
				if (!job.equals("null")) {
					projectApp.setJob(job);
				}
				if (!title.equals("null")) {
					projectApp.setTitle(title);
				}
				if (!birthday.equals("null") && !birthday.equals("")) {
					projectApp.setBirthday(_date(birthday));
				}
				String projectFeeContent = getArgumentByTag(itemRoot, "projectFeeItem");
				ProjectFee pojFee = null;
				if (!projectFeeContent.equals("null") && !projectFeeContent.equals("")) {
					Element feeElementRoot = (Element) itemRoot.selectNodes("//projectFeeItem").get(0);
					pojFee = getProjectFeeObject(feeElementRoot, type);
					dao.add(pojFee);
				}
				if (pojFee != null) {
					projectApp.setProjectFee(pojFee);
				}
				
				//为申请人添加当前2015(含2015)年度申报年份
				//根据每个申请人姓名，与所在高校姓名，如果专家库中有对应专家，则修改该专家往年申报年份字段信息
				String directorNameFirst = !(directorNames.indexOf(";") > 0) ? directorNames : directorNames.substring(0, directorNames.indexOf(";"));
				String[] applicants = directorNames.split(";");
				for (int i = 0; i < applicants.length; i++) {
					String applicantName = applicants[i];
					//修改项目申报者信息
					Expert expert = null;
					if (applicantName.equals(directorNameFirst)) {
						expert = tools.getExpert(applicantName, universityCode);
					} else {
						//从memeber中获取相应的高校名称
						String univName = _searchDirectUniversity(members, applicantName);
						if (univName == null) {
							continue;
						}
						try {
							String univCode = tools.getUnivByName(univName).getCode();
							expert = tools.getExpert(applicantName, univCode);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					//专家基地申报年份信心追加
					if (expert != null) {
						existingExperts.add(expert.getName() + "/" + expert.getId());
						Append append2 = new Append("\\s*;\\s*", "; ", false);
						String v1 = expert.getGeneralApplyYears();
						String v2 = year;
						String end = append2.merge(v1, v2);
						expert.setInstpApplyYears(end);
						dao.modify(expert);
					}
				}//for
				String smasPojAppID = (String) dao.add(projectApp);
				SmdbProjectApplication smdbPojApp = new SmdbProjectApplication();
				smdbPojApp.setImportedDate(new Date());
				//TODO 代以后替换为，执行操作账号
				smdbPojApp.setImportPerson("张楠");
				smdbPojApp.setIsAdded(1);
				smdbPojApp.setSmasApplID(smasPojAppID);
				smdbPojApp.setSmdbApplID(smdbAppID);
				dao.add(smdbPojApp);
			} else {
				//中间表中存在记录
				//执行修复程序···
			}
			
		} else {//数据不规范
			badItemsList.add(smdbAppID);
		}
	}
	//查询smas中是否有关联，若关联取出关联smas中的id，然后添加到中间表；否则增加smas记录，然后添加到中间表
	//同步往年其他数据
	@Transactional
	private void parseGeneralResolver() throws ParseException {
		String smdbAppID = getArgumentByTag(itemRoot, "id");
		String director = getArgumentByTag(itemRoot, "director");
		String projectName = getArgumentByTag(itemRoot, "projectName");
		
		if (!smdbAppID.equals("null") &&!director.equals("null") && !director.equals("") && !projectName.equals("null") && !projectName.equals("")) {
			Map argsMap = new HashMap<String, String>();
			argsMap.put("projectName", projectName);
			argsMap.put("director", director);
			String smasID = checkProjectExists(argsMap);
			if (!smasID.equals("")) {
				//关联，修改smas中的项目申报对象
				ProjectApplication projectApp = (ProjectApplication) dao.query(ProjectApplication.class, smasID);
				
				String note = getArgumentByTag(itemRoot, "note");//
				String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");//
				String finalAuditorName = getArgumentByTag(itemRoot, "finalAuditorName");//
				String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");//
				String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");//
				String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");//
				String reviewDate = getArgumentByTag(itemRoot, "reviewDate");//
				String reviewTotalScore = getArgumentByTag(itemRoot, "reviewTotalScore");//
				String reviewAverageScore = getArgumentByTag(itemRoot, "reviewAverageScore");//
				String reviewWay = getArgumentByTag(itemRoot, "reviewWay");//
				String reviewResult = getArgumentByTag(itemRoot, "reviewResult");//
				String reviewOpinion = getArgumentByTag(itemRoot, "reviewOpinion");//
				String reviewGread = getArgumentByTag(itemRoot, "reviewGread");//
				String reviewOpinionQualitative = getArgumentByTag(itemRoot, "reviewOpinionQualitative");//
				String productType = getArgumentByTag(itemRoot, "productType");//--
				String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
		
				if (!productType.equals("null")) {
					projectApp.setProductType(productType);
				}
				if (!productTypeOther.equals("null")) {
					projectApp.setProductTypeOther(productTypeOther);
				}
				
				projectApp.setIsImported(0);//0是smdb中导入方式，导入日期不知道不修改
				
				if (!note.equals("null")) {
					projectApp.setNote(note);
				}
				if (!finalAuditResult.equals("null")) {
					projectApp.setFinalAuditResult(Integer.parseInt(finalAuditResult));
				}
				if (!finalAuditorName.equals("null")) {
					projectApp.setFinalAuditorName(finalAuditorName);
				}
				if (!finalAuditDate.equals("null")) {
					projectApp.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectApp.setFinalAuditOpinion(finalAuditOpinion);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectApp.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				if (!reviewDate.equals("null")) {
					projectApp.setReviewDate(_date(reviewDate));
				}
				if (!reviewTotalScore.equals("null")) {
					projectApp.setReviewTotalScore(Double.parseDouble(reviewTotalScore));
				}
				if (!reviewAverageScore.equals("null")) {
					projectApp.setReviewAverageScore(Double.parseDouble(reviewAverageScore));
				}
				if (!reviewWay.equals("null")) {
					projectApp.setReviewWay(Integer.parseInt(reviewWay));
				}
				if (!reviewResult.equals("null")) {
					projectApp.setReviewResult(Integer.parseInt(reviewResult));
				}
				if (!reviewOpinion.equals("null")) {
					projectApp.setReviewOpinion(reviewOpinion);
				}
				if (!reviewGread.equals("null")) {
					projectApp.setReviewGrade(reviewGread);
				}
				if (!reviewOpinionQualitative.equals("null")) {
					projectApp.setReviewOpinionQualitative(reviewOpinionQualitative);
				}
				dao.addOrModify(projectApp);
				//修改中间表
				if (!checkIsAlreadyInSmdbProjectApp(smasID, smdbAppID)) {
					SmdbProjectApplication smdbApp = new SmdbProjectApplication();
					smdbApp.setImportedDate(new Date());
					smdbApp.setImportPerson("张楠");
					smdbApp.setIsAdded(1);
					smdbApp.setSmasApplID(smasID);
					smdbApp.setSmdbApplID(smdbAppID);
					dao.add(smdbApp);
				}
			} else {
				//不存在，入库
				String type = getArgumentByTag(itemRoot, "type");
				String discipline = getArgumentByTag(itemRoot, "discipline");
				String disciplineType = getArgumentByTag(itemRoot, "disciplineType");
				String year = getArgumentByTag(itemRoot, "year");
				String researchType = getArgumentByTag(itemRoot, "researchType");
				String projectType = getArgumentByTag(itemRoot, "projectType");
				String isReviewable = getArgumentByTag(itemRoot, "isReviewable");
				String applyDate = getArgumentByTag(itemRoot, "applyDate");
				String planEndDate = getArgumentByTag(itemRoot, "planEndDate");
				String file = getArgumentByTag(itemRoot, "file");
				String productType = getArgumentByTag(itemRoot, "productType");//--
				String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
				
				String applyFee = getArgumentByTag(itemRoot, "applyFee");
				String otherFee = getArgumentByTag(itemRoot, "otherFee");
				String department = getArgumentByTag(itemRoot, "department");
				String universityName = getArgumentByTag(itemRoot, "universityName");
				String universityCode = getArgumentByTag(itemRoot, "universityCode");
				String universityType = getArgumentByTag(itemRoot, "universityType");
				String auditStatus = getArgumentByTag(itemRoot, "auditStatus");
				String note = getArgumentByTag(itemRoot, "note");//
				String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");//
				String finalAuditorName = getArgumentByTag(itemRoot, "finalAuditorName");//
				String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");//
				String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");//
				String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");//
				String reviewDate = getArgumentByTag(itemRoot, "reviewDate");//
				String reviewTotalScore = getArgumentByTag(itemRoot, "reviewTotalScore");//
				String reviewAverageScore = getArgumentByTag(itemRoot, "reviewAverageScore");//
				String reviewWay = getArgumentByTag(itemRoot, "reviewWay");//
				String reviewResult = getArgumentByTag(itemRoot, "reviewResult");//
				String reviewOpinion = getArgumentByTag(itemRoot, "reviewOpinion");//
				String reviewGread = getArgumentByTag(itemRoot, "reviewGread");//
				String reviewOpinionQualitative = getArgumentByTag(itemRoot, "reviewOpinionQualitative");//
				
				String members = getArgumentByTag(itemRoot, "members");
				String job = getArgumentByTag(itemRoot, "job");
				String title = getArgumentByTag(itemRoot, "title");
				String birthday = getArgumentByTag(itemRoot, "birthday");
				String email = getArgumentByTag(itemRoot, "email");
				String idcardType = getArgumentByTag(itemRoot, "idcardType");
				String idcardNumber = getArgumentByTag(itemRoot, "idcardNumber");
				String mobile = getArgumentByTag(itemRoot, "mobile");
				String phone = getArgumentByTag(itemRoot, "phone");
				String postcode = getArgumentByTag(itemRoot, "postcode");
				String address = getArgumentByTag(itemRoot, "address");
				String gender = getArgumentByTag(itemRoot, "gender");
				String foreign = getArgumentByTag(itemRoot, "foreign");
				String degree = getArgumentByTag(itemRoot, "degree");
				String education = getArgumentByTag(itemRoot, "education");
			
				ProjectApplication projectApp = new ProjectApplication();
				//添加导入时间
				projectApp.setIsImported(0);// 0：正常流程产生，1，从smas中录入产生
				projectApp.setImportedDate(new Date()); 
				projectApp.setDirector(director);
				projectApp.setProjectName(projectName);
				if (!type.equals("null")) {
					projectApp.setType(type);
				}
				if (!discipline.equals("null")) {
					projectApp.setDiscipline(discipline);				
				}
				if (!disciplineType.equals("null")) {
					projectApp.setDisciplineType(disciplineType);
				}
				if (!year.equals("null")) {
					projectApp.setYear(Integer.parseInt(year));
				}
				if (!researchType.equals("null")) {
					projectApp.setResearchType(researchType);
				}
				if (!projectType.equals("null")) {
					projectApp.setProjectType(projectType);
				}
				if (!isReviewable.equals("null")) {
					projectApp.setIsReviewable(Integer.parseInt(isReviewable));
				}
				if (!applyDate.equals("null") && !applyDate.equals("")) {
					projectApp.setApplyDate(_date(applyDate));
				}
				if (!planEndDate.equals("null") && !planEndDate.equals("")) {
					projectApp.setPlanEndDate(_date(planEndDate));
				}
				if (!file.equals("null")) {
					projectApp.setFile(file);
				}
				if (!productType.equals("null")) {
					projectApp.setProductType(productType);
				}
				if (!productTypeOther.equals("null")) {
					projectApp.setProductTypeOther(productTypeOther);
				}
				if (!applyFee.equals("null")) {
					projectApp.setApplyFee(applyFee);
				}
				if (!otherFee.equals("null")) {
					projectApp.setOtherFee(otherFee);
				}
				if (!department.equals("null")) {
					projectApp.setDepartment(department);
				}
				if (!universityName.equals("null")) {
					projectApp.setUniversityName(universityName);
				}
				if (!universityCode.equals("null")) {
					projectApp.setUniversityCode(universityCode);
				}
				if (!auditStatus.equals("null")) {
					projectApp.setAuditStatus(auditStatus);
				}
				if (!note.equals("null")) {
					projectApp.setNote(note);
				}
				if (!finalAuditResult.equals("null")) {
					projectApp.setFinalAuditResult(Integer.parseInt(finalAuditResult));
				}
				if (!finalAuditorName.equals("null")) {
					projectApp.setFinalAuditorName(finalAuditorName);
				}
				if (!finalAuditDate.equals("null")) {
					projectApp.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectApp.setFinalAuditOpinion(finalAuditOpinion);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectApp.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				if (!reviewDate.equals("null")) {
					projectApp.setReviewDate(_date(reviewDate));
				}
				if (!reviewTotalScore.equals("null")) {
					projectApp.setReviewTotalScore(Double.parseDouble(reviewTotalScore));
				}
				if (!reviewAverageScore.equals("null")) {
					projectApp.setReviewAverageScore(Double.parseDouble(reviewAverageScore));
				}
				if (!reviewWay.equals("null")) {
					projectApp.setReviewWay(Integer.parseInt(reviewWay));
				}
				if (!reviewResult.equals("null")) {
					projectApp.setReviewResult(Integer.parseInt(reviewResult));
				}
				if (!reviewOpinion.equals("null")) {
					projectApp.setReviewOpinion(reviewOpinion);
				}
				if (!reviewGread.equals("null")) {
					projectApp.setReviewGrade(reviewGread);
				}
				if (!reviewOpinionQualitative.equals("null")) {
					projectApp.setReviewOpinionQualitative(reviewOpinionQualitative);
				}
				if (!members.equals("null")) {
					projectApp.setMembers(members);
				}
				if (!job.equals("null")) {
					projectApp.setJob(job);
				}
				if (!title.equals("null")) {
					projectApp.setTitle(title);
				}
				if (!birthday.equals("null") && !birthday.equals("")) {
					projectApp.setBirthday(_date(birthday));
				}
				if (!email.equals("null")) {
					projectApp.setEmail(email);
				}
				if (!idcardNumber.equals("null")) {
					projectApp.setIdcard(idcardNumber);
				}
				if (!mobile.equals("null")) {
					projectApp.setMobile(mobile);
				}
				if (!phone.equals("null")) {
					projectApp.setPhone(phone);
				}
				if (!postcode.equals("null")) {
					projectApp.setPostcode(postcode);
				}
				if (!address.equals("null")) {
					projectApp.setAddress(address);
				}
				if (!gender.equals("null")) {
					projectApp.setGender(gender);
				}
				if (!foreign.equals("null")) {
					projectApp.setForeign(foreign);
				}
				if (!degree.equals("null")) {
					projectApp.setDegree(degree);
				}
				if (!education.equals("null")) {
					projectApp.setEducation(education);
				}
				String projectFeeContent = getArgumentByTag(itemRoot, "projectFeeItem");
				ProjectFee pojFee = null;
				if (!projectFeeContent.equals("null") && !projectFeeContent.equals("")) {
					Element feeElementRoot = (Element) itemRoot.selectNodes("//projectFeeItem").get(0);
					pojFee = getProjectFeeObject(feeElementRoot, type);
					dao.add(pojFee);
				}
				
				if (pojFee != null) {
					projectApp.setProjectFee(pojFee);
				}
				String smasPojAppID = (String) dao.add(projectApp);
				SmdbProjectApplication smdbPojApp = new SmdbProjectApplication();
				//关联，数据不用入库,产生中间表记录
				smdbPojApp.setImportedDate(new Date());
				smdbPojApp.setImportPerson("张楠");
				smdbPojApp.setIsAdded(1);
				smdbPojApp.setSmasApplID(smasPojAppID);
				smdbPojApp.setSmdbApplID(smdbAppID);
				dao.add(smdbPojApp);
			}
		} else {//数据规范
			badItemsList.add(smdbAppID);
		}
	}
	@Transactional
	private void parseInstpResolver() throws ParseException {
		String smdbAppID = getArgumentByTag(itemRoot, "id");
		String director = getArgumentByTag(itemRoot, "director");
		String projectName = getArgumentByTag(itemRoot, "projectName");
		
		if (!smdbAppID.equals("null") &&!director.equals("null") && !director.equals("") && !projectName.equals("null") && !projectName.equals("")) {
			
			Map argsMap = new HashMap<String, String>();
			argsMap.put("projectName", projectName);
			argsMap.put("director", director);
			String smasID = checkProjectExists(argsMap);
			if (!smasID.equals("")) {
				//关联 修改smas中的项目申报对象
				ProjectApplication projectApp =  (ProjectApplication) dao.query(ProjectApplication.class, smasID);
				String note = getArgumentByTag(itemRoot, "note");//
				String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");//
				String finalAuditorName = getArgumentByTag(itemRoot, "finalAuditorName");//
				String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");//
				String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");//
				String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");//
				String reviewDate = getArgumentByTag(itemRoot, "reviewDate");//
				String reviewTotalScore = getArgumentByTag(itemRoot, "reviewTotalScore");//
				String reviewAverageScore = getArgumentByTag(itemRoot, "reviewAverageScore");//
				String reviewWay = getArgumentByTag(itemRoot, "reviewWay");//
				String reviewResult = getArgumentByTag(itemRoot, "reviewResult");//
				String reviewOpinion = getArgumentByTag(itemRoot, "reviewOpinion");//
				String reviewGread = getArgumentByTag(itemRoot, "reviewGread");//
				String reviewOpinionQualitative = getArgumentByTag(itemRoot, "reviewOpinionQualitative");//
				String productType = getArgumentByTag(itemRoot, "productType");//--
				String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
				
				projectApp.setIsImported(0);//0是smdb中导入方式，导入日期不知道不修改
				if (!note.equals("null")) {
					projectApp.setNote(note);
				}
				if (!finalAuditResult.equals("null")) {
					projectApp.setFinalAuditResult(Integer.parseInt(finalAuditResult));
				}
				if (!finalAuditorName.equals("null")) {
					projectApp.setFinalAuditorName(finalAuditorName);
				}
				if (!finalAuditDate.equals("null")) {
					projectApp.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectApp.setFinalAuditOpinion(finalAuditOpinion);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectApp.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				if (!reviewDate.equals("null")) {
					projectApp.setReviewDate(_date(reviewDate));
				}
				if (!reviewTotalScore.equals("null")) {
					projectApp.setReviewTotalScore(Double.parseDouble(reviewTotalScore));
				}
				if (!reviewAverageScore.equals("null")) {
					projectApp.setReviewAverageScore(Double.parseDouble(reviewAverageScore));
				}
				if (!reviewWay.equals("null")) {
					projectApp.setReviewWay(Integer.parseInt(reviewWay));
				}
				if (!reviewResult.equals("null")) {
					projectApp.setReviewResult(Integer.parseInt(reviewResult));
				}
				if (!reviewOpinion.equals("null")) {
					projectApp.setReviewOpinion(reviewOpinion);
				}
				if (!reviewGread.equals("null")) {
					projectApp.setReviewGrade(reviewGread);
				}
				if (!reviewOpinionQualitative.equals("null")) {
					projectApp.setReviewOpinionQualitative(reviewOpinionQualitative);
				}
				if (!productType.equals("null")) {
					projectApp.setProductType(productType);
				}
				if (!productTypeOther.equals("null")) {
					projectApp.setProductTypeOther(productTypeOther);
				}
				dao.addOrModify(projectApp);
				
				if (!checkIsAlreadyInSmdbProjectApp(smasID, smdbAppID)) {
					SmdbProjectApplication smdbApp = new SmdbProjectApplication();
					smdbApp.setImportedDate(new Date());
					smdbApp.setImportPerson("张楠");
					smdbApp.setIsAdded(1);
					smdbApp.setSmasApplID(smasID);
					smdbApp.setSmdbApplID(smdbAppID);
					dao.add(smdbApp);
				}
			} else {
				//不存在，入库
				String type = getArgumentByTag(itemRoot, "type");
				String discipline = getArgumentByTag(itemRoot, "discipline");
				String disciplineType = getArgumentByTag(itemRoot, "disciplineType");
				String year = getArgumentByTag(itemRoot, "year");
				String researchType = getArgumentByTag(itemRoot, "researchType");
				String projectType = getArgumentByTag(itemRoot, "projectType");
				String isReviewable = getArgumentByTag(itemRoot, "isReviewable");
				String applyDate = getArgumentByTag(itemRoot, "applyDate");
				String planEndDate = getArgumentByTag(itemRoot, "planEndDate");
				String file = getArgumentByTag(itemRoot, "file");
				String productType = getArgumentByTag(itemRoot, "productType");//--
				String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
				
//				String applyFee = getArgumentByTag(itemRoot, "applyFee");
//				String otherFee = getArgumentByTag(itemRoot, "otherFee");
//				String department = getArgumentByTag(itemRoot, "department");
				String universityName = getArgumentByTag(itemRoot, "universityName");
				String universityCode = getArgumentByTag(itemRoot, "universityCode");
				String universityType = getArgumentByTag(itemRoot, "universityType");
				String auditStatus = getArgumentByTag(itemRoot, "auditStatus");
				String members = getArgumentByTag(itemRoot, "members");
				String job = getArgumentByTag(itemRoot, "job");
				String title = getArgumentByTag(itemRoot, "title");
				String birthday = getArgumentByTag(itemRoot, "birthday");
//				String email = getArgumentByTag(itemRoot, "email");
//				String idcardType = getArgumentByTag(itemRoot, "idcardType");
//				String idcardNumber = getArgumentByTag(itemRoot, "idcardNumber");
//				String mobile = getArgumentByTag(itemRoot, "mobile");
//				String phone = getArgumentByTag(itemRoot, "phone");
//				String postcode = getArgumentByTag(itemRoot, "postcode");
//				String address = getArgumentByTag(itemRoot, "address");
//				String gender = getArgumentByTag(itemRoot, "gender");
//				String foreign = getArgumentByTag(itemRoot, "foreign");
//				String degree = getArgumentByTag(itemRoot, "degree");
//				String education = getArgumentByTag(itemRoot, "education");
				String note = getArgumentByTag(itemRoot, "note");//
				String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");//
				String finalAuditorName = getArgumentByTag(itemRoot, "finalAuditorName");//
				String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");//
				String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");//
				String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");//
				String reviewDate = getArgumentByTag(itemRoot, "reviewDate");//
				String reviewTotalScore = getArgumentByTag(itemRoot, "reviewTotalScore");//
				String reviewAverageScore = getArgumentByTag(itemRoot, "reviewAverageScore");//
				String reviewWay = getArgumentByTag(itemRoot, "reviewWay");//
				String reviewResult = getArgumentByTag(itemRoot, "reviewResult");//
				String reviewOpinion = getArgumentByTag(itemRoot, "reviewOpinion");//
				String reviewGread = getArgumentByTag(itemRoot, "reviewGread");//
				String reviewOpinionQualitative = getArgumentByTag(itemRoot, "reviewOpinionQualitative");//
				
				//基地项目特有
				String instituteName = getArgumentByTag(itemRoot, "instituteName");
				String directorUniversity = getArgumentByTag(itemRoot, "directorUniversity");
				String directorDivisionName = getArgumentByTag(itemRoot, "directorDivisionName");
				String disciplineDirection = getArgumentByTag(itemRoot, "disciplineDirection");
				
				ProjectApplication projectApp = new ProjectApplication();
				//添加导入时间
				projectApp.setIsImported(0);// 0：正常流程产生，1，从smas中录入产生
				projectApp.setImportedDate(new Date());
				projectApp.setDirector(director);
				projectApp.setProjectName(projectName);
				if (!type.equals("null")) {
					projectApp.setType(type);
				}
				if (!discipline.equals("null")) {
					projectApp.setDiscipline(discipline);				
				}
				if (!disciplineType.equals("null")) {
					projectApp.setDisciplineType(disciplineType);
				}
				if (!year.equals("null")) {
					projectApp.setYear(Integer.parseInt(year));
				}
				if (!projectType.equals("null")) {
					projectApp.setProjectType(projectType);
				}
				if (!researchType.equals("null")) {
					projectApp.setResearchType(researchType);
				}
				if (!isReviewable.equals("null")) {
					projectApp.setIsReviewable(Integer.parseInt(isReviewable));
				}
				if (!applyDate.equals("null") && !applyDate.equals("")) {
					projectApp.setApplyDate(_date(applyDate));
				}
				if (!planEndDate.equals("null") && !planEndDate.equals("")) {
					projectApp.setPlanEndDate(_date(planEndDate));
				}
				if (!file.equals("null")) {
					projectApp.setFile(file);
				}
				if (!productType.equals("null")) {
					projectApp.setProductType(productType);
				}
				if (!productTypeOther.equals("null")) {
					projectApp.setProductTypeOther(productTypeOther);
				}
				
				if (!universityName.equals("null")) {
					projectApp.setUniversityName(universityName);
				}
				if (!universityCode.equals("null")) {
					projectApp.setUniversityCode(universityCode);
				}
				if (!auditStatus.equals("null")) {
					projectApp.setAuditStatus(auditStatus);
				}
				if (!note.equals("null")) {
					projectApp.setNote(note);
				}
				if (!finalAuditResult.equals("null")) {
					projectApp.setFinalAuditResult(Integer.parseInt(finalAuditResult));
				}
				if (!finalAuditorName.equals("null")) {
					projectApp.setFinalAuditorName(finalAuditorName);
				}
				if (!finalAuditDate.equals("null")) {
					projectApp.setFinalAuditDate(_date(finalAuditDate));
				}
				if (!finalAuditOpinion.equals("null")) {
					projectApp.setFinalAuditOpinion(finalAuditOpinion);
				}
				if (!finalAuditOpinionFeedback.equals("null")) {
					projectApp.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
				}
				if (!reviewDate.equals("null")) {
					projectApp.setReviewDate(_date(reviewDate));
				}
				if (!reviewTotalScore.equals("null")) {
					projectApp.setReviewTotalScore(Double.parseDouble(reviewTotalScore));
				}
				if (!reviewAverageScore.equals("null")) {
					projectApp.setReviewAverageScore(Double.parseDouble(reviewAverageScore));
				}
				if (!reviewWay.equals("null")) {
					projectApp.setReviewWay(Integer.parseInt(reviewWay));
				}
				if (!reviewResult.equals("null")) {
					projectApp.setReviewResult(Integer.parseInt(reviewResult));
				}
				if (!reviewOpinion.equals("null")) {
					projectApp.setReviewOpinion(reviewOpinion);
				}
				if (!reviewGread.equals("null")) {
					projectApp.setReviewGrade(reviewGread);
				}
				if (!reviewOpinionQualitative.equals("null")) {
					projectApp.setReviewOpinionQualitative(reviewOpinionQualitative);
				}
				if (!members.equals("null")) {
					projectApp.setMembers(members);
				}
				if (!job.equals("null")) {
					projectApp.setJob(job);
				}
				if (!title.equals("null")) {
					projectApp.setTitle(title);
				}
				if (!birthday.equals("null") && !birthday.equals("")) {
					projectApp.setBirthday(_date(birthday));
				}

				if (!instituteName.equals("null")) {
					projectApp.setInstituteName(instituteName);
				}
				if (!directorDivisionName.equals("null")) {
					projectApp.setDirectorDivisionName(directorDivisionName);
				}
				if (!directorUniversity.equals("null")) {
					projectApp.setDirectorUniversity(directorUniversity);
				}
				if (!disciplineDirection.equals("null")) {
				projectApp.setDisciplineDirection(disciplineDirection);		
				}
				
				String projectFeeContent = getArgumentByTag(itemRoot, "projectFeeItem");
				ProjectFee pojFee = null;
				if (!projectFeeContent.equals("null") && !projectFeeContent.equals("")) {
					Element feeElementRoot = (Element) itemRoot.selectNodes("//projectFeeItem").get(0);
					pojFee = getProjectFeeObject(feeElementRoot, type);
					dao.add(pojFee);
				}
				if (pojFee != null) {
					projectApp.setProjectFee(pojFee);
				}
				String smasPojAppID = (String) dao.add(projectApp);
				SmdbProjectApplication smdbPojApp = new SmdbProjectApplication();
				//关联，数据不用入库,产生中间表
				smdbPojApp.setImportedDate(new Date());
				smdbPojApp.setImportPerson("张楠");
				smdbPojApp.setIsAdded(1);
				smdbPojApp.setSmasApplID(smasPojAppID);
				smdbPojApp.setSmdbApplID(smdbAppID);
				dao.add(smdbPojApp);
			}
		} else {//数据不合法
			badItemsList.add(smdbAppID);
		}
	}
	/**
	 * 检查smdbProjectAplication中间表是否存在项目申请记录
	 * 存在返回true,不存在返回false
	 * @param smasID
	 * @param smdbID
	 * @return
	 */
	private boolean checkIsAlreadyInSmdbProjectApp(String smasID, String smdbID) {
		Map argMap = new HashMap();
		argMap.put("smdbApplID", smdbID);
		argMap.put("smasApplID", smasID);
		String hql = "select middleProjectApp.id from SmdbProjectApplication middleProjectApp where middleProjectApp.smdbApplID = :smdbApplID and middleProjectApp.smasApplID = :smasApplID";
		int exists = (int) dao.count(hql, argMap);
		if(exists != 0) {
			return true;
		}
		return false;
	}
	/**
	 * 检查smdbProjectAplication中间表是否存在项目申请记录
	 * 存在返回true,不存在返回false
	 * @param smasID
	 * @param smdbID
	 * @return
	 */
	private boolean checkIsAlreadyInSmdbProjectApp(String smdbID) {
		Map argMap = new HashMap();
		argMap.put("smdbApplID", smdbID);
		String hql = "select middleProjectApp.id from SmdbProjectApplication middleProjectApp where middleProjectApp.smdbApplID = :smdbApplID ";
		int exists = (int) dao.count(hql, argMap);
		if(exists > 0) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public void parseFix(Element item, SimpleDateFormat sdf,
			IHibernateBaseDao dao, String projectType, String applicationId)
			throws ParseException {
		this.itemRoot = item;
		this.dao = dao;
		this.sdf = sdf;
		this.projectType = projectType;
		//解析
		parseFix(applicationId);
	}
	
	/**
	 * 修复补充
	 * @param applicationId
	 * @throws ParseException
	 */
	public void parseFix(String applicationId) throws ParseException{
		if (projectType.equals("general")) {
			parseFixGeneralResolver(applicationId);
		} else if (projectType.equals("instp")) {
			parseFixInstpResolver(applicationId);
		}
	}
	
	
	private void parseFixGeneralResolver(String applicationId) throws ParseException {
		ProjectApplication projectApp = (ProjectApplication) dao.query(ProjectApplication.class, applicationId);
		String smdbAppID = getArgumentByTag(itemRoot, "id");
		String director = getArgumentByTag(itemRoot, "director");
		String projectName = getArgumentByTag(itemRoot, "projectName");
		
		String note = getArgumentByTag(itemRoot, "note");//
		String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");//
		String finalAuditorName = getArgumentByTag(itemRoot, "finalAuditorName");//
		String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");//
		String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");//
		String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");//
		String reviewDate = getArgumentByTag(itemRoot, "reviewDate");//
		String reviewTotalScore = getArgumentByTag(itemRoot, "reviewTotalScore");//
		String reviewAverageScore = getArgumentByTag(itemRoot, "reviewAverageScore");//
		String reviewWay = getArgumentByTag(itemRoot, "reviewWay");//
		String reviewResult = getArgumentByTag(itemRoot, "reviewResult");//
		String reviewOpinion = getArgumentByTag(itemRoot, "reviewOpinion");//
		String reviewGread = getArgumentByTag(itemRoot, "reviewGread");//
		String reviewOpinionQualitative = getArgumentByTag(itemRoot, "reviewOpinionQualitative");//
		String productType = getArgumentByTag(itemRoot, "productType");//--
		String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
		
		if (!productType.equals("null")) {
			projectApp.setProductType(productType);
		}
		if (!productTypeOther.equals("null")) {
			projectApp.setProductTypeOther(productTypeOther);
		}
		
		projectApp.setIsImported(0);
		
			projectApp.setNote(note);
			if (!note.equals("null")) {
		}
		if (!finalAuditResult.equals("null")) {
			projectApp.setFinalAuditResult(Integer.parseInt(finalAuditResult));
		}
		if (!finalAuditorName.equals("null")) {
			projectApp.setFinalAuditorName(finalAuditorName);
		}
		if (!finalAuditDate.equals("null")) {
			projectApp.setFinalAuditDate(_date(finalAuditDate));
		}
		if (!finalAuditOpinion.equals("null")) {
			projectApp.setFinalAuditOpinion(finalAuditOpinion);
		}
		if (!finalAuditOpinionFeedback.equals("null")) {
			projectApp.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
		}
		if (!reviewDate.equals("null")) {
			projectApp.setReviewDate(_date(reviewDate));
		}
		if (!reviewTotalScore.equals("null")) {
			projectApp.setReviewTotalScore(Double.parseDouble(reviewTotalScore));
		}
		if (!reviewAverageScore.equals("null")) {
			projectApp.setReviewAverageScore(Double.parseDouble(reviewAverageScore));
		}
		if (!reviewWay.equals("null")) {
			projectApp.setReviewWay(Integer.parseInt(reviewWay));
		}
		if (!reviewResult.equals("null")) {
			projectApp.setReviewResult(Integer.parseInt(reviewResult));
		}
		if (!reviewOpinion.equals("null")) {
			projectApp.setReviewOpinion(reviewOpinion);
		}
		if (!reviewGread.equals("null")) {
			projectApp.setReviewGrade(reviewGread);
		}
		if (!reviewOpinionQualitative.equals("null")) {
			projectApp.setReviewOpinionQualitative(reviewOpinionQualitative);
		}
		dao.addOrModify(projectApp);
		//修改中间表
		if (!checkIsAlreadyInSmdbProjectApp(applicationId, smdbAppID)) {
			SmdbProjectApplication smdbApp = new SmdbProjectApplication();
			smdbApp.setImportedDate(new Date());
			smdbApp.setImportPerson("张楠");
			smdbApp.setIsAdded(1);
			smdbApp.setSmasApplID(applicationId);
			smdbApp.setSmdbApplID(smdbAppID);
			dao.add(smdbApp);
		}
	}
	
	private void parseFixInstpResolver(String applicationId) throws ParseException {
		//关联修改smas中的项目申报对象
		ProjectApplication projectApp =  (ProjectApplication) dao.query(ProjectApplication.class, applicationId);
		String smdbAppID = getArgumentByTag(itemRoot, "id");
		String director = getArgumentByTag(itemRoot, "director");
		String projectName = getArgumentByTag(itemRoot, "projectName");
		String note = getArgumentByTag(itemRoot, "note");//
		String finalAuditResult = getArgumentByTag(itemRoot, "finalAuditResult");//
		String finalAuditorName = getArgumentByTag(itemRoot, "finalAuditorName");//
		String finalAuditDate = getArgumentByTag(itemRoot, "finalAuditDate");//
		String finalAuditOpinion = getArgumentByTag(itemRoot, "finalAuditOpinion");//
		String finalAuditOpinionFeedback = getArgumentByTag(itemRoot, "finalAuditOpinionFeedback");//
		String reviewDate = getArgumentByTag(itemRoot, "reviewDate");//
		String reviewTotalScore = getArgumentByTag(itemRoot, "reviewTotalScore");//
		String reviewAverageScore = getArgumentByTag(itemRoot, "reviewAverageScore");//
		String reviewWay = getArgumentByTag(itemRoot, "reviewWay");//
		String reviewResult = getArgumentByTag(itemRoot, "reviewResult");//
		String reviewOpinion = getArgumentByTag(itemRoot, "reviewOpinion");//
		String reviewGread = getArgumentByTag(itemRoot, "reviewGread");//
		String reviewOpinionQualitative = getArgumentByTag(itemRoot, "reviewOpinionQualitative");//
		String productType = getArgumentByTag(itemRoot, "productType");//--
		String productTypeOther = getArgumentByTag(itemRoot, "productTypeOther");//--
		
		projectApp.setIsImported(0);//0是smdb中导入方式，导入日期不知道不修改
		if (!note.equals("null")) {
			projectApp.setNote(note);
		}
		if (!finalAuditResult.equals("null")) {
			projectApp.setFinalAuditResult(Integer.parseInt(finalAuditResult));
		}
		if (!finalAuditorName.equals("null")) {
			projectApp.setFinalAuditorName(finalAuditorName);
		}
		if (!finalAuditDate.equals("null")) {
			projectApp.setFinalAuditDate(_date(finalAuditDate));
		}
		if (!finalAuditOpinion.equals("null")) {
			projectApp.setFinalAuditOpinion(finalAuditOpinion);
		}
		if (!finalAuditOpinionFeedback.equals("null")) {
			projectApp.setFinalAuditOpinionFeedback(finalAuditOpinionFeedback);
		}
		if (!reviewDate.equals("null")) {
			projectApp.setReviewDate(_date(reviewDate));
		}
		if (!reviewTotalScore.equals("null")) {
			projectApp.setReviewTotalScore(Double.parseDouble(reviewTotalScore));
		}
		if (!reviewAverageScore.equals("null")) {
			projectApp.setReviewAverageScore(Double.parseDouble(reviewAverageScore));
		}
		if (!reviewWay.equals("null")) {
			projectApp.setReviewWay(Integer.parseInt(reviewWay));
		}
		if (!reviewResult.equals("null")) {
			projectApp.setReviewResult(Integer.parseInt(reviewResult));
		}
		if (!reviewOpinion.equals("null")) {
			projectApp.setReviewOpinion(reviewOpinion);
		}
		if (!reviewGread.equals("null")) {
			projectApp.setReviewGrade(reviewGread);
		}
		if (!reviewOpinionQualitative.equals("null")) {
			projectApp.setReviewOpinionQualitative(reviewOpinionQualitative);
		}
		if (!productType.equals("null")) {
			projectApp.setProductType(productType);
		}
		if (!productTypeOther.equals("null")) {
			projectApp.setProductTypeOther(productTypeOther);
		}
		dao.addOrModify(projectApp);
		
		if (!checkIsAlreadyInSmdbProjectApp(applicationId, smdbAppID)) {
			SmdbProjectApplication smdbApp = new SmdbProjectApplication();
			smdbApp.setImportedDate(new Date());
			smdbApp.setImportPerson("张楠");
			smdbApp.setIsAdded(1);
			smdbApp.setSmasApplID(applicationId);
			smdbApp.setSmdbApplID(smdbAppID);
			dao.add(smdbApp);
		}
	}
	
	/**
	 * 查询member字段中的“非第一负责人”的高校名称，如果没有返回null
	 * 用于修改申请人往年申报年份字段
	 * @param members
	 * @param director
	 * @return
	 */
	private String _searchDirectUniversity(String members, String serarchName) {
		String universityName = null;
		if (members.indexOf(serarchName) > 0) {
			String[] array = members.split(";");
			for (int i = 0; i < array.length; i++) {
				String temp = array[i];
				if (temp.indexOf(serarchName) > 0) {
					int start = temp.indexOf(serarchName) + serarchName.length() + 1;
					int end = temp.indexOf(")");
					if (end != -1) {
						universityName = temp.substring(start, end);
					}
				}
			}
		}
		return universityName;
	}
}
