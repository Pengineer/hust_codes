package csdc.tool.execution.importer;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.Student;
import csdc.bean.Teacher;
import csdc.dao.JdbcDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.execution.importer.tool.Tool;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库AC_Student表
 * @author xuhan
 *
 */
public class CMIPSACStudentImporter extends Importer {
	
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
	private Tool tool;

	
	
	private void resetReader() throws Exception {
		reader.query("SELECT [StudentName] " +	//A
				",studentType.StudentTypeName " +	//B
				",tutor.StaffName " +	//C
				",[InDate] " +	//D
				",[OutDate] " +	//E
				",[ResearchField] " +	//F
				",[ArticleTitle] " +	//G
				",base.BaseName " +	//H
				",univ.UniName " +	//I
				"FROM [CMIPS].[dbo].[AC_Student] student " +
				"JOIN [CMIPS].[dbo].[Lib_StudentType] studentType ON student.StudentTypeID = studentType.StudentTypeID " +
				"JOIN [CMIPS].[dbo].[Base_staff] tutor ON student.StaffID = tutor.StaffID " +
				"JOIN [CMIPS].[dbo].[BaseList] base ON student.BaseID = base.BaseID " +
				"JOIN [CMIPS].[dbo].[Lib_University] univ ON base.UniID = univ.UniID " + 
				"order by [InDate] asc ");
	}
	
	public void work() throws Exception {
		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();

		while (next(reader)) {
			System.out.println(reader.getCurrentRowIndex());
			if (H.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(I);
			Institute institute = instituteFinder.getInstitute(university, H, false);
			
			Student student = univPersonFinder.findStudent(A, institute);
			if (B.equals("硕士")) {
				student.setType("硕士生");
			} else if (B.equals("博士")) {
				student.setType("博士生");
			} else if (B.equals("博士后")) {
				student.setType("博士后");
			}
			
			Teacher tutor = univPersonFinder.findTeacher(C, institute);
			student.setTutor(tutor.getPerson());
			student.setStartDate(tool.getDate(D));
			student.setEndDate(tool.getDate(E));
			if (student.getEndDate() != null && student.getEndDate().compareTo(new Date()) < 0) {
				student.setStatus("毕业");
			} else {
				student.setStatus("在读");
			}
			beanFieldUtils.setField(student, "researchField", F, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(student, "thesisTitle", G, BuiltinMergeStrategies.REPLACE);
		}
	}
	
	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (H.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(I);
			Institute institute = instituteFinder.getInstitute(university, H, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + I + " - " + H);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	
	public CMIPSACStudentImporter(){
	}
	
	public CMIPSACStudentImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
