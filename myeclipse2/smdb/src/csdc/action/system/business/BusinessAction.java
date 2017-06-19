package csdc.action.system.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Business;
import csdc.dao.SystemOptionDao;
import csdc.service.IBusinessService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.BusinessInfo;
import csdc.tool.info.GlobalInfo;

@Transactional
public class BusinessAction  extends BaseAction{
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	@Autowired
	private static final long serialVersionUID = 1L;

	private static final String HQL = "select b.id, so.name, b.status, b.startDate, b.startYear, b.endYear, b.applicantDeadline," + 
		" b.deptInstDeadline, b.univDeadline, b.provDeadline, b.startYear, b.endYear from Business b left outer join b.subType so where 1=1";
	private static final String[] COLUMN = {
		"so.name desc",
		"b.status",
		"b.startDate",
		"b.startYear, b.endYear",
		"b.applicantDeadline",
		"b.deptInstDeadline",
		"b.univDeadline",
		"b.provDeadline",
		"b.reviewDeadline",
		"b.startYear",
		"b.endYear",
//		"b.businessYear",
	};//排序列
	private static final String PAGE_NAME = "businessPage";//列表页面名称
	private static final String PAGE_BUFFER_ID = "b.id";//缓存id
	private static final String DATE_FORMAT = "yyyy-MM-dd";//列表页面时间格式
	private static final String TMP_ENTITY_ID = "businessId";// 用于session缓存实体的ID名称

	protected String mainFlag;//首页进入列表参数
	private IBusinessService businessService;// 业务管理接口
	private int businessStatus;// 高级检索关键字
	private String type;// 高级检索关键字
	private int startYear, endYear;
//	private int businessYear;
	private Date startDate, endDate; 
	private Date applicantDeadline1, applicantDeadline2, deptInstDeadline1, deptInstDeadline2, univDeadline1, univDeadline2, provDeadline1, provDeadline2, reviewDeadline1, reviewDeadline2;//高级检索条件
	
	private Business business;// 业务对象
	
	public String[] column() {
		return COLUMN;
	}
	public String pageName() {
		return PAGE_NAME;
	}
	public String dateFormat() {
		return DATE_FORMAT;
	}
	public String pageBufferId() {
		return PAGE_BUFFER_ID;
	}
	
	/**
	 * 初始化列表查询的hql、hql参数、默认排序列、错误信息(如有错误信息，加载列表时会显示该信息而非列表数据)
	 * @return 0:hql  1:hql参数   2:默认排序列编号  3:错误信息  
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] simpleSearchCondition() {
		AccountType accountType = loginer.getCurrentType();
		int columnLabel = 0;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		keyword = (keyword == null)? "" : keyword.toLowerCase();
		if(!keyword.equals("")){
			if (searchType == 1){// 按业务名称查询
				hql.append(" and LOWER(so.name) like :keyword");
				map.put("keyword", "%" + keyword + "%");
			} else if (searchType == 2) {// 按业务状态查询
				boolean activating = "业务激活中".contains(keyword);
				boolean stop = "业务停止".contains(keyword);
				if (activating&&stop){
					
				} else if (!(activating||stop)){
					hql.append(" and b.status='-1'");
				} else {
					if (activating) {
						hql.append(" and b.status='1'");
					} else if (stop) {
						hql.append(" and b.status='0'");
					}
				}		
				map.put("keyword", "%" + keyword + "%");
			} else {// 按上述所有字段查询
				boolean activating = "业务激活中".contains(keyword);
				boolean stop = "业务停止".contains(keyword);
				if (activating&&stop){
					
				} else if (!(activating||stop)){
					hql.append(" and LOWER(so.name) like :keyword");
				} else {
					if (activating) {
						hql.append(" and b.status='1'");
					} else if (stop) {
						hql.append(" and b.status='0'");
					}
				}		
				map.put("keyword", "%" + keyword + "%");
			}
		}
		if(accountType.equals(AccountType.DEPARTMENT) || accountType.equals(AccountType.INSTITUTE)){
			columnLabel = 5;
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){
			columnLabel = 6;
		}else if(accountType.equals(AccountType.PROVINCE)){
			columnLabel = 7;
		}else{
			columnLabel = 0;
		}
		return new Object[]{
			hql.toString(),
			map,
			columnLabel,
			null
		};
	}
	
	/**
	 * 处理高级检索条件，拼装查询语句。
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object[] advSearchCondition() {
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		
		// 拼接检索条件，当检索关键字非空时，才添加检索条件，忽略大小写
		if (!business.getSubType().getId().equals("-1")) {// 按业务名称检索
			hql.append(" and so.id like :type");
			map.put("type", business.getSubType().getId());
		}
		if (businessStatus != -1) {// 按业务状态检索
			hql.append(" and b.status =:businessStatus");
			map.put("businessStatus", businessStatus);
		}
		//按业务起始时间检索
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			map.put("startDate", df.format(startDate));
			hql.append(" and b.startDate is not null and to_char(b.startDate,'yyyy-MM-dd')>=:startDate");
		}
		if (endDate != null) {
			map.put("endDate", df.format(endDate));
			if(startDate == null){
				hql.append(" and b.startDate is not null");
			}
			hql.append(" and to_char(b.startDate,'yyyy-MM-dd')<=:endDate");
		}
		//按个人申请截止时间检索
		if (applicantDeadline1 != null) {
			map.put("applicantDeadline1", df.format(applicantDeadline1));
			hql.append(" and b.applicantDeadline is not null and to_char(b.applicantDeadline,'yyyy-MM-dd')>=:applicantDeadline1");
		}
		if (applicantDeadline2 != null) {
			map.put("applicantDeadline2", df.format(applicantDeadline2));
			if(applicantDeadline1 == null){
				hql.append(" and b.applicantDeadline is not null");
			}
			hql.append(" and to_char(b.applicantDeadline,'yyyy-MM-dd')<=:applicantDeadline2");
		}
		//按部门审核截止时间检索
		if (deptInstDeadline1 != null) {
			map.put("deptInstDeadline1", df.format(deptInstDeadline1));
			hql.append(" and b.deptInstDeadline is not null and to_char(b.deptInstDeadline,'yyyy-MM-dd')>=:deptInstDeadline1");
		}
		if (deptInstDeadline2 != null) {
			map.put("deptInstDeadline2", df.format(deptInstDeadline2));
			if(deptInstDeadline1 == null){
				hql.append(" and b.deptInstDeadline is not null");
			}
			hql.append(" and to_char(b.deptInstDeadline,'yyyy-MM-dd')<=:deptInstDeadline2");
		}
		//按高校审核截止时间检索
		if (univDeadline1 != null) {
			map.put("univDeadline1", df.format(univDeadline1));
			hql.append(" and b.univDeadline is not null and to_char(b.univDeadline,'yyyy-MM-dd')>=:univDeadline1");
		}
		if (univDeadline2 != null) {
			map.put("univDeadline2", df.format(univDeadline2));
			if(deptInstDeadline1 == null){
				hql.append(" and b.univDeadline is not null");
			}
			hql.append(" and to_char(b.univDeadline,'yyyy-MM-dd')<=:univDeadline2");
		}
		//按省厅审核截止时间检索
		if (provDeadline1 != null) {
			map.put("provDeadline1", df.format(provDeadline1));
			hql.append(" and b.provDeadline is not null and to_char(b.provDeadline,'yyyy-MM-dd')>=:provDeadline1");
		}
		if (provDeadline2 != null) {
			map.put("provDeadline2", df.format(provDeadline2));
			if(provDeadline1 == null){
				hql.append(" and b.provDeadline is not null");
			}
			hql.append(" and to_char(b.provDeadline,'yyyy-MM-dd')<=:provDeadline2");
		}
		//按评审截止时间检索
		if (reviewDeadline1 != null) {
			map.put("reviewDeadline1", df.format(reviewDeadline1));
			hql.append(" and b.reviewDeadline is not null and to_char(b.reviewDeadline,'yyyy-MM-dd')>=:reviewDeadline1");
		}
		if (reviewDeadline2 != null) {
			map.put("reviewDeadline2", df.format(reviewDeadline2));
			if(reviewDeadline1 == null){
				hql.append(" and b.reviewDeadline is not null ");
			}
			hql.append(" and to_char(b.reviewDeadline,'yyyy-MM-dd')<=:reviewDeadline2");
		}
		return new Object[] {
			hql.toString(),
			map,
			0,
			null
		};
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author 肖雅
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAdvSearchQuery(Map searchQuery) {
		// 拼接检索条件，当检索关键字非空时，才添加检索条件，忽略大小写
		if (!business.getSubType().getId().equals("-1")) {// 按业务名称检索
			searchQuery.put("type", business.getSubType().getId());
		}
		if (businessStatus != -1) {// 按业务状态检索
			searchQuery.put("businessStatus", businessStatus);
		}
		//按业务起始时间检索
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null) {
			searchQuery.put("startDate", df.format(startDate));
		}
		if (endDate != null) {
			searchQuery.put("endDate", df.format(endDate));
		}
		//按个人申请截止时间检索
		if (applicantDeadline1 != null) {
			searchQuery.put("applicantDeadline1", df.format(applicantDeadline1));
		}
		if (applicantDeadline2 != null) {
			searchQuery.put("applicantDeadline2", df.format(applicantDeadline2));
		}
		//按部门审核截止时间检索
		if (deptInstDeadline1 != null) {
			searchQuery.put("deptInstDeadline1", df.format(deptInstDeadline1));
		}
		if (deptInstDeadline2 != null) {
			searchQuery.put("deptInstDeadline2", df.format(deptInstDeadline2));
		}
		//按高校审核截止时间检索
		if (univDeadline1 != null) {
			searchQuery.put("univDeadline1", df.format(univDeadline1));
		}
		if (univDeadline2 != null) {
			searchQuery.put("univDeadline2", df.format(univDeadline2));
		}
		//按省厅审核截止时间检索
		if (provDeadline1 != null) {
			searchQuery.put("provDeadline1", df.format(provDeadline1));
		}
		if (provDeadline2 != null) {
			searchQuery.put("provDeadline2", df.format(provDeadline2));
		}
		//按评审截止时间检索
		if (reviewDeadline1 != null) {
			searchQuery.put("reviewDeadline1", df.format(reviewDeadline1));
		}
		if (reviewDeadline2 != null) {
			searchQuery.put("reviewDeadline2", df.format(reviewDeadline2));
		}
	}

	
	/**
	 * 进入添加
	 */
	public String toAdd() {
		getBusinessType();
		return SUCCESS;
	}
	
	/**
	 * 添加业务
	 */
	public String add() {
		entityId = dao.add(business);// 添加业务 
		return SUCCESS;
	}
	
	/**
	 * 添加校验
	 */
	public void validateAdd() {
		this.updateValidate(1);
	}
	
	/**
	 * 进入修改
	 */
	public String toModify() {
		business = (Business) dao.query(Business.class, entityId);// 获取业务
		if (business == null) {// 业务不存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_BUSINESS_NULL);
			return INPUT;
		} else {// 业务存在，备用业务ID
			ActionContext.getContext().getSession().put(TMP_ENTITY_ID, entityId);
			return SUCCESS;
		}
	}
	
	/**
	 * 进入修改校验
	 */
	public void validateToModify() {
		if (entityId == null || entityId.isEmpty()) {// 业务ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_MODIFY_NULL);
		}
	}
	
	/**
	 * 修改权限
	 */
	public String modify() {
		entityId = (String) ActionContext.getContext().getSession().get(TMP_ENTITY_ID);// 获取备用修改ID
		Business oldBusiness = (Business) dao.query(Business.class, entityId);// 获取原来业务
		entityId = businessService.modifyBusiness(oldBusiness, business);// 修改业务
		ActionContext.getContext().getSession().remove("entityId");// 删除备用业务ID
		return SUCCESS;
	}
	
	/**
	 * 修改校验
	 */
	public void validateModify() {
		this.updateValidate(2);
	}
	
	/**
	 * 输入校验
	 * @param type 校验类型：1添加; 2修改
	 */
	public void updateValidate(int type) {
		if (business.getType() == null || business.getType().isEmpty() || business.getType().equals("-1")) {// 业务类型不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_TYPE_NULL);
		}
		if (business.getSubType() == null || business.getSubType().getId().isEmpty() || business.getSubType().getId().equals("-1")) {// 业务类型不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_SUBTYPE_NULL);
		} 
		if (business.getStatus() == -1) {// 业务状态不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_STATUS_NULL);
		}
		if (business.getStartDate() == null) {//业务起始时间不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_STARTDATE_NULL);
		}
//		if (business.getBusinessYear() == null) {//业务年份不得为空
//			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_BUSINESSYEAR_NULL);
//		}
		if (business.getEndYear() != -1 && business.getEndYear().compareTo(business.getStartYear()) < 0) {// 业务对象截止年份不得小于起始年份
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_ENDYEAR_ERROR);
		}
		if (!businessService.isBusinessYearEqual(business.getSubType().getId(), business.getStartYear(), business.getEndYear())) {//业务起止年份是否一致
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_YEAR_NOT_EQUAL);
		}
		String businessId = "";
		entityId = (String) ActionContext.getContext().getSession().get(TMP_ENTITY_ID);// 获取备用修改ID;
		if(type == 2){
			businessId = entityId;
		}
		if (!businessService.checkBusinessType(business.getSubType().getId(), businessId, business.getStartYear(), business.getEndYear())) {// 业务类型存在，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_TYPE_EXIST);
		}
		if(!businessService.isBusinessYearCrossed(business.getSubType().getId(), businessId, business.getStartYear(), business.getEndYear())) {//业务对象起止年份交叉，返回错误提示
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_YEAR_CROSS);
		}
	}
	
	/**
	 * 删除业务
	 */
	public String delete() {
		for (String entityId : entityIds) {
			dao.delete(Business.class, entityId);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除校验
	 */
	@SuppressWarnings("unchecked")
	public String validateDelete() {
		if (entityIds == null || entityIds.isEmpty()) {// 业务ID不得为空
			jsonMap.put(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_DELETE_NULL);
			return INPUT;
		} else {
			return null;
		}
	}
	
	/**
	 * 进入查看
	 */
	public String toView() {
		return SUCCESS;
	}
	
	/**
	 * 进入查看校验
	 */
	public void validateToView() {
		if (entityId == null || entityId.isEmpty()) {// 业务ID不得为空
			this.addFieldError(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_VIEW_NULL);
		}
	}
	
	
	/**
	 * 查看业务信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String view() {
		business = (Business) dao.query(Business.class, entityId.trim());// 获取业务
		if (business == null) {// 业务不存在，返回错误提示
			jsonMap.put(GlobalInfo.ERROR_INFO, BusinessInfo.ERROR_BUSINESS_NULL);
			return INPUT;
		} else {// 业务存在，存入jsonMap
			jsonMap.put("business", business);
			return SUCCESS;
		}
	}
	
	/**
	 * 获取相关系统选项(业务类型等)
	 * @param 
	 * @return 系统业务；
	 */
	
	@SuppressWarnings("rawtypes")
	public void getBusinessType(){
		List businessType = systemOptionDao.queryLeafNodes("businessType");
		request.setAttribute("businessType", businessType);
	}

	public String getMainFlag() {
		return mainFlag;
	}
	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}
	public Business getBusiness() {
		return business;
	}
	public void setBusiness(Business business) {
		this.business = business;
	}
	public IBusinessService getBusinessService() {
		return businessService;
	}
	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setBusinessStatus(int businessStatus) {
		this.businessStatus = businessStatus;
	}
	public int getBusinessStatus() {
		return businessStatus;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public Date getApplicantDeadline1() {
		return applicantDeadline1;
	}
	public void setApplicantDeadline1(Date applicantDeadline1) {
		this.applicantDeadline1 = applicantDeadline1;
	}
	public Date getApplicantDeadline2() {
		return applicantDeadline2;
	}
	public void setApplicantDeadline2(Date applicantDeadline2) {
		this.applicantDeadline2 = applicantDeadline2;
	}
	public Date getDeptInstDeadline1() {
		return deptInstDeadline1;
	}
	public void setDeptInstDeadline1(Date deptInstDeadline1) {
		this.deptInstDeadline1 = deptInstDeadline1;
	}
	public Date getDeptInstDeadline2() {
		return deptInstDeadline2;
	}
	public void setDeptInstDeadline2(Date deptInstDeadline2) {
		this.deptInstDeadline2 = deptInstDeadline2;
	}
	public Date getUnivDeadline1() {
		return univDeadline1;
	}
	public void setUnivDeadline1(Date univDeadline1) {
		this.univDeadline1 = univDeadline1;
	}
	public Date getUnivDeadline2() {
		return univDeadline2;
	}
	public void setUnivDeadline2(Date univDeadline2) {
		this.univDeadline2 = univDeadline2;
	}
	public Date getProvDeadline1() {
		return provDeadline1;
	}
	public void setProvDeadline1(Date provDeadline1) {
		this.provDeadline1 = provDeadline1;
	}
	public Date getProvDeadline2() {
		return provDeadline2;
	}
	public void setProvDeadline2(Date provDeadline2) {
		this.provDeadline2 = provDeadline2;
	}
	public Date getReviewDeadline1() {
		return reviewDeadline1;
	}
	public void setReviewDeadline1(Date reviewDeadline1) {
		this.reviewDeadline1 = reviewDeadline1;
	}
	public Date getReviewDeadline2() {
		return reviewDeadline2;
	}
	public void setReviewDeadline2(Date reviewDeadline2) {
		this.reviewDeadline2 = reviewDeadline2;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public int getEndYear() {
		return endYear;
	}
//	public int getBusinessYear() {
//		return businessYear;
//	}
//	public void setBusinessYear(int businessYear) {
//		this.businessYear = businessYear;
//	}
}
