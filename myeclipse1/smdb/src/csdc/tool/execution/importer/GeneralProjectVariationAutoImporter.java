package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import csdc.bean.Agency;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralGranted;
import csdc.bean.GeneralMember;
import csdc.bean.GeneralVariation;
import csdc.bean.InstpMember;
import csdc.bean.Person;
import csdc.bean.SinossMembers;
import csdc.bean.SinossModifyContent;
import csdc.bean.SinossProjectVariation;
import csdc.service.IGeneralService;


public class GeneralProjectVariationAutoImporter extends ProjectVariationAutoImporter {
	
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
		List<SinossProjectVariation> sinossProjectVariations =(List<SinossProjectVariation>)dao.query("select o from SinossProjectVariation o where o.isAdded = 1 and o.note is null and o.typeCode = 'gener'");
		totalNum = sinossProjectVariations == null ? 0 : sinossProjectVariations.size();
		List<SinossMembers> sinossGeneralMembers = new ArrayList<SinossMembers>();
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
			GeneralGranted granted = generalProjectFinder.findGranted(codeString);//根据项目批准号找到立项实体(一般项目)
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
				GeneralVariation generalVariation = new GeneralVariation();
				granted.addVariation(generalVariation);//建立关联关系
				spv.setImportedDate(new Date());
				spv.setProjectGranted(granted);//将立项id填入临时表
				spv.setProjectVariation(generalVariation);
				spv.setProjectApplication(granted.getApplication());
				generalVariation.setCreateDate(new Date());
				generalVariation.setCreateMode(0);
				generalVariation.setApplicantSubmitDate(applyDate);//变更申请日期
				generalVariation.setApplicantSubmitStatus(3);
				generalVariation.setVariationReason(variatonReason);//变更原因
//				generalVariation.setDeptInstAuditResult(2);//院系审核都通过
//				generalVariation.setDeptInstAuditStatus(3);
//				generalVariation.setStatus(5);
				StringBuffer deptAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer universityAuditResultDetail = new StringBuffer("00000000000000000000");
				StringBuffer provinceAuditResultDetail = new StringBuffer("00000000000000000000");
				
				String variationId = spv.getId();//得到变更id				
				sinossModifyContents = sinossModifyContentMap.get(variationId);
				boolean changedirector = false;
				List<GeneralMember> currentMembers = null;
				if (sinossModifyContents == null || sinossModifyContents.size() == 0) {					
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
							generalVariation.setOldOnceDate(tool.getDate(sinossModifyContent.getBeforeValue()));
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
							String[] oldProductType = productTypeNormalizer.getNormalizedProductType(sinossModifyContent.getBeforeValue());
							String[] newProductType = productTypeNormalizer.getNormalizedProductType(sinossModifyContent.getAfterValue());
							generalVariation.setOldProductType(oldProductType[0]);
							generalVariation.setOldProductTypeOther(oldProductType[1]);
							generalVariation.setNewProductType(newProductType[0]);
							generalVariation.setNewProductTypeOther(newProductType[1]);
							deptAuditResultDetail.setCharAt(2, '1');
							universityAuditResultDetail.setCharAt(2, '1');
							provinceAuditResultDetail.setCharAt(2, '1');								
						} else if (modifyMean.contains("变更项目管理单位")) {	
							deptAuditResultDetail.setCharAt(1, '1');
							universityAuditResultDetail.setCharAt(1, '1');
							provinceAuditResultDetail.setCharAt(1, '1');							
							generalVariation.setChangeAgency(1);
							Agency oldAgency = universityFinder.getAgencyByName(sinossModifyContent.getBeforeValue());
							Agency newAgency = universityFinder.getAgencyByName(sinossModifyContent.getAfterValue());
							
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
							changedirector = true;
							currentMembers = new ArrayList<GeneralMember>();
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
							GeneralMember generalApplicant = new GeneralMember();
							String applicantName = sinossModifyContent.getAfterValue();
							String appplicantAgency = "";//等待社科网的数据
							Person person = (Person) dao.queryUnique("from Person p where p.idcardNumber = ?", sinossModifyContent.getIdNumber());
							if(person == null){
								person = new Person();
								person.setIdcardNumber(sinossModifyContent.getIdNumber());
								dao.add(person);
							}
							generalApplicant.setMember(person);
							person.setName(tool.getName(sinossModifyContent.getAfterValue()));
//							Agency university = universityFinder.getUnivByName(appplicantAgency);
//							if (university == null) {
//								if (notFoundGrantedProjectName == null) {
//									notFoundGrantedProjectName = new ArrayList<String[]>();
//								}
//								String[] tempNotFoundGrantedProjectName = new String[2];
//								tempNotFoundGrantedProjectName[0] = spv.getName();
//								tempNotFoundGrantedProjectName[1] = appplicantAgency;
//								notFoundGrantedProjectName.add(tempNotFoundGrantedProjectName);
//								spv.setNote(exchangeNoteInfo(spv.getNote(), "变更负责人高校不存在", spv.getName(), appplicantAgency));
//							}
//							generalApplicant.setUniversity(university);
//							generalApplicant.setAgencyName(appplicantAgency);
							generalApplicant.setIdcardNumber(sinossModifyContent.getIdNumber());
							generalApplicant.setApplication(application);
							generalApplicant.setGroupNumber(flag + 1);
							generalApplicant.setIdcardType("身份证");
							generalApplicant.setIsDirector(1);
							generalApplicant.setMemberName(applicantName);
							generalApplicant.setMemberSn(1);
							generalApplicant.setMemberType(1);
							generalVariation.setNewMemberGroupNumber(flag + 1);
							currentMembers.add(generalApplicant);
							dao.add(generalApplicant);
							//添加一般成员
							for(int k = 0;k < generalMembers.size();k++) {
								if ((generalMembers.get(k).getIsDirector() == 0) && (generalMembers.get(k).getGroupNumber() == flag)) {
									String memberName = generalMembers.get(j).getMemberName();
									String unitName = generalMembers.get(j).getAgencyName();
									if ((memberName + unitName).equals(applicantName + appplicantAgency)) {
										continue;
									}
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
									currentMembers.add(generalMember);
									dao.add(generalMember);
								}
							}
						} else {
							System.out.println("该条数据修改内容有问题（variationId）：" + variationId);
							error.add("gener-变更类型不存在：" + variationId + "--" + spv.getName() + "--" + modifyMean);
							if (otherExceptionProjectName == null) {
								otherExceptionProjectName = new ArrayList<String[]>();
							}
							String[] tempOtherExceptionProjectName = new String[2];
							tempOtherExceptionProjectName[0] = spv.getName();
							tempOtherExceptionProjectName[1] = new String("gener-变更类型不存在：" + variationId + "--" + modifyMean);
							otherExceptionProjectName.add(tempOtherExceptionProjectName);
							sinossModifyContent.setNote("gener-变更类型不存在：" + variationId + "--" + spv.getName() + "--" + modifyMean);
							spv.setNote(exchangeNoteInfo(spv.getNote(), "其他（变更类型不存在）", spv.getName(), modifyMean));
							continue;
						}
						sinossModifyContent.setIsAdded(0);
					}						
				}
								 
				sinossGeneralMembers = sinossMembersMap.get(variationId);
				if (sinossGeneralMembers != null && sinossGeneralMembers.size() != 0) {  //只变更项目成员，不变更项目负责人
					deptAuditResultDetail.setCharAt(0, '1');
					universityAuditResultDetail.setCharAt(0, '1');
					provinceAuditResultDetail.setCharAt(0, '1');
					generalVariation.setChangeMember(1);
					generalVariation.setOldMemberGroupNumber(granted.getMemberGroupNumber());
					
					String applicationId = granted.getApplicationId();
					GeneralApplication application = granted.getApplication();
					List<GeneralMember> generalMembers = null;
					if (changedirector) {
						generalMembers = currentMembers;
					} else {
						generalMembers = dao.query("from GeneralMember o where o.application.id = ?",applicationId);
					}
					int flag = 1;
					for(int k = 0;k < generalMembers.size();k++){
						if (generalMembers.get(k).getGroupNumber()>flag) {
							flag = generalMembers.get(k).getGroupNumber();
						}
					}
					String directorName = null;
					String directorAgency = null;
					for(int k = 0;k < generalMembers.size();k++){
						if (generalMembers.get(k).getIsDirector() == 1) {
							GeneralMember generalMember = new GeneralMember();
							directorName = generalMembers.get(k).getMemberName();
							directorAgency = generalMembers.get(k).getAgencyName();
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
						if(!((memberName + unitName).equals(directorName + directorAgency))) {//确定不是负责人才添加
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
					}
					generalVariation.setNewMemberGroupNumber(flag + 1);
				}
				//最终审核信息
				if(checkStatus == 0) {
					generalVariation.setApplicantSubmitStatus(0);
				}else if(checkStatus == 1) {
					generalVariation.setApplicantSubmitStatus(1);
				}else if (checkStatus == 2) {
					generalVariation.setApplicantSubmitStatus(3);
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 3) {
					generalVariation.setApplicantSubmitStatus(3);
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(1);
					generalVariation.setUniversityAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail("00000000000000000000");
					generalVariation.setUniversityAuditStatus(3);
				}else if (checkStatus == 4) {
					generalVariation.setApplicantSubmitStatus(3);
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
					generalVariation.setApplicantSubmitStatus(3);
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
					generalVariation.setUniversityAuditResult(2);
					generalVariation.setUniversityAuditStatus(3);
					generalVariation.setProvinceAuditResult(1);
					generalVariation.setProvinceAuditDate(checkDate);
					generalVariation.setDeptInstAuditResultDetail(deptAuditResultDetail.toString());
					generalVariation.setUniversityAuditResultDetail(universityAuditResultDetail.toString());
					generalVariation.setProvinceAuditResultDetail("00000000000000000000");
					generalVariation.setProvinceAuditStatus(3);
				}else if(checkStatus == 8) {
					generalVariation.setStatus(3);
					generalVariation.setApplicantSubmitStatus(2);
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
				}else if(checkStatus == 9) {
					generalVariation.setStatus(3);
					generalVariation.setApplicantSubmitStatus(3);
					generalVariation.setDeptInstAuditResult(2);
					generalVariation.setDeptInstAuditStatus(3);
				}else{
					error.add("gener-审核状态码不存在：：" + variationId + "--" + spv.getName() + "--" + checkStatus);
					if (otherExceptionProjectName == null) {
						otherExceptionProjectName = new ArrayList<String[]>();
					}
					String[] tempOtherExceptionProjectName = new String[2];
					tempOtherExceptionProjectName[0] = spv.getName();
					tempOtherExceptionProjectName[1] = new String("gener-审核状态码不存在：：" + variationId + "--" + checkStatus);
					otherExceptionProjectName.add(tempOtherExceptionProjectName);
					spv.setNote(exchangeNoteInfo(spv.getNote(), "其他（审核状态码不存在）", spv.getName(), String.valueOf(checkStatus)));
				}
				//导入审核详情
				if(checkStatus > 1 && checkStatus < 6) {
					AddProjectVaritionCheckLogs(spv, generalVariation);
				}
				dao.addOrModify(generalVariation);
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
