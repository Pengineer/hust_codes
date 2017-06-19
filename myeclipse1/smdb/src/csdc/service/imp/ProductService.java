package csdc.service.imp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.csdc.domain.fm.ThirdCheckInForm;
import org.csdc.domain.fm.ThirdUploadForm;

import csdc.bean.Account;
import csdc.bean.Agency;
import csdc.bean.AwardApplication;
import csdc.bean.AwardGranted;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Product;
import csdc.bean.ProjectAnninspection;
import csdc.bean.ProjectAnninspectionProduct;
import csdc.bean.ProjectEndinspection;
import csdc.bean.ProjectEndinspectionProduct;
import csdc.bean.ProjectGranted;
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectMidinspectionProduct;
import csdc.bean.ProjectProduct;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.service.IProductService;
import csdc.tool.ApplicationContainer;
import csdc.tool.FileTool;
import csdc.tool.bean.AccountType;
/**
 * 成果管理
 */
public class ProductService extends BaseService implements IProductService {
	
	//========================================================================================
	// 1.成果相关业务方法
	//========================================================================================
	/**
	 * 成果查看范围
	 * @param account 当前账号对象
	 * @return 查询语句、参数map
	 * type 1:系统管理员; 2:部级账号; 3:省级账号; 4:部属高校; 5:地方高校; 6:高校院系; 7:研究基地; 8:外部专家; 9:内部专家
	 * isPrincipal 1:主账号; 2:子账号
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getScopeHql(Account account, Map parMap){
		String hql = "";
		String nonCon = " and 1=0 ";
		String belongId = this.getBelongIdByAccount(account);
		AccountType type = account.getType();
		int isPrincipal = account.getIsPrincipal();
		//省级账号以下且为主账号，获取账号所属id，
		if(type.compareTo(AccountType.MINISTRY)>0 && isPrincipal == 1) {
			parMap.put("belongId", belongId);
		}
		//根据账号类型获取限定子句
		switch(type){
			case ADMINISTRATOR : {//系统管理员
				break;
			} 
			case MINISTRY : {//部级账号
				//如果非主账号
				if(isPrincipal != 1) {
					String ministryId = this.getAgencyIdByOfficerId(belongId);
					hql = (null != ministryId) ? hql : nonCon;
				}
				break;
			}
			case PROVINCE:{//省级账号
				if(isPrincipal == 1){
					hql = " and uni.type = 4 and uni.subjection.id = :belongId ";
				} else {
					String provinceId = this.getAgencyIdByOfficerId(belongId);
					if(null != provinceId) {
						hql = " and uni.type = 4 and uni.subjection.id = :provinceId ";
						parMap.put("provinceId", provinceId);
					} else {
						hql = nonCon;
						
					}
				}
				break;
			}
			//高校账号
			case MINISTRY_UNIVERSITY :
			case LOCAL_UNIVERSITY : {
				if(isPrincipal == 1){
					hql = " and uni.id = :belongId " ;
				} else {
					String universityId = this.getAgencyIdByOfficerId(belongId);
					if(null != universityId) {
						hql = " and uni.id = :universityId ";
						parMap.put("universityId", universityId);
					} else {
						hql = nonCon;
					}
				}
				break;
			}
			case DEPARTMENT : {//院系账号
				if(isPrincipal == 1) {
					hql = " and dep.id = :belongId ";
				} else {
					String departmentId = this.getDepartmentIdByOfficerId(belongId);
					if(null != departmentId){
						hql = " and dep.id = :departmentId ";
						parMap.put("departmentId", departmentId);
					} else {
						hql = nonCon;
					}
				}
				break;
			}
			case INSTITUTE : {//基地账号
				if(isPrincipal == 1){
					hql = " and ins.id = :belongId ";
				} else {
					String instituteId = this.getInstituteIdByOfficerId(belongId);
					if(null != instituteId){
						hql = " and ins.id = :instituteId ";
						parMap.put("instituteId", instituteId);
					}else{
						hql = nonCon;
					}
				}
				break;
			}
			//教师、专家、学生账号
			case EXPERT : 
			case TEACHER :
			case STUDENT : {
				hql = " and aut.id = :belongId ";
				break;
			}
			default : {
				hql = nonCon;
				break;
			}
		}
		//上级账号只能看到已提交成果
		if(type.compareTo(AccountType.EXPERT)<0) {
			hql += " and p.submitStatus = 3 ";
		}
		return hql;
	}
	
	/**
	 * 根据文件、成果保存路径、成果形式生成保存成果文件名并保存文件
	 * @param uploadFile 上传的文件
	 * @param savePath 存储路径
	 * @param type 成果形式(1.论文；2.著作；3.研究咨询报告； 4.电子出版物； 5.专利；6.其他成果)
	 * @return 上传文件保存后的相对路径
	 */
	@SuppressWarnings("deprecation")
	public String getFileName(File uploadFile, String savePath, int type) {
		try {	
			String realPath = ApplicationContainer.sc.getRealPath(savePath);
			// 获取系统时间并转成字符串
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String oldName = uploadFile.getName();
			String extendName = oldName.substring(oldName.lastIndexOf("."));
			String dateformat = format.format(date);
			String year = String.valueOf(date.getYear() + 1900);
			String filepath = year + "/";
			String realName = "";
			switch(type){
				case 1 : {
					realName = "pap_" + year + "_" + dateformat+extendName;
					break;
				}
				case 2 : {
					realName = "book_" + year + "_" + dateformat+extendName;
					break;
				}
				case 3 : {
					realName = "cst_" + year + "_" + dateformat+extendName;
					break;
				}
				case 4 : {
					realName = "ele_" + year + "_" + dateformat+extendName;
					break;
				}
				case 5 : {
					realName = "pat_" + year + "_" + dateformat+extendName;
					break;
				}
				case 6 : {
					realName = "otp_" + year + "_" + dateformat+extendName;
					break;
				}
			}
			realPath = realPath.replace('\\', '/');
			filepath = savePath + "/" + filepath + realName;
			return filepath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据账号、成果对象、作者填充成果作者、单位相关信息
	 * @param account : 账号对象; product : 成果对象; authorId : 作者id; authorType : {1: 教师, 2: 专家, 3: 学生 }
	 * @param authorTypeId : 专家、教师、学生id
	 * @return 成果对象
	 */
	public Product fillAuthorAndAgencyInfos(AccountType type, Product product, String authorId, String authorTypeId, int authorType) {
		Person author = (Person)dao.query(Person.class, authorId);
		if (null != author) {
			product.setAuthor(author);
			product.setAuthorName(author.getName());
		}
		if(type.equals(AccountType.EXPERT) || authorType == 2){//外部专家
			Expert expert = (Expert)dao.query(Expert.class, authorTypeId);
			if(null != expert) {
				product.setAuthorType(2);
				product.setAgencyName(expert.getAgencyName());
				product.setDivisionName(expert.getDivisionName());
			}
		} else if(type.equals(AccountType.TEACHER) || authorType == 1){//教师 
			Teacher teacher = (Teacher)dao.queryUnique("select tea from Teacher tea left join fetch tea.university uni " +
				"left join fetch tea.department dep left join fetch tea.institute ins where tea.id= ?", authorTypeId);
			if(null != teacher) {
				product.setAuthorType(1);
				product.setUniversity(teacher.getUniversity());
				product.setAgencyName(teacher.getUniversity().getName());
				product.setProvinceName(teacher.getUniversity().getProvince().getName());
				product.setProvince(teacher.getUniversity().getProvince());
				if(null != teacher.getDepartment()) {
					product.setDepartment(teacher.getDepartment());
					product.setDivisionName(teacher.getDepartment().getName());
				} else if(null != teacher.getInstitute()) {
					product.setInstitute(teacher.getInstitute());
					product.setDivisionName(teacher.getInstitute().getName());
				}
			}
		} else if(type.equals(AccountType.STUDENT) || authorType == 3) {//学生
			Student student = (Student)dao.queryUnique("select stu from Student stu left join fetch stu.university uni " +
				"left join fetch stu.department dep left join fetch stu.institute ins where stu.id= ?", authorTypeId);
			if(null != student) {
				product.setAuthorType(3);
				product.setUniversity(student.getUniversity());
				product.setAgencyName(student.getUniversity().getName());
				product.setProvinceName(student.getUniversity().getProvince().getName());
				product.setProvince(student.getUniversity().getProvince());
				if(null != student.getDepartment()) {
					product.setDepartment(student.getDepartment());
					product.setDivisionName(student.getDepartment().getName());
				} else if(null != student.getInstitute()) {
					product.setInstitute(student.getInstitute());
					product.setDivisionName(student.getInstitute().getName());
				}
			} 
		}
		return product;
	}
	
	/**
	 * 根据成果id获取成果作者id
	 * @param productId : 成果id
	 * @return 返回成果作者id：专家id|教师id|学生id
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getAuthorIdOfAuthorType(String productId) {
		Product product = (Product)dao.query(Product.class, productId);
		if(null == product) return null;
		String personid = (null != product.getAuthor()) ? product.getAuthor().getId() : null;
		Agency university = product.getUniversity();
		Department department = product.getDepartment();
		Institute institute = product.getInstitute();
		if(null != personid) {
			Map map = new HashMap();
			map.put("personid", personid);
			switch(product.getAuthorType()) {
				case 1 : {//教师
					String hql = "from Teacher t where t.person.id= :personid ";
					List teachers = dao.query(hql, map);
					if(teachers.size() > 0) {//作者为教师
						if(null != university) {
							hql += "and t.university.id= :universityId ";
							map.put("universityId", university.getId());
							if(null != department) {
								hql += "and t.department.id= :departmentId ";
								map.put("departmentId", department.getId());
							} else if(null != institute) {
								hql += "and t.institute.id= :instituteId ";
								map.put("instituteId", institute.getId());
							}
						}
						Teacher teacher = (Teacher)dao.queryUnique(hql, map);
						if(null != teacher) {
							return teacher.getId();
						}
					}
					break;
				}
				case 2 : {//专家
					String hql = "from Expert exp where exp.person.id= :personid";
					Expert expert = (Expert)dao.queryUnique(hql, map);
					if(null != expert) {
						return expert.getId();
					}
					break;
				}
				case 3 : {
					String hql = "from Student stu where stu.person.id= :personid ";
					List students=dao.query("select stu.id from Student stu where stu.person.id= :personid", map);
					if(students.size() > 0) {//作者为学生
						if(null != university) {
							hql += "and stu.university.id= :universityId ";
							map.put("universityId", university.getId());
							if(null != department) {
								hql += "and stu.department.id= :departmentId ";
								map.put("departmentId", department.getId());
							} else if(null != institute) {
								hql += "and stu.institute.id= :instituteId ";
								map.put("instituteId", institute.getId());
							}
						}
						Student student = (Student)dao.queryUnique(hql, map);
						if(null != student) {
							return student.getId();
						} 
					}
					break;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据成果id、审核结果、账号审核成果
	 * @param entityId ： 成果id; result : 审核结果{1 : 不同意  2 : 同意 }; account : 帐号对象
	 */
	public void auditProduct(String entityId, int result, Account account) {
		if(result != 1 && result != 2) {//非法审核结果直接返回
			return;
		}
		//所有成果统一审核, 仅一级//成果含审核级别
		Product product = (Product)dao.query(Product.class, entityId);
		if(null != product && product.getSubmitStatus() == 3) {
			product = fillAuditInfo(account, product, result);
		}
		dao.modify(product);
	}
	
	/**
	 * 根据账号类型填充成果审核信息
	 * @param account ： 账号; product : 成果对象; result : 审核结果{1 : 不同意  2 : 同意 }
	 * @return 成果对象
	 */
	public Product fillAuditInfo(Account account, Product product, int result) {
		this.clearAuditInfo(product);//清除上次审核信息
		if (account.getIsPrincipal() == 0) {//子账号
			Officer o = (Officer) dao.query(Officer.class, account.getOfficer().getId());
			if(o != null) {
				Person p = (Person) dao.query(Person.class, o.getPerson().getId());
				if(p != null) {
					product.setAuditor(p);//审核人
					product.setAuditorName(p.getName());//审核人名称
					product.setAuditorAgency(o.getAgency());//审核人机构
					product.setDepartment(o.getDepartment());//审核人院系
					product.setInstitute(o.getInstitute());//审核人所在基地
				}
			}
		} else if (account.getIsPrincipal() == 1) {//主账号
			if(account.getType().compareTo(AccountType.DEPARTMENT)<0 && !account.getType().equals(AccountType.ADMINISTRATOR)){
				Agency a = (Agency) dao.query(Agency.class, account.getAgency().getId());
				if(a != null) {
					product.setAuditorAgency(a);//审核人机构
				}
			} else if(account.getType().equals(AccountType.DEPARTMENT)){//院系主账号
				Department d = (Department) dao.query(Department.class, account.getDepartment().getId());
				if(d != null) {
					product.setAuditorDept(d);
					product.setAuditorAgency(d.getUniversity());
				}
			} else if(account.getType().equals(AccountType.INSTITUTE)){// 基地主账号
				Institute i = (Institute)dao.query(Institute.class,account.getInstitute().getId());
				if(i != null) {
					product.setAuditorInst(i);
					product.setAuditorAgency(i.getSubjection());
				}
			}
		}
		product.setAuditDate(new Date());
		product.setAuditStatus(3);//审核结果均为已提交
		product.setAuditResult(result);
		return product;
	}
	
	/**
	 * 清除成果审核信息
	 * @param product : 成果对象
	 */
	public void clearAuditInfo(Product product) {
		product.setAuditDate(null);
		product.setAuditor(null);
		product.setAuditorAgency(null);
		product.setAuditorDept(null);
		product.setAuditorInst(null);
		product.setAuditorName(null);
		product.setAuditResult(0);
		product.setAuditStatus(0);
	}
	
	/**
	 * 根据成果id判断成果能否被删除(被项目或奖励引用不能删除)
	 * @param productId : 成果id
	 * @return true: 能被删除; false: 不能被删除
	 */
	@SuppressWarnings("unchecked")
	public boolean canDeleteProduct(String productId) {
		List<ProjectProduct> projectProducts = dao.query(
			"from ProjectProduct pgp where pgp.product.id = ?", productId);//项目引用
		boolean isProjectProduct = (null != projectProducts && !projectProducts.isEmpty()) ? true : false;
		List<AwardApplication> awardApplications = dao.query(
			"from AwardApplication aa where aa.product.id = ? order by aa.applicantSubmitDate desc", productId);//奖励引用
		boolean isAwardProduct = (null != awardApplications && !awardApplications.isEmpty()) ? true : false;
		return (isProjectProduct || isAwardProduct) ? false : true;
	}
	
	/**
	 * 根据成果id判断成果能否被修改(下述情况不允许修改)
	 * @param productId : 成果id
	 * 1.关联到正在处理的年检; 2.关联到正在处理的中检; 3.关联到正在处理的结项; 4.关联到正在处理的报奖
	 * @return true: 能被修改; false: 不能被修改
	 */
	@SuppressWarnings({ "unchecked" })
	public boolean canModifyProduct(String productId) {
		//项目年检正在引用
		List<ProjectAnninspectionProduct> projectAnninspectionProducts = dao.query(
			"from ProjectAnninspectionProduct pap left join pap.projectAnninspection pa where pa.finalAuditStatus < 3 " +
			"and pa.applicantSubmitStatus = 3 and pap.product.id = ?", productId);
		boolean isAnnProduct = (null != projectAnninspectionProducts && !projectAnninspectionProducts.isEmpty()) ? true : false;
		//项目中检正在引用
		List<ProjectMidinspectionProduct> projectMidinspectionProducts = dao.query(
			"from ProjectMidinspectionProduct pmp left join pmp.projectMidinspection pm where pm.finalAuditStatus < 3 " +
			"and pm.applicantSubmitStatus = 3 and pmp.product.id = ?", productId);
		boolean isMidProduct = (null != projectMidinspectionProducts && !projectMidinspectionProducts.isEmpty()) ? true : false;
		//项目结项正在引用
		List<ProjectEndinspectionProduct> projectEndinspectionProducts = dao.query("" +
			"from ProjectEndinspectionProduct pep left join pep.projectEndinspection pe where pe.finalAuditStatus < 3 " +
			"and pe.applicantSubmitStatus = 3 and pep.product.id = ?", productId);
		boolean isEndProduct = (null != projectEndinspectionProducts && !projectEndinspectionProducts.isEmpty()) ? true : false;
		//成果报奖正在引用
		List<AwardApplication> awardApplications = dao.query(
			"from AwardApplication aa where aa.finalAuditStatus < 3 and aa.applicantSubmitStatus = 3 " +
			"and aa.product.id = ?", productId);
		boolean isAwardProduct = (null != awardApplications && !awardApplications.isEmpty()) ? true : false;
		return (isAnnProduct || isMidProduct || isEndProduct || isAwardProduct) ? false : true;
	}
	
	//========================================================================================
	// 2.成果-项目相关业务方法
	//========================================================================================
	/**
	 * 根据成果id获得成果相关项目信息
	 * @param productId : 成果id
	 * @return map对象，包含成果关联项目相关信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getRelProjectInfos(String productId) {
		List<Map> RelProjectsList = new ArrayList<Map>();
		//找到成果相关项目立项id
		List infos = dao.query("select pgp.projectGranted.id, pgp.isMarkMoeSupport from ProjectProduct pgp where pgp.product.id = ?", productId);
		for(Object o : infos) {
			Map map = new HashMap();
			Object[] oo = (Object[])o;
			try{
				ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, (String)oo[0]);
				String projectTypeName = ProjectGranted.findTypeName(projectGranted.getProjectType());
				map.put("projectTypeName", projectTypeName);//项目类型名称
				map.put("projectType", projectGranted.getProjectType());
				map.put("projectName", projectGranted.getName());//项目名称
				map.put("grantedId", projectGranted.getId());//项目id
			}catch(Exception e) {
				System.out.println(e);
			}
			
			map.put("isAnnProduct", isAnnProduct((String)oo[0], productId));//是否年检成果
			map.put("isMidProduct", isMidProduct((String)oo[0], productId));//是否中检成果
			map.put("isEndProduct", isEndProduct((String)oo[0], productId));//是否结项成果
			map.put("isMarkMoeSupport", (Integer)oo[1] == 1 ? true : false);//是否资助项目
			RelProjectsList.add(map);
		}
		return RelProjectsList;
	}
	
	/**
	 * 根据立项id, 返回项目下地所有成果类型（按照系统选项表代码标准排序）
	 * @param grantedId : 立项id
	 * @return list对象，包含成果类型
	 */
	@SuppressWarnings("unchecked")
	public List<String> getProductTypesByProject(String grantedId) {
		List<String> productTypes = new ArrayList<String>(); 
		List<String> ptypes = dao.query("select p.productType from ProjectProduct pgp left join pgp.product p where pgp.projectGranted.id = ? ", grantedId);
		for(String ptype : ptypes) {
			if(!productTypes.contains(ptype)) {
				productTypes.add(ptype);
			}
		}
		return productTypes;
	}
	
	/**
	 * 根据成果id获取成果立项对象
	 * @param productId : 成果id
	 * @return 项目立项成果对象
	 */
	@SuppressWarnings("unchecked")
	public ProjectProduct getProjectProductByProductId(String productId) {
		List<ProjectProduct> projectProducts = dao.query(
			"select pgp from ProjectProduct pgp left join pgp.product p where p.id = ?", productId);
		return (null != projectProducts && !projectProducts.isEmpty()) ? projectProducts.get(0) : null;
	}
	
	/**
	 * 根据成果id、结项id获取成果结项对象
	 * @param productId : 成果id, endInspectionId : 结项id
	 * @return 项目结项成果对象
	 */
	@SuppressWarnings("unchecked")
	public ProjectEndinspectionProduct getProjectEndinspectionProduct(String productId, String endInspectionId) {
		List<ProjectEndinspectionProduct> projectEndinspectionProducts = dao.query("select pep from ProjectEndinspectionProduct " +
			"pep where pep.product.id = ? and pep.projectEndinspection.id = ?", productId, endInspectionId);
		return (null != projectEndinspectionProducts && !projectEndinspectionProducts.isEmpty()) ? projectEndinspectionProducts.get(0) : null;
	}
	
	/**
	 * 根据成果id、结项id重新设置结项最终成果（只有一个）
	 * @param productId : 成果id, endInspectionId : 结项id, isFinalProduct : 是否最终成果(0:否,  1:是)
	 */
	@SuppressWarnings("unchecked")
	public void setEndInspectionFinalProduct(String productId, String endInspectionId, int isFinalProduct) {
		if(!(isFinalProduct == 0 || isFinalProduct == 1)) return ;
		//找到该次结项下所有成果结项对象、并清除最终成果标志位
		List<ProjectEndinspectionProduct> projectEndinspectionProducts = dao.query("select pep from ProjectEndinspectionProduct " +
			"pep where pep.projectEndinspection.id = ?", endInspectionId);
		for(ProjectEndinspectionProduct projectEndinspectionProduct : projectEndinspectionProducts) {
			if(projectEndinspectionProduct.getIsFinalProduct() == 1) {
				projectEndinspectionProduct.setIsFinalProduct(0);
				dao.modify(projectEndinspectionProduct);
			}
		}
		//设置最终成果
		ProjectEndinspectionProduct projectEndinspectionProduct = getProjectEndinspectionProduct(productId, endInspectionId);
		if(null != projectEndinspectionProduct) {
			projectEndinspectionProduct.setIsFinalProduct(isFinalProduct);
			dao.modify(projectEndinspectionProduct);
		}
	}
	
	/**
	 * 根据成果id判定成果是否为项目成果
	 * @param productId : 成果id
	 * @return true: 是项目成果; false: 不是项目成果
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean isRelProduct(String productId) {
		List list = dao.query("select p.id from ProjectProduct pgp left join pgp.product p " +
			"where p.id = ?", productId);
		return (null != list && !list.isEmpty());
	}
	
	/**
	 * 根据立项id、成果id判定成果是否为年检成果
	 * @param grantedId : 立项id; productId : 成果id
	 * @return true: 是项目年检成果; false: 不是项目年检成果
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean isAnnProduct(String grantedId, String productId) {
		try {//如果存在未实现的方法，则捕获异常并返回false， 即某些项目没有年检
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
			List list = dao.query("select p.id from ProjectAnninspectionProduct pap left join pap.product p, " +
				projectGranted.getAnninspectionClassName() + " anninspection where pap.projectAnninspection.id = " +
					"anninspection.id and anninspection.granted.id = ? and p.id = ?", grantedId, productId);
			return (null != list && !list.isEmpty());
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 根据立项id、成果id判定成果是否为中检成果
	 * @param grantedId : 立项id; productId : 成果id
	 * @return true: 是项目中检成果; false: 不是项目中检成果
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean isMidProduct(String grantedId, String productId) {
		try {//如果存在未实现的方法，则捕获异常并返回false， 即某些项目没有中检
			ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
			List list = dao.query("select p.id from ProjectMidinspectionProduct pmp left join pmp.product p, " +
				projectGranted.getMidinspectionClassName() + " midInspection where pmp.projectMidinspection.id = " +
					"midInspection.id and midInspection.granted.id = ? and p.id = ?", grantedId, productId);
			return (null != list && !list.isEmpty());
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 根据立项id、成果id判定成果是否为结项成果
	 * @param grantedId : 立项id; productId : 成果id
	 * @return true: 是项目结项成果; false: 不是项目结项成果
	 */
	@SuppressWarnings({"rawtypes"})
	public boolean isEndProduct(String grantedId, String productId) {
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		List list = dao.query("select p.id from ProjectEndinspectionProduct pep left join pep.product p, " +
			projectGranted.getEndinspectionClassName() + " endInspection where pep.projectEndinspection.id = " +
				"endInspection.id and endInspection.granted.id = ? and p.id = ?", grantedId, productId);
		return (null != list && !list.isEmpty());
	}
	
	/**
	 * 根据立项id获取项目相关成果列表
	 * @param grantedId : 项目立项id
	 * @return List<Map>对象, 包含项目相关成果信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getRelProducts(String grantedId) {
		List<Map> tlist = new ArrayList<Map>();
		//获取成果形式
		String productType = "";
		List proInfos = dao.query("select pg.productType, pg.productTypeOther from ProjectGranted pg where pg.id = ?", grantedId);
		if(null != proInfos && proInfos.size() > 0) {
			Object[] prodType = (Object[]) proInfos.get(0);
			if(prodType[0] != null && !((String)prodType[0]).isEmpty()) {
				productType = productType + prodType[0].toString();
			}
			if(prodType[1] != null && !((String)prodType[1]).isEmpty()) {
				productType = productType + "(" + prodType[1].toString() + ")";
			}
		}
		//查询语句
		String hql = "select p.id, p.chineseName, p.productType, p.authorName, p.author.id, p.agencyName, p.university.id, " +
			"p.disciplineType, pgp.isMarkMoeSupport, 'isAnn', 'isMid', 'isEnd', p.auditResult from Product p, ProjectProduct pgp " +
			"where p.id = pgp.product.id and pgp.projectGranted.id = :grantedId and p.submitStatus = 3 and p.productType = :productType " +
			"order by p.submitDate desc";
		Map parMap = new HashMap();
		parMap.put("grantedId", grantedId);
		Map productMap = new HashMap();
		productMap.put("productType", productType);
		productMap.put("submitStatus", 0);
		List ps = new ArrayList();
		List<String> ptypes = getProductTypesByProject(grantedId);
		//获取该次项目下所有成果
		for(String ptype : ptypes) {
			parMap.put("productType", ptype);
			List p = dao.query(hql, parMap);
			//判决是否年检、中检、结项成果
			if(null != p) {
				for(int i = 0; i < p.size(); i++) {
					Object[] oo = (Object[])p.get(i);
					oo[9] = (isAnnProduct(grantedId, (String)oo[0])) ? 1 : 0;
					oo[10] = (isMidProduct(grantedId, (String)oo[0])) ? 1 : 0;
					oo[11] = (isEndProduct(grantedId, (String)oo[0])) ? 1 : 0;
					p.set(i, oo);
				}
			}
			ps.addAll(p);
			productMap.put(ptype + "Size", (null != p) ? p.size() : 0);
		}
		productMap.put("productSize", ps.size());
		productMap.put("productList", ps);
		tlist.add(productMap);
		return tlist;
	}
	
	/**
	 * 根据立项id, 年检id获取项目年检成果列表
	 * @param id ：项目立项id; annIds : 年检id
	 * @return List<Map>对象, 包含项目年检成果信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getAnnProducts(String grantedId, List<String> annIds) {
		List<Map> tlist = new ArrayList<Map>();
		//获取成果形式
		String productType = "";
		List proInfos = dao.query("select pg.productType, pg.productTypeOther from ProjectGranted pg where pg.id = ?", grantedId);
		if(null != proInfos && proInfos.size() > 0) {
			Object[] prodType = (Object[]) proInfos.get(0);
			if(prodType[0] != null && !((String)prodType[0]).isEmpty()) {
				productType = productType + prodType[0].toString();
			}
			if(prodType[1] != null && !((String)prodType[1]).isEmpty()) {
				productType = productType + "(" + prodType[1].toString() + ")";
			}
		}
		//获取当前年检状态
		int submitStatus = 0;
		if(null != annIds && !annIds.isEmpty()) {
			List annInfos = dao.query("select ann.applicantSubmitStatus, ann.finalAuditStatus, ann.finalAuditResult, ann.createMode from " +
				"ProjectAnninspection ann where ann.id = ?", annIds.get(0));
			if(null != annInfos && annInfos.size() > 0) {
				Object[] obj = (Object[]) annInfos.get(0);
				int status = (Integer)obj[1], result = (Integer)obj[2], createMode = (Integer)obj[3];
				if(status == 3 && result == 1){//当前年检没通过，能再添加年检成果
					submitStatus = 0;
				} else if(status == 3 && result == 2){//当前年检已通过，不能再添加年检成果
					submitStatus = 3;
				} else if(status != 3 && (createMode == 1 || createMode == 2)){//当前年检是录入的，且正在处理，不能添加年检成果
					submitStatus = 3;
				}
			} 
		}
		//查询语句
		String hql = "select p.id, p.chineseName, p.productType, p.authorName, p.author.id, p.agencyName, p.university.id, " +
			"p.disciplineType, 'isMarkMoeSupport', pannp.firstAuditResult, pannp.finalAuditResult from ProjectAnninspectionProduct pannp " +
			"left join pannp.product p where pannp.projectAnninspection.id = ? and p.productType = ? and p.submitStatus = 3" +
			"order by p.submitDate desc";
		for(String annId : annIds) {
			Map productMap = new HashMap();
			productMap.put("productType", productType);
			productMap.put("submitStatus", submitStatus);
			List ps = new ArrayList();
			List<String> ptypes = getProductTypesByProject(grantedId);
			//获取该次年检下的成果
			for(String ptype : ptypes) {
				List p = dao.query(hql, annId, ptype);
				for(int i = 0; i < p.size(); i++) {//判定是否资助项目
					Object[] oo = (Object[])p.get(i);
					ProjectProduct projectProduct = (ProjectProduct)dao.queryUnique(
						"from ProjectProduct pgp where pgp.projectGranted.id = ? and pgp.product.id = ?", grantedId, (String)oo[0]);
					oo[8] = projectProduct.getIsMarkMoeSupport();
					p.set(i, oo);
				}
				ps.addAll(p);
				productMap.put(ptype + "Size", (null != p) ? p.size() : 0);
			}
			productMap.put("productSize", ps.size());
			productMap.put("productList", ps);
			tlist.add(productMap);
		}
		return tlist;
	}
	
	/**
	 * 根据立项id, 中检id获取项目中检成果列表
	 * @param id ：项目立项id; midIds : 中检id
	 * @return List<Map>对象, 包含项目中检成果信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getMidProducts(String grantedId, List<String> midIds) {
		List<Map> tlist = new ArrayList<Map>();
		//获取成果形式
		String productType = "";
		List proInfos = dao.query("select pg.productType, pg.productTypeOther from ProjectGranted pg where pg.id = ?", grantedId);
		if(null != proInfos && proInfos.size() > 0) {
			Object[] prodType = (Object[]) proInfos.get(0);
			if(prodType[0] != null && !((String)prodType[0]).isEmpty()) {
				productType = productType + prodType[0].toString();
			}
			if(prodType[1] != null && !((String)prodType[1]).isEmpty()) {
				productType = productType + "(" + prodType[1].toString() + ")";
			}
		}
		//获取当前中检状态
		int submitStatus = 0;
		if(null != midIds && !midIds.isEmpty()) {
			List midInfos = dao.query("select pm.applicantSubmitStatus, pm.finalAuditStatus, pm.finalAuditResult, pm.createMode from " +
				"ProjectMidinspection pm where pm.id = ?", midIds.get(0));
			if(null != midInfos && midInfos.size() > 0) {
				Object[] obj = (Object[]) midInfos.get(0);
				int status = (Integer)obj[1], result = (Integer)obj[2], createMode = (Integer)obj[3];
				if(status == 3 && result == 1){//当前中检没通过，能再添加中检成果
					submitStatus = 0;
				} else if(status == 3 && result == 2){//当前中检已通过，不能再添加中检成果
					submitStatus = 3;
				} else if(status != 3 && (createMode == 1 || createMode == 2)){//当前中检是录入的，且正在处理，不能添加中检成果
					submitStatus = 3;
				}
			} 
		}
		//查询语句
		String hql = "select p.id, p.chineseName, p.productType, p.authorName, p.author.id, p.agencyName, p.university.id, " +
			"p.disciplineType, 'isMarkMoeSupport', pmp.firstAuditResult, pmp.finalAuditResult from ProjectMidinspectionProduct pmp " +
			"left join pmp.product p where pmp.projectMidinspection.id = ? and p.productType = ? and p.submitStatus = 3" +
			"order by p.submitDate desc";
		for(String midId : midIds) {
			Map productMap = new HashMap();
			productMap.put("productType", productType);
			productMap.put("submitStatus", submitStatus);
			List ps = new ArrayList();
			List<String> ptypes = getProductTypesByProject(grantedId);
			//获取该次中检下的成果
			for(String ptype : ptypes) {
				List p = dao.query(hql, midId, ptype);
				for(int i = 0; i < p.size(); i++) {//判定是否资助项目
					Object[] oo = (Object[])p.get(i);
					ProjectProduct projectProduct = (ProjectProduct)dao.queryUnique(
						"from ProjectProduct pgp where pgp.projectGranted.id = ? and pgp.product.id = ?", grantedId, (String)oo[0]);
					oo[8] = projectProduct.getIsMarkMoeSupport();
					p.set(i, oo);
				}
				ps.addAll(p);
				productMap.put(ptype + "Size", (null != p) ? p.size() : 0);
			}
			productMap.put("productSize", ps.size());
			productMap.put("productList", ps);
			tlist.add(productMap);
		}
		return tlist;
	}
	
	/**
	 * 根据立项id, 结项id获取项目结项成果列表
	 * @param id ：项目立项id; midIds : 结项id
	 * @return List<Map>对象, 包含项目结项成果信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getEndProducts(String grantedId, List<String> endIds) {
		List<Map> tlist = new ArrayList<Map>();
		//获取成果形式
		String productType = "";
		List proInfos = dao.query("select pg.productType, pg.productTypeOther from ProjectGranted pg where pg.id = ?", grantedId);
		if(null != proInfos && proInfos.size() > 0) {
			Object[] prodType = (Object[]) proInfos.get(0);
			if(prodType[0] != null && !((String)prodType[0]).isEmpty()) {
				productType = productType + prodType[0].toString();
			}
			if(prodType[1] != null && !((String)prodType[1]).isEmpty()) {
				productType = productType + "(" + prodType[1].toString() + ")";
			}
		}
		//获取当前结项状态
		int submitStatus = 0;
		if(null != endIds && !endIds.isEmpty()) {
			List endInfos = dao.query("select pe.applicantSubmitStatus, pe.finalAuditStatus, pe.finalAuditResultEnd, pe.createMode " +
				"from ProjectEndinspection pe where pe.id = ?", endIds.get(0));
			if(null != endInfos && endInfos.size() > 0) {
				Object[] obj = (Object[]) endInfos.get(0);
				int status = (Integer)obj[1], result = (Integer)obj[2], createMode = (Integer)obj[3];
				if(status == 3 && result == 1){//当前结项没通过，能再添加结项成果
					submitStatus = 0;
				} else if(status == 3 && result == 2){//当前结项已通过，不能再添加结项成果
					submitStatus = 3;
				} else if(status != 3 && (createMode == 1 || createMode == 2)){//当前结项是录入的，且正在处理，不能添加结项成果
					submitStatus = 3;
				}
			} 
		}
		//查询语句
		String hql = "select p.id, p.chineseName, p.productType, p.authorName, p.author.id, p.agencyName, p.university.id, " +
			"p.disciplineType, 'isMarkMoeSupport', pep.firstAuditResult, pep.finalAuditResult, pep.isFinalProduct " +
			"from ProjectEndinspectionProduct pep left join pep.product p where pep.projectEndinspection.id = ? " + 
			"and p.productType = ? and p.submitStatus = 3 order by p.submitDate desc";
		for(String endId : endIds) {
			Map productMap = new HashMap();
			productMap.put("productType", productType);
			productMap.put("submitStatus", submitStatus);
			List ps = new ArrayList();
			List<String> ptypes = getProductTypesByProject(grantedId);
			//获取该次结项下的成果
			for(String ptype : ptypes) {
				List p = dao.query(hql, endId, ptype);
				for(int i = 0; i < p.size(); i++) {//判定是否资助项目
					Object[] oo = (Object[])p.get(i);
					ProjectProduct projectProduct = (ProjectProduct)dao.queryUnique(
						"from ProjectProduct pgp where pgp.projectGranted.id = ? and pgp.product.id = ?", grantedId, (String)oo[0]);
					oo[8] = projectProduct.getIsMarkMoeSupport();
					p.set(i, oo);
				}
				
				ps.addAll(p);
				productMap.put(ptype + "Size", (null != p) ? p.size() : 0);
			}
			productMap.put("productSize", ps.size());
			productMap.put("productList", ps);
			tlist.add(productMap);
		}
		return tlist;
	}
	
	/**
	 * 根据项目立项id判断是否能添加相关结果
	 * @param grantedId : 项目立项id
	 * @return true: 能添加相关成果; false：不能添加相关成果
	 */
	public Boolean canAddRelProduct(String grantedId){
		return (null != (ProjectGranted)dao.query(ProjectGranted.class, grantedId)) ? true : false;
	}
	
	
	/**
	 * 根据项目立项id判断是否能添加年检成果
	 * @param grantedId : 项目立项id
	 * @return true: 能添加年检成果; false：不能添加年检成果
	 */
	@SuppressWarnings("rawtypes")
	public Boolean canAddAnnProduct(String grantedId){
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		String hql = "select pa.applicantSubmitStatus, pa.finalAuditStatus, pa.finalAuditResult, pa.createMode " +
			"from " + projectGranted.getAnninspectionClassName() + " pa left join pa.granted gra " +
			"where gra.id = ? order by pa.applicantSubmitDate desc";
		List anns = dao.query(hql, grantedId);
		int submitStatus = 0;
		if(anns.size() > 0) {
			Object[] o = (Object[]) anns.get(0);//当前年检
			//用来判断是否能添加年检成果 0：能添加 3：年检申请已提交，不能添加
			submitStatus = (Integer)o[0];
			int status = (Integer)o[1], result = (Integer)o[2], createMode = (Integer)o[3];
			if(status == 3 && result == 1){//当前年检没通过，能再添加年检成果
				submitStatus = 0;
			} else if(status == 3 && result == 2){//当前年检已通过，不能再添加年检成果
				submitStatus = 3;
			} else if(status != 3 && (createMode == 1 || createMode == 2)){//当前年检信息是录入的，且正在处理，不能添加年检成果
				submitStatus = 3;
			}
		}
		//如果最近一次年检信息已提交或已通过或为通过，则不能再添加年检成果，否则能添加
		return (submitStatus == 3) ? false : true;
	}
	
	/**
	 * 根据项目立项id判断是否能添加中检结果
	 * @param grantedId : 项目立项id
	 * @return true: 能添加中检成果; false：不能添加中检成果
	 */
	@SuppressWarnings("rawtypes")
	public Boolean canAddMidProduct(String grantedId){
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		String hql = "select pm.applicantSubmitStatus, pm.finalAuditStatus, pm.finalAuditResult, pm.createMode " +
			"from " + projectGranted.getMidinspectionClassName() + " pm left join pm.granted gra " +
			"where gra.id = ? order by pm.applicantSubmitDate desc";
		List mids = dao.query(hql, grantedId);
		int submitStatus = 0;
		if(mids.size() > 0) {
			Object[] o = (Object[]) mids.get(0);//当前中检
			//用来判断是否能添加中检成果 0：能添加 3：中检申请已提交，不能添加
			submitStatus = (Integer)o[0];
			int status = (Integer)o[1], result = (Integer)o[2], createMode = (Integer)o[3];
			if(status == 3 && result == 1){//当前中检没通过，能再添加中检成果
				submitStatus = 0;
			} else if(status == 3 && result == 2){//当前中检已通过，不能再添加中检成果
				submitStatus = 3;
			} else if(status != 3 && (createMode == 1 || createMode == 2)){//当前中检是录入的，且正在处理，不能添加中检成果
				submitStatus = 3;
			}
		}
		//如果最近一次中检已提交或已通过或为通过，则不能再添加中检成果，否则能添加
		return (submitStatus == 3) ? false : true;
	}
	
	/**
	 * 根据项目立项id判断是否能添加结项结果
	 * @param grantedId : 项目立项id
	 * @return true: 能添加结项成果; false：不能添加结项成果
	 */
	@SuppressWarnings("rawtypes")
	public Boolean canAddEndProduct(String grantedId){
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		String hql = "select pe.applicantSubmitStatus, pe.finalAuditStatus, pe.finalAuditResultEnd, pe.createMode " +
			"from " + projectGranted.getEndinspectionClassName() + " pe left join pe.granted gra " +
			"where gra.id = ? order by pe.applicantSubmitDate desc";
		List ends = dao.query(hql, grantedId);
		int submitStatus = 0;
		if(ends.size() > 0) {
			Object[] o = (Object[]) ends.get(0);//当前结项
			//用来判断是否能添加结项成果 0：能添加 3：结项申请已提交，不能添加
			submitStatus = (Integer)o[0];
			int status = (Integer)o[1], result = (Integer)o[2], createMode = (Integer)o[3];
			if(status == 3 && result == 1){//当前结项没通过，能再添加结项成果
				submitStatus = 0;
			} else if(status == 3 && result == 2){//当前结项已通过，不能再添加结项成果
				submitStatus = 3;
			} else if(status != 3 && (createMode == 1 || createMode == 2)){//当前结项是录入的，且正在处理，不能添加结项成果
				submitStatus = 3;
			}
		}
		//如果最近一次中检已提交或已通过或为通过，则不能再添加中检成果，否则能添加
		return (submitStatus == 3) ? false : true;
	}
	
	/**
	 * 根据项目立项id、成果类型、列表类型、检查id获取成果
	 * @param grantedId : 项目立项id; listType : {1 : 年检; 2 : 中检; 3:结项}; productType : 成果类型; inspectionId : 年检、中检、结项id
	 * @return Map对象, key: 成果id, value: 成果名称
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> getProduct(String grantedId, int projectType, int listType, String productType, String inspectionId){
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		Map<String, String> map = new HashMap<String, String>();
		//找出没有被该次年检、中检或结项引用的项目成果
		String hql = "";
		if(listType == 1) { //年度
			hql = "select p.id, p.chineseName from ProjectProduct pgp left join pgp.projectGranted gra left join " +
				"pgp.product p where gra.id = ? and p.productType = ? and not exists (select 1 from ProjectAnninspectionProduct " +
				"pap left join pap.product p1, " + projectGranted.getAnninspectionClassName() + " Anninspection left join Anninspection.granted gra1 " +
				"where Anninspection.id = pap.projectAnninspection.id and gra1.id = gra.id and p.id = p1.id and Anninspection.id = ?)";
		}else if(listType == 2) { //中检
			hql = "select p.id, p.chineseName from ProjectProduct pgp left join pgp.projectGranted gra left join " +
				"pgp.product p where gra.id = ? and p.productType = ? and not exists (select 1 from ProjectMidinspectionProduct " +
				"pmp left join pmp.product p1, " + projectGranted.getMidinspectionClassName() + " midInspection left join midInspection.granted gra1 " +
				"where midInspection.id = pmp.projectMidinspection.id and gra1.id = gra.id and p.id = p1.id and midInspection.id = ?)";
		} else if(listType == 3) { //结项
			hql = "select p.id, p.chineseName from ProjectProduct pgp left join pgp.projectGranted gra left join " +
				"pgp.product p where gra.id = ? and p.productType = ? and not exists (select 1 from ProjectEndinspectionProduct " +
				"pep left join pep.product p1, " + projectGranted.getEndinspectionClassName() + " endInspection left join endInspection.granted gra1 " +
				"where endInspection.id = pep.projectEndinspection.id and gra1.id = gra.id and p.id = p1.id and endInspection.id = ?)";
		} else return map;
		List list = dao.query(hql, grantedId, productType, inspectionId);
		for(Object o : list){
			Object[] oo = (Object[])o;
			map.put(oo[0].toString(), oo[1].toString());
		}
		return map;
	}
	
	/**
	 * 根据账号类型、审核级别判定账号是否能够审核年检成果
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 * @return true: 能审核; false: 不能审核
	 */
	public boolean canAuditAnninspectionProduct(List<ProjectAnninspection> annList, Account account, int firstAuditLevel, int finalAuditLevel) {
		if(null == annList || annList.isEmpty()) return false;
		AccountType accountType = AccountType.UNDEFINED;
		AccountType type1 = accountType.chageType(firstAuditLevel);
		AccountType type2 = accountType.chageType(finalAuditLevel);
		boolean firstFlag = (account.getType().equals(type1)|| (firstAuditLevel == 4 && account.getType().equals(AccountType.LOCAL_UNIVERSITY)));
		boolean finalFlag = (account.getType().equals(type2)|| (finalAuditLevel == 4 && account.getType().equals(AccountType.LOCAL_UNIVERSITY)));
		//根据中检状态、判决当前账号能否审核成果
		boolean canAudit = false;
		ProjectAnninspection projectAnninspection = annList.get(0);
		if(projectAnninspection.getFinalAuditStatus() != 3) {
			//流程状态[1新建中检申请，2院系/研究机构审核，3校级审核，4省级审核，5最终审核]
			int status = projectAnninspection.getStatus();
			if((account.getType().equals(AccountType.DEPARTMENT) || account.getType().equals(AccountType.INSTITUTE)) && status == 2) {//院系、研究基地
				canAudit = true;
			} else if((account.getType().equals(AccountType.MINISTRY_UNIVERSITY) || account.getType().equals(AccountType.LOCAL_UNIVERSITY)) && status == 3) {//高校
				canAudit = true;
			} else if(account.getType().equals(AccountType.PROVINCE) && status == 4) {//省级
				canAudit = true;
			} else if(account.getType().equals(AccountType.MINISTRY) && status == 5) {//部级
				canAudit = true;
			}
		}
		return (firstFlag || finalFlag) && canAudit;
	}
	
	/**
	 * 根据账号类型、审核级别判定账号是否能够审核中检成果
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 * @return true: 能审核; false: 不能审核
	 */
	public boolean canAuditMidInspectionProduct(List<ProjectMidinspection> midList, Account account, int firstAuditLevel, int finalAuditLevel) {
		if(null == midList || midList.isEmpty()) return false;
		AccountType accountType = AccountType.UNDEFINED;
		AccountType type1 = accountType.chageType(firstAuditLevel);
		AccountType type2 = accountType.chageType(finalAuditLevel);
		boolean firstFlag = (account.getType().equals(type1)|| (firstAuditLevel == 4 && account.getType().equals(AccountType.LOCAL_UNIVERSITY)));
		boolean finalFlag = (account.getType().equals(type2)|| (finalAuditLevel == 4 && account.getType().equals(AccountType.LOCAL_UNIVERSITY)));
		//根据中检状态、判决当前账号能否审核成果
		boolean canAudit = false;
		ProjectMidinspection projectMidinspection = midList.get(0);
		if(projectMidinspection.getFinalAuditStatus() != 3) {
			//流程状态[1新建中检申请，2院系/研究机构审核，3校级审核，4省级审核，5最终审核]
			int status = projectMidinspection.getStatus();
			if((account.getType().equals(AccountType.DEPARTMENT) || account.getType().equals(AccountType.INSTITUTE)) && status == 2) {//院系、研究基地
				canAudit = true;
			} else if((account.getType().equals(AccountType.MINISTRY_UNIVERSITY) || account.getType().equals(AccountType.LOCAL_UNIVERSITY)) && status == 3) {//高校
				canAudit = true;
			} else if(account.getType().equals(AccountType.PROVINCE) && status == 4) {//省级
				canAudit = true;
			} else if(account.getType().equals(AccountType.MINISTRY) && status == 5) {//部级
				canAudit = true;
			}
		}
		return (firstFlag || finalFlag) && canAudit;
	}
	
	/**
	 * 根据账号类型、审核级别判定账号是否能够审核结项成果
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 * @return true: 能审核; false: 不能审核
	 */
	public boolean canAuditEndInspectionProduct(List<ProjectEndinspection> endList, Account account, int firstAuditLevel, int finalAuditLevel) {
		if(null == endList || endList.isEmpty()) return false;
		AccountType accountType = AccountType.UNDEFINED;
		AccountType type1 = accountType.chageType(firstAuditLevel);
		AccountType type2 = accountType.chageType(finalAuditLevel);
		boolean firstFlag = (account.getType().equals(type1)|| (firstAuditLevel == 4 && account.getType().equals(AccountType.LOCAL_UNIVERSITY)));
		boolean finalFlag = (account.getType().equals(type2)|| (finalAuditLevel == 4 && account.getType().equals(AccountType.LOCAL_UNIVERSITY)));
		//根据结项状态、判决当前账号能否审核成果
		boolean canAudit = false;
		ProjectEndinspection projectEndinspection = endList.get(0);
		if(projectEndinspection.getFinalAuditStatus() != 3) {
			//流程状态[1新建结项申请，2院系/研究机构审核，3校级审核，4省级审核，5部级审核，6评审，7最终审核]
			int status = projectEndinspection.getStatus();
			if((account.getType().equals(AccountType.DEPARTMENT) || account.getType().equals(AccountType.INSTITUTE)) && status == 2) {//院系、研究基地
				canAudit = true;
			} else if((account.getType().equals(AccountType.MINISTRY_UNIVERSITY) || account.getType().equals(AccountType.LOCAL_UNIVERSITY)) && status == 3) {//高校
				canAudit = true;
			} else if(account.getType().equals(AccountType.PROVINCE) && status == 4) {//省级
				canAudit = true;
			} else if(account.getType().equals(AccountType.MINISTRY) && status == 5) {//省级
				canAudit = true;
			}
		}
		return (firstFlag || finalFlag) && canAudit;
	}
	
	/**
	 * 根据账号级别、审核级别判定当前审核类型
	 * @param account : 账号对象; firstAuditLevel : 初审级别; finalAuditLevel : 终审级别
	 * @return 1 : 初审; 2 : 终审
	 */
	public int getAuditType(Account account, int firstAuditLevel, int finalAuditLevel) {
		AccountType accountType = AccountType.UNDEFINED;
		AccountType type1 = accountType.chageType(firstAuditLevel);
		boolean firstFlag = (account.getType().equals(type1)|| (firstAuditLevel == 4 && account.getType().equals(AccountType.LOCAL_UNIVERSITY)));
		return firstFlag ? 1 : 2;
	}
	
	/**
	 * 审核项目成果
	 * @param account : 账号对象; productId : 成果id; grantedId : 立项id; viewType : 列表类型; inspectionId : 年检、中检、结项id; auditResult : 审核结果
	 */
	public void auditProjectProduct(Account account, String productId, String grantedId, int viewType, String inspectionId, int auditResult, int firstAuditLevel, int finalAuditLevel) {
		if(viewType == 1) {//年检成果
			ProjectAnninspectionProduct projectAnninspectionProduct = (ProjectAnninspectionProduct)dao.queryUnique(
				"from ProjectAnninspectionProduct pap where pap.projectAnninspection.id = ? and pap.product.id = ?", inspectionId, productId);
			projectAnninspectionProduct = fillAnninspectionAuditInfo(account, projectAnninspectionProduct, auditResult, 
				getAuditType(account, firstAuditLevel, finalAuditLevel));
			dao.modify(projectAnninspectionProduct);
		}else if(viewType == 2) {//中检成果
			ProjectMidinspectionProduct projectMidinspectionProduct = (ProjectMidinspectionProduct)dao.queryUnique(
				"from ProjectMidinspectionProduct pmp where pmp.projectMidinspection.id = ? and pmp.product.id = ?", inspectionId, productId);
			projectMidinspectionProduct = fillMidinspectionAuditInfo(account, projectMidinspectionProduct, auditResult, 
				getAuditType(account, firstAuditLevel, finalAuditLevel));
			dao.modify(projectMidinspectionProduct);
		} else if(viewType == 3) {//结项成果
			ProjectEndinspectionProduct projectEndinspectionProduct = (ProjectEndinspectionProduct)dao.queryUnique(
				"from ProjectEndinspectionProduct pep where pep.projectEndinspection.id = ? and pep.product.id = ?", inspectionId, productId);
			projectEndinspectionProduct = fillEndinspectionAuditInfo(account, projectEndinspectionProduct, auditResult, 
				getAuditType(account, firstAuditLevel, finalAuditLevel));
			dao.modify(projectEndinspectionProduct);
		} 
		//相关成果; 如果审核结果为同意, 则审核本身审核结果也更新
		if(viewType == 4 || auditResult == 2) {
			Product product = (Product)dao.query(Product.class, productId);
			product = fillAuditInfo(account, product, auditResult);
			dao.modify(product);
		}
	}
	
	//根据账号类型填充成果年度审核信息
	public ProjectAnninspectionProduct fillAnninspectionAuditInfo(Account account, ProjectAnninspectionProduct projectAnninspectionProduct, int result, int auditType) {
		if(auditType != 1 && auditType != 2) return projectAnninspectionProduct;
		clearAnninspectionAuditInfo(projectAnninspectionProduct, auditType);//清除审核信息
		if (account.getIsPrincipal() == 0) {//子账号
			Officer o = (Officer) dao.query(Officer.class, account.getOfficer().getId());
			if(o != null) {
				Person p = (Person) dao.query(Person.class, o.getPerson().getId());
				if(p != null) {
					if(auditType == 1) {//初审
						projectAnninspectionProduct.setFirstAuditor(p);//审核人
						projectAnninspectionProduct.setFirstAuditorName(p.getName());//审核人名称
						projectAnninspectionProduct.setFirstAuditorAgency(o.getAgency());//审核人机构
						projectAnninspectionProduct.setFirstAuditorDept(o.getDepartment());//审核人院系
						projectAnninspectionProduct.setFirstAuditorInst(o.getInstitute());//审核人所在基地
					} else {//终审
						projectAnninspectionProduct.setFinalAuditor(p);//审核人
						projectAnninspectionProduct.setFinalAuditorName(p.getName());//审核人名称
						projectAnninspectionProduct.setFinalAuditorAgency(o.getAgency());//审核人机构
						projectAnninspectionProduct.setFinalAuditorDept(o.getDepartment());//审核人院系
						projectAnninspectionProduct.setFinalAuditorInst(o.getInstitute());//审核人所在基地
					}
					
				}
			}
		} else if (account.getIsPrincipal() == 1) {//主账号
			if(account.getType().compareTo(AccountType.DEPARTMENT)<0 && account.getType().compareTo(AccountType.ADMINISTRATOR)>0){
				Agency a = (Agency) dao.query(Agency.class, account.getAgency().getId());
				if(a != null) {
					if(auditType == 1) {//初审
						projectAnninspectionProduct.setFirstAuditorAgency(a);//审核人机构
					} else {//终审
						projectAnninspectionProduct.setFinalAuditorAgency(a);//审核人机构
					}
					
				}
			} else if(account.getType().equals(AccountType.DEPARTMENT)){//院系主账号
				Department d = (Department) dao.query(Department.class, account.getAgency().getId());
				if(d != null) {
					if(auditType == 1) {//初审
						projectAnninspectionProduct.setFirstAuditorDept(d);
						projectAnninspectionProduct.setFirstAuditorAgency(d.getUniversity());
					} else {//终审
						projectAnninspectionProduct.setFinalAuditorDept(d);
						projectAnninspectionProduct.setFinalAuditorAgency(d.getUniversity());
					}
					
				}
			} else if(account.getType().equals(AccountType.INSTITUTE)){// 基地主账号
				Institute i = (Institute)dao.query(Institute.class,account.getInstitute().getId());
				if(i != null) {
					if(auditType == 1) {//初审
						projectAnninspectionProduct.setFirstAuditorInst(i);
						projectAnninspectionProduct.setFirstAuditorAgency(i.getSubjection());
					} else {//终审
						projectAnninspectionProduct.setFinalAuditorInst(i);
						projectAnninspectionProduct.setFinalAuditorAgency(i.getSubjection());
					}
					
				}
			}
		}
		if(auditType == 1) {//初审
			projectAnninspectionProduct.setFirstAuditDate(new Date());
			projectAnninspectionProduct.setFirstAuditStatus(3);//审核结果均为已提交
			projectAnninspectionProduct.setFirstAuditResult(result);
		} else {//终审
			projectAnninspectionProduct.setFinalAuditDate(new Date());
			projectAnninspectionProduct.setFinalAuditStatus(3);//审核结果均为已提交
			projectAnninspectionProduct.setFinalAuditResult(result);
		}
		return projectAnninspectionProduct;
	}
	
	//根据账号类型填充成果中检审核信息
	public ProjectMidinspectionProduct fillMidinspectionAuditInfo(Account account, ProjectMidinspectionProduct projectMidinspectionProduct, int result, int auditType) {
		if(auditType != 1 && auditType != 2) return projectMidinspectionProduct;
		clearMidinspectionAuditInfo(projectMidinspectionProduct, auditType);//清除审核信息
		if (account.getIsPrincipal() == 0) {//子账号
			Officer o = (Officer) dao.query(Officer.class, account.getOfficer().getId());
			if(o != null) {
				Person p = (Person) dao.query(Person.class, o.getPerson().getId());
				if(p != null) {
					if(auditType == 1) {//初审
						projectMidinspectionProduct.setFirstAuditor(p);//审核人
						projectMidinspectionProduct.setFirstAuditorName(p.getName());//审核人名称
						projectMidinspectionProduct.setFirstAuditorAgency(o.getAgency());//审核人机构
						projectMidinspectionProduct.setFirstAuditorDept(o.getDepartment());//审核人院系
						projectMidinspectionProduct.setFirstAuditorInst(o.getInstitute());//审核人所在基地
					} else {//终审
						projectMidinspectionProduct.setFinalAuditor(p);//审核人
						projectMidinspectionProduct.setFinalAuditorName(p.getName());//审核人名称
						projectMidinspectionProduct.setFinalAuditorAgency(o.getAgency());//审核人机构
						projectMidinspectionProduct.setFinalAuditorDept(o.getDepartment());//审核人院系
						projectMidinspectionProduct.setFinalAuditorInst(o.getInstitute());//审核人所在基地
					}
					
				}
			}
		} else if (account.getIsPrincipal() == 1) {//主账号
			if(account.getType().compareTo(AccountType.DEPARTMENT)<0 && account.getType().compareTo(AccountType.ADMINISTRATOR)>0){
				Agency a = (Agency) dao.query(Agency.class, account.getAgency().getId());
				if(a != null) {
					if(auditType == 1) {//初审
						projectMidinspectionProduct.setFirstAuditorAgency(a);//审核人机构
					} else {//终审
						projectMidinspectionProduct.setFinalAuditorAgency(a);//审核人机构
					}
					
				}
			} else if(account.getType().equals(AccountType.DEPARTMENT)){//院系主账号
				Department d = (Department) dao.query(Department.class, account.getDepartment().getId());
				if(d != null) {
					if(auditType == 1) {//初审
						projectMidinspectionProduct.setFirstAuditorDept(d);
						projectMidinspectionProduct.setFirstAuditorAgency(d.getUniversity());
					} else {//终审
						projectMidinspectionProduct.setFinalAuditorDept(d);
						projectMidinspectionProduct.setFinalAuditorAgency(d.getUniversity());
					}
					
				}
			} else if(account.getType().equals(AccountType.INSTITUTE)){// 基地主账号
				Institute i = (Institute)dao.query(Institute.class,account.getInstitute().getId());
				if(i != null) {
					if(auditType == 1) {//初审
						projectMidinspectionProduct.setFirstAuditorInst(i);
						projectMidinspectionProduct.setFirstAuditorAgency(i.getSubjection());
					} else {//终审
						projectMidinspectionProduct.setFinalAuditorInst(i);
						projectMidinspectionProduct.setFinalAuditorAgency(i.getSubjection());
					}
					
				}
			}
		}
		if(auditType == 1) {//初审
			projectMidinspectionProduct.setFirstAuditDate(new Date());
			projectMidinspectionProduct.setFirstAuditStatus(3);//审核结果均为已提交
			projectMidinspectionProduct.setFirstAuditResult(result);
		} else {//终审
			projectMidinspectionProduct.setFinalAuditDate(new Date());
			projectMidinspectionProduct.setFinalAuditStatus(3);//审核结果均为已提交
			projectMidinspectionProduct.setFinalAuditResult(result);
		}
		return projectMidinspectionProduct;
	}
	
	//根据账号类型填充成果结项审核信息
	public ProjectEndinspectionProduct fillEndinspectionAuditInfo(Account account, ProjectEndinspectionProduct projectEndinspectionProduct, int result, int auditType) {
		if(auditType != 1 && auditType != 2) return projectEndinspectionProduct;
		clearEndinspectionAuditInfo(projectEndinspectionProduct, auditType);//清除终审审核信息
		if (account.getIsPrincipal() == 0) {//子账号
			Officer o = (Officer) dao.query(Officer.class, account.getOfficer().getId());
			if(o != null) {
				Person p = (Person) dao.query(Person.class, o.getPerson().getId());
				if(p != null) {
					if(auditType == 1) {//初审
						projectEndinspectionProduct.setFirstAuditor(p);//审核人
						projectEndinspectionProduct.setFirstAuditorName(p.getName());//审核人名称
						projectEndinspectionProduct.setFirstAuditorAgency(o.getAgency());//审核人机构
						projectEndinspectionProduct.setFirstAuditorDept(o.getDepartment());//审核人院系
						projectEndinspectionProduct.setFirstAuditorInst(o.getInstitute());//审核人所在基地
					} else {//终审
						projectEndinspectionProduct.setFinalAuditor(p);//审核人
						projectEndinspectionProduct.setFinalAuditorName(p.getName());//审核人名称
						projectEndinspectionProduct.setFinalAuditorAgency(o.getAgency());//审核人机构
						projectEndinspectionProduct.setFinalAuditorDept(o.getDepartment());//审核人院系
						projectEndinspectionProduct.setFinalAuditorInst(o.getInstitute());//审核人所在基地
					}
					
				}
			}
		} else if (account.getIsPrincipal() == 1) {//主账号
			if(account.getType().compareTo(AccountType.DEPARTMENT)<0 && account.getType().compareTo(AccountType.ADMINISTRATOR)>0){
				Agency a = (Agency) dao.query(Agency.class, account.getAgency().getId());
				if(a != null) {
					if(auditType == 1) {//初审
						projectEndinspectionProduct.setFirstAuditorAgency(a);//审核人机构
					} else {//终审
						projectEndinspectionProduct.setFinalAuditorAgency(a);//审核人机构
					}
					
				}
			} else if(account.getType().equals(AccountType.DEPARTMENT)){//院系主账号
				Department d = (Department) dao.query(Department.class, account.getDepartment().getId());
				if(d != null) {
					if(auditType == 1) {//初审
						projectEndinspectionProduct.setFirstAuditorDept(d);
						projectEndinspectionProduct.setFirstAuditorAgency(d.getUniversity());
					} else {//终审
						projectEndinspectionProduct.setFinalAuditorDept(d);
						projectEndinspectionProduct.setFinalAuditorAgency(d.getUniversity());
					}
					
				}
			} else if(account.getType().equals(AccountType.INSTITUTE)){// 基地主账号
				Institute i = (Institute)dao.query(Institute.class,account.getInstitute().getId());
				if(i != null) {
					if(auditType == 1) {//初审
						projectEndinspectionProduct.setFirstAuditorInst(i);
						projectEndinspectionProduct.setFirstAuditorAgency(i.getSubjection());
					} else {//终审
						projectEndinspectionProduct.setFinalAuditorInst(i);
						projectEndinspectionProduct.setFinalAuditorAgency(i.getSubjection());
					}
					
				}
			}
		}
		if(auditType == 1) {//初审
			projectEndinspectionProduct.setFirstAuditDate(new Date());
			projectEndinspectionProduct.setFirstAuditStatus(3);//审核结果均为已提交
			projectEndinspectionProduct.setFirstAuditResult(result);
		} else {//终审
			projectEndinspectionProduct.setFinalAuditDate(new Date());
			projectEndinspectionProduct.setFinalAuditStatus(3);//审核结果均为已提交
			projectEndinspectionProduct.setFinalAuditResult(result);
		}
		return projectEndinspectionProduct;
	}
	
	//清除成果年度审核信息
	public void clearAnninspectionAuditInfo(ProjectAnninspectionProduct projectAnninspectionProduct, int auditType) {
		if(auditType != 1 && auditType != 2) return;
		if(auditType == 1) {
			projectAnninspectionProduct.setFirstAuditDate(null);
			projectAnninspectionProduct.setFirstAuditor(null);
			projectAnninspectionProduct.setFirstAuditorAgency(null);
			projectAnninspectionProduct.setFirstAuditorDept(null);
			projectAnninspectionProduct.setFirstAuditorInst(null);
			projectAnninspectionProduct.setFirstAuditorName(null);
			projectAnninspectionProduct.setFirstAuditResult(0);
			projectAnninspectionProduct.setFirstAuditStatus(0);
		} else if(auditType == 2) {
			projectAnninspectionProduct.setFinalAuditDate(null);
			projectAnninspectionProduct.setFinalAuditor(null);
			projectAnninspectionProduct.setFinalAuditorAgency(null);
			projectAnninspectionProduct.setFinalAuditorDept(null);
			projectAnninspectionProduct.setFinalAuditorInst(null);
			projectAnninspectionProduct.setFinalAuditorName(null);
			projectAnninspectionProduct.setFinalAuditResult(0);
			projectAnninspectionProduct.setFinalAuditStatus(0);
		}
	}
	
	//清除成果中检审核信息
	public void clearMidinspectionAuditInfo(ProjectMidinspectionProduct projectMidinspectionProduct, int auditType) {
		if(auditType != 1 && auditType != 2) return;
		if(auditType == 1) {
			projectMidinspectionProduct.setFirstAuditDate(null);
			projectMidinspectionProduct.setFirstAuditor(null);
			projectMidinspectionProduct.setFirstAuditorAgency(null);
			projectMidinspectionProduct.setFirstAuditorDept(null);
			projectMidinspectionProduct.setFirstAuditorInst(null);
			projectMidinspectionProduct.setFirstAuditorName(null);
			projectMidinspectionProduct.setFirstAuditResult(0);
			projectMidinspectionProduct.setFirstAuditStatus(0);
		} else if(auditType == 2) {
			projectMidinspectionProduct.setFinalAuditDate(null);
			projectMidinspectionProduct.setFinalAuditor(null);
			projectMidinspectionProduct.setFinalAuditorAgency(null);
			projectMidinspectionProduct.setFinalAuditorDept(null);
			projectMidinspectionProduct.setFinalAuditorInst(null);
			projectMidinspectionProduct.setFinalAuditorName(null);
			projectMidinspectionProduct.setFinalAuditResult(0);
			projectMidinspectionProduct.setFinalAuditStatus(0);
		}
	}
	
	//清除成果结项审核信息
	public void clearEndinspectionAuditInfo(ProjectEndinspectionProduct projectEndinspectionProduct, int auditType) {
		if(auditType != 1 && auditType != 2) return;
		if(auditType == 1) {
			projectEndinspectionProduct.setFirstAuditDate(null);
			projectEndinspectionProduct.setFirstAuditor(null);
			projectEndinspectionProduct.setFirstAuditorAgency(null);
			projectEndinspectionProduct.setFirstAuditorDept(null);
			projectEndinspectionProduct.setFirstAuditorInst(null);
			projectEndinspectionProduct.setFirstAuditorName(null);
			projectEndinspectionProduct.setFirstAuditResult(0);
			projectEndinspectionProduct.setFirstAuditStatus(0);
		} else if(auditType == 2) {
			projectEndinspectionProduct.setFinalAuditDate(null);
			projectEndinspectionProduct.setFinalAuditor(null);
			projectEndinspectionProduct.setFinalAuditorAgency(null);
			projectEndinspectionProduct.setFinalAuditorDept(null);
			projectEndinspectionProduct.setFinalAuditorInst(null);
			projectEndinspectionProduct.setFinalAuditorName(null);
			projectEndinspectionProduct.setFinalAuditResult(0);
			projectEndinspectionProduct.setFinalAuditStatus(0);
		}
	}
	
	/**
	 * 删除成果对应项目信息
	 * @param productId : 成果id; grantedId : 立项id; viewType : 查看类型; inspectionId : 年检、中检、结项id
	 */
	public void deleteProjectInfo(String productId, String grantedId, int viewType, String inspectionId) {
		if(viewType == 1) {//年检成果
			//仅删除与该次年度信息的关联性
			ProjectAnninspectionProduct projectAnninspectionProduct = (ProjectAnninspectionProduct)dao.queryUnique(
				"from ProjectAnninspectionProduct pap where pap.projectAnninspection.id = ? and pap.product.id = ?", inspectionId, productId);
			dao.delete(projectAnninspectionProduct);
		} else if(viewType == 2) {//中检成果
			//仅删除与该次中检的关联性
			ProjectMidinspectionProduct projectMidinspectionProduct = (ProjectMidinspectionProduct)dao.queryUnique(
				"from ProjectMidinspectionProduct pmp where pmp.projectMidinspection.id = ? and pmp.product.id = ?", inspectionId, productId);
			dao.delete(projectMidinspectionProduct);
		} else if(viewType == 3) {//结项成果
			//仅删除与该次结项的关联性
			ProjectEndinspectionProduct projectEndinspectionProduct = (ProjectEndinspectionProduct)dao.queryUnique(
				"from ProjectEndinspectionProduct pep where pep.projectEndinspection.id = ? and pep.product.id = ?", inspectionId, productId);
			dao.delete(projectEndinspectionProduct);
		} else if(viewType == 4) {//相关成果
			//如果成果被年检、中检、结项引用到则不能删除关联性
			if(!(isAnnProduct(grantedId, productId) ||isMidProduct(grantedId, productId) || isEndProduct(grantedId, productId))) {
				ProjectProduct projectProduct = (ProjectProduct)dao.queryUnique(
					"from ProjectProduct pgp where pgp.projectGranted.id = ? and pgp.product.id = ?", grantedId, productId);
				dao.delete(projectProduct);
			}
		}
	}
	
	/**
	 * 添加已有成果到项目年度信息报告、中检或结项
	 * @param productId : 成果id; grantedId : 立项id; viewType : 查看类型; inspectionId : 年检、中检、结项id
	 */
	public void addExistedProductToProjectInspection(String productId, String grantedId, int viewType, String inspectionId) {
		Product product = (Product)dao.query(Product.class, productId);
		if(viewType == 1) {//年检成果
			ProjectAnninspection projectAnninspection = (ProjectAnninspection)dao.queryUnique(
				"from ProjectAnninspection pa where pa.id = ?", inspectionId);
			ProjectAnninspectionProduct projectAnninspectionProduct = new ProjectAnninspectionProduct();
			projectAnninspectionProduct.setProduct(product);
			projectAnninspectionProduct.setProjectAnninspection(projectAnninspection);
			dao.add(projectAnninspectionProduct);
		} else if(viewType == 2) {//中检成果
			ProjectMidinspection projectMidinspection = (ProjectMidinspection)dao.queryUnique(
				"from ProjectMidinspection pm where pm.id = ?", inspectionId);
			ProjectMidinspectionProduct projectMidinspectionProduct = new ProjectMidinspectionProduct();
			projectMidinspectionProduct.setProduct(product);
			projectMidinspectionProduct.setProjectMidinspection(projectMidinspection);
			dao.add(projectMidinspectionProduct);
		} else if(viewType == 3) {//结项成果
			ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.queryUnique(
				"from ProjectEndinspection pe where pe.id = ?", inspectionId);
			ProjectEndinspectionProduct projectEndinspectionProduct = new ProjectEndinspectionProduct();
			projectEndinspectionProduct.setProduct(product);
			projectEndinspectionProduct.setProjectEndinspection(projectEndinspection);
			dao.add(projectEndinspectionProduct);
		}
	}
	
	/**
	 * 添加新成果到项目相关、年检、中检或结项
	 * @param productId : 成果id; grantedId : 立项id; viewType : 查看类型; inspectionId : 年检、中检、结项id
	 * @param projectProduct : 项目立项成果对象
	 */
	public void addNewProductToProjectInspection(ProjectProduct projectProduct, String productId, String grantedId, int viewType, String inspectionId, int isFinalProduct) {
		Product product = (Product)dao.query(Product.class, productId);
		//添加添加一条项目立项成果记录
		ProjectGranted projectGranted = (ProjectGranted)dao.query(ProjectGranted.class, grantedId);
		projectProduct.setProjectGranted(projectGranted);
		projectProduct.setProduct(product);
		dao.add(projectProduct);
		if(viewType == 1) {//添加年检成果
			//添加添加一条项目年检成果记录
			ProjectAnninspection projectAnninspection = (ProjectAnninspection)dao.queryUnique(
				"from ProjectAnninspection pa where pa.id = ?", inspectionId);
			ProjectAnninspectionProduct projectAnninspectionProduct = new ProjectAnninspectionProduct();
			projectAnninspectionProduct.setProduct(product);
			projectAnninspectionProduct.setProjectAnninspection(projectAnninspection);
			dao.add(projectAnninspectionProduct);	
		} else if(viewType == 2) {//添加中检成果
			//添加添加一条项目中检成果记录
			ProjectMidinspection projectMidinspection = (ProjectMidinspection)dao.queryUnique(
				"from ProjectMidinspection pm where pm.id = ?", inspectionId);
			ProjectMidinspectionProduct projectMidinspectionProduct = new ProjectMidinspectionProduct();
			projectMidinspectionProduct.setProduct(product);
			projectMidinspectionProduct.setProjectMidinspection(projectMidinspection);
			dao.add(projectMidinspectionProduct);	
		} else if(viewType == 3) {//添加结项成果
			//添加添加一条项目结项成果记录
			ProjectEndinspection projectEndinspection = (ProjectEndinspection)dao.queryUnique(
				"from ProjectEndinspection pe where pe.id = ?", inspectionId);
			ProjectEndinspectionProduct projectEndinspectionProduct = new ProjectEndinspectionProduct();
			projectEndinspectionProduct.setProduct(product);
			projectEndinspectionProduct.setIsFinalProduct(isFinalProduct);
			projectEndinspectionProduct.setProjectEndinspection(projectEndinspection);
			dao.add(projectEndinspectionProduct);	
		} 
	}
	
	/**
	 * 根据项目类型、项目批准号、作者id获取项目信息
	 * @param projectType: 项目类型; number：批准号; personid: 作者id
	 * @return 数组，包含项目信息
	 */
	@SuppressWarnings({ "rawtypes" })
	public String[] getProject(String projectType, String number, String personid){
		//将项目类型首字母变成大写， 获取项目类型前缀
		projectType = projectType.substring(0, 1).toUpperCase() + projectType.substring(1);
		String memberBeanName = projectType + "Member", grantedBeanName = projectType + "Granted";  
		List list = dao.query("select gra from " + memberBeanName + " mem, " + grantedBeanName + " gra where " +
			"mem.application.id = gra.application.id and gra.number = ? and mem.member.id = ?", number, personid);
		return (null != list && !list.isEmpty()) ? (String[])list.get(0) : null;
	}
	
	/**
	 * 根据中检、结项id判断该此检查下是否成果是否全部审核
	 * @param inspectionId : 中检、结项id
	 * @param type 1:年度信息; 2：中检； 3：结项
	 * @return true : 已全部审核 false : 存在未审成果
	 */
	@SuppressWarnings("rawtypes")
	public boolean isProductAuditedOfInspection(int type, String inspectionId) {
		if(type != 1 && type != 2 && type != 3) return false;
		List list = new ArrayList();
		if(type == 1) {//年度信息
			list = dao.query("select p.id from ProjectAnninspectionProduct pap left join pap.product p " +
				"where p.auditResult = 0 and pap.projectAnninspection.id = ?", inspectionId);
		}else if(type == 2) {//中检
			list = dao.query("select p.id from ProjectMidinspectionProduct pmp left join pmp.product p " +
				"where p.auditResult = 0 and pmp.projectMidinspection.id = ?", inspectionId);
		} else if (type == 3) {//结项
			list = dao.query("select p.id from ProjectEndinspectionProduct pep left join pep.product p " +
				"where p.auditResult = 0 and pep.projectEndinspection.id = ?", inspectionId);
		} 
		return (list.size() > 0) ? false : true;
	}
	
	//========================================================================================
	// 3.成果-奖励相关业务方法
	//========================================================================================
	/**
	 * 根据成果id得到成果的获奖情况
	 * @param productId : 成果id
	 * @return 奖励对象
	 */
	@SuppressWarnings("unchecked")
	public AwardGranted getAward(String productId) {
		Product product = (Product)dao.query(Product.class, productId);
		if(null == product) return null;
		AwardApplication awardApplication = (AwardApplication)dao.queryUnique(
			"from AwardApplication aa where aa.product.id = ?", productId);
		List<AwardGranted> awards = new ArrayList<AwardGranted>();
		if(null != awardApplication) {
			 awards = dao.query("from AwardGranted aw left join fetch aw.grade g where aw.application.id = ?", awardApplication.getId());
		}
		return (!awards.isEmpty()) ? awards.get(0) : null;
	}
	
	/**
	 * 根据成果id得到成果的相关获奖信息
	 * @param productId : 成果id
	 * @return map对象，包含成果获奖信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getRelAwardInfos(String productId) {
		Map map = new HashMap();
		AwardGranted award = this.getAward(productId);
		map.put("award", award);
		AwardApplication awardApplication = null;
		if(null != award){//已获奖
			SystemOption awardGrade = (SystemOption)dao.query(SystemOption.class, award.getGrade().getId());
			map.put("awardGrade", awardGrade.getName());
			awardApplication = (AwardApplication)dao.query(AwardApplication.class, award.getApplication().getId());
			map.put("dtype", awardApplication.getDisciplineType());
			map.put("isAwarded", awardApplication.getFinalAuditResult());//0:待处理	1：不获奖 2：获奖 	其他：无奖励申请
			map.put("saveDate", null);
		} else{//未获奖
			List<AwardApplication> awardApplications = dao.query(
				"from AwardApplication aa where aa.product.id = ? order by aa.applicantSubmitDate desc", productId);
			if(null != awardApplications && !awardApplications.isEmpty()){//有奖励申请
				awardApplication = awardApplications.get(0);
				map.put("isAwarded", 0);
				map.put("submitStatus", awardApplication.getApplicantSubmitStatus());//提交状态  0：默认 1:退回 2：暂存 3：提交
				if(awardApplication.getApplicantSubmitStatus() == 2 || awardApplication.getApplicantSubmitStatus() == 1){
					map.put("saveDate", awardApplication.getApplicantSubmitDate());
					map.put("file", awardApplication.getFile());
					map.put("awardApplicationId", awardApplication.getId());
				}
			} else {//无奖励申请
				map.put("isAwarded", "");
				map.put("submitStatus", 0);
				map.put("saveDate", null);
			}
		}
		return map;
	}
	
	//========================================================================================
	// 4.成果-人员、机构相关业务方法
	//========================================================================================
	/**
	 * 得到研究人员、高校、院系、研究基地的成果列表
	 * @param type 类型( 1 : 研究人员 , 2 : 高校 , 3 : 院系, 4 : 研究基地) entityId : 人员、机构id
	 * @return list对象，包含成果信息
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getProductListByEntityId(int type, String entityId){
		String hql = "select p.id, p.chineseName, p.productType, p.author.name, p.author.id, p.agencyName, p.university.id, " +
			"p.disciplineType, to_char(p.submitDate, 'yyyy-MM-dd') from Product p where p.auditResult = 2 and p.productType = ? ";
		if(type == 1) {//研究人员
			hql += "and p.author.id = ? ";
		} else if(type == 2) {//高校
			hql += "and p.university.id = ? ";
		} else if(type == 3) {//院系
			hql += "and p.department.id = ? ";
		} else if(type == 4) {//研究基地
			hql += "and p.institute.id = ? ";
		}
		hql += "order by p.submitDate desc";
		Iterator<String> it = Product.typeMap.keySet().iterator();
		List products = new ArrayList();
		while(it.hasNext()) {
			List p = dao.query(hql, it.next(), entityId);
			if(null != p && !p.isEmpty()) {
				products.addAll(p);
			}
		}
		return products;
	}
	
	/**
	 * 根据账号类型、账号所属id获取专家、教师、学生所在单位部门信息
	 * @param type : 账号类型(8.专家; 9.教师; 10.学生); personId : 账号所属人员id
	 * @return map对象，包含机构信息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getAgencyInfosByAccount(AccountType type, String personId) {
		Map<String, String> map = new HashMap<String, String>();
		switch(type) {
			case EXPERT : {//专家
				List<Expert> experts = dao.query("from Expert exp where exp.person.id = ?", personId);
				for(Expert expert : experts) {
					map.put(expert.getId(), expert.getAgencyName() + " " + expert.getDivisionName());
				}
				break;
			}
			case TEACHER : {//教师
				List<Teacher> teachers = dao.query("select tea from Teacher tea left join fetch tea.university uni left " +
					"join fetch tea.department dep left join fetch tea.institute ins where tea.person.id = ?", personId);
				for(Teacher teacher : teachers) {
					if(null != teacher.getUniversity()) {
						if(null != teacher.getDepartment()) {
							map.put(teacher.getId(), teacher.getUniversity().getName() + " " + teacher.getDepartment().getName());
						} else if(null != teacher.getInstitute()) {
							map.put(teacher.getId(), teacher.getUniversity().getName() + " " + teacher.getInstitute().getName());
						}
					}
				}
				break;
			}
			case STUDENT : {//学生
				List<Student> students = dao.query("select stu from Student stu left join fetch stu.university uni left " +
					"join fetch stu.department dep left join fetch stu.institute ins where stu.person.id = ?", personId);
				for(Student student : students) {
					if(null != student.getUniversity()) {
						if(null != student.getDepartment()) {
							map.put(student.getId(), student.getUniversity().getName() + " " + student.getDepartment().getName());
						} else if(null != student.getInstitute()) {
							map.put(student.getId(), student.getUniversity().getName() + " " + student.getInstitute().getName());
						}
					}
				}
				break;
			}
		}
		return map;
	}
	
	/**
	 * 把成果上传到DMSS
	 * @param product
	 * @return 返回dfsId
	 * @throws Exception
	 */
	public String uploadToDmss(Product product) throws Exception{
		if(dmssService.getStatus() && null != product.getFile()){ //dmss在线
			ThirdUploadForm form = new ThirdUploadForm();
			form.setTitle(getFileTitle(product.getFile()));
			form.setFileName(getFileName(product.getFile()));
			if(null != product.getAuthorName())
				form.setSourceAuthor(product.getAuthorName());
			form.setRating("0");
			form.setTags("");
			form.setCategoryPath(getDmssCategory(product.getFile()));
			String  dfsId =dmssService.upload(ApplicationContainer.sc.getRealPath(product.getFile()), form);
			return dfsId;
		}else {
			return null;
		}
	}
	
	/**
	 * 把成果检入到DMSS
	 * @param product
	 * @return 返回dfsId
	 * @throws Exception
	 */
	public String checkInToDmss(Product product) throws Exception{
		if(null != product.getFile() && null != product.getDfs()){ //现在有文件
			ThirdCheckInForm form = new ThirdCheckInForm();
			form.setComment("更新了");
			form.setFileName(getFileName(product.getFile()));
			form.setTitle(getFileTitle(product.getFile()));
			form.setId(product.getDfs());
			String dfsId = dmssService.checkIn(ApplicationContainer.sc.getRealPath(product.getFile()), form);
			return dfsId;
		}else{
			return null;
		}
	}

}