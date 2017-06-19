package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Consultation;
import csdc.bean.Institute;
import csdc.bean.Teacher;
import csdc.dao.JdbcDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.ConsultationFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库AC_RecReport表
 * 按照[成果名+作者名+出版日期]来判重
 * 
 * @author xuhan
 *
 */
public class CMIPSACRecReportImporter extends Importer {
	
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
	private ConsultationFinder consultationFinder;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	private void resetReader() throws Exception {
		reader.query(
			"SELECT" +
			"[ReportName] " +	//A
			",adoptType.TypeName " +	//B
			",[DepartName] " +	//C
			",staff.StaffName " +	//D
			",[CoAuthor] " +	//E
			",[SupportPrj] " +	//F
			",[PressDate] " +	//G
			",[Evaluation] " +	//H
			",base.BaseName " +	//I
			",univ.UniName " +	//J
			",[IsSecret] " +	//K
			"FROM [CMIPS].[dbo].[AC_RecReport] report " +
			"left join [CMIPS].[dbo].[Lib_AdoptType] adoptType on report.TypeID = adoptType.TypeID " +
			"left join [CMIPS].[dbo].Base_staff staff on report.StaffID = staff.StaffID " +
			"left join [CMIPS].[dbo].BaseList base on report.[BaseID] = base.[BaseID] " +
			"left join [CMIPS].[dbo].Lib_University univ on base.UniID = univ.UniID"
  		);
	}
	
	public void work() throws Exception {
		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();
		
		while (next(reader)) {
			if (I.equals("中心测试账号")) {
				continue;
			}
			if (A == null || A.isEmpty()) {
				continue;
			}
			System.out.println(reader.getCurrentRowIndex());
			
			Agency university = universityFinder.getUnivByName(J);
			Institute institute = instituteFinder.getInstitute(university, I, false);
			
			Consultation consultation = consultationFinder.getConsultation(A, D, G != null ? sdf.parse(G) : null, true);
			beanFieldUtils.setField(consultation, "adoptType", B, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(consultation, "useUnit", C, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(consultation, "otherAuthorName", E, BuiltinMergeStrategies.APPEND);
			beanFieldUtils.setField(consultation, "supportProject", F, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(consultation, "evaluation", H, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(consultation, "isSecret", Integer.parseInt(K), BuiltinMergeStrategies.REPLACE);
			
			if (D != null && D.length() > 0) {
				Teacher author = univPersonFinder.findTeacher(D, institute);
				consultation.setAuthor(author.getPerson());
				consultation.setAuthorType(1);
			}
			
			consultation.setUniversity(university);
			consultation.setInstitute(institute);
			consultation.setAgencyName(university.getName());
			consultation.setDivisionName(institute.getName());
			consultation.setIsImported(1);
			consultation.setSubmitStatus(3);
			consultation.setAuditStatus(3);
			consultation.setAuditResult(2);
		}
	}
	
	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (I.equals("中心测试账号")) {
				continue;
			}
			Agency university = universityFinder.getUnivByName(J);
			Institute institute = instituteFinder.getInstitute(university, I, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + J + " - " + I);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	
	public CMIPSACRecReportImporter(){
	}
	
	public CMIPSACRecReportImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
