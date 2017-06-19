package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
import csdc.bean.Institute;
import csdc.bean.InstpApplication;
import csdc.bean.InstpMember;
import csdc.bean.Person;
import csdc.bean.SinossMembers;
import csdc.bean.SinossProjectApplication;
import csdc.bean.Student;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.SystemOptionDao;
import csdc.tool.StringTool;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.DepartmentFinder;
import csdc.tool.execution.finder.ExpertFinder;
import csdc.tool.execution.finder.GeneralProjectFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.InstpProjectFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.GeneralApplicationMemberParser;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.SinossTableTool;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 2014年基地项目申请数据入库   (from sinoss)
 * @author pengliang
 *
 */
public class InstpApplication2014Importer extends Importer{
	/**
	 * 《附件1：20140314_教育部社会科学研究管理平台数据交换职称代码.xls》
	 */
	private ExcelReader reader;
	
	@Autowired
	private BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	@Autowired
	private DepartmentFinder departmentFinder;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;

	@Autowired
	private AcademicFinder academicFinder;
	
	@Autowired
	private InstpProjectFinder instpProjectFinder;

	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;

	@Autowired
	private GeneralApplicationMemberParser generalApplicationMemberParser;
	
	@Autowired
	private ExpertFinder expertFinder;
	
	@Autowired
	private Tool tool;
	
	@Autowired
	private SinossTableTool sTool;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	/**
	 * 从初始化的map集合中获取指定项目的成员
	 */
	private List<SinossMembers> sinossMembers = new ArrayList<SinossMembers>();
	
	/**
	 * 初始化职称代码 -> 职称名
	 */
	public Map<String, String> title;
	
	/**
	 * 项目成员序号(申请人是1，成员从2开始)
	 */
	private Map<InstpMember, String> memberOrder = new HashMap<InstpMember, String>();
	
	/**
	 * 成员序号
	 */
	private int memberCount =2;
	
	/**
	 * 最小成员序号
	 */
	private int minMemberOrder =0;
	
	/**
	 * 项目年度
	 */
	private final int year = 2014;
	
	/**
	 * 当前导入项目
	 */
	private SinossProjectApplication spa;
	
	public void work() throws Exception {
		validate();
		initTitle();
		importData();
	}
	
	public void importData() throws Exception { 
		// 当前导入项目条数		 
		int currentImport = 0;
		//总项目数
		int totalImport =0;
		sTool.initSinossApplicationList("base",10001,20001,"2014");
		sTool.initSinossMemberList("base","2014");		
		System.out.println("开始导入...");
		//基地项目
		SystemOption 校级基地 = systemOptionDao.query("researchAgencyType", "04");
		
		Iterator<SinossProjectApplication> appIterator = sTool.sinossApplicationList.iterator();	
		totalImport = sTool.sinossApplicationList.size();
		while(appIterator.hasNext()){		
			spa = appIterator.next();
			System.out.println("当前导入项目名：" + spa.getProjectName() + "  (学校代码：" + spa.getUnitCode() + ")————导入第" + (++currentImport) + "/" + totalImport+ "条数据");//用户查找出错项
			InstpApplication application = instpProjectFinder.findApplication(spa.getProjectName(), spa.getApplyer());
			Agency university = universityFinder.getUnivByName(spa.getUnitName()); //此次需要验证是否找得到
	    
			Institute institute = instituteFinder.getInstitute(university, spa.getProjectJDName().trim(), false);
			if (institute.getType().getCode().equals("06")) {
				institute.setType(校级基地);
			}
		
			if (application == null) {
				application = new InstpApplication();   
			}
			beanFieldUtils.setField(application, "name", spa.getProjectName(), BuiltinMergeStrategies.REPLACE);
			
			//项目机构部门单位
			application.setYear(year);
			application.setApplicantSubmitDate(spa.getBeginDate());  
			application.setUniversity(university);
			application.setAgencyName(spa.getUnitName());
			application.setInstitute(institute);
			application.setDivisionName(institute.getName());	
			application.setFile(spa.getApplyDocName());
			
	        //学科门类+学科代码
			String subject = spa.getSubject();
			String subject1_1 = spa.getSubject1_1();
			String subject1_2 = spa.getSubject1_2();
			String researchDir = spa.getResearchDirection();
			String subjectCode = getSubjectCode(subject, subject1_1, subject1_2, researchDir);
			beanFieldUtils.setField(application, "disciplineType", getSubjectType(subject), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(application, "discipline", subjectCode, BuiltinMergeStrategies.REPLACE);
			
			//项目计划结束时间+经费
			beanFieldUtils.setField(application, "planEndDate", spa.getPlanFinishDate(), BuiltinMergeStrategies.PRECISE_DATE);
			beanFieldUtils.setField(application, "applyFee", tool.getFee(spa.getApplyTotleFee()), BuiltinMergeStrategies.REPLACE);
			if(spa.getOtherFeeSource() != null){
				beanFieldUtils.setField(application, "otherFee", tool.getFee(spa.getOtherFeeSource()), BuiltinMergeStrategies.REPLACE);
			}
			
			//项目最终成果形式
			String[] productType = productTypeNormalizer.getNormalizedProductType(spa.getLastProductMode());
			application.setProductType(productType[0]);
			application.setProductTypeOther(productType[1]);
			
			//添加成员信息	
			if(!memberOrder.isEmpty()){
				memberOrder.clear();
			}
			System.out.println("导入项目成员.........");//用于定位错误
            addApplicant(application,tool.getName(spa.getApplyer()), spa.getUnitName(), this.regulateTitle(spa.getTitle()), spa.getDuty());//添加项目申请人    			
            
            if(!sinossMembers.isEmpty()){
            	sinossMembers.clear();
            }
            Iterator<SinossMembers> iterator = sTool.sinossMemberList.iterator();
			while(iterator.hasNext()){
				SinossMembers sm = iterator.next();
				if(sm.getProjectApplication().getId().equals(spa.getId())){
					sinossMembers.add(sm);
				}
			}
			for (SinossMembers SinossMember : sinossMembers) {
				addMember(application, SinossMember);
			}
			
			memberOrder();
			
			//项目审核信息由ProjectApplicationAudit2014Import.java来导入
			application.setImportedDate(new Date());
			application.setIsImported(0);
			
			//将上述数据跟新到数据库
			dao.addOrModify(application);
		}

		
		System.out.println("over");
		freeMemory();
//		int i = 1/0;
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
			if (position != null && !position.isEmpty()) {
				expert.setPosition(position);
			}
			applicant = expert.getPerson();
			member.setMemberType(2);
		}
		
		application.setApplicantId(applicant.getId());
		application.setApplicantName(personName);
		beanFieldUtils.setField(applicant, "gender", spa.getGender(), BuiltinMergeStrategies.REPLACE);
		beanFieldUtils.setField(applicant, "birthday", spa.getBirthday(), BuiltinMergeStrategies.PRECISE_DATE);
		beanFieldUtils.setField(applicant, "officeAddress", spa.getAddress(), BuiltinMergeStrategies.APPEND);
		beanFieldUtils.setField(applicant, "officePostcode", spa.getPostalCode(), BuiltinMergeStrategies.APPEND);
		beanFieldUtils.setField(applicant, "officePhone", spa.getTelOffice(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
		beanFieldUtils.setField(applicant, "mobilePhone", spa.getTel(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
		beanFieldUtils.setField(applicant, "email", spa.getEmail(), BuiltinMergeStrategies.APPEND);
		beanFieldUtils.setField(applicant, "idcardNumber", spa.getIdCardNo(), BuiltinMergeStrategies.REPLACE);
			
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
				
		member.setMember(applicant);
		member.setMemberName(personName);
		member.setAgencyName(agencyName);
		
		member.setIsDirector(1);
		member.setMemberSn(1);			
		member.setIdcardType(checkIDType(applicant.getIdcardNumber()));
		member.setGroupNumber(1);
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
		
		String JDName = spa.getProjectJDName();
		String memberUnivName = sMember.getUnitName();
		if (memberUnivName == null || memberUnivName.replaceAll("\\s+", "") == "") {
			memberUnivName = "未知机构";
		}
		String memberName = tool.getName(sMember.getName());
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
			memberOrder.put(member, order);		
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
		Set<Entry<InstpMember, String>> memberAndOrders = memberOrder.entrySet();//nameAndOrders只包含sinoss-member里面的非申请人成员
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
	 * 从混乱的ID号中获取ID类型（咨询boss，要不要再项目成员表的证件类型里面添加一个台胞证）
	 */
	public String checkIDType(String id){
		if(id == null){
			return "";
		}else if(id.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")){
			return "身份证";
		} else if(id.contains("护照")){
			return "护照";
		}else if(id.contains("军官")){
			return "军官";
		}else {
			return "其它";
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
	
	/**
	 * 从混乱的称号里面判定是否是学生  (尽量匹配原则：)
	 */
	public boolean checkStudentTitle(String str) throws Exception{	//"科长博士生  讲师（研究生）讲师/研究生  学生   学生/学生   大学生   学生/研究生  在读大学生 在校学生   在校学生/辅导员  MSW学生   讲师/学生办副主任  学生办副主任  五年制学生   法学研究生   博士/讲师......."	
		String str1 = str.replaceAll("\\s+", "");  
		String str2 = str1.replaceAll("[;；,，、。\\./~]+", "/"); 
		String str3 = str2.replaceAll("\\((.*?)\\)", "（$1）"); 
		String str4 = str3.replaceAll("\\（(.*?)\\）", ""); 
		
		if(str4.matches("^(.*)(学生|在读|高中生)$")){
			return true;
		}
		if(str4.matches("^博士生?|硕士生?|研究生|本科生$")){
			return true;
		}
		
		String[] strs = str4.split("/");
		for(String s :strs){
			if(s.matches("^学生$")){      //学生/研究生
				return true;
			}
			if(!s.matches("^(.*)(学生|在读|高中生)$")){    
				return false;
			}
		}
		
		return false;	
	}
	
	/**
	 * 校验学校是否存在
	 */
	private void validate() throws Exception{
		HashSet<String> exMsg = new HashSet<String>();
		
		List<SinossProjectApplication> spaAllApplications =(List<SinossProjectApplication>)dao.query("select spa from SinossProjectApplication spa where spa.typeCode = 'base'");			
		Iterator<SinossProjectApplication> iterator = spaAllApplications.iterator();			
		while (iterator.hasNext()) {
			SinossProjectApplication nextspa = iterator.next();
			Agency universityFindByName = universityFinder.getUnivByName(nextspa.getUnitName());
			if (universityFindByName == null) { 
				exMsg.add("不存在的高校名称: " + nextspa.getUnitName().trim());				
			}
			
			if (nextspa.getProjectJDName() != null){ //判断数据库基地表里面有无该基地     （有的项目没有填基地） 
				Institute institute = instituteFinder.getInstitute(universityFindByName, nextspa.getProjectJDName(), false);//验证用false，不能添加 				
				if(institute == null){
					exMsg.add("不存在的基地：" + nextspa.getProjectJDName());
				}
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public InstpApplication2014Importer(){
	}
	
	public InstpApplication2014Importer(String filepath) {
		reader = new ExcelReader(filepath);
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
			} else {
				return subject2 + "/" + tool.discCodeNameMap.get(subject2);
			}
		} else if ((subject1.equals("75047-99") || subject1.equals("75011-44")) && subject2.length()==0 && subject3.length()==0) {
			throw new RuntimeException();//只有一级学科且一级学科是个综合学科，需要手动处理
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
	 * 通过一级学科获取学科门类  
	 */
	public String getSubjectType(String subject){
		if(subject.startsWith ("170")){
			return "地球科学";
		}else if(subject.startsWith("190")){
			return "心理学";
		}else if(subject.startsWith("630")){
			return "管理学";
		}else if(subject.startsWith("710")){
			return "马克思主义";
		}else if(subject.startsWith("720")){
			if(subject.startsWith("72040")){
				return "逻辑学";
			}else return "哲学";
		}else if(subject.startsWith("730")){
			return "宗教学";
		}else if(subject.startsWith("740")){
			return "语言学";
		}else if (subject.startsWith("75011-44")){
			return "中国文学";
		}else if(subject.startsWith("75047-99")){
			return "外国文学";	
		}else if(subject.startsWith("760")){
			return "艺术学";
		}else if(subject.startsWith("770")){
			return "历史学";
		}else if(subject.startsWith("780")){
			return "考古学";
		}else if(subject.startsWith("790")){
			return "经济学";
		}else if(subject.startsWith("810")){
			return "政治学";
		}else if(subject.startsWith("820")){
			return "法学";
		}else if(subject.startsWith("840")){
			return "社会学";
		}else if(subject.startsWith("850")){
			return "民族学与文化学";
		}else if(subject.startsWith("860")){
			return "新闻学与传播学";
		}else if(subject.startsWith("870")){
			return "图书馆、情报与文献学";
		}else if(subject.startsWith("880")){
			return "教育学";
		}else if(subject.startsWith("890")){
			return "体育学";
		}else if(subject.startsWith("910")){
			return "统计学";
		}else if(subject.startsWith("GAT")){
			return "港澳台问题研究";
		}else if(subject.startsWith("GJW")){
			return "国际问题研究";
		}else if(subject.startsWith("zjxxk")){
			return "综合研究/交叉学科";
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
	
	/**
	 * 规范化职称名（（各式各样的职称都有，本次只需要将职称代号转换，其它的写法一律直接储存））
	 */
	public String regulateTitle(String memberTitle){
		if(memberTitle != null){
			memberTitle = memberTitle.replaceAll("\\s+", "").replaceAll("\\((.*?)\\)", "（$1）");
			if(memberTitle.matches("[0-9][0-9a-zA-Z]*")){//各式各样的职称都有，只需要将职称代号转换，其它的写法一律直接储存
				memberTitle = title.get(memberTitle);
			}
		}		
		return memberTitle;
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
	 * 初始化职称代码 -> 职称名
	 * @throws Exception 
	 */
	public void initTitle() throws Exception{
		Date begin = new Date();
		
		reader.readSheet(0);		
		title = new HashMap<String,String>();
		while (next(reader)) {
			title.put(A, E);
		}
		
		System.out.println("initTitle complete! Used time: " + (new Date().getTime() - begin.getTime()) + "ms");
	}
	
	/**
	 * 学位代码 -> 学位名  
	 * @author 2014-9-8 
	 */
	public String eduDegreeCodeTrans(String eduDegree){
		if(eduDegree == null){
			return null;
		}
		if(eduDegree.equals("1")){
			return "名誉博士";
		}else if(eduDegree.equals("2")){
			return "博士";
		}else if(eduDegree.equals("3")){
			return "硕士";
		}else if(eduDegree.equals("4")){
			return "学士";
		}else if(eduDegree.equals("999")){
			return "其他";
		}else{
			return "未知";
		} 		
	}
	
	/**
	 * 学历代码 -> 学历名  
	 * @author 2014-9-8 
	 */
	public String eduLevelCodeTrans(String eduLevel){
		if(eduLevel == null){
			return null;
		}
		if(eduLevel.equals("0")){
			return "博士研究生";
		}else if(eduLevel.equals("1")){
			return "硕士研究生";
		}else if(eduLevel.equals("2")){
			return "本科生";
		}else if(eduLevel.equals("3")){
			return "大专生";
		}else if(eduLevel.equals("4")){
			return "中专生";
		}else if(eduLevel.equals("5")){
			return "其他";
		}else return "未知";

	}
	
	/**
	 * 必须释放generalProjectFinder在初始化时占据的内存，否则审核信息将无法正常初始化
	 */
	public void freeMemory(){
/*		generalProjectFinder.reset();
		universityFinder.reset();
		departmentFinder.reset();*/
		univPersonFinder.reset();
	/*	academicFinder.reset();
		productTypeNormalizer.reset();
		instituteFinder.reset();
		expertFinder.reset();*/
	}
}
