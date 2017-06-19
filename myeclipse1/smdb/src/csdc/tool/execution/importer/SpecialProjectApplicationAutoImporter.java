package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import csdc.bean.Academic;
import csdc.bean.Address;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.Person;
import csdc.bean.SinossMembers;
import csdc.bean.SinossProjectApplication;
import csdc.bean.SpecialApplication;
import csdc.bean.SpecialMember;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.tool.StringTool;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;

/**
 * 一般项目申请数据入库   (from sinoss)
 * @author pengliang
 *
 */
public class SpecialProjectApplicationAutoImporter extends ProjectApplicationAutoImporter{
	@SuppressWarnings("unchecked")
	public void initSinossProjectData(){
		templist = dao.query("select spa from SinossProjectApplication spa where spa.typeCode = 'special' and spa.isAdded = 2 order by spa.id", 0, 3000);
	}
	
	public void work() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		isFinished = 0;
		currentNum = 0;
		totalImportNum = 0;
		totalNum = (int) dao.count("select count(spa.id) from SinossProjectApplication spa where spa.typeCode = 'special' and spa.isAdded = 2");
		System.out.println("**************************************************************************");
		System.out.println("开始导入...");
		if (illegalException != null) {
			illegalException = null;
		}
		while (currentNum < totalNum) {
			initTitle();
			initSinossProjectData();
			initSpecialSubSub();
			if (templist != null && templist.size() > 0) {
				dumpDate = sdf.format(templist.get(0).getDumpDate());
			}
			initSinossCheckData();
			initSinossMembers(dumpDate);
			importData();
			dao.flush();
			dao.clear();
		}
		freeMemory();
		isFinished = 1;
		System.out.println("over");	
	}

	@SuppressWarnings("unchecked")
	public void importData() throws Exception {
		SystemOption 工程科技 = systemOptionDao.query("projectType", "0708");
		SystemOption 思政工作 = systemOptionDao.query("projectType", "0706");
		SystemOption 中特理论 = systemOptionDao.query("projectType", "0707");
		SystemOption 教育廉政 = systemOptionDao.query("projectType", "0702");
		SystemOption 科研诚信 = systemOptionDao.query("projectType", "0701");

		SystemOption 基础研究 = systemOptionDao.query("researchType", "01");   
		SystemOption 应用研究 = systemOptionDao.query("researchType", "02");        
		SystemOption 实验与发展 = systemOptionDao.query("researchType", "03");
		
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
			String sApplicationId = spa.getId();
			//++currentNum不被能注释
			System.out.println("当前导入项目名：" + spa.getProjectName() + "  (学校代码：" + spa.getUnitCode() + ")————导入第" + (++currentNum) + "/" + totalNum+ "条数据");
		
			Agency university = universityFinder.getUnivByName(spa.getUnitName().trim());
			if(university == null) {
				if (notFoundUnivProjectName == null) {
					notFoundUnivProjectName = new ArrayList<String[]>();
				}
				String[] tempNotFoundUnivProjectName = new String[2];
				tempNotFoundUnivProjectName[0] = spa.getProjectName();
				tempNotFoundUnivProjectName[1] = spa.getUnitName();
				notFoundUnivProjectName.add(tempNotFoundUnivProjectName);
				spa.setNote(exchangeNoteInfo(spa.getNote(), "找不到该项目对应的高校", spa.getProjectName(), spa.getUnitName()));
			} else {
				//chechStatus用于区分同一项目多次申请的情况
				SpecialApplication application = specialProjectFinder.findApplication(spa.getProjectName().trim(), spa.getApplyer(), Integer.parseInt(spa.getYear()), Integer.parseInt(spa.getCheckStatus()));
				//dept即部门，单独的处理函数
				String dept = getRegularDepartment(spa.getDivision());
				Department department = departmentFinder.getDepartment(university, dept, true);
				
				//申请人只能是老师，若没有，则自动增加
				String applicantName = tool.getName(spa.getApplyer());
				Teacher applicant = univPersonFinder.findTeacher(applicantName, department);	
				Academic academic = academicFinder.findAcademic(applicant.getPerson());//在数据库中查找有没有此人的学术信息，若没有，就添加一个新的

				if (academic == null) {
					academic = new Academic();
					academic.setPerson(applicant.getPerson());	
					applicant.getPerson().setAcademic(academic);
				}
				
				if (application == null) {
					application = new SpecialApplication();   
				}
				beanFieldUtils.setField(application, "name", spa.getProjectName().trim(), BuiltinMergeStrategies.REPLACE);
				
				//项目机构部门单位等+申请人
				application.setYear(Integer.parseInt(spa.getYear()));
				application.setApplicantSubmitDate(spa.getApplyDate());
				application.setUniversity(university);
				application.setAgencyName(university.getName());
				application.setDepartment(department);
				application.setDivisionName(department.getName());
				application.setApplicantId(applicant.getPerson().getId());
				application.setApplicantName(applicantName);
				application.setFile(spa.getApplyDocName());
				
				//项目子类
				char projectTypeLastNum = spa.getProjectType().charAt(spa.getProjectType().length()-1);
				if (projectTypeLastNum == '1') {
					application.setSubtype(工程科技);
				} else if (projectTypeLastNum == '2') {
					application.setSubtype(思政工作);
				} else if (projectTypeLastNum == '3') {
					application.setSubtype(中特理论);
				} else if (projectTypeLastNum == '4') {
					application.setSubtype(教育廉政);
				} else if (projectTypeLastNum == '5') {
					application.setSubtype(科研诚信);
				}
				
				//项目子类的子类
				SystemOption projectSubsubtype = specialSubSubMap.get(spa.getSubType()+"/"+spa.getProjectType());
				if (spa.getSubType() != null && projectSubsubtype == null) {
					throw new RuntimeException();  //需要补充完善SystemOption中专项任务子类的子类
				}
				application.setSubsubtype(projectSubsubtype);
				
		        //学科门类+学科代码
				String subject = spa.getSubject();
				String subject1_1 = spa.getSubject1_1();
				String subject1_2 = spa.getSubject1_2();
				String researchDir = spa.getResearchDirection();
				String subjectCode = getSubjectCode(subject, subject1_1, subject1_2, researchDir);
				beanFieldUtils.setField(application, "disciplineType", getSubjectType(subject), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(application, "discipline", subjectCode, BuiltinMergeStrategies.REPLACE);
				
				//项目研究类别
				if (spa.getResearchType().contains("基础研究")) {
					application.setResearchType(基础研究);
				} else if (spa.getResearchType().contains("实验与发展")) {
					application.setResearchType(实验与发展);
				} else if (spa.getResearchType().contains("应用研究")) {
					application.setResearchType(应用研究);
				}
				
				//项目计划结束时间+经费
				beanFieldUtils.setField(application, "planEndDate", spa.getPlanFinishDate(), BuiltinMergeStrategies.PRECISE_DATE);
				beanFieldUtils.setField(application, "applyFee", tool.getFee(spa.getApplyTotleFee()), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(application, "otherFee", tool.getFee(spa.getOtherFeeSource()), BuiltinMergeStrategies.REPLACE);
				
				//项目最终成果形式
				String[] productType = productTypeNormalizer.getNormalizedProductType(spa.getLastProductMode());
				application.setProductType(productType[0]);
				application.setProductTypeOther(productType[1]);
				
				//项目申请人
				beanFieldUtils.setField(applicant.getPerson(), "gender", spa.getGender(), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(applicant.getPerson(), "birthday", spa.getBirthday(), BuiltinMergeStrategies.PRECISE_DATE);
				beanFieldUtils.setField(applicant.getPerson(), "officePhone", spa.getTelOffice(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				beanFieldUtils.setField(applicant.getPerson(), "mobilePhone", spa.getTel(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
				beanFieldUtils.setField(applicant.getPerson(), "email", spa.getEmail(), BuiltinMergeStrategies.APPEND);
				beanFieldUtils.setField(applicant.getPerson(), "idcardNumber", spa.getIdCardNo(), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(applicant, "position", spa.getDuty(), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(academic, "lastEducation", this.eduLevelCodeTrans(spa.getEduLevel()), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(academic, "lastDegree", this.eduDegreeCodeTrans(spa.getEduDegree()), BuiltinMergeStrategies.REPLACE);
				beanFieldUtils.setField(academic, "language", spa.getLanguage(), BuiltinMergeStrategies.APPEND);
				beanFieldUtils.setField(academic, "specialityTitle", this.regulateTitle(spa.getTitle()), BuiltinMergeStrategies.REPLACE);
				
				//地址数据信息
				if(applicant.getPerson().getOfficeAddressIds() != null){
					if(addressParaMap == null) {
						addressParaMap = new HashMap<String,String>();
					}
					addressParaMap.put("addressIds", applicant.getPerson().getOfficeAddressIds());
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
					address.setIds(applicant.getPerson().getOfficeAddressIds());
					address.setCreateDate(new Date());
					address.setCreateMode(0);
					address.setIsDefault(1);
					address.setSn(1);
				} else {
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					applicant.getPerson().setOfficeAddressIds(uuid);
					Address address = new Address();
					address.setIds(uuid);
					address.setAddress(spa.getAddress());
					address.setPostCode(spa.getPostalCode());
					address.setCreateDate(new Date());
					address.setCreateMode(0);
					address.setIsDefault(1);
					address.setSn(1);
				}
				
				//成员信息
				if(specialMemberOrder != null){
					specialMemberOrder.clear();
				}			
				System.out.println("导入项目成员.........");
				addMember(application, null, applicant);
				currentSinossMembers = projectIdToSMembersMap.get(sApplicationId);
				projectIdToSMembersMap.remove(sApplicationId);
				if (currentSinossMembers == null) {
					currentSinossMembers = new ArrayList<SinossMembers>();
				}
				for (SinossMembers sinossMember : currentSinossMembers) {
					try{
						addMember(application, sinossMember , null);
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
				
				//导入审核信息
				int finalStatus = 0;
				if (spa.getCheckStatus() != null) {
					switch (Integer.parseInt(spa.getCheckStatus())) {
						case 0: finalStatus = 0;
								application.setApplicantSubmitStatus(0);
								break;
						case 1: finalStatus = 0;
								application.setApplicantSubmitStatus(1);
								break;
						case 2: if(university.getType() == 3) {  //部属高校项目
									finalStatus = 5;
									application.setStatus(5);
								} else {
									finalStatus = 4;
									application.setStatus(4);
								}
								application.setApplicantSubmitStatus(3);
						        application.setUniversityAuditResult(2);
						        application.setUniversityAuditStatus(3);
						        application.setUniversityAuditDate(spa.getCheckDate());
						        application.setUniversityAuditorAgency(universityFinder.getUnivByName(spa.getChecker()));
						        break;
						case 3: finalStatus = 3;
								application.setApplicantSubmitStatus(3);
								application.setStatus(3);
								application.setUniversityAuditResult(1);
						        application.setUniversityAuditStatus(3);
						        application.setUniversityAuditDate(spa.getCheckDate());
						        application.setUniversityAuditorAgency(universityFinder.getUnivByName(spa.getChecker()));
						        break;
						case 4: finalStatus = 5;
								application.setStatus(5);
								application.setApplicantSubmitStatus(3);
								application.setProvinceAuditResult(2);
								application.setProvinceAuditStatus(3);
								application.setProvinceAuditDate(spa.getCheckDate());
								application.setProvinceAuditorAgency(universityFinder.getAgencyByName(spa.getChecker()));
								break;
						case 5: finalStatus = 4;
								application.setStatus(4);
								application.setApplicantSubmitStatus(3);
								application.setProvinceAuditResult(1);
								application.setProvinceAuditStatus(3);
								application.setProvinceAuditDate(spa.getCheckDate());
								application.setProvinceAuditorAgency(universityFinder.getAgencyByName(spa.getChecker()));
								break;
						case 6: finalStatus = 5;
								application.setStatus(5);
								application.setApplicantSubmitStatus(3);
								application.setMinistryAuditResult(2);
								application.setMinistryAuditStatus(3);
								break;
						case 7: finalStatus = 5;
								application.setStatus(5);
								application.setApplicantSubmitStatus(3);
								application.setMinistryAuditResult(1);
								application.setMinistryAuditStatus(3);
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
				}
				//导入详细审核信息
				if(finalStatus>0 && finalStatus<8){
					AddProjectApplicationCheckLogs(spa, application, finalStatus);
				}
				
				//将上述数据更新到数据库
				memberOrder(); 

				application.setCreateDate(new Date());
				application.setCreateMode(0);
				
				spa.setIsAdded(0);
				dao.addOrModify(application);
				dao.addOrModify(academic);
				totalImportNum++;
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
	
	//成员表对应多张表
	private void addMember(SpecialApplication application, SinossMembers sMember, Teacher director) throws Exception {
		SpecialMember member = new SpecialMember();  		
		Person person = null;
		if (sMember != null && director == null){   //一般成员
			String memberUnivName = sMember.getUnitName();
			if (memberUnivName == null || "".equals(memberUnivName.replaceAll("\\s+", ""))) {
				memberUnivName = "未知机构";
			}
			String memberName = tool.getName(sMember.getName());
			if(memberName == null || "".equals(memberName)){
				throw new RuntimeException();
			}
			String memberTitle = this.regulateTitle(sMember.getTitle());
			Agency memberUniv = universityFinder.getUniversityWithLongestName(memberUnivName);

			if(sMember.getTitle() != null && this.checkStudentTitle(sMember.getTitle()) && memberUniv != null){  //学生（如果学生的高校信息为空，判定为专家）
				Student stuMember = univPersonFinder.findStudent(memberName, memberUniv);
				person = stuMember.getPerson();				
				member.setUniversity(memberUniv);
				member.setAgencyName(memberUniv.getName());
				member.setMemberType(3);				
			} else if (memberUniv == null){        //专家
				Expert eMember = expertFinder.findExpert(memberName, memberUnivName);
				person = eMember.getPerson();
				member.setAgencyName(memberUnivName);//专家直接传入表中对应数据
				member.setMemberType(2);
			} else { //教师
				Teacher tMember = univPersonFinder.findTeacher(memberName, memberUniv);
				person = tMember.getPerson();				
				member.setUniversity(memberUniv);
				member.setAgencyName(memberUniv.getName());
				member.setMemberType(1);
			}
			//将学位信息存进Acadmic表中，研究专长字段是不应该存在的
			Academic academic = academicFinder.findAcademic(person);
			if (academic == null) {
				academic = new Academic();
				academic.setPerson(person);	
				person.setAcademic(academic);
			}
			//学位   
			beanFieldUtils.setField(academic, "lastDegree", this.eduDegreeCodeTrans(spa.getEduDegree()), BuiltinMergeStrategies.REPLACE);//学历
			beanFieldUtils.setField(academic, "researchSpeciality", sMember.getSpecialty(), BuiltinMergeStrategies.REPLACE);
			
			member.setIsDirector(0);
			member.setMemberName(memberName);
			member.setSpecialistTitle(memberTitle);
			String memberCode = StringTool.toDBC(sMember.getCode());//居然有输入全角的３５０１０２１９９００５０５２８１１
			Date birth = sMember.getBirthday();
			String order = sMember.getOrders();
			if(memberCode != null){
				member.setIdcardType(checkIDType(memberCode));
				member.setIdcardNumber(memberCode);				
			}		 		
			if(birth != null){
				member.setBirthday(sMember.getBirthday());
			}
			if(order == null){
				order = "isnull";
			}else{
				member.setMemberSn(Integer.parseInt(order));
			}
			if(!spa.getApplyer().equals(memberName)){ //如果该成员不是申请人，就添加（假定同一项目组里的人不同名）
				if (specialMemberOrder == null) {
					specialMemberOrder = new HashMap<SpecialMember, String>();
				}
				specialMemberOrder.put(member, order);		
				application.addMember(member);
				member.setMember(person);
				//System.out.println("成员名称："+ sTool.sinossMemberMap.get(sMember)[1]);
			}else{
				System.out.println("成员与申请人可能同名，需手动审核数据修改数据，防止成员数据重复导入。如果是申请人，就不用理睬，如果仅仅是和申请人同名，就得手动添加成员信息");
			}	
		} else if (sMember == null && director != null){   //项目负责人（老师）
			person = director.getPerson();
			System.out.println("项目申请人：" + person.getName());
			Department dept = application.getDepartment();
			member.setUniversity(application.getUniversity());
			member.setDepartment(dept);
			member.setAgencyName(spa.getUnitName());
			if (dept != null) {
				member.setDivisionName(dept.getName());
				member.setDivisionType(2);
			}		

			member.setMemberType(1);
			member.setIsDirector(1);
			member.setMemberName(person.getName());
			member.setIdcardType(checkIDType(person.getIdcardNumber()));
			member.setSpecialistTitle(person.getAcademic().getSpecialityTitle());	
			member.setMemberSn(1);	
			application.addMember(member);
		}
		
		member.setMember(person);	
		member.setGroupNumber(1);				
	}
	
	/**
	 * 成员序号问题：普通成员序号是null的，任意设置从2开始；普通成员序号是非null的，在现有的顺序的基础上规范化从2开始（申请人序号是1）
	 */
	public void memberOrder(){		
		memberCount = 2;
		minMemberOrder = 1000;
		if (specialMemberOrder == null) {
			specialMemberOrder = new HashMap<SpecialMember, String>();
		}
		Set<Entry<SpecialMember, String>> memberAndOrders = specialMemberOrder.entrySet();//nameAndOrders只包含sinoss-member里面的非申请人成员
		for(Entry<SpecialMember, String> memberAndOrder : memberAndOrders ){
			String val = memberAndOrder.getValue();
			if(!val.equals("isnull") && Integer.parseInt(val) < minMemberOrder){
				minMemberOrder = Integer.parseInt(val);
			}				
		}
		Iterator<Entry<SpecialMember, String>> iterator = memberAndOrders.iterator();
		if(iterator.hasNext()){
			if(iterator.next().getValue().equals("isnull")){  //只要有一个为空，默认所有成员序号都为空				
				for(Entry<SpecialMember, String> memberAndOrder : memberAndOrders){
					memberAndOrder.getKey().setMemberSn(memberCount++);
				}
			} else{ //将最小的设置为2，依次增加（基于现有的顺序）
				if(minMemberOrder == 0){
					for(Entry<SpecialMember, String> memberAndOrder : memberAndOrders){
						SpecialMember gmember = memberAndOrder.getKey();
						gmember.setMemberSn(gmember.getMemberSn()+2);
					}
				}else if(minMemberOrder == 1){
					for(Entry<SpecialMember, String> memberAndOrder : memberAndOrders){
						SpecialMember gmember = memberAndOrder.getKey();
						gmember.setMemberSn(gmember.getMemberSn()+1);
					}
				}
			}
		}
	}
	
	/**
	 * 从混乱的学院名中获取学院名
	 * @author 2014-8-30
	 */
	public String getRegularDepartment(String str) throws Exception{	//"华中科技大学文华学院     华中科技大学文华学院商学院"	
		String str1 = str.replaceAll("\\s+", "");  
		String str2 = str1.replaceAll("[;；,，、。\\./~]+", "、"); 
		String str3 = str2.replaceAll("\\((.*?)\\)", "（$1）"); 
		String str4 = str3.replaceAll("？*", "");
		String str5 = str4.replaceAll("\\（(.*?)\\）", ""); 
		
		Agency univer = universityFinder.getUniversityWithLongestName(str5);  //具有耦合性，不能放入Tool中
		if(univer != null){
			String univerName = univer.getName();
			if(str5.contains(univerName)){
				if(str5.endsWith(univerName)){  
					return ""; 
				}
				
				str4 = str4.replaceAll(univerName, "");
				str4 = str4.replaceAll("[,，/、；;-~]+(.*?)", "");
				return str4;
			}
		}
		
		return str4;		
	}
		
	public SpecialProjectApplicationAutoImporter(){
	}

	/**
	 * 通过一级学科、二级学科、三级学科获取学科代码  
	 * @author 2014-8-30 
	 */
	public String getSubjectCode(String subject1, String subject2, String subject3, String researchDir){
		String discipline = tool.transformDisc(researchDir);
		
		if (subject1.equals("75047-99") || subject1.equals("75011-44") || subject1.equals("zjxxk")) {
			if (discipline == null || "".equals(discipline)) {
				throw new RuntimeException();//只有综合学科
			} else {
				return discipline;
			}
		} else {
			if (discipline.contains(_discipline(subject1))) {
				return discipline;
			} else {
				if (discipline.compareTo(_discipline(subject1)) > 0) {
					return _discipline(subject1) + "/" + tool.discCodeNameMap.get(_discipline(subject1)) + "; " + discipline;
				} else {
					return discipline + "; " + _discipline(subject1) + "/" + tool.discCodeNameMap.get(_discipline(subject1)) + "; " + discipline;
				}
			}
		}
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
	
	/**
	 * 初始化专项项目子类的子类
	 */
	public void initSpecialSubSub(){
		Date begin = new Date();
		if (specialSubSubMap == null) {
			specialSubSubMap = new HashMap<String, SystemOption>();
		}
		specialSubSubMap.put("一般/402882f34292f7540142933a8f0c0005", systemOptionDao.query("projectType", "070101"));
		specialSubSubMap.put("重大/402882f34292f7540142933a8f0c0005",systemOptionDao.query("projectType", "070102"));
		specialSubSubMap.put("委托/402882f34292f7540142933a8f0c0005",systemOptionDao.query("projectType", "070103"));
		specialSubSubMap.put("一类/402882f34292f75401429339f48b0002", systemOptionDao.query("projectType", "070601"));
		specialSubSubMap.put("二类/402882f34292f75401429339f48b0002", systemOptionDao.query("projectType", "070602"));
		specialSubSubMap.put("辅导员专项/402882f34292f75401429339f48b0002", systemOptionDao.query("projectType", "070603"));
		specialSubSubMap.put("重大/402882f34292f75401429339f48b0002", systemOptionDao.query("projectType", "070604"));
		specialSubSubMap.put("委托/402882f34292f75401429339f48b0002", systemOptionDao.query("projectType", "070605"));
		specialSubSubMap.put("一类/402882f34292f7540142933a2bff0003", systemOptionDao.query("projectType", "070701"));
		specialSubSubMap.put("二类/402882f34292f7540142933a2bff0003", systemOptionDao.query("projectType", "070702"));
		specialSubSubMap.put("我国高校工科教师师资队伍建设研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070801"));
		specialSubSubMap.put("我国高等工程教育布局与产业发展对接问题研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070802"));
		specialSubSubMap.put("工程硕士和工程博士培养若干问题研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070803"));
		specialSubSubMap.put("工程教育国际比较研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070804"));
		specialSubSubMap.put("校企深度合作培养工程人才若干问题研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070805"));
		specialSubSubMap.put("工程科技人才的评价标准、评估体系研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070806"));
		specialSubSubMap.put("面向我国新型工业化、信息化、城镇化、农业现代化的职业教育体系研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070807"));
		specialSubSubMap.put("我国医学、农林专业人才培养问题研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070808"));
		specialSubSubMap.put("工程科技人才培养的质量保障体系建设研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070809"));
		specialSubSubMap.put("中国工程教育国际竞争力问题研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070810"));
		specialSubSubMap.put("新科技革命和产业变革背景下工程教育的布局以及工程院校的分类与定位研究/402882f34292f75401429339a4890001", systemOptionDao.query("projectType", "070811"));
		
		System.out.println("initSpecialSubSub completed! Use time : " + (new Date().getTime() - begin.getTime()) + "ms");		
	}
	
}

