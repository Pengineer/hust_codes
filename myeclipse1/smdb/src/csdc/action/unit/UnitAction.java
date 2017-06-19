package csdc.action.unit;

/**
 * 管理机构的基类
 * 部级、省级、校级管理机构功能类似，MinistryAction、ProvinceAction、UniversityAction均继承此类
 * 此类中包含管理机构的增、删、改、查、基本检索和高级检索
 * 2010-07-14
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.BaseAction;
import csdc.bean.Account;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.BankAccount;
import csdc.bean.Person;
import csdc.service.IUnitService;
import csdc.tool.InputValidate;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.UnitInfo;

@SuppressWarnings("unchecked")
public abstract class UnitAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	protected static final String DATE_FORMAT = "yyyy-MM-dd";// 列表时间格式
	protected static final String PAGE_BUFFER_ID = "ag.id";//缓存id
	protected static final String[] COLUMN = {
		"ag.name","ag.code, ag.name","pe.name, ag.name","ag.sname, ag.name","pr.name, ag.name","ag.sphone, ag.name","ag.sfax, ag.name","ag.type, ag.name"
		};// 排序列

	protected IUnitService unitService;// 机构管理接口
	protected String MOEId;// 教育部id(由程序获取)
	protected InputValidate inputValidate = new InputValidate();// 输入校验类
	protected List<Person> persons;// 机构中所有人员
	private List deptList; //学校中的院系列表
	protected Agency agency; //管理机构对象
	protected List<BankAccount> bankList = new ArrayList<BankAccount>(); //银行信息对象组
	protected Person director=new Person();// 管理机构负责人对象
	protected Person sdirector;// 部门负责人
	protected Person slinkman;// 部门联系人
	protected Account account; //单位主账号
	protected String delids; //选中的id组成的字符串
	protected int unitType;// 搜索时管理机构类型,删除时也许指定删除类型
	protected int listLabel; //列表显示的标志位，1:从左边栏进入
	protected Integer viewFlag; //查看类型：0,普通查看;1,个人空间中的查看

	protected String directorName; //高级检索中负责人名称
	protected String sDirectorName; //高级检索中部门负责人名称
	protected String aName; //高级检索单位名称
	protected String aCode; //高级检索单位代码
	protected String aSname; //高级检索管理部门名称
	protected String type;// 单位类型（高校才有的）
	protected String provinceId; //高级检索所在省份
	protected String directorId; //单位负责人
	protected String subjectionName; //上级管理机构名称
	protected String slinkmanId; //社科管理部门联系人
	protected String sdirectorId; //社科管理部门负责人
	protected String univOrganizerCode; //学校举办者代码
	protected String univOrganizer; //学校举办者
	
	protected List<Address> commonAddress = new ArrayList<Address>();//普通住址组
	protected List<Address> financeAddress = new ArrayList<Address>();//财务部门住址组
	protected List<Address> subjectionAddress = new ArrayList<Address>();//管理部门住址组
	
	public abstract int subType();//获取机构子类（1：部级，2：省级，3：校级）
	
	/**
	 * 添加管理机构的页面跳转
	 *@return SUCCESS，跳转到添加页面
	 */
	public String toAdd(){
		return SUCCESS;
	}
	
	/**
	 * 添加管理机构
	 * @return SUCCESS 添加成功
	 */
	@Transactional
	public String add(){
		agency = unitService.setBaseInfo(agency);
		Account current = loginer.getAccount();// 获取账号信息
		//根据账号判断添加的机构是否合法
		int subType = 0;
		if(1 == subType() || 2 == subType()){ //子类为部或省
			subType = subType();
			agency.setType(subType);
		}else if(3 == subType()){ //子类为校级
			subType = agency.getType();
		}else{
			this.addFieldError("agency.name",UnitInfo.ERROR_CANNOT_ADD);
			return INPUT;
		}
		if(1 == agency.getType()  || 2 == agency.getType()){ //单位类型为部级或省级
			agency.setSubjection(unitService.getMOE());
		}else if(3 == agency.getType() || 4 == agency.getType()){ //单位类型为校级
			
		}else{
			this.addFieldError("agency.name",UnitInfo.ERROR_AGENCY_TYPE_ILLEAGER);
			return INPUT; 
		}
		if(!unitService.checkAgencyLeadByAccount(current, subType, null, agency.getSubjection().getId(),loginer.getCurrentBelongUnitId())){
			this.addFieldError("agency.name",UnitInfo.ERROR_MANAGEMENT_OUTSIDE);
			return INPUT;
		}
		agency.setName(agency.getName().trim());
		//合并学校举办者代码与举办者
		StringBuffer organizer = new StringBuffer();
		if(null != univOrganizerCode && univOrganizerCode.trim().length()>0){
			organizer.append(univOrganizerCode+"/");
			if(null != univOrganizer && univOrganizer.trim().length()>0){
				organizer.append(univOrganizer);
			}
		}else{
			if(null != univOrganizer && univOrganizer.trim().length()>0){
				organizer.append("/"+univOrganizer);
			}
		}
		//设置bankAccount与Agency的相关关系[非外键]
		agency = unitService.setBankInfo(agency, bankList);
		agency = unitService.setAddress(agency, commonAddress, financeAddress, subjectionAddress);
		agency.setOrganizer(organizer.toString());
		agency.setCreateDate(new Date());
		agency.setCreateMode(1);
		entityId = dao.add(agency);
		return SUCCESS;
	}
	
	/**
	 * 根据编号删除对应管理机构
	 * @param
	 * @return
	 */
	@Transactional
	public String delete(){
		int type = unitService.deleteAgencyByIds(unitType,entityIds);
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

	/**
	 * 为编辑准备管理机构对象
	 * @return
	 */
	@Transactional
	public String toModify(){
		agency = (Agency) dao.query(Agency.class, entityId);
		String bankIds = agency.getBankIds();
		bankList = dao.query("select ba from BankAccount ba where ba.ids = ? order by ba.sn asc ", bankIds);
		commonAddress = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", agency.getAddressIds());
		financeAddress = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", agency.getFaddressIds());
		subjectionAddress = dao.query("select a from Address a where a.ids = ? order by a.sn asc ", agency.getSaddressIds());
		//将数据库中的负责人、联系人等对应的personId转化为officerId,方便前台使用
		if(agency.getDirector()!=null){
			directorId = unitService.getOfficerIdByPersonId(agency.getDirector().getId());
			directorName = agency.getDirector().getName();
		}
		if((agency.getType() == 2 || agency.getType() == 3 || agency.getType() == 4) && agency.getSubjection() != null) {
			subjectionName = agency.getSubjection().getName();
		};
		if(agency.getSdirector()!=null){
			sdirectorId = unitService.getOfficerIdByPersonId(agency.getSdirector().getId());
		}
		if(agency.getSlinkman()!=null){
			slinkmanId = unitService.getOfficerIdByPersonId(agency.getSlinkman().getId());
		}
		//学校举办者处理，分为代码和名称显示（考虑到数据库中有很多不规范的数据）
		if(agency.getOrganizer() !=null && agency.getOrganizer().length()>0 ){
			univOrganizerCode = agency.getOrganizer().substring(0, agency.getOrganizer().indexOf("/"));
			univOrganizer = agency.getOrganizer().substring(agency.getOrganizer().indexOf("/")+1, agency.getOrganizer().length());
		}
		Hibernate.initialize(agency.getSdirector());
		Hibernate.initialize(agency.getSlinkman());
		return SUCCESS;
	}
	
	/**
	 * 编辑管理机构信息
	 * @return
	 */
	@Transactional
	public String modify(){
		agency = unitService.setBaseInfo(agency);
		int subType = 0;
		if(1 == subType() || 2 == subType()){ //子类为部或省
			subType = subType();
			agency.setType(subType);
		}else if(3 == subType()){ //子类为校
			subType = agency.getType();
		}else{
			return INPUT;
		}
		entityId=agency.getId();
		Account current = loginer.getAccount();
		// 高校才修改上级管理机构，部级省级默认为教育部
		if(subType == 3 && !unitService.checkAgencyLeadByAccount(current, subType, entityId, agency.getSubjection().getId(), loginer.getCurrentBelongUnitId())){
			this.addFieldError("agency.name",UnitInfo.ERROR_MANAGEMENT_OUTSIDE);
			return INPUT;
		}
		unitService.getModifyOfAgency(session, agency, directorId, slinkmanId, sdirectorId, univOrganizerCode, univOrganizer, entityId);
		//设置bankAccount与Agency的相关关系[非外键]
		agency = unitService.resetBankInfo(agency, bankList);
		agency = unitService.resetAddress(agency, commonAddress, financeAddress, subjectionAddress);
		agency.setUpdateDate(new Date());
		dao.merge(agency);
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
	 * 查看管理机构详细信息
	 * @return
	 */
	public String view(){
		if(!unitService.checkIfUnderControl(loginer, entityId.trim(), subType(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO, UnitInfo.ERROR_MANAGEMENT_OUTSIDE);
			return ERROR;
		}
		unitService.getViewOfAgency(entityId, jsonMap);
		return SUCCESS;
	}
	
	/**
	 * 管理机构高级检索
	 * @return
	 */
	public Object[] advSearchCondition(){
		this.MOEId=unitService.getMOEId(); //获取教育部id
		if(!aName.isEmpty()){
			aName = aName.toLowerCase().trim();
		}
		if(!aCode.isEmpty()){
			aCode = aCode.toLowerCase().trim();
		}
		if(!directorName.isEmpty()){
			directorName.trim();
		}
		if(type == null){
			type = "";
		}else {
			if(!type.isEmpty()){
				type.trim();
			}
		}
		if(!sDirectorName.isEmpty()){
			sDirectorName.trim();
		}
		if(!aSname.isEmpty()){
			aSname.trim();
		}
		this.directorName = this.directorName.trim();
		this.sDirectorName = this.sDirectorName.trim();
		if(loginer.getCurrentType().equals(AccountType.ADMINISTRATOR)){ //系统管理员
			entityId="";
		}else if(loginer.getCurrentType().equals(AccountType.MINISTRY) && MOEId.equals(loginer.getCurrentBelongUnitId())){ //社科司账号 
			entityId="";
		}else if(loginer.getCurrentType().equals(AccountType.MINISTRY) && !MOEId.equals(loginer.getCurrentBelongUnitId())){ //中心账号
			if("ministryPage".equalsIgnoreCase(pageName())){ //中心账号查看部级列表
				return null;
			}else{
				entityId="";
			}
		}else if(loginer.getCurrentType().equals(AccountType.PROVINCE)){ //省级账号
			entityId=loginer.getCurrentBelongUnitId();
		}else { //高校及下级单位不能获取管理机构列表
			entityId=loginer.getCurrentBelongUnitId();
		}
		return unitService.advSearchAgency(entityId,aName,aCode,directorName,type,provinceId,aSname,sDirectorName,pageName());
	}
	
	/**
	 * 对saveAdvSearchQuery方法进行子类重写
	 * @author yangfq
	 */
	public void saveAdvSearchQuery(Map searchQuery) {
		if(null != aName && !aName.isEmpty()){
			searchQuery.put("aName", aName);
		}
		if(null != aCode && !aCode.isEmpty()){
			searchQuery.put("aCode", aCode);
		}
		if (null != directorName && !directorName.isEmpty()) {
			searchQuery.put("directorName", directorName);
		}
		if (null != provinceId && !provinceId.isEmpty() ) {
			searchQuery.put("provinceId", provinceId);
		}
		if(aSname != null && !aSname.isEmpty())	{
			searchQuery.put("aSname", aSname);
		}
		if(sDirectorName != null && !sDirectorName.isEmpty())	{
			searchQuery.put("sDirectorName", sDirectorName);
		}
		if(type == null){
			type = "";
		}else if (null != type && !type.isEmpty()) {
			searchQuery.put("type", type);
		}
//		 if (null != type && !type.isEmpty()) {
//			searchQuery.put("type", type);
//		 }
	}
	
	
	/**
	 * 添加管理机构时输入信息校验
	 * @return
	 */
	public String validateAdd(){
		if(null == agency.getName() || agency.getName().trim().length()==0){
			this.addFieldError("agency.name",UnitInfo.ERROR_NAME_NULL);
		}else if(agency.getName().trim().length()>40 || agency.getName().trim().length()<0){
			this.addFieldError("agency.name",UnitInfo.ERROR_NAME_ILLEGAL);
		}

		if(null == agency.getEnglishName() || agency.getEnglishName().trim().length()>40 || agency.getEnglishName().trim().length()<0){
			this.addFieldError("agency.englishName",UnitInfo.ERROR_ENGLISHNAME_ILLEGAL);
		}

		if(null !=agency.getCode() && (agency.getCode().trim().length()>40 || agency.getCode().trim().length()<0)){
			this.addFieldError("agency.code",UnitInfo.ERROR_CODE_ILLEGAL);
		}

		if(null !=agency.getAbbr() && (agency.getAbbr().trim().length()>40 || agency.getAbbr().trim().length()<0)){
			this.addFieldError("agency.abbr",UnitInfo.ERROR_ABBR_ILLEGAL);
		}

		if(agency.getType()!=0 && (agency.getType()>5 || agency.getType()<1)){
			this.addFieldError("agency.type",UnitInfo.ERROR_AGENCY_TYPE_ILLEAGER);
		}

		if(null == agency.getProvince().getId() || agency.getProvince().getId().trim().length()==0){
			this.addFieldError("agency.province.id",UnitInfo.ERROR_PROVINCE_NULL);
		}else if(agency.getProvince().getId().trim().length()>40 || agency.getProvince().getId().trim().length()<0){
			this.addFieldError("agency.province.id",UnitInfo.ERROR_PROVINCE_ILLEGAL);
		}

		if(null != agency.getCity().getId() && (agency.getCity().getId().trim().length()>40 ||agency.getCity().getId().trim().length()<0 )){
			this.addFieldError("agency.city",UnitInfo.ERROR_CITY_ILLEAGER);
		}

		//上级管理部门改为省级管理机构，只对高校有效
		if((agency.getType()==3 || agency.getType()==4) && (null== agency.getSubjection() || null == agency.getSubjection().getId().trim() || "".equals(agency.getSubjection().getId().trim()))){
			this.addFieldError("agency.subjection.id",UnitInfo.ERROR_SUBJECTION_NULL);
		}
//		else if(agency.getSubjection().getId().trim().length()>40 || agency.getSubjection().getId().trim().length()<0 ){
//			this.addFieldError("agency.subjection.id",UnitInfo.ERROR_SUBJECTION_ILLEGAL);
//		}

		if(null != agency.getIntroduction() &&(agency.getIntroduction().trim().length()>20000 || agency.getIntroduction().trim().length()<0)){
			this.addFieldError("agency.introduction",UnitInfo.ERROR_INTRODUCTION_ILLEGAL);
		}

//		if(null == agency.getSname() || agency.getSname().trim().length()==0){
//			this.addFieldError("agency.sname",UnitInfo.ERROR_SNAME_NULL);
//		}else 
		if(agency.getSname().trim().length()>40 || agency.getSname().trim().length()<0){
			this.addFieldError("agency.sname",UnitInfo.ERROR_SNAME_ILLEGAL);
		}

		if(null != agency.getShomepage() && agency.getShomepage().trim().length()>40){
			this.addFieldError("agency.shomepage",UnitInfo.ERROR_SHOMEPAGE_ILLEGAL);
		}
		
//		if(null == agency.getFname() || agency.getFname().trim().length()>40 || agency.getFname().trim().length()<1){
//			this.addFieldError("agency.fname",UnitInfo.ERROR_FNAME_ILLEGAL);
//		}
		
		for(BankAccount bankAccount : bankList){
			if (bankAccount.getBankName() != null && bankAccount.getBankName().trim().length() > 50){
				this.addFieldError("bankAccount.bankName", UnitInfo.ERROR_BANKNAME_ILLEGAL);
			}
			if (bankAccount.getBankCupNumber() != null && bankAccount.getBankCupNumber().trim().length() > 40){
				this.addFieldError("bankAccount.bankCupNumber", UnitInfo.ERROR_FCUPNUMBER_ILLEGAL);
			}
			if (bankAccount.getAccountName() != null && bankAccount.getAccountName().trim().length() > 50){
				this.addFieldError("bankAccount.accountName", UnitInfo.ERROR_BANKACCOUNTNAME_ILLEGAL);
			}
			if (bankAccount.getAccountNumber() != null && bankAccount.getAccountNumber().trim().length() > 40){
				this.addFieldError("bankAccount.accountNumber", UnitInfo.ERROR_BANKACCOUN_ILLEGAL);
			}
		}
		for(Address address : commonAddress){
			if(null != address.getPostCode() && !inputValidate.checkPostcode(address.getPostCode().trim())){
				this.addFieldError("address.postcode",UnitInfo.ERROR_POSTCODE_ILLEGAL);
			}
			if(null != address.getAddress() && address.getAddress().trim().length()>100){
				this.addFieldError("address.address",UnitInfo.ERROR_ADDRESS_ILLEGAL);
			}
		}
		for(Address address : subjectionAddress){
			if(null != address.getPostCode() && !inputValidate.checkPostcode(address.getPostCode().trim())){
				this.addFieldError("address.postcode",UnitInfo.ERROR_POSTCODE_ILLEGAL);
			}
			if(null != address.getAddress() && address.getAddress().trim().length()>100){
				this.addFieldError("address.address",UnitInfo.ERROR_ADDRESS_ILLEGAL);
			}
		}
		for(Address address : financeAddress){
			if(null != address.getPostCode() && !inputValidate.checkPostcode(address.getPostCode().trim())){
				this.addFieldError("address.postcode",UnitInfo.ERROR_POSTCODE_ILLEGAL);
			}
			if(null != address.getAddress() && address.getAddress().trim().length()>100){
				this.addFieldError("address.address",UnitInfo.ERROR_ADDRESS_ILLEGAL);
			}
		}
		if(hasErrors()){
			return INPUT;
		}else{
			return null;
		}
	}

	/**
	 * 更改管理机构信息时的校验
	 */
	public void validateModify(){
		unitService.getValidateModifyOfAgency(agency, bankList, inputValidate, this);
		unitService.getValidateModify(inputValidate, commonAddress, this);
		unitService.getValidateModify(inputValidate, financeAddress, this);
		unitService.getValidateModify(inputValidate, subjectionAddress, this);
	}

	public IUnitService getUnitService() {
		return unitService;
	}
	public void setUnitService(IUnitService unitService) {
		this.unitService = unitService;
	}
	public InputValidate getInputValidate() {
		return inputValidate;
	}
	public void setInputValidate(InputValidate inputValidate) {
		this.inputValidate = inputValidate;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	public Person getDirector() {
		return director;
	}
	public void setDirector(Person director) {
		this.director = director;
	}
	public Person getSdirector() {
		return sdirector;
	}
	public void setSdirector(Person sdirector) {
		this.sdirector = sdirector;
	}
	public Person getSlinkman() {
		return slinkman;
	}
	
	public void setSlinkman(Person slinkman) {
		this.slinkman = slinkman;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public String getDelids() {
		return delids;
	}
	
	public void setDelids(String delids) {
		this.delids = delids;
	}

	public int getUnitType() {
		return unitType;
	}

	public void setUnitType(int unitType) {
		this.unitType = unitType;
	}

	public String[] getColumn() {
		return COLUMN;
	}
	public Integer getViewFlag() {
		return viewFlag;
	}
	
	public void setViewFlag(Integer viewFlag) {
		this.viewFlag = viewFlag;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public int getListLabel() {
		return listLabel;
	}

	public void setListLabel(int listLabel) {
		this.listLabel = listLabel;
	}

	public String[] column() {
		return UnitAction.COLUMN;
	}
	
	public String dateFormat() {
		return UnitAction.DATE_FORMAT;
	}
	
	public String getDirectorName() {
		return directorName;
	}
	
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	
	public String getSDirectorName() {
		return sDirectorName;
	}
	
	public void setSDirectorName(String directorName) {
		sDirectorName = directorName;
	}
	
	public String getAName() {
		return aName;
	}
	
	public void setAName(String name) {
		aName = name;
	}
	
	public String getACode() {
		return aCode;
	}
	
	public void setACode(String code) {
		aCode = code;
	}
	
	public String getASname() {
		return aSname;
	}
	
	public void setASname(String sname) {
		aSname = sname;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProvinceId() {
		return provinceId;
	}
	
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String pageBufferId(){
		return UnitAction.PAGE_BUFFER_ID;
	}
	public String getDirectorId() {
		return directorId;
	}
	public void setDirectorId(String directorId) {
		this.directorId = directorId;
	}
	public String getSubjectionName() {
		return subjectionName;
	}

	public void setSubjectionName(String subjectionName) {
		this.subjectionName = subjectionName;
	}

	public String getSlinkmanId() {
		return slinkmanId;
	}
	public void setSlinkmanId(String slinkmanId) {
		this.slinkmanId = slinkmanId;
	}
	public String getSdirectorId() {
		return sdirectorId;
	}
	public void setSdirectorId(String sdirectorId) {
		this.sdirectorId = sdirectorId;
	}
	public String getMOEId() {
		return MOEId;
	}
	public void setMOEId(String id) {
		MOEId = id;
	}
	public List getDeptList() {
		return deptList;
	}
	public void setDeptList(List deptList) {
		this.deptList = deptList;
	}
	public String getUnivOrganizerCode() {
		return univOrganizerCode;
	}
	public void setUnivOrganizerCode(String univOrganizerCode) {
		this.univOrganizerCode = univOrganizerCode;
	}
	public String getUnivOrganizer() {
		return univOrganizer;
	}
	public void setUnivOrganizer(String univOrganizer) {
		this.univOrganizer = univOrganizer;
	}
	public List<BankAccount> getBankList() {
		return bankList;
	}
	public void setBankList(List<BankAccount> bankList) {
		this.bankList = bankList;
	}

	public List<Address> getCommonAddress() {
		return commonAddress;
	}

	public void setCommonAddress(List<Address> commonAddress) {
		this.commonAddress = commonAddress;
	}

	public List<Address> getFinanceAddress() {
		return financeAddress;
	}

	public void setFinanceAddress(List<Address> financeAddress) {
		this.financeAddress = financeAddress;
	}

	public List<Address> getSubjectionAddress() {
		return subjectionAddress;
	}

	public void setSubjectionAddress(List<Address> subjectionAddress) {
		this.subjectionAddress = subjectionAddress;
	}
}
