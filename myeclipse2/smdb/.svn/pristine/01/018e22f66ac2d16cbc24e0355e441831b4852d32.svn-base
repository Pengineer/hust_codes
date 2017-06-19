package csdc.action.unit;

import org.springframework.transaction.annotation.Transactional;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.tool.bean.AccountType;

/**
 * 机构个人空间类
 * 该类中包含个人空间的查看，及修改功能。
 * 个人空间各类机构的处理权限为同一个权限，实际处理根据账号类型自动匹配。
 * @author 江荣国
 * @version 2011.04.16
 */
public class UnitSelfspaceAction extends UnitAction {

	private static final long serialVersionUID = 1L;
	private Department department;//院系对象
	private Institute institute; //基地对象
	private String linkmanId; //联系人
	
//	private IUnitService unitService; //机构接口
//	private Agency agency; //机构对象
//	private Integer viewFlag; //查看标志 1,个人空间中查看;0,普通查看
//	protected List<Person> persons; //机构中所有人员
//	protected String directorId; //负责人
//	protected String slinkmanId; //社科管理部门联系人
//	protected String sdirectorId; //社科管理部门负责人
//	protected String univOrganizerCode; //学校举办者代码
//	protected String univOrganizer; //学校举办者

	/**
	 * 个人空间查看准备
	 * 包括各类主账号的查看，根据不同的账号类型，跳转到合适页面
	 * @return
	 */
	public String toView(){
		AccountType accountType = loginer.getCurrentType();
		viewFlag = 1;//viewFlag = 1表示从用户中心查看我的资料
		if(loginer.getIsPrincipal() ==1){ //单位主账号
			if(accountType.equals(AccountType.MINISTRY)){ //部级主账号
				return "ministry";
			}else if(accountType.equals(AccountType.PROVINCE)){ //省级主账号
				return "province";
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){ //校级主账号
				return "university";
			}else if(accountType.equals(AccountType.DEPARTMENT)){ //院系主账号
				return "department";
			}else if(accountType.equals(AccountType.INSTITUTE)){ //基地主账号
				return "institute";
			}else{
				return INPUT;
			}
		}else{ //子账号的个人空间不是机构信息
			return INPUT;
		}
	}
	
	/**
	 * 个人空间查看
	 * 根据账号类型的不同，进行区别处理
	 * 在这个方法中，由于各类机构的处理差异较大，而各类处理又已经在机构管理中有处理，所以在这里尽量利用机构管理中的方法来处理
	 * @return
	 */
	public String view(){
		AccountType accountType = loginer.getCurrentType();
		entityId = baseService.getBelongIdByLoginer(loginer);//查看自己的机构资料
		if(loginer.getIsPrincipal() ==1){ //单位主账号
			if(accountType.equals(AccountType.MINISTRY) || accountType.equals(AccountType.PROVINCE) || accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){ //部级、省级、校级主账号
				unitService.getViewOfAgency(entityId, jsonMap);
				return SUCCESS;
			}else if(accountType.equals(AccountType.DEPARTMENT)){ //院系主账号
				unitService.getViewOfDepartment(entityId, jsonMap);
				return SUCCESS;
			}else if(accountType.equals(AccountType.INSTITUTE)){ //基地主账号
				unitService.getViewOfInstitute(entityId, jsonMap);
				return SUCCESS;
			}else{
				return INPUT;
			}
		}else{ //子账号的个人空间不是机构信息
			return INPUT;
		}
	}
	
	/**
	 * 供toModify调用，管理机构修改的准备工作
	 * @return
	 */
	@Transactional
	public String toModifyAgency(){
		agency = (Agency) dao.query(Agency.class, entityId);
		if(agency.getDirector()!=null){
			directorId=unitService.getOfficerIdByPersonId(agency.getDirector().getId());
		}
		if(agency.getSdirector()!=null){
			sdirectorId=unitService.getOfficerIdByPersonId(agency.getSdirector().getId());
		}
		if(agency.getSlinkman()!=null){
			slinkmanId=unitService.getOfficerIdByPersonId(agency.getSlinkman().getId());
		}
		if(agency.getOrganizer() !=null && agency.getOrganizer().length()>0 ){
			univOrganizerCode = agency.getOrganizer().substring(0, agency.getOrganizer().indexOf("/"));
			univOrganizer = agency.getOrganizer().substring(agency.getOrganizer().indexOf("/")+1, agency.getOrganizer().length());
		}
		return SUCCESS;
	}
	
	/**
	 * 供toModify调用，院系修改的准备工作
	 * @return
	 */
	@Transactional
	public String toModifyDept(){
		department = (Department) dao.query(Department.class, entityId);
		if(department.getDirector()!=null){
			directorId=unitService.getOfficerIdByPersonId(department.getDirector().getId());
		}
		if(department.getLinkman()!=null){
			linkmanId=unitService.getOfficerIdByPersonId(department.getLinkman().getId());
		}
		return SUCCESS;
	}
	
	/**
	 * 供toModify调用，基地修改的准备工作
	 * @return
	 */
	@Transactional
	public boolean toModifyInst(){
		try{
			institute = (Institute) dao.query(Institute.class, entityId);
			if(institute.getDirector()!=null){
				directorId=unitService.getOfficerIdByPersonId(institute.getDirector().getId());
			}
			if(institute.getLinkman()!=null){
				linkmanId=unitService.getOfficerIdByPersonId(institute.getLinkman().getId());
			}            
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 个人空间修改准备
	 * @return
	 */
	public String toModify(){
		viewFlag = 1;
		entityId = baseService.getBelongIdByLoginer(loginer);
		AccountType accountType = loginer.getCurrentType();
		if(loginer.getIsPrincipal() ==1){ //单位主账号
			if(accountType.equals(AccountType.MINISTRY)){ //部省校级主账号
				//在这里没有用机构管理中的toModify,是觉得需要暂存入session的内容有点多（下同）
				this.toModifyAgency();
				return "ministry";
			}else if(accountType.equals(AccountType.PROVINCE)){ //省级主账号
				//在这里没有用机构管理中的toModify,是觉得需要暂存入session的内容有点多
				this.toModifyAgency();
				return "province";
			}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){ //校级主账号
				//在这里没有用机构管理中的toModify,是觉得需要暂存入session的内容有点多
				this.toModifyAgency();
				return "university";
			}else if(accountType.equals(AccountType.DEPARTMENT)){ //院系主账号
				//在这里没有用机构管理中的toModify,是觉得需要暂存入session的内容有点多
				this.toModifyDept();
				return "department";
			}else if(accountType.equals(AccountType.INSTITUTE)){ //基地主账号
				//在这里没有用机构管理中的toModify,是觉得需要暂存入session的内容有点多
				return (this.toModifyInst()) ? "institute" : INPUT; 
			}else{
				return INPUT;
			}
		}else{ //子账号
			return INPUT;
		}
	}
	
	/**
	 * 个人空间修改
	 * 根据账号类型的不同，进行区别处理
	 * 在这个方法中，由于各类机构的处理差异较大，而各类处理又已经在机构管理中有处理，所以在这里尽量利用机构管理中的方法来处理
	 * @return
	 */
	@Transactional
	public String modify(){
		AccountType accountType = loginer.getCurrentType();
		entityId = baseService.getBelongIdByLoginer(loginer);
		if(loginer.getIsPrincipal() ==1){ //单位主账号
			//if(1 < accountType && accountType < 6) //部、省、校级主账号
			if(accountType.compareTo(AccountType.ADMINISTRATOR)>0 && accountType.compareTo(AccountType.DEPARTMENT)<0){
				unitService.getModifyOfAgency(session, agency, directorId, slinkmanId, sdirectorId, univOrganizerCode, univOrganizer, entityId);
				return SUCCESS;
			}else if(accountType.equals(AccountType.DEPARTMENT)){ //院系
				unitService.getModifyOfDepartment(session, department, directorId, linkmanId, entityId);
				return SUCCESS;
			}else if(accountType.equals(AccountType.INSTITUTE)){//基地
				unitService.getModifyOfInstitute(session, institute, directorId, linkmanId, entityId);
				return SUCCESS;
			}else{
				return INPUT;
			}
		}else{ //子账号
			return INPUT;
		}
	}
	
	//复写父类方法
	public void validateModify(){
		AccountType accountType = loginer.getCurrentType();
		if(accountType.compareTo(AccountType.ADMINISTRATOR)>0 && accountType.compareTo(AccountType.DEPARTMENT)<0){ //部、省、校级主账号
			unitService.getValidateModifyOfAgency(agency, inputValidate, this);
		}else if(accountType.equals(AccountType.DEPARTMENT)){
			unitService.getValidateModifyOfDepartment(department, inputValidate, this);
		}else if(accountType.equals(AccountType.INSTITUTE)){
			unitService.getValidateModifyOfInstitute(institute, inputValidate, this);
		}
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Institute getInstitute() {
		return institute;
	}
	public void setInstitute(Institute institute) {
		this.institute = institute;
	}
	public String getLinkmanId() {
		return linkmanId;
	}
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}
	@Override
	public int subType() {
		return 0;
	}

	@Override
	public Object[] simpleSearchCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String pageName() {
		return null;
	}
}
