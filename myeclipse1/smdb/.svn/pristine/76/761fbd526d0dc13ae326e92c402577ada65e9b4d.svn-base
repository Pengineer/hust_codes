package csdc.tool.execution.importer;

import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.bean.Agency;
import csdc.bean.Book;
import csdc.bean.Institute;
import csdc.bean.Teacher;
import csdc.dao.JdbcDao;
import csdc.tool.beanutil.BeanFieldUtils;
import csdc.tool.beanutil.mergeStrategy.BuiltinMergeStrategies;
import csdc.tool.execution.finder.BookFinder;
import csdc.tool.execution.finder.InstituteFinder;
import csdc.tool.execution.finder.UnivPersonFinder;
import csdc.tool.execution.finder.UniversityFinder;
import csdc.tool.reader.JdbcTemplateReader;

/**
 * 导入CMIPS数据库AC_ForeignPublication表
 * 按照[成果名+作者名+出版日期]来判重
 * 
 * 
 * @author xuhan
 *
 */
public class CMIPSACForeignPublicationImporter extends Importer {
	
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
	private BookFinder bookFinder;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	
	private void resetReader() throws Exception {
		reader.query(
			"SELECT [ForeignPublicationName], " +	//A
			"[PressRegion], " +	//B
			"[Language], " +	//C
			"[PressDate], " +	//D
			"[Publisher], " +	//E
			"[CoAuther], " +	//F
			"univ.UniName, " +	//G
			"base.BaseName, " +	//H
			"staff.StaffName, " +	//I
			"[Shown] " +	//J
			"FROM [CMIPS].[dbo].[AC_ForeignPublication] pub " +
			"left join [CMIPS].[dbo].BaseList base on pub.[BaseID] = base.[BaseID] " +
			"left join [CMIPS].[dbo].Base_staff staff on pub.StaffID = staff.StaffID " +
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
			System.out.println(reader.getCurrentRowIndex());
			if (H.equals("中心测试账号") || A.isEmpty()) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(G);
			Institute institute = instituteFinder.getInstitute(university, H, false);
			
			Book book = bookFinder.getBook(A, I, D != null ? sdf.parse(D) : null, true);
			
			beanFieldUtils.setField(book, "publishRegion", B, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(book, "originalLanguage", C, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(book, "publishUnit", E, BuiltinMergeStrategies.REPLACE);
			beanFieldUtils.setField(book, "otherAuthorName", F, BuiltinMergeStrategies.APPEND);
			
			if (I != null && I.length() > 0) {
				Teacher author = univPersonFinder.findTeacher(I, institute);
				book.setAuthor(author.getPerson());
				book.setAuthorType(1);
			}
			
			book.setUniversity(university);
			book.setInstitute(institute);
			book.setAgencyName(university.getName());
			book.setDivisionName(institute.getName());
			book.setIsImported(1);
			book.setSubmitStatus(3);
			book.setAuditStatus(3);
			book.setAuditResult(2);
		}
	}
	
	public void validate() throws Exception {
		resetReader();
		
		HashSet exMsg = new HashSet();
		while (next(reader)) {
			if (H.equals("中心测试账号")) {
				continue;
			}
			
			Agency university = universityFinder.getUnivByName(G);
			Institute institute = instituteFinder.getInstitute(university, H, false);
			if (institute == null || !institute.getType().getCode().equals("01") && !institute.getType().getCode().equals("02")) {
				exMsg.add("找不到的部级/省部共建基地：" + G + " - " + H);
			}
		}

		if (exMsg.size() > 0) {
			System.out.println(exMsg.toString().replaceAll(",\\s+", "\n"));
			throw new RuntimeException();
		}
	}
	

	
	
	public CMIPSACForeignPublicationImporter(){
	}
	
	public CMIPSACForeignPublicationImporter(JdbcDao dao) {
		this.reader = new JdbcTemplateReader(dao);
	}
}
