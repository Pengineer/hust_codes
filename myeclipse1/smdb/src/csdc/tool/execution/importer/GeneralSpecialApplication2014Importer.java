package csdc.tool.execution.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Department;
import csdc.bean.Expert;
import csdc.bean.GeneralApplication;
import csdc.bean.GeneralMember;
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
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.ProductTypeNormalizer;
import csdc.tool.execution.importer.tool.SinossTableTool;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.ExcelReader;

/**
 * 2014年专项项目申请数据入库   (from sinoss)
 * @author pengliang
 * 
 * 备注：专项项目申请数据在入库时被归为一般申请项目，属于一般项目的专项子类
 *
 */
public class GeneralSpecialApplication2014Importer extends Importer{
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
	private DepartmentFinder departmentFinder;

	@Autowired
	private GeneralProjectFinder generalProjectFinder;

	@Autowired
	private AcademicFinder academicFinder;
	
	@Autowired
	private ProductTypeNormalizer productTypeNormalizer;

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
	 * 当前导入项目
	 */
	private SinossProjectApplication spa;
	
	/**
	 * 项目年度
	 */
	private final int year = 2014;
	
	/**
	 * 项目成员序号(申请人是1，成员从2开始)
	 */
	private Map<GeneralMember, String> memberOrder = new HashMap<GeneralMember, String>();
	
	/**
	 * 成员序号
	 */
	private int memberCount =2;
	
	/**
	 * 最小成员序号
	 */
	private int minMemberOrder =0;
	
	public GeneralSpecialApplication2014Importer(){}
	
	public GeneralSpecialApplication2014Importer(String filePath){
		reader = new ExcelReader(filePath);
	}
	
	public void work() throws Exception {
		validate();
		initTitle();
	//	importData();
		fixData();
	}
	/*
	 * 修复数据：setSubtype写成了setResearchType
	 */
	public void fixData(){
		// 当前导入项目条数		  
		int currentImport = 0;
		//总项目数
		int totalImport =0;
		
		System.out.println("开始导入...");
		SystemOption 专项 = systemOptionDao.query("projectType", "07");
		sTool.initSinossApplicationList("select spa from SinossProjectApplication spa where spa.typeCode='special' and spa.year='2014'");
		sTool.initSinossMemberList("special","2014");
		Iterator<SinossProjectApplication> appIterator = sTool.sinossApplicationList.iterator();
		totalImport = sTool.sinossApplicationList.size();
		while(appIterator.hasNext()){
			spa = appIterator.next();
			System.out.println("当前导入项目名：" + spa.getProjectName() + "  (学校代码：" + spa.getUnitCode() + ")————导入第" + (++currentImport) + "/" + totalImport+ "条数据");//用户查找出错项
			GeneralApplication application = generalProjectFinder.findApplication(spa.getProjectName(), spa.getApplyer(), year);
			if(application == null){
				System.out.println("有变更。。。");
			}
			application.setSubtype(专项);
			dao.modify(application);
		}
	}

	public void importData() throws Exception { 
		// 当前导入项目条数		  
		int currentImport = 0;
		//总项目数
		int totalImport =0;
		
		System.out.println("开始导入...");
		SystemOption 专项 = systemOptionDao.query("projectType", "014");
         
		SystemOption 基础研究 = systemOptionDao.query("researchType", "01");
		SystemOption 应用研究 = systemOptionDao.query("researchType", "02");
		SystemOption 实验与发展 = systemOptionDao.query("researchType", "03");
		
		//专项项目		
		sTool.initSinossApplicationList("select spa from SinossProjectApplication spa where spa.typeCode='special' and spa.year='2014'");
		sTool.initSinossMemberList("special","2014");
		Iterator<SinossProjectApplication> appIterator = sTool.sinossApplicationList.iterator();
		totalImport = sTool.sinossApplicationList.size();
		while(appIterator.hasNext()){
			spa = appIterator.next();
			System.out.println("当前导入项目名：" + spa.getProjectName() + "  (学校代码：" + spa.getUnitCode() + ")————导入第" + (++currentImport) + "/" + totalImport+ "条数据");//用户查找出错项
			GeneralApplication application = generalProjectFinder.findApplication(spa.getProjectName(), spa.getApplyer(), year);

			Agency university = universityFinder.getUnivByName(spa.getUnitName()); 
			
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
				application = new GeneralApplication();   
			}
			beanFieldUtils.setField(application, "name", spa.getProjectName(), BuiltinMergeStrategies.REPLACE);
			
			//项目机构部门单位等+申请人
			application.setYear(year);
			application.setApplicantSubmitDate(spa.getApplyDate());  
			application.setUniversity(university);
			application.setAgencyName(university.getName());
			application.setDepartment(department);
			application.setDivisionName(department.getName());
			application.setApplicantId(applicant.getPerson().getId());
			application.setApplicantName(applicantName);
			application.setFile(spa.getApplyDocName());
			
			//项目子类
			//application.setResearchType(专项);
			application.setSubtype(专项);
			
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
			beanFieldUtils.setField(applicant.getPerson(), "officeAddress", spa.getAddress(), BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(applicant.getPerson(), "officePostcode", spa.getPostalCode(), BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(applicant.getPerson(), "officePhone", spa.getTelOffice(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(applicant.getPerson(), "mobilePhone", spa.getTel(), BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(applicant.getPerson(), "email", spa.getEmail(), BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(applicant.getPerson(), "idcardNumber", spa.getIdCardNo(), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(applicant, "position", spa.getDuty(), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(academic, "lastEducation", this.eduLevelCodeTrans(spa.getEduLevel()), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(academic, "lastDegree", this.eduDegreeCodeTrans(spa.getEduDegree()), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(academic, "language", spa.getLanguage(), BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(academic, "specialityTitle", this.regulateTitle(spa.getTitle()), BuiltinMergeStrategies.REPLACE);
														
		    //成员信息
			if(memberOrder != null){
				memberOrder.clear();
			}			
			System.out.println("导入项目成员.........");
			addMember(application, null, applicant);   
			
			if(!sinossMembers.isEmpty()){
            	sinossMembers.clear();
            }
			Iterator<SinossMembers> memIterator = sTool.sinossMemberList.iterator();
			while(memIterator.hasNext()){
				SinossMembers sm = memIterator.next();
				if(sm.getProjectApplication().getId().equals(spa.getId())){
					sinossMembers.add(sm);
				}
			}
			for (SinossMembers SinossMember : sinossMembers) {
				addMember(application, SinossMember,null);
			}
			
			memberOrder();
						
			//项目审核信息由ProjectApplicationAudit2014Import.java来导入
			application.setImportedDate(new Date());
			application.setIsImported(0);
			
			//将上述数据跟新到数据库
			dao.addOrModify(application);
			dao.addOrModify(academic);
		}
		System.out.println("over");
		freeMemory();
	//	int i = 1/0;
	}
	
	//成员表对应多张表
	private void addMember(GeneralApplication application, SinossMembers sMember, Teacher director) throws Exception {
		GeneralMember member = new GeneralMember();  		
		Person person = null;
		if (sMember != null && director == null){   //一般成员
			String memberUnivName = sMember.getUnitName();
			if (memberUnivName == null || memberUnivName.replaceAll("\\s+", "") == "") {
				memberUnivName = "未知机构";
			}
			String memberName = tool.getName(sMember.getName());
			if(memberName == null || "".equals(memberName)){
				memberName = "未知字符";
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
			
			member.setIsDirector(0);
			member.setMemberName(memberName);
			member.setSpecialistTitle(memberTitle);
			String memberCode = StringTool.toDBC(sMember.getCode());//居然有输入全角的３５０１０２１９９００５０５２８１１
			Date birth = sMember.getBirthday();
			String specialty = sMember.getSpecialty();
			String order = sMember.getOrders();
			if(memberCode != null){
				member.setIdcardNumber(memberCode);
				member.setIdcardType(checkIDType(memberCode));
			}		 		
			if(birth != null){
				member.setBirthday(sMember.getBirthday());
			}        
			if(specialty != null){
				member.setMajor(specialty);
			}	
			if(order == null){
				order = "isnull";
			}else{
				member.setMemberSn(Integer.parseInt(order));
			}
			if(!spa.getApplyer().equals(memberName)){ //如果该成员不是申请人，就添加（假定同一项目组里的人不同名）
				memberOrder.put(member, order);		
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
		minMemberOrder = 100;
		Set<Entry<GeneralMember, String>> memberAndOrders = memberOrder.entrySet();//nameAndOrders只包含sinoss-member里面的非申请人成员
		for(Entry<GeneralMember, String> memberAndOrder : memberAndOrders ){
			String val = memberAndOrder.getValue();
			if(!val.equals("isnull") && Integer.parseInt(val) < minMemberOrder){
				minMemberOrder = Integer.parseInt(val);
			}				
		}
		Iterator<Entry<GeneralMember, String>> iterator = memberAndOrders.iterator();
		if(iterator.hasNext()){
			if(iterator.next().getValue().equals("isnull")){  //只要有一个为空，默认所有成员序号都为空				
				for(Entry<GeneralMember, String> memberAndOrder : memberAndOrders){
					memberAndOrder.getKey().setMemberSn(memberCount++);
				}
			} else{ //将最小的设置为2，依次增加（基于现有的顺序）
				if(minMemberOrder == 0){
					for(Entry<GeneralMember, String> memberAndOrder : memberAndOrders){
						GeneralMember gmember = memberAndOrder.getKey();
						gmember.setMemberSn(gmember.getMemberSn()+2);
					}
				}else if(minMemberOrder == 1){
					for(Entry<GeneralMember, String> memberAndOrder : memberAndOrders){
						GeneralMember gmember = memberAndOrder.getKey();
						gmember.setMemberSn(gmember.getMemberSn()+1);
					}
				}
			}
		}
	}
	
	/**
	 * 从混乱的ID号中获取ID类型（咨询boss，要不要再项目成员表的证件类型里面添加一个台胞证）
	 * @author 2014-8-30
	 */
	public String checkIDType(String id){
		if(id.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")){
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
	 * @author 2014-8-30
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
	 * @author 2014-8-30 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private void validate() throws Exception{
		HashSet<String> exMsg = new HashSet<String>();
		
		List<SinossProjectApplication> spaAllApplications =(List<SinossProjectApplication>)dao.query("select spa from SinossProjectApplication spa where spa.typeCode = 'special' and spa.year='2014'");			
		Iterator<SinossProjectApplication> iterator = spaAllApplications.iterator();			
		while (iterator.hasNext()) {
			SinossProjectApplication nextspa = iterator.next();
			Agency universityFindByName = universityFinder.getUnivByName(nextspa.getUnitName().trim());
			if (universityFindByName == null) { 
				exMsg.add("不存在的高校名称: " + nextspa.getUnitName().trim());				
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	/**
	 * 通过一级学科获取学科门类  
	 * @author 2014-8-30 
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
	 * 通过一级学科、二级学科、三级学科获取学科代码  
	 * @author 2014-8-30 
	 */
	public String getSubjectCode(String subject, String subject1_1, String subject1_2, String researchDir){
		if(subject.startsWith("GAT")){
			subject = "980";
		}else if(subject.startsWith("GJW")){
			subject = "990";
		}else if(subject.startsWith("SXZZJY")){
			subject = "970";
		}
		//通过researchDir获取里面的学科代码
		String code = "";
		subject1_2 = tool.transformDisc(researchDir).replaceAll("\\s+", "");//发展经济学（79021）、农村经济学（79057）、数量经济学（79035）    排序得到：79021/发展经济学; 79035/数量经济学; 79057/农村经济学
		String[] researchDirs = subject1_2.split(";");
		for(int i = 0; i<researchDirs.length; i++){
			String[] codesubject = researchDirs[i].split("/");          //code = 790217903579057
			code += codesubject[0];
		}
		
		if(subject == "75047-99" || subject == "75011-44" || subject == "zjxxk"){
			return code;
		}else{
			code = subject;
			if(subject1_1 != null){
				code += subject1_1;
			}
			if(subject1_2 != null){
				code += subject1_2;
			}
			return code;
		}
	}
	
	/**
	 * 规范化职称名（（各式各样的职称都有，本次只需要将职称代号转换，其它的写法一律直接储存））
	 * @author 2014-8-30 
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
	 * 初始化职称代码 -> 职称名
	 * @throws Exception 
	 * @author 2014-8-30 
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
	 * @author 2014-8-30 
	 */
	public void freeMemory(){
		/*generalProjectFinder.reset();
		universityFinder.reset();
		departmentFinder.reset();*/
		univPersonFinder.reset();
		/*academicFinder.reset();
		productTypeNormalizer.reset();
		instituteFinder.reset();
		expertFinder.reset();*/
	}
	
}
