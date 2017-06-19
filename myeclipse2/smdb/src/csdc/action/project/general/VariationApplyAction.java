package csdc.action.project.general;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.bean.Department;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.GeneralVariation;
import csdc.bean.Institute;
import csdc.bean.ProjectMember;
import csdc.service.IGeneralService;
import csdc.tool.bean.AccountType;
import csdc.tool.info.GlobalInfo;
import csdc.tool.info.ProjectInfo;

/**
 * 变更项目管理
 * 实现了父类所有的抽象方法及一般项目变更申请特有方法
 * @author 刘雅琴,余潜玉
 */
public class VariationApplyAction extends csdc.action.project.VariationApplyAction {

	private static final long serialVersionUID = 6501329881611241590L;
	//管理人员使用
	private static final String HQL2 = "from GeneralVariation all_vari, GeneralVariation vari left join vari.granted gra, GeneralMember mem " +
		"left join gra.application app left outer join app.subtype sub left join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and vari.granted.id = gra.id and all_vari.granted.id = gra.id ";
	//研究人员使用
	private static final String HQL3 = "from GeneralVariation vari, GeneralVariation all_vari, GeneralMember mem, GeneralGranted gra " +
		"left outer join gra.application app left outer join app.subtype sub left outer join gra.university uni left outer join gra.subtype so " +
		"where app.id = mem.application.id and vari.granted.id = gra.id and all_vari.granted.id = gra.id "; 
	private static final String PAGE_NAME = "generalVariationPage";// 列表页面名称
	private static final String BUSINESS_TYPE = "014";//一般项目变更申请对应的业务类型名称
	private static final String PROJECT_TYPE = "general";//一般项目类别号
	private static final String VARIATION_CLASS_NAME_TYPE = "GeneralVariation";
	private static final int CHECK_APPLICATION_FLAG = 14;//判断一般项目申请是否在管辖范围内的标志位
	private static final int CHECK_GRANTED_FLAG = 21;//判断一般项目立项是否在管辖范围内的标志位
	
	private IGeneralService generalService;
	private GeneralVariation variation;//一般项目变更
	private List<GeneralMember> members;//项目成员
	private List personList;
	private String personId;
//	private List<MembersType> membersType;//是否新建

	private int flag; // 区分添加和修改的标志位（toAdd：0； toModify：1）
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String pageName() {
		return VariationApplyAction.PAGE_NAME;
	}
	public String projectType(){
		return VariationApplyAction.PROJECT_TYPE;
	}
	public String businessType(){
		return VariationApplyAction.BUSINESS_TYPE;
	}
	public int checkApplicationFlag(){
		return VariationApplyAction.CHECK_APPLICATION_FLAG;
	}
	public int checkGrantedFlag(){
		return VariationApplyAction.CHECK_GRANTED_FLAG;
	}
	public String variationClassName(){
		return VariationApplyAction.VARIATION_CLASS_NAME_TYPE;
	}
	public String listHql2() {
		return VariationApplyAction.HQL2;
	}
	public String listHql3() {
		return VariationApplyAction.HQL3;
	}
	
	/**
	 * 准备添加一般项目变更申请
	 * @author 余潜玉
	 * @return SUCCESS获取准备信息成功
	 */
	@SuppressWarnings("unchecked")
	public String toAdd(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		AccountType accountType = loginer.getCurrentType();
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class,projectid);
		if(accountType.compareTo(AccountType.INSTITUTE) > 0){
			this.projectList = this.generalService.getDireProjectListByGrantedId(projectid, baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.equals(AccountType.MINISTRY)){
			this.projectList = this.generalService.getDireProjectListByGrantedId(projectid);
		}
		Map application = ActionContext.getContext().getApplication();
		varListForSelect = new ArrayList<Object>();
		List list = (List)application.get("varItems");
		varListForSelect.addAll(list);
		List<GeneralVariation> vars = this.projectService.getAuditedVariationByGrantedId(projectid);
		for (int i = 0; i < vars.size(); i++) {
			if (vars.get(i).getChangeFee() == 1) {
				if (varListForSelect.size() == 10) {
					varListForSelect.remove(8);
					break;
				}
			}
		}
		
		flag = 0;
		String groupId1 = "file_add";
		String groupId2 = "file_postponementPlan_add";
		uploadService.resetGroup(groupId1);
		uploadService.resetGroup(groupId2);
		//默认项目成员信息
		members = this.generalService.getMemberFetchUnit(granted.getApplication().getId(), granted.getMemberGroupNumber());
//		默认责任人信息
//		members = this.generalService.getDirectorList(granted.getApplication().getId(), granted.getMemberGroupNumber());
		//录入时的处理
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && members.size() > 0){
			for (int i=0; i < members.size(); i++){
				GeneralMember member = members.get(i);
				member = (GeneralMember)this.generalService.setMemberPersonInfoFromMember(member);
				members.set(i, member);
			}
		}
		this.doWithToAdd(granted);
		//是否有待审中检
		int midPending = this.generalService.getPendingMidinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;
		request.setAttribute("midPending", midPending);//是否有待审中检
		return SUCCESS;
	}
	public void validateToAdd() throws Exception{
		this.validateEdit(11);
	}

	@Transactional
	/**
	 * 添加一般项目变更申请
	 * @author 余潜玉
	 */
	public String add(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		variation = new GeneralVariation();
		GeneralGranted granted = (GeneralGranted)dao.query(GeneralGranted.class,projectid);
		granted.addVariation(variation);
		entityId = granted.getApplication().getId();
		variation = (GeneralVariation)this.doWithAddOrModify(variation, 1);
		variation = (GeneralVariation) this.doWithVariation(variation, granted, 1, 1,null);
   		if(submitStatus == 3 && variation.getChangeMember() == 1){//提交则对变更后项目成员信息进行入库处理
			this.generalService.doWithNewMember(granted.getApplication().getId(), variation.getNewMemberGroupNumber());
   		}
		return SUCCESS;
	}
	
	/**
	 * @author 余潜玉
	 * 校验添加变更申请
	 * @throws Exception 
	 */
	public void validateAdd() throws Exception{
		this.validateEdit(12);
	}
	
	/**
	 * 准备修改一般项目变更申请
	 * @author 余潜玉
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String toModify(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		Map session = ActionContext.getContext().getSession();
	    session.put("varId", varId.trim());
	    AccountType accountType = loginer.getCurrentType();
		variation = (GeneralVariation)this.dao.query(GeneralVariation.class, varId.trim());
		if(variation == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_MODIFY_APPLY_NULL);
			return INPUT;
		}else if(variation.getApplicantSubmitStatus() == 3 || variation.getStatus() > 1 || variation.getFinalAuditStatus() == 3){//如果已提交
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SUBMIT_ALREADY);
			return INPUT;
		}
		projectid = variation.getGranted().getId();
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class,projectid);
		defaultSelectCode = "";
		if(accountType.compareTo(AccountType.INSTITUTE) > 0){
			this.projectList = this.generalService.getDireProjectListByGrantedId(projectid, baseService.getBelongIdByLoginer(loginer));
		}else if(accountType.equals(AccountType.MINISTRY)){
			this.projectList = this.generalService.getDireProjectListByGrantedId(projectid);
		}
		//项目成员信息
		if(this.variation.getChangeMember() == 1){//变更项目成员
			defaultSelectCode += "01,";
			members = this.generalService.getMemberFetchUnit(granted.getApplication().getId(), variation.getNewMemberGroupNumber());
		}else{
			//默认项目成员信息
			members = this.generalService.getMemberFetchUnit(granted.getApplication().getId(), granted.getMemberGroupNumber());
		}
		//录入时对项目成员id的处理
		StringBuffer stringBuffer = new StringBuffer();
		if(loginer.getCurrentType().equals(AccountType.MINISTRY) && members.size() > 0){
			for (int i=0; i < members.size(); i++){
				GeneralMember member = members.get(i);
				member = (GeneralMember)this.generalService.setMemberPersonInfoFromMember(member);
				members.set(i, member);
				stringBuffer.append(member.getType()+",");
			}
		}
		type = stringBuffer.toString();
		if(type.trim().length()!=0){
			type = type.substring(0, type.length() - 1);
		}
		Map application = ActionContext.getContext().getApplication();
		varListForSelect = new ArrayList<Object>();
		List list = (List)application.get("varItems");
		varListForSelect.addAll(list);
		List<GeneralVariation> vars = this.projectService.getAuditedVariationByGrantedId(projectid);
		for (int i = 0; i < vars.size(); i++) {
			if (vars.get(i).getChangeFee() == 1) {
				if (varListForSelect.size() == 10) {
					varListForSelect.remove(8);
					break;
				}
			}
		}
		
		flag = 1;
		this.doWithToModify(granted, variation);
		//是否有待审中检
		int midPending = this.generalService.getPendingMidinspectionByGrantedId(this.projectid).size()>0 ? 1 : 0;
		request.setAttribute("midPending", midPending);//是否有待审中检
		return SUCCESS;
	}
	
	/**
	 * 预处理修改一般项目变更申请校验
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void validateToModify() throws Exception{
		this.validateEdit(checkGrantedFlag());
	}
	/**
	 * 修改一般项目变更申请
	 * @author 余潜玉
	 * @return SUCCESS添加变更申请成功
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String modify(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		Map session = ActionContext.getContext().getSession();
		String realVarId = (String)session.get("varId");
		if(varId.equals(realVarId)){
			variation = (GeneralVariation)this.dao.query(GeneralVariation.class, varId.trim());
			if(variation.getApplicantSubmitStatus() == 3 || variation.getStatus() > 1 || variation.getFinalAuditStatus() == 3){//如果已提交
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SUBMIT_ALREADY);
				return INPUT;
			}
		}else{
			return INPUT;
		}
		GeneralGranted granted = (GeneralGranted)dao.query(GeneralGranted.class,projectid);
		variation.setGranted(granted); //设置变更项目
		entityId = granted.getApplication().getId();
		variation = (GeneralVariation)this.doWithAddOrModify(variation, 2);
   		variation = (GeneralVariation) this.doWithVariation(variation, granted, 1, 2, null);
   		if(submitStatus == 3 && variation.getChangeMember() == 1){//提交则对变更后项目成员信息进行入库处理
			this.generalService.doWithNewMember(granted.getApplication().getId(), variation.getNewMemberGroupNumber());
   		}
   		this.dao.modify(variation);
		session.remove("varId");
		return SUCCESS;
	}

	/**
	 * 修改一般项目变更申请校验
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void validateModify() throws Exception{
		this.validateEdit(22);
	}
	/**
	 * 提交一般项目变更申请
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submit(){
		variation = (GeneralVariation)this.dao.query(GeneralVariation.class, varId.trim());
		projectid = variation.getGranted().getId();
		GeneralGranted granted = (GeneralGranted) this.dao.query(GeneralGranted.class, projectid);
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		if(variation.getApplicantSubmitStatus() == 3 || variation.getStatus() > 1 || variation.getFinalAuditStatus() == 3){//如果已提交
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SUBMIT_ALREADY);
			return INPUT;
		}
		variation = (GeneralVariation)this.doWithSubmit(variation);
		if(variation.getChangeMember() == 1){//选择变更项目成员
			this.generalService.doWithNewMember(granted.getApplication().getId(), variation.getNewMemberGroupNumber());
		}
		this.dao.modify(variation);
		return SUCCESS;
	}
	
	/**
	 * 提交一般项目变更校验
	 * @author 余潜玉
	 */
	public void validateSubmit() throws Exception{
		this.validateEdit(3);
	}
	
	/**
	 * 准备添加一般项目变更录入
	 * @author 余潜玉
	 */
	public String toAddResult(){
		varDate = new Date();
		return this.toAdd();
	}
	
	/**
	 * 准备添加一般项目变更录入校验
	 * @author 余潜玉
	 */
	public void validateToAddResult() throws Exception{
		this.validateEditResult(11);
	}
	/**
	 * 一般项目变更结果录入添加
	 * @author 余潜玉、肖雅
	 */
	@Transactional
	public String addResult(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		variation = new GeneralVariation();
		GeneralGranted granted = (GeneralGranted)this.dao.query(GeneralGranted.class,projectid);
		variation.setGranted(granted); //设置变更项目
		variation= (GeneralVariation)this.doWithAddOrModifyResult(variation, 1);
		defaultSelectCode = "";
		personList = (List) this.doWithMember();//根据变更成员信息检索库中成员
		if (personList != null && !personList.isEmpty() && personList.size() >= 1 && (personId == "" || personId.isEmpty())) {
			jsonMap.put("personList", personList);
			return "choose";
		} else {
			if (personId.contains(",")) {
				personId = personId.substring(0, personId.lastIndexOf(",")).trim();
			}
			variation = (GeneralVariation) this.doWithVariation(variation, granted, 2, 1, personId);//处理变更事项
			this.dao.add(variation);
			if(varResult == 2 && varImportedStatus == 3){
				this.generalService.variationProject(variation);
			}
			jsonMap.put("varResultFlag", 1);
			return SUCCESS;
		}
	}
	/**
	 * 录入变更结果校验
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void validateAddResult() throws Exception{
		this.validateEditResult(12);
	}
	/**
	 * 准备修改一般项目变更结果
	 * @author 余潜玉
	 */
	public String toModifyResult(){
		return this.toModify();
	}
	
	/**
	 * 准备修改一般项目变更结果校验
	 * @throws Exception
	 */
	public void validateToModifyResult() throws Exception{
		this.validateEditResult(21);
	}
	
	/**
	 * 一般项目变更录入修改
	 * @author 余潜玉
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public String modifyResult(){
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			this.addFieldError(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		Map session = ActionContext.getContext().getSession();
		String realVarId = (String)session.get("varId");
		if(varId.equals(realVarId)){
			variation = (GeneralVariation)this.dao.query(GeneralVariation.class, varId.trim());
			if(variation.getFinalAuditStatus() == 3){
				this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SUBMIT_ALREADY);
				return INPUT;
			}
		}else{
			return INPUT;
		}
		GeneralGranted granted = (GeneralGranted)this.dao.query(GeneralGranted.class,variation.getGranted().getId());
		variation = (GeneralVariation)this.doWithAddOrModifyResult(variation, 2);
		personList = (List) this.doWithMember();//根据变更成员信息检索库中成员
		if (personList != null && !personList.isEmpty() && personList.size() > 1 && (personId == "" || personId.isEmpty())) {
			jsonMap.put("personList", personList);
			return "choose";
		} else {
			if (personId.contains(",")) {
				personId = personId.substring(0, personId.lastIndexOf(",")).trim();
			}
			variation = (GeneralVariation) this.doWithVariation(variation, granted, 2, 2, personId);//处理变更事项
			this.dao.modify(variation);
			if(varResult == 2 && varImportedStatus == 3){
				this.generalService.variationProject(variation);
			}
			session.remove("varId");
			jsonMap.put("varResultFlag", 1);//修改变更结果成功
			return SUCCESS;
		}
		
	}
	
	/**
	 * 修改一般项目变更结果校验
	 * @throws Exception
	 */
	public void validateModifyResult() throws Exception{
		this.validateEditResult(22);
	}
	
	/**
	 * 一般项目变更录入提交
	 * @author 余潜玉
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String submitResult(){
		projectid = variation.getGranted().getId();
		GeneralGranted granted = (GeneralGranted)this.dao.query(GeneralGranted.class, projectid);
		if(!this.generalService.checkIfUnderControl(loginer, this.generalService.getApplicationIdByGrantedId(projectid).trim(), checkGrantedFlag(), true)){
			jsonMap.put(GlobalInfo.ERROR_INFO,ProjectInfo.ERROR_NOT_IN_SCOPE);
			return INPUT;
		}
		if(variation.getFinalAuditStatus() == 3){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SUBMIT_ALREADY);
			jsonMap.put(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_SUBMIT_ALREADY);
			return INPUT;
		}
		this.doWithSubmitResult(variation);
		if(variation.getChangeMember() == 1){//选择变更项目成员
//			this.generalService.deleteMulMember(granted.getApplication().getId(), variation.getNewMemberGroupNumber());//去重
			this.generalService.refreshMemberSn(granted.getApplication().getId(), variation.getNewMemberGroupNumber());//重新排序
		}
		if(variation.getFinalAuditResult() == 2){
			this.generalService.variationProject(variation);
		}
		return SUCCESS;
	}
	
	/**
	 * 提交一般项目变更结果校验
	 * @throws Exception
	 */
	public void validateSubmitResult() throws Exception{
		this.validateEditResult(3);
	}

	/**
	 * 编辑变更校验
	 * @param type 校验类型：11准备添加;	12添加;	21准备修改; 22修改;3提交
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void validateEdit(int type) throws Exception{
		this.doWithValidateEdit(type);
		if(type == 12 || type == 22){
			this.validateVar(1);
			if (hasErrors()) {
				this.projectList = this.generalService.getDireProjectListByGrantedId(projectid, baseService.getBelongIdByLoginer(loginer));
			}
		}
	}
	/**
	 * 编辑变更结果校验
	 * @param type 校验类型：11:准备添加; 12添加; 21准备修改;	22修改;	3提交
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void validateEditResult(int type) throws Exception{
		this.doWithValidateEditResult(type);
		if(type == 12 || type == 22){
			this.validateVar(2);
		}
		if(type == 3){
			variation = (GeneralVariation)this.generalService.getCurrentVariationByGrantedId(projectid);
			if(variation.getStop() == 1){//中检待处理，不能变更为中止
				if(this.generalService.getPendingMidinspectionByGrantedId(this.projectid).size()>0){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDM_DEALING);
				}
			}
			if(variation.getWithdraw() == 1){//中检待处理，不能变更为撤项
				if(this.generalService.getPendingMidinspectionByGrantedId(this.projectid).size()>0){
					this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_ENDM_DEALING);
				}
			}
		}
	}
	
	/**
	 * 变更之前对于成员的处理
	 */
	public List doWithMember(){
		List personList = new ArrayList();
		for(int i = 0; i < members.size(); i++){
			GeneralMember member = members.get(i);
			if (null != member.getType() && member.getType() == 1 && member.getId() == null) {//新建项目成员
				Map map = new HashMap();
				map.put("personName", member.getMemberName() != null ? member.getMemberName() : null);
				map.put("personType", member.getMemberType() != null ?  member.getMemberType() : null);
				map.put("gender", member.getGender() != null ? member.getGender() : null);
				map.put("idcardType", member.getIdcardType() != null ? member.getIdcardType() : null);
				map.put("idcardNumber", member.getIdcardNumber() != null ? member.getIdcardNumber() : null);
				if (member.getDivisionType() == 2) {//院系
					Department department = dao.query(Department.class, member.getDepartment().getId());
					map.put("divisionName",department.getName());
					map.put("agencyId", (department.getUniversity() != null) ? department.getUniversity().getId() : null);
					map.put("agencyName", (department.getUniversity() != null) ? department.getUniversity().getName() : null);
					map.put("departmentId", member.getDepartment().getId());
				} else {
					Institute institute = dao.query(Institute.class, member.getInstitute().getId());
					map.put("divisionName",institute.getName());
					map.put("agencyId", (institute.getSubjection() != null) ? institute.getSubjection().getId() : null);
					map.put("agencyName", (institute.getSubjection() != null) ? institute.getSubjection().getName() : null);
					map.put("instituteId", member.getInstitute().getId());
				}
				map.put("divisionType", member.getDivisionType());
				map.put("workMonthPerYear", member.getWorkMonthPerYear());
				map.put("specialistTitle", member.getSpecialistTitle());
				if (!(member.getIdcardType().equals("-1") && member.getIdcardType().isEmpty() && member.getIdcardNumber().equals("") && member.getIdcardNumber().isEmpty())) {
					personList = generalService.doWithPerson(map);
				}
			}
		}
		return personList;
	}
	
	/**
	 * 处理变更的公共函数，用于变更的添加与修改以及录入变更数据的添加与修改
	 * @param variation	原变更对象
	 * @param granted	原项目变更对象对应的立项对象
	 * @param type 1:走流程	2：录入
	 * @param flag 1:添加	2：修改
	 * @param entityId 选中的成员PersonId
	 * @return 处理后的变更对象
	 * @author 余潜玉
	 */
	public GeneralVariation doWithVariation(GeneralVariation variation, GeneralGranted granted, int type, int flag, String entityId){
		String selectIssues = "";
		for(int i = 0; i < selectIssue.size(); i++){
			selectIssues += selectIssue.get(i) + ";";
		}
		if(selectIssues.indexOf("01") != -1){//变更项目成员
			if(variation.getChangeMember() == 1){//删除之前的项目成员
				List<ProjectMember> oldMembers = this.generalService.getMember(granted.getApplication().getId(), variation.getNewMemberGroupNumber());
				for (ProjectMember oldMember : oldMembers) {
					dao.delete(oldMember);
				}
			}
			int nowGroupNumber = this.generalService.getMaxGroupNumber(granted.getApplication().getId()) + 1;
			if(type == 1){//走流程时项目成员入库
				for(int i = 0; i < members.size(); i++){
					GeneralMember member = members.get(i);
					member.setApplication(granted.getApplication());
					member.setGroupNumber(nowGroupNumber);
					member.setMemberSn(i + 1);
//					member.setIsDirector(1);
					dao.add(member);
				}
			}else if(type == 2){//录入时项目成员入库
				for(int i = 0; i < members.size(); i++){
					GeneralMember member = members.get(i);
					member.setApplication(granted.getApplication());
					if (null != member.getType() && member.getType() == 1) {//新建项目成员
						member = (GeneralMember)this.generalService.setMemberInfoFromNewMember(member ,entityId);
					} else {
						member = (GeneralMember)this.generalService.setMemberInfoFromMember(member);
						member.setType(0);
					}
					member.setMemberSn(i + 1);
//					member.setIsDirector(1);
					member.setGroupNumber(nowGroupNumber);
					dao.add(member);
				}
			}
//			//复制项目非负责人成员
//			List notDirMembers = this.generalService.getNoDirMembers(granted.getApplication().getId(), granted.getMemberGroupNumber());
//			if(notDirMembers != null && notDirMembers.size() > 0){
//				for(int i = 0 ; i < notDirMembers.size(); i++){
//					try {
//						GeneralMember membertmp1 = (GeneralMember)notDirMembers.get(i);
//						GeneralMember membertmp2 = membertmp1.clone();
//						membertmp2.setId(null);
//						membertmp2.setGroupNumber(nowGroupNumber);
//						this.dao.add(membertmp2);
//					} catch (CloneNotSupportedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
			variation.setChangeMember(1);
			variation.setOldMemberGroupNumber(granted.getMemberGroupNumber());
			variation.setNewMemberGroupNumber(nowGroupNumber);
			generalService.refreshMemberSn(granted.getApplication().getId(), nowGroupNumber);
		}else if(defaultSelectCode.indexOf("01") != -1){//之前变更项目成员，现在不变更
			List<ProjectMember> oldMembers = this.generalService.getMember(granted.getApplication().getId(), variation.getNewMemberGroupNumber());
			for (ProjectMember oldMember : oldMembers) {
				dao.delete(oldMember);
			}
			variation.setChangeMember(2);
			variation.setOldMemberGroupNumber(null);
			variation.setNewMemberGroupNumber(null);
			
		}
		variation = (GeneralVariation)this.setVariationInfo(granted, variation, selectIssues, flag);
		return variation;
	}
	/**
	 * 变更的校验公共部分(用于变更申请的添加与修改以及录入变更结果的添加与修改的校验)
	 * @param type 1:走流程	2：录入
	 * @author 余潜玉
	 * @throws Exception 
	 */
	public void validateVar(int type) throws Exception{
		if(this.selectIssue == null){
			this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_ISSUE_NULL);
		}else{
			int count = this.selectIssue.size();
			for(int i = 0; i < count; i++){
				if(this.selectIssue.get(i).equals("01")){
					if(members == null || members.size() == 0){
						this.addFieldError(GlobalInfo.ERROR_INFO, ProjectInfo.ERROR_VAR_MEMBER_NULL);
					}
					for(int j = 0; j < members.size(); j++){
						GeneralMember member = members.get(j);
						this.validateMember(member, type);
					}
				}
				this.validateVarNotContianMember(selectIssue.get(i));
			}
			this.validateOpinion();
		}
	}
	
	/**
	 * 查看项目责任人详细信息
	 * @author 余潜玉
	 */
	public String viewDir(){
		members = new ArrayList<GeneralMember>();
		GeneralMember member = (GeneralMember)this.dao.query(GeneralMember.class, entityId);
		members.add(0, member);
		return SUCCESS;
	}

	public GeneralVariation getVariation() {
		return variation;
	}
	public void setVariation(GeneralVariation variation) {
		this.variation = variation;
	}
	public List<GeneralMember> getMembers() {
		return members;
	}
	public void setMembers(List<GeneralMember> members) {
		this.members = members;
	}
	public IGeneralService getGeneralService() {
		return generalService;
	}
	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}
	/*public List<MembersType> getMembersType() {
		return membersType;
	}
	public void setMembersType(List<MembersType> membersType) {
		this.membersType = membersType;
	}*/
	public List getPersonList() {
		return personList;
	}
	public void setPersonList(List personList) {
		this.personList = personList;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
}