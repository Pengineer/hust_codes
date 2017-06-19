package csdc.tool.execution.importer;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Academic;
import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.Officer;
import csdc.bean.Person;
import csdc.bean.Teacher;
import csdc.dao.JdbcDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.AcademicFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库Base_Staff表
 * @author xuhan
 *
 */
public class CMIPSBaseStaffImporter extends Importer {
	
	private JdbcTemplateReader reader;

	@Autowired
	protected BeanFieldUtils beanFieldUtils;

	@Autowired
	private UniversityFinder universityFinder;
	
	@Autowired
	private InstituteFinder instituteFinder;
	
	@Autowired
	private UnivPersonFinder univPersonFinder;
	
	@Autowired
	private AcademicFinder academicFinder;
	

	@Autowired
	private Tool tool;
	
	private void resetReader() throws Exception {
		reader.query("SELECT " +
				"[IsLinkMan]" + //A
				",univ.UniName" + //B
				",base.BaseName" + //C
				",[StaffName]" + //D
				",[StaffCode]" + //E
				",[Gender]" + //F
				",[Birthday]" + //G
				",[StartDate]" + //H
				",[EndDate]" + //I
				",[FormerDep]" + //J
				",politic.PoliticStatusName" + //K
				",[OfficePhone]" + //L
				",[MobilePhone]" + //M
				",staff.[Email]" + //N
				",[IsMaster]" + //O
				",staffType.StaffTypeName" + //P
				",title.TitleName" + //Q
				",staffPosition.PositionName" + //R
				",[FinalDegree]" + //S
				",[GetTime]" + //T
				",[FLanguage1]" + //U
				",[LevelFlanguage1]" + //V
				",[FLanguage2]" + //W
				",[LevelFlanguage2]" + //X
				",[NLanguage]" + //Y
				",[LevelNlanguage]" + //Z
				",[ResearchArea]" + //AA
				",staff.[SubjectL1]" + //AB
				",staff.[SubjectL2]" + //AC
				",staff.[SubjectL3]" + //AD
				"FROM [CMIPS].[dbo].[Base_staff] staff " + 
				"JOIN [CMIPS].[dbo].[BaseList] base ON staff.BaseID = base.BaseID " + 
				"JOIN [CMIPS].[dbo].[Lib_University] univ ON base.UniID = univ.UniID " + 
				"JOIN [CMIPS].[dbo].[Lib_PoliticStatus] politic ON staff.PoliticStatusID = politic.PoliticStatusID " + 
				"JOIN [CMIPS].[dbo].[Lib_StaffType] staffType ON staff.StaffTypeID = staffType.StaffTypeID " + 
				"JOIN [CMIPS].[dbo].[Lib_Title] title ON staff.TitleID = title.TitleID " + 
				"JOIN [CMIPS].[dbo].[Lib_StaffPosition] staffPosition ON staff.PositionID = staffPosition.PositionID;");
	}

	
	public void work() throws Exception {
		validate();
		importData();
	}
	
	public void importData() throws Exception {
		resetReader();

		while (next(reader)) {
			if (C.equals("中心测试账号") || D.isEmpty()) {
				continue;
			}

			System.out.println(reader.getCurrentRowIndex() + " " + B + " " + C + " " + D);
			
			Agency university = universityFinder.getUnivByName(B);
			Institute institute = instituteFinder.getInstitute(university, C, false);
			
			Person person = null;
			if (R.equals("研究人员") || R.equals("访问学者")) {
				Teacher teacher = univPersonFinder.findTeacher(D, institute);
				beanFieldUtils.setField(teacher, "startDate", tool.getDate(H), BuiltinMergeStrategies.PRECISE_DATE);
				beanFieldUtils.setField(teacher, "endDate", tool.getDate(I), BuiltinMergeStrategies.PRECISE_DATE);
				if (P.contains("专职")) {
					teacher.setType("专职人员");
				} else {
					//兼职 或者 基地合作者
					teacher.setType("兼职人员");
				}
				beanFieldUtils.setField(teacher, "position", R, BuiltinMergeStrategies.REPLACE);
				person = teacher.getPerson();
			} else {
				Officer officer = univPersonFinder.findOfficer(D, institute);
				beanFieldUtils.setField(officer, "startDate", tool.getDate(H), BuiltinMergeStrategies.PRECISE_DATE);
				beanFieldUtils.setField(officer, "endDate", tool.getDate(I), BuiltinMergeStrategies.PRECISE_DATE);
				if (P.contains("专职")) {
					officer.setType("专职人员");
				} else {
					//兼职 或者 基地合作者
					officer.setType("兼职人员");
				}
				beanFieldUtils.setField(officer, "position", R, BuiltinMergeStrategies.REPLACE);
				person = officer.getPerson();
			}
			
			Academic academic = academicFinder.findAcademic(person);
			
			//StaffCode
			beanFieldUtils.setField(person, "gender", (F.contains("1") ? "女" : "男"), BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(person, "birthday", tool.getDate(G), BuiltinMergeStrategies.PRECISE_DATE);
			//FormerDep
			beanFieldUtils.setField(person, "membership", K, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(person, "officePhone", L, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(person, "mobilePhone", M, BuiltinMergeStrategies.MERGE_PHONE_NUMBER_PREPEND);
			beanFieldUtils.setField(person, "email", N, BuiltinMergeStrategies.APPEND);
			//IsMaster
			beanFieldUtils.setField(academic, "specialityTitle", Q, BuiltinMergeStrategies.REPLACE);
			//staffPosition.PositionName
			beanFieldUtils.setField(academic, "lastDegree", S, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(academic, "degreeDate", tool.getDate(T), BuiltinMergeStrategies.PRECISE_DATE);
			//FLanguage1
			//LevelFlanguage1
			//FLanguage2
			//LevelFlanguage2
			//NLanguage
			//LevelNlanguage
			beanFieldUtils.setField(academic, "researchField", AA, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(academic, "discipline", tool.transformDisc(academic.getDiscipline() + " " + AB + " " + AC + " " + AD), BuiltinMergeStrategies.REPLACE);
			
			if (A.contains("1")) {
				institute.setLinkman(person);
			}
		}
	}
	

	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (C.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(B);
			Institute institute = instituteFinder.getInstitute(university, C, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + B + " - " + C);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	public CMIPSBaseStaffImporter(){
	}
	
	public CMIPSBaseStaffImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}

}
