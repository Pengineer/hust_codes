package csdc.tool.execution.importer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import csdc.bean.Academic;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.Expert;
import csdc.bean.Institute;
import csdc.bean.InstpApplication;
import csdc.bean.InstpMember;
import csdc.bean.Person;
import csdc.bean.SinossChecklogs;
import csdc.bean.SinossMembers;
import csdc.bean.SinossProjectApplication;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.ApplicationContainer;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;

/**
 * 基地项目申请数据入库   (from sinoss)
 * @author pengliang
 *
 */
public class InstpApplicationAutoImporter extends ProjectApplicationAutoImporter{
	@SuppressWarnings("unchecked")
	public void initSinossData(){
		templist = dao.query("select spa from SinossProjectApplication spa where spa.typeCode = 'base' and spa.isAdded = 1 order by spa.id");
	}
	
	@SuppressWarnings("unchecked")
	public void work() throws Exception {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		isFinished = 0;
		currentNum = 0;
		totalImportNum = 0;
		//List<Object> tempNumList = dao.query("select count(spa.id) from SinossProjectApplication spa where spa.typeCode = 'base' and spa.isAdded = 1");
		//totalNum = Integer.parseInt(tempNumList.get(0).toString());
		System.out.println("**************************************************************************");
		System.out.println("开始导入...");
		if (illegalException != null) {
			illegalException = null;
		}
		//while (currentNum < totalNum) {
			initTitle();
			tool.initDiscNameCodeMap();
			initSinossData();
			totalNum = templist.size();
			if (templist != null && templist.size() > 0) {
				dumpDate = sdf.format(templist.get(0).getDumpDate());
			}
			initSinossCheckData();
			initSinossMembers(dumpDate);
			importData();
			dao.flush();
			dao.clear();
		//}
		freeMemory();
		isFinished = 1;
		System.out.println("over");
	}
	
	public void importData() throws Exception { 		
		//基地项目
//		Officer 白晓 = (Officer) dao.queryUnique("from Officer o where o.agency.type = 1 and o.person.name = '白晓'");
		
		Iterator<SinossProjectApplication> appIterator = templist.iterator();
		List<String[]> notFoundUnivProjectName = null;	
		List<String[]> notFoundMemberProjectName = null;
		while(appIterator.hasNext()){				
			//取消导入
			if (status != 0) {
				freeMemory();
				isFinished = 1;
				throw new RuntimeException();
			}
			spa = appIterator.next();
			/*if(spa.getProjectName().equals("俄罗斯母语（俄语）教育调查报告")){
				continue;
			}*/
			System.out.println("当前导入项目名：" + spa.getProjectName() + "  (学校代码：" + spa.getUnitCode() + ")————导入第" + (++currentNum) + "/" + totalNum+ "条数据");//用户查找出错项
			Agency university = universityFinder.getUnivByName(spa.getUnitName().trim());
			if (university == null) {
				if (notFoundUnivProjectName == null) {
					notFoundUnivProjectName = new ArrayList<String[]>();
				}
				String[] tempNotFoundUnivProjectName = new String[2];
				tempNotFoundUnivProjectName[0] = spa.getProjectName();
				tempNotFoundUnivProjectName[1] = spa.getUnitName();
				notFoundUnivProjectName.add(tempNotFoundUnivProjectName);
				spa.setNote(exchangeNoteInfo(spa.getNote(), "找不到该项目对应的高校", spa.getProjectName(), spa.getUnitName()));
			}else {
				if (projectAppParaMap == null) {
					projectAppParaMap = new HashMap<String, String>();
				}
				projectAppParaMap.put("projectApplicationId", spa.getId());
				//chechStatus用于区分同一项目多次申请的情况
				InstpApplication application = instpProjectFinder.findApplication(spa.getProjectName().trim(), spa.getApplyer(), Integer.parseInt(spa.getCheckStatus()));
				Institute institute = instituteFinder.getInstitute(university, spa.getProjectJDName().trim(), false);
				/*if (institute.getType().getCode().equals("06")) {
					institute.setType(校级基地);
				}*/
				if(spa.getProjectJDName().equals("毛泽东思想研究中心（湘潭大学中国共产党革命精神与文化资源研究中心）")){
					institute = (Institute) dao.queryUnique("select i from Institute i where i.id = '4028d89231523c110131523d9da670ee'");
				}
			
				if (application == null) {
					application = new InstpApplication();   
				}
				beanFieldUtils.setField(application, "name", spa.getProjectName().trim(), BuiltinMergeStrategies.REPLACE);
				
				//项目机构部门单位
				application.setYear(Integer.parseInt(spa.getYear()));
				application.setApplicantSubmitDate(spa.getApplyDate());  
				application.setUniversity(university);
				application.setAgencyName(spa.getUnitName());
				application.setInstitute(institute);
				application.setDivisionName(institute.getName());	
				application.setFile(spa.getApplyDocName());
				
		        //学科门类+学科代码
				String subject = spa.getSubject().replaceAll("[^0-9]", "0");
				String subject1_1 = spa.getSubject1_1().replaceAll("[^0-9]", "0");
				String subject1_2 = spa.getSubject1_2().replaceAll("[^0-9]", "0");
				String researchDir = spa.getResearchDirection();
				String subjectCode = getSubjectCode(subject, subject1_1, subject1_2, researchDir);
				beanFieldUtils.setField(application, "disciplineType", getSubjectType(subject), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(application, "discipline", subjectCode, BuiltinMergeStrategies.REPLACE);
				
				//项目计划结束时间+经费
				beanFieldUtils.setField(application, "planEndDate", spa.getPlanFinishDate(), BuiltinMergeStrategies.PRECISE_DATE, true);
				beanFieldUtils.setField(application, "applyFee", tool.getFee(spa.getApplyTotleFee()), BuiltinMergeStrategies.REPLACE);
				if(spa.getOtherFeeSource() != null){
					beanFieldUtils.setField(application, "otherFee", tool.getFee(spa.getOtherFeeSource()), BuiltinMergeStrategies.REPLACE);
				}
				
				//项目最终成果形式
				String[] productType = productTypeNormalizer.getNormalizedProductType(spa.getLastProductMode());
				application.setProductType(productType[0]);
				application.setProductTypeOther(productType[1]);
				
				//添加成员信息	
				if(instpMemberOrder != null){
					instpMemberOrder.clear();
				}
				System.out.println("导入项目成员.........");//用于定位错误
	            addApplicant(application,tool.getName(spa.getApplyer()), spa.getUnitName(), this.regulateTitle(spa.getTitle()), spa.getDuty());//添加项目申请人    			
//	            sinossMembers = dao.query("select sm from SinossMembers sm where sm.projectApplication.id = :projectApplicationId", projectAppParaMap);
//	            if (sinossMembers == null) {
//					sinossMembers = new ArrayList<SinossMembers>();
//				}
	            currentSinossMembers = projectIdToSMembersMap.get(spa.getId());
	            if(null != currentSinossMembers){
	            	for (SinossMembers sinossMember : currentSinossMembers) {				
						try{
							if(null != sinossMember.getName()){
								addMember(application, sinossMember);
							}
							
						}catch(Exception e){
							if (notFoundMemberProjectName == null) {
								notFoundMemberProjectName = new ArrayList<String[]>();
							}
							String[] tempNotFoundMemberProjectName = new String[2];
							tempNotFoundMemberProjectName[0] = spa.getProjectName();
							tempNotFoundMemberProjectName[1] = sinossMember.getName();
							notFoundMemberProjectName.add(tempNotFoundMemberProjectName);
							spa.setNote(exchangeNoteInfo(spa.getNote(), "未成功添加该项目的成员", spa.getProjectName(), sinossMember.getName()));
						}
					}
				}
				
				SinossChecklogs[] lastSChecklogs = {null, null};//[0]：校级审核记录；[1]:主管部门(省级)审核记录
				
				//部级以下审核不通过(1,3,5)
				int statusNumber = 0;
				List<SinossChecklogs> spmChecklogs = projectIdToSChecklogMap.get(spa.getId());
				if(null != spmChecklogs){
					for(SinossChecklogs sicheck : spmChecklogs){
						statusNumber = sicheck.getCheckStatus();
						if (statusNumber == 2 || statusNumber ==3 ){ //校级审核
							lastSChecklogs[0] = (lastSChecklogs[0] != null && sicheck.getCheckDate().before(lastSChecklogs[0].getCheckDate())) ? lastSChecklogs[0]: sicheck;
						}else if (statusNumber == 4 || statusNumber ==5) { //省级审核
							lastSChecklogs[1] = (lastSChecklogs[1] != null && sicheck.getCheckDate().before(lastSChecklogs[1].getCheckDate())) ? lastSChecklogs[1]: sicheck;
						}
						sicheck.setIsAdded(0);
					}
				}
				/*if(!flag){
					return "";
				}*/
				//
				/*if(null != lastSChecklogs[0] && null == getAgencyByName(lastSChecklogs[0].getChecker().trim())){
					lastSChecklogs[0].setChecker(pg.getAgencyName());
				}
				if(null != lastSChecklogs[1] && null == getAgencyByName(lastSChecklogs[1].getChecker().trim())){
					lastSChecklogs[1].setChecker(pg.getAgencyName());
				}*/
				
				//导入审核信息
				int finalStatus = 0;
				Date checkDate = null;
				String checker = null;
				String auditOpion = null;
				if (spa.getCheckStatus() != null) {
					switch (Integer.parseInt(spa.getCheckStatus())) {
					case 0: finalStatus = 0;
							application.setApplicantSubmitStatus(0);
							break;
					case 1: finalStatus = 0;
							application.setApplicantSubmitStatus(1);
							break;
					case 2: finalStatus = 5;//校级通过，直接推送到部级
							application.setStatus(5);
							application.setApplicantSubmitStatus(3);
							application.setUniversityAuditResult(2);
							application.setUniversityAuditStatus(3);
							
							if(null != lastSChecklogs[0]){
								//处理高校变更情况
								if(null == getAgencyByName(lastSChecklogs[0].getChecker().trim())){
									//lastSChecklogs[0].setChecker(pg.getAgencyName());
								}
								checkDate = lastSChecklogs[0].getCheckDate();
								checker = lastSChecklogs[0].getChecker().trim();
								auditOpion = lastSChecklogs[0].getCheckInfo();
							}else{
								checkDate = spa.getCheckDate();
								checker = spa.getChecker().trim();
								//auditOpion = spa.getCheckInfo();
							}
							
							application.setUniversityAuditDate(checkDate);
							application.setUniversityAuditorName(checker);
							application.setUniversityAuditorAgency(getAgencyByName(checker));
							application.setUniversityAuditOpinion(auditOpion);
							
							break;
					case 3: finalStatus = 3;
							application.setStatus(3);
							application.setApplicantSubmitStatus(3);
							application.setUniversityAuditResult(1);
							application.setUniversityAuditStatus(3);
							
							application.setFinalAuditResult(1);
							application.setFinalAuditStatus(3);
							
							if(null != lastSChecklogs[0]){
								checkDate = lastSChecklogs[0].getCheckDate();
								checker = lastSChecklogs[0].getChecker().trim();
								auditOpion = lastSChecklogs[0].getCheckInfo();
							}else{
								checkDate = spa.getCheckDate();
								checker = spa.getChecker().trim();
								//auditOpion = spa.getCheckInfo();
							}
							
							application.setUniversityAuditDate(checkDate);
							application.setUniversityAuditorName(checker);
							application.setUniversityAuditorAgency(getAgencyByName(checker));
							application.setUniversityAuditOpinion(auditOpion);
							
							application.setFinalAuditDate(checkDate);
							application.setFinalAuditorName(checker);
							application.setFinalAuditorAgency(getAgencyByName(checker));
							application.setFinalAuditOpinion(auditOpion);
							
							break;
					case 4: finalStatus = 5;
							application.setStatus(5);
							application.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
							application.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
							application.setProvinceAuditResult(2);
							application.setProvinceAuditStatus(3);
							
							if(null != lastSChecklogs[0]){
								application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
								application.setUniversityAuditorName(lastSChecklogs[0].getChecker().trim());
								application.setUniversityAuditorAgency(getAgencyByName(lastSChecklogs[0].getChecker().trim()));
								application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
							}
							
							if(null != lastSChecklogs[1]){
								checkDate = lastSChecklogs[1].getCheckDate();
								checker = lastSChecklogs[1].getChecker().trim();
								auditOpion = lastSChecklogs[1].getCheckInfo();
							}else{
								checkDate = spa.getCheckDate();
								checker = spa.getChecker().trim();
								//auditOpion = spa.getCheckInfo();
							}
							application.setProvinceAuditDate(checkDate);
							application.setProvinceAuditorName(checker);
							application.setProvinceAuditorAgency(getAgencyByName(checker));
							application.setProvinceAuditOpinion(auditOpion);
							
							break;
					case 5: finalStatus = 5;
							application.setStatus(5);
							application.setUniversityAuditResult(2);//[0：未审核；1：不同意；2：同意]
							application.setUniversityAuditStatus(3);//[0：默认；1：退回；2：暂存；3：提交]
							application.setProvinceAuditResult(1);
							application.setProvinceAuditStatus(3);
							application.setFinalAuditResult(1);
							application.setFinalAuditStatus(3);
							
							if(null != lastSChecklogs[0]){
								application.setUniversityAuditDate(lastSChecklogs[0].getCheckDate());
								application.setUniversityAuditorName(lastSChecklogs[0].getChecker().trim());
								application.setUniversityAuditorAgency(getAgencyByName(lastSChecklogs[0].getChecker().trim()));
								application.setUniversityAuditOpinion(lastSChecklogs[0].getCheckInfo());
							}
							
							if(null != lastSChecklogs[1]){
								checkDate = lastSChecklogs[1].getCheckDate();
								checker = lastSChecklogs[1].getChecker().trim();
								auditOpion = lastSChecklogs[1].getCheckInfo();
							}else{
								checkDate = spa.getCheckDate();
								checker = spa.getChecker().trim();
								//auditOpion = spa.getCheckInfo();
							}
							application.setProvinceAuditDate(checkDate);
							application.setProvinceAuditorName(checker);
							application.setProvinceAuditorAgency(getAgencyByName(checker));
							application.setProvinceAuditOpinion(auditOpion);
							
							application.setFinalAuditDate(spa.getCheckDate());
							application.setFinalAuditorName(spa.getChecker().trim());
							application.setFinalAuditorAgency(getAgencyByName(spa.getChecker().trim()));
							application.setFinalAuditOpinion(auditOpion);
							
							break;
					case 6: finalStatus = 5;
							application.setStatus(5);
							application.setApplicantSubmitStatus(3);
							break;
					case 7: finalStatus = 5;
							application.setStatus(5);
							application.setApplicantSubmitStatus(3);
							break;
					case 8: finalStatus = 3; 
							application.setStatus(3);
							application.setApplicantSubmitStatus(2);
							application.setDeptInstAuditResult(2);//院系审核都通过
							application.setDeptInstAuditStatus(3);
							break;
					case 9: finalStatus = 3; 
							application.setStatus(3);
							application.setApplicantSubmitStatus(3);
							application.setDeptInstAuditResult(2);//院系审核都通过
							application.setDeptInstAuditStatus(3);
							break;
					default:finalStatus = 3; 
							application.setStatus(3);
				}
				if(finalStatus > 1 && finalStatus < 6){
					application.setDeptInstAuditResult(2);
					application.setDeptInstAuditStatus(3);
					application.setDeptInstAuditDate(spa.getApplyDate());
				}
				
				//将上述数据更新到数据库
				memberOrder();
				
				application.setCreateDate(new Date());
				application.setCreateMode(0);
				
				//将上述数据跟新到数据库
				spa.setIsAdded(0);
				spa.setProjectApplication(application);
				dao.addOrModify(application);
				dao.modify(spa);
				totalImportNum++;
			}
			}
		}		
		if (notFoundUnivProjectName != null && notFoundUnivProjectName.size() > 0) {
			if (illegalException == null) {
				illegalException = new HashMap<String, List<String[]>>();
			}
			List<String[]> tempException = illegalException.get("找不到该项目对应的高校");
			if (tempException == null) {
				tempException = new ArrayList<String[]>(notFoundUnivProjectName);
			}else {
				for (String[] tempStrings : notFoundUnivProjectName) {
					tempException.add(tempStrings);
				}
			}
			illegalException.put("找不到该项目对应的高校", tempException);
		}
		if (notFoundMemberProjectName != null && notFoundMemberProjectName.size() > 0) {
			if (illegalException == null) {
				illegalException = new HashMap<String, List<String[]>>();
			}
			List<String[]> tempException = illegalException.get("未成功添加该项目的成员");
			if (tempException == null) {
				tempException = new ArrayList<String[]>(notFoundMemberProjectName);
			}else {
				for (String[] tempStrings : notFoundMemberProjectName) {
					tempException.add(tempStrings);
				}
			}
			illegalException.put("未成功添加该项目的成员", tempException);
		}
		if (illegalException != null && illegalException.size() > 0 && !onlyUnivException(illegalException)) {
			freeMemory();
			isFinished = 1;
			throw new RuntimeException();
		}
	}
	/**
	 * 添加一个负责人(单独处理，基地项目负责人有可能是教师，也有可能是专家)
	 * @param application
	 * @param spa
	 * @param personName
	 * @param agencyName
	 * @param title 职称
	 * @param positon 职务
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void addApplicant(InstpApplication application, String personName, String agencyName, String title, String position) throws Exception {
		if (agencyName.isEmpty()) {
			agencyName = "未知机构";
		}
		Person applicant = null;
		InstpMember member = new InstpMember();
		System.out.println("申请人："+personName);
		
		//判断机构名是否学校名称，以判断该负责人是否教师。
		Agency univ = universityFinder.getUnivByName(agencyName);
		if (univ != null) {
			//教师
			Teacher teacher = univPersonFinder.findTeacher(personName, univ);
			//Teacher teacher = new Teacher();
			/*if(personName.equals("齐青") && univ.getId().equals("4028d88a2d2b1df0012d2b2218d81130")){
				teacher = (Teacher)dao.queryUnique("select t from Teacher t where t.id='4028d88a3098b5a1013098b7228e2335'");
			}else{*/
				/*teacher = (Teacher)dao.queryUnique("select t from Teacher t where t.person.name='" + personName + 
						"' and t.university.id='" + univ.getId() + "' and t.divisionName='" + spa.getProjectJDName() + "'");*/
			//}
			
			if (position != null && !position.isEmpty()) {
				teacher.setPosition(position);
			}
			applicant = teacher.getPerson();
			member.setUniversity(teacher.getUniversity());
			member.setDepartment(teacher.getDepartment());
			member.setInstitute(teacher.getInstitute());
			member.setDivisionName(teacher.getDepartment() != null ? teacher.getDepartment().getName() : teacher.getInstitute() != null ? teacher.getInstitute().getName() : null);
			member.setMemberType(1);
			member.setDivisionType(teacher.getDepartment() != null ? 2 : 1);
		} else {
			//专家
			Expert expert = expertFinder.findExpert(personName, agencyName);
			//Expert expert = (Expert) dao.queryUnique("select e from Expert e where e.person.name='" + personName + "' and e.agencyName='" + agencyName + "'");
			if (position != null && !position.isEmpty()) {
				expert.setPosition(position);
			}
			applicant = expert.getPerson();
			member.setMemberType(2);
		}
		
		application.setApplicantId(applicant.getId());
		application.setApplicantName(personName);
		beanFieldUtils.setField(applicant, "gender", spa.getGender(), BuiltinMergeStrategies.REPLACE);
		beanFieldUtils.setField(applicant, "birthday", spa.getBirthday(), BuiltinMergeStrategies.PRECISE_DATE, true);
		beanFieldUtils.setField(applicant, "officePhone", spa.getTelOffice(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
		beanFieldUtils.setField(applicant, "mobilePhone", spa.getTel(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
		beanFieldUtils.setField(applicant, "email", spa.getEmail(), BuiltinMergeStrategies.APPEND);
		beanFieldUtils.setField(applicant, "idcardNumber", spa.getIdCardNo(), BuiltinMergeStrategies.REPLACE);
		
		//地址数据信息
		if(applicant.getOfficeAddressIds() != null){
			if(addressParaMap == null) {
				addressParaMap = new HashMap<String,String>();
			}
			addressParaMap.put("addressIds", applicant.getOfficeAddressIds());
			List<Address> addressList = dao.query("select address from Address address where address.ids = :addressIds", addressParaMap);
			for (Address address : addressList) {
				if(address.getIsDefault() == 1) {
					address.setIsDefault(0);
				}
				address.setSn(address.getSn() + 1);
			}
			Address address = new Address();
			address.setAddress(spa.getAddress());
			address.setPostCode(spa.getPostalCode());
			address.setIds(applicant.getOfficeAddressIds());
			address.setCreateDate(new Date());
			address.setCreateMode(0);
			address.setIsDefault(1);
			address.setSn(1);
		} else {
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			applicant.setOfficeAddressIds(uuid);
			Address address = new Address();
			address.setIds(uuid);
			address.setAddress(spa.getAddress());
			address.setPostCode(spa.getPostalCode());
			address.setCreateDate(new Date());
			address.setCreateMode(0);
			address.setIsDefault(1);
			address.setSn(1);
		}
			
		//将研究专长存进Acadmic表中
		Academic academic = academicFinder.findAcademic(applicant);
		if (academic == null) {
			academic = new Academic();
			academic.setPerson(applicant);	
			applicant.setAcademic(academic);
		}			
		beanFieldUtils.setField(academic, "specialityTitle", title, BuiltinMergeStrategies.REPLACE);
		beanFieldUtils.setField(academic, "lastEducation", this.eduLevelCodeTrans(spa.getEduLevel()), BuiltinMergeStrategies.REPLACE);
		beanFieldUtils.setField(academic, "lastDegree", this.eduDegreeCodeTrans(spa.getEduDegree()), BuiltinMergeStrategies.REPLACE);
		beanFieldUtils.setField(academic, "language", spa.getLanguage(), BuiltinMergeStrategies.APPEND);
		dao.add(academic);
				
		member.setMember(applicant);
		member.setBirthday(spa.getBirthday());
		member.setMemberName(personName);
		member.setAgencyName(agencyName);
		beanFieldUtils.setField(member, "specialistTitle", title, BuiltinMergeStrategies.REPLACE);
		member.setIsDirector(1);
		member.setMemberSn(1);			
		member.setIdcardType(checkIDType(applicant.getIdcardNumber()));
		member.setGroupNumber(1);
		member.setGender(spa.getGender());
		member.setIdcardNumber(spa.getIdCardNo());
		member.setPosition(spa.getDuty());
		member.setEducation(this.eduLevelCodeTrans(spa.getEduLevel()));
		/*if(tool.isMemberExist(application, applicant)){  //需要时再用
			return;
		}*/
		application.addMember(member);
		
	}
	
	/**
	 * 添加一般成员
	 * @param application
	 * @param oMember
	 * @param isDirector
	 * @throws Exception
	 */
	private void addMember(InstpApplication application, SinossMembers sMember) throws Exception {
		InstpMember member = new InstpMember();  		
		Person person = null;
		
		//外部专家
		if(sMember.getName().equals("Елена Маркасова") || sMember.getName().equals("Юлия Меньшикова") || sMember.getName().equals("Светлана Друговейко Должанская")){
			
		}
		
		String JDName = spa.getProjectJDName();
		String memberUnivName = sMember.getUnitName();
		if (memberUnivName == null || memberUnivName.replaceAll("\\s+", "") == "") {
			memberUnivName = "未知机构";
		}
		String memberName = "";
		if(sMember.getName().equals("Елена Маркасова") || sMember.getName().equals("Юлия Меньшикова") || sMember.getName().equals("Светлана Друговейко Должанская")){
			memberName = sMember.getName();
		}else{
			memberName = tool.getName(sMember.getName());
		}
		if(memberName == null || "".equals(memberName)){
			throw new RuntimeException();
		}
		String memberTitle = this.regulateTitle(sMember.getTitle());
		Agency memberUniv = universityFinder.getUniversityWithLongestName(memberUnivName);		    
		Institute memberInst = null;

		if(sMember.getTitle() != null && this.checkStudentTitle(sMember.getTitle()) && memberUniv != null){  //学生（如果学生的高校信息为空，判定为专家）
			memberInst = instituteFinder.getInstitute(memberUniv, JDName, false);
			//instituteFinder.reset();//........................
			Student stuMember = univPersonFinder.findStudent(memberName, memberUniv);
			person = stuMember.getPerson();
			member.setUniversity(memberUniv);
			member.setInstitute(memberInst);
			member.setAgencyName(memberUniv.getName());				
			member.setMemberType(3);				
		} else if (memberUniv == null && memberUnivName != null){        //专家
			Expert eMember = expertFinder.findExpert(memberName, memberUnivName);
			person = eMember.getPerson();
			member.setAgencyName(memberUnivName);//专家直接传入表中对应数据						
			member.setMemberType(2);
		} else { //教师
			memberInst = instituteFinder.getInstitute(memberUniv, JDName, false);
			Teacher tMember = univPersonFinder.findTeacher(memberName, memberUniv);
			person = tMember.getPerson();				
			member.setUniversity(memberUniv);
			member.setInstitute(memberInst);
			if(memberUniv.getName() == null || memberUniv.getName().equals("")){
				System.out.println("3333");
				throw new RuntimeException();
			}				
			member.setAgencyName(memberUniv.getName());				
			member.setMemberType(1);
		}
		
		//将学位信息存进Acadmic表中，研究专长字段是不应该存在的   费侃如
		Academic academic = academicFinder.findAcademic(person);
		if (academic == null) {
			academic = new Academic();
			academic.setPerson(person);	
			person.setAcademic(academic);
		}
		beanFieldUtils.setField(academic, "researchSpeciality", sMember.getSpecialty(), BuiltinMergeStrategies.REPLACE);
		//学位   (sinoss里面是学位信息，数据库member里面是学历，Academic表里面都有。如何存。根据文卉师姐的说法只存入Academic表)
		beanFieldUtils.setField(academic, "lastDegree", this.eduDegreeCodeTrans(spa.getEduDegree()), BuiltinMergeStrategies.REPLACE);//学历			
			
		member.setIsDirector(0);
		member.setMemberName(memberName);
		member.setSpecialistTitle(memberTitle);  
		String memberCode = sMember.getCode();
		Date birth = sMember.getBirthday();
	//	String specialty = sMember.getSpecialty();
		String order = sMember.getOrders();
		if(memberCode != null){
			member.setIdcardNumber(memberCode);
			member.setIdcardType(checkIDType(memberCode));
		}		 		
		if(birth != null){
			member.setBirthday(birth);
		}        
		/*if(specialty != null){
			member.setMajor(sMember.getSpecialty());
		}*/		
		if(order == null || "".equals(order)){
			order = "isnull";
		}else{
			member.setMemberSn(Integer.parseInt(order));
		}
		/*if(tool.isMemberExist(application, person)){  //有需要时调用
			return;
		}*/
		if(!spa.getApplyer().equals(memberName)){ //如果该成员不是申请人，就添加（假定同一项目组里的人不同名）
			if (instpMemberOrder == null) {
				instpMemberOrder = new HashMap<InstpMember, String>();
			}
			instpMemberOrder.put(member, order);		
			application.addMember(member);
			member.setMember(person);
			//System.out.println("成员名称："+ sTool.sinossMemberMap.get(sMember)[1]);
		}else{
			System.out.println("成员与申请人可能同名，需手动审核数据修改数据，防止成员数据重复导入。如果是申请人，就不用理睬，如果仅仅是和申请人同名，就得手动添加成员信息");
		}		  
		member.setGroupNumber(1);	  				
	}
	
	/**
	 * 成员序号问题：普通成员序号是null的，任意设置从2开始；普通成员序号是非null的，在现有的顺序的基础上规范化从2开始（申请人序号是1）
	 */
	public void memberOrder(){		
		memberCount = 2;
		minMemberOrder = 100;
		if (instpMemberOrder == null) {
			instpMemberOrder = new HashMap<InstpMember, String>();
		}
		Set<Entry<InstpMember, String>> memberAndOrders = instpMemberOrder.entrySet();//nameAndOrders只包含sinoss-member里面的非申请人成员
		for(Entry<InstpMember, String> memberAndOrder : memberAndOrders ){
			String val = memberAndOrder.getValue();
			if(!val.equals("isnull") && Integer.parseInt(val) < minMemberOrder){
				minMemberOrder = Integer.parseInt(val);
			}				
		}
		Iterator<Entry<InstpMember, String>> iterator = memberAndOrders.iterator();
		if(iterator.hasNext()){
			if(iterator.next().getValue().equals("isnull")){  //只要有一个为空，默认所有成员序号都为空				
				for(Entry<InstpMember, String> memberAndOrder : memberAndOrders){
					memberAndOrder.getKey().setMemberSn(memberCount++);
				}
			} else{ //将最小的设置为2，依次增加（基于现有的顺序）
				if(minMemberOrder == 0){
					for(Entry<InstpMember, String> memberAndOrder : memberAndOrders){
						InstpMember gmember = memberAndOrder.getKey();
						gmember.setMemberSn(gmember.getMemberSn()+2);
					}
				}else if(minMemberOrder == 1){
					for(Entry<InstpMember, String> memberAndOrder : memberAndOrders){
						InstpMember gmember = memberAndOrder.getKey();
						gmember.setMemberSn(gmember.getMemberSn()+1);
					}
				}
			}
		}
	}
	
	/**
	 * 从混乱的学院名中获取学院名
	 */
	public String getRegularDivision(String str) throws Exception{	//"华中科技大学文华学院     华中科技大学文华学院商学院"	
		String str1 = str.replaceAll("\\s+", "");  
		String str2 = str1.replaceAll("[;；,，、。\\./~]+", "、"); 
		String str3 = str2.replaceAll("\\((.*?)\\)", "（$1）"); 
		String str4 = str3.replaceAll("？*", "");
		String str5 = str4.replaceAll("\\（(.*?)\\）", ""); 
		
		Agency univer = universityFinder.getUniversityWithLongestName(str5);
		if(univer != null){
			String univerName = univer.getName();
			if(str5.contains(univerName)){
				if(str5.endsWith(univerName)){  
					return ""; 
				}
					
				String[] strs = str4.split(univerName);
				if (strs[1].matches("[,，/、；;-~]+(.*?)")){ //哈尔滨工业大学，管理学院
					return strs[1].replaceAll("[,，/、；;-~]+", "");
				}else {
					return strs[1];  
				}
			}
		}
		
		return str4;		
	}
		
	public InstpApplicationAutoImporter(){
	}
		
	/**
	 * 通过一级学科、二级学科、三级学科获取学科代码  
	 */
	public String getSubjectCode(String subject1, String subject2, String subject3, String researchDir){
		subject1 = (subject1 == null) ? "" : _discipline(subject1);
		subject2 = (subject2 == null) ? "" : _discipline(subject2);
		subject3 = (subject3 == null) ? "" : _discipline(subject3);
		
		if ((subject1.equals("75047-99") || subject1.equals("75011-44")) && subject2.length()>0) {
			if (subject3.length() > 0 && subject3.contains(subject2)) {
				return subject3 + "/" + tool.discCodeNameMap.get(subject3);
			} else if (subject3.length() > 0 && !subject3.contains(subject2)) {
				if (subject2.compareTo(subject3) > 0) {
					return subject3 + "/" + tool.discCodeNameMap.get(subject3) + "; " + subject2 + "/" + tool.discCodeNameMap.get(subject2);
				} else {
					return subject2 + "/" + tool.discCodeNameMap.get(subject2) + "; " + subject3 + "/" + tool.discCodeNameMap.get(subject3);
				}
			} else if (subject3.length() == 0 ) {
				return subject2 + "/" + tool.discCodeNameMap.get(subject2);
			} else {
				throw new RuntimeException();//只有一级学科且一级学科是个综合学科，需要手动处理
			}
		} else {
			if (subject3.length() > 0) {//有一级、二级和三级
				if (subject3.contains(subject2)) {//三级包含二级
					if (subject2.contains(subject1)) {//三级包含二级，二级包含一级
						return subject3 + "/" + tool.discCodeNameMap.get(subject3);
					} else {//三级包含二级，二级不包含一级
						if(subject1.compareTo(subject3) > 0) {
							return subject3 + "/" + tool.discCodeNameMap.get(subject3) + "; " + subject1 + "/" + tool.discCodeNameMap.get(subject1);
						} else {
							return subject1 + "/" + tool.discCodeNameMap.get(subject1) + "; " + subject3 + "/" + tool.discCodeNameMap.get(subject3);
						}
					}
				} else {//三级不包含二级
					if (subject2.contains(subject1)) {//三级不包含二级，二级包含一级
						if (subject2.compareTo(subject3) > 0){
							return subject3 + "/" + tool.discCodeNameMap.get(subject3) + "; " + subject2 + "/" + tool.discCodeNameMap.get(subject2);
						} else {
							return subject2 + "/" + tool.discCodeNameMap.get(subject2) + "; " + subject3 + "/" + tool.discCodeNameMap.get(subject3);
						}
					} else {//三级不包含二级，二级不包含一级(排序)
						String[] subjects = {subject1, subject2, subject3};
						Arrays.sort(subjects);
						return subjects[0] + "/" + tool.discCodeNameMap.get(subjects[0]) + 
								"; " +subjects[1] + "/" + tool.discCodeNameMap.get(subjects[1]) + 
								"; " +subjects[2] + "/" + tool.discCodeNameMap.get(subjects[2]);
					}
				}
			} else if (subject3.length() == 0 && subject2.length() > 0) {//只有一级和二级
				if (subject2.contains(subject1)){
					return subject2 + "/" + tool.discCodeNameMap.get(subject2);
				} else {
					if (subject2.compareTo(subject1) > 0) {
						return subject1 + "/" + tool.discCodeNameMap.get(subject1) + "; " + subject2 + "/" + tool.discCodeNameMap.get(subject2);
					}
					else {
						return subject2 + "/" + tool.discCodeNameMap.get(subject2) + "; " + subject1 + "/" + tool.discCodeNameMap.get(subject1);
					}
				}
			} else {//只有一级
				return subject1 + "/" + tool.discCodeNameMap.get(subject1);
			}						
		}
	}
		
	/**
	 * 审核状态码-->审核状态  
	 */
	public String AuditStatus(String auditStatue){
		if(auditStatue.equals("0")){
			return "未提交";
		}else if (auditStatue.equals("1")){
			return "退回修改";
			
		}else if (auditStatue.equals("2")){
			return "学校审核通过";
		}else if (auditStatue.equals("3")){
			return "学校审核不通过";
			
		}else if (auditStatue.equals("4")){
			return "主管部门审核通过";
		}else if (auditStatue.equals("5")){
			return "主管部门审核不通过";
//----------------------------------------------------------------			
		}else if (auditStatue.equals("6")){
			return "社科司审核通过";
		}else if (auditStatue.equals("7")){
			return "社科司审核不通过";
		}else if (auditStatue.equals("8")){
			return "已修改";
		}else if (auditStatue.equals("9")){
			return "已提交";
		}else return "未知";
	}
	
	/**
	 * 学科转换
	 * @param subject
	 * @return
	 */
	public String _discipline(String subject){
		if(subject.startsWith("GAT")){
			return "980";
		}else if(subject.startsWith("GJW")){
			return "990";
		}else if(subject.startsWith("SXZZJY")){
			return "970";
		}else return subject;
	}
	
	public String dealFile(String filename){
		filename = filename + ".doc";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Long tempFileModifiedTime = null ;
		String smdbFilePath = "upload/project/instp/app/2015/";
		String smdbRealPath = ApplicationContainer.sc.getRealPath(smdbFilePath);
		
		String sinossFilePath = "upload/sinoss/base/app/2015/";
		String sinossRealPath = ApplicationContainer.sc.getRealPath(sinossFilePath);
		
		String smdbFileName = "instp_app_2015_" + spa.getUnitCode() + "_" + sdf.format(new Date(tempFileModifiedTime)) + ".doc";
		File destFile = new File(smdbRealPath, smdbFileName);
		File srcFile = new File(sinossRealPath, filename);
		for(int i=1; destFile.exists(); i++) {
			smdbFileName = "instp_app_2015_" + spa.getUnitCode() + "_" + sdf.format(new Date(tempFileModifiedTime + i)) + ".doc";
			destFile = new File(smdbRealPath, smdbFileName);
		}
		
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			if(destFile.exists()) {		//删除拷贝不完整的文件
				destFile.delete();
			}
		}
		
		return smdbFilePath + smdbFileName;
	}
	
}
