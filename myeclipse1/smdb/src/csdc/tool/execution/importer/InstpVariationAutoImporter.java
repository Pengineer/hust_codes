package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import csdc.bean.Agency;
import csdc.bean.InstpApplication;
import csdc.bean.InstpGranted;
import csdc.bean.InstpMember;
import csdc.bean.InstpVariation;
import csdc.bean.Person;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;

public class InstpVariationAutoImporter extends ProjectVariationAutoImporter {
	
	public void work() throws Throwable{
		if (illegalException != null) {
			illegalException = null;
		}
		isFinished = 0;
		currentNum = 0;
		totalImportNum = 0;
		freeMemory();
		initSinossMembers();
		initSinossModifyContents();
		initSinossCheckData();
		importData();
		freeMemory();
		isFinished = 1;
	}
	@SuppressWarnings("unchecked")	
	public void importData() throws Throwable {
		List<SinossProjectVariation> sinossProjectVariations =(List<SinossProjectVariation>)dao.query("select o from SinossProjectVariation o where o.isAdded = 1 and o.note is null and o.typeCode = 'base'");
		totalNum = sinossProjectVariations == null ? 0 : sinossProjectVariations.size();
		List<SinossMembers> sinossInstpMembers = new ArrayList<SinossMembers>();
		List<SinossModifyContent> sinossModifyContents = new ArrayList<SinossModifyContent>();
		List<String[]> notFoundGrantedProjectName = null;//该项目不存在立项（导入中检、变更）
		List<String[]> otherExceptionProjectName = null;//其他（修改内容/审核状态码）
		for (SinossProjectVariation spv : sinossProjectVariations) {
			//取消导入
			if (status != 0) {
				freeMemory();
				throw new RuntimeException();
			} 
			System.out.println("读到第" + (++currentNum) + "条数据" + "项目名称为：" + spv.getName());	
			String codeString = spv.getCode();//项目编号
			InstpGranted granted = instpProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(基地项目)
			if (granted == null) {
				if (notFoundGrantedProjectName == null) {
					notFoundGrantedProjectName = new ArrayList<String[]>();
				}
				String[] tempNotFoundGrantedProjectName = new String[2];
				tempNotFoundGrantedProjectName[0] = spv.getName();
				tempNotFoundGrantedProjectName[1] = codeString;
				notFoundGrantedProjectName.add(tempNotFoundGrantedProjectName);
				spv.setNote(exchangeNoteInfo(spv.getNote(), "该项目不存在立项（导入中检、变更）", spv.getName(), codeString));
			}else {
				String variatonReason = spv.getModifyReason();//变更原因
				Date applyDate = spv.getApplyDate();//提交时间
				Date checkDate = spv.getCheckDate();//最后审核日期
				int checkStatus = spv.getCheckStatus();//审核状态
				importNumber++;
				InstpVariation instpVariation = new InstpVariation();
				granted.addVariation(instpVariation);//建立关联关系
				spv.setProjectVariation(instpVariation); //中间表中C_VARIATION_ID是我们入库的时候要加进去的，表示这条数据和我们库里面的数据时对应的
				spv.setProjectGranted(granted);//将立项实体填入临时表
				spv.setImportedDate(new Date());
				spv.setProjectApplication(granted.getApplication());
				instpVariation.setCreateDate(new Date());
				instpVariation.setCreateMode(0);
				instpVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				instpVariation.setApplicantSubmitStatus(3);
				instpVariation.setVariationReason(variatonReason);//变更原因
//				instpVariation.setDeptInstAuditResult(2);//院系审核都通过
//				instpVariation.setDeptInstAuditStatus(3);
//				instpVariation.setStatus(5);
				//记录变更类型
				StringBuffer deptAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("00000000000000000000");
				String variationId = spv.getId();
				
				//变更项目负责人或则项目相关内容
				sinossModifyContents = sinossModifyContentMap.get(variationId);
				boolean changedirector = false;
				List<InstpMember> currentMembers = null;
				if (sinossModifyContents == null || sinossModifyContents.size() == 0) {					
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
							String[] oldProductType = productTypeNormalizer.getNormalizedProductType(sinossModifyContent.getBeforeValue());
							String[] newProductType = productTypeNormalizer.getNormalizedProductType(sinossModifyContent.getAfterValue());
							instpVariation.setOldProductType(oldProductType[0]);
							instpVariation.setOldProductTypeOther(oldProductType[1]);
							instpVariation.setNewProductType(newProductType[0]);
							instpVariation.setNewProductTypeOther(newProductType[1]);
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
							changedirector = true;
							currentMembers = new ArrayList<InstpMember>();
							deptAuditResultDetail.setCharAt(0, '1');
							universityAuditResultDetail.setCharAt(0, '1');
							provinceAuditResultDetail.setCharAt(0, '1');
							instpVariation.setChangeMember(1);
							instpVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());
							String applicationId = granted.getApplicationId();
							InstpApplication application = granted.getApplication();
							
							int flag = 1;
							List<InstpMember> instpMembers = (List<InstpMember>)dao.query("select o from InstpMember o left join o.application app where app.id = ?",applicationId);
							//GroupNumber组号：表示每一段时期的项目组成员，如果有成员变更，GroupNumber就会+1（第一次申请项目时GroupNumber=1）
							for(int k = 0; k < instpMembers.size(); k++){  
								if (instpMembers.get(k).getGroupNumber()>flag) { 
									flag = instpMembers.get(k).getGroupNumber();
								}
							}
							
							InstpMember instpApplicant = new InstpMember();
							String applicantName = sinossModifyContent.getAfterValue();
							String appplicantAgency = "";
							if (sinossModifyContent.getAfterValue().contains("颜洽茂")) {
								appplicantAgency = "浙江大学";
							} else if (sinossModifyContent.getAfterValue().contains("许建平")) {
								appplicantAgency = "浙江大学";
							} else if (sinossModifyContent.getAfterValue().contains("陈文忠")) {
								appplicantAgency = "安徽师范大学";
							} else 
								throw new RuntimeException();
							Person person = (Person) dao.queryUnique("from Person p where p.idcardNumber = ?", sinossModifyContent.getIdNumber());
							if(person == null){
								person = new Person();
								person.setIdcardNumber(sinossModifyContent.getIdNumber());
								person.setName(tool.getName(sinossModifyContent.getAfterValue()));
								dao.add(person);
							}
							instpApplicant.setMember(person);	
							person.setName(tool.getName(sinossModifyContent.getAfterValue()));
							instpApplicant.setAgencyName(appplicantAgency);
							instpApplicant.setIdcardNumber(sinossModifyContent.getIdNumber());
							instpApplicant.setApplication(application);
							instpApplicant.setGroupNumber(flag + 1);
							instpApplicant.setIdcardType("身份证");
							instpApplicant.setIsDirector(1);
							instpApplicant.setMemberType(1);
							instpApplicant.setMemberSn(1);
							instpApplicant.setMemberName(applicantName);
							instpVariation.setNewMemberGroupNumber(flag + 1);	
							currentMembers.add(instpApplicant);
							dao.add(instpApplicant);
							
							//重新设置变更项目负责人后下一阶段所有普通成员信息（和上一次相同），组号+1
							for(int k = 0; k < instpMembers.size(); k++){
								if ((instpMembers.get(k).getIsDirector() == 0) && (instpMembers.get(k).getGroupNumber() == flag)) {
									String memberName = instpMembers.get(j).getMemberName();
									String unitName = instpMembers.get(j).getAgencyName();
									if ((memberName + unitName).equals(applicantName + appplicantAgency)) {
										continue;
									}
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
									currentMembers.add(instpMember);
									dao.add(instpMember);
								} 
							}
						} else {
							System.out.println("该条数据修改内容有问题（variationId）：" + variationId);
							error.add("Instp-变更类型不存在：" + variationId + "--" + spv.getName() + "--" + modifyMean);
							if (otherExceptionProjectName == null) {
								otherExceptionProjectName = new ArrayList<String[]>();
							}
							String[] tempOtherExceptionProjectName = new String[2];
							tempOtherExceptionProjectName[0] = spv.getName();
							tempOtherExceptionProjectName[1] = new String("Instp-变更类型不存在：" + variationId + "--" + modifyMean);
							otherExceptionProjectName.add(tempOtherExceptionProjectName);
							sinossModifyContent.setNote("Instp-变更类型不存在：" + variationId + "--" + spv.getName() + "--" + modifyMean);
							spv.setNote(exchangeNoteInfo(spv.getNote(), "其他（变更类型不存在）", spv.getName(), modifyMean));
							continue;
						}
						sinossModifyContent.setIsAdded(0);
					}											
				}
				
				//变更项目一般成员（非负责人）
				sinossInstpMembers = sinossMembersMap.get(variationId);
				if (sinossInstpMembers != null && sinossInstpMembers.size() != 0) { //变更普通成员begin
					deptAuditResultDetail.setCharAt(0, '1');
					universityAuditResultDetail.setCharAt(0, '1');
					provinceAuditResultDetail.setCharAt(0, '1');
					instpVariation.setChangeMember(1);
					instpVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());						
					String applicationId = granted.getApplicationId();
					InstpApplication application = granted.getApplication();
					List<InstpMember> instpMembers = null;
					if (changedirector) {
						instpMembers = currentMembers;
					} else {						
						instpMembers = dao.query("select o from InstpMember o left join o.application app where app.id = ?",applicationId);
					}
					int flag = 1;
					for(int k = 0;k < instpMembers.size();k++){
						if (instpMembers.get(k).getGroupNumber()>flag) {
							flag = instpMembers.get(k).getGroupNumber();
						}
					}
					//为新的组添加项目负责人
					String directorName = null;
					String directorAgency = null;
					for(int k = 0;k < instpMembers.size();k++){ 
						if (instpMembers.get(k).getIsDirector() == 1) {
							InstpMember instpMember = new InstpMember();
							directorName = instpMembers.get(k).getMemberName();
							directorAgency = instpMembers.get(k).getAgencyName();
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
						if(!((memberName + unitName).equals(directorName + directorAgency))) {
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
					}
					instpVariation.setNewMemberGroupNumber(flag + 1);
				}
				
				//变更项目审核信息								
				if(checkStatus == 0) {
					instpVariation.setApplicantSubmitStatus(0);
				}else if(checkStatus == 1) {
					instpVariation.setApplicantSubmitStatus(1);
				}else if (checkStatus == 2) {
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
					instpVariation.setUniversityAuditResultDetail("00000000000000000000");
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
					instpVariation.setProvinceAuditResultDetail("00000000000000000000");
					instpVariation.setProvinceAuditStatus(3);
				}else if(checkStatus == 8) {
					instpVariation.setStatus(3);
					instpVariation.setApplicantSubmitStatus(2);
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
				}else if(checkStatus == 9) {
					instpVariation.setStatus(3);
					instpVariation.setApplicantSubmitStatus(3);
					instpVariation.setDeptInstAuditResult(2);
					instpVariation.setDeptInstAuditStatus(3);
				}else {
					//如果不是上面几个审核状态，就表示这些数据未提交审核，就不用处理审核信息，这些审核状态对应的是社科网的数据字典，和我们这边不一样
					error.add("Instp-审核状态码不存在：：" + variationId + "--" + spv.getName() + "--" + checkStatus);
					if (otherExceptionProjectName == null) {
						otherExceptionProjectName = new ArrayList<String[]>();
					}
					String[] tempOtherExceptionProjectName = new String[2];
					tempOtherExceptionProjectName[0] = spv.getName();
					tempOtherExceptionProjectName[1] = new String("Instp-审核状态码不存在：：" + variationId + "--" + checkStatus);
					otherExceptionProjectName.add(tempOtherExceptionProjectName);
					spv.setNote(exchangeNoteInfo(spv.getNote(), "其他（审核状态码不存在）", spv.getName(), String.valueOf(checkStatus)));
				}
				//导入审核详情
				if(checkStatus > 1 && checkStatus < 6) {
					AddProjectVaritionCheckLogs(spv, instpVariation);
				}
				dao.addOrModify(instpVariation);
				spv.setIsAdded(0);
				totalImportNum++;
			}
		}
		if (notFoundGrantedProjectName != null && notFoundGrantedProjectName.size() > 0) {
			if (illegalException == null) {
				illegalException = new HashMap<String, List<String[]>>();
			}
			List<String[]> tempException = new ArrayList<String[]>(notFoundGrantedProjectName);
			illegalException.put("该项目不存在立项（导入中检、变更）", tempException);
		}
		if (otherExceptionProjectName != null && otherExceptionProjectName.size() > 0) {
			if (illegalException == null) {
				illegalException = new HashMap<String, List<String[]>>();
			}
			List<String[]> tempException = new ArrayList<String[]>(otherExceptionProjectName);
			illegalException.put("其他", tempException);
		}
		System.out.println("共有" + sinossProjectVariations.size() + "条数据");
		System.out.println("共入库" + importNumber + "条数据");		
		System.out.println(agencyError);
	}
}
