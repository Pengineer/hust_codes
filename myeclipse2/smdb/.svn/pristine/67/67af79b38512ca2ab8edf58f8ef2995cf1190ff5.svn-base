package csdc.action.mobile.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import csdc.bean.AwardGranted;
import csdc.bean.AwardApplication;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Product;
import csdc.bean.SystemOption;
import csdc.service.IAwardService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.AwardInfo;
import csdc.action.mobile.MobileAction;

/**
 * mobile奖励模块
 * @author fengcl
 */
@SuppressWarnings("unchecked")
public class MobileAwardAction extends MobileAction{
	
	private static final long serialVersionUID = 1L;

	private static final String PAGENAME = "mobileAwardPage";
	
//	private static final String HQL4COMMON = "select un.name, aa.productName, aa.applicantName, aa.id";
	private static final String HQL4APPLY = "select un.name, aa.productName, aa.applicantName, aa.id from AwardApplication aa left join aa.product pr left join aa.university un where 1 = 1";
	private static final String HQL4PUBLICITY = "select un.name, aa.productName, aa.applicantName, aa.id from AwardApplication aa left join aa.product pr left join aa.reviewGrade gr left join aa.university un where aa.status=8 and aa.finalAuditStatus != 3";
	private static final String HQL4AWARDED = "select un.name, aa.productName, aa.applicantName, aa.id from AwardGranted aw left join aw.grade gr left join aw.application aa left join aa.product pr left join aa.university un where 1 = 1";
//	private static final String HQL4APPLY = " from AwardApplication aa left join aa.product pr left join aa.university un where 1 = 1";
//	private static final String HQL4PUBLICITY = " from AwardApplication aa left join aa.product pr left join aa.reviewGrade gr left join aa.university un where aa.status=8 and aa.finalAuditStatus != 3";
//	private static final String HQL4AWARDED = " from Award aw left join aw.grade gr left join aw.application aa left join aa.product pr left join aa.university un where 1 = 1";

	private String awardType;//奖励类型（人文社科奖）
//	private Integer listType;//列表类型（1、申报数据，2、公示数据，3、获奖数据）
	private IAwardService awardService;
	private AwardApplication awardApplication;//奖励申请对象
	private AwardGranted award;//奖励对象
	
	//用于高级检索
	private String prodName;//成果名称
	private String prodType;//成果类型
	private String applicantName;//申请人
	private String univName;//所属高校
	private String startSession;//申请届次/获奖届次
	private String endSession;//申请届次/获奖届次
	
	//隐藏类初始化法
	private static final Map<String, String> productTypeMap = new HashMap(){
		private static final long serialVersionUID = -3130001498213184583L;
		{
			put("论文", "paper");
			put("著作", "book");
			put("研究咨询报告", "consultation");
			put("电子出版物", "electronic");
			put("专利", "patent");
			put("其他成果", "otherProduct");
		}
	};
	//隐藏类初始化法
	//MOESOCIALITEMS：人文社科奖列表
	private static final ArrayList MOESOCIALITEMS = new ArrayList();
	static{
		MOESOCIALITEMS.add("申报数据#1");
		MOESOCIALITEMS.add("公示数据#2");
		MOESOCIALITEMS.add("获奖数据#3");
	}
	
	/**
	 * 客户端主列表条目
	 * @return
	 */
	public String fetchMenu(){
//		AccountType accountType = loginer.getCurrentType();
		Map mainItems = new LinkedHashMap();
		List moesocialItems = null;
		moesocialItems = MOESOCIALITEMS;
		mainItems.put("MoeSocial", moesocialItems);
		jsonMap.put("listItem", mainItems);
		return SUCCESS;
	}
	
	/**
	 * 初级检索列表
	 */
	public String simpleSearch(){
		AccountType accountType = loginer.getCurrentType();
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		if (awardType.equals("MoeSocial")) {
			switch(listType){
			case 1://申报数据
				hql.append(HQL4APPLY);
				break;
			case 2://公示数据
				hql.append(HQL4PUBLICITY);
				break;
			case 3://获奖数据
				hql.append(HQL4AWARDED);
				break;
			}
			if(accountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
				hql.append(" ");
			}else if(accountType.equals(AccountType.MINISTRY)){//部级
				hql.append(" and aa.status>=5");
			}else if(accountType.equals(AccountType.PROVINCE)){//省级
				hql.append(" and un.type=4 and un.subjection.id=:belongId and aa.status>=4");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
				hql.append(" and un.id=:belongId and aa.status>=3");
			}else if(accountType.equals(AccountType.DEPARTMENT)){//院系
				hql.append(" and aa.department.id=:belongId and aa.status>=2");
			}else if(accountType.equals(AccountType.INSTITUTE)){//研究基地
				hql.append(" and aa.institute.id=:belongId and aa.status>=2");
			}else if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
				hql.append(" and aa.applicant.id=:belongId");
			}else{
				hql.append(" and 1=0");
			}
			if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用	
				map.put("belongId", baseService.getBelongIdByLoginer(loginer));
			}else if(accountType.compareWith(AccountType.MINISTRY) == -1){//部级以下管理人员
				map.put("belongId", loginer.getCurrentBelongUnitId());
			}
			keyword = (keyword == null) ? "" : keyword.toLowerCase();
			if(!keyword.isEmpty()){
				hql.append(" and (LOWER(aa.productName) like :keyword or LOWER(aa.applicantName) like :keyword or LOWER(un.name) like :keyword)");
				map.put("keyword", "%" + keyword + "%");	
			}
			hql.append(" order by aa.productName asc");
		}
	    //调用公共方法
	    search(hql, map);
		return SUCCESS;
	}
//	/**
//	 * 初级检索列表
//	 */
//	public String simpleSearch(){
//		AccountType accountType = loginer.getCurrentType();
//		StringBuffer hql = new StringBuffer();
//		HashMap map = new HashMap();
//		if (awardType.equals("moesocial")) {
//			switch(listType){
//			case 1://申报数据
//				hql = this.awardService.getHql(HQL4COMMON, HQL4APPLY, accountType);
//				break;
//			case 2://公示数据
//				hql = this.awardService.getHql(HQL4COMMON + HQL4PUBLICITY, accountType);
//				break;
//			case 3://获奖数据
//				hql = this.awardService.getHql(HQL4COMMON + HQL4AWARDED, accountType);
//				break;
//			}
//			if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用	
//				map.put("belongId", loginer.getCurrentBelongId());
//			}else if(accountType.compareWith(AccountType.MINISTRY) == -1){//部级以下管理人员
//				map.put("belongId", loginer.getCurrentBelongUnitId());
//			}
//			keyword = (keyword == null) ? "" : keyword.toLowerCase();
//			if(!keyword.isEmpty()){
//				hql.append(" and (LOWER(aa.productName) like :keyword or LOWER(aa.applicantName) like :keyword)");
//				map.put("keyword", "%" + keyword + "%");	
//			}
//			hql.append(" order by aa.productName asc");
//		}
//	    //调用公共方法
//	    search(hql, map);
//		return SUCCESS;
//	}
	
	/**
	 * 高级检索
	 */
	public String advSearch(){
		AccountType accountType = loginer.getCurrentType();
		StringBuffer hql = new StringBuffer();
		HashMap map = new HashMap();
		if (awardType.equals("MoeSocial")) {
			switch(listType){
			case 1://申报数据
				hql.append(HQL4APPLY);
				break;
			case 2://公示数据
				hql.append(HQL4PUBLICITY);
				break;
			case 3://获奖数据
				hql.append(HQL4AWARDED);
				System.out.println(hql.toString());
				break;
			}
			if(accountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
				hql.append(" ");
			}else if(accountType.equals(AccountType.MINISTRY)){//部级
				hql.append(" and aa.status>=5");
			}else if(accountType.equals(AccountType.PROVINCE)){//省级
				hql.append(" and un.type=4 and un.subjection.id=:belongId and aa.status>=4");
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校
				hql.append(" and un.id=:belongId and aa.status>=3");
			}else if(accountType.equals(AccountType.DEPARTMENT)){//院系
				hql.append(" and aa.department.id=:belongId and aa.status>=2");
			}else if(accountType.equals(AccountType.INSTITUTE)){//研究基地
				hql.append(" and aa.institute.id=:belongId and aa.status>=2");
			}else if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员
				hql.append(" and aa.applicant.id=:belongId");
			}else{
				hql.append(" and 1=0");
			}
			if(accountType.equals(AccountType.EXPERT) || accountType.equals(AccountType.TEACHER) || accountType.equals(AccountType.STUDENT)){//研究人员使用
				map.put("belongId",baseService.getBelongIdByLoginer(loginer));
			}else if(accountType.compareWith(AccountType.MINISTRY) == -1){//部级以下管理人员
				map.put("belongId", loginer.getCurrentBelongUnitId());
			}
			//高级检索条件
			if (null != prodName && !prodName.isEmpty()) {
				prodName = prodName.toLowerCase();
				hql.append(" and LOWER(aa.productName) like :productName");
				map.put("productName", "%" + prodName + "%");
			}//成果名称
			if (null != prodType && !prodType.equals("--请选择--") && !prodType.isEmpty()) {
				prodType = prodType.toLowerCase();
				hql.append(" and LOWER(pr.productType) = :prodType");
				map.put("prodType", productTypeMap.get(prodType));
			}//成果类型
			if (null != applicantName && !applicantName.isEmpty()) {
				applicantName = applicantName.toLowerCase();
				hql.append(" and LOWER(aa.applicantName) like :applicantName");
				map.put("applicantName", "%" + applicantName + "%");
			}//申请人
			if (null != univName && !univName.isEmpty()) {
				univName = univName.toLowerCase();
				hql.append(" and LOWER(un.name) like :universityName");
				map.put("universityName", "%" + univName + "%");
			}//所属高校
			if ( (null != startSession && startSession.isEmpty()) || (null != endSession && endSession.isEmpty()) ){
				Integer session1 = null;
				Integer session2 = null;
				if(Pattern.matches("\\d+", startSession.trim())){//是数字
					session1 = Integer.parseInt(startSession.trim());
				}//判断输入是否数字，是则转换，否则为null
				if(Pattern.matches("\\d+", endSession.trim())){//是数字
					session2 = Integer.parseInt(endSession.trim());
				}//判断输入是否数字，是则转换，否则为null
				if(null != session1 && session1 > 0){
					hql.append(" and aa.session >=:session1");
					map.put("session1", session1 );
				}//申请届次
				if(null != session2 && session2 > 0){
					hql.append(" and aa.session <=:session2");
					map.put("session2", session2);
				}//申请届次/获奖届次	
			}
			hql.append(" order by aa.productName asc");
		}
		//调用公共接口
		search(hql, map);
		return SUCCESS;
	}
	
	/**
	 * 详情查看
	 */
	public String view(){
		//1、申报数据：成果名称，申请人，依托高校，学科门类，成果类型，申请届次
		//2、公示数据：成果名称，申请人，依托高校，学科门类，成果类型，获奖等级，获奖届次
		//2、获奖数据：成果名称，申请人，依托高校，学科门类，成果类型，获奖等级，获奖届次，获奖年度
		Map dataMap = new HashMap();
		if(!this.awardService.checkIfUnderControl(loginer, entityId.trim(),18, true)){
			jsonMap.put("errorInfo", AwardInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}			
		awardApplication = (AwardApplication)dao.query(AwardApplication.class, entityId.trim());//奖励申请
		award = this.awardService.getAward(entityId.trim());//奖励
		String awardGrade = (null == award) ? "" : ((SystemOption)dao.query(SystemOption.class, award.getGrade().getId().trim())).getName();//获奖等级名称
		//公共部分
		String productName = (awardApplication == null || awardApplication.getProductName() == null)?"":awardApplication.getProductName();
		dataMap.put("productName", productName);//成果名称
		String personName = (awardApplication == null || awardApplication.getApplicantName()== null)?"":awardApplication.getApplicantName();
		dataMap.put("personName", personName);//申请人
		String agencyName = (awardApplication == null || awardApplication.getAgencyName() == null)?"":awardApplication.getAgencyName();
		dataMap.put("agencyName", agencyName);//依托高校
		String departmentId = (awardApplication == null || awardApplication.getDepartment() == null)? null:awardApplication.getDepartment().getId();//院系
		String instituteId = (awardApplication == null || awardApplication.getInstitute() == null)? null:awardApplication.getInstitute().getId();//研究机构
		if(departmentId!= null){
			Department department = (Department) dao.query(Department.class, departmentId);
			dataMap.put("instituteName", department.getName());//依托院系
		}
		else if (instituteId!= null){
			Institute institute = (Institute) dao.query(Institute.class, instituteId);
			dataMap.put("instituteName", institute.getName());//依托基地
		}
		else dataMap.put("instituteName", "");
		String dType = (awardApplication == null || awardApplication.getDisciplineType() == null)?"":awardApplication.getDisciplineType();
		dataMap.put("dType", dType);//学科门类
		String id = awardApplication.getProduct().getId().trim();
//		String id = "4028d88a2dc6d3ae012dc6d3f536007f";
		try{
			Product product = (Product) dao.query(Product.class, id);
			String ptype = Product.findTypeName(product.getProductType());
			dataMap.put("pType", ptype);//成果类型
		}catch(Exception e){
			e.printStackTrace();
		}
		//不同部分
		if (awardType.equals("MoeSocial")) {
			switch(listType){
			case 1://申报数据
				//[获奖信息 3]：申请届次
				String applySession = (awardApplication == null || awardApplication.getSession() == 0) ? "" : String.valueOf(awardApplication.getSession());
				dataMap.put("applySession", "第" + applySession + "届");//申请届次
				break;
			case 2://公示数据
				//[获奖信息 3]：获奖等级，获奖届次
				awardGrade = (null == awardGrade) ? "" : awardGrade;
				dataMap.put("awardGrade", awardGrade);//获奖等级
				String awardSession = (null != awardApplication && awardApplication.getSession() != 0)? String.valueOf(awardApplication.getSession()) : "";
				dataMap.put("awardSession", "第" + awardSession + "届");//获奖届次
				break;
			case 3://获奖数据
				//[获奖信息 3]：获奖等级，获奖届次，获奖年度，证书编号
				awardGrade = (awardGrade == null) ? "" : awardGrade;
				dataMap.put("awardGrade", awardGrade);//获奖等级
				String session = (null != awardApplication && awardApplication.getSession() != 0)? String.valueOf(awardApplication.getSession()) : "";
				dataMap.put("awardSession", "第" + session + "届");//获奖届次
				String year = (null != awardApplication && null != awardApplication.getYear()) ? awardApplication.getYear().toString() : "";
				dataMap.put("year", year);//获奖年度
				String number = (null != awardApplication && null != awardApplication.getNumber()) ? awardApplication.getNumber().toString() : "";
				dataMap.put("number", number);//证书编号
				String date = (null != award && null != award.getDate()) ? award.getDate().toString() : "";
				dataMap.put("date", date);//获奖时间				
				break;
			}
		}
		jsonMap.put("laData", dataMap);
		return SUCCESS;
	}
	
	/**
	 * 奖励审核
	 * @return
	 */
	public String audit(){
		//
		return SUCCESS;
	}
	
	/**
	 * 审核修改
	 * @return
	 */
	public String toModify(){
		//
		return SUCCESS;
	}

	public String modify(){
		//
		return SUCCESS;
	}
	
	/**
	 * 审核查看
	 * @return
	 */
	public String viewAudit(){
		//
		return SUCCESS;
	}
	
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	public IAwardService getAwardService() {
		return awardService;
	}
	public void setAwardService(IAwardService awardService) {
		this.awardService = awardService;
	}
	public AwardApplication getAwardApplication() {
		return awardApplication;
	}
	public void setAwardApplication(AwardApplication awardApplication) {
		this.awardApplication = awardApplication;
	}
	public AwardGranted getAward() {
		return award;
	}
	public void setAward(AwardGranted award) {
		this.award = award;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getUnivName() {
		return univName;
	}
	public void setUnivName(String univName) {
		this.univName = univName;
	}
	public String getStartSession() {
		return startSession;
	}
	public void setStartSession(String startSession) {
		this.startSession = startSession;
	}
	public String getEndSession() {
		return endSession;
	}
	public void setEndSession(String endSession) {
		this.endSession = endSession;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
}
