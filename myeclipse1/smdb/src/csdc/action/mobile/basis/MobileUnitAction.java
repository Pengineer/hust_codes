package csdc.action.mobile.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import csdc.action.mobile.MobileAction;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Institute;
import csdc.bean.Person;
import csdc.service.IUnitService;
import csdc.service.ext.IUnitExtService;
import csdc.tool.bean.AccountType;

/**
 * mobile机构模块
 * @author fengcl
 */
@SuppressWarnings("unchecked")
public class MobileUnitAction extends MobileAction{

	@Autowired
	private IUnitService unitService;
	@Autowired
	private IUnitExtService unitExtService;
	
	private static final long serialVersionUID = 8259102837377107736L;
	private static final String PAGENAME = "mobileUnitPage"; 
	
	private static final String HQL4MPUCOMMON = "select sd.name,ag.name,ag.sname,ag.id,sd.id,ag.code from Agency ag left join ag.sdirector sd ";//部省校机构
	private static final String HQL4DEPRATMENT = "select di.name,de.name,un.name,de.id,di.id,de.code from Department de left join de.director di left join de.university un ";//院系机构
	private static final String HQL4INSTITUTE = "select di.name,ins.name,su.name,ins.id,di.id,ins.code from Institute ins left join ins.director di left join ins.subjection su left join ins.type tt";//基地机构

	private String unitType;//机构类型（管理机构：manage，研究机构：research）
	private String MOEId;// 教育部id(由程序获取)
	private Agency agency; //管理机构对象
	private Person sdirector;// 部门负责人
	private Person slinkman;// 部门联系人
	private Department department;//院系对象
	private Institute institute; //研究基地对象
	
	//用于高级检索
	private String guName; //高级检索单位名称/院系(研究机构)名称
	private String guCode; //高级检索单位代码/院系(研究机构)代码
	private String guDirector; //高级检索中单位负责人名称
	private String guProvince; //高级检索所在省份
	private String guSname; //高级检索管理部门名称/院系(研究机构)所属高校
	private String guSdirector; //高级检索中部门负责人名称


	//隐藏类初始化法
	//MANAGEITEMS：社科管理机构列表
	private static final ArrayList AGENCYITEMS = new ArrayList();
	static{
		AGENCYITEMS.add("部级管理机构#1");
		AGENCYITEMS.add("省级管理机构#2");
		AGENCYITEMS.add("校级管理机构#3");
		AGENCYITEMS.add("院系管理机构#4");
	}
	//RESEARCHITEMS：社科研究基地列表
	private static final ArrayList INSTITUTEITEMS = new ArrayList();
	static{
		INSTITUTEITEMS.add("部级重点研究基地#1");
		INSTITUTEITEMS.add("部省共建重点研究基地#2");
		INSTITUTEITEMS.add("部部重点研究基地#3");
		INSTITUTEITEMS.add("部级重点研究基地#4");
		INSTITUTEITEMS.add("省级重点研究基地#5");
		INSTITUTEITEMS.add("校级重点研究基地#6");
		INSTITUTEITEMS.add("校企合作研究基地#7");
		INSTITUTEITEMS.add("其他研究基地#8"); 
		//INSTITUTEITEMS.add("社科研究基地#1");
	}
	
	/**
	 * 客户端主列表条目
	 * @return
	 */
	public String fetchMenu(){
		AccountType accountType = loginer.getCurrentType();
		Map mainItems = new LinkedHashMap();
		List agencyItems = null;
		List instituteItems = null;
		switch (accountType) {
		case ADMINISTRATOR://管理员
			agencyItems = AGENCYITEMS;
			instituteItems = INSTITUTEITEMS;
			mainItems.put("Agency", agencyItems);
			mainItems.put("Institute", instituteItems);
			break;
		case MINISTRY://部级管理人员
			agencyItems = AGENCYITEMS.subList(1, 4);
			instituteItems = INSTITUTEITEMS;
			mainItems.put("Agency", agencyItems);
			mainItems.put("Institute", instituteItems);
			break;
		case PROVINCE://省级管理人员
			agencyItems = AGENCYITEMS.subList(2, 4);
			mainItems.put("Agency", agencyItems);
			mainItems.put("Institute", instituteItems);
			break;
		case LOCAL_UNIVERSITY:
		case MINISTRY_UNIVERSITY://高校管理人员	
			agencyItems = AGENCYITEMS.subList(3, 4);
			instituteItems = INSTITUTEITEMS;
			mainItems.put("Agency", agencyItems);
			mainItems.put("Institute", instituteItems);
			break;	
		case DEPARTMENT://院系管理人员	
		case INSTITUTE://基地管理人员	
		case EXPERT://外部专家	
		case TEACHER://教师	
		case STUDENT://学生	
			mainItems.put("无法查看机构列表", null);
			break;			
		}
		jsonMap.put("listItem", mainItems);
		return SUCCESS;	
	}
	
	public String simpleSearch(){
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();//登录者所属id
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		if(unitType.equals("Agency")){//管理机构
			switch (listType) {
			case 1://部级
				hql.append(HQL4MPUCOMMON);
				hql.append(" where ag.type = 1");
				this.MOEId = unitExtService.getMOEId(); //获取教育部id
				if(accountType.equals(AccountType.MINISTRY)  && !MOEId.equals(belongUnitId)){ //系统管理员和moe账号可以查看全部，而中心账号只能查看部分部级列表
					hql.append(" and ag.subjection.id =:belongUnitId ");
					parMap.put("belongUnitId", belongUnitId);
				}else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下账号无法查看
					hql.append(" and 1 = 0 ");
				}
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(ag.name) like :keyword or LOWER(ag.sname) like :keyword or LOWER(sd.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by ag.name asc");//默认按照机构名称排序
				break;
			case 2://省级
				hql.append(HQL4MPUCOMMON); 
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){// 系统管理员、部级账号可以查看所有省级列表
				hql.append(" where ag.type = 2");}
				else if (accountType.equals(AccountType.PROVINCE) && loginer.getIsPrincipal() == 1) {// 省级账号且是主账号
					hql.append(" and ag.subjection.id =:belongUnitId ");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.compareTo(AccountType.PROVINCE) > 0){//省级以下账号无法查看
					hql.append(" and 1 = 0 ");
				}
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(ag.name) like :keyword or LOWER(ag.sname) like :keyword or LOWER(sd.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by ag.name asc");//默认按照机构名称排序
				break;
			case 3://高校
				hql.append(HQL4MPUCOMMON);
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){// 系统管理员、部级账号可以查看所有高校
					hql.append(" where (ag.type=3 or ag.type=4)");
				}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(3)只能管理本省地方高校(type=4)；
					hql.append(" where ag.subjection.id =:belongUnitId and ag.type=4");
					parMap.put("belongUnitId", belongUnitId);
				}else{////高校及下级单位(accountType > 3)无法查看
					hql.append(" where 1 = 0");
				}
				if(!keyword.isEmpty()){
					hql.append(" and (LOWER(ag.name) like :keyword or LOWER(ag.sname) like :keyword or LOWER(sd.name) like :keyword)");
					parMap.put("keyword", "%" + keyword + "%");
				}
				hql.append(" order by ag.name asc");//默认按照机构名称排序
				break;
			case 4://院系
				hql.append(HQL4DEPRATMENT);
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){ //系统管理员或部级管理员
					hql.append(" where 1=1");
				}else if(accountType.equals(AccountType.PROVINCE) ){//省级账号(省内地方高校的院系)
					hql.append(" where ag.subjection.id=:belongUnitId and ag.type=4");
					parMap.put("belongUnitId", belongUnitId);
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校账号
					hql.append(" where de.university.id=:belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				}else{//部级及其他
					hql.append(" where 1=0");
				}
				if(!keyword.isEmpty()){
					parMap.put("keyword", "%" + keyword + "%");
					hql.append(" and (LOWER(di.name) like :keyword or LOWER(de.name) like :keyword or LOWER(un.name) like :keyword)");
				}
				hql.append(" order by de.name asc");//默认按照机构名称排序
				break;
			}
		}else if(unitType.equals("Institute")){//研究机构
			//switch(listType){
			/*case 1://基地
				hql.append(HQL4INSTITUTE);
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//系统管理员或部级管理员
					hql.append(" where 1=1");
				}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(本省地方高校研究基地或本省省及研究基地
					hql.append(" left join ins.type typ where ag.subjection.id=:belongUnitId and (ag.type=4 or typ.code='02' or typ.code='03')");
					parMap.put("belongUnitId", belongUnitId);
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//学校管理员(位于本校的研究机构）
					hql.append(" where ins.subjection.id=:belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				}else{
					hql.append(" where 1=0");
				}
				if(!keyword.isEmpty()){
					parMap.put("keyword", "%" + keyword + "%");
					hql.append(" and (LOWER(ins.name) like :keyword or LOWER(di.name) like :keyword or LOWER(su.name) like :keyword)");
				}
				hql.append(" order by ins.name asc");//默认按照基地名称排序
				break;*/
			//不同的研究基地获取内容
				hql.append(HQL4INSTITUTE);
				String instTypeString = "0"+listType.toString();
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//系统管理员或部级管理员
					hql.append(" where 1=1 and tt.standard = 'researchAgencyType' and tt.code = :instType");			
					parMap.put("instType", instTypeString);
				}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(本省地方高校研究基地或本省省及研究基地
					hql.append(" left join ins.type typ where ag.subjection.id=:belongUnitId and (ag.type=4 or typ.code='02' or typ.code='03')and tt.standard = 'researchAgencyType' and tt.code = :instType");
					parMap.put("belongUnitId", belongUnitId);
					parMap.put("instType", instTypeString);
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//学校管理员(位于本校的研究机构）
					hql.append(" where ins.subjection.id=:belongUnitId and tt.standard = 'researchAgencyType' and tt.code = :instType");
					parMap.put("belongUnitId", belongUnitId);
					parMap.put("instType", instTypeString);
				}else{
					hql.append(" where 1=0");
				}
				if(!keyword.isEmpty()){
					parMap.put("keyword", "%" + keyword + "%");
					hql.append(" and (LOWER(ins.name) like :keyword or LOWER(di.name) like :keyword or LOWER(su.name) like :keyword)");
				}
				hql.append(" order by ins.name asc");//默认按照基地名称排序
		}
		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	public String advSearch(){
		AccountType accountType = loginer.getCurrentType();
		String belongUnitId = loginer.getCurrentBelongUnitId();//belongUnitId：subjectionId 检索范围
		StringBuffer hql = new StringBuffer();
		HashMap parMap = new HashMap();
		if(unitType.equals("Agency")){//管理机构
			switch (listType) {
			case 1://部级
				hql.append(HQL4MPUCOMMON);
				hql.append(" left join ag.province pv");
				hql.append(" where ag.type = 1");
				this.MOEId = unitExtService.getMOEId(); //获取教育部id
				if(accountType.equals(AccountType.MINISTRY)  && !MOEId.equals(belongUnitId)){ //系统管理员和moe账号可以查看全部，而中心账号只能查看部分部级列表
					hql.append(" and ag.subjection.id =:belongUnitId ");
					parMap.put("belongUnitId", belongUnitId);
				}else if(accountType.compareTo(AccountType.MINISTRY) > 0){//部级以下账号无法查看
					hql.append(" and 1 = 0 ");
				}
				if(null != guName && !guName.isEmpty()){ //名称
					hql.append(" and LOWER(ag.name) like :agName");
					parMap.put("agName", '%'+ guName.trim() +'%');
				}
				if(null != guCode && !guCode.isEmpty()){ //单位代码
					hql.append(" and LOWER(ag.code) like :agCode");
					parMap.put("agCode", '%'+ guCode.trim() +'%');
				}
				if(null != guDirector && !guDirector.isEmpty()){ //负责人
					hql.append(" and LOWER(pe.name) like :director");
					parMap.put("director", '%'+ guDirector.trim() +'%');
				}
				if(null != guProvince && !guProvince.isEmpty()){ //所在省
					hql.append(" and LOWER(pv.name) like :provinceName");
					parMap.put("provinceName", '%' + guProvince.trim() + '%');
				}
				if(null != guSname && !guSname.isEmpty()){ //部门名称
					hql.append(" and ag.sname like :sName");
					parMap.put("sName", '%'+ guSname.trim() +'%');
				}
				if(null != guSdirector && !guSdirector.isEmpty()){ //部门负责人
					hql.append(" and LOWER(pr.name) like :sDirector");
					parMap.put("sDirector", '%'+ guSdirector +'%');
				}
				hql.append(" order by ag.name ");
				break;
			case 2://省级
				hql.append(HQL4MPUCOMMON); 
				hql.append(" left join ag.province pv");
				hql.append(" where ag.type = 2");
				if (accountType.equals(AccountType.PROVINCE) && loginer.getIsPrincipal() == 1) {// 省级账号且是主账号
					hql.append(" and ag.subjection.id =:belongUnitId ");
					parMap.put("belongUnitId", belongUnitId);
				} else if (accountType.compareTo(AccountType.PROVINCE) > 0){//省级以下账号无法查看
					hql.append(" and 1 = 0 ");
				}
				if(null != guName && !guName.isEmpty()){ //名称
					hql.append(" and LOWER(ag.name) like :agName");
					parMap.put("agName", '%'+ guName.trim() +'%');
				}
				if(null != guCode && !guCode.isEmpty()){ //单位代码
					hql.append(" and LOWER(ag.code) like :agCode");
					parMap.put("agCode", '%'+ guCode.trim() +'%');
				}
				if(null != guDirector && !guDirector.isEmpty()){ //负责人
					hql.append(" and LOWER(pe.name) like :director");
					parMap.put("director", '%'+ guDirector.trim() +'%');
				}
				if(null != guProvince && !guProvince.isEmpty()){ //所在省
					hql.append(" and LOWER(pv.name) like :provinceName");
					parMap.put("provinceName", '%' + guProvince.trim() + '%');
				}
				if(null != guSname && !guSname.isEmpty()){ //部门名称
					hql.append(" and ag.sname like :sName");
					parMap.put("sName", '%'+ guSname.trim() +'%');
				}
				if(null != guSdirector && !guSdirector.isEmpty()){ //部门负责人
					hql.append(" and LOWER(pr.name) like :sDirector");
					parMap.put("sDirector", '%'+ guSdirector +'%');
				}
				hql.append(" order by ag.name ");
				break;
			case 3://校级
				hql.append(HQL4MPUCOMMON);
				hql.append(" left join ag.province pv");
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){// 系统管理员、部级账号可以查看所有高校
					hql.append(" where (ag.type=3 or ag.type=4)");
				}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(3)只能管理本省地方高校(type=4)；
					hql.append(" where ag.subjection.id =:belongUnitId and ag.type=4");
					parMap.put("belongUnitId", belongUnitId);
				}else{//高校及下级单位(accountType > 3)无法查看
					hql.append(" where 1 = 0");
				}
				//高级检索条件拼接
				if(null != guName && !guName.isEmpty()){ //名称
					hql.append(" and LOWER(ag.name) like :agName");
					parMap.put("agName", '%'+ guName.trim() +'%');
				}
				if(null != guCode && !guCode.isEmpty()){ //单位代码
					hql.append(" and LOWER(ag.code) like :agCode");
					parMap.put("agCode", '%'+ guCode.trim() +'%');
				}
				if(null != guDirector && !guDirector.isEmpty()){ //负责人
					hql.append(" and LOWER(pe.name) like :director");
					parMap.put("director", '%'+ guDirector.trim() +'%');
				}
				if(null != guProvince && !guProvince.isEmpty()){ //所在省
					hql.append(" and LOWER(pv.name) like :provinceName");
					parMap.put("provinceName", '%' + guProvince.trim() + '%');
				}
				if(null != guSname && !guSname.isEmpty()){ //部门名称
					hql.append(" and ag.sname like :sName");
					parMap.put("sName", '%'+ guSname.trim() +'%');
				}
				if(null != guSdirector && !guSdirector.isEmpty()){ //部门负责人
					hql.append(" and LOWER(pr.name) like :sDirector");
					parMap.put("sDirector", '%'+ guSdirector +'%');
				}
				hql.append(" order by ag.name ");
				break;
			case 4://院系
				hql.append(HQL4DEPRATMENT);
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){ //系统管理员或部级管理员
					hql.append(" where 1=1");
				}else if(accountType.equals(AccountType.PROVINCE)){//省级账号(省内地方高校的院系)
					hql.append(" where ag.subjection.id=:belongUnitId and ag.type=4");
					parMap.put("belongUnitId", belongUnitId);
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//高校账号
					hql.append(" where de.university.id=:belongUnitId");
					parMap.put("belongUnitId", belongUnitId);
				}else{//部级及其他
					hql.append(" where 1=0");
				}
				if(null != guName && !guName.trim().isEmpty()){//院系名称
					hql.append(" and LOWER(de.name) like :iName");
					parMap.put("iName", '%'+ guName.trim() +'%');
				}
				if(null != guCode && !guCode.trim().isEmpty()){//院系代码
					hql.append(" and LOWER(de.code) like :iCode");
					parMap.put("iCode", '%'+ guCode.trim() +'%');
				}
				if(null != guSname && !guSname.isEmpty()){ //院系所属高校
					hql.append(" and LOWER(ag.name) like :iUniversity");
					parMap.put("iUniversity", '%'+ guSname.trim() +'%');
				}
				if(null != guSdirector && !guSdirector.isEmpty()){ //院系负责人
					hql.append(" and LOWER(pe.name) like :iDirectorName");
					parMap.put("iDirectorName", '%'+ guSdirector.trim() +'%');
				}
				hql.append(" order by de.name ");
				break;
			}
		}else if(unitType.equals("Institute")){//研究机构
				hql.append(HQL4INSTITUTE);
				hql.append(" left join su.province so");
				String instTypeString = "0"+listType.toString();
				if(accountType.equals(AccountType.ADMINISTRATOR) || accountType.equals(AccountType.MINISTRY)){//系统管理员或部级管理员
					hql.append(" where 1=1 and tt.standard = 'researchAgencyType' and tt.code = :instType");
					parMap.put("instType", instTypeString);
				}else if(accountType.equals(AccountType.PROVINCE)){//省级管理员(本省地方高校研究基地或本省省及研究基地
					hql.append(" left join ins.type typ where ag.subjection.id=:belongUnitId and (ag.type=4 or typ.code='02' or typ.code='03') and tt.standard = 'researchAgencyType' and tt.code = :instType");
					parMap.put("belongUnitId", belongUnitId);
					parMap.put("instType", instTypeString);
				}else if(accountType.equals(AccountType.MINISTRY_UNIVERSITY) || accountType.equals(AccountType.LOCAL_UNIVERSITY)){//学校管理员(位于本校的研究机构）
					hql.append(" where ins.subjection.id=:belongUnitId and tt.standard = 'researchAgencyType' and tt.code = :instType");
					parMap.put("belongUnitId", belongUnitId);
					parMap.put("instType", instTypeString);
				}else{
					hql.append(" where 1=0");
				}
				//高级检索条件
				if(null != guName && !guName.trim().isEmpty()){//研究机构名称
					hql.append(" and LOWER(ins.name) like :iName");
					parMap.put("iName", '%'+ guName.trim() +'%');
				}
				if(null != guCode && !guCode.trim().isEmpty()){//研究机构代码
					hql.append(" and LOWER(ins.code) like :code");
					parMap.put("code", '%'+ guCode.trim() +'%');
				}
				if(null != guSname && !guSname.isEmpty()){ //研究机构所属高校
					hql.append(" and LOWER(su.name) like :university");
					parMap.put("university", '%'+ guSname.trim() +'%');
				}
				if(null != guProvince && !guProvince.isEmpty()){ //所在省
					hql.append(" and LOWER(so.name) like :provinceName");
					parMap.put("provinceName", '%' + guProvince.trim() + '%');
				}
				if(null != guSdirector && !guSdirector.isEmpty()){ //研究机构负责人
					hql.append(" and LOWER(di.name) like :directorName");
					parMap.put("iDirectorName", '%'+ guSdirector.trim() +'%');
				}
				hql.append(" order by ins.name ");
		}
		//调用公共接口
		search(hql.toString(), parMap);
		return SUCCESS;
	}
	
	/**
	 * 查看管理机构详细信息
	 * @return
	 */
	@Transactional
	public String view(){
		Map dataMap = new HashMap(); 
		if(unitType.equals("Agency") && listType == 4){//院系管理机构
			department = (Department) dao.query(Department.class, entityId);
			unitService.getViewOfDepartment(entityId, dataMap);
			//[基本信息 4]:院系名称，院系代码，院系负责人，上级管理部门
			String uName = (department.getName() == null) ? "" : department.getName();
			dataMap.put("uName", uName);//院系名称
			String uCode = (department.getCode() == null) ? "" : department.getCode();
			dataMap.put("uCode", uCode);//院系代码
			String directorName =(department.getDirector() == null) ? "" : department.getDirector().getName();
			dataMap.put("directorName", directorName);//院系负责人
			String subjectionName =(department.getUniversity() == null)? "" : department.getUniversity().getName();
			dataMap.put("subjectionName", subjectionName);//所属高校
			//[联系信息 7]：院系联系人，院系电话，院系传真，邮编，通信地址，院系邮箱，院系主页
			String linkmanName = (department.getLinkman() == null) ? "" : department.getLinkman().getName();
			dataMap.put("linkmanName", linkmanName);//院系联系人
			String uPhone = (department.getPhone() == null) ? "" : department.getPhone();
			dataMap.put("uPhone", uPhone);//院系电话
			String uFax = (department.getFax() == null) ? "" : department.getFax();
			dataMap.put("uFax", uFax);//院系传真
			String uEmail = (department.getEmail() == null) ? "" : department.getEmail();
			dataMap.put("uEmail", uEmail);//院系邮箱
			String uHomepage = (department.getHomepage() == null) ? "" : department.getHomepage();
			dataMap.put("uHomepage", uHomepage);//院系主页
			if(department.getAddressIds()!=null){
				List list = unitService.getAddress(department);
				dataMap.put("uAddress", list.get(0));//通信地址
				dataMap.put("uPostcode", list.get(1));//院系邮编
			}
		}else if(unitType.equals("Institute")){//基地研究机构
			institute = (Institute) dao.query(Institute.class, entityId);
			unitService.getViewOfInstitute(entityId, dataMap);
			//[基本信息 7]:研究基地名称，研究基地代码，研究基地类型，研究基地负责人，上级管理部门，研究活动类型,所属学科门类
			String uName = (institute.getName() == null) ? "" : institute.getName();
			dataMap.put("uName", uName);//研究基地名称
			String uCode = (institute.getCode() == null) ? "" : institute.getCode();
			dataMap.put("uCode", uCode);//研究基地代码
			String instType =(institute.getType()==null) ? "" : institute.getType().getName();
			dataMap.put("uType", instType);//研究基地类型
			if(institute.getResearchActivityType() !=null ){
				String researchTypeName = unitExtService.getNamesByIds(institute.getResearchActivityType().getId());
				dataMap.put("researchType", researchTypeName);
			}else{
				dataMap.put("researchType", "");// 研究活动类型
			}
			String discipline = (institute.getDisciplineType() == null) ? "" : institute.getDisciplineType();
			dataMap.put("discipline", discipline);//所属学科门类
			String subjectionName =(institute.getSubjection() == null)? "" : institute.getSubjection().getName();
			dataMap.put("subjectionName", subjectionName);//上级管理部门
			String directorName = (institute.getDirector() == null) ? "" : institute.getDirector().getName();
			dataMap.put("directorName", directorName);//研究基地负责人
			//[联系信息 7]：联系人，电话，传真，邮箱，主页，邮政编码 ，通信地址
			String linkmanName = (institute.getLinkman() == null)? "" : institute.getLinkman().getName();
			dataMap.put("linkmanName", linkmanName);//联系人
			String uPhone = (institute.getPhone() == null) ? "" : institute.getPhone();
			dataMap.put("uPhone", uPhone);//电话
			String uFax = (institute.getFax() == null) ? "" : institute.getFax();
			dataMap.put("uFax", uFax);//传真
			String uEmail = (institute.getEmail() == null) ? "" : institute.getEmail();
			dataMap.put("uEmail", uEmail);//邮箱
			String uHomepage = (institute.getHomepage() == null) ? "" : institute.getHomepage();
			dataMap.put("uHomepage", uHomepage);//主页
			if(institute.getAddressIds()!=null){
				List list = unitService.getAddress(institute);
				dataMap.put("uAddress", list.get(0));//通信地址
				dataMap.put("uPostcode", list.get(1));//院系邮编
			}
		}else {//部级，省级，校级管理部门
			agency = (Agency) dao.query(Agency.class, entityId);
			//[基本信息 7]：单位名称，单位代码，单位类型，单位负责人，上级管理部门，所在省，所在市
			unitService.getViewOfAgency(entityId, dataMap);

			String uName = (agency.getName() == null)? "":agency.getName();
			dataMap.put("uName", uName);//单位名称
			String uCode = (agency.getCode() == null)? "":agency.getCode();
			dataMap.put("uCode", uCode);//单位代码
			String phone = (agency.getPhone() == null)? "":agency.getPhone();
			dataMap.put("phone", phone);
			String fax = (agency.getFax() == null)? "":agency.getFax();
			dataMap.put("fax", fax);
			String email = (agency.getEmail() == null)? "":agency.getEmail();
			dataMap.put("email", email);
			String introduction = (agency.getIntroduction() == null)? "":agency.getIntroduction();
			dataMap.put("introduction", introduction);
			String HomePage = (agency.getHomepage() == null)? "":agency.getHomepage();
			dataMap.put("HomePage", HomePage);
			//机构类型，将数字转化为名称字符串
			String[] str = {"部级","省级","部属高校","地方高校"};
			if(agency.getType()>0 && agency.getType()<5){//机构类型
				dataMap.put("uType", str[agency.getType()-1]);
			}else{
				dataMap.put("uType", "");
			}
			String directorName = (agency.getDirector() == null)? "":agency.getDirector().getName();
			dataMap.put("directorName", directorName);////单位负责人
			String subjectionName = (agency.getSubjection() == null)? "":agency.getSubjection().getName();
			dataMap.put("subjectionName", subjectionName);//上级管理部门名称
			String provinceName = (agency.getProvince() == null)? "":agency.getProvince().getName();
			dataMap.put("provinceName",provinceName);//所在省
			String cityName = (agency.getCity() == null)? "":agency.getCity().getName();
			dataMap.put("cityName", cityName);//所在市
			//[社科管理部门 9]:部门名称，部门负责人，部门联系人，部门电话，部门传真，部门邮编 ，部门地址，部门邮箱，部门主页；
			String sName = (agency.getSname() == null)? "":agency.getSname();
			dataMap.put("sName", sName);//社科管理部门名称
			String sDirectorName = (agency.getSdirector() == null)? "":agency.getSdirector().getName();
			dataMap.put("sDirectorName", sDirectorName);// 社科管理部门负责人
			String sLinkmanName = (agency.getSlinkman() == null)? "":agency.getSlinkman().getName();
			dataMap.put("sLinkmanName", sLinkmanName);//社科管理部门联系人
			String sPhone = (agency.getSphone() == null)? "":agency.getSphone();
			dataMap.put("sPhone", sPhone);//社科管理部门电话
			String sFax = (agency.getSfax() == null)? "":agency.getSfax();
			dataMap.put("sFax", sFax);//社科管理部门传真
			String sEmail = (agency.getSemail() == null)? "":agency.getSemail();
			dataMap.put("sEmail", sEmail);//社科管理部门邮箱
			String sHomepage = (agency.getShomepage() == null)? "":agency.getShomepage();
			dataMap.put("sHomepage", sHomepage);//社科管理部门主页
			String sPostcode = (agency.getCode() == null)? "":agency.getCode();
			dataMap.put("sPostcode", sPostcode);
			if(agency.getSaddressIds()!=null){
				List list = unitService.getSAddress(agency);
				dataMap.put("uAddress", list.get(0));//通信地址
				dataMap.put("uPostcode", list.get(1));//院系邮编
			}
			String fName = (agency.getFname() == null)? "":agency.getFname();
			dataMap.put("fName", fName);//财务部门名称
			String fDirectorName = (agency.getFdirector() == null)? "":agency.getFdirector();
			dataMap.put("fDirectorName", fDirectorName);
			String fLinkmanName = (agency.getFlinkman() == null)? "":agency.getFlinkman();
			dataMap.put("fLinkmanName", fLinkmanName);
			String fPhone = (agency.getFphone() == null)? "":agency.getFphone();
			dataMap.put("fPhone", fPhone);
			String fFax = (agency.getFfax() == null)? "":agency.getFfax();
			dataMap.put("fFax", fFax);
			String bankIds = agency.getBankIds();
			List bankList = dao.query("select ba from BankAccount ba where ba.ids = ? order by ba.sn asc ", bankIds);
			dataMap.put("bankList", bankList);
		}
		jsonMap.put("laData", dataMap);
		return SUCCESS;
	}
	
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getUnitType() {
		return unitType;
	}
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
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
	public String getGuName() {
		return guName;
	}
	public void setGuName(String guName) {
		this.guName = guName;
	}
	public String getGuCode() {
		return guCode;
	}
	public void setGuCode(String guCode) {
		this.guCode = guCode;
	}
	public String getGuDirector() {
		return guDirector;
	}
	public void setGuDirector(String guDirector) {
		this.guDirector = guDirector;
	}
	public String getGuProvince() {
		return guProvince;
	}
	public void setGuProvince(String guProvince) {
		this.guProvince = guProvince;
	}
	public String getGuSname() {
		return guSname;	
	}
	public void setGuSname(String guSname) {
		this.guSname = guSname;
	}
	public String getGuSdirector() {
		return guSdirector;
	}
	public void setGuSdirector(String guSdirector) {
		this.guSdirector = guSdirector;
	}
	@Override
	public String pageName() {
		return PAGENAME;
	}
}
