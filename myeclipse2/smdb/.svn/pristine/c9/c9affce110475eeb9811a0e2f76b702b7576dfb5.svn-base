package csdc.service.imp;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.csdc.domain.fm.ThirdUploadForm;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Academic;
//import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.AwardGranted;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.bean.AwardApplication;
import csdc.bean.AwardReview;
import csdc.bean.Book;
import csdc.bean.Paper;
import csdc.bean.Consultation;
import csdc.bean.Product;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.service.IAwardService;
import csdc.tool.FileTool;
import csdc.tool.WordTool;
import csdc.tool.info.WordInfo;
import csdc.tool.bean.AccountType;
/**
 * 奖励管理实现类
 * @author 余潜玉
 */
@Transactional
public class AwardService extends BaseService implements IAwardService {
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	/*------------------------获取奖励相关查询语句--------------------------------*/
	/**
	 * 根据登陆者的身份得到查询语句(用于奖励申报数据)
	 * @param	hql1 查询语句的查询部分
	 * @param	hql2查询语句的选择数据表部分
	 * @param	accountType登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return StringBuffer 查询语句
	 */
	public StringBuffer getHql(String hql1, String hql2, AccountType accountType){
		   StringBuffer hql = new StringBuffer();
			if(accountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
				hql.append(hql1).append(",aa.finalAuditStatus, aa.finalAuditResult, aa.finalAuditDate").append(hql2);
			}else if(accountType.equals(AccountType.MINISTRY)){//部级
				hql.append(hql1).append(",aa.ministryAuditStatus,aa.ministryAuditResult,aa.reviewStatus,aa.reviewResult,aa.reviewAuditStatus,aa.reviewAuditResult,aa.reviewAuditDate");
				hql.append(hql2).append(" and aa.status>=5");
			}else if(accountType.equals(AccountType.PROVINCE)){//省级
				hql.append(hql1).append(",aa.provinceAuditStatus,aa.provinceAuditResult,aa.provinceAuditDate").append(hql2).append(" and un.type=4 and un.subjection.id=:belongId and aa.status>=4");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
				hql.append(hql1).append(",aa.universityAuditStatus,aa.universityAuditResult,aa.universityAuditDate").append(hql2).append(" and un.id=:belongId and aa.status>=3");
			}else if(accountType.equals(AccountType.DEPARTMENT)){//院系
				hql.append(hql1).append(",aa.deptInstAuditStatus,aa.deptInstAuditResult,aa.deptInstAuditDate").append(hql2).append(" and aa.department.id=:belongId and aa.status>=2");
			}else if(accountType.equals(AccountType.INSTITUTE)){//研究基地
				hql.append(hql1).append(",aa.deptInstAuditStatus,aa.deptInstAuditResult,aa.deptInstAuditDate").append(hql2).append(" and aa.institute.id=:belongId and aa.status>=2");
			}else if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
				hql.append(hql1).append(",aa.applicantSubmitStatus,aa.applicantSubmitDate").append(hql2).append(" and aa.applicant.id=:belongId");
			}else{
				hql.append(hql1).append(hql2).append(" and 1=0");
			}
			return hql;
	}
	
	/**
	 * 根据登陆者的身份得到查询语句（用于公示数据和获奖数据）
	 * @param hql	原hql
	 * @param accountType登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return StringBuffer
	 */
	public StringBuffer getHql(String hql, AccountType accountType){
		   StringBuffer endhql = new StringBuffer();
		   endhql.append(hql);
			if(accountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
				;
			}else if(accountType.equals(AccountType.MINISTRY)){//社科司
				;
			}else if(accountType.equals(AccountType.PROVINCE)){//省级
				endhql.append(" and un.type=4 and un.subjection.id=:belongId ");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
				endhql.append(" and un.id=:belongId ");
			}else if(accountType.equals(AccountType.DEPARTMENT)){//院系/
				endhql.append(" and aa.department.id=:belongId ");
			}else if(accountType.equals(AccountType.INSTITUTE)){//研究基地
				endhql.append(" and aa.institute.id=:belongId ");
			}else if(accountType.equals(AccountType.STUDENT)){//研究人员
				endhql.append(" and aa.applicant.id=:belongId ");
			}else{
				endhql.append(" and 1=0 ");
			}
			return endhql;
	}
	
	/**
	 * 根据原有hql、初级检索条件、查询列表类型得到初级检索hql
	 * @param hql	原hql
	 * @param searchType	初级检索条件
	 * @param listflag	查询列表类型		1：奖励申请列表	2：公示列表	3：获奖列表
	 * @return StringBuffer	初级检索hql
	 */
	public StringBuffer getHql(StringBuffer hql,int searchType,int listflag){
		if (searchType == 1) {
			hql.append("LOWER(aa.productName) like :keyword ");
		}else if (searchType == 2) {
				hql.append("LOWER(aa.applicantName) like :keyword ");
		}else if (searchType == 3) {
				hql.append("LOWER(aa.agencyName) like :keyword ");
		}else if(searchType == 4){
				hql.append("LOWER(aa.disciplineType) like :keyword");
		}else if(searchType == 5){
				hql.append("LOWER(pr.productType) like :keyword");
		}else if(searchType == 7){
				hql.append("LOWER(gr.name) like :keyword");
		}else if(searchType == 8){
				hql.append("cast(aa.year as string) like :keyword");
		} else {
				hql.append("(LOWER(aa.productName) like :keyword or LOWER(aa.applicantName) like :keyword or LOWER(aa.agencyName) like :keyword or " +
					"LOWER(aa.disciplineType) like :keyword or LOWER(pr.productType) like :keyword ");
				if(listflag == 2)
					hql.append("or LOWER(gr.name) like :keyword ");
				else if(listflag == 3)
					hql.append("or LOWER(gr.name) like :keyword or cast(aa.year as string) like :keyword");
				hql.append(")");
		}
			return hql;
	}
	/*------------获取或设置奖励申请相关信息--------------*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * 根据奖励申请id获得奖励申请及申请人的相关信息
	 * @param entityId	奖励申请id
	 * @return AwardApplication对象
	 */
	
	public AwardApplication getAwardApplicationById(String entityId){
		Map map = new HashMap();
		map.put("entityId", entityId.trim());
		String hql = "from AwardApplication aa left outer join fetch aa.applicant pe left outer join fetch aa.university un "+
			"left outer join fetch aa.product at left outer join fetch aa.reviewGrade gr where aa.id =:entityId";
		AwardApplication awardApplication = (AwardApplication) dao.queryUnique(hql, map);
		return awardApplication;
	}

	/**
	 * 设置奖励申请各个字段参数2012
	 * @param awardApplication 奖励申请
	 * @param productType 成果类型
	 * @param productId 成果id
	 * @return 奖励申请
	 * @author leide 2012-02-16
	 */
	public AwardApplication setAwardApplicationParam(AwardApplication awardApplication, String productId) {
		try {
			System.out.println(productId);
			Product product= (Product)dao.query(Product.class, productId);
			awardApplication.setProductName(product.getChineseName());
			awardApplication.setApplicantName(product.getAuthorName());
			if(product.getUniversity() != null) {
				awardApplication.setUniversity(product.getUniversity());
			}
			awardApplication.setAgencyName(product.getAgencyName());
			awardApplication.setProvinceName(product.getUniversity().getProvince().getName());
			awardApplication.setProvince(product.getUniversity().getProvince());
			awardApplication.setDivisionName(product.getDivisionName());
			if(product.getAuthor() != null) {
				awardApplication.setApplicant(product.getAuthor());
			}
			awardApplication.setApplicantName(product.getAuthorName());
			awardApplication.setDisciplineType(product.getDisciplineType());
			awardApplication.setSubType((SystemOption)dao.query(SystemOption.class, "productAward"));
			//此处读系统配置
			Map<String, Object> sc = ActionContext.getContext().getApplication();
			awardApplication.setYear(Integer.parseInt((String) sc.get("currentYear")));
			awardApplication.setSession(Integer.parseInt((String) sc.get("awardSession")));
			awardApplication.setProduct(product);
			awardApplication.setApplicantSubmitDate(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return awardApplication;
	}
	/**
	 * 隐藏部分奖励相关信息
	 * @param awardApplication 奖励申请对象
	 * @param accountType登陆者身份	1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @param allReviewSubmit  是否所有专家的评审已提交	1:是		0:否
	 * @return 隐藏信息后的奖励申请对象
	 */
	public AwardApplication hideAwardAppInfo(AwardApplication awardApplication, AccountType accountType, int allReviewSubmit){
		if(allReviewSubmit != 0 && accountType.compareTo(AccountType.MINISTRY)>0){//隐藏评审信息
			awardApplication.setReviewDate(null);
			awardApplication.setReviewWay(null);
			awardApplication.setReviewGrade(null);
			awardApplication.setReviewOpinion(null);
			awardApplication.setReviewAverageScore(null);
			awardApplication.setReviewTotalScore(null);
			awardApplication.setReviewResult(0);
			awardApplication.setReviewStatus(0);
			awardApplication.setReviewerName(null);
			awardApplication.setReviewer(null);
			awardApplication.setReviewerAgency(null);
			
		}
		if(accountType.compareTo(AccountType.MINISTRY)>0 && awardApplication.getFinalAuditStatus() != 3){
			awardApplication.setFinalAuditResult(0);
			awardApplication.setFinalAuditDate(null);
		}
		if(accountType.compareTo(AccountType.MINISTRY)>0){//隐藏部级审核信息、评审审核信息、公示审核信息
			awardApplication.setFinalAuditOpinion(null);
			awardApplication.setFinalAuditorName(null);
			awardApplication.setFinalAuditor(null);
			awardApplication.setFinalAuditorAgency(null);
			awardApplication.setReviewAuditResult(0);
			awardApplication.setReviewAuditStatus(0);
			awardApplication.setReviewAuditDate(null);
			awardApplication.setReviewAuditOpinion(null);
			awardApplication.setReviewAuditorName(null);
			awardApplication.setReviewAuditor(null);
			awardApplication.setReviewAuditorAgency(null);
			awardApplication.setMinistryAuditResult(0);
			awardApplication.setMinistryAuditStatus(0);
			awardApplication.setMinistryAuditDate(null);
			awardApplication.setMinistryAuditOpinion(null);
			awardApplication.setMinistryAuditorName(null);
			awardApplication.setMinistryAuditor(null);
			awardApplication.setMinistryAuditorAgency(null);
		}
		if(accountType.compareTo(AccountType.PROVINCE)>0){//隐藏省级审核信息
			awardApplication.setProvinceAuditResult(0);
			awardApplication.setProvinceAuditStatus(0);
			awardApplication.setProvinceAuditDate(null);
			awardApplication.setProvinceAuditOpinion(null);
			awardApplication.setProvinceAuditorName(null);
			awardApplication.setProvinceAuditor(null);
			awardApplication.setProvinceAuditorAgency(null);
			
		}
		if(accountType.compareTo(AccountType.LOCAL_UNIVERSITY)>0){//隐藏
			awardApplication.setUniversityAuditResult(0);
			awardApplication.setUniversityAuditStatus(0);
			awardApplication.setUniversityAuditDate(null);
			awardApplication.setUniversityAuditOpinion(null);
			awardApplication.setUniversityAuditorName(null);
			awardApplication.setUniversityAuditor(null);
			awardApplication.setUniversityAuditorAgency(null);
		}
		if(accountType.compareTo(AccountType.INSTITUTE)>0){//隐藏院系/研究基地审核信息
			awardApplication.setDeptInstAuditResult(0);
			awardApplication.setDeptInstAuditStatus(0);
			awardApplication.setDeptInstAuditDate(null);
			awardApplication.setDeptInstAuditOpinion(null);
			awardApplication.setDeptInstAuditorName(null);
			awardApplication.setDeptInstAuditor(null);
			awardApplication.setDeptInstAuditorDept(null);
			awardApplication.setDeptInstAuditorInst(null);
		}
		return awardApplication;
	}
	/**
	 * 获得id为personId的人报奖的所有届次的list
	 * @param personId 人员id
	 * @return 所有届次的list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getPersonalAllSession(String personId){
		if(personId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("personId", personId);
		return dao.query("select aa.session from AwardApplication aa where aa.applicant.id=:personId",map);
	}
	
	/*---------------上传申请书--------------*/
	
	/**
	 *  得到上传文件的唯一路径并保存上传的文件
	 * @param uploadFile
	 * @return String 返回上传文件保存后的相对路径
	 */
	@SuppressWarnings("deprecation")
	public String getFileName(File uploadFile){
		try{// 获得绝对路径
			String realPath = ServletActionContext.getServletContext().getRealPath("upload");
			// 获取系统时间并转成字符串
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String dateformat = format.format(date);
			String year = String.valueOf(date.getYear() + 1900);
			String filepath = "award/moesocial/app/" + year + "/";
			String realName = "msc_app_" + year + "_" + dateformat + extendName;
			realPath = realPath.replace('\\', '/');
			String path = realPath + "/" + filepath + realName;
			FileTool.mkdir_p(realPath + "/" + filepath);
			File x = new File(path);
			FileUtils.copyFile(uploadFile, x);
			filepath = "upload/" + filepath + realName;
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*-------------获取或设置获奖对象相关信息---------------*/
	/**
	 * 根据奖励申请id取出获奖信息
	 * @param entityId 奖励申请id
	 * @return AwardGranted  返回奖励对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AwardGranted getAward(String entityId){
		Map map = new HashMap();
		map.put("entityId", entityId.trim());
		String hql = "from AwardGranted aw left outer join fetch aw.grade g where aw.application.id=:entityId";
		List as = (List)dao.query(hql, map);
		if(as != null && as.size() > 0)
			return (AwardGranted)as.get(0);
		else 
			return null;
	}

	/**
	 * 奖励申请公示审核通过后添加获奖信息
	 * @param awardApplication 奖励申请对象
	 */
	public void addAward(AwardApplication awardApplication){
		AwardGranted award = new AwardGranted();
		award.setApplication(awardApplication);
		award.setDate(awardApplication.getFinalAuditDate());
		award.setNumber(awardApplication.getNumber());
		award.setSession(awardApplication.getSession());
		award.setYear(awardApplication.getYear());
		SystemOption grade = (SystemOption)dao.query(SystemOption.class,awardApplication.getReviewGrade().getId().trim());
		award.setGrade(grade);
		dao.add(award);
	}
	/*------------------获取或设置奖励评审相关信息--------------*/
	/**
	 * 根据奖励申请id及人员id获取判断是否奖励评审人及评审组长
	 * @param entityId 奖励申请id
	 * @param personId 人员id
	 * @return 0不是评审人；1是评审人但不是评审组长；2是评审人且是评审组长
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int isReviewer(String entityId, String personId){
		int isReviewer = 0;
		if(entityId == null || entityId.trim().isEmpty()){
			return isReviewer;
		}
		Map map = new HashMap();
		map.put("entityId", entityId);
		map.put("reviewerId", personId);
		List list = dao.query("select ar.reviewerSn from AwardReview ar where ar.application.id =:entityId and ar.reviewer.id =:reviewerId", map);
		if(!list.isEmpty()){
			if((Integer)list.get(0) == 1){
				isReviewer = 2;
			}else{
				isReviewer = 1;
			}
		}
		return isReviewer;
	}
	
	/**
	 * 判断是否所有评审专家都提交了个人评审
	 * @param entityId 奖励申请id
	 * @return 0所有专家已提交；-1还有专家未提交
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int isAllReviewSubmit(String entityId){
		if(entityId == null){
			return -1;
		}
		Map map = new HashMap();
		map.put("entityId", entityId);
		List list = dao.query("select ar.id from AwardReview ar where ar.application.id =:entityId and ar.submitStatus !=3", map);
		if(list.size()>0){
			return -1;
		}
		return 0;
	}
	/**
	 * 获取当前账号能看到的所有人结项评审
	 * @param endId 结项id
	 * @return 当前账号能看到的所有人结项评审
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getAllReviewList(String entityId){
		if(entityId == null){
			return null;
		}
		Map map = new HashMap();
		map.put("awardApplicationId", entityId);
		StringBuffer hql1 = new StringBuffer("select ar.id,ar.reviewerSn,pe.name,ar.score,gr.name,ar.submitStatus from AwardReview ar left outer join ar.grade gr, " +
			"Person pe where ar.reviewer.id=pe.id and ar.application.id=:awardApplicationId order by ar.reviewerSn asc");
		return dao.query(hql1.toString(), map);
	}
	/**
	 * 根据奖励申请id和评审专家id得到奖励评审信息
	 * @param entityId 奖励申请id
	 * @param personId 评审专家id
	 * @return 奖励评审对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AwardReview getAwardReview(String entityId, String personId){
		if(entityId == null || personId == null){
			return null;
		}
		Map map = new HashMap();
		map.put("entityId", entityId.trim());
		map.put("personId", personId.trim());
		String hql = "from AwardReview ar left outer join fetch ar.grade gr where ar.application.id=:entityId and ar.reviewer.id=:personId";
		List awardReviews = dao.query(hql,map);
		if(awardReviews != null && awardReviews.size()>0)
		return (AwardReview)awardReviews.get(0);
		else return null;
	}
	
	/**
	 * 根据奖励评审id得到奖励评审信息
	 * @param entityId奖励评审id
	 * @return 奖励评审对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AwardReview getAwardReview(String entityId){
		if(entityId == null){
			return null;
		}
		Map map = new HashMap();
		map.put("entityId", entityId.trim());
		String hql = "from AwardReview ar left outer join fetch ar.reviewer pe left outer join fetch ar.grade gr where ar.id=:entityId";
		List awardReviews = dao.query(hql,map);
		if(awardReviews != null && awardReviews.size()>0)
		return (AwardReview)awardReviews.get(0);
		else return null;
	}
	/**
	 * 设置奖励评审的分数信息
	 * @param awardApplication 奖励申请对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setReviewScore(AwardApplication awardApplication){
		Map map1 = new HashMap();
		map1.put("awardApplicationid", awardApplication.getId());
		List<AwardReview> awardReviews = (List<AwardReview>)dao.query("select ar from AwardReview ar where ar.application.id=:awardApplicationid",map1);
		if(awardReviews.size() > 0){
			double totalScore = 0;
			double averageScore = 0;
			for(int i = 0; i < awardReviews.size(); i++){
				totalScore += awardReviews.get(i).getScore();
			}
			averageScore = totalScore/awardReviews.size();
			awardApplication.setReviewTotalScore(totalScore);
			awardApplication.setReviewAverageScore(averageScore);
			dao.modify(awardApplication);
		}
	}
	
	/**
	 * 根据奖励申请id获得奖励申请的所有评审意见
	 * @param entityId 奖励申请id
	 * @return 评审意见List
	 */
	@SuppressWarnings("unchecked")
	public List getGroupOpinionByAppId(String entityId){
		Map map = new HashMap();
        map.put("entityId", entityId);
		String hql = "select ar.reviewer.name, ar.opinion from AwardReview ar where ar.application.id=:entityId";
		List groupOpinion = dao.query(hql, map);
		return groupOpinion;
	}
/* -------------------dwr需要用到的方法--------------------------*/
	
	/** 
	 * 根据奖励申请人id得到奖励申请人所属的所有团队名称和id
	 * @param personid 团队负责人id
	 * @return	团队map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,String> getOrganization(String personid){
		Map<String, String> organizationMap = new LinkedHashMap<String, String>();
		Map hqlMap = new HashMap();
		hqlMap.put("personid", personid);
		List list = dao.query("select o.id, o.orgName from Product o where o.orgPerson.id= :personid and o.id not in (select og.id from AwardApplication aa left join aa.product og where aa.finalAuditStatus=3 and aa.finalAuditResult=2 and og.orgPerson.id=:personid )", hqlMap);
		if(list.size() > 0){
			for(int i = 0; i<list.size(); i++){
				Object[] p = (Object[]) list.get(i); 
				organizationMap.put(p[0].toString(), p[1].toString());
			}
		}
		return organizationMap;
	}
	
	/** 
	 * 根据成果类别和成果作者id得到改作者对应成果类别的所有成果
	 * @param ptype 成果类别
	 * @param personid 成果作者id
	 * @param organizationId 团队id
	 * @return	成果map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> getProduct(String ptype, String personid, String organizationId){
		Map<String, String> resultMap = new LinkedHashMap<String, String>();
		Map hqlMap = new HashMap();
		StringBuffer hql = new StringBuffer();
		hqlMap.put("personid", personid);
		hqlMap.put("ptype", ptype);
		hql.append("select p.id, p.chineseName from Product p where p.author.id=:personid and p.productType =:ptype and p.submitStatus=3 and p.auditResult != 1 and p.id not in (select pr.id from AwardApplication aa left join aa.product pr where aa.finalAuditStatus=3 and aa.finalAuditResult=2 and pr.author.id=:personid )");
		if(organizationId != null && !"-1".equals(organizationId) && !organizationId.isEmpty()){
			hql.append(" and p.id = :organizationId ");
			hqlMap.put("organizationId", organizationId);
		}
		List list = dao.query(hql.toString(), hqlMap);
		if(list.size() > 0){
			for(int i = 0; i<list.size(); i++){
				Object[] p = (Object[]) list.get(i); 
				resultMap.put(p[0].toString(), p[1].toString());
			}
		}
		return resultMap;
	}

	/**
	 * 获得成果的学科门类列表
	 * @param ptype 成果类别
	 * @param product 成果id
	 * @return	成果对应的学科门类map
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getDtype(String ptype,String product){
		Map<String, String> map = new LinkedHashMap<String, String>();
		Map hqlMap = new HashMap();
		hqlMap.put("productid", product);
		String dtypenames = "";
		dtypenames = (String)dao.query("select p.disciplineType from Product p where p.id=:productid",hqlMap).get(0);
		String arr[] = dtypenames.split(";");
		for(int i = 0; i < arr.length; i++){
		map.put(arr[i], arr[i]);
		}
		return map;
	}
	
	/**
	 * 判断奖励证书编号是否唯一
	 * @param entityId	奖励申请对象id
	 * @param number 证书编号
	 * @return	true:唯一	false:不唯一
	 */
	@SuppressWarnings("unchecked")
	public Boolean isNumberUnique(String entityId, String number){
		String hql;
		Map map = new HashMap();
		map.put("number", number);
		if(entityId != null && entityId.trim().length() > 0){
			map.put("entityId", entityId);
			hql = "select a.number from AwardApplication a where a.number=:number and a.id!=:entityId";
		}else{
			hql = "select a.number from AwardApplication a where a.number=:number ";
		}
		List numbers = dao.query(hql, map);
		return numbers.isEmpty();
	}
	
	/**
	 * 获得最大奖励申请年份
	 * @param entityId  奖励申请id，多个以";"隔开。
	 * @return 最大奖励申请年份
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String getApplyYear(String entityId){
		Map map = new HashMap();
		Date date;
		String entityIds[] = entityId.split(";");
		if(entityIds.length>0){
			StringBuffer hql = new StringBuffer(); 
			hql.append("select max(aa.applicantSubmitDate) from AwardApplication aa where (");
			for(int i = 0; i < entityIds.length; i++){
				map.put("entityId"+i, entityIds[i]);
				hql.append("aa.id=:entityId"+i);
				if(i != entityIds.length-1)
					hql.append(" or ");
				else hql.append(") and aa.status = 8 ");
			}
			date = (Date)dao.query(hql.toString(),map).get(0);
			if(date != null){
				String year = String.valueOf(date.getYear() + 1900);
				return year;
			}
			else return null;
		}
		else return null;
	}
//	
//	/**
//	 * 查询当年年份形成年份列表，最大年份为当前年份
//	 * @return 年份列表
//	 */
//	@SuppressWarnings("unchecked")
//	public Map<Integer, Integer> getYearMap(){
//		Map<Integer, Integer> yearMap = new LinkedHashMap();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		String year = sdf.format(new Date());
//		int y = Integer.parseInt(year);
//		for(int i = 0; i < 30; i++){
//			yearMap.put(y-i, y-i);
//		}
//		return yearMap;
//	}	
	/**
	 * 查询当年年份形成年份列表，起始年份为2000
	 * @return 年份列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Integer, Integer> getYearMap(){
		Map<Integer, Integer> yearMap = new LinkedHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		int y = Integer.parseInt(year);
		for(int i=2000; i<y; i++){
			yearMap.put(i, i);
		}
		yearMap.put(y, y);
		return yearMap;
	}
	
	
	/*--------------操作word宏的相关方法------------------*/
	/**
	 * 校验奖励申报表是否合法
	 * @param wordFile word文件
	 * @param personId 当前登陆者id
	 * @return 错误信息(如果合法则返回null)
	 * @author leide 2012-02-14
	 */
	public String checkAwardWordFile(File wordFile, String personId) {
		try {
			String awardUploadXMLData = WordTool.getWordTableData(wordFile, 0, 0, 0);
			// 文件合法性校验
			if(awardUploadXMLData == null || !awardUploadXMLData.startsWith("<awarddata>")) {
				return WordInfo.ERROR_WORD_ILLEGAL;
			}
			Document document = DocumentHelper.parseText(awardUploadXMLData);
			Element rootElement = document.getRootElement();
			//Element docVersion = rootElement.element("docVersion");
			// v1.0.x和v2.0.版本word的校验
			//if(docVersion.getText().startsWith("v1.0.") || docVersion.getText().startsWith("v2.0.")) {
			// 检查保护校验
			if(awardUploadXMLData.indexOf("<contentVersion>") < 0 || awardUploadXMLData.indexOf("<docVersion>") < 0) {
				return WordInfo.ERROR_WORD_NOT_PROTECTED;
			}
			// 成果信息完整性校验
			if(awardUploadXMLData.indexOf("<Product>") < 0) {
				return WordInfo.ERROR_WORD_PRODUCT_INFO_NOT_FINISHED;
			}
			// 负责人信息完整性校验
			if(awardUploadXMLData.indexOf("<Director>") < 0) {
				return WordInfo.ERROR_WORD_DIRECTOR_INFO_NOT_FINISHED;
			}
			// 校验上传word中的负责人是否与当前登陆者一样
			Element directorElement = rootElement.element("Director");
			Person person = (Person)dao.query(Person.class, personId);
			if(!directorElement.elementText("directorName").equals(person.getName())) {
				return WordInfo.ERROR_WORD_DIRECTOR_INFO_NOT_MATCH;
			}
			//}
		} catch (DocumentException e) {
			e.printStackTrace();
			return WordInfo.ERROR_WORD_ILLEGAL;
		}
		return null;
	}
	
	/**
	 * 导入奖励申报表的word宏信息
	 * @param wordFile word文件
	 * @param personId 人员id
	 * @param submitStatus 提交状态
	 * @return 成果id
	 * @author leide 2012-02-14
	 */
	public String importAwardFileData(File wordFile, String personId, int submitStatus) {
		try {
			//获取上传的xml数据
			String awardUploadXMLData = WordTool.getWordTableData(wordFile, 0, 0, 0);
			//解析成dom对象
			Document document = DocumentHelper.parseText(awardUploadXMLData);
			//获取根元素
			Element rootElement = document.getRootElement();
			//文件版本号
			Element docVersion = rootElement.element("docVersion");
			if(docVersion.getText().startsWith("v1.0.") || docVersion.getText().startsWith("v2.0.") || docVersion.getText().startsWith("v3.0.") || docVersion.getText().startsWith("v4.0.") || docVersion.getText().startsWith("v5.0.") || docVersion.getText().startsWith("v6.0.")) {
				Element productElement = rootElement.element("Product");
				Element directorElement = rootElement.element("Director");
				String directorName = directorElement.elementText("directorName");
				String productType = productElement.elementText("productType");
				//String productForm = productElement.elementText("productForm");
				//匹配高校
				Agency ageny = (Agency)dao.query(" select a from Agency a where a.code = ? ", directorElement.elementText("directorSchoolName").split("/")[0]).get(0);
				String divisionName = directorElement.elementText("directorDepartment");
				Person person = (Person)dao.query(Person.class, personId);
				String otherAuthorName = "";
				//获取其他作者
				for(int i = 1; i <= 5; i++) {
					if(directorElement.elementText("otherAuthor" + i) != null && !directorElement.elementText("otherAuthor" + i).isEmpty()) {
						otherAuthorName += directorElement.elementText("otherAuthor" + i) + "; ";
					}
				}
				if(otherAuthorName != null) {
					otherAuthorName = otherAuthorName.substring(0, otherAuthorName.length() - 2);
				}
				SystemOption productFormSystemOption = null;
				/*if(productForm.equals("书稿")) {
					productFormSystemOption = (SystemOption) query(SystemOption.class, "manuscript");
				} else if(productForm.equals("正式出版物")) {
					productFormSystemOption = (SystemOption) query(SystemOption.class, "publications");
				} else if(productForm.equals("其它")) {
					productFormSystemOption = (SystemOption) query(SystemOption.class, "otherForm");
				}*/
				//添加论文类型成果
				if(productType.equals("论文")) {
					Paper paper = new Paper();
					if(person != null) {
						paper.setAuthor(person);
					}
					if(productFormSystemOption != null) {
						paper.setForm(productFormSystemOption);
					}
					paper.setAgencyName(ageny.getName());
					paper.setUniversity(ageny);
					paper.setProvinceName(ageny.getProvince().getName());
					paper.setProvince(ageny.getProvince());
					paper.setDivisionName(divisionName);
					paper.setAuthorName(directorName);
					paper.setOtherAuthorName(otherAuthorName);
					paper.setChineseName(productElement.elementText("productName"));//成果名
					paper.setPublication(productElement.elementText("productPublishUnit"));//出版单位
					paper.setWordNumber(Double.valueOf(productElement.elementText("productWordNumber")));//字数
					paper.setDisciplineType(productElement.elementText("productDisciplineType"));//学科门类
					paper.setDiscipline(productElement.elementText("productDiscipline"));//学科代码
					Date date = null;
					try {
						date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("productPublishDate"));//出版日期
					} catch (ParseException e) {
						e.printStackTrace();
					}
					paper.setPublicationDate(date);
					paper.setIsImported(0);
					paper.setSubmitStatus(submitStatus);
					paper.setSubmitDate(new Date());
					return dao.add(paper);
				} else if (productType.equals("著作")) {
					Book book = new Book();
					if(person != null) {
						book.setAuthor(person);
					}
					if(productFormSystemOption != null) {
						book.setForm(productFormSystemOption);
					}
					book.setAgencyName(ageny.getName());
					book.setUniversity(ageny);
					book.setProvinceName(ageny.getProvince().getName());
					book.setProvince(ageny.getProvince());
					book.setDivisionName(divisionName);
					book.setAuthorName(directorName);
					book.setOtherAuthorName(otherAuthorName);
					book.setChineseName(productElement.elementText("productName"));
					book.setPublishUnit(productElement.elementText("productPublishUnit"));
					book.setWordNumber(Double.valueOf(productElement.elementText("productWordNumber")));
					book.setDisciplineType(productElement.elementText("productDisciplineType"));
					book.setDiscipline(productElement.elementText("productDiscipline"));
					Date date = null;
					try {
						date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("productPublishDate"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					book.setPublishDate(date);
					book.setIsImported(0);
					book.setSubmitStatus(submitStatus);
					book.setPublicationStatus(0);
					book.setSubmitDate(new Date());
					return dao.add(book);
				} else if(productType.equals("研究咨询报告")) {
					Consultation consultation = new Consultation();
					if(person != null) {
						consultation.setAuthor(person);
					}
					if(productFormSystemOption != null) {
						consultation.setForm(productFormSystemOption);
					}
					consultation.setAgencyName(ageny.getName());
					consultation.setUniversity(ageny);
					consultation.setProvinceName(ageny.getProvince().getName());
					consultation.setProvince(ageny.getProvince());
					consultation.setDivisionName(divisionName);
					consultation.setAuthorName(directorName);
					consultation.setOtherAuthorName(otherAuthorName);
					consultation.setChineseName(productElement.elementText("productName"));
					consultation.setWordNumber(Double.valueOf(productElement.elementText("productWordNumber")));
					consultation.setDisciplineType(productElement.elementText("productDisciplineType"));
					consultation.setDiscipline(productElement.elementText("productDiscipline"));
					Date date = null;
					try {
						date = (new SimpleDateFormat("yyyy-MM")).parse(productElement.elementText("productPublishDate"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					consultation.setPublicationDate(date);
					consultation.setIsImported(0);
					consultation.setSubmitStatus(submitStatus);
					consultation.setSubmitDate(new Date());
					return dao.add(consultation);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过word宏中的xml信息更新人员的信息
	 * @param wordFile word宏文件
	 * @param personId 人员id
	 * @author leida 2011-09-15
	 */
	@SuppressWarnings("unchecked")
	public void updateDirectorData(File wordFile, String personId) {
		try {
			String awardUploadXMLData = WordTool.getWordTableData(wordFile, 0, 0, 0);
			Document document = DocumentHelper.parseText(awardUploadXMLData);
			Element rootElement = document.getRootElement();
			Element docVersion = rootElement.element("docVersion");
			if(docVersion.getText().startsWith("v1.0.") || docVersion.getText().startsWith("v2.0.") || docVersion.getText().startsWith("v3.0.") || docVersion.getText().startsWith("v4.0.") || docVersion.getText().startsWith("v5.0.") || docVersion.getText().startsWith("v6.0.")) {
				Element directorElement = rootElement.element("Director");
				//更新人员基本信息
				Person person = (Person)dao.query(Person.class, personId);
				if(person != null) {
					// 合并办公电话、家庭电话、移动电话、Email
					person.setOfficePhone(WordTool.updateData(person.getOfficePhone(), directorElement.elementText("directorOfficePhone"), 2, 400));
					person.setHomePhone(WordTool.updateData(person.getHomePhone(), directorElement.elementText("directorHomePhone"), 2, 400));
					person.setMobilePhone(WordTool.updateData(person.getMobilePhone(), directorElement.elementText("directorMobilePhone"), 2, 400));
					person.setEmail(WordTool.updateData(person.getEmail(), directorElement.elementText("directorEmail"), 2, 400));
					// 覆盖办公邮编、办公地址、证件类型、证件号码、性别、生日
					person.setOfficePostcode(WordTool.updateData(person.getOfficePostcode(), directorElement.elementText("directorOfficePostcode"), 1, -1));
					person.setOfficeAddress(WordTool.updateData(person.getOfficeAddress(), directorElement.elementText("directorOfficeAddress"), 1, -1));
					person.setIdcardType(WordTool.updateData(person.getIdcardType(), directorElement.elementText("directorIdcardType"), 1, -1));
					person.setIdcardNumber(WordTool.updateData(person.getIdcardNumber(), directorElement.elementText("directorIdcardNumber"), 1, -1));
					person.setGender(WordTool.updateData(person.getGender(), directorElement.elementText("directorGender"), 1, -1));
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					person.setBirthday(format.parse(directorElement.elementText("directorBirthday")));
					dao.modify(person);
					System.out.println("Word:更新人员基本信息成功!");
				}
				//更新人员学术信息
				Map<String, String> parMap = new HashMap<String, String>();
				parMap.put("personId", person.getId());
				List<Academic> academics = dao.query(" select a from Academic a where a.person.id = :personId ", parMap);
				if(academics != null && !academics.isEmpty()) {
					Academic academic = academics.get(0);
					// 合并外语语种、学科代码
					academic.setLanguage(WordTool.updateData(academic.getLanguage(), directorElement.elementText("directorLanguage"), 2, 400));
					academic.setDiscipline(WordTool.updateData(academic.getDiscipline(), directorElement.elementText("directorDiscipline"), 2, 800));
					// 覆盖专业职称、最后学位、导师类型
					academic.setSpecialityTitle(WordTool.updateData(academic.getSpecialityTitle(), directorElement.elementText("directorSpecialityTitle"), 1, -1));
					academic.setLastDegree(WordTool.updateData(academic.getLastDegree(), directorElement.elementText("directorLastDegree"), 1, -1));
					if(directorElement.elementText("directorIsDrTutor").equals("1")) {
						academic.setTutorType("博士生导师");
					}
					academic.setPostdoctor(Integer.parseInt(directorElement.elementText("directorIsPostDr")));
					dao.modify(academic);
					System.out.println("Word:更新人员教育信息成功!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导入奖励申请的大段文字
	 * 大段文字的存储方式为索引存储，格式为"A; B; C",代表改栏位的内容存储在word的第A个表格的第B行第C列的单元格中
	 * @param wordFile word文件
	 * @param awardApplication 
	 * @return awardApplication
	 * @author leida 2012-02-22
	 */
	public AwardApplication importBigSection(File wordFile, AwardApplication awardApplication) {
		try {
			//获取上报的xml数据
			String awardUploadXMLData = WordTool.getWordTableData(wordFile, 0, 0, 0);
			Document document = DocumentHelper.parseText(awardUploadXMLData);
			Element rootElement = document.getRootElement();
			Element docVersion = rootElement.element("docVersion");
			Element indexesElement = rootElement.element("Indexes");
			if(docVersion.getText().startsWith("v1.0.") || docVersion.getText().startsWith("v2.0.") || docVersion.getText().startsWith("v3.0.") || docVersion.getText().startsWith("v4.0.") || docVersion.getText().startsWith("v5.0.") || docVersion.getText().startsWith("v6.0.")) {
				String[] indexes = indexesElement.elementText("productPrizeObtained").split("; ");
				awardApplication.setPrizeObtained(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
				indexes = indexesElement.elementText("productResponse").split("; ");
				awardApplication.setResponse(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
				indexes = indexesElement.elementText("productAdoption").split("; ");
				awardApplication.setAdoption(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
				indexes = indexesElement.elementText("productIntroduction").split("; ");
				awardApplication.setIntroduction(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
			}
			if(docVersion.getText().startsWith("v2.0.") || docVersion.getText().startsWith("v3.0.") || docVersion.getText().startsWith("v4.0.") || docVersion.getText().startsWith("v5.0.") || docVersion.getText().startsWith("v6.0.")) {
				String[] indexes = indexesElement.elementText("committeeAuditOpinion").split("; ");
				awardApplication.setCommitteeAuditOpinion(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
				indexes = indexesElement.elementText("universityAuditOpinion").split("; ");
				awardApplication.setUniversityAuditOpinion(WordTool.getWordTableData(wordFile, Integer.parseInt(indexes[0]) - 1, Integer.parseInt(indexes[1]) - 1, Integer.parseInt(indexes[2]) - 1));
			}
			System.out.println("导入大段文字成功");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return awardApplication;
	}
	/**
	 * 提交奖励申请下面未提交的成果申请
	 * @param productId
	 * @author leida 2012-02-22
	 */
	public void submitAwardAppProduct(String productId) {
		try {
			Product product= (Product)dao.query(Product.class, productId);
			if(product.getSubmitStatus() != 3) {
				product.setSubmitStatus(3);
				product.setSubmitDate(new Date());
				dao.modify(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*--------------------------获取其他信息--------------------------------*/
	/**
	 * 根据教师人员id得到教师信息
	 * @param personid 人员主表id
	 * @return Teacher  返回教师对象
	 */
	@SuppressWarnings("unchecked")
	public Teacher findTeacherBypersonid(String personid){
		Teacher t = null;
		Map map = new HashMap();
		map.put("personid", personid.trim());
		String hql = "from Teacher t left outer join fetch t.person p where p.id=:personid";
		List ts = dao.query(hql,map);
		if(ts != null && ts.size() != 0)
			t = (Teacher)ts.get(0);
		return t;
	}
	
	/**
	 * 获得所有成果类别列表
	 * @return 所有成果类别列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getProductTypes(){
		return dao.query("select so from SystemOption so where so.name in ('论文','著作','研究咨询报告','电子出版物','专利','其他成果') and so.isAvailable=1");
	}
	
	/**
	 * 根据SystemOption父项名称获得子项List
	 * @param name
	 * @return 子项List
	 */
	@SuppressWarnings("unchecked")
	public List getSystemOptionListByParentName(String name){
		if(name == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("name", name);
		return dao.query("select so from SystemOption so where so.systemOption.name=:name and so.isAvailable=1 order by so.code", map);
	}

	/**
	 * 根据奖励申请判断是否是部署高校
	 * @param awardApplication 奖励申请对象
	 * @return 1：是		0：否
	 */
	public int getIsSubUniByApp(AwardApplication awardApplication){
		int isSubUni = 0;
		if(awardApplication == null || awardApplication.getUniversity() == null){
			return isSubUni;
		}
		Agency university = (Agency)dao.query(Agency.class,awardApplication.getUniversity().getId().trim());
		if(university != null && university.getType() == 3){
			isSubUni = 1;
		}
		return isSubUni;
	}
	/**
	 * 根据成果类型获取描述
	 * @param productType
	 * @return 成果类型描述 
	 */
	public String fetchProductDescription(String productType) {
		String code = "", standard = "productType";
		if("paper".equals(productType)) {
			code = "01";
		} else if("book".equals(productType)) {
			code = "02";
		} else if("consultation".equals(productType)) {
			code = "03";
		} else if("electronic".equals(productType)) {
			code = "04";
		} else if("patent".equals(productType)) {
			code = "05";
		} else if("otherProduct".equals(productType)) {
			code = "06";
		}
		SystemOption so = systemOptionDao.query(standard, code);
		return (null != so) ? so.getName() : null;
	}
	
	/**
	 * 根据成果类型中文获取英文
	 * @param productType
	 * @return 成果类型描述 
	 */
	public String fetchProductEnglish(String productType) {
		String productTypeEg = "";
		if("论文".equals(productType)) {
			productTypeEg = "paper";
		} else if("著作".equals(productType)) {
			productTypeEg = "book";
		} else if("研究咨询报告".equals(productType)) {
			productTypeEg = "consultation";
		} else if("电子出版物".equals(productType)) {
			productTypeEg = "electronic";
		} else if("专利".equals(productType)) {
			productTypeEg = "patent";
		} else if("其他成果".equals(productType)) {
			productTypeEg = "otherProduct";
		}
		return (null != productTypeEg) ? productTypeEg : null;
	}
	
	/*--------------------------用于奖励录入评审--------------------------------*/
	/**
	 * 根据登陆账号类别好的评审人身份
	 * @param accountType 登陆账号类别1：系统管理员	2：部级	3：省级	4、5：校级	6：院系	7：研究机构	8：外部专家	9：教师	10：学生
	 * @return 评审人身份	0默认，1专家，2教育部，3省厅，4高校
	 */
	public int getReviewTypeByAccountType(AccountType accountType){
		int reviewType = 0;
		if(accountType.equals(AccountType.MINISTRY)){
			reviewType = 2;
		}else if(accountType.equals(AccountType.PROVINCE)){
			reviewType = 3;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			reviewType = 4;
		}else if(accountType.compareTo(AccountType.INSTITUTE)>0){
			reviewType = 1;
		}
		return reviewType;
	}
	
	
	/**
	 * 判断奖励申报是否有评审记录
	 * @param entityId 申报id
	 * @return -1无评审记录，1有评审记录且是专家评审，22教育部录入暂存，23教育部录入提交，32省厅录入暂存，33省厅录入提交，42高校录入暂存，43高校录入提交
	 */
	@SuppressWarnings("unused")
	public int checkReview(String entityId){
		int check = -1;//无评审记录
		if(entityId == null || entityId.trim().length() == 0){
			return check;
		}
		AwardApplication awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId);
		String hql = "from AwardReview review where review.application.id=?";
		List reviews = dao.query(hql, entityId);
		if(reviews !=null && reviews.size()>0){
			AwardReview review = (AwardReview)reviews.get(0);
			if(null == review.getReviewType() || review.getReviewType() == 1 ){
				check = 1;//有评审记录且是专家评审
			}else{
				int reviewType = review.getReviewType();
				int submitStatus = review.getSubmitStatus();
				check = reviewType * 10 + submitStatus;
			}
		}
		return check;
	}
	
	/**
	 * 根据分数计算等级
	 * @param score分数
	 * @return 等级
	 */
	public SystemOption getReviewGrade(double score){
		SystemOption grade = null;
		if(score > 90){
			grade = (SystemOption) soDao.query("reviewGrade", "01");
		}else if(score>=65 && score<=90){
			grade = (SystemOption) soDao.query("reviewGrade", "02");
		}else if(score < 65){
			grade = (SystemOption) soDao.query("reviewGrade", "03");
		}
		return grade;
	}
	
	/**
	 * 获得教师及其人员
	 * @param teacherId 教师id
	 * @return  教师
	 */
	@SuppressWarnings("unchecked")
	public List getTeacherFetchPerson(String teacherId){
		if(teacherId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("teacherId", teacherId);
		return dao.query("from Teacher te left join fetch te.person left join fetch te.university left join fetch te.department " +
				"left join fetch te.institute where te.id =:teacherId",map);
	}
	
	/**
	 * 获得外部专家及其人员
	 * @param expertId 外部专家id
	 * @return  外部专家
	 */
	@SuppressWarnings("unchecked")
	public List getExpertFetchPerson(String expertId){
		if(expertId == null){
			return new ArrayList();
		}
		Map map = new HashMap();
		map.put("expertId", expertId);
		return dao.query("from Expert exp left join fetch exp.person where exp.id =:expertId",map);
	}
	
	/**
	 * 保存奖励评审信息
	 * @param awardReview 奖励评审对象
	 * @return 更新后的奖励评审对象
	 */
	@SuppressWarnings("unchecked")
	public AwardReview setAwardReviewInfoFromAwardReview(AwardReview awardReview){
		//人员信息
		if(awardReview.getReviewer() != null && awardReview.getReviewer().getId() != null){//有成员id信息
			if(awardReview.getReviewerType() == 1){//教师
				List<Teacher> list = this.getTeacherFetchPerson(awardReview.getReviewer().getId());
				if(list != null && list.size() > 0){
					Teacher teacherM = list.get(0);
					awardReview.setReviewer(teacherM.getPerson());
					awardReview.setReviewerName(teacherM.getPerson().getName());
					awardReview.setIdcardType(teacherM.getPerson().getIdcardType());
					awardReview.setIdcardNumber(teacherM.getPerson().getIdcardNumber());
					awardReview.setGender(teacherM.getPerson().getGender());
					awardReview.setUniversity(teacherM.getUniversity());
					awardReview.setAgencyName(teacherM.getUniversity().getName());
					awardReview.setDepartment(teacherM.getDepartment());
					awardReview.setInstitute(teacherM.getInstitute());
					if(teacherM.getDepartment() != null){
						awardReview.setDivisionType(2);
						awardReview.setDivisionName(teacherM.getDepartment().getName());
					}else if(teacherM.getInstitute() != null){
						awardReview.setDivisionType(1);
						awardReview.setDivisionName(teacherM.getInstitute().getName());
					}else{
						awardReview.setDivisionType(null);
						awardReview.setDivisionName(null);
					}
				}
			}else if(awardReview.getReviewerType() == 2){//专家
				List<Expert> list2 = this.getExpertFetchPerson(awardReview.getReviewer().getId());
				if(list2!=null && list2.size()==1){
					Expert expertM = list2.get(0);
					awardReview.setReviewer(expertM.getPerson());
					awardReview.setReviewerName(expertM.getPerson().getName());
					awardReview.setIdcardType(expertM.getPerson().getIdcardType());
					awardReview.setIdcardNumber(expertM.getPerson().getIdcardNumber());
					awardReview.setGender(expertM.getPerson().getGender());
					awardReview.setUniversity(null);
					awardReview.setAgencyName(expertM.getAgencyName());
					awardReview.setDepartment(null);
					awardReview.setInstitute(null);
					awardReview.setDivisionType(3);
					awardReview.setDivisionName(expertM.getDivisionName());
				}
			}else if(awardReview.getReviewerType() == 3){//学生
				;
			}
		}else{//不含成员id信息
			;
		}
		awardReview.setDate(new Date());
		awardReview.setIsManual(1);//手动分配专家
//		double score = awardReview.getInnovationScore() + awardReview.getInfluenceScore() + awardReview.getMethodScore()+ awardReview.getMeaningScore();
//		awardReview.setScore(score);
//		awardReview.setGrade(this.getReviewGrade(score));
		return awardReview;
	}
	
	/**
	 * 通过申请id获得奖励评审组长的评审信息
	 * @param entityId 申请id
	 * @return 评审组长的评审信息
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public AwardReview getGroupDirectorReview(String entityId){
		if(entityId == null){
			return null;
		}
		AwardApplication awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId);
		String hql = "select review from AwardReview review left join fetch review.university uni where review.application.id=? and review.reviewerSn=1";
		List review = dao.query(hql, entityId);
		if(review.size() > 0){
			return (AwardReview)review.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取奖励申请评审总分与均分
	 * @param entityId 申请id
	 * @return 奖励申请评审总分与均分
	 */
	@SuppressWarnings("rawtypes")
	public double[] getReviewScore(String entityId){
		double[] scores = new double[2];
		if(entityId == null){
			return scores;
		}
		String hql = "select review.score from AwardReview review left outer join review.application app where app.id=?";
		double reviewTotalScore = 0;
		List reviewScores = dao.query(hql.toString(), entityId);
		for(int i=0; i<reviewScores.size(); i++){
			double score = (Double) reviewScores.get(i);
			reviewTotalScore += score;	
		}
		double reviewAvgScore = reviewTotalScore/reviewScores.size();
		scores[0] = reviewTotalScore;
		scores[1] = reviewAvgScore;
		return scores;
	}
	
	/**
	 * 根据申请id获得奖励的所有评审列表
	 * @param appId 奖励申请id
	 * @return 奖励的所有评审列表
	 */
	@SuppressWarnings({"unchecked" })
	public List<AwardReview> getAllReviewByAppId(String appId){
		List<AwardReview> awardReviews = new ArrayList<AwardReview>();
		if(null != appId){
			String hql = "select review from AwardReview review left join fetch review.grade gra left join fetch review.reviewer rev where review.application.id=? order by review.reviewerSn asc";
			awardReviews = dao.query(hql, appId);
		}
		return awardReviews;
	}
	
//	/**
//	 * 根据申请id对奖励评审对象对应的人员、机构进行入库处理
//	 * @param appId 奖励申请id
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void doWithNewReview(String appId){
//		if(appId == null){
//			return;
//		}
//		List<AwardReview> reviews = this.getAllReviewByAppId(appId);
//		for(int i = 0; i < reviews.size(); i++){
//			AwardReview review = reviews.get(i);
//			Map map = new HashMap();
//			map.put("idcardType", review.getIdcardType());
//			map.put("idcardNumber", review.getIdcardNumber());
//			map.put("personName", review.getReviewerName());
//			map.put("personType", review.getReviewerType());
//			map.put("gender", review.getGender());
//			map.put("agencyName", review.getAgencyName());
//			map.put("agencyId", (review.getUniversity() != null) ? review.getUniversity().getId() : null);
//			map.put("divisionName", review.getDivisionName());
//			map.put("divisionType", review.getDivisionType());
//			map = this.doWithNewPerson(map);
//			String personId = map.get("personId").toString();
//			String divisionId = map.get("divisionId").toString();
//			Person person = (Person)this.query(Person.class, personId);
//			review.setReviewer(person);
//			if(review.getDivisionType() == 1){//研究基地
//				Institute institute =(Institute)this.query(Institute.class, divisionId);
//				review.setInstitute(institute);
//				review.setDepartment(null);
//			}else if(review.getDivisionType() == 2){//院系
//				Department department =(Department)this.query(Department.class, divisionId);
//				review.setInstitute(null);
//				review.setDepartment(department);
//			}
//			this.modify(review);
//		}
//	}
	
	/**
	 * 根据奖励人员id、高校id、院系id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	@SuppressWarnings("unchecked")
	public String getTeacherIdByMemberAllUnit(String personId, String universityId, Department department, Institute institute){
		if(personId == null || universityId == null){
			return null;
		}
		StringBuffer hql = new StringBuffer("select tea.id from Teacher tea where tea.person.id=:personId and tea.university.id=:universityId ");
		Map map = new HashMap();
		map.put("personId", personId);
		map.put("universityId", universityId);
		if(department != null){
			map.put("departmentId", department.getId());
			hql.append(" and tea.department.id=:departmentId");
		}else if(institute != null){
			map.put("instituteId", institute.getId());
			hql.append(" and tea.institute.id=:instituteId");
		}
		List<String> teacherIds = dao.query(hql.toString(), map);
		if(teacherIds.size() == 1){
			return teacherIds.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据奖励人员id、高校id获得教师id
	 * @param personId	人员主表id
	 * @param universityId	高校id
	 * @param department	院系
	 * @param institute	研究基地
	 * @return	教师id
	 */
	@SuppressWarnings("unchecked")
	public String getTeacherIdByMemberPartUnit(String personId, String universityId){
		StringBuffer hql = new StringBuffer("select tea.id from Teacher tea where tea.person.id=:personId and tea.university.id=:universityId ");
		Map map = new HashMap();
		map.put("personId", personId);
		map.put("universityId", universityId);
		List<String> teacherIds = dao.query(hql.toString(), map);
		if(teacherIds.size() != 0){
			return teacherIds.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据奖励人员id、机构名称、部门名称获得专家id
	 * @param personId 人员主表id
	 * @param agencyName 机构名称
	 * @param divisionName 部门名称
	 * @return 专家id
	 */
	@SuppressWarnings("unchecked")
	public String getExpertIdByPersonIdUnit(String personId, String agencyName, String divisionName){
		if(personId == null){
			return null;
		}
		StringBuffer hql = new StringBuffer("select exp.id from Expert exp where exp.person.id=:personId ");
		Map map = new HashMap();
		map.put("personId", personId);
		if(agencyName != null && agencyName.trim().length() > 0){
			map.put("agencyName", agencyName.trim());
			hql.append(" and exp.agencyName=:agencyName");
		}else if(divisionName != null && divisionName.trim().length() > 0){
			map.put("divisionName", divisionName.trim());
			hql.append(" and exp.divisionName=:divisionName");
		}
		List<String> expertIds = dao.query(hql.toString(), map);
		if(expertIds.size() == 1){
			return expertIds.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 设置奖励评审人员对应的研究人员id(teacherId, ExpertId, studentId)的信息
	 * @param review 奖励申报评审对象
	 * @return ProjectApplicationReview 奖励申报评审对象
	 */
	public AwardReview setReviewPersonInfoFromReview(AwardReview review){
		Person person = review.getReviewer();
		if(person == null || person.getId() == null || person.getId().trim().isEmpty() || review.getReviewerType() < 1){
			;
		}else if(review.getReviewerType() == 1){//教师
			String teacherId = "";
			teacherId = this.getTeacherIdByMemberAllUnit(person.getId(), review.getUniversity().getId(), review.getDepartment(), review.getInstitute());
			if(teacherId == null){
				teacherId = this.getTeacherIdByMemberPartUnit(person.getId(), review.getUniversity().getId());
			}
			person.setId((teacherId != null) ? teacherId : null);
			review.setReviewer(person);
		}else if(review.getReviewerType() == 2){//外部专家
			String expertId = this.getExpertIdByPersonIdUnit(person.getId(), review.getAgencyName(), review.getDivisionName());
			if(expertId == null){
				expertId = this.getExpertIdByPersonId(person.getId());
			}
			person.setId((expertId != null) ? expertId : null);
			review.setReviewer(person);
		}else if(review.getReviewerType() == 3){//学生
			String studentId = this.getStudentIdByPersonId(person.getId());
			person.setId((studentId != null) ? studentId : null);
			review.setReviewer(person);
		}
		return review;
	}
	
	/**
	 * 同步奖励文件到DMSS服务器
	 * @param awardApplication
	 * @return DMSS文档持久化后的标识
	 */
	public String flushToDmss(AwardApplication awardApplication){
		ThirdUploadForm form = new ThirdUploadForm();
        form.setTitle(awardApplication.getProductName());
        form.setFileName(getFileName(awardApplication.getFile()));
        form.setSourceAuthor(awardApplication.getApplicantName());
        form.setRating("5.0");
        form.setTags(awardApplication.getDivisionName()+";"+awardApplication.getAgencyName()+";"+awardApplication.getYear());
        form.setCategoryPath("/SMDB/"+getRelativeFileDir(awardApplication.getFile()));
        return flushToDmss(awardApplication.getFile(), form);
	}
	
}