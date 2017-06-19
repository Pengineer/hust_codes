package csdc.tool.execution.importer;

import java.util.Date;
import java.util.List;

import java_cup.internal_error;

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
import csdc.bean.ProjectMidinspection;
import csdc.bean.ProjectVariation;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectMidinspection;
import csdc.bean.SinossProjectVariation;
import csdc.bean.Teacher;
import csdc.dao.IHibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.importer.tool.Tool;

/**
 * 
 * 
 * @author maowh
 * 
 */
@Component
public class ProjectVariationImporter extends Execution {
	
	@Autowired
	private Tool tool;
	
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
	

	@Override
	public void work() throws Throwable {
		List<SinossProjectVariation> sinossProjectVariations =(List<SinossProjectVariation>)dao.query("select o from SinossProjectVariation o");
		
		for(int i = 0;i < sinossProjectVariations.size();i++){
			
			
			System.out.println("读到第" + (i+1) + "条数据");
			System.out.println(sinossProjectVariations.get(i).getName());
			String codeString = sinossProjectVariations.get(i).getCode();//项目编号
			String projectType = sinossProjectVariations.get(i).getTypeCode();
			Date applyDate = sinossProjectVariations.get(i).getApplyDate();
			Date checkDate = sinossProjectVariations.get(i).getCheckDate();
			String variatonReason = sinossProjectVariations.get(i).getModifyReason();
//    		String checker = sinossProjectVariation.get(i).getChecker();//审核人
			int checkStatus = sinossProjectVariations.get(i).getCheckStatus();
			
			
			if (projectType.contains("base")) {
				InstpGranted granted = instpProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(基地项目)
				InstpVariation instpVariation = new InstpVariation();
				granted.addVariation(instpVariation);//建立关联关系
				
//				String grantIdString = granted.getId();//得到项目的立项id
				sinossProjectVariations.get(i).setSmdbProjectId(granted.getId());//将立项id填入临时表
				instpVariation.setImportedDate(new Date());//入库时间
				instpVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				instpVariation.setApplicantSubmitStatus(3);
				instpVariation.setVariationReason(variatonReason);//变更原因
				instpVariation.setDeptInstAuditResult(2);//院系审核都通过
				instpVariation.setDeptInstAuditStatus(3);
				instpVariation.setStatus(5);
				StringBuffer deptAuditResultDetail = new StringBuffer("000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("000000000");
				
				String variationId = sinossProjectVariations.get(i).getId();//得到变更id
				
				List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);//一条变更id可能对应多条变更内容
				if (sinossModifyContents.size() == 0) {					
					System.out.println("变更项目成员而不是负责人");
					
				} else {
					for(int j = 0;j < sinossModifyContents.size();j++){
						SinossModifyContent sinossModifyContent = sinossModifyContents.get(j);
						String modifyMean = sinossModifyContent.getModifyFieldMean();
						
						if (modifyMean.contains("其他")) {
							
							instpVariation.setOther(1);
							instpVariation.setOtherInfo(sinossModifyContent.getBeforeValue() + "▶" + sinossModifyContent.getAfterValue());
							deptAuditResultDetail.setCharAt(8, '1');
							universityAuditResultDetail.setCharAt(8, '1');
							provinceAuditResultDetail.setCharAt(8, '1');
							
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
								
								List<InstpMember> instpMembers = (List<InstpMember>)dao.query("from InstpMember o where o.application.id = ?",applicationId);
								int flag = 1;
								for(int k = 0;k < instpMembers.size();k++){
									if (instpMembers.get(k).getGroupNumber()>flag) {
										flag = instpMembers.get(k).getGroupNumber();
									}
								}
								for(int k = 0;k < instpMembers.size();k++){
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
								InstpMember instpMember = new InstpMember();
								Person person = (Person) dao.queryUnique("from Person p where p.idcardNumber = ?", sinossModifyContent.getIdNumber());
								instpMember.setMember(person);
								if (sinossModifyContent.getAfterValue().contains("罗江华")) {
									instpMember.setAgencyName("西南大学");
									instpMember.setGender("男");
								} else if (sinossModifyContent.getAfterValue().contains("严兵")) {
									instpMember.setAgencyName("南开大学");
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
								System.out.println(variationId);
							}
					}
				
					
				}
				
				
				List<SinossMembers> sinossMembers = (List<SinossMembers>)dao.query("from SinossMembers o where o.projectVariation.id = ? ",variationId);//只变更项目成员，不变更项目负责人
				
				if (sinossMembers.size() != 0) {
					deptAuditResultDetail.setCharAt(0, '1');
					universityAuditResultDetail.setCharAt(0, '1');
					provinceAuditResultDetail.setCharAt(0, '1');
					instpVariation.setChangeMember(1);
					instpVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());
					
					String applicationId = granted.getApplicationId();
					InstpApplication application = granted.getApplication();
					try {
						dao.query("from InstpMember o where o.application.id = ?",applicationId);
					} catch (Exception e) {
						e.printStackTrace();
					}
					List<InstpMember> instpMembers = dao.query("from InstpMember o where o.application.id = ?",applicationId);
					int flag = 1;
					for(int k = 0;k < instpMembers.size();k++){
						if (instpMembers.get(k).getGroupNumber()>flag) {
							flag = instpMembers.get(k).getGroupNumber();
						}
					}
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
					for(int j = 0;j < sinossMembers.size();j++){
//						InstpMember instpMember = new InstpMember();
//						instpMember.setMemberName(sinossMembers.get(j).getName());
//						instpMember.setAgencyName(sinossMembers.get(j).getUnitName());
//						instpMember.setSpecialistTitle(sinossMembers.get(j).getTitle());
//						instpMember.setMajor(sinossMembers.get(j).getSpecialty());
//						instpMember.setWorkDivision(sinossMembers.get(j).getDivide());
//						if (sinossMembers.get(j).getOrders() != null) {
//							instpMember.setMemberSn(Integer.parseInt(sinossMembers.get(j).getOrders()) + 1);
//						}
//						instpMember.setGroupNumber(flag + 1);
//						instpMember.setApplication(application);
//						dao.add(instpMember);
						String memberName = sinossMembers.get(j).getName();
						String unitName = sinossMembers.get(j).getUnitName();
						if ((memberName != null) &&(unitName != null)) {
							String specialistTitle = sinossMembers.get(j).getTitle();
							String workDivision = sinossMembers.get(j).getDivide();
							int memberSn = -1;
							if (sinossMembers.get(j).getOrders() != null) {
								memberSn = Integer.parseInt(sinossMembers.get(j).getOrders()) + 1;
							}
							int groupNumber = flag + 1;
							Object instpMember = getMember(memberName, unitName);
							addInstpMember(application, instpMember, specialistTitle, workDivision, memberSn, groupNumber);
						}
					}
					instpVariation.setNewMemberGroupNumber(flag + 1);
				}
				
												
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
					System.out.println("该条数据有问题：" + i);
					}								
			}
			
			if (projectType.contains("gener")) {
				GeneralGranted granted = generalProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(一般项目)
				GeneralVariation generalVariation = new GeneralVariation();
				granted.addVariation(generalVariation);//建立关联关系
				
//				String grantIdString = granted.getId();//得到项目的立项id
				sinossProjectVariations.get(i).setSmdbProjectId(granted.getId());//将立项id填入临时表
				generalVariation.setImportedDate(new Date());//入库时间
				generalVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				generalVariation.setApplicantSubmitStatus(3);
				generalVariation.setVariationReason(variatonReason);//变更原因
				generalVariation.setDeptInstAuditResult(2);//院系审核都通过
				generalVariation.setDeptInstAuditStatus(3);
				generalVariation.setStatus(5);
				StringBuffer deptAuditResultDetail = new StringBuffer("000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("000000000");
				
				String variationId = sinossProjectVariations.get(i).getId();//得到变更id
				
				List<SinossModifyContent> sinossModifyContents = (List<SinossModifyContent>) dao.query("from SinossModifyContent o where o.projectVariation.id = ? ",variationId);//一条变更id可能对应多条变更内容
				
				if (sinossModifyContents.size() == 0) {					
					System.out.println("变更项目成员而不是负责人");
					
				} else {
					for(int j = 0;j < sinossModifyContents.size();j++){
						SinossModifyContent sinossModifyContent = sinossModifyContents.get(j);
						String modifyMean = sinossModifyContent.getModifyFieldMean();
						
						if (modifyMean.contains("其他")) {
							generalVariation.setOther(1);
							generalVariation.setOtherInfo(sinossModifyContent.getBeforeValue() + "▶" + sinossModifyContent.getAfterValue());
							deptAuditResultDetail.setCharAt(8, '1');
							universityAuditResultDetail.setCharAt(8, '1');
							provinceAuditResultDetail.setCharAt(8, '1');
							
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
								System.out.println(variationId);
							}
					}
				
					
				}
								 
				List<SinossMembers> sinossMembers = (List<SinossMembers>)dao.query("from SinossMembers o where o.projectVariation.id = ? ",variationId);//只变更项目成员，不变更项目负责人
																		
				if (sinossMembers.size() != 0) {
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
					for(int j = 0;j < sinossMembers.size();j++){
//						GeneralMember generalMember = new GeneralMember();
//						generalMember.setMemberName(sinossMembers.get(j).getName());
//						generalMember.setAgencyName(sinossMembers.get(j).getUnitName());
//						generalMember.setSpecialistTitle(sinossMembers.get(j).getTitle());
//						generalMember.setMajor(sinossMembers.get(j).getSpecialty());
//						generalMember.setWorkDivision(sinossMembers.get(j).getDivide());
//						if (sinossMembers.get(j).getOrders() != null) {
//							generalMember.setMemberSn(Integer.parseInt(sinossMembers.get(j).getOrders()) + 1);
//						}
//						generalMember.setGroupNumber(flag + 1);
//						generalMember.setApplication(application);
//						dao.add(generalMember);
						
						String memberName = sinossMembers.get(j).getName();
						String unitName = sinossMembers.get(j).getUnitName();
						if ((memberName != null) &&(unitName != null)) {
							String specialistTitle = sinossMembers.get(j).getTitle();
							String workDivision = sinossMembers.get(j).getDivide();
							int memberSn = -1;
							if (sinossMembers.get(j).getOrders() != null) {
								memberSn = Integer.parseInt(sinossMembers.get(j).getOrders()) + 1;
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
						System.out.println("该条数据有问题：" + i);
						}								
				}
				
				
			}		
		
		//从中间表中录入审核信息
		String importedDate = "2013-10-20"; //录在变更中的中检记录
		int j = 0;
		List<ProjectVariation> projectVariations = dao.query("from ProjectVariation so where to_char(so.importedDate, 'yyyy-MM-dd') = ?", importedDate);
		String [] 问题数据 = new String[20];
		
		//在中间表中根据中检表中的立项id搜索对应数据
		for (ProjectVariation projectVariation: projectVariations) {		
			List<SinossProjectMidinspection> sProjectMidinspections = dao.query("from SinossProjectMidinspection o where o.projectGranted.id = ?", projectVariation.getGrantedId());
			if (sProjectMidinspections.size() != 1) {								
				问题数据[j] = projectVariation.getGrantedId();
				j++;									
				//System.out.print("问题数据：" + projectVariation.getGrantedId());					
			} else {				
				//在审核记录表中根据中间表中的立项id搜索对审核记录数据
				List<SinossChecklogs> sChecklogs = dao.query("from SinossChecklogs o where o.projectMidinspection.id = ?", sProjectMidinspections.get(0).getId());
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
					projectVariation.setUniversityAuditDate(maxSChecklogs[0].getCheckDate());
					projectVariation.setUniversityAuditOpinion(maxSChecklogs[0].getCheckInfo());
					Agency university = universityFinder.getUnivByName(maxSChecklogs[0].getChecker());
					projectVariation.setUniversityAuditorAgency(university);	
				}
				//将中间表中的主管部门审核记录写到中检表的对应字段
				if (maxSChecklogs[1] != null) {										
					projectVariation.setProvinceAuditDate(maxSChecklogs[1].getCheckDate());
					projectVariation.setProvinceAuditOpinion(maxSChecklogs[1].getCheckInfo());
					Agency province = universityFinder.getProByName(maxSChecklogs[1].getChecker());
					projectVariation.setProvinceAuditorAgency(province);
				}
			}																	
		}
		for (int i = 0; i < 问题数据.length; i++) {
			System.out.println(问题数据[i]);
			
		}
	}
	
	/*private Object getMember(String memberName, String unitName) throws Exception {
		Object member = new Object();
		memberName = memberName.replaceAll(" ", "");
//		unitName = unitName.replaceAll(" ", "");

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
				System.out.println(application.getName());
				member.setAgencyName(teacher.getUniversity().getName());
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
				System.out.println(application.getName());
				member.setAgencyName(expert.getAgencyName());
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
				member.setAgencyName(teacher.getUniversity().getName());
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
				member.setAgencyName(expert.getAgencyName());
			}

			member.setMemberType(2);
		}
		
		member.setMember(person);
		member.setMemberName(person.getName());
		
		member.setSpecialistTitle(specialistTitle);
		member.setWorkDivision(workDivision);
		member.setMemberSn(memberSn);
		member.setGroupNumber(groupNumber);
	}*/
	
	public static void main(String[] args) {
		String testString = "变更项目成员：曹迪。李仁伟";
		String[] testStrings = testString.replaceAll("变更项目成员：", "").split("[;//s]+");
		System.out.println("ok!");
	}
}
		
		
		
		
		
			
			
		
			
		
		
			
			
			
			
			
			
	
	

