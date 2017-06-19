package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.GeneralVariation;
import csdc.bean.Institute;
import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMember;
import csdc.bean.InstpVariation;
import csdc.bean.Person;
import csdc.bean.ProjectVariation;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.importer.tool.SinossTableTool;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 
 * 
 * @author maowh pengliang
 * 
 */
@Component
public class ProjectVariationImporter extends Importer {
	
	private ExcelReader reader;
	
	private int importNumber = 0;
	
	private Map<String, SinossProjectVariation> map;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private SinossTableTool sTool;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;
	
	@Autowired
	private GeneralProjectFinder generalProjectFinder;
	
	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;
	
	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private IHibernateBaseDao dao;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	private List<SinossMembers> sinossGeneralMembers = new ArrayList<SinossMembers>();
	
	private List<SinossMembers> sinossInstpMembers = new ArrayList<SinossMembers>();
	
	private List<SinossModifyContent> sinossModifyContents = new ArrayList<SinossModifyContent>();
	
	private List error = new ArrayList();
	
	private int agencyError=0;
	
	/*
	 * 初始化中间表中所有变更项目的成员
	 */
	private List<SinossMembers> sinossMembers;	
	public void initSinossMembers(){
		Date begin = new Date();
		sinossMembers = dao.query("from SinossMembers o where o.projectVariation is not null");
		System.out.println("initSinossMembers completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
	/*
	 * 初始化中间表变更内容
	 */
	private List<SinossModifyContent> initSinossModifyContents;
	public void initSinossModifyContents(){
		Date begin = new Date();
		initSinossModifyContents = dao.query("from SinossModifyContent o where o.projectVariation is not null");
		System.out.println("initSinossModifyContents completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
	/*
	 * 初始化项目成员（初始化到内存的过程慢，数据太多，没有明显的过滤条件）
	 */
	/*private List<InstpMember> instpMembers = new ArrayList<InstpMember>();	
	private List<GeneralMember> generalMembers = new ArrayList<GeneralMember>();
	private List<ProjectMember> initProjectMembers;
	public void initProjectMembers(){
		Date begin = new Date();
		initProjectMembers = dao.query("select o from ProjectMember o where o.projectType='general' or o.projectType='instp'");
		System.out.println("initProjectMembers completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}*/
	
	@Override
	public void work() throws Throwable{
	//	initSinossMembers();
	//	initSinossModifyContents();
	//	importData();
		importCheckData();  //审核信息与数据分开导入
	}

	@SuppressWarnings("unchecked")	
	public void importData() throws Throwable {
		List<SinossProjectVariation> sinossProjectVariations =(List<SinossProjectVariation>)dao.query("select o from SinossProjectVariation o where isAdded = 1");
		
		for(int i = 0;i < sinossProjectVariations.size();i++){	
			System.out.println("读到第" + (i+1) + "条数据" + "项目名称为：" + sinossProjectVariations.get(i).getName());			
			String codeString = sinossProjectVariations.get(i).getCode();//项目编号
			String projectType = sinossProjectVariations.get(i).getTypeCode();//项目类别
			String variatonReason = sinossProjectVariations.get(i).getModifyReason();//变更原因
			
			Date applyDate = sinossProjectVariations.get(i).getApplyDate();//提交时间
			Date checkDate = sinossProjectVariations.get(i).getCheckDate();//最后审核日期
			int checkStatus = sinossProjectVariations.get(i).getCheckStatus();//审核状态
				
			//基地项目变更
			if (projectType.contains("base")) {
				InstpGranted granted = instpProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(基地项目)											
				importNumber++;
				InstpVariation instpVariation = new InstpVariation();
				granted.addVariation(instpVariation);//建立关联关系
				sinossProjectVariations.get(i).setProjectVariation(instpVariation); //中间表中C_VARIATION_ID是我们入库的时候要加进去的，表示这条数据和我们库里面的数据时对应的
				sinossProjectVariations.get(i).setProjectGranted(granted);//将立项实体填入临时表
				sinossProjectVariations.get(i).setImportedDate(new Date());
				sinossProjectVariations.get(i).setProjectApplication(granted.getApplication());
				instpVariation.setImportedDate(new Date());//入库时间
				instpVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				instpVariation.setApplicantSubmitStatus(3);
				instpVariation.setVariationReason(variatonReason);//变更原因
				instpVariation.setDeptInstAuditResult(2);//院系审核都通过
				instpVariation.setDeptInstAuditStatus(3);
				instpVariation.setStatus(5);
				//记录变更类型
				StringBuffer deptAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("00000000000000000000");
				String variationId = sinossProjectVariations.get(i).getId();
				
				//变更项目负责人或则项目相关内容
			//	List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);//获取变更项目的变更内容（项目变更表 -> 变更内容表）；一条变更id可能对应多条变更内容
				if(!sinossModifyContents.isEmpty()){
					sinossModifyContents.clear();
	            }
				Iterator<SinossModifyContent> smcIterator = initSinossModifyContents.iterator();
				while(smcIterator.hasNext()){
					SinossModifyContent smc = smcIterator.next();
					if(smc.getProjectVariation().getId().equals(variationId)){
						sinossModifyContents.add(smc);
					}
				}
				if (sinossModifyContents.size() == 0) {					
					System.out.println("变更项目一般成员而不是项目相关内容和负责人");			
				} else {
					for(int j = 0;j < sinossModifyContents.size();j++){    //负责人和项目内容begin
						SinossModifyContent sinossModifyContent = sinossModifyContents.get(j);
						String modifyMean = sinossModifyContent.getModifyFieldMean();
						if (modifyMean.contains("其他")) {									
							instpVariation.setOther(1);
							instpVariation.setOtherInfo(sinossModifyContent.getBeforeValue() + "▶" + sinossModifyContent.getAfterValue());
							deptAuditResultDetail.setCharAt(19, '1');
							universityAuditResultDetail.setCharAt(19, '1');
							provinceAuditResultDetail.setCharAt(19, '1');
						} else if (modifyMean.contains("延期")) {				
							instpVariation.setPostponement(1);
							instpVariation.setOldOnceDate(granted.getPlanEndDate());
							instpVariation.setNewOnceDate(tool.getDate(sinossModifyContent.getAfterValue()));	
							deptAuditResultDetail.setCharAt(5, '1');
							universityAuditResultDetail.setCharAt(5, '1');
							provinceAuditResultDetail.setCharAt(5, '1');						
						} else if (modifyMean.contains("改变项目名称")) {
							instpVariation.setChangeName(1);
							instpVariation.setOldName(sinossModifyContent.getBeforeValue());
							instpVariation.setNewName(sinossModifyContent.getAfterValue());	
							deptAuditResultDetail.setCharAt(3, '1');
							universityAuditResultDetail.setCharAt(3, '1');
							provinceAuditResultDetail.setCharAt(3, '1');
						} else if (modifyMean.contains("申请撤项")) {		
							instpVariation.setWithdraw(1);
							deptAuditResultDetail.setCharAt(7, '1');
							universityAuditResultDetail.setCharAt(7, '1');
							provinceAuditResultDetail.setCharAt(7, '1');
						} else if (modifyMean.contains("重大研究内容调整")) {	
							instpVariation.setChangeContent(1);
							deptAuditResultDetail.setCharAt(4, '1');
							universityAuditResultDetail.setCharAt(4, '1');
							provinceAuditResultDetail.setCharAt(4, '1');
						} else if (modifyMean.contains("改变成果形式")) {	
							instpVariation.setChangeProductType(1);
							if (sinossModifyContent.getBeforeValue() != null) {
								instpVariation.setOldProductType(sinossModifyContent.getBeforeValue());
							} else {
								instpVariation.setOldProductType(granted.getProductType());
							}
							instpVariation.setNewProductType(sinossModifyContent.getAfterValue().replaceAll(",", ";"));
							deptAuditResultDetail.setCharAt(2, '1');
							universityAuditResultDetail.setCharAt(2, '1');
							provinceAuditResultDetail.setCharAt(2, '1');
						} else if (modifyMean.contains("变更项目管理单位")) {	
							deptAuditResultDetail.setCharAt(1, '1');
							universityAuditResultDetail.setCharAt(1, '1');
							provinceAuditResultDetail.setCharAt(1, '1');							
							instpVariation.setChangeAgency(1);
							Agency oldAgency = universityFinder.getUnivByName(sinossModifyContent.getBeforeValue());
							Agency newAgency = universityFinder.getUnivByName(sinossModifyContent.getAfterValue());							
							instpVariation.setOldAgencyName(sinossModifyContent.getBeforeValue());
							instpVariation.setNewAgencyName(sinossModifyContent.getAfterValue());							
							if (oldAgency != null){
								instpVariation.setOldAgency(oldAgency);
								instpVariation.setOldDepartment(departmentFinder.getDepartment(oldAgency, null, false));
								instpVariation.setOldDivisionName(instpVariation.getOldDepartment().getName());								
							} 								
							if (newAgency != null) {
								instpVariation.setNewAgency(newAgency);
								instpVariation.setNewDepartment(departmentFinder.getDepartment(newAgency, null, false));
								instpVariation.setNewDivisionName(instpVariation.getNewDepartment().getName());
							}																					
						} else if (modifyMean.contains("变更项目责任人")) {
							deptAuditResultDetail.setCharAt(0, '1');
							universityAuditResultDetail.setCharAt(0, '1');
							provinceAuditResultDetail.setCharAt(0, '1');
							instpVariation.setChangeMember(1);
							instpVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());
							String applicationId = granted.getApplicationId();
							InstpApplication application = granted.getApplication();
							List<InstpMember> instpMembers = (List<InstpMember>)dao.query("select o from InstpMember o left join o.application app where app.id = ?",applicationId);
							//GroupNumber组号：表示每一段时期的项目组成员，如果有成员变更，GroupNumber就会+1（第一次申请项目时GroupNumber=1）
							int flag = 1;
							for(int k = 0; k < instpMembers.size(); k++){  
								if (instpMembers.get(k).getGroupNumber()>flag) { 
									flag = instpMembers.get(k).getGroupNumber();
								}
							}
							//重新设置变更项目负责人后下一阶段所有普通成员信息（和上一次相同），组号+1
							for(int k = 0; k < instpMembers.size(); k++){   
								if ((instpMembers.get(k).getIsDirector() == 0) && (instpMembers.get(k).getGroupNumber() == flag)) {
									InstpMember instpMember = new InstpMember();
									instpMember.setMember(instpMembers.get(k).getMember());
									instpMember.setUniversity(instpMembers.get(k).getUniversity());
									instpMember.setDepartment(instpMembers.get(k).getDepartment());
									instpMember.setInstitute(instpMembers.get(k).getInstitute());
									instpMember.setAgencyName(instpMembers.get(k).getAgencyName());
									instpMember.setDivisionName(instpMembers.get(k).getDivisionName());
									instpMember.setSpecialistTitle(instpMembers.get(k).getSpecialistTitle());
									instpMember.setMajor(instpMembers.get(k).getMajor());
									instpMember.setIsDirector(0);
									instpMember.setWorkDivision(instpMembers.get(k).getWorkDivision());
									instpMember.setMemberName(instpMembers.get(k).getMemberName());
									instpMember.setMemberSn(instpMembers.get(k).getMemberSn());
									instpMember.setMemberType(instpMembers.get(k).getMemberType());
									instpMember.setWorkMonthPerYear(instpMembers.get(k).getWorkMonthPerYear());
									instpMember.setGroupNumber(flag + 1);     
									instpMember.setIdcardType(instpMembers.get(k).getIdcardType());
									instpMember.setGender(instpMembers.get(k).getGender());
									instpMember.setDivisionType(instpMembers.get(k).getDivisionType());
									instpMember.setIdcardNumber(instpMembers.get(k).getIdcardNumber());
									instpMember.setProjectType(instpMembers.get(k).getProjectType());
									instpMember.setApplication(instpMembers.get(k).getApplication());
									dao.add(instpMember);
								} 
							}
							//为项目下一阶段添加负责人(变更责任人的项目相当少，经查证都是基地项目，因此在此穷举)
							InstpMember instpMember = new InstpMember();
							Person person = (Person) dao.queryUnique("from Person p where p.idcardNumber = ?", sinossModifyContent.getIdNumber());
							if(person == null){
								person = new Person();
								person.setIdcardNumber(sinossModifyContent.getIdNumber());
							}
							instpMember.setMember(person);									
							if (sinossModifyContent.getAfterValue().contains("罗江华")) {  
								//person.setName(tool.getName(sinossModifyContent.getAfterValue()));
								person.setName("罗江华");
								person.setGender("男");
								dao.add(person);
								instpMember.setAgencyName("西南大学");
								instpMember.setGender("男");
							} else if (sinossModifyContent.getAfterValue().contains("严兵")) {
								person.setName("严兵");
								person.setGender("男");
								dao.add(person);
								instpMember.setAgencyName("南开大学");
								instpMember.setGender("男");
							} else if(sinossModifyContent.getAfterValue().contains("王望波")){
								person.setName("王望波");
								person.setGender("男");
								dao.add(person);
								instpMember.setAgencyName("厦门大学");
								instpMember.setGender("男");
							} else if (sinossModifyContent.getAfterValue().contains("姜琳")){
								person.setName("姜琳");
								person.setGender("女");
								dao.add(person);
								instpMember.setAgencyName("广东外语外贸大学");
								instpMember.setGender("女");
							} else if (sinossModifyContent.getAfterValue().contains("杨静")){
								person.setName("杨静");
								person.setGender("女");
								dao.add(person);
								instpMember.setAgencyName("四川师范大学");
								instpMember.setGender("女");
							} else if (sinossModifyContent.getAfterValue().contains("吴捷")){
								person.setName("吴捷");
								person.setGender("男");
								dao.add(person);
								instpMember.setAgencyName("天津师范大学");
								instpMember.setGender("男");
							} else if (sinossModifyContent.getAfterValue().contains("郑崇选")){
								person.setName("郑崇选");
								person.setGender("男");
								dao.add(person);
								instpMember.setAgencyName("上海师范大学");
								instpMember.setGender("男");
							} else if (sinossModifyContent.getAfterValue().contains("杨明洪")){
								person.setName("杨明洪");
								person.setGender("男");
								dao.add(person);
								instpMember.setAgencyName("四川大学");
								instpMember.setGender("男");
							}					
							instpMember.setIdcardNumber(sinossModifyContent.getIdNumber());
							instpMember.setApplication(application);
							instpMember.setGroupNumber(flag + 1);
							instpMember.setIdcardType("身份证");
							instpMember.setIsDirector(1);
							instpMember.setMemberType(1);
							instpMember.setMemberSn(1);
							instpMember.setMemberName(sinossModifyContent.getAfterValue());
							instpVariation.setNewMemberGroupNumber(flag + 1);	
							dao.add(instpMember);
						} else {
							System.out.println("该条数据修改内容有问题（variationId）：" + variationId);
							error.add("Instp-变更类型不存在：" + variationId + "--" + sinossProjectVariations.get(i).getName() + "--" + modifyMean);
						//	throw new RuntimeException();
					    }
					}											
				}
				
				//变更项目一般成员（非负责人）
	//			List<SinossMembers> sinossInstpMembers = (List<SinossMembers>)dao.query("from SinossMembers o where o.projectVariation.id = ? ",variationId);						
				if(!sinossInstpMembers.isEmpty()){
	            	sinossInstpMembers.clear();
	            }
				Iterator<SinossMembers> memIterator = sinossMembers.iterator();
				while(memIterator.hasNext()){
					SinossMembers sm = memIterator.next();
					if(sm.getProjectVariation().getId().equals(variationId)){
						sinossInstpMembers.add(sm);
					}
				}
				if (sinossInstpMembers.size() != 0) { //变更普通成员begin
					deptAuditResultDetail.setCharAt(0, '1');
					universityAuditResultDetail.setCharAt(0, '1');
					provinceAuditResultDetail.setCharAt(0, '1');
					instpVariation.setChangeMember(1);
					instpVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());						
					String applicationId = granted.getApplicationId();
					InstpApplication application = granted.getApplication();
					List<InstpMember> instpMembers = dao.query("select o from InstpMember o left join o.application app where app.id = ?",applicationId);
					int flag = 1;
					for(int k = 0;k < instpMembers.size();k++){
						if (instpMembers.get(k).getGroupNumber()>flag) {
							flag = instpMembers.get(k).getGroupNumber();
						}
					}
					//为新的组添加项目负责人
					for(int k = 0;k < instpMembers.size();k++){ 
						if (instpMembers.get(k).getIsDirector() == 1) {
							InstpMember instpMember = new InstpMember();
							instpMember.setMember(instpMembers.get(k).getMember());
							instpMember.setUniversity(instpMembers.get(k).getUniversity());
							instpMember.setDepartment(instpMembers.get(k).getDepartment());
							instpMember.setInstitute(instpMembers.get(k).getInstitute());
							instpMember.setAgencyName(instpMembers.get(k).getAgencyName());
							instpMember.setDivisionName(instpMembers.get(k).getDivisionName());
							instpMember.setSpecialistTitle(instpMembers.get(k).getSpecialistTitle());
							instpMember.setMajor(instpMembers.get(k).getMajor());
							instpMember.setIsDirector(1);
							instpMember.setWorkDivision(instpMembers.get(k).getWorkDivision());
							instpMember.setMemberName(instpMembers.get(k).getMemberName());
							instpMember.setMemberSn(1);
							instpMember.setMemberType(instpMembers.get(k).getMemberType());
							instpMember.setWorkMonthPerYear(instpMembers.get(k).getWorkMonthPerYear());
							instpMember.setGroupNumber(flag + 1);
							instpMember.setIdcardType(instpMembers.get(k).getIdcardType());
							instpMember.setGender(instpMembers.get(k).getGender());
							instpMember.setDivisionType(instpMembers.get(k).getDivisionType());
							instpMember.setIdcardNumber(instpMembers.get(k).getIdcardNumber());
							instpMember.setProjectType(instpMembers.get(k).getProjectType());
							instpMember.setApplication(instpMembers.get(k).getApplication());
							dao.add(instpMember);
						}
					}
					//为新的组添加项目一般成员
					for(int j = 0;j < sinossInstpMembers.size();j++){   
						String memberName = sinossInstpMembers.get(j).getName();
						String unitName = sinossInstpMembers.get(j).getUnitName();
						if ((memberName != null) &&(unitName != null)) {
							String specialistTitle = sinossInstpMembers.get(j).getTitle();
							String workDivision = sinossInstpMembers.get(j).getDivide();
							int memberSn = -1;
							if (sinossInstpMembers.get(j).getOrders() != null) {
								memberSn = Integer.parseInt(sinossInstpMembers.get(j).getOrders()) + 1;
							}
							int groupNumber = flag + 1;
							Object instpMember = getMember(memberName, unitName);
							addInstpMember(application, instpMember, specialistTitle, workDivision, memberSn, groupNumber);
						}
					}
					instpVariation.setNewMemberGroupNumber(flag + 1);
				}
				
				//变更项目审核信息								
				if (checkStatus == 2) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(2);
					instpVariation.setUniversityAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 3) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(1);
					instpVariation.setUniversityAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 4) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(2);
					instpVariation.setUniversityAuditStatus(3);
					instpVariation.setProvinceAuditResult(2);
					instpVariation.setProvinceAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					instpVariation.setProvinceAuditStatus(3);
				}else if (checkStatus == 5) {
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
					instpVariation.setUniversityAuditResult(2);
					instpVariation.setUniversityAuditStatus(3);
					instpVariation.setProvinceAuditResult(1);
					instpVariation.setProvinceAuditDate(checkDate);
					instpVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					instpVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					instpVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					instpVariation.setProvinceAuditStatus(3);
				}else {
					//如果不是上面几个审核状态，就表示这些数据未提交审核，就不用处理审核信息
					error.add("Instp-审核状态码不存在：：" + variationId + "--" + sinossProjectVariations.get(i).getName() + "--" + checkStatus);					
				}
				sinossProjectVariations.get(i).setIsAdded(0);
			}												
				

			//一般项目变更
			if (projectType.contains("gener")) {
				GeneralGranted granted = generalProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(一般项目)
				importNumber++;
				GeneralVariation generalVariation = new GeneralVariation();
				granted.addVariation(generalVariation);//建立关联关系
				sinossProjectVariations.get(i).setImportedDate(new Date());
				sinossProjectVariations.get(i).setProjectGranted(granted);//将立项id填入临时表
				sinossProjectVariations.get(i).setProjectVariation(generalVariation);
				sinossProjectVariations.get(i).setProjectApplication(granted.getApplication());
//					String grantIdString = granted.getId();//得到项目的立项id
				generalVariation.setImportedDate(new Date());//入库时间
				generalVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				generalVariation.setApplicantSubmitStatus(3);
				generalVariation.setVariationReason(variatonReason);//变更原因
				generalVariation.setDeptInstAuditResult(2);//院系审核都通过
				generalVariation.setDeptInstAuditStatus(3);
				generalVariation.setStatus(5);
				StringBuffer deptAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("00000000000000000000");
				
				String variationId = sinossProjectVariations.get(i).getId();//得到变更id				
				//List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);					
				if(!sinossModifyContents.isEmpty()){
					sinossModifyContents.clear();
	            }
				Iterator<SinossModifyContent> smcIterator = initSinossModifyContents.iterator();
				while(smcIterator.hasNext()){
					SinossModifyContent smc = smcIterator.next();
					if(smc.getProjectVariation().getId().equals(variationId)){
						sinossModifyContents.add(smc);
					}
				}
				if (sinossModifyContents.size() == 0) {					
					System.out.println("变更项目一般成员而不是项目相关内容和负责人");						
				} else {
					for(int j = 0;j < sinossModifyContents.size();j++){
						SinossModifyContent sinossModifyContent = sinossModifyContents.get(j);
						String modifyMean = sinossModifyContent.getModifyFieldMean();							
						if (modifyMean.contains("其他")) {
							generalVariation.setOther(1);
							generalVariation.setOtherInfo(sinossModifyContent.getBeforeValue() + "▶" + sinossModifyContent.getAfterValue());
							deptAuditResultDetail.setCharAt(19, '1');
							universityAuditResultDetail.setCharAt(19, '1');
							provinceAuditResultDetail.setCharAt(19, '1');					
						} else if (modifyMean.contains("延期")) {	
							generalVariation.setPostponement(1);
							generalVariation.setOldOnceDate(granted.getPlanEndDate());
							generalVariation.setNewOnceDate(tool.getDate(sinossModifyContent.getAfterValue()));	
							deptAuditResultDetail.setCharAt(5, '1');
							universityAuditResultDetail.setCharAt(5, '1');
							provinceAuditResultDetail.setCharAt(5, '1');									
						} else if (modifyMean.contains("改变项目名称")) {
							generalVariation.setChangeName(1);
							generalVariation.setOldName(sinossModifyContent.getBeforeValue());
							generalVariation.setNewName(sinossModifyContent.getAfterValue());	
							deptAuditResultDetail.setCharAt(3, '1');
							universityAuditResultDetail.setCharAt(3, '1');
							provinceAuditResultDetail.setCharAt(3, '1');									
						} else if (modifyMean.contains("申请撤项")) {	
							generalVariation.setWithdraw(1);
							deptAuditResultDetail.setCharAt(7, '1');
							universityAuditResultDetail.setCharAt(7, '1');
							provinceAuditResultDetail.setCharAt(7, '1');								
						} else if (modifyMean.contains("重大研究内容调整")) {		
							generalVariation.setChangeContent(1);
							deptAuditResultDetail.setCharAt(4, '1');
							universityAuditResultDetail.setCharAt(4, '1');
							provinceAuditResultDetail.setCharAt(4, '1');									
						} else if (modifyMean.contains("改变成果形式")) {
							generalVariation.setChangeProductType(1);
							if (sinossModifyContent.getBeforeValue() != null) {
								generalVariation.setOldProductType(sinossModifyContent.getBeforeValue());
							} else {
								generalVariation.setOldProductType(granted.getProductType());
							}
							generalVariation.setNewProductType(sinossModifyContent.getAfterValue().replaceAll(",", ";"));
							deptAuditResultDetail.setCharAt(2, '1');
							universityAuditResultDetail.setCharAt(2, '1');
							provinceAuditResultDetail.setCharAt(2, '1');								
						} else if (modifyMean.contains("变更项目管理单位")) {	
							deptAuditResultDetail.setCharAt(1, '1');
							universityAuditResultDetail.setCharAt(1, '1');
							provinceAuditResultDetail.setCharAt(1, '1');							
							generalVariation.setChangeAgency(1);
							Agency oldAgency = universityFinder.getUnivByName(sinossModifyContent.getBeforeValue());
							Agency newAgency = universityFinder.getUnivByName(sinossModifyContent.getAfterValue());
							
							generalVariation.setOldAgencyName(sinossModifyContent.getBeforeValue());
							generalVariation.setNewAgencyName(sinossModifyContent.getAfterValue());								
							if (oldAgency != null){
								generalVariation.setOldAgency(oldAgency);
								generalVariation.setOldDepartment(departmentFinder.getDepartment(oldAgency, null, false));
								generalVariation.setOldDivisionName(generalVariation.getOldDepartment().getName());								
							} 								
							if (newAgency != null) {
								generalVariation.setNewAgency(newAgency);
								generalVariation.setNewDepartment(departmentFinder.getDepartment(newAgency, null, false));
								generalVariation.setNewDivisionName(generalVariation.getNewDepartment().getName());
							}																					
						} else if (modifyMean.contains("变更项目责任人")) {	
							deptAuditResultDetail.setCharAt(0, '1');
							universityAuditResultDetail.setCharAt(0, '1');
							provinceAuditResultDetail.setCharAt(0, '1');
							generalVariation.setChangeMember(1);
							generalVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());
							String applicationId = granted.getApplicationId();
							GeneralApplication application = granted.getApplication();
							
							List<GeneralMember> generalMembers = (List<GeneralMember>)dao.query("from GeneralMember o where o.application.id = ?",applicationId);
							int flag = 1;
							for(int k = 0;k < generalMembers.size();k++){
								if (generalMembers.get(k).getGroupNumber()>flag) {
									flag = generalMembers.get(k).getGroupNumber();
								}
							}
							for(int k = 0;k < generalMembers.size();k++) {
								if ((generalMembers.get(k).getIsDirector() == 0) && (generalMembers.get(k).getGroupNumber() == flag)) {
									GeneralMember generalMember = new GeneralMember();
									generalMember.setMember(generalMembers.get(k).getMember());
									generalMember.setUniversity(generalMembers.get(k).getUniversity());
									generalMember.setDepartment(generalMembers.get(k).getDepartment());
									generalMember.setInstitute(generalMembers.get(k).getInstitute());
									generalMember.setAgencyName(generalMembers.get(k).getAgencyName());
									generalMember.setDivisionName(generalMembers.get(k).getDivisionName());
									generalMember.setSpecialistTitle(generalMembers.get(k).getSpecialistTitle());
									generalMember.setMajor(generalMembers.get(k).getMajor());
									generalMember.setIsDirector(0);
									generalMember.setWorkDivision(generalMembers.get(k).getWorkDivision());
									generalMember.setMemberName(generalMembers.get(k).getMemberName());
									generalMember.setMemberSn(generalMembers.get(k).getMemberSn());
									generalMember.setMemberType(generalMembers.get(k).getMemberType());
									generalMember.setWorkMonthPerYear(generalMembers.get(k).getWorkMonthPerYear());
									generalMember.setGroupNumber(flag + 1);
									generalMember.setIdcardType(generalMembers.get(k).getIdcardType());
									generalMember.setGender(generalMembers.get(k).getGender());
									generalMember.setDivisionType(generalMembers.get(k).getDivisionType());
									generalMember.setIdcardNumber(generalMembers.get(k).getIdcardNumber());
									generalMember.setProjectType(generalMembers.get(k).getProjectType());
									generalMember.setApplication(generalMembers.get(k).getApplication());
									dao.add(generalMember);
								}
							}
							GeneralMember generalMember = new GeneralMember();
							generalMember.setIdcardNumber(sinossModifyContent.getIdNumber());
							generalMember.setApplication(application);
							generalMember.setGroupNumber(flag + 1);
							generalMember.setIdcardType("身份证");
							generalMember.setIsDirector(1);
							generalMember.setMemberName(sinossModifyContent.getAfterValue());
							generalMember.setMemberSn(1);
							generalMember.setMemberType(1);
							generalVariation.setNewMemberGroupNumber(flag + 1);
							dao.add(generalMember);
						} else {
							System.out.println("该条数据修改内容有问题（variationId）：" + variationId);
							error.add("gener-变更类型不存在：" + variationId + "--" + sinossProjectVariations.get(i).getName() + "--" + modifyMean);
						//	throw new RuntimeException();
						}
					}						
				}
								 
		//		List<SinossMembers> sinossGeneralMembers = (List<SinossMembers>)dao.query("from SinossMembers o where o.projectVariation.id = ? ",variationId);//只变更项目成员，不变更项目负责人
				if(!sinossGeneralMembers.isEmpty()){  
					sinossGeneralMembers.clear();
	            }
				Iterator<SinossMembers> memIterator = sinossMembers.iterator();
				while(memIterator.hasNext()){
					SinossMembers sm = memIterator.next();
					if(sm.getProjectVariation().getId().equals(variationId)){
						sinossGeneralMembers.add(sm);
					}
				}														
				if (sinossGeneralMembers.size() != 0) {  //只变更项目成员，不变更项目负责人
					deptAuditResultDetail.setCharAt(0, '1');
					universityAuditResultDetail.setCharAt(0, '1');
					provinceAuditResultDetail.setCharAt(0, '1');
					generalVariation.setChangeMember(1);
					generalVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());
					
					String applicationId = granted.getApplicationId();
					GeneralApplication application = granted.getApplication();
					List<GeneralMember> generalMembers = dao.query("from GeneralMember o where o.application.id = ?",applicationId);
					int flag = 1;
					for(int k = 0;k < generalMembers.size();k++){
						if (generalMembers.get(k).getGroupNumber()>flag) {
							flag = generalMembers.get(k).getGroupNumber();
						}
					}
					for(int k = 0;k < generalMembers.size();k++){
						if (generalMembers.get(k).getIsDirector() == 1) {
							GeneralMember generalMember = new GeneralMember();
							generalMember.setMember(generalMembers.get(k).getMember());
							generalMember.setUniversity(generalMembers.get(k).getUniversity());
							generalMember.setDepartment(generalMembers.get(k).getDepartment());
							generalMember.setInstitute(generalMembers.get(k).getInstitute());
							generalMember.setAgencyName(generalMembers.get(k).getAgencyName());
							generalMember.setDivisionName(generalMembers.get(k).getDivisionName());
							generalMember.setSpecialistTitle(generalMembers.get(k).getSpecialistTitle());
							generalMember.setMajor(generalMembers.get(k).getMajor());
							generalMember.setIsDirector(1);
							generalMember.setWorkDivision(generalMembers.get(k).getWorkDivision());
							generalMember.setMemberName(generalMembers.get(k).getMemberName());
							generalMember.setMemberSn(generalMembers.get(k).getMemberSn());
							generalMember.setMemberType(generalMembers.get(k).getMemberType());
							generalMember.setWorkMonthPerYear(generalMembers.get(k).getWorkMonthPerYear());
							generalMember.setGroupNumber(flag + 1);
							generalMember.setIdcardType(generalMembers.get(k).getIdcardType());
							generalMember.setGender(generalMembers.get(k).getGender());
							generalMember.setDivisionType(generalMembers.get(k).getDivisionType());
							generalMember.setIdcardNumber(generalMembers.get(k).getIdcardNumber());
							generalMember.setProjectType(generalMembers.get(k).getProjectType());
							generalMember.setApplication(generalMembers.get(k).getApplication());
							dao.add(generalMember);
						}
					}
					for(int j = 0;j < sinossGeneralMembers.size();j++){							
						String memberName = sinossGeneralMembers.get(j).getName();
						String unitName = sinossGeneralMembers.get(j).getUnitName();
						if ((memberName != null) &&(unitName != null)) {
							String specialistTitle = sinossGeneralMembers.get(j).getTitle();
							String workDivision = sinossGeneralMembers.get(j).getDivide();
							int memberSn = -1;
							if (sinossGeneralMembers.get(j).getOrders() != null) {
								memberSn = Integer.parseInt(sinossGeneralMembers.get(j).getOrders()) + 1;
							}
							int groupNumber = flag + 1;
							Object generalMember = getMember(memberName, unitName);
							addGeneralMember(application, generalMember, specialistTitle, workDivision, memberSn, groupNumber);
						}
					}
					generalVariation.setNewMemberGroupNumber(flag + 1);
				}
														
				if (checkStatus == 2) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 3) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(1);
					generalVariation.setUniversityAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 4) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditStatus(3);
					generalVariation.setProvinceAuditResult(2);
					generalVariation.setProvinceAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					generalVariation.setProvinceAuditStatus(3);
				}else if (checkStatus == 5) {
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditStatus(3);
					generalVariation.setProvinceAuditResult(1);
					generalVariation.setProvinceAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setProvinceAuditResultDetail(provinceAuditResultDetail.toString());
					generalVariation.setProvinceAuditStatus(3);
				}else {
					error.add("gener-审核状态码不存在：：" + variationId + "--" + sinossProjectVariations.get(i).getName() + "--" + checkStatus);
				}							
				sinossProjectVariations.get(i).setIsAdded(0);					
			}				
		}
		System.out.println("共有" + sinossProjectVariations.size() + "条数据");
		System.out.println("共入库" + importNumber + "条数据");		
		System.out.println(agencyError);
	//	System.out.println(error.toString().replaceAll(",\\s+", "\n"));
	//	int i = 1/0;
	}
	
	private Object getMember(String memberName, String unitName) throws Exception {
		Object member = new Object();
		memberName = memberName.replaceAll(" ", "");
		unitName = unitName.replaceAll(" ", "");

		Agency memberUniv = null;
		Department memberDept = null;
		Institute memberInst = null;
		String divisionName = null;
		for (int len = unitName.length(); memberUniv == null && len >= 1; len--) {
			for (int j = 0; memberUniv == null && j + len <= unitName.length(); j++) {
				memberUniv = universityFinder.getUnivByName(unitName.substring(j, j + len));
				divisionName = unitName.substring(j + len);
			}
		}
		
		if (memberUniv == null) {
			member = expertFinder.findExpert(memberName, unitName);
		} else {
			memberInst = instituteFinder.getInstitute(memberUniv, divisionName, false);
			if (memberInst == null) {
				memberDept = departmentFinder.getDepartment(memberUniv, divisionName, true);
			}
			member = univPersonFinder.findTeacher(memberName, memberUniv, memberDept, memberInst);
		}
		return member;
	}
	
	private void addGeneralMember(GeneralApplication application, Object oMember, String specialistTitle, String workDivision, int memberSn, int groupNumber) {
		GeneralMember member = new GeneralMember();
		application.addMember(member);
		
		Person person = null;
		if (oMember instanceof Teacher) {
			Teacher teacher = (Teacher) oMember;
			person = teacher.getPerson();
			member.setUniversity(teacher.getUniversity());
			member.setInstitute(teacher.getInstitute());
			member.setDepartment(teacher.getDepartment());
			if (teacher.getUniversity().getName() != null) {
				member.setAgencyName(teacher.getUniversity().getName());
			} else {
				System.out.println("出错：" + application.getName());
				member.setAgencyName("未知机构");  
				agencyError++;
			}
			if (teacher.getDepartment() != null) {
				member.setDivisionName(teacher.getDepartment().getName());
				member.setDivisionType(2);
			} else {
				member.setDivisionName(teacher.getInstitute().getName());
				member.setDivisionType(1);
			}
			
			member.setMemberType(1);
		} else {
			Expert expert = (Expert) oMember;
			person = expert.getPerson();
			if (expert.getAgencyName() != null) {
				member.setAgencyName(expert.getAgencyName());
			} else {
				System.out.println("出错：" + application.getName());
				member.setAgencyName("未知机构");
				agencyError++;
			}
			member.setMemberType(2);
		}
		
		member.setMember(person);
		member.setMemberName(person.getName());
		
		member.setSpecialistTitle(specialistTitle);
		member.setWorkDivision(workDivision);
		member.setMemberSn(memberSn);
		member.setGroupNumber(groupNumber);
	}
	
	private void addInstpMember(InstpApplication application, Object oMember, String specialistTitle, String workDivision, int memberSn, int groupNumber) {
		InstpMember member = new InstpMember();
		application.addMember(member);
		
		Person person = null;
		if (oMember instanceof Teacher) {
			Teacher teacher = (Teacher) oMember;
			person = teacher.getPerson();
			member.setUniversity(teacher.getUniversity());
			member.setInstitute(teacher.getInstitute());
			member.setDepartment(teacher.getDepartment());
			if (teacher.getUniversity().getName() != null) {
				member.setAgencyName(teacher.getUniversity().getName());
			} else {
				System.out.println("出错！" + application.getName());
				member.setAgencyName("未知机构");
				agencyError++;
			}
			if (teacher.getDepartment() != null) {
				member.setDivisionName(teacher.getDepartment().getName());
				member.setDivisionType(2);
			} else {
				member.setDivisionName(teacher.getInstitute().getName());
				member.setDivisionType(1);
			}
			
			member.setMemberType(1);
		} else {
			Expert expert = (Expert) oMember;
			person = expert.getPerson();
			if (expert.getAgencyName() != null) {
				member.setAgencyName(expert.getAgencyName());
			} else {
				System.out.println("出错！" + application.getName());
				member.setAgencyName("未知机构");
				agencyError++;
			}

			member.setMemberType(2);
		}
		
		member.setMember(person);
		member.setMemberName(person.getName());
		
		member.setSpecialistTitle(specialistTitle);
		member.setWorkDivision(workDivision);
		member.setMemberSn(memberSn);
		member.setGroupNumber(groupNumber);
	}
	
	@SuppressWarnings("unchecked")
	public void importCheckData() {//10月16 17 11月04号   11月07号  12月09号 
		 //从中间表中录入审核信息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String importedDate = "2014-12-09"; //录在变更中的中检记录
		List<ProjectVariation> projectVariations = dao.query("from ProjectVariation so where to_char(so.importedDate, 'yyyy-MM-dd') = ?", importedDate);
		List<String> badDatas = new ArrayList<String>();
		
		//在中间表中根据中检表中的立项id搜索对应数据
		for (ProjectVariation projectVariation: projectVariations) {
			if(projectVariation.getOtherInfo() !=null && projectVariation.getOtherInfo().equals("申请项目中检延期。")){
				continue;
			}
			List<SinossProjectVariation> sProjectVariations = dao.query("from SinossProjectVariation o where o.projectGranted.id = ?", projectVariation.getGrantedId());
			if(sProjectVariations.size() == 0){
				continue;
			}
			if (sProjectVariations.size() != 1) {								
				badDatas.add(projectVariation.getGrantedId());
				for(SinossProjectVariation sProjectVariation : sProjectVariations){
					if(sdf.format(sProjectVariation.getDumpDate()).equals("2014-09-02") || sdf.format(sProjectVariation.getDumpDate()).equals("2014-11-05")){
						System.out.println(projectVariation.getGrantedId());  
						addCheckInfoDetial(sProjectVariation.getId(),projectVariation);
					}
				}
			} else {
				System.out.println(sProjectVariations.get(0).getName());
				addCheckInfoDetial(sProjectVariations.get(0).getId(),projectVariation);
			}
		}
		for (String badData : badDatas) {
			System.out.println(badData);
		}
		 
	}
	
	public void addCheckInfoDetial(String sProjectVariationId,ProjectVariation projectVariation) {
		//在审核记录表中根据中间表中的立项id搜索对审核记录数据
		List<SinossChecklogs> sChecklogs = dao.query("from SinossChecklogs o where o.projectVariation.id = ?", sProjectVariationId);
		SinossChecklogs[] maxSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门审核记录
		for (SinossChecklogs sChecklog : sChecklogs) {
			int checkStatus = sChecklog.getCheckStatus();
			if (checkStatus == 2 || checkStatus ==3 ) {//校级审核
					maxSChecklogs[0] = (maxSChecklogs[0] != null && sChecklog.getCheckDate().before(maxSChecklogs[0].getCheckDate())) ? maxSChecklogs[0]: sChecklog;
			}else if (checkStatus == 4 || checkStatus ==5) {//主管部门审核
				maxSChecklogs[1] = (maxSChecklogs[1] != null && sChecklog.getCheckDate().before(maxSChecklogs[1].getCheckDate())) ? maxSChecklogs[1]: sChecklog;
			}				
		}																																		
		//将中间表中的校级审核记录写到中检表的对应字段
		if (maxSChecklogs[0] != null) {
			if(maxSChecklogs[0].getCheckStatus() == 2){				
				projectVariation.setUniversityAuditResult(2);			
			}else if(maxSChecklogs[0].getCheckStatus() == 3){
				projectVariation.setUniversityAuditResult(1);
			}
			projectVariation.setDeptInstAuditResult(2);
			projectVariation.setDeptInstAuditStatus(3);
			projectVariation.setUniversityAuditStatus(3);
			projectVariation.setUniversityAuditDate(maxSChecklogs[0].getCheckDate());
			projectVariation.setUniversityAuditOpinion(maxSChecklogs[0].getCheckInfo());
			Agency university = universityFinder.getUnivByName(maxSChecklogs[0].getChecker());
			projectVariation.setUniversityAuditorAgency(university);	
		}
		//将中间表中的主管部门审核记录写到中检表的对应字段
		if (maxSChecklogs[1] != null) {		
			if(maxSChecklogs[1].getCheckStatus() == 4){
				projectVariation.setProvinceAuditResult(2);
			}else if(maxSChecklogs[1].getCheckStatus() == 5){						
				projectVariation.setProvinceAuditResult(1);
			}
			projectVariation.setDeptInstAuditResult(2);
			projectVariation.setDeptInstAuditStatus(3);
			projectVariation.setUniversityAuditResult(2);
			projectVariation.setUniversityAuditStatus(3);
			projectVariation.setProvinceAuditStatus(3);
			projectVariation.setProvinceAuditDate(maxSChecklogs[1].getCheckDate());
			projectVariation.setProvinceAuditOpinion(maxSChecklogs[1].getCheckInfo());
			Agency province = universityFinder.getProByName(maxSChecklogs[1].getChecker());
			projectVariation.setProvinceAuditorAgency(province);
		}
	}

	/**
	* 得到指定月前的日期（格式为：2014-01）

	*/
	public String getBeforMonth(int month, Date date) {
	        Calendar c = Calendar.getInstance();//获得一个日历的实例  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
	  
	        c.setTime(date);//设置日历时间  
	        c.add(Calendar.MONTH,-month);//在日历的月份上增加6个月   
	        String strDate = sdf.format(c.getTime());//的到你想要得6个月前的日期   
	        return strDate;
	 }
	
	public Date getBeforMonthDate(int distinctMonth, Date date) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例   
        c.setTime(date);//设置日历时间  
        c.add(Calendar.MONTH,-distinctMonth);//在日历的月份上增加6个月    
        return c.getTime();
	}
	
	/*
	 * 判断changeBefore和changeCurrent相同的索引位上是否都是1
	 */
	public boolean isAddedBefore(StringBuffer changeBefore, StringBuffer changeCurrent){
		for(int i = 0; i < 20; i++){
			if(changeBefore.charAt(i) == changeCurrent.charAt(i) && changeBefore.charAt(i) == '1'){
				return true;
			}
		}
			
		return false;
	}
	
	public static void main(String[] args) {
		Date d1 = new Date();
		Date d2 = new Date();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(d1);
		sdf.format(d2);*/
		System.out.println(d1.compareTo(d2));
	}

	public ProjectVariationImporter(){
	}
	
	public ProjectVariationImporter(String file) {
		reader = new ExcelReader(file);
	}	
}
		
		
		
		
		
			
			
		
			
		
		
			
			
			
			
			
			
	
	

