package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Institute;
import csdc.bean.Paper;
import csdc.bean.SystemOption;
import csdc.bean.Teacher;
import csdc.dao.JdbcDao;
import csdc.dao.SystemOptionDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.PaperFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库AC_JournalPaper表
 * 按照[成果名+作者名+出版日期]来判重
 * 
 * @author xuhan
 *
 */
public class CMIPSACJournalPaperImporter extends Importer {
	
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
	private PaperFinder paperFinder;
	
	@Autowired
	private SystemOptionDao systemOptionDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	
	private void resetReader() throws Exception {
		reader.query(
			"SELECT [PaperName] " +	//A
			",staff.StaffName " + 	//B
			",[JournalNum] " + 	//C
			",[PressDate] " + 	//D
			",base.BaseName " + //E
			",univ.UniName " + 	//F
			",journal.JournalName " +	//G
			"FROM [CMIPS].[dbo].[AC_JournalPaper] paper " +
			"left join [CMIPS].[dbo].Base_staff staff on paper.StaffID = staff.StaffID " + 
			"left join [CMIPS].[dbo].BaseList base on paper.[BaseID] = base.[BaseID] " + 
			"left join [CMIPS].[dbo].Lib_University univ on base.UniID = univ.UniID " +
			"left join [CMIPS].[dbo].Lib_Journal journal on paper.JournalID = journal.JournalID "
		);
	}
	
	public void work() throws Exception {
		validate();
		importData();
	}

	public void importData() throws Exception {
		resetReader();
		
		SystemOption 期刊论文 = systemOptionDao.query("productType", "011");
		
		while (next(reader)) {
			if (E.equals("中心测试账号")) {
				continue;
			}
			if (A.isEmpty()) {
				continue;
			}
			System.out.println(reader.getCurrentRowIndex());
			
			Agency university = universityFinder.getUnivByName(F);
			Institute institute = instituteFinder.getInstitute(university, E, false);
			
			Paper paper = paperFinder.getPaper(A, B, D != null ? sdf.parse(D) : null, true);
			paper.setType(期刊论文);
			beanFieldUtils.setField(paper, "number", C, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(paper, "publication", G, BuiltinMergeStrategies.REPLACE);
			//F 是否署名基地
			
			if (B != null && B.length() > 0) {
				Teacher author = univPersonFinder.findTeacher(B, institute);
				paper.setAuthor(author.getPerson());
				paper.setAuthorType(1);
			}
			
			paper.setUniversity(university);
			paper.setInstitute(institute);
			paper.setAgencyName(university.getName());
			paper.setDivisionName(institute.getName());
			paper.setIsImported(1);
			paper.setSubmitStatus(3);
			paper.setAuditStatus(3);
			paper.setAuditResult(2);
			
		}
	}
	
	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (E.equals("中心测试账号")) {
				continue;
			}
			Agency university = universityFinder.getUnivByName(F);
			Institute institute = instituteFinder.getInstitute(university, E, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + F + " - " + E);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	
	
	public CMIPSACJournalPaperImporter(){
	}
	
	public CMIPSACJournalPaperImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
