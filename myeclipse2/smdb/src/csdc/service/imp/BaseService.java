package csdc.service.imp;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import oracle.net.aso.i;

import org.csdc.domain.fm.ThirdUploadForm;
import org.csdc.service.imp.DmssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import csdc.action.BaseAction;
import csdc.bean.Academic;
import csdc.bean.Account;
import csdc.bean.AccountRole;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Log;
import csdc.bean.Message;
import csdc.bean.News;
import csdc.bean.Notice;
import csdc.bean.Officer;
import csdc.bean.Passport;
import csdc.bean.Person;
import csdc.bean.Role;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.HibernateBaseDao;
import csdc.dao.IBaseDao;
import csdc.dao.IHibernateBaseDao;
import csdc.dao.SystemOptionDao;
import csdc.service.IBaseService;
import csdc.service.IUploadService;
import csdc.tool.SpringBean;
import csdc.tool.bean.AccountType;
import csdc.tool.bean.AuditInfo;
import csdc.tool.bean.LoginInfo;
import csdc.tool.info.GlobalInfo;

@SuppressWarnings("unchecked")
public class BaseService implements IBaseService {
	
	private IBaseDao baseDao;
	@Autowired
	protected IHibernateBaseDao dao;
	@Autowired
	protected SystemOptionDao soDao;

	@Autowired
	protected DmssService dmssService;
	
	//************************系统选项表相关************************/
	/**
	 * 根据指定standard, code获取其直接子节点, map的key为id
	 * @param standard
	 * @param code
	 * @return	map (id与name的映射)
	 */
	@Transactional
	public Map<String,String> getSystemOptionMap(String standard, String code){
		Map<String,String> map = null;
		List<SystemOption> systemOptionList = soDao.queryChildren(soDao.query(standard, code));
		if(systemOptionList.size() > 0){
			map = new LinkedHashMap<String, String>();
			for (SystemOption systemOption : systemOptionList) {
				map.put(systemOption.getId(), systemOption.getName());
			}
		}
		return map;
	}
	
	/**
	 * 根据指定standard, code获取其直接子节点, map的key为name
	 * @param standard
	 * @param code
	 * @return	map (name与name的映射)
	 */
	@Transactional
	public Map<String,String> getSystemOptionMapAsName(String standard, String code){
		Map<String,String> map = null;
		List<SystemOption> systemOptionList = soDao.queryChildren(soDao.query(standard, code));
		if(systemOptionList.size() > 0){
			map = new LinkedHashMap<String, String>();
			for (SystemOption systemOption : systemOptionList) {
				map.put(systemOption.getName(), systemOption.getName());
			}
		}
		return map;
	}
	
	/**
	 * 根据指定id获取其直接子节点, map的key为id
	 * @param parentId
	 * @return	map (id与name的映射)
	 */
	@Transactional
	public Map<String,String> getChildrenMapByParentId(String parentId){
		Map<String,String> map = null;
		SystemOption so = (SystemOption) dao.query(SystemOption.class, parentId);
		List<SystemOption> systemOptionList = soDao.queryChildren(so);
		if(systemOptionList.size() > 0){
			map = new LinkedHashMap<String, String>();
			for (SystemOption systemOption : systemOptionList) {
				map.put(systemOption.getId(), systemOption.getName());
			}
		}
		return map;
	}
	
	/**
	 * 根据指定id获取其直接子节点, map的key为code/name
	 * @param parentId
	 * @return	map (code/name 到 code/name 的映射)
	 */
	@Transactional
	public Map<String,String> getCodeNameMapByParentId(String parentId) {
		Map<String,String> map = null;
		SystemOption so = (SystemOption) dao.query(SystemOption.class, parentId);
		List<SystemOption> systemOptionList = soDao.queryChildren(so);
		if(systemOptionList.size() > 0){
			map = new LinkedHashMap<String, String>();
			for (SystemOption systemOption : systemOptionList) {
				map.put(systemOption.getCode()+"/"+systemOption.getName(), systemOption.getCode()+"/"+systemOption.getName());
			}
		}
		return map;
	}
	
	//*****************************end*****************************/
	
	
	//***************************业务相关**************************/

	/**
	 * 判断某个实体是否在当前账号的管辖范围之内
	 * @param loginer当前登录对象
	 * @param id待判定的实体ID
	 * @param idType待判定的实体类别
	 *  1--部级机构，已知条件，ministryUnitId
	 *  2--省级机构，已知条件，provinceUnitId
	 *  3--校级机构，已知条件，universityUnitId
	 *  4--高校院系，已知条件，departmentUnitId
	 *  5--研究基地，已知条件，instituteUnitId
	 *  6--部级人员，已知条件，ministryOfficerId
	 *  7--省级人员，已知条件，provinceOfficerId
	 *  8--校级人员，已知条件，universityOfficerUnitId
	 *  9--院系人员，已知条件，departmentOfficerUnitId
	 * 10--基地人员，已知条件，instituteOfficerUnitId
	 * 11--外部专家，已知条件，expertId
	 * 12--高校教师，已知条件，teacherId
	 * 13--高校学生，已知条件，studentId
	 * 14--一般项目申报数据，已知条件，applicationId
	 * 15--论文数据，已知条件，productId
	 * 16--著作数据，已知条件，productId
	 * 17--研究报告数据，已知条件，productId
	 * 18--奖励数据，已知条件，awardId
	 * 19--基地项目申报数据，已知条件，applicationId
	 * 20--后期资助项目申报数据，已知条件，applicationId
	 * 21--一般项目立项数据，已知条件，applicationId
	 * 22--基地项目立项数据，已知条件，applicationId
	 * 23--后期资助项目立项数据，已知条件，applicationId
	 * 24--重大攻关项目招标数据，已知条件，applicationId
	 * 25--重大攻关项目中标数据，已知条件，applicationId
	 * 26--重大攻关项目选题数据，已知条件，applicationId
	 * 27--委托应急课题申报数据，已知条件，applicationId
	 * 28--委托应急课题立项数据，已知条件，applicationId
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfUnderControl(LoginInfo loginer, String id, int idType, boolean containSelf) {
		if (loginer == null || id == null || id.isEmpty()) {//  如果判定者或者被判定者为空，则恒为false
			return false;
		} else {
			switch (idType) {
				case 1: {// 部级机构
					return this.checkIfMinistryUnitUnderControl(loginer, id, containSelf);
				}
				case 2: {// 省级机构
					return this.checkIfProvinceUnitUnderControl(loginer, id, containSelf);
				}
				case 3: {// 校级机构
					return this.checkIfUniversityUnitUnderControl(loginer, id, containSelf);
				}
				case 4: {// 高校院系
					return this.checkIfDepartmentUnitUnderControl(loginer, id, containSelf);
				}
				case 5: {// 研究基地
					return this.checkIfInstituteUnitUnderControl(loginer, id, containSelf);
				}
				case 6: {// 部级管理人员
					return this.checkIfMinistryOfficerUnderControl(loginer, id, containSelf);
				}
				case 7: {// 省级管理人员
					return this.checkIfProvinceOfficerUnderControl(loginer, id, containSelf);
				}
				case 8: {// 校级管理人员
					return this.checkIfUniversityOfficerUnderControl(loginer, id, containSelf);
				}
				case 9: {// 院系管理人员
					return this.checkIfDepartmentOfficerUnderControl(loginer, id, containSelf);
				}
				case 10: {// 基地管理人员
					return this.checkIfInstituteOfficerUnderControl(loginer, id, containSelf);
				}
				case 11: {// 外部专家
					return this.checkIfExpertUnderControl(loginer, id, containSelf);
				}
				case 12: {// 高校教师
					return this.checkIfTeacherUnderControl(loginer, id, containSelf);
				}
				case 13: {// 高校学生
					return this.checkIfStudentUnderControl(loginer, id, containSelf);
				}
				case 14: {// 一般项目申报数据
					return this.checkIfGenAppUnderControl(loginer, id, containSelf);
				}
				case 15: {// 论文
					//return this.checkIfPaperUnderControl(loginer, id,containSelf);
					return this.checkIfProductUnderControl(loginer, id,containSelf);
				}
				case 16: {// 著作
					//return this.checkIfBookUnderControl(loginer, id,containSelf);
					return this.checkIfProductUnderControl(loginer, id,containSelf);
				}
				case 17: {// 研究咨询报告
					//return this.checkIfConsultationUnderControl(loginer, id,containSelf);
					return this.checkIfProductUnderControl(loginer, id,containSelf);
				}
				case 18: {// 奖励
					return this.checkIfAwardAppUnderControl(loginer, id,containSelf);
				}
				case 19: {// 基地项目申报数据
					return this.checkIfInstpAppUnderControl(loginer, id, containSelf);
				}
				case 20: {// 后期资助项目申报数据
					return this.checkIfPostAppUnderControl(loginer, id, containSelf);
				}
				case 21: {// 一般项目立项数据
					return this.checkIfGenGrantedUnderControl(loginer, id, containSelf);
				}
				case 22: {// 基地项目立项数据
					return this.checkIfInstpGrantedUnderControl(loginer, id, containSelf);
				}
				case 23: {// 后期资助项目立项数据
					return this.checkIfPostGrantedUnderControl(loginer, id, containSelf);
				}
				case 24: {// 重大攻关项目招标数据
					return this.checkIfKeyAppUnderControl(loginer, id, containSelf);
				}
				case 25: {// 重大攻关项目中标数据
					return this.checkIfKeyGrantedUnderControl(loginer, id, containSelf);
				}
				case 26: {// 重大攻关项目选题数据
					return this.checkIfKeyTopsUnderControl(loginer, id, containSelf);
				}
				case 27: {// 委托应急课题申报数据
					return this.checkIfEntrustAppUnderControl(loginer, id, containSelf);
				}
				case 28: {// 委托应急课题立项数据
					return this.checkIfEntrustGrantedUnderControl(loginer, id, containSelf);
				}
				default : {
					return false;
				}
			}
		}
	}
	
	/**
	 * 获取当前登陆者所在的院系
	 * @return	map (id与name的映射)
	 */
	public Map<String,String> getLocalUnitMap(){
		Map<String,String> map = null;
		LoginInfo loginer = (LoginInfo) ActionContext.getContext().getSession().get(GlobalInfo.LOGINER);
		Map paraMap = new HashMap();
		paraMap.put("personId", loginer.getPerson().getId());
		List<Department> LocalUnits= dao.query("select d from Person person left join person.teacher t left join t.department d where person.id = :personId ", paraMap);
		map = new LinkedHashMap<String, String>();
		if (LocalUnits.size() > 0) {
			for (Department department : LocalUnits) {
				map.put(department.getId(), department.getName());
			}
		}
		return map;
	}

	/**
	 * 获取当前登陆者所在的基地
	 * @return	map (id与name的映射)
	 */
	public Map<String,String> getLocalInitMap(){
		Map<String,String> map = null;
		LoginInfo loginer = (LoginInfo) ActionContext.getContext().getSession().get(GlobalInfo.LOGINER);
		Map paraMap = new HashMap();
		paraMap.put("personId", loginer.getPerson().getId());
		List<Institute> institutes= dao.query("select i from Person person left join person.teacher t left join t.institute i where person.id = :personId ", paraMap);
		map = new LinkedHashMap<String, String>();
		if (institutes.size() > 0) {
			for (Institute institute : institutes) {
				if (null != institute) {
					map.put(institute.getId(), institute.getName());
				}
			}
		}
		return map;
	}
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个部级单位是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id部级机构ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfMinistryUnitUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 部级管理机构管理范围判定，不存在跨不跨级之分：
		 * 1、系统管理员、社科司，可以管理所有部级机构
		 * 2、社科司下的三个中心，可以管理自己
		 */
		boolean isMOE = false;
		if(currentAccountType.equals(AccountType.MINISTRY)){//如果登录者为部级账号，判断是否是MOE(教育部)
			List list = dao.query("select ag.id from Agency ag where ag.code='360'");
			isMOE = (list.size() > 0) ? currentAccountBelongUnitId.equals((String) list.get(0)) : false;
		}
		if (currentAccountType.equals(AccountType.ADMINISTRATOR) || isMOE) {// 系统管理员账号
			hql.append("select a.id from Agency a where a.type = 1 and a.id = :unitId");
			map.put("unitId", id);
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号但不是moe账号
			return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(currentAccountBelongUnitId, id, containSelf) : false;
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}

	/**
	 * 根据当前账号的级别、所在单位，判断某个省级单位是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id省级机构ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfProvinceUnitUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 省级管理机构管理范围判定，不存在跨不跨级之分：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有省级机构
		 * 2、省级管理机构，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR) || currentAccountType.equals(AccountType.MINISTRY)) {// 系统管理员账号、部级账号
			hql.append("select a.id from Agency a where a.type = 2 and a.id = :unitId");
			map.put("unitId", id);
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(currentAccountBelongUnitId, id, containSelf) : false;
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}

	/**
	 * 根据当前账号的级别、所在单位，判断某个校级单位是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id校级机构ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfUniversityUnitUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 校级管理机构管理范围判定
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有校级机构
		 * 2、省级管理机构、省级管理员，可以管理本省所有地方高校
		 * 3、高校，可以管理自己
		 * 不跨级
		 * 1、系统管理员，可以管理所有校级机构
		 * 2、部级机构、部级管理人员，可以管理所有部属高校
		 * 3、省级管理机构、省级管理员，可以管理本省所有地方高校
		 * 4、高校，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select u.id from Agency u where u.id = :universityId and (u.type = 3 or u.type = 4)");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select u.id from Agency u where u.id = :universityId and (u.type = 3 or u.type = 4)");
			} else {// 不跨级
				hql.append("select u.id from Agency u where u.id = :universityId and u.type = 3");
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			hql.append("select u.id from Agency u where u.id = :universityId and u.type = 4 and u.subjection.id = :unitId");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(currentAccountBelongUnitId, id, containSelf) : false;
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("universityId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}

	/**
	 * 根据当前账号的级别、所在单位，判断某个院系单位是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id高校院系ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfDepartmentUnitUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 院系管理机构管理范围判定
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有院系机构
		 * 2、省级管理机构、省级管理员，可以管理本省所有地方高校院系
		 * 3、高校，可以管理本校所有院系
		 * 4、院系，可以管理自己
		 * 不跨级
		 * 1、系统管理，可以管理所有院系机构
		 * 2、高校，可以管理本校所有院系
		 * 3、院系，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select d.id from Department d where d.id = :departmentId");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select d.id from Department d where d.id = :departmentId");
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select d.id from Department d left join d.university u where d.id = :departmentId and u.subjection.id = :unitId and u.type = 4");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			hql.append("select d.id from Department d where d.id = :departmentId and d.university.id = :unitId");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.DEPARTMENT)) {// 院系账号
			return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(currentAccountBelongUnitId, id, containSelf) : false;
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("departmentId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}

	/**
	 * 根据当前账号的级别、所在单位，判断某个基地单位是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id高校基地ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfInstituteUnitUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 基地管理机构管理范围判定
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有基地
		 * 2、省级管理机构、省级管理员，可以管理本省地方高校所有基地以及部属高校中的省属基地
		 * 3、高校，可以管理本校所有基地
		 * 4、基地，可以管理自己
		 * 不跨级
		 * 1、系统管理，可以管理所有基地
		 * 2、部级机构、部级管理人员，可以管理所有部属基地
		 * 3、省级机构、省级管理人员，可以管理所有省属基地
		 * 4、高校，可以管理本校所有院系
		 * 5、基地，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select i.id from Institute i where i.id = :instituteId");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select i.id from Institute i where i.id = :instituteId");
			} else {// 不跨级
				hql.append("select i.id from Institute i left join i.type sys where i.id = :instituteId and (sys.code = '01' or sys.code = '02')");
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select i.id from Institute i left join i.subjection u left join i.type sys where i.id = :instituteId and u.subjection.id = :unitId and (u.type = 4 or (sys.code = '02' or sys.code = '03') and u.type = 3)");
			} else {// 不跨级
				hql.append("select i.id from Institute i left join i.subjection u left join i.type sys where i.id = :instituteId and u.subjection.id = :unitId and (sys.code = '02' or sys.code = '03')");
			}
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			hql.append("select i.id from Institute i where i.id = :instituteId and i.subjection.id = :unitId");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.INSTITUTE)) {// 基地账号
			return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(currentAccountBelongUnitId, id, containSelf) : false;
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("instituteId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个部级管理人员是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id部级管理人员ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfMinistryOfficerUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 部级管理人员管理范围判定，不存在跨不跨级之分：
		 * 1、系统管理员，可以管理所有部级人员
		 * 2、社科司及三个中心，可以管理本单位的所有人员
		 * 3、管理人员，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and a.type = 1");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (currentAccountPrincipal == 1) {// 主账号
				hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and a.type = 1 and o.agency.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 子账号
				return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(this.getBelongIdByLoginer(loginer), id, containSelf) : false;
			}
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("officerId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个省级管理人员是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id省级管理人员ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfProvinceOfficerUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 省级管理人员管理范围判定，不存在跨不跨级之分：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有省级管理人员
		 * 2、省级机构，管理本单位所有人员
		 * 3、管理人员，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR) || currentAccountType.equals(AccountType.MINISTRY)) {// 系统管理员账号或部级账号
			hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and a.type = 2 ");
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			if (currentAccountPrincipal == 1) {// 主账号
				hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and a.type = 2 and o.agency.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 子账号
				return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(this.getBelongIdByLoginer(loginer), id, containSelf) : false;
			}
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("officerId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个校级管理人员是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id校级管理人员ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfUniversityOfficerUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 校级管理人员管理范围判定
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有高校管理人员
		 * 2、省级机构、省级管理人员，可以管理本省地方高校所有管理人员
		 * 3、校级机构，可以管理本校所有管理人员
		 * 4、管理人员，可以管理自己
		 * 不跨级：
		 * 1、系统管理员，可以管理所有高校管理人员
		 * 2、部级机构、部级管理人员，可以管理所有部属高校管理人员
		 * 3、省级机构、省级管理人员，可以管理本省地方高校所有管理人员
		 * 4、校级机构，可以管理本校所有管理人员
		 * 5、管理人员，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and (a.type = 3 or a.type = 4) and o.department.id is null and o.institute.id is null");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and (a.type = 3 or a.type = 4 ) and o.department.id is null and o.institute.id is null");
			} else {// 不跨级
				hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and a.type = 3 and o.department.id is null and o.institute.id is null");
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE )) {// 省级账号
			hql.append("select o.id from Officer o left join o.agency a where o.id = :officerId and a.type = 4 and a.subjection.id = :unitId and o.department.id is null and o.institute.id is null");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			if (currentAccountPrincipal == 1) {// 主账号
				hql.append("select o.id from Officer o where o.id = :officerId and o.agency.id = :unitId and o.department.id is null and o.institute.id is null");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 子账号
				return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(this.getBelongIdByLoginer(loginer), id, containSelf) : false;
			}
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("officerId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个院系管理人员是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id院系管理人员ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfDepartmentOfficerUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 院系管理人员管理范围判断
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有院系管理人员
		 * 2、省级机构、省级管理人员，可以管理本省地方高校所有院系管理人员
		 * 3、校级机构、校级管理人员，可以管理本校所有院系管理人员
		 * 4、院系，可以管理本院系所有管理人员
		 * 5、管理人员，可以管理自己
		 * 不跨级：
		 * 1、系统管理员，可以管理所有院系管理人员
		 * 2、校级机构、校级管理人员，可以管理本校所有院系管理人员
		 * 3、院系，可以管理本院系所有管理人员
		 * 4、管理人员，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select o.id from Officer o where o.id = :officerId and o.department.id is not null");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select o.id from Officer o where o.id = :officerId and o.department.id is not null");
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select o.id from Officer o left join o.department d left join o.agency u where o.id = :officerId and u.type = 4 and u.subjection.id = :unitId and o.department.id is not null");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			hql.append("select o.id from Officer o where o.id = :officerId and o.agency.id = :unitId and o.department.id is not null");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.DEPARTMENT)) {// 院系账号
			if (currentAccountPrincipal == 1) {// 主账号
				hql.append("select o.id from Officer o where o.id = :officerId and o.department.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 子账号
				return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(this.getBelongIdByLoginer(loginer), id, containSelf) : false;
			}
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("officerId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个基地管理人员是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id基地管理人员ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfInstituteOfficerUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 基地管理人员管理范围判断
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有基地管理人员
		 * 2、省级机构、省级管理人员，可以管理本省地方高校的所有基地管理人员，以及部属高校的省属基地管理人员
		 * 3、校级机构、校级管理人员，可以管理本校所有基地管理人员
		 * 4、基地，可以管理本基地所有管理人员
		 * 5、管理人员，可以管理自己
		 * 不跨级：
		 * 1、系统管理员，可以管理所有基地管理人员
		 * 2、部级机构、部级管理人员，可以管理所有部级基地管理人员
		 * 3、省级机构、省级管理人员，可以管理本省所有省属基地管理人员
		 * 4、校级机构、校级管理人员，可以管理本校所有基地管理人员
		 * 5、基地，可以管理本基地所有管理人员
		 * 6、管理人员，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR) ) {// 系统管理员账号
			hql.append("select o.id from Officer o where o.id = :officerId and o.institute.id is not null");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select o.id from Officer o where o.id = :officerId and o.institute.id is not null");
			} else {// 不跨级
				hql.append("select o.id from Officer o left join o.institute i left join i.type sys where o.id = :officerId and o.institute.id is not null and (sys.code = '01' or sys.code = '02')");
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select o.id from Officer o left join o.institute i left join i.type sys left join o.agency u where o.id = :officerId and (u.type = 4 or (sys.code = '02' or sys.code = '03') and u.type = 3) and u.subjection.id = :unitId and o.institute.id is not null");
			} else {// 不跨级
				hql.append("select o.id from Officer o left join o.institute i left join i.type sys left join o.agency u where o.id = :officerId and (sys.code = '02' or sys.code = '03') and u.subjection.id = :unitId and o.institute.id is not null");
			}
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			hql.append("select o.id from Officer o where o.id = :officerId and o.agency.id = :unitId and o.institute.id is not null");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.INSTITUTE)) {// 基地账号
			if (currentAccountPrincipal == 1) {// 主账号
				hql.append("select o.id from Officer o where o.id = :officerId and o.institute.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 子账号
				return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(this.getBelongIdByLoginer(loginer), id, containSelf) : false;
			}
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("officerId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}

	/**
	 * 根据当前账号的级别，判断某个外部专家是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id外部专家人员ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfExpertUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		AccountType currentAccountType = loginer.getCurrentType();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		/**
		 * 外部专家管理范围判定
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有外部专家
		 * 2、外部专家可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR) || currentAccountType.equals(AccountType.MINISTRY)) {// 系统管理员账号、部级账号
			Map map = new HashMap();
			map.put("expertId", id);
			List<String> expertIds = dao.query("select e.id from Expert e where e.id = :expertId", map);
			return !expertIds.isEmpty();
		} else if (currentAccountType.equals(AccountType.EXPERT)) {// 外部专家账号
			return this.checkIfSelfLegal(this.getExpertIdByPersonId(currentAccountBelongId), id, containSelf);
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
	}

	/**
	 * 根据当前账号的级别、所在单位，判断某个教师是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id教师ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfTeacherUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		/**
		 * 教师管理范围判断
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有教师
		 * 2、省级机构、省级管理人员，可以管理本省地方高校的所有教师，以及部属高校的省属基地教师
		 * 3、校级机构、校级管理人员，可以管理本校所有教师
		 * 4、院系，可以管理本院系所有教师
		 * 5、基地，可以管理本基地所有教师
		 * 6、教师，可以管理自己
		 * 不跨级：
		 * 1、系统管理员，可以管理所有教师
		 * 2、院系，可以管理本院系所有教师
		 * 3、基地，可以管理本基地所有教师
		 * 4、教师，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select t.id from Teacher t where t.id = :teacherId");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select t.id from Teacher t where t.id = :teacherId");
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select t.id from Teacher t left join t.department d left join t.institute i left join t.university u left join i.type sys where t.id = :teacherId and ((sys.code = '02' or sys.code = '03') and u.type = 3 or u.type = 4 ) and u.subjection.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select t.id from Teacher t where t.id = :teacherId and t.university.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.DEPARTMENT)) {// 院系账号
			hql.append("select t.id from Teacher t where t.id = :teacherId and t.department.id = :unitId");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.INSTITUTE)) {// 基地账号
			hql.append("select t.id from Teacher t where t.id = :teacherId and t.institute.id = :unitId");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.TEACHER)) {// 教师账号
			return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(this.getBelongIdByLoginer(loginer), id, containSelf) : false;
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("teacherId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}

	/**
	 * 根据当前账号的级别、所在单位，判断某个学生是否在管辖范围
	 * @param loginer当前登录对象
	 * @param id学生ID
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @return true在当前账号管理范围之内, false不存在当前账号管理范围之内
	 */
	public boolean checkIfStudentUnderControl(LoginInfo loginer, String id, boolean containSelf) {
		List<String> re;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		/**
		 * 学生管理范围判断
		 * 跨级：
		 * 1、系统管理员、部级机构、部级管理人员，可以管理所有学生
		 * 2、省级机构、省级管理人员，可以管理本省地方高校的所有学生，以及部属高校的省属基地学生
		 * 3、校级机构、校级管理人员，可以管理本校所有学生
		 * 4、院系，可以管理本院系所有学生
		 * 5、基地，可以管理本基地所有学生
		 * 6、学生，可以管理自己
		 * 不跨级：
		 * 1、系统管理员，可以管理所有学生
		 * 2、院系，可以管理本院系所有学生
		 * 3、基地，可以管理本基地所有学生
		 * 4、学生，可以管理自己
		 */
		if (currentAccountType.equals(AccountType.ADMINISTRATOR)) {// 系统管理员账号
			hql.append("select s.id from Student s where s.id = :studentId");
		} else if (currentAccountType.equals(AccountType.MINISTRY)) {// 部级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select s.id from Student s where s.id = :studentId");
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.PROVINCE)) {// 省级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select s.id from Student s left join s.department d left join s.institute i left join s.university u left join i.type sys where s.id = :studentId and ((sys.code = '02' or sys.code = '03') and u.type = 3 or u.type = 4) and u.subjection.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)) {// 校级账号
			if (GlobalInfo.IS_CROSS_LEVEL) {// 跨级
				hql.append("select s.id from Student s where s.id = :studentId and s.university.id = :unitId");
				map.put("unitId", currentAccountBelongUnitId);
			} else {// 不跨级
				return false;
			}
		} else if (currentAccountType.equals(AccountType.DEPARTMENT)) {// 院系账号
			hql.append("select s.id from Student s where s.id = :studentId and s.department.id = :unitId");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.INSTITUTE)) {// 基地账号
			hql.append("select s.id from Student s where s.id = :studentId and s.institute.id = :unitId");
			map.put("unitId", currentAccountBelongUnitId);
		} else if (currentAccountType.equals(AccountType.STUDENT)) {// 学生账号
			return currentAccountPrincipal == 1 ? this.checkIfSelfLegal(currentAccountBelongId, id, containSelf) : false;
		} else {// 其它账号类别，不符合实际管理逻辑，一律视为不合法
			return false;
		}
		map.put("studentId", id);
		re = dao.query(hql.toString(), map);
		return !re.isEmpty();
	}
	
	/**
	 * 判定自己是否可以管理自己
	 * @param originalId本体ID
	 * @param newId待判定ID
	 * @param containSelf是否包含自己可以进行管理
	 * @return true可以，false不可以
	 */
	public boolean checkIfSelfLegal(String originalId, String newId, boolean containSelf) {
		if (containSelf) {// 如果管理范围包括自己，则进行判定
			if (originalId == null || originalId.isEmpty() || newId == null || newId.isEmpty()) {
				return false;
			} else {
				return originalId.equals(newId);
			}
		} else {// 如果管理范围不包括自己，则恒为false
			return false;
		}
	}
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个成果是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @param productId待判断的论文成果
	 * @return true在,false不在
	 */
	public boolean checkIfProductUnderControl(LoginInfo loginer, String productId, boolean containSelf){
		List<String> ps;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		map.put("productId", productId);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select p.id from Product p where p.id =:productId");
		} else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select p.id from Product p where p.id =:productId");
		} else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select p.id from Product p left join p.university u where p.id =:productId and u.type=4 and u.subjection.id =:provinceId");
			map.put("provinceId", currentAccountBelongUnitId);
		} else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select p.id from Product p left outer join p.university univ where p.id =:productId and univ.id =:universityId");
			map.put("universityId", currentAccountBelongUnitId);
		} else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select p.id from Product p left outer join p.department dept where p.id =:productId and dept.id =:departmentId");
			map.put("departmentId", currentAccountBelongUnitId);
		} else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select p.id from Product p left outer join p.institute inst where p.id =:productId and inst.id =:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		} else if(currentAccountType.within(AccountType.EXPERT, AccountType.STUDENT)){// 专家，教师及学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select p.id from Product p left outer join p.author pe where p.id =:productId and pe.id =:personId");
				map.put("personId", loginer.getPerson().getId());
			} else {
				return false;
			}
		}
		ps = dao.query(hql.toString(), map);
		return !ps.isEmpty();
	}
	
	
	/**
	 * 根据当前账号的级别、所在单位，判断某个论文是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @param paperId待判断的论文成果
	 * @return true在,false不在
	 */
//	public boolean checkIfPaperUnderControl(LoginInfo loginer, String paperId, boolean containSelf){
//		List<String> ps;
//		StringBuffer hql = new StringBuffer();
//		Map map = new HashMap();
//		int currentAccountType = loginer.getCurrentType();
//		int currentAccountPrincipal = loginer.getIsPrincipal();
//		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
//		String currentAccountBelongId = loginer.getCurrentBelongId();
//		map.put("paperId", paperId);
//		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
//			hql.append("select pa.id from Paper pa where pa.id=:paperId");
//		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
//			hql.append("select pa.id from Paper pa where pa.id=:paperId and pa.deptInstAuditStatus=3 and pa.deptInstAuditResult=2");
//		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
//			hql.append("select pa.id from Paper pa left join pa.university u where pa.id = :paperId and u.type = 4 and u.subjection.id = :provinceId and pa.deptInstAuditStatus=3 and pa.deptInstAuditResult=2");
//			map.put("provinceId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
//			hql.append("select pa.id from Paper pa left outer join pa.university univ where pa.id=:paperId and univ.id=:universityId and pa.deptInstAuditStatus=3 and pa.deptInstAuditResult=2");
//			map.put("universityId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
//			hql.append("select pa.id from Paper pa left outer join pa.department dept where pa.id=:paperId and dept.id=:departmentId");
//			map.put("departmentId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
//			hql.append("select pa.id from Paper pa left outer join pa.institute inst where pa.id=:paperId and inst.id=:instituteId");
//			map.put("instituteId",currentAccountBelongUnitId);
//		}else if(currentAccountType >7 && currentAccountType <11){// 专家，教师及学生
//			if(containSelf && currentAccountPrincipal==1){
//				hql.append("select pa.id from Paper pa left outer join pa.author pe where pa.id=:paperId and pe.id=:personId");
//				map.put("personId", currentAccountBelongId);
//			}else{
//				return false;
//			}
//		}
//		ps = dao.query(hql.toString(), map);
//		return !ps.isEmpty();
//	}
//	/**
//	 * 根据当前账号的级别、所在单位，判断某个著作是否在管辖的范围
//	 * @param loginer当前登录对象
//	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
//	 * @param bookId待判断的著作成果
//	 * @return true在,false不在
//	 */
//	public boolean checkIfBookUnderControl(LoginInfo loginer, String bookId, boolean containSelf){
//		List<String> bs;
//		StringBuffer hql = new StringBuffer();
//		Map map = new HashMap();
//		map.put("bookId", bookId);
//		int currentAccountType = loginer.getCurrentType();
//		int currentAccountPrincipal = loginer.getIsPrincipal();
//		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
//		String currentAccountBelongId = loginer.getCurrentBelongId();
//		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
//			hql.append("select bo.id from Book bo where bo.id=:bookId");
//		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
//			hql.append("select bo.id from Book bo where bo.id=:bookId and bo.deptInstAuditStatus=3 and bo.deptInstAuditResult=2");
//		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
//			hql.append("select bo.id from Book bo left join bo.university u where bo.id = :bookId and u.type = 4 and u.subjection.id = :provinceId and bo.deptInstAuditStatus=3 and bo.deptInstAuditResult=2");
//			map.put("provinceId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
//			hql.append("select bo.id from Book bo left outer join bo.university univ where bo.id=:bookId and univ.id=:universityId and bo.deptInstAuditStatus=3 and bo.deptInstAuditResult=2");
//			map.put("universityId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
//			hql.append("select bo.id from Book bo left outer join bo.department dept where bo.id=:bookId and dept.id=:departmentId");
//			map.put("departmentId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
//			hql.append("select bo.id from Book bo left outer join bo.institute inst where bo.id=:bookId and inst.id=:instituteId");
//			map.put("instituteId",currentAccountBelongUnitId);
//		}else if(currentAccountType >7 && currentAccountType <11){// 专家，教师及学生
//			if(containSelf && currentAccountPrincipal==1){
//				hql.append("select bo.id from Book bo left outer join bo.author pe where bo.id=:bookId and pe.id=:personId");
//				map.put("personId", currentAccountBelongId);
//			}else{
//				return false;
//			}
//		}
//		bs = dao.query(hql.toString(), map);
//		return !bs.isEmpty();
//	}
	/**
	 * 根据当前账号的级别、所在单位，判断某个研究咨询报告是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，fase不包括自己
	 * @param consultationId待判断的研究咨询报告成果
	 * @return true在,false不在
	 */
//	public boolean checkIfConsultationUnderControl(LoginInfo loginer, String consultationId,boolean containSelf){
//		List<String> bs;
//		StringBuffer hql = new StringBuffer();
//		Map map = new HashMap();
//		map.put("consultationId", consultationId);
//		int currentAccountType = loginer.getCurrentType();
//		int currentAccountPrincipal = loginer.getIsPrincipal();
//		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
//		String currentAccountBelongId = loginer.getCurrentBelongId();
//		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
//			hql.append("select co.id from Consultation co where co.id=:consultationId");
//		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
//			hql.append("select co.id from Consultation co where co.id=:consultationId and co.deptInstAuditStatus=3 and co.deptInstAuditResult=2");
//		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
//			hql.append("select co.id from Consultation co left join co.university u where co.id = :consultationId and u.type = 4 and u.subjection.id = :provinceId and co.deptInstAuditStatus=3 and co.deptInstAuditResult=2");
//			map.put("provinceId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
//			hql.append("select co.id from Consultation co left outer join co.university univ where co.id=:consultationId and univ.id=:universityId and co.deptInstAuditStatus=3 and co.deptInstAuditResult=2");
//			map.put("universityId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
//			hql.append("select co.id from Consultation co left outer join co.department dept where co.id=:consultationId and dept.id=:departmentId");
//			map.put("departmentId",currentAccountBelongUnitId);
//		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
//			hql.append("select co.id from Consultation co left outer join co.institute inst where co.id=:consultationId and inst.id=:instituteId");
//			map.put("instituteId",currentAccountBelongUnitId);
//		}else if(currentAccountType >7 && currentAccountType <11){// 专家，教师及学生
//			if(containSelf && currentAccountPrincipal==1){
//				hql.append("select co.id from Consultation co left outer join co.author pe where co.id=:consultationId and pe.id=:personId");
//				map.put("personId", currentAccountBelongId);
//			}else{
//				return false;
//			}
//		}
//		bs = dao.query(hql.toString(), map);
//		return !bs.isEmpty();
//	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个奖励是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param awardApplicationId待判断的奖励申请
	 * @return true在,false不在 
	 */
	public boolean checkIfAwardAppUnderControl(LoginInfo loginer, String awardApplicationId,boolean containSelf){
		List<String> aa;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("aaId", awardApplicationId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
//		String currentAccountBelongId = loginer.getCurrentBelongId();
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select aa.id from AwardApplication aa where aa.id=:aaId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select aa.id from AwardApplication aa where aa.id=:aaId and (aa.status>=5 or aa.isImported = 1)");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select aa.id from AwardApplication aa left join aa.university u where aa.id = :aaId and u.type = 4 and u.subjection.id = :provinceId and (aa.status>=4 or aa.isImported = 1)");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select aa.id from AwardApplication aa left outer join aa.university univ where aa.id=:aaId and univ.id=:universityId and (aa.status>=3 or aa.isImported = 1)");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select aa.id from AwardApplication aa left outer join aa.department dept where aa.id=:aaId and dept.id=:departmentId and (aa.status>=2 or aa.isImported = 1)");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select aa.id from AwardApplication aa left outer join aa.institute inst where aa.id=:aaId and inst.id=:instituteId and (aa.status>=2 or aa.isImported = 1)");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select aa.id from AwardApplication aa left outer join aa.applicant pe where aa.id=:aaId and pe.id=:personId");
				String hql1 = "select ar.id from AwardReview ar where ar.application.id=:aaId and ar.reviewer.id=:personId";
				map.put("personId", loginer.getPerson().getId());
				aa = dao.query(hql.toString(), map);
				List<String> bb = dao.query(hql1, map);
				return  !aa.isEmpty() || !bb.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select aa.id from AwardApplication aa left outer join aa.applicant pe where aa.id=:aaId and pe.id=:personId");
				map.put("personId", loginer.getPerson().getId());
			}else{
				return false;
			}
		}
		aa = dao.query(hql.toString(), map);
		return !aa.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个一般项目申报数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param genAppId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfGenAppUnderControl(LoginInfo loginer, String genAppId, boolean containSelf){
		List<String> ga;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", genAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
//		String currentAccountBelongId = loginer.getCurrentBelongId();
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select app.id from GeneralApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select app.id from GeneralApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select app.id from GeneralApplication app left outer join app.university u where app.id = :appId and u.type = 4 and u.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select app.id from GeneralApplication app left outer join app.university univ where app.id=:appId and univ.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select app.id from GeneralApplication app left outer join app.department dept where app.id=:appId and dept.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select app.id from GeneralApplication app left outer join app.institute inst where app.id=:appId and inst.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from GeneralApplication app, GeneralMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", loginer.getPerson().getId());
				ga = dao.query(hql.toString(), map);
				String hql1 = "select endRev.id from GeneralEndinspectionReview endRev, GeneralEndinspection endi, GeneralGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from GeneralApplicationReview appRev, GeneralApplication app where appRev.application.id=app.id and appRev.reviewer.id=:personId";
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !ga.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from GeneralApplication app, GeneralMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", loginer.getPerson().getId());
			}else{
				return false;
			}
		}
		ga = dao.query(hql.toString(), map);
		return !ga.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个基地项目申报数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param instpAppId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfInstpAppUnderControl(LoginInfo loginer, String instpAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", instpAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
//		String currentAccountBelongId = loginer.getCurrentBelongId();
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select app.id from InstpApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select app.id from InstpApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select app.id from InstpApplication app left outer join app.university u where app.id = :appId and u.type = 4 and u.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select app.id from InstpApplication app left outer join app.university univ where app.id=:appId and univ.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select app.id from InstpApplication app left outer join app.department dept where app.id=:appId and dept.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select app.id from InstpApplication app left outer join app.institute inst where app.id=:appId and inst.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from InstpApplication app, InstpMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", loginer.getPerson().getId());
				app = dao.query(hql.toString(), map);
				String hql1 = "select endRev.id from InstpEndinspectionReview endRev, InstpEndinspection endi, InstpGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from InstpApplicationReview appRev, InstpApplication app where appRev.application.id=app.id and appRev.reviewer.id=:personId";
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from InstpApplication app, InstpMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", loginer.getPerson().getId());
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个后期资助项目申报数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param appId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfPostAppUnderControl(LoginInfo loginer, String postAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", postAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select app.id from PostApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select app.id from PostApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select app.id from PostApplication app left outer join app.university uni where app.id = :appId and uni.type = 4 and uni.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select app.id from PostApplication app left outer join app.university uni where app.id=:appId and uni.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select app.id from PostApplication app left outer join app.department dep where app.id=:appId and dep.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select app.id from PostApplication app left outer join app.institute ins where app.id=:appId and ins.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from PostApplication app, PostMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				String hql1 = "select endRev.id from PostEndinspectionReview endRev, PostEndinspection endi, PostGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from PostApplicationReview appRev, PostApplication app where appRev.application.id=app.id and appRev.reviewer.id=:personId";
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from PostApplication app, PostMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个重大攻关项目招标数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param keyAppId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfKeyAppUnderControl(LoginInfo loginer, String keyAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", keyAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select app.id from KeyApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select app.id from KeyApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select app.id from KeyApplication app left outer join app.university u where app.id = :appId and u.type = 4 and u.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select app.id from KeyApplication app left outer join app.university univ where app.id=:appId and univ.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select app.id from KeyApplication app left outer join app.department dept where app.id=:appId and dept.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select app.id from KeyApplication app left outer join app.institute inst where app.id=:appId and inst.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from KeyApplication app, KeyMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				String hql1 = "select endRev.id from KeyEndinspectionReview endRev, KeyEndinspection endi, KeyGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from KeyApplicationReview appRev, KeyApplication app where appRev.application.id=app.id and appRev.reviewer.id=:personId";
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from KeyApplication app, KeyMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个委托应急课题申报数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param appId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfEntrustAppUnderControl(LoginInfo loginer, String entrustAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", entrustAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select app.id from EntrustApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select app.id from EntrustApplication app where app.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select app.id from EntrustApplication app left outer join app.university uni where app.id = :appId and uni.type = 4 and uni.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select app.id from EntrustApplication app left outer join app.university uni where app.id=:appId and uni.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select app.id from EntrustApplication app left outer join app.department dep where app.id=:appId and dep.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select app.id from EntrustApplication app left outer join app.institute ins where app.id=:appId and ins.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from EntrustApplication app, EntrustMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				String hql1 = "select endRev.id from EntrustEndinspectionReview endRev, EntrustEndinspection endi, EntrustGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from EntrustApplicationReview appRev, EntrustApplication app where appRev.application.id=app.id and appRev.reviewer.id=:personId";
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select app.id from EntrustApplication app, EntrustMember mem where mem.application.id=app.id and app.id=:appId and (mem.member is null or mem.member.id =:personId)");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个一般项目立项数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param genAppId待判断的项目申请
	 * @return true在,false不在 
	 */
	@SuppressWarnings("rawtypes")
	public boolean checkIfGenGrantedUnderControl(LoginInfo loginer, String genAppId, boolean containSelf){
		List<String> ga;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", genAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select gra.id from GeneralGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select gra.id from GeneralGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select gra.id from GeneralGranted gra left outer join gra.university u where gra.application.id = :appId and u.type = 4 and u.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select gra.id from GeneralGranted gra left outer join gra.university univ where gra.application.id=:appId and univ.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select gra.id from GeneralGranted gra left outer join gra.department dept where gra.application.id=:appId and dept.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select gra.id from GeneralGranted gra left outer join gra.institute inst where gra.application.id=:appId and inst.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from GeneralGranted gra, GeneralMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				String hql1 = "select endRev.id from GeneralEndinspectionReview endRev, GeneralEndinspection endi, GeneralGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from GeneralApplicationReview appRev, GeneralApplication app, GeneralGranted gra where appRev.application.id=app.id and gra.application.id=app.id and appRev.reviewer.id=:personId";
				map.put("personId", currentAccountBelongId);
				ga = dao.query(hql.toString(), map);
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !ga.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from GeneralGranted gra, GeneralMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		ga = dao.query(hql.toString(), map);
		return !ga.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个基地项目立项数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param instpAppId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfInstpGrantedUnderControl(LoginInfo loginer, String instpAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", instpAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select gra.id from InstpGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select gra.id from InstpGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select gra.id from InstpGranted gra left outer join gra.university u where gra.application.id = :appId and u.type = 4 and u.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select gra.id from InstpGranted gra left outer join gra.university univ where gra.application.id=:appId and univ.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select gra.id from InstpGranted gra left outer join gra.department dept where gra.application.id=:appId and dept.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select gra.id from InstpGranted gra left outer join gra.institute inst where gra.application.id=:appId and inst.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from InstpGranted gra, InstpMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				String hql1 = "select endRev.id from InstpEndinspectionReview endRev, InstpEndinspection endi, InstpGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from InstpApplicationReview appRev, InstpApplication app, InstpGranted gra where appRev.application.id=app.id and gra.application.id=app.id and appRev.reviewer.id=:personId";
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from InstpGranted gra, InstpMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个后期资助项目立项数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param appId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfPostGrantedUnderControl(LoginInfo loginer, String postAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", postAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select gra.id from PostGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select gra.id from PostGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select gra.id from PostGranted gra left outer join gra.university uni where gra.application.id= :appId and uni.type = 4 and uni.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select gra.id from PostGranted gra left outer join gra.university uni where gra.application.id=:appId and uni.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select gra.id from PostGranted gra left outer join gra.department dep where gra.application.id=:appId and dep.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select gra.id from PostGranted gra left outer join gra.institute ins where gra.application.id=:appId and ins.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from PostGranted gra, PostMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				String hql1 = "select endRev.id from PostEndinspectionReview endRev, PostEndinspection endi, PostGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from PostApplicationReview appRev, PostApplication app, PostGranted gra where appRev.application.id=app.id and gra.application.id=app.id and appRev.reviewer.id=:personId";
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from PostGranted gra, PostMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个重大攻关项目中标数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param appId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfKeyGrantedUnderControl(LoginInfo loginer, String keyAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", keyAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select gra.id from KeyGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select gra.id from KeyGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select gra.id from KeyGranted gra left outer join gra.university uni where gra.application.id= :appId and uni.type = 4 and uni.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select gra.id from KeyGranted gra left outer join gra.university uni where gra.application.id=:appId and uni.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select gra.id from KeyGranted gra left outer join gra.department dep where gra.application.id=:appId and dep.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select gra.id from KeyGranted gra left outer join gra.institute ins where gra.application.id=:appId and ins.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from KeyGranted gra, KeyMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				String hql1 = "select endRev.id from KeyEndinspectionReview endRev, KeyEndinspection endi, KeyGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from KeyApplicationReview appRev, KeyApplication app, KeyGranted gra where appRev.application.id=app.id and gra.application.id=app.id and appRev.reviewer.id=:personId";
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from KeyGranted gra, KeyMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个重大攻关项目选题数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param topsId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfKeyTopsUnderControl(LoginInfo loginer, String topsId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("topsId", topsId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select tops.id from KeyTopicSelection tops where tops.id=:topsId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select tops.id from KeyTopicSelection tops where tops.id=:topsId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select tops.id from KeyTopicSelection tops left outer join tops.university uni left outer join tops.expUniversity expUni where tops.id=:topsId and ((uni.type = 4 and uni.subjection.id = :provinceId) or (expUni.type = 4 and expUni.subjection.id = :provinceId))");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select tops.id from KeyTopicSelection tops left outer join tops.university uni left outer join tops.expUniversity expUni where tops.id=:topsId and (uni.id=:universityId or expUni.id=:universityId)");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select tops.id from KeyTopicSelection tops left outer join tops.expDepartment expDep where tops.id=:topsId and expDep.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select tops.id from KeyTopicSelection tops left outer join tops.expInstitute expIns where tops.id=:topsId and expIns.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER) || currentAccountType.equals(AccountType.STUDENT)){// 专家，教师，学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select tops.id from KeyTopicSelection tops where tops.applicantId=:personId and tops.id=:topsId");
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				return  !app.isEmpty();
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 *根据当前账号的级别、所在单位，判断某个委托应急课题立项数据是否在管辖的范围
	 * @param loginer当前登录对象
	 * @param containSelf管理范围是否包含自己：true包括自己，false不包括自己
	 * @param appId待判断的项目申请
	 * @return true在,false不在 
	 */
	public boolean checkIfEntrustGrantedUnderControl(LoginInfo loginer, String entrustAppId, boolean containSelf){
		List<String> app;
		StringBuffer hql = new StringBuffer();
		Map map = new HashMap();
		map.put("appId", entrustAppId);
		AccountType currentAccountType = loginer.getCurrentType();
		int currentAccountPrincipal = loginer.getIsPrincipal();
		String currentAccountBelongUnitId = loginer.getCurrentBelongUnitId();
		String currentAccountBelongId = this.getBelongIdByLoginer(loginer);
		if(currentAccountType.equals(AccountType.ADMINISTRATOR)){//系统管理员
			hql.append("select gra.id from EntrustGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.MINISTRY)){//部级
			hql.append("select gra.id from EntrustGranted gra where gra.application.id=:appId");
		}else if(currentAccountType.equals(AccountType.PROVINCE)){//省厅
			hql.append("select gra.id from EntrustGranted gra left outer join gra.university uni where gra.application.id= :appId and uni.type = 4 and uni.subjection.id = :provinceId");
			map.put("provinceId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.MINISTRY_UNIVERSITY) || currentAccountType.equals(AccountType.LOCAL_UNIVERSITY)){//部属高校与地方高校
			hql.append("select gra.id from EntrustGranted gra left outer join gra.university uni where gra.application.id=:appId and uni.id=:universityId");
			map.put("universityId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.DEPARTMENT)){//院系
			hql.append("select gra.id from EntrustGranted gra left outer join gra.department dep where gra.application.id=:appId and dep.id=:departmentId");
			map.put("departmentId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.INSTITUTE)){//研究机构
			hql.append("select gra.id from EntrustGranted gra left outer join gra.institute ins where gra.application.id=:appId and ins.id=:instituteId");
			map.put("instituteId",currentAccountBelongUnitId);
		}else if(currentAccountType.equals(AccountType.EXPERT) || currentAccountType.equals(AccountType.TEACHER)){// 专家，教师
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from EntrustGranted gra, EntrustMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				String hql1 = "select endRev.id from EntrustEndinspectionReview endRev, EntrustEndinspection endi, EntrustGranted gra where endRev.endinspection.id=endi.id and endi.granted.id=gra.id and gra.application.id=:appId and endRev.reviewer.id=:personId";
				String hql2 = "select appRev.id from EntrustApplicationReview appRev, EntrustApplication app, EntrustGranted gra where appRev.application.id=app.id and gra.application.id=app.id and appRev.reviewer.id=:personId";
				map.put("personId", currentAccountBelongId);
				app = dao.query(hql.toString(), map);
				List<String> endReview = dao.query(hql1, map);
				List<String> appReview = dao.query(hql2, map);
				return  !app.isEmpty() || !endReview.isEmpty() || !appReview.isEmpty();
			}else{
				return false;
			}
		}else if( currentAccountType.equals(AccountType.STUDENT)){//学生
			if(containSelf && currentAccountPrincipal==1){
				hql.append("select gra.id from EntrustGranted gra, EntrustMember mem where gra.application.id=mem.application.id and gra.application.id=:appId and mem.member.id=:personId");
				map.put("personId", currentAccountBelongId);
			}else{
				return false;
			}
		}
		app = dao.query(hql.toString(), map);
		return !app.isEmpty();
	}
	
	/**
	 * 根据officerId找到officer，包括里面的person、
	 * agency、department、institute等外键对象
	 * @param officerId
	 * @return officer完整对象
	 */
	public Officer getOfficerByOfficerId(String officerId) {
		if (officerId == null || officerId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("officerId", officerId);
			List<Officer> officers = dao.query("select o from Officer o left join fetch o.person p left join fetch o.agency ag left join fetch o.department d left join fetch o.institute i where o.id = :officerId", map);
			return (officers == null || officers.isEmpty()) ? null : officers.get(0);
		}
	}

	/**
	 * 根据personId找到expert，包括里面的person对象
	 * @param personId
	 * @return expert完整对象
	 */
	public Expert getExpertByPersonId(String personId) {
		if (personId == null || personId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("personId", personId);
			List<Expert> experts = dao.query("select e from Expert e left join fetch e.person p where p.id = :personId", map);
			return (experts == null || experts.isEmpty()) ? null : experts.get(0);
		}
	}

	/**
	 * 根据personId找到teacher对象(专职)，包括里面的person、
	 * university、department、institute等外键对象
	 * @param personId
	 * @return teacher完整对象
	 */
	public Teacher getTeacherByPersonId(String personId) {
		if (personId == null || personId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("personId", personId);
			List<Teacher> teachers = dao.query("select t from Teacher t left join fetch t.person p left join fetch t.university u left join fetch t.department d left join fetch t.institute i where p.id = :personId and t.type = '专职人员' ", map);
			return (teachers == null || teachers.isEmpty()) ? null : teachers.get(0);
		}
	}

	/**
	 * 根据personId找到student对象，包括里面的person、
	 * university、department、institute等外键对象
	 * @param personId
	 * @return student完整对象
	 */
	public Student getStudentByPersonId(String personId) {
		if (personId == null || personId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("personId", personId);
			List<Student> students = dao.query("select s from Student s left join fetch s.person p left join fetch s.university u left join fetch s.department d left join fetch s.institute i where p.id = :personId ", map);
			return (students == null || students.isEmpty()) ? null : students.get(0);
		}
	}

	/**
	 * 根据账号的officerId找到其所属agencyId
	 * @param officerId
	 * @return 机构ID
	 */
	public String getAgencyIdByOfficerId(String officerId) {
		if (officerId == null || officerId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("officerId", officerId);
			List<String> re = dao.query("select o.agency.id from Officer o where o.id = :officerId", map);
			return re.isEmpty() ? null : re.get(0);
		}
	}

	/**
	 * 根据账号的officerId找到其所属departmentId
	 * @param officerId
	 * @return 机构ID
	 */
	public String getDepartmentIdByOfficerId(String officerId) {
		if (officerId == null || officerId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("officerId", officerId);
			List<String> re = dao.query("select o.department.id from Officer o where o.id = :officerId", map);
			return re.isEmpty() ? null : re.get(0);
		}
	}

	/**
	 * 根据账号的officerId找到其所属instituteId
	 * @param officerId
	 * @return 机构ID
	 */
	public String getInstituteIdByOfficerId(String officerId) {
		if (officerId == null || officerId.isEmpty()) {
			return null;
		} else {
			Map map = new HashMap();
			map.put("officerId", officerId);
			List<String> re = dao.query("select o.institute.id from Officer o where o.id = :officerId", map);
			return re.isEmpty() ? null : re.get(0);
		}
	}

	/**
	 * 根据personId找到expertId(专职)
	 * @param personId
	 * @return expertId
	 */
	public String getExpertIdByPersonId(String personId) {
		Map map = new HashMap();
		map.put("personId", personId);
		List<String> expertList = dao.query("select e.id from Expert e where e.person.id = :personId", map);
		return (expertList == null || expertList.isEmpty()) ? "" : expertList.get(0);
	}

	/**
	 * 根据personId找到teacherId(专职)
	 * @param personId
	 * @return teacherId
	 */
	public String getTeacherIdByPersonId(String personId) {
		Map map = new HashMap();
		map.put("personId", personId);
		List<String> teacherList = dao.query("select t.id from Teacher t where t.person.id = :personId and t.type = '专职人员'", map);
		return (teacherList == null || teacherList.isEmpty()) ? "" : teacherList.get(0);
	}

	/**
	 * 根据personId找到studentId
	 * @param personId
	 * @return studentId
	 */
	public String getStudentIdByPersonId(String personId) {
		Map map = new HashMap();
		map.put("personId", personId);
		List<String> studentList = dao.query("select s.id from Student s where s.person.id = :personId", map);
		return (studentList == null || studentList.isEmpty()) ? "" : studentList.get(0);
	}
	
	/**
	 * 根据账号级别和类别，获得角色默认串的匹配位置
	 * @param type 账号级别
	 * @param isPrincipal 账号类别
	 * @return position 角色默认串配置位置
	 */
	public int getDefaultRoleType(AccountType type, int isPrincipal) {
		int typePosition;
		if (type.equals(AccountType.MINISTRY)) {// 部级账号
			if (isPrincipal == 1) {// 主账号
				typePosition = 1;
			} else {// 子账号
				typePosition = 2;
			}
		} else if (type.equals(AccountType.PROVINCE)) {// 省级账号
			if (isPrincipal == 1) {// 主账号
				typePosition = 3;
			} else {// 子账号
				typePosition = 4;
			}
		} else if (type.equals(AccountType.MINISTRY_UNIVERSITY)) {// 部属高校账号
			if (isPrincipal == 1) {// 主账号
				typePosition = 5;
			} else {// 子账号
				typePosition = 6;
			}
		} else if (type.equals(AccountType.LOCAL_UNIVERSITY)) {// 地方高校账号
			if (isPrincipal == 1) {// 主账号
				typePosition = 7;
			} else {// 子账号
				typePosition = 8;
			}
		} else if (type.equals(AccountType.DEPARTMENT)) {// 院系账号
			if (isPrincipal == 1) {// 主账号
				typePosition = 9;
			} else {// 子账号
				typePosition = 10;
			}
		} else if (type.equals(AccountType.INSTITUTE)) {// 基地账号
			if (isPrincipal == 1) {// 主账号
				typePosition = 11;
			} else {// 子账号
				typePosition = 12;
			}
		} else if (type.equals(AccountType.EXPERT)) {// 外部专家账号
			typePosition = 13;
		} else if (type.equals(AccountType.TEACHER)) {// 教师账号
			typePosition = 14;
		} else {// 学生账号
			typePosition = 15;
		}
		return typePosition;
	}

	/**
	 * 根据agency类型获得账号级别
	 * @param unitType 待判定的机构类型
	 * @return 对应的账号级别
	 */
	public AccountType getAccountTypeByUnitType(int unitType) {
		switch (unitType) {
			case 1: {// 部级机构
				return AccountType.MINISTRY;
			}
			case 2: {// 省级机构
				return AccountType.PROVINCE;
			}
			case 3: {// 部属高校
				return AccountType.MINISTRY_UNIVERSITY;
			}
			default : {// 地方高校
				return AccountType.LOCAL_UNIVERSITY;
			}
		}
	}
	
	/**
	 * 设置单个账号默认角色
	 * @param account 账号
	 */
	public void setAccountRole(Account account) {
		Map parMap = new HashMap();
		
		// 先清除当前账号所有角色
		parMap.put("accountId", account.getId());
		List<String> arIds = dao.query("select ar.id from AccountRole ar where ar.account.id = :accountId", parMap);
		parMap.remove("accountId");
		for (String entityId : arIds) {
			dao.delete(AccountRole.class, entityId);
		}

		int isPrincipal = account.getIsPrincipal();// 当前账号类别不变，即是否主子账号不会变
		String belongId = this.getBelongIdByAccount(account);
		AccountType accountType;// 账号级别
		// 重置账号级别，根据所属判断账号的级别
		if (isPrincipal == 1) {// 主账号
			Agency agency = dao.query(Agency.class, belongId);
			if (agency != null) {
				accountType = this.getAccountTypeByUnitType(agency.getType());
			} else {
				Department department = dao.query(Department.class, belongId);
				if (department != null) {
					accountType = AccountType.DEPARTMENT;
				} else {
					Institute institute = dao.query(Institute.class, belongId);
					if (institute != null) {
						accountType = AccountType.INSTITUTE;
					} else {// 专家、教师、学生账号级别不变
						accountType = account.getType();
					}
				}
			}
		} else {// 子账号
			Officer officer = this.getOfficerByOfficerId(belongId);
			Department department = officer.getDepartment();
			Institute institute = officer.getInstitute();
			if (department == null && institute == null) {// 校级以上管理员
				accountType = this.getAccountTypeByUnitType(officer.getAgency().getType());
			} else if (department != null && institute == null) {// 院系管理员
				accountType = AccountType.DEPARTMENT;
			} else if (department == null && institute != null) {// 基地管理员
				accountType = AccountType.INSTITUTE;
			} else {// 未知类型
				accountType = AccountType.UNDEFINED;
			}
		}
		account.setType(accountType);
		dao.modify(account);// 更新账号级别
		// 再添加相应的账号默认角色和机构默认角色
		AccountType type = account.getType();// 当前账号级别
		// 根据账号级别和类别获得查询默认角色的15位字符串的匹配位置
		int typePosition = getDefaultRoleType(type, isPrincipal);

		List<Role> roles;
		
		// 设置账号默认账号角色
		parMap.put("index", typePosition);
		roles = dao.query("select r from Role r left join r.roleAgency ra where substring(r.defaultAccountType, :index, 1) = '1' and ra is null", parMap);
		parMap.remove("index");
		for (Role r : roles) {
			AccountRole ar = new AccountRole();
			ar.setAccount(account);
			ar.setRole(r);
			dao.add(ar);
		}
		
		// 设置账号默认机构角色
		if (type.equals(AccountType.MINISTRY) || type.equals(AccountType.PROVINCE) || type.equals(AccountType.MINISTRY_UNIVERSITY) || type.equals(AccountType.LOCAL_UNIVERSITY)) {// 部、省、校级账号
			if (isPrincipal == 1) {// 主账号
				parMap.put("agencyId", "%" + account.getAgency().getId() + "%");
				roles = dao.query("select r from Role r left join r.roleAgency ra where (r.defaultAccountType = '11' or r.defaultAccountType = '10') and ra.defaultAgency.id like :agencyId", parMap);
			} else {// 子账号
				parMap.put("officerId", account.getOfficer().getId());
				List<String> re = dao.query("select o.agency.id from Officer o where o.id = :officerId", parMap);
				parMap.remove("officerId");
				parMap.put("agencyId", "%" + re.get(0) + "%");
				roles = dao.query("select r from Role r left join r.roleAgency ra where (r.defaultAccountType = '11' or r.defaultAccountType = '01') and ra.defaultAgency.id like :agencyId", parMap);
			}
			
			for (Role r : roles) {
				AccountRole ar = new AccountRole();
				ar.setAccount(account);
				ar.setRole(r);
				dao.add(ar);
			}
		}
	}
	
	/**
	 * 设置多个账号默认角色
	 * @param accounts 账号集合
	 */
	public void setAccountsRole(List<Account> accounts) {
		for (Account account : accounts) {
			setAccountRole(account);
		}
	}

	/**
	 * 根据账号找到人员或机构的名字
	 * @param account 账号
	 * @return 人员或机构的名字
	 */
	public String getAccountBelongName(Account account) {
		String belongId = this.getBelongIdByAccount(account);
		if (account.getIsPrincipal() == 0) {
			Officer o = dao.query(Officer.class, belongId);
			if(o != null) {
				Person p = (Person) dao.query(Person.class, o.getPerson().getId());
				if(p != null)
					return p.getName();
			}
			return "未知";
		} else if (account.getIsPrincipal() == 1) {
			//if(account.getType()<=5 && account.getType()!=1)
			if(account.getType().within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)){
				Agency a = dao.query(Agency.class, belongId);
				if(a != null)
					return a.getName();
				else return "未知";
			}else if(account.getType().equals(AccountType.DEPARTMENT)){//院系主账号
				Department d = dao.query(Department.class, belongId);
				if(d != null)
					return d.getName();
				else return "未知";
			}else if(account.getType().equals(AccountType.INSTITUTE)){// 基地主账号
				Institute i = dao.query(Institute.class, belongId);
				if(i != null)
					return i.getName();
				else return "未知";
			}else if(account.getType().equals(AccountType.ADMINISTRATOR) ||account.getType().within(AccountType.EXPERT, AccountType.STUDENT)){// 外部专家账号或教师账号或学生账号
				Person pe = dao.query(Person.class, belongId);
				if(pe !=null)
					return pe.getName();
				else return "未知";
			}
			else return "未知";
		}
		else return "未知";
	}
	
	/**
	 * 系统选项表中获取子选项列表格式为（name, name）
	 * @param str 父选项name
	 * @return 子选项列表格式为（name, name）
	 */
	public Map<String,String> getSONameMapByParentName(String str){
		if(str !=null && str.trim().length()>0){
			Map<String,String> subList = new LinkedHashMap<String,String>();
			String hql="select sys.name from SystemOption sys left join sys.systemOption so where so.name=:name and sys.isAvailable=1 order by sys.code asc";
			Map paraMap = new HashMap();
			paraMap.put("name", str);
			List list = dao.query(hql, paraMap);
			if(list !=null && !list.isEmpty()){
				for (int i=0;i<list.size();i++){
					String keyValue=(String)list.get(i);
					subList.put(keyValue, keyValue);
				}
			}
			return subList;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取系统选项id列表
	 * @param str 父选项name
	 * @return 系统选项id列表
	 */
	public List<String> getSOIdListByParentName(String str){
		Map map = new HashMap();
		map.put("name", str);
		String hql = "select so.id from SystemOption so where so.systemOption.name=:name and so.isAvailable=1 order by so.code asc";
		List<String> list = dao.query(hql, map);
		return list;
	}
	
	/**
	 * 获取系统选项
	 * @param str 父选项name
	 * @return 系统选项
	 */
	public List<SystemOption> getSOByParentName(String str){
		Map map = new HashMap();
		map.put("name", str);
		String hql = "select so from SystemOption so where so.systemOption.name=:name and so.isAvailable=1 order by so.code asc";
		List<SystemOption> list = dao.query(hql, map);
		return list;
	}
	
	/**
	 * 根据省份获取高校格式为（id, name）
	 * @param provId 省份id
	 * @return 高校格式为（id, name）
	 */
	public Map<String,String> getUnivIdNameByProv(String provId){
		if(provId !=null && provId.trim().length()>0){
			Map<String,String> univList = new LinkedHashMap<String,String>();
			String hql="select a.id, a.name from Agency a left join a.province p where p.id=:provId and (a.type=3 or a.type=4) order by a.name asc";
			Map paraMap = new HashMap();
			paraMap.put("provId", provId);
			List list = dao.query(hql, paraMap);
			if(list !=null && !list.isEmpty()){
				for (int i=0;i<list.size();i++){
					Object[] obj = (Object[]) list.get(i);
					String key = (String) obj[0];
					String value = (String) obj[1];
					univList.put(key, value);
				}
			}
			return univList;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据高校获取院系格式为（id, name）
	 * @param univId 高校id
	 * @return  院系格式为（id, name）
	 */
	public Map<String,String> getDeptIdNameByUniv(String univId){
		if(univId !=null && univId.trim().length()>0){
			Map<String,String> deptList = new LinkedHashMap<String,String>();
			String hql="select d.id, d.name from Department d left join d.university u where u.id=:univId order by d.name asc";
			Map paraMap = new HashMap();
			paraMap.put("univId", univId);
			List list = dao.query(hql, paraMap);
			if(list !=null && !list.isEmpty()){
				for (int i=0;i<list.size();i++){
					Object[] obj = (Object[]) list.get(i);
					String key = (String) obj[0];
					String value = (String) obj[1];
					deptList.put(key, value);
				}
			}
			return deptList;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据高校获取基地格式为（id, name）
	 * @param univId 高校id
	 * @return  基地格式为（id, name）
	 */
	public Map<String,String> getInstIdNameByUniv(String univId){
		if(univId !=null && univId.trim().length()>0){
			Map<String,String> instList = new LinkedHashMap<String,String>();
			String hql="select i.id, i.name from Institute i left join i.subjection a where a.id=:univId order by i.name asc";
			Map paraMap = new HashMap();
			paraMap.put("univId", univId);
			List list = dao.query(hql, paraMap);
			if(list !=null && !list.isEmpty()){
				for (int i=0;i<list.size();i++){
					Object[] obj = (Object[]) list.get(i);
					String key = (String) obj[0];
					String value = (String) obj[1];
					instList.put(key, value);
				}
			}
			return instList;
		}else{
			return null;
		}
	}
	/**
	 * 删除账号
	 * @param accountids待删除账号ID集合
	 */
	public void deleteAccount(List<String> accountIds) {
		if (accountIds != null && !accountIds.isEmpty()) {// 如果账号ID非空，则需要删除账号
			Account account;// 账号对象
			Passport passport;// 账号所属通行证
			for (String id : accountIds) {// 遍历账号所有账号，分别删除
				account = (Account) dao.query(Account.class, id);// 查询账号
				passport = (Passport) dao.query(Passport.class, account.getPassport().getId());
				String hql="select a.id from Account a where a.passport.id =:pid";
				Map paraMap = new HashMap();
				paraMap.put("pid", account.getPassport().getId());
				List list = dao.query(hql, paraMap);
				// 删除账号时
				// 删除账号创建的角色
				// 删除账号角色对应关系;删除该账号创建的角色; 该账号创建邮件的账号信息置空(邮件表增加账号所属机构或人员字段)
				// 该账号创建新闻的账号信息置空(新闻表增加账号所属机构或人员字段); 该账号创建通知的账号信息置空(通知表增加账号所属机构或人员字段)
				// 该账号留言的账号信息置空; 该账号日志的accountId和passportId置空
				List<String> roleIds = dao.query("select r.id from Role r where r.account.id = ?", id);//角色
				for (String roleId : roleIds) {
					dao.delete(Role.class, roleId);
				} 
				List mesId = dao.query("select m.id from Message m where m.account.id = ?", id);
				if (mesId.size() > 0) {
					Message message = (Message) dao.query("select m from Message m where m.account.id = ?", id);//留言簿
					if (null != message) {
						message.setAccount(null);
						dao.modify(message);
					}
				}
				List newId = dao.query("select n.id from News n where n.account.id = ?", id);
				if (newId.size() > 0) {
					News news = (News) dao.query("select n from News n where n.account.id = ?", id).get(0);//新闻
					if (null != news) {
						news.setAccount(null);
						dao.modify(news);
					}
				}
				List noticeId = dao.query("select no.id from Notice no where no.account.id = ?", id);
				if (noticeId.size() > 0) {
					Notice notice  = (Notice) dao.query("select no from Notice no where no.account.id = ?", id).get(0);//通知
					
					if (null != notice) {
						notice.setAccount(null);
						dao.modify(notice);
					}
				}
				paraMap.put("accountId", id);
				List<String> logIds = dao.query("select l.id from Log l where l.account.id = :accountId or l.passport.id = :pid ", paraMap);
				if (logIds.size() > 0) {
					for (String logId : logIds) {
						Log log = dao.query(Log.class, logId);
						log.setAccount(null);
						//log.setPassport(null);
					}
				}
				dao.delete(account);// 删除账号
				if (list.size() == 1) {
					dao.delete(passport);
				}else{
					if(passport !=null){
						String pid=passport.getId();
						Map map = new HashMap();
						map.put("id", pid);
						List<Account> accountList = dao.query("select a from Account a where a.passport.id = :id", map);						
						if(accountList!=null){
							Date expireDate=accountList.get(0).getExpireDate();
							Integer status=accountList.get(0).getStatus();
							for(Account account1:accountList){
								if(account1.getExpireDate().after(expireDate)){
									expireDate=account1.getExpireDate();
								}
								if(account1.getStatus() ==1){
									status =1;
								}
							}
							passport.setExpireDate(expireDate);
							passport.setStatus(status);
						}
					}
					dao.modify(passport);
				}
			}
		}
	}
	/**
	 * 由于账号的删除或者账号信息的改变而相对的改变passport中的信息
	 * @param passportIds 改变信息的账号所对应的通行id
	 */	
	
	public void updatePassport(List<String> passportIds){
		if(passportIds!=null){
			Passport passport;
			for(String pid:passportIds){
				passport= (Passport) dao.query(Passport.class, pid);
				Map map = new HashMap();
				map.put("id", pid);
				List<Account> accountList = dao.query("select a from Account a where a.passport.id = :id", map);
				if(accountList!=null){
					Date expireDate=accountList.get(0).getExpireDate();
					Integer status=accountList.get(0).getStatus();
					for(Account account:accountList){
						if(account.getExpireDate().after(expireDate)){
							expireDate=account.getExpireDate();
						}
						if(account.getStatus() ==1){
							status =1;
						}
					}
					passport.setExpireDate(expireDate);
					passport.setStatus(status);
					dao.modify(passport);
				}		
			}
		}
	}
	
	/**
	 * 由于账号的删除或者账号信息的改变而相对的改变passport中的信息
	 * @param passport 改变信息的账号所对应的通行证
	 */	
	
	public void updatePassport(String passportId){
		if(passportId!=null){
			Passport passport;
			passport= (Passport) dao.query(Passport.class, passportId);
			Map map = new HashMap();
			map.put("id", passportId);
			List<Account> accountList = dao.query("select a from Account a where a.passport.id = :id", map);
			if(accountList!=null){
				Date expireDate=accountList.get(0).getExpireDate();
				Integer status=accountList.get(0).getStatus();
				for(Account account:accountList){
					if(account.getExpireDate().after(expireDate)){
						expireDate=account.getExpireDate();
					}
					if(account.getStatus() ==1){
						status =1;
					}
				}
				passport.setExpireDate(expireDate);
				passport.setStatus(status);
				dao.modify(passport);
			}		
		
		}
	}
	
	/**
	 * 处理字串，多个以英文分号与空格隔开
	 * @param originString 原始字串
	 * @return 处理后字串
	 */
	public String MutipleToFormat(String originString){
		if(null == originString || originString.trim().isEmpty()){
			return originString;
		}else{
			originString = originString.replaceAll("(\\s)+", " ");//消除多余空格
			originString = originString.replaceAll("；", ";");//统一英文分号
			originString = originString.replaceAll("(\\s)*;(\\s)*", "; ");//英文分号与空格隔开
			return originString;
		}
	}
	
	/**
	 * 获取文件大小
	 * @param fileLength
	 * @return 文件大小字符串
	 */
	public String accquireFileSize(long fileLength) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileLength < 1024) {
			fileSizeString = df.format((double) fileLength) + "B";
		} else if (fileLength < 1048576) {
			fileSizeString = df.format((double) fileLength / 1024) + "K";
		} else if (fileLength < 1073741824) {
			fileSizeString = df.format((double) fileLength / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileLength / 1073741824) + "G";
		}
		return fileSizeString;
	}
	/**
	 * 根据登陆信息、审核结果、审核状态、审核意见获得审核信息对象
	 * @param loginer	登陆信息
	 * @param result	操作结果		1:不同意	2：同意
	 * @param status	操作状态		1:退回	2：暂存	3：提交
	 * @param opinion	审核意见
	 * @return
	 */
	public AuditInfo getAuditInfo(LoginInfo loginer, int result, int status, String opinion){
		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setAuditDate(new Date());
		auditInfo.setAuditOpinion(opinion);
		auditInfo.setAuditResult(result);
		auditInfo.setAuditStatus(status);
		AccountType type = loginer.getCurrentType();
		if (type.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {// 部级、省级、校级账号
			Agency agency = (Agency)dao.query(Agency.class, loginer.getCurrentBelongUnitId());
			auditInfo.setAuditorAgency(agency);
			if(loginer.getIsPrincipal() == 1){//主账号
				auditInfo.setAuditorName(loginer.getCurrentBelongUnitName());
			}else{//子账号
				auditInfo.setAuditorName(loginer.getCurrentPersonName());
				Officer officer = dao.query(Officer.class, loginer.getOfficer().getId());
				auditInfo.setAuditor(officer);
			}
		}else if(type.equals(AccountType.DEPARTMENT)) {// 院系账号
			Department department = (Department)dao.query(Department.class, loginer.getCurrentBelongUnitId());
			auditInfo.setAuditorDept(department);
			auditInfo.setAuditorInst(null);
			if(loginer.getIsPrincipal() == 1){//主账号
				auditInfo.setAuditorName(loginer.getCurrentBelongUnitName());
			}else{//子账号
				auditInfo.setAuditorName(loginer.getCurrentPersonName());
				Officer officer = dao.query(Officer.class, loginer.getOfficer().getId());
				auditInfo.setAuditor(officer);
			}
		}else if(type.equals(AccountType.INSTITUTE)) {// 基地账号
			Institute institute = (Institute)dao.query(Institute.class, loginer.getCurrentBelongUnitId());
			auditInfo.setAuditorDept(null);
			auditInfo.setAuditorInst(institute);
			if(loginer.getIsPrincipal() == 1){//主账号
				auditInfo.setAuditorName(loginer.getCurrentBelongUnitName());
			}else{//子账号
				auditInfo.setAuditorName(loginer.getCurrentPersonName());
				Officer officer = dao.query(Officer.class, loginer.getOfficer().getId());
				auditInfo.setAuditor(officer);
			}
		}else if(type.within(AccountType.EXPERT, AccountType.STUDENT)){//研究人员（外部专家，教师，学生）
			Officer officer = null;
			auditInfo.setAuditorName(loginer.getCurrentPersonName());
			Person auditor = dao.query(Person.class, loginer.getPerson().getId());
			List officerIds = dao.query("select o.id from Officer o, Person p where o.person.id = p.id and p.id = ?",auditor.getId());
			if (officerIds.size() != 0) {
				String officerId = (String) officerIds.get(0);
				officer = dao.query(Officer.class, officerId);
			}
			auditInfo.setAuditor(officer);
			if(loginer.getCurrentBelongUnitId() != null){
				Agency unit = (Agency)dao.query(Agency.class, loginer.getCurrentBelongUnitId());
				auditInfo.setAuditorAgency(unit);
			}else{
				auditInfo.setAuditorAgency(null);
			}
		}
		return auditInfo;
	}
	
	
	/**
	 * 根据账号类别和研究人员账号所属人员id获得该研究人员国有的机构
	 * @param  personId 研究人员id
	 * @param accountType 账号类别	8：外部专家	9：教师 10：学生
	 */
	public Map<String,String> getUnitDetailByAccountInfo(String personId, AccountType accountType){
		Map<String, String> map = new HashMap<String, String>();
		Map hqlMap = new HashMap();
		hqlMap.put("personId", personId);
		StringBuffer hql = new StringBuffer();
		if(accountType.equals(AccountType.EXPERT)){//外部专家
			hql.append("select exp.agencyName, exp.divisionName, '', exp.agencyName, exp.divisionName,'', exp from Expert exp where exp.person.id=:personId");
		}else if(accountType.equals(AccountType.TEACHER)){//教师
			hql.append("select uni.id, dep.id, inst.id, uni.name, dep.name, inst.name, tea from Teacher tea left join fetch tea.university uni " +
			"left join fetch tea.department dep left join fetch tea.institute inst where tea.person.id=:personId");
		}else if(accountType.equals(AccountType.STUDENT)){//学生
			hql.append("select uni.id, dep.id, inst.id, uni.name, dep.name, inst.name, stu from Student stu left join fetch stu.university uni " +
			"left join fetch stu.department dep left join fetch stu.institute inst where stu.person.id=:personId");
		}
		List units = dao.query(hql.toString(), hqlMap);
		if(units.size() > 0){
			for(int i = 0; i < units.size(); i++){
				Object[] unitDetail = (Object[])units.get(i);
				String unitId = "";//机构id
				String unitName = "";//机构名称
				unitId = unitId + ((unitDetail[0] != null && unitDetail[0].toString().trim().length() > 0) ? unitDetail[0].toString().trim() : " ") + "; ";
				unitId = unitId + ((unitDetail[1] != null && unitDetail[1].toString().trim().length() > 0) ? unitDetail[1].toString().trim() : " ") + "; ";
				unitId = unitId + ((unitDetail[2] != null && unitDetail[2].toString().trim().length() > 0) ? unitDetail[2].toString().trim() : " ");
				unitName = unitName + ((unitDetail[3] != null && unitDetail[3].toString().trim().length() > 0) ? unitDetail[3].toString().trim() : "") + " ";
				unitName = unitName + ((unitDetail[4] != null && unitDetail[4].toString().trim().length() > 0) ? unitDetail[4].toString().trim() : "") ;
				unitName = unitName + ((unitDetail[5] != null && unitDetail[5].toString().trim().length() > 0) ? unitDetail[5].toString().trim() : "") ;
				map.put(unitId, unitName);
			}
		}
		return map;
	}
	
	/**
	 * 根据新添人员进行入库和关联处理
	 * @param personInfoJson 存储人员信息的Map对象 至少要有的键有：idcardType, idcardNumber, personName, personType, gender, agencyName, agencyId, divisionName, divisionType
	 * @return 存储人员id及所在机构id的Map对象 键有：personId, researcherId, divisionId
	 */
	public Map doWithNewPerson(Map personInfoJson){
		if(personInfoJson == null){
			return null;
		}
		String idcardType = (String)personInfoJson.get("idcardType");
		String idcardNumber = (String)personInfoJson.get("idcardNumber");
		String personName = (String)personInfoJson.get("personName");
		Integer personType = (Integer)personInfoJson.get("personType");
		String gender = (String)personInfoJson.get("gender");
		String agencyName = (String)personInfoJson.get("agencyName");
		String agencyId = (String)personInfoJson.get("agencyId");
		String divisionName = (String)personInfoJson.get("divisionName");
		Integer divisionType = (Integer)personInfoJson.get("divisionType");
		Object workMonthPerYear = personInfoJson.get("workMonthPerYear");
		Object specialistTitle = personInfoJson.get("specialistTitle");
		String personId = "";
		String researcherId = "";
		String divisionId = "";
		Person person = null;
		Map idsMap = new HashMap();
		Map map = new HashMap();
		map.put("agencyId", agencyId);
		map.put("divisionName", divisionName);
		//首先找部门
		if(divisionType == 1){//研究基地
			List instIds = dao.query("select inst.id from Institute inst where inst.subjection.id=:agencyId and inst.name=:divisionName", map);
			if(instIds != null && !instIds.isEmpty()){//找到研究基地
				divisionId = (String)instIds.get(0);
			}else{//找不到研究基地则新建
				Institute institute= new Institute();
				institute.setName(divisionName);
				institute.setSubjection((Agency)dao.query(Agency.class, agencyId));
				institute.setType((SystemOption)soDao.query("researchAgencyType", "06"));
				divisionId = dao.add(institute);
			}
		}else if(divisionType == 2){//院系
			List deptIds = dao.query("select dept.id from Department dept where dept.university.id=:agencyId and dept.name=:divisionName", map);
			if(deptIds != null && !deptIds.isEmpty()){//找到院系
				divisionId = (String)deptIds.get(0);
			}else{//找不到院系则新建
				Department department = new Department();
				department.setName(divisionName);
				department.setUniversity((Agency)dao.query(Agency.class, agencyId));
				divisionId = dao.add(department);
			}
		}
		//然后找人
		//第一步根据证件类别、证件号、姓名、性别去找是否存在此人
		map.put("idcardType", idcardType);
		map.put("idcardNumber", idcardNumber);
		map.put("personName", personName);
		map.put("gender", gender);
		StringBuffer hql = new StringBuffer("select per from Person per where per.idcardType=:idcardType and per.idcardNumber=:idcardNumber and per.name=:personName and (per.gender is null or per.gender=:gender)");
		List persons= dao.query(hql.toString(), map);
		map.put("divisionId", divisionId);
		if(persons != null && !persons.isEmpty()){//存在此人
			person = (Person)persons.get(0);
			map.put("personId", person.getId());
			if(personType == 1){//教师
				// 根据人员主表id、学校、部门去找是否存在此教师
				hql = new StringBuffer("select tea from Teacher tea left outer join fetch tea.person per where per.id=:personId and tea.university.id=:agencyId");
				if(divisionType == 1){//研究基地
					hql.append(" and tea.institute.id =:divisionId");
				}else if(divisionType == 2){//院系
					hql.append(" and tea.department.id =:divisionId");
				}else{
					hql.append(" and 1=0");
				}
				List teachers1 = dao.query(hql.toString(), map);
				if(teachers1 != null && !teachers1.isEmpty()){//存在此教师，则教师信息关联
					Teacher teacher1 = (Teacher)teachers1.get(0);
					researcherId = teacher1.getId();
				}else{
					//根据人员主表id、学校去找是否存在此教师
					hql = new StringBuffer("select tea from Teacher tea left outer join fetch tea.person per where per.id=:personId and tea.university.id=:agencyId");
					List teachers2 = dao.query(hql.toString(), map);
					if(teachers2 != null && !teachers2.isEmpty()){//存在此教师,则对教师信息进行相关处理。
						Teacher teacher2 = (Teacher)teachers2.get(0);
						if(teacher2.getDepartment() == null && teacher2.getInstitute() == null){//无部门信息则补上
							if(divisionType == 1){//研究基地
								teacher2.setInstitute((Institute)dao.query(Institute.class, divisionId));
							}else if(divisionType == 2){//院系
								teacher2.setDepartment((Department)dao.query(Department.class, divisionId));
							}
							dao.modify(teacher2);
							researcherId = teacher2.getId();
						}else{//有部门信息,则新建一条关联此人的兼职教师
							Teacher teacher3 = new Teacher();
							teacher3.setPerson(person);
							teacher3.setUniversity((Agency)dao.query(Agency.class, agencyId));
							if(divisionType == 1){//研究基地
								teacher3.setInstitute((Institute)dao.query(Institute.class, divisionId));
							}else if(divisionType == 2){//院系
								teacher3.setDepartment((Department)dao.query(Department.class, divisionId));
							}
							teacher3.setType("兼职人员");
							if(workMonthPerYear != null && (Integer)workMonthPerYear > 0){
								teacher3.setWorkMonthPerYear((Integer)workMonthPerYear);
							}
							researcherId = dao.add(teacher3);
						}
					}else{//不存在此教师，则新建专职教师
						Teacher teacher4 = new Teacher();
						teacher4.setPerson(person);
						teacher4.setUniversity((Agency)dao.query(Agency.class, agencyId));
						if(divisionType == 1){//研究基地
							teacher4.setInstitute((Institute)dao.query(Institute.class, divisionId));
						}else if(divisionType == 2){//院系
							teacher4.setDepartment((Department)dao.query(Department.class, divisionId));
						}
						teacher4.setType("专职人员");
						if(workMonthPerYear != null && (Integer)workMonthPerYear > 0){
							teacher4.setWorkMonthPerYear((Integer)workMonthPerYear);
						}
						researcherId = dao.add(teacher4);
					}
				}
			}else if(personType == 2){//外部专家
				// 根据人员主表id、学校、部门去找是否存在此外部专家
				hql = new StringBuffer("select exp from Expert exp left outer join fetch exp.person per where per.id=:personId and exp.agencyName=:agencyName and exp.divisionName=:divisionName");
				map.put("agencyName", agencyName);
				List experts1 = dao.query(hql.toString(), map);
				if(experts1 != null && !experts1.isEmpty()){//存在此外部专家，则将人员信息补上，教师信息关联
					Expert expert1 = (Expert)experts1.get(0);
					researcherId = expert1.getId();
				}else{
					//根据人员主表id、学校去找是否存在此外部专家
					hql = new StringBuffer("select exp from Expert exp left outer join fetch exp.person per where per.id=:personId and exp.agencyName=:agencyName");
					List experts2 = dao.query(hql.toString(), map);
					if(experts2 != null && !experts2.isEmpty()){//存在此外部专家,则将人员信息补上，并对专家信息进行相关处理。
						Expert expert2 = (Expert)experts2.get(0);
						if(expert2.getDivisionName() == null){//无部门信息则补上
							expert2.setDivisionName(divisionName);	
							dao.modify(expert2);
							researcherId = expert2.getId();
						}else{//有部门信息,则新建一条关联此人的兼职外部专家
							Expert expert3 = new Expert();
							expert3.setPerson(person);
							expert3.setAgencyName(agencyName);
							expert3.setDivisionName(divisionName);
							expert3.setType("兼职人员");
							researcherId = dao.add(expert3);
						}
					}else{//不存在此外部专家，则新建专职外部专家
						Expert expert4 = new Expert();
						expert4.setPerson(person);
						expert4.setAgencyName(agencyName);
						expert4.setDivisionName(divisionName);
						expert4.setType("专职人员");
						researcherId = dao.add(expert4);
					}
				}
			}else if(personType== 3){//学生
				// 根据人员主表id、学校、部门去找是否存在此学生
				hql = new StringBuffer("select stu from Student stu left outer join fetch stu.person per where per.id=:personId and stu.university.id=:agencyId");
				if(divisionType == 1){//研究基地
					hql.append(" and stu.institute.id =:divisionId");
				}else if(divisionType == 2){//院系
					hql.append(" and stu.department.id =:divisionId");
				}else{
					hql.append(" and 1=0");
				}
				List students1 = dao.query(hql.toString(), map);
				if(students1 != null && !students1.isEmpty()){//存在此学生，则学生信息关联
					Student student1 = (Student)students1.get(0);
					researcherId = student1.getId();
				}else{
					//根据人员主表id、学校去找是否存在此学生
					hql = new StringBuffer("select stu from Teacher stu left outer join fetch stu.person per where per.id=:personId and stu.university.id=:agencyId");
					List students2 = dao.query(hql.toString(), map);
					if(students2 != null && !students2.isEmpty()){//存在此学生,则对学生信息进行相关处理。
						Student student2 = (Student)students2.get(0);
						if(student2.getDepartment() == null && student2.getInstitute() == null){//无部门信息则补上
							if(divisionType == 1){//研究基地
								student2.setInstitute((Institute)dao.query(Institute.class, divisionId));
							}else if(divisionType == 2){//院系
								student2.setDepartment((Department)dao.query(Department.class, divisionId));
							}
							dao.modify(student2);
							researcherId = student2.getId();
						}else{//有部门信息,直接关联，不做处理
							researcherId = student2.getId();
						}
					}else{//不存在此学生，则新建学生
						Student student3 = new Student();
						student3.setPerson(person);
						student3.setUniversity((Agency)dao.query(Agency.class, agencyId));
						if(specialistTitle != null){
							student3.setType((String)specialistTitle);
						}else{
							student3.setType("硕士生");
						}
						student3.setStatus("0");
						if(divisionType == 1){//研究基地
							student3.setInstitute((Institute)dao.query(Institute.class, divisionId));
						}else if(divisionType == 2){//院系
							student3.setDepartment((Department)dao.query(Department.class, divisionId));
						}
						researcherId = dao.add(student3);
					}
				}
			}
		}else{//不存在此人，则执行第二步找人
			map.remove("idcardType");
			map.remove("idcardNumber");
			map.remove("gender");
			if(personType == 1){//教师
				//第二步	 根据学校、部门、姓名及身份证为空或不合法来去找是否存在此教师
				map.put("agencyId", agencyId);
				map.put("divisionId", divisionId);
				hql = new StringBuffer("select tea from Teacher tea left outer join fetch tea.person per where per.name=:personName and tea.university.id=:agencyId" +
						" and (per.idcardNumber is null or (lengthb(per.idcardNumber) != 15 and lengthb(per.idcardNumber) != 18))");
				if(divisionType == 1){//研究基地
					hql.append(" and tea.institute.id =:divisionId");
				}else if(divisionType == 2){//院系
					hql.append(" and tea.department.id =:divisionId");
				}else{
					hql.append(" and 1=0");
				}
				List teachers1 = dao.query(hql.toString(), map);
				if(teachers1 != null && !teachers1.isEmpty()){//存在此教师，则将人员信息补上，教师信息关联
					Teacher teacher1 = (Teacher)teachers1.get(0);
					person = teacher1.getPerson();
					person.setIdcardType(idcardType);
					person.setIdcardNumber(idcardNumber);
					dao.modify(person);
					researcherId = teacher1.getId();
				}else{//不存在此人，则执行第三步找人
					//第三步 根据学校、姓名及身份证为空或不合法来去找是否存在此教师
					hql = new StringBuffer("select tea from Teacher tea left outer join fetch tea.person per where per.name=:personName and tea.university.id=:agencyId" +
							" and (per.idcardNumber is null or (lengthb(per.idcardNumber) != 15 and lengthb(per.idcardNumber) != 18))");
					List teachers2 = dao.query(hql.toString(), map);
					if(teachers2 != null && !teachers2.isEmpty()){//存在此教师,则将人员信息补上，并对教师信息进行相关处理。
						Teacher teacher2 = (Teacher)teachers2.get(0);
						person = teacher2.getPerson();
						person.setIdcardType(idcardType);
						person.setIdcardNumber(idcardNumber);
						if(teacher2.getDepartment() == null && teacher2.getInstitute() == null){//无部门信息则补上
							if(divisionType == 1){//研究基地
								teacher2.setInstitute((Institute)dao.query(Institute.class, divisionId));
							}else if(divisionType == 2){//院系
								teacher2.setDepartment((Department)dao.query(Department.class, divisionId));
							}
							dao.modify(person);
							dao.modify(teacher2);
							researcherId = teacher2.getId();
						}else{//有部门信息,则新建一条关联此人的兼职教师
							Teacher teacher3 = new Teacher();
							teacher3.setPerson(person);
							teacher3.setUniversity((Agency)dao.query(Agency.class, agencyId));
							if(divisionType == 1){//研究基地
								teacher3.setInstitute((Institute)dao.query(Institute.class, divisionId));
							}else if(divisionType == 2){//院系
								teacher3.setDepartment((Department)dao.query(Department.class, divisionId));
							}
							teacher3.setType("兼职人员");
							if(workMonthPerYear != null && (Integer)workMonthPerYear > 0){
								teacher2.setWorkMonthPerYear((Integer)workMonthPerYear);
							}
							dao.modify(person);
							researcherId = dao.add(teacher3);
						}
					}else{//不存在此教师，则执行第四步新建
						//第四步新建
						Person person1 = new Person();
						person1.setIdcardType(idcardType);
						person1.setIdcardNumber(idcardNumber);
						person1.setName(personName);
						person1.setGender(gender);
						Teacher teacher4 = new Teacher();
						teacher4.setPerson(person1);
						teacher4.setUniversity((Agency)dao.query(Agency.class, agencyId));
						if(divisionType == 1){//研究基地
							teacher4.setInstitute((Institute)dao.query(Institute.class, divisionId));
						}else if(divisionType == 2){//院系
							teacher4.setDepartment((Department)dao.query(Department.class, divisionId));
						}
						teacher4.setType("专职人员");
						if(workMonthPerYear != null && (Integer)workMonthPerYear > 0){
							teacher4.setWorkMonthPerYear((Integer)workMonthPerYear);
						}
						personId = dao.add(person1);
						researcherId = dao.add(teacher4);
						person = (Person)dao.query(Person.class, personId);
					}
				}
			}else if(personType == 2){//外部专家
				//第二步	 根据学校、部门、姓名及身份证为空或不合法来去找是否存在此外部专家
				map.put("agencyName", agencyName);
				map.put("divisionName", divisionName);
				hql = new StringBuffer("select exp from Expert exp left outer join fetch exp.person per where per.name=:personName and exp.agencyName=:agencyName and exp.divisionName=:divisionName" +
						" and (per.idcardNumber is null or (lengthb(per.idcardNumber) != 15 and lengthb(per.idcardNumber) != 18))");
				List experts1 = dao.query(hql.toString(), map);
				if(experts1 != null && !experts1.isEmpty()){//存在此外部专家，则将人员信息补上，教师信息关联
					Expert expert1 = (Expert)experts1.get(0);
					person = expert1.getPerson();
					person.setIdcardType(idcardType);
					person.setIdcardNumber(idcardNumber);
					dao.modify(person);
					researcherId = expert1.getId();
				}else{//不存在此人，则执行第三步找人
					//第三步 根据学校、姓名及身份证为空或不合法来去找是否存在此外部专家
					hql = new StringBuffer("select exp from Expert exp left outer join fetch exp.person per where per.name=:personName and exp.agencyName=:agencyName" +
					" and (per.idcardNumber is null or (lengthb(per.idcardNumber) != 15 and lengthb(per.idcardNumber) != 18))");
					List experts2 = dao.query(hql.toString(), map);
					if(experts2 != null && !experts2.isEmpty()){//存在此外部专家,则将人员信息补上，并对专家信息进行相关处理。
						Expert expert2 = (Expert)experts2.get(0);
						person = expert2.getPerson();
						person.setIdcardType(idcardType);
						person.setIdcardNumber(idcardNumber);
						if(expert2.getDivisionName() == null){//无部门信息则补上
							expert2.setDivisionName(divisionName);	
							dao.modify(person);
							dao.modify(expert2);
							researcherId = expert2.getId();
						}else{//有部门信息,则新建一条关联此人的兼职外部专家
							Expert expert3 = new Expert();
							expert3.setPerson(person);
							expert3.setAgencyName(agencyName);
							expert3.setDivisionName(divisionName);
							expert3.setType("兼职人员");
							dao.modify(person);
							researcherId = dao.add(expert3);
						}
					}else{//不存在此外部专家，则执行第四步新建
						//第四步新建
						Person person1 = new Person();
						person1.setIdcardType(idcardType);
						person1.setIdcardNumber(idcardNumber);
						person1.setName(personName);
						person1.setGender(gender);
						Expert expert4 = new Expert();
						expert4.setPerson(person1);
						expert4.setAgencyName(agencyName);
						expert4.setDivisionName(divisionName);
						expert4.setType("专职人员");
						personId = dao.add(person1);
						researcherId = dao.add(expert4);
						person = (Person)dao.query(Person.class, personId);
					}
				}
			}else if(personType == 3){//学生
				//第二步	 根据学校、部门、姓名及身份证为空或不合法来去找是否存在此学生
				map.put("agencyId", agencyId);
				map.put("divisionId", divisionId);
				hql = new StringBuffer("select stu from Student stu left outer join fetch stu.person per where per.name=:personName and stu.university.id=:agencyId" +
						" and (per.idcardNumber is null or (lengthb(per.idcardNumber) != 15 and lengthb(per.idcardNumber) != 18))");
				if(divisionType == 1){//研究基地
					hql.append(" and stu.institute.id =:divisionId");
				}else if(divisionType == 2){//院系
					hql.append(" and stu.department.id =:divisionId");
				}else{
					hql.append(" and 1=0");
				}
				List students1 = dao.query(hql.toString(), map);
				if(students1 != null && !students1.isEmpty()){//存在此学生，则将人员信息补上，学生信息关联
					Student student1 = (Student)students1.get(0);
					person = student1.getPerson();
					person.setIdcardType(idcardType);
					person.setIdcardNumber(idcardNumber);
					dao.modify(person);
					researcherId = student1.getId();
				}else{//不存在此学生，则执行第三步找人
					//第三步 根据学校、姓名及身份证为空或不合法来去找是否存在此学生
					hql = new StringBuffer("select stu from Student stu left outer join fetch stu.person per where per.name=:personName and stu.university.id=:agencyId" +
							" and (per.idcardNumber is null or (lengthb(per.idcardNumber) != 15 and lengthb(per.idcardNumber) != 18))");
					List students2 = dao.query(hql.toString(), map);
					if(students2 != null && !students2.isEmpty()){//存在此学生,则将人员信息补上，并对学生信息进行相关处理。
						Student student2 = (Student)students2.get(0);
						person = student2.getPerson();
						person.setIdcardType(idcardType);
						person.setIdcardNumber(idcardNumber);
						if(student2.getDepartment() == null && student2.getInstitute() == null){//无部门信息则补上
							if(divisionType == 1){//研究基地
								student2.setInstitute((Institute)dao.query(Institute.class, divisionId));
							}else if(divisionType == 2){//院系
								student2.setDepartment((Department)dao.query(Department.class, divisionId));
							}
							dao.modify(person);
							dao.modify(student2);
							researcherId = student2.getId();
						}else{//有部门信息,则不做任何操作
							dao.modify(person);
							researcherId = dao.add(student2);
						}
					}else{//不存在此学生，则执行第四步新建
						//第四步新建
						Person person1 = new Person();
						person1.setIdcardType(idcardType);
						person1.setIdcardNumber(idcardNumber);
						person1.setName(personName);
						person1.setGender(gender);
						Student student3 = new Student();
						student3.setPerson(person1);
						if(specialistTitle != null){
							student3.setType((String)specialistTitle);
						}else{
							student3.setType("硕士生");
						}
						student3.setStatus("0");
						student3.setUniversity((Agency)dao.query(Agency.class, agencyId));
						if(divisionType == 1){//研究基地
							student3.setInstitute((Institute)dao.query(Institute.class, divisionId));
						}else if(divisionType == 2){//院系
							student3.setDepartment((Department)dao.query(Department.class, divisionId));
						}
						personId = dao.add(person1);
						researcherId = dao.add(student3);
						person = (Person)dao.query(Person.class, personId);
					}
				}
			}
		}
		if(person != null){
			if(person.getGender() == null){//性别为空，设置性别
				person.setGender(gender);
			}
			if(specialistTitle != null && ((String)specialistTitle).trim().length() > 0){//传入变量存在专业职称信息
				map.clear();
				map.put("personId", person.getId());
				hql = new StringBuffer("select aca from Academic aca where aca.person.id=:personId");
				List academics = dao.query(hql.toString(), map);
				if(academics != null && !academics.isEmpty()){//该人员有学术信息
					Academic academic = (Academic)academics.get(0);
					if(academic.getSpecialityTitle() == null){//该人员无专业职称信息
						academic.setSpecialityTitle((String)specialistTitle);
						dao.modify(academic);
					}
				}else{//该人员无学术信息
					Academic academic = new Academic();
					academic.setPerson(person);
					academic.setSpecialityTitle((String)specialistTitle);
					dao.add(academic);
				}
			}
		}
		idsMap.put("personId", person.getId());
		idsMap.put("researcherId", researcherId);
		idsMap.put("divisionId", divisionId);
		return idsMap;
	}
	//*****************************end*****************************/

	public String getPropertiesValue(Class clazz, String fileName, String key) {
		Properties prop = new Properties(); 
		InputStream in = clazz.getResourceAsStream(fileName); 
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key).trim();
	}
	
	/**
	 * DMSS文件上传
	 * @param filePath 文件路径
	 * @param form 文件上传附加表单信息
	 * @return dmss的文档ID
	 */
	public String flushToDmss(String filePath ,ThirdUploadForm form){
		if(dmssService.getOn() == 1 && dmssService.getStatus()){
			try {
				return dmssService.upload(filePath, form);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 获取上传后的文件的相对目录  （比如 filePath为"upload/award/moesocial/app/2001/hello.doc",则返回目录为"award/moesocial/app/2001"）
	 * @param filePath 上传后的文件相对路径
	 * @return 上传后的文件的相对目录  
	 */
	public String getRelativeFileDir(String filePath){
		if(filePath.startsWith("/")){
			filePath = filePath.substring(1);
		}
		return filePath.substring(filePath.indexOf("/")+1,filePath.lastIndexOf("/"));
	}
	
	
	
	/**
	 * 根据SMDB文档存储的相对路径，确定在DMSS上存储的相对目录
	 * @param filePath
	 * @return
	 */
	public String getDmssCategory(String filePath){
		return "/SMDB/"+getRelativeFileDir(filePath);
	}
	
	
	
	/**
	 * 获取文件名
	 * @param filePath 上传后的文件相对路径
	 * @return 文件名 
	 */
	public String getFileName(String filePath){
		return filePath.substring(filePath.lastIndexOf("/")+1);
	}
	
	
	/**
	 * 获取文件标题
	 * @param filePath 上传后的文件相对路径
	 * @return 文件名  (比如  /aaa/bb.txt 返回 bb.txt)
	 */
	public String getFileTitle(String filePath){
		if(filePath.lastIndexOf(".")>-1){
			return filePath.substring(filePath.lastIndexOf("/")+1,filePath.lastIndexOf("."));
		}else {
			return getFileName(filePath);
		}
	}

	/**
	 * 根据账号获取账号所属Id
	 * @param account
	 */
	public String getBelongIdByAccount(Account account){
		String belongId = "";//账号所属id，若为教师，则是personId，若是机构，则是机构id，若是管理者，则是officorId
		AccountType accountType = account.getType();
		if (accountType.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {
			if (account.getIsPrincipal() == 1) {
				belongId = account.getAgency().getId();
			} else {
				belongId = account.getOfficer().getId(); 
			}
		} else if (accountType.equals(AccountType.DEPARTMENT)) {
			if (account.getIsPrincipal() == 1) {
				belongId = account.getDepartment().getId();
			} else {
				belongId = account.getOfficer().getId();
			}
		} else if (accountType.equals(AccountType.INSTITUTE)) {
			if (account.getIsPrincipal() == 1) {
				belongId = account.getInstitute().getId();
			} else {
				belongId = account.getOfficer().getId();
			}
		} else if (accountType.within(AccountType.EXPERT, AccountType.STUDENT) || accountType.equals(AccountType.ADMINISTRATOR)) {
			belongId = account.getPerson().getId();
		}
		return belongId;
	}
	
	/**
	 * 根据账号获取账号所属Id
	 * @param loginer
	 */
	public String getBelongIdByLoginer(LoginInfo loginer){
		String belongId = "";//账号所属id，若为教师，则是personId，若是机构，则是机构id，若是管理者，则是officorId
		AccountType accountType = loginer.getCurrentType();
		if (accountType.within(AccountType.MINISTRY, AccountType.LOCAL_UNIVERSITY)) {
			if (loginer.getIsPrincipal() == 1) {
				belongId = loginer.getAgency().getId();
			} else {
				belongId = loginer.getOfficer().getId(); 
			}
		} else if (accountType.equals(AccountType.DEPARTMENT)) {
			if (loginer.getIsPrincipal() == 1) {
				belongId = loginer.getDepartment().getId();
			} else {
				belongId = loginer.getOfficer().getId();
			}
		} else if (accountType.equals(AccountType.INSTITUTE)) {
			if (loginer.getIsPrincipal() == 1) {
				belongId = loginer.getInstitute().getId();
			} else {
				belongId = loginer.getOfficer().getId();
			}
		} else if (accountType.within(AccountType.EXPERT, AccountType.STUDENT) || accountType.equals(AccountType.ADMINISTRATOR)) {
			belongId = loginer.getPerson().getId();
		}
		return belongId;
	}
	
	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
}
