package csdc.action.unit;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.service.IUnitService;
import csdc.tool.InputValidate;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.UnitInfo;
import csdc.tool.merger.DepartmentMerger;

public class DepartmentAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private IUnitService unitService;
	private Department department;//院系对象
	protected InputValidate inputValidate = new InputValidate();// 输入校验类
	private Account account; //账号对象（用于查看页面显示账号信息）
	@SuppressWarnings("unchecked")
	private List departments;// 院系列表对象
	private String universityid;// 学校编号
	private int deleteType; //删除类型 1:部级及省级;2校级;3研究机构;4:院系
	private int listLabel; //是否从左边栏进入的标志 1:从左边栏进入
	private int viewFlag; ////查看类型：1，院系中list的查看、添加修改后的查看；2，其他地方引用的查看;3,查看自身的信息。
	private String[] delid;// 选中的id
//	private String delids; //选中的id组成的字符串（逗号隔开）
	private static final String HQL = "select de.id,de.name,de.code,ag.name,pe.name,de.phone,de.fax,pe.id,ag.id from Department de left join de.director pe left join de.university ag left join ag.province so ";
	private static final String[] COLUMN = {"de.name","de.code, de.name","ag.name, de.name","pe.name, de.name","de.phone, de.name","de.fax, de.name"}; // 排序列
	private static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	private static final String PAGE_NAME = "departmentPage";
	private static final String PAGE_BUFFER_ID = "de.id"; //缓存id
	protected String iName; //高级检索中院系名称
	protected String iCode; //高级检索中院系代码
	protected String iUniversity; //高级检索中院系所属高校
	protected String iDirectorName; //高级检索中院系负责人姓名
	protected String provName; //高级检索中高校所在省份
	
	private List<Address> commonAddress = new ArrayList<Address>();//院系住址组
	
	public String getUniversityId() {
		return universityId;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	protected String universityId;
	protected String universityName;
	protected String directorId;
	protected String directorName;
	protected String linkmanId;
	protected String linkmanName;
	private String checkedIds, mainId, name, code;//合并高校、院系、基地时的所选id和主id，合并后的名称和代码


	public static String[] getColumn() {
		return COLUMN;
	}

	public IUnitService getUnitService() {
		return unitService;
	}
	public String[] column() {
		return DepartmentAction.COLUMN;
	}
	public String pageName() {
		return DepartmentAction.PAGE_NAME;
	}
	public String pageBufferId() {
		return DepartmentAction.PAGE_BUFFER_ID;
	}

	public String getDirectorId() {
		return directorId;
	}

	public void setDirectorId(String directorId) {
		this.directorId = directorId;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public static String getPageName() {
		return PAGE_NAME;
	}

	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}

	@SuppressWarnings("unchecked")
	public List getDepartments() {
		return departments;
	}

	@SuppressWarnings("unchecked")
	public void setDepartments(List departments) {
		this.departments = departments;
	}

	public String getUniversityid() {
		return universityid;
	}

	public void setUniversityid(String universityid) {
		this.universityid = universityid;
	}

	public String[] getDelid() {
		return delid;
	}

	public void setDelid(String[] delid) {
		this.delid = delid;
	}

	public int getListLabel() {
		return listLabel;
	}

	public void setListLabel(int listLabel) {
		this.listLabel = listLabel;
	}

	public int getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(int deleteType) {
		this.deleteType = deleteType;
	}

	public String getIName() {
		return iName;
	}

	public void setIName(String name) {
		iName = name;
	}

	public String getICode() {
		return iCode;
	}

	public void setICode(String code) {
		iCode = code;
	}

	public String getIUniversity() {
		return iUniversity;
	}

	public void setIUniversity(String university) {
		iUniversity = university;
	}

	public String getIDirectorName() {
		return iDirectorName;
	}

	public void setIDirectorName(String directorName) {
		iDirectorName = directorName;
	}

	public String dateFormat() {
		return DepartmentAction.DATE_FORMAT;
	}

	/**
	 * 为添加院系做准备
	 */
	public String toAdd(){
		return SUCCESS;
	}

	/**
	 * 添加院系
	 * @return success 添加成功，转向请求view页面
	 */
	@Transactional
	public String add(){
		Account current = loginer.getAccount();// 获取账号信息
		if(!unitService.checkDepartmentLeadByAccount(department, current, loginer.getCurrentBelongUnitId())){
			this.addFieldError("department.name","该院系不在您的管理范围内。");
			return INPUT;
		}
		department.setName(department.getName().trim());
		department.setCreateDate(new Date());
		department.setCreateMode(1);
		entityId = dao.add(department);
		unitService.setAddress(department, commonAddress);
		return SUCCESS;
	}


	/**
	 * 进入查看页面
	 * @return
	 */
	public String toView() {
		return SUCCESS;
	}

	/**
	 * 查看院系详细信息
	 * @return success 查看成功，转到view页面
	 */
	@SuppressWarnings("unchecked")
	public String view(){
		if(!unitService.checkIfUnderControl(loginer, entityId.trim(), 4, true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, UnitInfo.ERROR_MANAGEMENT_OUTSIDE);
			return ERROR;
		}
		unitService.getViewOfDepartment(entityId, jsonMap);
		return SUCCESS;
	}

	/**
	 * 为修改院系信息做准备
	 * @return success 准备成功，转到modify页面
	 */
	@Transactional
	public String toModify(){
		department =(Department) dao.query(Department.class, entityId);
		if(department.getUniversity() != null){
			universityId = department.getUniversity().getId();
			universityName = department.getUniversity().getName();
		}
		if(department.getDirector()!=null){
			directorId = unitService.getOfficerIdByPersonId(department.getDirector().getId());
			directorName = department.getDirector().getName();
		}
		if(department.getLinkman()!=null){
			linkmanId = unitService.getOfficerIdByPersonId(department.getLinkman().getId());
			linkmanName = department.getLinkman().getName();
		}
		commonAddress = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", department.getAddressIds());
		setCommonAddress(commonAddress);
		session.put("departmentId", entityId);
		return SUCCESS;
	}

	/**
	 * 修改院系信息
	 * @return success 编辑成功，转向请求view页面
	 */
	@Transactional
	public String modify(){
		Account account = loginer.getAccount();// 获取账号信息
		if(!unitService.checkDepartmentLeadByAccount(department, account, loginer.getCurrentBelongUnitId())){
			this.addFieldError("department.name","该院系不在您的管理范围内。");
			return INPUT;
		}
		entityId = (String) session.get("departmentId");
		unitService.getModifyOfDepartment(session, department, directorId, linkmanId, entityId);
		unitService.resetAddress(department, commonAddress);
		session.remove("departmentId");
		return SUCCESS;
	}



	/**
	 * 初级检索
	 * @return SUCCESS返回列表页面,INPUT信息提示
	 */
	@SuppressWarnings("unchecked")
	public Object[] simpleSearchCondition() {
		keyword = (null == keyword) ? "" : keyword.toLowerCase();// 预处理关键字
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){ //系统管理员或部级管理员
			hql.append(" where 1=1");
		}else if(accountType.equals(AccountType.PROVINCE)){//省级账号(省内地方高校的院系)
			hql.append(" where ag.subjection.id=:belongUnitId and ag.type=4");
			map.put("belongUnitId", belongUnitId);
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校账号
			hql.append(" where de.university.id=:belongUnitId");
			map.put("belongUnitId", belongUnitId);
		}else{
			hql.append(" where 1=0");
		}
		if(!keyword.isEmpty()){
			if(searchType == 1){
				hql.append(" and LOWER(ag.name) like :keyword ");
				map.put("keyword", '%'+keyword+'%');
			}else if(searchType == 2){
				hql.append(" and LOWER(de.code) like :keyword ");
				map.put("keyword", '%'+keyword+'%');
			}else if (searchType == 3){
				hql.append(" and LOWER(de.name) like :keyword ");
				map.put("keyword", '%'+keyword+'%');
			}else if(searchType == 4){
				hql.append(" and LOWER(pe.name) like :keyword");
				map.put("keyword", '%'+keyword+'%');
			}else{
				hql.append(" and (LOWER(ag.name) like :keyword or LOWER(de.name) like :keyword or LOWER(de.code) like :keyword or LOWER(pe.name) like :keyword) ");
				map.put("keyword", '%'+keyword+'%');
			}
		}
		return new Object[]{
			hql.toString(),
			map,
			0,
			null
		};
	}

	@SuppressWarnings("unchecked")
	public Object[] advSearchCondition(){
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		hql.append(HQL);
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();
		if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){ //系统管理员或部级管理员
			hql.append(" where 1=1");
		}else if(accountType.equals(AccountType.PROVINCE)){ //省级管理员(研究机构所在学校位于该省，并且研究机构的类型不是部级研究机构）
			hql.append(" where ag.subjection.id=:belongUnitId and ag.type=4");
			map.put("belongUnitId", belongUnitId);
		}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){ //校级管理员
			hql.append(" where de.university.id=:belongUnitId");
			map.put("belongUnitId", belongUnitId);
		}else{
			hql.append(" where 1=0");
		}
		if(iName !=null && iName.trim().length()>0){
			hql.append(" and LOWER(de.name) like :name");
			map.put("name", '%'+iName+'%');
		}
		if(iCode !=null && iCode.trim().length()>0){
			hql.append(" and LOWER(de.code) like :code");
			map.put("code", '%'+iCode+'%');
		}
		if(iUniversity != null && iUniversity.trim().length()>0){
			hql.append(" and LOWER(ag.name) like :university");
			map.put("university", '%'+iUniversity+'%');
		}
		if(iDirectorName !=null && iDirectorName.trim().length()>0){
			hql.append(" and LOWER(pe.name) like :directorName");
			map.put("directorName", '%'+iDirectorName+'%');
		}
		if(provName !=null && provName.trim().length()>0){
			hql.append(" and LOWER(so.name) like :provName");
			map.put("provName", '%'+provName+'%');
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
	 * @author yangfq
	 */
	public void saveAdvSearchQuery(Map searchQuery) {
		if(null != iName && !iName.isEmpty()){
			searchQuery.put("iName", iName);
		}
		if(null != iCode && !iCode.isEmpty()){
			searchQuery.put("iCode", iCode);
		}
		if (null != iUniversity && !iUniversity.isEmpty()) {
			searchQuery.put("iUniversity", iUniversity);
		}
		if (null != iDirectorName && !iDirectorName.isEmpty()) {
			searchQuery.put("iDirectorName", iDirectorName);
		}
	}
	

	/**
	 * 根据编号删除对应管理机构
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String delete(){
		int type = unitService.deleteDeptByIds(entityIds);
		if(type == 1){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_1);
			return INPUT;
		} else if(type == 2){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_2);
			return INPUT;
		} else if(type == 3){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_3);
			return INPUT;
		} else if(type == 4){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_4);
			return INPUT;
		} else if(type == 5){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_5);
			return INPUT;
		} else if(type == 6){
			jsonMap.put(GlobalInfo.ERROR_INFO,UnitInfo.ERROR_UNIT_DELETE_CANNOT_6);
			return INPUT;
		}
		return SUCCESS;
	}
	
//	@SuppressWarnings({ "unchecked", "deprecation" })
//	public String merge(){
//		if(entityIds.size()<2){
//			jsonMap.put(GlobalInfo.ERROR_INFO, "应至少选择2个院系。");
//			return INPUT;
//		}
//		if(unitService.checkDeptsInSameUniv(entityIds)){
//			unitService.mergeDepts(entityIds);
//		}else{
//			jsonMap.put(GlobalInfo.ERROR_INFO, "院系不在同一学校内，无法合并。");
//			return INPUT;
//		}
////		backToList(PAGE_NAME, pageNumber, null);
//		return SUCCESS;
//	}
	
	/**
	 * 添加院系时的校验
	 * @return INPUT 校验未通过，返回添加页面；null,校验通过，转向执行add法。
	 */
	public void validateAdd(){
//		System.out.println("所属高校"+department.getSubjection().getId());
//		System.out.println("院系名称"+department.getName());

		if(null==department.getName() || department.getName().trim().length()==0){
			this.addFieldError("department.name","院系名不能为空");
		}else if(department.getName().trim().length()>40 || department.getName().trim().length()<1){
			this.addFieldError("department.name", "院系名应为1~40个字符");
		}

		if(null !=department.getCode() && (department.getCode().trim().length()>40 || department.getCode().trim().length()<0)){
			this.addFieldError("department.name","院系编号应为1~40个字符");
		}

		if(null == department.getUniversity() || null == department.getUniversity().getId().trim() || "".equals(department.getUniversity().getId().trim())){
			this.addFieldError("department.university.id","所属高校不能为空");
		}


		//if(null != department.getPostcode() && department.getPostcode().trim().length()>40){
		//	this.addFieldError("department.postcode", "邮政编码最长为40个字符");
		//}

		if(null != department.getPhone() && !inputValidate.checkPhone(department.getPhone().trim())){
			this.addFieldError("agency.phone","电话输入错误");
		}

		if(null != department.getEmail()){
			String[] mail = department.getEmail().split(";");
			for (int i = 0; i < mail.length; i++) {
				String	email = mail[i];
				if(!inputValidate.checkEmail(email.trim())){
					this.addFieldError("agency.email","邮箱输入错误，请重新输入");
				}
			}
		}
		if(null != department.getFax() && !inputValidate.checkFax(department.getFax().trim())){
			this.addFieldError("agency.fax","传真输入错误，请重新输入");
		}

		if(null != department.getHomepage() && department.getHomepage().trim().length()>60){
			this.addFieldError("department.homepage", "主页最长为60个字符");
		}

		if(null != department.getIntroduction() && department.getIntroduction().trim().length()>20000){
			this.addFieldError("department.introduction", "院系简介最长为800个字符");
		}
		for(Address address : commonAddress){
			if(null != address.getPostCode() && !inputValidate.checkPostcode(address.getPostCode().trim())){
				this.addFieldError("address.postcode",UnitInfo.ERROR_POSTCODE_ILLEGAL);
			}
			if(null != address.getAddress() && address.getAddress().trim().length()>100){
				this.addFieldError("address.address",UnitInfo.ERROR_ADDRESS_ILLEGAL);
			}
		}
	}

	/**
	 * 修改部门时的校验
	 * @return INPUT 校验未通过，返回修改页面；null 校验通过，转向执行modify方法
	 */
	public void validateModify(){
		department.setUniversity((Agency)dao.query(Agency.class, universityId));
		unitService.getValidateModifyOfDepartment(department, inputValidate, this);
		unitService.getValidateModify(inputValidate, commonAddress, this);
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toMerge() {
		List departments = unitService.getCheckedList(checkedIds, 2);
		String universityId = (String)((Object[]) departments.get(0))[3];
		for (int i = 1; i < departments.size(); i++) {
			if(!((String)((Object[]) departments.get(i))[3]).equals(universityId)){
				this.addFieldError(GlobalInfo.ERROR_INFO, "您选择的院系不在一个高校，请重新选择！");
				return INPUT;
			}
		}
		request.setAttribute("list", departments);
		return SUCCESS;
	}
	
	/**
	 * 合并院系
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws IllegalArgumentException 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String merge() throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		DepartmentMerger merger = (DepartmentMerger) SpringBean.getBean("departmentMerger");

		Serializable targetId = mainId;
		Set<Serializable> incomeIds = new HashSet<Serializable>();
		Matcher matcher = Pattern.compile("\\w+").matcher(checkedIds);
		while (matcher.find()) {
			incomeIds.add(matcher.group());
		}
		
		merger.mergeDepartment(targetId, new ArrayList<Serializable>(incomeIds));
		
		Department newDepartment = (Department) dao.query(Department.class, targetId);
		newDepartment.setName(name);
		newDepartment.setCode(code);
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String validateMergeDepartment() {
		
		return SUCCESS;
	}

	public String getCheckedIds() {
		return checkedIds;
	}

	public String getMainId() {
		return mainId;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	
	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Address> getCommonAddress() {
		return commonAddress;
	}

	public void setCommonAddress(List<Address> commonAddress) {
		this.commonAddress = commonAddress;
	}
}
